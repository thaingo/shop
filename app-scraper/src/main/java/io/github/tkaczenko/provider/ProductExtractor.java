package io.github.tkaczenko.provider;

import io.github.tkaczenko.data.model.Product;
import io.github.tkaczenko.data.model.Visualization;
import io.github.tkaczenko.data.model.attribute.Attribute;

import java.util.List;

public interface ProductExtractor {
    Product getProduct(Product product) throws Exception;

    List<Attribute> parseAttributes(Product product) throws Exception;

    List<Visualization> parseVisualizations(Product product) throws Exception;
}
