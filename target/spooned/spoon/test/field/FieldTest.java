package spoon.test.field;


import java.util.List;
import spoon.Launcher;
import spoon.reflect.code.CtFieldRead;
import spoon.reflect.code.CtReturn;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.support.reflect.eval.VisitorPartialEvaluator;
import spoon.test.field.testclasses.A;
import spoon.test.field.testclasses.BaseClass;


public class FieldTest {
    @org.junit.Test
    public void testAddAFieldInAClassAtAPositionGiven() throws java.lang.Exception {
        final spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        final CtClass<java.lang.Object> fieldClass = factory.Class().create("FieldClass");
        final java.util.HashSet<spoon.reflect.declaration.ModifierKind> modifiers = new java.util.HashSet<spoon.reflect.declaration.ModifierKind>();
        modifiers.add(spoon.reflect.declaration.ModifierKind.STATIC);
        final spoon.reflect.declaration.CtField<java.lang.Integer> first = createField(factory, modifiers, "FIELD");
        fieldClass.addField(first);
        final spoon.reflect.declaration.CtField<java.lang.Integer> second = createField(factory, modifiers, "FIELD_2");
        second.setDefaultExpression(factory.Code().createCodeSnippetExpression(((first.getSimpleName()) + " + 1")));
        fieldClass.addField(1, second);
        final spoon.reflect.declaration.CtField<java.lang.Integer> third = createField(factory, modifiers, "FIELD_3");
        third.setDefaultExpression(factory.Code().createCodeSnippetExpression(((first.getSimpleName()) + " + 1")));
        fieldClass.addField(1, third);
        org.junit.Assert.assertEquals(3, fieldClass.getFields().size());
        org.junit.Assert.assertEquals(first, fieldClass.getFields().get(0));
        org.junit.Assert.assertEquals(third, fieldClass.getFields().get(1));
        org.junit.Assert.assertEquals(second, fieldClass.getFields().get(2));
    }

    @org.junit.Test
    public void testgetDeclaredFields() throws java.lang.Exception {
        final CtClass<spoon.test.field.testclasses.AddFieldAtTop> aClass = ((CtClass<spoon.test.field.testclasses.AddFieldAtTop>) (spoon.testing.utils.ModelUtils.buildClass(spoon.test.field.testclasses.AddFieldAtTop.class)));
        org.junit.Assert.assertEquals(1, aClass.getReference().getDeclaredFields().size());
        spoon.reflect.reference.CtTypeReference<?> fileClass = aClass.getFactory().Type().get(java.io.File.class).getReference();
        org.junit.Assert.assertEquals(13, fileClass.getDeclaredFields().size());
        org.junit.Assert.assertEquals("pathSeparator", fileClass.getDeclaredField("pathSeparator").getSimpleName());
        org.junit.Assert.assertEquals("pathSeparator", fileClass.getDeclaredOrInheritedField("pathSeparator").getSimpleName());
        org.junit.Assert.assertEquals("pathSeparator", fileClass.getDeclaredField("pathSeparator").getFieldDeclaration().getSimpleName());
    }

    @org.junit.Test
    public void testAddFieldsAtTop() throws java.lang.Exception {
        final CtClass<spoon.test.field.testclasses.AddFieldAtTop> aClass = ((CtClass<spoon.test.field.testclasses.AddFieldAtTop>) (spoon.testing.utils.ModelUtils.buildClass(spoon.test.field.testclasses.AddFieldAtTop.class)));
        org.junit.Assert.assertEquals(1, aClass.getFields().size());
        final spoon.reflect.declaration.CtField<java.lang.String> generated = aClass.getFactory().Field().create(null, new java.util.HashSet<>(), aClass.getFactory().Type().STRING, "generated");
        aClass.addFieldAtTop(generated);
        final spoon.reflect.declaration.CtField<java.lang.String> generated2 = aClass.getFactory().Field().create(null, new java.util.HashSet<>(), aClass.getFactory().Type().STRING, "generated2");
        aClass.addFieldAtTop(generated2);
        org.junit.Assert.assertEquals(3, aClass.getFields().size());
        org.junit.Assert.assertEquals(generated2, aClass.getTypeMembers().get(0));
        org.junit.Assert.assertEquals(generated, aClass.getTypeMembers().get(1));
        org.junit.Assert.assertEquals(aClass.getAnonymousExecutables().get(0), aClass.getTypeMembers().get(3));
    }

    @org.junit.Test
    public void testFieldImplicitTarget() throws java.lang.Exception {
        final CtClass<spoon.test.field.testclasses.AddFieldAtTop> aClass = ((CtClass<spoon.test.field.testclasses.AddFieldAtTop>) (spoon.testing.utils.ModelUtils.buildClass(spoon.test.field.testclasses.AddFieldAtTop.class)));
        List<CtFieldRead> fieldReads = aClass.getElements(new spoon.reflect.visitor.filter.TypeFilter<>(CtFieldRead.class));
        org.junit.Assert.assertEquals(1, fieldReads.size());
        org.junit.Assert.assertEquals("i", fieldReads.get(0).toString());
        fieldReads.get(0).getTarget().setImplicit(false);
        org.junit.Assert.assertEquals(false, fieldReads.get(0).getTarget().isImplicit());
        org.junit.Assert.assertEquals("this.i", fieldReads.get(0).toString());
    }

    private spoon.reflect.declaration.CtField<java.lang.Integer> createField(spoon.reflect.factory.Factory factory, java.util.HashSet<spoon.reflect.declaration.ModifierKind> modifiers, java.lang.String name) {
        final spoon.reflect.declaration.CtField<java.lang.Integer> first = factory.Core().createField();
        first.setModifiers(modifiers);
        first.setType(factory.Type().INTEGER_PRIMITIVE);
        first.setSimpleName(name);
        return first;
    }

    @org.junit.Test
    public void testGetDefaultExpression() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/java/spoon/test/field/testclasses/A.java");
        spoon.addInputResource("./src/test/java/spoon/test/field/testclasses/BaseClass.java");
        spoon.buildModel();
        final CtClass<A> aClass = spoon.getFactory().Class().get(A.class);
        CtClass<A.ClassB> bClass = aClass.getFactory().Class().get(A.ClassB.class);
        List<CtMethod<?>> methods = bClass.getMethodsByName("getKey");
        org.junit.Assert.assertEquals(1, methods.size());
        CtReturn<?> returnExpression = methods.get(0).getBody().getStatement(0);
        CtFieldRead fieldRead = ((CtFieldRead) (returnExpression.getReturnedExpression()));
        org.junit.Assert.assertEquals("spoon.test.field.testclasses.BaseClass.PREFIX", fieldRead.toString());
        spoon.reflect.declaration.CtField<?> field = fieldRead.getVariable().getDeclaration();
        CtClass<BaseClass> baseClass = aClass.getFactory().Class().get(BaseClass.class);
        spoon.reflect.declaration.CtField<?> expectedField = baseClass.getField("PREFIX");
        org.junit.Assert.assertEquals(expectedField, field);
        VisitorPartialEvaluator visitorPartial = new VisitorPartialEvaluator();
        java.lang.Object retour = visitorPartial.evaluate(methods.get(0));
        org.junit.Assert.assertTrue((retour != null));
    }
}

