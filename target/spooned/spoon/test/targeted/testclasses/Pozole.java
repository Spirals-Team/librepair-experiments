package spoon.test.targeted.testclasses;


public class Pozole<T> {
    T[] data;

    public Pozole(spoon.test.targeted.testclasses.Pozole<T> v1) {
        final T[] data = ((spoon.test.targeted.testclasses.Pozole<T>) (v1)).data;
    }
}

