package spoon.test.targeted.testclasses;


public class Foo<T> extends spoon.test.targeted.testclasses.SuperClass {
    private int i;

    private int j;

    static int k;

    spoon.test.targeted.testclasses.Foo foo;

    spoon.test.targeted.testclasses.Bar bar;

    spoon.test.targeted.testclasses.Foo.Fii.Fuu fuu;

    static final int p;

    static {
        p = 0;
    }

    public void m() {
        int x;
        x = this.k;
        x = spoon.test.targeted.testclasses.Foo.k;
        x = spoon.test.targeted.testclasses.Foo.k;
        this.k = x;
        spoon.test.targeted.testclasses.Foo.k = x;
        spoon.test.targeted.testclasses.Foo.k = x;
        x = spoon.test.targeted.testclasses.Bar.FIELD;
        x = spoon.test.targeted.testclasses.Bar.FIELD;
        spoon.test.targeted.testclasses.Bar.FIELD = x;
        spoon.test.targeted.testclasses.Bar.FIELD = x;
    }

    public void field() {
        int x = this.i;
        x = i;
        x = this.bar.i;
        x = bar.i;
        x = this.o;
        x = o;
        x = fuu.p;
    }

    public void inv() {
        new spoon.test.targeted.testclasses.Foo(0, 0).method();
        foo.method();
        this.method();
        method();
        bar.methodBar();
        fuu.method();
        superMethod();
    }

    public void invStatic() {
        new spoon.test.targeted.testclasses.Foo(0, 0).staticMethod();
        foo.staticMethod();
        this.staticMethod();
        spoon.test.targeted.testclasses.Foo.staticMethod();
        spoon.test.targeted.testclasses.Foo.staticMethod();
        spoon.test.targeted.testclasses.Bar.staticMethodBar();
        spoon.test.targeted.testclasses.Bar.staticMethodBar();
        spoon.test.targeted.testclasses.Foo.Fii.Fuu.m();
    }

    private spoon.test.targeted.testclasses.Foo method() {
        class NestedTypeScanner {
            spoon.test.targeted.testclasses.Foo type;

            public void checkType(spoon.test.targeted.testclasses.Foo type) {
                this.checkType(type);
            }

            public void checkField() {
                spoon.test.targeted.testclasses.Foo inner = this.type;
                inner = type;
            }
        }
        return new spoon.test.targeted.testclasses.Foo(0, 0) {
            int i;

            @java.lang.Override
            public void m() {
                spoon.test.targeted.testclasses.Foo.this.invStatic();
                this.invStatic();
            }

            public void invStatic() {
                int inner = spoon.test.targeted.testclasses.Foo.this.i;
                inner = this.i;
                inner = i;
            }
        };
    }

    private static void staticMethod() {
    }

    public Foo(int i, int k) {
        this.i = i;
        j = k;
    }

    class InnerClass {
        int i;

        public void innerInv() {
            inv();
            spoon.test.targeted.testclasses.Foo.this.inv();
            spoon.test.targeted.testclasses.Foo.staticMethod();
            spoon.test.targeted.testclasses.Foo.staticMethod();
            superMethod();
            spoon.test.targeted.testclasses.Foo.this.superMethod();
            method();
            this.method();
        }

        public void innerField() {
            int x = this.i;
            x = i;
            x = spoon.test.targeted.testclasses.Foo.this.i;
            x = spoon.test.targeted.testclasses.Foo.k;
            x = spoon.test.targeted.testclasses.Foo.this.o;
            x = o;
        }

        void method() {
        }
    }

    public static class Fii {
        public static class Fuu {
            int p;

            static void m() {
            }

            void method() {
            }
        }
    }
}

