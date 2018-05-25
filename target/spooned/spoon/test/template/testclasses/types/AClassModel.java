package spoon.test.template.testclasses.types;


public class AClassModel<E> extends java.util.AbstractList<E> implements spoon.test.template.testclasses.types.AnIfaceModel {
    public AClassModel() {
    }

    @java.lang.Override
    public E get(int index) {
        throw new java.lang.IndexOutOfBoundsException();
    }

    @java.lang.Override
    public int size() {
        return 0;
    }

    @java.lang.Override
    public void someMethod() {
    }
}

