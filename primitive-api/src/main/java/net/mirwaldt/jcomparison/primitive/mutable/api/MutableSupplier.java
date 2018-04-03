package net.mirwaldt.jcomparison.primitive.mutable.api;

public interface MutableSupplier {
    Mutable<?, ?> getOrCreateMutable(int type);
}
