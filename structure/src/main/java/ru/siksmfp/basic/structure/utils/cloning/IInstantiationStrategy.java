package ru.siksmfp.basic.structure.utils.cloning;

public interface IInstantiationStrategy {
    <T> T newInstance(final Class<T> c);
}
