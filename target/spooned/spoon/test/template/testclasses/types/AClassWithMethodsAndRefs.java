package spoon.test.template.testclasses.types;


public class AClassWithMethodsAndRefs {
    public AClassWithMethodsAndRefs() {
        someMethod(0);
    }

    spoon.test.template.testclasses.types.AClassWithMethodsAndRefs.Local local = () -> {
        spoon.test.template.testclasses.types.AClassWithMethodsAndRefs.sameTypeStatic.foo();
    };

    spoon.test.template.testclasses.types.AClassWithMethodsAndRefs sameType = new spoon.test.template.testclasses.types.AClassWithMethodsAndRefs();

    static spoon.test.template.testclasses.types.AClassWithMethodsAndRefs sameTypeStatic;

    public spoon.test.template.testclasses.types.AClassWithMethodsAndRefs anotherMethod() {
        someMethod(0);
        return new spoon.test.template.testclasses.types.AClassWithMethodsAndRefs();
    }

    public void someMethod(int i) {
        if (i == 0) {
            someMethod(1);
        }
    }

    interface Local {
        void bar();
    }

    spoon.test.template.testclasses.types.AClassWithMethodsAndRefs.Local foo() {
        class Bar implements spoon.test.template.testclasses.types.AClassWithMethodsAndRefs.Local {
            public void bar() {
                bar();
                foo();
            }
        }
        return new Bar();
    }
}

