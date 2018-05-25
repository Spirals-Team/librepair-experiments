package spoon.reflect.code;


public interface CtSynchronized extends spoon.reflect.code.CtStatement {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    spoon.reflect.code.CtExpression<?> getExpression();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    <T extends spoon.reflect.code.CtSynchronized> T setExpression(spoon.reflect.code.CtExpression<?> expression);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.BODY)
    spoon.reflect.code.CtBlock<?> getBlock();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.BODY)
    <T extends spoon.reflect.code.CtSynchronized> T setBlock(spoon.reflect.code.CtBlock<?> block);

    @java.lang.Override
    spoon.reflect.code.CtSynchronized clone();
}

