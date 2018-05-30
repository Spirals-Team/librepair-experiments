package spoon.test.annotation.testclasses;


public class AnnotationIntrospection {
    @spoon.test.annotation.testclasses.TestAnnotation
    public void m() throws java.lang.NoSuchMethodException {
        spoon.test.annotation.testclasses.TestAnnotation annotation = getClass().getMethod("m").getAnnotation(spoon.test.annotation.testclasses.TestAnnotation.class);
        annotation.equals(null);
    }
}

