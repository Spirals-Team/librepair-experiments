package spoon.reflect.visitor.filter;


public class AnnotationFilter<E extends spoon.reflect.declaration.CtElement> extends spoon.reflect.visitor.filter.TypeFilter<E> {
    private java.lang.Class<? extends java.lang.annotation.Annotation> annotationType;

    public AnnotationFilter(java.lang.Class<? extends java.lang.annotation.Annotation> annotationType) {
        super(spoon.reflect.declaration.CtElement.class);
        this.annotationType = annotationType;
    }

    public AnnotationFilter(java.lang.Class<E> elementType, java.lang.Class<? extends java.lang.annotation.Annotation> annotationType) {
        super(elementType);
        this.annotationType = annotationType;
    }

    @java.lang.Override
    public boolean matches(E element) {
        return (super.matches(element)) && ((element.getAnnotation(annotationType)) != null);
    }
}

