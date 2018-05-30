package spoon.reflect.code;


public interface CtDo extends spoon.reflect.code.CtLoop {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    spoon.reflect.code.CtExpression<java.lang.Boolean> getLoopingExpression();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    <T extends spoon.reflect.code.CtDo> T setLoopingExpression(spoon.reflect.code.CtExpression<java.lang.Boolean> expression);

    @java.lang.Override
    spoon.reflect.code.CtDo clone();
}

