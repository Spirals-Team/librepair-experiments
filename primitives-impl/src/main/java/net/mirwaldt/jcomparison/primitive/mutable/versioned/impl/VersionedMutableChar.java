package net.mirwaldt.jcomparison.primitive.mutable.versioned.impl;

import net.mirwaldt.jcomparison.primitive.mutable.api.Mutable;
import net.mirwaldt.jcomparison.primitive.mutable.api.MutableChar;
import net.mirwaldt.jcomparison.primitive.mutable.versioned.api.VersionedMutable;
import net.mirwaldt.jcomparison.primitive.util.api.VersionCounter;

public class VersionedMutableChar implements MutableChar, VersionedMutable<Character, MutableChar> {
    private final MutableChar mutableChar;
    private final VersionCounter versionCounter;

    public VersionedMutableChar(MutableChar mutableChar, VersionCounter versionCounter) {
        this.mutableChar = mutableChar;
        this.versionCounter = versionCounter;
    }

    @Override
    public char getValue() {
        return mutableChar.getValue();
    }

    @Override
    public Mutable<Character, MutableChar> copy() {
        return mutableChar.copy();
    }

    @Override
    public void copyTo(MutableChar otherMutable) {
        mutableChar.copyTo(otherMutable);
    }

    @Override
    public byte getType() {
        return mutableChar.getType();
    }

    @Override
    public Character get() {
        return mutableChar.get();
    }
    
    @Override
    public void setValue(char value) {
        if(getValue() != value) {
            mutableChar.setValue(value);
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
