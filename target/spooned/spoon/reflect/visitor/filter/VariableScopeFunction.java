package spoon.reflect.visitor.filter;


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

        @java.lang.Override
        public <T> void visitCtField(spoon.reflect.declaration.CtField<T> field) {
            throw new spoon.SpoonException("Field scope function is not supported");
        }

        @java.lang.Override
        public <T> void visitCtLocalVariable(spoon.reflect.code.CtLocalVariable<T> localVariable) {
            spoon.reflect.visitor.filter.VariableScopeFunction.localVariableScopeFunction.apply(localVariable, outputConsumer);
        }

        @java.lang.Override
        public <T> void visitCtParameter(spoon.reflect.declaration.CtParameter<T> parameter) {
            spoon.reflect.visitor.filter.VariableScopeFunction.parameterScopeFunction.apply(parameter, outputConsumer);
        }

        @java.lang.Override
        public <T> void visitCtCatchVariable(spoon.reflect.code.CtCatchVariable<T> catchVariable) {
            spoon.reflect.visitor.filter.VariableScopeFunction.catchVariableScopeFunction.apply(catchVariable, outputConsumer);
        }
    }
}

