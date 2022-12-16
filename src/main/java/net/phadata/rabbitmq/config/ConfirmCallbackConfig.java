package net.phadata.rabbitmq.config;

import net.phadata.rabbitmq.common.MqLogStatus;
import net.phadata.rabbitmq.service.RabbitmqService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author fujie
 * @version 1
 * @date 2021-06-17 12:36:39
 */
@Slf4j
@Component
@Configuration
public class ConfirmCallbackConfig implements RabbitTemplate.ConfirmCallback {

    private final RabbitmqService rabbitmqService;

    public ConfirmCallbackConfig(RabbitmqService rabbitmqService) {
        this.rabbitmqService = rabbitmqService;
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String message) {
        String msgId = correlationData.getId();
        if (!ack) {
            rabbitmqService.updateStatusAndFailDesc(msgId, message, MqLogStatus.SEND_EXCHANGE_FAIL.status());
            log.error("交换机接收失败,失败原因：{}", message);
        }
        log.info("交换机接收消息-> msgId: {},是否成功ack：{}, 投递交换机: {}", msgId, ack, correlationData.getReturned().getExchange());
    }
}
