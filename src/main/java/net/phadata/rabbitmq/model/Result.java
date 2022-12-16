package net.phadata.rabbitmq.model;

import lombok.Data;

/**
 * @author fujie
 * @version 1
 * @date 2021-06-16 15:45:23
 */
@Data
public class Result<T> {
    private String code;
    private String message;
    private T payload;


    private Result<T> payload(T payload) {
        this.payload = payload;
        return this;
    }

    private Result<T> code(String code) {
        this.code = code;
        return this;
    }

    private Result<T> message(String message) {
        this.message = message;
        return this;
    }

    public static <T> Result<T> thin(String code, String message, T payload) {
        return new Result<T>().code(code).message(message).payload(payload);
    }

    public static <T> Result<T> thin(ErrorCode code, T payload) {
        return new Result<T>().code(code.getCode()).message(code.getMessage()).payload(payload);
    }
}
