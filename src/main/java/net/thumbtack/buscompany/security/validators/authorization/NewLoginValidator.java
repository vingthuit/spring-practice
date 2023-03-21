package net.thumbtack.buscompany.security.validators.authorization;

import net.thumbtack.buscompany.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class NewLoginValidator implements ConstraintValidator<NewLogin, String> {
    @Autowired
    private UserDao dao;

    @Override
    public boolean isValid(String login, ConstraintValidatorContext constraintValidatorContext) {
        return login != null && dao.findByKey(login) == null && login.matches("[a-zA-Zа-яЁ-ё0-9-]+");
    }
}