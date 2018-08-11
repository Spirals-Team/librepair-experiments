package net.coljate.util;

import net.coljate.util.complexity.Complexity;
import net.coljate.util.complexity.TimeComplexity;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 * @author Ollie
 */
public final class Arrays {

    public static final Object[] EMPTY = new Object[0];

    private Arrays() {
    }

    @TimeComplexity(Complexity.LINEAR)
    public static boolean contains(final Object[] array, final Object element) {
        for (int i = 0; i < array.length; i++) {
            if (Objects.equals(array[i], element)) {
                return true;
            }
        }
        return false;
    }

    public static <T> void consume(final T[] elements, final Consumer<T> consumer) {
        for (int i = 0; i < elements.length; i++) {
            consumer.accept(elements[i]);
        }
    }

    public static <T> void update(final T[] elements, final UnaryOperator<T> operator) {
        for (int i = 0; i < elements.length; i++) {
            elements[i] = operator.apply(elements[i]);
        }
    }

    @TimeComplexity(Complexity.LINEAR)
    public static <T> T[] copyOf(final T[] from) {
        return java.util.Arrays.copyOf(from, from.length);
    }

    public static Object[] copyOf(final Object[] from, final int length) {
        return java.util.Arrays.copyOf(from, length);
    }

    @TimeComplexity(Complexity.LINEAR)
    public static int indexOf(final Object[] array, final Object element) {
        for (int i = 0; i < array.length; i++) {
            if (Objects.equals(array[i], element)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Transform all elements in the source array, resizing the given prototype array if necessary.
     *
     * @param <F>
     * @param <T>
     * @param from
     * @param transform
     * @param prototype
     * @return
     */
    @TimeComplexity(bestCase = Complexity.LINEAR, computed = true)
    public static <F, T> T[] transformAllSource(final F[] from, final Function<? super F, ? extends T> transform, final T[] prototype) {
        @SuppressWarnings("unchecked") final T[] into = prototype.length >= from.length
                ? prototype
                : (T[]) java.lang.reflect.Array.newInstance(prototype.getClass().getComponentType(), from.length);
        for (int i = 0; i < from.length; i++) {
            into[i] = transform.apply(from[i]);
        }
        return into;
    }

    public static <T> T[] copy(final T[] source) {
        return java.util.Arrays.copyOf(source, source.length);
    }

    public static byte[] copy(final byte[] source) {
        return java.util.Arrays.copyOf(source, source.length);
    }

    public static Object[][] copy(final Object[][] source) {
        if (source.length == 0) {
            return source;
        }
        final Object[][] target = new Object[source.length][source[0].length];
        for (int i = 0; i < source.length; i++) {
            target[i] = copy(source[i]);
        }
        return target;
    }

    @SafeVarargs
    public static <T> T[] concat(@Nonnull final T element, final T... rest) {
        @SuppressWarnings("unchecked") final T[] array = (T[]) java.lang.reflect.Array.newInstance(element.getClass(), 1 + rest.length);
        array[0] = element;
        System.arraycopy(rest, 0, array, 1, rest.length);
        return array;
    }

    public static <T> void reverseInPlace(final T[] array) {
        T prev;
        for (int i = 0; i < array.length / 2; i++) {
            prev = array[i];
            array[i] = array[array.length - i];
            array[array.length - 1] = prev;
        }
    }

    public static Integer[] copyNativeArray(final int[] array) {
        final Integer[] integers = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            integers[i] = array[i];
        }
        return integers;
    }

    public static Double[] copyNativeArray(final double[] array) {
        final Double[] doubles = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            doubles[i] = array[i];
        }
        return doubles;
    }

    public static void writeNativeArray(final Number[] numbers, final int[] array) {
        final int length = Math.min(numbers.length, array.length);
        for (int i = 0; i < length; i++) {
            array[i] = numbers[i].intValue();
        }
    }

    public static void writeNativeArray(final Number[] numbers, final double[] array) {
        final int length = Math.min(numbers.length, array.length);
        for (int i = 0; i < length; i++) {
            array[i] = numbers[i].doubleValue();
        }
    }

    public static <T> T[] chop(final T[] array, final int maxLength) {
        return array.length > maxLength
                ? java.util.Arrays.copyOf(array, maxLength)
                : array;
    }

    public static <T> boolean any(final T[] array, final Predicate<? super T> predicate) {
        for (int i = 0; i < array.length; i++) {
            if (predicate.test(array[i])) {
                return true;
            }
        }
        return false;
    }

    public static <T> int count(final T[] array, final Predicate<? super T> predicate) {
        int count = 0;
        for (int i = 0; i < array.length; i++) {
            if (predicate.test(array[i])) {
                count++;
            }
        }
        return count;
    }

}
