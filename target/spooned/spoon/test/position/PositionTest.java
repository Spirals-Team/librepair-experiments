package spoon.test.position;


public class PositionTest {
    @org.junit.Test
    public void testPositionClass() throws java.lang.Exception {
        final spoon.reflect.factory.Factory build = spoon.testing.utils.ModelUtils.build(new java.io.File("src/test/java/spoon/test/position/testclasses/"));
        final spoon.reflect.declaration.CtType<spoon.test.position.testclasses.FooClazz> foo = build.Type().get(spoon.test.position.testclasses.FooClazz.class);
        java.lang.String classContent = getClassContent(foo);
        spoon.reflect.cu.position.BodyHolderSourcePosition position = ((spoon.reflect.cu.position.BodyHolderSourcePosition) (foo.getPosition()));
        org.junit.Assert.assertEquals(4, position.getLine());
        org.junit.Assert.assertEquals(6, position.getEndLine());
        org.junit.Assert.assertEquals(42, position.getSourceStart());
        org.junit.Assert.assertEquals(79, position.getSourceEnd());
        org.junit.Assert.assertEquals(("@Deprecated\n" + (("public class FooClazz {\n" + "\n") + "}")), contentAtPosition(classContent, position));
        org.junit.Assert.assertEquals("{\n\n}", contentAtPosition(classContent, position.getBodyStart(), position.getBodyEnd()));
        final spoon.reflect.declaration.CtType<spoon.test.position.testclasses.FooClazz> foo2 = build.Type().get(spoon.test.position.testclasses.FooClazz2.class);
        org.junit.Assert.assertEquals(42, foo2.getPosition().getSourceStart());
        org.junit.Assert.assertEquals(4, foo2.getPosition().getLine());
        org.junit.Assert.assertEquals(4, foo2.getPosition().getEndLine());
        org.junit.Assert.assertEquals("FooClazz", contentAtPosition(classContent, position.getNameStart(), position.getNameEnd()));
        org.junit.Assert.assertEquals("@Deprecated\npublic", contentAtPosition(classContent, position.getModifierSourceStart(), position.getModifierSourceEnd()));
    }

    @org.junit.Test
    public void testPositionClassWithComments() throws java.lang.Exception {
        final spoon.reflect.factory.Factory build = spoon.testing.utils.ModelUtils.build(new java.io.File("src/test/java/spoon/test/position/testclasses/"));
        final spoon.reflect.declaration.CtType<spoon.test.position.testclasses.FooClazzWithComments> foo = build.Type().get(spoon.test.position.testclasses.FooClazzWithComments.class);
        java.lang.String classContent = getClassContent(foo);
        spoon.reflect.cu.position.BodyHolderSourcePosition position = ((spoon.reflect.cu.position.BodyHolderSourcePosition) (foo.getPosition()));
        org.junit.Assert.assertEquals(42, position.getSourceStart());
        org.junit.Assert.assertEquals(132, position.getSourceEnd());
        org.junit.Assert.assertEquals(("/*c1*/\n" + (((((((("//lc1\n" + "public /*c2*/\n") + "//lc2 /*\n") + "class \n") + "// */\n") + "/*c3 class // */\n") + "FooClazzWithComments {\n") + "\n") + "}")), contentAtPosition(classContent, position));
        org.junit.Assert.assertEquals("{\n\n}", contentAtPosition(classContent, position.getBodyStart(), position.getBodyEnd()));
        final spoon.reflect.declaration.CtType<spoon.test.position.testclasses.FooClazz> foo2 = build.Type().get(spoon.test.position.testclasses.FooClazz2.class);
        org.junit.Assert.assertEquals(42, foo2.getPosition().getSourceStart());
        org.junit.Assert.assertEquals(4, foo2.getPosition().getLine());
        org.junit.Assert.assertEquals(4, foo2.getPosition().getEndLine());
        org.junit.Assert.assertEquals("FooClazzWithComments", contentAtPosition(classContent, position.getNameStart(), position.getNameEnd()));
        org.junit.Assert.assertEquals(("/*c1*/\n" + ("//lc1\n" + "public")), contentAtPosition(classContent, position.getModifierSourceStart(), position.getModifierSourceEnd()));
    }

    @org.junit.Test
    public void testPositionParameterTypeReference() throws java.lang.Exception {
        final spoon.reflect.factory.Factory build = spoon.testing.utils.ModelUtils.build(new java.io.File("src/test/java/spoon/test/position/testclasses/"));
        final spoon.reflect.declaration.CtType<?> foo = build.Type().get(spoon.test.position.testclasses.PositionParameterTypeWithReference.class);
        java.lang.String classContent = getClassContent(foo);
        spoon.reflect.reference.CtTypeReference<?> field2Type = foo.getField("field2").getType();
        org.junit.Assert.assertEquals("List<T>[][]", contentAtPosition(classContent, field2Type.getPosition()));
        spoon.reflect.reference.CtTypeReference<?> field1Type = foo.getField("field1").getType();
        org.junit.Assert.assertEquals("List<T>", contentAtPosition(classContent, field1Type.getPosition()));
        spoon.reflect.reference.CtTypeReference<?> field3Type = foo.getField("field3").getType();
        org.junit.Assert.assertEquals("List<T // */ >\n\t/*// */>", contentAtPosition(classContent, field3Type.getPosition()));
    }

