package spoon.test.template;


class TypeReferenceClassAccess {
    java.lang.Object o;

    spoon.test.template.TypeReferenceClassAccess.Example<java.util.Date> someMethod(spoon.test.template.TypeReferenceClassAccess.Example<java.util.Date> param) {
        o = spoon.test.template.TypeReferenceClassAccess.Example.out;
        spoon.test.template.TypeReferenceClassAccess.Example<java.util.Date> ret = new spoon.test.template.TypeReferenceClassAccess.Example<java.util.Date>();
        o = spoon.test.template.TypeReferenceClassAccess.Example.currentTimeMillis();
        o = spoon.test.template.TypeReferenceClassAccess.Example.class;
        o = (o) instanceof spoon.test.template.TypeReferenceClassAccess.Example<?>;
        java.util.function.Supplier<java.lang.Long> p = spoon.test.template.TypeReferenceClassAccess.Example::currentTimeMillis;
        return ret;
    }

    public static class Example<T> {
        static final java.lang.String out = "";

        static long currentTimeMillis() {
            return 0;
        }
    }
}

