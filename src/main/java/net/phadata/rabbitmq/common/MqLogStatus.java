package net.phadata.rabbitmq.common;

/**
 * mq 消息日志状态
 *
 * @author felix
 */
public enum MqLogStatus {

    /**
     * 消息消费成功
     */
    SEND_SUCCESS(0, "发送到交换机成功"),

    SEND_EXCHANGE_FAIL(1, "发送到交换机失败失败"),

    EXCHANGE_ROUTE_FAIL(2, "交换机到路由失败");



    private final int status;

    private final String msg;

    MqLogStatus(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public String msg() {
        return msg;
    }

    public int status() {
        return status;
    }
}
