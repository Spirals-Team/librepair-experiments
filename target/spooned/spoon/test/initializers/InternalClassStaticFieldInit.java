package spoon.test.initializers;


public class InternalClassStaticFieldInit {
    static class InternalClass {
        static final java.lang.String tmp;

        static {
            tmp = "nop";
        }
    }
}

