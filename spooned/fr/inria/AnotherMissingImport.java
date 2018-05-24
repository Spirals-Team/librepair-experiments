package fr.inria;


public class AnotherMissingImport {
    public void doSomething(externallib.SomeType<java.lang.String> someType) {
    }

    public static void main(java.lang.String[] args) {
        fr.inria.AnotherMissingImport instance = new fr.inria.AnotherMissingImport();
        instance.doSomething(null);
    }
}

