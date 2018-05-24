package spoon.support.reflect.declaration;


public class CtAnnotationTypeImpl<T extends java.lang.annotation.Annotation> extends spoon.support.reflect.declaration.CtTypeImpl<T> implements spoon.reflect.declaration.CtAnnotationType<T> {
    private static final long serialVersionUID = 1L;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor v) {
        v.visitCtAnnotationType(this);
    }

    @java.lang.Override
    public boolean isAnnotationType() {
        return true;
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public java.util.Set<spoon.reflect.reference.CtTypeReference<?>> getSuperInterfaces() {
        return java.util.Collections.emptySet();
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public spoon.reflect.reference.CtTypeReference<?> getSuperclass() {
        return null;
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <C extends spoon.reflect.declaration.CtType<T>> C setSuperclass(spoon.reflect.reference.CtTypeReference<?> superClass) {
        return ((C) (this));
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <C extends spoon.reflect.declaration.CtType<T>> C setSuperInterfaces(java.util.Set<spoon.reflect.reference.CtTypeReference<?>> interfaces) {
        return ((C) (this));
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public java.util.List<spoon.reflect.declaration.CtTypeParameter> getFormalCtTypeParameters() {
        return spoon.support.reflect.declaration.CtElementImpl.emptyList();
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <C extends spoon.reflect.declaration.CtFormalTypeDeclarer> C setFormalCtTypeParameters(java.util.List<spoon.reflect.declaration.CtTypeParameter> formalTypeParameters) {
        return ((C) (this));
    }

    @java.lang.Override
    public boolean isSubtypeOf(spoon.reflect.reference.CtTypeReference<?> type) {
        return getReference().isSubtypeOf(type);
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtAnnotationType<T> clone() {
        return ((spoon.reflect.declaration.CtAnnotationType<T>) (super.clone()));
    }

    @java.lang.Override
    public java.util.Set<spoon.reflect.declaration.CtAnnotationMethod<?>> getAnnotationMethods() {
        java.util.Set<spoon.reflect.declaration.CtAnnotationMethod<?>> annotationsMethods = new java.util.HashSet<>();
        for (spoon.reflect.declaration.CtMethod<?> method : getMethods()) {
            annotationsMethods.add(((spoon.reflect.declaration.CtAnnotationMethod<?>) (method)));
        }
        return annotationsMethods;
    }

    @java.lang.Override
    public <M, C extends spoon.reflect.declaration.CtType<T>> C addMethod(spoon.reflect.declaration.CtMethod<M> method) {
        if ((method != null) && (!(method instanceof spoon.reflect.declaration.CtAnnotationMethod))) {
            throw new java.lang.IllegalArgumentException(((("The method " + (method.getSignature())) + " should be a ") + (spoon.reflect.declaration.CtAnnotationMethod.class.getName())));
        }
        return super.addMethod(method);
    }
}

