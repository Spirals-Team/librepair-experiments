package net.coljate.util;

import java.util.StringJoiner;

import javax.annotation.Nonnull;

/**
 *
 * @author Ollie
 */
public class Strings {

    public static String toString(final Object object) {
        return object instanceof Iterable
                ? toStringWithClass((Iterable<?>) object)
                : String.valueOf(object);
    }

    public static String toStringWithClass(final Iterable<?> iterable) {
        return iterable == null
                ? String.valueOf(iterable)
                : iterable.getClass().getSimpleName() + ":[" + elementsToString(iterable) + "]";
    }

    public static String elementsToString(@Nonnull final Iterable<?> iterable) {
        final StringJoiner joiner = new StringJoiner(",", "[", "]");
        iterable.forEach(element -> joiner.add(element == iterable ? "(self)" : toString(element))); //FIXME need to do recursive self check
        return joiner.toString();
    }

}
