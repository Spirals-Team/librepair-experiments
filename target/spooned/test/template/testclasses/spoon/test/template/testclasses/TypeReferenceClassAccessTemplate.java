package spoon.test.template.testclasses;


public class TypeReferenceClassAccessTemplate extends spoon.template.ExtensionTemplate {
    java.lang.Object o;

    spoon.test.template.testclasses.TypeReferenceClassAccessTemplate.$Type$ someMethod(spoon.test.template.testclasses.TypeReferenceClassAccessTemplate.$Type$ param) {
        o = spoon.test.template.testclasses.TypeReferenceClassAccessTemplate.$Type$.out;
        spoon.test.template.testclasses.TypeReferenceClassAccessTemplate.$Type$ ret = new spoon.test.template.testclasses.TypeReferenceClassAccessTemplate.$Type$();
        o = spoon.test.template.testclasses.TypeReferenceClassAccessTemplate.$Type$.currentTimeMillis();
        o = spoon.test.template.testclasses.TypeReferenceClassAccessTemplate.$Type$.class;
        o = (o) instanceof spoon.test.template.testclasses.TypeReferenceClassAccessTemplate.$Type$;
        java.util.function.Supplier<java.lang.Long> p = spoon.test.template.testclasses.TypeReferenceClassAccessTemplate.$Type$::currentTimeMillis;
        return ret;
    }

    @spoon.template.Local
    public TypeReferenceClassAccessTemplate(spoon.reflect.reference.CtTypeReference<?> typeRef) {
        this.typeRef = typeRef;
    }

    @spoon.template.Parameter("$Type$")
    spoon.reflect.reference.CtTypeReference<?> typeRef;

    @spoon.template.Local
    static class $Type$ {
        static final java.lang.String out = "";

        static long currentTimeMillis() {
            return 0;
        }
    }

    public static class Example<T> {
        static final java.lang.String out = "";

        static long currentTimeMillis() {
            return 0;
        }
    }
}

