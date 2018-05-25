package spoon.test.annotation.testclasses;


public @interface AnnotationDefaultAnnotation {
    spoon.test.annotation.testclasses.InnerAnnot inner() default @spoon.test.annotation.testclasses.InnerAnnot("");
}

