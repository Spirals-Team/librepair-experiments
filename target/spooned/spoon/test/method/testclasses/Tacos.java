package spoon.test.method.testclasses;


public class Tacos {
    public <T> void method1(T t) {
    }

    public <T extends java.lang.String> void method1(T t) {
    }

    public <T extends java.lang.Integer> void method1(T t) {
    }
}

