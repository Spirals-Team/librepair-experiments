package spoon.test.constructorcallnewclass.testclasses;


public class Foo {
    int i;

    public Foo() {
    }

    public Foo(int i) {
        this.i = i;
    }

    public void m() {
        new java.lang.String();
        new java.lang.String("");
        new spoon.test.constructorcallnewclass.testclasses.Foo();
        new spoon.test.constructorcallnewclass.testclasses.Foo(42);
    }

    public void m2() {
        new java.lang.Object() {};
        new spoon.test.constructorcallnewclass.testclasses.Foo.Bar() {};
        new spoon.test.constructorcallnewclass.testclasses.Foo.Tacos<java.lang.String>() {};
        new spoon.test.constructorcallnewclass.testclasses.Foo.BarImpl(1) {};
    }

    public interface Bar {}

    public interface Tacos<K> {}

    public class BarImpl implements spoon.test.constructorcallnewclass.testclasses.Foo.Bar {
        int i;

        public BarImpl(int i) {
            this.i = i;
        }
    }
}

