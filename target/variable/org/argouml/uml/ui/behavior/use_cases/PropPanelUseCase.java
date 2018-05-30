package org.argouml.uml.ui.behavior.use_cases;


public class PropPanelUseCase extends org.argouml.uml.ui.foundation.core.PropPanelClassifier {
    public PropPanelUseCase() {
        super("UseCase", org.argouml.util.ConfigLoader.getTabPropsOrientation());
        addField(org.argouml.i18n.Translator.localize("UMLMenu", "label.name"), getNameTextField());
        addField(org.argouml.i18n.Translator.localize("UMLMenu", "label.stereotype"), new org.argouml.uml.ui.UMLComboBoxNavigator(this, org.argouml.i18n.Translator.localize("UMLMenu", "tooltip.nav-stereo"), getStereotypeBox()));
        addField(org.argouml.i18n.Translator.localize("UMLMenu", "label.namespace"), getNamespaceComboBox());
        org.argouml.uml.ui.PropPanelModifiers mPanel = new org.argouml.uml.ui.PropPanelModifiers(3);
        java.lang.Class mclass = ((java.lang.Class) (org.argouml.model.ModelFacade.USE_CASE));
        mPanel.add("isLeaf", mclass, "isLeaf", "setLeaf", org.argouml.i18n.Translator.localize("UMLMenu", "checkbox.final-lc"), this);
        mPanel.add("isRoot", mclass, "isRoot", "setRoot", localize("root"), this);
        addField(org.argouml.i18n.Translator.localize("UMLMenu", "label.modifiers"), mPanel);
        javax.swing.JList extensionPoints = new org.argouml.uml.ui.UMLMutableLinkedList(new org.argouml.uml.ui.behavior.use_cases.UMLUseCaseExtensionPointListModel(), null, ActionNewUseCaseExtensionPoint.SINGLETON);
        addField(org.argouml.i18n.Translator.localize("UMLMenu", "label.extension-points"), new javax.swing.JScrollPane(extensionPoints));
        addSeperator();
        addField(org.argouml.i18n.Translator.localize("UMLMenu", "label.generalizations"), getGeneralizationScroll());
        addField(org.argouml.i18n.Translator.localize("UMLMenu", "label.specializations"), getSpecializationScroll());
        javax.swing.JList extendsList = new org.argouml.uml.ui.UMLLinkedList(new org.argouml.uml.ui.behavior.use_cases.UMLUseCaseExtendListModel());
        addField(org.argouml.i18n.Translator.localize("UMLMenu", "label.extends"), new javax.swing.JScrollPane(extendsList));
        javax.swing.JList includesList = new org.argouml.uml.ui.UMLLinkedList(new org.argouml.uml.ui.behavior.use_cases.UMLUseCaseIncludeListModel());
        addField(org.argouml.i18n.Translator.localize("UMLMenu", "label.includes"), new javax.swing.JScrollPane(includesList));
        addSeperator();
        addField(org.argouml.i18n.Translator.localize("UMLMenu", "label.association-ends"), getAssociationEndScroll());
        new org.argouml.uml.ui.PropPanelButton(this, buttonPanel, _navUpIcon, org.argouml.i18n.Translator.localize("UMLMenu", "button.go-up"), "navigateNamespace", null);
        new org.argouml.uml.ui.PropPanelButton(this, buttonPanel, _useCaseIcon, org.argouml.i18n.Translator.localize("UMLMenu", "button.add-usecase"), "newUseCase", null);
        new org.argouml.uml.ui.PropPanelButton(this, buttonPanel, _extensionPointIcon, localize("Add extension point"), "newExtensionPoint", null);
        new org.argouml.uml.ui.PropPanelButton(this, buttonPanel, _deleteIcon, localize("Delete"), "removeElement", null);
    }

    public void newUseCase() {
        java.lang.Object target = getTarget();
        if (org.argouml.model.ModelFacade.isAUseCase(target)) {
            java.lang.Object ns = org.argouml.model.ModelFacade.getNamespace(target);
            if (ns != null) {
                java.lang.Object useCase = org.argouml.model.uml.behavioralelements.usecases.UseCasesFactory.getFactory().createUseCase();
                org.argouml.model.ModelFacade.addOwnedElement(ns, useCase);
                org.argouml.ui.targetmanager.TargetManager.getInstance().setTarget(useCase);
            }
        }
    }

    public void newExtensionPoint() {
        java.lang.Object target = getTarget();
        if (org.argouml.model.ModelFacade.isAUseCase(target)) {
            org.argouml.ui.targetmanager.TargetManager.getInstance().setTarget(org.argouml.model.uml.behavioralelements.usecases.UseCasesFactory.getFactory().buildExtensionPoint(target));
        }
    }
}

