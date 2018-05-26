package spoon.processing;


public abstract class AbstractManualProcessor implements spoon.processing.Processor<spoon.reflect.declaration.CtElement> {
    spoon.reflect.factory.Factory factory;

    public AbstractManualProcessor() {
    }

    protected void addProcessedElementType(java.lang.Class<? extends spoon.reflect.declaration.CtElement> elementType) {
    }

    public spoon.compiler.Environment getEnvironment() {
        return getFactory().getEnvironment();
    }

    public final spoon.reflect.factory.Factory getFactory() {
        return this.factory;
    }

    public final java.util.Set<java.lang.Class<? extends spoon.reflect.declaration.CtElement>> getProcessedElementTypes() {
        return null;
    }

    public final spoon.processing.TraversalStrategy getTraversalStrategy() {
        return spoon.processing.TraversalStrategy.POST_ORDER;
    }

    public void init() {
    }

    public final boolean isPrivileged() {
        return false;
    }

    public final boolean isToBeProcessed(spoon.reflect.declaration.CtElement candidate) {
        return false;
    }

    public final void process(spoon.reflect.declaration.CtElement element) {
    }

    public void processingDone() {
    }

    public final void setFactory(spoon.reflect.factory.Factory factory) {
        this.factory = factory;
    }

    public final void initProperties(spoon.processing.ProcessorProperties properties) {
        spoon.testing.utils.ProcessorUtils.initProperties(this, properties);
    }

    @java.lang.Override
    public void interrupt() {
        throw new spoon.processing.ProcessInterruption();
    }
}

