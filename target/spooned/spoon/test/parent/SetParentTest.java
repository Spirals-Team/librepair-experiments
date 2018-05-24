package spoon.test.parent;


@org.junit.runner.RunWith(org.junit.runners.Parameterized.class)
public class SetParentTest<T extends spoon.reflect.visitor.CtVisitable> {
    private static spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();

    @org.junit.runners.Parameterized.Parameters(name = "{0}")
    public static java.util.Collection<java.lang.Object[]> data() throws java.lang.Exception {
        return spoon.test.parent.ParentContractTest.createReceiverList();
    }

    @org.junit.runners.Parameterized.Parameter(0)
    public spoon.reflect.declaration.CtType<?> toTest;

    @org.junit.Test
    public void testContract() throws java.lang.Throwable {
        java.lang.Object o = spoon.test.parent.SetParentTest.factory.Core().create(((java.lang.Class<? extends spoon.reflect.declaration.CtElement>) (toTest.getActualClass())));
        spoon.reflect.declaration.CtMethod<?> setter = spoon.test.parent.SetParentTest.factory.Type().get(spoon.reflect.declaration.CtElement.class).getMethodsByName("setParent").get(0);
        java.lang.Object argument = spoon.test.parent.ParentContractTest.createCompatibleObject(setter.getParameters().get(0).getType());
        if (!(argument instanceof spoon.reflect.declaration.CtElement)) {
            throw new java.lang.AssertionError("impossible, setParent always takes an element");
        }
        spoon.reflect.declaration.CtElement receiver = ((spoon.reflect.declaration.CtElement) (o)).clone();
        if ((((("CtClass".equals(toTest.getSimpleName())) || ("CtInterface".equals(toTest.getSimpleName()))) || ("CtEnum".equals(toTest.getSimpleName()))) || ("CtAnnotationType".equals(toTest.getSimpleName()))) || ("CtPackage".equals(toTest.getSimpleName()))) {
            org.junit.Assert.assertTrue(((receiver.getParent()) instanceof spoon.reflect.CtModelImpl.CtRootPackage));
        }else
            if ("CtModule".equals(toTest.getSimpleName())) {
                org.junit.Assert.assertTrue(((receiver.getParent()) instanceof spoon.reflect.factory.ModuleFactory.CtUnnamedModule));
            }else {
                try {
                    receiver.getParent().hashCode();
                    org.junit.Assert.fail(receiver.getParent().getClass().getSimpleName());
                } catch (spoon.reflect.declaration.ParentNotInitializedException normal) {
                }
            }

        java.lang.reflect.Method actualMethod = setter.getReference().getActualMethod();
        spoon.reflect.declaration.CtElement argumentClone = ((spoon.reflect.declaration.CtElement) (argument)).clone();
        actualMethod.invoke(receiver, new java.lang.Object[]{ argument });
        org.junit.Assert.assertTrue(argument.equals(argumentClone));
        org.junit.Assert.assertFalse((argument == argumentClone));
    }
}

