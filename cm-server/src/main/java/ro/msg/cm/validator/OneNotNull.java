package ro.msg.cm.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = CustomNotNullValidator.class)
public @interface OneNotNull {
    String[] value();
    String message() default "Phone or email must not be null";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
