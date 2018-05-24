package spoon.reflect.code;


public interface CtSwitch<S> extends spoon.reflect.code.CtStatement {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    spoon.reflect.code.CtExpression<S> getSelector();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    <T extends spoon.reflect.code.CtSwitch<S>> T setSelector(spoon.reflect.code.CtExpression<S> selector);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.CASE)
    java.util.List<spoon.reflect.code.CtCase<? super S>> getCases();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.CASE)
    <T extends spoon.reflect.code.CtSwitch<S>> T setCases(java.util.List<spoon.reflect.code.CtCase<? super S>> cases);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.CASE)
    <T extends spoon.reflect.code.CtSwitch<S>> T addCase(spoon.reflect.code.CtCase<? super S> c);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.CASE)
    boolean removeCase(spoon.reflect.code.CtCase<? super S> c);

    @java.lang.Override
    spoon.reflect.code.CtSwitch<S> clone();
}

