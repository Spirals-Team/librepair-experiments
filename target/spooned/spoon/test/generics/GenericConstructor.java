package spoon.test.generics;


public class GenericConstructor {
    public <E> GenericConstructor() {
        java.util.List<java.lang.Integer> l = new java.util.ArrayList<>();
        l.size();
    }
}

