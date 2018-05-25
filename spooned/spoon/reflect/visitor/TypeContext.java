package spoon.reflect.visitor;


public class TypeContext {
    spoon.reflect.declaration.CtType<?> type;

    spoon.reflect.reference.CtTypeReference<?> typeRef;

    java.util.Set<java.lang.String> memberNames;

    TypeContext(spoon.reflect.declaration.CtType<?> p_type) {
        type = p_type;
        typeRef = type.getReference();
    }

    public boolean isNameConflict(java.lang.String name) {
        if ((memberNames) == null) {
            java.util.Collection<spoon.reflect.reference.CtFieldReference<?>> allFields = type.getAllFields();
            memberNames = new java.util.HashSet<>(allFields.size());
            for (spoon.reflect.reference.CtFieldReference<?> field : allFields) {
                memberNames.add(field.getSimpleName());
            }
        }
        return memberNames.contains(name);
    }

    public java.lang.String getSimpleName() {
        return typeRef.getSimpleName();
    }

    public spoon.reflect.reference.CtPackageReference getPackage() {
        return typeRef.getPackage();
    }
}

