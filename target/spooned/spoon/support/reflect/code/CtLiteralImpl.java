package spoon.support.reflect.code;


public class CtLiteralImpl<T extends java.lang.Object> extends spoon.support.reflect.code.CtExpressionImpl<T> implements spoon.reflect.code.CtLiteral<T> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.VALUE)
    T value;

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtLiteral(this);
    }

    @java.lang.Override
    public T getValue() {
        return value;
    }

    @java.lang.Override
    public <C extends spoon.reflect.code.CtLiteral<T>> C setValue(T value) {
        if ((this.value) instanceof spoon.reflect.declaration.CtElement) {
            ((spoon.reflect.declaration.CtElement) (this.value)).setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.EXPRESSION, value, this.value);
        this.value = value;
        return ((C) (this));
    }

    @java.lang.Override
    public spoon.reflect.code.CtLiteral<T> clone() {
        return ((spoon.reflect.code.CtLiteral<T>) (super.clone()));
    }
}

