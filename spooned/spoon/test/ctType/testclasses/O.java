package spoon.test.ctType.testclasses;


class O<A extends spoon.test.ctType.testclasses.X> {
    <B extends A> B foo() {
        return null;
    }
}

