package spoon.test.parent;


class Foo {
    void foo() {
        int x = 3;
        x = x + 1;
    }

    int bar;

    spoon.test.parent.Foo foo;

    int nullParent() {
        foo.bar = 0;
        assert true : "message";
        for (int i = 0; i < 10; i++) {
        }
        if (true) {
        }else {
        }
        foo.foo();
        class Bar {
            int bar = 0;
        }
        return 0;
    }

    void m() {
        java.lang.String one;
        java.lang.String two;
        java.lang.String three;
        one = two = three = "";
        for (int k = 0; k < 0; k++) {
        }
    }

    void internalClass() {
        class T {
            void m() {
                new java.lang.Object() {
                    void m() {
                        java.lang.String one;
                        java.lang.String two;
                        java.lang.String three;
                    }
                };
            }
        }
    }
}

