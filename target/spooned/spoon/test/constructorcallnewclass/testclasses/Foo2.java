package spoon.test.constructorcallnewclass.testclasses;


public class Foo2 {
    public void exec() {
        spoon.test.constructorcallnewclass.testclasses.Foo2.AbstractClass abstractClass1 = new spoon.test.constructorcallnewclass.testclasses.Foo2.AbstractClass(1) {
            @java.lang.Override
            public double getValue(double[] value) {
                return 0;
            }
        };
        spoon.test.constructorcallnewclass.testclasses.Foo2.AbstractClass abstractClass2 = new spoon.test.constructorcallnewclass.testclasses.Foo2.AbstractClass(1) {
            @java.lang.Override
            public double getValue(double[] value) {
                return 0;
            }
        };
        spoon.test.constructorcallnewclass.testclasses.Foo2.AbstractClass abstractClass3 = new spoon.test.constructorcallnewclass.testclasses.Foo2.AbstractClass(2) {
            @java.lang.Override
            public double getValue(double[] value) {
                return 0;
            }
        };
        spoon.test.constructorcallnewclass.testclasses.Foo2.AbstractClass abstractClass4 = new spoon.test.constructorcallnewclass.testclasses.Foo2.AbstractClass(3) {
            @java.lang.Override
            public double getValue(double[] value) {
                return 0;
            }
        };
        spoon.test.constructorcallnewclass.testclasses.Foo2.AbstractClass abstractClass5 = new spoon.test.constructorcallnewclass.testclasses.Foo2.AbstractClass(4) {
            @java.lang.Override
            public double getValue(double[] value) {
                return 0;
            }
        };
        spoon.test.constructorcallnewclass.testclasses.Foo2.AbstractClass abstractClass6 = new spoon.test.constructorcallnewclass.testclasses.Foo2.AbstractClass(6) {
            @java.lang.Override
            public double getValue(double[] value) {
                return 0;
            }
        };
        spoon.test.constructorcallnewclass.testclasses.Foo2.AbstractClass abstractClass7 = new spoon.test.constructorcallnewclass.testclasses.Foo2.AbstractClass(7) {
            @java.lang.Override
            public double getValue(double[] value) {
                return 0;
            }
        };
        spoon.test.constructorcallnewclass.testclasses.Foo2.AbstractClass abstractClass8 = new spoon.test.constructorcallnewclass.testclasses.Foo2.AbstractClass(8) {
            @java.lang.Override
            public double getValue(double[] value) {
                return 0;
            }
        };
        spoon.test.constructorcallnewclass.testclasses.Foo2.AbstractClass abstractClass9 = new spoon.test.constructorcallnewclass.testclasses.Foo2.AbstractClass(9) {
            @java.lang.Override
            public double getValue(double[] value) {
                return 0;
            }
        };
        spoon.test.constructorcallnewclass.testclasses.Foo2.AbstractClass abstractClass10 = new spoon.test.constructorcallnewclass.testclasses.Foo2.AbstractClass(10) {
            @java.lang.Override
            public double getValue(double[] value) {
                return 0;
            }
        };
        spoon.test.constructorcallnewclass.testclasses.Foo2.AbstractClass abstractClass11 = new spoon.test.constructorcallnewclass.testclasses.Foo2.AbstractClass(11) {
            @java.lang.Override
            public double getValue(double[] value) {
                return 0;
            }
        };
        spoon.test.constructorcallnewclass.testclasses.Foo2.AbstractClass abstractClass12 = new spoon.test.constructorcallnewclass.testclasses.Foo2.AbstractClass(12) {
            @java.lang.Override
            public double getValue(double[] value) {
                return new spoon.test.constructorcallnewclass.testclasses.Foo2.AbstractClass(12) {
                    @java.lang.Override
                    public double getValue(double[] value) {
                        return 0;
                    }
                }.getValue(value);
            }
        };
    }

    private abstract class AbstractClass {
        private int i;

        AbstractClass(int i) {
            this.i = i;
        }

        public abstract double getValue(double[] value);
    }
}

