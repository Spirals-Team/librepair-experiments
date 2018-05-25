package spoon.test.annotation.testclasses;


public class AnnotArrayInnerClass {
    @java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
    public @interface Annotation {
        java.lang.Class<?>[] value() default {  };
    }
}

