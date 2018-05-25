package spoon.test.replace;


@org.junit.runner.RunWith(org.junit.runners.Parameterized.class)
public class ReplaceParametrizedTest<T extends spoon.reflect.visitor.CtVisitable> {
    private static spoon.reflect.factory.Factory factory;

    private static spoon.test.metamodel.SpoonMetaModel metaModel;

    @org.junit.runners.Parameterized.Parameters(name = "{0}")
    public static java.util.Collection<java.lang.Object[]> data() throws java.lang.Exception {
        spoon.test.replace.ReplaceParametrizedTest.metaModel = new spoon.test.metamodel.SpoonMetaModel(new java.io.File("src/main/java"));
        spoon.test.replace.ReplaceParametrizedTest.factory = spoon.test.replace.ReplaceParametrizedTest.metaModel.getFactory();
        java.util.List<java.lang.Object[]> values = new java.util.ArrayList<>();
        for (spoon.test.metamodel.MetamodelConcept t : spoon.test.replace.ReplaceParametrizedTest.metaModel.getConcepts()) {
            if ((t.getKind()) == (spoon.test.metamodel.MMTypeKind.LEAF)) {
                values.add(new java.lang.Object[]{ t });
            }
        }
        return values;
    }

    @org.junit.runners.Parameterized.Parameter(0)
    public spoon.test.metamodel.MetamodelConcept typeToTest;

    @org.junit.Test
    public void testContract() throws java.lang.Throwable {
        java.util.List<java.lang.String> problems = new java.util.ArrayList<>();
        spoon.reflect.declaration.CtType<?> toTest = typeToTest.getModelInterface();
        spoon.reflect.declaration.CtElement o = spoon.test.replace.ReplaceParametrizedTest.factory.Core().create(((java.lang.Class<? extends spoon.reflect.declaration.CtElement>) (toTest.getActualClass())));
        for (spoon.test.metamodel.MetamodelProperty mmField : typeToTest.getRoleToProperty().values()) {
            java.lang.Class<?> argType = mmField.getItemValueType().getActualClass();
            if (!(spoon.reflect.declaration.CtElement.class.isAssignableFrom(argType))) {
                continue;
            }
            spoon.reflect.reference.CtTypeReference<?> itemType = mmField.getItemValueType();
            if (itemType.getQualifiedName().equals(spoon.reflect.code.CtStatement.class.getName())) {
                itemType = spoon.test.replace.ReplaceParametrizedTest.factory.createCtTypeReference(spoon.reflect.code.CtBlock.class);
            }
            if ((o.getClass().getSimpleName().equals("CtAnnotationFieldAccessImpl")) && ((mmField.getRole()) == (spoon.reflect.path.CtRole.VARIABLE))) {
                itemType = spoon.test.replace.ReplaceParametrizedTest.factory.createCtTypeReference(spoon.reflect.reference.CtFieldReference.class);
            }else
                if ((spoon.reflect.code.CtFieldAccess.class.isAssignableFrom(o.getClass())) && ((mmField.getRole()) == (spoon.reflect.path.CtRole.VARIABLE))) {
                    itemType = spoon.test.replace.ReplaceParametrizedTest.factory.createCtTypeReference(spoon.reflect.reference.CtFieldReference.class);
                }

            spoon.reflect.declaration.CtElement argument = ((spoon.reflect.declaration.CtElement) (spoon.test.parent.ParentContractTest.createCompatibleObject(itemType)));
            org.junit.Assert.assertNotNull(argument);
            spoon.reflect.declaration.CtElement receiver = ((spoon.reflect.declaration.CtElement) (o)).clone();
            spoon.reflect.meta.RoleHandler rh = spoon.reflect.meta.impl.RoleHandlerHelper.getRoleHandler(o.getClass(), mmField.getRole());
            if (mmField.isUnsettable()) {
                try {
                    spoon.test.replace.ReplaceParametrizedTest.invokeSetter(rh, receiver, argument);
                } catch (spoon.SpoonException e) {
                    return;
                }
                spoon.reflect.path.CtRole argumentsRoleInParent = argument.getRoleInParent();
                if (argumentsRoleInParent == null) {
                    continue;
                }
                if (argumentsRoleInParent == (mmField.getRole())) {
                    problems.add((("UnsettableProperty " + mmField) + " sets the value"));
                }else {
                    if (mmField.isDerived()) {
                    }else {
                        problems.add(((("UnsettableProperty " + mmField) + " sets the value into different role ") + argumentsRoleInParent));
                    }
                }
                continue;
            }
            spoon.test.replace.ReplaceParametrizedTest.invokeSetter(rh, receiver, argument);
            spoon.reflect.declaration.CtElement finalArgument = argument;
            class Scanner extends spoon.reflect.visitor.CtScanner {
                boolean found = false;

                @java.lang.Override
                public void scan(spoon.reflect.path.CtRole role, spoon.reflect.declaration.CtElement e) {
                    super.scan(role, e);
                    if (e == finalArgument) {
                        if (((rh.getRole()) == role) || ((rh.getRole().getSuperRole()) == role)) {
                            found = true;
                            return;
                        }
                        problems.add(((("Argument was set into " + (rh.getRole())) + " but was found in ") + role));
                    }
                }
            }
            Scanner s = new Scanner();
            receiver.accept(s);
            org.junit.Assert.assertTrue(((("Settable field " + (mmField.toString())) + " should set value.\n") + (getReport(problems))), s.found);
            org.junit.Assert.assertSame(argument, spoon.test.replace.ReplaceParametrizedTest.invokeGetter(rh, receiver));
            final spoon.reflect.declaration.CtElement argument2 = argument.clone();
            org.junit.Assert.assertNotSame(argument, argument2);
            argument.replace(argument2);
            org.junit.Assert.assertTrue((((receiver.getClass().getSimpleName()) + " failed for ") + mmField), ((receiver.getElements(new spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtElement>() {
                @java.lang.Override
                public boolean matches(spoon.reflect.declaration.CtElement element) {
                    return element == argument2;
                }
            }).size()) == 1));
        }
        if ((problems.size()) > 0) {
            org.junit.Assert.fail(getReport(problems));
        }
    }

