package net.thumbtack.buscompany.security.validators.fullname;

import org.springframework.beans.factory.annotation.Value;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PatronymicValidator implements ConstraintValidator<Patronymic, String> {
    @Value("${max_name_length}")
    private int maxLength;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return name == null || (name.length() < maxLength && name.matches("[а-яЁ-ё-\\s\\-]+"));
    }
}