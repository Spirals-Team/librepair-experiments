package spoon.support.reflect.code;


public class CtVariableWriteImpl<T> extends spoon.support.reflect.code.CtVariableAccessImpl<T> implements spoon.reflect.code.CtVariableWrite<T> {
    private static final long serialVersionUID = 1L;

    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtVariableWrite(this);
    }

    @java.lang.Override
    public spoon.reflect.code.CtVariableWrite<T> clone() {
        return ((spoon.reflect.code.CtVariableWrite<T>) (super.clone()));
    }
}

