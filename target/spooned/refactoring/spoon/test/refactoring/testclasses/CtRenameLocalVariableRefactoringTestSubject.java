package spoon.test.refactoring.testclasses;


public class CtRenameLocalVariableRefactoringTestSubject {
    public CtRenameLocalVariableRefactoringTestSubject() {
        int local1 = 0;
    }

    public void checkModelConsistency() throws java.lang.Throwable {
        java.lang.reflect.Method[] methods = getClass().getDeclaredMethods();
        for (java.lang.reflect.Method method : methods) {
            if ("checkModelConsistency".equals(method.getName())) {
                continue;
            }
            try {
                if (java.lang.reflect.Modifier.isPrivate(method.getModifiers())) {
                    continue;
                }
                method.invoke(this);
            } catch (java.lang.reflect.InvocationTargetException e) {
                throw e.getTargetException();
            } catch (java.lang.IllegalAccessException | java.lang.IllegalArgumentException e) {
                throw new java.lang.RuntimeException((("Invocation of method " + (method.getName())) + " failed"), e);
            }
        }
    }

    void callConflictWithParam() {
        conflictWithParam(2);
    }

    private void conflictWithParam(@spoon.test.refactoring.testclasses.TestTryRename("-var1")
    int var2) {
        @spoon.test.refactoring.testclasses.TestTryRename("-var2")
        int var1 = 1;
        org.junit.Assert.assertTrue((var1 == 1));
        org.junit.Assert.assertTrue((var2 == 2));
    }

    private void conflictWithCatchVariable() {
        @spoon.test.refactoring.testclasses.TestTryRename({ "-var2", "-var3" })
        int var1 = 1;
        try {
            org.junit.Assert.assertTrue((var1 == 1));
            @spoon.test.refactoring.testclasses.TestTryRename({ "-var1", "var3" })
            int var2 = 2;
            org.junit.Assert.assertTrue((var2 == 2));
            throw new java.lang.NumberFormatException();
        } catch (java.lang.NumberFormatException var3) {
            org.junit.Assert.assertTrue((var1 == 1));
        }
        org.junit.Assert.assertTrue((var1 == 1));
    }

    void nestedClassMethodWithRefs() {
        @spoon.test.refactoring.testclasses.TestTryRename({ "-var2", "-var3", "-var4", "-var5", "-var6" })
        int var1 = 1;
        new java.util.function.Consumer<java.lang.Integer>() {
            @spoon.test.refactoring.testclasses.TestTryRename({ "-var1", "-var3", "-var4", "-var5", "-var6" })
            int var2 = 2;

            @java.lang.Override
            public void accept(@spoon.test.refactoring.testclasses.TestTryRename({ "-var1", "var2", "-var3", "-var5", "-var6" })
            java.lang.Integer var4) {
                @spoon.test.refactoring.testclasses.TestTryRename({ "-var1", "var2", "-var4", "-var5", "-var6" })
                int var3 = 3;
                try {
                    @spoon.test.refactoring.testclasses.TestTryRename({ "-var1", "var2", "-var3", "-var4", "var6" })
                    int var5 = 5;
                    org.junit.Assert.assertTrue((var1 == 1));
                    org.junit.Assert.assertTrue(((var2) == 2));
                    org.junit.Assert.assertTrue((var3 == 3));
                    org.junit.Assert.assertTrue((var4 == 4));
                    org.junit.Assert.assertTrue((var5 == 5));
                    throw new java.lang.NumberFormatException();
                } catch (java.lang.NumberFormatException var6) {
                    org.junit.Assert.assertTrue((var1 == 1));
                    org.junit.Assert.assertTrue(((var2) == 2));
                    org.junit.Assert.assertTrue((var3 == 3));
                    org.junit.Assert.assertTrue((var4 == 4));
                }
            }
        }.accept(4);
        org.junit.Assert.assertTrue((var1 == 1));
    }

    void nestedClassMethodWithoutRefs() {
        @spoon.test.refactoring.testclasses.TestTryRename({ "var2", "var3", "var4", "var5", "var6" })
        int var1 = 1;
        new java.util.function.Consumer<java.lang.Integer>() {
            @spoon.test.refactoring.testclasses.TestTryRename({ "-var1", "var3", "var4", "var5", "var6" })
            int var2 = 2;

            @java.lang.Override
            public void accept(@spoon.test.refactoring.testclasses.TestTryRename({ "-var1", "var2", "-var3", "-var5", "-var6" })
            java.lang.Integer var4) {
                @spoon.test.refactoring.testclasses.TestTryRename({ "var1", "var2", "-var4", "-var5", "-var6" })
                int var3 = 3;
                try {
                    @spoon.test.refactoring.testclasses.TestTryRename({ "var1", "var2", "-var3", "-var4", "var6" })
                    int var5 = 5;
                    org.junit.Assert.assertTrue(((var2) == 2));
                    org.junit.Assert.assertTrue((var3 == 3));
                    org.junit.Assert.assertTrue((var4 == 4));
                    org.junit.Assert.assertTrue((var5 == 5));
                    throw new java.lang.NumberFormatException();
                } catch (java.lang.NumberFormatException var6) {
                    org.junit.Assert.assertTrue(((var2) == 2));
                    org.junit.Assert.assertTrue((var3 == 3));
                    org.junit.Assert.assertTrue((var4 == 4));
                }
            }
        }.accept(4);
        org.junit.Assert.assertTrue((var1 == 1));
    }

