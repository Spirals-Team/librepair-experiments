package spoon.test.jdtimportbuilder;


import spoon.Launcher;
import spoon.reflect.cu.CompilationUnit;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtImport;
import spoon.reflect.declaration.CtImportKind;
import spoon.reflect.reference.CtFieldReference;
import spoon.reflect.reference.CtPackageReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.test.imports.testclasses.A;
import spoon.test.imports.testclasses.ClassWithInvocation;
import spoon.test.jdtimportbuilder.testclasses.StarredImport;
import spoon.test.jdtimportbuilder.testclasses.StaticImport;
import spoon.test.jdtimportbuilder.testclasses.StaticImportWithInheritance;


public class ImportBuilderTest {
    @org.junit.Test
    public void testWithNoImport() {
        Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/java/spoon/test/imports/testclasses/A.java");
        spoon.getEnvironment().setAutoImports(true);
        spoon.buildModel();
        CtClass classA = spoon.getFactory().Class().get(A.class);
        CompilationUnit unitA = spoon.getFactory().CompilationUnit().getMap().get(classA.getPosition().getFile().getPath());
        org.junit.Assert.assertTrue(unitA.getImports().isEmpty());
    }

    @org.junit.Test
    public void testWithSimpleImport() {
        Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/java/spoon/test/imports/testclasses/ClassWithInvocation.java");
        spoon.getEnvironment().setAutoImports(true);
        spoon.buildModel();
        CtClass classA = spoon.getFactory().Class().get(ClassWithInvocation.class);
        CompilationUnit unitA = spoon.getFactory().CompilationUnit().getMap().get(classA.getPosition().getFile().getPath());
        java.util.Collection<CtImport> imports = unitA.getImports();
        org.junit.Assert.assertEquals(1, imports.size());
        CtImport ref = imports.iterator().next();
        org.junit.Assert.assertEquals("import spoon.test.annotation.testclasses.GlobalAnnotation;\n", ref.toString());
        org.junit.Assert.assertTrue(((ref.getReference()) instanceof CtTypeReference));
        CtTypeReference refType = ((CtTypeReference) (ref.getReference()));
        org.junit.Assert.assertEquals("spoon.test.annotation.testclasses.GlobalAnnotation", refType.getQualifiedName());
    }

    @org.junit.Test
    public void testWithSimpleImportNoAutoimport() {
        Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/java/spoon/test/imports/testclasses/ClassWithInvocation.java");
        spoon.getEnvironment().setAutoImports(false);
        spoon.buildModel();
        CtClass classA = spoon.getFactory().Class().get(ClassWithInvocation.class);
        CompilationUnit unitA = spoon.getFactory().CompilationUnit().getMap().get(classA.getPosition().getFile().getPath());
        org.junit.Assert.assertTrue(unitA.getImports().isEmpty());
    }

    @org.junit.Test
    public void testInternalImportWhenNoClasspath() {
        Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/resources/noclasspath/Attachment.java");
        spoon.getEnvironment().setAutoImports(true);
        spoon.getEnvironment().setNoClasspath(true);
        spoon.buildModel();
        CtClass classA = spoon.getFactory().Class().get("it.feio.android.omninotes.models.Attachment");
        CompilationUnit unitA = spoon.getFactory().CompilationUnit().getMap().get(classA.getPosition().getFile().getPath());
        org.junit.Assert.assertTrue(unitA.getImports().isEmpty());
    }

    @org.junit.Test
    public void testSimpleStaticImport() {
        Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/java/spoon/test/jdtimportbuilder/testclasses/StaticImport.java");
        spoon.getEnvironment().setAutoImports(true);
        spoon.buildModel();
        CtClass classA = spoon.getFactory().Class().get(StaticImport.class);
        CompilationUnit unitA = spoon.getFactory().CompilationUnit().getMap().get(classA.getPosition().getFile().getPath());
        java.util.Collection<CtImport> imports = unitA.getImports();
        org.junit.Assert.assertEquals(1, imports.size());
        CtImport ref = imports.iterator().next();
        org.junit.Assert.assertTrue(((ref.getReference()) instanceof CtFieldReference));
        org.junit.Assert.assertEquals("spoon.test.jdtimportbuilder.testclasses.staticimport.Dependency#ANY", ((CtFieldReference) (ref.getReference())).getQualifiedName());
    }

