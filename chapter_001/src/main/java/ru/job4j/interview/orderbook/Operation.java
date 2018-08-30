package ru.job4j.interview.orderbook;

import static java.lang.String.format;

/**
 * Enum represent a type of operation.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public enum Operation {

    SELL("SELL"),
    BUY("BUY");

    private String type;

    Operation(final String type) {
        this.type = type;
    }

    public static Operation operationFromTitle(final String title) {
        for (Operation operation : Operation.values()) {
            if (operation.type.equalsIgnoreCase(title)) {
                return operation;
            }
        }
        throw new IllegalArgumentException(
            format("Operation with title: {%s}, not found", title));
    }

    public static Operation revertOperation(Operation operation) {
        return (operation.equals(BUY)) ? SELL : BUY;
    }

    @Override
    public String toString() {
        return "Operation{type=" + this.type + "}";
    }

    public String getType() {
        return type;
    }
}
