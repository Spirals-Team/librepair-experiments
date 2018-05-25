package spoon.test.reference;


public class FooBar {
    public class Bar {}

    public class Foo {}

    public spoon.test.reference.FooBar.Foo getFoo() {
        return new spoon.test.reference.FooBar.Foo() {
            public void printString(java.lang.String myArgFoo) {
                java.lang.System.out.println(myArgFoo);
            }
        };
    }

    public spoon.test.reference.FooBar.Bar getBar() {
        return new spoon.test.reference.FooBar.Bar() {
            public void printString(java.lang.String myArg) {
                java.lang.System.out.println(myArg);
            }
        };
    }
}