    @org.junit.Test
    public void testPositionInterface() throws java.lang.Exception {
        final spoon.reflect.factory.Factory build = spoon.testing.utils.ModelUtils.build(new java.io.File("src/test/java/spoon/test/position/testclasses/"));
        final spoon.reflect.declaration.CtType<spoon.test.position.testclasses.FooInterface> foo = build.Type().get(spoon.test.position.testclasses.FooInterface.class);
        java.lang.String classContent = getClassContent(foo);
        spoon.reflect.cu.position.BodyHolderSourcePosition position = ((spoon.reflect.cu.position.BodyHolderSourcePosition) (foo.getPosition()));
        org.junit.Assert.assertEquals(7, position.getLine());
        org.junit.Assert.assertEquals(9, position.getEndLine());
        org.junit.Assert.assertEquals(96, position.getSourceStart());
        org.junit.Assert.assertEquals(169, position.getSourceEnd());
        org.junit.Assert.assertEquals(("@Deprecated\n" + ((("@InnerAnnot(value=\"machin\")\n" + "public interface FooInterface {\n") + "\n") + "}")), contentAtPosition(classContent, position));
        org.junit.Assert.assertEquals("{\n\n}", contentAtPosition(classContent, position.getBodyStart(), position.getBodyEnd()));
        org.junit.Assert.assertEquals("FooInterface", contentAtPosition(classContent, position.getNameStart(), position.getNameEnd()));
        org.junit.Assert.assertEquals("@Deprecated\n@InnerAnnot(value=\"machin\")\npublic", contentAtPosition(classContent, position.getModifierSourceStart(), position.getModifierSourceEnd()));
        {
            spoon.reflect.cu.SourcePosition annPosition = foo.getAnnotations().get(0).getPosition();
            org.junit.Assert.assertEquals("@Deprecated", contentAtPosition(classContent, annPosition.getSourceStart(), annPosition.getSourceEnd()));
        }
        {
            spoon.reflect.cu.SourcePosition annPosition = foo.getAnnotations().get(1).getPosition();
            org.junit.Assert.assertEquals("@InnerAnnot(value=\"machin\")", contentAtPosition(classContent, annPosition.getSourceStart(), annPosition.getSourceEnd()));
        }
    }

    @org.junit.Test
    public void testPositionAnnotation() throws java.lang.Exception {
        final spoon.reflect.factory.Factory build = spoon.testing.utils.ModelUtils.build(new java.io.File("src/test/java/spoon/test/position/testclasses/"));
        final spoon.reflect.declaration.CtType<spoon.test.position.testclasses.FooAnnotation> foo = build.Type().get(spoon.test.position.testclasses.FooAnnotation.class);
        java.lang.String classContent = getClassContent(foo);
        spoon.reflect.cu.position.BodyHolderSourcePosition position = ((spoon.reflect.cu.position.BodyHolderSourcePosition) (foo.getPosition()));
        org.junit.Assert.assertEquals(9, position.getLine());
        org.junit.Assert.assertEquals(11, position.getEndLine());
        org.junit.Assert.assertEquals(163, position.getSourceStart());
        org.junit.Assert.assertEquals(279, position.getSourceEnd());
        org.junit.Assert.assertEquals(("@Target(value={})\n" + ((("@Retention(RetentionPolicy.RUNTIME)  \n" + "public abstract @interface FooAnnotation {\n") + "\tString value();\n") + "}")), contentAtPosition(classContent, position));
        org.junit.Assert.assertEquals(("{\n" + ("\tString value();\n" + "}")), contentAtPosition(classContent, position.getBodyStart(), position.getBodyEnd()));
        org.junit.Assert.assertEquals("FooAnnotation", contentAtPosition(classContent, position.getNameStart(), position.getNameEnd()));
        org.junit.Assert.assertEquals(("@Target(value={})\n" + "@Retention(RetentionPolicy.RUNTIME)  \npublic abstract"), contentAtPosition(classContent, position.getModifierSourceStart(), position.getModifierSourceEnd()));
        spoon.reflect.declaration.CtMethod<?> method1 = foo.getMethodsByName("value").get(0);
        spoon.reflect.cu.position.BodyHolderSourcePosition position1 = ((spoon.reflect.cu.position.BodyHolderSourcePosition) (method1.getPosition()));
        org.junit.Assert.assertEquals(10, position1.getLine());
        org.junit.Assert.assertEquals(10, position1.getEndLine());
        org.junit.Assert.assertEquals(263, position1.getSourceStart());
        org.junit.Assert.assertEquals(277, position1.getSourceEnd());
        org.junit.Assert.assertEquals("String value();", contentAtPosition(classContent, position1));
        org.junit.Assert.assertEquals("value", contentAtPosition(classContent, position1.getNameStart(), position1.getNameEnd()));
        org.junit.Assert.assertEquals("", contentAtPosition(classContent, position1.getModifierSourceStart(), position1.getModifierSourceEnd()));
        org.junit.Assert.assertEquals("", contentAtPosition(classContent, position1.getBodyStart(), position1.getBodyEnd()));
    }

