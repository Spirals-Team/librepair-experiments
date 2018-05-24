package spoon.test.ctType;


public class CtTypeTest {
    @org.junit.Test
    public void testHasMethodInDirectMethod() {
        spoon.reflect.declaration.CtClass<?> clazz = spoon.testing.utils.ModelUtils.createFactory().Code().createCodeSnippetStatement("class X { public void foo() {} }").compile();
        org.junit.Assert.assertTrue(clazz.hasMethod(clazz.getMethods().iterator().next()));
    }

    @org.junit.Test
    public void testHasMethodNotHasMethod() {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        spoon.reflect.declaration.CtClass<?> clazz = factory.Code().createCodeSnippetStatement("class X { public void foo() {} }").compile();
        spoon.reflect.declaration.CtClass<?> clazz2 = factory.Code().createCodeSnippetStatement("class Y { public void foo2() {} }").compile();
        org.junit.Assert.assertFalse(clazz.hasMethod(clazz2.getMethods().iterator().next()));
    }

    @org.junit.Test
    public void testHasMethodOnNull() {
        spoon.reflect.declaration.CtClass<?> clazz = spoon.testing.utils.ModelUtils.createFactory().Code().createCodeSnippetStatement("class X { public void foo() {} }").compile();
        org.junit.Assert.assertFalse(clazz.hasMethod(null));
    }

