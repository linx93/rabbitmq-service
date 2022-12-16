package net.phadata.rabbitmq.controller;

import net.phadata.rabbitmq.model.BizMessage;
import net.phadata.rabbitmq.model.ErrorCode;
import net.phadata.rabbitmq.model.Result;
import net.phadata.rabbitmq.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @description: 发送到 mq
 * @Author: kb
 * @Date: 2021-06-11 11:13
 */
@RestController
@RequestMapping(value = "/api/producer")
public class ProducerController {


    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public Result<?> send(@RequestBody @Valid BizMessage bizMessage) {
        messageService.createMessage(bizMessage);
        return Result.thin(ErrorCode.SUCCESS, null);
    }
}
