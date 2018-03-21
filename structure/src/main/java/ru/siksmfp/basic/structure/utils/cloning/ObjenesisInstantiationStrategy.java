package ru.siksmfp.basic.structure.utils.cloning;

import ru.siksmfp.basic.structure.utils.objenesis.Objenesis;
import ru.siksmfp.basic.structure.utils.objenesis.ObjenesisStd;

public class ObjenesisInstantiationStrategy implements IInstantiationStrategy {
    private final Objenesis objenesis = new ObjenesisStd();

    public <T> T newInstance(Class<T> c) {
        return objenesis.newInstance(c);
    }

    private static ObjenesisInstantiationStrategy instance = new ObjenesisInstantiationStrategy();

    public static ObjenesisInstantiationStrategy getInstance() {
        return instance;
    }
}
