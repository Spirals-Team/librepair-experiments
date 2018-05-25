package spoon.test.refactoring;


public class CtRenameLocalVariableRefactoringTest {
    @org.junit.Test
    public void testModelConsistency() throws java.lang.Throwable {
        new spoon.test.refactoring.testclasses.CtRenameLocalVariableRefactoringTestSubject().checkModelConsistency();
    }

    private java.lang.String[] DEBUG = new java.lang.String[]{  };

    @org.junit.Test
    public void testRenameAllLocalVariablesOfRenameTestSubject() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        final spoon.SpoonModelBuilder comp = launcher.createCompiler();
        comp.addInputSources(spoon.compiler.SpoonResourceHelper.resources((("./src/test/java/" + (spoon.test.refactoring.testclasses.CtRenameLocalVariableRefactoringTestSubject.class.getName().replace('.', '/'))) + ".java")));
        comp.build();
        final spoon.reflect.factory.Factory factory = comp.getFactory();
        spoon.reflect.declaration.CtClass<?> varRenameClass = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().get(spoon.test.refactoring.testclasses.CtRenameLocalVariableRefactoringTestSubject.class)));
        spoon.reflect.reference.CtTypeReference<spoon.test.refactoring.testclasses.TestTryRename> tryRename = varRenameClass.getFactory().createCtTypeReference(spoon.test.refactoring.testclasses.TestTryRename.class);
        varRenameClass.getMethods().forEach(( method) -> {
            if (((DEBUG.length) == 3) && ((DEBUG[0].equals(method.getSimpleName())) == false))
                return;

            method.filterChildren((spoon.reflect.declaration.CtVariable var) -> true).map((spoon.reflect.declaration.CtVariable var) -> var.getAnnotation(tryRename)).forEach((spoon.reflect.declaration.CtAnnotation<spoon.test.refactoring.testclasses.TestTryRename> annotation) -> {
                java.lang.String[] newNames = annotation.getActualAnnotation().value();
                spoon.reflect.declaration.CtVariable<?> targetVariable = ((spoon.reflect.declaration.CtVariable<?>) (annotation.getAnnotatedElement()));
                for (java.lang.String newName : newNames) {
                    boolean renameShouldPass = (newName.startsWith("-")) == false;
                    if (!renameShouldPass) {
                        newName = newName.substring(1);
                    }
                    if (targetVariable instanceof spoon.reflect.code.CtLocalVariable<?>) {
                        if ((((DEBUG.length) == 3) && (DEBUG[1].equals(targetVariable.getSimpleName()))) && (DEBUG[2].equals(newName))) {
                            this.getClass();
                        }
                        checkLocalVariableRename(launcher, ((spoon.reflect.code.CtLocalVariable<?>) (targetVariable)), newName, renameShouldPass);
                    }else {
                    }
                }
            });
        });
    }

    protected void checkLocalVariableRename(spoon.Launcher launcher, spoon.reflect.code.CtLocalVariable<?> targetVariable, java.lang.String newName, boolean renameShouldPass) {
        java.lang.String originName = targetVariable.getSimpleName();
        spoon.refactoring.CtRenameLocalVariableRefactoring refactor = new spoon.refactoring.CtRenameLocalVariableRefactoring();
        refactor.setTarget(targetVariable);
        refactor.setNewName(newName);
        if (renameShouldPass) {
            try {
                refactor.refactor();
            } catch (spoon.SpoonException e) {
                throw new java.lang.AssertionError((((((((getParentMethodName(targetVariable)) + " Rename of \"") + originName) + "\" should NOT fail when trying rename to \"") + newName) + "\"\n") + (targetVariable.toString())), e);
            }
            org.junit.Assert.assertEquals(((((((getParentMethodName(targetVariable)) + " Rename of \"") + originName) + "\" to \"") + newName) + "\" passed, but the name of variable was not changed"), newName, targetVariable.getSimpleName());
            assertCorrectModel(launcher, ((((((getParentMethodName(targetVariable)) + " Rename of \"") + originName) + "\" to \"") + newName) + "\""));
        }else {
            try {
                refactor.refactor();
                org.junit.Assert.fail(((((((getParentMethodName(targetVariable)) + " Rename of \"") + originName) + "\" should fail when trying rename to \"") + newName) + "\""));
            } catch (spoon.SpoonException e) {
            }
            org.junit.Assert.assertEquals(((((((getParentMethodName(targetVariable)) + " Rename of \"") + originName) + "\" failed when trying rename to \"") + newName) + "\" but the name of variable should not be changed"), originName, targetVariable.getSimpleName());
        }
        if (renameShouldPass) {
            rollback(targetVariable, originName);
        }
        org.junit.Assert.assertEquals(originName, targetVariable.getSimpleName());
    }

    private void rollback(spoon.reflect.code.CtLocalVariable<?> targetVariable, java.lang.String originName) {
        java.lang.String newName = targetVariable.getSimpleName();
        spoon.refactoring.CtRenameLocalVariableRefactoring refactor = new spoon.refactoring.CtRenameLocalVariableRefactoring();
        refactor.setTarget(targetVariable);
        refactor.setNewName(originName);
        try {
            refactor.refactor();
        } catch (spoon.SpoonException e) {
            throw new java.lang.AssertionError(((((((((getParentMethodName(targetVariable)) + " Rename of \"") + originName) + "\" to \"") + newName) + "\" passed, but rename back to \"") + originName) + "\" failed"), e);
        }
    }

    private void assertCorrectModel(spoon.Launcher launcher, java.lang.String refactoringDescription) {
        java.io.File outputBinDirectory = new java.io.File("./target/spooned-refactoring-test");
        if (!(outputBinDirectory.exists())) {
            outputBinDirectory.mkdirs();
        }
        launcher.setBinaryOutputDirectory(outputBinDirectory);
        launcher.setSourceOutputDirectory(outputBinDirectory);
        try {
            launcher.getModelBuilder().generateProcessedSourceFiles(spoon.OutputType.CLASSES);
        } catch (java.lang.Throwable e) {
            new java.lang.AssertionError(("The printing of java sources failed after: " + refactoringDescription), e);
        }
        try {
            launcher.getModelBuilder().compile(spoon.SpoonModelBuilder.InputType.CTTYPES);
        } catch (java.lang.Throwable e) {
            new java.lang.AssertionError(((("The compilation of java sources in " + (launcher.getEnvironment().getBinaryOutputDirectory())) + " failed after: ") + refactoringDescription), e);
        }
        try {
            spoon.test.refactoring.CtRenameLocalVariableRefactoringTest.TestClassloader classLoader = new spoon.test.refactoring.CtRenameLocalVariableRefactoringTest.TestClassloader(launcher);
            java.lang.Class testModelClass = classLoader.loadClass(spoon.test.refactoring.testclasses.CtRenameLocalVariableRefactoringTestSubject.class.getName());
            testModelClass.getMethod("checkModelConsistency").invoke(testModelClass.newInstance());
        } catch (java.lang.reflect.InvocationTargetException e) {
            throw new java.lang.AssertionError(((("The model validation of code in " + (launcher.getEnvironment().getBinaryOutputDirectory())) + " failed after: ") + refactoringDescription), e.getTargetException());
        } catch (java.lang.Throwable e) {
            throw new java.lang.AssertionError(((("The model validation of code in " + (launcher.getEnvironment().getBinaryOutputDirectory())) + " failed after: ") + refactoringDescription), e);
        }
    }

    private class TestClassloader extends java.net.URLClassLoader {
        TestClassloader(spoon.Launcher launcher) throws java.net.MalformedURLException {
            super(new java.net.URL[]{ new java.io.File(launcher.getEnvironment().getBinaryOutputDirectory()).toURL() }, spoon.test.refactoring.CtRenameLocalVariableRefactoringTest.class.getClassLoader());
        }

        @java.lang.Override
        public java.lang.Class<?> loadClass(java.lang.String s) throws java.lang.ClassNotFoundException {
            try {
                return findClass(s);
            } catch (java.lang.Exception e) {
                return super.loadClass(s);
            }
        }
    }

    private java.lang.String getParentMethodName(spoon.reflect.declaration.CtElement ele) {
        spoon.reflect.declaration.CtMethod parentMethod = ele.getParent(spoon.reflect.declaration.CtMethod.class);
        spoon.reflect.declaration.CtMethod m;
        while ((parentMethod != null) && ((m = parentMethod.getParent(spoon.reflect.declaration.CtMethod.class)) != null)) {
            parentMethod = m;
        } 
        if (parentMethod != null) {
            return ((parentMethod.getParent(spoon.reflect.declaration.CtType.class).getSimpleName()) + "#") + (parentMethod.getSimpleName());
        }else {
            return (ele.getParent(spoon.reflect.declaration.CtType.class).getSimpleName()) + "#annonymous block";
        }
    }

    @org.junit.Test
    public void testRefactorWrongUsage() throws java.lang.Exception {
        spoon.reflect.declaration.CtType varRenameClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.refactoring.testclasses.CtRenameLocalVariableRefactoringTestSubject.class);
        spoon.reflect.code.CtLocalVariable<?> local1Var = varRenameClass.filterChildren((spoon.reflect.code.CtLocalVariable<?> var) -> var.getSimpleName().equals("local1")).first();
        spoon.refactoring.CtRenameLocalVariableRefactoring refactor = new spoon.refactoring.CtRenameLocalVariableRefactoring();
        refactor.setNewName("local1");
        try {
            refactor.refactor();
            org.junit.Assert.fail();
        } catch (spoon.SpoonException e) {
        }
        refactor.setTarget(local1Var);
        try {
            refactor.setNewName("");
            org.junit.Assert.fail();
        } catch (spoon.SpoonException e) {
        }
        try {
            refactor.setNewName("x ");
            org.junit.Assert.fail();
        } catch (spoon.SpoonException e) {
        }
        try {
            refactor.setNewName("x y");
            org.junit.Assert.fail();
        } catch (spoon.SpoonException e) {
        }
        try {
            refactor.setNewName("x(");
            org.junit.Assert.fail();
        } catch (spoon.SpoonException e) {
        }
    }

    @org.junit.Test
    public void testRenameLocalVariableToSameName() throws java.lang.Exception {
        spoon.reflect.declaration.CtType varRenameClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.refactoring.testclasses.CtRenameLocalVariableRefactoringTestSubject.class);
        spoon.reflect.code.CtLocalVariable<?> local1Var = varRenameClass.filterChildren((spoon.reflect.code.CtLocalVariable<?> var) -> var.getSimpleName().equals("local1")).first();
        spoon.refactoring.CtRenameLocalVariableRefactoring refactor = new spoon.refactoring.CtRenameLocalVariableRefactoring();
        refactor.setTarget(local1Var);
        refactor.setNewName("local1");
        refactor.refactor();
        org.junit.Assert.assertEquals("local1", local1Var.getSimpleName());
    }
}

