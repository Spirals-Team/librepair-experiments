package spoon.reflect.code;


public interface CtForEach extends spoon.reflect.code.CtLoop {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    spoon.reflect.code.CtExpression<?> getExpression();

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.FOREACH_VARIABLE)
    spoon.reflect.code.CtLocalVariable<?> getVariable();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    <T extends spoon.reflect.code.CtForEach> T setExpression(spoon.reflect.code.CtExpression<?> expression);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.FOREACH_VARIABLE)
    <T extends spoon.reflect.code.CtForEach> T setVariable(spoon.reflect.code.CtLocalVariable<?> variable);

    @java.lang.Override
    spoon.reflect.code.CtForEach clone();
}

