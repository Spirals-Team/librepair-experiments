package spoon.test.method_overriding;


public class MethodOverriddingTest {
    @org.junit.Test
    public void testMethodOverride() {
        checkMethodOverride(( m1, m2) -> m1.isOverriding(m2));
    }

    @org.junit.Test
    public void testMethodOverrideByReference() {
        checkMethodOverride(( m1, m2) -> m1.getReference().isOverriding(m2.getReference()));
    }

    private void checkMethodOverride(java.util.function.BiFunction<spoon.reflect.declaration.CtMethod<?>, spoon.reflect.declaration.CtMethod<?>, java.lang.Boolean> isOverriding) {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(new java.io.File("src/test/java/spoon/test/method_overriding/testclasses").listFiles());
        java.util.Map<java.lang.String, java.util.List<spoon.reflect.declaration.CtMethod>> methodsByName = new java.util.HashMap<>();
        factory.getModel().filterChildren(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.declaration.CtMethod.class)).forEach((spoon.reflect.declaration.CtMethod m) -> {
            java.util.List<spoon.reflect.declaration.CtMethod> methods = methodsByName.get(m.getSimpleName());
            if (methods == null) {
                methods = new java.util.ArrayList<>();
                methodsByName.put(m.getSimpleName(), methods);
            }
            methods.add(m);
        });
        org.junit.Assert.assertTrue(((methodsByName.size()) > 0));
        for (java.util.Map.Entry<java.lang.String, java.util.List<spoon.reflect.declaration.CtMethod>> e : methodsByName.entrySet()) {
            combine(e.getValue(), 0, isOverriding);
        }
    }

    private void combine(java.util.List<spoon.reflect.declaration.CtMethod> value, int start, java.util.function.BiFunction<spoon.reflect.declaration.CtMethod<?>, spoon.reflect.declaration.CtMethod<?>, java.lang.Boolean> isOverriding) {
        spoon.reflect.declaration.CtMethod m1 = value.get(start);
        if ((start + 1) < (value.size())) {
            for (spoon.reflect.declaration.CtMethod m2 : value.subList((start + 1), value.size())) {
                if (m1.getDeclaringType().isSubtypeOf(m2.getDeclaringType().getReference())) {
                    checkOverride(m1, m2, isOverriding);
                }else
                    if (m2.getDeclaringType().isSubtypeOf(m1.getDeclaringType().getReference())) {
                        checkOverride(m2, m1, isOverriding);
                    }else {
                        checkNotOverride(m1, m2, isOverriding);
                    }

            }
            combine(value, (start + 1), isOverriding);
        }
    }

    private void checkOverride(spoon.reflect.declaration.CtMethod m1, spoon.reflect.declaration.CtMethod m2, java.util.function.BiFunction<spoon.reflect.declaration.CtMethod<?>, spoon.reflect.declaration.CtMethod<?>, java.lang.Boolean> isOverriding) {
        org.junit.Assert.assertTrue((((descr(m1)) + " overriding ") + (descr(m2))), isOverriding.apply(m1, m2));
        org.junit.Assert.assertFalse((((descr(m2)) + " NOT overriding ") + (descr(m1))), isOverriding.apply(m2, m1));
    }

    private void checkNotOverride(spoon.reflect.declaration.CtMethod m1, spoon.reflect.declaration.CtMethod m2, java.util.function.BiFunction<spoon.reflect.declaration.CtMethod<?>, spoon.reflect.declaration.CtMethod<?>, java.lang.Boolean> isOverriding) {
        org.junit.Assert.assertFalse((((descr(m1)) + " NOT overriding ") + (descr(m2))), isOverriding.apply(m1, m2));
        org.junit.Assert.assertFalse((((descr(m2)) + " NOT overriding ") + (descr(m1))), isOverriding.apply(m2, m1));
    }

    private java.lang.String descr(spoon.reflect.declaration.CtMethod m) {
        return ((m.getDeclaringType().getSimpleName()) + "#") + (m.getSimpleName());
    }
}

