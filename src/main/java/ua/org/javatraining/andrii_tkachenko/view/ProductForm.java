package ua.org.javatraining.andrii_tkachenko.view;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;
import ua.org.javatraining.andrii_tkachenko.data.model.category.Category;

import java.util.Collections;
import java.util.List;

/**
 * Created by tkaczenko on 03.05.17.
 */
@Component
public class ProductForm {
    private String sku;

    @NotEmpty
    private String name;

    private int price;

    private int amount;

    private String description;

    private List<Category> categories = Collections.emptyList();

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
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
}
