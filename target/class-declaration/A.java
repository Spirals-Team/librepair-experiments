

class A extends example.B {
    private java.lang.Runnable runnable;

    public void foo() {
        synchronized(lock) {
            runnable = new java.lang.Runnable() {
                @java.lang.Override
                public void run() {
                }
            };
        }
    }
}

