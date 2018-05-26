package spoon.reflect.reference;


public interface CtUnboundVariableReference<T> extends spoon.reflect.reference.CtVariableReference<T> {
    @java.lang.Override
    spoon.reflect.reference.CtUnboundVariableReference<T> clone();

    @java.lang.Override
    @spoon.support.UnsettableProperty
    <E extends spoon.reflect.declaration.CtElement> E setAnnotations(java.util.List<spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation>> annotation);
}

