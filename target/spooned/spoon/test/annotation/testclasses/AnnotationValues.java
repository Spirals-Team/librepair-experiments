package spoon.test.annotation.testclasses;


@spoon.test.annotation.testclasses.AnnotationValues.Annotation(integer = 42, integers = { 7, 42 }, string = "Everyone love tacos!", strings = "", clazz = spoon.test.annotation.testclasses.AnnotationValues.class, classes = { java.lang.annotation.Annotation.class, spoon.test.annotation.testclasses.AnnotationValues.class }, b = true, e = spoon.test.annotation.testclasses.AnnotationValues.Annotation.InnerEnum.R, ia = @spoon.test.annotation.testclasses.AnnotationValues.Annotation.InnerAnnotation, ias = { @spoon.test.annotation.testclasses.AnnotationValues.Annotation.InnerAnnotation })
public class AnnotationValues {
    public void method() {
        new java.lang.@spoon.test.annotation.testclasses.AnnotationValues.Annotation(integer = 42, integers = { 7, 42 }, string = "Everyone love tacos!", strings = "", clazz = spoon.test.annotation.testclasses.AnnotationValues.class, classes = { java.lang.annotation.Annotation.class, spoon.test.annotation.testclasses.AnnotationValues.class }, b = true, e = spoon.test.annotation.testclasses.AnnotationValues.Annotation.InnerEnum.R, ia = @spoon.test.annotation.testclasses.AnnotationValues.Annotation.InnerAnnotation, ias = { @spoon.test.annotation.testclasses.AnnotationValues.Annotation.InnerAnnotation })
        String();
    }

    @java.lang.annotation.Target({ java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.CONSTRUCTOR, java.lang.annotation.ElementType.LOCAL_VARIABLE, java.lang.annotation.ElementType.PACKAGE, java.lang.annotation.ElementType.TYPE_USE })
    public @interface Annotation {
        int integer();

        int[] integers();

        java.lang.String string();

        java.lang.String[] strings();

        java.lang.Class<?> clazz();

        java.lang.Class<?>[] classes();

        boolean b();

        spoon.test.annotation.testclasses.AnnotationValues.Annotation.InnerEnum e();

        spoon.test.annotation.testclasses.AnnotationValues.Annotation.InnerAnnotation ia();

        spoon.test.annotation.testclasses.AnnotationValues.Annotation.InnerAnnotation[] ias();

        @interface InnerAnnotation {}

        enum InnerEnum {
            R;}
    }
}

