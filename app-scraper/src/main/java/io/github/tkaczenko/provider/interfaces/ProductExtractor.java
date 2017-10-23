package io.github.tkaczenko.provider.interfaces;

import io.github.tkaczenko.model.Product;
import io.github.tkaczenko.model.Visualization;
import io.github.tkaczenko.model.attribute.Attribute;

import java.util.Set;

public interface ProductExtractor {
    Product getProduct(Product product) throws Exception;

    Set<Attribute> parseAttributes(Product product) throws Exception;

    Set<Visualization> parseVisualizations(Product product) throws Exception;
}
