package spoon.test.staticFieldAccess2;


public class ImplicitStaticFieldReferenceTest {
    private static final boolean expectImplicit = false;

    @org.junit.Test
    public void testImplicitStaticFieldReference() throws java.lang.Exception {
        spoon.Launcher launcher = spoon.test.staticFieldAccess2.ImplicitStaticFieldReferenceTest.checkFile(false, "ImplicitStaticFieldReference.java");
        if (spoon.test.staticFieldAccess2.ImplicitStaticFieldReferenceTest.expectImplicit) {
            spoon.reflect.declaration.CtClass<?> cls = launcher.getFactory().Class().get(spoon.test.staticFieldAccess2.ImplicitStaticFieldReference.class);
            org.junit.Assert.assertEquals("return ImplicitStaticFieldReference", cls.getMethod("reader").getBody().getStatements().get(0).toString());
            org.junit.Assert.assertEquals("ImplicitStaticFieldReference = value", cls.getMethodsByName("writer").get(0).getBody().getStatements().get(0).toString());
            org.junit.Assert.assertEquals("reader()", cls.getMethodsByName("testLocalMethodInvocations").get(0).getBody().getStatements().get(0).toString());
            org.junit.Assert.assertEquals("longWriter(7)", cls.getMethodsByName("testLocalMethodInvocations").get(0).getBody().getStatements().get(1).toString());
        }
    }

    @org.junit.Test
    public void testImplicitStaticFieldReferenceAutoImport() throws java.lang.Exception {
        spoon.Launcher launcher = spoon.test.staticFieldAccess2.ImplicitStaticFieldReferenceTest.checkFile(true, "ImplicitStaticFieldReference.java");
        if (spoon.test.staticFieldAccess2.ImplicitStaticFieldReferenceTest.expectImplicit) {
            spoon.reflect.declaration.CtClass<?> cls = launcher.getFactory().Class().get(spoon.test.staticFieldAccess2.ImplicitStaticFieldReference.class);
            org.junit.Assert.assertEquals("return ImplicitStaticFieldReference", cls.getMethod("reader").getBody().getStatements().get(0).toString());
            org.junit.Assert.assertEquals("ImplicitStaticFieldReference = value", cls.getMethodsByName("writer").get(0).getBody().getStatements().get(0).toString());
            org.junit.Assert.assertEquals("reader()", cls.getMethodsByName("testLocalMethodInvocations").get(0).getBody().getStatements().get(0).toString());
            org.junit.Assert.assertEquals("longWriter(7)", cls.getMethodsByName("testLocalMethodInvocations").get(0).getBody().getStatements().get(1).toString());
        }
    }

    @org.junit.Test
    public void testImplicitFieldReference() throws java.lang.Exception {
        spoon.Launcher launcher = spoon.test.staticFieldAccess2.ImplicitStaticFieldReferenceTest.checkFile(false, "ImplicitFieldReference.java");
        if (spoon.test.staticFieldAccess2.ImplicitStaticFieldReferenceTest.expectImplicit) {
            spoon.reflect.declaration.CtClass<?> cls = launcher.getFactory().Class().get(spoon.test.staticFieldAccess2.ImplicitFieldReference.class);
            org.junit.Assert.assertEquals("return memberField", cls.getMethod("getMemberField").getBody().getStatements().get(0).toString());
            org.junit.Assert.assertEquals("memberField = p_memberField", cls.getMethodsByName("setMemberField").get(0).getBody().getStatements().get(0).toString());
            org.junit.Assert.assertEquals("getMemberField()", cls.getMethodsByName("testLocalMethodInvocations").get(0).getBody().getStatements().get(0).toString());
        }
    }

    @org.junit.Test
    public void testImplicitFieldReferenceAutoImport() throws java.lang.Exception {
        spoon.Launcher launcher = spoon.test.staticFieldAccess2.ImplicitStaticFieldReferenceTest.checkFile(true, "ImplicitFieldReference.java");
        if (spoon.test.staticFieldAccess2.ImplicitStaticFieldReferenceTest.expectImplicit) {
            spoon.reflect.declaration.CtClass<?> cls = launcher.getFactory().Class().get(spoon.test.staticFieldAccess2.ImplicitFieldReference.class);
            org.junit.Assert.assertEquals("return memberField", cls.getMethod("getMemberField").getBody().getStatements().get(0).toString());
            org.junit.Assert.assertEquals("memberField = p_memberField", cls.getMethodsByName("setMemberField").get(0).getBody().getStatements().get(0).toString());
            org.junit.Assert.assertEquals("getMemberField()", cls.getMethodsByName("testLocalMethodInvocations").get(0).getBody().getStatements().get(0).toString());
        }
    }

    @org.junit.Test
    public void testAmbiguousImplicitFieldReference() throws java.lang.Exception {
        spoon.Launcher launcher = spoon.test.staticFieldAccess2.ImplicitStaticFieldReferenceTest.checkFile(false, "AmbiguousImplicitFieldReference.java");
        if (spoon.test.staticFieldAccess2.ImplicitStaticFieldReferenceTest.expectImplicit) {
            spoon.reflect.declaration.CtClass<?> cls = launcher.getFactory().Class().get(spoon.test.staticFieldAccess2.AmbiguousImplicitFieldReference.class);
            org.junit.Assert.assertEquals("return memberField", cls.getMethod("getMemberField").getBody().getStatements().get(0).toString());
            org.junit.Assert.assertEquals("memberField = p_memberField", cls.getMethodsByName("setMemberField").get(0).getBody().getStatements().get(0).toString());
            org.junit.Assert.assertEquals("getMemberField()", cls.getMethodsByName("testLocalMethodInvocations").get(0).getBody().getStatements().get(0).toString());
        }
    }

