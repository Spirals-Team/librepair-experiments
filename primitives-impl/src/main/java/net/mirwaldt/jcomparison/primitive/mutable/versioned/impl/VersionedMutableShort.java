package net.mirwaldt.jcomparison.primitive.mutable.versioned.impl;

import net.mirwaldt.jcomparison.primitive.mutable.api.Mutable;
import net.mirwaldt.jcomparison.primitive.mutable.api.MutableShort;
import net.mirwaldt.jcomparison.primitive.mutable.versioned.api.VersionedMutable;
import net.mirwaldt.jcomparison.primitive.util.api.VersionCounter;

public class VersionedMutableShort implements MutableShort, VersionedMutable<Short, MutableShort> {
    private final MutableShort mutableShort;
    private final VersionCounter versionCounter;

    public VersionedMutableShort(MutableShort mutableBoolean, VersionCounter versionCounter) {
        this.mutableShort = mutableBoolean;
        this.versionCounter = versionCounter;
    }

    @Override
    public short getValue() {
        return mutableShort.getValue();
    }

    @Override
    public Mutable<Short, MutableShort> copy() {
        return mutableShort.copy();
    }

    @Override
    public void copyTo(MutableShort otherMutable) {
        mutableShort.copyTo(otherMutable);
    }

    @Override
    public byte getType() {
        return mutableShort.getType();
    }

    @Override
    public Short get() {
        return mutableShort.get();
    }
    
    @Override
    public void setValue(short value) {
        if(getValue() != value) {
            mutableShort.setValue(value);
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
