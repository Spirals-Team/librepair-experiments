package net.mirwaldt.jcomparison.core.array.impl;

import java.util.function.Function;
import java.util.function.Predicate;

public class CheckingArrayWrapperDecorator implements Function<Object, Object> {
    private final Predicate<Object> isArrayPredicate;
    private final Function<Object, Object> arrayWrapperFunction;

    public CheckingArrayWrapperDecorator(Predicate<Object> isArrayPredicate, Function<Object, Object> arrayWrapperFunction) {
        this.isArrayPredicate = isArrayPredicate;
        this.arrayWrapperFunction = arrayWrapperFunction;
    }

    @Override
    public Object apply(Object object) {
        if(isArrayPredicate.test(object)) {
            return arrayWrapperFunction.apply(object);
        } else {
            return object;
        }
    }
}
