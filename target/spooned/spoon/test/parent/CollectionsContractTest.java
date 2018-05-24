package spoon.test.parent;


@org.junit.runner.RunWith(org.junit.runners.Parameterized.class)
public class CollectionsContractTest<T extends spoon.reflect.visitor.CtVisitable> {
    private static spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();

    private static final java.util.List<spoon.reflect.declaration.CtType<? extends spoon.reflect.declaration.CtElement>> allInstantiableMetamodelInterfaces = spoon.test.SpoonTestHelpers.getAllInstantiableMetamodelInterfaces();

    @org.junit.runners.Parameterized.Parameters(name = "{0}")
    public static java.util.Collection<java.lang.Object[]> data() throws java.lang.Exception {
        return spoon.test.parent.CollectionsContractTest.createReceiverList();
    }

    public static java.util.Collection<java.lang.Object[]> createReceiverList() throws java.lang.Exception {
        spoon.test.parent.CollectionsContractTest.metamodel = new spoon.test.metamodel.SpoonMetaModel(new java.io.File("src/main/java"));
        spoon.test.parent.CollectionsContractTest.allProblems = new java.util.ArrayList<>();
        java.util.List<java.lang.Object[]> values = new java.util.ArrayList<>();
        for (spoon.test.metamodel.MetamodelConcept mmC : spoon.test.parent.CollectionsContractTest.metamodel.getConcepts()) {
            if ((mmC.getKind()) == (spoon.test.metamodel.MMTypeKind.LEAF)) {
                values.add(new java.lang.Object[]{ mmC });
            }
        }
        return values;
    }

    @org.junit.AfterClass
    public static void reportAllProblems() {
        java.lang.System.out.println("Expected collection handling:");
        java.lang.System.out.println(spoon.test.parent.CollectionsContractTest.allExpected.stream().sorted().collect(java.util.stream.Collectors.joining("\n")));
        if ((spoon.test.parent.CollectionsContractTest.allProblems.size()) > 0) {
            java.lang.System.out.println("-----------------------------");
            java.lang.System.out.println("Wrong collection handling:");
            java.lang.System.out.println(spoon.test.parent.CollectionsContractTest.allProblems.stream().sorted().collect(java.util.stream.Collectors.joining("\n")));
        }
    }

    private static spoon.test.metamodel.SpoonMetaModel metamodel;

    @org.junit.runners.Parameterized.Parameter(0)
    public spoon.test.metamodel.MetamodelConcept mmConcept;

    enum CollectionKind {
        READ_ONLY, MUTABLE_DETACHED, MUTABLE_ATTACHED_INCORRECT, MUTABLE_ATTACHED_CORRECT;}

    static java.util.Set<spoon.reflect.path.CtRole> ignoredRoles = new java.util.HashSet<>(java.util.Arrays.asList(spoon.reflect.path.CtRole.POSITION, spoon.reflect.path.CtRole.MODIFIER));

    static java.util.List<java.lang.String> allProblems = new java.util.ArrayList<>();

    static java.util.List<java.lang.String> allExpected = new java.util.ArrayList<>();

