package spoon.support.reflect.code;


public abstract class CtVariableAccessImpl<T> extends spoon.support.reflect.code.CtExpressionImpl<T> implements spoon.reflect.code.CtVariableAccess<T> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.VARIABLE)
    spoon.reflect.reference.CtVariableReference<T> variable;

    @java.lang.Override
    public spoon.reflect.reference.CtVariableReference<T> getVariable() {
        if (((variable) == null) && ((getFactory()) != null)) {
            variable = getFactory().Core().createLocalVariableReference();
            variable.setParent(this);
        }
        return ((spoon.reflect.reference.CtVariableReference<T>) (variable));
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtVariableAccess<T>> C setVariable(spoon.reflect.reference.CtVariableReference<T> variable) {
        if (variable != null) {
            variable.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.VARIABLE, variable, this.variable);
        this.variable = variable;
        return ((C) (this));
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public spoon.reflect.reference.CtTypeReference<T> getType() {
        return getVariable().getType();
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public <C extends spoon.reflect.declaration.CtTypedElement> C setType(spoon.reflect.reference.CtTypeReference<T> type) {
        if (type != null) {
            type.setParent(this);
        }
        if (type != null) {
            getVariable().setType(type);
        }
        return ((C) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtVariableAccess<T> clone() {
        return ((spoon.reflect.code.CtVariableAccess<T>) (super.clone()));
    }
}

