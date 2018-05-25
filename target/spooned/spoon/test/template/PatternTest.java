package spoon.test.template;


public class PatternTest {
    @org.junit.Test
    public void testMatchForeach() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> ctClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.template.testclasses.match.MatchForEach.class);
        spoon.reflect.declaration.CtType<?> type = ctClass.getFactory().Type().get(spoon.test.template.testclasses.match.MatchForEach.class);
        spoon.pattern.Pattern pattern = spoon.pattern.PatternBuilder.create(new spoon.pattern.PatternBuilderHelper(type).setBodyOfMethod("matcher1").getPatternElements()).configurePatternParameters(( pb) -> {
            pb.parameter("values").byVariable("values").setContainerKind(spoon.reflect.meta.ContainerKind.LIST).matchInlinedStatements();
        }).build();
        java.util.List<spoon.pattern.Match> matches = pattern.getMatches(ctClass);
        org.junit.Assert.assertEquals(2, matches.size());
        {
            spoon.pattern.Match match = matches.get(0);
            org.junit.Assert.assertEquals(java.util.Arrays.asList("java.lang.System.out.println(value)"), toListOfStrings(match.getMatchingElements()));
        }
        {
            spoon.pattern.Match match = matches.get(1);
            org.junit.Assert.assertEquals(java.util.Arrays.asList("java.lang.System.out.println(\"a\")", "java.lang.System.out.println(\"Xxxx\")", "java.lang.System.out.println(((java.lang.String) (null)))", "java.lang.System.out.println(java.lang.Long.class.toString())"), toListOfStrings(match.getMatchingElements()));
            org.junit.Assert.assertEquals(java.util.Arrays.asList("\"a\"", "\"Xxxx\"", "((java.lang.String) (null))", "java.lang.Long.class.toString()"), toListOfStrings(((java.util.List) (match.getParameters().getValue("values")))));
        }
    }

    @org.junit.Test
    public void testMatchForeachWithOuterSubstitution() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> ctClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.template.testclasses.match.MatchForEach2.class);
        spoon.reflect.declaration.CtType<?> type = ctClass.getFactory().Type().get(spoon.test.template.testclasses.match.MatchForEach2.class);
        spoon.pattern.Pattern pattern = spoon.pattern.PatternBuilder.create(new spoon.pattern.PatternBuilderHelper(type).setBodyOfMethod("matcher1").getPatternElements()).configurePatternParameters(( pb) -> {
            pb.parameter("values").byVariable("values").setContainerKind(spoon.reflect.meta.ContainerKind.LIST).matchInlinedStatements();
            pb.parameter("varName").byString("var");
        }).build();
        java.util.List<spoon.pattern.Match> matches = pattern.getMatches(ctClass);
        org.junit.Assert.assertEquals(3, matches.size());
        {
            spoon.pattern.Match match = matches.get(0);
            org.junit.Assert.assertEquals(java.util.Arrays.asList("int var = 0"), toListOfStrings(match.getMatchingElements()));
        }
        {
            spoon.pattern.Match match = matches.get(1);
            org.junit.Assert.assertEquals(java.util.Arrays.asList("int cc = 0", "java.lang.System.out.println(\"Xxxx\")", "cc++", "java.lang.System.out.println(((java.lang.String) (null)))", "cc++"), toListOfStrings(match.getMatchingElements()));
            org.junit.Assert.assertEquals("cc", match.getParameters().getValue("varName"));
        }
        {
            spoon.pattern.Match match = matches.get(2);
            org.junit.Assert.assertEquals(java.util.Arrays.asList("int dd = 0", "java.lang.System.out.println(java.lang.Long.class.toString())", "dd++"), toListOfStrings(match.getMatchingElements()));
            org.junit.Assert.assertEquals("dd", match.getParameters().getValue("varName"));
        }
    }

    @org.junit.Test
    public void testMatchIfElse() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> ctClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.template.testclasses.match.MatchIfElse.class);
        spoon.reflect.declaration.CtType<?> type = ctClass.getFactory().Type().get(spoon.test.template.testclasses.match.MatchIfElse.class);
        spoon.pattern.Pattern pattern = spoon.pattern.PatternBuilder.create(new spoon.pattern.PatternBuilderHelper(type).setBodyOfMethod("matcher1").getPatternElements()).configurePatternParameters(( pb) -> {
            pb.parameter("option").byVariable("option");
            pb.parameter("value").byFilter(new spoon.reflect.visitor.filter.TypeFilter(spoon.reflect.code.CtLiteral.class));
        }).configureInlineStatements(( lsb) -> lsb.inlineIfOrForeachReferringTo("option")).build();
        java.util.List<spoon.pattern.Match> matches = pattern.getMatches(ctClass.getMethodsByName("testMatch1").get(0));
        org.junit.Assert.assertEquals(5, matches.size());
        {
            spoon.pattern.Match match = matches.get(0);
            org.junit.Assert.assertEquals(java.util.Arrays.asList("java.lang.System.out.println(\"a\")"), toListOfStrings(match.getMatchingElements()));
            org.junit.Assert.assertEquals(true, match.getParameters().getValue("option"));
            org.junit.Assert.assertEquals("\"a\"", match.getParameters().getValue("value").toString());
        }
        {
            spoon.pattern.Match match = matches.get(1);
            org.junit.Assert.assertEquals(java.util.Arrays.asList("java.lang.System.out.println(\"Xxxx\")"), toListOfStrings(match.getMatchingElements()));
            org.junit.Assert.assertEquals(true, match.getParameters().getValue("option"));
            org.junit.Assert.assertEquals("\"Xxxx\"", match.getParameters().getValue("value").toString());
        }
        {
            spoon.pattern.Match match = matches.get(2);
            org.junit.Assert.assertEquals(java.util.Arrays.asList("java.lang.System.out.println(((java.lang.String) (null)))"), toListOfStrings(match.getMatchingElements()));
            org.junit.Assert.assertEquals(true, match.getParameters().getValue("option"));
            org.junit.Assert.assertEquals("((java.lang.String) (null))", match.getParameters().getValue("value").toString());
        }
        {
            spoon.pattern.Match match = matches.get(4);
            org.junit.Assert.assertEquals(java.util.Arrays.asList("java.lang.System.out.println(3.14)"), toListOfStrings(match.getMatchingElements()));
            org.junit.Assert.assertEquals(false, match.getParameters().getValue("option"));
            org.junit.Assert.assertEquals("3.14", match.getParameters().getValue("value").toString());
        }
    }

    @org.junit.Test
    public void testGenerateMultiValues() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> ctClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.template.testclasses.match.MatchMultiple.class);
        spoon.reflect.factory.Factory factory = ctClass.getFactory();
        spoon.pattern.Pattern pattern = spoon.test.template.testclasses.match.MatchMultiple.createPattern(null, null, null);
        spoon.support.util.ImmutableMap params = new spoon.support.util.ImmutableMapImpl();
        params = params.putValue("printedValue", "does it work?");
        java.util.List<spoon.reflect.code.CtStatement> statementsToBeAdded = null;
        statementsToBeAdded = java.util.Arrays.asList(new spoon.reflect.code.CtStatement[]{ factory.createCodeSnippetStatement("int foo = 0"), factory.createCodeSnippetStatement("foo++") });
        params = params.putValue("statements", statementsToBeAdded);
        java.util.List<spoon.reflect.code.CtStatement> generated = pattern.generator().generate(spoon.reflect.code.CtStatement.class, params);
        org.junit.Assert.assertEquals(java.util.Arrays.asList("int foo = 0", "foo++", "java.lang.System.out.println(\"does it work?\")"), generated.stream().map(java.lang.Object::toString).collect(java.util.stream.Collectors.toList()));
    }

    @org.junit.Test
    public void testMatchGreedyMultiValueUnlimited() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> ctClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.template.testclasses.match.MatchMultiple.class);
        spoon.pattern.Pattern pattern = spoon.test.template.testclasses.match.MatchMultiple.createPattern(null, null, null);
        java.util.List<spoon.pattern.Match> matches = pattern.getMatches(ctClass.getMethodsByName("testMatch1").get(0));
        org.junit.Assert.assertEquals(1, matches.size());
        spoon.pattern.Match match = matches.get(0);
        org.junit.Assert.assertEquals(java.util.Arrays.asList("int i = 0", "i++", "java.lang.System.out.println(i)", "java.lang.System.out.println(\"Xxxx\")", "java.lang.System.out.println(((java.lang.String) (null)))", "java.lang.System.out.println(\"last one\")"), toListOfStrings(match.getMatchingElements()));
        org.junit.Assert.assertEquals(java.util.Arrays.asList("int i = 0", "i++", "java.lang.System.out.println(i)", "java.lang.System.out.println(\"Xxxx\")", "java.lang.System.out.println(((java.lang.String) (null)))"), toListOfStrings(((java.util.List) (match.getParameters().getValue("statements")))));
        org.junit.Assert.assertTrue(((match.getParameters().getValue("printedValue")) instanceof spoon.reflect.code.CtLiteral));
        org.junit.Assert.assertEquals("\"last one\"", match.getParameters().getValue("printedValue").toString());
    }

    @org.junit.Test
    public void testMatchGreedyMultiValueMaxCountLimit() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> ctClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.template.testclasses.match.MatchMultiple.class);
        spoon.pattern.Pattern pattern = spoon.test.template.testclasses.match.MatchMultiple.createPattern(null, null, 3);
        java.util.List<spoon.pattern.Match> matches = pattern.getMatches(ctClass.getMethodsByName("testMatch1").get(0));
        org.junit.Assert.assertEquals(2, matches.size());
        {
            spoon.pattern.Match match = matches.get(0);
            org.junit.Assert.assertEquals(java.util.Arrays.asList("int i = 0", "i++", "java.lang.System.out.println(i)", "java.lang.System.out.println(\"Xxxx\")"), toListOfStrings(match.getMatchingElements()));
            org.junit.Assert.assertEquals(java.util.Arrays.asList("int i = 0", "i++", "java.lang.System.out.println(i)"), toListOfStrings(((java.util.List) (match.getParameters().getValue("statements")))));
            org.junit.Assert.assertTrue(((match.getParameters().getValue("printedValue")) instanceof spoon.reflect.code.CtLiteral));
            org.junit.Assert.assertEquals("\"Xxxx\"", match.getParameters().getValue("printedValue").toString());
        }
        {
            spoon.pattern.Match match = matches.get(1);
            org.junit.Assert.assertEquals(java.util.Arrays.asList("java.lang.System.out.println(((java.lang.String) (null)))", "java.lang.System.out.println(\"last one\")"), toListOfStrings(match.getMatchingElements()));
            org.junit.Assert.assertEquals(java.util.Arrays.asList("java.lang.System.out.println(((java.lang.String) (null)))"), toListOfStrings(((java.util.List) (match.getParameters().getValue("statements")))));
            org.junit.Assert.assertTrue(((match.getParameters().getValue("printedValue")) instanceof spoon.reflect.code.CtLiteral));
            org.junit.Assert.assertEquals("\"last one\"", match.getParameters().getValue("printedValue").toString());
        }
    }

    @org.junit.Test
    public void testMatchReluctantMultivalue() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> ctClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.template.testclasses.match.MatchMultiple.class);
        spoon.pattern.Pattern pattern = spoon.test.template.testclasses.match.MatchMultiple.createPattern(spoon.pattern.Quantifier.RELUCTANT, null, null);
        java.util.List<spoon.pattern.Match> matches = pattern.getMatches(ctClass.getMethodsByName("testMatch1").get(0));
        org.junit.Assert.assertEquals(3, matches.size());
        {
            spoon.pattern.Match match = matches.get(0);
            org.junit.Assert.assertEquals(java.util.Arrays.asList("int i = 0", "i++", "java.lang.System.out.println(i)", "java.lang.System.out.println(\"Xxxx\")"), toListOfStrings(match.getMatchingElements()));
            org.junit.Assert.assertEquals(java.util.Arrays.asList("int i = 0", "i++", "java.lang.System.out.println(i)"), toListOfStrings(((java.util.List) (match.getParameters().getValue("statements")))));
            org.junit.Assert.assertTrue(((match.getParameters().getValue("printedValue")) instanceof spoon.reflect.code.CtLiteral));
            org.junit.Assert.assertEquals("\"Xxxx\"", match.getParameters().getValue("printedValue").toString());
        }
        {
            spoon.pattern.Match match = matches.get(1);
            org.junit.Assert.assertEquals(java.util.Arrays.asList("java.lang.System.out.println(((java.lang.String) (null)))"), toListOfStrings(match.getMatchingElements()));
            org.junit.Assert.assertEquals(java.util.Arrays.asList(), toListOfStrings(((java.util.List) (match.getParameters().getValue("statements")))));
            org.junit.Assert.assertTrue(((match.getParameters().getValue("printedValue")) instanceof spoon.reflect.code.CtLiteral));
            org.junit.Assert.assertEquals("((java.lang.String) (null))", match.getParameters().getValue("printedValue").toString());
        }
        {
            spoon.pattern.Match match = matches.get(2);
            org.junit.Assert.assertEquals(java.util.Arrays.asList("java.lang.System.out.println(\"last one\")"), toListOfStrings(match.getMatchingElements()));
            org.junit.Assert.assertEquals(java.util.Arrays.asList(), toListOfStrings(((java.util.List) (match.getParameters().getValue("statements")))));
            org.junit.Assert.assertTrue(((match.getParameters().getValue("printedValue")) instanceof spoon.reflect.code.CtLiteral));
            org.junit.Assert.assertEquals("\"last one\"", match.getParameters().getValue("printedValue").toString());
        }
    }

    @org.junit.Test
    public void testMatchReluctantMultivalueMinCount1() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> ctClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.template.testclasses.match.MatchMultiple.class);
        spoon.pattern.Pattern pattern = spoon.test.template.testclasses.match.MatchMultiple.createPattern(spoon.pattern.Quantifier.RELUCTANT, 1, null);
        java.util.List<spoon.pattern.Match> matches = pattern.getMatches(ctClass.getMethodsByName("testMatch1").get(0));
        org.junit.Assert.assertEquals(2, matches.size());
        {
            spoon.pattern.Match match = matches.get(0);
            org.junit.Assert.assertEquals(java.util.Arrays.asList("int i = 0", "i++", "java.lang.System.out.println(i)", "java.lang.System.out.println(\"Xxxx\")"), toListOfStrings(match.getMatchingElements()));
            org.junit.Assert.assertEquals(java.util.Arrays.asList("int i = 0", "i++", "java.lang.System.out.println(i)"), toListOfStrings(((java.util.List) (match.getParameters().getValue("statements")))));
            org.junit.Assert.assertTrue(((match.getParameters().getValue("printedValue")) instanceof spoon.reflect.code.CtLiteral));
            org.junit.Assert.assertEquals("\"Xxxx\"", match.getParameters().getValue("printedValue").toString());
        }
        {
            spoon.pattern.Match match = matches.get(1);
            org.junit.Assert.assertEquals(java.util.Arrays.asList("java.lang.System.out.println(((java.lang.String) (null)))", "java.lang.System.out.println(\"last one\")"), toListOfStrings(match.getMatchingElements()));
            org.junit.Assert.assertEquals(java.util.Arrays.asList("java.lang.System.out.println(((java.lang.String) (null)))"), toListOfStrings(((java.util.List) (match.getParameters().getValue("statements")))));
            org.junit.Assert.assertTrue(((match.getParameters().getValue("printedValue")) instanceof spoon.reflect.code.CtLiteral));
            org.junit.Assert.assertEquals("\"last one\"", match.getParameters().getValue("printedValue").toString());
        }
    }

    @org.junit.Test
    public void testMatchReluctantMultivalueExactly2() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> ctClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.template.testclasses.match.MatchMultiple.class);
        spoon.pattern.Pattern pattern = spoon.test.template.testclasses.match.MatchMultiple.createPattern(spoon.pattern.Quantifier.RELUCTANT, 2, 2);
        java.util.List<spoon.pattern.Match> matches = pattern.getMatches(ctClass.getMethodsByName("testMatch1").get(0));
        org.junit.Assert.assertEquals(1, matches.size());
        {
            spoon.pattern.Match match = matches.get(0);
            org.junit.Assert.assertEquals(java.util.Arrays.asList("i++", "java.lang.System.out.println(i)", "java.lang.System.out.println(\"Xxxx\")"), toListOfStrings(match.getMatchingElements()));
            org.junit.Assert.assertEquals(java.util.Arrays.asList("i++", "java.lang.System.out.println(i)"), toListOfStrings(((java.util.List) (match.getParameters().getValue("statements")))));
            org.junit.Assert.assertTrue(((match.getParameters().getValue("printedValue")) instanceof spoon.reflect.code.CtLiteral));
            org.junit.Assert.assertEquals("\"Xxxx\"", match.getParameters().getValue("printedValue").toString());
        }
    }

    @org.junit.Test
    public void testMatchPossesiveMultiValueUnlimited() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> ctClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.template.testclasses.match.MatchMultiple.class);
        spoon.pattern.Pattern pattern = spoon.test.template.testclasses.match.MatchMultiple.createPattern(spoon.pattern.Quantifier.POSSESSIVE, null, null);
        java.util.List<spoon.pattern.Match> matches = pattern.getMatches(ctClass.getMethodsByName("testMatch1").get(0).getBody());
        org.junit.Assert.assertEquals(0, matches.size());
    }

    @org.junit.Test
    public void testMatchPossesiveMultiValueMaxCount4() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> ctClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.template.testclasses.match.MatchMultiple.class);
        spoon.pattern.Pattern pattern = spoon.test.template.testclasses.match.MatchMultiple.createPattern(spoon.pattern.Quantifier.POSSESSIVE, null, 4);
        java.util.List<spoon.pattern.Match> matches = pattern.getMatches(ctClass);
        org.junit.Assert.assertEquals(1, matches.size());
        spoon.pattern.Match match = matches.get(0);
        org.junit.Assert.assertEquals(java.util.Arrays.asList("int i = 0", "i++", "java.lang.System.out.println(i)", "java.lang.System.out.println(\"Xxxx\")", "java.lang.System.out.println(((java.lang.String) (null)))"), toListOfStrings(match.getMatchingElements()));
        org.junit.Assert.assertEquals(java.util.Arrays.asList("int i = 0", "i++", "java.lang.System.out.println(i)", "java.lang.System.out.println(\"Xxxx\")"), toListOfStrings(((java.util.List) (match.getParameters().getValue("statements")))));
        org.junit.Assert.assertTrue(((match.getParameters().getValue("printedValue")) instanceof spoon.reflect.code.CtLiteral));
        org.junit.Assert.assertEquals("((java.lang.String) (null))", match.getParameters().getValue("printedValue").toString());
    }

    @org.junit.Test
    public void testMatchPossesiveMultiValueMinCount() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> ctClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.template.testclasses.match.MatchMultiple3.class);
        for (int count = 0; count < 6; count++) {
            final int countFinal = count;
            spoon.reflect.declaration.CtType<?> type = ctClass.getFactory().Type().get(spoon.test.template.testclasses.match.MatchMultiple3.class);
            spoon.pattern.Pattern pattern = spoon.pattern.PatternBuilder.create(new spoon.pattern.PatternBuilderHelper(type).setBodyOfMethod("matcher1").getPatternElements()).configurePatternParameters().configurePatternParameters(( pb) -> {
                pb.parameter("statements1").setContainerKind(spoon.reflect.meta.ContainerKind.LIST).setMatchingStrategy(spoon.pattern.Quantifier.GREEDY);
                pb.parameter("statements2").setContainerKind(spoon.reflect.meta.ContainerKind.LIST).setMatchingStrategy(spoon.pattern.Quantifier.POSSESSIVE).setMinOccurence(countFinal).setMaxOccurence(countFinal);
                pb.parameter("printedValue").byFilter((spoon.reflect.code.CtLiteral<?> literal) -> "something".equals(literal.getValue()));
            }).build();
            java.util.List<spoon.pattern.Match> matches = pattern.getMatches(ctClass.getMethodsByName("testMatch1").get(0).getBody());
            org.junit.Assert.assertEquals(("count=" + countFinal), countFinal, getCollectionSize(matches.get(0).getParameters().getValue("statements2")));
            org.junit.Assert.assertEquals(("count=" + countFinal), (5 - countFinal), getCollectionSize(matches.get(0).getParameters().getValue("statements1")));
        }
    }

    @org.junit.Test
    public void testMatchPossesiveMultiValueMinCount2() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> ctClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.template.testclasses.match.MatchMultiple2.class);
        for (int count = 0; count < 5; count++) {
            final int countFinal = count;
            spoon.pattern.Pattern pattern = spoon.pattern.PatternBuilder.create(new spoon.pattern.PatternBuilderHelper(ctClass).setBodyOfMethod("matcher1").getPatternElements()).configurePatternParameters(( pb) -> {
                pb.byTemplateParameter();
                pb.parameter("statements1").setContainerKind(spoon.reflect.meta.ContainerKind.LIST).setMatchingStrategy(spoon.pattern.Quantifier.GREEDY);
                pb.parameter("statements2").setContainerKind(spoon.reflect.meta.ContainerKind.LIST).setMatchingStrategy(spoon.pattern.Quantifier.POSSESSIVE).setMinOccurence(countFinal).setMaxOccurence(countFinal);
                pb.parameter("inlinedSysOut").byVariable("something").setMatchingStrategy(spoon.pattern.Quantifier.POSSESSIVE).setContainerKind(spoon.reflect.meta.ContainerKind.LIST).setMinOccurence(2).matchInlinedStatements();
            }).build();
            java.util.List<spoon.pattern.Match> matches = pattern.getMatches(ctClass.getMethodsByName("testMatch1").get(0).getBody());
            org.junit.Assert.assertEquals(("count=" + countFinal), 1, matches.size());
            org.junit.Assert.assertEquals(("count=" + countFinal), (4 - countFinal), getCollectionSize(matches.get(0).getParameters().getValue("statements1")));
            org.junit.Assert.assertEquals(("count=" + countFinal), countFinal, getCollectionSize(matches.get(0).getParameters().getValue("statements2")));
            org.junit.Assert.assertEquals(("count=" + countFinal), 2, getCollectionSize(matches.get(0).getParameters().getValue("inlinedSysOut")));
        }
        for (int count = 5; count < 7; count++) {
            final int countFinal = count;
            spoon.pattern.Pattern pattern = spoon.pattern.PatternBuilder.create(new spoon.pattern.PatternBuilderHelper(ctClass).setBodyOfMethod("matcher1").getPatternElements()).configurePatternParameters().build();
            java.util.List<spoon.pattern.Match> matches = pattern.getMatches(ctClass.getMethodsByName("testMatch1").get(0).getBody());
            org.junit.Assert.assertEquals(("count=" + countFinal), 0, matches.size());
        }
    }

    @org.junit.Test
    public void testMatchGreedyMultiValueMinCount2() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> ctClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.template.testclasses.match.MatchMultiple2.class);
        for (int i = 0; i < 7; i++) {
            final int count = i;
            spoon.reflect.declaration.CtType<?> type = ctClass.getFactory().Type().get(spoon.test.template.testclasses.match.MatchMultiple2.class);
            spoon.pattern.Pattern pattern = spoon.pattern.PatternBuilder.create(new spoon.pattern.PatternBuilderHelper(type).setBodyOfMethod("matcher1").getPatternElements()).configurePatternParameters(( pb) -> {
                pb.byTemplateParameter();
                pb.parameter("statements1").setContainerKind(spoon.reflect.meta.ContainerKind.LIST).setMatchingStrategy(spoon.pattern.Quantifier.RELUCTANT);
                pb.parameter("statements2").setContainerKind(spoon.reflect.meta.ContainerKind.LIST).setMatchingStrategy(spoon.pattern.Quantifier.GREEDY).setMaxOccurence(count);
                pb.parameter("printedValue").byVariable("something").matchInlinedStatements();
                pb.parameter("printedValue").setMatchingStrategy(spoon.pattern.Quantifier.GREEDY).setContainerKind(spoon.reflect.meta.ContainerKind.LIST).setMinOccurence(2);
            }).build();
            java.util.List<spoon.pattern.Match> matches = pattern.getMatches(ctClass.getMethodsByName("testMatch1").get(0).getBody());
            if (count < 7) {
                org.junit.Assert.assertEquals(("count=" + count), 1, matches.size());
                org.junit.Assert.assertEquals(("count=" + count), java.lang.Math.max(0, (3 - count)), getCollectionSize(matches.get(0).getParameters().getValue("statements1")));
                org.junit.Assert.assertEquals(("count=" + count), (count - (java.lang.Math.max(0, (count - 4)))), getCollectionSize(matches.get(0).getParameters().getValue("statements2")));
                org.junit.Assert.assertEquals(("count=" + count), java.lang.Math.max(2, (3 - (java.lang.Math.max(0, (count - 3))))), getCollectionSize(matches.get(0).getParameters().getValue("printedValue")));
            }else {
                org.junit.Assert.assertEquals(("count=" + count), 0, matches.size());
            }
        }
    }

    private int getCollectionSize(java.lang.Object list) {
        if (list instanceof java.util.Collection) {
            return ((java.util.Collection) (list)).size();
        }
        if (list == null) {
            return 0;
        }
        org.junit.Assert.fail(("Unexpected object of type " + (list.getClass())));
        return -1;
    }

    @org.junit.Test
    public void testMatchParameterValue() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> ctClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.template.testclasses.match.MatchWithParameterType.class);
        spoon.pattern.Pattern pattern = spoon.pattern.PatternBuilder.create(new spoon.pattern.PatternBuilderHelper(ctClass).setBodyOfMethod("matcher1").getPatternElements()).configurePatternParameters(( pb) -> {
            pb.parameter("value").byVariable("value");
        }).build();
        java.util.List<spoon.pattern.Match> matches = pattern.getMatches(ctClass);
        org.junit.Assert.assertEquals(("{\n" + ((("value=value\n" + "}\n") + "----------\n") + "1) java.lang.System.out.println(value)")), matches.get(0).toString());
        org.junit.Assert.assertEquals(5, matches.size());
        {
            spoon.pattern.Match match = matches.get(0);
            org.junit.Assert.assertEquals(java.util.Arrays.asList("java.lang.System.out.println(value)"), toListOfStrings(match.getMatchingElements()));
            java.lang.Object value = match.getParameters().getValue("value");
            org.junit.Assert.assertTrue((value instanceof spoon.reflect.code.CtVariableRead));
            org.junit.Assert.assertEquals("value", value.toString());
            org.junit.Assert.assertTrue(((spoon.reflect.declaration.CtElement) (value)).isParentInitialized());
            org.junit.Assert.assertSame(spoon.reflect.path.CtRole.ARGUMENT, ((spoon.reflect.declaration.CtElement) (value)).getRoleInParent());
        }
    }

    @org.junit.Test
    public void testMatchParameterValueType() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> ctClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.template.testclasses.match.MatchWithParameterType.class);
        {
            spoon.pattern.Pattern pattern = spoon.pattern.PatternBuilder.create(new spoon.pattern.PatternBuilderHelper(ctClass).setBodyOfMethod("matcher1").getPatternElements()).configurePatternParameters(( pb) -> {
                pb.parameter("value").byVariable("value");
                pb.setValueType(spoon.reflect.code.CtLiteral.class);
            }).build();
            java.util.List<spoon.pattern.Match> matches = pattern.getMatches(ctClass.getMethodsByName("testMatch1").get(0));
            org.junit.Assert.assertEquals(3, matches.size());
            {
                spoon.pattern.Match match = matches.get(0);
                org.junit.Assert.assertEquals(java.util.Arrays.asList("java.lang.System.out.println(\"a\")"), toListOfStrings(match.getMatchingElements()));
                org.junit.Assert.assertTrue(((match.getParameters().getValue("value")) instanceof spoon.reflect.code.CtLiteral));
                org.junit.Assert.assertEquals("\"a\"", match.getParameters().getValue("value").toString());
            }
            {
                spoon.pattern.Match match = matches.get(1);
                org.junit.Assert.assertEquals(java.util.Arrays.asList("java.lang.System.out.println(\"Xxxx\")"), toListOfStrings(match.getMatchingElements()));
                org.junit.Assert.assertTrue(((match.getParameters().getValue("value")) instanceof spoon.reflect.code.CtLiteral));
                org.junit.Assert.assertEquals("\"Xxxx\"", match.getParameters().getValue("value").toString());
            }
            {
                spoon.pattern.Match match = matches.get(2);
                org.junit.Assert.assertEquals(java.util.Arrays.asList("java.lang.System.out.println(((java.lang.String) (null)))"), toListOfStrings(match.getMatchingElements()));
                org.junit.Assert.assertTrue(((match.getParameters().getValue("value")) instanceof spoon.reflect.code.CtLiteral));
                org.junit.Assert.assertEquals("((java.lang.String) (null))", match.getParameters().getValue("value").toString());
            }
        }
        {
            spoon.pattern.Pattern pattern = spoon.pattern.PatternBuilder.create(new spoon.pattern.PatternBuilderHelper(ctClass).setBodyOfMethod("matcher1").getPatternElements()).configurePatternParameters(( pb) -> {
                pb.parameter("value").byVariable("value");
                pb.setValueType(spoon.reflect.code.CtInvocation.class);
            }).build();
            java.util.List<spoon.pattern.Match> matches = pattern.getMatches(ctClass);
            org.junit.Assert.assertEquals(1, matches.size());
            {
                spoon.pattern.Match match = matches.get(0);
                org.junit.Assert.assertEquals(java.util.Arrays.asList("java.lang.System.out.println(java.lang.Long.class.toString())"), toListOfStrings(match.getMatchingElements()));
                org.junit.Assert.assertTrue(((match.getParameters().getValue("value")) instanceof spoon.reflect.code.CtInvocation));
                org.junit.Assert.assertEquals("java.lang.Long.class.toString()", match.getParameters().getValue("value").toString());
            }
        }
    }

    @org.junit.Test
    public void testMatchParameterCondition() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> ctClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.template.testclasses.match.MatchWithParameterCondition.class);
        {
            spoon.pattern.Pattern pattern = spoon.pattern.PatternBuilder.create(new spoon.pattern.PatternBuilderHelper(ctClass).setBodyOfMethod("matcher1").getPatternElements()).configurePatternParameters(( pb) -> {
                pb.parameter("value").byVariable("value");
                pb.byCondition(null, (java.lang.Object value) -> value instanceof spoon.reflect.code.CtLiteral);
            }).build();
            java.util.List<spoon.pattern.Match> matches = pattern.getMatches(ctClass);
            org.junit.Assert.assertEquals(3, matches.size());
            {
                spoon.pattern.Match match = matches.get(0);
                org.junit.Assert.assertEquals(java.util.Arrays.asList("java.lang.System.out.println(\"a\")"), toListOfStrings(match.getMatchingElements()));
                org.junit.Assert.assertTrue(((match.getParameters().getValue("value")) instanceof spoon.reflect.code.CtLiteral));
                org.junit.Assert.assertEquals("\"a\"", match.getParameters().getValue("value").toString());
            }
            {
                spoon.pattern.Match match = matches.get(1);
                org.junit.Assert.assertEquals(java.util.Arrays.asList("java.lang.System.out.println(\"Xxxx\")"), toListOfStrings(match.getMatchingElements()));
                org.junit.Assert.assertTrue(((match.getParameters().getValue("value")) instanceof spoon.reflect.code.CtLiteral));
                org.junit.Assert.assertEquals("\"Xxxx\"", match.getParameters().getValue("value").toString());
            }
            {
                spoon.pattern.Match match = matches.get(2);
                org.junit.Assert.assertEquals(java.util.Arrays.asList("java.lang.System.out.println(((java.lang.String) (null)))"), toListOfStrings(match.getMatchingElements()));
                org.junit.Assert.assertTrue(((match.getParameters().getValue("value")) instanceof spoon.reflect.code.CtLiteral));
                org.junit.Assert.assertEquals("((java.lang.String) (null))", match.getParameters().getValue("value").toString());
            }
        }
    }

    @org.junit.Test
    public void testMatchOfAttribute() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> ctClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.template.testclasses.match.MatchModifiers.class);
        {
            spoon.pattern.Pattern pattern = spoon.pattern.PatternBuilder.create(new spoon.pattern.PatternBuilderHelper(ctClass).setTypeMember("matcher1").getPatternElements()).configurePatternParameters(( pb) -> {
                pb.parameter("modifiers").byRole(spoon.reflect.path.CtRole.MODIFIER, new spoon.reflect.visitor.filter.TypeFilter(spoon.reflect.declaration.CtMethod.class));
                pb.parameter("methodName").byString("matcher1");
                pb.parameter("parameters").byRole(spoon.reflect.path.CtRole.PARAMETER, new spoon.reflect.visitor.filter.TypeFilter(spoon.reflect.declaration.CtMethod.class));
            }).build();
            java.util.List<spoon.pattern.Match> matches = pattern.getMatches(ctClass);
            org.junit.Assert.assertEquals(3, matches.size());
            {
                spoon.pattern.Match match = matches.get(0);
                org.junit.Assert.assertEquals(1, match.getMatchingElements().size());
                org.junit.Assert.assertEquals("matcher1", match.getMatchingElement(spoon.reflect.declaration.CtMethod.class).getSimpleName());
                org.junit.Assert.assertEquals(3, match.getParametersMap().size());
                org.junit.Assert.assertEquals("matcher1", match.getParametersMap().get("methodName"));
                org.junit.Assert.assertEquals(new java.util.HashSet<>(java.util.Arrays.asList(spoon.reflect.declaration.ModifierKind.PUBLIC)), match.getParametersMap().get("modifiers"));
                org.junit.Assert.assertEquals(java.util.Arrays.asList(), match.getParametersMap().get("parameters"));
            }
            {
                spoon.pattern.Match match = matches.get(1);
                org.junit.Assert.assertEquals(1, match.getMatchingElements().size());
                org.junit.Assert.assertEquals("publicStaticMethod", match.getMatchingElement(spoon.reflect.declaration.CtMethod.class).getSimpleName());
                org.junit.Assert.assertEquals(3, match.getParametersMap().size());
                org.junit.Assert.assertEquals("publicStaticMethod", match.getParametersMap().get("methodName"));
                org.junit.Assert.assertEquals(new java.util.HashSet<>(java.util.Arrays.asList(spoon.reflect.declaration.ModifierKind.PUBLIC, spoon.reflect.declaration.ModifierKind.STATIC)), match.getParametersMap().get("modifiers"));
                org.junit.Assert.assertEquals(java.util.Arrays.asList(), match.getParametersMap().get("parameters"));
            }
            {
                spoon.pattern.Match match = matches.get(2);
                org.junit.Assert.assertEquals(1, match.getMatchingElements().size());
                org.junit.Assert.assertEquals("packageProtectedMethodWithParam", match.getMatchingElement(spoon.reflect.declaration.CtMethod.class).getSimpleName());
                org.junit.Assert.assertEquals(3, match.getParametersMap().size());
                org.junit.Assert.assertEquals("packageProtectedMethodWithParam", match.getParametersMap().get("methodName"));
                org.junit.Assert.assertEquals(new java.util.HashSet<>(), match.getParametersMap().get("modifiers"));
                org.junit.Assert.assertEquals(2, ((java.util.List) (match.getParametersMap().get("parameters"))).size());
            }
        }
        {
            spoon.pattern.Pattern pattern = spoon.pattern.PatternBuilder.create(new spoon.pattern.PatternBuilderHelper(ctClass).setTypeMember("matcher1").getPatternElements()).configurePatternParameters(( pb) -> {
                pb.parameter("modifiers").byRole(spoon.reflect.path.CtRole.MODIFIER, new spoon.reflect.visitor.filter.TypeFilter(spoon.reflect.declaration.CtMethod.class));
                pb.parameter("methodName").byString("matcher1");
                pb.parameter("parameters").byRole(spoon.reflect.path.CtRole.PARAMETER, new spoon.reflect.visitor.filter.TypeFilter(spoon.reflect.declaration.CtMethod.class));
                pb.parameter("statements").byRole(spoon.reflect.path.CtRole.STATEMENT, new spoon.reflect.visitor.filter.TypeFilter(spoon.reflect.code.CtBlock.class));
            }).build();
            java.util.List<spoon.pattern.Match> matches = pattern.getMatches(ctClass);
            org.junit.Assert.assertEquals(4, matches.size());
            {
                spoon.pattern.Match match = matches.get(3);
                org.junit.Assert.assertEquals(1, match.getMatchingElements().size());
                org.junit.Assert.assertEquals("withBody", match.getMatchingElement(spoon.reflect.declaration.CtMethod.class).getSimpleName());
                org.junit.Assert.assertEquals(4, match.getParametersMap().size());
                org.junit.Assert.assertEquals("withBody", match.getParametersMap().get("methodName"));
                org.junit.Assert.assertEquals(new java.util.HashSet<>(java.util.Arrays.asList(spoon.reflect.declaration.ModifierKind.PRIVATE)), match.getParametersMap().get("modifiers"));
                org.junit.Assert.assertEquals(0, ((java.util.List) (match.getParametersMap().get("parameters"))).size());
                org.junit.Assert.assertEquals(2, ((java.util.List) (match.getParametersMap().get("statements"))).size());
                org.junit.Assert.assertEquals("this.getClass()", ((java.util.List) (match.getParametersMap().get("statements"))).get(0).toString());
                org.junit.Assert.assertEquals("java.lang.System.out.println()", ((java.util.List) (match.getParametersMap().get("statements"))).get(1).toString());
            }
        }
    }

    @org.junit.Test
    public void testMatchOfMapAttribute() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> matchMapClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.template.testclasses.match.MatchMap.class);
        {
            spoon.reflect.declaration.CtType<?> type = matchMapClass.getFactory().Type().get(spoon.test.template.testclasses.match.MatchMap.class);
            spoon.pattern.Pattern pattern = spoon.pattern.PatternBuilder.create(new spoon.pattern.PatternBuilderHelper(type).setTypeMember("matcher1").getPatternElements()).configurePatternParameters(( pb) -> {
                pb.parameter("__pattern_param_annot").byRole(spoon.reflect.path.CtRole.VALUE, new spoon.reflect.visitor.filter.TypeFilter(spoon.reflect.declaration.CtAnnotation.class)).setContainerKind(spoon.reflect.meta.ContainerKind.MAP);
                pb.parameter("__pattern_param_method_name").byString("matcher1");
            }).build();
            java.util.List<spoon.pattern.Match> matches = pattern.getMatches(matchMapClass);
            org.junit.Assert.assertEquals(3, matches.size());
            {
                spoon.pattern.Match match = matches.get(0);
                org.junit.Assert.assertEquals(1, match.getMatchingElements().size());
                org.junit.Assert.assertEquals("matcher1", match.getMatchingElement(spoon.reflect.declaration.CtMethod.class).getSimpleName());
                org.junit.Assert.assertEquals(2, match.getParametersMap().size());
                org.junit.Assert.assertEquals("matcher1", match.getParametersMap().get("__pattern_param_method_name"));
                java.util.Map<java.lang.String, java.lang.Object> values = getMap(match, "__pattern_param_annot");
                org.junit.Assert.assertEquals(0, values.size());
            }
            {
                spoon.pattern.Match match = matches.get(1);
                org.junit.Assert.assertEquals(1, match.getMatchingElements().size());
                org.junit.Assert.assertEquals("m1", match.getMatchingElement(spoon.reflect.declaration.CtMethod.class).getSimpleName());
                org.junit.Assert.assertEquals(2, match.getParametersMap().size());
                org.junit.Assert.assertEquals("m1", match.getParametersMap().get("__pattern_param_method_name"));
                java.util.Map<java.lang.String, java.lang.Object> values = getMap(match, "__pattern_param_annot");
                org.junit.Assert.assertEquals(1, values.size());
                org.junit.Assert.assertEquals("\"xyz\"", values.get("value").toString());
            }
            {
                spoon.pattern.Match match = matches.get(2);
                org.junit.Assert.assertEquals(1, match.getMatchingElements().size());
                org.junit.Assert.assertEquals("m2", match.getMatchingElement(spoon.reflect.declaration.CtMethod.class).getSimpleName());
                org.junit.Assert.assertEquals(2, match.getParametersMap().size());
                org.junit.Assert.assertEquals("m2", match.getParametersMap().get("__pattern_param_method_name"));
                java.util.Map<java.lang.String, java.lang.Object> values = getMap(match, "__pattern_param_annot");
                org.junit.Assert.assertEquals(2, values.size());
                org.junit.Assert.assertEquals("\"abc\"", values.get("value").toString());
                org.junit.Assert.assertEquals("123", values.get("timeout").toString());
            }
        }
    }

    @org.junit.Test
    public void testMatchOfMapAttributeAndOtherAnnotations() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> ctClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.template.testclasses.match.MatchMap.class);
        {
            spoon.reflect.declaration.CtType<?> type = ctClass.getFactory().Type().get(spoon.test.template.testclasses.match.MatchMap.class);
            spoon.pattern.Pattern pattern = spoon.pattern.PatternBuilder.create(new spoon.pattern.PatternBuilderHelper(type).setTypeMember("matcher1").getPatternElements()).configurePatternParameters(( pb) -> {
                pb.parameter("methodName").byString("matcher1");
                pb.parameter("allAnnotations").setConflictResolutionMode(spoon.pattern.ConflictResolutionMode.APPEND).byRole(spoon.reflect.path.CtRole.ANNOTATION, new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.declaration.CtMethod.class));
                pb.parameter("CheckAnnotationValues").byRole(spoon.reflect.path.CtRole.VALUE, new spoon.reflect.visitor.filter.TypeFilter(spoon.reflect.declaration.CtAnnotation.class)).setContainerKind(spoon.reflect.meta.ContainerKind.MAP);
            }).build();
            java.util.List<spoon.pattern.Match> matches = pattern.getMatches(ctClass);
            org.junit.Assert.assertEquals(4, matches.size());
            {
                spoon.pattern.Match match = matches.get(3);
                org.junit.Assert.assertEquals(1, match.getMatchingElements().size());
                org.junit.Assert.assertEquals("deprecatedTestAnnotation2", match.getMatchingElement(spoon.reflect.declaration.CtMethod.class).getSimpleName());
                org.junit.Assert.assertEquals(3, match.getParametersMap().size());
                org.junit.Assert.assertEquals("deprecatedTestAnnotation2", match.getParametersMap().get("methodName"));
                org.junit.Assert.assertEquals("{timeout=4567}", getMap(match, "CheckAnnotationValues").toString());
                org.junit.Assert.assertEquals("@java.lang.Deprecated", match.getParameters().getValue("allAnnotations").toString());
            }
        }
    }

    @org.junit.Test
    public void testMatchOfMapKeySubstring() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> ctClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.template.testclasses.match.MatchMap.class);
        {
            spoon.reflect.declaration.CtType<?> type = ctClass.getFactory().Type().get(spoon.test.template.testclasses.match.MatchMap.class);
            spoon.pattern.Pattern pattern = spoon.pattern.PatternBuilder.create(new spoon.pattern.PatternBuilderHelper(type).setTypeMember("m1").getPatternElements()).configurePatternParameters(( pb) -> {
                pb.parameter("CheckKey").bySubstring("value");
                pb.parameter("CheckValue").byFilter((spoon.reflect.code.CtLiteral lit) -> true);
                pb.parameter("methodName").byString("m1");
                pb.parameter("allAnnotations").setConflictResolutionMode(spoon.pattern.ConflictResolutionMode.APPEND).byRole(spoon.reflect.path.CtRole.ANNOTATION, new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.declaration.CtMethod.class));
            }).build();
            java.util.List<spoon.pattern.Match> matches = pattern.getMatches(ctClass);
            org.junit.Assert.assertEquals(2, matches.size());
            {
                spoon.pattern.Match match = matches.get(0);
                org.junit.Assert.assertEquals(1, match.getMatchingElements().size());
                org.junit.Assert.assertEquals("m1", match.getMatchingElement(spoon.reflect.declaration.CtMethod.class).getSimpleName());
                org.junit.Assert.assertEquals(3, match.getParametersMap().size());
                org.junit.Assert.assertEquals("m1", match.getParametersMap().get("methodName"));
                org.junit.Assert.assertEquals("value", match.getParameters().getValue("CheckKey").toString());
                org.junit.Assert.assertEquals("\"xyz\"", match.getParameters().getValue("CheckValue").toString());
            }
            {
                spoon.pattern.Match match = matches.get(1);
                org.junit.Assert.assertEquals(1, match.getMatchingElements().size());
                org.junit.Assert.assertEquals("deprecatedTestAnnotation2", match.getMatchingElement(spoon.reflect.declaration.CtMethod.class).getSimpleName());
                org.junit.Assert.assertEquals(4, match.getParametersMap().size());
                org.junit.Assert.assertEquals("deprecatedTestAnnotation2", match.getParametersMap().get("methodName"));
                org.junit.Assert.assertEquals("timeout", match.getParameters().getValue("CheckKey").toString());
                org.junit.Assert.assertEquals("4567", match.getParameters().getValue("CheckValue").toString());
                org.junit.Assert.assertEquals("@java.lang.Deprecated", match.getParameters().getValue("allAnnotations").toString());
            }
        }
    }

    @org.junit.Test
    public void testMatchInSet() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> ctClass = spoon.testing.utils.ModelUtils.buildClass(spoon.test.template.testclasses.match.MatchThrowables.class);
        spoon.reflect.factory.Factory f = ctClass.getFactory();
        spoon.pattern.Pattern pattern = spoon.pattern.PatternBuilder.create(new spoon.pattern.PatternBuilderHelper(ctClass).setTypeMember("matcher1").getPatternElements()).configurePatternParameters(( pb) -> {
            pb.parameter("otherThrowables").setConflictResolutionMode(spoon.pattern.ConflictResolutionMode.APPEND).setContainerKind(spoon.reflect.meta.ContainerKind.SET).setMinOccurence(0).byRole(spoon.reflect.path.CtRole.THROWN, new spoon.reflect.visitor.filter.TypeFilter(spoon.reflect.declaration.CtMethod.class));
        }).configurePatternParameters(( pb) -> {
            pb.parameter("modifiers").byRole(spoon.reflect.path.CtRole.MODIFIER, new spoon.reflect.visitor.filter.TypeFilter(spoon.reflect.declaration.CtMethod.class));
            pb.parameter("methodName").byString("matcher1");
            pb.parameter("parameters").byRole(spoon.reflect.path.CtRole.PARAMETER, new spoon.reflect.visitor.filter.TypeFilter(spoon.reflect.declaration.CtMethod.class));
            pb.parameter("statements").byRole(spoon.reflect.path.CtRole.STATEMENT, new spoon.reflect.visitor.filter.TypeFilter(spoon.reflect.code.CtBlock.class));
        }).build();
        java.lang.String str = pattern.toString();
        java.util.List<spoon.pattern.Match> matches = pattern.getMatches(ctClass);
        org.junit.Assert.assertEquals(4, matches.size());
        {
            spoon.pattern.Match match = matches.get(0);
            org.junit.Assert.assertEquals(1, match.getMatchingElements().size());
            org.junit.Assert.assertEquals("matcher1", match.getMatchingElement(spoon.reflect.declaration.CtMethod.class).getSimpleName());
        }
        {
            spoon.pattern.Match match = matches.get(1);
            org.junit.Assert.assertEquals(1, match.getMatchingElements().size());
            org.junit.Assert.assertEquals("sample2", match.getMatchingElement(spoon.reflect.declaration.CtMethod.class).getSimpleName());
            org.junit.Assert.assertEquals(new java.util.HashSet(java.util.Arrays.asList("java.lang.UnsupportedOperationException", "java.lang.IllegalArgumentException")), ((java.util.Set<spoon.reflect.reference.CtTypeReference<?>>) (match.getParameters().getValue("otherThrowables"))).stream().map(( e) -> e.toString()).collect(java.util.stream.Collectors.toSet()));
        }
        {
            spoon.pattern.Match match = matches.get(2);
            org.junit.Assert.assertEquals(1, match.getMatchingElements().size());
            org.junit.Assert.assertEquals("sample3", match.getMatchingElement(spoon.reflect.declaration.CtMethod.class).getSimpleName());
            org.junit.Assert.assertEquals(new java.util.HashSet(java.util.Arrays.asList("java.lang.IllegalArgumentException")), ((java.util.Set<spoon.reflect.reference.CtTypeReference<?>>) (match.getParameters().getValue("otherThrowables"))).stream().map(( e) -> e.toString()).collect(java.util.stream.Collectors.toSet()));
        }
        {
            spoon.pattern.Match match = matches.get(3);
            org.junit.Assert.assertEquals(1, match.getMatchingElements().size());
            org.junit.Assert.assertEquals("sample4", match.getMatchingElement(spoon.reflect.declaration.CtMethod.class).getSimpleName());
            org.junit.Assert.assertNull(match.getParameters().getValue("otherThrowables"));
        }
    }

    private java.util.List<java.lang.String> toListOfStrings(java.util.List<? extends java.lang.Object> list) {
        if (list == null) {
            return java.util.Collections.emptyList();
        }
        java.util.List<java.lang.String> strings = new java.util.ArrayList<>(list.size());
        for (java.lang.Object obj : list) {
            strings.add((obj == null ? "null" : obj.toString()));
        }
        return strings;
    }

    private spoon.test.template.PatternTest.MapBuilder map() {
        return new spoon.test.template.PatternTest.MapBuilder();
    }

    class MapBuilder extends java.util.LinkedHashMap<java.lang.String, java.lang.Object> {
        public spoon.test.template.PatternTest.MapBuilder put(java.lang.String key, java.lang.Object value) {
            super.put(key, value);
            return this;
        }
    }

    @org.junit.Test
    public void testPatternParameters() {
        spoon.reflect.factory.Factory f = spoon.testing.utils.ModelUtils.build(new java.io.File("./src/test/java/spoon/test/template/testclasses/replace/DPPSample1.java"), new java.io.File("./src/test/java/spoon/test/template/testclasses/replace"));
        spoon.pattern.Pattern p = spoon.test.template.testclasses.replace.OldPattern.createPatternFromMethodPatternModel(f);
        java.util.Map<java.lang.String, spoon.pattern.internal.parameter.ParameterInfo> parameterInfos = p.getParameterInfos();
        org.junit.Assert.assertEquals(15, parameterInfos.size());
        org.junit.Assert.assertEquals(new java.util.HashSet<>(java.util.Arrays.asList("next", "item", "startPrefixSpace", "printer", "start", "statements", "nextPrefixSpace", "startSuffixSpace", "elementPrinterHelper", "endPrefixSpace", "startKeyword", "useStartKeyword", "end", "nextSuffixSpace", "getIterable")), parameterInfos.keySet());
        for (java.util.Map.Entry<java.lang.String, spoon.pattern.internal.parameter.ParameterInfo> e : parameterInfos.entrySet()) {
            org.junit.Assert.assertEquals(e.getKey(), e.getValue().getName());
        }
    }

    @org.junit.Test
    public void testPatternToString() {
        java.lang.System.setProperty("line.separator", "\n");
        spoon.reflect.factory.Factory f = spoon.testing.utils.ModelUtils.build(new java.io.File("./src/test/java/spoon/test/template/testclasses/replace/DPPSample1.java"), new java.io.File("./src/test/java/spoon/test/template/testclasses/replace"));
        spoon.pattern.Pattern p = spoon.test.template.testclasses.replace.OldPattern.createPatternFromMethodPatternModel(f);
        org.junit.Assert.assertEquals(("if (/* CtInvocation\n" + ((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((((("    / <= ${useStartKeyword}\n" + " */\n") + "useStartKeyword()) {\n") + "    /* CtInvocation\n") + "        /argument/ <= ${startKeyword}\n") + "     */\n") + "    /* CtInvocation\n") + "        /target/ <= ${printer}\n") + "     */\n") + "    /* CtInvocation\n") + "        / <= ${printer}\n") + "     */\n") + "    printer().writeSpace().writeKeyword(/* CtInvocation\n") + "        / <= ${startKeyword}\n") + "     */\n") + "    startKeyword()).writeSpace();\n") + "}\n") + "try (final spoon.reflect.visitor.ListPrinter lp = /* CtInvocation\n") + "    /argument/ <= ${end}\n") + "    /target/ <= ${elementPrinterHelper}\n") + " */\n") + "/* CtInvocation\n") + "    / <= ${elementPrinterHelper}\n") + " */\n") + "elementPrinterHelper().createListPrinter(/* CtInvocation\n") + "    / <= ${startPrefixSpace}\n") + " */\n") + "startPrefixSpace(), /* CtInvocation\n") + "    / <= ${start}\n") + " */\n") + "start(), /* CtInvocation\n") + "    / <= ${startSuffixSpace}\n") + " */\n") + "startSuffixSpace(), /* CtInvocation\n") + "    / <= ${nextPrefixSpace}\n") + " */\n") + "nextPrefixSpace(), /* CtInvocation\n") + "    / <= ${next}\n") + " */\n") + "next(), /* CtInvocation\n") + "    / <= ${nextSuffixSpace}\n") + " */\n") + "nextSuffixSpace(), /* CtInvocation\n") + "    / <= ${endPrefixSpace}\n") + " */\n") + "endPrefixSpace(), /* CtInvocation\n") + "    / <= ${end}\n") + " */\n") + "end())) {\n") + "    /* CtForEach\n") + "        /expression/ <= ${getIterable}\n") + "        /foreachVariable/ <= ${item}\n") + "     */\n") + "    for (/* CtLocalVariable\n") + "        / <= ${item}\n") + "     */\n") + "    java.lang.Object item : /* CtInvocation\n") + "        / <= ${getIterable}\n") + "     */\n") + "    getIterable()) /* CtBlock\n") + "        /statement/ <= ${statements}\n") + "     */\n") + "    {\n") + "        lp.printSeparatorIfAppropriate();\n") + "        /* CtInvocation\n") + "            / <= ${statements}\n") + "         */\n") + "        statements();\n") + "    }\n") + "}\n")), p.toString());
    }

    @org.junit.Test
    public void testMatchSample1() throws java.lang.Exception {
        spoon.reflect.factory.Factory f = spoon.testing.utils.ModelUtils.build(new java.io.File("./src/test/java/spoon/test/template/testclasses/replace/DPPSample1.java"), new java.io.File("./src/test/java/spoon/test/template/testclasses/replace"));
        spoon.reflect.declaration.CtClass<?> classDJPP = f.Class().get(spoon.test.template.testclasses.replace.DPPSample1.class);
        org.junit.Assert.assertNotNull(classDJPP);
        org.junit.Assert.assertFalse(classDJPP.isShadow());
        spoon.reflect.declaration.CtType<java.lang.Object> type = f.Type().get(spoon.test.template.testclasses.replace.OldPattern.class);
        spoon.pattern.Pattern p = spoon.pattern.PatternBuilder.create(new spoon.pattern.PatternBuilderHelper(type).setBodyOfMethod("patternModel").getPatternElements()).configurePatternParameters((spoon.pattern.PatternParameterConfigurator pb) -> pb.byFieldAccessOnVariable("params").byFieldAccessOnVariable("item").parameter("statements").setContainerKind(spoon.reflect.meta.ContainerKind.LIST)).configurePatternParameters().configureInlineStatements(( ls) -> ls.inlineIfOrForeachReferringTo("useStartKeyword")).build();
        java.util.List<spoon.pattern.Match> matches = p.getMatches(classDJPP);
        org.junit.Assert.assertEquals(2, matches.size());
        spoon.support.util.ImmutableMap params = matches.get(0).getParameters();
        org.junit.Assert.assertEquals("\"extends\"", params.getValue("startKeyword").toString());
        org.junit.Assert.assertEquals(java.lang.Boolean.TRUE, params.getValue("useStartKeyword"));
        org.junit.Assert.assertEquals("false", params.getValue("startPrefixSpace").toString());
        org.junit.Assert.assertEquals("null", params.getValue("start").toString());
        org.junit.Assert.assertEquals("false", params.getValue("startSuffixSpace").toString());
        org.junit.Assert.assertEquals("false", params.getValue("nextPrefixSpace").toString());
        org.junit.Assert.assertEquals("\",\"", params.getValue("next").toString());
        org.junit.Assert.assertEquals("true", params.getValue("nextSuffixSpace").toString());
        org.junit.Assert.assertEquals("false", params.getValue("endPrefixSpace").toString());
        org.junit.Assert.assertEquals("\";\"", params.getValue("end").toString());
        org.junit.Assert.assertEquals("ctEnum.getEnumValues()", params.getValue("getIterable").toString());
        org.junit.Assert.assertEquals("[scan(enumValue)]", params.getValue("statements").toString());
        params = matches.get(1).getParameters();
        org.junit.Assert.assertEquals(null, params.getValue("startKeyword"));
        org.junit.Assert.assertEquals(java.lang.Boolean.FALSE, params.getValue("useStartKeyword"));
        org.junit.Assert.assertEquals("false", params.getValue("startPrefixSpace").toString());
        org.junit.Assert.assertEquals("null", params.getValue("start").toString());
        org.junit.Assert.assertEquals("false", params.getValue("startSuffixSpace").toString());
        org.junit.Assert.assertEquals("false", params.getValue("nextPrefixSpace").toString());
        org.junit.Assert.assertEquals("\",\"", params.getValue("next").toString());
        org.junit.Assert.assertEquals("true", params.getValue("nextSuffixSpace").toString());
        org.junit.Assert.assertEquals("false", params.getValue("endPrefixSpace").toString());
        org.junit.Assert.assertEquals("\";\"", params.getValue("end").toString());
        org.junit.Assert.assertEquals("ctEnum.getEnumValues()", params.getValue("getIterable").toString());
        org.junit.Assert.assertEquals("[scan(enumValue)]", params.getValue("statements").toString());
        org.junit.Assert.assertEquals(params.asMap(), params.checkpoint().asMap());
    }

    @org.junit.Test
    public void testAddGeneratedBy() throws java.lang.Exception {
        spoon.reflect.declaration.CtType templateModel = spoon.testing.utils.ModelUtils.buildClass(spoon.test.template.testclasses.types.AClassWithMethodsAndRefs.class);
        spoon.reflect.factory.Factory factory = templateModel.getFactory();
        spoon.pattern.Pattern pattern = spoon.pattern.PatternBuilder.create(templateModel).setAddGeneratedBy(true).build();
    }

    @org.junit.Test
    public void testGenerateClassWithSelfReferences() throws java.lang.Exception {
        spoon.reflect.declaration.CtType templateModel = spoon.testing.utils.ModelUtils.buildClass(spoon.test.template.testclasses.types.AClassWithMethodsAndRefs.class);
        spoon.reflect.factory.Factory factory = templateModel.getFactory();
        spoon.pattern.Pattern pattern = spoon.pattern.PatternBuilder.create(templateModel).setAddGeneratedBy(true).build();
        final java.lang.String newQName = "spoon.test.generated.ACloneOfAClassWithMethodsAndRefs";
        spoon.reflect.declaration.CtClass<?> generatedType = pattern.generator().generateType(newQName, java.util.Collections.emptyMap());
        org.junit.Assert.assertNotNull(generatedType);
        org.junit.Assert.assertEquals(java.util.Arrays.asList("<init>", "local", "sameType", "sameTypeStatic", "anotherMethod", "someMethod", "Local", "foo"), generatedType.getTypeMembers().stream().map(spoon.reflect.declaration.CtTypeMember::getSimpleName).collect(java.util.stream.Collectors.toList()));
        org.junit.Assert.assertEquals(newQName, generatedType.getQualifiedName());
        java.util.Set<java.lang.String> usedTypeRefs = new java.util.HashSet<>();
        generatedType.filterChildren(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.reference.CtTypeReference.class)).forEach((spoon.reflect.reference.CtTypeReference ref) -> usedTypeRefs.add(ref.getQualifiedName()));
        org.junit.Assert.assertEquals(new java.util.HashSet<>(java.util.Arrays.asList("spoon.test.generated.ACloneOfAClassWithMethodsAndRefs", "void", "boolean", "spoon.test.generated.ACloneOfAClassWithMethodsAndRefs$1Bar", "java.lang.Object", "int", "spoon.test.generated.ACloneOfAClassWithMethodsAndRefs$Local")), usedTypeRefs);
        generatedType.filterChildren(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.reference.CtExecutableReference.class)).forEach((spoon.reflect.reference.CtExecutableReference execRef) -> {
            spoon.reflect.reference.CtTypeReference declTypeRef = execRef.getDeclaringType();
            if (declTypeRef.getQualifiedName().startsWith("spoon.test.generated.ACloneOfAClassWithMethodsAndRefs")) {
                return;
            }
            if (declTypeRef.getQualifiedName().equals(java.lang.Object.class.getName())) {
                return;
            }
            org.junit.Assert.fail(("Unexpected declaring type " + (declTypeRef.getQualifiedName())));
        });
    }

    @org.junit.Test
    public void testGenerateMethodWithSelfReferences() throws java.lang.Exception {
        spoon.reflect.declaration.CtType templateModel = spoon.testing.utils.ModelUtils.buildClass(spoon.test.template.testclasses.types.AClassWithMethodsAndRefs.class);
        spoon.reflect.factory.Factory factory = templateModel.getFactory();
        spoon.pattern.Pattern pattern = spoon.pattern.PatternBuilder.create(((spoon.reflect.declaration.CtMethod) (templateModel.getMethodsByName("foo").get(0)))).setAddGeneratedBy(true).build();
        spoon.reflect.declaration.CtClass<?> generatedType = factory.createClass("spoon.test.generated.ACloneOfAClassWithMethodsAndRefs");
        pattern.generator().addToType(spoon.reflect.declaration.CtMethod.class, java.util.Collections.emptyMap(), generatedType);
        org.junit.Assert.assertEquals(java.util.Arrays.asList("foo"), generatedType.getTypeMembers().stream().map(spoon.reflect.declaration.CtTypeMember::getSimpleName).collect(java.util.stream.Collectors.toList()));
        org.junit.Assert.assertEquals(1, generatedType.getMethodsByName("foo").size());
        org.junit.Assert.assertEquals("Generated by spoon.test.template.testclasses.types.AClassWithMethodsAndRefs#foo(AClassWithMethodsAndRefs.java:30)", generatedType.getMethodsByName("foo").get(0).getDocComment().trim());
        java.util.Set<java.lang.String> usedTypeRefs = new java.util.HashSet<>();
        generatedType.filterChildren(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.reference.CtTypeReference.class)).forEach((spoon.reflect.reference.CtTypeReference ref) -> usedTypeRefs.add(ref.getQualifiedName()));
        org.junit.Assert.assertEquals(new java.util.HashSet<>(java.util.Arrays.asList("spoon.test.generated.ACloneOfAClassWithMethodsAndRefs", "void", "spoon.test.generated.ACloneOfAClassWithMethodsAndRefs$1Bar", "java.lang.Object", "spoon.test.generated.ACloneOfAClassWithMethodsAndRefs$Local")), usedTypeRefs);
        generatedType.filterChildren(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.reference.CtExecutableReference.class)).forEach((spoon.reflect.reference.CtExecutableReference execRef) -> {
            spoon.reflect.reference.CtTypeReference declTypeRef = execRef.getDeclaringType();
            if (declTypeRef.getQualifiedName().startsWith("spoon.test.generated.ACloneOfAClassWithMethodsAndRefs")) {
                return;
            }
            if (declTypeRef.getQualifiedName().equals(java.lang.Object.class.getName())) {
                return;
            }
            org.junit.Assert.fail(("Unexpected declaring type " + (declTypeRef.getQualifiedName())));
        });
    }

    @org.junit.Test
    public void testPatternMatchOfMultipleElements() throws java.lang.Exception {
        spoon.reflect.declaration.CtType toBeMatchedtype = spoon.testing.utils.ModelUtils.buildClass(spoon.test.template.testclasses.ToBeMatched.class);
        java.util.List<spoon.reflect.code.CtLiteral<java.lang.String>> literals1 = getFirstStmt(toBeMatchedtype, "match1", spoon.reflect.code.CtInvocation.class).getArguments();
        java.util.List<spoon.reflect.code.CtLiteral<java.lang.String>> literals2 = getFirstStmt(toBeMatchedtype, "match2", spoon.reflect.code.CtInvocation.class).getArguments();
        org.junit.Assert.assertEquals("a", literals1.get(0).getValue());
        spoon.reflect.factory.Factory f = toBeMatchedtype.getFactory();
        {
            java.util.List<spoon.reflect.declaration.CtElement> found = new java.util.ArrayList<>();
            spoon.pattern.Pattern p = spoon.pattern.PatternBuilder.create(f.createLiteral("a")).build();
            org.junit.Assert.assertEquals(0, p.getParameterInfos().size());
            p.forEachMatch(toBeMatchedtype, ( match) -> {
                found.add(match.getMatchingElement());
            });
            org.junit.Assert.assertEquals(3, found.size());
            org.junit.Assert.assertSame(literals1.get(0), found.get(0));
            org.junit.Assert.assertSame(literals1.get(6), found.get(1));
            org.junit.Assert.assertSame(literals2.get(0), found.get(2));
        }
        {
            java.util.List<java.util.List<spoon.reflect.declaration.CtElement>> found = new java.util.ArrayList<>();
            spoon.pattern.Pattern pattern = spoon.test.template.PatternTest.patternOfStringLiterals(toBeMatchedtype.getFactory(), "a", "b", "c");
            pattern.forEachMatch(toBeMatchedtype, ( match) -> {
                found.add(match.getMatchingElements());
            });
            org.junit.Assert.assertEquals(2, found.size());
            org.junit.Assert.assertEquals(3, found.get(1).size());
            org.junit.Assert.assertEquals("\"a\"", found.get(0).get(0).toString());
            org.junit.Assert.assertEquals(17, found.get(0).get(0).getPosition().getColumn());
            org.junit.Assert.assertEquals("\"b\"", found.get(0).get(1).toString());
            org.junit.Assert.assertEquals(22, found.get(0).get(1).getPosition().getColumn());
            org.junit.Assert.assertEquals("\"c\"", found.get(0).get(2).toString());
            org.junit.Assert.assertEquals(27, found.get(0).get(2).getPosition().getColumn());
            assertSequenceOn(literals1, 0, 3, found.get(0));
            assertSequenceOn(literals1, 6, 3, found.get(1));
        }
        {
            java.util.List<java.util.List<spoon.reflect.declaration.CtElement>> found = new java.util.ArrayList<>();
            spoon.test.template.PatternTest.patternOfStringLiterals(toBeMatchedtype.getFactory(), "b", "c").forEachMatch(toBeMatchedtype, ( match) -> {
                found.add(match.getMatchingElements());
            });
            org.junit.Assert.assertEquals(3, found.size());
            assertSequenceOn(literals1, 1, 2, found.get(0));
            assertSequenceOn(literals1, 7, 2, found.get(1));
            assertSequenceOn(literals2, 3, 2, found.get(2));
        }
        {
            java.util.List<java.util.List<spoon.reflect.declaration.CtElement>> found = new java.util.ArrayList<>();
            spoon.test.template.PatternTest.patternOfStringLiterals(toBeMatchedtype.getFactory(), "d", "d").forEachMatch(toBeMatchedtype, ( match) -> {
                found.add(match.getMatchingElements());
            });
            org.junit.Assert.assertEquals(2, found.size());
            assertSequenceOn(literals2, 6, 2, found.get(0));
            assertSequenceOn(literals2, 8, 2, found.get(1));
        }
    }

    private static spoon.pattern.Pattern patternOfStringLiterals(spoon.reflect.factory.Factory f, java.lang.String... strs) {
        return spoon.pattern.PatternBuilder.create(java.util.Arrays.asList(strs).stream().map(( s) -> f.createLiteral(s)).collect(java.util.stream.Collectors.toList())).build();
    }

    private void assertSequenceOn(java.util.List<? extends spoon.reflect.declaration.CtElement> source, int expectedOffset, int expectedSize, java.util.List<spoon.reflect.declaration.CtElement> matches) {
        org.junit.Assert.assertEquals(expectedSize, matches.size());
        for (int i = 0; i < expectedSize; i++) {
            org.junit.Assert.assertSame(source.get((expectedOffset + i)), matches.get(i));
        }
    }

    private <T extends spoon.reflect.declaration.CtElement> T getFirstStmt(spoon.reflect.declaration.CtType type, java.lang.String methodName, java.lang.Class<T> stmtType) {
        return ((T) (type.filterChildren((spoon.reflect.declaration.CtMethod m) -> m.getSimpleName().equals(methodName)).first(spoon.reflect.declaration.CtMethod.class).getBody().getStatement(0)));
    }

    private int indexOf(java.util.List list, java.lang.Object o) {
        for (int i = 0; i < (list.size()); i++) {
            if ((list.get(i)) == o) {
                return i;
            }
        }
        return -1;
    }

    @org.junit.Test
    public void testExtensionDecoupledSubstitutionVisitor() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput" });
        launcher.addInputResource("./src/test/java/spoon/test/template/testclasses/logger/Logger.java");
        launcher.addTemplateResource(new spoon.support.compiler.FileSystemFile("./src/test/java/spoon/test/template/testclasses/LoggerModel.java"));
        launcher.buildModel();
        spoon.reflect.factory.Factory factory = launcher.getFactory();
        final spoon.reflect.declaration.CtClass<?> aTargetType = launcher.getFactory().Class().get(spoon.test.template.testclasses.logger.Logger.class);
        final spoon.reflect.declaration.CtMethod<?> toBeLoggedMethod = aTargetType.getMethodsByName("enter").get(0);
        java.util.Map<java.lang.String, java.lang.Object> params = new java.util.HashMap<>();
        params.put("_classname_", factory.Code().createLiteral(aTargetType.getSimpleName()));
        params.put("_methodName_", factory.Code().createLiteral(toBeLoggedMethod.getSimpleName()));
        params.put("_block_", toBeLoggedMethod.getBody());
        spoon.reflect.declaration.CtType<?> type = factory.Type().get(spoon.test.template.testclasses.LoggerModel.class);
        spoon.pattern.Pattern pattern = spoon.pattern.PatternBuilder.create(type.getMethodsByName("block").get(0)).configurePatternParameters().build();
        final java.util.List<spoon.reflect.declaration.CtMethod> aMethods = pattern.generator().addToType(spoon.reflect.declaration.CtMethod.class, params, aTargetType);
        org.junit.Assert.assertEquals(1, aMethods.size());
        final spoon.reflect.declaration.CtMethod<?> aMethod = aMethods.get(0);
        org.junit.Assert.assertTrue(((aMethod.getBody().getStatement(0)) instanceof spoon.reflect.code.CtTry));
        final spoon.reflect.code.CtTry aTry = ((spoon.reflect.code.CtTry) (aMethod.getBody().getStatement(0)));
        org.junit.Assert.assertTrue(((aTry.getFinalizer().getStatement(0)) instanceof spoon.reflect.code.CtInvocation));
        org.junit.Assert.assertEquals("spoon.test.template.testclasses.logger.Logger.exit(\"enter\")", aTry.getFinalizer().getStatement(0).toString());
        org.junit.Assert.assertTrue(((aTry.getBody().getStatement(0)) instanceof spoon.reflect.code.CtInvocation));
        org.junit.Assert.assertEquals("spoon.test.template.testclasses.logger.Logger.enter(\"Logger\", \"enter\")", aTry.getBody().getStatement(0).toString());
        org.junit.Assert.assertTrue(((aTry.getBody().getStatements().size()) > 1));
    }

    private java.util.Map<java.lang.String, java.lang.Object> getMap(spoon.pattern.Match match, java.lang.String name) {
        java.lang.Object v = match.getParametersMap().get(name);
        org.junit.Assert.assertNotNull(v);
        return ((spoon.support.util.ImmutableMap) (v)).asMap();
    }
}

