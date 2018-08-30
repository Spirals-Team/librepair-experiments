package ru.job4j.cache;

/**
 * Model for cache
 */
public abstract class CacheModel {

    abstract String getName();

    abstract void setName(String name);

    abstract Integer getId();

    abstract int getVersion();

    abstract void setVersion(int version);
}
