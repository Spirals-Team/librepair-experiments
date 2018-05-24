package spoon.test.annotation.testclasses.shadow;


public class DumbKlass {
    @spoon.test.annotation.testclasses.shadow.StandardRetention
    public void foo() {
    }

    @spoon.test.annotation.testclasses.shadow.ClassRetention
    public void fooClass() {
    }

    @spoon.test.annotation.testclasses.shadow.RuntimeRetention(role = "bidule")
    public void barOneValue() {
    }

    @spoon.test.annotation.testclasses.shadow.RuntimeRetention(role = { "bidule" })
    public void barMultipleValues() {
    }
}

