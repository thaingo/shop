package ua.org.javatraining.andrii_tkachenko.data.model.attribute;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by tkaczenko on 15.12.16.
 */
@Entity
public class Attribute implements Serializable {
    @Id
    private String name;

    @Override
    public boolean equals(Object c) {
        if (c instanceof Attribute) {
            Attribute that = (Attribute) c;
            return this.name.equals(that.getName());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @OneToMany(mappedBy = "product")
    private Set<AttributeAssociation> products;

    public Attribute() {

    }

    public Attribute(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AttributeAssociation> getProducts() {
        return products;
    }

    public void setProducts(Set<AttributeAssociation> products) {
        this.products = products;
    }
}
