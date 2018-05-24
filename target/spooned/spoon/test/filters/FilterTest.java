package spoon.test.filters;


import spoon.Launcher;
import spoon.reflect.declaration.CtField;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.reflect.visitor.filter.NamedElementFilter;
import spoon.test.imports.testclasses.internal4.Constants;


public class FilterTest {
    spoon.reflect.factory.Factory factory;

    @org.junit.Before
    public void setup() throws java.lang.Exception {
        factory = spoon.testing.utils.ModelUtils.build(spoon.test.filters.Foo.class);
    }

    @org.junit.Test
    public void testFilters() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> foo = factory.Package().get("spoon.test.filters").getType("Foo");
        org.junit.Assert.assertEquals("Foo", foo.getSimpleName());
        java.util.List<spoon.reflect.code.CtExpression<?>> expressions = foo.getElements(new spoon.reflect.visitor.filter.RegexFilter<spoon.reflect.code.CtExpression<?>>(".* = .*"));
        org.junit.Assert.assertEquals(2, expressions.size());
    }

    @org.junit.Test
    public void testReturnOrThrowFilter() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> foo = factory.Package().get("spoon.test.filters").getType("Foo");
        org.junit.Assert.assertEquals("Foo", foo.getSimpleName());
        java.util.List<spoon.reflect.code.CtCFlowBreak> expressions = foo.getElements(new spoon.reflect.visitor.filter.ReturnOrThrowFilter());
        org.junit.Assert.assertEquals(2, expressions.size());
    }

    @org.junit.Test
    public void testLineFilter() throws java.lang.Exception {
        CtType<spoon.test.filters.FooLine> foo = spoon.testing.utils.ModelUtils.buildClass(spoon.test.filters.FooLine.class);
        CtMethod method = foo.getMethod("simple");
        java.util.List<spoon.reflect.code.CtStatement> expressions = method.getElements(new spoon.reflect.visitor.filter.LineFilter());
        org.junit.Assert.assertEquals(3, expressions.size());
        org.junit.Assert.assertNull(expressions.get(0).getParent(new spoon.reflect.visitor.filter.LineFilter()));
        method = foo.getMethod("loopBlock");
        expressions = method.getElements(new spoon.reflect.visitor.filter.LineFilter());
        org.junit.Assert.assertEquals(2, expressions.size());
        org.junit.Assert.assertNull(expressions.get(0).getParent(new spoon.reflect.visitor.filter.LineFilter()));
        org.junit.Assert.assertTrue(((expressions.get(1).getParent(new spoon.reflect.visitor.filter.LineFilter())) instanceof spoon.reflect.code.CtLoop));
        method = foo.getMethod("loopNoBlock");
        expressions = method.getElements(new spoon.reflect.visitor.filter.LineFilter());
        org.junit.Assert.assertEquals(2, expressions.size());
        org.junit.Assert.assertNull(expressions.get(0).getParent(new spoon.reflect.visitor.filter.LineFilter()));
        org.junit.Assert.assertTrue(((expressions.get(1).getParent(new spoon.reflect.visitor.filter.LineFilter())) instanceof spoon.reflect.code.CtLoop));
        method = foo.getMethod("loopNoBody");
        spoon.reflect.code.CtFor lastStatement = ((spoon.reflect.code.CtFor) (method.getBody().getLastStatement()));
        expressions = method.getElements(new spoon.reflect.visitor.filter.LineFilter());
        org.junit.Assert.assertEquals(1, expressions.size());
        org.junit.Assert.assertEquals(lastStatement, lastStatement.getExpression().getParent(new spoon.reflect.visitor.filter.LineFilter()));
        method = foo.getMethod("ifBlock");
        expressions = method.getElements(new spoon.reflect.visitor.filter.LineFilter());
        org.junit.Assert.assertEquals(2, expressions.size());
        org.junit.Assert.assertNull(expressions.get(0).getParent(new spoon.reflect.visitor.filter.LineFilter()));
        org.junit.Assert.assertTrue(((expressions.get(1).getParent(new spoon.reflect.visitor.filter.LineFilter())) instanceof spoon.reflect.code.CtIf));
        method = foo.getMethod("ifNoBlock");
        expressions = method.getElements(new spoon.reflect.visitor.filter.LineFilter());
        org.junit.Assert.assertEquals(2, expressions.size());
        org.junit.Assert.assertNull(expressions.get(0).getParent(new spoon.reflect.visitor.filter.LineFilter()));
        org.junit.Assert.assertTrue(((expressions.get(1).getParent(new spoon.reflect.visitor.filter.LineFilter())) instanceof spoon.reflect.code.CtIf));
        method = foo.getMethod("switchBlock");
        expressions = method.getElements(new spoon.reflect.visitor.filter.LineFilter());
        org.junit.Assert.assertEquals(3, expressions.size());
        org.junit.Assert.assertNull(expressions.get(0).getParent(new spoon.reflect.visitor.filter.LineFilter()));
        org.junit.Assert.assertTrue(((expressions.get(1).getParent(new spoon.reflect.visitor.filter.LineFilter())) instanceof spoon.reflect.code.CtSwitch));
        org.junit.Assert.assertTrue(((expressions.get(2).getParent(new spoon.reflect.visitor.filter.LineFilter())) instanceof spoon.reflect.code.CtSwitch));
    }

    @org.junit.Test
    public void testFieldAccessFilter() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> foo = factory.Package().get("spoon.test.filters").getType("Foo");
        org.junit.Assert.assertEquals("Foo", foo.getSimpleName());
        java.util.List<spoon.reflect.declaration.CtNamedElement> elements = foo.getElements(new NamedElementFilter<>(spoon.reflect.declaration.CtNamedElement.class, "i"));
        org.junit.Assert.assertEquals(1, elements.size());
        spoon.reflect.reference.CtFieldReference<?> ref = ((spoon.reflect.reference.CtFieldReference<?>) (elements.get(0).getReference()));
        java.util.List<spoon.reflect.code.CtFieldAccess<?>> expressions = foo.getElements(new spoon.reflect.visitor.filter.FieldAccessFilter(ref));
        org.junit.Assert.assertEquals(2, expressions.size());
        final spoon.reflect.factory.Factory build = spoon.testing.utils.ModelUtils.build(spoon.test.filters.testclasses.FieldAccessFilterTacos.class);
        final CtType<spoon.test.filters.testclasses.FieldAccessFilterTacos> fieldAccessFilterTacos = build.Type().get(spoon.test.filters.testclasses.FieldAccessFilterTacos.class);
        try {
            java.util.List<CtField> fields = fieldAccessFilterTacos.getElements(new spoon.reflect.visitor.filter.TypeFilter<CtField>(CtField.class));
            for (CtField ctField : fields) {
                fieldAccessFilterTacos.getElements(new spoon.reflect.visitor.filter.FieldAccessFilter(ctField.getReference()));
            }
        } catch (java.lang.NullPointerException e) {
            org.junit.Assert.fail("FieldAccessFilter must not throw a NPE.");
        }
    }

    @org.junit.Test
    public void testAnnotationFilter() throws java.lang.Exception {
        spoon.reflect.declaration.CtClass<?> foo = factory.Package().get("spoon.test.filters").getType("Foo");
        org.junit.Assert.assertEquals("Foo", foo.getSimpleName());
        java.util.List<spoon.reflect.declaration.CtElement> expressions = foo.getElements(new spoon.reflect.visitor.filter.AnnotationFilter<>(java.lang.SuppressWarnings.class));
        org.junit.Assert.assertEquals(2, expressions.size());
        java.util.List<CtMethod> methods = foo.getElements(new spoon.reflect.visitor.filter.AnnotationFilter<>(CtMethod.class, java.lang.SuppressWarnings.class));
        org.junit.Assert.assertEquals(1, methods.size());
    }

    @java.lang.SuppressWarnings("rawtypes")
    @org.junit.Test
    public void filteredElementsAreOfTheCorrectType() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build("spoon.test.testclasses", "SampleClass").getFactory();
        java.lang.Class<CtMethod> filterClass = CtMethod.class;
        spoon.reflect.visitor.filter.TypeFilter<CtMethod> statementFilter = new spoon.reflect.visitor.filter.TypeFilter<CtMethod>(filterClass);
        java.util.List<CtMethod> elements = spoon.reflect.visitor.Query.getElements(factory, statementFilter);
        for (CtMethod element : elements) {
            org.junit.Assert.assertTrue(filterClass.isInstance(element));
        }
    }

    @java.lang.SuppressWarnings({ "rawtypes", "unchecked" })
    @org.junit.Test
    public void intersectionOfTwoFilters() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build("spoon.test.testclasses", "SampleClass").getFactory();
        spoon.reflect.visitor.filter.TypeFilter<CtMethod> statementFilter = new spoon.reflect.visitor.filter.TypeFilter<CtMethod>(CtMethod.class);
        spoon.reflect.visitor.filter.TypeFilter<spoon.support.reflect.declaration.CtMethodImpl> statementImplFilter = new spoon.reflect.visitor.filter.TypeFilter<spoon.support.reflect.declaration.CtMethodImpl>(spoon.support.reflect.declaration.CtMethodImpl.class);
        spoon.reflect.visitor.filter.CompositeFilter compositeFilter = new spoon.reflect.visitor.filter.CompositeFilter(spoon.reflect.visitor.filter.FilteringOperator.INTERSECTION, statementFilter, statementImplFilter);
        java.util.List<CtMethod> methodsWithInterfaceSuperclass = spoon.reflect.visitor.Query.getElements(factory, statementFilter);
        java.util.List<spoon.support.reflect.declaration.CtMethodImpl> methodWithConcreteClass = spoon.reflect.visitor.Query.getElements(factory, statementImplFilter);
        org.junit.Assert.assertEquals(methodsWithInterfaceSuperclass.size(), methodWithConcreteClass.size());
        org.junit.Assert.assertEquals(methodsWithInterfaceSuperclass, methodWithConcreteClass);
        java.util.List intersection = spoon.reflect.visitor.Query.getElements(factory, compositeFilter);
        org.junit.Assert.assertEquals(methodsWithInterfaceSuperclass.size(), intersection.size());
        org.junit.Assert.assertEquals(methodsWithInterfaceSuperclass, intersection);
    }

    @java.lang.SuppressWarnings({ "rawtypes", "unchecked" })
    @org.junit.Test
    public void unionOfTwoFilters() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build("spoon.test.testclasses", "SampleClass").getFactory();
        spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtNewClass> newClassFilter = new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtNewClass>(spoon.reflect.code.CtNewClass.class);
        spoon.reflect.visitor.filter.TypeFilter<CtMethod> methodFilter = new spoon.reflect.visitor.filter.TypeFilter<CtMethod>(CtMethod.class);
        spoon.reflect.visitor.filter.CompositeFilter compositeFilter = new spoon.reflect.visitor.filter.CompositeFilter(spoon.reflect.visitor.filter.FilteringOperator.UNION, methodFilter, newClassFilter);
        java.util.List filteredWithCompositeFilter = spoon.reflect.visitor.Query.getElements(factory, compositeFilter);
        java.util.List<CtMethod> methods = spoon.reflect.visitor.Query.getElements(factory, methodFilter);
        java.util.List<spoon.reflect.code.CtNewClass> newClasses = spoon.reflect.visitor.Query.getElements(factory, newClassFilter);
        java.util.List<spoon.reflect.declaration.CtElement> union = new java.util.ArrayList<spoon.reflect.declaration.CtElement>();
        union.addAll(methods);
        union.addAll(newClasses);
        org.junit.Assert.assertEquals(((methods.size()) + (newClasses.size())), union.size());
        org.junit.Assert.assertEquals(union.size(), filteredWithCompositeFilter.size());
        org.junit.Assert.assertTrue(filteredWithCompositeFilter.containsAll(union));
    }

    @java.lang.SuppressWarnings({ "rawtypes", "unchecked" })
    @org.junit.Test
    public void classCastExceptionIsNotThrown() throws java.lang.Exception {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.build("spoon.test.testclasses", "SampleClass").getFactory();
        NamedElementFilter<spoon.reflect.declaration.CtVariable> nameFilterA = new NamedElementFilter<>(spoon.reflect.declaration.CtVariable.class, "j");
        NamedElementFilter<spoon.reflect.declaration.CtVariable> nameFilterB = new NamedElementFilter<>(spoon.reflect.declaration.CtVariable.class, "k");
        spoon.reflect.visitor.filter.CompositeFilter compositeFilter = new spoon.reflect.visitor.filter.CompositeFilter(spoon.reflect.visitor.filter.FilteringOperator.INTERSECTION, nameFilterA, nameFilterB);
        java.util.List filteredWithCompositeFilter = spoon.reflect.visitor.Query.getElements(factory, compositeFilter);
        org.junit.Assert.assertTrue(filteredWithCompositeFilter.isEmpty());
    }

    @org.junit.Test
    public void testOverridingMethodFromAbstractClass() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput" });
        launcher.addInputResource("./src/test/java/spoon/test/filters/testclasses");
        launcher.run();
        final spoon.reflect.declaration.CtClass<spoon.test.filters.testclasses.AbstractTostada> aClass = launcher.getFactory().Class().get(spoon.test.filters.testclasses.AbstractTostada.class);
        java.util.TreeSet<CtMethod<?>> ts = new java.util.TreeSet<CtMethod<?>>(new spoon.support.comparator.DeepRepresentationComparator());
        java.util.List<CtMethod<?>> elements = spoon.reflect.visitor.Query.getElements(launcher.getFactory(), new spoon.reflect.visitor.filter.OverridingMethodFilter(aClass.getMethodsByName("prepare").get(0)));
        ts.addAll(elements);
        org.junit.Assert.assertEquals(5, elements.size());
        final java.util.List<CtMethod<?>> overridingMethods = java.util.Arrays.asList(ts.toArray(new CtMethod[0]));
        org.junit.Assert.assertEquals("spoon.test.filters.testclasses.AbstractTostada$1", overridingMethods.get(3).getParent(spoon.reflect.declaration.CtClass.class).getQualifiedName());
        org.junit.Assert.assertEquals(spoon.test.filters.testclasses.Antojito.class, overridingMethods.get(1).getParent(spoon.reflect.declaration.CtClass.class).getActualClass());
        org.junit.Assert.assertEquals(spoon.test.filters.testclasses.SubTostada.class, overridingMethods.get(2).getParent(spoon.reflect.declaration.CtClass.class).getActualClass());
        org.junit.Assert.assertEquals("spoon.test.filters.testclasses.Tostada$1", overridingMethods.get(0).getParent(spoon.reflect.declaration.CtClass.class).getQualifiedName());
        org.junit.Assert.assertEquals(spoon.test.filters.testclasses.Tostada.class, overridingMethods.get(4).getParent(spoon.reflect.declaration.CtClass.class).getActualClass());
    }

    @org.junit.Test
    public void testOverridingMethodFromSubClassOfAbstractClass() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput" });
        launcher.addInputResource("./src/test/java/spoon/test/filters/testclasses");
        launcher.run();
        final spoon.reflect.declaration.CtClass<spoon.test.filters.testclasses.Tostada> aTostada = launcher.getFactory().Class().get(spoon.test.filters.testclasses.Tostada.class);
        java.util.TreeSet<CtMethod<?>> ts = new java.util.TreeSet<CtMethod<?>>(new spoon.support.comparator.DeepRepresentationComparator());
        java.util.List<CtMethod<?>> elements = spoon.reflect.visitor.Query.getElements(launcher.getFactory(), new spoon.reflect.visitor.filter.OverridingMethodFilter(aTostada.getMethodsByName("prepare").get(0)));
        ts.addAll(elements);
        final java.util.List<CtMethod<?>> overridingMethods = java.util.Arrays.asList(ts.toArray(new CtMethod[0]));
        org.junit.Assert.assertEquals(3, overridingMethods.size());
        org.junit.Assert.assertEquals("spoon.test.filters.testclasses.AbstractTostada$1", overridingMethods.get(2).getParent(spoon.reflect.declaration.CtClass.class).getQualifiedName());
        org.junit.Assert.assertEquals(spoon.test.filters.testclasses.SubTostada.class, overridingMethods.get(1).getParent(spoon.reflect.declaration.CtClass.class).getActualClass());
        org.junit.Assert.assertEquals("spoon.test.filters.testclasses.Tostada$1", overridingMethods.get(0).getParent(spoon.reflect.declaration.CtClass.class).getQualifiedName());
        final spoon.reflect.declaration.CtClass<spoon.test.filters.testclasses.SubTostada> aSubTostada = launcher.getFactory().Class().get(spoon.test.filters.testclasses.SubTostada.class);
        org.junit.Assert.assertEquals(0, spoon.reflect.visitor.Query.getElements(launcher.getFactory(), new spoon.reflect.visitor.filter.OverridingMethodFilter(aSubTostada.getMethodsByName("prepare").get(0))).size());
    }

    @org.junit.Test
    public void testOverridingMethodFromInterface() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput" });
        launcher.addInputResource("./src/test/java/spoon/test/filters/testclasses");
        launcher.run();
        final spoon.reflect.declaration.CtInterface<spoon.test.filters.testclasses.ITostada> aITostada = launcher.getFactory().Interface().get(spoon.test.filters.testclasses.ITostada.class);
        java.util.TreeSet<CtMethod<?>> ts = new java.util.TreeSet<CtMethod<?>>(new spoon.support.comparator.DeepRepresentationComparator());
        java.util.List<CtMethod<?>> elements = spoon.reflect.visitor.Query.getElements(launcher.getFactory(), new spoon.reflect.visitor.filter.OverridingMethodFilter(aITostada.getMethodsByName("make").get(0)));
        ts.addAll(elements);
        final java.util.List<CtMethod<?>> overridingMethods = java.util.Arrays.asList(ts.toArray(new CtMethod[0]));
        org.junit.Assert.assertEquals(4, overridingMethods.size());
        org.junit.Assert.assertEquals(spoon.test.filters.testclasses.AbstractTostada.class, overridingMethods.get(3).getParent(CtType.class).getParent(spoon.reflect.declaration.CtClass.class).getActualClass());
        org.junit.Assert.assertEquals("spoon.test.filters.testclasses.AbstractTostada", overridingMethods.get(1).getParent(spoon.reflect.declaration.CtClass.class).getQualifiedName());
        org.junit.Assert.assertEquals(spoon.test.filters.testclasses.Tostada.class, overridingMethods.get(0).getParent(spoon.reflect.declaration.CtClass.class).getActualClass());
        org.junit.Assert.assertEquals(spoon.test.filters.testclasses.Tacos.class, overridingMethods.get(2).getParent(spoon.reflect.declaration.CtClass.class).getActualClass());
    }

    @org.junit.Test
    public void testOverridingMethodFromSubClassOfInterface() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput" });
        launcher.addInputResource("./src/test/java/spoon/test/filters/testclasses");
        launcher.run();
        final spoon.reflect.declaration.CtClass<spoon.test.filters.testclasses.AbstractTostada> anAbstractTostada = launcher.getFactory().Class().get(spoon.test.filters.testclasses.AbstractTostada.class);
        java.util.List<CtMethod<?>> overridingMethods = spoon.reflect.visitor.Query.getElements(launcher.getFactory(), new spoon.reflect.visitor.filter.OverridingMethodFilter(anAbstractTostada.getMethodsByName("make").get(0)));
        org.junit.Assert.assertEquals(2, overridingMethods.size());
        org.junit.Assert.assertEquals("spoon.test.filters.testclasses.AbstractTostada$1", overridingMethods.get(0).getParent(spoon.reflect.declaration.CtClass.class).getQualifiedName());
        org.junit.Assert.assertEquals(spoon.test.filters.testclasses.Tostada.class, overridingMethods.get(1).getParent(spoon.reflect.declaration.CtClass.class).getActualClass());
        final spoon.reflect.declaration.CtClass<spoon.test.filters.testclasses.Tostada> aTostada = launcher.getFactory().Class().get(spoon.test.filters.testclasses.Tostada.class);
        overridingMethods = spoon.reflect.visitor.Query.getElements(launcher.getFactory(), new spoon.reflect.visitor.filter.OverridingMethodFilter(aTostada.getMethodsByName("make").get(0)));
        org.junit.Assert.assertEquals(1, overridingMethods.size());
        org.junit.Assert.assertEquals("spoon.test.filters.testclasses.AbstractTostada$1", overridingMethods.get(0).getParent(spoon.reflect.declaration.CtClass.class).getQualifiedName());
    }

    @org.junit.Test
    public void testOverriddenMethodFromAbstractClass() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput" });
        launcher.addInputResource("./src/test/java/spoon/test/filters/testclasses");
        launcher.run();
        final spoon.reflect.declaration.CtClass<spoon.test.filters.testclasses.AbstractTostada> aClass = launcher.getFactory().Class().get(spoon.test.filters.testclasses.AbstractTostada.class);
        org.junit.Assert.assertEquals(0, spoon.reflect.visitor.Query.getElements(launcher.getFactory(), new spoon.reflect.visitor.filter.OverriddenMethodFilter(aClass.getMethodsByName("prepare").get(0))).size());
        org.junit.Assert.assertEquals(0, aClass.getMethodsByName("prepare").get(0).map(new spoon.reflect.visitor.filter.OverriddenMethodQuery()).list().size());
    }

    @org.junit.Test
    public void testOverriddenMethodsFromSubClassOfAbstractClass() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput" });
        launcher.addInputResource("./src/test/java/spoon/test/filters/testclasses");
        launcher.run();
        final spoon.reflect.declaration.CtClass<spoon.test.filters.testclasses.Tostada> aTostada = launcher.getFactory().Class().get(spoon.test.filters.testclasses.Tostada.class);
        final java.util.List<CtMethod<?>> overridenMethods = spoon.reflect.visitor.Query.getElements(launcher.getFactory(), new spoon.reflect.visitor.filter.OverriddenMethodFilter(aTostada.getMethodsByName("prepare").get(0)));
        org.junit.Assert.assertEquals(1, overridenMethods.size());
        org.junit.Assert.assertEquals(spoon.test.filters.testclasses.AbstractTostada.class, overridenMethods.get(0).getParent(spoon.reflect.declaration.CtClass.class).getActualClass());
        final spoon.reflect.declaration.CtClass<spoon.test.filters.testclasses.SubTostada> aSubTostada = launcher.getFactory().Class().get(spoon.test.filters.testclasses.SubTostada.class);
        final java.util.List<CtMethod<?>> overridenMethodsFromSub = spoon.reflect.visitor.Query.getElements(launcher.getFactory(), new spoon.reflect.visitor.filter.OverriddenMethodFilter(aSubTostada.getMethodsByName("prepare").get(0)));
        org.junit.Assert.assertEquals(2, overridenMethodsFromSub.size());
        org.junit.Assert.assertEquals(spoon.test.filters.testclasses.AbstractTostada.class, overridenMethodsFromSub.get(0).getParent(spoon.reflect.declaration.CtClass.class).getActualClass());
        org.junit.Assert.assertEquals(spoon.test.filters.testclasses.Tostada.class, overridenMethodsFromSub.get(1).getParent(spoon.reflect.declaration.CtClass.class).getActualClass());
    }

    @org.junit.Test
    public void testgetTopDefinitions() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput" });
        launcher.addInputResource("./src/test/java/spoon/test/filters/testclasses");
        launcher.run();
        final spoon.reflect.declaration.CtClass<spoon.test.filters.testclasses.Tostada> aTostada = launcher.getFactory().Class().get(spoon.test.filters.testclasses.Tostada.class);
        java.util.List<CtMethod<?>> methods;
        methods = orderByName(aTostada.getMethodsByName("make").get(0).getTopDefinitions());
        org.junit.Assert.assertEquals(2, methods.size());
        org.junit.Assert.assertEquals("AbstractTostada", methods.get(0).getDeclaringType().getSimpleName());
        org.junit.Assert.assertEquals("ITostada", methods.get(1).getDeclaringType().getSimpleName());
        methods = orderByName(aTostada.getMethodsByName("prepare").get(0).getTopDefinitions());
        org.junit.Assert.assertEquals(1, methods.size());
        org.junit.Assert.assertEquals("AbstractTostada", methods.get(0).getDeclaringType().getSimpleName());
        methods = orderByName(aTostada.getMethodsByName("toString").get(0).getTopDefinitions());
        org.junit.Assert.assertEquals(1, methods.size());
        org.junit.Assert.assertEquals("Object", methods.get(0).getDeclaringType().getSimpleName());
        methods = orderByName(aTostada.getMethodsByName("honey").get(0).getTopDefinitions());
        org.junit.Assert.assertEquals(2, methods.size());
        org.junit.Assert.assertEquals("AbstractTostada", methods.get(0).getDeclaringType().getSimpleName());
        org.junit.Assert.assertEquals("Honey", methods.get(1).getDeclaringType().getSimpleName());
        methods = orderByName(aTostada.getMethodsByName("foo").get(0).getTopDefinitions());
        org.junit.Assert.assertEquals(0, methods.size());
    }

    private java.util.List<CtMethod<?>> orderByName(java.util.Collection<CtMethod<?>> meths) {
        java.util.List<CtMethod<?>> ordered = new java.util.ArrayList<>(meths);
        ordered.sort(new java.util.Comparator<CtMethod<?>>() {
            @java.lang.Override
            public int compare(CtMethod<?> o1, CtMethod<?> o2) {
                return o1.getParent(CtType.class).getQualifiedName().compareTo(o2.getParent(CtType.class).getQualifiedName());
            }
        });
        return ordered;
    }

    @org.junit.Test
    public void testOverriddenMethodFromInterface() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput" });
        launcher.addInputResource("./src/test/java/spoon/test/filters/testclasses");
        launcher.run();
        final spoon.reflect.declaration.CtInterface<spoon.test.filters.testclasses.ITostada> aITostada = launcher.getFactory().Interface().get(spoon.test.filters.testclasses.ITostada.class);
        spoon.reflect.visitor.filter.OverriddenMethodFilter filter = new spoon.reflect.visitor.filter.OverriddenMethodFilter(aITostada.getMethodsByName("make").get(0));
        java.util.List<CtMethod<?>> overridingMethods = spoon.reflect.visitor.Query.getElements(launcher.getFactory(), filter);
        org.junit.Assert.assertEquals(0, overridingMethods.size());
        java.util.List<CtMethod> overridingMethods2 = aITostada.getMethodsByName("make").get(0).map(new spoon.reflect.visitor.filter.OverriddenMethodQuery()).list(CtMethod.class);
        org.junit.Assert.assertEquals(0, overridingMethods2.size());
    }

    @org.junit.Test
    public void testOverriddenMethodFromSubClassOfInterface() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput" });
        launcher.addInputResource("./src/test/java/spoon/test/filters/testclasses");
        launcher.run();
        final spoon.reflect.declaration.CtClass<spoon.test.filters.testclasses.AbstractTostada> anAbstractTostada = launcher.getFactory().Class().get(spoon.test.filters.testclasses.AbstractTostada.class);
        final java.util.List<CtMethod<?>> overriddenMethods = spoon.reflect.visitor.Query.getElements(launcher.getFactory(), new spoon.reflect.visitor.filter.OverriddenMethodFilter(anAbstractTostada.getMethodsByName("make").get(0)));
        org.junit.Assert.assertEquals(1, overriddenMethods.size());
        org.junit.Assert.assertEquals(spoon.test.filters.testclasses.ITostada.class, overriddenMethods.get(0).getParent(spoon.reflect.declaration.CtInterface.class).getActualClass());
        final spoon.reflect.declaration.CtClass<spoon.test.filters.testclasses.Tostada> aTostada = launcher.getFactory().Class().get(spoon.test.filters.testclasses.Tostada.class);
        spoon.reflect.visitor.filter.OverriddenMethodFilter filter = new spoon.reflect.visitor.filter.OverriddenMethodFilter(aTostada.getMethodsByName("make").get(0));
        final java.util.List<CtMethod<?>> overriddenMethodsFromSub = spoon.reflect.visitor.Query.getElements(launcher.getFactory(), filter);
        org.junit.Assert.assertEquals(2, overriddenMethodsFromSub.size());
        org.junit.Assert.assertEquals(spoon.test.filters.testclasses.AbstractTostada.class, overriddenMethodsFromSub.get(0).getParent(CtType.class).getActualClass());
        org.junit.Assert.assertEquals(spoon.test.filters.testclasses.ITostada.class, overriddenMethodsFromSub.get(1).getParent(CtType.class).getActualClass());
    }

    @org.junit.Test
    public void testInvocationFilterWithExecutableInLibrary() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput" });
        launcher.addInputResource("./src/test/java/spoon/test/filters/testclasses");
        launcher.run();
        final spoon.reflect.declaration.CtClass<spoon.test.filters.testclasses.Tacos> aTacos = launcher.getFactory().Class().get(spoon.test.filters.testclasses.Tacos.class);
        final spoon.reflect.code.CtInvocation<?> invSize = aTacos.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtInvocation<?>>(spoon.reflect.code.CtInvocation.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtInvocation<?> element) {
                if ((element.getExecutable()) == null) {
                    return false;
                }
                return ("size".equals(element.getExecutable().getSimpleName())) && (super.matches(element));
            }
        }).get(0);
        final java.util.List<spoon.reflect.code.CtInvocation<?>> invocations = aTacos.getElements(new spoon.reflect.visitor.filter.InvocationFilter(invSize.getExecutable()));
        org.junit.Assert.assertEquals(1, invocations.size());
        final spoon.reflect.code.CtInvocation<?> expectedInv = invocations.get(0);
        org.junit.Assert.assertNotNull(expectedInv);
        final spoon.reflect.reference.CtExecutableReference<?> expectedExecutable = expectedInv.getExecutable();
        org.junit.Assert.assertNotNull(expectedExecutable);
        org.junit.Assert.assertEquals("size", expectedExecutable.getSimpleName());
        org.junit.Assert.assertNull(expectedExecutable.getDeclaration());
        spoon.reflect.declaration.CtExecutable<?> exec = expectedExecutable.getExecutableDeclaration();
        org.junit.Assert.assertEquals("size", exec.getSimpleName());
        org.junit.Assert.assertEquals("ArrayList", ((spoon.reflect.declaration.CtClass) (exec.getParent())).getSimpleName());
        final spoon.reflect.declaration.CtExecutable<?> declaration = expectedExecutable.getExecutableDeclaration();
        org.junit.Assert.assertNotNull(declaration);
        org.junit.Assert.assertEquals("size", declaration.getSimpleName());
    }

    @org.junit.Test
    public void testReflectionBasedTypeFilter() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput" });
        launcher.addInputResource("./src/test/java/spoon/test/filters/testclasses");
        launcher.run();
        java.util.List<spoon.reflect.declaration.CtClass<?>> allClasses = launcher.getFactory().Package().getRootPackage().getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.declaration.CtClass<?>>(spoon.reflect.declaration.CtClass.class));
        org.junit.Assert.assertTrue(((allClasses.size()) > 0));
        allClasses.forEach(( result) -> {
            org.junit.Assert.assertTrue((result instanceof spoon.reflect.declaration.CtClass));
        });
        java.util.List<spoon.reflect.declaration.CtClass<?>> allClasses2 = launcher.getFactory().Package().getRootPackage().getElements(new spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtClass<?>>() {
            @java.lang.Override
            public boolean matches(spoon.reflect.declaration.CtClass<?> element) {
                return true;
            }
        });
        org.junit.Assert.assertArrayEquals(allClasses.toArray(), allClasses2.toArray());
        java.util.List<spoon.reflect.declaration.CtClass<?>> allClasses3 = launcher.getFactory().Package().getRootPackage().getElements((spoon.reflect.declaration.CtClass<?> element) -> true);
        org.junit.Assert.assertArrayEquals(allClasses.toArray(), allClasses3.toArray());
        final spoon.reflect.declaration.CtClass<spoon.test.filters.testclasses.Tacos> aTacos = launcher.getFactory().Class().get(spoon.test.filters.testclasses.Tacos.class);
        final spoon.reflect.code.CtInvocation<?> invSize = aTacos.getElements(new spoon.reflect.visitor.filter.AbstractFilter<spoon.reflect.code.CtInvocation<?>>() {
            @java.lang.Override
            public boolean matches(spoon.reflect.code.CtInvocation<?> element) {
                if ((element.getExecutable()) == null) {
                    return false;
                }
                return ("size".equals(element.getExecutable().getSimpleName())) && (super.matches(element));
            }
        }).get(0);
        org.junit.Assert.assertNotNull(invSize);
    }

    @org.junit.Test
    public void testQueryStepScannWithConsumer() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput" });
        launcher.addInputResource("./src/test/java/spoon/test/filters/testclasses");
        launcher.run();
        class Context {
            int counter = 0;
        }
        Context context = new Context();
        spoon.reflect.visitor.chain.CtQuery l_qv = launcher.getFactory().getModel().filterChildren(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.declaration.CtClass.class));
        org.junit.Assert.assertEquals(0, context.counter);
        l_qv.forEach(( cls) -> {
            org.junit.Assert.assertTrue((cls instanceof spoon.reflect.declaration.CtClass));
            (context.counter)++;
        });
        org.junit.Assert.assertTrue(((context.counter) > 0));
    }

    @org.junit.Test
    public void testQueryBuilderWithFilterChain() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput" });
        launcher.addInputResource("./src/test/java/spoon/test/filters/testclasses");
        launcher.run();
        class Context {
            CtMethod<?> method;

            int count = 0;
        }
        Context context = new Context();
        spoon.reflect.visitor.chain.CtQuery q = launcher.getFactory().Package().getRootPackage().filterChildren(new spoon.reflect.visitor.filter.TypeFilter<CtMethod<?>>(CtMethod.class)).map((CtMethod<?> method) -> {
            context.method = method;
            return method;
        }).map(new spoon.reflect.visitor.filter.OverriddenMethodQuery());
        q.forEach((CtMethod<?> method) -> {
            org.junit.Assert.assertTrue(context.method.getReference().isOverriding(method.getReference()));
            org.junit.Assert.assertTrue(context.method.isOverriding(method));
            (context.count)++;
        });
        org.junit.Assert.assertTrue(((context.count) > 0));
    }

    @org.junit.Test
    public void testFilterQueryStep() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput", "--level", "info" });
        launcher.addInputResource("./src/test/java/spoon/test/filters/testclasses");
        launcher.run();
        java.util.List<spoon.reflect.declaration.CtElement> realList = launcher.getFactory().Package().getRootPackage().filterChildren(( e) -> {
            return true;
        }).select(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.declaration.CtClass.class)).list();
        java.util.List<spoon.reflect.declaration.CtElement> expectedList = launcher.getFactory().Package().getRootPackage().filterChildren(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.declaration.CtClass.class)).list();
        org.junit.Assert.assertArrayEquals(expectedList.toArray(), realList.toArray());
        org.junit.Assert.assertTrue(((expectedList.size()) > 0));
    }

    @org.junit.Test
    public void testFilterChildrenWithoutFilterQueryStep() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput", "--level", "info" });
        launcher.addInputResource("./src/test/java/spoon/test/filters/testclasses");
        launcher.run();
        java.util.List<spoon.reflect.declaration.CtElement> list = launcher.getFactory().Package().getRootPackage().filterChildren(null).list();
        java.util.Iterator<spoon.reflect.declaration.CtElement> iter = list.iterator();
        launcher.getFactory().Package().getRootPackage().filterChildren(( e) -> {
            return true;
        }).forEach(( real) -> {
            spoon.reflect.declaration.CtElement expected = iter.next();
            if (real != expected) {
                org.junit.Assert.assertEquals(expected, real);
            }
        });
        org.junit.Assert.assertTrue(((list.size()) > 0));
        org.junit.Assert.assertTrue(((iter.hasNext()) == false));
    }

    @org.junit.Test
    public void testFunctionQueryStep() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput", "--level", "info" });
        launcher.addInputResource("./src/test/java/spoon/test/filters/testclasses");
        launcher.run();
        class Context {
            int count = 0;
        }
        Context context = new Context();
        spoon.reflect.visitor.chain.CtQuery query = launcher.getFactory().Package().getRootPackage().filterChildren((spoon.reflect.declaration.CtClass<?> c) -> {
            return true;
        }).name("filter CtClass only").map((spoon.reflect.declaration.CtClass<?> c) -> c.getSuperInterfaces()).name("super interfaces").map((spoon.reflect.reference.CtTypeReference<?> iface) -> iface.getTypeDeclaration()).map((CtType<?> iface) -> iface.getAllMethods()).name("allMethods if interface").map((CtMethod<?> method) -> method.getSimpleName().equals("make")).map((CtMethod<?> m) -> m.getType()).map((spoon.reflect.reference.CtTypeReference<?> t) -> t.getTypeDeclaration());
        ((spoon.reflect.visitor.chain.CtQueryImpl) (query)).logging(true);
        query.forEach((spoon.reflect.declaration.CtInterface<?> c) -> {
            org.junit.Assert.assertEquals("ITostada", c.getSimpleName());
            (context.count)++;
        });
        org.junit.Assert.assertTrue(((context.count) > 0));
    }

    @org.junit.Test
    public void testInvalidQueryStep() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput", "--level", "info" });
        launcher.addInputResource("./src/test/java/spoon/test/filters/testclasses");
        launcher.run();
        try {
            launcher.getFactory().Package().getRootPackage().filterChildren((spoon.reflect.declaration.CtClass<?> c) -> {
                return true;
            }).name("step1").map((CtMethod<?> m) -> m).name("invalidStep2").forEach((spoon.reflect.declaration.CtInterface<?> c) -> {
                org.junit.Assert.fail();
            });
            org.junit.Assert.fail();
        } catch (java.lang.ClassCastException e) {
            org.junit.Assert.assertTrue(((e.getMessage().indexOf("spoon.support.reflect.declaration.CtClassImpl cannot be cast to spoon.reflect.declaration.CtMethod")) >= 0));
        }
    }

    @org.junit.Test
    public void testInvalidQueryStepFailurePolicyIgnore() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput", "--level", "info" });
        launcher.addInputResource("./src/test/java/spoon/test/filters/testclasses");
        launcher.run();
        class Context {
            int count = 0;
        }
        Context context = new Context();
        launcher.getFactory().Package().getRootPackage().filterChildren((spoon.reflect.declaration.CtElement c) -> {
            return true;
        }).name("step1").map((CtMethod<?> m) -> m).name("invalidStep2").map(( o) -> o).name("step3").failurePolicy(spoon.reflect.visitor.chain.QueryFailurePolicy.IGNORE).forEach((spoon.reflect.declaration.CtElement c) -> {
            org.junit.Assert.assertTrue((c instanceof CtMethod));
            (context.count)++;
        });
        org.junit.Assert.assertTrue(((context.count) > 0));
    }

    @org.junit.Test
    public void testElementMapFunction() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput", "--level", "info" });
        launcher.addInputResource("./src/test/java/spoon/test/filters/testclasses");
        launcher.run();
        spoon.reflect.declaration.CtClass<?> cls = launcher.getFactory().Class().get(spoon.test.filters.testclasses.Tacos.class);
        cls.map((spoon.reflect.declaration.CtClass<?> c) -> c.getParent()).forEach((spoon.reflect.declaration.CtElement e) -> {
            org.junit.Assert.assertEquals(cls.getParent(), e);
        });
        org.junit.Assert.assertEquals(cls.getParent(), cls.map((spoon.reflect.declaration.CtClass<?> c) -> c.getParent()).list().get(0));
    }

    @org.junit.Test
    public void testElementMapFunctionOtherContracts() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        spoon.reflect.visitor.chain.CtQuery q = launcher.getFactory().Query().createQuery().map((java.lang.String s) -> new java.lang.String[]{ "a", null, s });
        java.util.List<java.lang.String> list = q.setInput(null).list();
        org.junit.Assert.assertEquals(0, list.size());
        list = q.setInput("c").list();
        org.junit.Assert.assertEquals(2, list.size());
        org.junit.Assert.assertEquals("a", list.get(0));
        org.junit.Assert.assertEquals("c", list.get(1));
        list = q.list();
        org.junit.Assert.assertEquals(2, list.size());
        org.junit.Assert.assertEquals("a", list.get(0));
        org.junit.Assert.assertEquals("c", list.get(1));
        spoon.reflect.visitor.chain.CtQuery q2 = launcher.getFactory().Query().createQuery().map((java.lang.String s) -> {
            throw new java.lang.AssertionError();
        });
        org.junit.Assert.assertEquals(0, q2.setInput(null).list().size());
    }

    @org.junit.Test
    public void testElementMapFunctionNull() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        spoon.reflect.visitor.chain.CtQuery q = launcher.getFactory().Query().createQuery().map((java.lang.String s) -> null);
        java.util.List<java.lang.String> list = q.setInput("c").list();
        org.junit.Assert.assertEquals(0, list.size());
    }

    @org.junit.Test
    public void testReuseOfQuery() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput", "--level", "info" });
        launcher.addInputResource("./src/test/java/spoon/test/filters/testclasses");
        launcher.run();
        spoon.reflect.declaration.CtClass<?> cls = launcher.getFactory().Class().get(spoon.test.filters.testclasses.Tacos.class);
        spoon.reflect.declaration.CtClass<?> cls2 = launcher.getFactory().Class().get(spoon.test.filters.testclasses.Tostada.class);
        spoon.reflect.visitor.chain.CtQuery q = cls.map((spoon.reflect.declaration.CtClass c) -> c.getSimpleName());
        org.junit.Assert.assertEquals(cls.getSimpleName(), q.list().get(0));
        org.junit.Assert.assertEquals(1, ((spoon.reflect.visitor.chain.CtQueryImpl) (q)).getInputs().size());
        org.junit.Assert.assertSame(cls, ((spoon.reflect.visitor.chain.CtQueryImpl) (q)).getInputs().get(0));
        q.setInput(cls2);
        org.junit.Assert.assertEquals(cls2.getSimpleName(), q.list().get(0));
        org.junit.Assert.assertEquals(1, ((spoon.reflect.visitor.chain.CtQueryImpl) (q)).getInputs().size());
        org.junit.Assert.assertSame(cls2, ((spoon.reflect.visitor.chain.CtQueryImpl) (q)).getInputs().get(0));
    }

    @org.junit.Test
    public void testReuseOfBaseQuery() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput", "--level", "info" });
        launcher.addInputResource("./src/test/java/spoon/test/filters/testclasses");
        launcher.run();
        spoon.reflect.declaration.CtClass<?> cls = launcher.getFactory().Class().get(spoon.test.filters.testclasses.Tacos.class);
        spoon.reflect.declaration.CtClass<?> cls2 = launcher.getFactory().Class().get(spoon.test.filters.testclasses.Tostada.class);
        spoon.reflect.visitor.chain.CtQuery q = launcher.getFactory().Query().createQuery().map((spoon.reflect.declaration.CtClass c) -> c.getSimpleName());
        org.junit.Assert.assertEquals("Tacos", q.setInput(cls).list().get(0));
        org.junit.Assert.assertEquals("Tostada", q.setInput(cls2).list().get(0));
    }

    @org.junit.Test
    public void testQueryWithOptionalNumberOfInputs() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput", "--level", "info" });
        launcher.addInputResource("./src/test/java/spoon/test/filters/testclasses");
        launcher.run();
        spoon.reflect.declaration.CtClass<?> cls = launcher.getFactory().Class().get(spoon.test.filters.testclasses.Tacos.class);
        spoon.reflect.declaration.CtClass<?> cls2 = launcher.getFactory().Class().get(spoon.test.filters.testclasses.Tostada.class);
        spoon.reflect.declaration.CtClass<?> cls3 = launcher.getFactory().Class().get(spoon.test.filters.testclasses.Antojito.class);
        spoon.reflect.visitor.chain.CtQuery q1 = launcher.getFactory().Query().createQuery(cls, cls2).map((spoon.reflect.declaration.CtClass c) -> c.getSimpleName());
        org.junit.Assert.assertArrayEquals(new java.lang.String[]{ "Tacos", "Tostada" }, q1.list().toArray());
        spoon.reflect.visitor.chain.CtQuery q1b = launcher.getFactory().Query().createQuery(java.util.Arrays.asList(cls, cls2)).map((spoon.reflect.declaration.CtClass c) -> c.getSimpleName());
        org.junit.Assert.assertArrayEquals(new java.lang.String[]{ "Tacos", "Tostada" }, q1b.list().toArray());
        spoon.reflect.visitor.chain.CtQuery q2 = launcher.getFactory().Query().createQuery(cls, cls3).map((spoon.reflect.declaration.CtClass c) -> c.getSimpleName());
        org.junit.Assert.assertArrayEquals(new java.lang.String[]{ "Tacos", "Antojito" }, q2.list().toArray());
        spoon.reflect.visitor.chain.CtQuery q2b = launcher.getFactory().Query().createQuery(java.util.Arrays.asList(cls, cls3)).map((spoon.reflect.declaration.CtClass c) -> c.getSimpleName());
        org.junit.Assert.assertArrayEquals(new java.lang.String[]{ "Tacos", "Antojito" }, q2b.list().toArray());
        spoon.reflect.visitor.chain.CtQuery q3 = launcher.getFactory().Query().createQuery(cls, cls2, cls3).map((spoon.reflect.declaration.CtClass c) -> c.getSimpleName());
        org.junit.Assert.assertArrayEquals(new java.lang.String[]{ "Tacos", "Tostada", "Antojito" }, q3.list().toArray());
        spoon.reflect.visitor.chain.CtQuery q3b = launcher.getFactory().Query().createQuery(java.util.Arrays.asList(cls, cls2, cls3)).map((spoon.reflect.declaration.CtClass c) -> c.getSimpleName());
        org.junit.Assert.assertArrayEquals(new java.lang.String[]{ "Tacos", "Tostada", "Antojito" }, q3b.list().toArray());
    }

    @org.junit.Test
    public void testElementMapConsumableFunction() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput", "--level", "info" });
        launcher.addInputResource("./src/test/java/spoon/test/filters/testclasses");
        launcher.run();
        spoon.reflect.declaration.CtClass<?> cls = launcher.getFactory().Class().get(spoon.test.filters.testclasses.Tacos.class);
        class aFunction implements spoon.reflect.visitor.chain.CtConsumableFunction<spoon.reflect.declaration.CtClass> {
            @java.lang.Override
            public void apply(spoon.reflect.declaration.CtClass c, spoon.reflect.visitor.chain.CtConsumer out) {
                out.accept(c.getParent());
            }
        }
        org.junit.Assert.assertEquals(cls.getParent(), cls.map(new aFunction()).list().get(0));
        org.junit.Assert.assertEquals(cls.getParent(), cls.map((spoon.reflect.declaration.CtClass<?> c,spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> out) -> out.accept(c.getParent())).list().get(0));
    }

    @org.junit.Test
    public void testQueryInQuery() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput", "--level", "info" });
        launcher.addInputResource("./src/test/java/spoon/test/filters/testclasses");
        launcher.run();
        class Context {
            int count = 0;
        }
        Context context = new Context();
        spoon.reflect.declaration.CtClass<?> cls = launcher.getFactory().Class().get(spoon.test.filters.testclasses.Tacos.class);
        spoon.reflect.visitor.chain.CtQuery allChildPublicClasses = launcher.getFactory().Query().createQuery().filterChildren((spoon.reflect.declaration.CtClass clazz) -> clazz.hasModifier(spoon.reflect.declaration.ModifierKind.PUBLIC));
        spoon.reflect.visitor.chain.CtQuery q = launcher.getFactory().Package().getRootPackage().map((spoon.reflect.declaration.CtElement in) -> allChildPublicClasses.setInput(in).list());
        q.forEach((spoon.reflect.declaration.CtElement clazz) -> {
            (context.count)++;
            org.junit.Assert.assertTrue((clazz instanceof spoon.reflect.declaration.CtClass));
            org.junit.Assert.assertTrue(((spoon.reflect.declaration.CtClass<?>) (clazz)).hasModifier(spoon.reflect.declaration.ModifierKind.PUBLIC));
        });
        org.junit.Assert.assertEquals(6, context.count);
        context.count = 0;
        spoon.reflect.visitor.chain.CtQuery q2 = launcher.getFactory().Package().getRootPackage().map((spoon.reflect.declaration.CtElement in,spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> out) -> allChildPublicClasses.setInput(in).forEach(out));
        q2.forEach((spoon.reflect.declaration.CtElement clazz) -> {
            (context.count)++;
            org.junit.Assert.assertTrue((clazz instanceof spoon.reflect.declaration.CtClass));
            org.junit.Assert.assertTrue(((spoon.reflect.declaration.CtClass<?>) (clazz)).hasModifier(spoon.reflect.declaration.ModifierKind.PUBLIC));
        });
        org.junit.Assert.assertEquals(6, context.count);
        context.count = 0;
        spoon.reflect.visitor.chain.CtQuery q3 = launcher.getFactory().Package().getRootPackage().map(( in, out) -> ((spoon.reflect.visitor.chain.CtQueryImpl) (allChildPublicClasses)).evaluate(in, out));
        q3.forEach((spoon.reflect.declaration.CtElement clazz) -> {
            (context.count)++;
            org.junit.Assert.assertTrue((clazz instanceof spoon.reflect.declaration.CtClass));
            org.junit.Assert.assertTrue(((spoon.reflect.declaration.CtClass<?>) (clazz)).hasModifier(spoon.reflect.declaration.ModifierKind.PUBLIC));
        });
        org.junit.Assert.assertEquals(6, context.count);
    }

    @org.junit.Test
    public void testEmptyQuery() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        org.junit.Assert.assertEquals(0, launcher.getFactory().createQuery().list().size());
        org.junit.Assert.assertEquals(0, launcher.getFactory().createQuery(((java.lang.Object) (null))).list().size());
        launcher.getFactory().createQuery().forEach(( x) -> org.junit.Assert.fail());
        launcher.getFactory().createQuery(((java.lang.Object) (null))).forEach(( x) -> org.junit.Assert.fail());
        org.junit.Assert.assertEquals(0, launcher.getFactory().createQuery().map(( x) -> {
            org.junit.Assert.fail();
            return true;
        }).list().size());
        org.junit.Assert.assertEquals(0, launcher.getFactory().createQuery(((java.lang.Object) (null))).map(( x) -> {
            org.junit.Assert.fail();
            return true;
        }).list().size());
        org.junit.Assert.assertEquals(0, launcher.getFactory().createQuery().filterChildren(( x) -> {
            org.junit.Assert.fail();
            return true;
        }).list().size());
        org.junit.Assert.assertEquals(0, launcher.getFactory().createQuery(((java.lang.Object) (null))).filterChildren(( x) -> {
            org.junit.Assert.fail();
            return true;
        }).list().size());
    }

    @org.junit.Test
    public void testBoundQuery() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        java.util.List<java.lang.String> list = launcher.getFactory().createQuery("x").list();
        org.junit.Assert.assertEquals(1, list.size());
        org.junit.Assert.assertEquals("x", list.get(0));
    }

    @org.junit.Test
    public void testClassCastExceptionOnForEach() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput", "--level", "info" });
        launcher.addInputResource("./src/test/java/spoon/test/filters/testclasses");
        launcher.run();
        class Context {
            int count = 0;
        }
        {
            Context context = new Context();
            launcher.getFactory().Package().getRootPackage().filterChildren(null).forEach((CtType t) -> {
                (context.count)++;
            });
            org.junit.Assert.assertTrue(((context.count) > 0));
        }
        {
            Context context = new Context();
            try {
                launcher.getFactory().Package().getRootPackage().filterChildren(null).forEach((CtType t) -> {
                    (context.count)++;
                    throw new java.lang.ClassCastException("TEST");
                });
                org.junit.Assert.fail("It must fail, because body of forEach should be called and thrown CCE");
            } catch (java.lang.ClassCastException e) {
                org.junit.Assert.assertTrue(((context.count) > 0));
                org.junit.Assert.assertEquals("TEST", e.getMessage());
            }
        }
        {
            Context context = new Context();
            try {
                launcher.getFactory().Package().getRootPackage().filterChildren(null).forEach(new spoon.reflect.visitor.chain.CtConsumer<CtType>() {
                    @java.lang.Override
                    public void accept(CtType t) {
                        (context.count)++;
                        throw new java.lang.ClassCastException("TEST");
                    }
                });
                org.junit.Assert.fail("It must fail, because body of forEach should be called and thrown CCE");
            } catch (java.lang.ClassCastException e) {
                org.junit.Assert.assertTrue(((context.count) > 0));
                org.junit.Assert.assertEquals("TEST", e.getMessage());
            }
        }
        {
            Context context = new Context();
            try {
                launcher.getFactory().Package().getRootPackage().filterChildren(null).select(new spoon.reflect.visitor.Filter<CtType>() {
                    @java.lang.Override
                    public boolean matches(CtType element) {
                        (context.count)++;
                        throw new java.lang.ClassCastException("TEST");
                    }
                }).list();
                org.junit.Assert.fail("It must fail, because body of select thrown CCE");
            } catch (java.lang.ClassCastException e) {
                org.junit.Assert.assertTrue(((context.count) > 0));
                org.junit.Assert.assertEquals("TEST", e.getMessage());
            }
        }
        {
            Context context = new Context();
            try {
                launcher.getFactory().Package().getRootPackage().filterChildren(null).select((CtType element) -> {
                    (context.count)++;
                    throw new java.lang.ClassCastException("TEST");
                }).list();
                org.junit.Assert.fail("It must fail, because body of select thrown CCE");
            } catch (java.lang.ClassCastException e) {
                org.junit.Assert.assertTrue(((context.count) > 0));
                org.junit.Assert.assertEquals("TEST", e.getMessage());
            }
        }
        {
            Context context = new Context();
            try {
                launcher.getFactory().Package().getRootPackage().filterChildren(null).map(new spoon.reflect.visitor.chain.CtFunction<CtType, java.lang.Object>() {
                    @java.lang.Override
                    public java.lang.Object apply(CtType input) {
                        (context.count)++;
                        throw new java.lang.ClassCastException("TEST");
                    }
                }).failurePolicy(spoon.reflect.visitor.chain.QueryFailurePolicy.IGNORE).list();
                org.junit.Assert.fail("It must fail, because body of map thrown CCE");
            } catch (java.lang.ClassCastException e) {
                org.junit.Assert.assertTrue(((context.count) > 0));
                org.junit.Assert.assertEquals("TEST", e.getMessage());
            }
        }
        {
            Context context = new Context();
            try {
                launcher.getFactory().Package().getRootPackage().filterChildren(null).map((CtType input) -> {
                    (context.count)++;
                    throw new java.lang.ClassCastException("TEST");
                }).failurePolicy(spoon.reflect.visitor.chain.QueryFailurePolicy.IGNORE).list();
                org.junit.Assert.fail("It must fail, because body of map thrown CCE");
            } catch (java.lang.ClassCastException e) {
                org.junit.Assert.assertTrue(((context.count) > 0));
                org.junit.Assert.assertEquals("TEST", e.getMessage());
            }
        }
        {
            Context context = new Context();
            try {
                launcher.getFactory().Package().getRootPackage().filterChildren(null).map(new spoon.reflect.visitor.chain.CtConsumableFunction<CtType>() {
                    @java.lang.Override
                    public void apply(CtType input, spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) {
                        (context.count)++;
                        throw new java.lang.ClassCastException("TEST");
                    }
                }).failurePolicy(spoon.reflect.visitor.chain.QueryFailurePolicy.IGNORE).list();
                org.junit.Assert.fail("It must fail, because body of map thrown CCE");
            } catch (java.lang.ClassCastException e) {
                org.junit.Assert.assertTrue(((context.count) > 0));
                org.junit.Assert.assertEquals("TEST", e.getMessage());
            }
        }
        {
            Context context = new Context();
            try {
                launcher.getFactory().Package().getRootPackage().filterChildren(null).map((CtType input,spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> outputConsumer) -> {
                    (context.count)++;
                    throw new java.lang.ClassCastException("TEST");
                }).failurePolicy(spoon.reflect.visitor.chain.QueryFailurePolicy.IGNORE).list();
                org.junit.Assert.fail("It must fail, because body of map thrown CCE");
            } catch (java.lang.ClassCastException e) {
                org.junit.Assert.assertTrue(((context.count) > 0));
                org.junit.Assert.assertEquals("TEST", e.getMessage());
            }
        }
    }

    @org.junit.Test
    public void testEarlyTerminatingQuery() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput", "--level", "info" });
        launcher.addInputResource("./src/test/java/spoon/test/filters/testclasses");
        launcher.run();
        class Context {
            boolean wasTerminated = false;

            void failIfTerminated(java.lang.String place) {
                org.junit.Assert.assertTrue((("The " + place) + " is called after query was terminated."), ((wasTerminated) == false));
            }
        }
        Context context = new Context();
        CtMethod firstMethod = launcher.getFactory().Package().getRootPackage().filterChildren(( e) -> {
            context.failIfTerminated("Filter#match of filterChildren");
            return true;
        }).map((spoon.reflect.declaration.CtElement e) -> {
            context.failIfTerminated("Array returning CtFunction#apply of map");
            return new spoon.reflect.declaration.CtElement[]{ e, e };
        }).map((spoon.reflect.declaration.CtElement e) -> {
            context.failIfTerminated("List returning CtFunction#apply of map");
            return java.util.Arrays.asList(new spoon.reflect.declaration.CtElement[]{ e, e });
        }).map((spoon.reflect.declaration.CtElement e,spoon.reflect.visitor.chain.CtConsumer<java.lang.Object> out) -> {
            context.failIfTerminated("CtConsumableFunction#apply of map");
            if (e instanceof CtMethod) {
                out.accept(e);
                context.wasTerminated = true;
            }
            out.accept(e);
        }).map(( e) -> {
            context.failIfTerminated("CtFunction#apply of map after CtConsumableFunction");
            return e;
        }).first(CtMethod.class);
        org.junit.Assert.assertTrue((firstMethod != null));
        org.junit.Assert.assertTrue(context.wasTerminated);
    }

    @org.junit.Test
    public void testParentFunction() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput", "--level", "info" });
        launcher.addInputResource("./src/test/java/spoon/test/filters/testclasses");
        launcher.run();
        spoon.reflect.declaration.CtClass<?> cls = launcher.getFactory().Class().get(spoon.test.filters.testclasses.Tacos.class);
        spoon.reflect.code.CtLocalVariable<?> varStrings = cls.filterChildren(new NamedElementFilter<>(spoon.reflect.code.CtLocalVariable.class, "strings")).first();
        class Context {
            spoon.reflect.declaration.CtElement expectedParent;
        }
        Context context = new Context();
        context.expectedParent = varStrings;
        varStrings.map(new spoon.reflect.visitor.filter.ParentFunction()).forEach(( parent) -> {
            context.expectedParent = context.expectedParent.getParent();
            org.junit.Assert.assertSame(context.expectedParent, parent);
        });
        org.junit.Assert.assertSame(launcher.getFactory().getModel().getUnnamedModule(), context.expectedParent);
        org.junit.Assert.assertSame(varStrings.getParent(), varStrings.map(new spoon.reflect.visitor.filter.ParentFunction().includingSelf(false)).first());
        org.junit.Assert.assertSame(varStrings, varStrings.map(new spoon.reflect.visitor.filter.ParentFunction().includingSelf(true)).first());
        org.junit.Assert.assertNull(factory.Type().createReference("p.T").map(new spoon.reflect.visitor.filter.ParentFunction()).first());
    }

    @org.junit.Test
    public void testCtScannerListener() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput", "--level", "info" });
        launcher.addInputResource("./src/test/java/spoon/test/filters/testclasses");
        launcher.run();
        class Context {
            long nrOfEnter = 0;

            long nrOfEnterRetTrue = 0;

            long nrOfExit = 0;

            long nrOfResults = 0;
        }
        Context context1 = new Context();
        java.util.List<spoon.reflect.declaration.CtElement> result1 = launcher.getFactory().getModel().map(new spoon.reflect.visitor.filter.CtScannerFunction().setListener(new spoon.reflect.visitor.chain.CtScannerListener() {
            @java.lang.Override
            public spoon.reflect.visitor.chain.ScanningMode enter(spoon.reflect.declaration.CtElement element) {
                (context1.nrOfEnter)++;
                if (element instanceof CtType) {
                    return spoon.reflect.visitor.chain.ScanningMode.SKIP_CHILDREN;
                }
                return spoon.reflect.visitor.chain.ScanningMode.NORMAL;
            }

            @java.lang.Override
            public void exit(spoon.reflect.declaration.CtElement element) {
                (context1.nrOfExit)++;
            }
        })).list();
        org.junit.Assert.assertTrue(((context1.nrOfEnter) > 0));
        org.junit.Assert.assertTrue(((result1.size()) > 0));
        org.junit.Assert.assertEquals(context1.nrOfEnter, context1.nrOfExit);
        Context context2 = new Context();
        java.util.Iterator iter = result1.iterator();
        launcher.getFactory().getModel().map(new spoon.reflect.visitor.filter.CtScannerFunction().setListener(new spoon.reflect.visitor.chain.CtScannerListener() {
            int inClass = 0;

            @java.lang.Override
            public spoon.reflect.visitor.chain.ScanningMode enter(spoon.reflect.declaration.CtElement element) {
                (context2.nrOfEnter)++;
                if ((inClass) > 0) {
                    return spoon.reflect.visitor.chain.ScanningMode.SKIP_ALL;
                }
                if (element instanceof CtType) {
                    (inClass)++;
                }
                (context2.nrOfEnterRetTrue)++;
                return spoon.reflect.visitor.chain.ScanningMode.NORMAL;
            }

            @java.lang.Override
            public void exit(spoon.reflect.declaration.CtElement element) {
                (context2.nrOfExit)++;
                if (element instanceof CtType) {
                    (inClass)--;
                }
                org.junit.Assert.assertTrue((((inClass) == 0) || ((inClass) == 1)));
            }
        })).forEach(( ele) -> {
            (context2.nrOfResults)++;
            org.junit.Assert.assertTrue(("ele instanceof " + (ele.getClass())), (((ele instanceof spoon.reflect.declaration.CtPackage) || (ele instanceof CtType)) || (ele instanceof spoon.reflect.declaration.CtModule)));
            org.junit.Assert.assertSame(ele, iter.next());
        });
        org.junit.Assert.assertTrue(((context2.nrOfEnter) > 0));
        org.junit.Assert.assertTrue(((context2.nrOfEnter) > (context2.nrOfEnterRetTrue)));
        org.junit.Assert.assertEquals(result1.size(), context2.nrOfResults);
        org.junit.Assert.assertEquals(context2.nrOfEnterRetTrue, context2.nrOfExit);
    }

    @org.junit.Test
    public void testSubInheritanceHierarchyResolver() throws java.lang.Exception {
        final Launcher launcher = new Launcher();
        launcher.setArgs(new java.lang.String[]{ "--output-type", "nooutput", "--level", "info" });
        launcher.addInputResource("./src/test/java/spoon/test/filters/testclasses");
        launcher.buildModel();
        spoon.support.visitor.SubInheritanceHierarchyResolver resolver = new spoon.support.visitor.SubInheritanceHierarchyResolver(launcher.getModel().getRootPackage());
        resolver.forEachSubTypeInPackage(new spoon.reflect.visitor.chain.CtConsumer<CtType<?>>() {
            @java.lang.Override
            public void accept(CtType<?> ctType) {
                org.junit.Assert.fail();
            }
        });
        resolver.addSuperType(launcher.getFactory().Type().createReference(spoon.test.filters.testclasses.AbstractTostada.class));
        class Counter {
            int counter = 0;
        }
        Counter c = new Counter();
        resolver.forEachSubTypeInPackage(new spoon.reflect.visitor.chain.CtConsumer<CtType<?>>() {
            @java.lang.Override
            public void accept(CtType<?> ctType) {
                (c.counter)++;
            }
        });
        org.junit.Assert.assertEquals(5, c.counter);
        resolver.addSuperType(launcher.getFactory().Type().createReference(spoon.test.filters.testclasses.Tostada.class));
        resolver.forEachSubTypeInPackage(new spoon.reflect.visitor.chain.CtConsumer<CtType<?>>() {
            @java.lang.Override
            public void accept(CtType<?> ctType) {
                org.junit.Assert.fail();
            }
        });
        resolver.addSuperType(launcher.getFactory().Type().createReference(spoon.test.filters.testclasses.ITostada.class));
        Counter c2 = new Counter();
        resolver.forEachSubTypeInPackage(new spoon.reflect.visitor.chain.CtConsumer<CtType<?>>() {
            @java.lang.Override
            public void accept(CtType<?> ctType) {
                (c2.counter)++;
                org.junit.Assert.assertEquals("spoon.test.filters.testclasses.Tacos", ctType.getQualifiedName());
            }
        });
        org.junit.Assert.assertEquals(1, c2.counter);
    }

    @org.junit.Test
    public void testNameFilterWithGenericType() {
        Launcher spoon = new Launcher();
        spoon.addInputResource("./src/test/java/spoon/test/imports/testclasses/internal4/Constants.java");
        spoon.buildModel();
        CtType type = spoon.getFactory().Type().get(Constants.class);
        java.util.List<CtMethod> ctMethods = type.getElements(new NamedElementFilter<>(CtMethod.class, "CONSTANT"));
        org.junit.Assert.assertTrue(ctMethods.isEmpty());
        java.util.List<CtField> ctFields = type.getElements(new NamedElementFilter<>(CtField.class, "CONSTANT"));
        org.junit.Assert.assertEquals(1, ctFields.size());
        org.junit.Assert.assertTrue(((ctFields.get(0)) instanceof CtField));
    }
}

