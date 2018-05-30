package spoon.test.refactoring.parameter.testclasses;


public class TypeB extends spoon.test.refactoring.parameter.testclasses.TypeA implements spoon.test.refactoring.parameter.testclasses.IFaceB<java.lang.Exception> {
    public TypeB() {
    }

    private void anMethodWithAnonymousClass() {
        new spoon.test.refactoring.parameter.testclasses.TypeB() {
            @java.lang.Override
            @spoon.test.refactoring.parameter.testclasses.TestHierarchy("A_method1")
            public void method1() {
                super.method1();
            }
        };
    }

    private void anMethodWithLambdaByParam(spoon.test.refactoring.parameter.testclasses.IFaceB ifaceB) {
        anMethodWithLambdaByParam(() -> {
            @spoon.test.refactoring.parameter.testclasses.TestHierarchy("A_method1")
            int x;
        });
    }

    private void anMethodWithLambda() {
        spoon.test.refactoring.parameter.testclasses.IFaceB ifaceB = () -> {
            @spoon.test.refactoring.parameter.testclasses.TestHierarchy("A_method1")
            int x;
        };
        ifaceB.method1();
    }

    private void anMethodWithLocalClass() {
        class Local extends spoon.test.refactoring.parameter.testclasses.TypeL {
            @java.lang.Override
            @spoon.test.refactoring.parameter.testclasses.TestHierarchy("A_method1")
            public void method1() {
                super.method1();
            }
        }
    }

    private void anMethodWithNullParameterValue() {
        spoon.test.refactoring.parameter.testclasses.IFaceB<java.lang.String> ifaceB = new spoon.test.refactoring.parameter.testclasses.IFaceB<java.lang.String>() {
            @java.lang.Override
            @spoon.test.refactoring.parameter.testclasses.TestHierarchy("A_method1")
            public void method1() {
            }
        };
        ifaceB.method1();
    }
}

