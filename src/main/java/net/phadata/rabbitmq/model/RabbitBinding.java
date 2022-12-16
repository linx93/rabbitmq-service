package net.phadata.rabbitmq.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;

/**
 * RabbitMq的队列，交换机，绑定关系的对象
 *
 * @author felix
 */
@Data
@Entity
public class RabbitBinding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 路由主键
     */
    private String routingKey;

    /**
     * 队列信息
     */
    private Integer rabbitQueue;

    /**
     * 交换机信息
     */
    private Integer rabbitExchange;

    @Transient
    private String exchangeName;

    @Transient
    private String queueName;




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
