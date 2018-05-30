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
package spoon.reflect.visitor;


/**
 * Extends {@link CtScanner}, to support early termination of scanning process and scan listeners.
 * It is useful when your algorithm is searching for a specific node only.
 * In this case, you can call {@link #terminate()}, which ensures that no more AST nodes are visited,
 * <br>
 * It is possible to register an implementation of {@link CtScannerListener},
 * whose {@link CtScannerListener#enter(CtElement)}/{@link CtScannerListener#exit(CtElement)}
 * methods are called before/after each AST node is visited.<br>
 *
 * @param <T>
 * 		the type of the result produced by this scanner.
 */
public class EarlyTerminatingScanner<T> extends spoon.reflect.visitor.CtScanner {
    private boolean terminate = false;

    private T result;

    private spoon.reflect.visitor.chain.CtScannerListener listener;

    protected spoon.reflect.path.CtRole scannedRole;

    protected void terminate() {
        terminate = true;
    }

    protected boolean isTerminated() {
        return terminate;
    }

    protected void setResult(T result) {
        this.result = result;
    }

    /**
     *
     *
     * @return the result of scanning - the value, which was stored by a previous call of {@link #setResult(Object)}
     */
    public T getResult() {
        return result;
    }

    /**
     *
     *
     * @return null or the implementation of {@link CtScannerListener}, which is registered to listen for enter/exit of nodes during scanning of the AST
     */
    public spoon.reflect.visitor.chain.CtScannerListener getListener() {
        return listener;
    }

    /**
     *
     *
     * @param listener
     * 		the implementation of {@link CtScannerListener}, which will be called back when entering/exiting
     * 		odes during scanning.
     * @return this to support fluent API
     */
    public spoon.reflect.visitor.EarlyTerminatingScanner<T> setListener(spoon.reflect.visitor.chain.CtScannerListener listener) {
        this.listener = listener;
        return this;
    }

    @java.lang.Override
    public void scan(spoon.reflect.path.CtRole role, java.util.Collection<? extends spoon.reflect.declaration.CtElement> elements) {
        if ((isTerminated()) || (elements == null)) {
            return;
        }
        // we use defensive copy so as to be able to change the class while scanning
        // otherwise one gets a ConcurrentModificationException
        for (spoon.reflect.declaration.CtElement e : new java.util.ArrayList<>(elements)) {
            scan(role, e);
            if (isTerminated()) {
                return;
            }
        }
    }

    @java.lang.Override
    public void scan(spoon.reflect.path.CtRole role, java.util.Map<java.lang.String, ? extends spoon.reflect.declaration.CtElement> elements) {
        if ((isTerminated()) || (elements == null)) {
            return;
        }
        for (spoon.reflect.declaration.CtElement obj : elements.values()) {
            scan(role, obj);
            if (isTerminated()) {
                return;
            }
        }
    }

    @java.lang.Override
    public void scan(spoon.reflect.path.CtRole role, spoon.reflect.declaration.CtElement element) {
        scannedRole = role;
        super.scan(role, element);
    }

    /* we cannot override scan(CtRole role, CtElement element) directly
    because some implementations needs scan(CtElement element), which must be called too
     */
    @java.lang.Override
    public void scan(spoon.reflect.declaration.CtElement element) {
        if ((element == null) || (isTerminated())) {
            return;
        }
        if ((listener) == null) {
            // the listener is not defined
            // visit this element and may be children
            doScan(scannedRole, element, spoon.reflect.visitor.chain.ScanningMode.NORMAL);
        }else {
            // the listener is defined, call it's enter method first
            spoon.reflect.visitor.chain.ScanningMode mode = listener.enter(element);
            if (mode != (spoon.reflect.visitor.chain.ScanningMode.SKIP_ALL)) {
                // the listener decided to visit this element and may be children
                doScan(scannedRole, element, mode);
                // then call exit, only if enter returned true
                listener.exit(element);
            }// else the listener decided to skip this element and all children. Do not call exit.

        }
    }

    /**
     * This method is called ONLY when the listener decides that the current element and children should be visited.
     * Subclasses can override it to react accordingly.
     */
    protected void doScan(spoon.reflect.path.CtRole role, spoon.reflect.declaration.CtElement element, spoon.reflect.visitor.chain.ScanningMode mode) {
        // send input to output
        if (mode.visitElement) {
            onElement(role, element);
        }
        if (mode.visitChildren) {
            // do not call scan(CtElement) nor scan(CtRole, CtElement), because they would cause StackOverflowError
            element.accept(this);
        }
    }

    /**
     * Called for each scanned element. The call of this method is influenced by {@link ScanningMode} defined by {@link CtScannerListener}
     *
     * @param role
     * 		a role of `element` in parent
     * @param element
     * 		a scanned element
     */
    protected void onElement(spoon.reflect.path.CtRole role, spoon.reflect.declaration.CtElement element) {
    }

    @java.lang.Override
    public void scan(spoon.reflect.path.CtRole role, java.lang.Object o) {
        if ((isTerminated()) || (o == null)) {
            return;
        }
        if (o instanceof spoon.reflect.declaration.CtElement) {
            scan(role, ((spoon.reflect.declaration.CtElement) (o)));
        }else
            if (o instanceof java.util.Collection<?>) {
                scan(role, ((java.util.Collection<? extends spoon.reflect.declaration.CtElement>) (o)));
            }else
                if (o instanceof java.util.Map<?, ?>) {
                    for (java.lang.Object obj : ((java.util.Map) (o)).values()) {
                        scan(role, obj);
                        if (isTerminated()) {
                            return;
                        }
                    }
                }


    }
}

