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
 * This Function expects a {@link CtElement} as input
 * and returns all parents of this element.
 *
 * By default input is not returned,
 * but this behavior can be changed by call of {@link #includingSelf(boolean)} with value true
 */
public class ParentFunction implements spoon.reflect.visitor.chain.CtConsumableFunction<spoon.reflect.declaration.CtElement> , spoon.reflect.visitor.chain.CtQueryAware {
    private boolean includingSelf = false;

    private spoon.reflect.visitor.chain.CtQuery query;

    public ParentFunction() {
    }

    /**
     *
     *
     * @param includingSelf
     * 		if true then input element is sent to output too. By default it is false.
     */
    public spoon.reflect.visitor.filter.ParentFunction includingSelf(boolean includingSelf) {
        this.includingSelf = includingSelf;
        return this;
    }

    @java.lang.Override
    public void apply(spoon.reflect.declaration.CtElement input, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        if (input == null) {
            return;
        }
        if (includingSelf) {
            outputConsumer.accept(input);
        }
        spoon.reflect.declaration.CtElement parent = input;
        spoon.reflect.declaration.CtModule topLevel = input.getFactory().getModel().getUnnamedModule();
        while ((((parent != null) && (parent != topLevel)) && ((query.isTerminated()) == false)) && (parent.isParentInitialized())) {
            parent = parent.getParent();
            outputConsumer.accept(parent);
        } 
    }

    @java.lang.Override
    public void setQuery(spoon.reflect.visitor.chain.CtQuery query) {
        this.query = query;
    }
}

