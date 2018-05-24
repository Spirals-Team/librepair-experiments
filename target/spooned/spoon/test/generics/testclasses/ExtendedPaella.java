package spoon.test.generics.testclasses;


public class ExtendedPaella<T extends java.util.List<T>> extends spoon.test.generics.testclasses.Paella {
    class InnerPaella<L extends T> {
        public <L extends java.util.ArrayList> T innerMachin(L param) {
            return null;
        }

        public <T extends java.lang.String> L innerToto(T param) {
            return null;
        }
    }

    public <T extends java.util.ArrayList> T machin() {
        return null;
    }

    public T toto(T param) {
        return null;
    }
}

