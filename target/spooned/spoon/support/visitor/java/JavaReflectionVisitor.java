package spoon.support.visitor.java;


interface JavaReflectionVisitor {
    void visitPackage(java.lang.Package aPackage);

    <T> void visitClass(java.lang.Class<T> clazz);

    <T> void visitInterface(java.lang.Class<T> clazz);

    <T> void visitEnum(java.lang.Class<T> clazz);

    <T extends java.lang.annotation.Annotation> void visitAnnotationClass(java.lang.Class<T> clazz);

    void visitAnnotation(java.lang.annotation.Annotation annotation);

    <T> void visitConstructor(java.lang.reflect.Constructor<T> constructor);

    void visitMethod(spoon.support.visitor.java.reflect.RtMethod method);

    void visitField(java.lang.reflect.Field field);

    void visitEnumValue(java.lang.reflect.Field field);

    void visitParameter(spoon.support.visitor.java.reflect.RtParameter parameter);

    <T extends java.lang.reflect.GenericDeclaration> void visitTypeParameter(java.lang.reflect.TypeVariable<T> parameter);

    <T extends java.lang.reflect.GenericDeclaration> void visitTypeParameterReference(spoon.reflect.path.CtRole role, java.lang.reflect.TypeVariable<T> parameter);

    void visitTypeReference(spoon.reflect.path.CtRole role, java.lang.reflect.Type type);

    void visitTypeReference(spoon.reflect.path.CtRole role, java.lang.reflect.ParameterizedType type);

    void visitTypeReference(spoon.reflect.path.CtRole role, java.lang.reflect.WildcardType type);

    <T> void visitTypeReference(spoon.reflect.path.CtRole role, java.lang.Class<T> clazz);

    <T> void visitArrayReference(spoon.reflect.path.CtRole role, java.lang.reflect.Type typeArray);
}

