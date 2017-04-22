package ua.org.javatraining.andrii_tkachenko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.javatraining.andrii_tkachenko.data.model.category.Category;
import ua.org.javatraining.andrii_tkachenko.data.repository.CategoryRepository;

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

    public Category findById(Integer id) {
        return categoryRepository.findOne(id);
    }

    public Set<Category> findAllByParent(Category category) {
        return categoryRepository.findAllByParentCategory(category);
    }

    public List<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    public void delete(Integer categoryId) {
        categoryRepository.delete(categoryId);
    }
}
