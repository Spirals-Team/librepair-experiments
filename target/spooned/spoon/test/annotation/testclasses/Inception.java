package spoon.test.annotation.testclasses;


public @interface Inception {
    spoon.test.annotation.testclasses.InnerAnnot value();

    spoon.test.annotation.testclasses.InnerAnnot[] values() default {  };
}

