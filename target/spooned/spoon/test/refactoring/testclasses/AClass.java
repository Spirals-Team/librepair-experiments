package spoon.test.refactoring.testclasses;


public class AClass extends spoon.test.refactoring.testclasses.AbstractClass {
    private final java.lang.String string;

    public AClass() {
        this("");
    }

    public AClass(java.lang.String string) {
        super();
        this.string = string;
    }

    public boolean isMySubclass(java.lang.Object o) {
        return o instanceof spoon.test.refactoring.testclasses.AClass;
    }
}

