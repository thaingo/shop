package io.github.tkaczenko.repository;

import io.github.tkaczenko.model.Product;
import io.github.tkaczenko.model.category.CategoryAssociation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Created by tkaczenko on 11.03.17.
 */
@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, String> {
    Product findByName(String name);

    Page<Product> findAllByCategoriesIn(Collection<CategoryAssociation> categoryAssociation, Pageable pageable);
}
