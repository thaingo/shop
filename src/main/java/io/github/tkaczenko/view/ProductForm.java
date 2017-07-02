package io.github.tkaczenko.view;

import io.github.tkaczenko.data.model.category.Category;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tkaczenko on 03.05.17.
 */
@Component
public class ProductForm {
    private String sku;

    @NotEmpty
    private String name;

    private BigDecimal price;

    private int amount;

    private String description;

    private int size;

    private List<Category> categories = new ArrayList<>();

    private List<AttributeValue> attributeValues = new ArrayList<>();

    public ProductForm() {

    }

    public ProductForm(String sku, String name, BigDecimal price, int amount) {
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<AttributeValue> getAttributeValues() {
        return attributeValues;
    }

    public void setAttributeValues(List<AttributeValue> attributeValues) {
        this.attributeValues = attributeValues;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public static class AttributeValue {
        private String attribute;
        private String value;

        public AttributeValue() {

        }

        public AttributeValue(String attribute, String value) {
            this.attribute = attribute;
            this.value = value;
        }

        public String getAttribute() {
            return attribute;
        }

        public void setAttribute(String attribute) {
            this.attribute = attribute;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
