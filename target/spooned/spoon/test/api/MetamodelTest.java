package spoon.test.api;


public class MetamodelTest {
    @org.junit.Test
    public void testGetAllMetamodelInterfacess() {
        spoon.SpoonAPI interfaces = new spoon.Launcher();
        interfaces.addInputResource("src/main/java/spoon/reflect/declaration");
        interfaces.addInputResource("src/main/java/spoon/reflect/code");
        interfaces.addInputResource("src/main/java/spoon/reflect/reference");
        interfaces.buildModel();
        org.hamcrest.MatcherAssert.assertThat(spoon.Metamodel.getAllMetamodelInterfaces().stream().map(( x) -> x.getQualifiedName()).collect(java.util.stream.Collectors.toSet()), org.hamcrest.core.IsEqual.equalTo(interfaces.getModel().getAllTypes().stream().map(( x) -> x.getQualifiedName()).collect(java.util.stream.Collectors.toSet())));
    }

    @org.junit.Test
    public void testGetterSetterFroRole() {
        spoon.SpoonAPI interfaces = new spoon.Launcher();
        interfaces.addInputResource("src/main/java/spoon/reflect/declaration");
        interfaces.addInputResource("src/main/java/spoon/reflect/code");
        interfaces.addInputResource("src/main/java/spoon/reflect/reference");
        interfaces.buildModel();
        spoon.reflect.factory.Factory factory = interfaces.getFactory();
        spoon.reflect.reference.CtTypeReference propertyGetter = factory.Type().get(spoon.reflect.annotations.PropertyGetter.class).getReference();
        spoon.reflect.reference.CtTypeReference propertySetter = factory.Type().get(spoon.reflect.annotations.PropertySetter.class).getReference();
        java.util.Set<java.lang.String> expectedRoles = java.util.Arrays.stream(spoon.reflect.path.CtRole.values()).map(( r) -> r.name()).collect(java.util.stream.Collectors.toSet());
        java.util.List<spoon.reflect.declaration.CtMethod<?>> getters = interfaces.getModel().getElements(new spoon.reflect.visitor.filter.AnnotationFilter<spoon.reflect.declaration.CtMethod<?>>(spoon.reflect.annotations.PropertyGetter.class));
        java.util.Set<java.lang.String> getterRoles = getters.stream().map(( g) -> ((spoon.reflect.code.CtFieldRead) (g.getAnnotation(propertyGetter).getValue("role"))).getVariable().getSimpleName()).collect(java.util.stream.Collectors.toSet());
        java.util.Set<spoon.reflect.declaration.CtMethod<?>> isNotGetter = getters.stream().filter(( m) -> !((m.getSimpleName().startsWith("get")) || (m.getSimpleName().startsWith("is")))).collect(java.util.stream.Collectors.toSet());
        java.util.List<spoon.reflect.declaration.CtMethod<?>> setters = interfaces.getModel().getElements(new spoon.reflect.visitor.filter.AnnotationFilter<spoon.reflect.declaration.CtMethod<?>>(spoon.reflect.annotations.PropertySetter.class));
        java.util.Set<java.lang.String> setterRoles = setters.stream().map(( g) -> ((spoon.reflect.code.CtFieldRead) (g.getAnnotation(propertySetter).getValue("role"))).getVariable().getSimpleName()).collect(java.util.stream.Collectors.toSet());
        java.util.Set<spoon.reflect.declaration.CtMethod<?>> isNotSetter = setters.stream().filter(( m) -> !((((m.getSimpleName().startsWith("set")) || (m.getSimpleName().startsWith("add"))) || (m.getSimpleName().startsWith("insert"))) || (m.getSimpleName().startsWith("remove")))).collect(java.util.stream.Collectors.toSet());
        org.junit.Assert.assertEquals(expectedRoles, getterRoles);
        org.junit.Assert.assertEquals(expectedRoles, setterRoles);
        org.junit.Assert.assertEquals(java.util.Collections.EMPTY_SET, isNotGetter);
        org.junit.Assert.assertEquals(java.util.Collections.EMPTY_SET, isNotSetter);
    }

