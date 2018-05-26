package spoon.test.refactoring.testclasses;


public class AClassX extends spoon.test.refactoring.testclasses.AbstractClassX {
    private final java.lang.String string;

    public AClassX() {
        this("");
    }

    public AClassX(java.lang.String string) {
        super();
        this.string = string;
    }

    public boolean isMySubclass(java.lang.Object o) {
        return o instanceof spoon.test.refactoring.testclasses.AClassX;
    }
}

