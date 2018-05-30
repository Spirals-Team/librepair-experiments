package spoon.metamodel;


public class MMMethod {
    private final spoon.metamodel.MetamodelProperty ownerField;

    private final spoon.reflect.declaration.CtMethod<?> method;

    private final java.util.List<spoon.reflect.declaration.CtMethod<?>> ownMethods = new java.util.ArrayList<>();

    private final java.util.List<spoon.metamodel.MMMethod> superMethods = new java.util.ArrayList<>();

    private final java.lang.String signature;

    private final spoon.metamodel.MMMethodKind methodKind;

    MMMethod(spoon.metamodel.MetamodelProperty field, spoon.reflect.declaration.CtMethod<?> method) {
        this.ownerField = field;
        spoon.support.visitor.MethodTypingContext mtc = new spoon.support.visitor.MethodTypingContext().setClassTypingContext(field.getOwnerConcept().getTypeContext()).setMethod(method);
        this.method = ((spoon.reflect.declaration.CtMethod<?>) (mtc.getAdaptationScope()));
        signature = this.method.getSignature();
        methodKind = spoon.metamodel.MMMethodKind.valueOf(this.method);
    }

    public spoon.reflect.declaration.CtMethod<?> getMethod() {
        return method;
    }

    public java.lang.String getName() {
        return method.getSimpleName();
    }

    public java.lang.String getSignature() {
        return signature;
    }

    public spoon.metamodel.MMMethodKind getMethodKind() {
        return methodKind;
    }

    public spoon.reflect.declaration.CtMethod<?> getFirstOwnMethod(spoon.metamodel.MetamodelConcept targetType) {
        for (spoon.reflect.declaration.CtMethod<?> ctMethod : ownMethods) {
            if (targetType.getTypeContext().isSubtypeOf(ctMethod.getDeclaringType().getReference())) {
                return ctMethod;
            }
        }
        for (spoon.metamodel.MMMethod mmMethod : superMethods) {
            spoon.reflect.declaration.CtMethod<?> m = mmMethod.getFirstOwnMethod(targetType);
            if (m != null) {
                return m;
            }
        }
        throw new spoon.SpoonException(("No own method exists in type " + (ownerField)));
    }

    public boolean overrides(spoon.reflect.declaration.CtMethod<?> method) {
        return ownerField.getOwnerConcept().getTypeContext().isOverriding(this.method, method);
    }

    void addSuperMethod(spoon.metamodel.MMMethod mmMethod) {
        spoon.metamodel.SpoonMetaModel.addUniqueObject(superMethods, mmMethod);
    }

    public spoon.metamodel.MetamodelProperty getOwnerField() {
        return ownerField;
    }

    public spoon.metamodel.MetamodelConcept getOwnerType() {
        return getOwnerField().getOwnerConcept();
    }

    public java.util.List<spoon.reflect.declaration.CtMethod<?>> getOwnMethods() {
        return java.util.Collections.unmodifiableList(ownMethods);
    }

    void addOwnMethod(spoon.reflect.declaration.CtMethod<?> method) {
        ownMethods.add(method);
    }

    public java.util.List<spoon.metamodel.MMMethod> getSuperMethods() {
        return java.util.Collections.unmodifiableList(superMethods);
    }

    public spoon.reflect.reference.CtTypeReference<?> getReturnType() {
        return method.getType();
    }

    public spoon.reflect.reference.CtTypeReference<?> getValueType() {
        if (method.getParameters().isEmpty()) {
            return method.getType();
        }
        return method.getParameters().get(((method.getParameters().size()) - 1)).getType();
    }

    @java.lang.Override
    public java.lang.String toString() {
        return ((getOwnerType().getName()) + "#") + (getSignature());
    }
}

