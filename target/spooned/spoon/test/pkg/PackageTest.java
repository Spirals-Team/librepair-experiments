package spoon.test.pkg;


import java.io.File;
import java.util.Collections;
import spoon.Launcher;
import spoon.compiler.SpoonResourceHelper;
import spoon.reflect.declaration.CtAnnotation;
import spoon.reflect.declaration.CtAnnotationType;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtPackageReference;
import spoon.reflect.visitor.DefaultJavaPrettyPrinter;
import spoon.reflect.visitor.PrettyPrinter;
import spoon.reflect.visitor.filter.NamedElementFilter;
import spoon.support.JavaOutputProcessor;
import spoon.test.annotation.testclasses.GlobalAnnotation;
import spoon.test.pkg.name.PackageTestClass;
import spoon.test.pkg.testclasses.ElementProcessor;
import spoon.test.pkg.testclasses.Foo;
import spoon.testing.utils.ModelUtils;


public class PackageTest {
    @org.junit.Test
    public void testPackage() throws java.lang.Exception {
        final String classFilePath = "./src/test/java/spoon/test/pkg/name/PackageTestClass.java";
        final String packageInfoFilePath = "./src/test/java/spoon/test/pkg/package-info.java";
        final File packageInfoFile = new File(packageInfoFilePath);
        Launcher spoon = new Launcher();
        Factory factory = spoon.createFactory();
        factory.getEnvironment().setCommentEnabled(true);
        spoon.createCompiler(factory, SpoonResourceHelper.resources(classFilePath, packageInfoFilePath)).build();
        CtClass<?> clazz = factory.Class().get(PackageTestClass.class);
        org.junit.Assert.assertEquals(PackageTestClass.class, clazz.getActualClass());
        CtPackage ctPackage = clazz.getPackage();
        org.junit.Assert.assertEquals("spoon.test.pkg.name", ctPackage.getQualifiedName());
        org.junit.Assert.assertEquals("", ctPackage.getDocComment());
        org.junit.Assert.assertTrue(CtPackage.class.isAssignableFrom(ctPackage.getParent().getClass()));
        ctPackage = ((CtPackage) (ctPackage.getParent()));
        org.junit.Assert.assertEquals("spoon.test.pkg", ctPackage.getQualifiedName());
        org.junit.Assert.assertNotNull(ctPackage.getPosition());
        org.junit.Assert.assertEquals(packageInfoFile.getCanonicalPath(), ctPackage.getPosition().getFile().getCanonicalPath());
        org.junit.Assert.assertEquals(1, ctPackage.getPosition().getLine());
        org.junit.Assert.assertEquals(0, ctPackage.getPosition().getSourceStart());
        org.junit.Assert.assertEquals(71, ctPackage.getPosition().getSourceEnd());
        org.junit.Assert.assertEquals(1, ctPackage.getAnnotations().size());
        org.junit.Assert.assertEquals("This is test\nJavaDoc.", ctPackage.getComments().get(0).getContent());
        CtAnnotation<?> annotation = ctPackage.getAnnotations().get(0);
        org.junit.Assert.assertEquals(java.lang.Deprecated.class, annotation.getAnnotationType().getActualClass());
        org.junit.Assert.assertEquals(packageInfoFile.getCanonicalPath(), annotation.getPosition().getFile().getCanonicalPath());
        org.junit.Assert.assertEquals(5, annotation.getPosition().getLine());
        org.junit.Assert.assertTrue(CtPackage.class.isAssignableFrom(ctPackage.getParent().getClass()));
        ctPackage = ((CtPackage) (ctPackage.getParent()));
        org.junit.Assert.assertEquals("spoon.test", ctPackage.getQualifiedName());
        org.junit.Assert.assertEquals("", ctPackage.getDocComment());
    }

    @org.junit.Test
    public void testAnnotationOnPackage() throws java.lang.Exception {
        Launcher launcher = new Launcher();
        Factory factory = launcher.getFactory();
        factory.getEnvironment().setAutoImports(false);
        spoon.SpoonModelBuilder compiler = launcher.createCompiler(factory);
        launcher.setSourceOutputDirectory("./target/spooned/");
        compiler.addInputSource(new File("./src/test/java/spoon/test/pkg/testclasses/"));
        compiler.build();
        compiler.generateProcessedSourceFiles(spoon.OutputType.CLASSES);
        final spoon.SpoonModelBuilder newCompiler = launcher.createCompiler(launcher.createFactory());
        newCompiler.addInputSource(new File("./target/spooned/spoon/test/pkg/testclasses/"));
        try {
            org.junit.Assert.assertTrue(newCompiler.build());
        } catch (java.lang.Exception ignore) {
            org.junit.Assert.fail();
        }
    }

