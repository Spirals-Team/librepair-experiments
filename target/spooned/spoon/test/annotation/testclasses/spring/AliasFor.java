package spoon.test.annotation.testclasses.spring;


@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Target(java.lang.annotation.ElementType.METHOD)
@java.lang.annotation.Documented
public @interface AliasFor {
    @spoon.test.annotation.testclasses.spring.AliasFor("attribute")
    java.lang.String value() default "";

    @spoon.test.annotation.testclasses.spring.AliasFor("value")
    java.lang.String attribute() default "";

    java.lang.Class<? extends java.lang.annotation.Annotation> annotation() default java.lang.annotation.Annotation.class;
}

