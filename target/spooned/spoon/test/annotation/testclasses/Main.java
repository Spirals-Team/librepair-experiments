package spoon.test.annotation.testclasses;


@spoon.test.annotation.testclasses.TestAnnotation
public class Main {
    @spoon.test.annotation.testclasses.TestAnnotation
    public Main() {
    }

    public void m(@spoon.test.annotation.testclasses.Bound(max = 8)
    int a) {
    }

    @spoon.test.annotation.testclasses.AnnotParamTypes(integer = 42, integers = { 42 }, string = "Hello World!", strings = { "Hello", "World" }, clazz = java.lang.Integer.class, classes = { java.lang.Integer.class, java.lang.String.class }, b = true, byt = 42, c = 'c', s = ((short) (42)), l = 42, f = 3.14F, d = 3.14159, e = spoon.test.annotation.testclasses.AnnotParamTypeEnum.G, ia = @spoon.test.annotation.testclasses.InnerAnnot("dd"))
    public void m1() {
    }

    public static final int INTEGER = 42;

    public static final java.lang.String STRING = "Hello World!";

    public static final java.lang.String STRING1 = "Hello";

    public static final java.lang.String STRING2 = "world";

    public static final boolean BOOLEAN = false;

    public static final byte BYTE = 42;

    public static final char CHAR = 'c';

    public static final short SHORT = 42;

    public static final short LONG = 42;

    public static final float FLOAT = 3.14F;

    public static final double DOUBLE = 3.14159;

    @spoon.test.annotation.testclasses.AnnotParamTypes(integer = spoon.test.annotation.testclasses.Main.INTEGER, integers = { spoon.test.annotation.testclasses.Main.INTEGER }, string = spoon.test.annotation.testclasses.Main.STRING, strings = { spoon.test.annotation.testclasses.Main.STRING1, spoon.test.annotation.testclasses.Main.STRING2 }, clazz = java.lang.Integer.class, classes = { java.lang.Integer.class, java.lang.String.class }, b = spoon.test.annotation.testclasses.Main.BOOLEAN, byt = spoon.test.annotation.testclasses.Main.BYTE, c = spoon.test.annotation.testclasses.Main.CHAR, s = spoon.test.annotation.testclasses.Main.SHORT, l = spoon.test.annotation.testclasses.Main.LONG, f = spoon.test.annotation.testclasses.Main.FLOAT, d = spoon.test.annotation.testclasses.Main.DOUBLE, e = spoon.test.annotation.testclasses.AnnotParamTypeEnum.G, ia = @spoon.test.annotation.testclasses.InnerAnnot("dd"))
    public void m2() {
    }

    @spoon.test.annotation.testclasses.AnnotParamTypes(integer = (spoon.test.annotation.testclasses.Main.INTEGER) + 3, integers = { (spoon.test.annotation.testclasses.Main.INTEGER) - 2, (spoon.test.annotation.testclasses.Main.INTEGER) * 3 }, string = (spoon.test.annotation.testclasses.Main.STRING) + "concatenated", strings = { (spoon.test.annotation.testclasses.Main.STRING1) + "concatenated", (spoon.test.annotation.testclasses.Main.STRING2) + "concatenated" }, clazz = java.lang.Integer.class, classes = { java.lang.Integer.class, java.lang.String.class }, b = !(spoon.test.annotation.testclasses.Main.BOOLEAN), byt = (spoon.test.annotation.testclasses.Main.BYTE) ^ 1, c = (spoon.test.annotation.testclasses.Main.CHAR) | 'd', s = (spoon.test.annotation.testclasses.Main.SHORT) / 2, l = (spoon.test.annotation.testclasses.Main.LONG) + 1, f = (spoon.test.annotation.testclasses.Main.FLOAT) * 2.0F, d = (spoon.test.annotation.testclasses.Main.DOUBLE) / 3.0, e = spoon.test.annotation.testclasses.AnnotParamTypeEnum.G, ia = @spoon.test.annotation.testclasses.InnerAnnot("dd" + "dd"))
    public void m3() {
    }

    @java.lang.Override
    public java.lang.String toString() {
        return super.toString();
    }

    @spoon.test.annotation.testclasses.AnnotArray({ java.lang.RuntimeException.class })
    public void testValueWithArray() {
    }

    @spoon.test.annotation.testclasses.AnnotArray(java.lang.RuntimeException.class)
    public void testValueWithoutArray() {
    }
}

