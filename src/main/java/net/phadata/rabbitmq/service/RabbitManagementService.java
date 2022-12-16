package net.phadata.rabbitmq.service;


import net.phadata.rabbitmq.model.RabbitBinding;
import net.phadata.rabbitmq.model.RabbitExchange;
import net.phadata.rabbitmq.model.RabbitQueue;

import java.util.List;

/**
 * @author felix
 */
public interface RabbitManagementService {

    /**
     * 创建交换机
     *
     * @param rabbitExchange 交换机
     * @return 更新记录
     */
    int createExchange(RabbitExchange rabbitExchange);

    /**
     * 查询所有交换机
     *
     * @return 交换机
     */
    List<RabbitExchange> findAllExchange();

    /**
     * 创建队列
     *
     * @param rabbitQueue 队列
     * @return 更新记录
     */
    int createQueue(RabbitQueue rabbitQueue);

    /**
     * 查询所有队列
     * @return 队列
     */
    List<RabbitQueue> findAllQueue();

    /**
     * 创建绑定
     *
     * @param rabbitBinding 绑定 信息
     * @return 更新记录
     */
    int createBinding(RabbitBinding rabbitBinding);

    int unBinding(Integer id);

    /**
     * 查询绑定
     * @return 绑定
     */
    List<RabbitBinding> findAllBinding();
}
