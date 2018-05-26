package spoon.reflect.factory;


public class AnnotationFactory extends spoon.reflect.factory.TypeFactory {
    public AnnotationFactory(spoon.reflect.factory.Factory factory) {
        super(factory);
    }

    public <T extends java.lang.annotation.Annotation> spoon.reflect.declaration.CtAnnotationType<?> create(spoon.reflect.declaration.CtPackage owner, java.lang.String simpleName) {
        spoon.reflect.declaration.CtAnnotationType<T> t = factory.Core().createAnnotationType();
        t.setSimpleName(simpleName);
        owner.addType(t);
        return t;
    }

    public spoon.reflect.declaration.CtAnnotationType<?> create(java.lang.String qualifiedName) {
        return create(factory.Package().getOrCreate(getPackageName(qualifiedName)), getSimpleName(qualifiedName));
    }

    public <T extends java.lang.annotation.Annotation> spoon.reflect.declaration.CtType<T> getAnnotationType(java.lang.String qualifiedName) {
        return get(qualifiedName);
    }

    public <A extends java.lang.annotation.Annotation> spoon.reflect.declaration.CtAnnotation<A> annotate(spoon.reflect.declaration.CtElement element, java.lang.Class<A> annotationType, java.lang.String annotationElementName, java.lang.Object value) {
        return annotate(element, factory.Type().createReference(annotationType), annotationElementName, value);
    }

    public <A extends java.lang.annotation.Annotation> spoon.reflect.declaration.CtAnnotation<A> annotate(spoon.reflect.declaration.CtElement element, spoon.reflect.reference.CtTypeReference<A> annotationType, java.lang.String annotationElementName, java.lang.Object value) {
        final spoon.reflect.declaration.CtAnnotation<A> annotation = annotate(element, annotationType);
        boolean isArray;
        spoon.reflect.declaration.CtAnnotationType<A> ctAnnotationType = ((spoon.reflect.declaration.CtAnnotationType<A>) (annotation.getAnnotationType().getDeclaration()));
        boolean newValue = (annotation.getValue(annotationElementName)) == null;
        if (ctAnnotationType != null) {
            spoon.reflect.declaration.CtMethod<?> e = ctAnnotationType.getMethod(annotationElementName);
            isArray = (e.getType()) instanceof spoon.reflect.reference.CtArrayTypeReference;
        }else {
            java.lang.reflect.Method m;
            try {
                m = annotation.getAnnotationType().getActualClass().getMethod(annotationElementName, new java.lang.Class[0]);
            } catch (java.lang.Exception ex) {
                annotation.addValue(annotationElementName, value);
                return annotation;
            }
            isArray = m.getReturnType().isArray();
        }
        if (isArray || newValue) {
            annotation.addValue(annotationElementName, value);
        }else {
            throw new spoon.SpoonException("cannot assign an array to a non-array annotation element");
        }
        return annotation;
    }

    public <A extends java.lang.annotation.Annotation> spoon.reflect.declaration.CtAnnotation<A> annotate(spoon.reflect.declaration.CtElement element, java.lang.Class<A> annotationType) {
        return annotate(element, factory.Type().createReference(annotationType));
    }

    public <A extends java.lang.annotation.Annotation> spoon.reflect.declaration.CtAnnotation<A> annotate(spoon.reflect.declaration.CtElement element, spoon.reflect.reference.CtTypeReference<A> annotationType) {
        spoon.reflect.declaration.CtAnnotationType<A> ctAnnotationType = ((spoon.reflect.declaration.CtAnnotationType<A>) (annotationType.getDeclaration()));
        boolean isRepeatable = false;
        if (ctAnnotationType != null) {
            isRepeatable = (ctAnnotationType.getAnnotation(factory.Type().createReference(java.lang.annotation.Repeatable.class))) != null;
        }
        spoon.reflect.declaration.CtAnnotation<A> annotation = element.getAnnotation(annotationType);
        if ((annotation == null) || isRepeatable) {
            annotation = factory.Core().createAnnotation();
            annotation.setAnnotationType(factory.Core().clone(annotationType));
            element.addAnnotation(annotation);
        }
        return annotation;
    }
}

