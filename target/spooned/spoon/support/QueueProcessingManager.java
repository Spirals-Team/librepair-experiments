package spoon.support;


public class QueueProcessingManager implements spoon.processing.ProcessingManager {
    spoon.processing.Processor<?> current;

    spoon.reflect.factory.Factory factory;

    java.util.Queue<spoon.processing.Processor<?>> processors;

    spoon.support.visitor.ProcessingVisitor visitor;

    public QueueProcessingManager(spoon.reflect.factory.Factory factory) {
        super();
        setFactory(factory);
    }

    public void addProcessor(java.lang.Class<? extends spoon.processing.Processor<?>> type) {
        try {
            spoon.processing.Processor<?> p = type.newInstance();
            addProcessor(p);
        } catch (java.lang.Exception e) {
            throw new spoon.SpoonException((("Unable to instantiate processor \"" + (type.getName())) + "\" - Your processor should have a constructor with no arguments"), e);
        }
    }

    public boolean addProcessor(spoon.processing.Processor<?> p) {
        p.setFactory(getFactory());
        return getProcessors().add(p);
    }

    @java.lang.SuppressWarnings("unchecked")
    public void addProcessor(java.lang.String qualifiedName) {
        try {
            addProcessor(((java.lang.Class<? extends spoon.processing.Processor<?>>) (getFactory().getEnvironment().getInputClassLoader().loadClass(qualifiedName))));
        } catch (java.lang.ClassNotFoundException e) {
            throw new spoon.SpoonException((("Unable to load processor \"" + qualifiedName) + "\" - Check your classpath."), e);
        }
    }

    public spoon.processing.Processor<?> getCurrentProcessor() {
        return current;
    }

    public spoon.reflect.factory.Factory getFactory() {
        return factory;
    }

    public java.util.Queue<spoon.processing.Processor<?>> getProcessors() {
        if ((processors) == null) {
            processors = new java.util.LinkedList<>();
        }
        return processors;
    }

    protected spoon.support.visitor.ProcessingVisitor getVisitor() {
        if ((visitor) == null) {
            visitor = new spoon.support.visitor.ProcessingVisitor(getFactory());
        }
        return visitor;
    }

    public void process(java.util.Collection<? extends spoon.reflect.declaration.CtElement> elements) {
        spoon.processing.Processor<?> p;
        java.util.Queue<spoon.processing.Processor<?>> processors = new java.util.LinkedList<>(getProcessors());
        while ((p = processors.poll()) != null) {
            try {
                getFactory().getEnvironment().reportProgressMessage(p.getClass().getName());
                current = p;
                p.init();
                p.process();
                for (spoon.reflect.declaration.CtElement e : new java.util.ArrayList<>(elements)) {
                    getVisitor().setProcessor(p);
                    getVisitor().scan(e);
                }
            } catch (spoon.processing.ProcessInterruption ignore) {
            } finally {
                p.processingDone();
            }
        } 
    }

    public void process(spoon.reflect.declaration.CtElement element) {
        java.util.List<spoon.reflect.declaration.CtElement> l = new java.util.ArrayList<>();
        l.add(element);
        process(l);
    }

    public void setFactory(spoon.reflect.factory.Factory factory) {
        this.factory = factory;
        factory.getEnvironment().setManager(this);
    }
}

