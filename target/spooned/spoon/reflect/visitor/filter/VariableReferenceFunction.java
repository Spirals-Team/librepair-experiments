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
 * <li>CtField - member field of an type
 * <li>CtParameter - method parameter
 * <li>CtCatchVariable - try - catch variable
 * </ul>
 * and returning all the {@link CtVariableReference}, which refers this variable
 */
public class VariableReferenceFunction implements spoon.reflect.visitor.chain.CtConsumableFunction<spoon.reflect.declaration.CtElement> {
    protected final spoon.reflect.visitor.filter.VariableReferenceFunction.Visitor visitor = new spoon.reflect.visitor.filter.VariableReferenceFunction.Visitor();

    private final spoon.reflect.declaration.CtVariable<?> variable;

    protected spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer;

    protected spoon.reflect.declaration.CtElement scope;

    public VariableReferenceFunction() {
        this.variable = null;
    }

    public VariableReferenceFunction(spoon.reflect.declaration.CtVariable<?> variable) {
        this.variable = variable;
    }

    @java.lang.Override
    public void apply(spoon.reflect.declaration.CtElement variableOrScope, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        scope = variableOrScope;
        spoon.reflect.declaration.CtVariable<?> var = this.variable;
        if (var == null) {
            if (variableOrScope instanceof spoon.reflect.declaration.CtVariable<?>) {
                var = ((spoon.reflect.declaration.CtVariable<?>) (variableOrScope));
            }else {
                throw new spoon.SpoonException(("The input of VariableReferenceFunction must be a CtVariable but is a " + (variableOrScope.getClass().getSimpleName())));
            }
        }
        this.outputConsumer = outputConsumer;
        var.accept(visitor);
    }

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
            new spoon.reflect.visitor.filter.FieldReferenceFunction(((spoon.reflect.declaration.CtField<?>) (variable))).apply(scope, outputConsumer);
        }

        /**
         * calls outputConsumer for each reference of the local variable
         */
        @java.lang.Override
        public <T> void visitCtLocalVariable(spoon.reflect.code.CtLocalVariable<T> localVariable) {
            new spoon.reflect.visitor.filter.LocalVariableReferenceFunction(((spoon.reflect.code.CtLocalVariable<?>) (variable))).apply(scope, outputConsumer);
        }

        /**
         * calls outputConsumer for each reference of the parameter
         */
        @java.lang.Override
        public <T> void visitCtParameter(spoon.reflect.declaration.CtParameter<T> parameter) {
            new spoon.reflect.visitor.filter.ParameterReferenceFunction(((spoon.reflect.declaration.CtParameter<?>) (variable))).apply(scope, outputConsumer);
        }

        /**
         * calls outputConsumer for each reference of the catch variable
         */
        @java.lang.Override
        public <T> void visitCtCatchVariable(spoon.reflect.code.CtCatchVariable<T> catchVariable) {
            new spoon.reflect.visitor.filter.CatchVariableReferenceFunction(((spoon.reflect.code.CtCatchVariable<?>) (variable))).apply(scope, outputConsumer);
        }
    }
}

