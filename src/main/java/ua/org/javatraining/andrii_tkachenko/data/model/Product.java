package ua.org.javatraining.andrii_tkachenko.data.model;

import ua.org.javatraining.andrii_tkachenko.data.model.attribute.AttributeAssociation;
import ua.org.javatraining.andrii_tkachenko.data.model.category.CategoryAssociation;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by tkaczenko on 12.03.17.
 */
@Entity
public class Product implements Serializable {
    @Id
    private String sku;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int amount;

    private int likes;

    private int dislikes;

    @Column(columnDefinition = "text")
    private String description;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CategoryAssociation> categories;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Visualization> visualizations;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AttributeAssociation> attributes;

    public Product() {

    }

    public Product(String sku, String name, int price, int amount) {
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        if (sku != null) {
            this.sku = sku;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null) {
            this.name = name;
        }
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

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<CategoryAssociation> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryAssociation> categories) {
        this.categories = categories;
    }

    public Set<Visualization> getVisualizations() {
        return visualizations;
    }

    public void setVisualizations(Set<Visualization> visualizations) {
        this.visualizations = visualizations;
    }

    public Set<AttributeAssociation> getAttributes() {
        return attributes;
    }

    public void setAttributes(Set<AttributeAssociation> attributes) {
        this.attributes = attributes;
    }
}
