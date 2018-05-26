package spoon.processing;


public abstract class AbstractProblemFixer<T extends spoon.reflect.declaration.CtElement> implements spoon.processing.ProblemFixer<T> {
    spoon.reflect.factory.Factory factory;

    public AbstractProblemFixer() {
    }

    public spoon.reflect.factory.Factory getFactory() {
        return factory;
    }

    public void setFactory(spoon.reflect.factory.Factory factory) {
        this.factory = factory;
    }
}

