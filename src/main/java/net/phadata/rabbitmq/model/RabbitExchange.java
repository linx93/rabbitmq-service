package net.phadata.rabbitmq.model;

import net.phadata.rabbitmq.common.ExchangeTypeEnum;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * 交换机的详细配置
 *
 * @author felix
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class RabbitExchange {

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 交换机名称
     */
    @NotBlank(message = "不能为空")
    private String exchangeName;

    /**
     * 交互机类型。
     * 默认：直连型号
     */
    @Column(length = 10)
    private String exchangeType = ExchangeTypeEnum.DIRECT.name();

    /**
     * 是否持久化
     * 默认true：当RabbitMq重启时，消息不丢失
     */
    @Column(columnDefinition = "tinyint(1) default 1")
    private Boolean durable = true;

    /**
     * 当所有绑定队列都不在使用时，是否自动 删除交换器
     * 默认值false：不自动删除，推荐使用。
     */
    @Column(columnDefinition = "tinyint(1) default 0")
    private Boolean autoDelete = false;

    /**
     * 判断是否是延迟交互机
     */
    @Column(name = "`delayed`",columnDefinition = "tinyint(1) default 0")
    private Boolean delayed=false;

    /**
     * 交互机的额外参数
     */
    @Transient
    private Map<String, Object> arguments;


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
