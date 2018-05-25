package spoon.test.variable.testclasses;


import spoon.test.variable.testclasses.ForStaticVariables;
import spoon.test.variable.testclasses.MultiBurritos;

import static spoon.Launcher.SPOONED_CLASSES;
import static spoon.test.variable.testclasses.ForStaticVariables.foo;
import static spoon.test.variable.testclasses.MultiBurritos.toto;


public class MultiBurritos {
    static java.lang.Object spoon = "bla";

    java.util.List<java.lang.String> Launcher;

    static void toto() {
    }

    void bar() {
        java.lang.Object spoon = null;
        java.lang.Object x = SPOONED_CLASSES;
        Launcher.isEmpty();
    }

    void bidule() {
        new java.lang.Thread(new java.lang.Runnable() {
            @java.lang.Override
            public void run() {
                toto();
                MultiBurritos.spoon = "truc";
                foo();
            }
        });
    }
}

