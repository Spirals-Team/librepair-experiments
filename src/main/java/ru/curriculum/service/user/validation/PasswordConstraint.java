package ru.curriculum.service.user.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraint {
    String message() default "Длина пароля не может быть меньше 3";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}