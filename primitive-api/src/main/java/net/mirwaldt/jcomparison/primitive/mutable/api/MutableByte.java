package net.mirwaldt.jcomparison.primitive.mutable.api;

public interface MutableByte extends Mutable<Byte, MutableByte> {
    byte getValue();
    void setValue(byte value);

    default byte getType() {
        return TYPE_BYTE;
    }
}
