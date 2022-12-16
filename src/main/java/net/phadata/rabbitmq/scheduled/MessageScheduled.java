package net.phadata.rabbitmq.scheduled;

import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import net.phadata.rabbitmq.common.MqLogStatus;
import net.phadata.rabbitmq.model.RabbitmqLog;
import net.phadata.rabbitmq.service.RabbitmqService;
import net.phadata.rabbitmq.service.SendMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author fujie
 * @version 1
 * @date 2021-06-18 14:36:14
 */
@Slf4j
@Component
public class MessageScheduled extends IJobHandler {

    private final RabbitmqService rabbitmqService;


    private final SendMsgService sendMsgService;


    @Autowired
    public MessageScheduled(RabbitmqService rabbitmqService,SendMsgService sendMsgService) {
        this.rabbitmqService = rabbitmqService;
        this.sendMsgService=sendMsgService;
    }

    /**
     * 最大重试投递次数
     */
    private static final int MAX_RETRY_COUNT = 15;


    /**
     * 每30s拉取投递失败的消息, 重新投递
     */
    @XxlJob("resendHandler")
    public void resendHandler() {
        List<RabbitmqLog> logs = rabbitmqService.findAllByStatusIsNot(MqLogStatus.SEND_SUCCESS.status());
        logs.forEach(log -> {
            String msgId = log.getMsgId();
            if (log.getRetry() < MAX_RETRY_COUNT) {
                // 投递次数+1
                rabbitmqService.updateTryCount(msgId, log.getRetry() + 1);
                // 重新投递
                sendMsgService.sendMsg(msgId, log.getExchange(), log.getExchangeType(), log.getRoutingKey(), log.getContent().getBytes(StandardCharsets.UTF_8));
                MessageScheduled.log.info("第 " + (log.getRetry() + 1) + " 次重新投递消息");
            }
        });
    }

    @Override
    public void execute() throws Exception {
        log.info("xxl-job-resendHandler");
    }
}
