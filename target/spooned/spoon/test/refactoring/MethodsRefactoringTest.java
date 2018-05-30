package spoon.test.refactoring;


public class MethodsRefactoringTest {
    @org.junit.Test
    public void testSubInheritanceHierarchyFunction() {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(new java.io.File("./src/test/java/spoon/test/refactoring/parameter/testclasses"));
        java.util.List<java.lang.String> allSubtypes = factory.Class().get(spoon.test.refactoring.parameter.testclasses.TypeA.class).map(new spoon.reflect.visitor.filter.SubInheritanceHierarchyFunction()).map((spoon.reflect.declaration.CtType type) -> type.getQualifiedName()).list();
        checkContainsOnly(allSubtypes, "spoon.test.refactoring.parameter.testclasses.TypeB", "spoon.test.refactoring.parameter.testclasses.TypeB$1", "spoon.test.refactoring.parameter.testclasses.TypeC");
        allSubtypes = factory.Class().get(spoon.test.refactoring.parameter.testclasses.TypeB.class).map(new spoon.reflect.visitor.filter.SubInheritanceHierarchyFunction()).map((spoon.reflect.declaration.CtType type) -> type.getQualifiedName()).list();
        checkContainsOnly(allSubtypes, "spoon.test.refactoring.parameter.testclasses.TypeB$1", "spoon.test.refactoring.parameter.testclasses.TypeC");
        allSubtypes = factory.Class().get(spoon.test.refactoring.parameter.testclasses.TypeC.class).map(new spoon.reflect.visitor.filter.SubInheritanceHierarchyFunction()).map((spoon.reflect.declaration.CtType type) -> type.getQualifiedName()).list();
        org.junit.Assert.assertEquals(0, allSubtypes.size());
        allSubtypes = factory.Interface().get(spoon.test.refactoring.parameter.testclasses.IFaceB.class).map(new spoon.reflect.visitor.filter.SubInheritanceHierarchyFunction()).map((spoon.reflect.declaration.CtType type) -> type.getQualifiedName()).list();
        checkContainsOnly(allSubtypes, "spoon.test.refactoring.parameter.testclasses.TypeB", "spoon.test.refactoring.parameter.testclasses.TypeB$1", "spoon.test.refactoring.parameter.testclasses.TypeB$1Local", "spoon.test.refactoring.parameter.testclasses.TypeB$2", "spoon.test.refactoring.parameter.testclasses.TypeC", "spoon.test.refactoring.parameter.testclasses.IFaceL", "spoon.test.refactoring.parameter.testclasses.TypeL", "spoon.test.refactoring.parameter.testclasses.TypeM");
        allSubtypes = factory.Interface().get(spoon.test.refactoring.parameter.testclasses.IFaceL.class).map(new spoon.reflect.visitor.filter.SubInheritanceHierarchyFunction()).map((spoon.reflect.declaration.CtType type) -> type.getQualifiedName()).list();
        checkContainsOnly(allSubtypes, "spoon.test.refactoring.parameter.testclasses.TypeB$1Local", "spoon.test.refactoring.parameter.testclasses.TypeL", "spoon.test.refactoring.parameter.testclasses.TypeM");
        allSubtypes = factory.Interface().get(spoon.test.refactoring.parameter.testclasses.IFaceK.class).map(new spoon.reflect.visitor.filter.SubInheritanceHierarchyFunction()).map((spoon.reflect.declaration.CtType type) -> type.getQualifiedName()).list();
        checkContainsOnly(allSubtypes, "spoon.test.refactoring.parameter.testclasses.TypeB$1Local", "spoon.test.refactoring.parameter.testclasses.TypeL", "spoon.test.refactoring.parameter.testclasses.TypeM", "spoon.test.refactoring.parameter.testclasses.TypeK", "spoon.test.refactoring.parameter.testclasses.TypeR", "spoon.test.refactoring.parameter.testclasses.TypeS");
    }

    private void checkContainsOnly(java.util.List<java.lang.String> foundNames, java.lang.String... expectedNames) {
        for (java.lang.String name : expectedNames) {
            org.junit.Assert.assertTrue((("The " + name) + " not found"), foundNames.remove(name));
        }
        org.junit.Assert.assertTrue(("Unexpected names found: " + foundNames), foundNames.isEmpty());
    }

