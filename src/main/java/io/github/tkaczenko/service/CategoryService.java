package io.github.tkaczenko.service;

import com.google.common.collect.Sets;
import io.github.tkaczenko.model.category.Category;
import io.github.tkaczenko.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by tkaczenko on 12.03.17.
 */
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category save(Category entity) {
        return categoryRepository.save(entity);
    }

    public List<Category> save(Collection<Category> entities) {
        List<Category> categories = new ArrayList<>();
        categoryRepository.save(entities).forEach(categories::add);
        return categories;
    }

    public Category findById(Integer id) {
        return categoryRepository.findOne(id);
    }

    public Set<Category> findAllByParent(Category category) {
        return categoryRepository.findAllByParentCategory(category);
    }

    public Set<Category> findAll() {
        return Sets.newHashSet(categoryRepository.findAll());
    }

    public List<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    public void delete(Integer categoryId) {
        categoryRepository.delete(categoryId);
    }
}
