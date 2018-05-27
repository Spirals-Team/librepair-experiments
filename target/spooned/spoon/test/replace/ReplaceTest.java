package spoon.test.replace;


import spoon.Launcher;
import spoon.compiler.SpoonResourceHelper;


public class ReplaceTest {
    spoon.reflect.factory.Factory factory;

    @org.junit.Before
    public void setup() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        factory = spoon.createFactory();
        spoon.createCompiler(factory, SpoonResourceHelper.resources("./src/test/java/spoon/test/replace/testclasses")).build();
    }

    @org.junit.Test
    public void testReplaceSet() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> foo = factory.Package().get("spoon.test.replace.testclasses").getType("Foo");
        org.junit.Assert.assertEquals("Foo", foo.getSimpleName());
        spoon.reflect.declaration.CtClass<?> bar = factory.Package().get("spoon.test.replace.testclasses").getType("Bar");
        org.junit.Assert.assertEquals("Bar", bar.getSimpleName());
        spoon.reflect.declaration.CtField<java.lang.Number> i1 = ((spoon.reflect.declaration.CtField<java.lang.Number>) (foo.getField("i")));
        spoon.reflect.declaration.CtField<java.lang.Number> i2 = ((spoon.reflect.declaration.CtField<java.lang.Number>) (bar.getField("i")));
        org.junit.Assert.assertEquals("int", foo.getField("i").getType().getSimpleName());
        i1.replace(i2);
        org.junit.Assert.assertSame(i2, foo.getField("i"));
        org.junit.Assert.assertEquals("float", foo.getField("i").getType().getSimpleName());
        org.junit.Assert.assertEquals(foo, i2.getParent());
        i2.replace(i1);
        org.junit.Assert.assertSame(i1, foo.getField("i"));
        org.junit.Assert.assertEquals("int", foo.getField("i").getType().getSimpleName());
        org.junit.Assert.assertEquals(foo, i1.getParent());
    }

    @org.junit.Test
    public void testReplaceBlock() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> foo = factory.Package().get("spoon.test.replace.testclasses").getType("Foo");
        spoon.reflect.declaration.CtMethod<?> m = foo.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "foo")).get(0);
        org.junit.Assert.assertEquals("foo", m.getSimpleName());
        final spoon.reflect.code.CtStatement parent = m.getBody().getStatements().get(2);
        spoon.reflect.code.CtAssignment<?, ?> assignment = ((spoon.reflect.code.CtAssignment<?, ?>) (parent));
        spoon.reflect.code.CtExpression<java.lang.Integer> s1 = ((spoon.reflect.code.CtExpression<java.lang.Integer>) (assignment.getAssignment()));
        spoon.reflect.code.CtExpression<java.lang.Integer> s2 = factory.Code().createLiteral(3);
        org.junit.Assert.assertEquals("z = x + 1", assignment.toString());
        org.junit.Assert.assertEquals("x + 1", s1.toString());
        s1.replace(s2);
        org.junit.Assert.assertSame(s2, assignment.getAssignment());
        org.junit.Assert.assertEquals("z = 3", assignment.toString());
        org.junit.Assert.assertEquals(parent, s2.getParent());
        s2.replace(s1);
        org.junit.Assert.assertSame(s1, assignment.getAssignment());
        org.junit.Assert.assertEquals("z = x + 1", assignment.toString());
        org.junit.Assert.assertEquals(parent, s1.getParent());
    }

    @org.junit.Test
    public void testReplaceReplace() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> foo = factory.Package().get("spoon.test.replace.testclasses").getType("Foo");
        spoon.reflect.declaration.CtMethod<?> fooMethod = foo.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "foo")).get(0);
        org.junit.Assert.assertEquals("foo", fooMethod.getSimpleName());
        spoon.reflect.declaration.CtMethod<?> barMethod = foo.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "bar")).get(0);
        org.junit.Assert.assertEquals("bar", barMethod.getSimpleName());
        spoon.reflect.code.CtLocalVariable<?> assignment = ((spoon.reflect.code.CtLocalVariable<?>) (fooMethod.getBody().getStatements().get(0)));
        spoon.reflect.code.CtLocalVariable<?> newAssignment = barMethod.getBody().getStatement(0);
        assignment.replace(newAssignment);
        org.junit.Assert.assertEquals(fooMethod.getBody(), newAssignment.getParent());
        spoon.reflect.code.CtLiteral<java.lang.Integer> lit = ((spoon.reflect.code.CtLiteral<java.lang.Integer>) (foo.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtLiteral<?>>(spoon.reflect.code.CtLiteral.class)).get(0)));
        final spoon.reflect.declaration.CtElement parent = lit.getParent();
        spoon.reflect.code.CtLiteral<java.lang.Integer> newLit = factory.Code().createLiteral(0);
        lit.replace(newLit);
        org.junit.Assert.assertEquals("int y = 0", fooMethod.getBody().getStatement(0).toString());
        org.junit.Assert.assertEquals(parent, newLit.getParent());
    }

    @org.junit.Test
    public void testReplaceStmtByList() {
        spoon.reflect.declaration.CtClass<?> sample = factory.Package().get("spoon.test.replace.testclasses").getType("Foo");
        spoon.reflect.code.CtStatement stmt = sample.getMethod("retry").getBody().getStatement(0);
        spoon.reflect.code.CtBlock lst = sample.getMethod("statements").getBody();
        stmt.replace(lst);
        org.junit.Assert.assertEquals(1, sample.getMethod("retry").getBody().getStatements().size());
        org.junit.Assert.assertEquals(2, ((spoon.reflect.code.CtBlock) (sample.getMethod("retry").getBody().getStatement(0))).getStatements().size());
    }

    @org.junit.Test
    public void testReplaceStmtByListStatements() {
        spoon.reflect.declaration.CtClass<?> sample = factory.Package().get("spoon.test.replace.testclasses").getType("Foo");
        spoon.reflect.code.CtStatement stmt = sample.getMethod("retry").getBody().getStatement(0);
        java.util.List<spoon.reflect.code.CtStatement> lst = sample.getMethod("statements").getBody().getStatements();
        stmt.replace(lst);
        org.junit.Assert.assertEquals(2, sample.getMethod("retry").getBody().getStatements().size());
    }

    @org.junit.Test
    public void testReplaceStmtByListStatementsAndNull() {
        spoon.reflect.declaration.CtClass<?> sample = factory.Package().get("spoon.test.replace.testclasses").getType("Foo");
        spoon.reflect.code.CtStatement stmt = sample.getMethod("retry").getBody().getStatement(0);
        java.util.List<spoon.reflect.code.CtStatement> lst = sample.getMethod("statements").getBody().getStatements();
        java.util.List<spoon.reflect.code.CtStatement> lstWithNulls = new java.util.ArrayList<>();
        lstWithNulls.add(null);
        lstWithNulls.add(lst.get(0));
        lstWithNulls.add(null);
        lstWithNulls.add(lst.get(1));
        lstWithNulls.add(null);
        stmt.replace(lstWithNulls);
        org.junit.Assert.assertEquals(2, sample.getMethod("retry").getBody().getStatements().size());
    }

    @org.junit.Test
    public void testReplaceField() {
        spoon.reflect.declaration.CtClass<?> sample = factory.Package().get("spoon.test.replace.testclasses").getType("Foo");
        org.junit.Assert.assertEquals(factory.Type().createReference(int.class), sample.getField("i").getType());
        spoon.reflect.declaration.CtField replacement = factory.Core().createField();
        replacement.setSimpleName("i");
        replacement.setType(factory.Type().createReference(double.class));
        sample.getField("i").replace(replacement);
        org.junit.Assert.assertEquals(factory.Type().createReference(double.class), sample.getField("i").getType());
        replacement = factory.Core().createField();
        replacement.setSimpleName("j");
        replacement.setType(factory.Type().createReference(double.class));
        sample.getField("i").replace(replacement);
        org.junit.Assert.assertNull(sample.getField("i"));
        org.junit.Assert.assertNotNull(sample.getField("j"));
        org.junit.Assert.assertEquals(factory.Type().createReference(double.class), sample.getField("j").getType());
    }

    @org.junit.Test
    public void testReplaceMethod() {
        spoon.reflect.declaration.CtClass<?> sample = factory.Package().get("spoon.test.replace.testclasses").getType("Foo");
        org.junit.Assert.assertNotNull(sample.getMethod("foo"));
        org.junit.Assert.assertNull(sample.getMethod("notfoo"));
        spoon.reflect.declaration.CtMethod bar = factory.Core().createMethod();
        bar.setSimpleName("notfoo");
        bar.setType(factory.Type().createReference(void.class));
        sample.getMethod("foo").replace(bar);
        org.junit.Assert.assertNull(sample.getMethod("foo"));
        org.junit.Assert.assertNotNull(sample.getMethod("notfoo"));
    }

    @org.junit.Test
    public void testReplaceTwoMethods() {
        spoon.reflect.declaration.CtClass<?> sample = factory.Package().get("spoon.test.replace.testclasses").getType("Foo");
        org.junit.Assert.assertNotNull(sample.getMethod("foo"));
        org.junit.Assert.assertNull(sample.getMethod("notfoo"));
        spoon.reflect.declaration.CtMethod bar = factory.Core().createMethod();
        bar.setSimpleName("notfoo");
        bar.setType(factory.Type().createReference(void.class));
        spoon.reflect.declaration.CtMethod bar2 = factory.Core().createMethod();
        bar2.setSimpleName("notfoo2");
        bar2.setType(factory.Type().createReference(void.class));
        int originCountOfMethods = sample.getTypeMembers().size();
        sample.getMethod("foo").replace(java.util.Arrays.asList(bar, bar2));
        org.junit.Assert.assertNull(sample.getMethod("foo"));
        org.junit.Assert.assertNotNull(sample.getMethod("notfoo"));
        org.junit.Assert.assertNotNull(sample.getMethod("notfoo2"));
        org.junit.Assert.assertEquals((originCountOfMethods + 1), sample.getTypeMembers().size());
    }

    @org.junit.Test
    public void testReplaceExpression() {
        spoon.reflect.declaration.CtMethod<?> sample = factory.Package().get("spoon.test.replace.testclasses").getType("Foo").getMethod("foo");
        spoon.reflect.declaration.CtVariable<?> var = sample.getBody().getStatement(0);
        org.junit.Assert.assertTrue(((var.getDefaultExpression()) instanceof spoon.reflect.code.CtLiteral));
        org.junit.Assert.assertEquals(3, ((spoon.reflect.code.CtLiteral<?>) (var.getDefaultExpression())).getValue());
        spoon.reflect.code.CtLiteral replacement = factory.Core().createLiteral();
        replacement.setValue(42);
        var.getDefaultExpression().replace(replacement);
        org.junit.Assert.assertEquals(42, ((spoon.reflect.code.CtLiteral<?>) (var.getDefaultExpression())).getValue());
    }

    @org.junit.Test
    public void testReplaceStatement() {
        spoon.reflect.declaration.CtMethod<?> sample = factory.Package().get("spoon.test.replace.testclasses").getType("Foo").getMethod("foo");
        org.junit.Assert.assertTrue(((sample.getBody().getStatement(0)) instanceof spoon.reflect.declaration.CtVariable));
        spoon.reflect.code.CtStatement replacement = factory.Core().createInvocation();
        sample.getBody().getStatement(0).replace(replacement);
        org.junit.Assert.assertTrue(((sample.getBody().getStatement(0)) instanceof spoon.reflect.code.CtInvocation));
    }

    @org.junit.Test
    public void testReplaceIntegerReference() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.replace.testclasses.Tacos.class);
        final spoon.reflect.declaration.CtType<spoon.test.replace.testclasses.Tacos> aTacos = factory.Type().get(spoon.test.replace.testclasses.Tacos.class);
        final spoon.reflect.declaration.CtMethod<?> aMethod = aTacos.getMethodsByName("m").get(0);
        org.junit.Assert.assertEquals(factory.Type().INTEGER_PRIMITIVE, aMethod.getType());
        aMethod.getType().replace(factory.Type().DOUBLE_PRIMITIVE);
        org.junit.Assert.assertEquals(factory.Type().DOUBLE_PRIMITIVE, aMethod.getType());
    }

    @org.junit.Test
    public void testReplaceAllTypeRefenceWithGenerics() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.replace.testclasses.Tacos.class);
        final java.util.List<spoon.reflect.reference.CtTypeReference> references = spoon.reflect.visitor.Query.getElements(factory, new spoon.reflect.visitor.filter.ReferenceTypeFilter<spoon.reflect.reference.CtTypeReference>(spoon.reflect.reference.CtTypeReference.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.reference.CtTypeReference reference) {
                return ((reference.getActualTypeArguments().size()) > 0) && (super.matches(reference));
            }
        });
        references.get(0).replace(factory.Type().createReference(references.get(0).getQualifiedName()));
        final spoon.reflect.declaration.CtType<spoon.test.replace.testclasses.Tacos> aTacos = factory.Type().get(spoon.test.replace.testclasses.Tacos.class);
        final spoon.reflect.declaration.CtMethod<?> aMethod = aTacos.getMethodsByName("m2").get(0);
        final spoon.reflect.reference.CtTypeReference<java.lang.Object> expected = factory.Type().createReference("spoon.test.replace.testclasses.Tacos");
        org.junit.Assert.assertEquals(expected, aMethod.getType());
        org.junit.Assert.assertEquals(expected.getTypeDeclaration(), aMethod.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtConstructorCall.class)).get(0).getType().getTypeDeclaration());
    }

    @org.junit.Test
    public void testReplaceAPackageReferenceByAnotherOne() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput" });
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource("./src/test/resources/reference-package");
        launcher.run();
        final spoon.reflect.declaration.CtType<java.lang.Object> panini = launcher.getFactory().Type().get("Panini");
        final spoon.reflect.reference.CtTypeReference<?> burritos = panini.getElements(new spoon.reflect.visitor.filter.ReferenceTypeFilter<spoon.reflect.reference.CtTypeReference<?>>(spoon.reflect.reference.CtTypeReference.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.reference.CtTypeReference<?> reference) {
                return ("Burritos".equals(reference.getSimpleName())) && (super.matches(reference));
            }
        }).get(0);
        org.junit.Assert.assertEquals("com.awesome", burritos.getPackage().toString());
        org.junit.Assert.assertEquals("com.awesome.Burritos", panini.getMethodsByName("m").get(0).getType().toString());
        burritos.getPackage().replace(launcher.getFactory().Package().createReference("com.best"));
        org.junit.Assert.assertEquals("com.best", burritos.getPackage().toString());
        org.junit.Assert.assertEquals("com.best.Burritos", panini.getMethodsByName("m").get(0).getType().toString());
    }

    @org.junit.Test
    public void testReplaceAParameterReferenceToFieldReference() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.replace.testclasses.Tacos.class);
        final spoon.reflect.declaration.CtType<spoon.test.replace.testclasses.Tacos> aTacos = factory.Type().get(spoon.test.replace.testclasses.Tacos.class);
        final spoon.reflect.code.CtInvocation inv = aTacos.getMethodsByName("m3").get(0).getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtInvocation.class)).get(0);
        final spoon.reflect.code.CtVariableRead<?> variableRead = ((spoon.reflect.code.CtVariableRead<?>) (inv.getArguments().get(0)));
        final spoon.reflect.reference.CtParameterReference<?> aParameterReference = ((spoon.reflect.reference.CtParameterReference<?>) (variableRead.getVariable()));
        final spoon.reflect.reference.CtFieldReference<?> aFieldReference = aTacos.getField("field").getReference();
        org.junit.Assert.assertEquals(aParameterReference, variableRead.getVariable());
        org.junit.Assert.assertEquals("java.lang.System.err.println(param)", inv.toString());
        aParameterReference.replace(aFieldReference);
        org.junit.Assert.assertEquals(aFieldReference, variableRead.getVariable());
        org.junit.Assert.assertEquals("java.lang.System.err.println(field)", inv.toString());
    }

    @org.junit.Test
    public void testReplaceExecutableReferenceByAnotherOne() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.replace.testclasses.Tacos.class);
        final spoon.reflect.declaration.CtType<spoon.test.replace.testclasses.Tacos> aTacos = factory.Type().get(spoon.test.replace.testclasses.Tacos.class);
        final spoon.reflect.code.CtInvocation inv = aTacos.getMethodsByName("m3").get(0).getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtInvocation.class)).get(0);
        final spoon.reflect.reference.CtExecutableReference oldExecutable = inv.getExecutable();
        final spoon.reflect.reference.CtExecutableReference<java.lang.Object> newExecutable = factory.Executable().createReference("void java.io.PrintStream#print(java.lang.String)");
        org.junit.Assert.assertSame(oldExecutable, inv.getExecutable());
        oldExecutable.replace(newExecutable);
        org.junit.Assert.assertSame(newExecutable, inv.getExecutable());
        org.junit.Assert.assertEquals("print(java.lang.String)", inv.getExecutable().toString());
        org.junit.Assert.assertEquals("java.io.PrintStream", inv.getExecutable().getDeclaringType().toString());
        try {
            newExecutable.replace(java.util.Arrays.asList(oldExecutable, null));
            org.junit.Assert.fail();
        } catch (spoon.support.visitor.replace.InvalidReplaceException e) {
        }
    }

    @org.junit.Test
    public void testReplaceBlockTry() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<spoon.test.replace.testclasses.Mole> aMole = spoon.testing.utils.ModelUtils.buildClass(spoon.test.replace.testclasses.Mole.class);
        final spoon.reflect.code.CtBlock<?> newBlock = aMole.getFactory().Code().createCtBlock(aMole.getFactory().Code().createCodeSnippetStatement("int j = 0;").compile());
        final spoon.reflect.code.CtTry ctTry = aMole.getMethod("m").getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtTry.class)).get(0);
        org.junit.Assert.assertNotEquals(newBlock, ctTry.getBody());
        ctTry.getBody().replace(newBlock);
        org.junit.Assert.assertEquals(newBlock, ctTry.getBody());
    }
}

