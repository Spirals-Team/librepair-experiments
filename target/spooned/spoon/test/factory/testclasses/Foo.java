package spoon.test.factory.testclasses;


@spoon.test.factory.testclasses.Foo.Bar
public class Foo {
    public @interface Bar {
        java.lang.Class<spoon.test.factory.testclasses.Foo> clazz() default spoon.test.factory.testclasses.Foo.class;

        java.lang.Class<spoon.test.factory.testclasses.Foo>[] classes() default {  };
    }
}

