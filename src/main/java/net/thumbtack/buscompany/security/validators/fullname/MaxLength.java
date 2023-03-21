package net.thumbtack.buscompany.security.validators.fullname;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MaxLengthValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxLength {
    String message() default "line has wrong length";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
