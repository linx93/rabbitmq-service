package net.phadata.rabbitmq.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.phadata.rabbitmq.mapper.RabbitmqLogRepository;
import net.phadata.rabbitmq.model.RabbitmqLog;
import net.phadata.rabbitmq.service.RabbitmqService;
import net.phadata.rabbitmq.utils.DateUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author fujie
 * @version 1
 * @date 2021-06-17 11:00:41
 */
@Slf4j
@Service
public class RabbitmqServiceImpl implements RabbitmqService {


    private final RabbitmqLogRepository rabbitmqLogRepository;

    public RabbitmqServiceImpl(RabbitmqLogRepository rabbitmqLogRepository) {
        this.rabbitmqLogRepository = rabbitmqLogRepository;
    }

    @Async
    @Override
    public RabbitmqLog save(RabbitmqLog rabbitmqLog) {
        rabbitmqLog.setUpdateTime(DateUtil.localDateTime());
        rabbitmqLog.setCreateTime(DateUtil.localDateTime());
        Optional<RabbitmqLog> repository = rabbitmqLogRepository.findByMsgId(rabbitmqLog.getMsgId());
        if (repository.isEmpty()) {
            return rabbitmqLogRepository.save(rabbitmqLog);
        }
        return null;
    }

    @Override
    public List<RabbitmqLog> findAllByStatusIsNot(int status) {
        return rabbitmqLogRepository.findAllByStatusIsNot(status);

    }

    @Override
    public List<RabbitmqLog> findAllByStatus(int status) {
        return rabbitmqLogRepository.findAllByStatus(status);
    }

    @Override
    public int updateStatusAndFailDesc(String msgId, String failDesc, int status) {
        return rabbitmqLogRepository.updateStatusAndFailDesc(msgId, status, failDesc, DateUtil.localDateTime());
    }
    @Override
    public int updateTryCount(String msgId, int retry) {
        return rabbitmqLogRepository.updateTryCount(msgId, retry, DateUtil.localDateTime());
    }

    @Override
    public List<RabbitmqLog> findAll() {
        return rabbitmqLogRepository.findAll();
    }
}
