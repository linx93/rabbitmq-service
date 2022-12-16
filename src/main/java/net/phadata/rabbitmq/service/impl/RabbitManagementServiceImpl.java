package net.phadata.rabbitmq.service.impl;

import net.phadata.rabbitmq.mapper.RabbitBindingMapper;
import net.phadata.rabbitmq.mapper.RabbitExchangeMapper;
import net.phadata.rabbitmq.mapper.RabbitQueueMapper;
import net.phadata.rabbitmq.model.RabbitBinding;
import net.phadata.rabbitmq.model.RabbitExchange;
import net.phadata.rabbitmq.model.RabbitQueue;
import net.phadata.rabbitmq.service.RabbitManagementService;
import net.phadata.rabbitmq.utils.DateUtil;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author felix
 */
@Service
public class RabbitManagementServiceImpl implements RabbitManagementService {

    private final RabbitQueueMapper rabbitQueueMapper;

    private final RabbitExchangeMapper rabbitExchangeMapper;

    private final RabbitBindingMapper rabbitBindingMapper;

    private final AmqpAdmin rabbitAdmin;

    /**
     * 死信队列 交换机标识符
     */
    public static final String DEAD_LETTER_QUEUE_KEY = "x-dead-letter-exchange";
    /**
     * 死信队列交换机绑定键标识符
     */
    public static final String DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";

