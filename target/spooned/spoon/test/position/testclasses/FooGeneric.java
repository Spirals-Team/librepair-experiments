package spoon.test.position.testclasses;


public class FooGeneric<T extends java.lang.Object> {
    public final T variable = null;

    @java.lang.Deprecated
    public static <S> S m(int parm1) {
        return null;
    }

    int mWithDoc(int parm1) {
        return parm1;
    }

    public static final int mWithLine(int parm1) {
        return parm1;
    }

    public FooGeneric(int arg1) {
    }
}

