package spoon.support.reflect.declaration;


public class CtModuleRequirementImpl extends spoon.support.reflect.declaration.CtElementImpl implements spoon.reflect.declaration.CtModuleRequirement {
    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.MODIFIER)
    private java.util.Set<spoon.reflect.declaration.CtModuleRequirement.RequiresModifier> requiresModifiers = spoon.support.reflect.declaration.CtElementImpl.emptySet();

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.MODULE_REF)
    private spoon.reflect.reference.CtModuleReference moduleReference;

    public CtModuleRequirementImpl() {
        super();
    }

    @java.lang.Override
    public java.util.Set<spoon.reflect.declaration.CtModuleRequirement.RequiresModifier> getRequiresModifiers() {
        return this.requiresModifiers;
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtModuleRequirement> T setRequiresModifiers(java.util.Set<spoon.reflect.declaration.CtModuleRequirement.RequiresModifier> requiresModifiers) {
        getFactory().getEnvironment().getModelChangeListener().onSetDeleteAll(this, spoon.reflect.path.CtRole.MODIFIER, this.requiresModifiers, new java.util.HashSet<>(requiresModifiers));
        if ((requiresModifiers == null) || (requiresModifiers.isEmpty())) {
            this.requiresModifiers = spoon.support.reflect.declaration.CtElementImpl.emptySet();
            return ((T) (this));
        }
        if ((this.requiresModifiers) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.declaration.CtModuleRequirement.RequiresModifier>emptySet())) {
            this.requiresModifiers = new java.util.HashSet<>();
        }
        this.requiresModifiers.clear();
        for (spoon.reflect.declaration.CtModuleRequirement.RequiresModifier requiresModifier : requiresModifiers) {
            getFactory().getEnvironment().getModelChangeListener().onSetAdd(this, spoon.reflect.path.CtRole.MODIFIER, this.requiresModifiers, requiresModifier);
            this.requiresModifiers.add(requiresModifier);
        }
        return ((T) (this));
    }

    @java.lang.Override
    public spoon.reflect.reference.CtModuleReference getModuleReference() {
        return this.moduleReference;
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtModuleRequirement> T setModuleReference(spoon.reflect.reference.CtModuleReference moduleReference) {
        if (moduleReference != null) {
            moduleReference.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.MODULE_REF, moduleReference, this.moduleReference);
        this.moduleReference = moduleReference;
        return ((T) (this));
    }

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtModuleRequirement(this);
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtModuleRequirement clone() {
        return ((spoon.reflect.declaration.CtModuleRequirement) (super.clone()));
    }
}

