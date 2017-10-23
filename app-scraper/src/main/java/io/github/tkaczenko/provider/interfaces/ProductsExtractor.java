package io.github.tkaczenko.provider.interfaces;

import io.github.tkaczenko.model.Product;
import io.github.tkaczenko.model.category.Category;
import org.jsoup.select.Elements;

import java.util.Set;

public interface ProductsExtractor {
    Set<Category> parseCategories() throws Exception;

    Set<Category> processSubCategories(Elements subCategoryItems, Category category);

    Set<Product> parseProductList() throws Exception;
}