    @org.junit.Test
    public void testAllMethodsSameSignatureFunction() {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(new java.io.File("./src/test/java/spoon/test/refactoring/parameter/testclasses"));
        java.util.List<spoon.reflect.declaration.CtExecutable<?>> executablesOfHierarchyA = getExecutablesOfHierarchy(factory, "A_method1");
        checkMethodHierarchies(executablesOfHierarchyA);
        java.util.List<spoon.reflect.declaration.CtExecutable<?>> executablesOfHierarchyR = getExecutablesOfHierarchy(factory, "R_method1");
        checkMethodHierarchies(executablesOfHierarchyR);
        spoon.reflect.declaration.CtConstructor<?> constructorTypeA = factory.Class().get(spoon.test.refactoring.parameter.testclasses.TypeA.class).getConstructors().iterator().next();
        spoon.reflect.declaration.CtExecutable<?> exec = constructorTypeA.map(new spoon.reflect.visitor.filter.AllMethodsSameSignatureFunction()).first();
        org.junit.Assert.assertNull(("Unexpected executable found by Constructor of TypeA " + exec), exec);
        spoon.reflect.declaration.CtConstructor<?> constructorTypeB = factory.Class().get(spoon.test.refactoring.parameter.testclasses.TypeB.class).getConstructors().iterator().next();
        exec = constructorTypeA.map(new spoon.reflect.visitor.filter.AllMethodsSameSignatureFunction()).first();
        org.junit.Assert.assertNull(("Unexpected executable found by Constructor of TypeA " + exec), exec);
        org.junit.Assert.assertSame(constructorTypeA, constructorTypeA.map(new spoon.reflect.visitor.filter.AllMethodsSameSignatureFunction().includingSelf(true)).first());
    }

    private void checkMethodHierarchies(java.util.List<spoon.reflect.declaration.CtExecutable<?>> expectedExecutables) {
        int countOfTestedLambdas = 0;
        int countOfTestedMethods = 0;
        for (spoon.reflect.declaration.CtExecutable<?> ctExecutable : expectedExecutables) {
            if (ctExecutable instanceof spoon.reflect.code.CtLambda) {
                countOfTestedLambdas++;
            }else {
                org.junit.Assert.assertTrue((ctExecutable instanceof spoon.reflect.declaration.CtMethod));
                countOfTestedMethods++;
            }
            checkMethodHierarchy(expectedExecutables, ctExecutable);
        }
        org.junit.Assert.assertTrue((countOfTestedLambdas > 0));
        org.junit.Assert.assertTrue((countOfTestedMethods > 0));
    }

    private void checkMethodHierarchy(java.util.List<spoon.reflect.declaration.CtExecutable<?>> expectedExecutables, spoon.reflect.declaration.CtExecutable startExecutable) {
        {
            final java.util.List<spoon.reflect.declaration.CtExecutable<?>> executables = startExecutable.map(new spoon.reflect.visitor.filter.AllMethodsSameSignatureFunction()).list();
            org.junit.Assert.assertFalse(("Unexpected start executable " + startExecutable), containsSame(executables, startExecutable));
            org.junit.Assert.assertTrue(((executables.size()) > 0));
            expectedExecutables.forEach(( m) -> {
                boolean found = removeSame(executables, m);
                if (startExecutable == m) {
                    org.junit.Assert.assertFalse((("The signature " + (getQSignature(m))) + " was returned too"), found);
                }else {
                    org.junit.Assert.assertTrue((("The signature " + (getQSignature(m))) + " not found"), found);
                }
            });
            org.junit.Assert.assertTrue(("Unexpected executables: " + executables), executables.isEmpty());
        }
        {
            final java.util.List<spoon.reflect.declaration.CtExecutable<?>> executables = startExecutable.map(new spoon.reflect.visitor.filter.AllMethodsSameSignatureFunction().includingSelf(true)).list();
            org.junit.Assert.assertTrue(("Missing start executable " + startExecutable), containsSame(executables, startExecutable));
            org.junit.Assert.assertTrue(((executables.size()) > 0));
            expectedExecutables.forEach(( m) -> {
                org.junit.Assert.assertTrue((("The signature " + (getQSignature(m))) + " not found"), removeSame(executables, m));
            });
            org.junit.Assert.assertTrue(("Unexpected executables: " + executables), executables.isEmpty());
        }
        {
            final java.util.List<spoon.reflect.declaration.CtExecutable<?>> executables = startExecutable.map(new spoon.reflect.visitor.filter.AllMethodsSameSignatureFunction().includingSelf(true).includingLambdas(false)).list();
            if (startExecutable instanceof spoon.reflect.code.CtLambda) {
                org.junit.Assert.assertFalse(("Unexpected start executable " + startExecutable), containsSame(executables, startExecutable));
            }else {
                org.junit.Assert.assertTrue(("Missing start executable " + startExecutable), containsSame(executables, startExecutable));
            }
            org.junit.Assert.assertTrue(((executables.size()) > 0));
            expectedExecutables.forEach(( m) -> {
                if (m instanceof spoon.reflect.code.CtLambda) {
                    return;
                }
                org.junit.Assert.assertTrue((("The signature " + (getQSignature(m))) + " not found"), removeSame(executables, m));
            });
            org.junit.Assert.assertTrue(("Unexepcted executables " + executables), executables.isEmpty());
        }
        spoon.reflect.declaration.CtExecutable<?> exec = startExecutable.map(new spoon.reflect.visitor.filter.AllMethodsSameSignatureFunction().includingSelf(true)).first();
        org.junit.Assert.assertSame(startExecutable, exec);
        exec = startExecutable.map(new spoon.reflect.visitor.filter.AllMethodsSameSignatureFunction().includingSelf(false)).first();
        org.junit.Assert.assertNotSame(startExecutable, exec);
        org.junit.Assert.assertTrue(containsSame(expectedExecutables, exec));
    }

