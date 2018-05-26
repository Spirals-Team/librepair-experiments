package spoon.support.reflect.code;


public abstract class CtFieldAccessImpl<T> extends spoon.support.reflect.code.CtVariableAccessImpl<T> implements spoon.reflect.code.CtFieldAccess<T> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.TARGET)
    spoon.reflect.code.CtExpression<?> target;

    @java.lang.Override
    public spoon.reflect.code.CtExpression<?> getTarget() {
        return target;
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtTargetedExpression<T, spoon.reflect.code.CtExpression<?>>> C setTarget(spoon.reflect.code.CtExpression<?> target) {
        if (target != null) {
            target.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.TARGET, target, this.target);
        this.target = target;
        return null;
    }

    @java.lang.Override
    public spoon.reflect.reference.CtFieldReference<T> getVariable() {
        if ((variable) != null) {
            return ((spoon.reflect.reference.CtFieldReference<T>) (variable));
        }
        if ((getFactory()) != null) {
            spoon.reflect.reference.CtFieldReference<java.lang.Object> fieldReference = getFactory().Core().createFieldReference();
            fieldReference.setParent(this);
            return ((spoon.reflect.reference.CtFieldReference<T>) (fieldReference));
        }
        return null;
    }

    @java.lang.Override
    public spoon.reflect.code.CtFieldAccess<T> clone() {
        return ((spoon.reflect.code.CtFieldAccess<T>) (super.clone()));
    }
}