    private java.lang.String getReport(java.util.List<java.lang.String> problems) {
        if ((problems.size()) > 0) {
            java.lang.StringBuilder report = new java.lang.StringBuilder();
            report.append((("The accessors of " + (typeToTest)) + " have problems:"));
            for (java.lang.String problem : problems) {
                report.append("\n").append(problem);
            }
            return report.toString();
        }
        return "";
    }

    private static void invokeSetter(spoon.reflect.meta.RoleHandler rh, spoon.reflect.declaration.CtElement receiver, spoon.reflect.declaration.CtElement item) {
        if ((rh.getContainerKind()) == (spoon.reflect.meta.ContainerKind.SINGLE)) {
            rh.setValue(receiver, item);
        }else
            if ((rh.getContainerKind()) == (spoon.reflect.meta.ContainerKind.MAP)) {
                rh.asMap(receiver).put("dummyKey", item);
            }else {
                rh.asCollection(receiver).add(item);
            }

    }

    private static spoon.reflect.declaration.CtElement invokeGetter(spoon.reflect.meta.RoleHandler rh, spoon.reflect.declaration.CtElement receiver) {
        if ((rh.getContainerKind()) == (spoon.reflect.meta.ContainerKind.SINGLE)) {
            return rh.getValue(receiver);
        }else
            if ((rh.getContainerKind()) == (spoon.reflect.meta.ContainerKind.MAP)) {
                return ((spoon.reflect.declaration.CtElement) (rh.asMap(receiver).get("dummyKey")));
            }else {
                return ((spoon.reflect.declaration.CtElement) (rh.asCollection(receiver).stream().findFirst().get()));
            }

    }
}

