package net.thumbtack.buscompany.security.validators.fullname;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PatronymicValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Patronymic {
    String message() default "invalid patronymic";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
