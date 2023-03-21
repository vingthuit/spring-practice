package net.thumbtack.buscompany.security.validators.authorization;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NewLoginValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NewLogin {
    String message() default "invalid login";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
