package spoon.test.ctType.testclasses;


public class ErasureModelA<A, B extends java.lang.Exception, C extends B, D extends java.util.List<B>> {
    A paramA;

    B paramB;

    C paramC;

    D paramD;

    public <I, J extends C> ErasureModelA(I paramI, J paramJ, D paramD) {
    }

    public <I, J extends C> void method(I paramI, J paramJ, D paramD) {
    }

    public <I, J extends C> void method2(I paramI, J paramJ, D paramD) {
    }

    public <I, J extends C, K extends spoon.test.ctType.testclasses.ErasureModelA<A, B, C, D> & java.io.Serializable> void method3(I paramI, J paramJ, D paramD, K paramK) {
    }

    public <I> void wildCardMethod(I paramI, spoon.test.ctType.testclasses.ErasureModelA<? extends I, B, C, D> extendsI) {
    }

    public void list(java.util.List<java.lang.Object> x, java.util.List<java.util.List<java.lang.Object>> y, java.util.List<java.lang.String> z) {
    }

    static class ModelB<A2, B2 extends java.lang.Exception, C2 extends B2, D2 extends java.util.List<B2>> extends spoon.test.ctType.testclasses.ErasureModelA<A2, B2, C2, D2> {
        A2 paramA2;

        B2 paramB2;

        C2 paramC2;

        D2 paramD2;

        public <I, J extends C2> ModelB(I paramI, J paramJ, D2 paramD2) {
            super(paramI, paramJ, paramD2);
        }

        @java.lang.Override
        public <I, J extends C2> void method(I paramI, J paramJ, D2 paramD2) {
        }
    }

    static class ModelC extends spoon.test.ctType.testclasses.ErasureModelA<java.lang.Integer, java.lang.RuntimeException, java.lang.IllegalArgumentException, java.util.List<java.lang.RuntimeException>> {
        public ModelC(java.lang.Float paramI, java.lang.IllegalArgumentException paramJ, spoon.test.ctType.testclasses.ErasureModelA.ModelC paramK) {
            super(paramI, paramJ, null);
        }

        public void method(java.lang.Float paramI, java.lang.IllegalArgumentException paramJ, spoon.test.ctType.testclasses.ErasureModelA.ModelC paramK) {
        }
    }
}