    @org.junit.Test
    public void testPositionField() throws java.lang.Exception {
        final spoon.reflect.factory.Factory build = spoon.testing.utils.ModelUtils.build(spoon.test.position.testclasses.FooField.class);
        final spoon.reflect.declaration.CtType<spoon.test.position.testclasses.FooField> foo = build.Type().get(spoon.test.position.testclasses.FooField.class);
        java.lang.String classContent = getClassContent(foo);
        spoon.reflect.cu.position.DeclarationSourcePosition position1 = ((spoon.reflect.cu.position.DeclarationSourcePosition) (foo.getField("field1").getPosition()));
        org.junit.Assert.assertEquals(5, position1.getLine());
        org.junit.Assert.assertEquals(5, position1.getEndLine());
        org.junit.Assert.assertEquals(68, position1.getSourceStart());
        org.junit.Assert.assertEquals(95, position1.getSourceEnd());
        org.junit.Assert.assertEquals("public final int field1 = 0;", contentAtPosition(classContent, position1));
        org.junit.Assert.assertEquals("field1", contentAtPosition(classContent, position1.getNameStart(), position1.getNameEnd()));
        org.junit.Assert.assertEquals("public final", contentAtPosition(classContent, position1.getModifierSourceStart(), position1.getModifierSourceEnd()));
        spoon.reflect.cu.position.DeclarationSourcePosition position2 = ((spoon.reflect.cu.position.DeclarationSourcePosition) (foo.getField("field2").getPosition()));
        org.junit.Assert.assertEquals(7, position2.getLine());
        org.junit.Assert.assertEquals(8, position2.getEndLine());
        org.junit.Assert.assertEquals(99, position2.getSourceStart());
        org.junit.Assert.assertEquals(116, position2.getSourceEnd());
        org.junit.Assert.assertEquals(("int field2 =\n" + "\t\t\t0;"), contentAtPosition(classContent, position2));
        org.junit.Assert.assertEquals("field2", contentAtPosition(classContent, position2.getNameStart(), position2.getNameEnd()));
        org.junit.Assert.assertEquals("", contentAtPosition(classContent, position2.getModifierSourceStart(), position2.getModifierSourceEnd()));
        spoon.reflect.code.CtAssignment m = foo.getMethod("m").getBody().getStatement(0);
        spoon.reflect.code.CtFieldAccess assigned = ((spoon.reflect.code.CtFieldAccess) (m.getAssigned()));
        spoon.reflect.cu.SourcePosition position3 = assigned.getPosition();
        org.junit.Assert.assertEquals(13, position3.getLine());
        org.junit.Assert.assertEquals(13, position3.getEndLine());
        org.junit.Assert.assertEquals(168, position3.getSourceStart());
        org.junit.Assert.assertEquals(184, position3.getSourceEnd());
        org.junit.Assert.assertEquals("FooField.f.field2", contentAtPosition(classContent, position3));
        spoon.reflect.code.CtFieldAccess target = ((spoon.reflect.code.CtFieldAccess) (assigned.getTarget()));
        spoon.reflect.cu.SourcePosition position4 = target.getPosition();
        org.junit.Assert.assertEquals(13, position4.getLine());
        org.junit.Assert.assertEquals(13, position4.getEndLine());
        org.junit.Assert.assertEquals(168, position4.getSourceStart());
        org.junit.Assert.assertEquals(177, position4.getSourceEnd());
        org.junit.Assert.assertEquals("FooField.f", contentAtPosition(classContent, position4));
        spoon.reflect.code.CtExpression typeAccess = target.getTarget();
        spoon.reflect.cu.SourcePosition position5 = typeAccess.getPosition();
        org.junit.Assert.assertEquals(13, position5.getLine());
        org.junit.Assert.assertEquals(13, position5.getEndLine());
        org.junit.Assert.assertEquals(168, position5.getSourceStart());
        org.junit.Assert.assertEquals(175, position5.getSourceEnd());
        org.junit.Assert.assertEquals("FooField", contentAtPosition(classContent, position5));
    }

    @org.junit.Test
    public void testPositionGeneric() throws java.lang.Exception {
        final spoon.reflect.factory.Factory build = spoon.testing.utils.ModelUtils.build(spoon.test.position.testclasses.FooGeneric.class);
        final spoon.reflect.declaration.CtClass<spoon.test.position.testclasses.FooGeneric> foo = build.Class().get(spoon.test.position.testclasses.FooGeneric.class);
        java.lang.String classContent = getClassContent(foo);
        spoon.reflect.cu.position.BodyHolderSourcePosition position = ((spoon.reflect.cu.position.BodyHolderSourcePosition) (foo.getPosition()));
        org.junit.Assert.assertEquals(3, position.getLine());
        org.junit.Assert.assertEquals(31, position.getEndLine());
        org.junit.Assert.assertEquals(42, position.getSourceStart());
        org.junit.Assert.assertEquals(411, position.getSourceEnd());
        org.junit.Assert.assertEquals("FooGeneric", contentAtPosition(classContent, position.getNameStart(), position.getNameEnd()));
        org.junit.Assert.assertEquals("public", contentAtPosition(classContent, position.getModifierSourceStart(), position.getModifierSourceEnd()));
        spoon.reflect.cu.position.DeclarationSourcePosition position1 = ((spoon.reflect.cu.position.DeclarationSourcePosition) (foo.getField("variable").getPosition()));
        org.junit.Assert.assertEquals(5, position1.getLine());
        org.junit.Assert.assertEquals(5, position1.getEndLine());
        org.junit.Assert.assertEquals(88, position1.getSourceStart());
        org.junit.Assert.assertEquals(118, position1.getSourceEnd());
        org.junit.Assert.assertEquals("public final T variable = null;", contentAtPosition(classContent, position1));
        org.junit.Assert.assertEquals("variable", contentAtPosition(classContent, position1.getNameStart(), position1.getNameEnd()));
        org.junit.Assert.assertEquals("public final", contentAtPosition(classContent, position1.getModifierSourceStart(), position1.getModifierSourceEnd()));
        spoon.reflect.declaration.CtMethod<?> method1 = foo.getMethodsByName("m").get(0);
        spoon.reflect.cu.position.BodyHolderSourcePosition position2 = ((spoon.reflect.cu.position.BodyHolderSourcePosition) (method1.getPosition()));
        org.junit.Assert.assertEquals(("public @Deprecated static <S> S m(int parm1) {\n" + ("\t\treturn null;\n" + "\t}")), contentAtPosition(classContent, position2));
        org.junit.Assert.assertEquals("m", contentAtPosition(classContent, position2.getNameStart(), position2.getNameEnd()));
        org.junit.Assert.assertEquals("public @Deprecated static", contentAtPosition(classContent, position2.getModifierSourceStart(), position2.getModifierSourceEnd()));
    }

