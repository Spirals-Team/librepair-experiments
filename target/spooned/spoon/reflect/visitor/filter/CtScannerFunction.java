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
package spoon.reflect.visitor.filter;


/**
 * Returns all children of an element.
 * More than this, it is a parameterizable class to be subclassed which provides all the power of {@link spoon.reflect.visitor.CtScanner} in the context of queries.
 * <br>
 * In particular, one can a register a {@link CtScannerListener}, it is called-back when entering/exiting each scanned AST node
 * and it drives the scanning process (see {@link ScanningMode}).
 */
public class CtScannerFunction implements spoon.reflect.visitor.chain.CtConsumableFunction<spoon.reflect.declaration.CtElement> , spoon.reflect.visitor.chain.CtQueryAware {
    private final spoon.reflect.visitor.filter.CtScannerFunction.Scanner scanner;

    private boolean includingSelf = true;

    public CtScannerFunction() {
        scanner = new spoon.reflect.visitor.filter.CtScannerFunction.Scanner();
    }

    /**
     *
     *
     * @param includingSelf
     * 		if true then input element is sent to output too. By default it is false.
     */
    public spoon.reflect.visitor.filter.CtScannerFunction includingSelf(boolean includingSelf) {
        this.includingSelf = includingSelf;
        return this;
    }

    /**
     *
     *
     * @param listener
     * 		the implementation of {@link CtScannerListener}, which will listen for enter/exit of nodes during scanning of AST
     * @return this to support fluent API
     */
    public spoon.reflect.visitor.filter.CtScannerFunction setListener(spoon.reflect.visitor.chain.CtScannerListener listener) {
        scanner.setListener(listener);
        return this;
    }

    @java.lang.Override
    public void apply(spoon.reflect.declaration.CtElement input, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        scanner.next = outputConsumer;
        if (this.includingSelf) {
            scanner.scan(input);
        }else {
            input.accept(scanner);
        }
    }

    /* it is called automatically by CtQuery implementation,
    when this mapping function is added.
     */
    @java.lang.Override
    public void setQuery(spoon.reflect.visitor.chain.CtQuery query) {
        scanner.query = query;
    }

    private static class Scanner extends spoon.reflect.visitor.EarlyTerminatingScanner<java.lang.Void> {
        protected spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> next;

        private spoon.reflect.visitor.chain.CtQuery query;

        @java.lang.Override
        protected void onElement(spoon.reflect.path.CtRole role, spoon.reflect.declaration.CtElement element) {
            next.accept(element);
            super.onElement(role, element);
        }

        /* override {@link EarlyTerminatingScanner#isTerminated()} and let it stop when query is terminated */
        @java.lang.Override
        protected boolean isTerminated() {
            return query.isTerminated();
        }
    }
}

