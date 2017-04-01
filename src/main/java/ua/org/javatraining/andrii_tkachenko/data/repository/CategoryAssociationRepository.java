package ua.org.javatraining.andrii_tkachenko.data.repository;

import org.springframework.data.repository.CrudRepository;
import ua.org.javatraining.andrii_tkachenko.data.model.category.Category;
import ua.org.javatraining.andrii_tkachenko.data.model.category.CategoryAssociation;

/**
 * Created by tkaczenko on 31.03.17.
 */
public interface CategoryAssociationRepository extends CrudRepository<CategoryAssociation, Integer> {
    Long countByCategory(Category category);
}
