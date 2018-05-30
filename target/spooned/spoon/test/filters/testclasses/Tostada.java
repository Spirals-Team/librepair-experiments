package spoon.test.filters.testclasses;


public class Tostada extends spoon.test.filters.testclasses.AbstractTostada implements spoon.test.filters.testclasses.Honey {
    @java.lang.Override
    public spoon.test.filters.testclasses.ITostada make() {
        return new spoon.test.filters.testclasses.Tostada() {
            @java.lang.Override
            public void prepare() {
                int a = 3;
                super.prepare();
            }
        };
    }

    @java.lang.Override
    public void prepare() {
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "";
    }

    @java.lang.Override
    public void honey() {
    }

    public void foo() {
    }
}

