package spoon.template;


@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Target({ java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.METHOD })
public @interface Parameter {
    java.lang.String value() default "";

    java.lang.Class<? extends spoon.support.template.ParameterMatcher> match() default spoon.support.template.DefaultParameterMatcher.class;
}

