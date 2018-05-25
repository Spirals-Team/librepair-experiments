package spoon.processing;


public abstract class AbstractProcessor<E extends spoon.reflect.declaration.CtElement> implements spoon.processing.Processor<E> {
    spoon.reflect.factory.Factory factory;

    java.util.Set<java.lang.Class<? extends spoon.reflect.declaration.CtElement>> processedElementTypes = new java.util.HashSet<>();

    @java.lang.SuppressWarnings("unchecked")
    public AbstractProcessor() {
        super();
        for (java.lang.reflect.Method m : getClass().getMethods()) {
            if (("process".equals(m.getName())) && ((m.getParameterTypes().length) == 1)) {
                java.lang.Class<?> c = m.getParameterTypes()[0];
                if ((spoon.reflect.declaration.CtElement.class) != c) {
                    addProcessedElementType(((java.lang.Class<E>) (c)));
                }
            }
        }
        if (processedElementTypes.isEmpty()) {
            addProcessedElementType(spoon.reflect.declaration.CtElement.class);
        }
    }

    protected void addProcessedElementType(java.lang.Class<? extends spoon.reflect.declaration.CtElement> elementType) {
        processedElementTypes.add(elementType);
    }

    protected void clearProcessedElementType() {
        processedElementTypes.clear();
    }

    public spoon.compiler.Environment getEnvironment() {
        return getFactory().getEnvironment();
    }

    public spoon.reflect.factory.Factory getFactory() {
        return this.factory;
    }

    public java.util.Set<java.lang.Class<? extends spoon.reflect.declaration.CtElement>> getProcessedElementTypes() {
        return processedElementTypes;
    }

    public spoon.processing.ProcessorProperties loadProperties() {
        spoon.processing.Processor<?> p = this;
        spoon.processing.ProcessorProperties props = null;
        try {
            props = p.getFactory().getEnvironment().getProcessorProperties(p.getClass().getName());
        } catch (java.io.FileNotFoundException e) {
            p.getFactory().getEnvironment().debugMessage((("property file not found for processor '" + (p.getClass().getName())) + "'"));
        } catch (java.io.IOException e) {
            p.getFactory().getEnvironment().report(p, org.apache.log4j.Level.ERROR, (("wrong properties file format for processor '" + (p.getClass().getName())) + "'"));
            spoon.Launcher.LOGGER.error(e.getMessage(), e);
        } catch (java.lang.Exception e) {
            p.getFactory().getEnvironment().report(p, org.apache.log4j.Level.ERROR, ((("unable to get properties for processor '" + (p.getClass().getName())) + "': ") + (e.getMessage())));
            spoon.Launcher.LOGGER.error(e.getMessage(), e);
        }
        return props;
    }

    public spoon.processing.TraversalStrategy getTraversalStrategy() {
        return spoon.processing.TraversalStrategy.POST_ORDER;
    }

    public void init() {
        this.initProperties(loadProperties());
    }

    public boolean isToBeProcessed(E candidate) {
        return true;
    }

    public void initProperties(spoon.processing.ProcessorProperties properties) {
        spoon.testing.utils.ProcessorUtils.initProperties(this, properties);
    }

    public final void process() {
    }

    public void processingDone() {
    }

    protected void removeProcessedElementType(java.lang.Class<? extends spoon.reflect.declaration.CtElement> elementType) {
        processedElementTypes.remove(elementType);
    }

    public void setFactory(spoon.reflect.factory.Factory factory) {
        this.factory = factory;
    }

    @java.lang.Override
    public void interrupt() {
        throw new spoon.processing.ProcessInterruption();
    }
}

