package spoon.test.control.testclasses;


public class Fors {
    void normalFor() {
        for (int i = 0; i < 2; i++) {
        }
    }

    @java.lang.SuppressWarnings("unused")
    void multipleInit() {
        for (int i = 0, j = 0; i < 2; j++) {
        }
    }

    @java.lang.SuppressWarnings("unused")
    void empty1() {
        int i = 0;
        for (i = 0; ; i++) {
        }
    }

    @java.lang.SuppressWarnings("unused")
    void empty2() {
        int i = 0;
        for (; ; i++) {
        }
    }
}

