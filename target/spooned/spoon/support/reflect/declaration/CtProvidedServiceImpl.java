package spoon.support.reflect.declaration;


public class CtProvidedServiceImpl extends spoon.support.reflect.declaration.CtElementImpl implements spoon.reflect.declaration.CtProvidedService {
    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.SERVICE_TYPE)
    private spoon.reflect.reference.CtTypeReference serviceType;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.IMPLEMENTATION_TYPE)
    private java.util.List<spoon.reflect.reference.CtTypeReference> implementationTypes = spoon.support.reflect.declaration.CtElementImpl.emptyList();

    public CtProvidedServiceImpl() {
        super();
    }

    @java.lang.Override
    public spoon.reflect.reference.CtTypeReference getServiceType() {
        return this.serviceType;
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtProvidedService> T setServiceType(spoon.reflect.reference.CtTypeReference providingType) {
        if (providingType != null) {
            providingType.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.SERVICE_TYPE, providingType, this.serviceType);
        this.serviceType = providingType;
        return ((T) (this));
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.reference.CtTypeReference> getImplementationTypes() {
        return java.util.Collections.unmodifiableList(this.implementationTypes);
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtProvidedService> T setImplementationTypes(java.util.List<spoon.reflect.reference.CtTypeReference> usedTypes) {
        getFactory().getEnvironment().getModelChangeListener().onListDeleteAll(this, spoon.reflect.path.CtRole.IMPLEMENTATION_TYPE, this.implementationTypes, new java.util.ArrayList<>(this.implementationTypes));
        if ((usedTypes == null) || ((usedTypes.size()) == 0)) {
            this.implementationTypes = spoon.support.reflect.declaration.CtElementImpl.emptyList();
            return ((T) (this));
        }
        if ((this.implementationTypes) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.reference.CtTypeReference>emptyList())) {
            this.implementationTypes = new java.util.ArrayList<>();
        }
        this.implementationTypes.clear();
        for (spoon.reflect.reference.CtTypeReference usedType : usedTypes) {
            this.addImplementationType(usedType);
        }
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtProvidedService> T addImplementationType(spoon.reflect.reference.CtTypeReference usedType) {
        if (usedType == null) {
            return ((T) (this));
        }
        if ((this.implementationTypes) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.reference.CtTypeReference>emptyList())) {
            this.implementationTypes = new java.util.ArrayList<>();
        }
        getFactory().getEnvironment().getModelChangeListener().onListAdd(this, spoon.reflect.path.CtRole.IMPLEMENTATION_TYPE, this.implementationTypes, usedType);
        usedType.setParent(this);
        this.implementationTypes.add(usedType);
        return ((T) (this));
    }

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtProvidedService(this);
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtProvidedService clone() {
        return ((spoon.reflect.declaration.CtProvidedService) (super.clone()));
    }
}