    void nestedClassMethodWithShadowVarWithRefs() {
        @spoon.test.refactoring.testclasses.TestTryRename({ "-var2", "var3" })
        int var1 = 2;
        new java.lang.Runnable() {
            @spoon.test.refactoring.testclasses.TestTryRename({ "var1", "var3" })
            int var2 = 3;

            @java.lang.Override
            public void run() {
                org.junit.Assert.assertTrue((var1 == 2));
                @spoon.test.refactoring.testclasses.TestTryRename({ "-var1", "var2" })
                int var3 = 1;
                @spoon.test.refactoring.testclasses.TestTryRename({ "var2", "-var3" })
                int var1 = 4;
                org.junit.Assert.assertTrue((var1 == 4));
                org.junit.Assert.assertTrue(((var2) == 3));
                org.junit.Assert.assertTrue((var3 == 1));
            }
        }.run();
        org.junit.Assert.assertTrue((var1 == 2));
    }

    void nestedClassMethodWithShadowVarWithoutRefs() {
        @spoon.test.refactoring.testclasses.TestTryRename({ "var2", "var3" })
        int var1 = 2;
        new java.lang.Runnable() {
            @spoon.test.refactoring.testclasses.TestTryRename({ "var1", "var3" })
            int var2 = 3;

            @java.lang.Override
            public void run() {
                @spoon.test.refactoring.testclasses.TestTryRename({ "-var1", "var2" })
                int var3 = 1;
                @spoon.test.refactoring.testclasses.TestTryRename({ "var2", "-var3" })
                int var1 = 4;
                org.junit.Assert.assertTrue((var1 == 4));
                org.junit.Assert.assertTrue(((var2) == 3));
                org.junit.Assert.assertTrue((var3 == 1));
            }
        }.run();
        org.junit.Assert.assertTrue((var1 == 2));
    }

    void nestedClassMethodWithShadowVarAndField() {
        @spoon.test.refactoring.testclasses.TestTryRename({ "var2", "var3" })
        int var1 = 2;
        new java.lang.Runnable() {
            @spoon.test.refactoring.testclasses.TestTryRename({ "var2", "var3" })
            int var1 = 3;

            @java.lang.Override
            public void run() {
                @spoon.test.refactoring.testclasses.TestTryRename({ "-var1", "var2" })
                int var2 = 1;
                org.junit.Assert.assertTrue(((var1) == 3));
                @spoon.test.refactoring.testclasses.TestTryRename({ "-var2", "var3" })
                int var1 = 4;
                org.junit.Assert.assertTrue((var1 == 4));
                org.junit.Assert.assertTrue(((this.var1) == 3));
                org.junit.Assert.assertTrue((var2 == 1));
            }
        }.run();
        org.junit.Assert.assertTrue((var1 == 2));
    }

    void lambda() {
        @spoon.test.refactoring.testclasses.TestTryRename({ "-var2", "-var3" })
        int var1 = 1;
        org.junit.Assert.assertTrue((var1 == 1));
        java.util.function.Function<java.lang.Integer, java.lang.Integer> fnc = (@spoon.test.refactoring.testclasses.TestTryRename({ "-var1", "-var3" })
        java.lang.Integer var2) -> {
            @spoon.test.refactoring.testclasses.TestTryRename({ "-var1", "-var2" })
            int var3 = 3;
            org.junit.Assert.assertTrue((var1 == 1));
            org.junit.Assert.assertTrue((var2 == 2));
            org.junit.Assert.assertTrue((var3 == 3));
            return var2;
        };
        org.junit.Assert.assertTrue(((fnc.apply(2)) == 2));
    }

    void tryCatch() {
        @spoon.test.refactoring.testclasses.TestTryRename({ "-var2", "-var3", "-var4" })
        int var1 = 1;
        org.junit.Assert.assertTrue((var1 == 1));
        try {
            @spoon.test.refactoring.testclasses.TestTryRename({ "-var1", "var3", "var4" })
            int var2 = 2;
            org.junit.Assert.assertTrue((var1 == 1));
            org.junit.Assert.assertTrue((var2 == 2));
            throw new java.lang.Exception("ex2");
        } catch (java.lang.Exception var3) {
            @spoon.test.refactoring.testclasses.TestTryRename({ "-var1", "var2", "-var3" })
            int var4 = 4;
            org.junit.Assert.assertTrue((var1 == 1));
            org.junit.Assert.assertTrue((var4 == 4));
        }
    }
}

