package spoon.test.architecture;


import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import org.junit.Test;
import spoon.Launcher;
import spoon.SpoonAPI;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.filter.AbstractFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.test.architecture.SpoonArchitectureEnforcerTest;

import static spoon.test.architecture.SpoonArchitectureEnforcerTest.assertSetEquals;


public class SpoonArchitectureEnforcerTest {
    @Test
    public void statelessFactory() throws java.lang.Exception {
        SpoonAPI spoon = new Launcher();
        spoon.addInputResource("src/main/java/spoon/reflect/factory");
        spoon.buildModel();
        for (CtType t : spoon.getFactory().Package().getRootPackage().getElements(new AbstractFilter<CtType>() {
            @java.lang.Override
            public boolean matches(CtType element) {
                return (super.matches(element)) && (element.getSimpleName().contains("Factory"));
            }
        })) {
            for (java.lang.Object o : t.getFields()) {
                spoon.reflect.declaration.CtField f = ((spoon.reflect.declaration.CtField) (o));
                if (f.getSimpleName().equals("factory")) {
                    continue;
                }
                if ((f.hasModifier(ModifierKind.FINAL)) || (f.hasModifier(ModifierKind.TRANSIENT))) {
                    continue;
                }
                org.junit.Assert.fail("architectural constraint: a factory must be stateless");
            }
        }
    }

