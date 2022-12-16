package net.phadata.rabbitmq.exception.handler;


import net.phadata.rabbitmq.common.BizException;
import net.phadata.rabbitmq.model.ErrorCode;
import net.phadata.rabbitmq.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tanwei
 * @desc
 * @time 1/6/21 10:21 AM
 * @since 1.0.0
 */
@Slf4j
@RestControllerAdvice
public class ApplicationExceptionHandler {


    @ExceptionHandler(value = Exception.class)
    private Result<?> exceptionHandler(Exception exception) {
        if (exception instanceof BizException) {
            log.error("自定义异常：{} ", exception.getMessage());
            log.error(getTrace(exception));
            return Result.thin(ErrorCode.PARAMETER_EXCEPTION.getCode(), exception.getMessage(), "");
        }
        if (exception instanceof MethodArgumentNotValidException) {
            List<FieldError> fieldErrors = ((MethodArgumentNotValidException) exception).getBindingResult().getFieldErrors();
            Map<String, Object> map = new HashMap<>(16);
            for (FieldError fieldError : fieldErrors) {
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            log.error("{}",map);
            log.error("自定义异常：{} ", exception.getMessage());
            log.error(getTrace(exception));
            return Result.thin(ErrorCode.PARAMETER_EXCEPTION, map);
        }
        log.error("服务异常：{} ", exception.getMessage(), exception);
        log.error(getTrace(exception));
        return Result.thin(ErrorCode.FAILED.getCode(), ErrorCode.FAILED.getMessage(), null);
    }


    public static String getTrace(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }
}
