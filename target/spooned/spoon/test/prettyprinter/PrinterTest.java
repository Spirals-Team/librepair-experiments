package spoon.test.prettyprinter;


import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import spoon.Launcher;
import spoon.compiler.SpoonResourceHelper;
import spoon.reflect.declaration.CtType;
import spoon.reflect.factory.Factory;
import spoon.reflect.visitor.DefaultJavaPrettyPrinter;
import spoon.reflect.visitor.DefaultTokenWriter;
import spoon.reflect.visitor.ElementPrinterHelper;
import spoon.reflect.visitor.PrettyPrinter;
import spoon.reflect.visitor.PrinterHelper;
import spoon.reflect.visitor.TokenWriter;
import spoon.testing.utils.ModelUtils;


public class PrinterTest {
    @org.junit.Test
    public void testPrettyPrinter() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        Factory factory = spoon.createFactory();
        spoon.createCompiler(factory, SpoonResourceHelper.resources("./src/test/java/spoon/test/annotation/testclasses/PersistenceProperty.java", "./src/test/java/spoon/test/prettyprinter/Validation.java")).build();
        for (CtType<?> t : factory.Type().getAll()) {
            t.toString();
        }
        org.junit.Assert.assertEquals(0, factory.getEnvironment().getWarningCount());
        org.junit.Assert.assertEquals(0, factory.getEnvironment().getErrorCount());
    }

    @org.junit.Test
    public void testChangeAutoImportModeWorks() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.getEnvironment().setAutoImports(false);
        PrettyPrinter printer = spoon.createPrettyPrinter();
        spoon.addInputResource("./src/test/java/spoon/test/prettyprinter/testclasses/AClass.java");
        spoon.buildModel();
        CtType element = spoon.getFactory().Class().getAll().get(0);
        List<CtType<?>> toPrint = new java.util.ArrayList<>();
        toPrint.add(element);
        printer.calculate(element.getPosition().getCompilationUnit(), toPrint);
        String result = printer.getResult();
        org.junit.Assert.assertTrue(("The result should not contain imports: " + result), (!(result.contains("import java.util.List;"))));
        spoon.getEnvironment().setAutoImports(true);
        printer = spoon.createPrettyPrinter();
        printer.calculate(element.getPosition().getCompilationUnit(), toPrint);
        result = printer.getResult();
        org.junit.Assert.assertTrue(("The result should now contain imports: " + result), result.contains("import java.util.List;"));
    }

    @org.junit.Test
    public void testFQNModeWriteFQNConstructorInCtVisitor() {
        Launcher spoon = new Launcher();
        PrettyPrinter printer = spoon.createPrettyPrinter();
        spoon.getEnvironment().setAutoImports(false);
        spoon.addInputResource("./src/main/java/spoon/support/visitor/replace/ReplacementVisitor.java");
        spoon.buildModel();
        CtType element = spoon.getFactory().Class().getAll().get(0);
        List<CtType<?>> toPrint = new java.util.ArrayList<>();
        toPrint.add(element);
        printer.calculate(element.getPosition().getCompilationUnit(), toPrint);
        String result = printer.getResult();
        org.junit.Assert.assertTrue(("The result should contain FQN for constructor: " + result), result.contains("new spoon.support.visitor.replace.ReplacementVisitor("));
        org.junit.Assert.assertTrue(("The result should not contain reduced constructors: " + result), (!(result.contains("new ReplacementVisitor("))));
    }

    @org.junit.Test
    public void testAutoimportModeDontImportUselessStatic() {
        Launcher spoon = new Launcher();
        spoon.getEnvironment().setAutoImports(true);
        PrettyPrinter printer = spoon.createPrettyPrinter();
        spoon.addInputResource("./src/test/java/spoon/test/prettyprinter/testclasses/ImportStatic.java");
        spoon.buildModel();
        CtType element = spoon.getFactory().Class().getAll().get(0);
        List<CtType<?>> toPrint = new java.util.ArrayList<>();
        toPrint.add(element);
        printer.calculate(element.getPosition().getCompilationUnit(), toPrint);
        String result = printer.getResult();
        org.junit.Assert.assertTrue("The result should not contain import static: ", (!(result.contains("import static spoon.test.prettyprinter.testclasses.sub.Constants.READY"))));
        org.junit.Assert.assertTrue("The result should contain import type: ", result.contains("import spoon.test.prettyprinter.testclasses.sub.Constants"));
        org.junit.Assert.assertTrue("The result should contain import static assertTrue: ", result.contains("import static org.junit.Assert.assertTrue;"));
        org.junit.Assert.assertTrue("The result should contain assertTrue(...): ", result.contains("assertTrue(\"blabla\".equals(\"toto\"));"));
        org.junit.Assert.assertTrue(("The result should use System.out.println(Constants.READY): " + result), result.contains("System.out.println(Constants.READY);"));
    }

    @org.junit.Test
    public void testRuleCanBeBuild() {
        Launcher spoon = new Launcher();
        PrettyPrinter printer = spoon.createPrettyPrinter();
        spoon.getEnvironment().setAutoImports(true);
        String output = "./target/spoon-rule/";
        spoon.addInputResource("./src/test/java/spoon/test/prettyprinter/testclasses/Rule.java");
        spoon.setSourceOutputDirectory(output);
        spoon.run();
        CtType element = spoon.getFactory().Class().getAll().get(0);
        List<CtType<?>> toPrint = new java.util.ArrayList<>();
        toPrint.add(element);
        printer.calculate(element.getPosition().getCompilationUnit(), toPrint);
        String result = printer.getResult();
        org.junit.Assert.assertTrue(("The result should contain direct this accessor for field: " + result), (!(result.contains("Rule.Phoneme.this.phonemeText"))));
        ModelUtils.canBeBuilt(output, 7);
    }

    @org.junit.Test
    public void testJDTBatchCompilerCanBeBuild() {
        Launcher spoon = new Launcher();
        PrettyPrinter printer = spoon.createPrettyPrinter();
        spoon.getEnvironment().setAutoImports(false);
        String output = "./target/spoon-jdtbatchcompiler/";
        spoon.addInputResource("./src/main/java/spoon/support/compiler/jdt/JDTBatchCompiler.java");
        spoon.setSourceOutputDirectory(output);
        spoon.run();
        CtType element = spoon.getFactory().Class().getAll().get(0);
        List<CtType<?>> toPrint = new java.util.ArrayList<>();
        toPrint.add(element);
        printer.calculate(element.getPosition().getCompilationUnit(), toPrint);
        String result = printer.getResult();
        ModelUtils.canBeBuilt(output, 7);
    }

    @org.junit.Test
    public void testPrintingOfOrphanFieldReference() throws java.lang.Exception {
        CtType<?> type = ModelUtils.buildClass(spoon.test.prettyprinter.testclasses.MissingVariableDeclaration.class);
        type.getField("testedField").delete();
        org.junit.Assert.assertEquals("/* ERROR: Missing field \"testedField\", please check your model. The code may not compile. */ testedField = 1", type.getMethodsByName("failingMethod").get(0).getBody().getStatement(0).toString());
    }

    private final java.util.Set<String> separators = new java.util.HashSet<>(Arrays.asList("->", "::", "..."));

    {
        "(){}[];,.:@=<>?&|".chars().forEach(( c) -> separators.add(new String(java.lang.Character.toChars(c))));
    }

    private final java.util.Set<String> operators = new java.util.HashSet<>(Arrays.asList("=", ">", "<", "!", "~", "?", ":", "==", "<=", ">=", "!=", "&&", "||", "++", "--", "+", "-", "*", "/", "&", "|", "^", "%", "<<", ">>", ">>>", "+=", "-=", "*=", "/=", "&=", "|=", "^=", "%=", "<<=", ">>=", ">>>=", "instanceof"));

    private final String[] javaKeywordsJoined = new String[]{ "abstract continue for new switch", "assert default goto package synchronized", "boolean do if private this", "break double implements protected throw", "byte else import public throws", "case enum instanceof return transient", "catch extends int short try", "char final interface static void", "class finally long strictfp volatile", "const float native super while" };

    private final java.util.Set<String> javaKeywords = new java.util.HashSet<>();

    {
        for (String str : javaKeywordsJoined) {
            java.util.StringTokenizer st = new java.util.StringTokenizer(str, " ");
            while (st.hasMoreTokens()) {
                javaKeywords.add(st.nextToken());
            } 
        }
    }

    @org.junit.Test
    public void testPrinterTokenListener() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        Factory factory = spoon.createFactory();
        spoon.createCompiler(factory, SpoonResourceHelper.resources("./src/test/java/spoon/test/annotation/testclasses/", "./src/test/java/spoon/test/prettyprinter/")).build();
        org.junit.Assert.assertTrue(((factory.Type().getAll().size()) > 0));
        for (CtType<?> t : factory.Type().getAll()) {
            DefaultJavaPrettyPrinter pp = new DefaultJavaPrettyPrinter(factory.getEnvironment());
            pp.calculate(t.getPosition().getCompilationUnit(), java.util.Collections.singletonList(t));
            String standardPrintedResult = pp.getResult();
            java.lang.StringBuilder allTokens = new java.lang.StringBuilder();
            pp.setPrinterTokenWriter(new TokenWriter() {
                String lastToken;

                PrinterHelper printerHelper = new PrinterHelper(factory.getEnvironment());

                @java.lang.Override
                public TokenWriter writeSeparator(String separator) {
                    checkRepeatingOfTokens("writeSeparator");
                    checkTokenWhitespace(separator, false);
                    org.junit.Assert.assertTrue(("Unexpected separator: " + separator), separators.contains(separator));
                    handleTabs();
                    allTokens.append(separator);
                    return this;
                }

                @java.lang.Override
                public TokenWriter writeOperator(String operator) {
                    checkRepeatingOfTokens("writeOperator");
                    checkTokenWhitespace(operator, false);
                    org.junit.Assert.assertTrue(("Unexpected operator: " + operator), operators.contains(operator));
                    handleTabs();
                    allTokens.append(operator);
                    return this;
                }

                @java.lang.Override
                public TokenWriter writeLiteral(String literal) {
                    checkRepeatingOfTokens("writeLiteral");
                    org.junit.Assert.assertTrue(((literal.length()) > 0));
                    handleTabs();
                    allTokens.append(literal);
                    return this;
                }

                @java.lang.Override
                public TokenWriter writeKeyword(String keyword) {
                    checkRepeatingOfTokens("writeKeyword");
                    checkTokenWhitespace(keyword, false);
                    org.junit.Assert.assertTrue(("Unexpected java keyword: " + keyword), javaKeywords.contains(keyword));
                    handleTabs();
                    allTokens.append(keyword);
                    return this;
                }

                @java.lang.Override
                public TokenWriter writeIdentifier(String identifier) {
                    checkRepeatingOfTokens("writeIdentifier");
                    checkTokenWhitespace(identifier, false);
                    for (int i = 0; i < (identifier.length()); i++) {
                        char c = identifier.charAt(i);
                        if (i == 0) {
                            org.junit.Assert.assertTrue(java.lang.Character.isJavaIdentifierStart(c));
                        }else {
                            org.junit.Assert.assertTrue(java.lang.Character.isJavaIdentifierPart(c));
                        }
                    }
                    org.junit.Assert.assertTrue(("Keyword found in Identifier: " + identifier), ((javaKeywords.contains(identifier)) == false));
                    handleTabs();
                    allTokens.append(identifier);
                    return this;
                }

                @java.lang.Override
                public TokenWriter writeComment(spoon.reflect.code.CtComment comment) {
                    checkRepeatingOfTokens("writeComment");
                    DefaultTokenWriter sptw = new DefaultTokenWriter(new PrinterHelper(factory.getEnvironment()));
                    PrinterHelper ph = sptw.getPrinterHelper();
                    ph.setLineSeparator(getPrinterHelper().getLineSeparator());
                    ph.setTabCount(getPrinterHelper().getTabCount());
                    sptw.writeComment(comment);
                    handleTabs();
                    allTokens.append(sptw.getPrinterHelper().toString());
                    return this;
                }

                @java.lang.Override
                public TokenWriter writeln() {
                    checkRepeatingOfTokens("writeln");
                    allTokens.append(getPrinterHelper().getLineSeparator());
                    lastTokenWasEOL = true;
                    return this;
                }

                private boolean lastTokenWasEOL = true;

                private int tabCount = 0;

                public TokenWriter handleTabs() {
                    if (lastTokenWasEOL) {
                        lastTokenWasEOL = false;
                        for (int i = 0; i < (tabCount); i++) {
                            if (factory.getEnvironment().isUsingTabulations()) {
                                allTokens.append('\t');
                            }else {
                                for (int j = 0; j < (factory.getEnvironment().getTabulationSize()); j++) {
                                    allTokens.append(' ');
                                }
                            }
                        }
                    }
                    return this;
                }

                @java.lang.Override
                public TokenWriter writeCodeSnippet(String token) {
                    checkRepeatingOfTokens("writeCodeSnippet");
                    org.junit.Assert.assertTrue(((token.length()) > 0));
                    handleTabs();
                    allTokens.append(token);
                    return this;
                }

                @java.lang.Override
                public TokenWriter incTab() {
                    (tabCount)++;
                    return this;
                }

                @java.lang.Override
                public TokenWriter decTab() {
                    (tabCount)--;
                    return this;
                }

                @java.lang.Override
                public PrinterHelper getPrinterHelper() {
                    return printerHelper;
                }

                @java.lang.Override
                public void reset() {
                    printerHelper.reset();
                }

                @java.lang.Override
                public TokenWriter writeSpace() {
                    checkRepeatingOfTokens("writeWhitespace");
                    allTokens.append(' ');
                    return this;
                }

                private void checkRepeatingOfTokens(String tokenType) {
                    if (((("writeln".equals(tokenType)) || ("writeIdentifier".equals(tokenType))) || ("writeSeparator".equals(tokenType))) || ("writeWhitespace".equals(tokenType))) {
                    }else {
                        org.junit.Assert.assertTrue(((("Two tokens of same type current:" + tokenType) + " ") + (allTokens.toString())), ((tokenType.equals(this.lastToken)) == false));
                    }
                    this.lastToken = tokenType;
                }
            });
            pp.calculate(t.getPosition().getCompilationUnit(), java.util.Collections.singletonList(t));
            String withEmptyListenerResult = pp.getResult();
            org.junit.Assert.assertEquals(0, withEmptyListenerResult.length());
            org.junit.Assert.assertEquals(standardPrintedResult, allTokens.toString());
        }
    }

    private void checkTokenWhitespace(String stringToken, boolean isWhitespace) {
        org.junit.Assert.assertTrue(((stringToken.length()) > 0));
        for (int i = 0; i < (stringToken.length()); i++) {
            char c = stringToken.charAt(i);
            if (isWhitespace) {
                org.junit.Assert.assertTrue(((java.lang.Character.isWhitespace(c)) == true));
            }else {
                org.junit.Assert.assertTrue(((java.lang.Character.isWhitespace(c)) == false));
            }
        }
    }

    @org.junit.Test
    public void testListPrinter() {
        Launcher spoon = new Launcher();
        DefaultJavaPrettyPrinter pp = ((DefaultJavaPrettyPrinter) (spoon.createPrettyPrinter()));
        PrinterHelper ph = new PrinterHelper(spoon.getEnvironment());
        TokenWriter tw = new DefaultTokenWriter(ph);
        pp.setPrinterTokenWriter(tw);
        ElementPrinterHelper elementPrinterHelper = pp.getElementPrinterHelper();
        String[] listString = new String[]{ "un", "deux", "trois" };
        elementPrinterHelper.printList(Arrays.asList(listString), null, true, "start", true, true, "next", true, true, "end", ( s) -> tw.writeIdentifier(s));
        String expectedResult = " start un next deux next trois end";
        org.junit.Assert.assertEquals(expectedResult, pp.toString());
    }
}

