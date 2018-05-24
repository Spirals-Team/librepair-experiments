package spoon.test.refactoring.parameter.testclasses;


public interface IFaceB<T> {
    @spoon.test.refactoring.parameter.testclasses.TestHierarchy("A_method1")
    void method1(T p1);
}

