package ua.org.javatraining.andrii_tkachenko.scraper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import ua.org.javatraining.andrii_tkachenko.ShopApplication;
import ua.org.javatraining.andrii_tkachenko.data.model.Product;
import ua.org.javatraining.andrii_tkachenko.data.model.Visualization;
import ua.org.javatraining.andrii_tkachenko.data.model.attribute.Attribute;
import ua.org.javatraining.andrii_tkachenko.data.model.attribute.AttributeAssociation;
import ua.org.javatraining.andrii_tkachenko.data.model.category.Category;
import ua.org.javatraining.andrii_tkachenko.data.model.category.CategoryAssociation;
import ua.org.javatraining.andrii_tkachenko.data.repository.*;

import java.util.List;
import java.util.Set;

/**
 * Created by tkaczenko on 31.03.17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class ProdjScraperTest {
    private ProdjScraper scraper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private VisualizationRepository visualizationRepository;

    @Autowired
    private AttributeAssociationRepository attributeAssociationRepository;

    @Autowired
    private CategoryAssociationRepository categoryAssociationRepository;

    @Before
    public void setUp() {
        scraper = new ProdjScraper();
    }

    @Test
    public void process() throws Exception {
        scraper.setNumOfCategories(1);
        long start = System.currentTimeMillis();
        scraper.process();
        long delta = System.currentTimeMillis() - start;
        System.out.println("Scraping: " + delta + " ms");

        start = System.currentTimeMillis();

        List<Category> categories = scraper.getCategories();
        Set<Attribute> attributes = scraper.getAttributes();
        List<Visualization> visualizations = scraper.getVisualizations();
        List<Product> products = scraper.getProducts();
        List<AttributeAssociation> attributeAssociations = scraper.getAttributeAssociations();
        List<CategoryAssociation> categoryAssociations = scraper.getCategoryAssociations();

        categoryRepository.save(categories);
        attributeRepository.save(attributes);
        productRepository.save(products);
        visualizationRepository.save(visualizations);
        attributeAssociationRepository.save(attributeAssociations);
        categoryAssociationRepository.save(categoryAssociations);

        delta = System.currentTimeMillis() - start;
        System.out.println("Saving: " + delta + " ms");
    }
}