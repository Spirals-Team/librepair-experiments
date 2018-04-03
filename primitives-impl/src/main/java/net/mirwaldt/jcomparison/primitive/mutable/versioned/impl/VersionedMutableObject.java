package net.mirwaldt.jcomparison.primitive.mutable.versioned.impl;

import net.mirwaldt.jcomparison.primitive.mutable.api.Mutable;
import net.mirwaldt.jcomparison.primitive.mutable.api.MutableObject;
import net.mirwaldt.jcomparison.primitive.mutable.versioned.api.VersionedMutable;
import net.mirwaldt.jcomparison.primitive.util.api.VersionCounter;

public class VersionedMutableObject implements MutableObject, VersionedMutable<Object, MutableObject> {
    private final MutableObject mutableDouble;
    private final VersionCounter versionCounter;

    public VersionedMutableObject(MutableObject mutableDouble, VersionCounter versionCounter) {
        this.mutableDouble = mutableDouble;
        this.versionCounter = versionCounter;
    }

    @Override
    public Object getObject() {
        return mutableDouble.getObject();
    }

    @Override
    public void setObject(Object value) {
        if ((getObject() != null && getObject().equals(value)) || getObject() != value) {
            mutableDouble.setObject(value);
            versionCounter.increment();
        }
    }

    @Override
    public Mutable<Object, MutableObject> copy() {
        return mutableDouble.copy();
    }

    @Override
    public void copyTo(MutableObject otherMutable) {
        mutableDouble.copyTo(otherMutable);
    }

    @Override
    public byte getType() {
        return mutableDouble.getType();
    }

    @Override
    public Object get() {
        return mutableDouble.get();
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