    @org.junit.Test
    public void testContract() throws java.lang.Throwable {
        java.lang.Class<? extends spoon.reflect.declaration.CtElement> elementClass = ((java.lang.Class<? extends spoon.reflect.declaration.CtElement>) (mmConcept.getModelInterface().getActualClass()));
        java.util.List<java.lang.String> problems = new java.util.ArrayList<>();
        java.util.List<java.lang.String> expected = new java.util.ArrayList<>();
        for (spoon.test.metamodel.MetamodelProperty mmProperty : mmConcept.getRoleToProperty().values()) {
            if (((mmProperty.getValueContainerType()) == (spoon.reflect.meta.ContainerKind.SINGLE)) || (spoon.test.parent.CollectionsContractTest.ignoredRoles.contains(mmProperty.getRole()))) {
                continue;
            }
            spoon.reflect.declaration.CtElement[] arguments = new spoon.reflect.declaration.CtElement[]{ ((spoon.reflect.declaration.CtElement) (spoon.test.parent.ParentContractTest.createCompatibleObject(mmProperty.getItemValueType()))), ((spoon.reflect.declaration.CtElement) (spoon.test.parent.ParentContractTest.createCompatibleObject(mmProperty.getItemValueType()))) };
            spoon.reflect.meta.RoleHandler roleHandler = spoon.reflect.meta.impl.RoleHandlerHelper.getRoleHandler(elementClass, mmProperty.getRole());
            spoon.test.parent.CollectionsContractTest.CollectionKind[] colKind;
            spoon.reflect.declaration.CtElement testedElement = spoon.test.parent.CollectionsContractTest.factory.Core().create(elementClass);
            if (elementClass.equals(spoon.reflect.reference.CtTypeReference.class)) {
                testedElement = spoon.test.parent.CollectionsContractTest.factory.Type().createReference(java.util.ArrayList.class);
            }
            try {
                colKind = detectCollectionKind(mmProperty, roleHandler, testedElement, arguments);
            } catch (java.lang.Throwable e) {
                problems.add(((((((("Failed check of;" + (mmConcept)) + "#") + (mmProperty.getName())) + ". ") + (e.getClass().getSimpleName())) + " : ") + (e.getMessage())));
                continue;
            }
            java.lang.String colKindStr = java.util.Arrays.asList(colKind).stream().map(spoon.test.parent.CollectionsContractTest.CollectionKind::name).collect(java.util.stream.Collectors.joining(", ", "[", "]"));
            if (mmProperty.isDerived()) {
                if ((containsOnly(colKind, spoon.test.parent.CollectionsContractTest.CollectionKind.READ_ONLY)) == false) {
                    problems.add((((("derived;" + colKindStr) + (mmProperty.getName())) + " of ") + (mmConcept)));
                }else {
                    expected.add((((("derived;" + colKindStr) + (mmProperty.getName())) + " of ") + (mmConcept)));
                }
            }else {
                if ((containsOnly(colKind, spoon.test.parent.CollectionsContractTest.CollectionKind.MUTABLE_ATTACHED_CORRECT)) == false) {
                    problems.add((((("normal;" + colKindStr) + (mmProperty.getName())) + " of ") + (mmConcept)));
                }else {
                    expected.add((((("normal;" + colKindStr) + (mmProperty.getName())) + " of ") + (mmConcept)));
                }
            }
        }
        spoon.test.parent.CollectionsContractTest.allExpected.addAll(expected);
        if ((problems.size()) > 0) {
            spoon.test.parent.CollectionsContractTest.allProblems.addAll(problems);
            org.junit.Assert.fail(java.lang.String.join("\n", problems));
        }
    }

    private boolean containsOnly(spoon.test.parent.CollectionsContractTest.CollectionKind[] cks, spoon.test.parent.CollectionsContractTest.CollectionKind expected) {
        for (spoon.test.parent.CollectionsContractTest.CollectionKind collectionKind : cks) {
            if (collectionKind != expected) {
                return false;
            }
        }
        return true;
    }

    private spoon.test.parent.CollectionsContractTest.CollectionKind[] detectCollectionKind(spoon.test.metamodel.MetamodelProperty mmProperty, spoon.reflect.meta.RoleHandler roleHandler, spoon.reflect.declaration.CtElement testedElement, spoon.reflect.declaration.CtElement... argument) {
        switch (roleHandler.getContainerKind()) {
            case MAP :
                return detectCollectionKindOfMap(mmProperty, roleHandler, testedElement, argument);
            case LIST :
            case SET :
                return detectCollectionKindOfCollection(mmProperty, roleHandler, testedElement, argument);
            case SINGLE :
                throw new spoon.SpoonException("Single is not tested here");
        }
        throw new spoon.SpoonException(("Unexpected container kind " + (roleHandler.getContainerKind())));
    }

