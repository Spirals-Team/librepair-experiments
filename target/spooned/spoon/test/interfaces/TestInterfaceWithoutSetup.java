package spoon.test.interfaces;


import spoon.Launcher;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.ModifierKind;
import spoon.support.reflect.CtExtendedModifier;


public class TestInterfaceWithoutSetup {
    @org.junit.Test
    public void testModifierFromInterfaceFieldAndMethod() {
        Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/resources/spoon/test/itf/DumbItf.java");
        spoon.getEnvironment().setNoClasspath(false);
        spoon.buildModel();
        CtType dumbType = spoon.getFactory().Type().get("toto.DumbItf");
        org.junit.Assert.assertEquals(2, dumbType.getFields().size());
        CtField fieldImplicit = dumbType.getField("CONSTANT_INT");
        java.util.Set<CtExtendedModifier> extendedModifierSet = fieldImplicit.getExtendedModifiers();
        org.junit.Assert.assertEquals(3, extendedModifierSet.size());
        org.junit.Assert.assertTrue(extendedModifierSet.contains(new CtExtendedModifier(ModifierKind.FINAL, true)));
        org.junit.Assert.assertTrue(extendedModifierSet.contains(new CtExtendedModifier(ModifierKind.PUBLIC, true)));
        org.junit.Assert.assertTrue(extendedModifierSet.contains(new CtExtendedModifier(ModifierKind.STATIC, true)));
        for (CtExtendedModifier extendedModifier : extendedModifierSet) {
            org.junit.Assert.assertTrue(extendedModifier.isImplicit());
        }
        org.junit.Assert.assertEquals(ModifierKind.PUBLIC, fieldImplicit.getVisibility());
        org.junit.Assert.assertTrue(fieldImplicit.hasModifier(ModifierKind.STATIC));
        org.junit.Assert.assertTrue(fieldImplicit.hasModifier(ModifierKind.PUBLIC));
        org.junit.Assert.assertTrue(fieldImplicit.hasModifier(ModifierKind.FINAL));
        CtField fieldExplicit = dumbType.getField("ANOTHER_INT");
        extendedModifierSet = fieldExplicit.getExtendedModifiers();
        org.junit.Assert.assertEquals(3, extendedModifierSet.size());
        org.junit.Assert.assertTrue(extendedModifierSet.contains(new CtExtendedModifier(ModifierKind.FINAL, true)));
        org.junit.Assert.assertTrue(extendedModifierSet.contains(new CtExtendedModifier(ModifierKind.PUBLIC, false)));
        org.junit.Assert.assertTrue(extendedModifierSet.contains(new CtExtendedModifier(ModifierKind.STATIC, false)));
        int counter = 0;
        for (CtExtendedModifier extendedModifier : extendedModifierSet) {
            if ((extendedModifier.getKind()) == (ModifierKind.FINAL)) {
                org.junit.Assert.assertTrue(extendedModifier.isImplicit());
                counter++;
            }else {
                org.junit.Assert.assertFalse(extendedModifier.isImplicit());
                org.junit.Assert.assertTrue(extendedModifier.getPosition().isValidPosition());
                org.junit.Assert.assertEquals(extendedModifier.getKind().toString(), extendedModifier.getPosition().getCompilationUnit().getOriginalSourceCode().substring(extendedModifier.getPosition().getSourceStart(), ((extendedModifier.getPosition().getSourceEnd()) + 1)));
                counter++;
            }
        }
        org.junit.Assert.assertEquals(3, counter);
        org.junit.Assert.assertEquals(ModifierKind.PUBLIC, fieldExplicit.getVisibility());
        org.junit.Assert.assertTrue(fieldExplicit.hasModifier(ModifierKind.STATIC));
        org.junit.Assert.assertTrue(fieldExplicit.hasModifier(ModifierKind.PUBLIC));
        org.junit.Assert.assertTrue(fieldExplicit.hasModifier(ModifierKind.FINAL));
        org.junit.Assert.assertEquals(4, dumbType.getMethods().size());
        CtMethod staticMethod = ((CtMethod) (dumbType.getMethodsByName("foo").get(0)));
        org.junit.Assert.assertTrue(staticMethod.hasModifier(ModifierKind.PUBLIC));
        org.junit.Assert.assertTrue(staticMethod.hasModifier(ModifierKind.STATIC));
        extendedModifierSet = staticMethod.getExtendedModifiers();
        org.junit.Assert.assertEquals(2, extendedModifierSet.size());
        org.junit.Assert.assertTrue(extendedModifierSet.contains(new CtExtendedModifier(ModifierKind.PUBLIC, true)));
        org.junit.Assert.assertTrue(extendedModifierSet.contains(new CtExtendedModifier(ModifierKind.STATIC, false)));
        CtMethod publicMethod = ((CtMethod) (dumbType.getMethodsByName("machin").get(0)));
        org.junit.Assert.assertTrue(publicMethod.hasModifier(ModifierKind.PUBLIC));
        org.junit.Assert.assertFalse(publicMethod.hasModifier(ModifierKind.STATIC));
        extendedModifierSet = publicMethod.getExtendedModifiers();
        org.junit.Assert.assertEquals(2, extendedModifierSet.size());
        org.junit.Assert.assertTrue(extendedModifierSet.contains(new CtExtendedModifier(ModifierKind.PUBLIC, true)));
        org.junit.Assert.assertTrue(extendedModifierSet.contains(new CtExtendedModifier(ModifierKind.ABSTRACT, true)));
        CtMethod defaultMethod = ((CtMethod) (dumbType.getMethodsByName("bla").get(0)));
        org.junit.Assert.assertTrue(defaultMethod.hasModifier(ModifierKind.PUBLIC));
        org.junit.Assert.assertTrue(defaultMethod.isDefaultMethod());
        org.junit.Assert.assertFalse(defaultMethod.hasModifier(ModifierKind.STATIC));
        extendedModifierSet = defaultMethod.getExtendedModifiers();
        org.junit.Assert.assertEquals(1, extendedModifierSet.size());
        org.junit.Assert.assertTrue(extendedModifierSet.contains(new CtExtendedModifier(ModifierKind.PUBLIC, true)));
        CtMethod explicitDefaultMethod = ((CtMethod) (dumbType.getMethodsByName("anotherOne").get(0)));
        org.junit.Assert.assertTrue(explicitDefaultMethod.hasModifier(ModifierKind.PUBLIC));
        extendedModifierSet = explicitDefaultMethod.getExtendedModifiers();
        org.junit.Assert.assertEquals(2, extendedModifierSet.size());
        org.junit.Assert.assertTrue(extendedModifierSet.contains(new CtExtendedModifier(ModifierKind.PUBLIC, false)));
        org.junit.Assert.assertTrue(extendedModifierSet.contains(new CtExtendedModifier(ModifierKind.ABSTRACT, true)));
    }

    @org.junit.Test
    public void testInterfacePrettyPrinting() throws java.io.IOException {
        String originalFilePath = "./src/test/resources/spoon/test/itf/DumbItf.java";
        String targetDir = "./target/spoon-dumbitf";
        Launcher spoon = new Launcher();
        spoon.addInputResource(originalFilePath);
        spoon.getEnvironment().setCommentEnabled(true);
        spoon.getEnvironment().setShouldCompile(true);
        spoon.getEnvironment().setAutoImports(true);
        spoon.setSourceOutputDirectory(targetDir);
        spoon.run();
        String originalFile = org.apache.commons.lang3.StringUtils.join(java.nio.file.Files.readAllLines(new java.io.File(originalFilePath).toPath()), "\n").replaceAll("\\s", "");
        String prettyPrintedFile = org.apache.commons.lang3.StringUtils.join(java.nio.file.Files.readAllLines(new java.io.File((targetDir + "/toto/DumbItf.java")).toPath()), "\n").replaceAll("\\s", "");
        org.junit.Assert.assertEquals(originalFile, prettyPrintedFile);
    }
}

