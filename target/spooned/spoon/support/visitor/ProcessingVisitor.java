package spoon.support.visitor;


public class ProcessingVisitor extends spoon.reflect.visitor.CtScanner {
    spoon.reflect.factory.Factory factory;

    spoon.processing.Processor<?> processor;

    public ProcessingVisitor(spoon.reflect.factory.Factory factory) {
        this.factory = factory;
    }

    private boolean canBeProcessed(spoon.reflect.declaration.CtElement e) {
        if ((!(factory.getEnvironment().isProcessingStopped())) && ((processor.getProcessedElementTypes()) != null)) {
            for (java.lang.Object o : processor.getProcessedElementTypes()) {
                if (!(((java.lang.Class<?>) (o)).isAssignableFrom(e.getClass()))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public spoon.processing.Processor<?> getProcessor() {
        return processor;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void scan(spoon.reflect.declaration.CtElement e) {
        if (e == null) {
            return;
        }
        spoon.processing.Processor<spoon.reflect.declaration.CtElement> p = ((spoon.processing.Processor<spoon.reflect.declaration.CtElement>) (processor));
        if (((p.getTraversalStrategy()) == (spoon.processing.TraversalStrategy.PRE_ORDER)) && (canBeProcessed(e))) {
            if (p.isToBeProcessed(e)) {
                p.process(e);
            }
        }
        super.scan(e);
        if (((p.getTraversalStrategy()) == (spoon.processing.TraversalStrategy.POST_ORDER)) && (canBeProcessed(e))) {
            if (p.isToBeProcessed(e)) {
                p.process(e);
            }
        }
    }

    public void setProcessor(spoon.processing.Processor<?> processor) {
        this.processor = processor;
    }
}

