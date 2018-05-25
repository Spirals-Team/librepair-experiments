package spoon.test.query_function;


public class VariableReferencesTest {
    spoon.reflect.declaration.CtClass<?> modelClass;

    @org.junit.Before
    public void setup() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput", "--level", "info" });
        launcher.getEnvironment().setCommentEnabled(true);
        launcher.addInputResource("./src/test/java/spoon/test/query_function/testclasses/VariableReferencesModelTest.java");
        launcher.run();
        spoon.reflect.factory.Factory factory = launcher.getFactory();
        modelClass = factory.Class().get(spoon.test.query_function.testclasses.VariableReferencesModelTest.class);
    }

    @org.junit.Test
    public void testCheckModelConsistency() throws java.lang.Exception {
        class Context {
            java.util.Map<java.lang.Integer, spoon.reflect.declaration.CtElement> unique = new java.util.HashMap<>();

            int maxKey = 0;

            void checkKey(int key, spoon.reflect.declaration.CtElement ele) {
                spoon.reflect.declaration.CtElement ambiquous = unique.put(key, ele);
                if (ambiquous != null) {
                    org.junit.Assert.fail((((((((("Two variables [" + (ambiquous.toString())) + " in ") + (getParentMethodName(ambiquous))) + ",") + (ele.toString())) + " in ") + (getParentMethodName(ele))) + "] has same value"));
                }
                maxKey = java.lang.Math.max(maxKey, key);
            }
        }
        Context context = new Context();
        modelClass.filterChildren((spoon.reflect.declaration.CtElement e) -> {
            if (e instanceof spoon.reflect.declaration.CtVariable) {
                spoon.reflect.declaration.CtVariable<?> var = ((spoon.reflect.declaration.CtVariable<?>) (e));
                if ((isTestFieldName(var.getSimpleName())) == false) {
                    return false;
                }
                java.lang.Integer val = getLiteralValue(var);
                context.checkKey(val, var);
            }
            return false;
        }).list();
        org.junit.Assert.assertTrue(((context.unique.size()) > 0));
        org.junit.Assert.assertEquals(("Only these keys were found: " + (context.unique.keySet())), context.maxKey, context.unique.size());
        org.junit.Assert.assertEquals("AllLocalVars#maxValue must be equal to maximum value number ", ((int) (getLiteralValue(((spoon.reflect.declaration.CtVariable) (modelClass.filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtVariable.class, "maxValue")).first()))))), context.maxKey);
    }

    @org.junit.Test
    public void testCatchVariableReferenceFunction() throws java.lang.Exception {
        modelClass.filterChildren((spoon.reflect.code.CtCatchVariable<?> var) -> {
            if (isTestFieldName(var.getSimpleName())) {
                int value = getLiteralValue(var);
                checkVariableAccess(var, value, new spoon.reflect.visitor.filter.CatchVariableReferenceFunction());
            }
            return false;
        }).list();
    }

    @org.junit.Test
    public void testLocalVariableReferenceFunction() throws java.lang.Exception {
        modelClass.filterChildren((spoon.reflect.code.CtLocalVariable<?> var) -> {
            if (isTestFieldName(var.getSimpleName())) {
                int value = getLiteralValue(var);
                checkVariableAccess(var, value, new spoon.reflect.visitor.filter.LocalVariableReferenceFunction());
            }
            return false;
        }).list();
    }

    @org.junit.Test
    public void testParameterReferenceFunction() throws java.lang.Exception {
        modelClass.filterChildren((spoon.reflect.declaration.CtParameter<?> var) -> {
            if (isTestFieldName(var.getSimpleName())) {
                int value = getLiteralValue(var);
                checkVariableAccess(var, value, new spoon.reflect.visitor.filter.ParameterReferenceFunction());
            }
            return false;
        }).list();
    }

    @org.junit.Test
    public void testVariableReferenceFunction() throws java.lang.Exception {
        modelClass.filterChildren((spoon.reflect.declaration.CtVariable<?> var) -> {
            if (isTestFieldName(var.getSimpleName())) {
                int value = getLiteralValue(var);
                checkVariableAccess(var, value, new spoon.reflect.visitor.filter.VariableReferenceFunction());
            }
            return false;
        }).list();
    }

    private boolean isTestFieldName(java.lang.String name) {
        return "field".equals(name);
    }

    @org.junit.Test
    public void testVariableScopeFunction() throws java.lang.Exception {
        java.util.List list = modelClass.filterChildren((spoon.reflect.declaration.CtVariable<?> var) -> {
            if (var.getSimpleName().equals("field")) {
                if (var instanceof spoon.reflect.declaration.CtField) {
                    return false;
                }
                spoon.reflect.declaration.CtElement[] real = var.map(new spoon.reflect.visitor.filter.VariableScopeFunction()).list().toArray(new spoon.reflect.declaration.CtElement[0]);
                if (var instanceof spoon.reflect.code.CtLocalVariable) {
                    org.junit.Assert.assertArrayEquals(var.map(new spoon.reflect.visitor.filter.LocalVariableScopeFunction()).list().toArray(new spoon.reflect.declaration.CtElement[0]), real);
                }else
                    if (var instanceof spoon.reflect.declaration.CtField) {
                    }else
                        if (var instanceof spoon.reflect.declaration.CtParameter) {
                            org.junit.Assert.assertArrayEquals(var.map(new spoon.reflect.visitor.filter.ParameterScopeFunction()).list().toArray(new spoon.reflect.declaration.CtElement[0]), real);
                        }else
                            if (var instanceof spoon.reflect.code.CtCatchVariable) {
                                org.junit.Assert.assertArrayEquals(var.map(new spoon.reflect.visitor.filter.CatchVariableScopeFunction()).list().toArray(new spoon.reflect.declaration.CtElement[0]), real);
                            }else {
                                org.junit.Assert.fail(("Unexpected variable of type " + (var.getClass().getName())));
                            }



                return true;
            }
            return false;
        }).list();
        org.junit.Assert.assertTrue(((list.size()) > 0));
    }

    @org.junit.Test
    public void testLocalVariableReferenceDeclarationFunction() throws java.lang.Exception {
        modelClass.filterChildren((spoon.reflect.reference.CtLocalVariableReference<?> varRef) -> {
            if (isTestFieldName(varRef.getSimpleName())) {
                spoon.reflect.code.CtLocalVariable<?> var = varRef.getDeclaration();
                org.junit.Assert.assertNotNull((((((((("The declaration of variable " + (varRef.getSimpleName())) + " in ") + (getParentMethodName(varRef))) + " on line ") + (var.getPosition().getLine())) + " with value ") + (getVariableReferenceValue(varRef))) + " was not found"), var);
                org.junit.Assert.assertEquals(("CtLocalVariableReference#getDeclaration returned wrong declaration in " + (getParentMethodName(varRef))), getVariableReferenceValue(varRef), ((int) (getLiteralValue(var))));
            }
            return false;
        }).list();
    }

    private void checkVariableAccess(spoon.reflect.declaration.CtVariable<?> var, int value, spoon.reflect.visitor.chain.CtConsumableFunction<?> query) {
        class Context {
            int realCount = 0;

            int expectedCount = 0;

            java.util.Set<java.lang.String> unique = new java.util.HashSet<>();
        }
        try {
            Context context = new Context();
            var.map(query).forEach((spoon.reflect.reference.CtVariableReference<?> fr) -> {
                org.junit.Assert.assertEquals(value, getVariableReferenceValue(fr));
                (context.realCount)++;
            });
            modelClass.filterChildren(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.reference.CtVariableReference.class)).forEach((spoon.reflect.reference.CtVariableReference varRef) -> {
                if ((isTestFieldName(varRef.getSimpleName())) == false) {
                    return;
                }
                int refValue = getVariableReferenceValue(varRef);
                if (refValue < 0) {
                    org.junit.Assert.fail(("Variable reference has no value:\n" + varRef));
                }
                if (refValue == value) {
                    (context.expectedCount)++;
                }
            });
            org.junit.Assert.assertEquals((("Number of references to field=" + value) + " does not match"), context.expectedCount, context.realCount);
        } catch (java.lang.Throwable e) {
            e.printStackTrace();
            throw new java.lang.AssertionError(("Test failed on " + (getParentMethodName(var))), e);
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

    private int getVariableReferenceValue(spoon.reflect.reference.CtVariableReference<?> fr) {
        spoon.reflect.code.CtBinaryOperator binOp = fr.getParent(spoon.reflect.code.CtBinaryOperator.class);
        if (binOp == null) {
            return getCommentValue(fr);
        }
        return getLiteralValue(binOp.getRightHandOperand());
    }

    private java.lang.Integer getLiteralValue(spoon.reflect.declaration.CtVariable<?> var) {
        spoon.reflect.code.CtExpression<?> exp = var.getDefaultExpression();
        if (exp != null) {
            try {
                return getLiteralValue(exp);
            } catch (java.lang.ClassCastException e) {
            }
        }
        if (var instanceof spoon.reflect.declaration.CtParameter) {
            spoon.reflect.declaration.CtParameter param = ((spoon.reflect.declaration.CtParameter) (var));
            spoon.reflect.declaration.CtExecutable<?> l_exec = param.getParent(spoon.reflect.declaration.CtExecutable.class);
            int l_argIdx = l_exec.getParameters().indexOf(param);
            org.junit.Assert.assertTrue((l_argIdx >= 0));
            if (l_exec instanceof spoon.reflect.code.CtLambda) {
                spoon.reflect.code.CtLambda<?> lambda = ((spoon.reflect.code.CtLambda<?>) (l_exec));
                spoon.reflect.code.CtLocalVariable<?> lamVar = ((spoon.reflect.code.CtLocalVariable) (lambda.getParent()));
                spoon.reflect.reference.CtLocalVariableReference<?> lamVarRef = lamVar.getParent().filterChildren((spoon.reflect.reference.CtLocalVariableReference ref) -> ref.getSimpleName().equals(lamVar.getSimpleName())).first();
                spoon.reflect.code.CtAbstractInvocation inv = lamVarRef.getParent(spoon.reflect.code.CtAbstractInvocation.class);
                return getLiteralValue(((spoon.reflect.code.CtExpression<?>) (inv.getArguments().get(l_argIdx))));
            }else {
                spoon.reflect.reference.CtExecutableReference<?> l_execRef = l_exec.getReference();
                java.util.List<spoon.reflect.code.CtAbstractInvocation<?>> list = l_exec.getFactory().Package().getRootPackage().filterChildren((spoon.reflect.code.CtAbstractInvocation inv) -> {
                    return (inv.getExecutable().getExecutableDeclaration()) == l_exec;
                }).list();
                spoon.reflect.code.CtAbstractInvocation inv = list.get(0);
                java.lang.Integer firstValue = getLiteralValue(((spoon.reflect.code.CtExpression<?>) (inv.getArguments().get(l_argIdx))));
                list.forEach(( inv2) -> {
                    org.junit.Assert.assertEquals(firstValue, getLiteralValue(((spoon.reflect.code.CtExpression<?>) (inv2.getArguments().get(l_argIdx)))));
                });
                return firstValue;
            }
        }
        return getCommentValue(var);
    }

    private int getCommentValue(spoon.reflect.declaration.CtElement e) {
        while (true) {
            if (e == null) {
                return -1;
            }
            if ((e.getComments().isEmpty()) == false) {
                break;
            }
            e = e.getParent();
        } 
        if ((e.getComments().size()) == 1) {
            java.lang.String l_c = e.getComments().get(0).getContent();
            return java.lang.Integer.parseInt(l_c);
        }
        return -1;
    }

    private java.lang.Integer getLiteralValue(spoon.reflect.code.CtExpression<?> exp) {
        return ((spoon.reflect.code.CtLiteral<java.lang.Integer>) (exp)).getValue();
    }

    private spoon.reflect.cu.SourcePosition getPosition(spoon.reflect.declaration.CtElement e) {
        spoon.reflect.cu.SourcePosition sp = e.getPosition();
        while (sp instanceof spoon.reflect.cu.position.NoSourcePosition) {
            e = e.getParent();
            if (e == null) {
                break;
            }
            sp = e.getPosition();
        } 
        return sp;
    }

    @org.junit.Test
    public void testPotentialVariableAccessFromStaticMethod() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.query_function.testclasses.VariableReferencesFromStaticMethod.class);
        spoon.reflect.declaration.CtClass<?> clazz = factory.Class().get(spoon.test.query_function.testclasses.VariableReferencesFromStaticMethod.class);
        spoon.reflect.declaration.CtMethod staticMethod = clazz.getMethodsByName("staticMethod").get(0);
        spoon.reflect.code.CtStatement stmt = staticMethod.getBody().getStatements().get(1);
        org.junit.Assert.assertEquals("org.junit.Assert.assertTrue((field == 2))", stmt.toString());
        spoon.reflect.reference.CtLocalVariableReference varRef = stmt.filterChildren(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.reference.CtLocalVariableReference.class)).first();
        java.util.List<spoon.reflect.declaration.CtVariable> vars = varRef.map(new spoon.reflect.visitor.filter.PotentialVariableDeclarationFunction()).list();
        org.junit.Assert.assertEquals("Found unexpected variable declaration.", 1, vars.size());
    }
}

