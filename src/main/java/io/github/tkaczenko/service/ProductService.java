package io.github.tkaczenko.service;

import io.github.tkaczenko.data.model.Product;
import io.github.tkaczenko.data.model.category.Category;
import io.github.tkaczenko.data.model.category.CategoryAssociation;
import io.github.tkaczenko.data.repository.*;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by tkaczenko on 12.03.17.
 */
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final VisualizationRepository visualizationRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryAssociationRepository categoryAssociationRepository;
    private final AttributeRepository attributeRepository;
    private final AttributeAssociationRepository attributeAssociationRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ProductService(ProductRepository productRepository, VisualizationRepository visualizationRepository,
                          CategoryRepository categoryRepository,
                          CategoryAssociationRepository categoryAssociationRepository,
                          AttributeRepository attributeRepository,
                          AttributeAssociationRepository attributeAssociationRepository) {
        this.productRepository = productRepository;
        this.visualizationRepository = visualizationRepository;
        this.categoryRepository = categoryRepository;
        this.categoryAssociationRepository = categoryAssociationRepository;
        this.attributeRepository = attributeRepository;
        this.attributeAssociationRepository = attributeAssociationRepository;
    }

    public List<Product> search(String text) {
        // get the full text entity manager
        FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search
                .getFullTextEntityManager(entityManager);
        // create the query using Hibernate Search query
        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Product.class)
                .get();
        // a very basic query by keywords
        Query query = queryBuilder.keyword().onFields("name", "description").matching(text)
                .createQuery();
        // wrap Lucene query in an Hibernate Query object
        FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, Product.class);
        // execute search and return results (sorted by relevance as default)
        @SuppressWarnings("unchecked")
        List<Product> results = jpaQuery.getResultList();
        return results;
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

    public void save(Iterable<Product> products) {
        productRepository.save(products);
    }

    public Product findByName(String name) {
        return productRepository.findByName(name);
    }

    public Product findById(String sku) {
        return productRepository.findOne(sku);
    }

    public Product findByIdWithVisualization(String sku, int type) {
        Product product = productRepository.findOne(sku);
        product.setVisualizations(visualizationRepository.findAllByProductSkuAndType(sku, type));
        return productRepository.findOne(sku);
    }

    public Product findFullById(String sku) {
        Product product = productRepository.findOne(sku);
        product.setCategories(categoryAssociationRepository.findAllByProductSku(sku));
        product.setAttributes(attributeAssociationRepository.findAllByProductSku(sku));
        product.setVisualizations(visualizationRepository.findAllByProductSku(sku));
        return product;
    }

    public void delete(String id) {
        productRepository.delete(id);
    }
}