    @org.junit.Test
    public void testPositionMethod() throws java.lang.Exception {
        final spoon.reflect.factory.Factory build = spoon.testing.utils.ModelUtils.build(spoon.test.position.testclasses.FooMethod.class);
        final spoon.reflect.declaration.CtClass<spoon.test.position.testclasses.FooMethod> foo = build.Class().get(spoon.test.position.testclasses.FooMethod.class);
        java.lang.String classContent = getClassContent(foo);
        spoon.reflect.declaration.CtMethod<?> method1 = foo.getMethodsByName("m").get(0);
        spoon.reflect.cu.position.BodyHolderSourcePosition position1 = ((spoon.reflect.cu.position.BodyHolderSourcePosition) (method1.getPosition()));
        org.junit.Assert.assertEquals(5, position1.getLine());
        org.junit.Assert.assertEquals(7, position1.getEndLine());
        org.junit.Assert.assertEquals(69, position1.getSourceStart());
        org.junit.Assert.assertEquals(114, position1.getSourceEnd());
        org.junit.Assert.assertEquals(("public static void m(int parm1) {\n" + ("\t\treturn;\n" + "\t}")), contentAtPosition(classContent, position1));
        org.junit.Assert.assertEquals("m", contentAtPosition(classContent, position1.getNameStart(), position1.getNameEnd()));
        org.junit.Assert.assertEquals("public static", contentAtPosition(classContent, position1.getModifierSourceStart(), position1.getModifierSourceEnd()));
        org.junit.Assert.assertEquals(("{\n" + ("\t\treturn;\n" + "\t}")), contentAtPosition(classContent, position1.getBodyStart(), position1.getBodyEnd()));
        spoon.reflect.cu.position.DeclarationSourcePosition positionParam1 = ((spoon.reflect.cu.position.DeclarationSourcePosition) (method1.getParameters().get(0).getPosition()));
        org.junit.Assert.assertEquals(5, positionParam1.getLine());
        org.junit.Assert.assertEquals(5, positionParam1.getEndLine());
        org.junit.Assert.assertEquals(90, positionParam1.getSourceStart());
        org.junit.Assert.assertEquals(98, positionParam1.getSourceEnd());
        org.junit.Assert.assertEquals("int parm1", contentAtPosition(classContent, positionParam1));
        org.junit.Assert.assertEquals("parm1", contentAtPosition(classContent, positionParam1.getNameStart(), positionParam1.getNameEnd()));
        org.junit.Assert.assertEquals("", contentAtPosition(classContent, positionParam1.getModifierSourceStart(), positionParam1.getModifierSourceEnd()));
        spoon.reflect.declaration.CtMethod method2 = foo.getMethodsByName("mWithDoc").get(0);
        spoon.reflect.cu.position.BodyHolderSourcePosition position2 = ((spoon.reflect.cu.position.BodyHolderSourcePosition) (method2.getPosition()));
        org.junit.Assert.assertEquals(13, position2.getLine());
        org.junit.Assert.assertEquals(15, position2.getEndLine());
        org.junit.Assert.assertEquals(("/**\n" + ((((("\t * Mathod with javadoc\n" + "\t * @param parm1 the parameter\n") + "\t */\n") + "\tint mWithDoc(int parm1) {\n") + "\t\treturn parm1;\n") + "\t}")), contentAtPosition(classContent, position2));
        org.junit.Assert.assertEquals("mWithDoc", contentAtPosition(classContent, position2.getNameStart(), position2.getNameEnd()));
        org.junit.Assert.assertEquals("", contentAtPosition(classContent, position2.getModifierSourceStart(), position2.getModifierSourceEnd()));
        spoon.reflect.declaration.CtConstructor<spoon.test.position.testclasses.FooMethod> constructor = foo.getConstructor(build.Type().integerPrimitiveType());
        spoon.reflect.cu.SourcePosition position3 = constructor.getPosition();
        contentAtPosition(classContent, position3);
        spoon.reflect.declaration.CtMethod mWithLine = foo.getMethod("mWithLine", build.Type().integerPrimitiveType());
        spoon.reflect.cu.SourcePosition position4 = mWithLine.getPosition();
        contentAtPosition(classContent, position4);
    }

    @org.junit.Test
    public void testPositionAbstractMethod() throws java.lang.Exception {
        final spoon.reflect.factory.Factory build = spoon.testing.utils.ModelUtils.build(spoon.test.position.testclasses.FooAbstractMethod.class);
        final spoon.reflect.declaration.CtClass<spoon.test.position.testclasses.FooMethod> foo = build.Class().get(spoon.test.position.testclasses.FooAbstractMethod.class);
        java.lang.String classContent = getClassContent(foo);
        spoon.reflect.declaration.CtMethod<?> method1 = foo.getMethodsByName("m").get(0);
        spoon.reflect.cu.position.BodyHolderSourcePosition position1 = ((spoon.reflect.cu.position.BodyHolderSourcePosition) (method1.getPosition()));
        org.junit.Assert.assertEquals(5, position1.getLine());
        org.junit.Assert.assertEquals(5, position1.getEndLine());
        org.junit.Assert.assertEquals(86, position1.getSourceStart());
        org.junit.Assert.assertEquals(125, position1.getSourceEnd());
        org.junit.Assert.assertEquals("public abstract void m(final int parm1);", contentAtPosition(classContent, position1));
        org.junit.Assert.assertEquals("m", contentAtPosition(classContent, position1.getNameStart(), position1.getNameEnd()));
        org.junit.Assert.assertEquals("public abstract", contentAtPosition(classContent, position1.getModifierSourceStart(), position1.getModifierSourceEnd()));
        org.junit.Assert.assertEquals("", contentAtPosition(classContent, position1.getBodyStart(), position1.getBodyEnd()));
        spoon.reflect.cu.position.DeclarationSourcePosition positionParam1 = ((spoon.reflect.cu.position.DeclarationSourcePosition) (method1.getParameters().get(0).getPosition()));
        org.junit.Assert.assertEquals(5, positionParam1.getLine());
        org.junit.Assert.assertEquals(5, positionParam1.getEndLine());
        org.junit.Assert.assertEquals(109, positionParam1.getSourceStart());
        org.junit.Assert.assertEquals(123, positionParam1.getSourceEnd());
        org.junit.Assert.assertEquals("final int parm1", contentAtPosition(classContent, positionParam1));
        org.junit.Assert.assertEquals("parm1", contentAtPosition(classContent, positionParam1.getNameStart(), positionParam1.getNameEnd()));
        org.junit.Assert.assertEquals("final", contentAtPosition(classContent, positionParam1.getModifierSourceStart(), positionParam1.getModifierSourceEnd()));
    }

