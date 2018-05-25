package spoon.test.visibility.testclasses;


public class A2 {
    public class B {
        public static final int i = 0;

        public boolean m(java.lang.Object o) {
            return (spoon.test.visibility.testclasses.A2.B.i) == 0;
        }
    }

    public class C<T> {}

    public boolean instanceOf(java.lang.Object o) {
        return o instanceof spoon.test.visibility.testclasses.A2.B;
    }

    public spoon.test.visibility.testclasses.A2.C<java.lang.String> returnType() {
        return new spoon.test.visibility.testclasses.A2.C<java.lang.String>();
    }

    public spoon.test.visibility.testclasses.Foo<java.lang.String>.Bar<java.lang.String> returnType2(java.lang.String s) {
        return null;
    }

    public void aMethod() {
        class D {}
        new D();
    }
}

