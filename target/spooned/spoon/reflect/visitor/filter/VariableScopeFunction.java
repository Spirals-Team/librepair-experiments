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
 * The mapping function, accepting {@link CtVariable}
 * <ul>
 * <li>CtLocalVariable - local variable declared in body
 * <li>CtParameter - method parameter
 * <li>CtCatchVariable - try - catch variable
 * </ul>
 * and returning all the CtElements, which exists in visibility scope of this variable.
 */
public class VariableScopeFunction implements spoon.reflect.visitor.chain.CtConsumableFunction<spoon.reflect.declaration.CtVariable<?>> {
    protected final spoon.reflect.visitor.filter.VariableScopeFunction.Visitor visitor = new spoon.reflect.visitor.filter.VariableScopeFunction.Visitor();

    protected spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer;

    @java.lang.Override
    public void apply(spoon.reflect.declaration.CtVariable<?> variable, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        this.outputConsumer = outputConsumer;
        variable.accept(visitor);
    }

    private static final spoon.reflect.visitor.filter.LocalVariableScopeFunction localVariableScopeFunction = new spoon.reflect.visitor.filter.LocalVariableScopeFunction();

    private static final spoon.reflect.visitor.filter.ParameterScopeFunction parameterScopeFunction = new spoon.reflect.visitor.filter.ParameterScopeFunction();

    private static final spoon.reflect.visitor.filter.CatchVariableScopeFunction catchVariableScopeFunction = new spoon.reflect.visitor.filter.CatchVariableScopeFunction();

    protected class Visitor extends spoon.reflect.visitor.CtScanner {
        @java.lang.Override
        protected void enter(spoon.reflect.declaration.CtElement e) {
            throw new spoon.SpoonException(("Unsupported variable of type " + (e.getClass().getName())));
        }

        /**
         * calls outputConsumer for each reference of the field
         */
        @java.lang.Override
        public <T> void visitCtField(spoon.reflect.declaration.CtField<T> field) {
            throw new spoon.SpoonException("Field scope function is not supported");
        }

        /**
         * calls outputConsumer for each reference of the local variable
         */
        @java.lang.Override
        public <T> void visitCtLocalVariable(spoon.reflect.code.CtLocalVariable<T> localVariable) {
            spoon.reflect.visitor.filter.VariableScopeFunction.localVariableScopeFunction.apply(localVariable, outputConsumer);
        }

        /**
         * calls outputConsumer for each reference of the parameter
         */
        @java.lang.Override
        public <T> void visitCtParameter(spoon.reflect.declaration.CtParameter<T> parameter) {
            spoon.reflect.visitor.filter.VariableScopeFunction.parameterScopeFunction.apply(parameter, outputConsumer);
        }

        /**
         * calls outputConsumer for each reference of the catch variable
         */
        @java.lang.Override
        public <T> void visitCtCatchVariable(spoon.reflect.code.CtCatchVariable<T> catchVariable) {
            spoon.reflect.visitor.filter.VariableScopeFunction.catchVariableScopeFunction.apply(catchVariable, outputConsumer);
        }
    }
}

