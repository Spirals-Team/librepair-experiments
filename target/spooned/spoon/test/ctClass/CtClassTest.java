package spoon.test.ctClass;


public class CtClassTest {
    @org.junit.Test
    public void getConstructor() throws java.lang.Exception {
        final spoon.reflect.factory.Factory build = spoon.testing.utils.ModelUtils.build(spoon.test.ctClass.testclasses.Foo.class);
        final spoon.reflect.declaration.CtClass<?> foo = ((spoon.reflect.declaration.CtClass<?>) (build.Type().get(spoon.test.ctClass.testclasses.Foo.class)));
        org.junit.Assert.assertEquals(3, foo.getConstructors().size());
        spoon.reflect.reference.CtTypeReference<java.lang.Object> typeString = build.Code().createCtTypeReference(java.lang.String.class);
        spoon.reflect.declaration.CtConstructor<?> constructor = foo.getConstructor(typeString);
        org.junit.Assert.assertEquals(typeString, constructor.getParameters().get(0).getType());
        spoon.reflect.reference.CtArrayTypeReference<java.lang.Object> typeStringArray = build.Core().createArrayTypeReference();
        typeStringArray.setComponentType(typeString);
        constructor = foo.getConstructor(typeStringArray);
        org.junit.Assert.assertEquals(typeStringArray, constructor.getParameters().get(0).getType());
        spoon.reflect.reference.CtArrayTypeReference<java.lang.Object> typeStringArrayArray = build.Core().createArrayTypeReference();
        typeStringArrayArray.setComponentType(typeStringArray);
        constructor = foo.getConstructor(typeStringArrayArray);
        org.junit.Assert.assertEquals(typeStringArrayArray, constructor.getParameters().get(0).getType());
        spoon.reflect.declaration.CtConstructor cons = foo.getConstructors().toArray(new spoon.reflect.declaration.CtConstructor[0])[0].clone();
        foo.addConstructor(cons);
        org.junit.Assert.assertEquals(3, foo.getConstructors().size());
        cons.addParameter(cons.getFactory().createParameter().setType(cons.getFactory().Type().OBJECT));
        org.junit.Assert.assertEquals(4, foo.getConstructors().size());
        org.junit.Assert.assertSame(cons, foo.getTypeMembers().get(1));
        org.junit.Assert.assertSame(foo, cons.getParent());
        spoon.reflect.declaration.CtConstructor cons2 = foo.getConstructors().toArray(new spoon.reflect.declaration.CtConstructor[0])[0].clone();
        cons2.setPosition(null);
        foo.addConstructor(cons2);
        org.junit.Assert.assertSame(cons2, foo.getTypeMembers().get(4));
    }

