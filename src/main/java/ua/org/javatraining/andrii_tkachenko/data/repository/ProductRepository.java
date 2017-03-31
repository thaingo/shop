package ua.org.javatraining.andrii_tkachenko.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.org.javatraining.andrii_tkachenko.data.model.Product;

/**
 * Created by tkaczenko on 11.03.17.
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, String> {
}
