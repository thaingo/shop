package io.github.tkaczenko.model.category;

import io.github.tkaczenko.model.Product;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by tkaczenko on 11.03.17.
 */
@Entity
public class CategoryAssociation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "product_categoryassociation_fkey"))
    private Product product;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "category_categoryassociation_fkey"))
    private Category category;

    public CategoryAssociation() {

    }

    public CategoryAssociation(Product product, Category category) {
        this.product = product;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}