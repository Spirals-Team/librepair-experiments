package spoon.reflect.visitor.filter;


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

        @java.lang.Override
        public <T> void visitCtField(spoon.reflect.declaration.CtField<T> field) {
            new spoon.reflect.visitor.filter.FieldReferenceFunction(((spoon.reflect.declaration.CtField<?>) (variable))).apply(scope, outputConsumer);
        }

        @java.lang.Override
        public <T> void visitCtLocalVariable(spoon.reflect.code.CtLocalVariable<T> localVariable) {
            new spoon.reflect.visitor.filter.LocalVariableReferenceFunction(((spoon.reflect.code.CtLocalVariable<?>) (variable))).apply(scope, outputConsumer);
        }

        @java.lang.Override
        public <T> void visitCtParameter(spoon.reflect.declaration.CtParameter<T> parameter) {
            new spoon.reflect.visitor.filter.ParameterReferenceFunction(((spoon.reflect.declaration.CtParameter<?>) (variable))).apply(scope, outputConsumer);
        }

        @java.lang.Override
        public <T> void visitCtCatchVariable(spoon.reflect.code.CtCatchVariable<T> catchVariable) {
            new spoon.reflect.visitor.filter.CatchVariableReferenceFunction(((spoon.reflect.code.CtCatchVariable<?>) (variable))).apply(scope, outputConsumer);
        }
    }
}

