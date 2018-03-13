package ru.job4j.strategy;

/**
 * operation add.
 */
public class OperationAdd implements Strategy {
    /**
     *
     * @param num1 is num1.
     * @param num2 is num2.
     * @return
     */
    @Override
    public int doOperation(int num1, int num2) {
        return num1 + num2;
    }
}
