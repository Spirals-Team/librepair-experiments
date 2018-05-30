package spoon.test.comment;


public class CommentTest {
    private java.lang.String newLine = java.lang.System.getProperty("line.separator");

    private spoon.reflect.factory.Factory getSpoonFactory() {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.run(new java.lang.String[]{ "-i", "./src/test/java/spoon/test/comment/testclasses/", "-o", "./target/spooned/", "-c" });
        return launcher.getFactory();
    }

    private spoon.reflect.code.CtComment createFakeComment(spoon.reflect.factory.Factory factory, java.lang.String content) {
        return factory.Code().createInlineComment(content);
    }

    private spoon.reflect.code.CtComment createFakeBlockComment(spoon.reflect.factory.Factory factory, java.lang.String content) {
        return factory.Code().createComment(content, spoon.reflect.code.CtComment.CommentType.BLOCK);
    }

    @org.junit.Test
    public void testCombinedPackageInfoComment() {
        spoon.reflect.factory.Factory f = getSpoonFactory();
        spoon.reflect.declaration.CtPackage p = f.Package().get("spoon.test.comment.testclasses");
        java.lang.String l_content = ((spoon.support.JavaOutputProcessor) (f.getEnvironment().getDefaultFileGenerator())).getPrinter().printPackageInfo(p);
        java.lang.String EOL = java.lang.System.getProperty("line.separator");
        org.junit.Assert.assertEquals(((((((((((((("/* comment1 */" + EOL) + "// comment2") + EOL) + "/**") + EOL) + " * Comment3") + EOL) + " */") + EOL) + "@java.lang.Deprecated") + EOL) + "package spoon.test.comment.testclasses;") + EOL), l_content);
    }

    private java.util.List<spoon.reflect.code.CtJavaDocTag> getTagByType(java.util.List<spoon.reflect.code.CtJavaDocTag> elements, spoon.reflect.code.CtJavaDocTag.TagType type) {
        java.util.List<spoon.reflect.code.CtJavaDocTag> output = new java.util.ArrayList<>();
        for (spoon.reflect.code.CtJavaDocTag element : elements) {
            if ((element.getType()) == type) {
                output.add(element);
            }
        }
        return output;
    }

    @org.junit.Test
    public void testJavaDocCommentOnUnix() {
        java.lang.String EOL = "\n";
        spoon.reflect.factory.Factory f = getSpoonFactory();
        spoon.reflect.declaration.CtClass<?> type = ((spoon.reflect.declaration.CtClass<?>) (f.Type().get(spoon.test.comment.testclasses.JavaDocComment.class)));
        this.testJavaDocComment(type, EOL);
    }

    @org.junit.Test
    public void testJavadocShortAndLongComment() {
        spoon.reflect.factory.Factory f = getSpoonFactory();
        spoon.reflect.declaration.CtClass<?> type = ((spoon.reflect.declaration.CtClass<?>) (f.Type().get(spoon.test.comment.testclasses.OtherJavaDoc.class)));
        spoon.reflect.code.CtJavaDoc classJavaDoc = ((spoon.reflect.code.CtJavaDoc) (type.getComments().get(0)));
        org.junit.Assert.assertEquals("A short description without a proper end", classJavaDoc.getShortDescription());
        org.junit.Assert.assertEquals("A short description without a proper end", classJavaDoc.getLongDescription());
    }

    @org.junit.Test
    public void testJavaDocCommentOnMac() {
        java.lang.String EOL = "\n";
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/resources/comment/JavaDocComment.java");
        launcher.getEnvironment().setCommentEnabled(true);
        launcher.run();
        spoon.reflect.declaration.CtClass<?> type = ((spoon.reflect.declaration.CtClass<?>) (launcher.getFactory().Type().get("spoon.test.comment.testclasses.JavaDocComment")));
        this.testJavaDocComment(type, EOL);
    }

