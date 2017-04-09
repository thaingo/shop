package ua.org.javatraining.andrii_tkachenko.data.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by tkaczenko on 15.12.16.
 */
@Entity
public class Customer implements Serializable {
    @Id
    private String email;

    private String phone;

    private String firstName;

    private boolean subscribed = false;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<CustomOrder> orders;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }

    public Set<CustomOrder> getOrders() {
        return orders;
    }

    public void setOrders(Set<CustomOrder> orders) {
        this.orders = orders;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
