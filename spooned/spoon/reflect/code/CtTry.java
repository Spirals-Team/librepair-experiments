package spoon.reflect.code;


public interface CtTry extends spoon.reflect.code.CtBodyHolder , spoon.reflect.code.CtStatement , spoon.template.TemplateParameter<java.lang.Void> {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.CATCH)
    java.util.List<spoon.reflect.code.CtCatch> getCatchers();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.CATCH)
    <T extends spoon.reflect.code.CtTry> T setCatchers(java.util.List<spoon.reflect.code.CtCatch> catchers);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.CATCH)
    <T extends spoon.reflect.code.CtTry> T addCatcher(spoon.reflect.code.CtCatch catcher);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.CATCH)
    boolean removeCatcher(spoon.reflect.code.CtCatch catcher);

    @java.lang.Override
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.BODY)
    spoon.reflect.code.CtBlock<?> getBody();

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.FINALIZER)
    spoon.reflect.code.CtBlock<?> getFinalizer();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.FINALIZER)
    <T extends spoon.reflect.code.CtTry> T setFinalizer(spoon.reflect.code.CtBlock<?> finalizer);

    @java.lang.Override
    spoon.reflect.code.CtTry clone();
}