    @Test
    public void testFactorySubFactory() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.addInputResource("./src/main/java/spoon/reflect/factory");
        class SanityCheck {
            int val = 0;
        }
        SanityCheck sanityCheck = new SanityCheck();
        launcher.addProcessor(new spoon.processing.AbstractManualProcessor() {
            @java.lang.Override
            public void process() {
                CtType factoryImpl = getFactory().Interface().get(spoon.reflect.factory.Factory.class);
                CtPackage factoryPackage = getFactory().Package().getOrCreate("spoon.reflect.factory");
                spoon.reflect.declaration.CtInterface itf = getFactory().Interface().create("MegaFactoryItf");
                CtClass impl = getFactory().Class().create("MegaFactory");
                for (CtType<?> t : factoryPackage.getTypes()) {
                    if (t.getSimpleName().startsWith("Mega"))
                        continue;

                    for (CtMethod<?> m : t.getMethods()) {
                        if ((m.hasModifier(ModifierKind.PUBLIC)) == false)
                            continue;

                        if (!(m.getSimpleName().startsWith("create")))
                            continue;

                        if (m.getSimpleName().equals("create")) {
                            String simpleNameType = m.getType().getSimpleName().replace("Ct", "");
                            CtMethod method = m.clone();
                            method.setSimpleName(("create" + simpleNameType));
                            org.junit.Assert.assertTrue(((((method.getSignature()) + " (from ") + (t.getQualifiedName())) + ") is not present in the main factory"), factoryImpl.hasMethod(method));
                            continue;
                        }
                        if (m.getSimpleName().equals("createReference"))
                            continue;

                        if (m.getModifiers().contains(ModifierKind.ABSTRACT))
                            continue;

                        (sanityCheck.val)++;
                        org.junit.Assert.assertTrue(((m.getSignature()) + " is not present in the main factory"), factoryImpl.hasMethod(m));
                    }
                }
            }
        });
        launcher.run();
        org.junit.Assert.assertTrue(((sanityCheck.val) > 100));
    }

    @Test
    public void testSrcMainJava() throws java.lang.Exception {
        Launcher spoon = new Launcher();
        spoon.getEnvironment().setCommentEnabled(true);
        spoon.addInputResource("src/main/java/");
        spoon.buildModel();
        java.util.List<String> notDocumented = new java.util.ArrayList<>();
        for (CtMethod method : spoon.getModel().getElements(new TypeFilter<>(CtMethod.class))) {
            if ((((((((method.hasModifier(ModifierKind.PUBLIC)) && (!(method.getSimpleName().startsWith("get")))) && (!(method.getSimpleName().startsWith("set")))) && (!(method.getSimpleName().startsWith("is")))) && (!(method.getSimpleName().startsWith("add")))) && (!(method.getSimpleName().startsWith("remove")))) && ((method.getTopDefinitions().size()) == 0)) && ((method.hasModifier(ModifierKind.ABSTRACT)) || ((method.filterChildren(new TypeFilter<>(spoon.reflect.code.CtCodeElement.class)).list().size()) > 35))) {
                if ((method.getDocComment().length()) <= 15) {
                    notDocumented.add((((method.getParent(CtType.class).getQualifiedName()) + "#") + (method.getSignature())));
                }
            }
        }
        if ((notDocumented.size()) > 0) {
            org.junit.Assert.fail((((notDocumented.size()) + " public methods should be documented with proper API documentation: \n") + (org.apache.commons.lang3.StringUtils.join(notDocumented, "\n"))));
        }
        java.util.List<CtConstructorCall> treeSetWithoutComparators = spoon.getFactory().Package().getRootPackage().filterChildren(new AbstractFilter<CtConstructorCall>() {
            @java.lang.Override
            public boolean matches(CtConstructorCall element) {
                return (element.getType().getActualClass().equals(TreeSet.class)) && ((element.getArguments().size()) == 0);
            }
        }).list();
        org.junit.Assert.assertEquals(0, treeSetWithoutComparators.size());
    }

    @Test
    public void metamodelPackageRule() throws java.lang.Exception {
        java.util.List<String> exceptions = java.util.Collections.singletonList("CtWildcardStaticTypeMemberReferenceImpl");
        SpoonAPI implementations = new Launcher();
        implementations.addInputResource("src/main/java/spoon/support/reflect/declaration");
        implementations.addInputResource("src/main/java/spoon/support/reflect/code");
        implementations.addInputResource("src/main/java/spoon/support/reflect/reference");
        implementations.buildModel();
        SpoonAPI interfaces = new Launcher();
        interfaces.addInputResource("src/main/java/spoon/reflect/declaration");
        interfaces.addInputResource("src/main/java/spoon/reflect/code");
        interfaces.addInputResource("src/main/java/spoon/reflect/reference");
        interfaces.addInputResource("src/main/java/spoon/support/DefaultCoreFactory.java");
        interfaces.buildModel();
        for (CtType<?> implType : implementations.getModel().getAllTypes()) {
            if (!(exceptions.contains(implType.getSimpleName()))) {
                String impl = implType.getQualifiedName().replace(".support", "").replace("Impl", "");
                CtType interfaceType = interfaces.getFactory().Type().get(impl);
                org.junit.Assert.assertTrue(implType.getReference().isSubtypeOf(interfaceType.getReference()));
            }
        }
    }

    @Test
    public void testGoodTestClassNames() throws java.lang.Exception {
        SpoonAPI spoon = new Launcher();
        spoon.addInputResource("src/test/java/");
        spoon.buildModel();
        for (CtMethod<?> meth : spoon.getModel().getElements(new TypeFilter<CtMethod>(CtMethod.class) {
            @java.lang.Override
            public boolean matches(CtMethod element) {
                return (super.matches(element)) && ((element.getAnnotation(Test.class)) != null);
            }
        })) {
            org.junit.Assert.assertTrue(("naming contract violated for " + (meth.getParent(CtClass.class).getSimpleName())), ((meth.getParent(CtClass.class).getSimpleName().startsWith("Test")) || (meth.getParent(CtClass.class).getSimpleName().endsWith("Test"))));
        }
        org.junit.Assert.assertEquals(0, spoon.getModel().getElements(new TypeFilter<CtTypeReference>(CtTypeReference.class) {
            @java.lang.Override
            public boolean matches(CtTypeReference element) {
                CtMethod parent = element.getParent(CtMethod.class);
                return "junit.framework.TestCase".equals(element.getQualifiedName());
            }
        }).size());
    }

    @Test
    public void testStaticClasses() throws java.lang.Exception {
        SpoonAPI spoon = new Launcher();
        spoon.addInputResource("src/main/java/");
        spoon.buildModel();
        for (CtClass<?> klass : spoon.getModel().getElements(new TypeFilter<CtClass>(CtClass.class) {
            @java.lang.Override
            public boolean matches(CtClass element) {
                return ((((element.getSuperclass()) == null) && (super.matches(element))) && ((element.getMethods().size()) > 0)) && (element.getElements(new TypeFilter<>(CtMethod.class)).stream().allMatch(( x) -> x.hasModifier(ModifierKind.STATIC)));
            }
        })) {
            org.junit.Assert.assertTrue(klass.getElements(new TypeFilter<>(spoon.reflect.declaration.CtConstructor.class)).stream().allMatch(( x) -> x.hasModifier(ModifierKind.PRIVATE)));
        }
    }

    @Test
    public void testInterfacesAreCtScannable() {
        Launcher interfaces = new Launcher();
        interfaces.addInputResource("src/main/java/spoon/support");
        interfaces.addInputResource("src/main/java/spoon/reflect/declaration");
        interfaces.addInputResource("src/main/java/spoon/reflect/code");
        interfaces.addInputResource("src/main/java/spoon/reflect/reference");
        interfaces.addInputResource("src/main/java/spoon/support/reflect/declaration");
        interfaces.addInputResource("src/main/java/spoon/support/reflect/code");
        interfaces.addInputResource("src/main/java/spoon/support/reflect/reference");
        interfaces.addInputResource("src/main/java/spoon/reflect/visitor/CtScanner.java");
        interfaces.buildModel();
        CtClass<?> ctScanner = interfaces.getFactory().Class().get(spoon.reflect.visitor.CtInheritanceScanner.class);
        java.util.List<String> missingMethods = new java.util.ArrayList<>();
        new spoon.test.metamodel.SpoonMetaModel(interfaces.getFactory()).getConcepts().forEach(( mmConcept) -> {
            if (((mmConcept.getKind()) == (spoon.test.metamodel.MMTypeKind.ABSTRACT)) && ((mmConcept.getModelInterface()) != null)) {
                spoon.reflect.declaration.CtInterface abstractIface = mmConcept.getModelInterface();
                String methodName = "scan" + (abstractIface.getSimpleName());
                if (ctScanner.getMethodsByName(methodName).isEmpty()) {
                    missingMethods.add(methodName);
                }
            }
        });
        org.junit.Assert.assertTrue(("The following methods are missing in CtScanner: \n" + (org.apache.commons.lang3.StringUtils.join(missingMethods, "\n"))), missingMethods.isEmpty());
    }

    @Test
    public void testSpecPackage() throws java.lang.Exception {
        Set<String> officialPackages = new TreeSet<>();
        officialPackages.add("spoon.compiler.builder");
        officialPackages.add("spoon.compiler");
        officialPackages.add("spoon.experimental.modelobs.action");
        officialPackages.add("spoon.experimental.modelobs.context");
        officialPackages.add("spoon.experimental.modelobs");
        officialPackages.add("spoon.experimental");
        officialPackages.add("spoon.legacy");
        officialPackages.add("spoon.processing");
        officialPackages.add("spoon.refactoring");
        officialPackages.add("spoon.reflect.annotations");
        officialPackages.add("spoon.reflect.code");
        officialPackages.add("spoon.reflect.cu.position");
        officialPackages.add("spoon.reflect.cu");
        officialPackages.add("spoon.reflect.declaration");
        officialPackages.add("spoon.reflect.eval");
        officialPackages.add("spoon.reflect.factory");
        officialPackages.add("spoon.reflect.path.impl");
        officialPackages.add("spoon.reflect.path");
        officialPackages.add("spoon.reflect.reference");
        officialPackages.add("spoon.reflect.visitor.chain");
        officialPackages.add("spoon.reflect.visitor.filter");
        officialPackages.add("spoon.reflect.visitor.printer");
        officialPackages.add("spoon.reflect.visitor");
        officialPackages.add("spoon.reflect");
        officialPackages.add("spoon.support.comparator");
        officialPackages.add("spoon.support.compiler.jdt");
        officialPackages.add("spoon.support.compiler");
        officialPackages.add("spoon.support.gui");
        officialPackages.add("spoon.support.reflect.code");
        officialPackages.add("spoon.support.reflect.cu.position");
        officialPackages.add("spoon.support.reflect.cu");
        officialPackages.add("spoon.support.reflect.declaration");
        officialPackages.add("spoon.support.reflect.eval");
        officialPackages.add("spoon.reflect.meta");
        officialPackages.add("spoon.reflect.meta.impl");
        officialPackages.add("spoon.support.reflect.reference");
        officialPackages.add("spoon.support.reflect");
        officialPackages.add("spoon.support.template");
        officialPackages.add("spoon.support.util");
        officialPackages.add("spoon.support.visitor.clone");
        officialPackages.add("spoon.support.visitor.equals");
        officialPackages.add("spoon.support.visitor.java.internal");
        officialPackages.add("spoon.support.visitor.java.reflect");
        officialPackages.add("spoon.support.visitor.java");
        officialPackages.add("spoon.support.visitor.replace");
        officialPackages.add("spoon.support.visitor");
        officialPackages.add("spoon.support");
        officialPackages.add("spoon.template");
        officialPackages.add("spoon.testing.utils");
        officialPackages.add("spoon.testing");
        officialPackages.add("spoon");
        officialPackages.add("");
        SpoonAPI spoon = new Launcher();
        spoon.addInputResource("src/main/java/");
        spoon.buildModel();
        final Set<String> currentPackages = new TreeSet<>();
        spoon.getModel().processWith(new spoon.processing.AbstractProcessor<CtPackage>() {
            @java.lang.Override
            public void process(CtPackage element) {
                currentPackages.add(element.getQualifiedName());
            }
        });
        assertSetEquals("you have created a new package or removed an existing one, please declare it explicitly in SpoonArchitectureEnforcerTest#testSpecPackage", officialPackages, currentPackages);
    }

    private static void assertSetEquals(String msg, Set<?> set1, Set<?> set2) {
        if ((set1 == null) || (set2 == null)) {
            throw new java.lang.IllegalArgumentException();
        }
        if ((set1.size()) != (set2.size())) {
            throw new java.lang.AssertionError(((msg + "\n\nDetails: ") + (SpoonArchitectureEnforcerTest.computeDifference(set1, set2))));
        }
        if (!(set1.containsAll(set2))) {
            throw new java.lang.AssertionError(((msg + "\n\nDetails: ") + (SpoonArchitectureEnforcerTest.computeDifference(set1, set2))));
        }
    }

    private static String computeDifference(Set<?> set1, Set<?> set2) {
        Set<String> results = new java.util.HashSet<>();
        for (java.lang.Object o : set1) {
            if (!(set2.contains(o))) {
                results.add((("Missing package " + o) + " in computed set"));
            }else {
                set2.remove(o);
            }
        }
        for (java.lang.Object o : set2) {
            results.add((("Package " + o) + " presents in computed but not expected set."));
        }
        return org.apache.commons.lang3.StringUtils.join(results, "\n");
    }
}

