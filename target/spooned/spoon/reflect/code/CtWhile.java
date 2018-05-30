package spoon.reflect.code;


public interface CtWhile extends spoon.reflect.code.CtLoop {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    spoon.reflect.code.CtExpression<java.lang.Boolean> getLoopingExpression();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    <T extends spoon.reflect.code.CtWhile> T setLoopingExpression(spoon.reflect.code.CtExpression<java.lang.Boolean> expression);

    @java.lang.Override
    spoon.reflect.code.CtWhile clone();
}

