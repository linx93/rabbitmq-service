package net.phadata.rabbitmq.mapper;

import net.phadata.rabbitmq.model.RabbitQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author felix
 */
@Repository
public interface RabbitQueueMapper extends JpaRepository<RabbitQueue,Integer> {
}
