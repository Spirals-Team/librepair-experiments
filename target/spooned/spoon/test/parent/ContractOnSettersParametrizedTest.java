package spoon.test.parent;


@org.junit.runner.RunWith(org.junit.runners.Parameterized.class)
public class ContractOnSettersParametrizedTest<T extends spoon.reflect.visitor.CtVisitable> {
    private static spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();

    private static final java.util.List<spoon.reflect.declaration.CtType<? extends spoon.reflect.declaration.CtElement>> allInstantiableMetamodelInterfaces = spoon.test.SpoonTestHelpers.getAllInstantiableMetamodelInterfaces();

    @org.junit.runners.Parameterized.Parameters(name = "{0}")
    public static java.util.Collection<java.lang.Object[]> data() throws java.lang.Exception {
        return spoon.test.parent.ContractOnSettersParametrizedTest.createReceiverList();
    }

    public static java.util.Collection<java.lang.Object[]> createReceiverList() throws java.lang.Exception {
        java.util.List<java.lang.Object[]> values = new java.util.ArrayList<>();
        for (spoon.reflect.declaration.CtType t : spoon.test.parent.ContractOnSettersParametrizedTest.allInstantiableMetamodelInterfaces) {
            if (!(spoon.reflect.reference.CtReference.class.isAssignableFrom(t.getActualClass()))) {
                values.add(new java.lang.Object[]{ t });
            }
        }
        return values;
    }

    @org.junit.runners.Parameterized.Parameter(0)
    public spoon.reflect.declaration.CtType<?> toTest;

    class ModelChangeListener extends spoon.experimental.modelobs.ActionBasedChangeListenerImpl {
        int nbCallsToOnAction = 0;

        java.util.List changedElements = new java.util.ArrayList();

        @java.lang.Override
        public void onAction(spoon.experimental.modelobs.action.Action action) {
            super.onAction(action);
            changedElements.add(action.getContext().getElementWhereChangeHappens());
            (nbCallsToOnAction)++;
        }
    }

    spoon.test.parent.ContractOnSettersParametrizedTest<T>.ModelChangeListener changeListener = new ModelChangeListener();

    public static java.lang.Object createCompatibleObject(spoon.reflect.reference.CtTypeReference<?> parameterType) {
        java.lang.Class<?> c = parameterType.getActualClass();
        for (spoon.reflect.declaration.CtType t : spoon.test.parent.ContractOnSettersParametrizedTest.allInstantiableMetamodelInterfaces) {
            if (c.isAssignableFrom(t.getActualClass())) {
                spoon.reflect.declaration.CtElement argument = spoon.test.parent.ContractOnSettersParametrizedTest.factory.Core().create(t.getActualClass());
                if (argument instanceof spoon.reflect.declaration.CtPackage) {
                    ((spoon.reflect.declaration.CtPackage) (argument)).setSimpleName(argument.getShortRepresentation());
                }
                return argument;
            }
        }
        if (java.util.Set.class.isAssignableFrom(c)) {
            java.util.HashSet<java.lang.Object> objects = new java.util.HashSet<>();
            objects.add(spoon.test.parent.ContractOnSettersParametrizedTest.createCompatibleObject(parameterType.getActualTypeArguments().get(0)));
            return objects;
        }
        if (java.util.Collection.class.isAssignableFrom(c)) {
            java.util.ArrayList<java.lang.Object> objects = new java.util.ArrayList<>();
            objects.add(spoon.test.parent.ContractOnSettersParametrizedTest.createCompatibleObject(parameterType.getActualTypeArguments().get(0)));
            return objects;
        }
        throw new java.lang.IllegalArgumentException(("cannot instantiate " + parameterType));
    }

    static int nTotalSetterCalls = 0;

    @org.junit.Test
    public void testContract() throws java.lang.Throwable {
        spoon.test.parent.ContractOnSettersParametrizedTest.factory.getEnvironment().setModelChangeListener(changeListener);
        int nSetterCalls = 0;
        int nAssertsOnParent = 0;
        int nAssertsOnParentInList = 0;
        java.lang.Object o = spoon.test.parent.ContractOnSettersParametrizedTest.factory.Core().create(((java.lang.Class<? extends spoon.reflect.declaration.CtElement>) (toTest.getActualClass())));
        for (spoon.reflect.declaration.CtMethod<?> setter : spoon.test.SpoonTestHelpers.getAllSetters(toTest)) {
            java.lang.Object argument = spoon.test.parent.ContractOnSettersParametrizedTest.createCompatibleObject(setter.getParameters().get(0).getType());
            try {
                spoon.reflect.declaration.CtElement receiver = ((spoon.reflect.declaration.CtElement) (o)).clone();
                java.lang.reflect.Method actualMethod = setter.getReference().getActualMethod();
                int nBefore = changeListener.nbCallsToOnAction;
                changeListener.changedElements = new java.util.ArrayList();
                actualMethod.invoke(receiver, new java.lang.Object[]{ argument });
                int nAfter = changeListener.nbCallsToOnAction;
                org.junit.Assert.assertTrue(actualMethod.getName(), (nBefore < nAfter));
                nSetterCalls++;
                (spoon.test.parent.ContractOnSettersParametrizedTest.nTotalSetterCalls)++;
                if ((spoon.reflect.declaration.CtElement.class.isInstance(argument)) && ((setter.getAnnotation(spoon.support.UnsettableProperty.class)) == null)) {
                    nAssertsOnParent++;
                    org.junit.Assert.assertTrue(((spoon.reflect.declaration.CtElement) (argument)).hasParent(receiver));
                }
                if ((java.util.Collection.class.isInstance(argument)) && ((setter.getAnnotation(spoon.support.UnsettableProperty.class)) == null)) {
                    nAssertsOnParentInList++;
                    org.junit.Assert.assertTrue(((spoon.reflect.declaration.CtElement) (((java.util.Collection) (argument)).iterator().next())).hasParent(receiver));
                }
            } catch (java.lang.AssertionError e) {
                java.lang.System.err.println(("one contract failed for " + (setter.toString())));
                throw e;
            } catch (java.lang.reflect.InvocationTargetException e) {
                if ((e.getCause()) instanceof java.lang.UnsupportedOperationException) {
                    throw e;
                }else
                    if ((e.getCause()) instanceof java.lang.RuntimeException) {
                        throw e.getCause();
                    }else {
                        throw new spoon.SpoonException(e.getCause());
                    }

            }
        }
        org.junit.Assert.assertTrue((nSetterCalls > 0));
        org.junit.Assert.assertTrue(((nAssertsOnParent > 0) || (nAssertsOnParentInList > 0)));
    }
}

