package spoon.test.reference.testclasses;


public class EnumValue {
    public EnumValue() {
    }

    public <T extends java.lang.Enum<T>> T asEnum() {
        return null;
    }

    public java.lang.Object unwrap() {
        return asEnum();
    }
}

