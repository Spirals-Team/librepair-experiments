package net.mirwaldt.jcomparison.primitive.mutable.api;

public interface MutableShort extends Mutable<Short, MutableShort> {
    short getValue();
    void setValue(short value);
    
    default byte getType() {
        return TYPE_SHORT;
    }
}