    private void testJavaDocComment(spoon.reflect.declaration.CtClass type, java.lang.String EOL) {
        spoon.reflect.code.CtJavaDoc classJavaDoc = ((spoon.reflect.code.CtJavaDoc) (type.getComments().get(0)));
        java.lang.String str = classJavaDoc.toString();
        java.util.StringTokenizer st = new java.util.StringTokenizer(str, java.lang.System.getProperty("line.separator"));
        boolean first = true;
        while (st.hasMoreTokens()) {
            java.lang.String line = st.nextToken();
            if (first) {
                first = false;
                org.junit.Assert.assertTrue(((line.length()) == 3));
                org.junit.Assert.assertEquals("/**", line);
            }else {
                if (st.hasMoreTokens()) {
                    org.junit.Assert.assertTrue(((line.length()) >= 2));
                    org.junit.Assert.assertEquals(" *", line.substring(0, 2));
                }else {
                    org.junit.Assert.assertTrue(((line.length()) == 3));
                    org.junit.Assert.assertEquals(" */", line.substring(0, 3));
                }
            }
        } 
        org.junit.Assert.assertEquals(((("JavaDoc test class." + EOL) + EOL) + "Long description"), classJavaDoc.getContent());
        java.util.List<spoon.reflect.code.CtJavaDocTag> elements = type.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtJavaDocTag.class));
        org.junit.Assert.assertEquals(8, elements.size());
        java.util.List<spoon.reflect.code.CtJavaDocTag> authorTags = getTagByType(elements, spoon.reflect.code.CtJavaDocTag.TagType.AUTHOR);
        org.junit.Assert.assertEquals(1, authorTags.size());
        org.junit.Assert.assertEquals("Thomas Durieux", authorTags.get(0).getContent());
        java.util.List<spoon.reflect.code.CtJavaDocTag> deprecatedTags = getTagByType(elements, spoon.reflect.code.CtJavaDocTag.TagType.DEPRECATED);
        org.junit.Assert.assertEquals(1, deprecatedTags.size());
        org.junit.Assert.assertEquals("", deprecatedTags.get(0).getContent());
        java.util.List<spoon.reflect.code.CtJavaDocTag> sinceTags = getTagByType(elements, spoon.reflect.code.CtJavaDocTag.TagType.SINCE);
        org.junit.Assert.assertEquals(2, sinceTags.size());
        org.junit.Assert.assertEquals("1.3", sinceTags.get(0).getContent());
        org.junit.Assert.assertEquals("1.3", sinceTags.get(1).getContent());
        java.util.List<spoon.reflect.code.CtJavaDocTag> paramTags = getTagByType(elements, spoon.reflect.code.CtJavaDocTag.TagType.PARAM);
        org.junit.Assert.assertEquals(1, paramTags.size());
        org.junit.Assert.assertEquals("the parameters", paramTags.get(0).getContent());
        org.junit.Assert.assertEquals("i", paramTags.get(0).getParam());
        spoon.reflect.code.CtJavaDocTag tagClone = paramTags.get(0).clone();
        org.junit.Assert.assertEquals("the parameters", tagClone.getContent());
        org.junit.Assert.assertEquals("i", tagClone.getParam());
        java.util.List<spoon.reflect.code.CtJavaDocTag> throwsTags = getTagByType(elements, spoon.reflect.code.CtJavaDocTag.TagType.THROWS);
        org.junit.Assert.assertEquals(1, throwsTags.size());
        org.junit.Assert.assertEquals("an exception", throwsTags.get(0).getContent());
        org.junit.Assert.assertEquals("RuntimeException", throwsTags.get(0).getParam());
        org.junit.Assert.assertEquals("JavaDoc test class.", classJavaDoc.getShortDescription());
        org.junit.Assert.assertEquals("Long description", classJavaDoc.getLongDescription());
        spoon.reflect.code.CtJavaDocTag deprecatedTag = classJavaDoc.getTags().get(0);
        org.junit.Assert.assertTrue(((classJavaDoc.toString().indexOf("@deprecated")) >= 0));
        classJavaDoc.removeTag(0);
        org.junit.Assert.assertEquals((-1), classJavaDoc.toString().indexOf("@deprecated"));
        classJavaDoc.addTag(deprecatedTag);
        org.junit.Assert.assertTrue(((classJavaDoc.toString().indexOf("@deprecated")) >= 0));
    }

    @org.junit.Test
    public void testJavaDocEmptyCommentAndTag() {
        java.lang.String EOL = "\n";
        spoon.reflect.factory.Factory f = getSpoonFactory();
        spoon.reflect.declaration.CtClass<?> type = ((spoon.reflect.declaration.CtClass<?>) (f.Type().get(spoon.test.comment.testclasses.JavaDocEmptyCommentAndTags.class)));
        spoon.reflect.code.CtJavaDoc classJavaDoc = ((spoon.reflect.code.CtJavaDoc) (type.getComments().get(0)));
        org.junit.Assert.assertNotNull(classJavaDoc.getContent());
        org.junit.Assert.assertEquals("", classJavaDoc.getContent());
        spoon.reflect.code.CtJavaDoc methodJavaDoc = ((spoon.reflect.code.CtJavaDoc) (type.getMethodsByName("m").get(0).getComments().get(1)));
        org.junit.Assert.assertNotNull(methodJavaDoc.getContent());
        org.junit.Assert.assertEquals("", methodJavaDoc.getContent());
    }

    @org.junit.Test
    public void testRemoveComment() {
        spoon.reflect.factory.Factory f = getSpoonFactory();
        spoon.reflect.declaration.CtClass<?> type = ((spoon.reflect.declaration.CtClass<?>) (f.Type().get(spoon.test.comment.testclasses.InlineComment.class)));
        java.util.List<spoon.reflect.code.CtComment> comments = type.getComments();
        org.junit.Assert.assertEquals(6, comments.size());
        type.removeComment(comments.get(0));
        org.junit.Assert.assertEquals(5, type.getComments().size());
    }

    @org.junit.Test
    public void testInLineComment() {
        spoon.reflect.factory.Factory f = getSpoonFactory();
        spoon.reflect.declaration.CtClass<?> type = ((spoon.reflect.declaration.CtClass<?>) (f.Type().get(spoon.test.comment.testclasses.InlineComment.class)));
        java.lang.String strType = type.toString();
        java.util.List<spoon.reflect.code.CtComment> comments = type.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtComment>(spoon.reflect.code.CtComment.class));
        org.junit.Assert.assertEquals(69, comments.size());
        for (spoon.reflect.code.CtComment comment : comments) {
            if ((comment.getCommentType()) == (spoon.reflect.code.CtComment.CommentType.FILE)) {
                continue;
            }
            org.junit.Assert.assertNotNull(comment.getParent());
            org.junit.Assert.assertTrue(((((comment.toString()) + ":") + (comment.getParent())) + " is not printed"), strType.contains(comment.toString()));
        }
        org.junit.Assert.assertEquals(6, type.getComments().size());
        org.junit.Assert.assertEquals(spoon.reflect.code.CtComment.CommentType.FILE, type.getComments().get(0).getCommentType());
        org.junit.Assert.assertEquals(createFakeComment(f, "comment class"), type.getComments().get(1));
        org.junit.Assert.assertEquals("Bottom File", type.getComments().get(5).getContent());
        spoon.reflect.declaration.CtField<?> field = type.getField("field");
        org.junit.Assert.assertEquals(4, field.getComments().size());
        org.junit.Assert.assertEquals(createFakeComment(f, "Comment Field"), field.getComments().get(0));
        org.junit.Assert.assertEquals((((((("// Comment Field" + (newLine)) + "// comment field 2") + (newLine)) + "// comment in field") + (newLine)) + "private int field = 10;// after field\n"), field.toString());
        spoon.reflect.declaration.CtAnonymousExecutable ctAnonymousExecutable = type.getAnonymousExecutables().get(0);
        org.junit.Assert.assertEquals(1, ctAnonymousExecutable.getComments().size());
        org.junit.Assert.assertEquals(createFakeComment(f, "comment static block"), ctAnonymousExecutable.getComments().get(0));
        org.junit.Assert.assertEquals(createFakeComment(f, "comment inside static"), ctAnonymousExecutable.getBody().getStatement(0));
        org.junit.Assert.assertEquals((((((("// comment static block" + (newLine)) + "static {") + (newLine)) + "    // comment inside static") + (newLine)) + "}"), ctAnonymousExecutable.toString());
        spoon.reflect.declaration.CtConstructor constructor = type.getConstructor();
        org.junit.Assert.assertEquals(1, constructor.getComments().size());
        org.junit.Assert.assertEquals(createFakeComment(f, "comment constructor"), constructor.getComments().get(0));
        org.junit.Assert.assertEquals(createFakeComment(f, "Comment in constructor"), constructor.getBody().getStatement(1));
        org.junit.Assert.assertEquals((((((("// comment constructor" + (newLine)) + "public InlineComment() {") + (newLine)) + "    // Comment in constructor") + (newLine)) + "}"), constructor.toString());
        spoon.reflect.declaration.CtMethod<java.lang.Object> m = type.getMethod("m");
        org.junit.Assert.assertEquals(1, m.getComments().size());
        org.junit.Assert.assertEquals(createFakeComment(f, "comment method"), m.getComments().get(0));
        org.junit.Assert.assertEquals(createFakeComment(f, "comment empty method block"), m.getBody().getStatement(0));
        org.junit.Assert.assertEquals((((((("// comment method" + (newLine)) + "public void m() {") + (newLine)) + "    // comment empty method block") + (newLine)) + "}"), m.toString());
        spoon.reflect.declaration.CtMethod<java.lang.Object> m1 = type.getMethod("m1");
        spoon.reflect.code.CtSwitch ctSwitch = m1.getBody().getStatement(0);
        org.junit.Assert.assertEquals(createFakeComment(f, "comment switch"), ctSwitch.getComments().get(0));
        org.junit.Assert.assertEquals((((((((((((((((((((("// comment switch" + (newLine)) + "switch (1) {") + (newLine)) + "    // before first case") + (newLine)) + "    case 0 :") + (newLine)) + "        // comment case 0: empty case") + (newLine)) + "    case 1 :") + (newLine)) + "        // comment case 1") + (newLine)) + "        int i = 0;") + (newLine)) + "    default :") + (newLine)) + "        // comment default") + (newLine)) + "}"), ctSwitch.toString());
        spoon.reflect.code.CtFor ctFor = m1.getBody().getStatement(1);
        org.junit.Assert.assertEquals(createFakeComment(f, "comment for"), ctFor.getComments().get(0));
        org.junit.Assert.assertEquals((((((("// comment for" + (newLine)) + "for (int i = 0; i < 10; i++) {") + (newLine)) + "    // comment for block") + (newLine)) + "}"), ctFor.toString());
        spoon.reflect.code.CtIf ctIf = m1.getBody().getStatement(2);
        org.junit.Assert.assertEquals(createFakeComment(f, "comment if"), ctIf.getComments().get(0));
        org.junit.Assert.assertEquals((((((((("// comment if" + (newLine)) + "if ((1 % 2) == 0) {") + (newLine)) + "    // comment unary operator") + (newLine)) + "    (field)++;") + (newLine)) + "}"), ctIf.toString());
        spoon.reflect.code.CtConstructorCall ctConstructorCall = m1.getBody().getStatement(3);
        org.junit.Assert.assertEquals(createFakeComment(f, "comment constructor call"), ctConstructorCall.getComments().get(0));
        org.junit.Assert.assertEquals((("// comment constructor call" + (newLine)) + "new spoon.test.comment.testclasses.InlineComment()"), ctConstructorCall.toString());
        spoon.reflect.code.CtInvocation ctInvocation = m1.getBody().getStatement(4);
        org.junit.Assert.assertEquals(createFakeComment(f, "comment invocation"), ctInvocation.getComments().get(0));
        org.junit.Assert.assertEquals((("// comment invocation" + (newLine)) + "this.m()"), ctInvocation.toString());
        spoon.reflect.code.CtLocalVariable ctLocalVariable = m1.getBody().getStatement(5);
        org.junit.Assert.assertEquals(createFakeComment(f, "comment local variable"), ctLocalVariable.getComments().get(0));
        org.junit.Assert.assertEquals((("// comment local variable" + (newLine)) + "int i = 0"), ctLocalVariable.toString());
        spoon.reflect.code.CtLocalVariable ctLocalVariable2 = m1.getBody().getStatement(6);
        org.junit.Assert.assertEquals(createFakeComment(f, "comment multi assignments"), ctLocalVariable2.getComments().get(0));
        org.junit.Assert.assertEquals((("// comment multi assignments" + (newLine)) + "int j = 2"), ctLocalVariable2.toString());
        spoon.reflect.code.CtDo ctDo = m1.getBody().getStatement(7);
        org.junit.Assert.assertEquals(createFakeComment(f, "comment dowhile"), ctDo.getComments().get(0));
        org.junit.Assert.assertEquals((((((((((("// comment dowhile" + (newLine)) + "do {") + (newLine)) + "    // comment in do while") + (newLine)) + "    i++;") + (newLine)) + "    // comment end do while") + (newLine)) + "} while (i < 10 )"), ctDo.toString());
        spoon.reflect.code.CtTry ctTry = m1.getBody().getStatement(8);
        org.junit.Assert.assertEquals(createFakeComment(f, "comment try"), ctTry.getComments().get(0));
        org.junit.Assert.assertEquals((((((((((((((((("// comment try" + (newLine)) + "try {") + (newLine)) + "    // comment in try") + (newLine)) + "    i++;") + (newLine)) + "}// between") + (newLine)) + "// try/catch") + (newLine)) + " catch (java.lang.Exception e) {") + (newLine)) + "    // comment in catch") + (newLine)) + "}"), ctTry.toString());
        spoon.reflect.code.CtSynchronized ctSynchronized = m1.getBody().getStatement(9);
        org.junit.Assert.assertEquals(createFakeComment(f, "comment synchronized"), ctSynchronized.getComments().get(0));
        org.junit.Assert.assertEquals((((((("// comment synchronized" + (newLine)) + "synchronized(this) {") + (newLine)) + "    // comment in synchronized") + (newLine)) + "}"), ctSynchronized.toString());
        spoon.reflect.code.CtLocalVariable ctLocalVariable1 = m1.getBody().getStatement(10);
        spoon.reflect.code.CtConditional ctConditional = ((spoon.reflect.code.CtConditional) (ctLocalVariable1.getDefaultExpression()));
        org.junit.Assert.assertEquals(createFakeComment(f, "comment after condition CtConditional"), ctConditional.getCondition().getComments().get(0));
        org.junit.Assert.assertEquals(createFakeComment(f, "comment before then CtConditional"), ctConditional.getThenExpression().getComments().get(0));
        org.junit.Assert.assertEquals(createFakeComment(f, "comment after then CtConditional"), ctConditional.getThenExpression().getComments().get(1));
        org.junit.Assert.assertEquals(createFakeComment(f, "comment before else CtConditional"), ctConditional.getElseExpression().getComments().get(0));
        org.junit.Assert.assertEquals(createFakeComment(f, "comment after else CtConditional"), ctLocalVariable1.getComments().get(0));
        org.junit.Assert.assertEquals((((((((("java.lang.Double dou = (i == 1// comment after condition CtConditional" + (newLine)) + ") ? // comment before then CtConditional") + (newLine)) + "null// comment after then CtConditional") + (newLine)) + " : // comment before else CtConditional") + (newLine)) + "new java.lang.Double((j / ((double) (i - 1))))"), ctLocalVariable1.toString());
        spoon.reflect.code.CtNewArray ctNewArray = ((spoon.reflect.code.CtNewArray) (((spoon.reflect.code.CtLocalVariable) (m1.getBody().getStatement(11))).getDefaultExpression()));
        org.junit.Assert.assertEquals(createFakeComment(f, "last comment at the end of array"), ctNewArray.getComments().get(0));
        spoon.reflect.declaration.CtElement arrayValue = ((spoon.reflect.declaration.CtElement) (ctNewArray.getElements().get(0)));
        org.junit.Assert.assertEquals(createFakeComment(f, "comment before array value"), arrayValue.getComments().get(0));
        org.junit.Assert.assertEquals(createFakeComment(f, "comment after array value"), arrayValue.getComments().get(1));
        spoon.reflect.code.CtLocalVariable ctLocalVariableString = m1.getBody().getStatement(12);
        org.junit.Assert.assertEquals(createFakeComment(f, "comment multi line string"), ((spoon.reflect.code.CtBinaryOperator) (((spoon.reflect.code.CtBinaryOperator) (ctLocalVariableString.getDefaultExpression())).getRightHandOperand())).getLeftHandOperand().getComments().get(0));
        org.junit.Assert.assertEquals((("\"\" + (\"\"// comment multi line string" + (newLine)) + " + \"\")"), ctLocalVariableString.getDefaultExpression().toString());
        ctLocalVariable1 = m1.getBody().getStatement(13);
        ctConditional = ((spoon.reflect.code.CtConditional) (ctLocalVariable1.getDefaultExpression()));
        org.junit.Assert.assertEquals((((("boolean c = (i == 1) ? // comment before then boolean CtConditional" + (newLine)) + "i == 1// comment after then boolean CtConditional") + (newLine)) + " : i == 2"), ctLocalVariable1.toString());
        spoon.reflect.code.CtReturn ctReturn = m1.getBody().getStatement(14);
        org.junit.Assert.assertEquals(createFakeComment(f, "comment return"), ctReturn.getComments().get(0));
        org.junit.Assert.assertEquals((("// comment return" + (newLine)) + "return"), ctReturn.toString());
        spoon.reflect.declaration.CtMethod m2 = type.getMethodsByName("m2").get(0);
        org.junit.Assert.assertEquals(6, m2.getComments().size());
        spoon.reflect.declaration.CtParameter ctParameter = ((spoon.reflect.declaration.CtParameter) (m2.getParameters().get(0)));
        org.junit.Assert.assertEquals(4, ctParameter.getComments().size());
        org.junit.Assert.assertEquals((((((((((((((((((((((("// comment before type" + (newLine)) + "// comment after parameter") + (newLine)) + "// comment before throws") + (newLine)) + "// comment before exception 1") + (newLine)) + "// comment before exception 2") + (newLine)) + "// comment before block") + (newLine)) + "public void m2(// comment before name") + (newLine)) + "// comment before parameters") + (newLine)) + "// comment before type parameter") + (newLine)) + "// comment before name parameter") + (newLine)) + "int i) throws java.lang.Error, java.lang.Exception {") + (newLine)) + "}"), m2.toString());
    }

    @org.junit.Test
    public void testBlockComment() {
        spoon.reflect.factory.Factory f = getSpoonFactory();
        spoon.reflect.declaration.CtClass<?> type = ((spoon.reflect.declaration.CtClass<?>) (f.Type().get(spoon.test.comment.testclasses.BlockComment.class)));
        java.lang.String strType = type.toString();
        java.util.List<spoon.reflect.code.CtComment> comments = type.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtComment>(spoon.reflect.code.CtComment.class));
        org.junit.Assert.assertEquals(52, comments.size());
        for (spoon.reflect.code.CtComment comment : comments) {
            if ((comment.getCommentType()) == (spoon.reflect.code.CtComment.CommentType.FILE)) {
                continue;
            }
            org.junit.Assert.assertNotNull(comment.getParent());
            org.junit.Assert.assertTrue(((((comment.toString()) + ":") + (comment.getParent())) + " is not printed"), strType.contains(comment.toString()));
        }
        org.junit.Assert.assertEquals(5, type.getComments().size());
        org.junit.Assert.assertEquals(createFakeBlockComment(f, "comment class"), type.getComments().get(1));
        org.junit.Assert.assertEquals("Bottom File", type.getComments().get(4).getContent());
        spoon.reflect.declaration.CtField<?> field = type.getField("field");
        org.junit.Assert.assertEquals(2, field.getComments().size());
        org.junit.Assert.assertEquals(createFakeBlockComment(f, "Comment Field"), field.getComments().get(0));
        org.junit.Assert.assertEquals((((("/* Comment Field */" + (newLine)) + "/* comment in field */") + (newLine)) + "private int field = 10;"), field.toString());
        spoon.reflect.declaration.CtAnonymousExecutable ctAnonymousExecutable = type.getAnonymousExecutables().get(0);
        org.junit.Assert.assertEquals(1, ctAnonymousExecutable.getComments().size());
        org.junit.Assert.assertEquals(createFakeBlockComment(f, "comment static block"), ctAnonymousExecutable.getComments().get(0));
        org.junit.Assert.assertEquals(createFakeBlockComment(f, "comment inside static"), ctAnonymousExecutable.getBody().getStatement(0));
        org.junit.Assert.assertEquals((((((("/* comment static block */" + (newLine)) + "static {") + (newLine)) + "    /* comment inside static */") + (newLine)) + "}"), ctAnonymousExecutable.toString());
        spoon.reflect.declaration.CtConstructor constructor = type.getConstructor();
        org.junit.Assert.assertEquals(1, constructor.getComments().size());
        org.junit.Assert.assertEquals(createFakeBlockComment(f, "comment constructor"), constructor.getComments().get(0));
        org.junit.Assert.assertEquals(createFakeBlockComment(f, "Comment in constructor"), constructor.getBody().getStatement(1));
        org.junit.Assert.assertEquals((((((("/* comment constructor */" + (newLine)) + "public BlockComment() {") + (newLine)) + "    /* Comment in constructor */") + (newLine)) + "}"), constructor.toString());
        spoon.reflect.declaration.CtMethod<java.lang.Object> m = type.getMethod("m");
        org.junit.Assert.assertEquals(1, m.getComments().size());
        org.junit.Assert.assertEquals(createFakeBlockComment(f, "comment method"), m.getComments().get(0));
        org.junit.Assert.assertEquals(createFakeBlockComment(f, "comment empty method block"), m.getBody().getStatement(0));
        org.junit.Assert.assertEquals((((((("/* comment method */" + (newLine)) + "public void m() {") + (newLine)) + "    /* comment empty method block */") + (newLine)) + "}"), m.toString());
        spoon.reflect.declaration.CtMethod<java.lang.Object> m1 = type.getMethod("m1");
        spoon.reflect.code.CtSwitch ctSwitch = m1.getBody().getStatement(0);
        org.junit.Assert.assertEquals(createFakeBlockComment(f, "comment switch"), ctSwitch.getComments().get(0));
        org.junit.Assert.assertEquals((((((((((((((((((((("/* comment switch */" + (newLine)) + "switch (1) {") + (newLine)) + "    /* before first case */") + (newLine)) + "    case 0 :") + (newLine)) + "        /* comment case 0: empty case */") + (newLine)) + "    case 1 :") + (newLine)) + "        /* comment case 1 */") + (newLine)) + "        int i = 0;") + (newLine)) + "    default :") + (newLine)) + "        /* comment default */") + (newLine)) + "}"), ctSwitch.toString());
        spoon.reflect.code.CtFor ctFor = m1.getBody().getStatement(1);
        org.junit.Assert.assertEquals(createFakeBlockComment(f, "comment for"), ctFor.getComments().get(0));
        org.junit.Assert.assertEquals((((((("/* comment for */" + (newLine)) + "for (int i = 0; i < 10; i++) {") + (newLine)) + "    /* comment for block */") + (newLine)) + "}"), ctFor.toString());
        spoon.reflect.code.CtIf ctIf = m1.getBody().getStatement(2);
        org.junit.Assert.assertEquals(createFakeBlockComment(f, "comment if"), ctIf.getComments().get(0));
        org.junit.Assert.assertEquals((((((((("/* comment if */" + (newLine)) + "if ((1 % 2) == 0) {") + (newLine)) + "    /* comment unary operator */") + (newLine)) + "    (field)++;") + (newLine)) + "}"), ctIf.toString());
        spoon.reflect.code.CtConstructorCall ctConstructorCall = m1.getBody().getStatement(3);
        org.junit.Assert.assertEquals(createFakeBlockComment(f, "comment constructor call"), ctConstructorCall.getComments().get(0));
        org.junit.Assert.assertEquals((("/* comment constructor call */" + (newLine)) + "new spoon.test.comment.testclasses.BlockComment()"), ctConstructorCall.toString());
        spoon.reflect.code.CtInvocation ctInvocation = m1.getBody().getStatement(4);
        org.junit.Assert.assertEquals(createFakeBlockComment(f, "comment invocation"), ctInvocation.getComments().get(0));
        org.junit.Assert.assertEquals((("/* comment invocation */" + (newLine)) + "this.m()"), ctInvocation.toString());
        spoon.reflect.code.CtLocalVariable ctLocalVariable = m1.getBody().getStatement(5);
        org.junit.Assert.assertEquals(createFakeBlockComment(f, "comment local variable"), ctLocalVariable.getComments().get(0));
        org.junit.Assert.assertEquals((("/* comment local variable */" + (newLine)) + "int i = 0"), ctLocalVariable.toString());
        spoon.reflect.code.CtLocalVariable ctLocalVariable2 = m1.getBody().getStatement(6);
        org.junit.Assert.assertEquals(createFakeBlockComment(f, "comment multi assignments"), ctLocalVariable2.getComments().get(0));
        org.junit.Assert.assertEquals((("/* comment multi assignments */" + (newLine)) + "int j = 2"), ctLocalVariable2.toString());
        spoon.reflect.code.CtDo ctDo = m1.getBody().getStatement(7);
        org.junit.Assert.assertEquals(createFakeBlockComment(f, "comment dowhile"), ctDo.getComments().get(0));
        org.junit.Assert.assertEquals((((((((((("/* comment dowhile */" + (newLine)) + "do {") + (newLine)) + "    /* comment in do while */") + (newLine)) + "    i++;") + (newLine)) + "    /* comment end do while */") + (newLine)) + "} while (i < 10 )"), ctDo.toString());
        spoon.reflect.code.CtTry ctTry = m1.getBody().getStatement(8);
        org.junit.Assert.assertEquals(createFakeBlockComment(f, "comment try"), ctTry.getComments().get(0));
        org.junit.Assert.assertEquals((((((((((((("/* comment try */" + (newLine)) + "try {") + (newLine)) + "    /* comment in try */") + (newLine)) + "    i++;") + (newLine)) + "} catch (java.lang.Exception e) {") + (newLine)) + "    /* comment in catch */") + (newLine)) + "}"), ctTry.toString());
        spoon.reflect.code.CtSynchronized ctSynchronized = m1.getBody().getStatement(9);
        org.junit.Assert.assertEquals(createFakeBlockComment(f, "comment synchronized"), ctSynchronized.getComments().get(0));
        org.junit.Assert.assertEquals((((((("/* comment synchronized */" + (newLine)) + "synchronized(this) {") + (newLine)) + "    /* comment in synchronized */") + (newLine)) + "}"), ctSynchronized.toString());
        spoon.reflect.code.CtReturn ctReturn = m1.getBody().getStatement(10);
        org.junit.Assert.assertEquals(createFakeBlockComment(f, "comment return"), ctReturn.getComments().get(0));
        org.junit.Assert.assertEquals((("/* comment return */" + (newLine)) + "return"), ctReturn.toString());
        spoon.reflect.declaration.CtMethod m2 = type.getMethodsByName("m2").get(0);
        org.junit.Assert.assertEquals(6, m2.getComments().size());
        spoon.reflect.declaration.CtParameter ctParameter = ((spoon.reflect.declaration.CtParameter) (m2.getParameters().get(0)));
        org.junit.Assert.assertEquals(4, ctParameter.getComments().size());
        org.junit.Assert.assertEquals((((((((((((((((((((((("/* comment before type */" + (newLine)) + "/* comment after parameter */") + (newLine)) + "/* comment before throws */") + (newLine)) + "/* comment before exception 1 */") + (newLine)) + "/* comment before exception 2 */") + (newLine)) + "/* comment before block */") + (newLine)) + "public void m2(/* comment before name */") + (newLine)) + "/* comment before parameters */") + (newLine)) + "/* comment before type parameter */") + (newLine)) + "/* comment before name parameter */") + (newLine)) + "int i) throws java.lang.Error, java.lang.Exception {") + (newLine)) + "}"), m2.toString());
    }

    @org.junit.Test
    public void testInsertNewComment() {
        spoon.reflect.factory.Factory f = getSpoonFactory();
        spoon.reflect.declaration.CtClass<?> type = ((spoon.reflect.declaration.CtClass<?>) (f.Type().get(spoon.test.comment.testclasses.InlineComment.class)));
        spoon.reflect.declaration.CtMethod method = f.Core().createMethod();
        method.setSimpleName("newMethod");
        method.setBody(f.Core().createBlock());
        method.setType(f.Type().VOID_PRIMITIVE);
        type.addMethod(method);
        method.addComment(createFakeComment(f, "comment method"));
        method.getBody().addStatement(createFakeComment(f, "comment empty block"));
        org.junit.Assert.assertEquals((((((("// comment method" + (newLine)) + "void newMethod() {") + (newLine)) + "    // comment empty block") + (newLine)) + "}"), method.toString());
        method.getBody().removeStatement(method.getBody().getStatement(0));
        spoon.reflect.code.CtLocalVariable<java.lang.Integer> i = f.Code().createLocalVariable(f.Type().INTEGER_PRIMITIVE, "i", null);
        i.addComment(createFakeComment(f, "comment local variable"));
        method.getBody().addStatement(i);
        org.junit.Assert.assertEquals((((((((("// comment method" + (newLine)) + "void newMethod() {") + (newLine)) + "    // comment local variable") + (newLine)) + "    int i;") + (newLine)) + "}"), method.toString());
    }

    @org.junit.Test
    public void testCoreFactory() {
        spoon.reflect.factory.Factory spoonFactory = getSpoonFactory();
        spoon.reflect.code.CtComment comment = spoonFactory.Core().createComment();
        org.junit.Assert.assertEquals("/*  */", comment.toString());
        comment.setContent("comment");
        org.junit.Assert.assertEquals("/* comment */", comment.toString());
        comment.setCommentType(spoon.reflect.code.CtComment.CommentType.INLINE);
        org.junit.Assert.assertEquals(spoon.reflect.code.CtComment.CommentType.INLINE, comment.getCommentType());
        org.junit.Assert.assertEquals("// comment", comment.toString());
        comment.setCommentType(spoon.reflect.code.CtComment.CommentType.BLOCK);
        org.junit.Assert.assertEquals(spoon.reflect.code.CtComment.CommentType.BLOCK, comment.getCommentType());
    }

    @org.junit.Test
    public void testCodeFactory() {
        spoon.reflect.factory.Factory spoonFactory = getSpoonFactory();
        spoon.reflect.code.CtComment comment = spoonFactory.Code().createComment("comment", spoon.reflect.code.CtComment.CommentType.INLINE);
        org.junit.Assert.assertEquals("// comment", comment.toString());
        org.junit.Assert.assertEquals(spoon.reflect.code.CtComment.CommentType.INLINE, comment.getCommentType());
        comment = spoonFactory.Code().createInlineComment("comment");
        org.junit.Assert.assertEquals("// comment", comment.toString());
        org.junit.Assert.assertEquals(spoon.reflect.code.CtComment.CommentType.INLINE, comment.getCommentType());
    }

    @org.junit.Test
    public void testSnippedWithComments() {
        spoon.reflect.factory.Factory factory = new spoon.reflect.factory.FactoryImpl(new spoon.support.DefaultCoreFactory(), new spoon.support.StandardEnvironment());
        factory.getEnvironment().setNoClasspath(true);
        factory.getEnvironment().setCommentEnabled(true);
        java.lang.String content = "//class comment\n" + ((((((("class PR {\n" + "/**\n * method javadoc comment */\n") + "public java.io.File foo(String p) {\n") + "/* method body comment*/\n") + " return /*inline comment*/ null;") + "}") + "};\n") + "// after class comment");
        spoon.support.compiler.jdt.JDTSnippetCompiler builder = new spoon.support.compiler.jdt.JDTSnippetCompiler(factory, content);
        builder.build();
        spoon.reflect.declaration.CtClass<?> clazz1 = ((spoon.reflect.declaration.CtClass<?>) (factory.Type().getAll().get(0)));
        org.junit.Assert.assertNotNull(clazz1);
        org.junit.Assert.assertEquals(2, clazz1.getComments().size());
        org.junit.Assert.assertEquals("class comment", clazz1.getComments().get(0).getContent());
        org.junit.Assert.assertEquals("after class comment", clazz1.getComments().get(1).getContent());
        org.junit.Assert.assertEquals(1, builder.getSnippetCompilationUnit().getDeclaredTypes().size());
        org.junit.Assert.assertTrue((clazz1 == (builder.getSnippetCompilationUnit().getDeclaredTypes().get(0))));
        spoon.reflect.declaration.CtMethod<?> methodString = ((spoon.reflect.declaration.CtMethod<?>) (clazz1.getMethods().toArray()[0]));
        org.junit.Assert.assertEquals("foo", methodString.getSimpleName());
        org.junit.Assert.assertEquals(1, methodString.getComments().size());
        org.junit.Assert.assertEquals("method javadoc comment", methodString.getComments().get(0).getContent());
        spoon.reflect.code.CtReturn<?> returnSt = methodString.getBody().getStatement(0);
        org.junit.Assert.assertEquals(2, returnSt.getComments().size());
        org.junit.Assert.assertEquals("method body comment", returnSt.getComments().get(0).getContent());
        org.junit.Assert.assertEquals("inline comment", returnSt.getComments().get(1).getContent());
    }

    @org.junit.Test
    public void testAddCommentsToSnippet() {
        spoon.reflect.factory.Factory factory = new spoon.reflect.factory.FactoryImpl(new spoon.support.DefaultCoreFactory(), new spoon.support.StandardEnvironment());
        factory.getEnvironment().setNoClasspath(true);
        factory.getEnvironment().setCommentEnabled(true);
        spoon.reflect.code.CtStatement statement = factory.Code().createCodeSnippetStatement("System.out.println(\"Caenorhabditis\")");
        spoon.reflect.code.CtComment comment = factory.createComment("My comment on my statement", spoon.reflect.code.CtComment.CommentType.INLINE);
        statement.addComment(comment);
        spoon.reflect.code.CtExpression expression = factory.Code().createCodeSnippetExpression("\"Caenorhabditis\" + \"Caenorhabditis\"");
        spoon.reflect.code.CtComment commentExpression = factory.createComment("My comment on my expression", spoon.reflect.code.CtComment.CommentType.INLINE);
        expression.addComment(commentExpression);
        org.junit.Assert.assertEquals((("// My comment on my statement" + (newLine)) + "System.out.println(\"Caenorhabditis\")"), statement.toString());
        org.junit.Assert.assertEquals((("// My comment on my expression" + (newLine)) + "\"Caenorhabditis\" + \"Caenorhabditis\""), expression.toString());
    }

    @org.junit.Test
    public void testDocumentationContract() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        launcher.getEnvironment().setCommentEnabled(true);
        launcher.addInputResource("./src/main/java/spoon/reflect/");
        launcher.addInputResource("./src/main/java/spoon/support/reflect/");
        launcher.buildModel();
        java.lang.StringBuffer codeElementsDocumentationPage = new java.lang.StringBuffer();
        codeElementsDocumentationPage.append(org.apache.commons.io.IOUtils.toString(new java.io.FileReader("doc/code_elements_header.md")));
        codeElementsDocumentationPage.append("\n\n");
        launcher.getModel().getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.declaration.CtInterface.class)).stream().forEach(( x) -> {
            org.junit.Assert.assertTrue(((x.getSimpleName()) + " has no documentation"), ((x.getDocComment()) != null));
            org.junit.Assert.assertTrue(((x.getSimpleName()) + " has no documentation"), ((x.getDocComment().length()) > 0));
            if ((launcher.getModel().getElements(new spoon.reflect.visitor.filter.AbstractFilter<spoon.reflect.declaration.CtElement>() {
                @java.lang.Override
                public boolean matches(spoon.reflect.declaration.CtElement element) {
                    return (((element instanceof spoon.reflect.declaration.CtNamedElement) && (((spoon.reflect.declaration.CtNamedElement) (element)).getSimpleName().equals(((x.getSimpleName()) + "Impl")))) && (element instanceof spoon.reflect.declaration.CtClass)) && (!(((spoon.reflect.declaration.CtClass) (element)).hasModifier(spoon.reflect.declaration.ModifierKind.ABSTRACT)));
                }
            }).size()) == 0) {
                return;
            }
            if (x.getSimpleName().endsWith("Reference")) {
                return;
            }
            if ((x.isSubtypeOf(launcher.getFactory().Type().get(spoon.reflect.code.CtStatement.class).getReference())) || (x.isSubtypeOf(launcher.getFactory().Type().get(spoon.reflect.code.CtExpression.class).getReference()))) {
                if (x.getSimpleName().equals("CtCodeSnippetStatement")) {
                    return;
                }
                if (x.getSimpleName().equals("CtCodeSnippetExpression")) {
                    return;
                }
                if (x.getSimpleName().equals("CtComment")) {
                    return;
                }
                if (x.getSimpleName().equals("CtEnum")) {
                    return;
                }
                if (x.getSimpleName().equals("CtAnnotationFieldAccess")) {
                    return;
                }
                codeElementsDocumentationPage.append((("### " + (x.getSimpleName())) + "\n"));
                codeElementsDocumentationPage.append((("[(javadoc)](http://spoon.gforge.inria.fr/mvnsites/spoon-core/apidocs/" + (x.getQualifiedName().replace('.', '/'))) + ".html)\n\n"));
                codeElementsDocumentationPage.append(("```java" + "\n"));
                java.util.regex.Pattern p = java.util.regex.Pattern.compile("<pre>(.*?)</pre>", ((((java.util.regex.Pattern.CASE_INSENSITIVE) | (java.util.regex.Pattern.DOTALL)) | (java.util.regex.Pattern.MULTILINE)) | (java.util.regex.Pattern.UNIX_LINES)));
                java.util.regex.Matcher m = p.matcher(x.getDocComment());
                m.find();
                do {
                    java.lang.String snippet = null;
                    try {
                        snippet = m.group(1);
                    } catch (java.lang.IllegalStateException e) {
                        org.junit.Assert.fail((x + " does not have code snippet"));
                    }
                    snippet = org.apache.commons.lang3.StringEscapeUtils.unescapeHtml4(snippet);
                    spoon.reflect.declaration.CtElement el = launcher.getFactory().Code().createCodeSnippetStatement(snippet).compile();
                    org.junit.Assert.assertTrue(((snippet + " does not contain a ") + (x.getSimpleName())), ((el.getElements(new spoon.reflect.visitor.filter.TypeFilter(x.getActualClass())).size()) > 0));
                    codeElementsDocumentationPage.append((snippet + "\n"));
                } while (m.find() );
                codeElementsDocumentationPage.append(("```" + "\n"));
            }
        });
        try {
            org.junit.Assert.assertEquals("doc outdated, please commit doc/code_elements.md", codeElementsDocumentationPage.toString(), org.apache.commons.io.IOUtils.toString(new java.io.FileReader("doc/code_elements.md")));
        } finally {
            org.apache.commons.io.IOUtils.write(codeElementsDocumentationPage.toString(), new java.io.FileOutputStream("doc/code_elements.md"));
        }
    }

    @org.junit.Test
    public void testCommentsInComment1And2() {
        spoon.reflect.factory.Factory f = getSpoonFactory();
        spoon.reflect.declaration.CtClass<?> type = ((spoon.reflect.declaration.CtClass<?>) (f.Type().get(spoon.test.comment.testclasses.Comment1.class)));
        java.util.List<spoon.reflect.code.CtComment> comments = type.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtComment>(spoon.reflect.code.CtComment.class));
        org.junit.Assert.assertEquals(4, comments.size());
        type = ((spoon.reflect.declaration.CtClass<?>) (f.Type().get(spoon.test.comment.testclasses.Comment2.class)));
        comments = type.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtComment>(spoon.reflect.code.CtComment.class));
        org.junit.Assert.assertEquals(2, comments.size());
        spoon.reflect.code.CtComment commentD = comments.get(1);
        org.junit.Assert.assertEquals("D", commentD.getContent());
    }

    @org.junit.Test
    public void testCommentsInResourcesWithWindowsEOL() throws java.io.IOException {
        try (java.io.InputStream is = new java.io.FileInputStream(new java.io.File("./src/test/java/spoon/test/comment/testclasses/WindowsEOL.java"))) {
            int b;
            boolean lastWasCR = false;
            while ((b = is.read()) != (-1)) {
                if (lastWasCR) {
                    org.junit.Assert.assertTrue((b == '\n'));
                    lastWasCR = false;
                }
                if (b == '\r') {
                    lastWasCR = true;
                }
            } 
        }
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.run(new java.lang.String[]{ "-i", "./src/test/java/spoon/test/comment/testclasses/WindowsEOL.java", "-o", "./target/spooned/", "-c" });
        spoon.reflect.factory.Factory f = launcher.getFactory();
        spoon.reflect.declaration.CtClass<?> type = ((spoon.reflect.declaration.CtClass<?>) (f.Type().get(spoon.test.comment.testclasses.WindowsEOL.class)));
        spoon.reflect.code.CtJavaDoc classJavaDoc = ((spoon.reflect.code.CtJavaDoc) (type.getComments().get(0)));
        java.lang.String str = classJavaDoc.toString();
        java.util.StringTokenizer st = new java.util.StringTokenizer(str, java.lang.System.getProperty("line.separator"));
        boolean first = true;
        while (st.hasMoreTokens()) {
            java.lang.String line = st.nextToken();
            if (first) {
                first = false;
                org.junit.Assert.assertTrue(((line.length()) == 3));
                org.junit.Assert.assertEquals("/**", line);
            }else {
                if (st.hasMoreTokens()) {
                    org.junit.Assert.assertTrue(((line.length()) >= 2));
                    org.junit.Assert.assertEquals(" *", line.substring(0, 2));
                }else {
                    org.junit.Assert.assertTrue(((line.length()) == 3));
                    org.junit.Assert.assertEquals(" */", line.substring(0, 3));
                }
            }
        } 
        org.junit.Assert.assertEquals(("This file contains MS Windows EOL.\n" + ("It is here to test whether comments are printed well\n" + "in this case")), classJavaDoc.getContent());
    }

    @org.junit.Test
    public void testWildComments() {
        spoon.reflect.factory.Factory f = getSpoonFactory();
        spoon.reflect.declaration.CtClass<?> type = ((spoon.reflect.declaration.CtClass<?>) (f.Type().get(spoon.test.comment.testclasses.WildComments.class)));
        java.util.List<spoon.reflect.code.CtLiteral<java.lang.String>> literals = ((java.util.List) (((spoon.reflect.code.CtNewArray<?>) (type.getField("comments").getDefaultExpression())).getElements()));
        org.junit.Assert.assertTrue(((literals.size()) > 10));
        for (spoon.reflect.code.CtLiteral<java.lang.String> literal : literals) {
            org.junit.Assert.assertEquals(1, literal.getComments().size());
            spoon.reflect.code.CtComment comment = literal.getComments().get(0);
            java.lang.String expected = literal.getValue();
            org.junit.Assert.assertEquals(literal.getPosition().toString(), expected, comment.getContent());
        }
    }
}

