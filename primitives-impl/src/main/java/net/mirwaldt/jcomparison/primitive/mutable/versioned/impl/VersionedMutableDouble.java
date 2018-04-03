package net.mirwaldt.jcomparison.primitive.mutable.versioned.impl;

import net.mirwaldt.jcomparison.primitive.mutable.api.Mutable;
import net.mirwaldt.jcomparison.primitive.mutable.api.MutableDouble;
import net.mirwaldt.jcomparison.primitive.mutable.versioned.api.VersionedMutable;
import net.mirwaldt.jcomparison.primitive.util.api.VersionCounter;

public class VersionedMutableDouble implements MutableDouble, VersionedMutable<Double, MutableDouble> {
    private final MutableDouble mutableDouble;
    private final VersionCounter versionCounter;

    public VersionedMutableDouble(MutableDouble mutableDouble, VersionCounter versionCounter) {
        this.mutableDouble = mutableDouble;
        this.versionCounter = versionCounter;
    }

    @Override
    public double getValue() {
        return mutableDouble.getValue();
    }

    @Override
    public Mutable<Double, MutableDouble> copy() {
        return mutableDouble.copy();
    }

    @Override
    public void copyTo(MutableDouble otherMutable) {
        mutableDouble.copyTo(otherMutable);
    }

    @Override
    public byte getType() {
        return mutableDouble.getType();
    }

    @Override
    public Double get() {
        return mutableDouble.get();
    }
    
    @Override
    public void setValue(double value) {
        if(getValue() != value) {
            mutableDouble.setValue(value);
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
