package spoon.test.position;

import org.junit.Test;
import spoon.Launcher;
import spoon.reflect.code.CtAssignment;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtFieldAccess;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtStatement;
import spoon.reflect.cu.SourcePosition;
import spoon.reflect.cu.position.BodyHolderSourcePosition;
import spoon.reflect.cu.position.DeclarationSourcePosition;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.reflect.factory.Factory;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.test.position.testclasses.Foo;
import spoon.test.position.testclasses.FooAbstractMethod;
import spoon.test.position.testclasses.FooAnnotation;
import spoon.test.position.testclasses.FooClazz;
import spoon.test.position.testclasses.FooClazz2;
import spoon.test.position.testclasses.FooClazzWithComments;
import spoon.test.position.testclasses.FooField;
import spoon.test.position.testclasses.FooGeneric;
import spoon.test.position.testclasses.FooInterface;
import spoon.test.position.testclasses.FooMethod;
import spoon.test.position.testclasses.FooStatement;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static spoon.testing.utils.ModelUtils.build;
import static spoon.testing.utils.ModelUtils.buildClass;

public class PositionTest {

	@Test
	public void testPositionClass() throws Exception {
		final Factory build = build(new File("src/test/java/spoon/test/position/testclasses/"));
		final CtType<FooClazz> foo = build.Type().get(FooClazz.class);
		String classContent = getClassContent(foo);

		BodyHolderSourcePosition position = (BodyHolderSourcePosition) foo.getPosition();

		assertEquals(4, position.getLine());
		assertEquals(6, position.getEndLine());

		assertEquals(42, position.getSourceStart());
		assertEquals(79, position.getSourceEnd());
		assertEquals("@Deprecated\n"
				+ "public class FooClazz {\n"
				+ "\n"
				+ "}", contentAtPosition(classContent, position));

		assertEquals("{\n\n}", contentAtPosition(classContent, position.getBodyStart(), position.getBodyEnd()));

		// this specifies that getLine starts at name (and not at Javadoc or annotation)
		final CtType<FooClazz> foo2 = build.Type().get(FooClazz2.class);
		assertEquals(42, foo2.getPosition().getSourceStart());
		assertEquals(4, foo2.getPosition().getLine());
		assertEquals(4, foo2.getPosition().getEndLine());

		assertEquals("FooClazz", contentAtPosition(classContent, position.getNameStart(), position.getNameEnd()));
		assertEquals("public", contentAtPosition(classContent, position.getModifierSourceStart(), position.getModifierSourceEnd()));
	}
	
	
	@Test
	public void testPositionClassWithComments() throws Exception {
		final Factory build = build(new File("src/test/java/spoon/test/position/testclasses/"));
		final CtType<FooClazzWithComments> foo = build.Type().get(FooClazzWithComments.class);
		String classContent = getClassContent(foo);

		BodyHolderSourcePosition position = (BodyHolderSourcePosition) foo.getPosition();

//		assertEquals(4, position.getLine());
//		assertEquals(6, position.getEndLine());

		assertEquals(42, position.getSourceStart());
		assertEquals(112, position.getSourceEnd());
		assertEquals("/*c1*/\n" + 
				"//lc1\n" + 
				"public /*c2*/\n" + 
				"//lc2\n" + 
				"class /*c3*/\n" + 
				"FooClazzWithComments {\n" + 
				"\n" + 
				"}", contentAtPosition(classContent, position));

		assertEquals("{\n\n}", contentAtPosition(classContent, position.getBodyStart(), position.getBodyEnd()));

		// this specifies that getLine starts at name (and not at Javadoc or annotation)
		final CtType<FooClazz> foo2 = build.Type().get(FooClazz2.class);
		assertEquals(42, foo2.getPosition().getSourceStart());
		assertEquals(4, foo2.getPosition().getLine());
		assertEquals(4, foo2.getPosition().getEndLine());

		assertEquals("FooClazzWithComments", contentAtPosition(classContent, position.getNameStart(), position.getNameEnd()));
		assertEquals("/*c1*/\n" + 
				"//lc1\n" + 
				"public /*c2*/\n" + 
				"//lc2", contentAtPosition(classContent, position.getModifierSourceStart(), position.getModifierSourceEnd()));
	}

	
	@Test
	public void testPositionInterface() throws Exception {
		final Factory build = build(new File("src/test/java/spoon/test/position/testclasses/"));
		final CtType<FooInterface> foo = build.Type().get(FooInterface.class);
		String classContent = getClassContent(foo);

		BodyHolderSourcePosition position = (BodyHolderSourcePosition) foo.getPosition();

		assertEquals(7, position.getLine());
		assertEquals(9, position.getEndLine());

		assertEquals(96, position.getSourceStart());
		assertEquals(169, position.getSourceEnd());
		assertEquals("@Deprecated\n"
				+ "@InnerAnnot(value=\"machin\")\n"
				+ "public interface FooInterface {\n"
				+ "\n"
				+ "}", contentAtPosition(classContent, position));

		assertEquals("{\n\n}", contentAtPosition(classContent, position.getBodyStart(), position.getBodyEnd()));

		assertEquals("FooInterface", contentAtPosition(classContent, position.getNameStart(), position.getNameEnd()));
		assertEquals("public", contentAtPosition(classContent, position.getModifierSourceStart(), position.getModifierSourceEnd()));
		
		{
			SourcePosition annPosition = foo.getAnnotations().get(0).getPosition();
			assertEquals("@Deprecated", contentAtPosition(classContent, annPosition.getSourceStart(), annPosition.getSourceEnd()));
		}
		{
			SourcePosition annPosition = foo.getAnnotations().get(1).getPosition();
			assertEquals("@InnerAnnot(value=\"machin\")", contentAtPosition(classContent, annPosition.getSourceStart(), annPosition.getSourceEnd()));
		}
	}

