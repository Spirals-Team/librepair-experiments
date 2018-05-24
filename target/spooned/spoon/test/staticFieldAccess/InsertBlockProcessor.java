package spoon.test.staticFieldAccess;


public class InsertBlockProcessor extends spoon.processing.AbstractProcessor<spoon.reflect.declaration.CtMethod<?>> {
    @java.lang.Override
    public boolean isToBeProcessed(spoon.reflect.declaration.CtMethod<?> candidate) {
        return (super.isToBeProcessed(candidate)) && ((candidate.getBody()) != null);
    }

    @java.lang.Override
    public void process(spoon.reflect.declaration.CtMethod<?> element) {
        spoon.reflect.code.CtBlock block = new spoon.support.reflect.code.CtBlockImpl();
        block.getStatements().add(element.getBody());
        element.setBody(block);
    }
}

