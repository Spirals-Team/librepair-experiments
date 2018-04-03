package net.mirwaldt.jcomparison.primitive.mutable.api;

public interface MutableFloat extends Mutable<Float, MutableFloat> {
    float getValue();
    void setValue(float value);

    default byte getType() {
        return TYPE_FLOAT;
    }
}
