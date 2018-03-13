package ru.job4j.strategy;

/**
 * Context.
 */
public class Context {
    /**
     * strategy.
     */
    private Strategy strategy;

    /**
     *
     * @param strategy is strategy.
     */
    public Context(Strategy strategy) {
        this.strategy = strategy;
    }

    /**
     *
     * @param num1 is num1.
     * @param num2 is num2.
     * @return operation.
     */
    public int executeStrategy(int num1, int num2) {
        return strategy.doOperation(num1, num2);
    }
}
