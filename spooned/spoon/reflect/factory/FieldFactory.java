package spoon.reflect.factory;


public class FieldFactory extends spoon.reflect.factory.SubFactory {
    public FieldFactory(spoon.reflect.factory.Factory factory) {
        super(factory);
    }

    public <T> spoon.reflect.declaration.CtField<T> create(spoon.reflect.declaration.CtType<?> target, java.util.Set<spoon.reflect.declaration.ModifierKind> modifiers, spoon.reflect.reference.CtTypeReference<T> type, java.lang.String name) {
        spoon.reflect.declaration.CtField<T> field = factory.Core().createField();
        field.setModifiers(modifiers);
        field.setType(type);
        field.setSimpleName(name);
        if (target != null) {
            target.addField(field);
        }
        return field;
    }

    public <T> spoon.reflect.declaration.CtField<T> create(spoon.reflect.declaration.CtType<?> target, java.util.Set<spoon.reflect.declaration.ModifierKind> modifiers, spoon.reflect.reference.CtTypeReference<T> type, java.lang.String name, spoon.reflect.code.CtExpression<T> defaultExpression) {
        spoon.reflect.declaration.CtField<T> field = create(target, modifiers, type, name);
        field.setDefaultExpression(defaultExpression);
        return field;
    }

    public <T> spoon.reflect.declaration.CtField<T> create(spoon.reflect.declaration.CtType<?> target, spoon.reflect.declaration.CtField<T> source) {
        spoon.reflect.declaration.CtField<T> newField = source.clone();
        if (target != null) {
            target.addField(newField);
        }
        return newField;
    }

    public <T> spoon.reflect.reference.CtFieldReference<T> createReference(spoon.reflect.declaration.CtField<T> field) {
        final spoon.reflect.reference.CtFieldReference<T> reference = createReference(factory.Type().createReference(field.getDeclaringType()), field.getType().clone(), field.getSimpleName());
        reference.setFinal(field.hasModifier(spoon.reflect.declaration.ModifierKind.FINAL));
        reference.setStatic(field.hasModifier(spoon.reflect.declaration.ModifierKind.STATIC));
        return reference;
    }

    public <T> spoon.reflect.reference.CtFieldReference<T> createReference(spoon.reflect.reference.CtTypeReference<?> declaringType, spoon.reflect.reference.CtTypeReference<T> type, java.lang.String fieldName) {
        spoon.reflect.reference.CtFieldReference<T> fieldRef = factory.Core().createFieldReference();
        fieldRef.setSimpleName(fieldName);
        fieldRef.setDeclaringType(declaringType);
        fieldRef.setType(type);
        return fieldRef;
    }

    @java.lang.SuppressWarnings("unchecked")
    public <T> spoon.reflect.reference.CtFieldReference<T> createReference(java.lang.reflect.Field field) {
        spoon.reflect.reference.CtFieldReference<T> fieldRef = factory.Core().createFieldReference();
        fieldRef.setSimpleName(field.getName());
        fieldRef.setDeclaringType(factory.Type().createReference(field.getDeclaringClass()));
        spoon.reflect.reference.CtTypeReference<T> t = factory.Type().createReference(((java.lang.Class<T>) (field.getType())));
        fieldRef.setType(t);
        return fieldRef;
    }

    public <T> spoon.reflect.reference.CtFieldReference<T> createReference(java.lang.String signature) {
        spoon.reflect.reference.CtFieldReference<T> fieldRef = factory.Core().createFieldReference();
        java.lang.String type = signature.substring(0, signature.indexOf(" "));
        java.lang.String declaringType = signature.substring(((signature.indexOf(" ")) + 1), signature.indexOf(spoon.reflect.declaration.CtField.FIELD_SEPARATOR));
        java.lang.String fieldName = signature.substring(((signature.indexOf(spoon.reflect.declaration.CtField.FIELD_SEPARATOR)) + 1));
        fieldRef.setSimpleName(fieldName);
        fieldRef.setDeclaringType(factory.Type().createReference(declaringType));
        spoon.reflect.reference.CtTypeReference<T> typeRef = factory.Type().createReference(type);
        fieldRef.setType(typeRef);
        return fieldRef;
    }
}

