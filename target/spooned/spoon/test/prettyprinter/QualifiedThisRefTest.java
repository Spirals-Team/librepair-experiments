package spoon.test.prettyprinter;


import spoon.Launcher;
import spoon.compiler.SpoonResourceHelper;


public class QualifiedThisRefTest {
    spoon.reflect.factory.Factory factory;

    @org.junit.Before
    public void setup() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        factory = spoon.createFactory();
        factory.getEnvironment().setComplianceLevel(8);
        spoon.createCompiler(factory, SpoonResourceHelper.resources("./src/test/java/spoon/test/prettyprinter/testclasses/QualifiedThisRef.java")).build();
        factory.getEnvironment().setAutoImports(true);
    }

    @org.junit.Test
    public void testQualifiedThisRef() {
        spoon.reflect.visitor.DefaultJavaPrettyPrinter printer = new spoon.reflect.visitor.DefaultJavaPrettyPrinter(factory.getEnvironment());
        spoon.reflect.declaration.CtType<?> ctClass = factory.Type().get(spoon.test.prettyprinter.testclasses.QualifiedThisRef.class);
        java.util.Collection<spoon.reflect.declaration.CtImport> imports = printer.computeImports(ctClass);
        final java.util.List<spoon.reflect.declaration.CtType<?>> ctTypes = new java.util.ArrayList<>();
        ctTypes.add(ctClass);
        printer.getElementPrinterHelper().writeHeader(ctTypes, imports);
        printer.scan(ctClass);
        org.junit.Assert.assertTrue(printer.getResult().contains("Object o = this"));
        org.junit.Assert.assertTrue(printer.getResult().contains("Object o2 = QualifiedThisRef.this"));
    }

    @org.junit.Test
    public void testCloneThisAccess() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.delete.testclasses.Adobada.class);
        final spoon.reflect.declaration.CtClass<spoon.test.delete.testclasses.Adobada> adobada = factory.Class().get(spoon.test.delete.testclasses.Adobada.class);
        final spoon.reflect.declaration.CtMethod<?> m2 = adobada.getMethod("methodUsingjlObjectMethods");
        spoon.reflect.code.CtThisAccess th = ((spoon.reflect.code.CtThisAccess) (m2.getElements(new spoon.reflect.visitor.filter.TypeFilter(spoon.reflect.code.CtThisAccess.class)).get(0)));
        org.junit.Assert.assertEquals(true, th.isImplicit());
        org.junit.Assert.assertEquals("notify()", th.getParent().toString());
        spoon.reflect.code.CtInvocation<?> clone = m2.clone().getBody().getStatement(0);
        org.junit.Assert.assertEquals(true, clone.getTarget().isImplicit());
        org.junit.Assert.assertEquals("notify()", clone.toString());
    }

    @org.junit.Test
    public void testPrintCtFieldAccessWorkEvenWhenParentNotInitialized() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass zeclass = factory.Class().get(spoon.test.prettyprinter.testclasses.QualifiedThisRef.class);
        java.util.List<spoon.reflect.declaration.CtMethod> methods = zeclass.getMethodsByName("bla");
        org.junit.Assert.assertEquals(1, methods.size());
        spoon.reflect.code.CtStatement invocation = methods.get(0).getBody().getStatement(0);
        org.junit.Assert.assertTrue((invocation instanceof spoon.reflect.code.CtInvocation));
        spoon.reflect.code.CtInvocation<?> arg0 = ((spoon.reflect.code.CtInvocation) (invocation));
        spoon.reflect.code.CtExpression param = arg0.getArguments().get(0);
        spoon.reflect.reference.CtExecutableReference execref = factory.Core().createExecutableReference();
        execref.setDeclaringType(factory.Type().createReference("java.util.Map"));
        execref.setSimpleName("exorcise");
        execref.setStatic(true);
        spoon.reflect.reference.CtTypeReference tmp = param.getType();
        spoon.reflect.code.CtExpression arg = null;
        spoon.reflect.reference.CtFieldReference ctfe = factory.createFieldReference();
        ctfe.setSimpleName("class");
        ctfe.setDeclaringType(tmp.box());
        arg = factory.Core().createFieldRead();
        ((spoon.support.reflect.code.CtFieldAccessImpl) (arg)).setVariable(ctfe);
        spoon.reflect.code.CtLiteral location = factory.Core().createLiteral();
        location.setType(factory.Type().createReference(java.lang.String.class));
        spoon.reflect.reference.CtTypeReference tmpref = factory.Core().clone(tmp);
        spoon.reflect.code.CtInvocation invoc = factory.Core().createInvocation();
        invoc.setExecutable(execref);
        invoc.setArguments(java.util.Arrays.asList(new spoon.reflect.code.CtExpression[]{ param, arg, location }));
        execref.setActualTypeArguments(java.util.Arrays.asList(new spoon.reflect.reference.CtTypeReference<?>[]{ tmpref }));
        arg0.getArguments().set(0, invoc);
        spoon.reflect.visitor.DefaultJavaPrettyPrinter printer = new spoon.reflect.visitor.DefaultJavaPrettyPrinter(factory.getEnvironment());
        printer.visitCtClass(zeclass);
        org.junit.Assert.assertFalse(printer.getResult().isEmpty());
    }
}

