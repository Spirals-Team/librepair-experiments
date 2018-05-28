package spoon.test.limits;


public class StaticClassAnon {
    static void methode() {
    }

    static {
        @java.lang.SuppressWarnings("unused")
        class StaticIntern {
            public void hasAMethod() {
                spoon.test.limits.StaticClassAnon.methode();
            }
        }
    }
}

