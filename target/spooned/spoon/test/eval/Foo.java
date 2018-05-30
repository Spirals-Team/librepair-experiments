package spoon.test.eval;


public class Foo {
    final boolean b0 = true && false;

    boolean b2 = true;

    boolean b3 = true & false;

    boolean b4 = true | false;

    boolean b5 = true ^ false;

    void foo() {
        boolean b1 = (true) ? false || (b0) : b2;
    }

    int bar() {
        final int x = 0;
        do {
        } while (x != 0 );
        if (x > (1 - 2)) {
        }
        int y = 0;
        y = x;
        while ((1 - 4) < 0) {
            return x;
        } 
    }
}