    public RabbitManagementServiceImpl(RabbitQueueMapper rabbitQueueMapper, RabbitExchangeMapper rabbitExchangeMapper, RabbitBindingMapper rabbitBindingMapper, AmqpAdmin rabbitAdmin) {
        this.rabbitQueueMapper = rabbitQueueMapper;
        this.rabbitExchangeMapper = rabbitExchangeMapper;
        this.rabbitBindingMapper = rabbitBindingMapper;
        this.rabbitAdmin = rabbitAdmin;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int createExchange(RabbitExchange rabbitExchange) {
        rabbitExchange.setCreateTime(DateUtil.getSecond());
        rabbitExchange.setUpdateTime(DateUtil.getSecond());
        RabbitExchange exchange = rabbitExchangeMapper.save(rabbitExchange);
        rabbitAdmin.declareExchange(tranSealExchange(exchange));
        return exchange.getId();
    }

    @Override
    public List<RabbitExchange> findAllExchange() {
        return rabbitExchangeMapper.findAll();
    }

    @Override
    public List<RabbitQueue> findAllQueue() {
        List<RabbitQueue> queues = rabbitQueueMapper.findAll();
        if (!queues.isEmpty()) {
            for (RabbitQueue queue : queues) {
                QueueInformation queueInfo = rabbitAdmin.getQueueInfo(queue.getQueueName());
                if (queueInfo != null) {
                    queue.setQueueInformation(queueInfo);
                }
            }
        }
        return queues;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int createQueue(RabbitQueue rabbitQueue) {
        rabbitQueue.setCreateTime(DateUtil.getSecond());
        rabbitQueue.setUpdateTime(DateUtil.getSecond());
        RabbitQueue queue = rabbitQueueMapper.save(rabbitQueue);
        rabbitAdmin.declareQueue(tranSealQueue(queue));
        return queue.getId();
    }

    /**
     * 创建绑定
     *
     * @param rabbitBinding 绑定 信息
     * @return 更新记录
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int createBinding(RabbitBinding rabbitBinding) {
        rabbitBinding.setCreateTime(DateUtil.getSecond());
        rabbitBinding.setUpdateTime(DateUtil.getSecond());
        RabbitBinding bind = rabbitBindingMapper.save(rabbitBinding);
        Optional<RabbitQueue> queue = rabbitQueueMapper.findById(bind.getRabbitQueue());
        Optional<RabbitExchange> rabbitExchange = rabbitExchangeMapper.findById(bind.getRabbitExchange());
        if (queue.isPresent() && rabbitExchange.isPresent()) {
            Binding binding = new Binding(queue.get().getQueueName(), Binding.DestinationType.QUEUE, rabbitExchange.get().getExchangeName(), rabbitBinding.getRoutingKey(), null);
            rabbitAdmin.declareBinding(binding);
        }
        return bind.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int unBinding(Integer id) {
        Optional<RabbitBinding> bind = rabbitBindingMapper.findById(id);
        if (bind.isPresent()) {
            Optional<RabbitQueue> queue = rabbitQueueMapper.findById(bind.get().getRabbitQueue());
            Optional<RabbitExchange> rabbitExchange = rabbitExchangeMapper.findById(bind.get().getRabbitExchange());
            if (queue.isPresent() && rabbitExchange.isPresent()) {
                Binding binding = new Binding(queue.get().getQueueName(), Binding.DestinationType.QUEUE, rabbitExchange.get().getExchangeName(), bind.get().getRoutingKey(), null);
                rabbitAdmin.removeBinding(binding);
            }
        }
        rabbitBindingMapper.deleteById(id);
        return 1;
    }

    @Override
    public List<RabbitBinding> findAllBinding() {
        List<RabbitBinding> bindings = rabbitBindingMapper.findAll();
        if (!bindings.isEmpty()) {
            for (RabbitBinding bind : bindings) {
                Optional<RabbitQueue> queue = rabbitQueueMapper.findById(bind.getRabbitQueue());
                Optional<RabbitExchange> rabbitExchange = rabbitExchangeMapper.findById(bind.getRabbitExchange());
                if (queue.isPresent() && rabbitExchange.isPresent()) {
                    bind.setExchangeName(rabbitExchange.get().getExchangeName());
                    bind.setQueueName(queue.get().getQueueName());
                }
            }
        }
        return bindings;
    }

    /**
     * 队列的对象转换
     *
     * @param queue 自定义的队列信息
     * @return RabbitMq的Queue对象
     */
    private Queue tranSealQueue(RabbitQueue queue) {
        Map<String, Object> arguments = queue.getArguments();
        //判断是否需要绑定死信队列
        if (queue.getDeadExchangeName() != null && queue.getDeadRoutingKey() != null) {
            //设置响应参数
            if (queue.getArguments() == null) {
                arguments = new HashMap<>(2);
            }
            arguments.put(DEAD_LETTER_QUEUE_KEY, queue.getDeadExchangeName());
            arguments.put(DEAD_LETTER_ROUTING_KEY, queue.getDeadRoutingKey());
        }
        return new Queue(queue.getQueueName(), queue.getDurable(), queue.getExclusive(), queue.getAutoDelete(), arguments);
    }


    /**
     * 将配置信息转换交换机信息
     *
     * @param exchangeInfo 自定义交换机信息
     * @return RabbitMq的Exchange的信息
     */
    private Exchange tranSealExchange(RabbitExchange exchangeInfo) {
        AbstractExchange exchange;
        //判断类型
        switch (exchangeInfo.getExchangeType().toUpperCase(Locale.ROOT)) {
            //广播模式：
            case "FANOUT":
                exchange = new FanoutExchange(exchangeInfo.getExchangeName(), exchangeInfo.getDurable(), exchangeInfo.getAutoDelete(), exchangeInfo.getArguments());
                break;
            //通配符模式
            case "TOPIC":
                exchange = new TopicExchange(exchangeInfo.getExchangeName(), exchangeInfo.getDurable(), exchangeInfo.getAutoDelete(), exchangeInfo.getArguments());
                break;
            case "HEADERS":
                exchange = new HeadersExchange(exchangeInfo.getExchangeName(), exchangeInfo.getDurable(), exchangeInfo.getAutoDelete(), exchangeInfo.getArguments());
                break;
            //直连模式
            default:
                exchange = new DirectExchange(exchangeInfo.getExchangeName(), exchangeInfo.getDurable(), exchangeInfo.getAutoDelete(), exchangeInfo.getArguments());
                break;
        }
        //设置延迟队列
        exchange.setDelayed(exchangeInfo.getDelayed());
        return exchange;
    }

}
