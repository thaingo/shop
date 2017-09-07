package io.github.tkaczenko.provider;

import io.github.tkaczenko.data.model.Product;
import io.github.tkaczenko.data.model.Visualization;
import io.github.tkaczenko.data.model.attribute.Attribute;
import io.github.tkaczenko.data.model.category.Category;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;

public abstract class Scraper {
    protected final Logger logger = LoggerFactory.getLogger(getScraperClass());
    protected final String URL = initUrl();
    protected final String CODE = setCode();

    private static final String pathToDriver =
            "/home/tkaczenko/Desktop/chromedriver";

    protected static WebDriver driver;

    static {
        System.setProperty("webdriver.chrome.driver", pathToDriver);

        driver = new ChromeDriver();
        ((RemoteWebDriver) driver).setLogLevel(Level.SEVERE);
    }

    protected int numOfRootCategories;
    protected int numOfProducts;
    protected int numOfOnePageProducts;

    private List<Category> categories;
    private List<Product> products;
    private List<Attribute> attributes = new ArrayList<>();
    private List<Visualization> visualizations = new ArrayList<>();

    public Scraper(int numOfRootCategories, int numOfProducts, int numOfOnePageProducts) {
        this.numOfRootCategories = numOfRootCategories;
        this.numOfProducts = numOfProducts;
        this.numOfOnePageProducts = numOfOnePageProducts;
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
                            logger.info("Load product details at {0}", new Object[]{product.getUrl()});
                            return getProduct(product);
                        } catch (Exception e) {
                            logger.info("Cannot load product details at {0}", new Object[]{product.getUrl()});
                            return null;
                        }
                    })
                    .collect(Collectors.toList());
            products.forEach(product -> {
                try {
                    attributes.addAll(parseAttributes(product));
                } catch (Exception e) {
                    logger.info("Cannot load attributes of product {0}", new Object[]{product.getSku()});
                }
            });
            products.forEach(product -> {
                try {
                    visualizations.addAll(parseVisualizations(product));
                } catch (Exception e) {
                    logger.info("Cannot load visualitations of product {0}", new Object[]{product.getSku()});
                }
            });
        } catch (Exception e) {
            logger.info("Cannot scrap informations with this scraper {0}", new Object[]{this.getScraperClass()});
            logger.info(e.getMessage(), e);
        }
    }

    protected abstract List<Category> parseCategories() throws Exception;

    protected abstract Map<String, Product> parseProductList(List<Category> categories) throws Exception;

    protected abstract Product getProduct(Product product) throws Exception;

    protected abstract List<Attribute> parseAttributes(Product product) throws Exception;

    protected abstract List<Visualization> parseVisualizations(Product product) throws Exception;

    protected abstract String initUrl();

    protected abstract String setCode();

    protected abstract Class getScraperClass();

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
