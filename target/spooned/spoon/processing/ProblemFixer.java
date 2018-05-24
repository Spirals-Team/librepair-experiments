package spoon.processing;


public interface ProblemFixer<T extends spoon.reflect.declaration.CtElement> extends spoon.processing.FactoryAccessor {
    java.lang.String getDescription();

    java.lang.String getLabel();

    spoon.reflect.Changes run(T element);
}

