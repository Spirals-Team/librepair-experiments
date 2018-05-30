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
 * This class defines an abstract processor to be subclassed by the user for
 * defining new processors.
 */
public abstract class AbstractProcessor<E extends spoon.reflect.declaration.CtElement> implements spoon.processing.Processor<E> {
    spoon.reflect.factory.Factory factory;

    java.util.Set<java.lang.Class<? extends spoon.reflect.declaration.CtElement>> processedElementTypes = new java.util.HashSet<>();

    /**
     * Empty constructor only for all processors (invoked by Spoon).
     */
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

    /**
     * Adds a processed element type. This method is typically invoked in
     * subclasses' constructors.
     */
    protected void addProcessedElementType(java.lang.Class<? extends spoon.reflect.declaration.CtElement> elementType) {
        processedElementTypes.add(elementType);
    }

    /**
     * Clears the processed element types.
     */
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

    /**
     * Helper method to load the properties of the given processor (uses
     * {@link Environment#getProcessorProperties(String)}).
     */
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

    /**
     * Helper method to initialize the properties of a given processor.
     */
    public void initProperties(spoon.processing.ProcessorProperties properties) {
        spoon.testing.utils.ProcessorUtils.initProperties(this, properties);
    }

    /**
     * The manual meta-model processing cannot be overridden (use
     * {@link AbstractManualProcessor}) to do so.
     */
    public final void process() {
    }

    public void processingDone() {
        // do nothing by default
    }

    /**
     * Removes a processed element type.
     */
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

