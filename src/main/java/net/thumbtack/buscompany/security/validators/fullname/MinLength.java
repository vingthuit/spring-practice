package net.thumbtack.buscompany.security.validators.fullname;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MinLengthValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MinLength {
    String message() default "line has wrong length";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
