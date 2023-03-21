package net.thumbtack.buscompany.security.validators;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class DatesValidator implements ConstraintValidator<Dates, Object> {

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext constraintValidatorContext) {
        Object schedule = new BeanWrapperImpl(obj).getPropertyValue("schedule");
        Object dates = new BeanWrapperImpl(obj).getPropertyValue("dates");
        return schedule != null || dates != null;
    }
}
