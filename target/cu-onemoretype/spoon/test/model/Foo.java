package spoon.test.model;


public class Foo extends spoon.test.model.Bar {
    int i;

    void fooMethod() {
    }

    Foo() {
    }

    @java.lang.Override
    void m() {
    }

    void useInner(spoon.test.model.Baz.Inner inner) {
    }
}

class Bar extends spoon.test.model.Baz {}

class Baz {
    int j;

    void bazMethod() {
    }

    void m() {
    }

    class Inner {}
}

class Bla {}

