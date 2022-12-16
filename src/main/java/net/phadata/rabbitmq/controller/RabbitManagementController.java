package net.phadata.rabbitmq.controller;

import net.phadata.rabbitmq.model.*;
import net.phadata.rabbitmq.service.RabbitManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author felix
 */
@Deprecated
@RestController
@RequestMapping(value = "/api/rabbit")
public class RabbitManagementController {

    @Autowired
    private RabbitManagementService rabbitManagementService;


    @PostMapping(value = "/createExchange")
    public Result<?> createExchange(@RequestBody @Valid RabbitExchange rabbitExchange) {
        rabbitManagementService.createExchange(rabbitExchange);
        return Result.thin(ErrorCode.SUCCESS, null);
    }

    @PostMapping(value = "/createQueue")
    public Result<?> createQueue(@RequestBody @Valid RabbitQueue rabbitQueue) {
        rabbitManagementService.createQueue(rabbitQueue);
        return Result.thin(ErrorCode.SUCCESS, null);
    }

    @GetMapping(value = "/findAllExchange")
    public Result<?> findAllExchange() {
        List<RabbitExchange> allExchange = rabbitManagementService.findAllExchange();
        return Result.thin(ErrorCode.SUCCESS, allExchange);
    }

    @PostMapping(value = "/findAllQueue")
    public Result<?> findAllQueue() {
        List<RabbitQueue> allQueue = rabbitManagementService.findAllQueue();
        return Result.thin(ErrorCode.SUCCESS, allQueue);
    }

    @PostMapping(value = "/createBinding")
    public Result<?> createBinding(@RequestBody @Valid RabbitBinding rabbitBinding) {
        rabbitManagementService.createBinding(rabbitBinding);
        return Result.thin(ErrorCode.SUCCESS, null);
    }

    @GetMapping(value = "/findAllBinding")
    public Result<?> findAllBinding() {
        List<RabbitBinding> allBinding = rabbitManagementService.findAllBinding();
        return Result.thin(ErrorCode.SUCCESS, allBinding);
    }
}
