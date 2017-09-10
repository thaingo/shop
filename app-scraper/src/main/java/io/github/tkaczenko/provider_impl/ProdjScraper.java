package io.github.tkaczenko.provider_impl;

import com.google.common.collect.Sets;
import io.github.tkaczenko.data.model.Product;
import io.github.tkaczenko.data.model.Visualization;
import io.github.tkaczenko.data.model.attribute.Attribute;
import io.github.tkaczenko.data.model.attribute.AttributeAssociation;
import io.github.tkaczenko.data.model.category.Category;
import io.github.tkaczenko.data.model.category.CategoryAssociation;
import io.github.tkaczenko.data.model.enumeration.VisualizationType;
import io.github.tkaczenko.provider.BaseScraper;
import io.github.tkaczenko.provider.ProductExtractor;
import io.github.tkaczenko.provider.SiteCode;
import io.github.tkaczenko.util.ProductUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by tkaczenko on 02.07.17.
 */
public class ProdjScraper extends BaseScraper {

    ProdjScraper(int numOfRootCategories, int numOfSubCategories, int numOfProducts, int numOfOnePageProducts, ProductExtractor productExtractor) {
        super(numOfRootCategories, numOfSubCategories, numOfProducts, numOfOnePageProducts, productExtractor);
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
    protected Elements getCategoryItems(Document page) {
        return page.select(".menu").first().select("> div");
    }

    @Override
    protected Category extractCategory(Elements categoryItems, int i) {
        Element categoryItem = categoryItems.get(i);
        Element link = categoryItem.select("a").first();
        String title = link.text().trim();
        String url = link.absUrl("href");
        Elements subCategoryItems = categoryItem.select(".sub").first().select("a");
        Category category = new Category(title, url, "");
        category.setSubCategories(processSubCategories(subCategoryItems, category));
        return category;
    }

    @Override
    protected Elements extractProductItems(Document page) {
        Elements productItems = page.select(".cont-tov").first().select(".block-tov");
        productItems.removeIf(productItem -> productItem.select(".bt-price").text().isEmpty());
        return productItems;
    }

    @Override
    protected Product extractProductBasicInfo(Category category, Category subCategory, Element productItem) {
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
        Product product = new Product(title, url, price, sku, DEFAULT_PRODUCT_AMOUNT);
        product.setCategories(Sets.newHashSet(new CategoryAssociation(product, subCategory)));
        countOfProducts++;
        return product;
    }

    @Override
    protected List<String> getNextPageUrls(Document page) {
        Element pagination = page.select(".nav-pages").first();
        if (pagination == null)
            return null;
        Elements pageLinks = pagination.select("a");
        return pageLinks.parallelStream().map(pageLink -> pageLink.absUrl("href"))
                .collect(Collectors.toList());
    }

    private Set<Category> processSubCategories(Elements subCategoryItems, Category category) {
        return subCategoryItems.parallelStream().limit(numOfSubCategories)
                .map(subCategoryItem -> {
                    String subTitle = subCategoryItem.text().trim();
                    String subUrl = subCategoryItem.absUrl("href");
                    Category temp = new Category(subTitle, subUrl, "");
                    temp.setParentCategory(category);
                    return temp;
                })
                .collect(Collectors.toSet());
    }

    public static class Extractor implements ProductExtractor {
        private Document page;
        private Element container;

        @Override
        public Product getProduct(Product product) throws Exception {
            page = Jsoup.connect(product.getUrl()).get();
            container = page.select("div.tov-text1:has(.more)").first();
            Elements descriptions = container.select("p");
            for (int i = 0; i < 3; i++) {
                if (descriptions.size() < i) {
                    break;
                }
                String text = descriptions.get(i).text();
                if (text.length() > 0) {
                    product.setDescription(text.trim());
                }
            }
            return product;
        }

        @Override
        public List<Visualization> parseVisualizations(Product product) {
            List<Visualization> visualizations = new ArrayList<>();
            Elements images = page.select("div[id=gallery] > a > img");
            for (int i = 1; i < images.size(); i++) {
                Visualization big = new Visualization();
                big.setProduct(product);
                big.setType(VisualizationType.BIG_PICTURE.getCode());
                big.setUrl(images.get(i).absUrl("data-big"));
                Visualization src = new Visualization();
                src.setProduct(product);
                src.setType(VisualizationType.PICTURE.getCode());
                src.setUrl(images.get(i).absUrl("src"));
                visualizations.add(big);
                visualizations.add(src);
            }
            return visualizations;
        }

        @Override
        public List<Attribute> parseAttributes(Product product) {
            List<Attribute> attributes = new ArrayList<>();
            Elements titles = container.select("p > b > span > span");
            Elements lists = container.select("ul");
            Set<AttributeAssociation> attributeAssociations = new HashSet<>();
            for (int i = 0; i < titles.size(); i++) {
                String title = titles.get(i).text();
                if (lists.size() <= i || title.contains("Гарантия")) {
                    break;
                }
                Elements properties = lists.get(i).select("li");
                for (int j = 0; j < properties.size(); j++) {
                    String value = properties.get(j).text();
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
    }
}
