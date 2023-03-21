package net.thumbtack.buscompany.security.validators.fullname;

import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MinLengthValidator implements ConstraintValidator<MinLength, String> {
    @Value("${min_password_length}")
    private int minLength;

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        return password != null && !password.isBlank() && password.length() >= minLength;
    }
}
