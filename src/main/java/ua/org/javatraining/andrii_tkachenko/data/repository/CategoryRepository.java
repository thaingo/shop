package ua.org.javatraining.andrii_tkachenko.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.org.javatraining.andrii_tkachenko.data.model.category.Category;

import java.util.List;
import java.util.Set;

/**
 * Created by tkaczenko on 11.03.17.
 */
@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {
    Set<Category> findAllByParentCategory(Category category);

    List<Category> findByName(String name);
}