    @org.junit.Test
    public void testPositionStatement() throws java.lang.Exception {
        final spoon.reflect.factory.Factory build = spoon.testing.utils.ModelUtils.build(spoon.test.position.testclasses.FooStatement.class);
        final spoon.reflect.declaration.CtType<spoon.test.position.testclasses.FooStatement> foo = build.Type().get(spoon.test.position.testclasses.FooStatement.class);
        java.lang.String classContent = getClassContent(foo);
        spoon.reflect.declaration.CtMethod<?> method1 = foo.getMethodsByName("m").get(0);
        spoon.reflect.code.CtBlock<?> body = method1.getBody();
        spoon.reflect.cu.SourcePosition positionBody = body.getPosition();
        org.junit.Assert.assertEquals(7, positionBody.getLine());
        org.junit.Assert.assertEquals(23, positionBody.getEndLine());
        org.junit.Assert.assertEquals(("{\n" + ((((((((((((((("\t\tint field2 = m2(parm1);\n" + "\t\tthis.field = m2(parm1);\n") + "\t\tif(parm1 > 2 && true) {\n") + "\t\t\tswitch (parm1) {\n") + "\t\t\tcase 1:\n") + "\t\t\t\treturn;\n") + "\t\t\tdefault:\n") + "\t\t\t\tparm1++;\n") + "\t\t\t}\n") + "\t\t\tint count = 0;\n") + "\t\t\tfor (int i =0; i< parm1; i++) {\n") + "\t\t\t\tcount ++;\n") + "\t\t\t}\n") + "\t\t}\n") + "\t\treturn;\n") + "\t}")), contentAtPosition(classContent, positionBody));
        spoon.reflect.cu.SourcePosition positionLocalVariable = body.getStatement(0).getPosition();
        org.junit.Assert.assertEquals(8, positionLocalVariable.getLine());
        org.junit.Assert.assertEquals(8, positionLocalVariable.getEndLine());
        org.junit.Assert.assertEquals("int field2 = m2(parm1);", contentAtPosition(classContent, positionLocalVariable));
        spoon.reflect.cu.SourcePosition positionFieldWrite = body.getStatement(1).getPosition();
        org.junit.Assert.assertEquals(9, positionFieldWrite.getLine());
        org.junit.Assert.assertEquals(9, positionFieldWrite.getEndLine());
        org.junit.Assert.assertEquals("this.field = m2(parm1);", contentAtPosition(classContent, positionFieldWrite));
        spoon.reflect.code.CtIf ctIf = body.getStatement(2);
        spoon.reflect.cu.SourcePosition positionIf = ctIf.getPosition();
        org.junit.Assert.assertEquals(10, positionIf.getLine());
        org.junit.Assert.assertEquals(21, positionIf.getEndLine());
        org.junit.Assert.assertEquals(("if(parm1 > 2 && true) {\n" + (((((((((("\t\t\tswitch (parm1) {\n" + "\t\t\tcase 1:\n") + "\t\t\t\treturn;\n") + "\t\t\tdefault:\n") + "\t\t\t\tparm1++;\n") + "\t\t\t}\n") + "\t\t\tint count = 0;\n") + "\t\t\tfor (int i =0; i< parm1; i++) {\n") + "\t\t\t\tcount ++;\n") + "\t\t\t}\n") + "\t\t}")), contentAtPosition(classContent, positionIf));
        spoon.reflect.cu.SourcePosition positionSwitch = ((spoon.reflect.code.CtBlock) (ctIf.getThenStatement())).getStatement(0).getPosition();
        org.junit.Assert.assertEquals(11, positionSwitch.getLine());
        org.junit.Assert.assertEquals(16, positionSwitch.getEndLine());
        org.junit.Assert.assertEquals(("switch (parm1) {\n" + (((("\t\t\tcase 1:\n" + "\t\t\t\treturn;\n") + "\t\t\tdefault:\n") + "\t\t\t\tparm1++;\n") + "\t\t\t}")), contentAtPosition(classContent, positionSwitch));
        positionLocalVariable = ((spoon.reflect.code.CtBlock) (ctIf.getThenStatement())).getStatement(1).getPosition();
        org.junit.Assert.assertEquals(17, positionLocalVariable.getLine());
        org.junit.Assert.assertEquals(17, positionLocalVariable.getEndLine());
        org.junit.Assert.assertEquals("int count = 0;", contentAtPosition(classContent, positionLocalVariable));
        spoon.reflect.cu.SourcePosition positionFor = ((spoon.reflect.code.CtBlock) (ctIf.getThenStatement())).getStatement(2).getPosition();
        org.junit.Assert.assertEquals(18, positionFor.getLine());
        org.junit.Assert.assertEquals(20, positionFor.getEndLine());
        org.junit.Assert.assertEquals(("for (int i =0; i< parm1; i++) {\n" + ("\t\t\t\tcount ++;\n" + "\t\t\t}")), contentAtPosition(classContent, positionFor));
        spoon.reflect.cu.SourcePosition positionReturn = method1.getBody().getStatement(3).getPosition();
        org.junit.Assert.assertEquals(22, positionReturn.getLine());
        org.junit.Assert.assertEquals(22, positionReturn.getEndLine());
        org.junit.Assert.assertEquals("return;", contentAtPosition(classContent, positionReturn));
    }

