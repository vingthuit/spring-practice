package net.thumbtack.buscompany.security.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DatesValidator.class)
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Dates {
    String message() default "There must be schedule or dates in trip";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}