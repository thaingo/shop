package io.github.tkaczenko.loader;

import io.github.tkaczenko.data.repository.AttributeRepository;
import io.github.tkaczenko.data.repository.CategoryRepository;
import io.github.tkaczenko.data.repository.ProductRepository;
import io.github.tkaczenko.data.repository.VisualizationRepository;
import io.github.tkaczenko.provider.Scraper;
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

    public void save(Scraper scraper) {
        scraper.getCategories().parallelStream().forEach(categoryRepository::save);
        scraper.getVisualizations().parallelStream().forEach(visualizationRepository::save);
        scraper.getAttributes().parallelStream().forEach(attributeRepository::save);
        scraper.getProducts().parallelStream().forEach(productRepository::save);
    }
}
