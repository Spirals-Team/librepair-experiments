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
 * This Query expects a {@link CtCatchVariable} as input
 * and returns all CtElements,
 * which are in visibility scope of that catch variable.
 * In other words, it returns all elements,
 * which might be reference to that catch variable.
 * <br>
 * It can be used to search for variable declarations or
 * variable references which might be in name conflict with input catch variable.
 * <br>
 * Usage:<br>
 * <pre> {@code
 * CtCatchVariable var = ...;
 * var.map(new CatchVariableScopeFunction()).forEach(...process result...);
 * }
 * </pre>
 */
public class CatchVariableScopeFunction implements spoon.reflect.visitor.chain.CtConsumableFunction<spoon.reflect.code.CtCatchVariable<?>> {
    private final spoon.reflect.visitor.chain.CtScannerListener listener;

    public CatchVariableScopeFunction() {
        this.listener = null;
    }

    public CatchVariableScopeFunction(spoon.reflect.visitor.chain.CtScannerListener queryListener) {
        this.listener = queryListener;
    }

    @java.lang.Override
    public void apply(spoon.reflect.code.CtCatchVariable<?> catchVariable, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        catchVariable.getParent(spoon.reflect.code.CtCatch.class).getBody().map(new spoon.reflect.visitor.filter.CtScannerFunction().setListener(this.listener)).forEach(outputConsumer);
    }
}

