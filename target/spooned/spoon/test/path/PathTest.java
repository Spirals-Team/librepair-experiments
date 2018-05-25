package spoon.test.path;


import spoon.Launcher;
import spoon.compiler.SpoonResourceHelper;


public class PathTest {
    spoon.reflect.factory.Factory factory;

    @org.junit.Before
    public void setup() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        factory = spoon.createFactory();
        spoon.createCompiler(factory, SpoonResourceHelper.resources("./src/test/java/spoon/test/path/Foo.java")).build();
    }

    private void equals(spoon.reflect.path.CtPath path, spoon.reflect.declaration.CtElement... elements) {
        java.util.Collection<spoon.reflect.declaration.CtElement> result = path.evaluateOn(factory.Package().getRootPackage());
        org.junit.Assert.assertEquals(elements.length, result.size());
        org.junit.Assert.assertArrayEquals(elements, result.toArray(new spoon.reflect.declaration.CtElement[0]));
    }

    private void equalsSet(spoon.reflect.path.CtPath path, java.util.Set<? extends spoon.reflect.declaration.CtElement> elements) {
        java.util.Collection<spoon.reflect.declaration.CtElement> result = path.evaluateOn(factory.Package().getRootPackage());
        org.junit.Assert.assertEquals(elements.size(), result.size());
        org.junit.Assert.assertTrue(result.containsAll(elements));
    }

    @org.junit.Test
    public void testBuilderMethod() throws java.lang.Exception {
        equalsSet(new spoon.reflect.path.CtPathBuilder().name("spoon").name("test").name("path").name("Foo").type(spoon.reflect.declaration.CtMethod.class).build(), factory.Type().get("spoon.test.path.Foo").getMethods());
        equalsSet(new spoon.reflect.path.CtPathStringBuilder().fromString(".spoon.test.path.Foo/CtMethod"), factory.Type().get("spoon.test.path.Foo").getMethods());
    }

    @org.junit.Test
    public void testBuilder() {
        equals(new spoon.reflect.path.CtPathBuilder().recursiveWildcard().name("toto").role(spoon.reflect.path.CtRole.DEFAULT_EXPRESSION).build(), factory.Package().get("spoon.test.path").getType("Foo").getField("toto").getDefaultExpression());
    }

    @org.junit.Test
    public void testPathFromString() throws java.lang.Exception {
        equals(new spoon.reflect.path.CtPathStringBuilder().fromString(".spoon.test.path.Foo.foo#body#statement[index=0]"), factory.Package().get("spoon.test.path").getType("Foo").getMethod("foo").getBody().getStatement(0));
        equals(new spoon.reflect.path.CtPathStringBuilder().fromString(".spoon.test.path.Foo.bar/CtParameter"), factory.Package().get("spoon.test.path").getType("Foo").getMethod("bar", factory.Type().createReference(int.class), factory.Type().createReference(int.class)).getParameters().toArray(new spoon.reflect.declaration.CtElement[0]));
        spoon.reflect.code.CtLiteral<java.lang.String> literal = factory.Core().createLiteral();
        literal.setValue("salut");
        literal.setType(literal.getFactory().Type().STRING);
        equals(new spoon.reflect.path.CtPathStringBuilder().fromString(".spoon.test.path.Foo.toto#defaultExpression"), literal);
    }

    @org.junit.Test
    public void testMultiPathFromString() throws java.lang.Exception {
        java.util.Collection<spoon.reflect.declaration.CtElement> results = new spoon.reflect.path.CtPathStringBuilder().fromString(".spoon.test.path.Foo.foo#body#statement").evaluateOn(factory.getModel().getRootPackage());
        org.junit.Assert.assertEquals(results.size(), 3);
        results = new spoon.reflect.path.CtPathStringBuilder().fromString("#subPackage").evaluateOn(factory.getModel().getRootPackage());
        org.junit.Assert.assertEquals(results.size(), 1);
        results = new spoon.reflect.path.CtPathStringBuilder().fromString(".spoon.test.path.Foo.bar##annotation[index=0]#value").evaluateOn(factory.getModel().getRootPackage());
        org.junit.Assert.assertEquals(results.size(), 1);
    }

    @org.junit.Test
    public void testIncorrectPathFromString() throws java.lang.Exception {
        java.util.Collection<spoon.reflect.declaration.CtElement> results = new spoon.reflect.path.CtPathStringBuilder().fromString(".spoon.test.path.Foo.bar#body#statement[index=2]#else").evaluateOn(factory.getModel().getRootPackage());
        org.junit.Assert.assertEquals(results.size(), 0);
        results = new spoon.reflect.path.CtPathStringBuilder().fromString(".spoon.test.path.Foo.foo#body#statement[index=3]").evaluateOn(factory.getModel().getRootPackage());
        org.junit.Assert.assertEquals(results.size(), 0);
        results = new spoon.reflect.path.CtPathStringBuilder().fromString("#subPackage[name=nonExistingPackage]").evaluateOn(factory.getModel().getRootPackage());
        org.junit.Assert.assertEquals(results.size(), 0);
        results = new spoon.reflect.path.CtPathStringBuilder().fromString(".spoon.test.path.Foo.bar##annotation[index=0]#value[key=misspelled]").evaluateOn(factory.getModel().getRootPackage());
        org.junit.Assert.assertEquals(results.size(), 0);
    }

    @org.junit.Test
    public void testGetPathFromNonParent() throws java.lang.Exception {
        spoon.reflect.declaration.CtMethod fooMethod = ((spoon.reflect.declaration.CtMethod) (new spoon.reflect.path.CtPathStringBuilder().fromString(".spoon.test.path.Foo.foo").evaluateOn(factory.getModel().getRootPackage()).iterator().next()));
        spoon.reflect.declaration.CtMethod barMethod = ((spoon.reflect.declaration.CtMethod) (new spoon.reflect.path.CtPathStringBuilder().fromString(".spoon.test.path.Foo.bar").evaluateOn(factory.getModel().getRootPackage()).iterator().next()));
        try {
            new spoon.reflect.path.CtElementPathBuilder().fromElement(fooMethod, barMethod);
            org.junit.Assert.fail("No path should be found to .spoon.test.path.Foo.foo from .spoon.test.path.Foo.bar");
        } catch (spoon.reflect.path.CtPathException e) {
        }
    }

    @org.junit.Test
    public void testWildcards() throws java.lang.Exception {
        java.util.List<spoon.reflect.declaration.CtElement> list = new java.util.LinkedList<>();
        list.add(factory.getModel().getRootPackage());
        equals(new spoon.reflect.path.CtPathStringBuilder().fromString(".spoon.test.path.Foo.*#body#statement[index=0]"), ((spoon.reflect.declaration.CtClass) (factory.Package().get("spoon.test.path").getType("Foo"))).getConstructor().getBody().getStatement(0), factory.Package().get("spoon.test.path").getType("Foo").getMethod("foo").getBody().getStatement(0), factory.Package().get("spoon.test.path").getType("Foo").getMethod("bar", factory.Type().createReference(int.class), factory.Type().createReference(int.class)).getBody().getStatement(0));
    }

    @org.junit.Test
    public void testRoles() throws java.lang.Exception {
        equals(new spoon.reflect.path.CtPathStringBuilder().fromString(".**/CtIf#else"), ((spoon.reflect.code.CtIf) (factory.Package().get("spoon.test.path").getType("Foo").getMethod("foo").getBody().getStatement(2))).getElseStatement());
        equals(new spoon.reflect.path.CtPathStringBuilder().fromString(".**#else"), ((spoon.reflect.code.CtIf) (factory.Package().get("spoon.test.path").getType("Foo").getMethod("foo").getBody().getStatement(2))).getElseStatement());
    }

    @org.junit.Test
    public void toStringTest() throws java.lang.Exception {
        comparePath(".spoon.test.path.Foo/CtMethod");
        comparePath(".spoon.test.path.Foo.foo#body#statement[index=0]");
        comparePath(".spoon.test.path.Foo.bar/CtParameter");
        comparePath(".spoon.test.path.Foo.toto#defaultExpression");
        comparePath(".spoon.test.path.Foo.*#body#statement[index=0]");
        comparePath(".**/CtIf#else");
        comparePath(".**#else");
    }

    private void comparePath(java.lang.String path) throws spoon.reflect.path.CtPathException {
        org.junit.Assert.assertEquals(path, new spoon.reflect.path.CtPathStringBuilder().fromString(path).toString());
    }

    @org.junit.Test
    public void exceptionTest() {
        try {
            new spoon.reflect.path.CtPathStringBuilder().fromString("/CtClassss");
            org.junit.Assert.fail();
        } catch (spoon.reflect.path.CtPathException e) {
            org.junit.Assert.assertEquals("Unable to locate element with type CtClassss in Spoon model", e.getMessage());
        }
    }
}

