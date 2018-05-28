package spoon.processing;


public abstract class AbstractAnnotationProcessor<A extends java.lang.annotation.Annotation, E extends spoon.reflect.declaration.CtElement> extends spoon.processing.AbstractProcessor<E> implements spoon.processing.AnnotationProcessor<A, E> {
    java.util.Map<java.lang.String, java.lang.Class<? extends A>> consumedAnnotationTypes = new java.util.TreeMap<>();

    java.util.Map<java.lang.String, java.lang.Class<? extends A>> processedAnnotationTypes = new java.util.TreeMap<>();

    @java.lang.SuppressWarnings("unchecked")
    public AbstractAnnotationProcessor() {
        super();
        clearProcessedElementType();
        for (java.lang.reflect.Method m : getClass().getMethods()) {
            if (("process".equals(m.getName())) && ((m.getParameterTypes().length) == 2)) {
                java.lang.Class<?> c = m.getParameterTypes()[0];
                if ((inferConsumedAnnotationType()) && ((java.lang.annotation.Annotation.class) != c)) {
                    addConsumedAnnotationType(((java.lang.Class<A>) (c)));
                }
                c = m.getParameterTypes()[1];
                if ((spoon.reflect.declaration.CtElement.class) != c) {
                    addProcessedElementType(((java.lang.Class<E>) (c)));
                }
            }
        }
        if ((inferConsumedAnnotationType()) && (processedAnnotationTypes.isEmpty())) {
            addProcessedAnnotationType(((java.lang.Class<? extends A>) (java.lang.annotation.Annotation.class)));
        }
        if (processedElementTypes.isEmpty()) {
            addProcessedElementType(spoon.reflect.declaration.CtElement.class);
        }
    }

    protected final void addConsumedAnnotationType(java.lang.Class<? extends A> annotationType) {
        addProcessedAnnotationType(annotationType);
        consumedAnnotationTypes.put(annotationType.getName(), annotationType);
    }

    protected final void addProcessedAnnotationType(java.lang.Class<? extends A> annotationType) {
        processedAnnotationTypes.put(annotationType.getName(), annotationType);
    }

    protected final void removeProcessedAnnotationType(java.lang.Class<? extends A> annotationType) {
        processedAnnotationTypes.remove(annotationType.getName());
    }

    protected final void clearProcessedAnnotationTypes() {
        processedAnnotationTypes.clear();
    }

    protected final void clearConsumedAnnotationTypes() {
        consumedAnnotationTypes.clear();
    }

    protected final void removeConsumedAnnotationType(java.lang.Class<? extends A> annotationType) {
        consumedAnnotationTypes.remove(annotationType.getName());
    }

    public final java.util.Set<java.lang.Class<? extends A>> getConsumedAnnotationTypes() {
        return new java.util.HashSet<>(consumedAnnotationTypes.values());
    }

    public final java.util.Set<java.lang.Class<? extends A>> getProcessedAnnotationTypes() {
        return new java.util.HashSet<>(processedAnnotationTypes.values());
    }

    public boolean inferConsumedAnnotationType() {
        return true;
    }

    @java.lang.Override
    public final boolean isToBeProcessed(E element) {
        if ((element != null) && ((element.getAnnotations()) != null)) {
            for (spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation> a : element.getAnnotations()) {
                if (shoudBeProcessed(a)) {
                    return true;
                }
            }
        }
        return false;
    }

    @java.lang.SuppressWarnings("unchecked")
    public final void process(E element) {
        for (spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation> annotation : new java.util.ArrayList<>(element.getAnnotations())) {
            if (shoudBeProcessed(annotation)) {
                try {
                    process(((A) (annotation.getActualAnnotation())), element);
                } catch (java.lang.Exception e) {
                    spoon.Launcher.LOGGER.error(e.getMessage(), e);
                }
                if (shoudBeConsumed(annotation)) {
                    element.removeAnnotation(annotation);
                }
            }
        }
    }

    @java.lang.Override
    public boolean shoudBeConsumed(spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation> annotation) {
        return consumedAnnotationTypes.containsKey(annotation.getAnnotationType().getQualifiedName());
    }

    private boolean shoudBeProcessed(spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation> annotation) {
        return processedAnnotationTypes.containsKey(annotation.getAnnotationType().getQualifiedName());
    }
}

