/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.support.reflect.declaration;


/**
 * Contains the default implementation of most CtElement methods.
 */
public abstract class CtElementImpl implements java.io.Serializable , spoon.reflect.declaration.CtElement {
    private static final long serialVersionUID = 1L;

    protected static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(spoon.support.reflect.declaration.CtElementImpl.class);

    public static final java.lang.String ERROR_MESSAGE_TO_STRING = "Error in printing the node. One parent isn't initialized!";

    private static final spoon.reflect.factory.Factory DEFAULT_FACTORY = new spoon.reflect.factory.FactoryImpl(new spoon.support.DefaultCoreFactory(), new spoon.support.StandardEnvironment());

    public static <T> java.util.List<T> emptyList() {
        return spoon.support.util.EmptyClearableList.instance();
    }

    public static <T> java.util.Set<T> emptySet() {
        return spoon.support.util.EmptyClearableSet.instance();
    }

    public static <T> java.util.List<T> unmodifiableList(java.util.List<T> list) {
        return list.isEmpty() ? java.util.Collections.<T>emptyList() : java.util.Collections.unmodifiableList(list);
    }

    spoon.reflect.factory.Factory factory;

    protected spoon.reflect.declaration.CtElement parent;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.ANNOTATION)
    java.util.List<spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation>> annotations = spoon.support.reflect.declaration.CtElementImpl.emptyList();

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.COMMENT)
    private java.util.List<spoon.reflect.code.CtComment> comments = spoon.support.reflect.declaration.CtElementImpl.emptyList();

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.POSITION)
    spoon.reflect.cu.SourcePosition position = spoon.reflect.cu.SourcePosition.NOPOSITION;

    java.util.Map<java.lang.String, java.lang.Object> metadata;

    public CtElementImpl() {
        super();
    }

    @java.lang.Override
    public java.lang.String getShortRepresentation() {
        return super.toString();
    }

    @java.lang.Override
    public boolean equals(java.lang.Object o) {
        if ((this) == o) {
            return true;
        }
        boolean ret = spoon.support.visitor.equals.EqualsVisitor.equals(this, ((spoon.reflect.declaration.CtElement) (o)));
        // neat online testing of core Java contract
        if ((ret && (!(factory.getEnvironment().checksAreSkipped()))) && ((this.hashCode()) != (o.hashCode()))) {
            throw new java.lang.IllegalStateException((((("violation of equal/hashcode contract between \n" + (this.toString())) + "\nand\n") + (o.toString())) + "\n"));
        }
        return ret;
    }

    @java.lang.SuppressWarnings("unchecked")
    @java.lang.Override
    public <A extends java.lang.annotation.Annotation> A getAnnotation(java.lang.Class<A> annotationType) {
        spoon.reflect.declaration.CtType annot = getFactory().Annotation().get(annotationType);
        for (spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation> a : getAnnotations()) {
            if (a.getAnnotationType().getQualifiedName().equals(annot.getQualifiedName())) {
                return ((spoon.reflect.declaration.CtAnnotation<A>) (a)).getActualAnnotation();// warning, here we do heavy and costly work with proxy

            }
        }
        return null;
    }

    @java.lang.Override
    public <A extends java.lang.annotation.Annotation> boolean hasAnnotation(java.lang.Class<A> annotationType) {
        spoon.reflect.declaration.CtType annot = getFactory().Annotation().get(annotationType);
        for (spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation> a : getAnnotations()) {
            if (a.getAnnotationType().getQualifiedName().equals(annot.getQualifiedName())) {
                return true;
            }
        }
        return false;
    }

    @java.lang.SuppressWarnings("unchecked")
    public <A extends java.lang.annotation.Annotation> spoon.reflect.declaration.CtAnnotation<A> getAnnotation(spoon.reflect.reference.CtTypeReference<A> annotationType) {
        for (spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation> a : getAnnotations()) {
            if (a.getAnnotationType().equals(annotationType)) {
                return ((spoon.reflect.declaration.CtAnnotation<A>) (a));
            }
        }
        return null;
    }

    public java.util.List<spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation>> getAnnotations() {
        if ((this) instanceof spoon.reflect.declaration.CtShadowable) {
            spoon.reflect.declaration.CtShadowable shadowable = ((spoon.reflect.declaration.CtShadowable) (this));
            if (shadowable.isShadow()) {
                spoon.Launcher.LOGGER.debug(("Some annotations might be unreachable from the shadow element: " + (this.getShortRepresentation())));
            }
        }
        return spoon.support.reflect.declaration.CtElementImpl.unmodifiableList(annotations);
    }

    public java.lang.String getDocComment() {
        for (spoon.reflect.code.CtComment ctComment : comments) {
            if ((ctComment.getCommentType()) == (spoon.reflect.code.CtComment.CommentType.JAVADOC)) {
                java.lang.StringBuffer result = new java.lang.StringBuffer();
                result.append(((ctComment.getContent()) + (java.lang.System.lineSeparator())));
                for (spoon.reflect.code.CtJavaDocTag tag : ((spoon.reflect.code.CtJavaDoc) (ctComment)).getTags()) {
                    result.append(tag.toString());// the tag already contains a new line

                }
                return result.toString();
            }
        }
        return "";
    }

    public spoon.reflect.cu.SourcePosition getPosition() {
        if ((position) != null) {
            return position;
        }
        return spoon.reflect.cu.SourcePosition.NOPOSITION;
    }

    @java.lang.Override
    public int hashCode() {
        spoon.support.visitor.HashcodeVisitor pr = new spoon.support.visitor.HashcodeVisitor();
        pr.scan(this);
        return pr.getHasCode();
    }

    public <E extends spoon.reflect.declaration.CtElement> E setAnnotations(java.util.List<spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation>> annotations) {
        if ((annotations == null) || (annotations.isEmpty())) {
            this.annotations = spoon.support.reflect.declaration.CtElementImpl.emptyList();
            return ((E) (this));
        }
        getFactory().getEnvironment().getModelChangeListener().onListDeleteAll(this, spoon.reflect.path.CtRole.ANNOTATION, this.annotations, new java.util.ArrayList<>(this.annotations));
        this.annotations.clear();
        for (spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation> annot : annotations) {
            addAnnotation(annot);
        }
        return ((E) (this));
    }

    @java.lang.Override
    public void delete() {
        // delete is implemented as replace by no element (empty list of elements)
        replace(java.util.Collections.<spoon.reflect.declaration.CtElement>emptyList());
    }

    public <E extends spoon.reflect.declaration.CtElement> E addAnnotation(spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation> annotation) {
        if (annotation == null) {
            return ((E) (this));
        }
        if ((this.annotations) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation>>emptyList())) {
            this.annotations = new java.util.ArrayList<>(spoon.reflect.ModelElementContainerDefaultCapacities.ANNOTATIONS_CONTAINER_DEFAULT_CAPACITY);
        }
        annotation.setParent(this);
        getFactory().getEnvironment().getModelChangeListener().onListAdd(this, spoon.reflect.path.CtRole.ANNOTATION, this.annotations, annotation);
        this.annotations.add(annotation);
        return ((E) (this));
    }

    public boolean removeAnnotation(spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation> annotation) {
        if ((this.annotations) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation>>emptyList())) {
            return false;
        }
        getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.ANNOTATION, annotations, annotations.indexOf(annotation), annotation);
        return this.annotations.remove(annotation);
    }

    public <E extends spoon.reflect.declaration.CtElement> E setDocComment(java.lang.String docComment) {
        for (spoon.reflect.code.CtComment ctComment : comments) {
            if ((ctComment.getCommentType()) == (spoon.reflect.code.CtComment.CommentType.JAVADOC)) {
                ctComment.setContent(docComment);
                return ((E) (this));
            }
        }
        this.addComment(factory.Code().createComment(docComment, spoon.reflect.code.CtComment.CommentType.JAVADOC));
        return ((E) (this));
    }

    public <E extends spoon.reflect.declaration.CtElement> E setPosition(spoon.reflect.cu.SourcePosition position) {
        if (position == null) {
            position = spoon.reflect.cu.SourcePosition.NOPOSITION;
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.POSITION, position, this.position);
        this.position = position;
        return ((E) (this));
    }

    public <E extends spoon.reflect.declaration.CtElement> E setPositions(final spoon.reflect.cu.SourcePosition position) {
        accept(new spoon.reflect.visitor.CtScanner() {
            @java.lang.Override
            public void enter(spoon.reflect.declaration.CtElement e) {
                e.setPosition(position);
            }
        });
        return ((E) (this));
    }

    @java.lang.Override
    public java.lang.String toString() {
        spoon.reflect.visitor.DefaultJavaPrettyPrinter printer = new spoon.reflect.visitor.DefaultJavaPrettyPrinter(getFactory().getEnvironment());
        java.lang.String errorMessage = "";
        try {
            // we do not want to compute imports of a CtImport as it may change the print of a reference
            if (!((this) instanceof spoon.reflect.declaration.CtImport)) {
                printer.computeImports(this);
            }
            printer.scan(this);
        } catch (spoon.reflect.declaration.ParentNotInitializedException ignore) {
            spoon.support.reflect.declaration.CtElementImpl.LOGGER.error(spoon.support.reflect.declaration.CtElementImpl.ERROR_MESSAGE_TO_STRING, ignore);
            errorMessage = spoon.support.reflect.declaration.CtElementImpl.ERROR_MESSAGE_TO_STRING;
        }
        // in line-preservation mode, newlines are added at the beginning to matches the lines
        // removing them from the toString() representation
        return (printer.toString().replaceFirst("^\\s+", "")) + errorMessage;
    }

    @java.lang.SuppressWarnings("unchecked")
    public <E extends spoon.reflect.declaration.CtElement> java.util.List<E> getAnnotatedChildren(java.lang.Class<? extends java.lang.annotation.Annotation> annotationType) {
        return ((java.util.List<E>) (spoon.reflect.visitor.Query.getElements(this, new spoon.reflect.visitor.filter.AnnotationFilter<>(spoon.reflect.declaration.CtElement.class, annotationType))));
    }

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.IS_IMPLICIT)
    boolean implicit = false;

    public boolean isImplicit() {
        return implicit;
    }

    public <E extends spoon.reflect.declaration.CtElement> E setImplicit(boolean implicit) {
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.IS_IMPLICIT, implicit, this.implicit);
        this.implicit = implicit;
        return ((E) (this));
    }

    @spoon.support.DerivedProperty
    public java.util.Set<spoon.reflect.reference.CtTypeReference<?>> getReferencedTypes() {
        spoon.support.visitor.TypeReferenceScanner s = new spoon.support.visitor.TypeReferenceScanner();
        s.scan(this);
        return s.getReferences();
    }

    @java.lang.SuppressWarnings({ "unchecked", "rawtypes" })
    public <E extends spoon.reflect.declaration.CtElement> java.util.List<E> getElements(spoon.reflect.visitor.Filter<E> filter) {
        return filterChildren(filter).list();
    }

    @java.lang.Override
    public <I> spoon.reflect.visitor.chain.CtQuery map(spoon.reflect.visitor.chain.CtConsumableFunction<I> queryStep) {
        return factory.Query().createQuery(this).map(queryStep);
    }

    @java.lang.Override
    public <I, R> spoon.reflect.visitor.chain.CtQuery map(spoon.reflect.visitor.chain.CtFunction<I, R> function) {
        return factory.Query().createQuery(this).map(function);
    }

    @java.lang.Override
    public <P extends spoon.reflect.declaration.CtElement> spoon.reflect.visitor.chain.CtQuery filterChildren(spoon.reflect.visitor.Filter<P> predicate) {
        return factory.Query().createQuery(this).filterChildren(predicate);
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtElement getParent() throws spoon.reflect.declaration.ParentNotInitializedException {
        if ((parent) == null) {
            java.lang.String exceptionMsg = "";
            if ((this) instanceof spoon.reflect.reference.CtReference) {
                exceptionMsg = ((("parent not initialized for " + (((spoon.reflect.reference.CtReference) (this)).getSimpleName())) + "(") + (this.getClass())) + ")";
            }else {
                spoon.reflect.cu.SourcePosition pos = getPosition();
                if ((this) instanceof spoon.reflect.declaration.CtNamedElement) {
                    exceptionMsg = (((("parent not initialized for " + (((spoon.reflect.declaration.CtNamedElement) (this)).getSimpleName())) + "(") + (this.getClass())) + ")") + (pos != null ? " " + pos : " (?)");
                }else {
                    exceptionMsg = ("parent not initialized for " + (this.getClass())) + (pos != null ? " " + pos : " (?)");
                }
            }
            throw new spoon.reflect.declaration.ParentNotInitializedException(exceptionMsg);
        }
        return parent;
    }

    @java.lang.Override
    public <E extends spoon.reflect.declaration.CtElement> E setParent(E parent) {
        this.parent = parent;
        return ((E) (this));
    }

    @java.lang.Override
    public boolean isParentInitialized() {
        return (parent) != null;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public <P extends spoon.reflect.declaration.CtElement> P getParent(java.lang.Class<P> parentType) throws spoon.reflect.declaration.ParentNotInitializedException {
        if ((parent) == null) {
            return null;
        }
        if (parentType.isAssignableFrom(getParent().getClass())) {
            return ((P) (getParent()));
        }
        return getParent().getParent(parentType);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public <E extends spoon.reflect.declaration.CtElement> E getParent(spoon.reflect.visitor.Filter<E> filter) throws spoon.reflect.declaration.ParentNotInitializedException {
        E current = ((E) (getParent()));
        while (true) {
            try {
                while ((current != null) && (!(filter.matches(current)))) {
                    current = ((E) (current.getParent()));
                } 
                break;
            } catch (java.lang.ClassCastException e) {
                // expected, some elements are not of type
                current = ((E) (current.getParent()));
            }
        } 
        if ((current != null) && (filter.matches(current))) {
            return current;
        }
        return null;
    }

    @java.lang.Override
    public boolean hasParent(spoon.reflect.declaration.CtElement candidate) {
        try {
            return ((this) != (getFactory().getModel().getUnnamedModule())) && (((getParent()) == candidate) || (getParent().hasParent(candidate)));
        } catch (spoon.reflect.declaration.ParentNotInitializedException e) {
            return false;
        }
    }

    @java.lang.Override
    public spoon.reflect.path.CtRole getRoleInParent() {
        if (isParentInitialized()) {
            spoon.reflect.visitor.EarlyTerminatingScanner<spoon.reflect.path.CtRole> ets = new spoon.reflect.visitor.EarlyTerminatingScanner<spoon.reflect.path.CtRole>() {
                @java.lang.Override
                public void scan(spoon.reflect.path.CtRole role, spoon.reflect.declaration.CtElement element) {
                    if (element == (spoon.support.reflect.declaration.CtElementImpl.this)) {
                        setResult(role);
                        terminate();
                    }
                    // do not call super.scan, because we do not want scan children
                }
            };
            getParent().accept(ets);
            return ets.getResult();
        }
        return null;
    }

    @java.lang.Override
    public void updateAllParentsBelow() {
        new spoon.reflect.visitor.ModelConsistencyChecker(getFactory().getEnvironment(), true, true).scan(this);
    }

    @java.lang.Override
    public spoon.reflect.factory.Factory getFactory() {
        if ((this.factory) == null) {
            return spoon.support.reflect.declaration.CtElementImpl.DEFAULT_FACTORY;
        }
        return factory;
    }

    @java.lang.Override
    public void setFactory(spoon.reflect.factory.Factory factory) {
        this.factory = factory;
        spoon.support.reflect.declaration.CtElementImpl.LOGGER.setLevel(factory.getEnvironment().getLevel());
    }

    @java.lang.Override
    public void replace(spoon.reflect.declaration.CtElement element) {
        spoon.support.visitor.replace.ReplacementVisitor.replace(this, element);
    }

    @java.lang.Override
    public <E extends spoon.reflect.declaration.CtElement> void replace(java.util.Collection<E> elements) {
        spoon.support.visitor.replace.ReplacementVisitor.replace(this, elements);
    }

    @java.lang.Override
    public <E extends spoon.reflect.declaration.CtElement> E putMetadata(java.lang.String key, java.lang.Object val) {
        if ((metadata) == null) {
            metadata = new java.util.HashMap<>();
        }
        metadata.put(key, val);
        return ((E) (this));
    }

    @java.lang.Override
    public java.lang.Object getMetadata(java.lang.String key) {
        if ((metadata) == null) {
            return null;
        }
        return metadata.get(key);
    }

    @java.lang.Override
    public java.util.Set<java.lang.String> getMetadataKeys() {
        if ((metadata) == null) {
            return java.util.Collections.EMPTY_SET;
        }
        return metadata.keySet();
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.code.CtComment> getComments() {
        return spoon.support.reflect.declaration.CtElementImpl.unmodifiableList(comments);
    }

    @java.lang.Override
    public <E extends spoon.reflect.declaration.CtElement> E addComment(spoon.reflect.code.CtComment comment) {
        if (comment == null) {
            return ((E) (this));
        }
        if ((this.comments) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.code.CtComment>emptyList())) {
            comments = new java.util.ArrayList<>(spoon.reflect.ModelElementContainerDefaultCapacities.COMMENT_CONTAINER_DEFAULT_CAPACITY);
        }
        comment.setParent(this);
        getFactory().getEnvironment().getModelChangeListener().onListAdd(this, spoon.reflect.path.CtRole.COMMENT, this.comments, comment);
        comments.add(comment);
        return ((E) (this));
    }

    @java.lang.Override
    public <E extends spoon.reflect.declaration.CtElement> E removeComment(spoon.reflect.code.CtComment comment) {
        if ((this.comments) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.code.CtComment>emptyList())) {
            return ((E) (this));
        }
        getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.COMMENT, comments, comments.indexOf(comment), comment);
        this.comments.remove(comment);
        return ((E) (this));
    }

    @java.lang.Override
    public <E extends spoon.reflect.declaration.CtElement> E setComments(java.util.List<spoon.reflect.code.CtComment> comments) {
        if ((comments == null) || (comments.isEmpty())) {
            this.comments = spoon.support.reflect.declaration.CtElementImpl.emptyList();
            return ((E) (this));
        }
        getFactory().getEnvironment().getModelChangeListener().onListDeleteAll(this, spoon.reflect.path.CtRole.COMMENT, this.comments, new java.util.ArrayList<>(this.comments));
        this.comments.clear();
        for (spoon.reflect.code.CtComment comment : comments) {
            addComment(comment);
        }
        return ((E) (this));
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtElement clone() {
        return spoon.support.visitor.equals.CloneHelper.INSTANCE.clone(this);
    }

    @java.lang.Override
    public <T> T getValueByRole(spoon.reflect.path.CtRole role) {
        spoon.reflect.meta.RoleHandler rh = spoon.reflect.meta.impl.RoleHandlerHelper.getRoleHandler(this.getClass(), role);
        return rh.getValue(this);
    }

    @java.lang.Override
    public <E extends spoon.reflect.declaration.CtElement, T> E setValueByRole(spoon.reflect.path.CtRole role, T value) {
        spoon.reflect.meta.RoleHandler rh = spoon.reflect.meta.impl.RoleHandlerHelper.getRoleHandler(this.getClass(), role);
        rh.setValue(this, value);
        return ((E) (this));
    }

    public spoon.reflect.path.CtPath getPath() {
        try {
            return new spoon.reflect.path.CtElementPathBuilder().fromElement(this, getParent(spoon.reflect.CtModelImpl.CtRootPackage.class));
        } catch (spoon.reflect.path.CtPathException e) {
            throw new spoon.SpoonException(e);
        }
    }

    @java.lang.Override
    public java.util.Iterator<spoon.reflect.declaration.CtElement> descendantIterator() {
        return new spoon.reflect.visitor.CtIterator(this);
    }

    @java.lang.Override
    public java.lang.Iterable<spoon.reflect.declaration.CtElement> asIterable() {
        return this::descendantIterator;
    }
}

