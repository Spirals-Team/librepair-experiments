package spoon.processing;


public interface Processor<E extends spoon.reflect.declaration.CtElement> extends spoon.processing.FactoryAccessor {
    spoon.processing.TraversalStrategy getTraversalStrategy();

    spoon.compiler.Environment getEnvironment();

    boolean isToBeProcessed(E candidate);

    void process(E element);

    void process();

    java.util.Set<java.lang.Class<? extends spoon.reflect.declaration.CtElement>> getProcessedElementTypes();

    void processingDone();

    void init();

    void initProperties(spoon.processing.ProcessorProperties properties);

    void interrupt();
}

