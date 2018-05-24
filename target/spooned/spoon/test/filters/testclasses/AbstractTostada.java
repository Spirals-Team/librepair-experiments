package spoon.test.filters.testclasses;


public abstract class AbstractTostada implements spoon.test.filters.testclasses.ITostada {
    @java.lang.Override
    public spoon.test.filters.testclasses.ITostada make() {
        return new spoon.test.filters.testclasses.Tostada() {
            @java.lang.Override
            public void prepare() {
                super.prepare();
            }

            @java.lang.Override
            public spoon.test.filters.testclasses.ITostada make() {
                return super.make();
            }
        };
    }

    public abstract void prepare();

    public void honey() {
    }
}

