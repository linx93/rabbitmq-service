package net.phadata.rabbitmq.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.phadata.rabbitmq.common.ExchangeTypeEnum;
import net.phadata.rabbitmq.service.SendMsgService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author felix
 */
@Slf4j
@Service
public class SendMsgServiceImpl implements SendMsgService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     *
     * @param msgId        msgid
     * @param exchange     交换机
     * @param exchangeType 交换机类型
     * @param routingKey   路由
     * @param content      消息
     */
    @Async
    @Override
    public void sendMsg(String msgId, String exchange, String exchangeType, String routingKey, byte[] content) {
        CorrelationData correlationData = new CorrelationData(msgId);
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setMessageId(msgId);
        Message message = new Message(content, messageProperties);
        correlationData.setReturned(new ReturnedMessage(message,
                0, "",
                exchange, routingKey));

        if (ExchangeTypeEnum.FANOUT.name().equalsIgnoreCase(exchangeType)) {
            log.info("fanout 模式发送");
            rabbitTemplate.convertAndSend(exchange, "", message, correlationData);
        }

        if (ExchangeTypeEnum.DIRECT.name().equalsIgnoreCase(exchangeType)) {
            log.info("direct 模式发送");
            rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationData);
        }
    }
}
