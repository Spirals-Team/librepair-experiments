package net.mirwaldt.jcomparison.primitive.mutable.versioned.impl;

import net.mirwaldt.jcomparison.primitive.mutable.api.Mutable;
import net.mirwaldt.jcomparison.primitive.mutable.api.MutableLong;
import net.mirwaldt.jcomparison.primitive.mutable.versioned.api.VersionedMutable;
import net.mirwaldt.jcomparison.primitive.util.api.VersionCounter;

public class VersionedMutableLong implements MutableLong, VersionedMutable<Long, MutableLong> {
    private final MutableLong mutableLong;
    private final VersionCounter versionCounter;

    public VersionedMutableLong(MutableLong mutableLong, VersionCounter versionCounter) {
        this.mutableLong = mutableLong;
        this.versionCounter = versionCounter;
    }

    @Override
    public long getValue() {
        return mutableLong.getValue();
    }

    @Override
    public Mutable<Long, MutableLong> copy() {
        return mutableLong.copy();
    }

    @Override
    public void copyTo(MutableLong otherMutable) {
        mutableLong.copyTo(otherMutable);
    }

    @Override
    public byte getType() {
        return mutableLong.getType();
    }

    @Override
    public Long get() {
        return mutableLong.get();
    }
    
    @Override
    public void setValue(long value) {
        if(getValue() != value) {
            mutableLong.setValue(value);
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
