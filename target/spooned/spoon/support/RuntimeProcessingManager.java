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
package spoon.support;


/**
 * This processing manager implements a blocking processing policy that consists
 * of applying the processors in a FIFO order until no processors remain to be
 * applied.
 */
public class RuntimeProcessingManager implements spoon.processing.ProcessingManager {
    spoon.processing.Processor<?> current;

    spoon.reflect.factory.Factory factory;

    java.util.List<spoon.processing.Processor<?>> processors;

    spoon.support.visitor.ProcessingVisitor visitor;

    /**
     * Creates a new processing manager that maintains a queue of processors to
     * be applied to a given factory.
     *
     * @param factory
     * 		the factory on which the processing applies (contains the
     * 		meta-model)
     */
    public RuntimeProcessingManager(spoon.reflect.factory.Factory factory) {
        super();
        setFactory(factory);
    }

    public void addProcessor(java.lang.Class<? extends spoon.processing.Processor<?>> type) {
        try {
            spoon.processing.Processor<?> p = type.newInstance();
            p.setFactory(factory);
            p.init();
            addProcessor(p);
        } catch (java.lang.Exception e) {
            factory.getEnvironment().report(null, org.apache.log4j.Level.ERROR, (("Unable to instantiate processor \"" + (type.getName())) + "\" - Your processor should have a constructor with no arguments"));
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
            factory.getEnvironment().report(null, org.apache.log4j.Level.ERROR, (("Unable to load processor \"" + qualifiedName) + "\" - Check your classpath."));
        }
    }

    public spoon.processing.Processor<?> getCurrentProcessor() {
        return current;
    }

    public spoon.reflect.factory.Factory getFactory() {
        return factory;
    }

    public java.util.List<spoon.processing.Processor<?>> getProcessors() {
        if ((processors) == null) {
            processors = new java.util.LinkedList<>();
        }
        return processors;
    }

    private spoon.support.visitor.ProcessingVisitor getVisitor() {
        if ((visitor) == null) {
            visitor = new spoon.support.visitor.ProcessingVisitor(getFactory());
        }
        return visitor;
    }

    public void process(java.util.Collection<? extends spoon.reflect.declaration.CtElement> elements) {
        for (spoon.processing.Processor<?> p : getProcessors()) {
            current = p;
            process(elements, p);
        }
    }

    /**
     * Recursively processes elements and their children with a given processor.
     */
    public void process(java.util.Collection<? extends spoon.reflect.declaration.CtElement> elements, spoon.processing.Processor<?> processor) {
        try {
            getFactory().getEnvironment().debugMessage((("processing with '" + (processor.getClass().getName())) + "'..."));
            current = processor;
            spoon.support.util.Timer.start(processor.getClass().getName());
            for (spoon.reflect.declaration.CtElement e : elements) {
                process(e, processor);
            }
            spoon.support.util.Timer.stop(processor.getClass().getName());
        } catch (spoon.processing.ProcessInterruption ignored) {
        }
    }

    public void process(spoon.reflect.declaration.CtElement element) {
        for (spoon.processing.Processor<?> p : getProcessors()) {
            current = p;
            process(element, p);
        }
    }

    /**
     * Recursively processes an element and its children with a given processor.
     */
    public void process(spoon.reflect.declaration.CtElement element, spoon.processing.Processor<?> processor) {
        getFactory().getEnvironment().debugMessage((((("processing '" + (element instanceof spoon.reflect.declaration.CtNamedElement ? ((spoon.reflect.declaration.CtNamedElement) (element)).getSimpleName() : element.toString())) + "' with '") + (processor.getClass().getName())) + "'..."));
        processor.init();
        getVisitor().setProcessor(processor);
        getVisitor().scan(element);
        processor.processingDone();
    }

    public void setFactory(spoon.reflect.factory.Factory factory) {
        this.factory = factory;
        factory.getEnvironment().setManager(this);
    }
}