    @org.junit.Test
    public void testWithStaticStarredImportFromInterface() {
        Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/java/spoon/test/jdtimportbuilder/testclasses/StarredImport.java");
        spoon.addInputResource("./src/test/java/spoon/test/jdtimportbuilder/testclasses/fullpack/");
        spoon.getEnvironment().setAutoImports(true);
        spoon.buildModel();
        CtClass classA = spoon.getFactory().Class().get(StarredImport.class);
        CompilationUnit unitA = spoon.getFactory().CompilationUnit().getMap().get(classA.getPosition().getFile().getPath());
        java.util.Collection<CtImport> imports = unitA.getImports();
        org.junit.Assert.assertEquals(1, imports.size());
        java.util.Iterator<CtImport> iterator = imports.iterator();
        CtImport ctImport = iterator.next();
        org.junit.Assert.assertEquals(CtImportKind.ALL_TYPES, ctImport.getImportKind());
        org.junit.Assert.assertTrue(((ctImport.getReference()) instanceof CtPackageReference));
        CtPackageReference ref = ((CtPackageReference) (ctImport.getReference()));
        org.junit.Assert.assertEquals("spoon.test.jdtimportbuilder.testclasses.fullpack", ref.getQualifiedName());
    }

    @org.junit.Test
    public void testWithStaticInheritedImport() {
        Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/java/spoon/test/jdtimportbuilder/testclasses/StaticImportWithInheritance.java");
        spoon.addInputResource("./src/test/java/spoon/test/jdtimportbuilder/testclasses/staticimport");
        spoon.getEnvironment().setAutoImports(true);
        spoon.getEnvironment().setShouldCompile(true);
        spoon.setSourceOutputDirectory("./target/spoon-jdtimport-inheritedstatic");
        spoon.run();
        CtClass classStatic = spoon.getFactory().Class().get(StaticImportWithInheritance.class);
        CompilationUnit unitStatic = spoon.getFactory().CompilationUnit().getMap().get(classStatic.getPosition().getFile().getPath());
        java.util.Collection<CtImport> imports = unitStatic.getImports();
        org.junit.Assert.assertEquals(1, imports.size());
        CtImport ctImport = imports.iterator().next();
        org.junit.Assert.assertEquals(CtImportKind.ALL_STATIC_MEMBERS, ctImport.getImportKind());
        org.junit.Assert.assertEquals("import static spoon.test.jdtimportbuilder.testclasses.staticimport.DependencySubClass.*;\n", ctImport.toString());
    }

    @org.junit.Test
    public void testWithImportFromItf() {
        Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/resources/jdtimportbuilder/");
        spoon.getEnvironment().setAutoImports(true);
        spoon.getEnvironment().setShouldCompile(true);
        spoon.setSourceOutputDirectory("./target/spoon-jdtimport-itfimport");
        spoon.run();
        CtClass classStatic = spoon.getFactory().Class().get("jdtimportbuilder.ItfImport");
        CompilationUnit unitStatic = spoon.getFactory().CompilationUnit().getMap().get(classStatic.getPosition().getFile().getPath());
        java.util.Collection<CtImport> imports = unitStatic.getImports();
        org.junit.Assert.assertEquals(1, imports.size());
        CtImport ctImport = imports.iterator().next();
        org.junit.Assert.assertEquals(CtImportKind.ALL_STATIC_MEMBERS, ctImport.getImportKind());
        org.junit.Assert.assertEquals("import static jdtimportbuilder.itf.DumbItf.*;\n", ctImport.toString());
    }
}