	@Test
	public void testPositionAnnotation() throws Exception {
		final Factory build = build(new File("src/test/java/spoon/test/position/testclasses/"));
		final CtType<FooAnnotation> foo = build.Type().get(FooAnnotation.class);
		String classContent = getClassContent(foo);

		BodyHolderSourcePosition position = (BodyHolderSourcePosition) foo.getPosition();

		assertEquals(9, position.getLine());
		assertEquals(11, position.getEndLine());

		assertEquals(163, position.getSourceStart());
		assertEquals(279, position.getSourceEnd());
		assertEquals("@Target(value={})\n"
				+ "@Retention(RetentionPolicy.RUNTIME)  \n"
				+ "public abstract @interface FooAnnotation {\n"
				+ "\tString value();\n"
				+ "}", contentAtPosition(classContent, position));

		assertEquals("{\n"
				+ "\tString value();\n"
				+ "}", contentAtPosition(classContent, position.getBodyStart(), position.getBodyEnd()));

		assertEquals("FooAnnotation", contentAtPosition(classContent, position.getNameStart(), position.getNameEnd()));
		assertEquals("public abstract", contentAtPosition(classContent, position.getModifierSourceStart(), position.getModifierSourceEnd()));
		
		CtMethod<?> method1 = foo.getMethodsByName("value").get(0);
		BodyHolderSourcePosition position1 = (BodyHolderSourcePosition) method1.getPosition();

		assertEquals(10, position1.getLine());
		assertEquals(10, position1.getEndLine());

		assertEquals(263, position1.getSourceStart());
		assertEquals(277, position1.getSourceEnd());

		assertEquals("String value();", contentAtPosition(classContent, position1));
		assertEquals("value", contentAtPosition(classContent, position1.getNameStart(), position1.getNameEnd()));
		assertEquals("", contentAtPosition(classContent, position1.getModifierSourceStart(), position1.getModifierSourceEnd()));
		//contract: body of abstract method is empty
		assertEquals("", contentAtPosition(classContent, position1.getBodyStart(), position1.getBodyEnd()));
	}

