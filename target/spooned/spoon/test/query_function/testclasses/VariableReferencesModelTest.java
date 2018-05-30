package spoon.test.query_function.testclasses;


public class VariableReferencesModelTest {
    int field = 15;

    @org.junit.Test
    public void localVarsInNestedBlocks() {
        org.junit.Assert.assertTrue(((this.field) == 15));
        {
            {
                int field = 1;
                org.junit.Assert.assertTrue((field == 1));
            }
            int f1;
            int f2;
            int f3;
            int field = 2;
            int f4;
            org.junit.Assert.assertTrue((field == 2));
        }
        int field = 3;
        org.junit.Assert.assertTrue((field == 3));
        {
            org.junit.Assert.assertTrue((field == 3));
        }
        org.junit.Assert.assertTrue(((this.field) == 15));
    }

    @org.junit.Test
    public void localVarsInTryCatch() {
        try {
            int field = 4;
            org.junit.Assert.assertTrue((field == 4));
            throw new java.lang.IllegalArgumentException();
        } catch (java.lang.IllegalArgumentException e) {
            org.junit.Assert.assertTrue(((field) == 15));
            {
                int field = 5;
                org.junit.Assert.assertTrue((field == 5));
            }
            int field = 6;
            org.junit.Assert.assertTrue((field == 6));
        } catch (java.lang.Exception field) {
            field.getMessage();
        }
    }

    @org.junit.Test
    public void localVarsInWhile() {
        while (true) {
            int field = 8;
            org.junit.Assert.assertTrue((field == 8));
            break;
        } 
        int field = 9;
        org.junit.Assert.assertTrue((field == 9));
    }

    @org.junit.Test
    public void localVarsInFor() {
        for (int field = 10; field == 10;) {
            org.junit.Assert.assertTrue((field == 10));
            break;
        }
        int field = 11;
        org.junit.Assert.assertTrue((field == 11));
    }

    @org.junit.Test
    public void localVarsInSwitch() {
        switch (7) {
            case 7 :
                int field = 12;
                org.junit.Assert.assertTrue((field == 12));
                break;
        }
        int field = 13;
        org.junit.Assert.assertTrue((field == 13));
    }

    @org.junit.Test
    public void localVarsInTryWithResource() throws java.io.IOException {
        try (java.io.Reader field = new java.io.StringReader("")) {
            field.toString();
        }
    }

    @org.junit.Test
    public void checkParameter() {
        parameter(16);
    }

    private void parameter(int field) {
        org.junit.Assert.assertTrue((field == 16));
        {
            org.junit.Assert.assertTrue((field == 16));
        }
        while (true) {
            org.junit.Assert.assertTrue((field == 16));
            break;
        } 
    }

    @org.junit.Test
    public void parameterInLambdaWithBody() {
        java.util.function.Consumer<java.lang.Integer> fnc = ( field) -> {
            org.junit.Assert.assertTrue((field == 17));
        };
        fnc.accept(17);
    }

    @org.junit.Test
    public void parameterInLambdaWithExpression() {
        java.util.function.Consumer<java.lang.Integer> fnc = ( field) -> org.junit.Assert.assertTrue((field == 18));
        fnc.accept(18);
    }

    @org.junit.Test
    public void localVarInLambda() {
        java.lang.Runnable fnc = () -> {
            int field = 19;
            org.junit.Assert.assertTrue((field == 19));
        };
        fnc.run();
        int field = 20;
        java.lang.Runnable fnc2 = () -> {
            org.junit.Assert.assertTrue((field == 20));
        };
        fnc2.run();
    }

    static {
        int field = 21;
        org.junit.Assert.assertTrue((field == 21));
    }

    {
        int field = 22;
        org.junit.Assert.assertTrue((field == 22));
    }

    @org.junit.Test
    public void localVarInNestedClass() {
        int field = 23;
        org.junit.Assert.assertTrue((field == 23));
        java.lang.Runnable fnc = new java.lang.Runnable() {
            @java.lang.Override
            public void run() {
                {
                    int field = 24;
                    org.junit.Assert.assertTrue((field == 24));
                }
                org.junit.Assert.assertTrue((field == 23));
                int field = 25;
                org.junit.Assert.assertTrue((field == 25));
            }
        };
        fnc.run();
        org.junit.Assert.assertTrue((field == 23));
    }

    @org.junit.Test
    public void localVarInNestedClass2() {
        int field = 26;
        org.junit.Assert.assertTrue((field == 26));
        java.lang.Runnable fnc = new java.lang.Runnable() {
            int field = 27;

            @java.lang.Override
            public void run() {
                {
                    int field = 36;
                    org.junit.Assert.assertTrue((field == 36));
                }
                org.junit.Assert.assertTrue(((field) == 27));
                int field = 28;
                org.junit.Assert.assertTrue((field == 28));
                org.junit.Assert.assertTrue(((this.field) == 27));
            }
        };
        fnc.run();
        org.junit.Assert.assertTrue((field == 26));
    }

    class A {
        int field = 29;
    }

    abstract class B extends spoon.test.query_function.testclasses.VariableReferencesModelTest.A {
        abstract void run();
    }

    @org.junit.Test
    public void localVarInNestedClass4() {
        int field = 30;
        org.junit.Assert.assertTrue((field == 30));
        spoon.test.query_function.testclasses.VariableReferencesModelTest.B fnc = new spoon.test.query_function.testclasses.VariableReferencesModelTest.B() {
            @java.lang.Override
            public void run() {
                {
                    int field = 31;
                    org.junit.Assert.assertTrue((field == 31));
                }
                org.junit.Assert.assertTrue(((field) == 29));
                int field = 32;
                org.junit.Assert.assertTrue((field == 32));
                org.junit.Assert.assertTrue(((this.field) == 29));
            }
        };
        fnc.run();
        org.junit.Assert.assertTrue((field == 30));
    }

    @org.junit.Test
    public void localVarInNestedClass5() {
        int field = 33;
        org.junit.Assert.assertTrue((field == 33));
        class Local {
            {
                {
                    int field = 34;
                    org.junit.Assert.assertTrue((field == 34));
                }
                org.junit.Assert.assertTrue((field == 33));
                int field = 35;
                org.junit.Assert.assertTrue((field == 35));
            }
        }
        new Local();
        org.junit.Assert.assertTrue((field == 33));
    }

    @org.junit.Test
    public void localVarInNestedClass6() {
        int field = 37;
        org.junit.Assert.assertTrue((field == 37));
        class Local {
            int field = 38;

            void method(int field) {
                org.junit.Assert.assertTrue((field == 39));
                org.junit.Assert.assertTrue(((this.field) == 38));
            }
        }
        new Local().method(39);
        org.junit.Assert.assertTrue((field == 37));
    }

    private static final int maxValue = 39;
}

