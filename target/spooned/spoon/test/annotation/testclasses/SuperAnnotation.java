package spoon.test.annotation.testclasses;


public @interface SuperAnnotation {
    java.lang.String value = "";

    java.lang.String value() default spoon.test.annotation.testclasses.SuperAnnotation.value;

    java.lang.String value1();
}

