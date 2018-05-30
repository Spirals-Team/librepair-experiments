package spoon.test.method_overriding.testclasses;


public class A<U> {
    public A() {
    }

    spoon.test.method_overriding.testclasses.A<U> m1(spoon.test.method_overriding.testclasses.C c) {
        return null;
    }

    <T extends spoon.test.method_overriding.testclasses.A<U>> T m2(spoon.test.method_overriding.testclasses.C c) {
        return null;
    }

    void m3(java.util.List<? super spoon.test.method_overriding.testclasses.C> c) {
    }

    void m4(java.util.List<? extends spoon.test.method_overriding.testclasses.A<U>> c) {
    }

    void m5(U u) {
    }
}