    @org.junit.Test
    public void testPrintPackageInfoWhenNothingInPackage() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/test/java/spoon/test/pkg/testclasses/internal");
        launcher.setSourceOutputDirectory("./target/spooned/package");
        launcher.getEnvironment().setCommentEnabled(true);
        launcher.run();
        final CtPackage aPackage = launcher.getFactory().Package().get("spoon.test.pkg.testclasses.internal");
        org.junit.Assert.assertEquals(1, aPackage.getAnnotations().size());
        org.junit.Assert.assertEquals(3, aPackage.getComments().size());
        org.junit.Assert.assertEquals(spoon.reflect.code.CtComment.CommentType.JAVADOC, aPackage.getComments().get(0).getCommentType());
        org.junit.Assert.assertEquals(spoon.reflect.code.CtComment.CommentType.BLOCK, aPackage.getComments().get(1).getCommentType());
        org.junit.Assert.assertEquals(spoon.reflect.code.CtComment.CommentType.INLINE, aPackage.getComments().get(2).getCommentType());
        spoon.testing.Assert.assertThat(aPackage).isEqualTo(ModelUtils.build(new File("./target/spooned/package/spoon/test/pkg/testclasses/internal")).Package().get("spoon.test.pkg.testclasses.internal"));
    }

    @org.junit.Test
    public void testAnnotationInPackageInfoWhenTemplatesCompiled() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        spoon.compiler.Environment environment = launcher.getEnvironment();
        environment.setAutoImports(true);
        environment.setCommentEnabled(true);
        launcher.addInputResource("./src/test/java/spoon/test/pkg/package-info.java");
        launcher.setSourceOutputDirectory("./target/spooned/packageAndTemplate");
        launcher.addTemplateResource(SpoonResourceHelper.createResource(new File("./src/test/java/spoon/test/pkg/test_templates/FakeTemplate.java")));
        launcher.buildModel();
        launcher.prettyprint();
        ModelUtils.canBeBuilt("./target/spooned/packageAndTemplate/spoon/test/pkg/package-info.java", 8);
    }

    @org.junit.Test
    public void testRenamePackageAndPrettyPrint() throws java.lang.Exception {
        final Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/java/spoon/test/pkg/testclasses/Foo.java");
        spoon.buildModel();
        CtPackage ctPackage = spoon.getModel().getElements(new NamedElementFilter<CtPackage>(CtPackage.class, "spoon")).get(0);
        ctPackage.setSimpleName("otherName");
        CtClass foo = spoon.getModel().getElements(new NamedElementFilter<CtClass>(CtClass.class, "Foo")).get(0);
        org.junit.Assert.assertEquals("otherName.test.pkg.testclasses.Foo", foo.getQualifiedName());
        PrettyPrinter prettyPrinter = new DefaultJavaPrettyPrinter(spoon.getEnvironment());
        prettyPrinter.calculate(spoon.getFactory().CompilationUnit().getOrCreate("./src/test/java/spoon/test/pkg/testclasses/Foo.java"), Collections.singletonList(foo));
        String result = prettyPrinter.getResult();
        org.junit.Assert.assertTrue(result.contains("package otherName.test.pkg.testclasses;"));
    }

    @org.junit.Test
    public void testRenamePackageAndPrettyPrintNoclasspath() throws java.lang.Exception {
        final Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/resources/noclasspath/app/Test.java");
        spoon.getEnvironment().setNoClasspath(true);
        spoon.buildModel();
        CtPackage ctPackage = spoon.getModel().getElements(new NamedElementFilter<CtPackage>(CtPackage.class, "app")).get(0);
        ctPackage.setSimpleName("otherName");
        CtClass foo = spoon.getModel().getElements(new NamedElementFilter<CtClass>(CtClass.class, "Test")).get(0);
        org.junit.Assert.assertEquals("otherName.Test", foo.getQualifiedName());
        PrettyPrinter prettyPrinter = new DefaultJavaPrettyPrinter(spoon.getEnvironment());
        prettyPrinter.calculate(spoon.getFactory().CompilationUnit().getOrCreate("./src/test/resources/noclasspath/app/Test.java"), Collections.singletonList(foo));
        String result = prettyPrinter.getResult();
        org.junit.Assert.assertTrue(result.contains("package otherName;"));
    }

    @org.junit.Test
    public void testRenamePackageAndPrettyPrintWithProcessor() throws java.lang.Exception {
        String destPath = "./target/spoon-rename-processor";
        final Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/resources/noclasspath/app/Test.java");
        spoon.getEnvironment().setNoClasspath(true);
        spoon.addProcessor(new ElementProcessor());
        spoon.setSourceOutputDirectory(destPath);
        spoon.run();
        String fileDir = destPath + "/newtest/Test.java";
        File f = new File(fileDir);
        org.junit.Assert.assertTrue(f.exists());
        java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(f));
        org.junit.Assert.assertTrue(reader.lines().anyMatch(( s) -> {
            return s.equals("package newtest;");
        }));
    }

    @org.junit.Test
    public void testRenameRootPackage() throws java.lang.Exception {
        final Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/resources/noclasspath/app/Test.java");
        spoon.getEnvironment().setNoClasspath(true);
        spoon.buildModel();
        CtPackage rootPackage = spoon.getFactory().Package().getRootPackage();
        String rootPackageName = rootPackage.getSimpleName();
        rootPackage.setSimpleName("test");
        org.junit.Assert.assertEquals(rootPackageName, rootPackage.getSimpleName());
    }

    @org.junit.Test
    public void testRenameRootPackageWithNullOrEmpty() throws java.lang.Exception {
        final Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/resources/noclasspath/app/Test.java");
        spoon.getEnvironment().setNoClasspath(true);
        spoon.buildModel();
        CtPackage rootPackage = spoon.getFactory().Package().getRootPackage();
        String rootPackageName = rootPackage.getSimpleName();
        org.junit.Assert.assertEquals(CtPackage.TOP_LEVEL_PACKAGE_NAME, rootPackageName);
        rootPackage.setSimpleName("");
        org.junit.Assert.assertEquals(CtPackage.TOP_LEVEL_PACKAGE_NAME, rootPackageName);
        rootPackage.setSimpleName(null);
        org.junit.Assert.assertEquals(CtPackage.TOP_LEVEL_PACKAGE_NAME, rootPackageName);
    }

    @org.junit.Test
    public void testAddAnnotationToPackage() throws java.lang.Exception {
        final Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/java/spoon/test/pkg/testclasses/Foo.java");
        spoon.getEnvironment().setAutoImports(true);
        File outputDir = new File("./target/spoon-packageinfo");
        spoon.getEnvironment().setSourceOutputDirectory(outputDir);
        spoon.buildModel();
        CtAnnotationType annotationType = ((CtAnnotationType) (spoon.getFactory().Annotation().get(GlobalAnnotation.class)));
        CtAnnotation annotation = spoon.getFactory().Core().createAnnotation();
        annotation.setAnnotationType(annotationType.getReference());
        CtPackage ctPackage = spoon.getFactory().Package().get("spoon.test.pkg.testclasses");
        ctPackage.addAnnotation(annotation);
        JavaOutputProcessor outputProcessor = spoon.createOutputWriter();
        outputProcessor.process(ctPackage);
        File packageInfo = new File(outputDir, "spoon/test/pkg/testclasses/package-info.java");
        org.junit.Assert.assertTrue(packageInfo.exists());
        ModelUtils.canBeBuilt(packageInfo, 8);
        java.util.List<String> lines = java.nio.file.Files.readAllLines(packageInfo.toPath());
        org.junit.Assert.assertFalse(lines.isEmpty());
        for (String s : lines) {
            if (s.trim().startsWith("import")) {
                org.junit.Assert.assertEquals("import spoon.test.annotation.testclasses.GlobalAnnotation;", s.trim());
            }
            if (s.trim().startsWith("@")) {
                org.junit.Assert.assertEquals("@GlobalAnnotation", s.trim());
            }
        }
    }

    @org.junit.Test
    public void testGetFQNSimple() {
        final Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/java/spoon/test/pkg/testclasses/Foo.java");
        spoon.buildModel();
        CtClass fooClass = spoon.getFactory().Class().get(Foo.class);
        CtField field = fooClass.getField("fieldList");
        CtPackageReference fieldPkg = field.getType().getPackage();
        org.junit.Assert.assertEquals("java.util", fieldPkg.getSimpleName());
        org.junit.Assert.assertEquals("java.util", fieldPkg.getQualifiedName());
    }

    @org.junit.Test
    public void testGetFQNInNoClassPath() {
        final Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/resources/noclasspath/TorIntegration.java");
        spoon.getEnvironment().setNoClasspath(true);
        spoon.buildModel();
        CtClass torClass = spoon.getFactory().Class().get("com.duckduckgo.mobile.android.util.TorIntegration");
        CtField field = torClass.getField("orbotHelper");
        CtPackageReference fieldPkg = field.getType().getPackage();
        org.junit.Assert.assertEquals("info.guardianproject.onionkit.ui", fieldPkg.getSimpleName());
        org.junit.Assert.assertEquals("info.guardianproject.onionkit.ui", fieldPkg.getQualifiedName());
    }
}