    private java.lang.String getQSignature(spoon.reflect.declaration.CtExecutable e) {
        if (e instanceof spoon.reflect.declaration.CtMethod<?>) {
            spoon.reflect.declaration.CtMethod<?> m = ((spoon.reflect.declaration.CtMethod<?>) (e));
            return ((m.getDeclaringType().getQualifiedName()) + "#") + (m.getSignature());
        }
        return e.getShortRepresentation();
    }

    private java.util.List<spoon.reflect.declaration.CtExecutable<?>> getExecutablesOfHierarchy(spoon.reflect.factory.Factory factory, java.lang.String hierarchyName) {
        return factory.getModel().filterChildren(new spoon.reflect.visitor.filter.TypeFilter(spoon.reflect.declaration.CtExecutable.class)).select((spoon.reflect.declaration.CtExecutable<?> exec) -> {
            spoon.reflect.declaration.CtElement ele = exec;
            if (exec instanceof spoon.reflect.code.CtLambda) {
                java.util.List<spoon.reflect.code.CtStatement> stats = exec.getBody().getStatements();
                if ((stats.size()) > 0) {
                    ele = stats.get(0);
                }
            }
            spoon.test.refactoring.parameter.testclasses.TestHierarchy th = ele.getAnnotation(spoon.test.refactoring.parameter.testclasses.TestHierarchy.class);
            if (th != null) {
                return (java.util.Arrays.asList(th.value()).indexOf(hierarchyName)) >= 0;
            }
            return false;
        }).list();
    }

    @org.junit.Test
    public void testExecutableReferenceFilter() {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(new java.io.File("./src/test/java/spoon/test/refactoring/parameter/testclasses"));
        java.util.List<spoon.reflect.declaration.CtExecutable<?>> executables = factory.getModel().filterChildren((spoon.reflect.declaration.CtExecutable<?> e) -> true).list();
        int nrExecRefsTotal = 0;
        for (spoon.reflect.declaration.CtExecutable<?> executable : executables) {
            nrExecRefsTotal += checkExecutableReferenceFilter(factory, java.util.Collections.singletonList(executable));
        }
        int nrExecRefsTotal2 = checkExecutableReferenceFilter(factory, executables);
        org.junit.Assert.assertSame(nrExecRefsTotal, nrExecRefsTotal2);
        spoon.reflect.code.CtLambda lambda = factory.getModel().filterChildren((spoon.reflect.code.CtLambda<?> e) -> true).first();
        org.junit.Assert.assertNotNull(lambda);
        spoon.reflect.reference.CtExecutableReference<?> lambdaRef = lambda.getReference();
        java.util.List<spoon.reflect.reference.CtExecutableReference<?>> refs = lambdaRef.filterChildren(null).select(new spoon.reflect.visitor.filter.ExecutableReferenceFilter(lambda)).list();
        org.junit.Assert.assertEquals(1, refs.size());
        org.junit.Assert.assertSame(lambdaRef, refs.get(0));
    }

