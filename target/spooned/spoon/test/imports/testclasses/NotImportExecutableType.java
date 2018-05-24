package spoon.test.imports.testclasses;


public class NotImportExecutableType {
    void m() {
        spoon.test.imports.testclasses.internal3.Foo foo = new spoon.test.imports.testclasses.internal3.Foo();
        java.lang.Object o = foo.<java.io.File>bar();
    }
}

