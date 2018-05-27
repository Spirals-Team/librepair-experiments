package spoon.test.annotation.testclasses.repeatable;


public class Repeated {
    @spoon.test.annotation.testclasses.repeatable.Tag("machin")
    @spoon.test.annotation.testclasses.repeatable.Tag("truc")
    public void method() {
    }

    public void withoutAnnotation() {
    }
}

