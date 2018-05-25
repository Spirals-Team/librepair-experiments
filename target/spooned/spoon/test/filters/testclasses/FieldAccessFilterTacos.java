package spoon.test.filters.testclasses;


public class FieldAccessFilterTacos extends java.util.ArrayList {
    private int myfield = 0;

    FieldAccessFilterTacos() {
        super();
        this.myfield = 0;
    }

    public void m() {
        myfield = super.size();
        java.lang.Object o = super.get(myfield);
    }
}