    static class ChangeListener extends spoon.experimental.modelobs.ActionBasedChangeListenerImpl {
        java.util.List<spoon.experimental.modelobs.action.Action> actions = new java.util.ArrayList<>();

        @java.lang.Override
        public void onAction(spoon.experimental.modelobs.action.Action action) {
            actions.add(action);
        }
    }

    java.util.Set<spoon.reflect.path.CtRole> setRoles = new java.util.HashSet<>(java.util.Arrays.asList(spoon.reflect.path.CtRole.MODULE_DIRECTIVE, spoon.reflect.path.CtRole.SERVICE_TYPE, spoon.reflect.path.CtRole.EXPORTED_PACKAGE, spoon.reflect.path.CtRole.OPENED_PACKAGE, spoon.reflect.path.CtRole.REQUIRED_MODULE, spoon.reflect.path.CtRole.PROVIDED_SERVICE, spoon.reflect.path.CtRole.BOUND, spoon.reflect.path.CtRole.VALUE));

    private spoon.test.parent.CollectionsContractTest.CollectionKind[] detectCollectionKindOfCollection(spoon.test.metamodel.MetamodelProperty mmProperty, spoon.reflect.meta.RoleHandler roleHandler, spoon.reflect.declaration.CtElement testedElement, spoon.reflect.declaration.CtElement... arguments) {
        if ((roleHandler.getRole()) == (spoon.reflect.path.CtRole.MODULE_DIRECTIVE)) {
            this.getClass();
        }
        spoon.test.parent.CollectionsContractTest.CollectionKind[] ck = new spoon.test.parent.CollectionsContractTest.CollectionKind[arguments.length];
        for (int i = 0; i < (arguments.length); i++) {
            spoon.reflect.declaration.CtElement argument = arguments[i];
            spoon.reflect.declaration.CtElement parentOfArgument = getParentOrNull(argument);
            java.util.Collection col;
            spoon.test.parent.CollectionsContractTest.ChangeListener changeListener = new spoon.test.parent.CollectionsContractTest.ChangeListener();
            testedElement.getFactory().getEnvironment().setModelChangeListener(changeListener);
            col = ((java.util.Collection) (roleHandler.getValue(testedElement)));
            try {
                col.add(argument);
            } catch (java.lang.UnsupportedOperationException e) {
                ck[i] = spoon.test.parent.CollectionsContractTest.CollectionKind.READ_ONLY;
                boolean isSet;
                try {
                    java.util.Collection c = roleHandler.asCollection(testedElement);
                    isSet = (c instanceof java.util.Set) || (setRoles.contains(roleHandler.getRole()));
                    c.add(argument);
                } catch (java.lang.UnsupportedOperationException e2) {
                    if (mmProperty.isDerived()) {
                        continue;
                    }
                    throw e2;
                }
                java.util.Collection col2 = ((java.util.Collection) (roleHandler.getValue(testedElement)));
                if (mmProperty.isUnsettable()) {
                    org.junit.Assert.assertFalse(col2.contains(argument));
                    org.junit.Assert.assertSame(parentOfArgument, getParentOrNull(argument));
                    org.junit.Assert.assertTrue(changeListener.actions.isEmpty());
                }else {
                    if ((i > 0) && isSet) {
                        continue;
                    }
                    org.junit.Assert.assertTrue(col2.contains(argument));
                    org.junit.Assert.assertTrue(argument.isParentInitialized());
                    org.junit.Assert.assertTrue(argument.hasParent(testedElement));
                    org.junit.Assert.assertTrue(((changeListener.actions.size()) > 0));
                }
                continue;
            }
            java.util.Collection col2 = ((java.util.Collection) (roleHandler.getValue(testedElement)));
            if ((col2.contains(argument)) == false) {
                ck[i] = spoon.test.parent.CollectionsContractTest.CollectionKind.MUTABLE_DETACHED;
                continue;
            }
            if ((argument.isParentInitialized()) && ((argument.getParent()) == testedElement)) {
                if ((changeListener.actions.size()) > 0) {
                    ck[i] = spoon.test.parent.CollectionsContractTest.CollectionKind.MUTABLE_ATTACHED_CORRECT;
                    continue;
                }
            }
            ck[i] = spoon.test.parent.CollectionsContractTest.CollectionKind.MUTABLE_ATTACHED_INCORRECT;
        }
        return ck;
    }

