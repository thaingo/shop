package io.github.tkaczenko.data.loader;

import io.github.tkaczenko.data.model.Product;
import io.github.tkaczenko.data.model.category.Category;
import io.github.tkaczenko.data.provider.Scraper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by tkaczenko on 02.07.17.
 */
public class BaseLoader {
    public void load() {
        Scraper scraper = new Scraper(2, 15, 15);
        try {
            List<Category> categories = scraper.parseCategories();
            categories.forEach(
                    category -> category.getSubCategories()
                            .forEach(category1 -> System.out.println(category1.getName()))
            );
            Map<String, Product> productMap = scraper.parseProductList(categories);
            productMap.keySet()
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
