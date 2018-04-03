package net.mirwaldt.jcomparison.primitive.mutable.versioned.api;

public interface VersionedMutableSupplier {
    VersionedMutable<?, ?> getOrCreateMutable(int type);
}
