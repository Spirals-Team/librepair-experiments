package spoon.test.delete.testclasses;


@java.lang.Deprecated
public class Adobada {
    {
        int i;
        int j;
    }

    static {
        int i;
        int j;
    }

    public Adobada() {
        int i;
        int j;
    }

    public void m() {
        int i;
        int j;
    }

    public spoon.test.delete.testclasses.Adobada m2() {
        return new spoon.test.delete.testclasses.Adobada() {
            @java.lang.Override
            public void m() {
                int i;
                int j;
            }
        };
    }

    public void m3() {
        switch (1) {
            case 1 :
                int i;
                int j;
            default :
                int o;
                int b;
        }
    }

    public void m4(int i, float j, java.lang.String s) {
        if (true) {
            java.lang.System.err.println("");
        }
        int k;
        j = i = k = 3;
    }

    public void methodUsingjlObjectMethods() {
        notify();
    }
}

