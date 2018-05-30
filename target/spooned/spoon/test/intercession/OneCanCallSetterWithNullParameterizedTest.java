package spoon.test.intercession;


@org.junit.runner.RunWith(org.junit.runners.Parameterized.class)
public class OneCanCallSetterWithNullParameterizedTest {
    @org.junit.runners.Parameterized.Parameters(name = "{1}")
    public static java.util.Collection<java.lang.Object[]> data() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        final spoon.reflect.factory.Factory factory = launcher.getFactory();
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource("./src/main/java/spoon/reflect/code");
        launcher.addInputResource("./src/main/java/spoon/reflect/declaration");
        launcher.addInputResource("./src/main/java/spoon/reflect/reference");
        launcher.buildModel();
        final java.util.List<java.lang.Object[]> values = new java.util.ArrayList<>();
        new spoon.test.intercession.IntercessionScanner(launcher.getFactory()) {
            @java.lang.Override
            protected boolean isToBeProcessed(spoon.reflect.declaration.CtMethod<?> candidate) {
                return ((candidate.getSimpleName().startsWith("set")) || (candidate.getSimpleName().startsWith("add"))) && (takeSetterForCtElement(candidate));
            }

            @java.lang.Override
            protected void process(spoon.reflect.declaration.CtMethod<?> element) {
                values.add(new java.lang.Object[]{ spoon.test.parent.ContractOnSettersParametrizedTest.createCompatibleObject(element.getDeclaringType().getReference()), element.getReference().getActualMethod() });
            }
        }.scan(launcher.getModel().getRootPackage());
        return values;
    }

    @org.junit.runners.Parameterized.Parameter(0)
    public java.lang.Object instance;

    @org.junit.runners.Parameterized.Parameter(1)
    public java.lang.reflect.Method toTest;

    @org.junit.Test
    public void testContract() throws java.lang.Throwable {
        spoon.reflect.factory.Factory factory = new spoon.reflect.factory.FactoryImpl(new spoon.support.DefaultCoreFactory(), new spoon.support.StandardEnvironment());
        java.lang.Object element = instance;
        if (element instanceof spoon.processing.FactoryAccessor) {
            ((spoon.processing.FactoryAccessor) (element)).setFactory(factory);
        }
        toTest.invoke(element, new java.lang.Object[]{ null });
    }
}