    private java.lang.String getClassContent(spoon.reflect.declaration.CtType type) {
        java.io.File file = type.getPosition().getFile();
        try {
            return org.apache.commons.io.FileUtils.readFileToString(file, "UTF-8");
        } catch (java.io.IOException e) {
            throw new java.lang.RuntimeException(e);
        }
    }

    private java.lang.String contentAtPosition(java.lang.String content, int start, int end) {
        return content.substring(start, (end + 1));
    }

    private java.lang.String contentAtPosition(java.lang.String content, spoon.reflect.cu.SourcePosition position) {
        return content.substring(position.getSourceStart(), ((position.getSourceEnd()) + 1));
    }

    @org.junit.Test
    public void testSourcePosition() throws java.lang.Exception {
        spoon.reflect.cu.SourcePosition s = new spoon.Launcher().getFactory().Core().createClass().getPosition();
        org.junit.Assert.assertFalse(s.isValidPosition());
        spoon.test.position.PositionTest.assertFails(() -> s.getSourceStart());
        spoon.test.position.PositionTest.assertFails(() -> s.getSourceEnd());
        spoon.test.position.PositionTest.assertFails(() -> s.getColumn());
        spoon.test.position.PositionTest.assertFails(() -> s.getLine());
        org.junit.Assert.assertEquals("(unknown file)", s.toString());
        org.junit.Assert.assertTrue(((s.hashCode()) > 0));
    }

    private static void assertFails(java.lang.Runnable code) {
        try {
            code.run();
            org.junit.Assert.fail();
        } catch (java.lang.Exception e) {
        }
    }

