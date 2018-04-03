package net.mirwaldt.jcomparison.core.facade;

import net.mirwaldt.jcomparison.core.array.impl.CheckingArrayWrapperDecorator;
import net.mirwaldt.jcomparison.core.array.impl.DefaultArrayWrapper;

import java.util.function.Function;
import java.util.function.Predicate;

public class ArrayWrapperFunctions {
    public static Function<Object, Object> defaultArrayWrapperFunction() {
        return (array) -> new DefaultArrayWrapper(array);
    }

    public static Function<Object, Object> checkingArrayWrapperFunction() {
        return new CheckingArrayWrapperDecorator(
                (object) -> object != null && object.getClass().isArray(),
                defaultArrayWrapperFunction());
    }

    public static Function<Object, Object> checkingArrayWrapperDecorator(
            Predicate<Object> isArrayPredicate, Function<Object, Object> arrayWrapperFunction) {
        return new CheckingArrayWrapperDecorator(isArrayPredicate, arrayWrapperFunction);
    }
}
