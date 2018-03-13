package ru.job4j.strategy;

/**
 * is substraction.
 */
public class OperationSubstract implements Strategy {
    /**
     *
     * @param num1 is num1.
     * @param num2 is num2.
     * @return is subs.
     */
    @Override
    public int doOperation(int num1, int num2) {
        return num1 - num2;
    }
}
