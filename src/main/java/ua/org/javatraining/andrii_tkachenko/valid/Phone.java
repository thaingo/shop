package ua.org.javatraining.andrii_tkachenko.valid;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by tkaczenko on 09.04.17.
 */
@Documented
@Constraint(validatedBy = PhoneValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Phone {
    String message() default "{Phone}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
