package io.github.tkaczenko.repository;

import io.github.tkaczenko.model.category.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

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
