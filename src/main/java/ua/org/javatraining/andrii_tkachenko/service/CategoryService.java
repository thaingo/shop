package ua.org.javatraining.andrii_tkachenko.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.org.javatraining.andrii_tkachenko.data.model.category.Category;
import ua.org.javatraining.andrii_tkachenko.data.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

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

    @Transactional
    public List<Category> findAllParentCategoryWithSubcategories() {
        List<Category> categories = findAllParentCategory();
        categories.forEach(Category::getSubCategories);
        return categories;
    }

    public List<Category> findAllParentCategory() {
        List<Category> categories = new ArrayList<>();
        categories.addAll(categoryRepository.findAllByParentCategory(null));
        return categories;
    }

    public List<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    public void delete(Integer categoryId) {
        categoryRepository.delete(categoryId);
    }
}
