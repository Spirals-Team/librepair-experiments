package spoon.reflect.code;


public interface CtTryWithResource extends spoon.reflect.code.CtTry {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.TRY_RESOURCE)
    java.util.List<spoon.reflect.code.CtLocalVariable<?>> getResources();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.TRY_RESOURCE)
    <T extends spoon.reflect.code.CtTryWithResource> T setResources(java.util.List<spoon.reflect.code.CtLocalVariable<?>> resources);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.TRY_RESOURCE)
    <T extends spoon.reflect.code.CtTryWithResource> T addResource(spoon.reflect.code.CtLocalVariable<?> resource);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.TRY_RESOURCE)
    boolean removeResource(spoon.reflect.code.CtLocalVariable<?> resource);

    @java.lang.Override
    spoon.reflect.code.CtTryWithResource clone();
}

