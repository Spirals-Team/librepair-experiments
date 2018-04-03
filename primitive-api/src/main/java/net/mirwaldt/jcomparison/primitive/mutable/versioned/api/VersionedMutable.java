package net.mirwaldt.jcomparison.primitive.mutable.versioned.api;

import net.mirwaldt.jcomparison.primitive.mutable.api.Mutable;

public interface VersionedMutable<Type, MutableType> extends Mutable<Type, MutableType> {
    int getVersion();
    int resetVersion();
}
