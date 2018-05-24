package spoon.test.visibility.testclasses;


import spoon.test.visibility.testclasses.internal.Double;


public class UseDouble {
    public final Double aDouble;

    public final Float aFloat;

    public UseDouble(Double aDouble, Float aFloat) {
        this.aDouble = aDouble;
        this.aFloat = aFloat;
    }

    public void aMethod() {
        aDouble.aMethodNotStatic();
        Double.aMethodNotInJavaLangDoubleClass("", "");
        Double.toHexString(42);
        aFloat.aMethodNotStatic();
        Float.aMethodNotInJavaLangFloatClass("", "");
        Float.sum(0.0F, 0.0F);
    }
}

