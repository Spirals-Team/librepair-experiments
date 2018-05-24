package spoon.test.annotation.testclasses;


@java.lang.annotation.Target({ java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.FIELD, java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.PARAMETER, java.lang.annotation.ElementType.CONSTRUCTOR, java.lang.annotation.ElementType.LOCAL_VARIABLE, java.lang.annotation.ElementType.PACKAGE })
@java.lang.annotation.Repeatable(spoon.test.annotation.testclasses.AnnotationContainer.class)
public @interface AnnotationRepeated {
    java.lang.String value();
}

