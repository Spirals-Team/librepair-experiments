package spoon.processing;


public interface ProcessingManager extends spoon.processing.FactoryAccessor {
    void addProcessor(java.lang.Class<? extends spoon.processing.Processor<?>> type);

    boolean addProcessor(spoon.processing.Processor<?> p);

    void addProcessor(java.lang.String qualifiedName);

    java.util.Collection<spoon.processing.Processor<?>> getProcessors();

    void process(java.util.Collection<? extends spoon.reflect.declaration.CtElement> elements);

    void process(spoon.reflect.declaration.CtElement element);
}

