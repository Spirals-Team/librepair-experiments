package spoon.test.field.testclasses;


public class A {
    public class ClassB {
        public static final java.lang.String PREFIX = (spoon.test.field.testclasses.BaseClass.PREFIX) + ".b";

        public java.lang.String getKey() {
            return spoon.test.field.testclasses.BaseClass.PREFIX;
        }
    }
}

