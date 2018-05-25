package spoon.support.visitor.java.internal;


public interface RuntimeBuilderContext {
    void addPackage(spoon.reflect.declaration.CtPackage ctPackage);

    void addType(spoon.reflect.declaration.CtType<?> aType);

    void addAnnotation(spoon.reflect.declaration.CtAnnotation<java.lang.annotation.Annotation> ctAnnotation);

    void addConstructor(spoon.reflect.declaration.CtConstructor<?> ctConstructor);

    void addMethod(spoon.reflect.declaration.CtMethod<?> ctMethod);

    void addField(spoon.reflect.declaration.CtField<?> ctField);

    void addEnumValue(spoon.reflect.declaration.CtEnumValue<?> ctEnumValue);

    void addParameter(spoon.reflect.declaration.CtParameter ctParameter);

    void addTypeReference(spoon.reflect.path.CtRole role, spoon.reflect.reference.CtTypeReference<?> ctTypeReference);

    void addFormalType(spoon.reflect.declaration.CtTypeParameter parameterRef);

    spoon.reflect.declaration.CtTypeParameter getTypeParameter(java.lang.reflect.GenericDeclaration genericDeclaration, java.lang.String string);
}

