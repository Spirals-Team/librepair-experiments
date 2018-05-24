package spoon.reflect.visitor.filter;


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

