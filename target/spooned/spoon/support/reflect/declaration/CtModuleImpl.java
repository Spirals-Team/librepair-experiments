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


public class CtModuleImpl extends spoon.support.reflect.declaration.CtNamedElementImpl implements spoon.reflect.declaration.CtModule {
    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.MODIFIER)
    private boolean openModule;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.MODULE_DIRECTIVE)
    private java.util.List<spoon.reflect.declaration.CtModuleDirective> moduleDirectives = spoon.support.reflect.declaration.CtElementImpl.emptyList();

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.SUB_PACKAGE)
    private spoon.reflect.declaration.CtPackage rootPackage;

    public CtModuleImpl() {
        super();
    }

    @java.lang.Override
    public boolean isUnnamedModule() {
        return spoon.reflect.declaration.CtModule.TOP_LEVEL_MODULE_NAME.equals(this.getSimpleName());
    }

    @java.lang.Override
    public boolean isOpenModule() {
        return this.openModule;
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtModule> T setModuleDirectives(java.util.List<spoon.reflect.declaration.CtModuleDirective> moduleDirectives) {
        getFactory().getEnvironment().getModelChangeListener().onListDeleteAll(this, spoon.reflect.path.CtRole.MODULE_DIRECTIVE, this.moduleDirectives, new java.util.ArrayList<>(this.moduleDirectives));
        if ((moduleDirectives == null) || (moduleDirectives.isEmpty())) {
            this.moduleDirectives = spoon.support.reflect.declaration.CtElementImpl.emptyList();
            return ((T) (this));
        }
        if ((this.moduleDirectives) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.declaration.CtModuleDirective>emptyList())) {
            this.moduleDirectives = new spoon.support.util.SortedList<>(new spoon.support.comparator.CtLineElementComparator());
        }
        this.moduleDirectives.clear();
        for (spoon.reflect.declaration.CtModuleDirective moduleDirective : moduleDirectives) {
            this.addModuleDirective(moduleDirective);
        }
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtModule> T addModuleDirective(spoon.reflect.declaration.CtModuleDirective moduleDirective) {
        if (moduleDirective == null) {
            return ((T) (this));
        }
        if ((this.moduleDirectives) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.declaration.CtModuleDirective>emptyList())) {
            this.moduleDirectives = new spoon.support.util.SortedList<>(new spoon.support.comparator.CtLineElementComparator());
        }
        if (!(this.moduleDirectives.contains(moduleDirective))) {
            moduleDirective.setParent(this);
            spoon.reflect.path.CtRole role = this.computeRoleFromModuleDirectory(moduleDirective);
            getFactory().getEnvironment().getModelChangeListener().onListAdd(this, role, this.moduleDirectives, moduleDirective);
            this.moduleDirectives.add(moduleDirective);
        }
        return ((T) (this));
    }

    private spoon.reflect.path.CtRole computeRoleFromModuleDirectory(spoon.reflect.declaration.CtModuleDirective moduleDirective) {
        spoon.reflect.path.CtRole role;
        if (moduleDirective instanceof spoon.reflect.declaration.CtModuleRequirement) {
            role = spoon.reflect.path.CtRole.REQUIRED_MODULE;
        }else
            if (moduleDirective instanceof spoon.reflect.declaration.CtUsedService) {
                role = spoon.reflect.path.CtRole.SERVICE_TYPE;
            }else
                if (moduleDirective instanceof spoon.reflect.declaration.CtProvidedService) {
                    role = spoon.reflect.path.CtRole.PROVIDED_SERVICE;
                }else
                    if (moduleDirective instanceof spoon.reflect.declaration.CtPackageExport) {
                        spoon.reflect.declaration.CtPackageExport packageExport = ((spoon.reflect.declaration.CtPackageExport) (moduleDirective));
                        if (packageExport.isOpenedPackage()) {
                            role = spoon.reflect.path.CtRole.OPENED_PACKAGE;
                        }else {
                            role = spoon.reflect.path.CtRole.EXPORTED_PACKAGE;
                        }
                    }else {
                        role = spoon.reflect.path.CtRole.MODULE_DIRECTIVE;
                    }



        return role;
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtModule> T addModuleDirectiveAt(int position, spoon.reflect.declaration.CtModuleDirective moduleDirective) {
        if (moduleDirective == null) {
            return ((T) (this));
        }
        if ((this.moduleDirectives) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.declaration.CtModuleDirective>emptyList())) {
            this.moduleDirectives = new spoon.support.util.SortedList<>(new spoon.support.comparator.CtLineElementComparator());
        }
        if (!(this.moduleDirectives.contains(moduleDirective))) {
            moduleDirective.setParent(this);
            spoon.reflect.path.CtRole role = this.computeRoleFromModuleDirectory(moduleDirective);
            getFactory().getEnvironment().getModelChangeListener().onListAdd(this, role, this.moduleDirectives, position, moduleDirective);
            this.moduleDirectives.add(position, moduleDirective);
        }
        return ((T) (this));
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.declaration.CtModuleDirective> getModuleDirectives() {
        return java.util.Collections.unmodifiableList(this.moduleDirectives);
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtModule> T removeModuleDirective(spoon.reflect.declaration.CtModuleDirective moduleDirective) {
        if ((moduleDirective == null) || ((this.moduleDirectives.size()) == 0)) {
            return ((T) (this));
        }
        if (this.moduleDirectives.contains(moduleDirective)) {
            getFactory().getEnvironment().getModelChangeListener().onListDelete(this, this.computeRoleFromModuleDirectory(moduleDirective), this.moduleDirectives, this.moduleDirectives.indexOf(moduleDirective), moduleDirective);
            if ((this.moduleDirectives.size()) == 1) {
                this.moduleDirectives = spoon.support.reflect.declaration.CtElementImpl.emptyList();
            }else {
                this.moduleDirectives.remove(moduleDirective);
            }
        }
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtModule> T setIsOpenModule(boolean openModule) {
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.MODIFIER, openModule, this.openModule);
        this.openModule = openModule;
        return ((T) (this));
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.declaration.CtUsedService> getUsedServices() {
        if (this.moduleDirectives.isEmpty()) {
            return spoon.support.reflect.declaration.CtElementImpl.emptyList();
        }else {
            java.util.List<spoon.reflect.declaration.CtUsedService> usedServices = new java.util.ArrayList<>();
            for (spoon.reflect.declaration.CtModuleDirective moduleDirective : this.moduleDirectives) {
                if (moduleDirective instanceof spoon.reflect.declaration.CtUsedService) {
                    usedServices.add(((spoon.reflect.declaration.CtUsedService) (moduleDirective)));
                }
            }
            return java.util.Collections.unmodifiableList(usedServices);
        }
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtModule> T setUsedServices(java.util.List<spoon.reflect.declaration.CtUsedService> consumedServices) {
        if ((consumedServices == null) || (consumedServices.isEmpty())) {
            return ((T) (this));
        }
        java.util.List<spoon.reflect.declaration.CtUsedService> usedServices = getUsedServices();
        getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.SERVICE_TYPE, this.moduleDirectives, new java.util.ArrayList<>(usedServices));
        this.moduleDirectives.removeAll(usedServices);
        for (spoon.reflect.declaration.CtUsedService consumedService : consumedServices) {
            this.addModuleDirective(consumedService);
        }
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtModule> T addUsedService(spoon.reflect.declaration.CtUsedService consumedService) {
        if (consumedService == null) {
            return ((T) (this));
        }
        this.addModuleDirective(consumedService);
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtModule> T removeUsedService(spoon.reflect.declaration.CtUsedService usedService) {
        if (usedService == null) {
            return ((T) (this));
        }
        return this.removeModuleDirective(usedService);
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.declaration.CtPackageExport> getExportedPackages() {
        if (this.moduleDirectives.isEmpty()) {
            return spoon.support.reflect.declaration.CtElementImpl.emptyList();
        }else {
            java.util.List<spoon.reflect.declaration.CtPackageExport> exportedPackages = new java.util.ArrayList<>();
            for (spoon.reflect.declaration.CtModuleDirective moduleDirective : this.moduleDirectives) {
                if (moduleDirective instanceof spoon.reflect.declaration.CtPackageExport) {
                    spoon.reflect.declaration.CtPackageExport exportedPackage = ((spoon.reflect.declaration.CtPackageExport) (moduleDirective));
                    if (!(exportedPackage.isOpenedPackage())) {
                        exportedPackages.add(exportedPackage);
                    }
                }
            }
            return java.util.Collections.unmodifiableList(exportedPackages);
        }
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtModule> T setExportedPackages(java.util.List<spoon.reflect.declaration.CtPackageExport> exportedPackages) {
        if ((exportedPackages == null) || (exportedPackages.isEmpty())) {
            return ((T) (this));
        }
        java.util.List<spoon.reflect.declaration.CtPackageExport> oldExportedPackages = getExportedPackages();
        getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.EXPORTED_PACKAGE, this.moduleDirectives, new java.util.ArrayList<>(oldExportedPackages));
        this.moduleDirectives.removeAll(oldExportedPackages);
        for (spoon.reflect.declaration.CtPackageExport exportedPackage : exportedPackages) {
            exportedPackage.setOpenedPackage(false);
            this.addModuleDirective(exportedPackage);
        }
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtModule> T addExportedPackage(spoon.reflect.declaration.CtPackageExport exportedPackage) {
        if (exportedPackage == null) {
            return ((T) (this));
        }
        exportedPackage.setOpenedPackage(false);
        this.addModuleDirective(exportedPackage);
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtModule> T removeExportedPackage(spoon.reflect.declaration.CtPackageExport exportedPackage) {
        if (exportedPackage == null) {
            return ((T) (this));
        }
        return this.removeModuleDirective(exportedPackage);
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.declaration.CtPackageExport> getOpenedPackages() {
        if (this.moduleDirectives.isEmpty()) {
            return spoon.support.reflect.declaration.CtElementImpl.emptyList();
        }else {
            java.util.List<spoon.reflect.declaration.CtPackageExport> openedPackages = new java.util.ArrayList<>();
            for (spoon.reflect.declaration.CtModuleDirective moduleDirective : this.moduleDirectives) {
                if (moduleDirective instanceof spoon.reflect.declaration.CtPackageExport) {
                    spoon.reflect.declaration.CtPackageExport exportedPackage = ((spoon.reflect.declaration.CtPackageExport) (moduleDirective));
                    if (exportedPackage.isOpenedPackage()) {
                        openedPackages.add(exportedPackage);
                    }
                }
            }
            return java.util.Collections.unmodifiableList(openedPackages);
        }
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtModule> T setOpenedPackages(java.util.List<spoon.reflect.declaration.CtPackageExport> openedPackages) {
        if ((openedPackages == null) || (openedPackages.isEmpty())) {
            return ((T) (this));
        }
        java.util.List<spoon.reflect.declaration.CtPackageExport> oldOpenedPackages = getOpenedPackages();
        getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.OPENED_PACKAGE, this.moduleDirectives, new java.util.ArrayList<>(oldOpenedPackages));
        this.moduleDirectives.removeAll(oldOpenedPackages);
        for (spoon.reflect.declaration.CtPackageExport exportedPackage : openedPackages) {
            exportedPackage.setOpenedPackage(true);
            this.addModuleDirective(exportedPackage);
        }
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtModule> T addOpenedPackage(spoon.reflect.declaration.CtPackageExport openedPackage) {
        if (openedPackage == null) {
            return ((T) (this));
        }
        openedPackage.setOpenedPackage(true);
        this.addModuleDirective(openedPackage);
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtModule> T removeOpenedPackage(spoon.reflect.declaration.CtPackageExport openedPackage) {
        if (openedPackage == null) {
            return ((T) (this));
        }
        return this.removeModuleDirective(openedPackage);
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.declaration.CtModuleRequirement> getRequiredModules() {
        if (this.moduleDirectives.isEmpty()) {
            return spoon.support.reflect.declaration.CtElementImpl.emptyList();
        }else {
            java.util.List<spoon.reflect.declaration.CtModuleRequirement> moduleRequirements = new java.util.ArrayList<>();
            for (spoon.reflect.declaration.CtModuleDirective moduleDirective : this.moduleDirectives) {
                if (moduleDirective instanceof spoon.reflect.declaration.CtModuleRequirement) {
                    moduleRequirements.add(((spoon.reflect.declaration.CtModuleRequirement) (moduleDirective)));
                }
            }
            return java.util.Collections.unmodifiableList(moduleRequirements);
        }
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtModule> T setRequiredModules(java.util.List<spoon.reflect.declaration.CtModuleRequirement> requiredModules) {
        if ((requiredModules == null) || (requiredModules.isEmpty())) {
            return ((T) (this));
        }
        java.util.List<spoon.reflect.declaration.CtModuleRequirement> oldRequiredModules = getRequiredModules();
        getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.REQUIRED_MODULE, this.moduleDirectives, new java.util.ArrayList<>(oldRequiredModules));
        this.moduleDirectives.removeAll(oldRequiredModules);
        for (spoon.reflect.declaration.CtModuleRequirement moduleRequirement : requiredModules) {
            this.addModuleDirective(moduleRequirement);
        }
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtModule> T addRequiredModule(spoon.reflect.declaration.CtModuleRequirement requiredModule) {
        if (requiredModule == null) {
            return ((T) (this));
        }
        this.addModuleDirective(requiredModule);
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtModule> T removeRequiredModule(spoon.reflect.declaration.CtModuleRequirement requiredModule) {
        if (requiredModule == null) {
            return ((T) (this));
        }
        return this.removeModuleDirective(requiredModule);
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.declaration.CtProvidedService> getProvidedServices() {
        if (this.moduleDirectives.isEmpty()) {
            return spoon.support.reflect.declaration.CtElementImpl.emptyList();
        }else {
            java.util.List<spoon.reflect.declaration.CtProvidedService> providedServices = new java.util.ArrayList<>();
            for (spoon.reflect.declaration.CtModuleDirective moduleDirective : this.moduleDirectives) {
                if (moduleDirective instanceof spoon.reflect.declaration.CtProvidedService) {
                    providedServices.add(((spoon.reflect.declaration.CtProvidedService) (moduleDirective)));
                }
            }
            return java.util.Collections.unmodifiableList(providedServices);
        }
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtModule> T setProvidedServices(java.util.List<spoon.reflect.declaration.CtProvidedService> providedServices) {
        if ((providedServices == null) || (providedServices.isEmpty())) {
            return ((T) (this));
        }
        java.util.List<spoon.reflect.declaration.CtProvidedService> oldProvidedServices = getProvidedServices();
        getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.PROVIDED_SERVICE, this.moduleDirectives, new java.util.ArrayList<>(oldProvidedServices));
        this.moduleDirectives.removeAll(oldProvidedServices);
        for (spoon.reflect.declaration.CtProvidedService providedService : providedServices) {
            this.addModuleDirective(providedService);
        }
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtModule> T addProvidedService(spoon.reflect.declaration.CtProvidedService providedService) {
        if (providedService == null) {
            return ((T) (this));
        }
        this.addModuleDirective(providedService);
        return ((T) (this));
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtModule> T removeProvidedService(spoon.reflect.declaration.CtProvidedService providedService) {
        if (providedService == null) {
            return ((T) (this));
        }
        return this.removeModuleDirective(providedService);
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtPackage getRootPackage() {
        return this.rootPackage;
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtModule> T setRootPackage(spoon.reflect.declaration.CtPackage rootPackage) {
        if (rootPackage != null) {
            rootPackage.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.SUB_PACKAGE, rootPackage, this.rootPackage);
        this.rootPackage = rootPackage;
        return ((T) (this));
    }

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtModule(this);
    }

    @java.lang.Override
    public spoon.reflect.reference.CtModuleReference getReference() {
        return this.getFactory().Module().createReference(this);
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtModule clone() {
        return ((spoon.reflect.declaration.CtModule) (super.clone()));
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public <T extends spoon.reflect.declaration.CtElement> T setParent(T parent) {
        return ((T) (this));
    }

    @java.lang.Override
    @spoon.support.DerivedProperty
    public spoon.reflect.declaration.CtElement getParent() {
        return getFactory().getModel().getUnnamedModule();
    }
}

