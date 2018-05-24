package spoon.support.reflect.reference;


public class CtIntersectionTypeReferenceImpl<T> extends spoon.support.reflect.reference.CtTypeReferenceImpl<T> implements spoon.reflect.reference.CtIntersectionTypeReference<T> {
    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.BOUND)
    java.util.List<spoon.reflect.reference.CtTypeReference<?>> bounds = spoon.support.reflect.declaration.CtElementImpl.emptyList();

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtIntersectionTypeReference(this);
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.reference.CtTypeReference<?>> getBounds() {
        return java.util.Collections.unmodifiableList(bounds);
    }

    @java.lang.Override
    public <C extends spoon.reflect.reference.CtIntersectionTypeReference> C setBounds(java.util.List<spoon.reflect.reference.CtTypeReference<?>> bounds) {
        if ((bounds == null) || (bounds.isEmpty())) {
            this.bounds = spoon.support.reflect.declaration.CtElementImpl.emptyList();
            return ((C) (this));
        }
        if ((this.bounds) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.reference.CtTypeReference<?>>emptySet())) {
            this.bounds = new java.util.ArrayList<>();
        }
        getFactory().getEnvironment().getModelChangeListener().onListDeleteAll(this, spoon.reflect.path.CtRole.BOUND, this.bounds, new java.util.ArrayList<>(this.bounds));
        this.bounds.clear();
        for (spoon.reflect.reference.CtTypeReference<?> bound : bounds) {
            addBound(bound);
        }
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.reference.CtIntersectionTypeReference> C addBound(spoon.reflect.reference.CtTypeReference<?> bound) {
        if (bound == null) {
            return ((C) (this));
        }
        if ((bounds) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.reference.CtTypeReference<?>>emptyList())) {
            bounds = new java.util.ArrayList<>();
        }
        if (!(bounds.contains(bound))) {
            bound.setParent(this);
            getFactory().getEnvironment().getModelChangeListener().onListAdd(this, spoon.reflect.path.CtRole.BOUND, this.bounds, bound);
            bounds.add(bound);
        }
        return ((C) (this));
    }

    @java.lang.Override
    public boolean removeBound(spoon.reflect.reference.CtTypeReference<?> bound) {
        if ((bounds) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.reference.CtTypeReference<?>>emptyList())) {
            return false;
        }
        getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.BOUND, bounds, bounds.indexOf(bound), bound);
        return bounds.remove(bound);
    }

    @java.lang.Override
    public spoon.reflect.reference.CtTypeReference<?> getTypeErasure() {
        if (((bounds) == null) || (bounds.isEmpty())) {
            return getFactory().Type().OBJECT;
        }
        return bounds.get(0).getTypeErasure();
    }

    @java.lang.Override
    public spoon.reflect.reference.CtIntersectionTypeReference<T> clone() {
        return ((spoon.reflect.reference.CtIntersectionTypeReference<T>) (super.clone()));
    }
}

