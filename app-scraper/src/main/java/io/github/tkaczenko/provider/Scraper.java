package io.github.tkaczenko.provider;

import io.github.tkaczenko.model.Product;
import io.github.tkaczenko.model.Visualization;
import io.github.tkaczenko.model.attribute.Attribute;
import io.github.tkaczenko.model.category.Category;
import io.github.tkaczenko.provider.interfaces.ProductExtractor;
import io.github.tkaczenko.provider.interfaces.ProductsExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public abstract class Scraper implements ProductsExtractor {
    protected final Logger logger = LoggerFactory.getLogger(getScraperClass());
    protected final String URL = initUrl();
    protected final String CODE = setCode();

    protected ProductExtractor productExtractor;
    protected Set<Category> categories;
    protected Set<Product> products;
    protected Set<Attribute> attributes = new HashSet<>();
    protected Set<Visualization> visualizations = new HashSet<>();

    protected abstract String initUrl();

    protected abstract String setCode();

    protected abstract Class getScraperClass();

    public Set<Category> getCategories() {
        return categories;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public Set<Visualization> getVisualizations() {
        return visualizations;
    }

}