    private int checkExecutableReferenceFilter(spoon.reflect.factory.Factory factory, java.util.List<spoon.reflect.declaration.CtExecutable<?>> executables) {
        org.junit.Assert.assertTrue(((executables.size()) > 0));
        spoon.reflect.visitor.filter.ExecutableReferenceFilter execRefFilter = new spoon.reflect.visitor.filter.ExecutableReferenceFilter();
        executables.forEach((spoon.reflect.declaration.CtExecutable<?> e) -> execRefFilter.addExecutable(e));
        final java.util.List<spoon.reflect.reference.CtExecutableReference<?>> refs = new java.util.ArrayList<>(factory.getModel().filterChildren(execRefFilter).list());
        int nrExecRefs = refs.size();
        factory.getModel().filterChildren((spoon.reflect.reference.CtExecutableReference er) -> {
            return containsSame(executables, er.getDeclaration());
        }).forEach((spoon.reflect.reference.CtExecutableReference er) -> {
            org.junit.Assert.assertTrue((("Executable reference: " + er) + " not found."), refs.remove(er));
        });
        org.junit.Assert.assertSame(0, refs.size());
        return nrExecRefs;
    }

    private boolean containsSame(java.util.Collection list, java.lang.Object item) {
        for (java.lang.Object object : list) {
            if (object == item) {
                return true;
            }
        }
        return false;
    }

    private boolean removeSame(java.util.Collection list, java.lang.Object item) {
        for (java.util.Iterator iter = list.iterator(); iter.hasNext();) {
            java.lang.Object object = ((java.lang.Object) (iter.next()));
            if (object == item) {
                iter.remove();
                return true;
            }
        }
        return false;
    }

    @org.junit.Test
    public void testCtParameterRemoveRefactoring() throws java.io.FileNotFoundException {
        java.lang.String testPackagePath = "spoon/test/refactoring/parameter/testclasses";
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        spoon.SpoonModelBuilder comp = launcher.createCompiler();
        comp.addInputSource(spoon.compiler.SpoonResourceHelper.createResource(new java.io.File(("./src/test/java/" + testPackagePath))));
        comp.build();
        spoon.reflect.factory.Factory factory = comp.getFactory();
        spoon.reflect.declaration.CtType<?> typeA = factory.Class().get(spoon.test.refactoring.parameter.testclasses.TypeA.class);
        spoon.reflect.declaration.CtMethod<?> methodTypeA_method1 = typeA.getMethodsByName("method1").get(0);
        spoon.refactoring.CtParameterRemoveRefactoring refactor = new spoon.refactoring.CtParameterRemoveRefactoring();
        refactor.setTarget(methodTypeA_method1.getParameters().get(0));
        java.util.List<spoon.reflect.declaration.CtExecutable<?>> execs = refactor.getTargetExecutables();
        execs.forEach(( exec) -> {
            org.junit.Assert.assertEquals(1, exec.getParameters().size());
        });
        refactor.refactor();
        execs.forEach(( exec) -> {
            org.junit.Assert.assertEquals(0, exec.getParameters().size());
        });
        launcher.setSourceOutputDirectory(new java.io.File("./target/spooned/"));
        launcher.getModelBuilder().generateProcessedSourceFiles(spoon.OutputType.CLASSES);
        spoon.testing.utils.ModelUtils.canBeBuilt(("./target/spooned/" + testPackagePath), 8);
    }

    @org.junit.Test
    public void testCtParameterRemoveRefactoringValidationCheck() throws java.io.FileNotFoundException {
        java.lang.String testPackagePath = "spoon/test/refactoring/parameter/testclasses";
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        spoon.SpoonModelBuilder comp = launcher.createCompiler();
        comp.addInputSource(spoon.compiler.SpoonResourceHelper.createResource(new java.io.File(("./src/test/java/" + testPackagePath))));
        comp.build();
        spoon.reflect.factory.Factory factory = comp.getFactory();
        spoon.reflect.declaration.CtType<?> typeR = factory.Class().get(spoon.test.refactoring.parameter.testclasses.TypeR.class);
        spoon.reflect.declaration.CtMethod<?> methodTypeR_method1 = typeR.getMethodsByName("method1").get(0);
        spoon.refactoring.CtParameterRemoveRefactoring refactor = new spoon.refactoring.CtParameterRemoveRefactoring().setTarget(methodTypeR_method1.getParameters().get(0));
        refactor.setTarget(methodTypeR_method1.getParameters().get(0));
        java.util.List<spoon.reflect.declaration.CtExecutable<?>> execs = refactor.getTargetExecutables();
        execs.forEach(( exec) -> {
            org.junit.Assert.assertEquals(1, exec.getParameters().size());
        });
        try {
            refactor.refactor();
            org.junit.Assert.fail();
        } catch (spoon.refactoring.RefactoringException e) {
            this.getClass();
        }
    }
}

