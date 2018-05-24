package spoon.test.reference;


import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtField;
import spoon.reflect.reference.CtVariableReference;


public class CloneReferenceTest {
    @org.junit.Test
    public void testGetDeclarationAfterClone() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        java.util.List<String> names = java.util.Arrays.asList("f1", "f2", "a", "b", "x", "param", "e");
        spoon.addInputResource("./src/test/resources/noclasspath/A2.java");
        spoon.getEnvironment().setComplianceLevel(8);
        spoon.getEnvironment().setNoClasspath(true);
        spoon.buildModel();
        final CtClass<Object> a = spoon.getFactory().Class().get("A2");
        for (String name : names) {
            spoon.reflect.declaration.CtVariable var1 = findVariable(a, name);
            spoon.reflect.declaration.CtVariable var2 = findReference(a, name).getDeclaration();
            org.junit.Assert.assertTrue((var1 == var2));
        }
        CtClass b = a.clone();
        for (String name : names) {
            spoon.reflect.declaration.CtVariable var1 = findVariable(b, name);
            CtVariableReference refVar1 = findReference(b, name);
            spoon.reflect.declaration.CtVariable var2 = refVar1.getDeclaration();
            org.junit.Assert.assertTrue("Var1 and var2 are not the same element", (var1 == var2));
        }
    }

    @org.junit.Test
    public void testGetDeclarationOfFieldAfterClone() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        String name = "field";
        spoon.addInputResource("./src/test/resources/noclasspath/A2.java");
        spoon.getEnvironment().setComplianceLevel(8);
        spoon.getEnvironment().setNoClasspath(true);
        spoon.buildModel();
        final CtClass<Object> a = spoon.getFactory().Class().get("A2");
        CtField oldVar1 = ((CtField) (findVariable(a, name)));
        CtField oldVar2 = ((CtField) (findReference(a, name).getDeclaration()));
        org.junit.Assert.assertTrue((oldVar1 == oldVar2));
        CtClass b = a.clone();
        CtField var1 = ((CtField) (findVariable(b, name)));
        CtVariableReference refVar1 = findReference(b, name);
        CtField var2 = ((CtField) (refVar1.getDeclaration()));
        org.junit.Assert.assertTrue((var1 != var2));
        org.junit.Assert.assertTrue((var2 == oldVar1));
        org.junit.Assert.assertTrue(((var1.getParent(CtClass.class)) == b));
    }

    class Finder<T> extends spoon.reflect.visitor.CtScanner {
        private final java.lang.Class<T> c;

        private final java.util.function.Predicate<T> filter;

        private T result;

        public Finder(java.lang.Class<T> c, java.util.function.Predicate<T> filter) {
            this.c = c;
            this.filter = filter;
        }

        @java.lang.Override
        public void scan(spoon.reflect.declaration.CtElement element) {
            if (((element != null) && (c.isAssignableFrom(element.getClass()))) && (filter.test(((T) (element))))) {
                result = ((T) (element));
            }else {
                super.scan(element);
            }
        }

        public T find(spoon.reflect.declaration.CtElement root) {
            scan(root);
            return result;
        }
    }

    public <T extends spoon.reflect.declaration.CtElement> T find(spoon.reflect.declaration.CtElement root, java.lang.Class<T> c, java.util.function.Predicate<T> filter) {
        return new spoon.test.reference.CloneReferenceTest.Finder<>(c, filter).find(root);
    }

    public spoon.reflect.declaration.CtVariable findVariable(spoon.reflect.declaration.CtElement root, String name) {
        return find(root, spoon.reflect.declaration.CtVariable.class, ( var) -> name.equals(var.getSimpleName()));
    }

    public CtVariableReference findReference(spoon.reflect.declaration.CtElement root, String name) {
        return find(root, CtVariableReference.class, ( ref) -> name.equals(ref.getSimpleName()));
    }
}

