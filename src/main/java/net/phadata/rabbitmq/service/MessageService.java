package net.phadata.rabbitmq.service;

import net.phadata.rabbitmq.model.BizMessage;


/**
 * @author felix
 */
public interface MessageService {

    /**
     * 创建消息
     *
     * @param message 消息内容
     * @return 更新记录
     */
    int createMessage(BizMessage message);

   }
