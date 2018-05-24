package spoon.processing;


public interface AnnotationProcessor<A extends java.lang.annotation.Annotation, E extends spoon.reflect.declaration.CtElement> extends spoon.processing.Processor<E> {
    void process(A annotation, E element);

    java.util.Set<java.lang.Class<? extends A>> getProcessedAnnotationTypes();

    java.util.Set<java.lang.Class<? extends A>> getConsumedAnnotationTypes();

    boolean inferConsumedAnnotationType();

    boolean shoudBeConsumed(spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation> annotation);
}

