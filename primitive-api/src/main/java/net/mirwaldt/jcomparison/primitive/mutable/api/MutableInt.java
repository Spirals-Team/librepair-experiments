package net.mirwaldt.jcomparison.primitive.mutable.api;

public interface MutableInt extends Mutable<Integer, MutableInt> {
    int getValue();
    void setValue(int value);
    
    default byte getType() {
        return TYPE_INT;
    }
}
