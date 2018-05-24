package spoon.support.reflect.code;


public class CtFieldWriteImpl<T> extends spoon.support.reflect.code.CtFieldAccessImpl<T> implements spoon.reflect.code.CtFieldWrite<T> {
    private static final long serialVersionUID = 1L;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtFieldWrite(this);
    }

    @java.lang.Override
    public spoon.reflect.code.CtFieldWrite<T> clone() {
        return ((spoon.reflect.code.CtFieldWrite<T>) (super.clone()));
    }
}

