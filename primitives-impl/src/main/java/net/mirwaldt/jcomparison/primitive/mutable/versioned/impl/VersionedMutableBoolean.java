package net.mirwaldt.jcomparison.primitive.mutable.versioned.impl;

import net.mirwaldt.jcomparison.primitive.mutable.api.Mutable;
import net.mirwaldt.jcomparison.primitive.mutable.api.MutableBoolean;
import net.mirwaldt.jcomparison.primitive.mutable.versioned.api.VersionedMutable;
import net.mirwaldt.jcomparison.primitive.util.api.VersionCounter;

public class VersionedMutableBoolean implements MutableBoolean, VersionedMutable<Boolean, MutableBoolean> {
    private final MutableBoolean mutableBoolean;
    private final VersionCounter versionCounter;

    public VersionedMutableBoolean(MutableBoolean mutableBoolean, VersionCounter versionCounter) {
        this.mutableBoolean = mutableBoolean;
        this.versionCounter = versionCounter;
    }

    @Override
    public boolean getValue() {
        return mutableBoolean.getValue();
    }

    @Override
    public Mutable<Boolean, MutableBoolean> copy() {
        return mutableBoolean.copy();
    }

    @Override
    public void copyTo(MutableBoolean otherMutable) {
        mutableBoolean.copyTo(otherMutable);
    }

    @Override
    public byte getType() {
        return mutableBoolean.getType();
    }

    @Override
    public Boolean get() {
        return mutableBoolean.get();
    }
    
    @Override
    public void setValue(boolean value) {
        if(getValue() != value) {
            mutableBoolean.setValue(value);
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
