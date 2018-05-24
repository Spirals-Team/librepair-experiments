package spoon.support.reflect.code;


public class CtSuperAccessImpl<T> extends spoon.support.reflect.code.CtVariableReadImpl<T> implements spoon.reflect.code.CtSuperAccess<T> {
    private static final long serialVersionUID = 1L;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtSuperAccess(this);
    }

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
    public spoon.reflect.code.CtSuperAccess<T> clone() {
        return ((spoon.reflect.code.CtSuperAccess<T>) (super.clone()));
    }
}

