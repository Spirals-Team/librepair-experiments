package net.mirwaldt.jcomparison.core.util.supplier.impl;

import net.mirwaldt.jcomparison.core.util.supplier.api.ThreadSafeSupplier;

import java.util.function.Supplier;

public class ThreadLocalSupplier<Type> implements Supplier<Type> {
    private final ThreadSafeSupplier<Type> supplier;
    private final ThreadLocal<Type> threadLocalSupplied = new ThreadLocal<>();

    public ThreadLocalSupplier(ThreadSafeSupplier<Type> supplier) {
        this.supplier = supplier;
    }

    @Override
    public Type get() {
        Type supplied = threadLocalSupplied.get();
        if(supplied == null) {
            supplied = supplier.get();
            threadLocalSupplied.set(supplied);
        }
        return supplied;
    }
}
