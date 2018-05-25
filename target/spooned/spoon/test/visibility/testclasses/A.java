package spoon.test.visibility.testclasses;


public class A<T> {
    public class B {
        public static final int i = 0;

        public boolean m(java.lang.Object o) {
            return (spoon.test.visibility.testclasses.A.B.i) == 0;
        }
    }

    public class C<T> {}

    public boolean instanceOf(java.lang.Object o) {
        return o instanceof spoon.test.visibility.testclasses.A.B;
    }

    public spoon.test.visibility.testclasses.A<T>.C<T> returnType() {
        return new C<T>();
    }

    public void aMethod() {
        class D {}
        new D();
    }
}

