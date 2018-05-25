package spoon.test.metamodel;


public class MetamodelConcept {
    spoon.test.metamodel.MMTypeKind kind;

    java.lang.String name;

    final java.util.Map<spoon.reflect.path.CtRole, spoon.test.metamodel.MetamodelProperty> role2Property = new java.util.LinkedHashMap<>();

    private final java.util.List<spoon.test.metamodel.MetamodelConcept> superConcepts = new java.util.ArrayList<>();

    private final java.util.List<spoon.test.metamodel.MetamodelConcept> subConcepts = new java.util.ArrayList<>();

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

    spoon.test.metamodel.MetamodelProperty getOrCreateMMField(spoon.reflect.path.CtRole role) {
        return spoon.test.metamodel.SpoonMetaModel.getOrCreate(role2Property, role, () -> new spoon.test.metamodel.MetamodelProperty(role.getCamelCaseName(), role, this));
    }

    public spoon.test.metamodel.MMTypeKind getKind() {
        if ((kind) == null) {
            if (((modelClass) == null) && ((modelInterface) == null)) {
                return null;
            }else {
                if ((modelClass) == null) {
                    this.kind = spoon.test.metamodel.MMTypeKind.ABSTRACT;
                }else {
                    if (modelClass.hasModifier(spoon.reflect.declaration.ModifierKind.ABSTRACT)) {
                        this.kind = spoon.test.metamodel.MMTypeKind.ABSTRACT;
                    }else {
                        this.kind = spoon.test.metamodel.MMTypeKind.LEAF;
                    }
                }
            }
        }
        return kind;
    }

    public java.util.Map<spoon.reflect.path.CtRole, spoon.test.metamodel.MetamodelProperty> getRoleToProperty() {
        return java.util.Collections.unmodifiableMap(role2Property);
    }

    public java.util.List<spoon.test.metamodel.MetamodelConcept> getSuperConcepts() {
        return superConcepts;
    }

    void addSuperConcept(spoon.test.metamodel.MetamodelConcept superType) {
        if (superType == (this)) {
            throw new spoon.SpoonException("Cannot add supertype to itself");
        }
        if (spoon.test.metamodel.SpoonMetaModel.addUniqueObject(superConcepts, superType)) {
            superType.subConcepts.add(this);
            superType.role2Property.forEach(( role, superMMField) -> {
                spoon.test.metamodel.MetamodelProperty mmField = getOrCreateMMField(role);
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

