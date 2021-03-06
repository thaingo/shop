package io.github.tkaczenko.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by tkaczenko on 15.12.16.
 */
@Entity
public class OrderItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private BigDecimal subtotal;

    @Column(nullable = false)
    private int amount;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "product_ordreritem_fkey"))
    private Product product;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "customorder_orderitem_fkey"))
    private CustomOrder order;

    public OrderItem() {

    }

    public OrderItem(BigDecimal subtotal, int amount, Product product, CustomOrder order) {
        this.subtotal = subtotal;
        this.amount = amount;
        this.product = product;
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public CustomOrder getOrder() {
        return order;
    }

    public void setOrder(CustomOrder order) {
        this.order = order;
    }
}
