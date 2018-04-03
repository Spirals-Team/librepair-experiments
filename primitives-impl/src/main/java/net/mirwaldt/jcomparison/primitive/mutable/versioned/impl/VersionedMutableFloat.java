package net.mirwaldt.jcomparison.primitive.mutable.versioned.impl;

import net.mirwaldt.jcomparison.primitive.mutable.api.Mutable;
import net.mirwaldt.jcomparison.primitive.mutable.api.MutableFloat;
import net.mirwaldt.jcomparison.primitive.mutable.versioned.api.VersionedMutable;
import net.mirwaldt.jcomparison.primitive.util.api.VersionCounter;

public class VersionedMutableFloat implements MutableFloat, VersionedMutable<Float, MutableFloat> {
    private final MutableFloat mutableFloat;
    private final VersionCounter versionCounter;

    public VersionedMutableFloat(MutableFloat mutableFloat, VersionCounter versionCounter) {
        this.mutableFloat = mutableFloat;
        this.versionCounter = versionCounter;
    }

    @Override
    public float getValue() {
        return mutableFloat.getValue();
    }

    @Override
    public Mutable<Float, MutableFloat> copy() {
        return mutableFloat.copy();
    }

    @Override
    public void copyTo(MutableFloat otherMutable) {
        mutableFloat.copyTo(otherMutable);
    }

    @Override
    public byte getType() {
        return mutableFloat.getType();
    }

    @Override
    public Float get() {
        return mutableFloat.get();
    }
    
    @Override
    public void setValue(float value) {
        if(getValue() != value) {
            mutableFloat.setValue(value);
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
