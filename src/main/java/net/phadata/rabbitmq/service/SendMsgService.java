package net.phadata.rabbitmq.service;

public interface SendMsgService {

    /**
     * 发送消息
     *
     * @param msgId      msgid
     * @param exchange   交换机
     * @param routingKey 路由
     * @param exchangeType 交换机类型
     * @param content    消息
     */
    void sendMsg(String msgId, String exchange,String exchangeType, String routingKey, byte[] content);

}