	@Test
	public void testPositionField() throws Exception {
		final Factory build = build(FooField.class);
		final CtType<FooField> foo = build.Type().get(FooField.class);
		String classContent = getClassContent(foo);

		DeclarationSourcePosition position1 = (DeclarationSourcePosition) foo.getField("field1").getPosition();

		assertEquals(5, position1.getLine());
		assertEquals(5, position1.getEndLine());

		assertEquals(68, position1.getSourceStart());
		assertEquals(95, position1.getSourceEnd());

		assertEquals("public final int field1 = 0;", contentAtPosition(classContent, position1));
		assertEquals("field1", contentAtPosition(classContent, position1.getNameStart(), position1.getNameEnd()));
		assertEquals("public final", contentAtPosition(classContent, position1.getModifierSourceStart(), position1.getModifierSourceEnd()));

		DeclarationSourcePosition position2 = (DeclarationSourcePosition) foo.getField("field2").getPosition();

		assertEquals(7, position2.getLine());
		assertEquals(8, position2.getEndLine());

		assertEquals(99, position2.getSourceStart());
		assertEquals(116, position2.getSourceEnd());

		assertEquals("int field2 =\n"
				+ "\t\t\t0;", contentAtPosition(classContent, position2));
		assertEquals("field2", contentAtPosition(classContent, position2.getNameStart(), position2.getNameEnd()));
		assertEquals("", contentAtPosition(classContent, position2.getModifierSourceStart(), position2.getModifierSourceEnd()));

		CtAssignment m = foo.getMethod("m").getBody().getStatement(0);
		CtFieldAccess assigned = (CtFieldAccess) m.getAssigned();
		SourcePosition position3 = assigned.getPosition();
		assertEquals(13, position3.getLine());
		assertEquals(13, position3.getEndLine());

		assertEquals(168, position3.getSourceStart());
		assertEquals(184, position3.getSourceEnd());

		assertEquals("FooField.f.field2", contentAtPosition(classContent, position3));

		CtFieldAccess target = (CtFieldAccess) assigned.getTarget();
		SourcePosition position4 = target.getPosition();
		assertEquals(13, position4.getLine());
		assertEquals(13, position4.getEndLine());

		assertEquals(168, position4.getSourceStart());
		assertEquals(177, position4.getSourceEnd());

		assertEquals("FooField.f", contentAtPosition(classContent, position4));

		CtExpression typeAccess = target.getTarget();
		SourcePosition position5 = typeAccess.getPosition();
		assertEquals(13, position5.getLine());
		assertEquals(13, position5.getEndLine());

		assertEquals(168, position5.getSourceStart());
		assertEquals(175, position5.getSourceEnd());

		assertEquals("FooField", contentAtPosition(classContent, position5));
	}

	@Test
	public void testPositionGeneric() throws Exception {
		final Factory build = build(FooGeneric.class);
		final CtClass<FooGeneric> foo = build.Class().get(FooGeneric.class);
		String classContent = getClassContent(foo);

		BodyHolderSourcePosition position = (BodyHolderSourcePosition) foo.getPosition();

		assertEquals(3, position.getLine());
		assertEquals(31, position.getEndLine());

		assertEquals(42, position.getSourceStart());
		assertEquals(411, position.getSourceEnd());

		assertEquals("FooGeneric", contentAtPosition(classContent, position.getNameStart(), position.getNameEnd()));
		assertEquals("public", contentAtPosition(classContent, position.getModifierSourceStart(), position.getModifierSourceEnd()));


		DeclarationSourcePosition position1 = (DeclarationSourcePosition) foo.getField("variable").getPosition();

		assertEquals(5, position1.getLine());
		assertEquals(5, position1.getEndLine());

		assertEquals(88, position1.getSourceStart());
		assertEquals(118, position1.getSourceEnd());

		assertEquals("public final T variable = null;", contentAtPosition(classContent, position1));
		assertEquals("variable", contentAtPosition(classContent, position1.getNameStart(), position1.getNameEnd()));
		assertEquals("public final", contentAtPosition(classContent, position1.getModifierSourceStart(), position1.getModifierSourceEnd()));

		CtMethod<?> method1 = foo.getMethodsByName("m").get(0);
		BodyHolderSourcePosition position2 = (BodyHolderSourcePosition) method1
				.getPosition();

		assertEquals("public @Deprecated static <S> S m(int parm1) {\n"
				+ "\t\treturn null;\n"
				+ "\t}", contentAtPosition(classContent, position2));
		assertEquals("m", contentAtPosition(classContent, position2.getNameStart(), position2.getNameEnd()));

		// /!\ the annotations can be between two modifiers
		assertEquals("public @Deprecated static", contentAtPosition(classContent, position2.getModifierSourceStart(), position2.getModifierSourceEnd()));
	}

