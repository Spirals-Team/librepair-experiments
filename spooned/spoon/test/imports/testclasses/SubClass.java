package spoon.test.imports.testclasses;


public class SubClass extends spoon.test.imports.testclasses.SuperClass {
    public void aMethod() {
        new spoon.test.imports.testclasses.SubClass.Item("");
    }

    public static class Item extends spoon.test.imports.testclasses.SuperClass.Item {
        public Item(java.lang.String s) {
            super(1, s);
        }
    }
}

