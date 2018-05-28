package spoon.test.annotation.testclasses;


@java.lang.annotation.Target({ java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.ANNOTATION_TYPE, java.lang.annotation.ElementType.TYPE_USE })
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Documented
public @interface PortRange {
    int min() default 1;

    int max() default 65535;

    java.lang.String message() default "{org.hibernate.validator.constraints.Range.message}";

    java.lang.Class<?>[] groups() default {  };

    java.lang.Class<?>[] payload() default {  };
}

