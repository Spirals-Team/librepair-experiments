package spoon.test.lambda.testclasses;


public class Panini {
    private static <T1, T2, R> java.util.function.Function<java.lang.Object[], R> toFunction(java.util.function.BiFunction<? super T1, ? super T2, ? extends R> biFunction) {
        java.util.Objects.requireNonNull(biFunction);
        return ( a) -> {
            if ((a.length) != 2) {
                throw new java.lang.IllegalArgumentException(("Array of size 2 expected but got " + (a.length)));
            }
            return ((java.util.function.BiFunction<java.lang.Object, java.lang.Object, R>) (biFunction)).apply(a[0], a[1]);
        };
    }
}

