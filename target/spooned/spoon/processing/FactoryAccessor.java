package spoon.processing;


public interface FactoryAccessor {
    spoon.reflect.factory.Factory getFactory();

    void setFactory(spoon.reflect.factory.Factory factory);
}

