package spoon.support.reflect.code;


public class CtThisAccessImpl<T> extends spoon.support.reflect.code.CtTargetedExpressionImpl<T, spoon.reflect.code.CtExpression<?>> implements spoon.reflect.code.CtThisAccess<T> {
    private static final long serialVersionUID = 1L;

    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtThisAccess(this);
    }

    @java.lang.Override
    public spoon.reflect.code.CtThisAccess<T> clone() {
        return ((spoon.reflect.code.CtThisAccess<T>) (super.clone()));
    }
}

