package spoon.support.reflect.code;


public class CtTryWithResourceImpl extends spoon.support.reflect.code.CtTryImpl implements spoon.reflect.code.CtTryWithResource {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.TRY_RESOURCE)
    java.util.List<spoon.reflect.code.CtLocalVariable<?>> resources = spoon.support.reflect.declaration.CtElementImpl.emptyList();

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtTryWithResource(this);
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.code.CtLocalVariable<?>> getResources() {
        return resources;
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtTryWithResource> T setResources(java.util.List<spoon.reflect.code.CtLocalVariable<?>> resources) {
        if ((resources == null) || (resources.isEmpty())) {
            this.resources = spoon.support.reflect.declaration.CtElementImpl.emptyList();
            return ((T) (this));
        }
        getFactory().getEnvironment().getModelChangeListener().onListDeleteAll(this, spoon.reflect.path.CtRole.TRY_RESOURCE, this.resources, new java.util.ArrayList<>(this.resources));
        this.resources.clear();
        for (spoon.reflect.code.CtLocalVariable<?> l : resources) {
            addResource(l);
        }
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.code.CtTryWithResource> T addResource(spoon.reflect.code.CtLocalVariable<?> resource) {
        if (resource == null) {
            return ((T) (this));
        }
        if ((resources) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.code.CtLocalVariable<?>>emptyList())) {
            resources = new java.util.ArrayList<>(spoon.reflect.ModelElementContainerDefaultCapacities.RESOURCES_CONTAINER_DEFAULT_CAPACITY);
        }
        resource.setParent(this);
        getFactory().getEnvironment().getModelChangeListener().onListAdd(this, spoon.reflect.path.CtRole.TRY_RESOURCE, this.resources, resource);
        resources.add(resource);
        return ((T) (this));
    }

    @java.lang.Override
    public boolean removeResource(spoon.reflect.code.CtLocalVariable<?> resource) {
        if ((resources) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.code.CtLocalVariable<?>>emptyList())) {
            return false;
        }
        getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.TRY_RESOURCE, resources, resources.indexOf(resource), resource);
        return resources.remove(resource);
    }

    @java.lang.Override
    public spoon.reflect.code.CtTryWithResource clone() {
        return ((spoon.reflect.code.CtTryWithResource) (super.clone()));
    }
}

