package spoon.test.ctClass.testclasses;


public class Pozole {
    public void m() {
        class Cook {
            public Cook() {
            }

            public void m() {
                final java.lang.Class<Cook> cookClass = Cook.class;
            }
        }
        new Cook();
    }
}

