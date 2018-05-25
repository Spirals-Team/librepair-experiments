package spoon.support.reflect.code;


public class CtAnnotationFieldAccessImpl<T> extends spoon.support.reflect.code.CtFieldAccessImpl<T> implements spoon.reflect.code.CtAnnotationFieldAccess<T> {
    private static final long serialVersionUID = 1L;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtAnnotationFieldAccess(this);
    }

    @java.lang.Override
    public spoon.support.reflect.code.CtAnnotationFieldAccessImpl<T> clone() {
        return ((spoon.support.reflect.code.CtAnnotationFieldAccessImpl<T>) (super.clone()));
    }
}

