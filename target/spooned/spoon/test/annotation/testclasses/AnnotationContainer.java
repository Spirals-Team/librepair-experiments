package spoon.test.annotation.testclasses;


@java.lang.annotation.Target({ java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.CONSTRUCTOR, java.lang.annotation.ElementType.LOCAL_VARIABLE, java.lang.annotation.ElementType.PACKAGE })
public @interface AnnotationContainer {
    spoon.test.annotation.testclasses.AnnotationRepeated[] value();
}