    @org.junit.Test
    public void testAmbiguousImplicitFieldReferenceAutoImport() throws java.lang.Exception {
        spoon.Launcher launcher = spoon.test.staticFieldAccess2.ImplicitStaticFieldReferenceTest.checkFile(true, "AmbiguousImplicitFieldReference.java");
        if (spoon.test.staticFieldAccess2.ImplicitStaticFieldReferenceTest.expectImplicit) {
            spoon.reflect.declaration.CtClass<?> cls = launcher.getFactory().Class().get(spoon.test.staticFieldAccess2.AmbiguousImplicitFieldReference.class);
            org.junit.Assert.assertEquals("return memberField", cls.getMethod("getMemberField").getBody().getStatements().get(0).toString());
            org.junit.Assert.assertEquals("memberField = p_memberField", cls.getMethodsByName("setMemberField").get(0).getBody().getStatements().get(0).toString());
            org.junit.Assert.assertEquals("getMemberField()", cls.getMethodsByName("testLocalMethodInvocations").get(0).getBody().getStatements().get(0).toString());
        }
    }

    @org.junit.Test
    public void testImplicitStaticClassAccess() throws java.lang.Exception {
        spoon.Launcher launcher = spoon.test.staticFieldAccess2.ImplicitStaticFieldReferenceTest.checkFile(false, "ImplicitStaticClassAccess.java");
    }

    @org.junit.Test
    public void testImplicitStaticClassAccessAutoImport() throws java.lang.Exception {
        spoon.Launcher launcher = spoon.test.staticFieldAccess2.ImplicitStaticFieldReferenceTest.checkFile(true, "ImplicitStaticClassAccess.java");
    }

    @org.junit.Test
    public void testGenericsWithAmbiguousStaticField() throws java.lang.Exception {
        spoon.Launcher launcher = spoon.test.staticFieldAccess2.ImplicitStaticFieldReferenceTest.checkFile(false, "GenericsWithAmbiguousStaticField.java");
    }

    @org.junit.Test
    public void testGenericsWithAmbiguousStaticFieldAutoImport() throws java.lang.Exception {
        spoon.Launcher launcher = spoon.test.staticFieldAccess2.ImplicitStaticFieldReferenceTest.checkFile(true, "GenericsWithAmbiguousStaticField.java");
    }

    @org.junit.Test
    public void testChildOfGenericsWithAmbiguousStaticField() throws java.lang.Exception {
        spoon.Launcher launcher = spoon.test.staticFieldAccess2.ImplicitStaticFieldReferenceTest.checkFile(false, "ChildOfGenericsWithAmbiguousStaticField.java");
    }

    @org.junit.Test
    public void testChildOfGenericsWithAmbiguousStaticFieldAutoImport() throws java.lang.Exception {
        spoon.Launcher launcher = spoon.test.staticFieldAccess2.ImplicitStaticFieldReferenceTest.checkFile(true, "ChildOfGenericsWithAmbiguousStaticField.java");
        if (spoon.test.staticFieldAccess2.ImplicitStaticFieldReferenceTest.expectImplicit) {
            spoon.reflect.declaration.CtClass<?> cls = launcher.getFactory().Class().get(spoon.test.staticFieldAccess2.ChildOfGenericsWithAmbiguousStaticField.class);
            org.junit.Assert.assertTrue(((cls.toString().indexOf("spoon.test.staticFieldAccess2.GenericsWithAmbiguousStaticField.<V, C>genericMethod()")) >= 0));
            org.junit.Assert.assertEquals("genericMethod()", cls.getMethod("m1").getBody().getStatements().get(1).toString());
        }
    }

    @org.junit.Test
    public void testGenericsWithAmbiguousMemberField() throws java.lang.Exception {
        spoon.Launcher launcher = spoon.test.staticFieldAccess2.ImplicitStaticFieldReferenceTest.checkFile(false, "GenericsWithAmbiguousMemberField.java");
    }

    @org.junit.Test
    public void testGenericsWithAmbiguousMemberFieldAutoImport() throws java.lang.Exception {
        spoon.Launcher launcher = spoon.test.staticFieldAccess2.ImplicitStaticFieldReferenceTest.checkFile(true, "GenericsWithAmbiguousMemberField.java");
    }

    @org.junit.Test
    public void testAnnotationInChildWithConstants() throws java.lang.Exception {
        spoon.Launcher launcher = spoon.test.staticFieldAccess2.ImplicitStaticFieldReferenceTest.checkFile(false, "ChildOfConstants.java", "Constants.java");
    }

    @org.junit.Test
    public void testAnnotationInChildWithConstantsAutoImport() throws java.lang.Exception {
        spoon.Launcher launcher = spoon.test.staticFieldAccess2.ImplicitStaticFieldReferenceTest.checkFile(true, "ChildOfConstants.java", "Constants.java");
    }

    private static spoon.Launcher checkFile(boolean autoImports, java.lang.String... fileName) {
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setAutoImports(autoImports);
        java.lang.String pckg = "spoon/test/staticFieldAccess2/";
        for (java.lang.String fn : fileName) {
            launcher.addInputResource((("src/test/java/" + pckg) + fn));
        }
        java.lang.String targetDir = "./target/spooned" + (autoImports ? "-autoImports" : "");
        launcher.setSourceOutputDirectory(targetDir);
        launcher.buildModel();
        launcher.prettyprint();
        for (java.lang.String fn : fileName) {
            spoon.testing.utils.ModelUtils.canBeBuilt((((targetDir + "/") + pckg) + fn), 8);
        }
        return launcher;
    }
}

