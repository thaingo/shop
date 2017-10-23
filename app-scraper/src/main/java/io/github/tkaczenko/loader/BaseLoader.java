package io.github.tkaczenko.loader;

import io.github.tkaczenko.provider.BaseScraper;
import io.github.tkaczenko.repository.AttributeRepository;
import io.github.tkaczenko.repository.CategoryRepository;
import io.github.tkaczenko.repository.ProductRepository;
import io.github.tkaczenko.repository.VisualizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseLoader {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final AttributeRepository attributeRepository;
    private final VisualizationRepository visualizationRepository;

    @Autowired
    public BaseLoader(CategoryRepository categoryRepository, ProductRepository productRepository,
                      AttributeRepository attributeRepository, VisualizationRepository visualizationRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.attributeRepository = attributeRepository;
        this.visualizationRepository = visualizationRepository;
    }

    public void save(BaseScraper baseScraper) {
        baseScraper.getCategories().parallelStream().forEach(categoryRepository::save);
        baseScraper.getAttributes().parallelStream().forEach(attributeRepository::save);
        baseScraper.getProducts().parallelStream().forEach(productRepository::save);
        baseScraper.getVisualizations().parallelStream().forEach(visualizationRepository::save);
    }
}
