package spoon.support.reflect.code;


public class CtExecutableReferenceExpressionImpl<T, E extends spoon.reflect.code.CtExpression<?>> extends spoon.support.reflect.code.CtTargetedExpressionImpl<T, E> implements spoon.reflect.code.CtExecutableReferenceExpression<T, E> {
    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.EXECUTABLE_REF)
    spoon.reflect.reference.CtExecutableReference<T> executable;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtExecutableReferenceExpression(this);
    }

    @java.lang.Override
    public spoon.reflect.reference.CtExecutableReference<T> getExecutable() {
        return executable;
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtExecutableReferenceExpression<T, E>> C setExecutable(spoon.reflect.reference.CtExecutableReference<T> executable) {
        if (executable != null) {
            executable.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.EXECUTABLE_REF, executable, this.executable);
        this.executable = executable;
        return ((C) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtExecutableReferenceExpression<T, E> clone() {
        return ((spoon.reflect.code.CtExecutableReferenceExpression<T, E>) (super.clone()));
    }
}

