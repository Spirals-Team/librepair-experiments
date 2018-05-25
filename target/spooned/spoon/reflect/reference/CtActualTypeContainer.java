package spoon.reflect.reference;


public interface CtActualTypeContainer {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.TYPE_ARGUMENT)
    java.util.List<spoon.reflect.reference.CtTypeReference<?>> getActualTypeArguments();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.TYPE_ARGUMENT)
    <T extends spoon.reflect.reference.CtActualTypeContainer> T setActualTypeArguments(java.util.List<? extends spoon.reflect.reference.CtTypeReference<?>> actualTypeArguments);

    <T extends spoon.reflect.reference.CtActualTypeContainer> T addActualTypeArgument(spoon.reflect.reference.CtTypeReference<?> actualTypeArgument);

    boolean removeActualTypeArgument(spoon.reflect.reference.CtTypeReference<?> actualTypeArgument);
}

