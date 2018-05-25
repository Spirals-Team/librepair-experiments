package spoon.test.generics.testclasses;


public class Mole {
    public void cook() {
        class Cook<T> {}
        final Cook<java.lang.String> aClass = new Cook<java.lang.String>();
    }

    public void prepare() {
        class Prepare<T> {}
        new <java.lang.Integer>Prepare<java.lang.String>();
    }
}

