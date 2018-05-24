package spoon.reflect.declaration;


public interface CtType<T> extends spoon.reflect.declaration.CtFormalTypeDeclarer , spoon.reflect.declaration.CtNamedElement , spoon.reflect.declaration.CtShadowable , spoon.reflect.declaration.CtTypeInformation , spoon.reflect.declaration.CtTypeMember {
    java.lang.String INNERTTYPE_SEPARATOR = "$";

    java.lang.String NAME_UNKNOWN = "<unknown>";

    @java.lang.Override
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.NAME)
    java.lang.String getSimpleName();

    java.util.Set<spoon.reflect.reference.CtTypeReference<?>> getUsedTypes(boolean includeSamePackage);

    @spoon.support.DerivedProperty
    java.lang.Class<T> getActualClass();

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.FIELD)
    spoon.reflect.declaration.CtField<?> getField(java.lang.String name);

    @spoon.support.DerivedProperty
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.FIELD)
    java.util.List<spoon.reflect.declaration.CtField<?>> getFields();

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.NESTED_TYPE)
    <N extends spoon.reflect.declaration.CtType<?>> N getNestedType(java.lang.String name);

    @spoon.support.DerivedProperty
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.NESTED_TYPE)
    java.util.Set<spoon.reflect.declaration.CtType<?>> getNestedTypes();

    @spoon.support.DerivedProperty
    spoon.reflect.declaration.CtPackage getPackage();

    @spoon.support.DerivedProperty
    spoon.reflect.reference.CtTypeReference<T> getReference();

    boolean isTopLevel();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.FIELD)
    <F, C extends spoon.reflect.declaration.CtType<T>> C addFieldAtTop(spoon.reflect.declaration.CtField<F> field);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.FIELD)
    <F, C extends spoon.reflect.declaration.CtType<T>> C addField(spoon.reflect.declaration.CtField<F> field);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.FIELD)
    <F, C extends spoon.reflect.declaration.CtType<T>> C addField(int index, spoon.reflect.declaration.CtField<F> field);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.FIELD)
    <C extends spoon.reflect.declaration.CtType<T>> C setFields(java.util.List<spoon.reflect.declaration.CtField<?>> fields);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.FIELD)
    <F> boolean removeField(spoon.reflect.declaration.CtField<F> field);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.NESTED_TYPE)
    <N, C extends spoon.reflect.declaration.CtType<T>> C addNestedType(spoon.reflect.declaration.CtType<N> nestedType);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.NESTED_TYPE)
    <N> boolean removeNestedType(spoon.reflect.declaration.CtType<N> nestedType);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.NESTED_TYPE)
    <C extends spoon.reflect.declaration.CtType<T>> C setNestedTypes(java.util.Set<spoon.reflect.declaration.CtType<?>> nestedTypes);

    void compileAndReplaceSnippets();

    @spoon.support.DerivedProperty
    java.util.Set<spoon.reflect.declaration.CtMethod<?>> getAllMethods();

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.METHOD)
    <R> spoon.reflect.declaration.CtMethod<R> getMethod(spoon.reflect.reference.CtTypeReference<R> returnType, java.lang.String name, spoon.reflect.reference.CtTypeReference<?>... parameterTypes);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.METHOD)
    <R> spoon.reflect.declaration.CtMethod<R> getMethod(java.lang.String name, spoon.reflect.reference.CtTypeReference<?>... parameterTypes);

    @spoon.support.DerivedProperty
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.METHOD)
    java.util.Set<spoon.reflect.declaration.CtMethod<?>> getMethods();

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.METHOD)
    java.util.Set<spoon.reflect.declaration.CtMethod<?>> getMethodsAnnotatedWith(spoon.reflect.reference.CtTypeReference<?>... annotationTypes);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.METHOD)
    java.util.List<spoon.reflect.declaration.CtMethod<?>> getMethodsByName(java.lang.String name);

    boolean hasMethod(spoon.reflect.declaration.CtMethod<?> method);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.METHOD)
    <C extends spoon.reflect.declaration.CtType<T>> C setMethods(java.util.Set<spoon.reflect.declaration.CtMethod<?>> methods);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.METHOD)
    <M, C extends spoon.reflect.declaration.CtType<T>> C addMethod(spoon.reflect.declaration.CtMethod<M> method);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.METHOD)
    <M> boolean removeMethod(spoon.reflect.declaration.CtMethod<M> method);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.SUPER_TYPE)
    <C extends spoon.reflect.declaration.CtType<T>> C setSuperclass(spoon.reflect.reference.CtTypeReference<?> superClass);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.INTERFACE)
    <C extends spoon.reflect.declaration.CtType<T>> C setSuperInterfaces(java.util.Set<spoon.reflect.reference.CtTypeReference<?>> interfaces);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.INTERFACE)
    <S, C extends spoon.reflect.declaration.CtType<T>> C addSuperInterface(spoon.reflect.reference.CtTypeReference<S> interfac);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.INTERFACE)
    <S> boolean removeSuperInterface(spoon.reflect.reference.CtTypeReference<S> interfac);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.TYPE_MEMBER)
    java.util.List<spoon.reflect.declaration.CtTypeMember> getTypeMembers();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.TYPE_MEMBER)
    <C extends spoon.reflect.declaration.CtType<T>> C addTypeMember(spoon.reflect.declaration.CtTypeMember member);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.TYPE_MEMBER)
    <C extends spoon.reflect.declaration.CtType<T>> C addTypeMemberAt(int position, spoon.reflect.declaration.CtTypeMember member);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.TYPE_MEMBER)
    boolean removeTypeMember(spoon.reflect.declaration.CtTypeMember member);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.TYPE_MEMBER)
    <C extends spoon.reflect.declaration.CtType<T>> C setTypeMembers(java.util.List<spoon.reflect.declaration.CtTypeMember> members);

    @java.lang.Override
    spoon.reflect.declaration.CtType<T> clone();

    spoon.reflect.declaration.CtType<?> copyType();
}

