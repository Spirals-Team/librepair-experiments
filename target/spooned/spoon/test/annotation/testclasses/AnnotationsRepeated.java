package spoon.test.annotation.testclasses;


@spoon.test.annotation.testclasses.AnnotationRepeated("First")
@spoon.test.annotation.testclasses.AnnotationRepeated("Second")
public class AnnotationsRepeated {
    @spoon.test.annotation.testclasses.AnnotationRepeated("Field 1")
    @spoon.test.annotation.testclasses.AnnotationRepeated("Field 2")
    private java.lang.String field;

    @spoon.test.annotation.testclasses.AnnotationRepeated("Constructor 1")
    @spoon.test.annotation.testclasses.AnnotationRepeated("Constructor 2")
    public AnnotationsRepeated() {
    }

    @spoon.test.annotation.testclasses.AnnotationRepeated("Method 1")
    @spoon.test.annotation.testclasses.AnnotationRepeated("Method 2")
    public void method() {
    }

    public void methodWithParameter(@spoon.test.annotation.testclasses.AnnotationRepeated("Param 1")
    @spoon.test.annotation.testclasses.AnnotationRepeated("Param 2")
    java.lang.String param) {
    }

    public void methodWithLocalVariable() {
        @spoon.test.annotation.testclasses.AnnotationRepeated("Local 1")
        @spoon.test.annotation.testclasses.AnnotationRepeated("Local 2")
        java.lang.String s = "";
    }
}

