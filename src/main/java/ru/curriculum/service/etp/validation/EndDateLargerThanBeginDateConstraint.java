package ru.curriculum.service.etp.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ETPDatesValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface EndDateLargerThanBeginDateConstraint {

    String message() default "Дата окончания должна быть больше даты начала";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String beginDate();

    String endDate();

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        EndDateLargerThanBeginDateConstraint[] value();
    }
}
