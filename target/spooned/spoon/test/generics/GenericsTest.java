package spoon.test.generics;


import java.util.List;
import spoon.Launcher;
import spoon.SpoonModelBuilder;
import spoon.compiler.SpoonResourceHelper;
import spoon.reflect.code.CtNewClass;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.declaration.CtTypeParameter;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtTypeReference;


public class GenericsTest {
    @org.junit.Test
    public void testBugComparableComparator() throws java.lang.Exception {
        CtClass<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.generics", "ComparableComparatorBug");
        org.junit.Assert.assertEquals("ComparableComparatorBug", type.getSimpleName());
        spoon.reflect.declaration.CtField<?> field = type.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.declaration.CtField<?>>(spoon.reflect.declaration.CtField.class)).get(1);
        org.junit.Assert.assertEquals(0, field.getType().getActualTypeArguments().size());
        org.junit.Assert.assertEquals(0, ((spoon.reflect.code.CtConstructorCall<?>) (field.getDefaultExpression())).getType().getActualTypeArguments().size());
    }

    @org.junit.Test
    public void testModelBuildingTree() throws java.lang.Exception {
        CtClass<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.generics", "Tree");
        org.junit.Assert.assertEquals("Tree", type.getSimpleName());
        CtTypeParameter typeParameter = type.getFormalCtTypeParameters().get(0);
        org.junit.Assert.assertEquals("V", typeParameter.getSimpleName());
        org.junit.Assert.assertEquals("[java.io.Serializable, java.lang.Comparable<V>]", typeParameter.getSuperclass().asCtIntersectionTypeReference().getBounds().toString());
        spoon.reflect.declaration.CtMethod<?> node5 = type.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "node5")).get(0);
        org.junit.Assert.assertEquals("this.<java.lang.Class<? extends java.lang.Throwable>>foo()", node5.getBody().getStatement(0).toString());
    }

    @org.junit.Test
    public void testModelBuildingGenericConstructor() throws java.lang.Exception {
        CtClass<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.generics", "GenericConstructor");
        org.junit.Assert.assertEquals("GenericConstructor", type.getSimpleName());
        CtTypeParameter typeParameter = type.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.declaration.CtConstructor<?>>(spoon.reflect.declaration.CtConstructor.class)).get(0).getFormalCtTypeParameters().get(0);
        org.junit.Assert.assertEquals("E", typeParameter.getSimpleName());
    }

    @org.junit.Test
    public void testDiamond2() throws java.lang.Exception {
        CtClass<spoon.test.generics.GenericConstructor> type = spoon.testing.utils.ModelUtils.build("spoon.test.generics", "GenericConstructor");
        org.junit.Assert.assertEquals("GenericConstructor", type.getSimpleName());
        spoon.reflect.declaration.CtConstructor<spoon.test.generics.GenericConstructor> c = type.getConstructor();
        spoon.reflect.code.CtLocalVariable<?> var = c.getBody().getStatement(1);
        org.junit.Assert.assertEquals("java.lang.Integer", var.getType().getActualTypeArguments().get(0).getQualifiedName());
        spoon.reflect.code.CtConstructorCall<?> constructorCall = ((spoon.reflect.code.CtConstructorCall<?>) (var.getDefaultExpression()));
        org.junit.Assert.assertTrue(constructorCall.getExecutable().getActualTypeArguments().isEmpty());
    }

    @org.junit.Test
    public void testDiamond1() {
        Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        CtClass<?> clazz = factory.Code().createCodeSnippetStatement(("class Diamond {\n" + ("\tjava.util.List<String> f = new java.util.ArrayList<>();\n" + "}"))).compile();
        spoon.reflect.declaration.CtField<?> f = clazz.getFields().get(0);
        spoon.reflect.code.CtConstructorCall<?> val = ((spoon.reflect.code.CtConstructorCall<?>) (f.getDefaultExpression()));
        org.junit.Assert.assertTrue(val.getType().getActualTypeArguments().get(0).isImplicit());
        org.junit.Assert.assertEquals("", val.getType().getActualTypeArguments().get(0).toString());
        org.junit.Assert.assertEquals("java.lang.String", val.getType().getActualTypeArguments().get(0).getQualifiedName());
        org.junit.Assert.assertEquals("new java.util.ArrayList<>()", val.toString());
    }

    @org.junit.Test
    public void testModelBuildingSimilarSignatureMethods() throws java.lang.Exception {
        CtClass<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.generics", "SimilarSignatureMethodes");
        List<spoon.reflect.declaration.CtNamedElement> methods = type.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtNamedElement.class, "methode"));
        org.junit.Assert.assertEquals(2, methods.size());
        CtTypeParameter typeParameter = ((spoon.reflect.declaration.CtMethod<?>) (methods.get(0))).getFormalCtTypeParameters().get(0);
        org.junit.Assert.assertEquals("E", typeParameter.getSimpleName());
        spoon.reflect.declaration.CtParameter<?> param = ((spoon.reflect.declaration.CtMethod<?>) (methods.get(0))).getParameters().get(0);
        org.junit.Assert.assertEquals("E", param.getType().toString());
    }

    @org.junit.Test
    public void testTypeParameterReference() throws java.lang.Exception {
        Factory factory = spoon.testing.utils.ModelUtils.build(spoon.test.generics.ClassThatBindsAGenericType.class, spoon.test.generics.ClassThatDefinesANewTypeArgument.class);
        CtClass<?> classThatBindsAGenericType = factory.Class().get(spoon.test.generics.ClassThatBindsAGenericType.class);
        CtClass<?> classThatDefinesANewTypeArgument = factory.Class().get(spoon.test.generics.ClassThatDefinesANewTypeArgument.class);
        CtTypeReference<?> tr1 = classThatBindsAGenericType.getSuperclass();
        CtTypeReference<?> trExtends = tr1.getActualTypeArguments().get(0);
        CtTypeParameter tr2 = classThatDefinesANewTypeArgument.getFormalCtTypeParameters().get(0);
        CtTypeReference<?> tr3 = classThatDefinesANewTypeArgument.getMethodsByName("foo").get(0).getParameters().get(0).getReference().getType();
        org.junit.Assert.assertTrue((!(trExtends instanceof spoon.reflect.reference.CtTypeParameterReference)));
        org.junit.Assert.assertTrue((tr3 instanceof spoon.reflect.reference.CtTypeParameterReference));
        org.junit.Assert.assertEquals("File", trExtends.getSimpleName());
        org.junit.Assert.assertEquals(java.io.File.class, trExtends.getActualClass());
        org.junit.Assert.assertEquals("T", tr2.getSimpleName());
        org.junit.Assert.assertEquals("T", tr3.getSimpleName());
    }

    @org.junit.Test
    public void testTypeParameterDeclarer() throws java.lang.Exception {
        CtClass<?> classThatDefinesANewTypeArgument = spoon.testing.utils.ModelUtils.build("spoon.test.generics", "ClassThatDefinesANewTypeArgument");
        CtTypeParameter typeParam = classThatDefinesANewTypeArgument.getFormalCtTypeParameters().get(0);
        org.junit.Assert.assertEquals("T", classThatDefinesANewTypeArgument.getFormalCtTypeParameters().get(0).getSimpleName());
        org.junit.Assert.assertSame(classThatDefinesANewTypeArgument, typeParam.getTypeParameterDeclarer());
        spoon.reflect.reference.CtTypeParameterReference typeParamReference = typeParam.getReference();
        org.junit.Assert.assertSame(typeParam, typeParamReference.getDeclaration());
        spoon.reflect.declaration.CtMethod m = classThatDefinesANewTypeArgument.getFactory().createMethod();
        m.setParent(classThatDefinesANewTypeArgument);
        m.setType(typeParamReference);
        classThatDefinesANewTypeArgument.addMethod(m);
        org.junit.Assert.assertSame(typeParam, typeParamReference.getDeclaration());
        org.junit.Assert.assertSame(classThatDefinesANewTypeArgument, typeParamReference.getDeclaration().getParent());
        CtClass<?> c2 = classThatDefinesANewTypeArgument.clone();
        c2.addMethod(m);
        org.junit.Assert.assertSame(c2, typeParamReference.getDeclaration().getParent());
        typeParamReference.setSimpleName("R");
        c2.getFormalCtTypeParameters().get(0).setSimpleName("R");
        org.junit.Assert.assertSame(c2, typeParamReference.getDeclaration().getParent());
    }

    @org.junit.Test
    public void testGenericMethodCallWithExtend() throws java.lang.Exception {
        CtClass<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.generics", "GenericMethodCallWithExtend");
        spoon.reflect.declaration.CtMethod<?> meth = type.getMethodsByName("methode").get(0);
        org.junit.Assert.assertEquals("E extends java.lang.Enum<E>", meth.getFormalCtTypeParameters().get(0).toString());
    }

    @org.junit.Test
    public void testBugCommonCollection() throws java.lang.Exception {
        try {
            CtClass<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.generics", "BugCollection");
            spoon.reflect.declaration.CtField<?> INSTANCE = type.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtField.class, "INSTANCE")).get(0);
            org.junit.Assert.assertEquals("public static final spoon.test.generics.ACLass<?> INSTANCE = new spoon.test.generics.ACLass();", INSTANCE.toString());
            spoon.reflect.declaration.CtField<?> INSTANCE2 = type.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtField.class, "INSTANCE2")).get(0);
            INSTANCE2.setAnnotations(new java.util.ArrayList<spoon.reflect.declaration.CtAnnotation<?>>());
            org.junit.Assert.assertEquals("public static final spoon.test.generics.ACLass<?> INSTANCE2 = new spoon.test.generics.ACLass();", INSTANCE2.toString());
            CtClass<?> ComparableComparator = type.getPackage().getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(CtClass.class, "ComparableComparator")).get(0);
            org.junit.Assert.assertTrue(ComparableComparator.toString().startsWith("class ComparableComparator<E extends java.lang.Comparable<? super E>>"));
            spoon.reflect.declaration.CtField<?> x = type.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtField.class, "x")).get(0);
            CtTypeReference<?> ref = x.getType();
            spoon.reflect.visitor.DefaultJavaPrettyPrinter pp = new spoon.reflect.visitor.DefaultJavaPrettyPrinter(new spoon.support.StandardEnvironment());
            org.junit.Assert.assertEquals("java.util.Map$Entry", ref.getQualifiedName());
            org.junit.Assert.assertEquals("java.util.Map.Entry", ref.toString());
            org.junit.Assert.assertEquals(java.util.Map.class, ref.getDeclaringType().getActualClass());
            pp.visitCtTypeReference(ref);
            org.junit.Assert.assertEquals("java.util.Map.Entry", pp.getResult().toString());
            spoon.reflect.declaration.CtField<?> y = type.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtField.class, "y")).get(0);
            org.junit.Assert.assertEquals("java.util.Map.Entry<?, ?> y;", y.toString());
            spoon.reflect.declaration.CtField<?> z = type.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtField.class, "z")).get(0);
            org.junit.Assert.assertEquals("java.util.Map.Entry<java.lang.String, java.lang.Integer> z;", z.toString());
            spoon.reflect.code.CtLocalVariable<?> lx = type.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.code.CtLocalVariable.class, "lx")).get(0);
            org.junit.Assert.assertEquals("java.util.Map.Entry lx", lx.toString());
            spoon.reflect.code.CtLocalVariable<?> ly = type.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.code.CtLocalVariable.class, "ly")).get(0);
            org.junit.Assert.assertEquals("java.util.Map.Entry<?, ?> ly", ly.toString());
            spoon.reflect.code.CtLocalVariable<?> lz = type.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.code.CtLocalVariable.class, "lz")).get(0);
            org.junit.Assert.assertEquals("java.util.Map.Entry<java.lang.String, java.lang.Integer> lz", lz.toString());
            spoon.reflect.code.CtLocalVariable<?> it = type.getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.code.CtLocalVariable.class, "it")).get(0);
            org.junit.Assert.assertEquals("java.util.Iterator<java.util.Map.Entry<?, ?>> it", it.toString());
        } catch (java.lang.Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @org.junit.Test
    public void testInstanceOfMapEntryGeneric() throws java.lang.Exception {
        CtClass<?> type = spoon.testing.utils.ModelUtils.build("spoon.test.generics", "InstanceOfMapEntryGeneric");
        spoon.reflect.declaration.CtMethod<?> meth = type.getMethodsByName("methode").get(0);
        spoon.reflect.code.CtBinaryOperator<?> instOf = ((spoon.reflect.code.CtBinaryOperator<?>) (((spoon.reflect.code.CtLocalVariable<?>) (meth.getBody().getStatement(0))).getDefaultExpression()));
        org.junit.Assert.assertEquals(spoon.reflect.code.BinaryOperatorKind.INSTANCEOF, instOf.getKind());
        org.junit.Assert.assertEquals("o instanceof java.util.Map.Entry<?, ?>", instOf.toString());
    }

    @org.junit.Test
    public void testAccessToGenerics() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        Factory factory = spoon.createFactory();
        SpoonModelBuilder compiler = spoon.createCompiler(factory, SpoonResourceHelper.resources("./src/test/java/spoon/test/generics/Foo.java", "./src/test/java/spoon/test/generics/Bar.java"));
        compiler.build();
        CtClass<?> foo = ((CtClass<?>) (factory.Type().get(spoon.test.generics.Foo.class)));
        CtInterface<?> bar = ((CtInterface<?>) (factory.Type().get(spoon.test.generics.Bar.class)));
        final CtNewClass<?> newAnonymousBar = foo.getElements(new spoon.reflect.visitor.filter.AbstractFilter<CtNewClass<?>>(CtNewClass.class) {
            @java.lang.Override
            public boolean matches(CtNewClass<?> element) {
                return ((element.getAnonymousClass()) != null) && (element.getAnonymousClass().isAnonymous());
            }
        }).get(0);
        final List<CtTypeParameter> barTypeParamGenerics = bar.getFormalCtTypeParameters();
        final CtTypeReference<?> anonymousBar = newAnonymousBar.getType();
        org.junit.Assert.assertEquals("Name of the first generic parameter in Bar interface must to be I.", "I", barTypeParamGenerics.get(0).getSimpleName());
        org.junit.Assert.assertEquals("Name of the first generic parameter in Bar usage must to be K.", "K", anonymousBar.getActualTypeArguments().get(0).getSimpleName());
        org.junit.Assert.assertEquals("Name of the second generic parameter in Bar interface must to be O.", "O", barTypeParamGenerics.get(1).getSimpleName());
        org.junit.Assert.assertEquals("Name of the second generic parameter in Bar usage must to be V.", "V", anonymousBar.getActualTypeArguments().get(1).getSimpleName());
    }

    @org.junit.Test
    public void testConstructorCallGenerics() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.run(new java.lang.String[]{ "-i", "./src/test/java/spoon/test/generics/testclasses/", "-o", "./target/spooned/" });
        final CtClass<?> aTacos = launcher.getFactory().Class().get(spoon.test.generics.testclasses.Tacos.class);
        org.junit.Assert.assertEquals(2, aTacos.getFormalCtTypeParameters().size());
        final CtTypeReference interfaces = aTacos.getSuperInterfaces().toArray(new CtTypeReference[0])[0];
        org.junit.Assert.assertEquals(1, interfaces.getActualTypeArguments().size());
        final spoon.reflect.declaration.CtMethod<?> m = aTacos.getMethodsByName("m").get(0);
        final spoon.reflect.declaration.CtElement local1 = m.getBody().getStatement(0).getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtLocalVariable.class)).get(0);
        final CtTypeReference<java.lang.Object> leftSideLocal1 = ((CtTypeReference<java.lang.Object>) (local1.getElements(new spoon.reflect.visitor.filter.ReferenceTypeFilter<>(CtTypeReference.class)).get(0)));
        final spoon.reflect.code.CtConstructorCall<java.lang.Object> rightSideLocal1 = ((spoon.reflect.code.CtConstructorCall<java.lang.Object>) (local1.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtConstructorCall.class)).get(0)));
        org.junit.Assert.assertEquals(1, leftSideLocal1.getActualTypeArguments().size());
        org.junit.Assert.assertEquals(1, rightSideLocal1.getType().getActualTypeArguments().size());
        org.junit.Assert.assertEquals("java.util.List<java.lang.String> l = new java.util.ArrayList<>()", local1.toString());
        final spoon.reflect.declaration.CtElement local2 = m.getBody().getStatement(1).getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtLocalVariable.class)).get(0);
        final CtTypeReference<java.lang.Object> leftSideLocal2 = ((CtTypeReference<java.lang.Object>) (local2.getElements(new spoon.reflect.visitor.filter.ReferenceTypeFilter<>(CtTypeReference.class)).get(0)));
        org.junit.Assert.assertEquals(0, leftSideLocal2.getActualTypeArguments().size());
        org.junit.Assert.assertEquals("java.util.List l2", local2.toString());
        final spoon.reflect.declaration.CtElement local3 = m.getBody().getStatement(2).getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtLocalVariable.class)).get(0);
        final CtTypeReference<java.lang.Object> leftSideLocal3 = ((CtTypeReference<java.lang.Object>) (local3.getElements(new spoon.reflect.visitor.filter.ReferenceTypeFilter<>(CtTypeReference.class)).get(0)));
        final spoon.reflect.code.CtConstructorCall<java.lang.Object> rightSideLocal3 = ((spoon.reflect.code.CtConstructorCall<java.lang.Object>) (local3.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtConstructorCall.class)).get(0)));
        org.junit.Assert.assertEquals(2, leftSideLocal3.getActualTypeArguments().size());
        org.junit.Assert.assertEquals(2, rightSideLocal3.getType().getActualTypeArguments().size());
        org.junit.Assert.assertEquals("spoon.test.generics.testclasses.IBurritos<?, ?> burritos = new Burritos<>()", local3.toString());
        final spoon.reflect.declaration.CtElement local4 = m.getBody().getStatement(3).getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtLocalVariable.class)).get(0);
        final CtTypeReference<java.lang.Object> leftSideLocal4 = ((CtTypeReference<java.lang.Object>) (local4.getElements(new spoon.reflect.visitor.filter.ReferenceTypeFilter<>(CtTypeReference.class)).get(0)));
        final spoon.reflect.code.CtConstructorCall<java.lang.Object> rightSideLocal4 = ((spoon.reflect.code.CtConstructorCall<java.lang.Object>) (local4.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtConstructorCall.class)).get(0)));
        org.junit.Assert.assertEquals(1, leftSideLocal4.getActualTypeArguments().size());
        org.junit.Assert.assertEquals(1, rightSideLocal4.getType().getActualTypeArguments().size());
        org.junit.Assert.assertEquals("java.util.List<?> l3 = new java.util.ArrayList<java.lang.Object>()", local4.toString());
        final spoon.reflect.code.CtConstructorCall constructorCall1 = ((spoon.reflect.code.CtConstructorCall) (m.getBody().getStatement(4).getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtConstructorCall.class)).get(0)));
        org.junit.Assert.assertEquals(1, constructorCall1.getActualTypeArguments().size());
        org.junit.Assert.assertEquals(2, constructorCall1.getType().getActualTypeArguments().size());
        org.junit.Assert.assertEquals("new <java.lang.Integer>spoon.test.generics.testclasses.Tacos<java.lang.Object, java.lang.String>()", constructorCall1.toString());
        final spoon.reflect.code.CtConstructorCall constructorCall2 = ((spoon.reflect.code.CtConstructorCall) (m.getBody().getStatement(5).getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtConstructorCall.class)).get(0)));
        org.junit.Assert.assertEquals(0, constructorCall2.getActualTypeArguments().size());
        org.junit.Assert.assertEquals(2, constructorCall2.getType().getActualTypeArguments().size());
        org.junit.Assert.assertEquals("new spoon.test.generics.testclasses.Tacos<>()", constructorCall2.toString());
        spoon.testing.utils.ModelUtils.canBeBuilt("./target/spooned/spoon/test/generics/testclasses/", 8);
    }

    @org.junit.Test
    public void testInvocationGenerics() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.run(new java.lang.String[]{ "-i", "./src/test/java/spoon/test/generics/testclasses/", "-o", "./target/spooned/" });
        final CtClass<?> aTacos = launcher.getFactory().Class().get(spoon.test.generics.testclasses.Tacos.class);
        final spoon.reflect.declaration.CtConstructor<?> defaultConstructor = aTacos.getConstructor();
        final spoon.reflect.code.CtInvocation<?> explicitConstructorCall = ((spoon.reflect.code.CtInvocation<?>) (defaultConstructor.getBody().getStatement(0).getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtInvocation.class)).get(0)));
        org.junit.Assert.assertEquals(1, explicitConstructorCall.getExecutable().getActualTypeArguments().size());
        org.junit.Assert.assertEquals("<java.lang.String>this(1)", explicitConstructorCall.toString());
        final spoon.reflect.declaration.CtMethod<?> m = aTacos.getMethodsByName("m2").get(0);
        final spoon.reflect.code.CtInvocation invocation1 = m.getBody().getStatement(0).getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtInvocation.class)).get(0);
        org.junit.Assert.assertEquals(1, invocation1.getExecutable().getActualTypeArguments().size());
        org.junit.Assert.assertEquals("this.<java.lang.String>makeTacos(null)", invocation1.toString());
        final spoon.reflect.code.CtInvocation invocation2 = m.getBody().getStatement(1).getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtInvocation.class)).get(0);
        org.junit.Assert.assertEquals(0, invocation2.getExecutable().getActualTypeArguments().size());
        org.junit.Assert.assertEquals("this.makeTacos(null)", invocation2.toString());
        spoon.testing.utils.ModelUtils.canBeBuilt("./target/spooned/spoon/test/generics/testclasses/", 8);
    }

    @org.junit.Test
    public void testNewClassGenerics() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.run(new java.lang.String[]{ "-i", "./src/test/java/spoon/test/generics/testclasses/", "-o", "./target/spooned/" });
        final CtClass<?> aTacos = launcher.getFactory().Class().get(spoon.test.generics.testclasses.Tacos.class);
        final spoon.reflect.declaration.CtMethod<?> m = aTacos.getMethodsByName("m3").get(0);
        final CtNewClass newClass1 = m.getBody().getStatement(0).getElements(new spoon.reflect.visitor.filter.TypeFilter<>(CtNewClass.class)).get(0);
        org.junit.Assert.assertEquals(0, newClass1.getActualTypeArguments().size());
        org.junit.Assert.assertEquals(2, newClass1.getType().getActualTypeArguments().size());
        org.junit.Assert.assertEquals("new javax.lang.model.util.SimpleTypeVisitor7<spoon.test.generics.testclasses.Tacos, java.lang.Void>() {}", newClass1.toString());
        final CtNewClass newClass2 = m.getBody().getStatement(1).getElements(new spoon.reflect.visitor.filter.TypeFilter<>(CtNewClass.class)).get(0);
        org.junit.Assert.assertEquals(0, newClass2.getActualTypeArguments().size());
        org.junit.Assert.assertEquals(2, newClass2.getType().getActualTypeArguments().size());
        org.junit.Assert.assertEquals("new javax.lang.model.util.SimpleTypeVisitor7<spoon.test.generics.testclasses.Tacos, java.lang.Void>() {}", newClass2.toString());
        spoon.testing.utils.ModelUtils.canBeBuilt("./target/spooned/spoon/test/generics/testclasses/", 8);
    }

    @org.junit.Test
    public void testMethodsWithGenericsWhoExtendsObject() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.run(new java.lang.String[]{ "-i", "./src/test/java/spoon/test/generics/testclasses/", "-o", "./target/spooned/" });
        final CtClass<?> aTacos = launcher.getFactory().Class().get(spoon.test.generics.testclasses.Tacos.class);
        final spoon.reflect.declaration.CtMethod<?> m = aTacos.getMethodsByName("m4").get(0);
        final spoon.reflect.code.CtInvocation<?> invocation1 = m.getBody().getStatement(0).getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtInvocation.class)).get(0);
        org.junit.Assert.assertEquals(2, invocation1.getExecutable().getActualTypeArguments().size());
        org.junit.Assert.assertEquals("spoon.test.generics.testclasses.Tacos.<V, C>makeTacos()", invocation1.toString());
        final spoon.reflect.code.CtInvocation<?> invocation2 = m.getBody().getStatement(1).getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtInvocation.class)).get(0);
        org.junit.Assert.assertEquals(0, invocation2.getExecutable().getActualTypeArguments().size());
        org.junit.Assert.assertEquals("spoon.test.generics.testclasses.Tacos.makeTacos()", invocation2.toString());
    }

    @org.junit.Test
    public void testName() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.run(new java.lang.String[]{ "-i", "./src/test/java/spoon/test/generics/testclasses/", "-o", "./target/spooned/" });
        final CtClass<?> aFactory = launcher.getFactory().Class().get(spoon.test.generics.testclasses.Tacos.BeerFactory.class);
        final spoon.reflect.declaration.CtMethod<?> m = aFactory.getMethodsByName("newBeer").get(0);
        final spoon.reflect.code.CtConstructorCall constructorCall1 = m.getBody().getStatement(0).getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtConstructorCall.class)).get(0);
        org.junit.Assert.assertEquals("new Beer()", constructorCall1.toString());
    }

    @org.junit.Test
    public void testGenericWithExtendsInDeclaration() throws java.lang.Exception {
        final Factory build = spoon.testing.utils.ModelUtils.build(spoon.test.generics.testclasses.Panini.class);
        final spoon.reflect.declaration.CtType<spoon.test.generics.testclasses.Panini> panini = build.Type().get(spoon.test.generics.testclasses.Panini.class);
        final spoon.reflect.declaration.CtMethod<?> apply = panini.getMethodsByName("apply").get(0);
        org.junit.Assert.assertEquals(1, apply.getType().getActualTypeArguments().size());
        org.junit.Assert.assertEquals("? super java.lang.Object", apply.getType().getActualTypeArguments().get(0).toString());
        org.junit.Assert.assertEquals(1, apply.getParameters().get(0).getType().getActualTypeArguments().size());
        org.junit.Assert.assertEquals("? extends java.lang.Long", apply.getParameters().get(0).getType().getActualTypeArguments().get(0).toString());
    }

    @org.junit.Test
    public void testGenericInField() throws java.lang.Exception {
        final Factory build = spoon.testing.utils.ModelUtils.build(spoon.test.generics.testclasses.Spaghetti.class);
        final spoon.reflect.declaration.CtType<spoon.test.generics.testclasses.Panini> aSpaghetti = build.Type().get(spoon.test.generics.testclasses.Spaghetti.class);
        org.junit.Assert.assertTrue(aSpaghetti.toString().contains("private spoon.test.generics.testclasses.Spaghetti<B>.Tester tester;"));
        org.junit.Assert.assertTrue(aSpaghetti.toString().contains("private spoon.test.generics.testclasses.Spaghetti<B>.Tester tester1;"));
        org.junit.Assert.assertTrue(aSpaghetti.toString().contains("private spoon.test.generics.testclasses.Spaghetti<B>.That<java.lang.String, java.lang.String> field;"));
        org.junit.Assert.assertTrue(aSpaghetti.toString().contains("private spoon.test.generics.testclasses.Spaghetti<java.lang.String>.That<java.lang.String, java.lang.String> field1;"));
        org.junit.Assert.assertTrue(aSpaghetti.toString().contains("private spoon.test.generics.testclasses.Spaghetti<java.lang.Number>.That<java.lang.String, java.lang.String> field2;"));
    }

    @org.junit.Test
    public void testGenericsInQualifiedNameInConstructorCall() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.run(new java.lang.String[]{ "-i", "./src/test/java/spoon/test/generics/testclasses/", "-o", "./target/spooned/" });
        final CtClass<spoon.test.generics.testclasses.Tacos> aTacos = launcher.getFactory().Class().get(spoon.test.generics.testclasses.Tacos.class);
        final spoon.reflect.declaration.CtType<?> burritos = aTacos.getNestedType("Burritos");
        spoon.support.util.SortedList<spoon.reflect.code.CtConstructorCall> elements = new spoon.support.util.SortedList<spoon.reflect.code.CtConstructorCall>(new spoon.support.comparator.CtLineElementComparator());
        elements.addAll(burritos.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtConstructorCall.class)));
        org.junit.Assert.assertEquals(3, elements.size());
        org.junit.Assert.assertEquals(0, elements.get(1).getExecutable().getType().getActualTypeArguments().size());
        org.junit.Assert.assertNotNull(elements.get(1).getType().getDeclaringType());
        org.junit.Assert.assertEquals("new Pozole()", elements.get(1).toString());
        org.junit.Assert.assertEquals(2, elements.get(0).getExecutable().getType().getActualTypeArguments().size());
        org.junit.Assert.assertNotNull(elements.get(0).getType().getDeclaringType());
        org.junit.Assert.assertEquals("new Burritos<K, V>()", elements.get(0).toString());
        org.junit.Assert.assertEquals(2, elements.get(2).getExecutable().getType().getActualTypeArguments().size());
        org.junit.Assert.assertNotNull(elements.get(2).getType().getDeclaringType());
        org.junit.Assert.assertEquals("new Burritos<K, V>() {}", elements.get(2).toString());
    }

    @org.junit.Test
    public void testGenericsOnLocalType() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<spoon.test.generics.testclasses.Mole> aMole = spoon.testing.utils.ModelUtils.buildClass(spoon.test.generics.testclasses.Mole.class);
        final spoon.reflect.declaration.CtMethod<java.lang.Object> cook = aMole.getMethod("cook");
        final spoon.reflect.code.CtConstructorCall<?> newCook = cook.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtConstructorCall.class)).get(0);
        org.junit.Assert.assertEquals(1, newCook.getType().getActualTypeArguments().size());
        org.junit.Assert.assertEquals("new Cook<java.lang.String>()", newCook.toString());
    }

    @org.junit.Test
    public void testGenericsInConstructorCall() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<spoon.test.generics.testclasses.Mole> aMole = spoon.testing.utils.ModelUtils.buildClass(spoon.test.generics.testclasses.Mole.class);
        final spoon.reflect.declaration.CtMethod<java.lang.Object> prepare = aMole.getMethod("prepare");
        final spoon.reflect.code.CtConstructorCall<?> newPrepare = prepare.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtConstructorCall.class)).get(0);
        org.junit.Assert.assertEquals(1, newPrepare.getActualTypeArguments().size());
        org.junit.Assert.assertEquals("java.lang.Integer", newPrepare.getActualTypeArguments().get(0).toString());
        org.junit.Assert.assertEquals(1, newPrepare.getType().getActualTypeArguments().size());
        org.junit.Assert.assertEquals("java.lang.String", newPrepare.getType().getActualTypeArguments().get(0).toString());
    }

    @org.junit.Test
    public void testWildcard() throws java.lang.Exception {
        List<spoon.reflect.reference.CtWildcardReference> wildcardReferences = spoon.testing.utils.ModelUtils.buildClass(spoon.test.generics.testclasses.Paella.class).getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.reference.CtWildcardReference>(spoon.reflect.reference.CtWildcardReference.class));
        org.junit.Assert.assertEquals(4, wildcardReferences.size());
    }

    @org.junit.Test
    public void testDeclarationOfTypeParameterReference() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<spoon.test.generics.testclasses.Tacos> aTacos = spoon.testing.utils.ModelUtils.buildNoClasspath(spoon.test.generics.testclasses.Tacos.class).Type().get(spoon.test.generics.testclasses.Tacos.class);
        for (spoon.reflect.reference.CtTypeParameterReference parameterReference : aTacos.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.reference.CtTypeParameterReference>(spoon.reflect.reference.CtTypeParameterReference.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.reference.CtTypeParameterReference element) {
                return (!(element instanceof spoon.reflect.reference.CtWildcardReference)) && (!((element.getParent()) instanceof spoon.reflect.reference.CtReference));
            }
        })) {
            org.junit.Assert.assertNotNull(parameterReference.getDeclaration());
        }
    }

    @org.junit.Test
    public void testIsGenericsMethod() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<spoon.test.generics.testclasses.Tacos> aTacos = spoon.testing.utils.ModelUtils.buildNoClasspath(spoon.test.generics.testclasses.Tacos.class).Type().get(spoon.test.generics.testclasses.Tacos.class);
        CtTypeParameter typeParameter = aTacos.getFormalCtTypeParameters().get(0);
        org.junit.Assert.assertTrue(typeParameter.isGenerics());
        org.junit.Assert.assertTrue(typeParameter.getReference().isGenerics());
        CtTypeReference ctTypeReference = aTacos.getSuperInterfaces().toArray(new CtTypeReference[aTacos.getSuperInterfaces().size()])[0];
        org.junit.Assert.assertFalse(aTacos.isGenerics());
        org.junit.Assert.assertEquals("spoon.test.generics.testclasses.ITacos<V>", ctTypeReference.toString());
        org.junit.Assert.assertTrue(ctTypeReference.isGenerics());
    }

    @org.junit.Test
    public void testTypeParameterReferenceAsActualTypeArgument() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<spoon.test.generics.testclasses.Tacos> aTacos = spoon.testing.utils.ModelUtils.buildNoClasspath(spoon.test.generics.ClassThatDefinesANewTypeArgument.class).Type().get(spoon.test.generics.ClassThatDefinesANewTypeArgument.class);
        CtTypeReference<?> typeRef = aTacos.getReference();
        org.junit.Assert.assertSame(aTacos, typeRef.getDeclaration());
        CtTypeParameter typeParam = aTacos.getFormalCtTypeParameters().get(0);
        spoon.reflect.reference.CtTypeParameterReference typeParamRef = typeParam.getReference();
        org.junit.Assert.assertSame(typeParam, typeParamRef.getDeclaration());
        org.junit.Assert.assertEquals("spoon.test.generics.ClassThatDefinesANewTypeArgument", typeRef.toString());
        typeRef.addActualTypeArgument(typeParamRef);
        org.junit.Assert.assertEquals("spoon.test.generics.ClassThatDefinesANewTypeArgument<T>", typeRef.toString());
        org.junit.Assert.assertSame(aTacos, typeRef.getDeclaration());
        org.junit.Assert.assertSame(typeParamRef, typeRef.getActualTypeArguments().get(0));
        org.junit.Assert.assertSame(typeRef, typeParamRef.getParent());
        org.junit.Assert.assertEquals(typeParam, typeParamRef.getDeclaration());
        org.junit.Assert.assertEquals(typeParam, typeParamRef.getTypeParameterDeclaration());
        typeParamRef.setSimpleName("Y");
        org.junit.Assert.assertEquals(typeParam, typeParamRef.getTypeParameterDeclaration());
    }

    @org.junit.Test
    public void testGenericTypeReference() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<spoon.test.generics.testclasses.Tacos> aTacos = spoon.testing.utils.ModelUtils.buildNoClasspath(spoon.test.generics.testclasses.Tacos.class).Type().get(spoon.test.generics.testclasses.Tacos.class);
        CtTypeReference<?> genericTypeRef = aTacos.getFactory().Type().createReference(aTacos, true);
        org.junit.Assert.assertTrue(((genericTypeRef.getActualTypeArguments().size()) > 0));
        org.junit.Assert.assertEquals(aTacos.getFormalCtTypeParameters().size(), genericTypeRef.getActualTypeArguments().size());
        for (int i = 0; i < (aTacos.getFormalCtTypeParameters().size()); i++) {
            org.junit.Assert.assertSame((("TypeParameter reference idx=" + i) + " is different"), aTacos.getFormalCtTypeParameters().get(i), genericTypeRef.getActualTypeArguments().get(i).getTypeParameterDeclaration());
            org.junit.Assert.assertSame(aTacos.getFormalCtTypeParameters().get(i), genericTypeRef.getActualTypeArguments().get(i).getTypeParameterDeclaration());
        }
    }

    @org.junit.Test
    public void testisGeneric() throws java.lang.Exception {
        Factory factory = spoon.testing.utils.ModelUtils.build(new java.io.File("src/test/java/spoon/test/generics/testclasses"));
        CtTypeReference<?> var1Ref = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "var1")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(true, var1Ref.isGenerics());
        CtTypeReference<?> sRef = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "s")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(true, sRef.isGenerics());
        CtTypeReference<?> notificationRef = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "notification")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(false, notificationRef.isGenerics());
        CtTypeReference<?> managerRef = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "manager")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(false, managerRef.isGenerics());
        CtTypeReference<?> subjectRef = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "subject")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(false, subjectRef.isGenerics());
        CtTypeReference<?> parentRef = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "parent")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(true, parentRef.isGenerics());
        CtTypeReference<?> actionRef = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "action")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(false, actionRef.isGenerics());
        CtTypeReference<?> trucRef = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "truc")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(false, trucRef.isGenerics());
        CtTypeReference<?> consumerRef = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "consumer")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(false, consumerRef.isGenerics());
        CtTypeReference<?> sectionRef = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "section")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(true, sectionRef.isGenerics());
        CtTypeReference<?> paramARef = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "paramA")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(true, paramARef.isGenerics());
        CtTypeReference<?> paramBRef = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "paramB")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(false, paramBRef.isGenerics());
        CtTypeReference<?> paramCRef = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "paramC")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(true, paramCRef.isGenerics());
        CtTypeReference<?> cookRef = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "cook")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(true, cookRef.isGenerics());
        CtTypeReference<?> clRef = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "cl")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(false, clRef.isGenerics());
        CtTypeReference<?> disgustRef = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "disgust")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(false, disgustRef.isGenerics());
        CtTypeReference<?> paramRef = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "param")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(true, paramRef.isGenerics());
        CtTypeReference<?> targetTypeRef = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "targetType")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(false, targetTypeRef.isGenerics());
        CtTypeReference<?> somethingRef = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "something")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(false, somethingRef.isGenerics());
        CtTypeReference<?> iRef = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "i")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(false, iRef.isGenerics());
        CtTypeReference<?> biduleRef = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "bidule")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(true, biduleRef.isGenerics());
        CtTypeReference<?> aClassRef = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "aClass")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(false, aClassRef.isGenerics());
        CtTypeReference<?> list2mRef = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "list2m")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(true, list2mRef.isGenerics());
        CtTypeReference<?> tRef = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "t")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(false, tRef.isGenerics());
        CtTypeReference<?> testerRef = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "tester")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(false, testerRef.isGenerics());
        CtTypeReference<?> tester1Ref = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "tester1")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(false, tester1Ref.isGenerics());
        CtTypeReference<?> fieldRef = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "field")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(false, fieldRef.isGenerics());
        CtTypeReference<?> field1Ref = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "field1")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(false, field1Ref.isGenerics());
        CtTypeReference<?> field2Ref = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "field2")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(false, field2Ref.isGenerics());
        CtTypeReference<?> burritosRef = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "burritos")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(false, burritosRef.isGenerics());
        CtTypeReference<?> nbTacosRef = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "nbTacos")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(false, nbTacosRef.isGenerics());
        CtTypeReference<?> lRef = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "l")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(false, lRef.isGenerics());
        CtTypeReference<?> l2Ref = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "l2")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(false, l2Ref.isGenerics());
        CtTypeReference<?> l3Ref = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "l3")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(false, l3Ref.isGenerics());
        CtTypeReference<?> anObjectRef = factory.getModel().filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter(spoon.reflect.declaration.CtVariable.class, "anObject")).first(spoon.reflect.declaration.CtVariable.class).getType();
        org.junit.Assert.assertEquals(true, anObjectRef.isGenerics());
    }

    @org.junit.Test
    public void testCtTypeReference_getSuperclass() throws java.lang.Exception {
        Factory factory = spoon.testing.utils.ModelUtils.build(new java.io.File("src/test/java/spoon/test/generics/testclasses"));
        CtClass<?> ctClassCelebrationLunch = factory.Class().get(spoon.test.generics.testclasses.CelebrationLunch.class);
        CtTypeReference<?> trWeddingLunch_Mole = ctClassCelebrationLunch.filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtNamedElement.class, "disgust")).map((spoon.reflect.declaration.CtTypedElement te) -> {
            return te.getType();
        }).first();
        org.junit.Assert.assertEquals("spoon.test.generics.testclasses.CelebrationLunch<java.lang.Integer, java.lang.Long, java.lang.Double>.WeddingLunch<spoon.test.generics.testclasses.Mole>", trWeddingLunch_Mole.toString());
        spoon.reflect.declaration.CtType<?> tWeddingLunch_X = trWeddingLunch_Mole.getDeclaration();
        CtTypeReference<?> trCelebrationLunch_Tacos_Paella_X = tWeddingLunch_X.getSuperclass();
        org.junit.Assert.assertEquals(("spoon.test.generics.testclasses.CelebrationLunch<" + ((("spoon.test.generics.testclasses.Tacos, " + "spoon.test.generics.testclasses.Paella, ") + "X") + ">")), trCelebrationLunch_Tacos_Paella_X.toString());
        org.junit.Assert.assertEquals(("spoon.test.generics.testclasses.CelebrationLunch<" + ((("spoon.test.generics.testclasses.Tacos, " + "spoon.test.generics.testclasses.Paella, ") + "X") + ">")), trWeddingLunch_Mole.getSuperclass().toString());
    }

    @org.junit.Test
    public void testTypeAdapted() throws java.lang.Exception {
        CtClass<?> ctModel = ((CtClass<?>) (spoon.testing.utils.ModelUtils.buildClass(spoon.test.ctType.testclasses.ErasureModelA.class)));
        CtTypeParameter tpA = ctModel.getFormalCtTypeParameters().get(0);
        CtTypeParameter tpB = ctModel.getFormalCtTypeParameters().get(1);
        CtTypeParameter tpC = ctModel.getFormalCtTypeParameters().get(2);
        CtTypeParameter tpD = ctModel.getFormalCtTypeParameters().get(3);
        CtClass<?> ctModelB = ctModel.filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter<>(CtClass.class, "ModelB")).first();
        spoon.support.visitor.ClassTypingContext sth = new spoon.support.visitor.ClassTypingContext(ctModelB);
        org.junit.Assert.assertEquals("A2", sth.adaptType(tpA).getQualifiedName());
        org.junit.Assert.assertEquals("B2", sth.adaptType(tpB).getQualifiedName());
        org.junit.Assert.assertEquals("C2", sth.adaptType(tpC).getQualifiedName());
        org.junit.Assert.assertEquals("D2", sth.adaptType(tpD).getQualifiedName());
        CtClass<?> ctModelC = ctModel.filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter<>(CtClass.class, "ModelC")).first();
        spoon.support.visitor.ClassTypingContext sthC = new spoon.support.visitor.ClassTypingContext(ctModelC);
        org.junit.Assert.assertEquals("java.lang.Integer", sthC.adaptType(tpA).getQualifiedName());
        org.junit.Assert.assertEquals("java.lang.RuntimeException", sthC.adaptType(tpB).getQualifiedName());
        org.junit.Assert.assertEquals("java.lang.IllegalArgumentException", sthC.adaptType(tpC).getQualifiedName());
        org.junit.Assert.assertEquals("java.util.List", sthC.adaptType(tpD).getQualifiedName());
    }

    @org.junit.Test
    public void testClassTypingContext() throws java.lang.Exception {
        Factory factory = spoon.testing.utils.ModelUtils.build(new java.io.File("src/test/java/spoon/test/generics/testclasses"));
        CtClass<?> ctClassCelebrationLunch = factory.Class().get(spoon.test.generics.testclasses.CelebrationLunch.class);
        CtTypeReference<?> typeReferenceOfDisgust = ctClassCelebrationLunch.filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtNamedElement.class, "disgust")).map((spoon.reflect.declaration.CtTypedElement te) -> {
            return te.getType();
        }).first();
        org.junit.Assert.assertEquals("spoon.test.generics.testclasses.CelebrationLunch<java.lang.Integer, java.lang.Long, java.lang.Double>.WeddingLunch<spoon.test.generics.testclasses.Mole>", typeReferenceOfDisgust.toString());
        spoon.reflect.declaration.CtMethod<?> tWeddingLunch_eatMe = typeReferenceOfDisgust.getDeclaration().filterChildren((spoon.reflect.declaration.CtNamedElement e) -> "eatMe".equals(e.getSimpleName())).first();
        CtClass<?> ctClassLunch = factory.Class().get(spoon.test.generics.testclasses.Lunch.class);
        spoon.reflect.declaration.CtMethod<?> ctClassLunch_eatMe = ctClassLunch.filterChildren((spoon.reflect.declaration.CtNamedElement e) -> "eatMe".equals(e.getSimpleName())).first();
        CtTypeReference<?> ctWeddingLunch_X = tWeddingLunch_eatMe.getParameters().get(0).getType();
        org.junit.Assert.assertEquals("X", ctWeddingLunch_X.getSimpleName());
        CtTypeReference<?> ctClassLunch_A = ctClassLunch_eatMe.getParameters().get(0).getType();
        org.junit.Assert.assertEquals("A", ctClassLunch_A.getSimpleName());
        spoon.support.visitor.ClassTypingContext typingContextOfDisgust = new spoon.support.visitor.ClassTypingContext(typeReferenceOfDisgust);
        org.junit.Assert.assertSame(typeReferenceOfDisgust.getTypeDeclaration(), typingContextOfDisgust.getAdaptationScope());
        org.junit.Assert.assertEquals("spoon.test.generics.testclasses.Mole", typingContextOfDisgust.adaptType(ctWeddingLunch_X).getQualifiedName());
        org.junit.Assert.assertEquals("spoon.test.generics.testclasses.Mole", typingContextOfDisgust.adaptType(ctClassLunch_A).getQualifiedName());
        org.junit.Assert.assertEquals("java.lang.Double", typingContextOfDisgust.getEnclosingGenericTypeAdapter().adaptType(ctClassLunch_A).getQualifiedName());
        spoon.support.visitor.ClassTypingContext sthOftWeddingLunch_X = new spoon.support.visitor.ClassTypingContext(typeReferenceOfDisgust.getDeclaration());
        org.junit.Assert.assertSame(typeReferenceOfDisgust.getDeclaration(), sthOftWeddingLunch_X.getAdaptationScope());
        org.junit.Assert.assertEquals("X", sthOftWeddingLunch_X.adaptType(ctWeddingLunch_X).getQualifiedName());
        org.junit.Assert.assertEquals("X", sthOftWeddingLunch_X.adaptType(ctClassLunch_A).getQualifiedName());
        org.junit.Assert.assertEquals("M", sthOftWeddingLunch_X.getEnclosingGenericTypeAdapter().adaptType(ctClassLunch_A).getQualifiedName());
    }

    @org.junit.Test
    public void testRecursiveTypeAdapting() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> classOrange = spoon.testing.utils.ModelUtils.buildClass(spoon.test.generics.testclasses.Orange.class);
        CtClass<?> classA = classOrange.getNestedType("A");
        CtTypeParameter typeParamO = classA.getFormalCtTypeParameters().get(0);
        CtTypeParameter typeParamM = classA.getFormalCtTypeParameters().get(1);
        org.junit.Assert.assertEquals("O", typeParamO.getQualifiedName());
        org.junit.Assert.assertEquals("M", typeParamM.getQualifiedName());
        org.junit.Assert.assertEquals("K", typeParamO.getSuperclass().getQualifiedName());
        org.junit.Assert.assertEquals("O", typeParamM.getSuperclass().getQualifiedName());
        org.junit.Assert.assertEquals("K", typeParamM.getSuperclass().getSuperclass().getQualifiedName());
        CtClass<?> classB = classOrange.getNestedType("B");
        CtTypeParameter typeParamN = classB.getFormalCtTypeParameters().get(0);
        CtTypeParameter typeParamP = classB.getFormalCtTypeParameters().get(1);
        org.junit.Assert.assertEquals("N", typeParamN.getQualifiedName());
        org.junit.Assert.assertEquals("P", typeParamP.getQualifiedName());
        spoon.support.visitor.ClassTypingContext ctcB = new spoon.support.visitor.ClassTypingContext(classB);
        org.junit.Assert.assertEquals("N", ctcB.adaptType(typeParamO).getQualifiedName());
        org.junit.Assert.assertEquals("P", ctcB.adaptType(typeParamM).getQualifiedName());
        org.junit.Assert.assertEquals("K", ctcB.adaptType(typeParamO).getSuperclass().getQualifiedName());
        org.junit.Assert.assertEquals("N", ctcB.adaptType(typeParamM).getSuperclass().getQualifiedName());
        org.junit.Assert.assertEquals("K", ctcB.adaptType(typeParamM).getSuperclass().getSuperclass().getQualifiedName());
        CtTypeReference<?> typeRef_list2m = classA.getField("list2m").getType();
        org.junit.Assert.assertEquals("java.util.List<java.util.List<M>>", typeRef_list2m.toString());
        org.junit.Assert.assertEquals("java.util.List<java.util.List<P>>", ctcB.adaptType(typeRef_list2m).toString());
        CtTypeReference<?> typeRef_ListQextendsM = classA.getMethodsByName("method").get(0).getParameters().get(0).getType();
        org.junit.Assert.assertEquals("java.util.List<? extends M>", typeRef_ListQextendsM.toString());
        org.junit.Assert.assertEquals("java.util.List<? extends P>", ctcB.adaptType(typeRef_ListQextendsM).toString());
    }

    @org.junit.Test
    public void testMethodTypingContext() throws java.lang.Exception {
        Factory factory = spoon.testing.utils.ModelUtils.build(new java.io.File("src/test/java/spoon/test/generics/testclasses"));
        CtClass<?> ctClassWeddingLunch = factory.Class().get(spoon.test.generics.testclasses.CelebrationLunch.WeddingLunch.class);
        spoon.reflect.declaration.CtMethod<?> trWeddingLunch_eatMe = ctClassWeddingLunch.filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "eatMe")).first();
        spoon.support.visitor.MethodTypingContext methodSTH = new spoon.support.visitor.MethodTypingContext().setMethod(trWeddingLunch_eatMe);
        org.junit.Assert.assertSame(trWeddingLunch_eatMe, methodSTH.getAdaptationScope());
        CtClass<?> ctClassLunch = factory.Class().get(spoon.test.generics.testclasses.Lunch.class);
        spoon.reflect.declaration.CtMethod<?> trLunch_eatMe = ctClassLunch.filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "eatMe")).first();
        spoon.reflect.code.CtInvocation<?> invokeReserve = factory.Class().get(spoon.test.generics.testclasses.CelebrationLunch.class).filterChildren(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtInvocation.class)).select((spoon.reflect.code.CtInvocation i) -> "reserve".equals(i.getExecutable().getSimpleName())).first();
        spoon.support.visitor.MethodTypingContext methodReserveTC = new spoon.support.visitor.MethodTypingContext().setInvocation(invokeReserve);
        org.junit.Assert.assertSame(invokeReserve.getExecutable().getDeclaration(), methodReserveTC.getAdaptationScope());
        spoon.reflect.declaration.CtMethod<?> methodReserve = ((spoon.reflect.declaration.CtMethod<?>) (invokeReserve.getExecutable().getDeclaration()));
        CtTypeParameter methodReserve_S = methodReserve.getFormalCtTypeParameters().get(0);
        org.junit.Assert.assertEquals("S", methodReserve_S.getSimpleName());
        org.junit.Assert.assertEquals("spoon.test.generics.testclasses.Tacos", methodReserveTC.adaptType(methodReserve_S).getQualifiedName());
        CtClass classSection = ((CtClass) (methodReserve.getDeclaringType()));
        org.junit.Assert.assertEquals("spoon.test.generics.testclasses.CelebrationLunch$WeddingLunch$Section", classSection.getQualifiedName());
        CtTypeParameter classSection_Y = classSection.getFormalCtTypeParameters().get(0);
        org.junit.Assert.assertEquals("Y", classSection_Y.getSimpleName());
        org.junit.Assert.assertEquals("spoon.test.generics.testclasses.Paella", methodReserveTC.adaptType(classSection_Y).getQualifiedName());
        CtClass classWeddingLunch = ((CtClass) (classSection.getDeclaringType()));
        org.junit.Assert.assertEquals("spoon.test.generics.testclasses.CelebrationLunch$WeddingLunch", classWeddingLunch.getQualifiedName());
        CtTypeParameter classWeddingLunch_X = classWeddingLunch.getFormalCtTypeParameters().get(0);
        org.junit.Assert.assertEquals("X", classWeddingLunch_X.getSimpleName());
        org.junit.Assert.assertEquals("spoon.test.generics.testclasses.Mole", methodReserveTC.adaptType(classWeddingLunch_X).getQualifiedName());
        CtClass classCelebrationLunch = ((CtClass) (classWeddingLunch.getDeclaringType()));
        org.junit.Assert.assertEquals("spoon.test.generics.testclasses.CelebrationLunch", classCelebrationLunch.getQualifiedName());
        CtTypeParameter classCelebrationLunch_K = classCelebrationLunch.getFormalCtTypeParameters().get(0);
        CtTypeParameter classCelebrationLunch_L = classCelebrationLunch.getFormalCtTypeParameters().get(1);
        CtTypeParameter classCelebrationLunch_M = classCelebrationLunch.getFormalCtTypeParameters().get(2);
        org.junit.Assert.assertEquals("K", classCelebrationLunch_K.getSimpleName());
        org.junit.Assert.assertEquals("L", classCelebrationLunch_L.getSimpleName());
        org.junit.Assert.assertEquals("M", classCelebrationLunch_M.getSimpleName());
        org.junit.Assert.assertEquals("spoon.test.generics.testclasses.Tacos", methodReserveTC.adaptType(classCelebrationLunch_K).getQualifiedName());
        org.junit.Assert.assertEquals("spoon.test.generics.testclasses.Paella", methodReserveTC.adaptType(classCelebrationLunch_L).getQualifiedName());
        org.junit.Assert.assertEquals("spoon.test.generics.testclasses.Mole", methodReserveTC.adaptType(classCelebrationLunch_M).getQualifiedName());
        spoon.support.visitor.GenericTypeAdapter celebrationLunchTC = methodReserveTC.getEnclosingGenericTypeAdapter().getEnclosingGenericTypeAdapter().getEnclosingGenericTypeAdapter();
        org.junit.Assert.assertEquals("java.lang.Integer", celebrationLunchTC.adaptType(classCelebrationLunch_K).getQualifiedName());
        org.junit.Assert.assertEquals("java.lang.Long", celebrationLunchTC.adaptType(classCelebrationLunch_L).getQualifiedName());
        org.junit.Assert.assertEquals("java.lang.Double", celebrationLunchTC.adaptType(classCelebrationLunch_M).getQualifiedName());
    }

    @org.junit.Test
    public void testMethodTypingContextAdaptMethod() throws java.lang.Exception {
        Factory factory = spoon.testing.utils.ModelUtils.build(new java.io.File("src/test/java/spoon/test/generics/testclasses"));
        CtClass<?> ctClassLunch = factory.Class().get(spoon.test.generics.testclasses.Lunch.class);
        spoon.reflect.declaration.CtMethod<?> trLunch_eatMe = ctClassLunch.filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "eatMe")).first();
        CtClass<?> ctClassWeddingLunch = factory.Class().get(spoon.test.generics.testclasses.CelebrationLunch.WeddingLunch.class);
        spoon.support.visitor.ClassTypingContext ctcWeddingLunch = new spoon.support.visitor.ClassTypingContext(ctClassWeddingLunch);
        final spoon.support.visitor.MethodTypingContext methodSTH = new spoon.support.visitor.MethodTypingContext().setClassTypingContext(ctcWeddingLunch);
        methodSTH.setMethod(trLunch_eatMe);
        spoon.reflect.declaration.CtMethod<?> adaptedLunchEatMe = ((spoon.reflect.declaration.CtMethod<?>) (methodSTH.getAdaptationScope()));
        org.junit.Assert.assertTrue((adaptedLunchEatMe != trLunch_eatMe));
        org.junit.Assert.assertSame(ctClassWeddingLunch, adaptedLunchEatMe.getDeclaringType());
        for (spoon.reflect.declaration.CtTypeMember typeMember : ctClassWeddingLunch.getTypeMembers()) {
            org.junit.Assert.assertFalse((adaptedLunchEatMe == typeMember));
        }
        org.junit.Assert.assertEquals("eatMe", adaptedLunchEatMe.getSimpleName());
        org.junit.Assert.assertEquals(1, adaptedLunchEatMe.getFormalCtTypeParameters().size());
        org.junit.Assert.assertEquals("C", adaptedLunchEatMe.getFormalCtTypeParameters().get(0).getQualifiedName());
        org.junit.Assert.assertEquals(3, adaptedLunchEatMe.getParameters().size());
        org.junit.Assert.assertEquals("X", adaptedLunchEatMe.getParameters().get(0).getType().getQualifiedName());
        org.junit.Assert.assertEquals(spoon.test.generics.testclasses.Tacos.class.getName(), adaptedLunchEatMe.getParameters().get(1).getType().getQualifiedName());
        org.junit.Assert.assertEquals("C", adaptedLunchEatMe.getParameters().get(2).getType().getQualifiedName());
        methodSTH.setMethod(adaptedLunchEatMe);
        org.junit.Assert.assertSame(adaptedLunchEatMe, methodSTH.getAdaptationScope());
    }

    @org.junit.Test
    public void testClassTypingContextMethodSignature() throws java.lang.Exception {
        Factory factory = spoon.testing.utils.ModelUtils.build(new java.io.File("src/test/java/spoon/test/generics/testclasses"));
        CtClass<?> ctClassLunch = factory.Class().get(spoon.test.generics.testclasses.Lunch.class);
        CtClass<?> ctClassWeddingLunch = factory.Class().get(spoon.test.generics.testclasses.CelebrationLunch.WeddingLunch.class);
        spoon.reflect.declaration.CtMethod<?> trLunch_eatMe = ctClassLunch.filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "eatMe")).first();
        spoon.reflect.declaration.CtMethod<?> trWeddingLunch_eatMe = ctClassWeddingLunch.filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "eatMe")).first();
        spoon.support.visitor.ClassTypingContext ctcWeddingLunch = new spoon.support.visitor.ClassTypingContext(ctClassWeddingLunch);
        org.junit.Assert.assertTrue(ctcWeddingLunch.isOverriding(trLunch_eatMe, trLunch_eatMe));
        org.junit.Assert.assertTrue(ctcWeddingLunch.isOverriding(trLunch_eatMe, trWeddingLunch_eatMe));
        org.junit.Assert.assertTrue(ctcWeddingLunch.isSubSignature(trLunch_eatMe, trWeddingLunch_eatMe));
        org.junit.Assert.assertTrue(ctcWeddingLunch.isOverriding(trWeddingLunch_eatMe, trLunch_eatMe));
        org.junit.Assert.assertTrue(ctcWeddingLunch.isOverriding(trWeddingLunch_eatMe, trWeddingLunch_eatMe));
        org.junit.Assert.assertTrue(ctcWeddingLunch.isSubSignature(trWeddingLunch_eatMe, trWeddingLunch_eatMe));
    }

    @org.junit.Test
    public void testClassContextOnInnerClass() throws java.lang.Exception {
        CtClass<?> classBanana = ((CtClass<?>) (spoon.testing.utils.ModelUtils.buildClass(spoon.test.generics.testclasses.Banana.class)));
        CtClass<?> classVitamins = classBanana.getNestedType("Vitamins");
        CtTypeReference<?> refList_T = classVitamins.getSuperclass();
        org.junit.Assert.assertSame(classBanana.getFormalCtTypeParameters().get(0), new spoon.support.visitor.ClassTypingContext(classVitamins).adaptType(refList_T.getActualTypeArguments().get(0)).getDeclaration());
    }

    private void checkFakeTpl(CtInterface<?> fakeTplItf) {
        org.junit.Assert.assertNotNull(fakeTplItf);
        spoon.reflect.declaration.CtMethod<?> applyMethod = fakeTplItf.getMethodsByName("apply").get(0);
        CtTypeReference<?> returnType = applyMethod.getType();
        org.junit.Assert.assertEquals("T", returnType.getSimpleName());
        org.junit.Assert.assertTrue((returnType instanceof spoon.reflect.reference.CtTypeParameterReference));
        org.junit.Assert.assertEquals("CtElement", returnType.getSuperclass().getSimpleName());
        spoon.reflect.declaration.CtParameter<?> targetType = applyMethod.getParameters().get(0);
        List<CtTypeReference<?>> targetTypeArgument = targetType.getType().getActualTypeArguments();
        org.junit.Assert.assertEquals(1, targetTypeArgument.size());
        org.junit.Assert.assertTrue(((targetTypeArgument.get(0)) instanceof spoon.reflect.reference.CtWildcardReference));
        spoon.reflect.declaration.CtMethod<?> testMethod = fakeTplItf.getMethodsByName("test").get(0);
        List<spoon.reflect.declaration.CtParameter<?>> parameters = testMethod.getParameters();
        org.junit.Assert.assertEquals(3, parameters.size());
        spoon.reflect.declaration.CtParameter thirdParam = parameters.get(2);
        org.junit.Assert.assertTrue(((thirdParam.getType()) instanceof spoon.reflect.reference.CtTypeParameterReference));
    }

    @org.junit.Test
    public void testWildCardonShadowClass() throws java.lang.Exception {
        Launcher launcher = new Launcher();
        Factory factory = launcher.getFactory();
        launcher.addInputResource("src/test/java/spoon/test/generics/testclasses/FakeTpl.java");
        launcher.buildModel();
        CtInterface<?> fakeTplItf = factory.Interface().get("spoon.test.generics.testclasses.FakeTpl");
        checkFakeTpl(fakeTplItf);
        launcher = new Launcher();
        factory = launcher.getFactory();
        CtInterface<?> fakeTplItf2 = factory.Interface().get(spoon.test.generics.testclasses.FakeTpl.class);
        checkFakeTpl(fakeTplItf2);
    }

    @org.junit.Test
    public void testDiamondComplexGenericsRxJava() {
        Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/generics/testclasses/rxjava/");
        launcher.setSourceOutputDirectory("./target/spooned-rxjava");
        launcher.run();
        Factory factory = launcher.getFactory();
        List<spoon.reflect.code.CtConstructorCall> invocations = factory.getModel().getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtConstructorCall.class));
        boolean invocationDetected = false;
        for (spoon.reflect.code.CtConstructorCall call : invocations) {
            if (call.getType().getSimpleName().equals("ToNotificationSubscriber")) {
                org.junit.Assert.assertEquals(1, call.getType().getActualTypeArguments().size());
                CtTypeReference actualTA = call.getType().getActualTypeArguments().get(0);
                org.junit.Assert.assertTrue((actualTA instanceof spoon.reflect.reference.CtWildcardReference));
                org.junit.Assert.assertEquals("?", actualTA.getSimpleName());
                org.junit.Assert.assertTrue(((spoon.reflect.reference.CtWildcardReference) (actualTA)).isDefaultBoundingType());
                invocationDetected = true;
            }
        }
        spoon.testing.utils.ModelUtils.canBeBuilt("./target/spooned-rxjava", 8);
        org.junit.Assert.assertTrue(invocationDetected);
    }

    @org.junit.Test
    public void testGetDeclarationOfTypeParameterReference() {
        Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/generics/testclasses/ExtendedPaella.java");
        launcher.addInputResource("./src/test/java/spoon/test/generics/testclasses/Paella.java");
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        CtClass extendedPaella = factory.getModel().getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(CtClass.class, "ExtendedPaella")).get(0);
        List<CtTypeParameter> typeParameterList = extendedPaella.getFormalCtTypeParameters();
        org.junit.Assert.assertEquals(1, typeParameterList.size());
        spoon.reflect.declaration.CtMethod totoMethod = factory.getModel().getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "toto")).get(0);
        CtTypeReference returnTypeToto = totoMethod.getType();
        CtTypeReference paramToto = ((spoon.reflect.declaration.CtParameter) (totoMethod.getParameters().get(0))).getType();
        spoon.reflect.declaration.CtType declaration = returnTypeToto.getDeclaration();
        org.junit.Assert.assertSame(typeParameterList.get(0), declaration);
        org.junit.Assert.assertSame(typeParameterList.get(0), paramToto.getDeclaration());
        spoon.reflect.declaration.CtMethod machinMethod = factory.getModel().getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "machin")).get(0);
        CtTypeReference returnTypeMachin = machinMethod.getType();
        List<CtTypeParameter> formalCtTypeParameters = machinMethod.getFormalCtTypeParameters();
        org.junit.Assert.assertEquals(1, formalCtTypeParameters.size());
        spoon.reflect.declaration.CtType declarationMachin = returnTypeMachin.getDeclaration();
        org.junit.Assert.assertNotSame(typeParameterList.get(0), declarationMachin);
        org.junit.Assert.assertSame(formalCtTypeParameters.get(0), declarationMachin);
        CtClass innerPaella = factory.getModel().getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(CtClass.class, "InnerPaella")).get(0);
        List<CtTypeParameter> innerTypeParametersList = innerPaella.getFormalCtTypeParameters();
        org.junit.Assert.assertEquals(typeParameterList.get(0), innerTypeParametersList.get(0).getSuperclass().getDeclaration());
        spoon.reflect.declaration.CtMethod innerMachinMethod = factory.getModel().getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "innerMachin")).get(0);
        CtTypeReference returnTypeInnerMachin = innerMachinMethod.getType();
        CtTypeReference paramInnerMachinType = ((spoon.reflect.declaration.CtParameter) (innerMachinMethod.getParameters().get(0))).getType();
        List<CtTypeParameter> innerMachinFormalCtType = innerMachinMethod.getFormalCtTypeParameters();
        org.junit.Assert.assertSame(typeParameterList.get(0), returnTypeInnerMachin.getDeclaration());
        org.junit.Assert.assertSame(innerMachinFormalCtType.get(0), paramInnerMachinType.getDeclaration());
        spoon.reflect.declaration.CtMethod innerTotoMethod = factory.getModel().getElements(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "innerToto")).get(0);
        CtTypeReference returnInnerToto = innerTotoMethod.getType();
        CtTypeReference paramInnerToto = ((spoon.reflect.declaration.CtParameter) (innerTotoMethod.getParameters().get(0))).getType();
        List<CtTypeParameter> innerTotoFormatCtType = innerTotoMethod.getFormalCtTypeParameters();
        org.junit.Assert.assertSame(innerTotoFormatCtType.get(0), paramInnerToto.getDeclaration());
        org.junit.Assert.assertSame(innerTypeParametersList.get(0), returnInnerToto.getDeclaration());
    }

    @org.junit.Test
    public void testIsSameSignatureWithGenerics() {
        Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/generics/testclasses/SameSignature.java");
        launcher.buildModel();
        CtClass ctClass = launcher.getFactory().Class().get(spoon.test.generics.testclasses.SameSignature.class);
        List<spoon.reflect.declaration.CtMethod> methods = ctClass.getMethodsByName("forEach");
        org.junit.Assert.assertEquals(1, methods.size());
        spoon.reflect.declaration.CtType<?> iterableItf = launcher.getFactory().Type().get(java.lang.Iterable.class);
        List<spoon.reflect.declaration.CtMethod<?>> methodsItf = iterableItf.getMethodsByName("forEach");
        org.junit.Assert.assertEquals(1, methodsItf.size());
        spoon.support.visitor.ClassTypingContext ctc = new spoon.support.visitor.ClassTypingContext(ctClass.getReference());
        org.junit.Assert.assertTrue(ctc.isOverriding(methods.get(0), methodsItf.get(0)));
        org.junit.Assert.assertTrue(ctc.isSubSignature(methods.get(0), methodsItf.get(0)));
        org.junit.Assert.assertTrue(ctc.isSameSignature(methods.get(0), methodsItf.get(0)));
    }

    @org.junit.Test
    public void testIsSameSignatureWithMethodGenerics() {
        Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/generics/testclasses2/SameSignature2.java");
        launcher.buildModel();
        CtClass ctClass = launcher.getFactory().Class().get(spoon.test.generics.testclasses2.SameSignature2.class);
        spoon.reflect.declaration.CtMethod classMethod = ((spoon.reflect.declaration.CtMethod) (ctClass.getMethodsByName("visitCtConditional").get(0)));
        spoon.reflect.declaration.CtType<?> iface = launcher.getFactory().Type().get("spoon.test.generics.testclasses2.ISameSignature");
        spoon.reflect.declaration.CtMethod ifaceMethod = ((spoon.reflect.declaration.CtMethod) (iface.getMethodsByName("visitCtConditional").get(0)));
        spoon.support.visitor.ClassTypingContext ctcSub = new spoon.support.visitor.ClassTypingContext(ctClass.getReference());
        org.junit.Assert.assertTrue(ctcSub.isOverriding(classMethod, ifaceMethod));
        org.junit.Assert.assertTrue(ctcSub.isOverriding(ifaceMethod, classMethod));
        org.junit.Assert.assertTrue(ctcSub.isSubSignature(classMethod, ifaceMethod));
        org.junit.Assert.assertTrue(ctcSub.isSubSignature(ifaceMethod, classMethod));
        org.junit.Assert.assertTrue(ctcSub.isSameSignature(classMethod, ifaceMethod));
        org.junit.Assert.assertTrue(ctcSub.isSameSignature(ifaceMethod, classMethod));
    }

    @org.junit.Test
    public void testGetExecDeclarationOfEnumSetOf() {
        Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/generics/testclasses/EnumSetOf.java");
        launcher.buildModel();
        CtClass<?> ctClass = launcher.getFactory().Class().get(spoon.test.generics.testclasses.EnumSetOf.class);
        spoon.reflect.code.CtInvocation invocation = ctClass.getMethodsByName("m").get(0).getBody().getStatement(0);
        spoon.reflect.declaration.CtExecutable<?> decl = invocation.getExecutable().getDeclaration();
        org.junit.Assert.assertNull(decl);
        CtClass<?> enumClass = launcher.getFactory().Class().get(java.util.EnumSet.class);
        List<spoon.reflect.declaration.CtMethod<?>> methods = enumClass.getMethodsByName("of");
        spoon.reflect.declaration.CtMethod rightOfMethod = null;
        for (spoon.reflect.declaration.CtMethod method : methods) {
            if ((method.getParameters().size()) == 1) {
                rightOfMethod = method;
            }
        }
        org.junit.Assert.assertNotNull(rightOfMethod);
        decl = invocation.getExecutable().getExecutableDeclaration();
        org.junit.Assert.assertEquals(rightOfMethod, decl);
    }

    @org.junit.Test
    public void testIsSameSignatureWithReferencedGenerics() {
        Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/generics/testclasses2/SameSignature3.java");
        launcher.buildModel();
        CtClass ctClass = launcher.getFactory().Class().get(spoon.test.generics.testclasses2.SameSignature3.class);
        spoon.reflect.declaration.CtMethod classMethod = ((spoon.reflect.declaration.CtMethod) (ctClass.getMethodsByName("visitCtConditional").get(0)));
        spoon.reflect.declaration.CtType<?> iface = launcher.getFactory().Type().get("spoon.test.generics.testclasses2.ISameSignature3");
        spoon.reflect.declaration.CtMethod ifaceMethod = ((spoon.reflect.declaration.CtMethod) (iface.getMethodsByName("visitCtConditional").get(0)));
        spoon.support.visitor.ClassTypingContext ctcSub = new spoon.support.visitor.ClassTypingContext(ctClass.getReference());
        org.junit.Assert.assertTrue(ctcSub.isOverriding(classMethod, ifaceMethod));
        org.junit.Assert.assertTrue(ctcSub.isOverriding(ifaceMethod, classMethod));
        org.junit.Assert.assertTrue(ctcSub.isSubSignature(classMethod, ifaceMethod));
        org.junit.Assert.assertTrue(ctcSub.isSubSignature(ifaceMethod, classMethod));
        org.junit.Assert.assertTrue(ctcSub.isSameSignature(classMethod, ifaceMethod));
        org.junit.Assert.assertTrue(ctcSub.isSameSignature(ifaceMethod, classMethod));
    }

    @org.junit.Test
    public void testIsGenericTypeEqual() {
        Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/generics/testclasses2/LikeCtClass.java");
        launcher.addInputResource("./src/test/java/spoon/test/generics/testclasses2/LikeCtClassImpl.java");
        launcher.buildModel();
        spoon.reflect.declaration.CtType<?> ctIFace = launcher.getFactory().Interface().get(spoon.test.generics.testclasses2.LikeCtClass.class);
        spoon.reflect.declaration.CtMethod<?> ifaceGetter = ((spoon.reflect.declaration.CtMethod) (ctIFace.getMethodsByName("getConstructors").get(0)));
        spoon.reflect.declaration.CtMethod<?> ifaceSetter = ((spoon.reflect.declaration.CtMethod) (ctIFace.getMethodsByName("setConstructors").get(0)));
        org.junit.Assert.assertEquals(ifaceGetter.getType().toString(), ifaceSetter.getParameters().get(0).getType().toString());
        org.junit.Assert.assertEquals(ifaceGetter.getType(), ifaceSetter.getParameters().get(0).getType());
        spoon.reflect.declaration.CtType<?> ctClass = launcher.getFactory().Class().get(spoon.test.generics.testclasses2.LikeCtClassImpl.class);
        spoon.reflect.declaration.CtMethod<?> classGetter = ((spoon.reflect.declaration.CtMethod) (ctClass.getMethodsByName("getConstructors").get(0)));
        spoon.reflect.declaration.CtMethod<?> classSetter = ((spoon.reflect.declaration.CtMethod) (ctClass.getMethodsByName("setConstructors").get(0)));
        org.junit.Assert.assertEquals(classGetter.getType().toString(), classSetter.getParameters().get(0).getType().toString());
        org.junit.Assert.assertEquals(classGetter.getType(), classSetter.getParameters().get(0).getType());
        org.junit.Assert.assertEquals(ifaceGetter.getType().toString(), classGetter.getType().toString());
        org.junit.Assert.assertEquals(ifaceGetter.getType(), classGetter.getType());
        org.junit.Assert.assertEquals(ifaceSetter.getParameters().get(0).getType().toString(), classSetter.getParameters().get(0).getType().toString());
        org.junit.Assert.assertEquals(ifaceSetter.getParameters().get(0).getType(), classSetter.getParameters().get(0).getType());
        org.junit.Assert.assertEquals(ifaceSetter.getParameters().get(0).getType(), classGetter.getType());
        spoon.support.visitor.MethodTypingContext mtc = new spoon.support.visitor.MethodTypingContext().setClassTypingContext(new spoon.support.visitor.ClassTypingContext(ctClass)).setMethod(ifaceSetter);
        spoon.reflect.declaration.CtMethod<?> adaptedMethod = ((spoon.reflect.declaration.CtMethod<?>) (mtc.getAdaptationScope()));
        org.junit.Assert.assertEquals(adaptedMethod.getParameters().get(0).getType(), classGetter.getType());
        org.junit.Assert.assertEquals(adaptedMethod.getParameters().get(0).getType(), classSetter.getParameters().get(0).getType());
        spoon.test.main.MainTest.checkParentConsistency(launcher.getFactory().getModel().getRootPackage());
        spoon.test.main.MainTest.checkParentConsistency(adaptedMethod);
    }

    @org.junit.Test
    public void testCannotAdaptTypeOfNonTypeScope() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> ctClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.generics.testclasses.OuterTypeParameter.class);
        spoon.reflect.code.CtReturn<?> retStmt = ((spoon.reflect.code.CtReturn<?>) (ctClass.getMethodsByName("method").get(0).getBody().getStatements().get(0)));
        CtNewClass<?> newClassExpr = ((CtNewClass<?>) (retStmt.getReturnedExpression()));
        spoon.reflect.declaration.CtType<?> declaringType = newClassExpr.getAnonymousClass();
        spoon.reflect.declaration.CtMethod<?> m1 = declaringType.getMethodsByName("iterator").get(0);
        spoon.support.visitor.ClassTypingContext c = new spoon.support.visitor.ClassTypingContext(declaringType);
        org.junit.Assert.assertFalse(c.isOverriding(m1, declaringType.getSuperclass().getTypeDeclaration().getMethodsByName("add").get(0)));
        org.junit.Assert.assertTrue(c.isOverriding(m1, declaringType.getSuperclass().getTypeDeclaration().getMethodsByName("iterator").get(0)));
    }
}

