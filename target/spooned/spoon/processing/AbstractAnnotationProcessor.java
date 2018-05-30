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
package spoon.processing;


/**
 * This class defines an abstract annotation processor to be subclassed by the
 * user for defining new annotation processors including Java 8 annotations.
 */
public abstract class AbstractAnnotationProcessor<A extends java.lang.annotation.Annotation, E extends spoon.reflect.declaration.CtElement> extends spoon.processing.AbstractProcessor<E> implements spoon.processing.AnnotationProcessor<A, E> {
    java.util.Map<java.lang.String, java.lang.Class<? extends A>> consumedAnnotationTypes = new java.util.TreeMap<>();

    java.util.Map<java.lang.String, java.lang.Class<? extends A>> processedAnnotationTypes = new java.util.TreeMap<>();

    /**
     * Empty constructor only for all processors (invoked by Spoon).
     */
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

    /**
     * Adds a consumed annotation type (to be used in subclasses constructors).
     * A consumed annotation type is also part of the processed annotation
     * types.
     */
    protected final void addConsumedAnnotationType(java.lang.Class<? extends A> annotationType) {
        addProcessedAnnotationType(annotationType);
        consumedAnnotationTypes.put(annotationType.getName(), annotationType);
    }

    /**
     * Adds a processed annotation type (to be used in subclasses constructors).
     */
    protected final void addProcessedAnnotationType(java.lang.Class<? extends A> annotationType) {
        processedAnnotationTypes.put(annotationType.getName(), annotationType);
    }

    /**
     * Removes a processed annotation type.
     */
    protected final void removeProcessedAnnotationType(java.lang.Class<? extends A> annotationType) {
        processedAnnotationTypes.remove(annotationType.getName());
    }

    /**
     * Clears the processed annotation types.
     */
    protected final void clearProcessedAnnotationTypes() {
        processedAnnotationTypes.clear();
    }

    /**
     * Clears the consumed annotation types.
     */
    protected final void clearConsumedAnnotationTypes() {
        consumedAnnotationTypes.clear();
    }

    /**
     * Removes a processed annotation type.
     */
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

    /**
     * Returns true if the element is annotated with an annotation whose type is
     * processed.
     */
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

    /**
     * {@inheritDoc}
     *
     * Removes all annotations A on elements E.
     */
    @java.lang.Override
    public boolean shoudBeConsumed(spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation> annotation) {
        return consumedAnnotationTypes.containsKey(annotation.getAnnotationType().getQualifiedName());
    }

    private boolean shoudBeProcessed(spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation> annotation) {
        return processedAnnotationTypes.containsKey(annotation.getAnnotationType().getQualifiedName());
    }
}

