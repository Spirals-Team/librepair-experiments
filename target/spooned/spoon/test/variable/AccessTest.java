package spoon.test.variable;


public class AccessTest {
    @org.junit.Test
    public void testCanVisitVariableAccessAndSubClasses() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.variable.testclasses.VariableAccessSample.class);
        final java.util.List<spoon.reflect.code.CtVariableRead<?>> variablesRead = spoon.reflect.visitor.Query.getElements(factory, new spoon.reflect.visitor.filter.AbstractFilter<spoon.reflect.code.CtVariableRead<?>>(spoon.reflect.code.CtVariableRead.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtVariableRead<?> element) {
                return super.matches(element);
            }
        });
        org.junit.Assert.assertEquals(2, variablesRead.size());
        final java.util.List<spoon.reflect.code.CtVariableWrite<?>> variablesWrite = spoon.reflect.visitor.Query.getElements(factory, new spoon.reflect.visitor.filter.AbstractFilter<spoon.reflect.code.CtVariableWrite<?>>(spoon.reflect.code.CtVariableWrite.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtVariableWrite<?> element) {
                return super.matches(element);
            }
        });
        org.junit.Assert.assertEquals(1, variablesWrite.size());
        final java.util.List<spoon.reflect.code.CtVariableAccess<?>> variablesAccess = spoon.reflect.visitor.Query.getElements(factory, new spoon.reflect.visitor.filter.AbstractFilter<spoon.reflect.code.CtVariableAccess<?>>(spoon.reflect.code.CtVariableAccess.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtVariableAccess<?> element) {
                return super.matches(element);
            }
        });
        org.junit.Assert.assertEquals(3, variablesAccess.size());
    }

    @org.junit.Test
    public void testCanVisitFieldAccessAndSubClasses() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.variable.testclasses.FieldAccessSample.class);
        final java.util.List<spoon.reflect.code.CtFieldRead<?>> fieldsRead = spoon.reflect.visitor.Query.getElements(factory, new spoon.reflect.visitor.filter.AbstractFilter<spoon.reflect.code.CtFieldRead<?>>(spoon.reflect.code.CtFieldRead.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtFieldRead<?> element) {
                return super.matches(element);
            }
        });
        org.junit.Assert.assertEquals(2, fieldsRead.size());
        final java.util.List<spoon.reflect.code.CtFieldWrite<?>> fieldsWrite = spoon.reflect.visitor.Query.getElements(factory, new spoon.reflect.visitor.filter.AbstractFilter<spoon.reflect.code.CtFieldWrite<?>>(spoon.reflect.code.CtFieldWrite.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtFieldWrite<?> element) {
                return super.matches(element);
            }
        });
        org.junit.Assert.assertEquals(3, fieldsWrite.size());
        final java.util.List<spoon.reflect.code.CtFieldAccess<?>> fieldsAccess = spoon.reflect.visitor.Query.getElements(factory, new spoon.reflect.visitor.filter.AbstractFilter<spoon.reflect.code.CtFieldAccess<?>>(spoon.reflect.code.CtFieldAccess.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtFieldAccess<?> element) {
                return super.matches(element);
            }
        });
        org.junit.Assert.assertEquals(5, fieldsAccess.size());
    }

    @org.junit.Test
    public void testCanVisitArrayAccessAndSubClasses() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.variable.testclasses.ArrayAccessSample.class);
        final java.util.List<spoon.reflect.code.CtArrayRead<?>> arraysRead = spoon.reflect.visitor.Query.getElements(factory, new spoon.reflect.visitor.filter.AbstractFilter<spoon.reflect.code.CtArrayRead<?>>(spoon.reflect.code.CtArrayRead.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtArrayRead<?> element) {
                return super.matches(element);
            }
        });
        org.junit.Assert.assertEquals(2, arraysRead.size());
        final java.util.List<spoon.reflect.code.CtArrayWrite<?>> arraysWrite = spoon.reflect.visitor.Query.getElements(factory, new spoon.reflect.visitor.filter.AbstractFilter<spoon.reflect.code.CtArrayWrite<?>>(spoon.reflect.code.CtArrayWrite.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtArrayWrite<?> element) {
                return super.matches(element);
            }
        });
        org.junit.Assert.assertEquals(1, arraysWrite.size());
        final java.util.List<spoon.reflect.code.CtArrayAccess<?, spoon.reflect.code.CtExpression<?>>> arraysAccess = spoon.reflect.visitor.Query.getElements(factory, new spoon.reflect.visitor.filter.AbstractFilter<spoon.reflect.code.CtArrayAccess<?, spoon.reflect.code.CtExpression<?>>>(spoon.reflect.code.CtArrayAccess.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtArrayAccess<?, spoon.reflect.code.CtExpression<?>> element) {
                return super.matches(element);
            }
        });
        org.junit.Assert.assertEquals(3, arraysAccess.size());
    }

    @org.junit.Test
    public void testStackedAssignments() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<spoon.test.variable.testclasses.StackedAssignmentSample> type = spoon.testing.utils.ModelUtils.buildClass(spoon.test.variable.testclasses.StackedAssignmentSample.class);
        java.util.List<spoon.reflect.code.CtAssignment> l = type.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtAssignment.class));
        org.junit.Assert.assertEquals(3, l.size());
    }

    @org.junit.Test
    public void testRHS() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<spoon.test.variable.testclasses.RHSSample> type = spoon.testing.utils.ModelUtils.buildClass(spoon.test.variable.testclasses.RHSSample.class);
        org.junit.Assert.assertEquals(4, type.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtRHSReceiver.class)).size());
    }

    @org.junit.Test
    public void testFieldWriteDeclaredInTheSuperclass() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.run(new java.lang.String[]{ "-i", "./src/test/resources/spoon/test/variable/Tacos.java", "-o", "target/spooned/variable", "--noclasspath", "--compliance", "8", "--level", "OFF" });
        spoon.test.main.MainTest.checkAssignmentContracts(launcher.getFactory().Package().getRootPackage());
    }

    @org.junit.Test
    public void testVariableAccessInNoClasspath() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource("./src/test/resources/variable");
        launcher.setSourceOutputDirectory("./target/variable/");
        launcher.run();
        final spoon.reflect.declaration.CtClass<java.lang.Object> aClass = launcher.getFactory().Class().get("org.argouml.uml.ui.behavior.use_cases.PropPanelUseCase");
        final java.util.List<spoon.reflect.code.CtFieldRead> elements = aClass.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtFieldRead.class));
        for (spoon.reflect.code.CtFieldRead element : elements) {
            org.junit.Assert.assertNotNull(element.getVariable());
        }
        org.junit.Assert.assertEquals("java.lang.Class mclass = ((java.lang.Class) (org.argouml.model.ModelFacade.USE_CASE))", elements.get(0).getParent().toString());
        org.junit.Assert.assertEquals("new org.argouml.uml.ui.PropPanelButton(this, buttonPanel, _navUpIcon, org.argouml.i18n.Translator.localize(\"UMLMenu\", \"button.go-up\"), \"navigateNamespace\", null)", elements.get(2).getParent().toString());
    }

    @org.junit.Test
    public void testAccessToStringOnPostIncrement() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        spoon.reflect.declaration.CtClass<?> clazz = factory.Code().createCodeSnippetStatement(("class X {" + ((((("public void foo() {" + " Integer i = 1;") + " (i++).toString();") + " int k = 0;") + " k++;") + "}};"))).compile();
        spoon.reflect.declaration.CtMethod<?> foo = ((spoon.reflect.declaration.CtMethod<?>) (clazz.getMethods().toArray()[0]));
        org.junit.Assert.assertEquals("(i++).toString()", foo.getBody().getStatement(1).toString());
        org.junit.Assert.assertEquals("k++", foo.getBody().getStatement(3).toString());
    }
}

