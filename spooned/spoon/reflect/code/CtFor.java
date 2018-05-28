package spoon.reflect.code;


public interface CtFor extends spoon.reflect.code.CtLoop {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    spoon.reflect.code.CtExpression<java.lang.Boolean> getExpression();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.EXPRESSION)
    <T extends spoon.reflect.code.CtFor> T setExpression(spoon.reflect.code.CtExpression<java.lang.Boolean> expression);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.FOR_INIT)
    java.util.List<spoon.reflect.code.CtStatement> getForInit();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.FOR_INIT)
    <T extends spoon.reflect.code.CtFor> T addForInit(spoon.reflect.code.CtStatement statement);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.FOR_INIT)
    <T extends spoon.reflect.code.CtFor> T setForInit(java.util.List<spoon.reflect.code.CtStatement> forInit);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.FOR_INIT)
    boolean removeForInit(spoon.reflect.code.CtStatement statement);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.FOR_UPDATE)
    java.util.List<spoon.reflect.code.CtStatement> getForUpdate();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.FOR_UPDATE)
    <T extends spoon.reflect.code.CtFor> T addForUpdate(spoon.reflect.code.CtStatement statement);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.FOR_UPDATE)
    <T extends spoon.reflect.code.CtFor> T setForUpdate(java.util.List<spoon.reflect.code.CtStatement> forUpdate);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.FOR_UPDATE)
    boolean removeForUpdate(spoon.reflect.code.CtStatement statement);

    @java.lang.Override
    spoon.reflect.code.CtFor clone();
}

