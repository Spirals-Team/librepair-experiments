package spoon.test.annotation.testclasses;


@java.lang.annotation.Target(java.lang.annotation.ElementType.PARAMETER)
public @interface Bound {
    int max() default 10;
}

