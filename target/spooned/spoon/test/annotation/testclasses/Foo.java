package spoon.test.annotation.testclasses;


public class Foo {
    @spoon.test.annotation.testclasses.Foo.OuterAnnotation({ @spoon.test.annotation.testclasses.Foo.MiddleAnnotation(@spoon.test.annotation.testclasses.Foo.InnerAnnotation("hello")), @spoon.test.annotation.testclasses.Foo.MiddleAnnotation(@spoon.test.annotation.testclasses.Foo.InnerAnnotation("hello again")) })
    public void test() {
    }

    public @interface OuterAnnotation {
        spoon.test.annotation.testclasses.Foo.MiddleAnnotation[] value();
    }

    public @interface MiddleAnnotation {
        spoon.test.annotation.testclasses.Foo.InnerAnnotation value();
    }

    public @interface InnerAnnotation {
        java.lang.String value();
    }
}

