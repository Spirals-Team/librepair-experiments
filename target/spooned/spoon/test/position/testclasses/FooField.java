package spoon.test.position.testclasses;


public class FooField {
    public final int field1 = 0;

    int field2 = 0;

    static spoon.test.position.testclasses.FooField f = null;

    public void m() {
        spoon.test.position.testclasses.FooField.f.field2 = 0;
    }
}

