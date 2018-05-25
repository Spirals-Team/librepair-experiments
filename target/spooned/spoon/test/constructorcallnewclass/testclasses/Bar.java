package spoon.test.constructorcallnewclass.testclasses;


public enum Bar {
    GREATER(">") {
        @java.lang.Override
        public boolean matchResult(final int result) {
            return result > 0;
        }
    };
    private java.lang.String symbol;

    private Bar(final java.lang.String symbol) {
        this.symbol = symbol;
    }

    public abstract boolean matchResult(int result);
}

