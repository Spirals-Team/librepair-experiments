/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.support.reflect.declaration;


/**
 * The implementation for {@link spoon.reflect.declaration.CtPackage}.
 *
 * @author Renaud Pawlak
 */
public class CtPackageImpl extends spoon.support.reflect.declaration.CtNamedElementImpl implements spoon.reflect.declaration.CtPackage {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.SUB_PACKAGE)
    protected spoon.support.util.ModelSet<spoon.reflect.declaration.CtPackage> packs = new spoon.support.util.ModelSet<spoon.reflect.declaration.CtPackage>(spoon.support.comparator.QualifiedNameComparator.INSTANCE) {
        private static final long serialVersionUID = 1L;

        @java.lang.Override
        protected spoon.reflect.declaration.CtElement getOwner() {
            return spoon.support.reflect.declaration.CtPackageImpl.this;
        }

        @java.lang.Override
        protected spoon.reflect.path.CtRole getRole() {
            return spoon.reflect.path.CtRole.SUB_PACKAGE;
        }

        @java.lang.Override
        public boolean add(spoon.reflect.declaration.CtPackage pack) {
            if (pack == null) {
                return false;
            }
            // they are the same
            if (spoon.support.reflect.declaration.CtPackageImpl.this.getQualifiedName().equals(pack.getQualifiedName())) {
                addAllTypes(pack, spoon.support.reflect.declaration.CtPackageImpl.this);
                addAllPackages(pack, spoon.support.reflect.declaration.CtPackageImpl.this);
                return false;
            }
            // it already exists
            for (spoon.reflect.declaration.CtPackage p1 : packs) {
                if (p1.getQualifiedName().equals(pack.getQualifiedName())) {
                    addAllTypes(pack, p1);
                    addAllPackages(pack, p1);
                    return false;
                }
            }
            return super.add(pack);
        }
    };

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.CONTAINED_TYPE)
    private spoon.support.util.ModelSet<spoon.reflect.declaration.CtType<?>> types = new spoon.support.util.ModelSet<spoon.reflect.declaration.CtType<?>>(spoon.support.comparator.QualifiedNameComparator.INSTANCE) {
        private static final long serialVersionUID = 1L;

        @java.lang.Override
        protected spoon.reflect.declaration.CtElement getOwner() {
            return spoon.support.reflect.declaration.CtPackageImpl.this;
        }

        @java.lang.Override
        protected spoon.reflect.path.CtRole getRole() {
            return spoon.reflect.path.CtRole.CONTAINED_TYPE;
        }
    };

    public CtPackageImpl() {
        super();
    }

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor v) {
        v.visitCtPackage(this);
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtPackage> T addPackage(spoon.reflect.declaration.CtPackage pack) {
        this.packs.add(pack);
        return ((T) (this));
    }

    /**
     * add all types of "from" in "to"
     */
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

    /**
     * add all packages of "from" in "to"
     */
    private void addAllPackages(spoon.reflect.declaration.CtPackage from, spoon.reflect.declaration.CtPackage to) {
        for (spoon.reflect.declaration.CtPackage p : from.getPackages()) {
            to.addPackage(p);
        }
    }

    @java.lang.Override
    public boolean removePackage(spoon.reflect.declaration.CtPackage pack) {
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
        this.packs.set(packs);
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtPackage> T setTypes(java.util.Set<spoon.reflect.declaration.CtType<?>> types) {
        this.types.set(types);
        return ((T) (this));
    }

    @java.lang.Override
    public spoon.reflect.reference.CtPackageReference getReference() {
        return getFactory().Package().createReference(this);
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtPackage> T addType(spoon.reflect.declaration.CtType<?> type) {
        types.add(type);
        return ((T) (this));
    }

    @java.lang.Override
    public void removeType(spoon.reflect.declaration.CtType<?> type) {
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

