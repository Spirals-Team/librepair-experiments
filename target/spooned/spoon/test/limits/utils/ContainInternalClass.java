package spoon.test.limits.utils;


public class ContainInternalClass {
    public class InternalClass {
        public class InsideInternalClass {}
    }

    java.lang.Runnable toto = new java.lang.Runnable() {
        public void run() {
        }

        @java.lang.SuppressWarnings("unused")
        static final long serialVersionUID = 1L;
    };
}

