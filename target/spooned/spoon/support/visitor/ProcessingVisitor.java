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
package spoon.support.visitor;


/**
 * This visitor implements the code processing engine.
 */
public class ProcessingVisitor extends spoon.reflect.visitor.CtScanner {
    spoon.reflect.factory.Factory factory;

    spoon.processing.Processor<?> processor;

    /**
     * The constructor.
     */
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

    /**
     * Applies the processing to the given element. To apply the processing,
     * this method upcalls, for all the registered processor in, the
     * {@link Processor#process(CtElement)} method if
     * {@link Processor#isToBeProcessed(CtElement)} returns true.
     */
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