    private spoon.reflect.declaration.CtElement getParentOrNull(spoon.reflect.declaration.CtElement argument) {
        if (argument.isParentInitialized()) {
            return argument.getParent();
        }
        return null;
    }

    private spoon.test.parent.CollectionsContractTest.CollectionKind[] detectCollectionKindOfMap(spoon.test.metamodel.MetamodelProperty mmProperty, spoon.reflect.meta.RoleHandler roleHandler, spoon.reflect.declaration.CtElement testedElement, spoon.reflect.declaration.CtElement... arguments) {
        java.util.Map<java.lang.String, spoon.reflect.declaration.CtElement> col = ((java.util.Map) (roleHandler.getValue(testedElement)));
        spoon.test.parent.CollectionsContractTest.CollectionKind[] ck = new spoon.test.parent.CollectionsContractTest.CollectionKind[arguments.length];
        for (int i = 0; i < (arguments.length); i++) {
            java.lang.String key = "x" + i;
            spoon.reflect.declaration.CtElement argument = arguments[i];
            spoon.reflect.declaration.CtElement parentOfArgument = getParentOrNull(argument);
            spoon.test.parent.CollectionsContractTest.ChangeListener changeListener = new spoon.test.parent.CollectionsContractTest.ChangeListener();
            testedElement.getFactory().getEnvironment().setModelChangeListener(changeListener);
            try {
                col.put(key, argument);
            } catch (java.lang.UnsupportedOperationException e) {
                ck[i] = spoon.test.parent.CollectionsContractTest.CollectionKind.READ_ONLY;
                try {
                    roleHandler.asMap(testedElement).put(key, argument);
                } catch (java.lang.UnsupportedOperationException e2) {
                    if (mmProperty.isDerived()) {
                        continue;
                    }
                    throw e2;
                }
                col = ((java.util.Map) (roleHandler.getValue(testedElement)));
                if (mmProperty.isUnsettable()) {
                    org.junit.Assert.assertNull(col.get(key));
                    org.junit.Assert.assertSame(parentOfArgument, getParentOrNull(argument));
                    org.junit.Assert.assertTrue(changeListener.actions.isEmpty());
                }else {
                    org.junit.Assert.assertSame(argument, col.get(key));
                    org.junit.Assert.assertTrue(argument.isParentInitialized());
                    org.junit.Assert.assertSame(testedElement, argument.getParent());
                    org.junit.Assert.assertTrue(((changeListener.actions.size()) > 0));
                }
                continue;
            }
            java.util.Map<java.lang.String, spoon.reflect.declaration.CtElement> col2 = ((java.util.Map<java.lang.String, spoon.reflect.declaration.CtElement>) (roleHandler.getValue(testedElement)));
            if ((col2.get(key)) != argument) {
                ck[i] = spoon.test.parent.CollectionsContractTest.CollectionKind.MUTABLE_DETACHED;
                continue;
            }
            if ((argument.isParentInitialized()) && ((argument.getParent()) == testedElement)) {
                if ((changeListener.actions.size()) > 0) {
                    ck[i] = spoon.test.parent.CollectionsContractTest.CollectionKind.MUTABLE_ATTACHED_CORRECT;
                    continue;
                }
            }
            ck[i] = spoon.test.parent.CollectionsContractTest.CollectionKind.MUTABLE_ATTACHED_INCORRECT;
        }
        return ck;
    }
}

