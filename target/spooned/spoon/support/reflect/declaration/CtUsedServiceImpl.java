package spoon.support.reflect.declaration;


public class CtUsedServiceImpl extends spoon.support.reflect.declaration.CtElementImpl implements spoon.reflect.declaration.CtUsedService {
    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.SERVICE_TYPE)
    private spoon.reflect.reference.CtTypeReference serviceType;

    @java.lang.Override
    public spoon.reflect.reference.CtTypeReference getServiceType() {
        return this.serviceType;
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtUsedService> T setServiceType(spoon.reflect.reference.CtTypeReference usedService) {
        if (usedService != null) {
            usedService.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.SERVICE_TYPE, usedService, this.serviceType);
        this.serviceType = usedService;
        return ((T) (this));
    }

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtUsedService(this);
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtUsedService clone() {
        return ((spoon.reflect.declaration.CtUsedService) (super.clone()));
    }
}

