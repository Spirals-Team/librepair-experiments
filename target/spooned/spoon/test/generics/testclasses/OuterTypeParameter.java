package spoon.test.generics.testclasses;


public class OuterTypeParameter {
    public <T> java.util.List<T> method() {
        return new java.util.ArrayList<T>() {
            @java.lang.Override
            public java.util.Iterator<T> iterator() {
                return super.iterator();
            }
        };
    }
}

