package spoon.test.prettyprinter.testclasses;


public class FooCasper {
    spoon.test.prettyprinter.testclasses.FooCasper f;

    public spoon.test.prettyprinter.testclasses.FooCasper bug1() {
        if ((new spoon.test.prettyprinter.testclasses.FooCasper(1).foo()) != null) {
            throw new java.lang.Error();
        }
        spoon.test.prettyprinter.testclasses.FooCasper g = new spoon.test.prettyprinter.testclasses.FooCasper(1).foo();
        f = g;
        java.lang.System.out.println(f);
        f.bar();
        return null;
    }

    public spoon.test.prettyprinter.testclasses.FooCasper foo() {
        return foo2();
    }

    public spoon.test.prettyprinter.testclasses.FooCasper foo2() {
        return null;
    }

    public spoon.test.prettyprinter.testclasses.FooCasper foo3() {
        return f;
    }

    public void bar() {
    }

    public spoon.test.prettyprinter.testclasses.FooCasper foo5(spoon.test.prettyprinter.testclasses.FooCasper o) {
        return o;
    }

    public void bug2() {
        foo5(null).f.bar();
    }

    public void bug3() {
        spoon.test.prettyprinter.testclasses.FooCasper[] tab = null;
        if (0 == 1) {
            tab = new spoon.test.prettyprinter.testclasses.FooCasper[0];
        }
        tab[0].bar();
    }

    public void bug4() {
        java.lang.Object tab = null;
        if (0 == 1) {
            tab = new java.lang.Object();
        }
        tab.toString();
    }

    public FooCasper(int i) {
    }

    public FooCasper() {
    }

    public void toString_support() {
        spoon.test.prettyprinter.testclasses.FooCasper o = null;
        o.toString();
    }

    public void array_support() {
        spoon.test.prettyprinter.testclasses.FooCasper o = null;
        spoon.test.prettyprinter.testclasses.FooCasper[] array = new spoon.test.prettyprinter.testclasses.FooCasper[10];
        array[1] = o;
        array[2] = array[1];
        array[2].bar();
    }

    public void literal() {
        spoon.test.prettyprinter.testclasses.FooCasper tab = null;
        tab.literal();
    }

    public void literal2() {
        spoon.test.prettyprinter.testclasses.FooCasper tab = new spoon.test.prettyprinter.testclasses.FooCasper();
        tab = null;
        tab.literal();
    }
}

