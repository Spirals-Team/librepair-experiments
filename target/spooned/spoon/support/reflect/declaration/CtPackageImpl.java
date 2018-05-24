package spoon.support.reflect.declaration;


public class CtPackageImpl extends spoon.support.reflect.declaration.CtNamedElementImpl implements spoon.reflect.declaration.CtPackage {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.SUB_PACKAGE)
    protected java.util.Set<spoon.reflect.declaration.CtPackage> packs = orderedPackageSet();

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.CONTAINED_TYPE)
    private java.util.Set<spoon.reflect.declaration.CtType<?>> types = orderedTypeSet();

    public CtPackageImpl() {
        super();
    }

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor v) {
        v.visitCtPackage(this);
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtPackage> T addPackage(spoon.reflect.declaration.CtPackage pack) {
        if (pack == null) {
            return ((T) (this));
        }
        if ((packs) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.declaration.CtPackage>emptySet())) {
            this.packs = orderedPackageSet();
        }
        if (this.getQualifiedName().equals(pack.getQualifiedName())) {
            addAllTypes(pack, this);
            addAllPackages(pack, this);
            return ((T) (this));
        }
        for (spoon.reflect.declaration.CtPackage p1 : packs) {
            if (p1.getQualifiedName().equals(pack.getQualifiedName())) {
                addAllTypes(pack, p1);
                addAllPackages(pack, p1);
                return ((T) (this));
            }
        }
        pack.setParent(this);
        getFactory().getEnvironment().getModelChangeListener().onSetAdd(this, spoon.reflect.path.CtRole.SUB_PACKAGE, this.packs, pack);
        this.packs.add(pack);
        return ((T) (this));
    }

    private java.util.Set<spoon.reflect.declaration.CtPackage> orderedPackageSet() {
        return new spoon.support.util.QualifiedNameBasedSortedSet<>();
    }

    private java.util.Set<spoon.reflect.declaration.CtType<?>> orderedTypeSet() {
        return new spoon.support.util.QualifiedNameBasedSortedSet<>();
    }

    private void addAllTypes(spoon.reflect.declaration.CtPackage from, spoon.reflect.declaration.CtPackage to) {
        for (spoon.reflect.declaration.CtType t : from.getTypes()) {
            for (spoon.reflect.declaration.CtType t2 : to.getTypes()) {
                if ((t2.getQualifiedName().equals(t.getQualifiedName())) && (!(t2.equals(t)))) {
                    throw new java.lang.IllegalStateException("types with same qualified names and different code cannot be merged");
                }
            }
            to.addType(t);
        }
    }

    private void addAllPackages(spoon.reflect.declaration.CtPackage from, spoon.reflect.declaration.CtPackage to) {
        for (spoon.reflect.declaration.CtPackage p : from.getPackages()) {
            to.addPackage(p);
        }
    }

    @java.lang.Override
    public boolean removePackage(spoon.reflect.declaration.CtPackage pack) {
        if ((packs) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.declaration.CtPackage>emptySet())) {
            return false;
        }
        getFactory().getEnvironment().getModelChangeListener().onSetDelete(this, spoon.reflect.path.CtRole.SUB_PACKAGE, packs, pack);
        return packs.remove(pack);
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtModule getDeclaringModule() {
        try {
            return getParent(spoon.reflect.declaration.CtModule.class);
        } catch (spoon.reflect.declaration.ParentNotInitializedException e) {
            return null;
        }
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtPackage getDeclaringPackage() {
        try {
            return getParent(spoon.reflect.declaration.CtPackage.class);
        } catch (spoon.reflect.declaration.ParentNotInitializedException e) {
            return null;
        }
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtPackage getPackage(java.lang.String name) {
        for (spoon.reflect.declaration.CtPackage p : packs) {
            if (p.getSimpleName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    @java.lang.Override
    public java.util.Set<spoon.reflect.declaration.CtPackage> getPackages() {
        return packs;
    }

    @java.lang.Override
    public java.lang.String getQualifiedName() {
        if (((getDeclaringPackage()) == null) || (getDeclaringPackage().isUnnamedPackage())) {
            return getSimpleName();
        }else {
            return ((getDeclaringPackage().getQualifiedName()) + ".") + (getSimpleName());
        }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public <T extends spoon.reflect.declaration.CtType<?>> T getType(java.lang.String simpleName) {
        for (spoon.reflect.declaration.CtType<?> t : types) {
            if (t.getSimpleName().equals(simpleName)) {
                return ((T) (t));
            }
        }
        return null;
    }

    @java.lang.Override
    public java.util.Set<spoon.reflect.declaration.CtType<?>> getTypes() {
        return types;
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtPackage> T setPackages(java.util.Set<spoon.reflect.declaration.CtPackage> packs) {
        if ((packs == null) || (packs.isEmpty())) {
            this.packs = spoon.support.reflect.declaration.CtElementImpl.emptySet();
            return ((T) (this));
        }
        getFactory().getEnvironment().getModelChangeListener().onSetDeleteAll(this, spoon.reflect.path.CtRole.SUB_PACKAGE, this.packs, new java.util.HashSet<>(this.packs));
        this.packs.clear();
        for (spoon.reflect.declaration.CtPackage p : packs) {
            addPackage(p);
        }
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtPackage> T setTypes(java.util.Set<spoon.reflect.declaration.CtType<?>> types) {
        if ((types == null) || (types.isEmpty())) {
            this.types = spoon.support.reflect.declaration.CtElementImpl.emptySet();
            return ((T) (this));
        }
        getFactory().getEnvironment().getModelChangeListener().onSetDeleteAll(this, spoon.reflect.path.CtRole.CONTAINED_TYPE, this.types, new java.util.HashSet<>(this.types));
        this.types.clear();
        for (spoon.reflect.declaration.CtType<?> t : types) {
            addType(t);
        }
        return ((T) (this));
    }

    @java.lang.Override
    public spoon.reflect.reference.CtPackageReference getReference() {
        return getFactory().Package().createReference(this);
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtPackage> T addType(spoon.reflect.declaration.CtType<?> type) {
        if (type == null) {
            return ((T) (this));
        }
        if ((types) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.declaration.CtType<?>>emptySet())) {
            this.types = orderedTypeSet();
        }
        type.setParent(this);
        getFactory().getEnvironment().getModelChangeListener().onSetAdd(this, spoon.reflect.path.CtRole.CONTAINED_TYPE, this.types, type);
        types.add(type);
        return ((T) (this));
    }

    @java.lang.Override
    public void removeType(spoon.reflect.declaration.CtType<?> type) {
        if ((types) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.declaration.CtType<?>>emptySet())) {
            return;
        }
        getFactory().getEnvironment().getModelChangeListener().onSetDelete(this, spoon.reflect.path.CtRole.CONTAINED_TYPE, types, type);
        types.remove(type);
    }

    @java.lang.Override
    public java.lang.String toString() {
        return getQualifiedName();
    }

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.IS_SHADOW)
    boolean isShadow;

    @java.lang.Override
    public boolean isShadow() {
        return isShadow;
    }

    @java.lang.Override
    public <E extends spoon.reflect.declaration.CtShadowable> E setShadow(boolean isShadow) {
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.IS_SHADOW, isShadow, this.isShadow);
        this.isShadow = isShadow;
        return ((E) (this));
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtPackage clone() {
        return ((spoon.reflect.declaration.CtPackage) (super.clone()));
    }

    @java.lang.Override
    public boolean isUnnamedPackage() {
        return spoon.reflect.declaration.CtPackage.TOP_LEVEL_PACKAGE_NAME.equals(getSimpleName());
    }
}

