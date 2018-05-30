package spoon.test.api;


import java.io.File;
import javax.sound.sampled.AudioFormat;
import spoon.Launcher;
import spoon.reflect.code.CtFieldAccess;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtLocalVariable;
import spoon.reflect.code.CtReturn;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtExecutableReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.visitor.SignaturePrinter;
import spoon.test.api.testclasses.Bar;


public class NoClasspathTest {
    @org.junit.Test
    public void test() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.getEnvironment().setNoClasspath(true);
        spoon.getEnvironment().setLevel("OFF");
        spoon.addInputResource("./src/test/resources/spoon/test/noclasspath/fields");
        spoon.getEnvironment().setSourceOutputDirectory(new File("target/spooned/apitest"));
        spoon.run();
        Factory factory = spoon.getFactory();
        CtClass<Object> clazz = factory.Class().get("Foo");
        org.junit.Assert.assertEquals("Foo", clazz.getSimpleName());
        CtTypeReference<?> superclass = clazz.getSuperclass();
        org.junit.Assert.assertEquals("Unknown", superclass.getSimpleName());
        try {
            superclass.getActualClass();
            org.junit.Assert.fail();
        } catch (spoon.support.SpoonClassNotFoundException e) {
        }
        org.junit.Assert.assertNull(superclass.getDeclaration());
        org.junit.Assert.assertTrue(superclass.getAllFields().isEmpty());
        try {
            superclass.getActualClass();
            org.junit.Assert.fail();
        } catch (spoon.support.SpoonClassNotFoundException e) {
        }
        {
            CtMethod<?> method = clazz.getMethod("method", new CtTypeReference[0]);
            org.junit.Assert.assertNotNull(method);
            java.util.List<CtInvocation<?>> invocations = method.getElements(new TypeFilter<CtInvocation<?>>(CtInvocation.class));
            org.junit.Assert.assertEquals(1, invocations.size());
            CtInvocation<?> c = invocations.get(0);
            org.junit.Assert.assertEquals("method", c.getExecutable().getSimpleName());
            org.junit.Assert.assertEquals("x.method()", method.getBody().getStatement(1).toString());
        }
        {
            CtMethod<?> method = clazz.getMethod("m2", new CtTypeReference[0]);
            org.junit.Assert.assertNotNull(method);
            java.util.List<CtInvocation<?>> invocations = method.getElements(new TypeFilter<CtInvocation<?>>(CtInvocation.class));
            org.junit.Assert.assertEquals(3, invocations.size());
            CtInvocation<?> c = invocations.get(1);
            org.junit.Assert.assertEquals("second", c.getExecutable().getSimpleName());
            org.junit.Assert.assertEquals("x.first().second().third()", method.getBody().getStatement(1).toString());
        }
        {
            CtMethod<?> method = clazz.getMethod("m1", new CtTypeReference[0]);
            org.junit.Assert.assertNotNull(method);
            java.util.List<CtInvocation<?>> invocations = method.getElements(new TypeFilter<CtInvocation<?>>(CtInvocation.class));
            org.junit.Assert.assertEquals(1, invocations.size());
            invocations.get(0);
            org.junit.Assert.assertEquals("x.y.z.method()", method.getBody().getStatement(0).toString());
        }
        {
            CtMethod<?> method = clazz.getMethod("m3", new CtTypeReference[0]);
            org.junit.Assert.assertNotNull(method);
            java.util.List<CtInvocation<?>> invocations = method.getElements(new TypeFilter<CtInvocation<?>>(CtInvocation.class));
            org.junit.Assert.assertEquals(1, invocations.size());
            invocations.get(0);
            CtLocalVariable<?> statement = method.getBody().getStatement(0);
            CtFieldAccess<?> fa = ((CtFieldAccess<?>) (statement.getDefaultExpression()));
            org.junit.Assert.assertTrue(((fa.getTarget()) instanceof CtInvocation));
            org.junit.Assert.assertEquals("field", fa.getVariable().getSimpleName());
            org.junit.Assert.assertEquals("int x = first().field", statement.toString());
        }
    }

    @org.junit.Test
    public void testBug20141021() {
        Launcher spoon = new Launcher();
        Factory f = spoon.getFactory();
        CtExecutableReference<Object> ref = f.Core().createExecutableReference();
        ref.setSimpleName("foo");
        SignaturePrinter pr = new SignaturePrinter();
        pr.scan(ref);
        String s = pr.getSignature();
        org.junit.Assert.assertEquals("foo()", s);
    }

    @org.junit.Test
    public void testGetStaticDependency() {
        Launcher spoon = new Launcher();
        final Factory factory = spoon.getFactory();
        factory.getEnvironment().setAutoImports(false);
        spoon.addInputResource("./src/test/java/spoon/test/api/testclasses/");
        spoon.getEnvironment().setSourceOutputDirectory(new File("target/spooned/apitest"));
        spoon.run();
        CtTypeReference<?> expectedType = factory.Type().createReference(AudioFormat.Encoding.class);
        CtClass<?> clazz = factory.Class().get(Bar.class);
        CtMethod<?> method = clazz.getMethodsByName("doSomething").get(0);
        CtReturn<?> ctReturn = method.getElements(new TypeFilter<CtReturn<?>>(CtReturn.class)).get(0);
        org.junit.Assert.assertEquals(true, ctReturn.getReferencedTypes().contains(expectedType));
    }

    @org.junit.Test
    public void testIssue1747() {
        Launcher spoon = new Launcher();
        final Factory factory = spoon.getFactory();
        factory.getEnvironment().setNoClasspath(true);
        spoon.addInputResource("./src/test/resources/noclasspath/SubscriptionAdapter.java");
        spoon.buildModel();
    }

    @org.junit.Test
    public void testInheritanceInNoClassPathWithClasses() throws java.io.IOException {
        String sourceInputDirPath = "./src/test/resources/spoon/test/inheritance";
        String targetBinPath = "./target/spoon-nocp-bin";
        Launcher spoon = new Launcher();
        spoon.getEnvironment().setShouldCompile(true);
        spoon.addInputResource(sourceInputDirPath);
        spoon.setBinaryOutputDirectory(targetBinPath);
        spoon.run();
        spoon = new Launcher();
        spoon.getEnvironment().setNoClasspath(true);
        spoon.getEnvironment().setSourceClasspath(new String[]{ targetBinPath });
        spoon.addInputResource((sourceInputDirPath + "/AnotherClass.java"));
        spoon.buildModel();
        CtType anotherclass = spoon.getFactory().Type().get("org.acme.AnotherClass");
        org.junit.Assert.assertEquals(1, anotherclass.getFields().size());
        CtField field = ((CtField) (anotherclass.getFields().get(0)));
        CtTypeReference myClassReference = spoon.getFactory().Type().createReference("fr.acme.MyClass");
        org.junit.Assert.assertEquals(myClassReference, field.getType());
        org.junit.Assert.assertNotNull(myClassReference.getActualClass());
        CtTypeReference myInterfaceReference = spoon.getFactory().Type().createReference("org.myorganization.MyInterface");
        org.junit.Assert.assertTrue(myClassReference.isSubtypeOf(myInterfaceReference));
        org.junit.Assert.assertTrue(field.getType().isSubtypeOf(myInterfaceReference));
    }
}

