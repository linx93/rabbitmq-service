package net.phadata.rabbitmq.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.phadata.rabbitmq.common.BizException;
import net.phadata.rabbitmq.common.MqLogStatus;
import net.phadata.rabbitmq.model.BizMessage;
import net.phadata.rabbitmq.model.RabbitmqLog;
import net.phadata.rabbitmq.service.MessageService;
import net.phadata.rabbitmq.service.RabbitmqService;
import net.phadata.rabbitmq.service.SendMsgService;
import net.phadata.rabbitmq.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @author felix
 */
@Slf4j
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private RabbitmqService rabbitmqService;
    @Autowired
    private PlatformTransactionManager transactionManager;


    @Autowired
    private SendMsgService sendMsgService;

    /**
     * 创建消息体并保存
     *
     * @param message 消息内容
     * @return 更新记录
     */
    @Override
    public int createMessage(BizMessage message) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        //新发起一个事务
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            String msgId = UUID.randomUUID().toString();
            saveRabbitmqLog(message, msgId);
            BizMessage.BaseMqInfo baseMqInfo = message.getBaseMqInfo();
            sendMsgService.sendMsg(msgId, baseMqInfo.getExchange(), baseMqInfo.getExchangeType(), baseMqInfo.getRoutingKey(), message.getBaseMqInfo().getContent().getBytes(StandardCharsets.UTF_8));
            transactionManager.commit(status);
            return 1;
        } catch (Exception e) {
            log.error("保存日志失败 {}", e.getMessage(), e);
            transactionManager.rollback(status);
            e.printStackTrace();
            throw new BizException("保存业务和消息日志出错: " + e.getMessage());
        }

    }

    private Object msgToObj(byte[] data) {
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
             ObjectInputStream ois = new ObjectInputStream(inputStream)) {
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveRabbitmqLog(BizMessage message, String msgId) {

        RabbitmqLog rabbitmqLog = new RabbitmqLog()
                .setContent(message.getBaseMqInfo().getContent())
                .setExchange(message.getBaseMqInfo().getExchange())
                .setQueue(message.getBaseMqInfo().getQueue())
                .setRoutingKey(message.getBaseMqInfo().getRoutingKey())
                .setOperator(message.getDtid())
                .setRetry(0)
                .setStatus(MqLogStatus.SEND_SUCCESS.status())
                .setMsgId(msgId)
                .setTitle(message.getTitle())
                .setExchangeType(message.getBaseMqInfo().getExchangeType())
                .setCreateTime(DateUtil.localDateTime())
                .setUpdateTime(DateUtil.localDateTime())
                .setDescription("");
        rabbitmqService.save(rabbitmqLog);



    }

}