	@Test
	public void testPositionMethod() throws Exception {
		final Factory build = build(FooMethod.class);
		final CtClass<FooMethod> foo = build.Class().get(FooMethod.class);
		String classContent = getClassContent(foo);

		CtMethod<?> method1 = foo.getMethodsByName("m").get(0);
		BodyHolderSourcePosition position1 = (BodyHolderSourcePosition) method1.getPosition();

		assertEquals(5, position1.getLine());
		assertEquals(7, position1.getEndLine());

		assertEquals(69, position1.getSourceStart());
		assertEquals(114, position1.getSourceEnd());

		assertEquals("public static void m(int parm1) {\n"
				+ "\t\treturn;\n"
				+ "\t}", contentAtPosition(classContent, position1));
		assertEquals("m", contentAtPosition(classContent, position1.getNameStart(), position1.getNameEnd()));
		assertEquals("public static", contentAtPosition(classContent, position1.getModifierSourceStart(), position1.getModifierSourceEnd()));
		//contract: body contains starting and ending brackets {}
		assertEquals("{\n"
				+ "\t\treturn;\n"
				+ "\t}", contentAtPosition(classContent, position1.getBodyStart(), position1.getBodyEnd()));

		DeclarationSourcePosition positionParam1 = (DeclarationSourcePosition) method1.getParameters().get(0).getPosition();

		assertEquals(5, positionParam1.getLine());
		assertEquals(5, positionParam1.getEndLine());

		assertEquals(90, positionParam1.getSourceStart());
		assertEquals(98, positionParam1.getSourceEnd());

		assertEquals("int parm1", contentAtPosition(classContent, positionParam1));
		assertEquals("parm1", contentAtPosition(classContent, positionParam1.getNameStart(), positionParam1.getNameEnd()));
		assertEquals("", contentAtPosition(classContent, positionParam1.getModifierSourceStart(), positionParam1.getModifierSourceEnd()));

		CtMethod method2 = foo.getMethodsByName("mWithDoc").get(0);
		BodyHolderSourcePosition position2 = (BodyHolderSourcePosition) method2.getPosition();

		assertEquals(13, position2.getLine());
		assertEquals(15, position2.getEndLine());

		assertEquals("/**\n"
				+ "\t * Mathod with javadoc\n"
				+ "\t * @param parm1 the parameter\n"
				+ "\t */\n"
				+ "\tint mWithDoc(int parm1) {\n"
				+ "\t\treturn parm1;\n"
				+ "\t}", contentAtPosition(classContent, position2));
		assertEquals("mWithDoc", contentAtPosition(classContent, position2.getNameStart(), position2.getNameEnd()));
		assertEquals("", contentAtPosition(classContent, position2.getModifierSourceStart(), position2.getModifierSourceEnd()));

		CtConstructor<FooMethod> constructor = foo.getConstructor(build.Type().integerPrimitiveType());
		SourcePosition position3 = constructor.getPosition();
		contentAtPosition(classContent, position3);

		CtMethod mWithLine = foo.getMethod("mWithLine", build.Type().integerPrimitiveType());
		SourcePosition position4 = mWithLine.getPosition();
		contentAtPosition(classContent, position4);
	}

	@Test
	public void testPositionAbstractMethod() throws Exception {
		final Factory build = build(FooAbstractMethod.class);
		final CtClass<FooMethod> foo = build.Class().get(FooAbstractMethod.class);
		String classContent = getClassContent(foo);

		CtMethod<?> method1 = foo.getMethodsByName("m").get(0);
		BodyHolderSourcePosition position1 = (BodyHolderSourcePosition) method1.getPosition();

		assertEquals(5, position1.getLine());
		assertEquals(5, position1.getEndLine());

		assertEquals(86, position1.getSourceStart());
		assertEquals(125, position1.getSourceEnd());

		assertEquals("public abstract void m(final int parm1);", contentAtPosition(classContent, position1));
		assertEquals("m", contentAtPosition(classContent, position1.getNameStart(), position1.getNameEnd()));
		assertEquals("public abstract", contentAtPosition(classContent, position1.getModifierSourceStart(), position1.getModifierSourceEnd()));
		//contract: body of abstract method is empty
		assertEquals("", contentAtPosition(classContent, position1.getBodyStart(), position1.getBodyEnd()));

		DeclarationSourcePosition positionParam1 = (DeclarationSourcePosition) method1.getParameters().get(0).getPosition();

		assertEquals(5, positionParam1.getLine());
		assertEquals(5, positionParam1.getEndLine());

		assertEquals(109, positionParam1.getSourceStart());
		assertEquals(123, positionParam1.getSourceEnd());

		assertEquals("final int parm1", contentAtPosition(classContent, positionParam1));
		assertEquals("parm1", contentAtPosition(classContent, positionParam1.getNameStart(), positionParam1.getNameEnd()));
		assertEquals("final", contentAtPosition(classContent, positionParam1.getModifierSourceStart(), positionParam1.getModifierSourceEnd()));
	}

