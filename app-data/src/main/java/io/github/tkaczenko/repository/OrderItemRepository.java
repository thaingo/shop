package io.github.tkaczenko.repository;

import io.github.tkaczenko.model.OrderItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tkaczenko on 11.03.17.
 */
@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem, Integer> {
}
