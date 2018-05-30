package spoon.metamodel;


public class MetamodelConcept {
    spoon.metamodel.MMTypeKind kind;

    java.lang.String name;

    final java.util.Map<spoon.reflect.path.CtRole, spoon.metamodel.MetamodelProperty> role2Property = new java.util.LinkedHashMap<>();

    private final java.util.List<spoon.metamodel.MetamodelConcept> superConcepts = new java.util.ArrayList<>();

    private final java.util.List<spoon.metamodel.MetamodelConcept> subConcepts = new java.util.ArrayList<>();

    private spoon.reflect.declaration.CtClass<?> modelClass;

    private spoon.reflect.declaration.CtInterface<?> modelInterface;

    private spoon.support.visitor.ClassTypingContext typeContext;

    final java.util.List<spoon.reflect.declaration.CtMethod<?>> otherMethods = new java.util.ArrayList<>();

    MetamodelConcept() {
        super();
    }

    public java.lang.String getName() {
        return name;
    }

    spoon.metamodel.MetamodelProperty getOrCreateMMField(spoon.reflect.path.CtRole role) {
        return spoon.metamodel.SpoonMetaModel.getOrCreate(role2Property, role, () -> new spoon.metamodel.MetamodelProperty(role.getCamelCaseName(), role, this));
    }

    public spoon.metamodel.MMTypeKind getKind() {
        if ((kind) == null) {
            if (((modelClass) == null) && ((modelInterface) == null)) {
                return null;
            }else {
                if ((modelClass) == null) {
                    this.kind = spoon.metamodel.MMTypeKind.ABSTRACT;
                }else {
                    if (modelClass.hasModifier(spoon.reflect.declaration.ModifierKind.ABSTRACT)) {
                        this.kind = spoon.metamodel.MMTypeKind.ABSTRACT;
                    }else {
                        this.kind = spoon.metamodel.MMTypeKind.LEAF;
                    }
                }
            }
        }
        return kind;
    }

    public java.util.Map<spoon.reflect.path.CtRole, spoon.metamodel.MetamodelProperty> getRoleToProperty() {
        return java.util.Collections.unmodifiableMap(role2Property);
    }

    public java.util.List<spoon.metamodel.MetamodelConcept> getSuperConcepts() {
        return superConcepts;
    }

    void addSuperConcept(spoon.metamodel.MetamodelConcept superType) {
        if (superType == (this)) {
            throw new spoon.SpoonException("Cannot add supertype to itself");
        }
        if (spoon.metamodel.SpoonMetaModel.addUniqueObject(superConcepts, superType)) {
            superType.subConcepts.add(this);
            superType.role2Property.forEach(( role, superMMField) -> {
                spoon.metamodel.MetamodelProperty mmField = getOrCreateMMField(role);
                mmField.addSuperField(superMMField);
            });
        }
    }

    public spoon.reflect.declaration.CtClass<?> getModelClass() {
        return modelClass;
    }

    void setModelClass(spoon.reflect.declaration.CtClass<?> modelClass) {
        this.modelClass = modelClass;
    }

    public spoon.reflect.declaration.CtInterface<?> getModelInterface() {
        return modelInterface;
    }

    void setModelInterface(spoon.reflect.declaration.CtInterface<?> modelInterface) {
        this.modelInterface = modelInterface;
    }

    public spoon.support.visitor.ClassTypingContext getTypeContext() {
        if ((typeContext) == null) {
            typeContext = new spoon.support.visitor.ClassTypingContext(((modelClass) != null ? modelClass : modelInterface));
        }
        return typeContext;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return getName();
    }
}

