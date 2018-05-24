package spoon.test.annotation.testclasses;


@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface AnnotArray {
    java.lang.Class<?>[] value() default {  };
}

