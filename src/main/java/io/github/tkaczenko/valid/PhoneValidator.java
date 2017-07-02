package io.github.tkaczenko.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by tkaczenko on 09.04.17.
 */
public class PhoneValidator implements ConstraintValidator<Phone, String> {
    @Override
    public void initialize(Phone constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || value.isEmpty() || (value.matches("\\d{10}") && value.charAt(0) == '0');
    }
}
