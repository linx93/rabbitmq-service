package net.phadata.rabbitmq.common;

/**
 * 交换机类型
 * @author felix
 */
public enum ExchangeTypeEnum {
    /**
     * 交换机类型
     */
    DIRECT,
    TOPIC,
    HEADERS,
    FANOUT;
}
