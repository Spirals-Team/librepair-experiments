package spoon.test.filters;


@java.lang.SuppressWarnings("bar")
class Foo {
    int i;

    void foo() {
        int x = 3;
        int z;
        z = x + (i);
        java.lang.System.out.println(z);
    }

    @java.lang.SuppressWarnings("foo")
    int bar() {
        if (0 == 1) {
            throw new java.lang.RuntimeException();
        }
        return i;
    }
}

