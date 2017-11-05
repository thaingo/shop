package io.github.tkaczenko.repository;

import io.github.tkaczenko.model.category.Category;
import io.github.tkaczenko.model.category.CategoryAssociation;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * Created by tkaczenko on 31.03.17.
 */
public interface CategoryAssociationRepository extends CrudRepository<CategoryAssociation, Integer> {
    Long countByCategory(Category category);

    Set<CategoryAssociation> findAllByCategoryId(int id);

    Set<CategoryAssociation> findAllByProductSku(String sku);
}
