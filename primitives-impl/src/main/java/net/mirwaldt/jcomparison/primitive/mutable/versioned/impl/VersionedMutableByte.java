package net.mirwaldt.jcomparison.primitive.mutable.versioned.impl;

import net.mirwaldt.jcomparison.primitive.mutable.api.Mutable;
import net.mirwaldt.jcomparison.primitive.mutable.api.MutableByte;
import net.mirwaldt.jcomparison.primitive.mutable.versioned.api.VersionedMutable;
import net.mirwaldt.jcomparison.primitive.util.api.VersionCounter;

public class VersionedMutableByte implements MutableByte, VersionedMutable<Byte, MutableByte> {
    private final MutableByte mutableByte;
    private final VersionCounter versionCounter;

    public VersionedMutableByte(MutableByte mutableByte, VersionCounter versionCounter) {
        this.mutableByte = mutableByte;
        this.versionCounter = versionCounter;
    }

    @Override
    public byte getValue() {
        return mutableByte.getValue();
    }

    @Override
    public Mutable<Byte, MutableByte> copy() {
        return mutableByte.copy();
    }

    @Override
    public void copyTo(MutableByte otherMutable) {
        mutableByte.copyTo(otherMutable);
    }

    @Override
    public byte getType() {
        return mutableByte.getType();
    }

    @Override
    public Byte get() {
        return mutableByte.get();
    }
    
    @Override
    public void setValue(byte value) {
        if(getValue() != value) {
            mutableByte.setValue(value);
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
