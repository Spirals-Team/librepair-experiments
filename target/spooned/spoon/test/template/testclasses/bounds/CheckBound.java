package spoon.test.template.testclasses.bounds;


public class CheckBound {
    java.util.List l;

    public void foo() {
        if ((new java.util.ArrayList<>().size()) > 10)
            throw new java.lang.IndexOutOfBoundsException();

    }

    public void foo2() {
        if ((new java.util.ArrayList<>().size()) > 11)
            throw new java.lang.IndexOutOfBoundsException();

    }

    public void fbar() {
        if ((l.size()) > 10)
            throw new java.lang.IndexOutOfBoundsException();

    }

    public void baz() {
        if ((new java.util.ArrayList<>().size()) > 10) {
        }
    }

    public void bou() {
        if ((new java.util.ArrayList<>().size()) > 10) {
            java.lang.System.out.println();
        }
    }

    public void bov() {
        java.lang.System.out.println("noise");
        if ((new java.util.ArrayList<>().size()) > 10)
            java.lang.System.out.println();

    }

    public void bos() {
        if ((new java.util.ArrayList<>().size()) == (new java.util.ArrayList<>().size()))
            java.lang.System.out.println();

    }
}