    @org.junit.Test
    public void defaultConstructorPositionTest() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<spoon.test.position.testclasses.Foo> aClass = ((spoon.reflect.declaration.CtClass<spoon.test.position.testclasses.Foo>) (spoon.testing.utils.ModelUtils.buildClass(spoon.test.position.testclasses.Foo.class)));
        spoon.reflect.declaration.CtConstructor<spoon.test.position.testclasses.Foo> defaultConstructor = aClass.getConstructor();
        org.junit.Assert.assertEquals(spoon.reflect.cu.SourcePosition.NOPOSITION, defaultConstructor.getPosition());
        spoon.reflect.code.CtStatement implicitSuperCall = defaultConstructor.getBody().getStatement(0);
        org.junit.Assert.assertTrue(implicitSuperCall.isImplicit());
        org.junit.Assert.assertEquals(spoon.reflect.cu.SourcePosition.NOPOSITION, implicitSuperCall.getPosition());
    }

    @org.junit.Test
    public void getPositionOfImplicitBlock() {
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/position/testclasses/ImplicitBlock.java");
        launcher.buildModel();
        spoon.reflect.code.CtIf ifElement = launcher.getModel().getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtIf>(spoon.reflect.code.CtIf.class)).get(0);
        spoon.reflect.code.CtStatement thenStatement = ifElement.getThenStatement();
        org.junit.Assert.assertTrue((thenStatement instanceof spoon.reflect.code.CtBlock));
        spoon.reflect.code.CtBlock thenBlock = ((spoon.reflect.code.CtBlock) (thenStatement));
        spoon.reflect.cu.SourcePosition positionThen = thenBlock.getPosition();
        spoon.reflect.code.CtStatement returnStatement = thenBlock.getStatement(0);
        org.junit.Assert.assertEquals(returnStatement.getPosition(), positionThen);
        org.junit.Assert.assertEquals("ImplicitBlock.java", positionThen.getFile().getName());
        org.junit.Assert.assertEquals(7, positionThen.getLine());
        spoon.reflect.code.CtStatement elseStatement = ifElement.getElseStatement();
        org.junit.Assert.assertTrue((elseStatement instanceof spoon.reflect.code.CtBlock));
        spoon.reflect.code.CtBlock elseBlock = ((spoon.reflect.code.CtBlock) (elseStatement));
        spoon.reflect.cu.SourcePosition positionElse = elseBlock.getPosition();
        spoon.reflect.code.CtStatement otherReturnStatement = elseBlock.getStatement(0);
        org.junit.Assert.assertEquals(otherReturnStatement.getPosition(), positionElse);
        org.junit.Assert.assertEquals("ImplicitBlock.java", positionThen.getFile().getName());
        org.junit.Assert.assertEquals(8, positionElse.getLine());
        org.junit.Assert.assertNotEquals(returnStatement, otherReturnStatement);
    }

    @org.junit.Test
    public void testPositionMethodTypeParameter() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<?> foo = spoon.testing.utils.ModelUtils.buildClass(spoon.test.position.testclasses.TypeParameter.class);
        java.lang.String classContent = getClassContent(foo);
        spoon.reflect.declaration.CtTypeParameter typeParam = foo.getMethodsByName("m").get(0).getFormalCtTypeParameters().get(0);
        org.junit.Assert.assertEquals("T extends List<?>", contentAtPosition(classContent, typeParam.getPosition()));
        org.junit.Assert.assertFalse(((typeParam.getPosition()) instanceof spoon.reflect.cu.position.DeclarationSourcePosition));
    }

    @org.junit.Test
    public void testPositionOfAnnonymousType() throws java.lang.Exception {
        final spoon.reflect.declaration.CtEnum foo = ((spoon.reflect.declaration.CtEnum) (spoon.testing.utils.ModelUtils.buildClass(spoon.test.position.testclasses.SomeEnum.class)));
        java.lang.String classContent = getClassContent(foo);
        spoon.reflect.code.CtNewClass<?> newClass = ((spoon.reflect.code.CtNewClass<?>) (foo.getEnumValue("X").getDefaultExpression()));
        spoon.reflect.declaration.CtClass<?> annonClass = newClass.getAnonymousClass();
        org.junit.Assert.assertEquals(("{\n" + ("\t\tvoid m() {};\n" + "	}")), contentAtPosition(classContent, annonClass.getPosition()));
        spoon.reflect.cu.position.BodyHolderSourcePosition bhsp = ((spoon.reflect.cu.position.BodyHolderSourcePosition) (annonClass.getPosition()));
        int start = annonClass.getPosition().getSourceStart();
        int end = annonClass.getPosition().getSourceEnd();
        org.junit.Assert.assertEquals(start, bhsp.getBodyStart());
        org.junit.Assert.assertEquals(end, bhsp.getBodyEnd());
        org.junit.Assert.assertEquals((start - 1), bhsp.getNameEnd());
        org.junit.Assert.assertEquals(start, bhsp.getModifierSourceStart());
        org.junit.Assert.assertEquals((start - 1), bhsp.getModifierSourceEnd());
        org.junit.Assert.assertEquals(start, bhsp.getNameStart());
        org.junit.Assert.assertEquals((start - 1), bhsp.getNameEnd());
    }

    @org.junit.Test
    public void testPositionOfAnnonymousTypeByNewInterface() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<?> foo = spoon.testing.utils.ModelUtils.buildClass(spoon.test.position.testclasses.AnnonymousClassNewIface.class);
        java.lang.String classContent = getClassContent(foo);
        spoon.reflect.code.CtLocalVariable<?> localVar = ((spoon.reflect.code.CtLocalVariable<?>) (foo.getMethodsByName("m").get(0).getBody().getStatement(0)));
        spoon.reflect.code.CtNewClass<?> newClass = ((spoon.reflect.code.CtNewClass<?>) (localVar.getDefaultExpression()));
        spoon.reflect.declaration.CtClass<?> annonClass = newClass.getAnonymousClass();
        spoon.reflect.cu.position.BodyHolderSourcePosition bhsp = ((spoon.reflect.cu.position.BodyHolderSourcePosition) (annonClass.getPosition()));
        int start = annonClass.getPosition().getSourceStart();
        int end = annonClass.getPosition().getSourceEnd();
        org.junit.Assert.assertEquals(("Consumer<Set<?>>() {\r\n" + ((("\t\t\t@Override\r\n" + "\t\t\tpublic void accept(Set<?> t) {\r\n") + "\t\t\t}\r\n") + "		}")), contentAtPosition(classContent, start, end));
        org.junit.Assert.assertEquals(("{\r\n" + ((("\t\t\t@Override\r\n" + "\t\t\tpublic void accept(Set<?> t) {\r\n") + "\t\t\t}\r\n") + "		}")), contentAtPosition(classContent, bhsp.getBodyStart(), bhsp.getBodyEnd()));
        org.junit.Assert.assertEquals((start - 1), bhsp.getNameEnd());
        org.junit.Assert.assertEquals(start, bhsp.getModifierSourceStart());
        org.junit.Assert.assertEquals((start - 1), bhsp.getModifierSourceEnd());
        org.junit.Assert.assertEquals(start, bhsp.getNameStart());
        org.junit.Assert.assertEquals((start - 1), bhsp.getNameEnd());
    }

    @org.junit.Test
    public void testEmptyModifiersOfMethod() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<?> foo = spoon.testing.utils.ModelUtils.buildClass(spoon.test.position.testclasses.NoMethodModifiers.class);
        java.lang.String classContent = getClassContent(foo);
        spoon.reflect.cu.position.BodyHolderSourcePosition bhsp = ((spoon.reflect.cu.position.BodyHolderSourcePosition) (foo.getMethodsByName("m").get(0).getPosition()));
        org.junit.Assert.assertEquals("void m();", contentAtPosition(classContent, bhsp));
        int start = bhsp.getSourceStart();
        int end = bhsp.getSourceEnd();
        org.junit.Assert.assertEquals(start, bhsp.getModifierSourceStart());
        org.junit.Assert.assertEquals((start - 1), bhsp.getModifierSourceEnd());
        org.junit.Assert.assertEquals("m", contentAtPosition(classContent, bhsp.getNameStart(), bhsp.getNameEnd()));
        org.junit.Assert.assertEquals(end, bhsp.getBodyStart());
        org.junit.Assert.assertEquals((end - 1), bhsp.getBodyEnd());
    }

    @org.junit.Test
    public void testPositionTryCatch() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<?> foo = spoon.testing.utils.ModelUtils.buildClass(spoon.test.position.testclasses.PositionTry.class);
        java.lang.String classContent = getClassContent(foo);
        java.util.List<spoon.reflect.code.CtCatchVariable> elements = foo.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtCatchVariable.class));
        spoon.reflect.code.CtCatchVariable withoutModifier = elements.get(0);
        org.junit.Assert.assertEquals("java.lang.Exception e", contentAtPosition(classContent, withoutModifier.getPosition().getSourceStart(), withoutModifier.getPosition().getSourceEnd()));
        org.junit.Assert.assertEquals("e", contentAtPosition(classContent, ((spoon.reflect.cu.position.DeclarationSourcePosition) (withoutModifier.getPosition())).getNameStart(), ((spoon.reflect.cu.position.DeclarationSourcePosition) (withoutModifier.getPosition())).getNameEnd()));
        org.junit.Assert.assertEquals("", contentAtPosition(classContent, ((spoon.reflect.cu.position.DeclarationSourcePosition) (withoutModifier.getPosition())).getModifierSourceStart(), ((spoon.reflect.cu.position.DeclarationSourcePosition) (withoutModifier.getPosition())).getModifierSourceEnd()));
        spoon.reflect.code.CtCatchVariable withModifier = elements.get(1);
        org.junit.Assert.assertEquals("final java.lang.Exception e", contentAtPosition(classContent, withModifier.getPosition().getSourceStart(), withModifier.getPosition().getSourceEnd()));
        org.junit.Assert.assertEquals("e", contentAtPosition(classContent, ((spoon.reflect.cu.position.DeclarationSourcePosition) (withModifier.getPosition())).getNameStart(), ((spoon.reflect.cu.position.DeclarationSourcePosition) (withModifier.getPosition())).getNameEnd()));
        org.junit.Assert.assertEquals("final", contentAtPosition(classContent, ((spoon.reflect.cu.position.DeclarationSourcePosition) (withModifier.getPosition())).getModifierSourceStart(), ((spoon.reflect.cu.position.DeclarationSourcePosition) (withModifier.getPosition())).getModifierSourceEnd()));
        spoon.reflect.code.CtCatchVariable withMultipleCatch = elements.get(2);
        org.junit.Assert.assertEquals("NullPointerException | java.lang.ArithmeticException e", contentAtPosition(classContent, withMultipleCatch.getPosition().getSourceStart(), withMultipleCatch.getPosition().getSourceEnd()));
        org.junit.Assert.assertEquals("e", contentAtPosition(classContent, ((spoon.reflect.cu.position.DeclarationSourcePosition) (withMultipleCatch.getPosition())).getNameStart(), ((spoon.reflect.cu.position.DeclarationSourcePosition) (withMultipleCatch.getPosition())).getNameEnd()));
        org.junit.Assert.assertEquals("", contentAtPosition(classContent, ((spoon.reflect.cu.position.DeclarationSourcePosition) (withMultipleCatch.getPosition())).getModifierSourceStart(), ((spoon.reflect.cu.position.DeclarationSourcePosition) (withMultipleCatch.getPosition())).getModifierSourceEnd()));
        foo = spoon.testing.utils.ModelUtils.buildClass(spoon.test.comment.testclasses.Comment1.class);
        classContent = getClassContent(foo);
        elements = foo.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtCatchVariable.class));
        withoutModifier = elements.get(0);
        org.junit.Assert.assertEquals("Exception ex", contentAtPosition(classContent, withoutModifier.getPosition().getSourceStart(), withoutModifier.getPosition().getSourceEnd()));
        org.junit.Assert.assertEquals("ex", contentAtPosition(classContent, ((spoon.reflect.cu.position.DeclarationSourcePosition) (withoutModifier.getPosition())).getNameStart(), ((spoon.reflect.cu.position.DeclarationSourcePosition) (withoutModifier.getPosition())).getNameEnd()));
        org.junit.Assert.assertEquals("", contentAtPosition(classContent, ((spoon.reflect.cu.position.DeclarationSourcePosition) (withoutModifier.getPosition())).getModifierSourceStart(), ((spoon.reflect.cu.position.DeclarationSourcePosition) (withoutModifier.getPosition())).getModifierSourceEnd()));
        foo = spoon.testing.utils.ModelUtils.buildClass(spoon.test.query_function.testclasses.VariableReferencesModelTest.class);
        classContent = getClassContent(foo);
        elements = foo.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtCatchVariable.class));
        withoutModifier = elements.get(0);
        org.junit.Assert.assertEquals("IllegalArgumentException e", contentAtPosition(classContent, withoutModifier.getPosition().getSourceStart(), withoutModifier.getPosition().getSourceEnd()));
        org.junit.Assert.assertEquals("e", contentAtPosition(classContent, ((spoon.reflect.cu.position.DeclarationSourcePosition) (withoutModifier.getPosition())).getNameStart(), ((spoon.reflect.cu.position.DeclarationSourcePosition) (withoutModifier.getPosition())).getNameEnd()));
        org.junit.Assert.assertEquals("", contentAtPosition(classContent, ((spoon.reflect.cu.position.DeclarationSourcePosition) (withoutModifier.getPosition())).getModifierSourceStart(), ((spoon.reflect.cu.position.DeclarationSourcePosition) (withoutModifier.getPosition())).getModifierSourceEnd()));
        withoutModifier = elements.get(1);
        org.junit.Assert.assertEquals("Exception /*7*/field", contentAtPosition(classContent, withoutModifier.getPosition().getSourceStart(), withoutModifier.getPosition().getSourceEnd()));
        org.junit.Assert.assertEquals("field", contentAtPosition(classContent, ((spoon.reflect.cu.position.DeclarationSourcePosition) (withoutModifier.getPosition())).getNameStart(), ((spoon.reflect.cu.position.DeclarationSourcePosition) (withoutModifier.getPosition())).getNameEnd()));
        org.junit.Assert.assertEquals("", contentAtPosition(classContent, ((spoon.reflect.cu.position.DeclarationSourcePosition) (withoutModifier.getPosition())).getModifierSourceStart(), ((spoon.reflect.cu.position.DeclarationSourcePosition) (withoutModifier.getPosition())).getModifierSourceEnd()));
    }
}

