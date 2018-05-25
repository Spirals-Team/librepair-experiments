package spoon.test.refactoring.parameter.testclasses;


public class TypeS extends spoon.test.refactoring.parameter.testclasses.TypeR {
    @java.lang.Override
    @spoon.test.refactoring.parameter.testclasses.TestHierarchy("R_method1")
    public void method1(java.lang.Double p1) {
    }

    private void methodWithLambdaOf_A() {
        spoon.test.refactoring.parameter.testclasses.IFaceB ifaceB = ( p) -> {
            @spoon.test.refactoring.parameter.testclasses.TestHierarchy("A_method1")
            int x;
        };
        ifaceB.method1(1);
    }

    private void methodWithLambdaOf_R() {
        spoon.test.refactoring.parameter.testclasses.IFaceT ifaceT = ( p) -> {
            @spoon.test.refactoring.parameter.testclasses.TestHierarchy("R_method1")
            int x;
        };
        ifaceT.method1(1.0);
    }

    private void methodWithComplexExpression_R() {
        spoon.test.refactoring.parameter.testclasses.IFaceT ifaceT = ( p) -> {
            @spoon.test.refactoring.parameter.testclasses.TestHierarchy("R_method1")
            int x;
        };
        ifaceT.method1(java.lang.Math.abs(1.0));
    }
}

