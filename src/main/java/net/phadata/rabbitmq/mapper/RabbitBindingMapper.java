package net.phadata.rabbitmq.mapper;

import net.phadata.rabbitmq.model.RabbitBinding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author felix
 */
@Repository
public interface RabbitBindingMapper extends JpaRepository<RabbitBinding, Integer> {
}
