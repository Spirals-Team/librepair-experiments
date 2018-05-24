package spoon.test.template.testclasses.match;


public @interface Check {
    long timeout() default 0L;

    java.lang.String value() default "def";
}

