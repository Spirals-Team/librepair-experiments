package spoon.test.ctType.testclasses;


class K<A extends spoon.test.ctType.testclasses.List<? extends spoon.test.ctType.testclasses.X>> {
    <B extends A> void m(spoon.test.ctType.testclasses.List<? extends B> l) {
    }
}

