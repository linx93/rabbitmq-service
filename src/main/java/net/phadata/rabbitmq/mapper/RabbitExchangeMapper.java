package net.phadata.rabbitmq.mapper;

import net.phadata.rabbitmq.model.RabbitExchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author felix
 */
@Repository
public interface RabbitExchangeMapper extends JpaRepository<RabbitExchange,Integer> {
}
