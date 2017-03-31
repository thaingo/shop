package ua.org.javatraining.andrii_tkachenko.data.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by tkaczenko on 15.12.16.
 */
@Entity
public class Visualization implements Serializable {
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private int type;

    @Column(nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "product_visualizatin_fkey"))
    private Product product;

    public Visualization() {

    }

    public Visualization(int type, String url, Product product) {
        this.type = type;
        this.url = url;
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
