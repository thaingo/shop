package io.github.tkaczenko.provider;

import io.github.tkaczenko.model.Product;
import io.github.tkaczenko.model.category.Category;
import io.github.tkaczenko.provider.interfaces.ProductExtractor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public abstract class BaseScraper extends Scraper {
    protected static final int DEFAULT_PRODUCT_AMOUNT = 10;

    protected int numOfRootCategories;
    protected int numOfSubCategories;
    protected int numOfProducts;
    protected int countOfProducts;
    protected int numOfOnePageProducts;

    private int newEntriesCount;
    private int goodEntriesCount;

    public BaseScraper(int numOfRootCategories, int numOfSubCategories, int numOfProducts, int numOfOnePageProducts,
                       ProductExtractor productExtractor) {
        this.numOfRootCategories = numOfRootCategories;
        this.numOfSubCategories = numOfSubCategories;
        this.numOfProducts = numOfProducts;
        this.numOfOnePageProducts = numOfOnePageProducts;
        this.productExtractor = productExtractor;
    }

    public BaseScraper(ProductExtractor productExtractor) {
        this.numOfRootCategories = 0;
        this.numOfSubCategories = 0;
        this.numOfProducts = Integer.MAX_VALUE;
        this.numOfOnePageProducts = 0;
        this.productExtractor = productExtractor;
    }

    public void load() {
        try {
            logger.info("Start loading categories");
            categories = this.parseCategories();
            logger.info("Start loading product lists");
            Set<Product> productSet = this.parseProductList();
            newEntriesCount = productSet.size();
            logger.info("Processed products " + newEntriesCount);
            products = productSet.parallelStream()
                    .map(product -> {
                        try {
                            logger.info("Load product details at {}", product.getUrl());
                            return productExtractor.getProduct(product);
                        } catch (Exception e) {
                            logger.info("Cannot load product details at {}", product.getUrl());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            goodEntriesCount = products.size();
            logger.info("Finished products " + goodEntriesCount);
            products.forEach(product -> {
                try {
                    attributes.addAll(productExtractor.parseAttributes(product));
                } catch (Exception e) {
                    logger.info("Cannot load attributes of product {}", product.getSku());
                }
            });
            products.forEach(product -> {
                try {
                    visualizations.addAll(productExtractor.parseVisualizations(product));
                } catch (Exception e) {
                    logger.info("Cannot load visualisations of product {}", product.getSku());
                }
            });
        } catch (Exception e) {
            logger.info("Cannot scrap information with this scraper {}", this.getScraperClass());
            logger.info(e.getMessage(), e);
        }
    }

    @Override
    public Set<Category> parseCategories() throws Exception {
        Document page = Jsoup.connect(URL).get();
        Set<Category> categories = new HashSet<>();
        Elements categoryItems = getCategoryItems(page);
        if (numOfRootCategories == 0) {
            numOfRootCategories = categoryItems.size();
        }
        for (int i = 0; i < numOfRootCategories; i++) {
            if (i >= categoryItems.size()) {
                i = categoryItems.size() - 1;
            }
            Category category = extractCategory(categoryItems, i);
            categories.add(category);
        }
        return categories;
    }

    @Override
    public Set<Product> parseProductList() throws Exception {
        Set<Product> products = new HashSet<>();
        categories.parallelStream()
                .forEach(category -> category.getSubCategories().parallelStream()
                        .forEach(subCategory -> {
                            try {
                                if (products.size() >= numOfProducts) {
                                    return;
                                }
                                String url = subCategory.getUrl();
                                Document page = Jsoup.connect(url).get();
                                List<String> pageLinks = getNextPageUrls(page);
                                if (pageLinks == null) {
                                    pageLinks = new ArrayList<>();
                                    pageLinks.add(url);
                                }
                                int size = getSize(pageLinks);
                                for (int i = 0; i < size; i++) {
                                    if (page == null) {
                                        page = Jsoup.connect(pageLinks.get(i)).get();
                                    }
                                    Elements productItems = extractProductItems(page);
                                    if (products.size() >= numOfProducts) {
                                        break;
                                    }
                                    products.addAll(processProducts(category, subCategory, productItems));
                                    page = null;
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }));
        return products;
    }

    protected abstract Elements getCategoryItems(Document page);

    protected abstract Category extractCategory(Elements categoryItems, int i);

    protected abstract Elements extractProductItems(Document page);

    protected abstract Product extractProductBasicInfo(Category category, Category subCategory, Element productItem);

    protected abstract List<String> getNextPageUrls(Document page);

    protected void validateNumOfSubCategories(Elements subCategoryItems) {
        if (numOfSubCategories == 0) {
            numOfSubCategories = subCategoryItems.size();
        }
    }

    private int getSize(List<String> pageLinks) {
        int size = numOfOnePageProducts == 0 ? pageLinks.size() : numOfProducts / numOfOnePageProducts;
        if (size == 0 && countOfProducts < numOfProducts)
            size = 1;
        return size > pageLinks.size() || size < 0 ? pageLinks.size() : size;
    }

    private Set<Product> processProducts(Category category, Category subCategory, Elements productItems) {
        return productItems.parallelStream()
                .map(productItem -> extractProductBasicInfo(category, subCategory, productItem))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
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
