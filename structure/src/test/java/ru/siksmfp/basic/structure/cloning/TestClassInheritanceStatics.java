package ru.siksmfp.basic.structure.cloning;

import org.junit.Test;
import ru.siksmfp.basic.structure.utils.cloning.Cloner;

public class TestClassInheritanceStatics {

    static class Parent {
        private String instanceFieldParent;
        private static String staticFieldParent;
    }

    static class Child extends Parent {
        private String instanceFieldChild;
        private static String staticFieldChild;
    }

    @Test
    public void testStaticFields_Parent() {
        Cloner cloner = new Cloner();
        cloner.registerStaticFields(Parent.class);
        // Works fine
    }

    @Test
    public void testStaticFields_Child() {
        Cloner cloner = new Cloner();
        cloner.registerStaticFields(Child.class);
        // Fails
    }
}
