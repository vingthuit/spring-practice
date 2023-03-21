package net.thumbtack.buscompany.security.validators.fullname;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class NameValidator implements ConstraintValidator<Name, String> {
    @Value("${max_name_length}")
    private int maxLength;

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return name != null && !name.isBlank() && name.length() < maxLength && name.matches("[а-яЁ-ё-\\s\\-]+");
    }
}
