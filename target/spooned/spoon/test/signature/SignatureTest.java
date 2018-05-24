package spoon.test.signature;


public class SignatureTest {
    @org.junit.Test
    public void testNullSignature() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = new spoon.Launcher().createFactory();
        spoon.reflect.declaration.CtClass<?> clazz = factory.Code().createCodeSnippetStatement(("" + ((("class X {" + "public Object foo() {") + " return null;") + "}};"))).compile();
        spoon.reflect.code.CtReturn<?> returnEl = clazz.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtReturn.class)).get(0);
        spoon.reflect.code.CtExpression<?> lit = returnEl.getReturnedExpression();
        org.junit.Assert.assertTrue((lit instanceof spoon.reflect.code.CtLiteral));
        org.junit.Assert.assertEquals("null", lit.toString());
        spoon.reflect.code.CtLiteral<?> lit2 = ((spoon.reflect.code.CtLiteral<?>) (lit)).clone();
        java.util.HashSet<spoon.reflect.code.CtExpression<?>> s = new java.util.HashSet<spoon.reflect.code.CtExpression<?>>();
        s.add(lit);
        s.add(lit2);
    }

    @org.junit.Test
    public void testNullSignatureInUnboundVariable() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = new spoon.Launcher().createFactory();
        factory.getEnvironment().setNoClasspath(true);
        java.lang.String unboundVarAccess = "Complex.I";
        java.lang.String content = (((("" + (("class X {" + "public Object foo(java.util.List<String> l) {") + " Integer.toString(")) + unboundVarAccess) + ");") + " return null;") + "}};";
        spoon.SpoonModelBuilder builder = new spoon.support.compiler.jdt.JDTSnippetCompiler(factory, content);
        try {
            builder.build();
            org.junit.Assert.fail();
        } catch (java.lang.Exception e) {
        }
        spoon.reflect.declaration.CtClass<?> clazz1 = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().getAll().get(0)));
        java.util.Set<spoon.reflect.declaration.CtMethod<?>> methods = clazz1.getMethods();
        spoon.reflect.declaration.CtMethod<?> method = ((spoon.reflect.declaration.CtMethod<?>) (methods.toArray()[0]));
        org.junit.Assert.assertEquals("foo(java.util.List)", method.getSignature());
        spoon.reflect.code.CtInvocation<?> invo = ((spoon.reflect.code.CtInvocation<?>) (method.getBody().getStatement(0)));
        spoon.reflect.code.CtExpression<?> argument1 = invo.getArguments().get(0);
        org.junit.Assert.assertEquals(unboundVarAccess, argument1.toString());
    }

    @org.junit.Test
    public void testLiteralSignature() {
        spoon.reflect.factory.Factory factory = new spoon.reflect.factory.FactoryImpl(new spoon.support.DefaultCoreFactory(), new spoon.support.StandardEnvironment());
        spoon.reflect.code.CtStatement sta1 = factory.Code().createCodeSnippetStatement("System.out.println(\"hello\")").compile();
        spoon.reflect.code.CtStatement sta2 = factory.Code().createCodeSnippetStatement("String hello =\"t1\"; System.out.println(hello)").compile();
        org.junit.Assert.assertFalse(sta1.equals(sta2));
        java.lang.String parameterWithQuotes = ((spoon.reflect.code.CtInvocation<?>) (sta1)).getArguments().get(0).toString();
        org.junit.Assert.assertEquals("\"hello\"", parameterWithQuotes);
        factory.Code().createCodeSnippetStatement("Integer.toBinaryString(20)").compile();
    }

    @org.junit.Test
    public void testMethodInvocationSignatureStaticFieldsVariables() {
        spoon.reflect.factory.Factory factory = new spoon.reflect.factory.FactoryImpl(new spoon.support.DefaultCoreFactory(), new spoon.support.StandardEnvironment());
        spoon.reflect.code.CtStatement sta1 = factory.Code().createCodeSnippetStatement("Integer.toBinaryString(Integer.MAX_VALUE)").compile();
        spoon.reflect.code.CtStatement sta2 = factory.Code().createCodeSnippetStatement("Integer.toBinaryString(Integer.MIN_VALUE)").compile();
        java.lang.String signature1 = ((spoon.reflect.code.CtInvocation) (sta1)).getExecutable().getSignature();
        java.lang.String signature2 = ((spoon.reflect.code.CtInvocation) (sta2)).getExecutable().getSignature();
        org.junit.Assert.assertEquals(signature1, signature2);
        org.junit.Assert.assertFalse(sta1.equals(sta2));
        spoon.reflect.code.CtStatement stb1 = factory.Code().createCodeSnippetStatement("Integer.toBinaryString(20)").compile();
        spoon.reflect.code.CtStatement stb2 = factory.Code().createCodeSnippetStatement("Integer.toBinaryString(30)").compile();
        java.lang.String signature1b = ((spoon.reflect.code.CtInvocation) (sta1)).getExecutable().getSignature();
        java.lang.String signature2b = ((spoon.reflect.code.CtInvocation) (sta2)).getExecutable().getSignature();
        org.junit.Assert.assertEquals(signature1b, signature2b);
        org.junit.Assert.assertFalse(stb1.equals(stb2));
        spoon.reflect.code.CtStatement stc1 = factory.Code().createCodeSnippetStatement("String.format(\"format1\",\"f2\" )").compile();
        spoon.reflect.code.CtStatement stc2 = factory.Code().createCodeSnippetStatement("String.format(\"format2\",\"f2\" )").compile();
        java.lang.String signaturestc1 = ((spoon.reflect.code.CtInvocation) (sta1)).getExecutable().getSignature();
        java.lang.String signaturestc2 = ((spoon.reflect.code.CtInvocation) (sta2)).getExecutable().getSignature();
        org.junit.Assert.assertEquals(signaturestc1, signaturestc2);
        org.junit.Assert.assertFalse(stc1.equals(stc2));
    }

    @org.junit.Test
    public void testMethodInvocationSignatureWithVariableAccess() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = new spoon.reflect.factory.FactoryImpl(new spoon.support.DefaultCoreFactory(), new spoon.support.StandardEnvironment());
        factory.getEnvironment().setNoClasspath(true);
        java.lang.String content = "" + (((((((((((((("class PR {" + "static String PRS = null;") + "public Object foo(String p) {") + " int s = 0; 	") + " this.foo(s);") + "this.foo(p);") + " return null;") + "}") + " public Object foo(int p) {") + " String s = null;") + " this.foo(s);") + "this.foo(p);") + "return null;") + "}") + "};");
        spoon.SpoonModelBuilder builder = new spoon.support.compiler.jdt.JDTSnippetCompiler(factory, content);
        builder.build();
        spoon.reflect.declaration.CtClass<?> clazz1 = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().getAll().get(0)));
        org.junit.Assert.assertNotNull(clazz1);
        java.util.TreeSet<spoon.reflect.declaration.CtMethod<?>> ts = new java.util.TreeSet<spoon.reflect.declaration.CtMethod<?>>(new spoon.support.comparator.DeepRepresentationComparator());
        ts.addAll(clazz1.getMethods());
        spoon.reflect.declaration.CtMethod[] methodArray = ts.toArray(new spoon.reflect.declaration.CtMethod[0]);
        spoon.reflect.declaration.CtMethod<?> methodInteger = methodArray[0];
        org.junit.Assert.assertEquals("foo(int)", methodInteger.getSignature());
        spoon.reflect.code.CtInvocation<?> invoToInt1 = ((spoon.reflect.code.CtInvocation<?>) (methodInteger.getBody().getStatement(1)));
        spoon.reflect.code.CtExpression<?> argumentToInt1 = invoToInt1.getArguments().get(0);
        spoon.reflect.declaration.CtMethod<?> methodString = ((spoon.reflect.declaration.CtMethod<?>) (methodArray[1]));
        org.junit.Assert.assertEquals("foo(java.lang.String)", methodString.getSignature());
        spoon.reflect.code.CtInvocation<?> invoToString = ((spoon.reflect.code.CtInvocation<?>) (methodString.getBody().getStatement(1)));
        spoon.reflect.code.CtExpression<?> argumentToString = invoToString.getArguments().get(0);
        org.junit.Assert.assertNotEquals(invoToInt1, invoToString);
        org.junit.Assert.assertNotEquals(argumentToInt1, argumentToString);
        spoon.reflect.code.CtInvocation<?> invoToString2 = ((spoon.reflect.code.CtInvocation<?>) (methodInteger.getBody().getStatement(2)));
        spoon.reflect.code.CtExpression<?> argumentToString2 = invoToString2.getArguments().get(0);
        spoon.reflect.code.CtInvocation<?> invoToInt2 = ((spoon.reflect.code.CtInvocation<?>) (methodString.getBody().getStatement(2)));
        spoon.reflect.code.CtExpression<?> argumentToInt2 = invoToInt2.getArguments().get(0);
        org.junit.Assert.assertNotEquals(invoToString2, invoToInt2);
        org.junit.Assert.assertNotEquals(argumentToString2, argumentToInt2);
    }

    @org.junit.Test
    public void testUnboundFieldSignature() {
        spoon.reflect.factory.Factory factory = new spoon.reflect.factory.FactoryImpl(new spoon.support.DefaultCoreFactory(), new spoon.support.StandardEnvironment());
        factory.getEnvironment().setNoClasspath(true);
        java.lang.String content = "" + ((((("class PR {" + "public java.io.File foo(String p) {") + " this.mfield = p; 	") + " return null;") + "}") + "};");
        spoon.SpoonModelBuilder builder = new spoon.support.compiler.jdt.JDTSnippetCompiler(factory, content);
        try {
            builder.build();
            org.junit.Assert.fail();
        } catch (java.lang.Exception e) {
        }
        spoon.reflect.declaration.CtClass<?> clazz1 = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().getAll().get(0)));
        org.junit.Assert.assertNotNull(clazz1);
        spoon.reflect.declaration.CtMethod<?> methodString = ((spoon.reflect.declaration.CtMethod<?>) (clazz1.getMethods().toArray()[0]));
        org.junit.Assert.assertEquals("foo(java.lang.String)", methodString.getSignature());
        spoon.reflect.code.CtAssignment<?, ?> invoToInt1 = ((spoon.reflect.code.CtAssignment<?, ?>) (methodString.getBody().getStatement(0)));
        spoon.reflect.code.CtExpression<?> left = invoToInt1.getAssigned();
        org.junit.Assert.assertEquals("this.mfield", left.toString());
        org.junit.Assert.assertEquals(null, left.getType());
        org.junit.Assert.assertEquals("this.mfield = p", invoToInt1.toString());
    }

    @org.junit.Test
    public void testArgumentNotNullForExecutableReference() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput" });
        launcher.addInputResource("./src/test/resources/variable/PropPanelUseCase_1.40.java");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.run();
        final java.util.List<spoon.reflect.reference.CtExecutableReference> references = spoon.reflect.visitor.Query.getElements(launcher.getFactory(), new spoon.reflect.visitor.filter.ReferenceTypeFilter<spoon.reflect.reference.CtExecutableReference>(spoon.reflect.reference.CtExecutableReference.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.reference.CtExecutableReference reference) {
                return ("addField".equals(reference.getSimpleName())) && (super.matches(reference));
            }
        });
        org.junit.Assert.assertEquals("addField(<unknown>,<unknown>)", references.get(0).getSignature());
        org.junit.Assert.assertEquals("addField(<unknown>,org.argouml.uml.ui.UMLComboBoxNavigator)", references.get(1).getSignature());
        for (spoon.reflect.reference.CtExecutableReference reference : references) {
            org.junit.Assert.assertNotEquals("addField(null,null)", reference.getSignature());
        }
    }

    @org.junit.Test
    public void testBugSignature() throws java.lang.Exception {
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        spoon.SpoonModelBuilder comp = launcher.createCompiler();
        comp.addInputSources(spoon.compiler.SpoonResourceHelper.resources("./src/main/java/spoon/SpoonModelBuilder.java"));
        comp.build();
        spoon.reflect.declaration.CtType<?> ctClass = ((spoon.reflect.declaration.CtType<?>) (comp.getFactory().Type().get(spoon.SpoonModelBuilder.class)));
        java.util.List<spoon.reflect.declaration.CtMethod> methods = ctClass.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "addInputSource"));
        org.junit.Assert.assertEquals(2, methods.size());
        spoon.reflect.declaration.CtMethod<?> method = methods.get(0);
        org.junit.Assert.assertEquals("addInputSource(java.io.File)", method.getSignature());
        spoon.reflect.declaration.CtMethod<?> method2 = methods.get(1);
        org.junit.Assert.assertEquals("addInputSource(spoon.compiler.SpoonResource)", method2.getSignature());
        org.junit.Assert.assertNotEquals(method, method2);
    }
}

