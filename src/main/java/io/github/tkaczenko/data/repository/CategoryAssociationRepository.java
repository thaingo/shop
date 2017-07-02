package io.github.tkaczenko.data.repository;

import io.github.tkaczenko.data.model.category.Category;
import io.github.tkaczenko.data.model.category.CategoryAssociation;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * Created by tkaczenko on 31.03.17.
 */
public interface CategoryAssociationRepository extends CrudRepository<CategoryAssociation, Integer> {
    Long countByCategory(Category category);

    Set<CategoryAssociation> findAllByProductSku(String sku);
}
