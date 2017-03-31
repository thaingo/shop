package ua.org.javatraining.andrii_tkachenko.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.org.javatraining.andrii_tkachenko.data.model.CustomOrder;

/**
 * Created by tkaczenko on 11.03.17.
 */
@Repository
public interface CustomOrderRepository extends CrudRepository<CustomOrder, Integer> {
}
