package spoon.testing.processors;


public class FooToBarProcessor extends spoon.processing.AbstractProcessor<spoon.reflect.declaration.CtClass<?>> {
    public FooToBarProcessor() {
    }

    @java.lang.Override
    public boolean isToBeProcessed(spoon.reflect.declaration.CtClass<?> candidate) {
        return "Foo".equals(candidate.getSimpleName());
    }

    @java.lang.Override
    public void process(spoon.reflect.declaration.CtClass<?> element) {
        element.setSimpleName("Bar");
    }
}

