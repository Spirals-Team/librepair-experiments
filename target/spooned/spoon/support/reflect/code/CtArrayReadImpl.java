package spoon.support.reflect.code;


public class CtArrayReadImpl<T> extends spoon.support.reflect.code.CtArrayAccessImpl<T, spoon.reflect.code.CtExpression<?>> implements spoon.reflect.code.CtArrayRead<T> {
    private static final long serialVersionUID = 1L;

    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtArrayRead(this);
    }

    @java.lang.Override
    public spoon.reflect.code.CtArrayRead<T> clone() {
        return ((spoon.reflect.code.CtArrayRead<T>) (super.clone()));
    }
}

