package spoon.reflect.visitor.filter;


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
                return localVariable != element;
            }
        }).forEach(outputConsumer);
    }
}

