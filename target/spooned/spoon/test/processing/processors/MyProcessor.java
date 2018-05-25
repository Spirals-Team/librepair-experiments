package spoon.test.processing.processors;


public class MyProcessor extends spoon.processing.AbstractProcessor<spoon.reflect.declaration.CtElement> {
    private boolean shouldStayAtFalse;

    @java.lang.Override
    public void process(spoon.reflect.declaration.CtElement element) {
        interrupt();
        shouldStayAtFalse = true;
    }

    public boolean isShouldStayAtFalse() {
        return shouldStayAtFalse;
    }
}

