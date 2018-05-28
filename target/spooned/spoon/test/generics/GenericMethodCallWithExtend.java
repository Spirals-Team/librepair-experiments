package spoon.test.generics;


public class GenericMethodCallWithExtend {
    public static <E> void tmp() {
    }

    @java.lang.SafeVarargs
    public static <E extends java.lang.Enum<E>> long methode(E... values) {
        spoon.test.generics.GenericMethodCallWithExtend.<E>tmp();
        return 2L;
    }
}

