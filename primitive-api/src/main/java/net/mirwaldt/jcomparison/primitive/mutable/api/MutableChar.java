package net.mirwaldt.jcomparison.primitive.mutable.api;

public interface MutableChar extends Mutable<Character, MutableChar> {
    char getValue();
    void setValue(char value);
    
    default byte getType() {
        return TYPE_CHAR;
    }

}
