package io.github.tkaczenko.data.provider;

import com.google.common.collect.Sets;
import io.github.tkaczenko.data.model.Product;
import io.github.tkaczenko.data.model.category.Category;
import io.github.tkaczenko.data.model.category.CategoryAssociation;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by tkaczenko on 02.07.17.
 */
public class Scraper {
    private static final String URL = "https://www.prodj.com.ua/";

    private int numOfRootCategories;
    private int numOfProducts;
    private int numOfOnePageProducts;

    public Scraper(int numOfRootCategories, int numOfProducts, int numOfOnePageProducts) {
        this.numOfRootCategories = numOfRootCategories;
        this.numOfProducts = numOfProducts;
        this.numOfOnePageProducts = numOfOnePageProducts;
    }

    public List<Category> parseCategories() throws IOException {
        Document page = Jsoup.connect(URL).get();
        List<Category> categories = new ArrayList<>();
        Elements categoryItems = page.select(".menu").first().select("div");
        for (int i = 0; i < numOfOnePageProducts; i++) {
            Element categoryItem = categoryItems.get(i);
            Element link = categoryItem.select("a").first();
            String title = link.text().trim();
            String url = link.absUrl("href");

            Category category = new Category(title, null);
            category.setUrl(url);
            Elements subCategoryItems = categoryItem.select(".sub").first().select("a");
            category.setSubCategories(processSubCategories(subCategoryItems, category));
            categories.add(category);
        }
        return categories;
    }

    public Map<String, Product> parseProductList(List<Category> categories) throws IOException {
        Map<String, Product> productMap = new HashMap<>();
        categories.parallelStream()
                .forEach(category -> {
                    category.getSubCategories().parallelStream()
                            .forEach(subCategory -> {
                                try {
                                    Document page = Jsoup.connect(subCategory.getUrl()).get();
                                    Elements paginationElems = page.select(".nav-pages").first().select("a");
                                    int size = numOfProducts / numOfOnePageProducts;
                                    size = size > paginationElems.size() ? paginationElems.size() : size;
                                    for (int i = 0; i < size; i++) {
                                        Element paginationElem = paginationElems.get(i);
                                        if (page == null) {
                                            page = Jsoup.connect(paginationElem.absUrl("href")).get();
                                        }
                                        Elements productItems = page.select(".content").first().select(".block-tov");
                                        productItems.removeIf(productItem -> productItem.select(".bt-price").text().isEmpty());
                                        productMap.putAll(processProducts(category, subCategory, productItems));
                                        page = null;
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                });
        return productMap;
    }

    private Set<Category> processSubCategories(Elements subCategoryItems, Category category) {
        return subCategoryItems.parallelStream()
                .map(subCategoryItem -> {
                    String subTitle = subCategoryItem.text().trim();
                    String subUrl = subCategoryItem.absUrl("href");
                    Category temp = new Category(subTitle, subUrl);
                    temp.setParentCategory(category);
                    return temp;
                })
                .collect(Collectors.toSet());
    }

    private Map<String, Product> processProducts(Category category, Category subCategory, Elements productItems) {
        return productItems.parallelStream()
                .map(productItem -> {
                    Element a = productItem.select("a").first();
                    String title = a.select("strong").first().text()
                            .replace(category.getName(), "")
                            .trim();
                    String url = a.absUrl("href");
                    String price = productItem.select(".bt-price").text()
                            .replace("грн", "")
                            .trim();

                    Product product = new Product();
                    product.setName(title);
                    product.setUrl(url);
                    product.setPrice(BigDecimal.valueOf(Integer.parseInt(price)));
                    product.setCategories(Sets.newHashSet(new CategoryAssociation(product, subCategory)));

                    return product;
                })
                .collect(Collectors.toMap(Product::getName, Function.identity()));
    }

    public int getNumOfRootCategories() {
        return numOfRootCategories;
    }

    public void setNumOfRootCategories(int numOfRootCategories) {
        this.numOfRootCategories = numOfRootCategories;
    }

    public void setNumOfProducts(int numOfProducts) {
        this.numOfProducts = numOfProducts;
    }

    public int getNumOfProducts() {
        return numOfProducts;
    }
}
