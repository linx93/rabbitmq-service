package net.phadata.rabbitmq.common;

/**
 * @author fujie
 * @version 1
 * @date 2021-06-16 15:53:45
 */
public class BizException extends RuntimeException {

    public BizException() {
        super();
    }

    public BizException(String message) {
        super(message);
    }
}
