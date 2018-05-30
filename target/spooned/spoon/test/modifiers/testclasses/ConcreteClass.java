package spoon.test.modifiers.testclasses;


final class ConcreteClass extends spoon.test.modifiers.testclasses.AbstractClass {
    public static final java.lang.String className = spoon.test.modifiers.testclasses.ConcreteClass.class.getName();

    private static int test = 42;

    private ConcreteClass() {
        spoon.test.modifiers.testclasses.ConcreteClass.test = 43;
    }

    public ConcreteClass(int i) {
        spoon.test.modifiers.testclasses.ConcreteClass.test = i;
    }

    @java.lang.Override
    protected void otherMethod() {
    }

    @java.lang.Override
    final int anotherOne() {
        return 0;
    }
}

