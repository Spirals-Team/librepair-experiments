package spoon.test.refactoring;


public class ThisTransformationProcessor extends spoon.processing.AbstractProcessor<spoon.reflect.declaration.CtClass<?>> {
    @java.lang.Override
    public void process(spoon.reflect.declaration.CtClass<?> element) {
        spoon.refactoring.Refactoring.changeTypeName(element, ((element.getSimpleName()) + "X"));
    }
}

