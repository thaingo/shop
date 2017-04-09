package ua.org.javatraining.andrii_tkachenko.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.javatraining.andrii_tkachenko.data.model.Product;
import ua.org.javatraining.andrii_tkachenko.data.model.category.Category;
import ua.org.javatraining.andrii_tkachenko.data.model.category.CategoryAssociation;
import ua.org.javatraining.andrii_tkachenko.data.repository.CategoryAssociationRepository;
import ua.org.javatraining.andrii_tkachenko.data.repository.CategoryRepository;
import ua.org.javatraining.andrii_tkachenko.data.repository.ProductRepository;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by tkaczenko on 12.03.17.
 */
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryAssociationRepository categoryAssociationRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository,
                          CategoryAssociationRepository categoryAssociationRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.categoryAssociationRepository = categoryAssociationRepository;
    }

    public long countByCategory(Category category) {
        return categoryAssociationRepository.countByCategory(category);
    }

    public Set<Product> findAllByCategoryName(String name) {
        return categoryRepository.findByName(name).parallelStream()
                .flatMap(category -> category.getProducts().parallelStream())
                .map(CategoryAssociation::getProduct)
                .collect(Collectors.toSet());
    }

    public Set<Product> findAllByCategoryId(int id) {
        return categoryRepository.findOne(id).getProducts().parallelStream()
                .map(CategoryAssociation::getProduct)
                .collect(Collectors.toSet());
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product findByName(String name) {
        return productRepository.findByName(name);
    }

    public Product findById(String sku) {
        return productRepository.findOne(sku);
    }

    public void delete(String id) {
        productRepository.delete(id);
    }
}
