package io.github.tkaczenko.provider_impl;

import com.google.common.collect.Sets;
import io.github.tkaczenko.data.model.Product;
import io.github.tkaczenko.data.model.Visualization;
import io.github.tkaczenko.data.model.attribute.Attribute;
import io.github.tkaczenko.data.model.attribute.AttributeAssociation;
import io.github.tkaczenko.data.model.category.Category;
import io.github.tkaczenko.data.model.category.CategoryAssociation;
import io.github.tkaczenko.data.model.enumeration.VisualizationType;
import io.github.tkaczenko.provider.Scraper;
import io.github.tkaczenko.provider.SiteCode;
import io.github.tkaczenko.util.ProductUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by tkaczenko on 02.07.17.
 */
public class ProdjScraper extends Scraper {
    private WebElement container;

    public ProdjScraper(int numOfRootCategories, int numOfProducts, int numOfOnePageProducts) {
        super(numOfRootCategories, numOfProducts, numOfOnePageProducts);
    }

    @Override
    protected String initUrl() {
        return "https://www.prodj.com.ua/";
    }

    @Override
    protected String setCode() {
        return SiteCode.prodj_;
    }

    @Override
    protected Class getScraperClass() {
        return ProdjScraper.class;
    }

    @Override
    protected List<Category> parseCategories() throws Exception {
        Document page = Jsoup.connect(URL).get();
        List<Category> categories = new ArrayList<>();
        Elements categoryItems = page.select(".menu").first().select("> div");
        for (int i = 0; i < numOfRootCategories; i++) {
            Element categoryItem = categoryItems.get(i);
            Element link = categoryItem.select("a").first();
            String title = link.text().trim();
            String url = link.absUrl("href");

            Elements subCategoryItems = categoryItem.select(".sub").first().select("a");

            Category category = new Category(title, url, "");
            category.setSubCategories(processSubCategories(subCategoryItems, category));
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
                                List<String> pageLinks = getPageUrls(page);
                                if (pageLinks == null) {
                                    pageLinks = new ArrayList<>();
                                    pageLinks.add(url);
                                }
                                int size = getSize(pageLinks);
                                for (int i = 0; i < size; i++) {
                                    if (page == null) {
                                        page = Jsoup.connect(pageLinks.get(i)).get();
                                    }
                                    Elements productItems = page.select(".cont-tov").first().select(".block-tov");
                                    productItems.removeIf(productItem -> productItem.select(".bt-price").text().isEmpty());
                                    productMap.putAll(processProducts(category, subCategory, productItems));
                                    page = null;
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }));
        return productMap;
    }

    @Override
    protected Product getProduct(Product product) throws Exception {
        driver.get(product.getUrl());
        // Click on more button
        WebElement element = driver.findElement(By.cssSelector(".more span button.arr4-d"));
        if (element.isDisplayed()) {
            element.click();
        }

        container = driver.findElement(By.cssSelector("div.tov-text1.more_open"));
        List<WebElement> descriptions = container.findElements(By.tagName("p"));

        for (int i = 0; i < 3; i++) {
            if (descriptions.size() < i) {
                break;
            }
            String text = descriptions.get(i).getText();
            if (text.length() > 0) {
                product.setDescription(text.trim());
            }
        }
        return product;
    }

    @Override
    protected List<Visualization> parseVisualizations(Product product) {
        List<Visualization> visualizations = new ArrayList<>();
        List<WebElement> images = driver.findElements(By.xpath("//div[@id='gallery']/a/img"));
        for (int i = 1; i < images.size(); i++) {
            Visualization big = new Visualization();
            big.setProduct(product);
            big.setType(VisualizationType.BIG_PICTURE.getCode());
            big.setUrl(customizeURL(images.get(i).getAttribute("data-big")));

            Visualization src = new Visualization();
            src.setProduct(product);
            src.setType(VisualizationType.PICTURE.getCode());
            src.setUrl(customizeURL(images.get(i).getAttribute("src")));

            visualizations.add(big);
            visualizations.add(src);
        }
        return visualizations;
    }

    @Override
    protected List<Attribute> parseAttributes(Product product) {
        List<Attribute> attributes = new ArrayList<>();
        List<WebElement> titles = container.findElements(By.xpath("//p/b/span/span"));
        List<WebElement> lists = container.findElements(By.tagName("ul"));
        Set<AttributeAssociation> attributeAssociations = new HashSet<>();
        for (int i = 0; i < titles.size(); i++) {
            String title = titles.get(i).getText();

            if (lists.size() <= i || title.contains("Гарантия")) {
                break;
            }

            List<WebElement> properties = lists.get(i).findElements(By.tagName("li"));
            for (int j = 0; j < properties.size(); j++) {
                String value = properties.get(j).getText();
                AttributeAssociation association = new AttributeAssociation();
                association.setProduct(product);
                Attribute attribute = new Attribute(title + "_" + j);
                association.setAttribute(attribute);
                association.setValue(value);

                attributes.add(attribute);
                attributeAssociations.add(association);
            }
        }
        product.setAttributes(attributeAssociations);
        return attributes;
    }

    private String customizeURL(String url) {
        return URL + url.substring(1);
    }

    private int getSize(List<String> pageLinks) {
        int size = numOfProducts / numOfOnePageProducts;
        return size > pageLinks.size() ? pageLinks.size() : size;
    }

    private List<String> getPageUrls(Document page) {
        Element pagination = page.select(".nav-pages").first();
        if (pagination == null)
            return null;
        Elements pageLinks = pagination.select("a");
        return pageLinks.parallelStream().map(pageLink -> pageLink.absUrl("href"))
                .collect(Collectors.toList());
    }

    private Set<Category> processSubCategories(Elements subCategoryItems, Category category) {
        return subCategoryItems.parallelStream()
                .map(subCategoryItem -> {
                    String subTitle = subCategoryItem.text().trim();
                    String subUrl = subCategoryItem.absUrl("href");
                    Category temp = new Category(subTitle, subUrl, "");
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
                    String priceStr = productItem.select(".bt-price").select(".tov-price").first().text()
                            .replace("Суперцена!", "")
                            .replace("\u00a0", "")
                            .replace("грн", "")
                            .trim();
                    BigDecimal price = BigDecimal.valueOf(Integer.parseInt(priceStr));
                    String sku = CODE + ProductUtil.makeSkuWithUrl(url);

                    Product product = new Product(title, url, price, sku, 10);
                    product.setCategories(Sets.newHashSet(new CategoryAssociation(product, subCategory)));
                    return product;
                })
                .collect(Collectors.toMap(Product::getSku, Function.identity()));
    }
}
