package io.github.tkaczenko.data.repository;

import io.github.tkaczenko.data.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by tkaczenko on 11.03.17.
 */
@Repository
public interface CustomerRepository extends CrudRepository<Customer, String> {
}
