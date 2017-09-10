package io.github.tkaczenko.provider;

import io.github.tkaczenko.data.model.Product;
import io.github.tkaczenko.data.model.Visualization;
import io.github.tkaczenko.data.model.attribute.Attribute;
import io.github.tkaczenko.data.model.category.Category;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.stream.Collectors;

public abstract class BaseScraper extends Scraper {
    protected final Logger logger = LoggerFactory.getLogger(getScraperClass());
    protected final String URL = initUrl();
    protected final String CODE = setCode();

    private static final String pathToDriver =
            "/home/tkaczenko/Desktop/chromedriver";
    protected static final int DEFAULT_PRODUCT_AMOUNT = 10;

    protected static WebDriver driver;

    static {
        System.setProperty("webdriver.chrome.driver", pathToDriver);

        driver = new ChromeDriver();
        ((RemoteWebDriver) driver).setLogLevel(Level.SEVERE);
    }

    protected int numOfRootCategories;
    protected int numOfSubCategories;
    protected int numOfProducts;
    protected int countOfProducts;
    protected int numOfOnePageProducts;

    private ProductExtractor productExtractor;
    private List<Category> categories;
    private List<Product> products;
    private List<Attribute> attributes = new ArrayList<>();
    private List<Visualization> visualizations = new ArrayList<>();

    public BaseScraper(int numOfRootCategories, int numOfSubCategories, int numOfProducts, int numOfOnePageProducts,
                       ProductExtractor productExtractor) {
        this.numOfRootCategories = numOfRootCategories;
        this.numOfSubCategories = numOfSubCategories;
        this.numOfProducts = numOfProducts;
        this.numOfOnePageProducts = numOfOnePageProducts;
        this.productExtractor = productExtractor;
    }

    public BaseScraper(ProductExtractor productExtractor) {
        this.numOfRootCategories = Integer.MAX_VALUE;
        this.numOfSubCategories = Integer.MAX_VALUE;
        this.numOfProducts = Integer.MAX_VALUE;
        this.numOfOnePageProducts = Integer.MAX_VALUE;
        this.productExtractor = productExtractor;
    }

    @Override
    protected List<Category> parseCategories() throws Exception {
        Document page = Jsoup.connect(URL).get();
        List<Category> categories = new ArrayList<>();
        Elements categoryItems = getCategoryItems(page);
        for (int i = 0; i < numOfRootCategories; i++) {
            Category category = extractCategory(categoryItems, i);
            categories.add(category);
        }
        return categories;
    }

    @Override
    protected Map<String, Product> parseProductList(List<Category> categories) throws Exception {
        Map<String, Product> productMap = new HashMap<>();
        categories.parallelStream()
                .forEach(category -> category.getSubCategories().parallelStream()
                        .forEach(subCategory -> {
                            try {
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
                                    productMap.putAll(processProducts(category, subCategory, productItems));
                                    page = null;
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }));
        return productMap;
    }

    public void load() {
        try {
            logger.info("Start loading categories");
            categories = this.parseCategories();
            logger.info("Start loading product lists");
            Map<String, Product> productMap = this.parseProductList(categories);
            products = productMap.values().parallelStream()
                    .map(product -> {
                        try {
                            logger.info("Load product details at {}", product.getUrl());
                            return productExtractor.getProduct(product);
                        } catch (Exception e) {
                            logger.info("Cannot load product details at {}", product.getUrl());
                            return null;
                        }
                    })
                    .collect(Collectors.toList());
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

    protected abstract Elements getCategoryItems(Document page);

    protected abstract Category extractCategory(Elements categoryItems, int i);

    protected abstract Elements extractProductItems(Document page);

    protected abstract Product extractProductBasicInfo(Category category, Category subCategory, Element productItem);

    protected abstract List<String> getNextPageUrls(Document page);

    private int getSize(List<String> pageLinks) {
        int size = numOfProducts / numOfOnePageProducts;
        if (size == 0 && countOfProducts < numOfProducts)
            size = 1;
        return size > pageLinks.size() ? pageLinks.size() : size;
    }

    private Map<String, Product> processProducts(Category category, Category subCategory, Elements productItems) {
        return productItems.parallelStream().limit(numOfProducts)
                .map(productItem -> extractProductBasicInfo(category, subCategory, productItem))
                .collect(Collectors.toMap(Product::getSku, Function.identity()));
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

    public List<Category> getCategories() {
        return categories;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public List<Visualization> getVisualizations() {
        return visualizations;
    }
}
