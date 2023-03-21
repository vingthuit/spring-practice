package net.thumbtack.buscompany.security.validators;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class PhoneValidator implements ConstraintValidator<Phone, String> {

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext constraintValidatorContext) {
        String phonePattern = "((8|\\+7)(-)?)((\\(9\\d{2})\\)|9\\d{2})-?\\d{3}-?(\\d{4}|\\d{2}-\\d{2})";
        return phone != null && phone.matches(phonePattern);
    }
}
