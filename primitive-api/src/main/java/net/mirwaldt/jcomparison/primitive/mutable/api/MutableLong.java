package net.mirwaldt.jcomparison.primitive.mutable.api;

public interface MutableLong extends Mutable<Long, MutableLong> {
    long getValue();
    void setValue(long value);
    
    default byte getType() {
        return TYPE_LONG;
    }
}
