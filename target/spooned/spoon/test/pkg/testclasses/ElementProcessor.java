package spoon.test.pkg.testclasses;


public class ElementProcessor extends spoon.processing.AbstractProcessor<spoon.reflect.declaration.CtElement> {
    public void process(spoon.reflect.declaration.CtElement element) {
        if (element instanceof spoon.reflect.declaration.CtPackage) {
            ((spoon.reflect.declaration.CtPackage) (element)).setSimpleName("newtest");
        }
    }
}

