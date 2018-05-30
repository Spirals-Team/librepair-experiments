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


public class CtPackageExportImpl extends spoon.support.reflect.declaration.CtElementImpl implements spoon.reflect.declaration.CtPackageExport {
    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.PACKAGE_REF)
    private spoon.reflect.reference.CtPackageReference packageReference;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.MODULE_REF)
    private java.util.List<spoon.reflect.reference.CtModuleReference> targets = spoon.support.reflect.declaration.CtElementImpl.emptyList();

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.OPENED_PACKAGE)
    private boolean isOpen;

    public CtPackageExportImpl() {
        super();
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtPackageExport> T setOpenedPackage(boolean openedPackage) {
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.OPENED_PACKAGE, openedPackage, this.isOpen);
        this.isOpen = openedPackage;
        return ((T) (this));
    }

    @java.lang.Override
    public boolean isOpenedPackage() {
        return this.isOpen;
    }

    @java.lang.Override
    public spoon.reflect.reference.CtPackageReference getPackageReference() {
        return this.packageReference;
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtPackageExport> T setPackageReference(spoon.reflect.reference.CtPackageReference packageReference) {
        if (packageReference != null) {
            packageReference.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.PACKAGE_REF, packageReference, this.packageReference);
        this.packageReference = packageReference;
        return ((T) (this));
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.reference.CtModuleReference> getTargetExport() {
        return java.util.Collections.unmodifiableList(targets);
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtPackageExport> T setTargetExport(java.util.List<spoon.reflect.reference.CtModuleReference> targetExports) {
        getFactory().getEnvironment().getModelChangeListener().onListDeleteAll(this, spoon.reflect.path.CtRole.MODULE_REF, this.targets, new java.util.ArrayList<>(this.targets));
        if ((targetExports == null) || (targetExports.isEmpty())) {
            this.targets = spoon.support.reflect.declaration.CtElementImpl.emptyList();
            return ((T) (this));
        }
        if ((this.targets) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.reference.CtModuleReference>emptyList())) {
            this.targets = new java.util.ArrayList<>();
        }
        this.targets.clear();
        for (spoon.reflect.reference.CtModuleReference targetExport : targetExports) {
            this.addTargetExport(targetExport);
        }
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtPackageExport> T addTargetExport(spoon.reflect.reference.CtModuleReference targetExport) {
        if (targetExport == null) {
            return ((T) (this));
        }
        if ((this.targets) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.reference.CtModuleReference>emptyList())) {
            this.targets = new java.util.ArrayList<>();
        }
        getFactory().getEnvironment().getModelChangeListener().onListAdd(this, spoon.reflect.path.CtRole.MODULE_REF, this.targets, targetExport);
        targetExport.setParent(this);
        this.targets.add(targetExport);
        return ((T) (this));
    }

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtPackageExport(this);
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtPackageExport clone() {
        return ((spoon.reflect.declaration.CtPackageExport) (super.clone()));
    }
}

