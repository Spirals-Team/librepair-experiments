package spoon.test.prettyprinter.testclasses;


public class QualifiedThisRef<T> {
    private spoon.test.prettyprinter.testclasses.QualifiedThisRef<T>.Sub sub;

    class Sub {
        void foo() {
            java.lang.Object o = this;
        }

        void foo2() {
            java.lang.Object o2 = spoon.test.prettyprinter.testclasses.QualifiedThisRef.this;
        }
    }

    void bla() {
        java.lang.System.out.println(sub);
    }
}