    @org.junit.Test
    public void testHasMethodInSuperClass() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/ctType/testclasses/X.java");
        launcher.run();
        final spoon.reflect.declaration.CtClass<?> xClass = launcher.getFactory().Class().get("spoon.test.ctType.testclasses.X");
        final spoon.reflect.declaration.CtClass<?> yClass = launcher.getFactory().Class().get("spoon.test.ctType.testclasses.Y");
        final spoon.reflect.declaration.CtMethod<?> superMethod = xClass.getMethods().iterator().next();
        org.junit.Assert.assertTrue(yClass.hasMethod(superMethod));
    }

    @org.junit.Test
    public void testHasMethodInDefaultMethod() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/ctType/testclasses/X.java");
        launcher.getEnvironment().setComplianceLevel(8);
        launcher.run();
        final spoon.reflect.declaration.CtClass<?> x = launcher.getFactory().Class().get("spoon.test.ctType.testclasses.W");
        final spoon.reflect.declaration.CtInterface<?> z = launcher.getFactory().Interface().get("spoon.test.ctType.testclasses.Z");
        final spoon.reflect.declaration.CtMethod<?> superMethod = z.getMethods().iterator().next();
        org.junit.Assert.assertTrue(x.hasMethod(superMethod));
    }

    @org.junit.Test
    public void testIsSubTypeOf() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<spoon.test.ctType.testclasses.X> xCtType = spoon.testing.utils.ModelUtils.buildClass(spoon.test.ctType.testclasses.X.class);
        spoon.reflect.declaration.CtType<?> yCtType = xCtType.getFactory().Type().get("spoon.test.ctType.testclasses.Y");
        org.junit.Assert.assertFalse(xCtType.isSubtypeOf(yCtType.getReference()));
        org.junit.Assert.assertTrue(yCtType.isSubtypeOf(xCtType.getReference()));
        org.junit.Assert.assertTrue(xCtType.getReference().isSubtypeOf(xCtType.getReference()));
        org.junit.Assert.assertTrue(xCtType.isSubtypeOf(xCtType.getReference()));
    }

    @org.junit.Test
    public void testIsSubTypeOfonTypeParameters() throws java.lang.Exception {
        spoon.reflect.declaration.CtType<spoon.test.ctType.testclasses.X> xCtType = spoon.testing.utils.ModelUtils.buildClass(spoon.test.ctType.testclasses.X.class);
        spoon.reflect.factory.Factory factory = xCtType.getFactory();
        spoon.reflect.declaration.CtType<?> oCtType = factory.Type().get("spoon.test.ctType.testclasses.O");
        spoon.reflect.declaration.CtType<?> pCtType = factory.Type().get("spoon.test.ctType.testclasses.P");
        spoon.reflect.reference.CtTypeReference<?> objectCtTypeRef = factory.Type().OBJECT;
        java.util.List<spoon.reflect.declaration.CtTypeParameter> oTypeParameters = oCtType.getFormalCtTypeParameters();
        org.junit.Assert.assertTrue(((oTypeParameters.size()) == 1));
        java.util.List<spoon.reflect.declaration.CtTypeParameter> pTypeParameters = pCtType.getFormalCtTypeParameters();
        org.junit.Assert.assertTrue(((pTypeParameters.size()) == 2));
        spoon.reflect.declaration.CtType<?> O_A_CtType = oTypeParameters.get(0);
        spoon.reflect.declaration.CtType<?> P_D_CtType = pTypeParameters.get(0);
        spoon.reflect.declaration.CtType<?> P_F_CtType = pTypeParameters.get(1);
        spoon.reflect.declaration.CtMethod<?> O_FooMethod = oCtType.filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "foo")).first();
        spoon.reflect.declaration.CtMethod<?> P_FooMethod = pCtType.filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "foo")).first();
        spoon.reflect.declaration.CtType<?> O_B_CtType = O_FooMethod.getType().getDeclaration();
        spoon.reflect.declaration.CtType<?> P_E_CtType = P_FooMethod.getType().getDeclaration();
        org.junit.Assert.assertTrue(O_B_CtType.isSubtypeOf(xCtType.getReference()));
        org.junit.Assert.assertTrue(O_B_CtType.isSubtypeOf(O_A_CtType.getReference()));
        org.junit.Assert.assertTrue(P_E_CtType.isSubtypeOf(xCtType.getReference()));
        org.junit.Assert.assertTrue(P_E_CtType.isSubtypeOf(P_D_CtType.getReference()));
        org.junit.Assert.assertTrue(P_E_CtType.isSubtypeOf(O_A_CtType.getReference()));
        org.junit.Assert.assertTrue(P_D_CtType.isSubtypeOf(O_A_CtType.getReference()));
        org.junit.Assert.assertTrue(P_E_CtType.isSubtypeOf(O_B_CtType.getReference()));
        org.junit.Assert.assertTrue(P_E_CtType.isSubtypeOf(objectCtTypeRef));
        org.junit.Assert.assertTrue(P_F_CtType.isSubtypeOf(objectCtTypeRef));
    }

    @org.junit.Test
    public void testIsSubTypeOfonTypeReferences() throws java.lang.Exception {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.setArgs(new java.lang.String[]{ "-c" });
        launcher.addInputResource("./src/test/java/spoon/test/ctType/testclasses/SubtypeModel.java");
        launcher.buildModel();
        spoon.reflect.factory.Factory factory = launcher.getFactory();
        spoon.reflect.declaration.CtType<?> oCtType = factory.Class().get("spoon.test.ctType.testclasses.SubtypeModel");
        spoon.reflect.declaration.CtMethod<?> O_FooMethod = oCtType.filterChildren(new spoon.reflect.visitor.filter.NamedElementFilter<>(spoon.reflect.declaration.CtMethod.class, "foo")).first();
        java.util.Map<java.lang.String, spoon.reflect.reference.CtTypeReference<?>> nameToTypeRef = new java.util.HashMap<>();
        O_FooMethod.filterChildren(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtLocalVariable.class)).forEach((spoon.reflect.code.CtLocalVariable var) -> {
            nameToTypeRef.put(var.getSimpleName(), var.getType());
        });
        int[] count = new int[1];
        O_FooMethod.filterChildren(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.code.CtAssignment.class)).forEach((spoon.reflect.code.CtAssignment ass) -> {
            for (spoon.reflect.code.CtComment comment : ass.getComments()) {
                checkIsNotSubtype(comment, nameToTypeRef);
                (count[0])++;
            }
            (count[0])++;
            checkIsSubtype(((spoon.reflect.code.CtVariableAccess) (ass.getAssigned())).getVariable().getType(), ((spoon.reflect.code.CtVariableAccess) (ass.getAssignment())).getVariable().getType(), nameToTypeRef);
        });
        org.junit.Assert.assertTrue(((count[0]) > (9 * 8)));
    }

    private void checkIsSubtype(spoon.reflect.reference.CtTypeReference superType, spoon.reflect.reference.CtTypeReference subType, java.util.Map<java.lang.String, spoon.reflect.reference.CtTypeReference<?>> nameToTypeRef) {
        java.lang.String msg = ((getTypeName(subType)) + " isSubTypeOf ") + (getTypeName(superType));
        org.junit.Assert.assertTrue(msg, subType.isSubtypeOf(superType));
    }

    private static final java.util.regex.Pattern assignment = java.util.regex.Pattern.compile("\\s*(\\w+)\\s*=\\s*(\\w+);");

    private void checkIsNotSubtype(spoon.reflect.code.CtComment comment, java.util.Map<java.lang.String, spoon.reflect.reference.CtTypeReference<?>> nameToTypeRef) {
        java.util.regex.Matcher m = spoon.test.ctType.CtTypeTest.assignment.matcher(comment.getContent());
        org.junit.Assert.assertTrue(m.matches());
        spoon.reflect.reference.CtTypeReference<?> superType = nameToTypeRef.get(m.group(1));
        spoon.reflect.reference.CtTypeReference<?> subType = nameToTypeRef.get(m.group(2));
        java.lang.String msg = ((getTypeName(subType)) + " is NOT SubTypeOf ") + (getTypeName(superType));
        org.junit.Assert.assertFalse(msg, subType.isSubtypeOf(superType));
    }

    private java.lang.String getTypeName(spoon.reflect.reference.CtTypeReference<?> ref) {
        java.lang.String name;
        spoon.reflect.reference.CtReference r = ref.getParent(spoon.reflect.reference.CtReference.class);
        if (r != null) {
            name = r.getSimpleName();
        }else {
            name = ref.getParent(spoon.reflect.declaration.CtNamedElement.class).getSimpleName();
        }
        return ((ref.toString()) + " ") + name;
    }
}

