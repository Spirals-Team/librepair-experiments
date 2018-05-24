package spoon.reflect.declaration;


public interface CtMultiTypedElement extends spoon.reflect.declaration.CtElement {
    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.MULTI_TYPE)
    <T extends spoon.reflect.declaration.CtMultiTypedElement> T addMultiType(spoon.reflect.reference.CtTypeReference<?> ref);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.MULTI_TYPE)
    boolean removeMultiType(spoon.reflect.reference.CtTypeReference<?> ref);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.MULTI_TYPE)
    java.util.List<spoon.reflect.reference.CtTypeReference<?>> getMultiTypes();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.MULTI_TYPE)
    <T extends spoon.reflect.declaration.CtMultiTypedElement> T setMultiTypes(java.util.List<spoon.reflect.reference.CtTypeReference<?>> types);
}

