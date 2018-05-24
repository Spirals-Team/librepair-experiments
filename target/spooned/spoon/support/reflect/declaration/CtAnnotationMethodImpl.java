package spoon.support.reflect.declaration;


public class CtAnnotationMethodImpl<T> extends spoon.support.reflect.declaration.CtMethodImpl<T> implements spoon.reflect.declaration.CtAnnotationMethod<T> {
    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.DEFAULT_EXPRESSION)
    spoon.reflect.code.CtExpression<T> defaultExpression;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor v) {
        v.visitCtAnnotationMethod(this);
    }

    @java.lang.Override
    public spoon.reflect.code.CtExpression<T> getDefaultExpression() {
        return defaultExpression;
    }

    @java.lang.Override
    public <C extends spoon.reflect.declaration.CtAnnotationMethod<T>> C setDefaultExpression(spoon.reflect.code.CtExpression<T> assignedExpression) {
        if (assignedExpression != null) {
            assignedExpression.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.DEFAULT_EXPRESSION, assignedExpression, this.defaultExpression);
        this.defaultExpression = assignedExpression;
        return ((C) (this));
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public spoon.reflect.code.CtBlock<T> getBody() {
        return null;
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <T extends spoon.reflect.code.CtBodyHolder> T setBody(spoon.reflect.code.CtStatement statement) {
        return ((T) (this));
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public java.util.Set<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>> getThrownTypes() {
        return spoon.support.reflect.declaration.CtElementImpl.emptySet();
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <U extends spoon.reflect.declaration.CtExecutable<T>> U setThrownTypes(java.util.Set<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>> thrownTypes) {
        return ((U) (this));
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
    @spoon.support.DerivedProperty
    public java.util.List<spoon.reflect.declaration.CtParameter<?>> getParameters() {
        return spoon.support.reflect.declaration.CtElementImpl.emptyList();
    }

    @java.lang.Override
    @spoon.support.UnsettableProperty
    public <U extends spoon.reflect.declaration.CtExecutable<T>> U setParameters(java.util.List<spoon.reflect.declaration.CtParameter<?>> parameters) {
        return ((U) (this));
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtAnnotationMethod<T> clone() {
        return ((spoon.reflect.declaration.CtAnnotationMethod<T>) (super.clone()));
    }
}

