package spoon.test.prettyprinter.testclasses;


public class AClass {
    public java.util.List<?> aMethod() {
        return new java.util.ArrayList<>();
    }

    public java.util.List<? extends java.util.ArrayList> aMethodWithGeneric() {
        return new java.util.ArrayList<>();
    }
}

