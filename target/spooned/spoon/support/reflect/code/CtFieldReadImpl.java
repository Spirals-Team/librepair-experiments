package spoon.support.reflect.code;


public class CtFieldReadImpl<T> extends spoon.support.reflect.code.CtFieldAccessImpl<T> implements spoon.reflect.code.CtFieldRead<T> {
    private static final long serialVersionUID = 1L;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtFieldRead(this);
    }

    @java.lang.Override
    public spoon.reflect.code.CtFieldRead<T> clone() {
        return ((spoon.reflect.code.CtFieldRead<T>) (super.clone()));
    }
}