    @org.junit.Test
    public void testRoleOnField() {
        java.lang.System.setProperty("line.separator", "\n");
        spoon.SpoonAPI implementations = new spoon.Launcher();
        implementations.addInputResource("src/main/java/spoon/support/reflect");
        implementations.buildModel();
        spoon.reflect.factory.Factory factory = implementations.getFactory();
        spoon.reflect.reference.CtTypeReference metamodelPropertyField = factory.Type().get(spoon.reflect.annotations.MetamodelPropertyField.class).getReference();
        final java.util.List<java.lang.String> result = new java.util.ArrayList();
        java.util.List<spoon.reflect.declaration.CtField> fieldWithoutAnnotation = ((java.util.List<spoon.reflect.declaration.CtField>) (implementations.getModel().getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.declaration.CtField>(spoon.reflect.declaration.CtField.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.declaration.CtField candidate) {
                if (((candidate.hasModifier(spoon.reflect.declaration.ModifierKind.FINAL)) || (candidate.hasModifier(spoon.reflect.declaration.ModifierKind.STATIC))) || (candidate.hasModifier(spoon.reflect.declaration.ModifierKind.TRANSIENT))) {
                    return false;
                }
                if (((("parent".equals(candidate.getSimpleName())) || ("metadata".equals(candidate.getSimpleName()))) || ("factory".equals(candidate.getSimpleName()))) || ("valueOfMethod".equals(candidate.getSimpleName()))) {
                    return false;
                }
                spoon.reflect.declaration.CtClass parent = candidate.getParent(spoon.reflect.declaration.CtClass.class);
                return (parent != null) && ((parent.isSubtypeOf(candidate.getFactory().createCtTypeReference(spoon.reflect.reference.CtReference.class))) || (parent.isSubtypeOf(candidate.getFactory().createCtTypeReference(spoon.reflect.declaration.CtElement.class))));
            }
        }).stream().map(( x) -> {
            result.add(x.toString());
            return x;
        }).filter(( f) -> (f.getAnnotation(metamodelPropertyField)) == null).collect(java.util.stream.Collectors.toList())));
        org.junit.Assert.assertTrue(result.contains("@spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.IS_SHADOW)\nboolean isShadow;"));
        org.junit.Assert.assertTrue(result.contains("@spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.TYPE)\nspoon.reflect.reference.CtTypeReference<T> type;"));
        org.junit.Assert.assertTrue(((result.size()) > 100));
        org.junit.Assert.assertEquals(java.util.Collections.emptyList(), fieldWithoutAnnotation);
        final spoon.reflect.reference.CtTypeReference propertySetter = factory.Type().get(spoon.reflect.annotations.PropertySetter.class).getReference();
        final spoon.reflect.reference.CtTypeReference propertyGetter = factory.Type().get(spoon.reflect.annotations.PropertyGetter.class).getReference();
        java.util.List<spoon.reflect.declaration.CtField> fields = factory.getModel().getElements(new spoon.reflect.visitor.filter.AnnotationFilter<spoon.reflect.declaration.CtField>(spoon.reflect.annotations.MetamodelPropertyField.class));
        for (spoon.reflect.declaration.CtField field : fields) {
            spoon.reflect.declaration.CtClass parent = field.getParent(spoon.reflect.declaration.CtClass.class);
            spoon.reflect.code.CtExpression roleExpression = field.getAnnotation(metamodelPropertyField).getValue("role");
            java.util.List<java.lang.String> roles = new java.util.ArrayList<>();
            if (roleExpression instanceof spoon.reflect.code.CtFieldRead) {
                roles.add(((spoon.reflect.code.CtFieldRead) (roleExpression)).getVariable().getSimpleName());
            }else
                if (roleExpression instanceof spoon.reflect.code.CtNewArray) {
                    java.util.List<spoon.reflect.code.CtFieldRead> elements = ((spoon.reflect.code.CtNewArray) (roleExpression)).getElements();
                    for (int i = 0; i < (elements.size()); i++) {
                        spoon.reflect.code.CtFieldRead ctFieldRead = elements.get(i);
                        roles.add(ctFieldRead.getVariable().getSimpleName());
                    }
                }

            spoon.reflect.visitor.chain.CtQuery superQuery = parent.map(new spoon.reflect.visitor.filter.SuperInheritanceHierarchyFunction());
            java.util.List<spoon.reflect.declaration.CtMethod> methods = superQuery.map((spoon.reflect.declaration.CtType type) -> type.getMethodsAnnotatedWith(propertyGetter, propertySetter)).list();
            boolean setterFound = false;
            boolean getterFound = false;
            for (spoon.reflect.declaration.CtMethod method : methods) {
                spoon.reflect.declaration.CtAnnotation getterAnnotation = method.getAnnotation(propertyGetter);
                spoon.reflect.declaration.CtAnnotation setterAnnotation = method.getAnnotation(propertySetter);
                if (getterAnnotation != null) {
                    getterFound |= roles.contains(((spoon.reflect.code.CtFieldRead) (getterAnnotation.getValue("role"))).getVariable().getSimpleName());
                }
                if (setterAnnotation != null) {
                    setterFound |= roles.contains(((spoon.reflect.code.CtFieldRead) (setterAnnotation.getValue("role"))).getVariable().getSimpleName());
                }
            }
            org.junit.Assert.assertTrue(((roles + " must have a getter in ") + (parent.getQualifiedName())), getterFound);
            org.junit.Assert.assertTrue(((roles + " must have a setter in ") + (parent.getQualifiedName())), setterFound);
        }
    }

    @org.junit.Test
    public void testMetamodelWithoutSources() {
        spoon.metamodel.SpoonMetaModel runtimeMM = new spoon.metamodel.SpoonMetaModel();
        java.util.Collection<spoon.metamodel.MetamodelConcept> concepts = runtimeMM.getConcepts();
        spoon.metamodel.SpoonMetaModel sourceBasedMM = new spoon.metamodel.SpoonMetaModel(new java.io.File("src/main/java"));
        java.util.Map<java.lang.String, spoon.metamodel.MetamodelConcept> expectedConceptsByName = new java.util.HashMap<>();
        sourceBasedMM.getConcepts().forEach(( c) -> {
            expectedConceptsByName.put(c.getName(), c);
        });
        for (spoon.metamodel.MetamodelConcept runtimeConcept : concepts) {
            spoon.metamodel.MetamodelConcept expectedConcept = expectedConceptsByName.remove(runtimeConcept.getName());
            org.junit.Assert.assertNotNull(expectedConcept);
            assertConceptsEqual(expectedConcept, runtimeConcept);
        }
        org.junit.Assert.assertEquals(0, expectedConceptsByName.size());
    }

    private void assertConceptsEqual(spoon.metamodel.MetamodelConcept expectedConcept, spoon.metamodel.MetamodelConcept runtimeConcept) {
        org.junit.Assert.assertEquals(expectedConcept.getName(), runtimeConcept.getName());
        if ((expectedConcept.getModelClass()) == null) {
            org.junit.Assert.assertNull(runtimeConcept.getModelClass());
        }else {
            org.junit.Assert.assertNotNull(runtimeConcept.getModelClass());
            org.junit.Assert.assertEquals(expectedConcept.getModelClass().getActualClass(), runtimeConcept.getModelClass().getActualClass());
        }
        org.junit.Assert.assertEquals(expectedConcept.getModelInterface().getActualClass(), runtimeConcept.getModelInterface().getActualClass());
        org.junit.Assert.assertEquals(expectedConcept.getKind(), runtimeConcept.getKind());
        org.junit.Assert.assertEquals(expectedConcept.getSuperConcepts().size(), runtimeConcept.getSuperConcepts().size());
        for (int i = 0; i < (expectedConcept.getSuperConcepts().size()); i++) {
            assertConceptsEqual(expectedConcept.getSuperConcepts().get(i), runtimeConcept.getSuperConcepts().get(i));
        }
        java.util.Map<spoon.reflect.path.CtRole, spoon.metamodel.MetamodelProperty> expectedRoleToProperty = new java.util.HashMap(expectedConcept.getRoleToProperty());
        for (java.util.Map.Entry<spoon.reflect.path.CtRole, spoon.metamodel.MetamodelProperty> e : runtimeConcept.getRoleToProperty().entrySet()) {
            spoon.metamodel.MetamodelProperty runtimeProperty = e.getValue();
            spoon.metamodel.MetamodelProperty expectedProperty = expectedRoleToProperty.remove(e.getKey());
            assertPropertiesEqual(expectedProperty, runtimeProperty);
        }
        org.junit.Assert.assertEquals(0, expectedRoleToProperty.size());
    }

    private void assertPropertiesEqual(spoon.metamodel.MetamodelProperty expectedProperty, spoon.metamodel.MetamodelProperty runtimeProperty) {
        org.junit.Assert.assertSame(expectedProperty.getRole(), runtimeProperty.getRole());
        org.junit.Assert.assertEquals(expectedProperty.getName(), runtimeProperty.getName());
        org.junit.Assert.assertEquals(expectedProperty.getItemValueType().getActualClass(), runtimeProperty.getItemValueType().getActualClass());
        org.junit.Assert.assertEquals(expectedProperty.getOwnerConcept().getName(), runtimeProperty.getOwnerConcept().getName());
        org.junit.Assert.assertSame(expectedProperty.getValueContainerType(), runtimeProperty.getValueContainerType());
        org.junit.Assert.assertEquals(expectedProperty.getValueType(), runtimeProperty.getValueType());
        org.junit.Assert.assertEquals(expectedProperty.isDerived(), runtimeProperty.isDerived());
        org.junit.Assert.assertEquals(expectedProperty.isUnsettable(), runtimeProperty.isUnsettable());
    }

    @org.junit.Test
    public void testMetamodelCachedInFactory() throws java.io.IOException {
        spoon.reflect.factory.Factory factory = new spoon.reflect.factory.FactoryImpl(new spoon.support.DefaultCoreFactory(), new spoon.support.StandardEnvironment());
        factory.getEnvironment().setLevel("INFO");
        spoon.metamodel.SpoonMetaModel metaModel = factory.getMetaModel();
        org.junit.Assert.assertNotNull(metaModel);
        org.junit.Assert.assertSame(metaModel, factory.getMetaModel());
    }

    @org.junit.Test
    public void spoonMetaModelTest() {
        spoon.metamodel.SpoonMetaModel mm = new spoon.metamodel.SpoonMetaModel(new java.io.File("./src/main/java"));
        java.util.List<java.lang.String> problems = new java.util.ArrayList<>();
        java.util.Set<spoon.reflect.path.CtRole> unhandledRoles = new java.util.HashSet<>(java.util.Arrays.asList(spoon.reflect.path.CtRole.values()));
        mm.getConcepts().forEach(( mmConcept) -> {
            mmConcept.getRoleToProperty().forEach(( role, mmField) -> {
                if (mmField.isUnsettable()) {
                    org.junit.Assert.assertTrue((("Unsettable field " + mmField) + " must be derived too"), mmField.isDerived());
                }
                unhandledRoles.remove(role);
                if ((mmField.getMethod(spoon.metamodel.MMMethodKind.GET)) == null) {
                    problems.add(((("Missing getter for " + (mmField.getOwnerConcept().getName())) + " and CtRole.") + (mmField.getRole())));
                }
                if ((mmField.getMethod(spoon.metamodel.MMMethodKind.SET)) == null) {
                    if (((mmConcept.getTypeContext().isSubtypeOf(mm.getFactory().Type().createReference(spoon.reflect.reference.CtReference.class))) == false) && ((mmConcept.getName().equals("CtTypeInformation")) == false)) {
                        problems.add(((("Missing setter for " + (mmField.getOwnerConcept().getName())) + " and CtRole.") + (mmField.getRole())));
                    }
                }
                org.junit.Assert.assertFalse((("Value type of Field " + (mmField.toString())) + " is implicit"), mmField.getValueType().isImplicit());
                org.junit.Assert.assertFalse((("Item value type of Field " + (mmField.toString())) + " is implicit"), mmField.getItemValueType().isImplicit());
                mmField.forEachUnhandledMethod(( ctMethod) -> problems.add(((("Unhandled method signature: " + (ctMethod.getDeclaringType().getSimpleName())) + "#") + (ctMethod.getSignature()))));
            });
        });
        unhandledRoles.forEach(( it) -> problems.add(("Unused CtRole." + (it.name()))));
    }

    @org.junit.Test
    public void testGetRoleHandlersOfClass() {
        int countOfIfaces = 0;
        for (spoon.reflect.declaration.CtType spoonIface : spoon.Metamodel.getAllMetamodelInterfaces()) {
            countOfIfaces++;
            checkRoleHandlersOfType(spoonIface);
        }
        org.junit.Assert.assertTrue((countOfIfaces > 10));
    }

    private void checkRoleHandlersOfType(spoon.reflect.declaration.CtType iface) {
        java.lang.Class ifaceClass = iface.getActualClass();
        java.util.List<spoon.reflect.meta.RoleHandler> roleHandlersOfIFace = new java.util.ArrayList(spoon.reflect.meta.impl.RoleHandlerHelper.getRoleHandlers(ifaceClass));
        java.util.Set<spoon.reflect.meta.RoleHandler> allRoleHandlers = new java.util.HashSet<>();
        spoon.reflect.meta.impl.RoleHandlerHelper.forEachRoleHandler(( rh) -> allRoleHandlers.add(rh));
        for (spoon.reflect.path.CtRole role : spoon.reflect.path.CtRole.values()) {
            spoon.reflect.meta.RoleHandler rh = spoon.reflect.meta.impl.RoleHandlerHelper.getOptionalRoleHandler(ifaceClass, role);
            if (rh != null) {
                org.junit.Assert.assertTrue(((("RoleHandler for role " + role) + " is missing for ") + ifaceClass), roleHandlersOfIFace.remove(rh));
                org.junit.Assert.assertTrue((("RoleHandler " + rh) + " is not accessible by RoleHandlerHelper#forEachRoleHandler()"), allRoleHandlers.contains(rh));
            }
        }
        org.junit.Assert.assertTrue(((("There are unexpected RoleHandlers " + roleHandlersOfIFace) + " for ") + ifaceClass), roleHandlersOfIFace.isEmpty());
    }

    @org.junit.Test
    public void testGetParentRoleHandler() {
        spoon.Launcher launcher = new spoon.Launcher();
        spoon.reflect.factory.Factory factory = launcher.getFactory();
        spoon.reflect.declaration.CtClass<?> type = ((spoon.reflect.declaration.CtClass) (factory.Core().create(spoon.reflect.declaration.CtClass.class)));
        spoon.reflect.declaration.CtField<?> field = factory.Field().create(type, java.util.Collections.emptySet(), factory.Type().booleanPrimitiveType(), "someField");
        org.junit.Assert.assertSame(type, field.getDeclaringType());
        org.junit.Assert.assertSame(spoon.reflect.path.CtRole.TYPE_MEMBER, spoon.reflect.meta.impl.RoleHandlerHelper.getRoleHandlerWrtParent(field).getRole());
        org.junit.Assert.assertSame(spoon.reflect.path.CtRole.TYPE_MEMBER, field.getRoleInParent());
        field.setParent(null);
        org.junit.Assert.assertNull(spoon.reflect.meta.impl.RoleHandlerHelper.getRoleHandlerWrtParent(field));
        org.junit.Assert.assertNull(spoon.reflect.meta.impl.RoleHandlerHelper.getRoleHandlerWrtParent(type));
    }

    @org.junit.Test
    public void elementAnnotationRoleHandlerTest() {
        spoon.Launcher launcher = new spoon.Launcher();
        spoon.reflect.factory.Factory factory = launcher.getFactory();
        spoon.reflect.declaration.CtClass<?> type = ((spoon.reflect.declaration.CtClass) (factory.Core().create(spoon.reflect.declaration.CtClass.class)));
        spoon.reflect.declaration.CtAnnotation<?> annotation = factory.Annotation().annotate(type, spoon.template.Parameter.class, "value", "abc");
        spoon.reflect.meta.RoleHandler roleHandler = spoon.reflect.meta.impl.RoleHandlerHelper.getRoleHandler(type.getClass(), spoon.reflect.path.CtRole.ANNOTATION);
        org.junit.Assert.assertNotNull(roleHandler);
        org.junit.Assert.assertEquals(spoon.reflect.declaration.CtElement.class, roleHandler.getTargetType());
        org.junit.Assert.assertSame(spoon.reflect.path.CtRole.ANNOTATION, roleHandler.getRole());
        org.junit.Assert.assertSame(spoon.reflect.meta.ContainerKind.LIST, roleHandler.getContainerKind());
        org.junit.Assert.assertEquals(spoon.reflect.declaration.CtAnnotation.class, roleHandler.getValueClass());
        java.util.List<spoon.reflect.declaration.CtAnnotation<?>> value = roleHandler.getValue(type);
        org.junit.Assert.assertEquals(1, value.size());
        org.junit.Assert.assertSame(annotation, value.get(0));
        try {
            value.remove(annotation);
            org.junit.Assert.fail();
        } catch (java.lang.Exception e) {
            this.getClass();
        }
        roleHandler.setValue(type, java.util.Collections.emptyList());
        value = roleHandler.getValue(type);
        org.junit.Assert.assertEquals(0, value.size());
        roleHandler.setValue(type, java.util.Collections.singletonList(annotation));
        value = roleHandler.getValue(type);
        org.junit.Assert.assertEquals(1, value.size());
        org.junit.Assert.assertSame(annotation, value.get(0));
        try {
            roleHandler.setValue(type, annotation);
            org.junit.Assert.fail();
        } catch (java.lang.ClassCastException e) {
        }
    }

    @org.junit.Test
    public void elementAnnotationRoleTest() {
        spoon.Launcher launcher = new spoon.Launcher();
        spoon.reflect.factory.Factory factory = launcher.getFactory();
        spoon.reflect.declaration.CtClass<?> type = ((spoon.reflect.declaration.CtClass) (factory.Core().create(spoon.reflect.declaration.CtClass.class)));
        spoon.reflect.declaration.CtAnnotation<?> annotation = factory.Annotation().annotate(type, spoon.template.Parameter.class, "value", "abc");
        java.util.List<spoon.reflect.declaration.CtAnnotation<?>> value = type.getValueByRole(spoon.reflect.path.CtRole.ANNOTATION);
        org.junit.Assert.assertEquals(1, value.size());
        org.junit.Assert.assertSame(annotation, value.get(0));
        try {
            value.remove(annotation);
            org.junit.Assert.fail();
        } catch (java.lang.Exception e) {
            this.getClass();
        }
        type.setValueByRole(spoon.reflect.path.CtRole.ANNOTATION, java.util.Collections.emptyList());
        value = type.getValueByRole(spoon.reflect.path.CtRole.ANNOTATION);
        org.junit.Assert.assertEquals(0, value.size());
        type.setValueByRole(spoon.reflect.path.CtRole.ANNOTATION, java.util.Collections.singletonList(annotation));
        value = type.getValueByRole(spoon.reflect.path.CtRole.ANNOTATION);
        org.junit.Assert.assertEquals(1, value.size());
        org.junit.Assert.assertSame(annotation, value.get(0));
        try {
            type.setValueByRole(spoon.reflect.path.CtRole.ANNOTATION, annotation);
            org.junit.Assert.fail();
        } catch (java.lang.ClassCastException e) {
        }
    }

    @org.junit.Test
    public void elementAnnotationAdaptedRoleTest() {
        spoon.Launcher launcher = new spoon.Launcher();
        spoon.reflect.factory.Factory factory = launcher.getFactory();
        spoon.reflect.declaration.CtClass<?> type = ((spoon.reflect.declaration.CtClass) (factory.Core().create(spoon.reflect.declaration.CtClass.class)));
        spoon.reflect.declaration.CtAnnotation<?> annotation = factory.Annotation().annotate(type, spoon.template.Parameter.class, "value", "abc");
        java.util.List<spoon.reflect.declaration.CtAnnotation<?>> value = spoon.reflect.meta.impl.RoleHandlerHelper.getRoleHandler(type.getClass(), spoon.reflect.path.CtRole.ANNOTATION).asList(type);
        org.junit.Assert.assertEquals(1, value.size());
        org.junit.Assert.assertSame(annotation, value.get(0));
        value.remove(annotation);
        org.junit.Assert.assertEquals(0, value.size());
        org.junit.Assert.assertEquals(0, ((java.util.List) (type.getValueByRole(spoon.reflect.path.CtRole.ANNOTATION))).size());
        value.add(annotation);
        org.junit.Assert.assertEquals(1, value.size());
        org.junit.Assert.assertSame(annotation, value.get(0));
        org.junit.Assert.assertEquals(1, ((java.util.List) (type.getValueByRole(spoon.reflect.path.CtRole.ANNOTATION))).size());
        org.junit.Assert.assertEquals(annotation, ((java.util.List) (type.getValueByRole(spoon.reflect.path.CtRole.ANNOTATION))).get(0));
    }

    @org.junit.Test
    public void singleValueRoleAddSetRemove() {
        spoon.Launcher launcher = new spoon.Launcher();
        spoon.reflect.factory.Factory factory = launcher.getFactory();
        spoon.reflect.reference.CtTypeReference<?> typeRef = factory.Type().createReference("some.test.package.TestType");
        spoon.reflect.meta.RoleHandler rh = spoon.reflect.meta.impl.RoleHandlerHelper.getRoleHandler(typeRef.getClass(), spoon.reflect.path.CtRole.PACKAGE_REF);
        java.util.List<spoon.reflect.reference.CtPackageReference> packages = rh.asList(typeRef);
        assertListContracts(packages, typeRef, 1, "some.test.package");
        try {
            packages.add(typeRef.getPackage());
            org.junit.Assert.fail();
        } catch (java.lang.Exception e) {
        }
        assertListContracts(packages, typeRef, 1, "some.test.package");
        try {
            org.junit.Assert.assertFalse(packages.add(null));
            org.junit.Assert.fail();
        } catch (java.lang.Exception e) {
        }
        assertListContracts(packages, typeRef, 1, "some.test.package");
        try {
            packages.add(factory.Package().createReference("some.test.another_package"));
            org.junit.Assert.fail();
        } catch (spoon.SpoonException e) {
        }
        assertListContracts(packages, typeRef, 1, "some.test.package");
        org.junit.Assert.assertFalse(packages.remove(factory.Package().createReference("some.test.another_package")));
        assertListContracts(packages, typeRef, 1, "some.test.package");
        org.junit.Assert.assertFalse(packages.remove(null));
        assertListContracts(packages, typeRef, 1, "some.test.package");
        org.junit.Assert.assertTrue(packages.remove(factory.Package().createReference("some.test.package")));
        assertListContracts(packages, typeRef, 0, null);
        org.junit.Assert.assertTrue(packages.add(null));
        assertListContracts(packages, typeRef, 1, null);
        try {
            packages.add(factory.Package().createReference("some.test.another_package"));
            org.junit.Assert.fail();
        } catch (spoon.SpoonException e) {
        }
        assertListContracts(packages, typeRef, 1, null);
        org.junit.Assert.assertEquals(null, packages.set(0, factory.Package().createReference("some.test.package")));
        assertListContracts(packages, typeRef, 1, "some.test.package");
        org.junit.Assert.assertEquals("some.test.package", packages.set(0, null).getQualifiedName());
        assertListContracts(packages, typeRef, 1, null);
        org.junit.Assert.assertNull(packages.remove(0));
        assertListContracts(packages, typeRef, 0, null);
        org.junit.Assert.assertTrue(packages.add(null));
        assertListContracts(packages, typeRef, 1, null);
        org.junit.Assert.assertTrue(packages.remove(null));
        assertListContracts(packages, typeRef, 0, null);
        try {
            packages.set(0, factory.Package().createReference("some.test.another_package"));
            org.junit.Assert.fail();
        } catch (java.lang.IndexOutOfBoundsException e) {
        }
        assertListContracts(packages, typeRef, 0, null);
        org.junit.Assert.assertTrue(packages.add(factory.Package().createReference("some.test.another_package")));
        assertListContracts(packages, typeRef, 1, "some.test.another_package");
        org.junit.Assert.assertEquals("some.test.another_package", packages.remove(0).getQualifiedName());
        assertListContracts(packages, typeRef, 0, null);
    }

    private void assertListContracts(java.util.List<spoon.reflect.reference.CtPackageReference> packages, spoon.reflect.reference.CtTypeReference<?> typeRef, int expectedSize, java.lang.String expectedValue) {
        if (expectedSize == 0) {
            org.junit.Assert.assertEquals(0, packages.size());
            org.junit.Assert.assertNull(typeRef.getPackage());
            for (int i = -1; i < 3; i++) {
                try {
                    packages.get(i);
                    org.junit.Assert.fail();
                } catch (java.lang.IndexOutOfBoundsException e) {
                }
            }
        }else
            if (expectedSize == 1) {
                org.junit.Assert.assertEquals(1, packages.size());
                assertPackageName(expectedValue, typeRef.getPackage());
                for (int i = -1; i < 3; i++) {
                    if (i == 0) {
                        assertPackageName(expectedValue, packages.get(0));
                    }else {
                        try {
                            packages.get(i);
                            org.junit.Assert.fail();
                        } catch (java.lang.IndexOutOfBoundsException e) {
                        }
                    }
                }
            }else {
                org.junit.Assert.fail();
            }

    }

    private void assertPackageName(java.lang.String expectedPackageName, spoon.reflect.reference.CtPackageReference packageRef) {
        if (expectedPackageName == null) {
            org.junit.Assert.assertNull(packageRef);
        }else {
            org.junit.Assert.assertEquals(expectedPackageName, packageRef.getQualifiedName());
        }
    }

    @org.junit.Test
    public void listValueRoleSetOn() {
        spoon.Launcher launcher = new spoon.Launcher();
        spoon.reflect.factory.Factory factory = launcher.getFactory();
        spoon.reflect.declaration.CtClass<?> ctClass = factory.Class().create("some.test.TestClass");
        spoon.reflect.meta.RoleHandler rh = spoon.reflect.meta.impl.RoleHandlerHelper.getRoleHandler(ctClass.getClass(), spoon.reflect.path.CtRole.TYPE_MEMBER);
        java.util.List<spoon.reflect.declaration.CtTypeMember> typeMembers = rh.asList(ctClass);
        org.junit.Assert.assertEquals(0, typeMembers.size());
        spoon.reflect.declaration.CtField<?> field1 = createField(factory, "field1");
        spoon.reflect.declaration.CtField<?> field2 = createField(factory, "field2");
        spoon.reflect.declaration.CtField<?> field3 = createField(factory, "field3");
        org.junit.Assert.assertEquals(0, typeMembers.size());
        typeMembers.add(field1);
        org.junit.Assert.assertEquals(1, typeMembers.size());
        org.junit.Assert.assertEquals(1, ctClass.getTypeMembers().size());
        org.junit.Assert.assertSame(ctClass, field1.getDeclaringType());
        org.junit.Assert.assertThat(java.util.Arrays.asList("field1"), org.hamcrest.CoreMatchers.is(ctClass.filterChildren(new spoon.reflect.visitor.filter.TypeFilter(spoon.reflect.declaration.CtField.class)).map((spoon.reflect.declaration.CtField e) -> e.getSimpleName()).list()));
        typeMembers.add(field2);
        org.junit.Assert.assertSame(ctClass, field2.getDeclaringType());
        org.junit.Assert.assertThat(java.util.Arrays.asList("field1", "field2"), org.hamcrest.CoreMatchers.is(ctClass.filterChildren(new spoon.reflect.visitor.filter.TypeFilter(spoon.reflect.declaration.CtField.class)).map((spoon.reflect.declaration.CtField e) -> e.getSimpleName()).list()));
        typeMembers.set(0, field3);
        org.junit.Assert.assertSame(ctClass, field3.getDeclaringType());
        org.junit.Assert.assertThat(java.util.Arrays.asList("field3", "field2"), org.hamcrest.CoreMatchers.is(ctClass.filterChildren(new spoon.reflect.visitor.filter.TypeFilter(spoon.reflect.declaration.CtField.class)).map((spoon.reflect.declaration.CtField e) -> e.getSimpleName()).list()));
        typeMembers.set(1, field1);
        org.junit.Assert.assertThat(java.util.Arrays.asList("field3", "field1"), org.hamcrest.CoreMatchers.is(ctClass.filterChildren(new spoon.reflect.visitor.filter.TypeFilter(spoon.reflect.declaration.CtField.class)).map((spoon.reflect.declaration.CtField e) -> e.getSimpleName()).list()));
        org.junit.Assert.assertSame(field3, typeMembers.remove(0));
        org.junit.Assert.assertThat(java.util.Arrays.asList("field1"), org.hamcrest.CoreMatchers.is(ctClass.filterChildren(new spoon.reflect.visitor.filter.TypeFilter(spoon.reflect.declaration.CtField.class)).map((spoon.reflect.declaration.CtField e) -> e.getSimpleName()).list()));
        org.junit.Assert.assertFalse(typeMembers.remove(field2));
        org.junit.Assert.assertThat(java.util.Arrays.asList("field1"), org.hamcrest.CoreMatchers.is(ctClass.filterChildren(new spoon.reflect.visitor.filter.TypeFilter(spoon.reflect.declaration.CtField.class)).map((spoon.reflect.declaration.CtField e) -> e.getSimpleName()).list()));
        org.junit.Assert.assertTrue(typeMembers.remove(field1));
        org.junit.Assert.assertThat(java.util.Arrays.asList(), org.hamcrest.CoreMatchers.is(ctClass.filterChildren(new spoon.reflect.visitor.filter.TypeFilter(spoon.reflect.declaration.CtField.class)).map((spoon.reflect.declaration.CtField e) -> e.getSimpleName()).list()));
    }

    private spoon.reflect.declaration.CtField<?> createField(spoon.reflect.factory.Factory factory, java.lang.String name) {
        spoon.reflect.declaration.CtField<?> field = factory.Core().createField();
        field.setType(((spoon.reflect.reference.CtTypeReference) (factory.Type().booleanType())));
        field.setSimpleName(name);
        return field;
    }
}

