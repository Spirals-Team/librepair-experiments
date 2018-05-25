package spoon.test.method_overriding.testclasses;


public class B<S, R extends S> extends spoon.test.method_overriding.testclasses.A<S> {
    public B() {
    }

    @java.lang.Override
    spoon.test.method_overriding.testclasses.B<S, R> m1(spoon.test.method_overriding.testclasses.C c) {
        return null;
    }

    @java.lang.Override
    <T extends spoon.test.method_overriding.testclasses.A<S>> T m2(spoon.test.method_overriding.testclasses.C c) {
        return null;
    }

    @java.lang.Override
    void m3(java.util.List<? super spoon.test.method_overriding.testclasses.C> c) {
    }

    @java.lang.Override
    void m5(S u) {
        super.m5(u);
    }

    @java.lang.Override
    void m4(java.util.List<? extends spoon.test.method_overriding.testclasses.A<S>> c) {
    }
}

