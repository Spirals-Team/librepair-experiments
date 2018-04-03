package net.mirwaldt.jcomparison.primitive.mutable.api;

public interface MutableBoolean extends Mutable<Boolean, MutableBoolean> {
    boolean getValue();
    void setValue(boolean value);
    
    default byte getType() {
        return TYPE_BOOLEAN;
    }
}
