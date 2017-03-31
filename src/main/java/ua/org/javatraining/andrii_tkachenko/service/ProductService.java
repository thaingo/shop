package ua.org.javatraining.andrii_tkachenko.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.org.javatraining.andrii_tkachenko.data.model.Product;
import ua.org.javatraining.andrii_tkachenko.data.model.category.CategoryAssociation;
import ua.org.javatraining.andrii_tkachenko.data.repository.CategoryRepository;
import ua.org.javatraining.andrii_tkachenko.data.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tkaczenko on 12.03.17.
 */
@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> findAllByCategoryId(int id) {
        return categoryRepository.findOne(id).getProducts().stream()
                .map(CategoryAssociation::getProduct)
                .collect(Collectors.toList());
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product findById(String sku) {
        Product product = productRepository.findOne(sku);
        Hibernate.initialize(product.getCategories());
        Hibernate.initialize(product.getAttributes());
        Hibernate.initialize(product.getVisualizations());
        return product;
    }

    public void delete(String id) {
        productRepository.delete(id);
    }
}
