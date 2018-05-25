package spoon.support.reflect.code;


public class CtArrayWriteImpl<T> extends spoon.support.reflect.code.CtArrayAccessImpl<T, spoon.reflect.code.CtExpression<?>> implements spoon.reflect.code.CtArrayWrite<T> {
    private static final long serialVersionUID = 1L;

    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtArrayWrite(this);
    }

    @java.lang.Override
    public spoon.reflect.code.CtArrayWrite<T> clone() {
        return ((spoon.reflect.code.CtArrayWrite<T>) (super.clone()));
    }
}

