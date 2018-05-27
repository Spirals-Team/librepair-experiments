package spoon.reflect.declaration;


public interface CtModuleRequirement extends spoon.reflect.declaration.CtModuleDirective {
    enum RequiresModifier {
        STATIC, TRANSITIVE;}

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.MODIFIER)
    java.util.Set<spoon.reflect.declaration.CtModuleRequirement.RequiresModifier> getRequiresModifiers();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.MODIFIER)
    <T extends spoon.reflect.declaration.CtModuleRequirement> T setRequiresModifiers(java.util.Set<spoon.reflect.declaration.CtModuleRequirement.RequiresModifier> requiresModifiers);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.MODULE_REF)
    spoon.reflect.reference.CtModuleReference getModuleReference();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.MODULE_REF)
    <T extends spoon.reflect.declaration.CtModuleRequirement> T setModuleReference(spoon.reflect.reference.CtModuleReference moduleReference);

    @java.lang.Override
    spoon.reflect.declaration.CtModuleRequirement clone();
}

