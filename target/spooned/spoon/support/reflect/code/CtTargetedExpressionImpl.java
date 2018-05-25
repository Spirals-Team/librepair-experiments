package spoon.support.reflect.code;


public abstract class CtTargetedExpressionImpl<E, T extends spoon.reflect.code.CtExpression<?>> extends spoon.support.reflect.code.CtExpressionImpl<E> implements spoon.reflect.code.CtTargetedExpression<E, T> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.TARGET)
    T target;

    @java.lang.Override
    public T getTarget() {
        return target;
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtTargetedExpression<E, T>> C setTarget(T target) {
        if (target != null) {
            target.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.TARGET, target, this.target);
        this.target = target;
        return ((C) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtTargetedExpression<E, T> clone() {
        return ((spoon.reflect.code.CtTargetedExpression<E, T>) (super.clone()));
    }
}

