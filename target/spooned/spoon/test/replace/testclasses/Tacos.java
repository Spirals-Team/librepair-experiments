package spoon.test.replace.testclasses;


public class Tacos<T> {
    java.lang.String field;

    public int m() {
        return 1;
    }

    public spoon.test.replace.testclasses.Tacos<java.lang.Integer> m2() {
        return new spoon.test.replace.testclasses.Tacos<>();
    }

    public void m3(java.lang.String param) {
        java.lang.System.err.println(param);
    }
}

