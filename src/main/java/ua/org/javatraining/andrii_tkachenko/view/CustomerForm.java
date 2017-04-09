package ua.org.javatraining.andrii_tkachenko.view;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;
import ua.org.javatraining.andrii_tkachenko.valid.Phone;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by tkaczenko on 06.04.17.
 */
@Component
public class CustomerForm {
    @NotNull
    @Size(min = 2, max = 30)
    private String name;

    @Phone
    private String phone;

    @NotEmpty
    @Email
    private String email;

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
}
