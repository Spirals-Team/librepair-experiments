package net.mirwaldt.jcomparison.primitive.mutable.api;

import java.util.function.Supplier;

public interface Mutable<Type, MutableType> extends Supplier<Type> {
    byte TYPE_OBJECT = 0;
    byte TYPE_BOOLEAN = 1;
    byte TYPE_BYTE = 2;
    byte TYPE_CHAR = 3;
    byte TYPE_SHORT = 4;
    byte TYPE_INT = 5;
    byte TYPE_LONG = 6;
    byte TYPE_FLOAT = 7;
    byte TYPE_DOUBLE = 8;
    
    Mutable<Type, MutableType> copy();
    void copyTo(MutableType otherMutable);
    byte getType();
}
