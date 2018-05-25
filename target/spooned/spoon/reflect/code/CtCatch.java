package spoon.reflect.code;


public interface CtCatch extends spoon.reflect.code.CtBodyHolder , spoon.reflect.code.CtCodeElement {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.PARAMETER)
    spoon.reflect.code.CtCatchVariable<? extends java.lang.Throwable> getParameter();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.PARAMETER)
    <T extends spoon.reflect.code.CtCatch> T setParameter(spoon.reflect.code.CtCatchVariable<? extends java.lang.Throwable> parameter);

    @java.lang.Override
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.BODY)
    spoon.reflect.code.CtBlock<?> getBody();

    @java.lang.Override
    spoon.reflect.code.CtCatch clone();
}

