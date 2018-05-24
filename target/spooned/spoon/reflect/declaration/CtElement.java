package spoon.reflect.declaration;


@spoon.reflect.visitor.Root
public interface CtElement extends java.lang.Cloneable , spoon.processing.FactoryAccessor , spoon.reflect.visitor.CtVisitable , spoon.reflect.visitor.chain.CtQueryable {
    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.ANNOTATION)
    <A extends java.lang.annotation.Annotation> A getAnnotation(java.lang.Class<A> annotationType);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.ANNOTATION)
    <A extends java.lang.annotation.Annotation> spoon.reflect.declaration.CtAnnotation<A> getAnnotation(spoon.reflect.reference.CtTypeReference<A> annotationType);

    @spoon.support.DerivedProperty
    <A extends java.lang.annotation.Annotation> boolean hasAnnotation(java.lang.Class<A> annotationType);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.ANNOTATION)
    java.util.List<spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation>> getAnnotations();

    @spoon.support.DerivedProperty
    java.lang.String getDocComment();

    @spoon.support.DerivedProperty
    java.lang.String getShortRepresentation();

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.POSITION)
    spoon.reflect.cu.SourcePosition getPosition();

    void replace(spoon.reflect.declaration.CtElement element);

    <E extends spoon.reflect.declaration.CtElement> void replace(java.util.Collection<E> elements);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.ANNOTATION)
    <E extends spoon.reflect.declaration.CtElement> E addAnnotation(spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation> annotation);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.ANNOTATION)
    boolean removeAnnotation(spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation> annotation);

    <E extends spoon.reflect.declaration.CtElement> E setDocComment(java.lang.String docComment);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.POSITION)
    <E extends spoon.reflect.declaration.CtElement> E setPosition(spoon.reflect.cu.SourcePosition position);

    <E extends spoon.reflect.declaration.CtElement> java.util.List<E> getAnnotatedChildren(java.lang.Class<? extends java.lang.annotation.Annotation> annotationType);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.IS_IMPLICIT)
    boolean isImplicit();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.IS_IMPLICIT)
    <E extends spoon.reflect.declaration.CtElement> E setImplicit(boolean b);

    @spoon.support.DerivedProperty
    java.util.Set<spoon.reflect.reference.CtTypeReference<?>> getReferencedTypes();

    <E extends spoon.reflect.declaration.CtElement> java.util.List<E> getElements(spoon.reflect.visitor.Filter<E> filter);

    <E extends spoon.reflect.declaration.CtElement> E setPositions(spoon.reflect.cu.SourcePosition position);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.ANNOTATION)
    <E extends spoon.reflect.declaration.CtElement> E setAnnotations(java.util.List<spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation>> annotation);

    @spoon.support.DerivedProperty
    spoon.reflect.declaration.CtElement getParent() throws spoon.reflect.declaration.ParentNotInitializedException;

    <P extends spoon.reflect.declaration.CtElement> P getParent(java.lang.Class<P> parentType) throws spoon.reflect.declaration.ParentNotInitializedException;

    <E extends spoon.reflect.declaration.CtElement> E getParent(spoon.reflect.visitor.Filter<E> filter) throws spoon.reflect.declaration.ParentNotInitializedException;

    <E extends spoon.reflect.declaration.CtElement> E setParent(E parent);

    boolean isParentInitialized();

    boolean hasParent(spoon.reflect.declaration.CtElement candidate);

    void updateAllParentsBelow();

    spoon.reflect.path.CtRole getRoleInParent();

    void delete();

    <E extends spoon.reflect.declaration.CtElement> E putMetadata(java.lang.String key, java.lang.Object val);

    java.lang.Object getMetadata(java.lang.String key);

    java.util.Set<java.lang.String> getMetadataKeys();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.COMMENT)
    <E extends spoon.reflect.declaration.CtElement> E setComments(java.util.List<spoon.reflect.code.CtComment> comments);

    @spoon.reflect.annotations.PropertyGetter(role = spoon.reflect.path.CtRole.COMMENT)
    java.util.List<spoon.reflect.code.CtComment> getComments();

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.COMMENT)
    <E extends spoon.reflect.declaration.CtElement> E addComment(spoon.reflect.code.CtComment comment);

    @spoon.reflect.annotations.PropertySetter(role = spoon.reflect.path.CtRole.COMMENT)
    <E extends spoon.reflect.declaration.CtElement> E removeComment(spoon.reflect.code.CtComment comment);

    spoon.reflect.declaration.CtElement clone();

    <T> T getValueByRole(spoon.reflect.path.CtRole role);

    <E extends spoon.reflect.declaration.CtElement, T> E setValueByRole(spoon.reflect.path.CtRole role, T value);

    spoon.reflect.path.CtPath getPath();

    java.util.Iterator<spoon.reflect.declaration.CtElement> descendantIterator();

    java.lang.Iterable<spoon.reflect.declaration.CtElement> asIterable();
}

