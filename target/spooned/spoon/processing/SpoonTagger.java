package spoon.processing;


public class SpoonTagger extends spoon.processing.AbstractManualProcessor {
    @java.lang.Override
    public void process() {
        spoon.reflect.declaration.CtClass<?> spoon = getFactory().Class().create("spoon.Spoon");
    }
}

