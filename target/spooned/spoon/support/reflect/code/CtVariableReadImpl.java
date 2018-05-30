package spoon.support.reflect.code;


public class CtVariableReadImpl<T> extends spoon.support.reflect.code.CtVariableAccessImpl<T> implements spoon.reflect.code.CtVariableRead<T> {
    private static final long serialVersionUID = 1L;

    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtVariableRead(this);
    }

    @java.lang.Override
    public spoon.reflect.code.CtVariableRead<T> clone() {
        return ((spoon.reflect.code.CtVariableRead<T>) (super.clone()));
    }
}

