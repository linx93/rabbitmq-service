package net.phadata.rabbitmq.config;

import net.phadata.rabbitmq.common.MqLogStatus;
import net.phadata.rabbitmq.service.RabbitmqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author fujie
 * @version 1
 * @date 2021-06-17 12:29:29
 */
@Slf4j
@Component
@Configuration
public class ReturnsCallbackConfig implements RabbitTemplate.ReturnsCallback {

    private final RabbitTemplate rabbitTemplate;

    private final RabbitmqService rabbitmqService;

    public ReturnsCallbackConfig(RabbitTemplate rabbitTemplate, RabbitmqService rabbitmqService) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitmqService = rabbitmqService;
    }

    @PostConstruct
    private void init() {
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnsCallback(this);
    }


    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        String messageExchange = returnedMessage.getExchange();
        Message message = returnedMessage.getMessage();
        String msgId = (String) message.getMessageProperties().getHeaders().get("spring_returned_message_correlation");
        int replyCode = returnedMessage.getReplyCode();
        String replyText = returnedMessage.getReplyText();
        String messageRoutingKey = returnedMessage.getRoutingKey();
        String desc = String.format("交换机到路由投递失败 交换机: %s 路由: %s 返回码: %s 返回错误信息: %s", messageExchange, messageRoutingKey, replyCode, replyText);
        rabbitmqService.updateStatusAndFailDesc(msgId, desc, MqLogStatus.EXCHANGE_ROUTE_FAIL.status());
        log.error("交换机到路由投递失败 交换机: {} 路由:{} 返回码:{} 返回错误信息:{}", messageExchange, messageRoutingKey, replyCode, replyText);
        log.error("交换机到路由投递失败 message: {}", message);
    }
}
