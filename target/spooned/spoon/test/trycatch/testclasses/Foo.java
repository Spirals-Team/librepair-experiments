package spoon.test.trycatch.testclasses;


public class Foo {
    public void m() throws java.lang.Exception {
        try {
        } catch (spoon.test.trycatch.testclasses.internal.MyException | spoon.test.trycatch.testclasses.internal.MyException2 ignore) {
        }
    }
}

