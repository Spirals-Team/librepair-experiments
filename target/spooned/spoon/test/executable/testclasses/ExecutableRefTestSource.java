package spoon.test.executable.testclasses;


public class ExecutableRefTestSource implements spoon.test.executable.testclasses.MyIntf {
    public void testMethod() {
        java.lang.String.valueOf("Hello World");
    }

    public void testConstructor() {
        new java.lang.String("Hello World");
    }

    @java.lang.Override
    public void myMethod() {
    }
}

