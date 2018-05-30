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
 * visits siblings of input element.
 * The sibling is the element whose sibling.getParent()==input.getParent()
 * <br>
 * The siblings are visited in order in which they would be visited by CtScanner.
 * The input element is by default not visited. But if {@link #includingSelf(boolean)} is called with value true,
 * then input element is visited too in the order in which CtScanner would visit it.
 *
 *  The visiting order is relevant, because this scanner is used for example resolve local variable declarations.
 */
public class SiblingsFunction implements spoon.reflect.visitor.chain.CtConsumableFunction<spoon.reflect.declaration.CtElement> {
    /**
     * Defines visiting mode
     */
    // only next siblings of input element
    public enum Mode {
        ALL, PREVIOUS, NEXT;}

    private spoon.reflect.visitor.filter.SiblingsFunction.Mode mode = spoon.reflect.visitor.filter.SiblingsFunction.Mode.ALL;

    private boolean includingSelf = false;

    public SiblingsFunction() {
    }

    /**
     *
     *
     * @param includingSelf
     * 		if false then input element is not visited
     */
    public spoon.reflect.visitor.filter.SiblingsFunction includingSelf(boolean includingSelf) {
        this.includingSelf = includingSelf;
        return this;
    }

    /**
     *
     *
     * @param mode
     * 		defines which siblings will be visited. See {@link Mode} for possible values
     */
    public spoon.reflect.visitor.filter.SiblingsFunction mode(spoon.reflect.visitor.filter.SiblingsFunction.Mode mode) {
        this.mode = mode;
        return this;
    }

    @java.lang.Override
    public void apply(final spoon.reflect.declaration.CtElement input, final spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        final spoon.reflect.declaration.CtElement parent = input.getParent();
        parent.accept(new spoon.reflect.visitor.CtScanner() {
            boolean hasVisitedInput = false;

            boolean visitPrev = ((mode) == (spoon.reflect.visitor.filter.SiblingsFunction.Mode.ALL)) || ((mode) == (spoon.reflect.visitor.filter.SiblingsFunction.Mode.PREVIOUS));

            boolean visitNext = ((mode) == (spoon.reflect.visitor.filter.SiblingsFunction.Mode.ALL)) || ((mode) == (spoon.reflect.visitor.filter.SiblingsFunction.Mode.NEXT));

            @java.lang.Override
            public void scan(spoon.reflect.declaration.CtElement element) {
                if (((element != null) && (element.isParentInitialized())) && ((element.getParent()) == parent)) {
                    // visit only elements whose parent is same
                    boolean canVisit = (hasVisitedInput) ? visitNext : visitPrev;
                    if (input == element) {
                        hasVisitedInput = true;
                        canVisit = includingSelf;
                    }
                    if (canVisit) {
                        outputConsumer.accept(element);
                    }
                }
            }
        });
    }
}

