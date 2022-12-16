package net.phadata.rabbitmq.service;

import net.phadata.rabbitmq.model.RabbitmqLog;

import java.util.List;

/**
 * @author felix
 */
public interface RabbitmqService {

    /**
     * 保存日志
     *
     * @param rabbitmqLog 日志对象
     * @return 日志
     */
    RabbitmqLog save(RabbitmqLog rabbitmqLog);

    /**
     * 查询状态 不是 status
     *
     * @param status 状态
     * @return 日志
     */
    List<RabbitmqLog> findAllByStatusIsNot(int status);

    /**
     * 查询状态 是 status
     *
     * @param status 状态
     * @return 日志
     */
    List<RabbitmqLog> findAllByStatus(int status);

    /**
     * 更新状态
     *
     * @param msgId  msgId
     * @param status 状态
     * @param failDesc 失败描述
     * @return 更新记录
     */
    int updateStatusAndFailDesc(String msgId,String failDesc, int status);

    /**
     * 更新重试记录
     *
     * @param msgId 消息唯一id
     * @param retry 重试次数
     * @return 更新记录
     */
    int updateTryCount(String msgId, int retry);

    /**
     * 查询所有
     *
     * @return 日志
     */
    List<RabbitmqLog> findAll();
}
