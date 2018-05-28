package spoon.test.targeted.testclasses;


public class InnerClassThisAccess {
    public void method() {
    }

    public class InnerClassForTest {
        @java.lang.SuppressWarnings("unused")
        private void method2() {
            spoon.test.targeted.testclasses.InnerClassThisAccess.this.method();
        }
    }

    public void otherMethod() {
        class InnerClass {
            private final boolean b;

            InnerClass(boolean b) {
                this.b = b;
            }

            boolean getB() {
                return b;
            }
        }
        new InnerClass(true).getB();
    }
}

