package io.github.tkaczenko.provider;

import io.github.tkaczenko.data.model.Product;
import io.github.tkaczenko.data.model.category.Category;

import java.util.List;
import java.util.Map;

public abstract class Scraper {
    protected abstract List<Category> parseCategories() throws Exception;

    protected abstract Map<String, Product> parseProductList(List<Category> categories) throws Exception;

    protected abstract String initUrl();

    protected abstract String setCode();

    protected abstract Class getScraperClass();
}
