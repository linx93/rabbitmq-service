package net.phadata.rabbitmq.model;

import lombok.Data;
import org.springframework.amqp.core.QueueInformation;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * 队列的详细信息
 * 提供默认的配置参数
 *
 * @author felix
 */
@Data
@Entity
public class RabbitQueue {

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 队列名
     * 必填
     */
    @NotBlank(message = "不能为空")
    private String queueName;

    /**
     * 是否持久化
     * 默认true：当RabbitMq重启时，消息不丢失
     */
    @Column(columnDefinition = "tinyint(1) default 1")
    private Boolean durable = true;

    /**
     * 是否具有排他性
     * 默认false：可以多个消息者消费同一个队列
     */
    @Column(columnDefinition = "tinyint(1) default 0")
    private Boolean exclusive = false;

    /**
     * 当消费者客户端均断开连接，是否自动删除队列
     * 默认值false：不自动删除，推荐使用，避免消费者断开后，队列中丢弃消息
     */
    @Column(columnDefinition = "tinyint(1) default 0")
    private Boolean autoDelete = false;

    /**
     * 需要绑定的死信队列的交换机名称
     */
    private String deadExchangeName;

    /**
     * 需要绑定的死信队列的路由key的名称
     */
    private String deadRoutingKey;

    /**
     * 队列的额外参数
     */
    @Transient
    private Map<String, Object> arguments;

    /**
     * 队列信息
     */
    @Transient
    private QueueInformation queueInformation;

    /**
     * 日志创建时间
     */
    @CreatedDate
    private Long createTime;
    /**
     * 更新时间
     */
    @LastModifiedDate
    private Long updateTime;
}
