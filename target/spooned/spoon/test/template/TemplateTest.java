package spoon.test.template;


import java.io.Serializable;
import java.rmi.Remote;
import java.util.Date;
import java.util.List;
import spoon.Launcher;
import spoon.compiler.SpoonResourceHelper;
import spoon.pattern.Match;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.declaration.CtType;
import spoon.reflect.factory.Factory;
import spoon.reflect.factory.TypeFactory;
import spoon.reflect.reference.CtFieldReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.Filter;
import spoon.reflect.visitor.ModelConsistencyChecker;
import spoon.reflect.visitor.chain.CtConsumer;
import spoon.reflect.visitor.chain.CtFunction;
import spoon.reflect.visitor.filter.NamedElementFilter;
import spoon.support.compiler.FileSystemFile;
import spoon.support.template.Parameters;
import spoon.template.ExpressionTemplate;
import spoon.template.TemplateMatcher;
import spoon.template.TemplateParameter;
import spoon.test.template.testclasses.AnExpressionTemplate;
import spoon.test.template.testclasses.AnotherFieldAccessTemplate;
import spoon.test.template.testclasses.ArrayAccessTemplate;
import spoon.test.template.testclasses.FieldAccessOfInnerClassTemplate;
import spoon.test.template.testclasses.FieldAccessTemplate;
import spoon.test.template.testclasses.InnerClassTemplate;
import spoon.test.template.testclasses.InvocationTemplate;
import spoon.test.template.testclasses.NtonCodeTemplate;
import spoon.test.template.testclasses.ObjectIsNotParamTemplate;
import spoon.test.template.testclasses.SecurityCheckerTemplate;
import spoon.test.template.testclasses.SimpleTemplate;
import spoon.test.template.testclasses.SubStringTemplate;
import spoon.test.template.testclasses.SubstituteLiteralTemplate;
import spoon.test.template.testclasses.SubstituteRootTemplate;
import spoon.test.template.testclasses.TypeReferenceClassAccessTemplate;
import spoon.test.template.testclasses.bounds.CheckBound;
import spoon.test.template.testclasses.bounds.CheckBoundMatcher;
import spoon.test.template.testclasses.bounds.CheckBoundTemplate;
import spoon.test.template.testclasses.bounds.FooBound;
import spoon.test.template.testclasses.constructors.C1;
import spoon.test.template.testclasses.constructors.TemplateWithConstructor;
import spoon.test.template.testclasses.constructors.TemplateWithFieldsAndMethods;
import spoon.test.template.testclasses.constructors.TemplateWithFieldsAndMethods_Wrong;
import spoon.test.template.testclasses.inheritance.InterfaceTemplate;
import spoon.test.template.testclasses.inheritance.SubClass;
import spoon.test.template.testclasses.inheritance.SubTemplate;
import spoon.test.template.testclasses.inheritance.SuperClass;
import spoon.test.template.testclasses.inheritance.SuperTemplate;
import spoon.testing.utils.ModelUtils;


public class TemplateTest {
    @org.junit.Test
    public void testTemplateInheritance() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        Factory factory = spoon.getFactory();
        spoon.getEnvironment().setCommentEnabled(true);
        spoon.createCompiler(factory, SpoonResourceHelper.resources("./src/test/java/spoon/test/template/testclasses/inheritance/SubClass.java", "./src/test/java/spoon/test/template/testclasses/inheritance/SuperClass.java"), SpoonResourceHelper.resources("./src/test/java/spoon/test/template/testclasses/inheritance/SubTemplate.java", "./src/test/java/spoon/test/template/testclasses/inheritance/SuperTemplate.java")).build();
        final java.util.Map<CtElement, String> elementToGeneratedByMember = new java.util.IdentityHashMap<>();
        CtClass<?> superc = factory.Class().get(SuperClass.class);
        new SuperTemplate().addGeneratedBy(true).apply(superc);
        CtMethod<?> addedMethod = superc.getElements(new NamedElementFilter<>(CtMethod.class, "toBeOverriden")).get(0);
        org.junit.Assert.assertEquals("toBeOverriden", addedMethod.getSimpleName());
        elementToGeneratedByMember.put(addedMethod, "#toBeOverriden");
        CtClass<?> subc = factory.Class().get(SubClass.class);
        SubTemplate template = new SubTemplate();
        template.addGeneratedBy(true);
        template.params = new java.util.ArrayList<>();
        CtParameter<Integer> parameter = factory.Core().createParameter();
        parameter.setSimpleName("x");
        parameter.setType(factory.Type().createReference(int.class));
        template.params.add(parameter);
        template.invocation = factory.Code().createInvocation(null, addedMethod.getReference());
        template.intValues = new spoon.reflect.code.CtExpression[2];
        template.intValues[0] = factory.Code().createLiteral(0);
        template.intValues[1] = factory.Code().createLiteral(1);
        template.apply(subc);
        CtMethod<?> addedMethod2 = subc.getElements(new NamedElementFilter<>(CtMethod.class, "toBeOverriden")).get(0);
        org.junit.Assert.assertEquals("toBeOverriden", addedMethod2.getSimpleName());
        org.junit.Assert.assertEquals("super.toBeOverriden()", addedMethod2.getBody().getStatements().get(0).toString());
        elementToGeneratedByMember.put(addedMethod2, "#toBeOverriden");
        CtMethod<?> methodWithTemplatedParameters = subc.getElements(new NamedElementFilter<>(CtMethod.class, "methodWithTemplatedParameters")).get(0);
        org.junit.Assert.assertEquals("methodWithTemplatedParameters", methodWithTemplatedParameters.getSimpleName());
        org.junit.Assert.assertEquals("x", methodWithTemplatedParameters.getParameters().get(0).getSimpleName());
        org.junit.Assert.assertEquals("int x", methodWithTemplatedParameters.getParameters().get(0).toString());
        elementToGeneratedByMember.put(methodWithTemplatedParameters, "#methodWithTemplatedParameters");
        org.junit.Assert.assertEquals(1, subc.getNestedTypes().size());
        elementToGeneratedByMember.put(subc.getNestedTypes().iterator().next(), "$InnerClass");
        org.junit.Assert.assertEquals(1, subc.getMethodsByName("newVarName").size());
        CtMethod<?> varMethod = subc.getMethodsByName("newVarName").get(0);
        elementToGeneratedByMember.put(varMethod, "#var");
        org.junit.Assert.assertEquals("newVarName", varMethod.getComments().get(0).getContent().split("[\\n\\r]+")[0]);
        org.junit.Assert.assertEquals("{@link LinkedList}", varMethod.getComments().get(1).getContent());
        org.junit.Assert.assertEquals("{@link SuperClass#toBeOverriden()}", varMethod.getComments().get(2).getContent());
        org.junit.Assert.assertEquals("java.util.List newVarName = null", methodWithTemplatedParameters.getBody().getStatement(0).toString());
        org.junit.Assert.assertEquals("java.util.LinkedList l = null", methodWithTemplatedParameters.getBody().getStatement(1).toString());
        org.junit.Assert.assertEquals("java.util.List o = ((java.util.LinkedList) (new java.util.LinkedList()))", methodWithTemplatedParameters.getBody().getStatement(2).toString());
        org.junit.Assert.assertEquals("toBeOverriden()", methodWithTemplatedParameters.getBody().getStatement(3).toString());
        CtBlock templatedForEach = methodWithTemplatedParameters.getBody().getStatement(4);
        org.junit.Assert.assertEquals("java.lang.System.out.println(0)", templatedForEach.getStatement(0).toString());
        org.junit.Assert.assertEquals("java.lang.System.out.println(1)", templatedForEach.getStatement(1).toString());
        org.junit.Assert.assertEquals("java.lang.System.out.println(0)", methodWithTemplatedParameters.getBody().getStatement(5).toString());
        org.junit.Assert.assertEquals("java.lang.System.out.println(1)", methodWithTemplatedParameters.getBody().getStatement(6).toString());
        org.junit.Assert.assertEquals("java.lang.System.out.println(0)", ((CtBlock) (methodWithTemplatedParameters.getBody().getStatement(7))).getStatement(0).toString());
        org.junit.Assert.assertEquals("java.lang.System.out.println(1)", ((CtBlock) (methodWithTemplatedParameters.getBody().getStatement(8))).getStatement(0).toString());
        org.junit.Assert.assertFalse(((methodWithTemplatedParameters.getBody().getStatement(9)) instanceof CtBlock));
        org.junit.Assert.assertEquals("java.lang.System.out.println(0)", methodWithTemplatedParameters.getBody().getStatement(9).toString());
        org.junit.Assert.assertFalse(((methodWithTemplatedParameters.getBody().getStatement(10)) instanceof CtBlock));
        org.junit.Assert.assertEquals("java.lang.System.out.println(1)", methodWithTemplatedParameters.getBody().getStatement(10).toString());
        org.junit.Assert.assertTrue(((methodWithTemplatedParameters.getBody().getStatement(11)) instanceof spoon.reflect.code.CtForEach));
        org.junit.Assert.assertEquals("newVarName = o", methodWithTemplatedParameters.getBody().getStatement(12).toString());
        org.junit.Assert.assertEquals("l = ((java.util.LinkedList) (newVarName))", methodWithTemplatedParameters.getBody().getStatement(13).toString());
        CtMethod<?> methodWithFieldAccess = subc.getElements(new NamedElementFilter<>(CtMethod.class, "methodWithFieldAccess")).get(0);
        elementToGeneratedByMember.put(methodWithFieldAccess, "#methodWithFieldAccess");
        elementToGeneratedByMember.put(subc.getField("newVarName"), "#var");
        org.junit.Assert.assertEquals("newVarName = o", methodWithFieldAccess.getBody().getStatement(2).toString());
        org.junit.Assert.assertEquals("l = ((java.util.LinkedList) (newVarName))", methodWithFieldAccess.getBody().getStatement(3).toString());
        class Context {
            int nrTypeMembers = 0;

