package spoon.reflect.visitor;


@org.junit.runner.RunWith(org.junit.runners.Parameterized.class)
public class CtInheritanceScannerTest<T extends spoon.reflect.visitor.CtVisitable> {
    private static spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();

    @org.junit.runners.Parameterized.Parameters(name = "{0}")
    public static java.util.Collection<java.lang.Object[]> data() throws java.lang.Exception {
        java.util.List<java.lang.Object[]> values = new java.util.ArrayList<>();
        for (java.lang.reflect.Method method : spoon.reflect.factory.CoreFactory.class.getDeclaredMethods()) {
            if (((method.getName().startsWith("create")) && ((method.getParameterCount()) == 0)) && (method.getReturnType().getSimpleName().startsWith("Ct"))) {
                values.add(new java.lang.Object[]{ method.getReturnType(), method.invoke(spoon.reflect.visitor.CtInheritanceScannerTest.factory.Core()) });
            }
        }
        return values;
    }

    @org.junit.runners.Parameterized.Parameter(0)
    public java.lang.Class<T> toTest;

    @org.junit.runners.Parameterized.Parameter(1)
    public T instance;

    private java.util.List<java.lang.reflect.Method> getMethodToInvoke(java.lang.Class<?> entry) throws java.lang.Exception {
        java.util.Queue<java.lang.Class<?>> tocheck = new java.util.LinkedList<>();
        tocheck.add(entry);
        java.util.List<java.lang.reflect.Method> toInvoke = new java.util.ArrayList<>();
        while (!(tocheck.isEmpty())) {
            java.lang.Class<?> intf = tocheck.poll();
            org.junit.Assert.assertTrue(intf.isInterface());
            if (!(intf.getSimpleName().startsWith("Ct"))) {
                continue;
            }
            java.lang.reflect.Method mth = null;
            try {
                mth = spoon.reflect.visitor.CtInheritanceScanner.class.getDeclaredMethod(("visit" + (intf.getSimpleName())), intf);
                if ((mth.getAnnotation(java.lang.Deprecated.class)) != null) {
                    mth = null;
                }
            } catch (java.lang.NoSuchMethodException ex) {
            }
            if ((mth != null) && (!(toInvoke.contains(mth)))) {
                toInvoke.add(mth);
            }
            try {
                mth = spoon.reflect.visitor.CtInheritanceScanner.class.getDeclaredMethod(("scan" + (intf.getSimpleName())), intf);
                if ((mth.getAnnotation(java.lang.Deprecated.class)) != null) {
                    mth = null;
                }
            } catch (java.lang.NoSuchMethodException ex) {
            }
            if ((mth != null) && (!(toInvoke.contains(mth)))) {
                toInvoke.add(mth);
            }
            for (java.lang.Class<?> aClass : intf.getInterfaces()) {
                tocheck.add(aClass);
            }
        } 
        return toInvoke;
    }

    @org.junit.Test
    public void testCtInheritanceScanner() throws java.lang.Throwable {
        spoon.reflect.visitor.CtInheritanceScanner mocked = org.mockito.Mockito.mock(spoon.reflect.visitor.CtInheritanceScanner.class);
        java.util.List<java.lang.reflect.Method> toInvoke = getMethodToInvoke(toTest);
        for (java.lang.reflect.Method method : toInvoke) {
            method.invoke(org.mockito.Mockito.doCallRealMethod().when(mocked), instance);
        }
        instance.accept(mocked);
        for (int i = 0; i < (toInvoke.size()); i++) {
            try {
                toInvoke.get(i).invoke(org.mockito.Mockito.verify(mocked), instance);
            } catch (java.lang.reflect.InvocationTargetException e) {
                if ((e.getTargetException()) instanceof java.lang.AssertionError) {
                    org.junit.Assert.fail(((("visit" + (instance.getClass().getSimpleName().replaceAll("Impl$", ""))) + " does not call ") + (toInvoke.get(i).getName())));
                }else {
                    throw e.getTargetException();
                }
            }
        }
    }
}

