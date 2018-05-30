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
 * This Query expects a {@link CtLocalVariable} as input
 * and returns all CtElements,
 * which are in visibility scope of that local variable.
 * In other words, it returns all elements,
 * which might be reference to that local variable.
 * <br>
 * It can be used to search for variable declarations or
 * variable references which might be in name conflict with input local variable.
 * <br>
 * Usage:<br>
 * <pre> {@code
 * CtLocalVariable var = ...;
 * var.map(new LocalVariableScopeFunction()).forEach(...process result...);
 * }
 * </pre>
 */
public class LocalVariableScopeFunction implements spoon.reflect.visitor.chain.CtConsumableFunction<spoon.reflect.code.CtLocalVariable<?>> {
    private final spoon.reflect.visitor.chain.CtScannerListener listener;

    public LocalVariableScopeFunction() {
        this.listener = null;
    }

    public LocalVariableScopeFunction(spoon.reflect.visitor.chain.CtScannerListener queryListener) {
        this.listener = queryListener;
    }

    @java.lang.Override
    public void apply(final spoon.reflect.code.CtLocalVariable<?> localVariable, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        localVariable.map(new spoon.reflect.visitor.filter.SiblingsFunction().mode(spoon.reflect.visitor.filter.SiblingsFunction.Mode.NEXT).includingSelf(true)).map(new spoon.reflect.visitor.filter.CtScannerFunction().setListener(this.listener)).select(new spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtElement>() {
            @java.lang.Override
            public boolean matches(spoon.reflect.declaration.CtElement element) {
                // ignore localVariable itself
                return localVariable != element;
            }
        }).forEach(outputConsumer);
    }
}

