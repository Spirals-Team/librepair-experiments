package spoon.reflect.visitor.filter;


public class CtScannerFunction implements spoon.reflect.visitor.chain.CtConsumableFunction<spoon.reflect.declaration.CtElement> , spoon.reflect.visitor.chain.CtQueryAware {
    private final spoon.reflect.visitor.filter.CtScannerFunction.Scanner scanner;

    private boolean includingSelf = true;

    public CtScannerFunction() {
        scanner = new spoon.reflect.visitor.filter.CtScannerFunction.Scanner();
    }

    public spoon.reflect.visitor.filter.CtScannerFunction includingSelf(boolean includingSelf) {
        this.includingSelf = includingSelf;
        return this;
    }

    public spoon.reflect.visitor.filter.CtScannerFunction setListener(spoon.reflect.visitor.chain.CtScannerListener listener) {
        scanner.setListener(listener);
        return this;
    }

    @java.lang.Override
    public void apply(spoon.reflect.declaration.CtElement input, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
        scanner.next = outputConsumer;
        if (this.includingSelf) {
            scanner.scan(input);
        }else {
            input.accept(scanner);
        }
    }

    @java.lang.Override
    public void setQuery(spoon.reflect.visitor.chain.CtQuery query) {
        scanner.query = query;
    }

    private static class Scanner extends spoon.reflect.visitor.EarlyTerminatingScanner<java.lang.Void> {
        protected spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> next;

        private spoon.reflect.visitor.chain.CtQuery query;

        @java.lang.Override
        protected void onElement(spoon.reflect.path.CtRole role, spoon.reflect.declaration.CtElement element) {
            next.accept(element);
            super.onElement(role, element);
        }

        @java.lang.Override
        protected boolean isTerminated() {
            return query.isTerminated();
        }
    }
}

