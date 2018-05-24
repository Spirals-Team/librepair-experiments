package spoon.test.processing.testclasses;


public abstract class GenericCtTypeProcessor<T extends spoon.reflect.declaration.CtType> extends spoon.processing.AbstractProcessor<T> {
    public GenericCtTypeProcessor(java.lang.Class<T> zeClass) {
        super.addProcessedElementType(zeClass);
    }

    public java.util.List<T> elements = new java.util.ArrayList<T>();

    @java.lang.Override
    public void process(T element) {
        elements.add(element);
    }
}

