package ru.job4j;

import static java.lang.String.format;

public enum Filter {

    PUBLISH_DATE, WITH_PICS, MANUFACTURE;

    public static Filter filterFromName(final String name) {
        for (Filter filter : Filter.values()) {
            if (filter.name().equalsIgnoreCase(name)) {
                return filter;
            }
        }
        throw new IllegalArgumentException(
            format("Filter with name: {%s}, not found", name));
    }
}