    @org.junit.Test
    public void testParentOfTheEnclosingClassOfStaticClass() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/resources/noclasspath/InvariantChecker.java");
        launcher.addInputResource("./src/test/resources/noclasspath/FileIO.java");
        launcher.addInputResource("./src/test/resources/noclasspath/Daikon.java");
        launcher.setSourceOutputDirectory("./target/class");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.run();
        final spoon.reflect.declaration.CtClass<java.lang.Object> aClass = launcher.getFactory().Class().get("daikon.tools.InvariantChecker");
        final spoon.reflect.declaration.CtType<?> staticClass = aClass.getNestedType("InvariantCheckProcessor");
        org.junit.Assert.assertNotNull(staticClass);
        org.junit.Assert.assertEquals("InvariantCheckProcessor", staticClass.getSimpleName());
        org.junit.Assert.assertNotNull(staticClass.getSuperclass());
        org.junit.Assert.assertEquals("daikon.FileIO$Processor", staticClass.getSuperclass().getQualifiedName());
        org.junit.Assert.assertNull(aClass.getSuperclass());
        spoon.testing.utils.ModelUtils.canBeBuilt("./target/class", 8, true);
    }

    @org.junit.Test
    public void testNoClasspathWithSuperClassOfAClassInAnInterface() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/resources/noclasspath/draw2d");
        launcher.setSourceOutputDirectory("./target/draw2d");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.run();
        final spoon.reflect.declaration.CtClass<java.lang.Object> aClass = launcher.getFactory().Class().get("org.eclipse.draw2d.parts.ScrollableThumbnail");
        final spoon.reflect.declaration.CtType<?> innerClass = aClass.getNestedType("ClickScrollerAndDragTransferrer");
        org.junit.Assert.assertEquals("org.eclipse.draw2d.MouseMotionListener$Stub", innerClass.getSuperclass().getQualifiedName());
        spoon.testing.utils.ModelUtils.canBeBuilt("./target/draw2d", 8, true);
    }

    @org.junit.Test
    public void testAllTypeReferencesToALocalTypeShouldNotStartWithNumber() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<spoon.test.ctClass.testclasses.Pozole> aPozole = spoon.testing.utils.ModelUtils.buildClass(spoon.test.ctClass.testclasses.Pozole.class);
        final spoon.reflect.declaration.CtClass<?> cook = aPozole.getNestedType("1Cook");
        org.junit.Assert.assertEquals("1Cook", cook.getSimpleName());
        org.junit.Assert.assertEquals("spoon.test.ctClass.testclasses.Pozole$1Cook", cook.getQualifiedName());
        final java.util.Set<? extends spoon.reflect.declaration.CtConstructor<?>> constructors = cook.getConstructors();
        final java.lang.String expectedConstructor = ("public Cook() {" + (java.lang.System.lineSeparator())) + "}";
        org.junit.Assert.assertEquals(expectedConstructor, constructors.toArray(new spoon.reflect.declaration.CtConstructor[constructors.size()])[0].toString());
        org.junit.Assert.assertEquals("final java.lang.Class<Cook> cookClass = Cook.class", cook.getMethod("m").getBody().getStatement(0).toString());
        spoon.reflect.factory.Factory factory = aPozole.getFactory();
        aPozole.removeModifier(spoon.reflect.declaration.ModifierKind.PUBLIC);
        factory.Code().createCodeSnippetStatement(aPozole.toString()).compile();
        spoon.reflect.declaration.CtClass internalClass = factory.Core().createClass();
        internalClass.setSimpleName("Foo");
        cook.getParent(spoon.reflect.code.CtBlock.class).addStatement(internalClass);
        org.junit.Assert.assertEquals("Foo", internalClass.getSimpleName());
        org.junit.Assert.assertEquals("spoon.test.ctClass.testclasses.Pozole$Foo", internalClass.getQualifiedName());
        internalClass.addConstructor(factory.Core().createConstructor());
        spoon.reflect.declaration.CtConstructor cons = ((spoon.reflect.declaration.CtConstructor) (internalClass.getConstructors().toArray(new spoon.reflect.declaration.CtConstructor[0])[0]));
        cons.setBody(factory.Core().createBlock());
        spoon.reflect.code.CtConstructorCall call = cook.getFactory().Core().createConstructorCall();
        call.setExecutable(cons.getReference());
        org.junit.Assert.assertEquals(internalClass, internalClass.getReference().getDeclaration());
        org.junit.Assert.assertEquals("new Foo()", call.toString());
        internalClass.insertAfter(call);
        factory.getEnvironment().setAutoImports(true);
        factory.Code().createCodeSnippetStatement(aPozole.toString()).compile();
        factory.getEnvironment().setAutoImports(false);
        factory.Code().createCodeSnippetStatement(aPozole.toString()).compile();
    }

    @org.junit.Test
    public void testSpoonShouldInferImplicitPackageInNoClasspath() throws java.lang.Exception {
        final spoon.Launcher launcher2 = new spoon.Launcher();
        launcher2.addInputResource("./src/test/resources/noclasspath/issue1293/com/cristal/ircica/applicationcolis/userinterface/fragments/TransporteurFragment.java");
        launcher2.getEnvironment().setNoClasspath(true);
        launcher2.buildModel();
        final spoon.reflect.declaration.CtClass<java.lang.Object> aClass2 = launcher2.getFactory().Class().get("com.cristal.ircica.applicationcolis.userinterface.fragments.TransporteurFragment");
        final java.lang.String type2 = aClass2.getSuperclass().getQualifiedName();
        spoon.reflect.declaration.CtField field = aClass2.getField("transporteurRadioGroup");
        org.junit.Assert.assertThat(field.getType().getQualifiedName(), org.hamcrest.core.Is.is("android.widget.RadioGroup"));
        org.junit.Assert.assertThat(type2, org.hamcrest.core.Is.is("com.cristal.ircica.applicationcolis.userinterface.fragments.CompletableFragment"));
    }

    @org.junit.Test
    public void testDefaultConstructorAreOk() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/ctClass/testclasses/issue1306");
        launcher.setSourceOutputDirectory("./target/issue1306");
        launcher.getEnvironment().setNoClasspath(false);
        launcher.getEnvironment().setShouldCompile(true);
        launcher.getEnvironment().setAutoImports(true);
        launcher.run();
        final spoon.reflect.declaration.CtClass<java.lang.Object> aClass = launcher.getFactory().Class().get("spoon.test.ctClass.testclasses.issue1306.internal.BooleanArraysBaseTest");
        org.junit.Assert.assertThat(aClass, org.hamcrest.CoreMatchers.notNullValue());
        spoon.testing.utils.ModelUtils.canBeBuilt("./target/issue1306", 8, true);
    }

    @org.junit.Test
    public void testCloneAnonymousClassInvocation() {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/ctClass/testclasses/AnonymousClass.java");
        launcher.getEnvironment().setAutoImports(false);
        launcher.buildModel();
        spoon.reflect.CtModel model = launcher.getModel();
        spoon.reflect.code.CtNewClass newClassInvocation = launcher.getModel().getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtNewClass>(spoon.reflect.code.CtNewClass.class)).get(0);
        spoon.reflect.code.CtNewClass newClassInvocationCloned = newClassInvocation.clone();
        spoon.reflect.declaration.CtClass anonymousClass = newClassInvocation.getAnonymousClass();
        spoon.reflect.declaration.CtClass anonymousClassCloned = newClassInvocationCloned.getAnonymousClass();
        org.junit.Assert.assertEquals(0, anonymousClass.getAllFields().size());
        org.junit.Assert.assertEquals(0, anonymousClassCloned.getAllFields().size());
        org.junit.Assert.assertTrue(((newClassInvocation.toString().length()) > 0));
        org.junit.Assert.assertTrue(((newClassInvocationCloned.toString().length()) > 0));
        org.junit.Assert.assertEquals(newClassInvocation.toString(), newClassInvocationCloned.toString());
    }

    @org.junit.Test
    public void testCloneAnonymousClassInvocationWithAutoimports() {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/ctClass/testclasses/AnonymousClass.java");
        launcher.getEnvironment().setAutoImports(true);
        launcher.buildModel();
        spoon.reflect.CtModel model = launcher.getModel();
        spoon.reflect.code.CtNewClass newClassInvocation = launcher.getModel().getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtNewClass>(spoon.reflect.code.CtNewClass.class)).get(0);
        spoon.reflect.code.CtNewClass newClassInvocationCloned = newClassInvocation.clone();
        spoon.reflect.declaration.CtClass anonymousClass = newClassInvocation.getAnonymousClass();
        spoon.reflect.declaration.CtClass anonymousClassCloned = newClassInvocationCloned.getAnonymousClass();
        org.junit.Assert.assertEquals(0, anonymousClass.getAllFields().size());
        org.junit.Assert.assertEquals(0, anonymousClassCloned.getAllFields().size());
        org.junit.Assert.assertTrue(((newClassInvocation.toString().length()) > 0));
        org.junit.Assert.assertTrue(((newClassInvocationCloned.toString().length()) > 0));
        org.junit.Assert.assertEquals(newClassInvocation.toString(), newClassInvocationCloned.toString());
    }
}

