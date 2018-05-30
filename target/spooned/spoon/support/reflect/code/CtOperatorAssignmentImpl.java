package spoon.support.reflect.code;


public class CtOperatorAssignmentImpl<T, A extends T> extends spoon.support.reflect.code.CtAssignmentImpl<T, A> implements spoon.reflect.code.CtOperatorAssignment<T, A> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.OPERATOR_KIND)
    spoon.reflect.code.BinaryOperatorKind kind;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtOperatorAssignment(this);
    }

    @java.lang.Override
    public spoon.reflect.code.BinaryOperatorKind getKind() {
        return kind;
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtOperatorAssignment<T, A>> C setKind(spoon.reflect.code.BinaryOperatorKind kind) {
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.OPERATOR_KIND, kind, this.kind);
        this.kind = kind;
        return ((C) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtOperatorAssignment<T, A> clone() {
        return ((spoon.reflect.code.CtOperatorAssignment<T, A>) (super.clone()));
    }
}

