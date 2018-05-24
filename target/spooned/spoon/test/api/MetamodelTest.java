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
    public void testRuntimeMetamodel() {
        spoon.test.metamodel.SpoonMetaModel testMetaModel = new spoon.test.metamodel.SpoonMetaModel(new java.io.File("src/main/java"));
        java.util.Map<java.lang.String, spoon.test.metamodel.MetamodelConcept> expectedTypesByName = new java.util.HashMap<>();
        testMetaModel.getConcepts().forEach(( t) -> {
            if ((t.getKind()) == (spoon.test.metamodel.MMTypeKind.LEAF)) {
                expectedTypesByName.put(t.getName(), t);
            }
        });
        for (spoon.Metamodel.Type type : spoon.Metamodel.getAllMetamodelTypes()) {
            spoon.test.metamodel.MetamodelConcept expectedType = expectedTypesByName.remove(type.getName());
            org.junit.Assert.assertSame(expectedType.getModelClass().getActualClass(), type.getModelClass());
            org.junit.Assert.assertSame(expectedType.getModelInterface().getActualClass(), type.getModelInterface());
            java.util.Map<spoon.reflect.path.CtRole, spoon.test.metamodel.MetamodelProperty> expectedRoleToField = new java.util.HashMap<>(expectedType.getRoleToProperty());
            for (spoon.Metamodel.Field field : type.getFields()) {
                spoon.test.metamodel.MetamodelProperty expectedField = expectedRoleToField.remove(field.getRole());
                org.junit.Assert.assertSame((("Field " + expectedField) + ".derived"), expectedField.isDerived(), field.isDerived());
                org.junit.Assert.assertSame((("Field " + expectedField) + ".unsettable"), expectedField.isUnsettable(), field.isUnsettable());
            }
            org.junit.Assert.assertTrue(((("These Metamodel.Field instances are missing on Type " + (type.getName())) + ": ") + (expectedRoleToField.keySet())), expectedRoleToField.isEmpty());
        }
        org.junit.Assert.assertTrue(("These Metamodel.Type instances are missing: " + (expectedTypesByName.keySet())), expectedTypesByName.isEmpty());
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
        spoon.test.metamodel.SpoonMetaModel runtimeMM = new spoon.test.metamodel.SpoonMetaModel();
        java.util.Collection<spoon.test.metamodel.MetamodelConcept> concepts = runtimeMM.getConcepts();
        spoon.test.metamodel.SpoonMetaModel sourceBasedMM = new spoon.test.metamodel.SpoonMetaModel(new java.io.File("src/main/java"));
        java.util.Map<java.lang.String, spoon.test.metamodel.MetamodelConcept> expectedConceptsByName = new java.util.HashMap<>();
        sourceBasedMM.getConcepts().forEach(( c) -> {
            expectedConceptsByName.put(c.getName(), c);
        });
        for (spoon.test.metamodel.MetamodelConcept runtimeConcept : concepts) {
            spoon.test.metamodel.MetamodelConcept expectedConcept = expectedConceptsByName.remove(runtimeConcept.getName());
            org.junit.Assert.assertNotNull(expectedConcept);
            assertConceptsEqual(expectedConcept, runtimeConcept);
        }
        org.junit.Assert.assertEquals(0, expectedConceptsByName.size());
    }

    private void assertConceptsEqual(spoon.test.metamodel.MetamodelConcept expectedConcept, spoon.test.metamodel.MetamodelConcept runtimeConcept) {
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
        java.util.Map<spoon.reflect.path.CtRole, spoon.test.metamodel.MetamodelProperty> expectedRoleToProperty = new java.util.HashMap(expectedConcept.getRoleToProperty());
        for (java.util.Map.Entry<spoon.reflect.path.CtRole, spoon.test.metamodel.MetamodelProperty> e : runtimeConcept.getRoleToProperty().entrySet()) {
            spoon.test.metamodel.MetamodelProperty runtimeProperty = e.getValue();
            spoon.test.metamodel.MetamodelProperty expectedProperty = expectedRoleToProperty.remove(e.getKey());
            assertPropertiesEqual(expectedProperty, runtimeProperty);
        }
        org.junit.Assert.assertEquals(0, expectedRoleToProperty.size());
    }

    private void assertPropertiesEqual(spoon.test.metamodel.MetamodelProperty expectedProperty, spoon.test.metamodel.MetamodelProperty runtimeProperty) {
        org.junit.Assert.assertSame(expectedProperty.getRole(), runtimeProperty.getRole());
        org.junit.Assert.assertEquals(expectedProperty.getName(), runtimeProperty.getName());
        org.junit.Assert.assertEquals(expectedProperty.getItemValueType().getActualClass(), runtimeProperty.getItemValueType().getActualClass());
        org.junit.Assert.assertEquals(expectedProperty.getOwnerConcept().getName(), runtimeProperty.getOwnerConcept().getName());
        org.junit.Assert.assertSame(expectedProperty.getValueContainerType(), runtimeProperty.getValueContainerType());
        org.junit.Assert.assertEquals(expectedProperty.getValueType(), runtimeProperty.getValueType());
        org.junit.Assert.assertEquals(expectedProperty.isDerived(), runtimeProperty.isDerived());
        org.junit.Assert.assertEquals(expectedProperty.isUnsettable(), runtimeProperty.isUnsettable());
    }
}

