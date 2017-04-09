package ua.org.javatraining.andrii_tkachenko.scraper;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import ua.org.javatraining.andrii_tkachenko.data.model.Product;
import ua.org.javatraining.andrii_tkachenko.data.model.Visualization;
import ua.org.javatraining.andrii_tkachenko.data.model.attribute.Attribute;
import ua.org.javatraining.andrii_tkachenko.data.model.attribute.AttributeAssociation;
import ua.org.javatraining.andrii_tkachenko.data.model.category.Category;
import ua.org.javatraining.andrii_tkachenko.data.model.category.CategoryAssociation;
import ua.org.javatraining.andrii_tkachenko.data.model.enumeration.VisualizationType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for scraping data at prodj.com.ua
 */
//// TODO: 09.04.17 Fix category name without / %20
public class ProdjScraper {
    private static final String PRODJ =
            "https://www.prodj.com.ua/";

    private static final int NUM_OF_CATEGORIES = 5;

    private static WebDriver driver;

    static {
        System.setProperty("webdriver.chrome.driver", "/home/tkaczenko/Desktop/chromedriver");

        driver = new ChromeDriver();
        ((RemoteWebDriver) driver).setLogLevel(Level.SEVERE);
    }

    private Pattern p = Pattern.compile("\\s\\(");

    private int numOfCategories = NUM_OF_CATEGORIES;

    // Data
    private List<Category> categories = new ArrayList<>();
    private List<CategoryAssociation> categoryAssociations = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private List<AttributeAssociation> attributeAssociations = new ArrayList<>();
    private Set<Attribute> attributes = new HashSet<>();
    private List<Visualization> visualizations = new ArrayList<>();

    private List<String> urls = new ArrayList<>();

    public void process() {
        parseCategories();
        parseSubCategories();
        parseListPages();
        parseProductPage();
        driver.quit();
    }

    private void parseCategories() {
        System.out.println("Try to get main page");
        driver.get(PRODJ);

        System.out.println("Try to find needed elements");
        List<WebElement> namesOfCategories = driver.findElements(By.cssSelector(".menu > div > a"));
        for (WebElement element : namesOfCategories) {
            String text = element.getText();
            Matcher m = p.matcher(text);
            String name = correctTitle(m, text);

            Category category = new Category(name, "");
            String url = element.getAttribute("href");

            System.out.println(
                    category.getName() + " " +
                            url
            );

            urls.add(url);
            categories.add(category);
        }
        System.out.println("Parse categories successfully");
    }

    private void parseSubCategories() {
        System.out.println("Try to get subcategories");
        // Temp list for new urls
        List<String> temp = new ArrayList<>();
        int count = 0;
        int k = 0;
        for (String url : urls) {
            driver.get(url);
            List<WebElement> namesOfSubcategories = driver.findElements(By.cssSelector(".subcats a"));

            for (WebElement element : namesOfSubcategories) {
                if (k == numOfCategories) {
                    break;
                }
                String text = element.getText();
                Matcher m = p.matcher(text);
                String name = correctTitle(m, text);

                Category category = new Category(name, "");
                Category parentCategory = categories.get(count);
                category.setParentCategory(parentCategory);
                String link = element.getAttribute("href");

                System.out.println(parentCategory.getName() + " " + category.getName() + " " + link);

                temp.add(link);
                categories.add(category);
                k++;
            }
            count++;
        }
        urls.clear();
        urls = temp;
        System.out.println("Parse subcategories successfully");
    }

    private void parseListPages() {
        System.out.println("Try to parse list of products page");

        List<String> temp = new ArrayList<>();
        for (String url : urls) {
            driver.get(url);

            System.out.println("Try to find elements");
            WebElement category = driver.findElement(By.cssSelector(".block-tov .bt-text"));
            List<WebElement> links = driver.findElements(By.cssSelector(".block-tov a"));
            List<WebElement> titles = driver.findElements(By.cssSelector(".block-tov strong"));
            List<WebElement> prices = driver.findElements(By.cssSelector("span.tov-price.data-cart"));
            List<WebElement> images = driver.findElements(By.cssSelector(".block-tov .bt-img img"));

            /*
            We've got one element that isn't correct title name
            Also we use prices.size(), because we don't have a price for every product
             */
            System.out.println(prices.size());
            for (int i = 0; i < prices.size(); i++) {
                Product product = new Product();
                product.setSku(category.getText() + "_" + i);
                product.setName(titles.get(i).getText());
                product.setPrice(
                        Integer.parseInt(prices.get(i).getText()
                                .replaceAll("\\D", ""))
                );

                Category c = null;
                for (Category category1 : categories) {
                    if (category1.getName().equals(category.getText())) {
                        c = category1;
                        break;
                    }
                }

                CategoryAssociation categoryAssociation = new CategoryAssociation(product, c);

                Visualization original = new Visualization();
                original.setType(VisualizationType.ORIGINAL_PICTURE.getCode());
                original.setProduct(product);
                original.setUrl(customizeURL(images.get(i).getAttribute("data-original")));

                Visualization big = new Visualization();
                big.setType(VisualizationType.BIG_PICTURE.getCode());
                big.setProduct(product);
                big.setUrl(customizeURL(images.get(i).getAttribute("data-big")));

                String link = links.get(i).getAttribute("href");

                System.out.println(i + " " + product.getName() + " " + product.getPrice() + " " + link + " " +
                        original.getUrl() + " " + original.getType() + " " + big.getUrl() + " " + big.getType());

                temp.add(link);
                products.add(product);
                visualizations.add(original);
                visualizations.add(big);
                categoryAssociations.add(categoryAssociation);
            }
            /*  IF NEED TO PARSE ALL STORE
                if (!url.contains("?page=")) {
                List<WebElement> numberOfPages = driver.findElements(By.cssSelector(".nav-pages a"));
                for (WebElement page : numberOfPages) {
                    String u = page.getAttribute("href");
                    if (!u.contains("?page=1")) {
                        urls.add(u);
                        System.out.println("PAGE LINK: " + u);
                    }
                }
            }*/
        }
        urls.clear();
        urls = temp;
        System.out.println("Get basic info of products successfully");
    }

