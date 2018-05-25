package spoon.test.arrays;


public class ArrayClass {
    int[][][] i = new @spoon.test.arrays.ArrayClass.TypeAnnotation(integer = 1)
    int[0][][];

    int[] x;

    void m() {
        int[][][] i = new int[0][][];
        i[0] = null;
    }

    @java.lang.annotation.Target({ java.lang.annotation.ElementType.TYPE_USE })
    @interface TypeAnnotation {
        int integer() default 0;
    }
}

