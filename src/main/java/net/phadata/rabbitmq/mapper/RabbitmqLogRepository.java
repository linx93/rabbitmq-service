package net.phadata.rabbitmq.mapper;

import net.phadata.rabbitmq.model.RabbitmqLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author fujie
 * @version 1
 * @date 2021-04-22 10:47:15
 */
@Repository
public interface RabbitmqLogRepository extends JpaRepository<RabbitmqLog, Long> {

    /**
     * 查询状态为不是 status 的日志
     *
     * @param status 状态
     * @return 状态
     */
    List<RabbitmqLog> findAllByStatusIsNot(int status);

    /**
     * 查询状态为是 status 的日志
     *
     * @param status 状态
     * @return 状态
     */
    List<RabbitmqLog> findAllByStatus(int status);

    /**
     * 查询日志
     *
     * @param msgId 消息唯一id
     * @return 日志
     */
    Optional<RabbitmqLog> findByMsgId(String msgId);

    /**
     * 更新状态
     *
     * @param msgId      消息唯一id
     * @param status     状态
     * @param updateTime 更新时间
     * @return 更新记录
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying(clearAutomatically = true)
    @Query(value = "update rabbitmq_log set status=?2 ,update_time=?3 where msg_id=?1", nativeQuery = true)
    int updateStatus(String msgId, int status, Long updateTime);

    /**
     * 更新重试记录
     *
     * @param msgId      消息唯一id
     * @param retry      重试次数
     * @param updateTime 更新时间
     * @return 更新记录
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying(clearAutomatically = true)
    @Query(value = "update rabbitmq_log set retry=?2 ,update_time=?3 where msg_id=?1", nativeQuery = true)
    int updateTryCount(String msgId, int retry, LocalDateTime updateTime);

    /**
     * 删除日志
     *
     * @param msgId msgid
     * @return 更新记录
     */
    int deleteByMsgId(String msgId);

    /**
     * 更新状态是失败描述
     *
     * @param msgId    消息id
     * @param status   状态
     * @param failDesc 失败描述
     * @param second   更新时间
     * @return 更新记录
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying(clearAutomatically = true)
    @Query(value = "update rabbitmq_log set status=?2 ,description=?3,update_time=?4 where msg_id=?1", nativeQuery = true)
    int updateStatusAndFailDesc(String msgId, int status, String failDesc, LocalDateTime second);
}