	@Test
	public void testPositionStatement() throws Exception {
		final Factory build = build(FooStatement.class);
		final CtType<FooStatement> foo = build.Type().get(FooStatement.class);
		String classContent = getClassContent(foo);

		CtMethod<?> method1 = foo.getMethodsByName("m").get(0);
		CtBlock<?> body = method1.getBody();
		SourcePosition positionBody = body.getPosition();

		assertEquals(7, positionBody.getLine());
		assertEquals(23, positionBody.getEndLine());

		assertEquals("{\n"
				+ "\t\tint field2 = m2(parm1);\n"
				+ "\t\tthis.field = m2(parm1);\n"
				+ "\t\tif(parm1 > 2 && true) {\n"
				+ "\t\t\tswitch (parm1) {\n"
				+ "\t\t\tcase 1:\n"
				+ "\t\t\t\treturn;\n"
				+ "\t\t\tdefault:\n"
				+ "\t\t\t\tparm1++;\n"
				+ "\t\t\t}\n"
				+ "\t\t\tint count = 0;\n"
				+ "\t\t\tfor (int i =0; i< parm1; i++) {\n"
				+ "\t\t\t\tcount ++;\n"
				+ "\t\t\t}\n"
				+ "\t\t}\n"
				+ "\t\treturn;\n"
				+ "\t}", contentAtPosition(classContent, positionBody));

		SourcePosition positionLocalVariable = body.getStatement(0).getPosition();

		assertEquals(8, positionLocalVariable.getLine());
		assertEquals(8, positionLocalVariable.getEndLine());

		assertEquals("int field2 = m2(parm1);", contentAtPosition(classContent, positionLocalVariable));

		SourcePosition positionFieldWrite = body.getStatement(1).getPosition();

		assertEquals(9, positionFieldWrite.getLine());
		assertEquals(9, positionFieldWrite.getEndLine());

		assertEquals("this.field = m2(parm1);", contentAtPosition(classContent, positionFieldWrite));

		CtIf ctIf = body.getStatement(2);
		SourcePosition positionIf = ctIf.getPosition();

		assertEquals(10, positionIf.getLine());
		assertEquals(21, positionIf.getEndLine());

		assertEquals("if(parm1 > 2 && true) {\n"
				+ "\t\t\tswitch (parm1) {\n"
				+ "\t\t\tcase 1:\n"
				+ "\t\t\t\treturn;\n"
				+ "\t\t\tdefault:\n"
				+ "\t\t\t\tparm1++;\n"
				+ "\t\t\t}\n"
				+ "\t\t\tint count = 0;\n"
				+ "\t\t\tfor (int i =0; i< parm1; i++) {\n"
				+ "\t\t\t\tcount ++;\n"
				+ "\t\t\t}\n"
				+ "\t\t}", contentAtPosition(classContent, positionIf));

		SourcePosition positionSwitch = ((CtBlock)ctIf.getThenStatement()).getStatement(0).getPosition();

		assertEquals(11, positionSwitch.getLine());
		assertEquals(16, positionSwitch.getEndLine());

		assertEquals("switch (parm1) {\n"
				+ "\t\t\tcase 1:\n"
				+ "\t\t\t\treturn;\n"
				+ "\t\t\tdefault:\n"
				+ "\t\t\t\tparm1++;\n"
				+ "\t\t\t}", contentAtPosition(classContent, positionSwitch));

		positionLocalVariable = ((CtBlock)ctIf.getThenStatement()).getStatement(1).getPosition();

		assertEquals(17, positionLocalVariable.getLine());
		assertEquals(17, positionLocalVariable.getEndLine());

		assertEquals("int count = 0;", contentAtPosition(classContent, positionLocalVariable));

		SourcePosition positionFor = ((CtBlock)ctIf.getThenStatement()).getStatement(2).getPosition();

		assertEquals(18, positionFor.getLine());
		assertEquals(20, positionFor.getEndLine());

		assertEquals("for (int i =0; i< parm1; i++) {\n"
				+ "\t\t\t\tcount ++;\n"
				+ "\t\t\t}", contentAtPosition(classContent, positionFor));

		SourcePosition positionReturn = method1.getBody().getStatement(3).getPosition();

		assertEquals(22, positionReturn.getLine());
		assertEquals(22, positionReturn.getEndLine());

		assertEquals("return;", contentAtPosition(classContent, positionReturn));
	}


