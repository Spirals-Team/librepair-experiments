package spoon.test.method_overriding.testclasses;


public class C extends spoon.test.method_overriding.testclasses.B<java.io.InputStream, java.io.FilterInputStream> {
    public C() {
    }

    @java.lang.Override
    spoon.test.method_overriding.testclasses.B m1(spoon.test.method_overriding.testclasses.C c) {
        return null;
    }

    @java.lang.Override
    spoon.test.method_overriding.testclasses.B<java.io.InputStream, java.io.FilterInputStream> m2(spoon.test.method_overriding.testclasses.C c) {
        return null;
    }

    @java.lang.Override
    void m3(java.util.List<? super spoon.test.method_overriding.testclasses.C> c) {
        super.m3(c);
    }

    @java.lang.Override
    void m4(java.util.List<? extends spoon.test.method_overriding.testclasses.A<java.io.InputStream>> c) {
    }

    @java.lang.Override
    void m5(java.io.InputStream u) {
        super.m5(u);
    }
}

