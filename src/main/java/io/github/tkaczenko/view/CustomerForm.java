package io.github.tkaczenko.view;

import io.github.tkaczenko.valid.Phone;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

/**
 * Created by tkaczenko on 06.04.17.
 */
@Component
public class CustomerForm {
    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String address;

    @Phone
    private String phone;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