    private void parseProductPage() {
        System.out.println("Try to parse product page");
        boolean success = true;
        for (String url : urls) {
            driver.get(url);

            // Click on more button
            WebElement element = driver.findElement(By.cssSelector(".more span button.arr4-d"));
            if (element.isDisplayed()) {
                element.click();
            }

            System.out.println("Try to find elements");
            WebElement name = driver.findElement(By.cssSelector(".tov h1"));
            try {
                WebElement container = driver.findElement(By.cssSelector("div.tov-text1.more_open"));
                List<WebElement> descriptions = container.findElements(By.tagName("p"));
                List<WebElement> titles = container.findElements(By.xpath("//p/b/span/span"));
                List<WebElement> lists = container.findElements(By.tagName("ul"));
                List<WebElement> images = driver.findElements(By.xpath("//div[@id='gallery']/a/img"));

                System.out.println("NAME: " + name.getText());
                Product real = null;
                int count = 0;
                for (int j = 0; j < products.size(); j++) {
                    if (products.get(j).getName().toLowerCase().equals(name.getText().trim().toLowerCase())) {
                        real = products.get(j);
                        count = j;
                    }
                }

                System.out.println("Founded product: " + real + " " + real.getName());

                for (int i = 0; i < 3; i++) {
                    if (descriptions.size() < i) {
                        break;
                    }
                    String text = descriptions.get(i).getText();
                    if (text.length() > 0) {
                        real.setDescription(text.trim());
                    }

                    System.out.println("Product: " + real.getName() + " " + real.getDescription());
                }
                System.out.println("Size of founded titles: " + titles.size());
                for (int i = 0; i < titles.size(); i++) {
                    String title = titles.get(i).getText();

                    if (lists.size() <= i || title.contains("Гарантия")) {
                        break;
                    }

                    List<WebElement> properties = lists.get(i).findElements(By.tagName("li"));
                    for (int j = 0; j < properties.size(); j++) {
                        String value = properties.get(j).getText();
                        AttributeAssociation association = new AttributeAssociation();
                        association.setProduct(real);
                        Attribute attribute = new Attribute(title + "_" + j);
                        association.setAttribute(attribute);
                        association.setValue(value);

                        attributes.add(attribute);
                        attributeAssociations.add(association);
                        System.out.println("Attribute: " + association.getAttribute().getName() +
                                " " + association.getValue() + " " + association.getProduct().getName());
                    }
                }
                for (int i = 1; i < images.size(); i++) {
                    Visualization big = new Visualization();
                    big.setProduct(real);
                    big.setType(VisualizationType.BIG_PICTURE.getCode());
                    big.setUrl(customizeURL(images.get(i).getAttribute("data-big")));

                    Visualization src = new Visualization();
                    src.setProduct(real);
                    src.setType(VisualizationType.PICTURE.getCode());
                    src.setUrl(customizeURL(images.get(i).getAttribute("src")));

                    System.out.println("Product: " + big.getProduct().getName() + big.getType() +
                            big.getUrl() + " " +
                            src.getProduct().getName() +
                            src.getType() +
                            src.getUrl());
                    visualizations.add(big);
                    visualizations.add(src);
                    products.set(count, real);
                }
            } catch (NoSuchElementException e) {
                continue;
            }
        }
    }

    private static String customizeURL(String url) {
        return PRODJ + url.substring(1);
    }

    private static String correctTitle(Matcher m, String str) {
        return (m.find()) ? str.substring(0, m.start()) : str;
    }

    public int getNumOfCategories() {
        return numOfCategories;
    }

    public void setNumOfCategories(int numOfCategories) {
        this.numOfCategories = numOfCategories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public List<CategoryAssociation> getCategoryAssociations() {
        return categoryAssociations;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<AttributeAssociation> getAttributeAssociations() {
        return attributeAssociations;
    }

    public Set<Attribute> getAttributes() {
        return attributes;
    }

    public List<Visualization> getVisualizations() {
        return visualizations;
    }
}
