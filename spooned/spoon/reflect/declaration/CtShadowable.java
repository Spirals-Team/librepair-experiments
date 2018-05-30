package spoon.reflect.declaration;


public interface CtShadowable {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.IS_SHADOW)
    boolean isShadow();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.IS_SHADOW)
    <E extends spoon.reflect.declaration.CtShadowable> E setShadow(boolean isShadow);
}

