package spoon.reflect.visitor.filter;


public class ParameterScopeFunction implements spoon.reflect.visitor.chain.CtConsumableFunction<spoon.reflect.declaration.CtParameter<?>> {
    private final spoon.reflect.visitor.chain.CtScannerListener listener;

    public ParameterScopeFunction() {
        this.listener = null;
    }

    public ParameterScopeFunction(spoon.reflect.visitor.chain.CtScannerListener queryListener) {
        this.listener = queryListener;
    }

    @java.lang.Override
    public void apply(spoon.reflect.declaration.CtParameter<?> parameter, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        spoon.reflect.declaration.CtExecutable<?> exec = parameter.getParent(spoon.reflect.declaration.CtExecutable.class);
        if (exec == null) {
            return;
        }
        exec.map(new spoon.reflect.visitor.filter.CtScannerFunction().setListener(this.listener)).forEach(outputConsumer);
    }
}

