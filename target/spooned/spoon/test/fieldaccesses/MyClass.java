package spoon.test.fieldaccesses;


public class MyClass {
    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getGlobal();

    public void foo() {
        java.lang.Runnable r = () -> {
            spoon.test.fieldaccesses.MyClass.LOG.info("bla");
        };
    }
}