            int nrOthers = 0;
        }
        final Context ctx = new Context();
        superc.filterChildren(null).forEach((CtElement e) -> {
            if (e instanceof spoon.reflect.declaration.CtTypeMember) {
                (ctx.nrTypeMembers)++;
                assertCommentHasGeneratedBy(e, SuperTemplate.class.getName(), elementToGeneratedByMember);
            }else {
                (ctx.nrOthers)++;
                org.junit.Assert.assertTrue((((e.getDocComment()) == null) || ((e.getDocComment().indexOf("Generated by")) == (-1))));
            }
        });
        org.junit.Assert.assertTrue(((ctx.nrTypeMembers) > 0));
        org.junit.Assert.assertTrue(((ctx.nrOthers) > 0));
        ctx.nrTypeMembers = 0;
        ctx.nrOthers = 0;
        subc.filterChildren(null).forEach((CtElement e) -> {
            if (e instanceof spoon.reflect.declaration.CtTypeMember) {
                (ctx.nrTypeMembers)++;
                assertCommentHasGeneratedBy(e, SubTemplate.class.getName(), elementToGeneratedByMember);
            }else {
                (ctx.nrOthers)++;
                org.junit.Assert.assertTrue((((e.getDocComment()) == null) || ((e.getDocComment().indexOf("Generated by")) == (-1))));
            }
        });
        org.junit.Assert.assertTrue(((ctx.nrTypeMembers) > 0));
        org.junit.Assert.assertTrue(((ctx.nrOthers) > 0));
    }

    private static final java.util.regex.Pattern generatedByRE = java.util.regex.Pattern.compile(".*Generated by ([^$#\\(]+)([^\\(]*)\\(([^\\.]+)\\.java:(\\d+)\\).*", ((java.util.regex.Pattern.MULTILINE) | (java.util.regex.Pattern.DOTALL)));

    private static final java.util.regex.Pattern typeMemberRE = java.util.regex.Pattern.compile("[^\\.$#]+$");

    private void assertCommentHasGeneratedBy(CtElement e, String templateQName, java.util.Map<CtElement, String> elementToGeneratedByMember) {
        String docComment = e.getDocComment();
        String generatedByMember = elementToGeneratedByMember.get(e);
        if (generatedByMember == null) {
            org.junit.Assert.assertEquals("", docComment);
        }else {
            org.junit.Assert.assertNotNull(("Javadoc comment is missing for " + (e.toString())), docComment);
            int idx = docComment.indexOf("Generated by");
            org.junit.Assert.assertTrue(("Javadoc comment doesn\'t contain Generated by. There is:\n" + docComment), (idx >= 0));
            java.util.regex.Matcher m = spoon.test.template.TemplateTest.generatedByRE.matcher(docComment);
            org.junit.Assert.assertTrue(("Unexpected Generated by:\n" + docComment), m.matches());
            org.junit.Assert.assertEquals(templateQName, m.group(1));
            org.junit.Assert.assertEquals(generatedByMember, m.group(2));
            org.junit.Assert.assertTrue(templateQName.endsWith(("." + (m.group(3)))));
            int lineNr = (Integer.parseInt(m.group(4))) - 1;
            org.junit.Assert.assertTrue((lineNr >= 0));
            String[] sourceLines = e.getPosition().getCompilationUnit().getOriginalSourceCode().split("\\r\\n|\\r|\\n");
            org.junit.Assert.assertTrue(((sourceLines.length) > lineNr));
            String sourceLine = sourceLines[lineNr];
            java.util.regex.Matcher m2 = spoon.test.template.TemplateTest.typeMemberRE.matcher(generatedByMember);
            org.junit.Assert.assertTrue(m2.find());
            String memberSimpleName = m2.group();
            org.junit.Assert.assertTrue(((memberSimpleName.length()) > 2));
            org.junit.Assert.assertTrue(((((("Source file " + (e.getPosition().getFile().getAbsolutePath())) + ":") + lineNr) + " doesn't contain member name ") + memberSimpleName), ((sourceLine.indexOf(memberSimpleName)) >= 0));
        }
    }

    @org.junit.Test
    public void testTemplateC1() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        Factory factory = spoon.createFactory();
        spoon.createCompiler(factory, SpoonResourceHelper.resources("./src/test/java/spoon/test/template/testclasses/constructors/C1.java"), SpoonResourceHelper.resources("./src/test/java/spoon/test/template/testclasses/constructors/TemplateWithConstructor.java", "./src/test/java/spoon/test/template/testclasses/constructors/TemplateWithFieldsAndMethods.java")).build();
        CtClass<?> c1 = factory.Class().get(C1.class);
        org.junit.Assert.assertEquals(1, c1.getConstructors().size());
        new TemplateWithConstructor(factory.Type().createReference(Date.class)).apply(c1);
        org.junit.Assert.assertEquals(3, c1.getConstructors().size());
        CtField<?> toBeInserted = c1.getElements(new NamedElementFilter<>(CtField.class, "toBeInserted")).get(0);
        org.junit.Assert.assertEquals(Date.class, toBeInserted.getType().getActualTypeArguments().get(0).getActualClass());
        org.junit.Assert.assertEquals("java.util.List<java.util.Date> toBeInserted = new java.util.ArrayList<java.util.Date>();", toBeInserted.toString());
        new TemplateWithFieldsAndMethods("testparam", factory.Code().createLiteral("testparam2")).apply(c1);
        org.junit.Assert.assertEquals(3, c1.getConstructors().size());
        org.junit.Assert.assertNotNull(c1.getField("fieldToBeInserted"));
        CtMethod<?> m = c1.getMethod("methodToBeInserted");
        org.junit.Assert.assertNotNull(m);
        org.junit.Assert.assertEquals("return \"testparam\"", m.getBody().getStatement(0).toString());
        CtMethod<?> m2 = c1.getMethod("methodToBeInserted2");
        org.junit.Assert.assertNotNull(m2);
        org.junit.Assert.assertEquals("return \"testparam2\"", m2.getBody().getStatement(0).toString());
        new ModelConsistencyChecker(factory.getEnvironment(), false, true).scan(c1);
        org.junit.Assert.assertEquals(0, factory.getEnvironment().getErrorCount());
        org.junit.Assert.assertEquals(0, factory.getEnvironment().getWarningCount());
    }

    @org.junit.Test
    public void testTemplateWithWrongUsedStringParam() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        Factory factory = spoon.createFactory();
        spoon.createCompiler(factory, SpoonResourceHelper.resources("./src/test/java/spoon/test/template/testclasses/constructors/C1.java"), SpoonResourceHelper.resources("./src/test/java/spoon/test/template/testclasses/constructors/TemplateWithFieldsAndMethods_Wrong.java")).build();
        CtClass<?> c1 = factory.Class().get(C1.class);
        new TemplateWithFieldsAndMethods_Wrong("testparam").apply(c1);
        CtMethod<?> m = c1.getMethod("methodToBeInserted");
        org.junit.Assert.assertNotNull(m);
        try {
            m.getBody().getStatement(0).toString();
        } catch (spoon.SpoonException e) {
            org.junit.Assert.assertTrue(("The error description doesn\'t contain name of invalid field. There is:\n" + (e.getMessage())), ((e.getMessage().indexOf("testparam")) >= 0));
        }
    }

    @org.junit.Test
    public void testCheckBoundTemplate() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        Factory factory = spoon.createFactory();
        spoon.createCompiler(factory, SpoonResourceHelper.resources("./src/test/java/spoon/test/template/testclasses/bounds/FooBound.java"), SpoonResourceHelper.resources("./src/test/java/spoon/test/template/testclasses/bounds/CheckBoundTemplate.java")).build();
        CtClass<?> c = factory.Class().get(FooBound.class);
        CtMethod<?> method = c.getMethodsByName("method").get(0);
        org.junit.Assert.assertEquals(1, Parameters.getAllTemplateParameterFields(CheckBoundTemplate.class).size());
        org.junit.Assert.assertEquals(1, Parameters.getAllTemplateParameterFields(CheckBoundTemplate.class, factory).size());
        CheckBoundTemplate t = new CheckBoundTemplate();
        org.junit.Assert.assertTrue(t.isWellFormed());
        org.junit.Assert.assertFalse(t.isValid());
        CtParameter<?> param = method.getParameters().get(0);
        t.setVariable(param);
        org.junit.Assert.assertTrue(t.isValid());
        CtStatement injectedCode = t.apply(null);
        org.junit.Assert.assertTrue((injectedCode instanceof CtIf));
        CtIf ifStmt = ((CtIf) (injectedCode));
        org.junit.Assert.assertEquals("(l.size()) > 10", ifStmt.getCondition().toString());
        method.getBody().insertBegin(injectedCode);
        org.junit.Assert.assertEquals(injectedCode, method.getBody().getStatement(0));
    }

    @org.junit.Test
    public void testTemplateMatcher() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        Factory factory = spoon.getFactory();
        spoon.createCompiler(factory, SpoonResourceHelper.resources("./src/test/java/spoon/test/template/testclasses/bounds/CheckBound.java"), SpoonResourceHelper.resources("./src/test/java/spoon/test/template/testclasses/bounds/CheckBoundMatcher.java")).build();
        {
            CtClass<?> templateKlass = factory.Class().get(CheckBoundMatcher.class);
            CtClass<?> klass = factory.Class().get(CheckBound.class);
            CtIf templateRoot = ((CtIf) (((CtMethod) (templateKlass.getElements(new NamedElementFilter<>(CtMethod.class, "matcher1")).get(0))).getBody().getStatement(0)));
            TemplateMatcher matcher = new TemplateMatcher(templateRoot);
            org.junit.Assert.assertEquals(2, matcher.find(klass).size());
            org.junit.Assert.assertThat(java.util.Arrays.asList("foo", "fbar"), org.hamcrest.CoreMatchers.is(klass.filterChildren(matcher).map((CtElement e) -> getMethodName(e)).list()));
            matcher.forEachMatch(klass, ( match) -> {
                org.junit.Assert.assertTrue(((checkParameters("foo", match, "_col_", "new java.util.ArrayList<>()")) || (checkParameters("fbar", match, "_col_", "l"))));
            });
        }
        {
            CtClass<?> templateKlass = factory.Class().get(CheckBoundMatcher.class);
            CtClass<?> klass = factory.Class().get(CheckBound.class);
            CtIf templateRoot = ((CtIf) (((CtMethod) (templateKlass.getElements(new NamedElementFilter<>(CtMethod.class, "matcher2")).get(0))).getBody().getStatement(0)));
            TemplateMatcher matcher = new TemplateMatcher(templateRoot);
            org.junit.Assert.assertEquals(1, matcher.find(klass).size());
            org.junit.Assert.assertThat(java.util.Arrays.asList("bov"), org.hamcrest.CoreMatchers.is(klass.filterChildren(matcher).map((CtElement e) -> getMethodName(e)).list()));
            matcher.forEachMatch(klass, ( match) -> {
                org.junit.Assert.assertTrue(checkParameters("bov", match, "_col_", "new java.util.ArrayList<>()"));
            });
        }
        {
            CtClass<?> templateKlass = factory.Class().get(CheckBoundMatcher.class);
            CtClass<?> klass = factory.Class().get(CheckBound.class);
            CtIf templateRoot = ((CtIf) (((CtMethod) (templateKlass.getElements(new NamedElementFilter<>(CtMethod.class, "matcher3")).get(0))).getBody().getStatement(0)));
            TemplateMatcher matcher = new TemplateMatcher(templateRoot);
            org.junit.Assert.assertEquals(2, matcher.find(klass).size());
            org.junit.Assert.assertThat(java.util.Arrays.asList("foo", "fbar"), org.hamcrest.CoreMatchers.is(klass.filterChildren(matcher).map((CtElement e) -> getMethodName(e)).list()));
            matcher.forEachMatch(klass, ( match) -> {
                org.junit.Assert.assertTrue(((checkParameters("foo", match, "_x_", "(new java.util.ArrayList<>().size())")) || (checkParameters("fbar", match, "_x_", "(l.size())"))));
            });
        }
        {
            CtClass<?> templateKlass = factory.Class().get(CheckBoundMatcher.class);
            CtClass<?> klass = factory.Class().get(CheckBound.class);
            CtIf templateRoot = ((CtIf) (((CtMethod) (templateKlass.getElements(new NamedElementFilter<>(CtMethod.class, "matcher4")).get(0))).getBody().getStatement(0)));
            TemplateMatcher matcher = new TemplateMatcher(templateRoot);
            org.junit.Assert.assertEquals(3, matcher.find(klass).size());
            org.junit.Assert.assertThat(java.util.Arrays.asList("foo", "foo2", "fbar"), org.hamcrest.CoreMatchers.is(klass.filterChildren(matcher).map((CtElement e) -> getMethodName(e)).list()));
            matcher.forEachMatch(klass, ( match) -> {
                org.junit.Assert.assertTrue((((checkParameters("foo", match, "_x_", "(new java.util.ArrayList<>().size())", "_y_", "10")) || (checkParameters("foo2", match, "_x_", "(new java.util.ArrayList<>().size())", "_y_", "11"))) || (checkParameters("fbar", match, "_x_", "(l.size())", "_y_", "10"))));
            });
        }
        {
            CtClass<?> templateKlass = factory.Class().get(CheckBoundMatcher.class);
            CtClass<?> klass = factory.Class().get(CheckBound.class);
            CtIf templateRoot = ((CtIf) (((CtMethod) (templateKlass.getElements(new NamedElementFilter<>(CtMethod.class, "matcher5")).get(0))).getBody().getStatement(0)));
            TemplateMatcher matcher = new TemplateMatcher(templateRoot);
            org.junit.Assert.assertEquals(6, matcher.find(klass).size());
            org.junit.Assert.assertThat(java.util.Arrays.asList("foo", "foo2", "fbar", "baz", "bou", "bov"), org.hamcrest.CoreMatchers.is(klass.filterChildren(matcher).map((CtElement e) -> getMethodName(e)).list()));
            matcher.forEachMatch(klass, ( match) -> {
                org.junit.Assert.assertTrue(((((((checkParameters("foo", match, "_x_", "(new java.util.ArrayList<>().size())", "_y_", "10", "_block_", "throw new java.lang.IndexOutOfBoundsException();")) || (checkParameters("foo2", match, "_x_", "(new java.util.ArrayList<>().size())", "_y_", "11", "_block_", "throw new java.lang.IndexOutOfBoundsException();"))) || (checkParameters("fbar", match, "_x_", "(l.size())", "_y_", "10", "_block_", "throw new java.lang.IndexOutOfBoundsException();"))) || (checkParameters("baz", match, "_x_", "(new java.util.ArrayList<>().size())", "_y_", "10", "_block_", "{}"))) || (checkParameters("bou", match, "_x_", "(new java.util.ArrayList<>().size())", "_y_", "10", "_block_", "{ java.lang.System.out.println();}"))) || (checkParameters("bov", match, "_x_", "(new java.util.ArrayList<>().size())", "_y_", "10", "_block_", "java.lang.System.out.println();"))));
            });
        }
        {
            CtClass<?> templateKlass = factory.Class().get(CheckBoundMatcher.class);
            CtClass<?> klass = factory.Class().get(CheckBound.class);
            CtIf templateRoot = ((CtIf) (((CtMethod) (templateKlass.getElements(new NamedElementFilter<>(CtMethod.class, "matcher6")).get(0))).getBody().getStatement(0)));
            TemplateMatcher matcher = new TemplateMatcher(templateRoot);
            org.junit.Assert.assertEquals(2, matcher.find(klass).size());
            org.junit.Assert.assertThat(java.util.Arrays.asList("baz", "bou"), org.hamcrest.CoreMatchers.is(klass.filterChildren(matcher).map((CtElement e) -> getMethodName(e)).list()));
            matcher.forEachMatch(klass, ( match) -> {
                org.junit.Assert.assertTrue(((checkParameters("baz", match, "_x_", "(new java.util.ArrayList<>().size())", "_y_", "10", "_stmt_", "null")) || (checkParameters("bou", match, "_x_", "(new java.util.ArrayList<>().size())", "_y_", "10", "_stmt_", "java.lang.System.out.println()"))));
            });
        }
        {
            CtClass<?> templateKlass = factory.Class().get(CheckBoundMatcher.class);
            CtClass<?> klass = factory.Class().get(CheckBound.class);
            CtIf templateRoot = ((CtIf) (((CtMethod) (templateKlass.getElements(new NamedElementFilter<>(CtMethod.class, "matcher7")).get(0))).getBody().getStatement(0)));
            TemplateMatcher matcher = new TemplateMatcher(templateRoot);
            org.junit.Assert.assertEquals(1, matcher.find(klass).size());
            org.junit.Assert.assertThat(java.util.Arrays.asList("bos"), org.hamcrest.CoreMatchers.is(klass.filterChildren(matcher).map((CtElement e) -> getMethodName(e)).list()));
            matcher.forEachMatch(klass, ( match) -> {
                org.junit.Assert.assertTrue(checkParameters("bos", match, "_x_", "(new java.util.ArrayList<>().size())", "_block_", "java.lang.System.out.println();"));
            });
        }
        {
            CtClass<?> templateKlass = factory.Class().get(CheckBoundMatcher.class);
            CtClass<?> klass = factory.Class().get(CheckBound.class);
            CtMethod meth = ((CtMethod) (templateKlass.getElements(new NamedElementFilter<>(CtMethod.class, "matcher3")).get(0)));
            meth.setSimpleName("foo");
            TemplateMatcher matcher = new TemplateMatcher(meth);
            List<CtMethod> ctElements = matcher.find(klass);
            org.junit.Assert.assertEquals(1, ctElements.size());
            org.junit.Assert.assertEquals("foo", ctElements.get(0).getSimpleName());
        }
        {
            CtClass<?> templateKlass = factory.Class().get(CheckBoundMatcher.class);
            CtClass<?> klass = factory.Class().get(CheckBound.class);
            CtMethod meth = ((CtMethod) (templateKlass.getElements(new NamedElementFilter<>(CtMethod.class, "matcher5")).get(0)));
            meth.setSimpleName("f_w_");
            TemplateMatcher matcher = new TemplateMatcher(meth);
            List<CtMethod> ctElements = matcher.find(klass);
            org.junit.Assert.assertEquals(3, ctElements.size());
            org.junit.Assert.assertEquals("foo", ctElements.get(0).getSimpleName());
            org.junit.Assert.assertEquals("foo2", ctElements.get(1).getSimpleName());
            org.junit.Assert.assertEquals("fbar", ctElements.get(2).getSimpleName());
        }
    }

    private boolean checkParameters(String methodName, Match match, String... keyValues) {
        if (methodName.equals(getMethodName(match.getMatchingElement()))) {
            org.junit.Assert.assertEquals("The arguments of keyValues must be in pairs", 0, ((keyValues.length) % 2));
            java.util.Map<String, java.lang.Object> allParams = new java.util.HashMap<>(match.getParameters().asMap());
            int count = (keyValues.length) / 2;
            for (int i = 0; i < count; i++) {
                String key = keyValues[(i * 2)];
                String expectedValue = keyValues[((i * 2) + 1)];
                java.lang.Object realValue = allParams.remove(key);
                org.junit.Assert.assertEquals(expectedValue, ModelUtils.getOptimizedString(realValue));
            }
            org.junit.Assert.assertTrue(("Unexpected parameter values: " + allParams), allParams.isEmpty());
            return true;
        }
        return false;
    }

    private String getMethodName(CtElement e) {
        return e.getParent(CtMethod.class).getSimpleName();
    }

    @org.junit.Test
    public void testExtensionBlock() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new String[]{ "--output-type", "nooutput" });
        launcher.addInputResource("./src/test/java/spoon/test/template/testclasses/logger/Logger.java");
        launcher.addTemplateResource(new FileSystemFile("./src/test/java/spoon/test/template/testclasses/logger/LoggerTemplate.java"));
        launcher.addProcessor(new spoon.test.template.testclasses.logger.LoggerTemplateProcessor());
        launcher.getEnvironment().setSourceClasspath(java.lang.System.getProperty("java.class.path").split(java.io.File.pathSeparator));
        try {
            launcher.run();
        } catch (java.lang.ClassCastException ignored) {
            org.junit.Assert.fail();
        }
        final CtClass<spoon.test.template.testclasses.logger.Logger> aLogger = launcher.getFactory().Class().get(spoon.test.template.testclasses.logger.Logger.class);
        final CtMethod aMethod = aLogger.getMethodsByName("enter").get(0);
        org.junit.Assert.assertTrue(((aMethod.getBody().getStatement(0)) instanceof spoon.reflect.code.CtTry));
        final spoon.reflect.code.CtTry aTry = ((spoon.reflect.code.CtTry) (aMethod.getBody().getStatement(0)));
        org.junit.Assert.assertTrue(((aTry.getFinalizer().getStatement(0)) instanceof spoon.reflect.code.CtInvocation));
        org.junit.Assert.assertEquals("spoon.test.template.testclasses.logger.Logger.exit(\"enter\")", aTry.getFinalizer().getStatement(0).toString());
        org.junit.Assert.assertTrue(((aTry.getBody().getStatement(0)) instanceof spoon.reflect.code.CtInvocation));
        org.junit.Assert.assertEquals("spoon.test.template.testclasses.logger.Logger.enter(\"Logger\", \"enter\")", aTry.getBody().getStatement(0).toString());
        org.junit.Assert.assertTrue(((aTry.getBody().getStatements().size()) > 1));
        org.junit.Assert.assertEquals("java.lang.System.out.println((((\"enter: \" + className) + \" - \") + methodName))", aTry.getBody().getStatement(1).toString());
    }

    @org.junit.Test
    public void testTemplateInterfaces() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        Factory factory = spoon.getFactory();
        spoon.createCompiler(factory, SpoonResourceHelper.resources("./src/test/java/spoon/test/template/testclasses/inheritance/SubClass.java"), SpoonResourceHelper.resources("./src/test/java/spoon/test/template/testclasses/inheritance/InterfaceTemplate.java")).build();
        CtClass<?> superc = factory.Class().get(SuperClass.class);
        InterfaceTemplate interfaceTemplate = new InterfaceTemplate(superc.getFactory());
        interfaceTemplate.apply(superc);
        org.junit.Assert.assertEquals(3, superc.getSuperInterfaces().size());
        org.junit.Assert.assertTrue(superc.getSuperInterfaces().contains(factory.Type().createReference(Comparable.class)));
        org.junit.Assert.assertTrue(superc.getSuperInterfaces().contains(factory.Type().createReference(Serializable.class)));
        org.junit.Assert.assertTrue(superc.getSuperInterfaces().contains(factory.Type().createReference(Remote.class)));
    }

    @org.junit.Test
    public void testTemplateMatcherWithWholePackage() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/java/spoon/test/template/testclasses/ContextHelper.java");
        spoon.addInputResource("./src/test/java/spoon/test/template/testclasses/BServiceImpl.java");
        spoon.addTemplateResource(new FileSystemFile("./src/test/java/spoon/test/template/testclasses/SecurityCheckerTemplate.java"));
        spoon.buildModel();
        Factory factory = spoon.getFactory();
        CtClass<?> templateKlass = factory.Class().get(SecurityCheckerTemplate.class);
        CtMethod templateMethod = ((CtMethod) (templateKlass.getElements(new NamedElementFilter<>(CtMethod.class, "matcher1")).get(0)));
        CtIf templateRoot = ((CtIf) (templateMethod.getBody().getStatement(0)));
        TemplateMatcher matcher = new TemplateMatcher(templateRoot);
        List<CtElement> matches = matcher.find(factory.getModel().getRootPackage());
        org.junit.Assert.assertEquals(1, matches.size());
        CtElement match = matches.get(0);
        org.junit.Assert.assertTrue("Match is not a if", (match instanceof CtIf));
        CtElement matchParent = match.getParent();
        org.junit.Assert.assertTrue("Match parent is not a block", (matchParent instanceof CtBlock));
        CtElement matchParentParent = matchParent.getParent();
        org.junit.Assert.assertTrue("Match grand parent is not a method", (matchParentParent instanceof CtMethod));
        CtMethod methodHello = ((CtMethod) (matchParentParent));
        org.junit.Assert.assertEquals("Match grand parent is not a method called hello", "hello", methodHello.getSimpleName());
        CtElement methodParent = methodHello.getParent();
        org.junit.Assert.assertTrue("Parent of the method is not a class", (methodParent instanceof CtClass));
        CtClass bservice = ((CtClass) (methodParent));
        org.junit.Assert.assertEquals("Parent of the method is not a class called BServiceImpl", "BServiceImpl", bservice.getSimpleName());
    }

    @org.junit.Test
    public void testTemplateMatcherMatchTwoSnippets() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/java/spoon/test/template/testclasses/TwoSnippets.java");
        spoon.addTemplateResource(new FileSystemFile("./src/test/java/spoon/test/template/testclasses/SecurityCheckerTemplate.java"));
        spoon.buildModel();
        Factory factory = spoon.getFactory();
        CtClass<?> templateKlass = factory.Class().get(SecurityCheckerTemplate.class);
        CtMethod templateMethod = ((CtMethod) (templateKlass.getElements(new NamedElementFilter<>(CtMethod.class, "matcher1")).get(0)));
        CtIf templateRoot = ((CtIf) (templateMethod.getBody().getStatement(0)));
        TemplateMatcher matcher = new TemplateMatcher(templateRoot);
        List<CtElement> matches = matcher.find(factory.getModel().getRootPackage());
        org.junit.Assert.assertEquals(2, matches.size());
        CtElement match1 = matches.get(0);
        CtElement match2 = matches.get(1);
        org.junit.Assert.assertTrue(match1.equals(match2));
        matches = factory.getModel().filterChildren(matcher).list();
        org.junit.Assert.assertEquals(2, matches.size());
        match1 = matches.get(0);
        match2 = matches.get(1);
        org.junit.Assert.assertTrue(match1.equals(match2));
    }

    @org.junit.Test
    public void testTemplateInvocationSubstitution() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.addTemplateResource(new FileSystemFile("./src/test/java/spoon/test/template/testclasses/InvocationTemplate.java"));
        spoon.buildModel();
        Factory factory = spoon.getFactory();
        CtClass<?> resultKlass = factory.Class().create("Result");
        new InvocationTemplate(factory.Type().OBJECT, "hashCode").apply(resultKlass);
        CtMethod<?> templateMethod = ((CtMethod<?>) (resultKlass.getElements(new NamedElementFilter<>(CtMethod.class, "invoke")).get(0)));
        CtStatement templateRoot = ((CtStatement) (templateMethod.getBody().getStatement(0)));
        org.junit.Assert.assertEquals("iface.hashCode()", templateRoot.toString());
    }

    @org.junit.Test
    public void testSimpleTemplate() {
        Launcher spoon = new Launcher();
        spoon.addTemplateResource(new FileSystemFile("./src/test/java/spoon/test/template/testclasses/SimpleTemplate.java"));
        spoon.buildModel();
        Factory factory = spoon.getFactory();
        CtClass<?> testSimpleTpl = factory.Class().create("TestSimpleTpl");
        new SimpleTemplate("Hello world").apply(testSimpleTpl);
        java.util.Set<CtMethod<?>> listMethods = testSimpleTpl.getMethods();
        org.junit.Assert.assertEquals(0, testSimpleTpl.getMethodsByName("apply").size());
        org.junit.Assert.assertEquals(1, listMethods.size());
    }

    @org.junit.Test
    public void testSubstitutionInsertAllNtoN() {
        Launcher spoon = new Launcher();
        spoon.addTemplateResource(new FileSystemFile("./src/test/java/spoon/test/template/testclasses/NtonCodeTemplate.java"));
        spoon.addInputResource("./src/test/java/spoon/test/template/testclasses/C.java");
        spoon.buildModel();
        Factory factory = spoon.getFactory();
        CtClass<?> cclass = factory.Class().get("spoon.test.template.testclasses.C");
        new NtonCodeTemplate(cclass.getReference(), 5).apply(cclass);
        java.util.Set<CtMethod<?>> listMethods = cclass.getMethods();
        org.junit.Assert.assertEquals(0, cclass.getMethodsByName("apply").size());
        org.junit.Assert.assertEquals(4, listMethods.size());
    }

    @org.junit.Test
    public void testTemplateArrayAccess() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.addTemplateResource(new FileSystemFile("./src/test/java/spoon/test/template/testclasses/ArrayAccessTemplate.java"));
        spoon.buildModel();
        Factory factory = spoon.getFactory();
        CtClass<?> resultKlass = factory.Class().create("Result");
        CtClass<?> templateClass = factory.Class().get(ArrayAccessTemplate.class);
        TemplateParameter[] params = templateClass.getMethod("sampleBlocks").getBody().getStatements().toArray(new TemplateParameter[0]);
        new ArrayAccessTemplate(params).apply(resultKlass);
        CtMethod<?> m = resultKlass.getMethod("method");
        org.junit.Assert.assertEquals(2, m.getBody().getStatements().size());
        org.junit.Assert.assertTrue(((m.getBody().getStatements().get(0)) instanceof CtBlock));
        org.junit.Assert.assertEquals("int i = 0", ((CtBlock) (m.getBody().getStatements().get(0))).getStatement(0).toString());
        org.junit.Assert.assertTrue(((m.getBody().getStatements().get(1)) instanceof CtBlock));
        org.junit.Assert.assertEquals("java.lang.String s = \"Spoon is cool!\"", ((CtBlock) (m.getBody().getStatements().get(1))).getStatement(0).toString());
        CtMethod<?> m2 = resultKlass.getMethod("method2");
        org.junit.Assert.assertEquals("java.lang.System.out.println(\"second\")", m2.getBody().getStatement(0).toString());
        org.junit.Assert.assertEquals("java.lang.System.out.println(null)", m2.getBody().getStatement(1).toString());
    }

    @org.junit.Test
    public void testSubstituteInnerClass() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.addTemplateResource(new FileSystemFile("./src/test/java/spoon/test/template/testclasses/InnerClassTemplate.java"));
        spoon.buildModel();
        Factory factory = spoon.getFactory();
        CtClass<?> result = factory.Class().create("x.Result");
        new InnerClassTemplate().apply(result);
        org.junit.Assert.assertEquals(1, result.getNestedTypes().size());
        CtType<?> innerType = result.getNestedType("Inner");
        org.junit.Assert.assertNotNull(innerType);
        CtField<?> innerField = innerType.getField("innerField");
        org.junit.Assert.assertNotNull(innerField);
        org.junit.Assert.assertSame(innerType, innerField.getDeclaringType());
        CtFieldReference<?> fr = innerType.filterChildren((CtFieldReference<?> e) -> true).first();
        org.junit.Assert.assertEquals("x.Result$Inner", fr.getDeclaringType().getQualifiedName());
    }

    @org.junit.Test
    public void testStatementTemplateRootSubstitution() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.addTemplateResource(new FileSystemFile("./src/test/java/spoon/test/template/testclasses/SubstituteRootTemplate.java"));
        spoon.buildModel();
        Factory factory = spoon.getFactory();
        CtClass<?> templateClass = factory.Class().get(SubstituteRootTemplate.class);
        CtBlock<Void> templateParam = ((CtBlock) (templateClass.getMethod("sampleBlock").getBody()));
        CtClass<?> resultKlass = factory.Class().create("Result");
        CtStatement result = new SubstituteRootTemplate(templateParam).apply(resultKlass);
        org.junit.Assert.assertEquals("java.lang.String s = \"Spoon is cool!\"", ((CtBlock) (result)).getStatement(0).toString());
    }

    @org.junit.Test
    public void testExpressionTemplate() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.addTemplateResource(new FileSystemFile("./src/test/java/spoon/test/template/testclasses/AnExpressionTemplate.java"));
        spoon.buildModel();
        Factory factory = spoon.getFactory();
        CtClass<? extends ExpressionTemplate<?>> templateClass = factory.Class().get(AnExpressionTemplate.class);
        org.junit.Assert.assertEquals("new java.lang.String(exp.S())", ExpressionTemplate.getExpression(templateClass).toString());
        CtClass<?> resultKlass = factory.Class().create("Result");
        spoon.reflect.code.CtExpression result = new AnExpressionTemplate(factory.createCodeSnippetExpression("\"Spoon is cool!\"")).apply(resultKlass);
        org.junit.Assert.assertEquals("new java.lang.String(\"Spoon is cool!\")", result.toString());
    }

    @org.junit.Test
    public void createTypeFromTemplate() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new String[]{ "--output-type", "nooutput" });
        launcher.addTemplateResource(new spoon.support.compiler.FileSystemFolder("./src/test/java/spoon/test/template/testclasses/types"));
        launcher.buildModel();
        Factory factory = launcher.getFactory();
        java.util.Map<String, java.lang.Object> parameters = new java.util.HashMap<>();
        parameters.put("someMethod", "genMethod");
        final CtType<?> aIfaceModel = launcher.getFactory().Interface().get(spoon.test.template.testclasses.types.AnIfaceModel.class);
        CtType<?> genIface = spoon.template.Substitution.createTypeFromTemplate("generated.GenIface", aIfaceModel, parameters);
        org.junit.Assert.assertNotNull(genIface);
        org.junit.Assert.assertSame(genIface, factory.Type().get("generated.GenIface"));
        CtMethod<?> generatedIfaceMethod = genIface.getMethod("genMethod");
        org.junit.Assert.assertNotNull(generatedIfaceMethod);
        org.junit.Assert.assertNull(genIface.getMethod("someMethod"));
        parameters.put("AnIfaceModel", genIface.getReference());
        final CtType<?> aClassModel = launcher.getFactory().Class().get(spoon.test.template.testclasses.types.AClassModel.class);
        CtType<?> genClass = spoon.template.Substitution.createTypeFromTemplate("generated.GenClass", aClassModel, parameters);
        org.junit.Assert.assertNotNull(genClass);
        org.junit.Assert.assertSame(genClass, factory.Type().get("generated.GenClass"));
        CtMethod<?> generatedClassMethod = genClass.getMethod("genMethod");
        org.junit.Assert.assertNotNull(generatedClassMethod);
        org.junit.Assert.assertNull(genClass.getMethod("someMethod"));
        org.junit.Assert.assertTrue((generatedIfaceMethod != generatedClassMethod));
        org.junit.Assert.assertTrue(generatedClassMethod.isOverriding(generatedIfaceMethod));
        parameters.put("case1", "GOOD");
        parameters.put("case2", "BETTER");
        final CtType<?> aEnumModel = launcher.getFactory().Type().get(spoon.test.template.testclasses.types.AnEnumModel.class);
        spoon.reflect.declaration.CtEnum<?> genEnum = ((spoon.reflect.declaration.CtEnum<?>) (spoon.template.Substitution.createTypeFromTemplate("generated.GenEnum", aEnumModel, parameters)));
        org.junit.Assert.assertNotNull(genEnum);
        org.junit.Assert.assertSame(genEnum, factory.Type().get("generated.GenEnum"));
        org.junit.Assert.assertEquals(2, genEnum.getEnumValues().size());
        org.junit.Assert.assertEquals("GOOD", genEnum.getEnumValues().get(0).getSimpleName());
        org.junit.Assert.assertEquals("BETTER", genEnum.getEnumValues().get(1).getSimpleName());
    }

    @org.junit.Test
    public void substituteStringLiteral() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.addTemplateResource(new FileSystemFile("./src/test/java/spoon/test/template/testclasses/SubstituteLiteralTemplate.java"));
        spoon.buildModel();
        Factory factory = spoon.getFactory();
        {
            final CtClass<?> result = ((CtClass<?>) (new SubstituteLiteralTemplate("value1").apply(factory.createClass())));
            org.junit.Assert.assertEquals("java.lang.String stringField1 = \"value1\";", result.getField("stringField1").toString());
            org.junit.Assert.assertEquals("java.lang.String stringField2 = \"Substring value1 is substituted too - value1\";", result.getField("stringField2").toString());
            org.junit.Assert.assertEquals("java.lang.System.out.println(spoon.test.template.testclasses.Params.value1())", result.getMethodsByName("m1").get(0).getBody().getStatement(0).toString());
        }
        {
            final CtClass<?> result = ((CtClass<?>) (new SubstituteLiteralTemplate(factory.createLiteral("value2")).apply(factory.createClass())));
            org.junit.Assert.assertEquals("java.lang.String stringField1 = \"value2\";", result.getField("stringField1").toString());
            org.junit.Assert.assertEquals("java.lang.String stringField2 = \"Substring value2 is substituted too - value2\";", result.getField("stringField2").toString());
            org.junit.Assert.assertEquals("java.lang.System.out.println(\"value2\")", result.getMethodsByName("m1").get(0).getBody().getStatement(0).toString());
        }
        {
            final CtClass<?> result = ((CtClass<?>) (new SubstituteLiteralTemplate(factory.Type().createReference("some.ignored.package.TypeName")).apply(factory.createClass())));
            org.junit.Assert.assertEquals("java.lang.String stringField1 = \"TypeName\";", result.getField("stringField1").toString());
            org.junit.Assert.assertEquals("java.lang.String stringField2 = \"Substring TypeName is substituted too - TypeName\";", result.getField("stringField2").toString());
            org.junit.Assert.assertEquals("java.lang.System.out.println(some.ignored.package.TypeName.class)", result.getMethodsByName("m1").get(0).getBody().getStatement(0).toString());
        }
        {
            final CtClass<?> result = ((CtClass<?>) (new SubstituteLiteralTemplate(factory.createLiteral(7)).apply(factory.createClass())));
            org.junit.Assert.assertEquals("java.lang.String stringField1 = \"7\";", result.getField("stringField1").toString());
            org.junit.Assert.assertEquals("java.lang.String stringField2 = \"Substring 7 is substituted too - 7\";", result.getField("stringField2").toString());
            org.junit.Assert.assertEquals("java.lang.System.out.println(7)", result.getMethodsByName("m1").get(0).getBody().getStatement(0).toString());
        }
    }

    @org.junit.Test
    public void substituteSubString() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.addTemplateResource(new FileSystemFile("./src/test/java/spoon/test/template/testclasses/SubStringTemplate.java"));
        spoon.buildModel();
        Factory factory = spoon.getFactory();
        {
            final CtClass<?> result = ((CtClass<?>) (new SubStringTemplate("A").apply(factory.createClass())));
            org.junit.Assert.assertEquals("java.lang.String m_A = \"A is here more times: A\";", result.getField("m_A").toString());
            CtMethod<?> method1 = result.getMethodsByName("setA").get(0);
            org.junit.Assert.assertEquals("setA", method1.getSimpleName());
            org.junit.Assert.assertEquals("java.lang.String p_A", method1.getParameters().get(0).toString());
            org.junit.Assert.assertEquals("this.m_A = p_A", method1.getBody().getStatement(0).toString());
            org.junit.Assert.assertEquals("setA(\"The A is here too\")", result.getMethodsByName("m").get(0).getBody().getStatements().get(0).toString());
        }
        {
            final CtClass<?> result = ((CtClass<?>) (new SubStringTemplate(factory.Type().OBJECT.getTypeDeclaration()).apply(factory.createClass())));
            org.junit.Assert.assertEquals("java.lang.String m_Object = \"Object is here more times: Object\";", result.getField("m_Object").toString());
            CtMethod<?> method1 = result.getMethodsByName("setObject").get(0);
            org.junit.Assert.assertEquals("setObject", method1.getSimpleName());
            org.junit.Assert.assertEquals("java.lang.String p_Object", method1.getParameters().get(0).toString());
            org.junit.Assert.assertEquals("this.m_Object = p_Object", method1.getBody().getStatement(0).toString());
            org.junit.Assert.assertEquals("setObject(\"The Object is here too\")", result.getMethodsByName("m").get(0).getBody().getStatements().get(0).toString());
        }
        {
            final CtClass<?> result = ((CtClass<?>) (new SubStringTemplate(factory.Type().OBJECT).apply(factory.createClass())));
            org.junit.Assert.assertEquals("java.lang.String m_Object = \"Object is here more times: Object\";", result.getField("m_Object").toString());
            CtMethod<?> method1 = result.getMethodsByName("setObject").get(0);
            org.junit.Assert.assertEquals("setObject", method1.getSimpleName());
            org.junit.Assert.assertEquals("java.lang.String p_Object", method1.getParameters().get(0).toString());
            org.junit.Assert.assertEquals("this.m_Object = p_Object", method1.getBody().getStatement(0).toString());
            org.junit.Assert.assertEquals("setObject(\"The Object is here too\")", result.getMethodsByName("m").get(0).getBody().getStatements().get(0).toString());
        }
        {
            final CtClass<?> result = ((CtClass<?>) (new SubStringTemplate(factory.createLiteral("Xxx")).apply(factory.createClass())));
            org.junit.Assert.assertEquals("java.lang.String m_Xxx = \"Xxx is here more times: Xxx\";", result.getField("m_Xxx").toString());
            CtMethod<?> method1 = result.getMethodsByName("setXxx").get(0);
            org.junit.Assert.assertEquals("setXxx", method1.getSimpleName());
            org.junit.Assert.assertEquals("java.lang.String p_Xxx", method1.getParameters().get(0).toString());
            org.junit.Assert.assertEquals("this.m_Xxx = p_Xxx", method1.getBody().getStatement(0).toString());
            org.junit.Assert.assertEquals("setXxx(\"The Xxx is here too\")", result.getMethodsByName("m").get(0).getBody().getStatements().get(0).toString());
        }
        {
            SubStringTemplate template = new SubStringTemplate(factory.createSwitch());
            try {
                template.apply(factory.createClass());
                org.junit.Assert.fail();
            } catch (spoon.SpoonException e) {
            }
        }
    }

    @org.junit.Test
    public void testObjectIsNotParamTemplate() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.addTemplateResource(new FileSystemFile("./src/test/java/spoon/test/template/testclasses/ObjectIsNotParamTemplate.java"));
        spoon.buildModel();
        Factory factory = spoon.getFactory();
        final CtClass<?> result = ((CtClass<?>) (new ObjectIsNotParamTemplate().apply(factory.createClass())));
        org.junit.Assert.assertEquals(0, result.getMethodsByName("methXXXd").size());
        org.junit.Assert.assertEquals(1, result.getMethodsByName("method").size());
    }

    @org.junit.Test
    public void testFieldAccessNameSubstitution() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.addTemplateResource(new FileSystemFile("./src/test/java/spoon/test/template/testclasses/FieldAccessTemplate.java"));
        spoon.buildModel();
        Factory factory = spoon.getFactory();
        {
            final CtClass<?> result = ((CtClass<?>) (new FieldAccessTemplate("value").apply(factory.Class().create("x.X"))));
            org.junit.Assert.assertEquals("int value;", result.getField("value").toString());
            org.junit.Assert.assertEquals("value = 7", result.getMethodsByName("m").get(0).getBody().getStatement(0).toString());
        }
    }

    @org.junit.Test
    public void testFieldAccessNameSubstitutionInInnerClass() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.addTemplateResource(new FileSystemFile("./src/test/java/spoon/test/template/testclasses/FieldAccessOfInnerClassTemplate.java"));
        spoon.buildModel();
        Factory factory = spoon.getFactory();
        {
            final CtClass<?> result = ((CtClass<?>) (new FieldAccessOfInnerClassTemplate("value").apply(factory.Class().create("x.X"))));
            final CtClass<?> innerClass = result.getNestedType("Inner");
            org.junit.Assert.assertEquals("int value;", innerClass.getField("value").toString());
            org.junit.Assert.assertEquals("value = 7", innerClass.getMethodsByName("m").get(0).getBody().getStatement(0).toString());
        }
    }

    @org.junit.Test
    public void testAnotherFieldAccessNameSubstitution() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.addTemplateResource(new FileSystemFile("./src/test/java/spoon/test/template/testclasses/AnotherFieldAccessTemplate.java"));
        spoon.buildModel();
        Factory factory = spoon.getFactory();
        {
            final CtClass<?> result = ((CtClass<?>) (new AnotherFieldAccessTemplate().apply(factory.Class().create("x.X"))));
            org.junit.Assert.assertEquals("int x;", result.getField("x").toString());
            org.junit.Assert.assertEquals("int m_x;", result.getField("m_x").toString());
            org.junit.Assert.assertEquals("java.lang.System.out.println(((x) + (m_x)))", result.getAnonymousExecutables().get(0).getBody().getStatement(0).toString());
        }
    }

    @org.junit.Test
    public void substituteTypeAccessReference() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.addTemplateResource(new FileSystemFile("./src/test/java/spoon/test/template/testclasses/TypeReferenceClassAccessTemplate.java"));
        String outputDir = "./target/spooned/test/template/testclasses";
        spoon.setSourceOutputDirectory(outputDir);
        spoon.buildModel();
        Factory factory = spoon.getFactory();
        CtTypeReference<?> typeRef = factory.Type().createReference("spoon.test.template.TypeReferenceClassAccess$Example");
        typeRef.addActualTypeArgument(factory.Type().DATE);
        final CtClass<?> result = ((CtClass<?>) (new TypeReferenceClassAccessTemplate(typeRef).apply(factory.Class().create("spoon.test.template.TypeReferenceClassAccess"))));
        spoon.prettyprint();
        ModelUtils.canBeBuilt(outputDir, 8);
        CtMethod<?> method = result.getMethodsByName("someMethod").get(0);
        org.junit.Assert.assertEquals("spoon.test.template.TypeReferenceClassAccess.Example<java.util.Date>", method.getType().toString());
        org.junit.Assert.assertEquals("spoon.test.template.TypeReferenceClassAccess.Example<java.util.Date>", method.getParameters().get(0).getType().toString());
        org.junit.Assert.assertEquals("o = spoon.test.template.TypeReferenceClassAccess.Example.out", method.getBody().getStatement(0).toString());
        org.junit.Assert.assertEquals("spoon.test.template.TypeReferenceClassAccess.Example<java.util.Date> ret = new spoon.test.template.TypeReferenceClassAccess.Example<java.util.Date>()", method.getBody().getStatement(1).toString());
        org.junit.Assert.assertEquals("o = spoon.test.template.TypeReferenceClassAccess.Example.currentTimeMillis()", method.getBody().getStatement(2).toString());
        org.junit.Assert.assertEquals("o = spoon.test.template.TypeReferenceClassAccess.Example.class", method.getBody().getStatement(3).toString());
        org.junit.Assert.assertEquals("o = (o) instanceof spoon.test.template.TypeReferenceClassAccess.Example<?>", method.getBody().getStatement(4).toString());
        org.junit.Assert.assertEquals("java.util.function.Supplier<java.lang.Long> p = spoon.test.template.TypeReferenceClassAccess.Example::currentTimeMillis", method.getBody().getStatement(5).toString());
    }
}

