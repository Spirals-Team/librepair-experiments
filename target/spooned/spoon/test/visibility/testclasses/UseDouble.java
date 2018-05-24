package spoon.test.visibility.testclasses;


public class UseDouble {
    public final spoon.test.visibility.testclasses.internal.Double aDouble;

    public final spoon.test.visibility.testclasses.Float aFloat;

    public UseDouble(spoon.test.visibility.testclasses.internal.Double aDouble, spoon.test.visibility.testclasses.Float aFloat) {
        this.aDouble = aDouble;
        this.aFloat = aFloat;
    }

    public void aMethod() {
        aDouble.aMethodNotStatic();
        spoon.test.visibility.testclasses.internal.Double.aMethodNotInJavaLangDoubleClass("", "");
        spoon.test.visibility.testclasses.internal.Double.toHexString(42);
        aFloat.aMethodNotStatic();
        spoon.test.visibility.testclasses.Float.aMethodNotInJavaLangFloatClass("", "");
        spoon.test.visibility.testclasses.Float.sum(0.0F, 0.0F);
    }
}

