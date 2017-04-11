package ua.org.javatraining.andrii_tkachenko.service;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.org.javatraining.andrii_tkachenko.data.model.Product;
import ua.org.javatraining.andrii_tkachenko.data.model.category.Category;
import ua.org.javatraining.andrii_tkachenko.data.model.category.CategoryAssociation;
import ua.org.javatraining.andrii_tkachenko.data.repository.CategoryAssociationRepository;
import ua.org.javatraining.andrii_tkachenko.data.repository.CategoryRepository;
import ua.org.javatraining.andrii_tkachenko.data.repository.ProductRepository;
import ua.org.javatraining.andrii_tkachenko.data.repository.VisualizationRepository;

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

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ProductService(ProductRepository productRepository, VisualizationRepository visualizationRepository,
                          CategoryRepository categoryRepository,
                          CategoryAssociationRepository categoryAssociationRepository) {
        this.productRepository = productRepository;
        this.visualizationRepository = visualizationRepository;
        this.categoryRepository = categoryRepository;
        this.categoryAssociationRepository = categoryAssociationRepository;
    }

    public List<Product> search(String text) {
        // get the full text entity manager
        FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search
                .getFullTextEntityManager(entityManager);

        // create the query using Hibernate Search query
        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Product.class)
                .get();

        // a very basic query by keywords
        org.apache.lucene.search.Query query = queryBuilder.keyword().onFields("name", "description").matching(text)
                .createQuery();

        // wrap Lucene query in an Hibernate Query object
        org.hibernate.search.jpa.FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, Product.class);

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

    public Product findByName(String name) {
        return productRepository.findByName(name);
    }

    public Product findById(String sku) {
        return productRepository.findOne(sku);
    }

    public Product findByIdWithVisualizations(String sku) {
        Product product = productRepository.findOne(sku);
        product.setVisualizations(visualizationRepository.findAllByProductSku(sku));
        return productRepository.findOne(sku);
    }

    public void delete(String id) {
        productRepository.delete(id);
    }
}
