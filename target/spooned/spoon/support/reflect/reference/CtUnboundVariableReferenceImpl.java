package spoon.support.reflect.reference;


public class CtUnboundVariableReferenceImpl<T> extends spoon.support.reflect.reference.CtVariableReferenceImpl<T> implements spoon.reflect.reference.CtUnboundVariableReference<T> {
    private static final long serialVersionUID = -932423216089690817L;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtUnboundVariableReference(this);
    }

    @java.lang.Override
    public spoon.reflect.reference.CtUnboundVariableReference<T> clone() {
        return ((spoon.reflect.reference.CtUnboundVariableReference<T>) (super.clone()));
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public java.util.List<spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation>> getAnnotations() {
        return spoon.support.reflect.declaration.CtElementImpl.emptyList();
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <E extends spoon.reflect.declaration.CtElement> E setAnnotations(java.util.List<spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation>> annotations) {
        return ((E) (this));
    }
}

