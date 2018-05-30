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
 * defining new manual processors. A manual processor should override the init
 * method (called once) and scan the meta-model manually.
 */
public abstract class AbstractManualProcessor implements spoon.processing.Processor<spoon.reflect.declaration.CtElement> {
    spoon.reflect.factory.Factory factory;

    /**
     * Empty constructor only for all processors (invoked by Spoon).
     */
    public AbstractManualProcessor() {
    }

    /**
     * Invalid method in this context.
     */
    protected void addProcessedElementType(java.lang.Class<? extends spoon.reflect.declaration.CtElement> elementType) {
    }

    public spoon.compiler.Environment getEnvironment() {
        return getFactory().getEnvironment();
    }

    public final spoon.reflect.factory.Factory getFactory() {
        return this.factory;
    }

    /**
     * Invalid method in this context.
     */
    public final java.util.Set<java.lang.Class<? extends spoon.reflect.declaration.CtElement>> getProcessedElementTypes() {
        return null;
    }

    /**
     * Invalid method in this context.
     */
    public final spoon.processing.TraversalStrategy getTraversalStrategy() {
        return spoon.processing.TraversalStrategy.POST_ORDER;
    }

    public void init() {
    }

    /**
     * Invalid method in this context.
     */
    public final boolean isPrivileged() {
        return false;
    }

    /**
     * Always returns false in this context.
     */
    public final boolean isToBeProcessed(spoon.reflect.declaration.CtElement candidate) {
        return false;
    }

    /**
     * Does nothing in this context.
     */
    public final void process(spoon.reflect.declaration.CtElement element) {
    }

    public void processingDone() {
        // do nothing by default
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

