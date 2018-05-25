package spoon.test.imports;


import java.security.AccessControlException;
import java.util.List;
import spoon.Launcher;
import spoon.SpoonModelBuilder;
import spoon.compiler.SpoonResourceHelper;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtType;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtFieldReference;
import spoon.reflect.visitor.ImportScanner;
import spoon.reflect.visitor.ImportScannerImpl;
import spoon.reflect.visitor.MinimalImportScanner;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.NamedElementFilter;


public class ImportScannerTest {
    @org.junit.Test
    public void testComputeMinimalImportsInClass() throws java.lang.Exception {
        String packageName = "spoon.test.testclasses";
        String className = "SampleImportClass";
        String qualifiedName = (packageName + ".") + className;
        Factory aFactory = spoon.testing.utils.ModelUtils.build(packageName, className).getFactory();
        CtType<?> theClass = aFactory.Type().get(qualifiedName);
        ImportScanner importContext = new MinimalImportScanner();
        importContext.computeImports(theClass);
        java.util.Collection<spoon.reflect.declaration.CtImport> imports = importContext.getAllImports();
        org.junit.Assert.assertTrue(imports.isEmpty());
    }

    @org.junit.Test
    public void testComputeImportsInClass() throws java.lang.Exception {
        String packageName = "spoon.test.testclasses";
        String className = "SampleImportClass";
        String qualifiedName = (packageName + ".") + className;
        Factory aFactory = spoon.testing.utils.ModelUtils.build(packageName, className).getFactory();
        CtType<?> theClass = aFactory.Type().get(qualifiedName);
        ImportScanner importContext = new ImportScannerImpl();
        importContext.computeImports(theClass);
        java.util.Collection<spoon.reflect.declaration.CtImport> imports = importContext.getAllImports();
        org.junit.Assert.assertEquals(4, imports.size());
    }

    @org.junit.Test
    public void testComputeImportsInClassWithSameName() throws java.lang.Exception {
        String packageName = "spoon.test.imports.testclasses2";
        String className = "ImportSameName";
        String qualifiedName = (packageName + ".") + className;
        Launcher spoon = new Launcher();
        spoon.addInputResource("src/test/resources/spoon/test/imports/testclasses2/");
        spoon.buildModel();
        Factory aFactory = spoon.getFactory();
        CtType<?> theClass = aFactory.Type().get(qualifiedName);
        ImportScanner importContext = new ImportScannerImpl();
        importContext.computeImports(theClass);
        java.util.Collection<spoon.reflect.declaration.CtImport> imports = importContext.getAllImports();
        org.junit.Assert.assertEquals(0, imports.size());
    }

    @org.junit.Test
    public void testMultiCatchImport() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        Factory factory = spoon.createFactory();
        SpoonModelBuilder compiler = spoon.createCompiler(factory, SpoonResourceHelper.resources("./src/test/java/spoon/test/imports/testclasses/MultiCatch.java"));
        compiler.build();
        final List<CtClass> classes = Query.getElements(factory, new NamedElementFilter<>(CtClass.class, "MultiCatch"));
        ImportScanner importScanner = new ImportScannerImpl();
        importScanner.computeImports(classes.get(0));
        org.junit.Assert.assertTrue(importScanner.isImported(factory.Type().createReference(AccessControlException.class)));
    }

    @org.junit.Test
    public void testTargetTypeNull() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        Factory factory = spoon.createFactory();
        CtFieldReference fieldRef = factory.createFieldReference();
        fieldRef.setStatic(true);
        ImportScanner importScanner = new MinimalImportScanner();
        importScanner.computeImports(fieldRef);
        java.util.Collection<spoon.reflect.declaration.CtImport> imports = importScanner.getAllImports();
        org.junit.Assert.assertEquals(0, imports.size());
    }
}

