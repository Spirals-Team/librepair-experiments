package spoon.test.literal;


public class LiteralTest {
    @org.junit.Test
    public void testCharLiteralInNoClasspath() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/resources/noclasspath/SecondaryIndexManager.java");
        launcher.setSourceOutputDirectory("./target/literal");
        launcher.getEnvironment().setNoClasspath(true);
        launcher.run();
        final spoon.reflect.declaration.CtClass<java.lang.Object> aClass = launcher.getFactory().Class().get("org.apache.cassandra.index.SecondaryIndexManager");
        java.util.TreeSet<spoon.reflect.code.CtLiteral<?>> ts = new java.util.TreeSet<spoon.reflect.code.CtLiteral<?>>(new spoon.support.comparator.DeepRepresentationComparator());
        ts.addAll(aClass.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtLiteral<java.lang.Character>>(spoon.reflect.code.CtLiteral.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtLiteral element) {
                return ((element.getValue()) instanceof java.lang.Character) && (super.matches(element));
            }
        }));
        org.junit.Assert.assertTrue(ts.last().getType().isPrimitive());
        org.junit.Assert.assertEquals(':', ts.last().getValue());
        spoon.testing.utils.ModelUtils.canBeBuilt("./target/literal", 8, true);
    }

    @org.junit.Test
    public void testLiteralInForEachWithNoClasspath() {
        spoon.Launcher runLaunch = new spoon.Launcher();
        runLaunch.getEnvironment().setNoClasspath(true);
        runLaunch.addInputResource("./src/test/resources/noclasspath/LiteralInForEach.java");
        runLaunch.buildModel();
    }

    @org.junit.Test
    public void testBuildLiternal() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<spoon.test.literal.testclasses.Tacos> ctType = spoon.testing.utils.ModelUtils.buildClass(spoon.test.literal.testclasses.Tacos.class);
        spoon.reflect.factory.TypeFactory typeFactory = ctType.getFactory().Type();
        spoon.reflect.code.CtLiteral<?> literal = ((spoon.reflect.code.CtLiteral<?>) (ctType.getField("a").getDefaultExpression()));
        org.junit.Assert.assertEquals(0, literal.getValue());
        org.junit.Assert.assertTrue(literal.getType().isPrimitive());
        org.junit.Assert.assertEquals(typeFactory.INTEGER_PRIMITIVE, literal.getType());
        literal = ((spoon.reflect.code.CtLiteral<?>) (ctType.getField("b").getDefaultExpression()));
        org.junit.Assert.assertEquals(0, literal.getValue());
        org.junit.Assert.assertTrue(literal.getType().isPrimitive());
        org.junit.Assert.assertEquals(typeFactory.INTEGER_PRIMITIVE, literal.getType());
        literal = ((spoon.reflect.code.CtLiteral<?>) (ctType.getField("c").getDefaultExpression()));
        org.junit.Assert.assertEquals(0.0F, literal.getValue());
        org.junit.Assert.assertTrue(literal.getType().isPrimitive());
        org.junit.Assert.assertEquals(typeFactory.FLOAT_PRIMITIVE, literal.getType());
        literal = ((spoon.reflect.code.CtLiteral<?>) (ctType.getField("d").getDefaultExpression()));
        org.junit.Assert.assertEquals(0L, literal.getValue());
        org.junit.Assert.assertTrue(literal.getType().isPrimitive());
        org.junit.Assert.assertEquals(typeFactory.LONG_PRIMITIVE, literal.getType());
        literal = ((spoon.reflect.code.CtLiteral<?>) (ctType.getField("e").getDefaultExpression()));
        org.junit.Assert.assertEquals(0.0, literal.getValue());
        org.junit.Assert.assertTrue(literal.getType().isPrimitive());
        org.junit.Assert.assertEquals(typeFactory.DOUBLE_PRIMITIVE, literal.getType());
        literal = ((spoon.reflect.code.CtLiteral<?>) (ctType.getField("f").getDefaultExpression()));
        org.junit.Assert.assertEquals('0', literal.getValue());
        org.junit.Assert.assertTrue(literal.getType().isPrimitive());
        org.junit.Assert.assertEquals(typeFactory.CHARACTER_PRIMITIVE, literal.getType());
        literal = ((spoon.reflect.code.CtLiteral<?>) (ctType.getField("g").getDefaultExpression()));
        org.junit.Assert.assertEquals("0", literal.getValue());
        org.junit.Assert.assertFalse(literal.getType().isPrimitive());
        org.junit.Assert.assertEquals(typeFactory.STRING, literal.getType());
        literal = ((spoon.reflect.code.CtLiteral<?>) (ctType.getField("h").getDefaultExpression()));
        org.junit.Assert.assertEquals(null, literal.getValue());
        org.junit.Assert.assertFalse(literal.getType().isPrimitive());
        org.junit.Assert.assertEquals(typeFactory.NULL_TYPE, literal.getType());
    }

    @org.junit.Test
    public void testFactoryLiternal() {
        spoon.Launcher runLaunch = new spoon.Launcher();
        spoon.reflect.factory.Factory factory = runLaunch.getFactory();
        spoon.reflect.factory.CodeFactory code = factory.Code();
        spoon.reflect.code.CtLiteral literal = code.createLiteral(1);
        org.junit.Assert.assertEquals(1, literal.getValue());
        org.junit.Assert.assertEquals(factory.Type().integerPrimitiveType(), literal.getType());
        literal = code.createLiteral(new java.lang.Integer(1));
        org.junit.Assert.assertEquals(1, literal.getValue());
        org.junit.Assert.assertEquals(factory.Type().integerPrimitiveType(), literal.getType());
        literal = code.createLiteral(1.0);
        org.junit.Assert.assertEquals(1.0, literal.getValue());
        org.junit.Assert.assertEquals(factory.Type().doublePrimitiveType(), literal.getType());
        literal = code.createLiteral("literal");
        org.junit.Assert.assertEquals("literal", literal.getValue());
        org.junit.Assert.assertEquals(factory.Type().stringType(), literal.getType());
    }

    @org.junit.Test
    public void testEscapedString() throws java.lang.Exception {
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/literal/testclasses/EscapedLiteral.java");
        launcher.getEnvironment().setCommentEnabled(true);
        launcher.getEnvironment().setAutoImports(true);
        launcher.buildModel();
        final spoon.reflect.declaration.CtClass<?> ctClass = launcher.getFactory().Class().get("spoon.test.literal.testclasses.EscapedLiteral");
        org.junit.Assert.assertTrue(('\u0000' == ((char) (((spoon.reflect.code.CtLiteral) (ctClass.getField("c1").getDefaultExpression())).getValue()))));
        org.junit.Assert.assertTrue(('\u0000' == ((char) (((spoon.reflect.code.CtLiteral) (ctClass.getField("c1").getDefaultExpression())).getValue()))));
        org.junit.Assert.assertTrue(('\u0007' == ((char) (((spoon.reflect.code.CtLiteral) (ctClass.getField("c2").getDefaultExpression())).getValue()))));
        org.junit.Assert.assertTrue(('\u0007' == ((char) (((spoon.reflect.code.CtLiteral) (ctClass.getField("c2").getDefaultExpression())).getValue()))));
        org.junit.Assert.assertTrue(('?' == ((char) (((spoon.reflect.code.CtLiteral) (ctClass.getField("c3").getDefaultExpression())).getValue()))));
        org.junit.Assert.assertTrue(('?' == ((char) (((spoon.reflect.code.CtLiteral) (ctClass.getField("c3").getDefaultExpression())).getValue()))));
        org.junit.Assert.assertTrue(('\u007f' == ((char) (((spoon.reflect.code.CtLiteral) (ctClass.getField("c4").getDefaultExpression())).getValue()))));
        org.junit.Assert.assertTrue(('\u007f' == ((char) (((spoon.reflect.code.CtLiteral) (ctClass.getField("c4").getDefaultExpression())).getValue()))));
        org.junit.Assert.assertTrue(('\u00bf' == ((char) (((spoon.reflect.code.CtLiteral) (ctClass.getField("c5").getDefaultExpression())).getValue()))));
        org.junit.Assert.assertTrue(('\u00bf' == ((char) (((spoon.reflect.code.CtLiteral) (ctClass.getField("c5").getDefaultExpression())).getValue()))));
        org.junit.Assert.assertTrue(('\u00ff' == ((char) (((spoon.reflect.code.CtLiteral) (ctClass.getField("c6").getDefaultExpression())).getValue()))));
        org.junit.Assert.assertTrue(('\u00ff' == ((char) (((spoon.reflect.code.CtLiteral) (ctClass.getField("c6").getDefaultExpression())).getValue()))));
        org.junit.Assert.assertTrue(('\u0000' == ((char) (((spoon.reflect.code.CtLiteral) (ctClass.getField("c7").getDefaultExpression())).getValue()))));
        org.junit.Assert.assertTrue(('\u0001' == ((char) (((spoon.reflect.code.CtLiteral) (ctClass.getField("c8").getDefaultExpression())).getValue()))));
        org.junit.Assert.assertTrue(('\u0002' == ((char) (((spoon.reflect.code.CtLiteral) (ctClass.getField("c9").getDefaultExpression())).getValue()))));
    }
}

