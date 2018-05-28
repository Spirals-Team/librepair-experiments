package spoon.test.visibility.testclasses;


public class A<T> {
    public class B {
        public static final int i = 0;

        public boolean m(Object o) {
            return (A.B.i) == 0;
        }
    }

    public class C<T> {}

    public boolean instanceOf(Object o) {
        return o instanceof A.B;
    }

    public A<T>.C<T> returnType() {
        return new C<T>();
    }

    public void aMethod() {
        class D {}
        new D();
    }
}

