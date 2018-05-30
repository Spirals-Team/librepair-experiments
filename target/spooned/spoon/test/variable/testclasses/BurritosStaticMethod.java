package spoon.test.variable.testclasses;


import spoon.test.variable.testclasses.BurritosStaticMethod;

import static spoon.test.variable.testclasses.BurritosStaticMethod.toto;


public class BurritosStaticMethod {
    static void toto() {
    }

    void foo() {
        java.lang.Object spoon = null;
        new java.lang.Thread(new java.lang.Runnable() {
            @java.lang.Override
            public void run() {
                toto();
            }
        });
    }
}

