package spoon.support.reflect.declaration;


public class CtEnumValueImpl<T> extends spoon.support.reflect.declaration.CtFieldImpl<T> implements spoon.reflect.declaration.CtEnumValue<T> {
    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor v) {
        v.visitCtEnumValue(this);
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtEnumValue clone() {
        return ((spoon.reflect.declaration.CtEnumValue) (super.clone()));
    }

    @spoon.support.DerivedProperty
    @java.lang.Override
    public spoon.reflect.code.CtExpression<T> getAssignment() {
        return null;
    }
}

