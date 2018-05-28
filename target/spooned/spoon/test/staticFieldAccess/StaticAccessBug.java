package spoon.test.staticFieldAccess;


public class StaticAccessBug {
    private java.lang.Runnable test() throws java.lang.Exception {
        return new java.util.concurrent.Callable<java.lang.Runnable>() {
            @java.lang.Override
            public java.lang.Runnable call() throws java.lang.Exception {
                return new java.lang.Runnable() {
                    private static final long C = 3;

                    public void run() {
                        long test = C;
                    }
                };
            }
        }.call();
    }

    public void references() {
        java.lang.String a = spoon.test.staticFieldAccess.internal.Extends.MY_STATIC_VALUE;
        java.lang.String b = spoon.test.staticFieldAccess.internal.Extends.MY_OTHER_STATIC_VALUE;
        new spoon.test.staticFieldAccess.internal.Extends().test();
    }
}

