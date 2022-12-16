package net.phadata.rabbitmq.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author fujie
 * @version 1
 * @date 2021-04-22 10:43:09
 */
@Data
@Entity
@Accessors(chain = true)
public class RabbitmqLog {

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 36, unique = true)
    private String msgId;
    /**
     * 操作人 数字身份
     */
    private String operator;
    /**
     * 日志标题
     */
    private String title;

    /**
     * 日志失败描述
     */
    @Column(length = 1000)
    private String description;


    /**
     * 交换机
     */
    private String exchange;

    /**
     * 交换机类型
     */
    private String exchangeType;


    /**
     * 队列
     */
    private String queue;


    /**
     * 路由key
     */
    private String routingKey;

    /**
     * 消息内容
     */
    @Column(columnDefinition = "TEXT")
    private String content;

    /**
     * 重试次数
     */
    private Integer retry=0;

    /**
     * 是否成功
     */
    private Integer status;


    /**
     * 日志创建时间
     */
    @CreatedDate
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @LastModifiedDate
    private LocalDateTime updateTime;
}
