package net.mirwaldt.jcomparison.primitive.mutable.api;

public interface MutableDouble extends Mutable<Double, MutableDouble> {
    double getValue();
    void setValue(double value);
    
    default byte getType() {
        return TYPE_DOUBLE;
    }
}