	private String getClassContent(CtType type) {
		File file = type.getPosition().getFile();
		String content = "";
		Charset charset = Charset.forName("UTF-8");
		try (BufferedReader reader = Files.newBufferedReader(
				Paths.get(file.getPath()), charset)) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				content+=line + "\n";
			}
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}

		return content;
	}

	private String contentAtPosition(String content, int start, int end) {
		return content.substring(start,  end + 1);
	}

	private String contentAtPosition(String content, SourcePosition position) {
		return content.substring(position.getSourceStart(),  position.getSourceEnd() + 1);
	}

	@Test
	public void testSourcePosition() throws Exception {
		SourcePosition s = new spoon.Launcher().getFactory().Core().createClass().getPosition();
		assertEquals(-1, s.getSourceStart());
		assertEquals(-1, s.getSourceEnd());
		assertEquals(-1, s.getColumn());
		assertEquals(-1, s.getLine());
		assertEquals("(unknown file)", s.toString());
		assertTrue(s.hashCode() > 0); // no NPE
	}

	@Test
	public void defaultConstructorPositionTest() throws Exception {
		CtClass<Foo> aClass = (CtClass<Foo>) buildClass(Foo.class);
		CtConstructor<Foo> defaultConstructor = aClass.getConstructor();
		assertEquals(SourcePosition.NOPOSITION, defaultConstructor.getPosition());
		CtStatement implicitSuperCall = defaultConstructor.getBody().getStatement(0);
		assertTrue(implicitSuperCall.isImplicit());
		assertEquals(SourcePosition.NOPOSITION, implicitSuperCall.getPosition());
	}

	@Test
	public void getPositionOfImplicitBlock() {
		// contract: position of implicit block in if (cond) [implicit block] else [implicit block] should be the source position of implicit block content.
		Launcher launcher = new Launcher();
		launcher.addInputResource("./src/test/java/spoon/test/position/testclasses/ImplicitBlock.java");
		launcher.buildModel();

		CtIf ifElement = launcher.getModel().getElements(new TypeFilter<CtIf>(CtIf.class)).get(0);
		CtStatement thenStatement = ifElement.getThenStatement();

		assertTrue(thenStatement instanceof CtBlock);

		CtBlock thenBlock = (CtBlock) thenStatement;
		SourcePosition positionThen = thenBlock.getPosition();
		CtStatement returnStatement = thenBlock.getStatement(0);
		assertEquals(returnStatement.getPosition(), positionThen);
		assertEquals("ImplicitBlock.java", positionThen.getFile().getName());
		assertEquals(7, positionThen.getLine());

		CtStatement elseStatement = ifElement.getElseStatement();

		assertTrue(elseStatement instanceof CtBlock);

		CtBlock elseBlock = (CtBlock) elseStatement;
		SourcePosition positionElse = elseBlock.getPosition();
		CtStatement otherReturnStatement = elseBlock.getStatement(0);
		assertEquals(otherReturnStatement.getPosition(), positionElse);
		assertEquals("ImplicitBlock.java", positionThen.getFile().getName());
		assertEquals(8, positionElse.getLine());

		assertNotEquals(returnStatement, otherReturnStatement);


	}
}
