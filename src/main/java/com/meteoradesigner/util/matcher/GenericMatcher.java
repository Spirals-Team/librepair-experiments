package com.meteoradesigner.util.matcher;

import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

//TODO later construct methods without reflection to boost performance.
/**
 * This @code{GenericMatcher} class represents generic matcher.
 */
public class GenericMatcher {

    /**
     * Validates if the objects are equal, except excluded fields.
     *
     * @param first  expected.
     * @param second to check.
     * @return @code{true}, if the object are equals except excluded fields.
     */
    public static <T> boolean isEqualsWithoutId(T first,
                                                T second) {
        return new ReflectionEquals(first, "id").matches(second);
    }
}