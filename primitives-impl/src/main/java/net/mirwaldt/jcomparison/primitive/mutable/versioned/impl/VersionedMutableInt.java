package net.mirwaldt.jcomparison.primitive.mutable.versioned.impl;

import net.mirwaldt.jcomparison.primitive.mutable.api.Mutable;
import net.mirwaldt.jcomparison.primitive.mutable.api.MutableInt;
import net.mirwaldt.jcomparison.primitive.mutable.versioned.api.VersionedMutable;
import net.mirwaldt.jcomparison.primitive.util.api.VersionCounter;

public class VersionedMutableInt implements MutableInt, VersionedMutable<Integer, MutableInt> {
    private final MutableInt mutableInt;
    private final VersionCounter versionCounter;

    public VersionedMutableInt(MutableInt mutableInt, VersionCounter versionCounter) {
        this.mutableInt = mutableInt;
        this.versionCounter = versionCounter;
    }

    @Override
    public int getValue() {
        return mutableInt.getValue();
    }

    @Override
    public Mutable<Integer, MutableInt> copy() {
        return mutableInt.copy();
    }

    @Override
    public void copyTo(MutableInt otherMutable) {
        mutableInt.copyTo(otherMutable);
    }

    @Override
    public byte getType() {
        return mutableInt.getType();
    }

    @Override
    public Integer get() {
        return mutableInt.get();
    }
    
    @Override
    public void setValue(int value) {
        if(getValue() != value) {
            mutableInt.setValue(value);
            versionCounter.increment();
        }
    }

    @Override
    public int getVersion() {
        return versionCounter.getAsInt();
    }

    @Override
    public int resetVersion() {
        return versionCounter.reset();
    }
}
