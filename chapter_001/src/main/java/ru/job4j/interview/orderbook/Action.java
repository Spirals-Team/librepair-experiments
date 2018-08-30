package ru.job4j.interview.orderbook;

import static java.lang.String.format;

/**
 * Enum represent a type of action.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public enum Action {

    ADD("AddOrder"),
    DELETE("DeleteOrder");

    private String type;

    Action(final String type) {
        this.type = type;
    }

    public static Action actionFromName(final String name) {
        for (Action action : Action.values()) {
            if (action.type.equalsIgnoreCase(name)) {
                return action;
            }
        }
        throw new IllegalArgumentException(
            format("Action with name: {%s}, not found", name));
    }

    @Override
    public String toString() {
        return "Action{type=" + this.type + "}";
    }

    public String getType() {
        return type;
    }
}
