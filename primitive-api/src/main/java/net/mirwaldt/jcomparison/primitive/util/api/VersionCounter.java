package net.mirwaldt.jcomparison.primitive.util.api;

import java.util.function.IntSupplier;

public interface VersionCounter extends IntSupplier {
    int increment();
    int reset();
}
