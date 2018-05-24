package spoon.test.replace.testclasses;


class Foo {
    int i;

    void foo() {
        int x = 3;
        int z;
        z = x + 1;
        java.lang.System.out.println(z);
    }

    void bar() {
        int y = 4;
    }

    public void retry() {
        new spoon.test.replace.testclasses.Foo();
    }

    private void statements() {
        java.lang.String a = "";
        java.lang.System.out.println(a.toLowerCase());
    }
}

