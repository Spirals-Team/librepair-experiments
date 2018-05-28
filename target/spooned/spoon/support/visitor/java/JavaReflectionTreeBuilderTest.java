package spoon.support.visitor.java;


public class JavaReflectionTreeBuilderTest {
    @org.junit.Test
    public void testScannerClass() throws java.lang.Exception {
        final spoon.reflect.declaration.CtClass<java.lang.Class> aClass = new spoon.support.visitor.java.JavaReflectionTreeBuilder(spoon.testing.utils.ModelUtils.createFactory()).scan(java.lang.Class.class);
        org.junit.Assert.assertNotNull(aClass);
        org.junit.Assert.assertEquals("java.lang.Class", aClass.getQualifiedName());
        org.junit.Assert.assertNull(aClass.getSuperclass());
        org.junit.Assert.assertTrue(((aClass.getSuperInterfaces().size()) > 0));
        org.junit.Assert.assertTrue(((aClass.getFields().size()) > 0));
        org.junit.Assert.assertTrue(((aClass.getMethods().size()) > 0));
        org.junit.Assert.assertTrue(((aClass.getNestedTypes().size()) > 0));
        org.junit.Assert.assertTrue(aClass.isShadow());
    }

    @org.junit.Test
    public void testScannerEnum() throws java.lang.Exception {
        final spoon.reflect.declaration.CtEnum<java.time.format.TextStyle> anEnum = new spoon.support.visitor.java.JavaReflectionTreeBuilder(spoon.testing.utils.ModelUtils.createFactory()).scan(java.time.format.TextStyle.class);
        org.junit.Assert.assertNotNull(anEnum);
        org.junit.Assert.assertEquals("java.time.format.TextStyle", anEnum.getQualifiedName());
        org.junit.Assert.assertNotNull(anEnum.getSuperclass());
        org.junit.Assert.assertTrue(((anEnum.getFields().size()) > 0));
        org.junit.Assert.assertTrue(((anEnum.getEnumValues().size()) > 0));
        org.junit.Assert.assertTrue(((anEnum.getMethods().size()) > 0));
        org.junit.Assert.assertTrue(anEnum.isShadow());
    }

    @org.junit.Test
    public void testScannerInterface() throws java.lang.Exception {
        final spoon.reflect.declaration.CtInterface<spoon.reflect.code.CtLambda> anInterface = new spoon.support.visitor.java.JavaReflectionTreeBuilder(spoon.testing.utils.ModelUtils.createFactory()).scan(spoon.reflect.code.CtLambda.class);
        org.junit.Assert.assertNotNull(anInterface);
        org.junit.Assert.assertEquals("spoon.reflect.code.CtLambda", anInterface.getQualifiedName());
        org.junit.Assert.assertNull(anInterface.getSuperclass());
        org.junit.Assert.assertTrue(((anInterface.getSuperInterfaces().size()) > 0));
        org.junit.Assert.assertTrue(((anInterface.getMethods().size()) > 0));
        org.junit.Assert.assertTrue(anInterface.isShadow());
    }

    @org.junit.Test
    public void testScannerAnnotation() throws java.lang.Exception {
        final spoon.reflect.declaration.CtAnnotationType<java.lang.SuppressWarnings> suppressWarning = new spoon.support.visitor.java.JavaReflectionTreeBuilder(spoon.testing.utils.ModelUtils.createFactory()).scan(java.lang.SuppressWarnings.class);
        org.junit.Assert.assertNotNull(suppressWarning);
        org.junit.Assert.assertEquals("java.lang.SuppressWarnings", suppressWarning.getQualifiedName());
        org.junit.Assert.assertTrue(((suppressWarning.getAnnotations().size()) > 0));
        org.junit.Assert.assertTrue(((suppressWarning.getFields().size()) > 0));
        org.junit.Assert.assertTrue(suppressWarning.isShadow());
        org.junit.Assert.assertNotNull(suppressWarning.getAnnotation(java.lang.annotation.Retention.class));
        org.junit.Assert.assertEquals("SOURCE", suppressWarning.getAnnotation(java.lang.annotation.Retention.class).value().toString());
    }

    @org.junit.Test
    public void testScannerGenericsInClass() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<spoon.test.generics.ComparableComparatorBug> aType = new spoon.support.visitor.java.JavaReflectionTreeBuilder(spoon.testing.utils.ModelUtils.createFactory()).scan(spoon.test.generics.ComparableComparatorBug.class);
        org.junit.Assert.assertNotNull(aType);
        org.junit.Assert.assertEquals(1, aType.getFormalCtTypeParameters().size());
        spoon.reflect.declaration.CtTypeParameter ctTypeParameter = aType.getFormalCtTypeParameters().get(0);
        org.junit.Assert.assertEquals("E extends java.lang.Comparable<? super E>", ctTypeParameter.toString());
        org.junit.Assert.assertEquals(1, ctTypeParameter.getSuperclass().getActualTypeArguments().size());
        org.junit.Assert.assertTrue(((ctTypeParameter.getSuperclass().getActualTypeArguments().get(0)) instanceof spoon.reflect.reference.CtTypeParameterReference));
        org.junit.Assert.assertEquals("? super E", ctTypeParameter.getSuperclass().getActualTypeArguments().get(0).toString());
    }

    @org.junit.Test
    public void testScannerArrayReference() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<java.net.URLClassLoader> aType = new spoon.support.visitor.java.JavaReflectionTreeBuilder(spoon.testing.utils.ModelUtils.createFactory()).scan(java.net.URLClassLoader.class);
        org.junit.Assert.assertNotNull(aType);
        final spoon.reflect.declaration.CtMethod<java.lang.Object> aMethod = aType.getMethod("getURLs");
        org.junit.Assert.assertTrue(((aMethod.getType()) instanceof spoon.reflect.reference.CtArrayTypeReference));
        final spoon.reflect.reference.CtArrayTypeReference<java.lang.Object> arrayRef = ((spoon.reflect.reference.CtArrayTypeReference<java.lang.Object>) (aMethod.getType()));
        org.junit.Assert.assertNull(arrayRef.getPackage());
        org.junit.Assert.assertNull(arrayRef.getDeclaringType());
        org.junit.Assert.assertNotNull(arrayRef.getComponentType());
    }

    @org.junit.Test
    public void testDeclaredMethods() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<java.lang.StringBuilder> type = new spoon.support.visitor.java.JavaReflectionTreeBuilder(spoon.testing.utils.ModelUtils.createFactory()).scan(java.lang.StringBuilder.class);
        org.junit.Assert.assertNotNull(type);
        org.junit.Assert.assertEquals(0, type.getMethods().stream().filter(( ctMethod) -> "java.lang.AbstractStringBuilder".equals(ctMethod.getType().getQualifiedName())).collect(java.util.stream.Collectors.toList()).size());
        org.junit.Assert.assertNotNull(type.getMethod("reverse"));
        org.junit.Assert.assertNotNull(type.getMethod("readObject", type.getFactory().Type().createReference(java.io.ObjectInputStream.class)));
    }

    @org.junit.Test
    public void testDeclaredField() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<java.net.CookieManager> aType = new spoon.support.visitor.java.JavaReflectionTreeBuilder(spoon.testing.utils.ModelUtils.createFactory()).scan(java.net.CookieManager.class);
        org.junit.Assert.assertNotNull(aType);
        org.junit.Assert.assertEquals(2, aType.getFields().size());
    }

    @org.junit.Test
    public void testDeclaredConstructor() throws java.lang.Exception {
        final spoon.reflect.declaration.CtType<spoon.support.compiler.jdt.JDTSnippetCompiler> aType = new spoon.support.visitor.java.JavaReflectionTreeBuilder(spoon.testing.utils.ModelUtils.createFactory()).scan(spoon.support.compiler.jdt.JDTSnippetCompiler.class);
        org.junit.Assert.assertNotNull(aType);
        org.junit.Assert.assertEquals(1, ((spoon.reflect.declaration.CtClass<spoon.support.compiler.jdt.JDTSnippetCompiler>) (aType)).getConstructors().size());
    }

    @org.junit.Test
    public void testShadowModelEqualsNormalModel() {
        spoon.test.metamodel.SpoonMetaModel metaModel = new spoon.test.metamodel.SpoonMetaModel(new java.io.File("src/main/java"));
        java.util.List<java.lang.String> allProblems = new java.util.ArrayList<>();
        for (spoon.test.metamodel.MetamodelConcept concept : metaModel.getConcepts()) {
            allProblems.addAll(checkShadowTypeIsEqual(concept.getModelClass()));
            allProblems.addAll(checkShadowTypeIsEqual(concept.getModelInterface()));
        }
        org.junit.Assert.assertTrue(((("Found " + (allProblems.size())) + " problems:\n") + (java.lang.String.join("\n", allProblems))), allProblems.isEmpty());
    }

    private java.util.List<java.lang.String> checkShadowTypeIsEqual(spoon.reflect.declaration.CtType<?> type) {
        if (type == null) {
            return java.util.Collections.emptyList();
        }
        spoon.reflect.factory.Factory shadowFactory = spoon.testing.utils.ModelUtils.createFactory();
        spoon.reflect.reference.CtTypeReference<?> shadowTypeRef = shadowFactory.Type().createReference(type.getActualClass());
        spoon.reflect.declaration.CtType<?> shadowType = shadowTypeRef.getTypeDeclaration();
        org.junit.Assert.assertFalse(type.isShadow());
        org.junit.Assert.assertTrue(shadowType.isShadow());
        spoon.support.visitor.java.JavaReflectionTreeBuilderTest.ShadowEqualsVisitor sev = new spoon.support.visitor.java.JavaReflectionTreeBuilderTest.ShadowEqualsVisitor(new java.util.HashSet<>(java.util.Arrays.asList(spoon.reflect.path.CtRole.STATEMENT, spoon.reflect.path.CtRole.DEFAULT_EXPRESSION, spoon.reflect.path.CtRole.COMMENT)));
        return sev.checkDiffs(type, shadowType);
    }

    private static class Diff {
        spoon.reflect.declaration.CtElement element;

        spoon.reflect.declaration.CtElement other;

        java.util.Set<spoon.reflect.path.CtRole> roles = new java.util.HashSet<>();

        Diff(spoon.reflect.declaration.CtElement element, spoon.reflect.declaration.CtElement other) {
            super();
            this.element = element;
            this.other = other;
        }
    }

    private static class ShadowEqualsChecker extends spoon.support.visitor.equals.EqualsChecker {
        spoon.support.visitor.java.JavaReflectionTreeBuilderTest.Diff currentDiff;

        java.util.List<spoon.support.visitor.java.JavaReflectionTreeBuilderTest.Diff> differences = new java.util.ArrayList<>();

        @java.lang.Override
        protected void setNotEqual(spoon.reflect.path.CtRole role) {
            if (role == (spoon.reflect.path.CtRole.MODIFIER)) {
                if ((currentDiff.element) instanceof spoon.reflect.declaration.CtTypeMember) {
                    spoon.reflect.declaration.CtTypeMember tm = ((spoon.reflect.declaration.CtTypeMember) (currentDiff.element));
                    spoon.reflect.declaration.CtType<?> type = tm.getDeclaringType();
                    if (type != null) {
                        java.util.Set<spoon.reflect.declaration.ModifierKind> elementModifiers = ((spoon.reflect.declaration.CtModifiable) (currentDiff.element)).getModifiers();
                        java.util.Set<spoon.reflect.declaration.ModifierKind> otherModifiers = ((spoon.reflect.declaration.CtModifiable) (currentDiff.other)).getModifiers();
                        if (type.isInterface()) {
                            if (removeModifiers(elementModifiers, spoon.reflect.declaration.ModifierKind.PUBLIC, spoon.reflect.declaration.ModifierKind.ABSTRACT).equals(removeModifiers(elementModifiers, spoon.reflect.declaration.ModifierKind.PUBLIC, spoon.reflect.declaration.ModifierKind.ABSTRACT))) {
                                return;
                            }
                        }else
                            if (type.isEnum()) {
                                spoon.reflect.declaration.CtType<?> type2 = type.getDeclaringType();
                                if (type2 != null) {
                                    if (type2.isInterface()) {
                                        if (removeModifiers(elementModifiers, spoon.reflect.declaration.ModifierKind.PUBLIC).equals(removeModifiers(elementModifiers, spoon.reflect.declaration.ModifierKind.PUBLIC))) {
                                            return;
                                        }
                                    }
                                }
                            }

                    }
                }
            }
            currentDiff.roles.add(role);
        }

        private java.util.Set<spoon.reflect.declaration.ModifierKind> removeModifiers(java.util.Set<spoon.reflect.declaration.ModifierKind> elementModifiers, spoon.reflect.declaration.ModifierKind... modifiers) {
            java.util.Set<spoon.reflect.declaration.ModifierKind> copy = new java.util.HashSet<>(elementModifiers);
            for (spoon.reflect.declaration.ModifierKind modifierKind : modifiers) {
                copy.remove(modifierKind);
            }
            return copy;
        }

        @java.lang.Override
        public void scan(spoon.reflect.declaration.CtElement element) {
            currentDiff = new spoon.support.visitor.java.JavaReflectionTreeBuilderTest.Diff(element, other);
            super.scan(element);
            if ((currentDiff.roles.size()) > 0) {
                differences.add(currentDiff);
            }
        }
    }

    private static class ShadowEqualsVisitor extends spoon.support.visitor.equals.EqualsVisitor {
        spoon.reflect.declaration.CtElement rootOfOther;

        spoon.reflect.path.CtElementPathBuilder pathBuilder = new spoon.reflect.path.CtElementPathBuilder();

        java.util.List<java.lang.String> differences;

        java.util.Set<spoon.reflect.path.CtRole> ignoredRoles;

        ShadowEqualsVisitor(java.util.Set<spoon.reflect.path.CtRole> ignoredRoles) {
            super(new spoon.support.visitor.java.JavaReflectionTreeBuilderTest.ShadowEqualsChecker());
            this.ignoredRoles = ignoredRoles;
        }

        java.util.List<spoon.support.visitor.java.JavaReflectionTreeBuilderTest.Diff> getDiffs() {
            return ((spoon.support.visitor.java.JavaReflectionTreeBuilderTest.ShadowEqualsChecker) (checker)).differences;
        }

        @java.lang.Override
        protected boolean fail(spoon.reflect.path.CtRole role, java.lang.Object element, java.lang.Object other) {
            if (role == null) {
                this.isNotEqual = false;
                return false;
            }
            if (ignoredRoles.contains(role)) {
                this.isNotEqual = false;
                return false;
            }
            if ((element instanceof spoon.reflect.declaration.CtEnumValue) && (role == (spoon.reflect.path.CtRole.VALUE))) {
                this.isNotEqual = false;
                return false;
            }
            spoon.reflect.declaration.CtElement parentOfOther = stack.peek();
            try {
                differences.add((((((((("Difference on path: " + (pathBuilder.fromElement(parentOfOther, rootOfOther).toString())) + "#") + (role.getCamelCaseName())) + "\nShadow: ") + (java.lang.String.valueOf(other))) + "\nNormal: ") + (java.lang.String.valueOf(element))) + "\n"));
            } catch (spoon.reflect.path.CtPathException e) {
                throw new spoon.SpoonException(e);
            }
            return false;
        }

        @java.lang.Override
        public void biScan(spoon.reflect.path.CtRole role, spoon.reflect.declaration.CtElement element, spoon.reflect.declaration.CtElement other) {
            if (element instanceof spoon.reflect.declaration.CtParameter) {
                spoon.reflect.declaration.CtParameter param = ((spoon.reflect.declaration.CtParameter) (element));
                spoon.reflect.declaration.CtParameter otherParam = ((spoon.reflect.declaration.CtParameter) (other));
                if (otherParam.getSimpleName().startsWith("arg")) {
                    otherParam.setSimpleName(param.getSimpleName());
                }
                if (param.isFinal()) {
                    otherParam.addModifier(spoon.reflect.declaration.ModifierKind.FINAL);
                }
            }
            if (element instanceof spoon.reflect.declaration.CtAnnotation) {
                spoon.reflect.declaration.CtAnnotation myAnnotation = ((spoon.reflect.declaration.CtAnnotation) (element));
                if (myAnnotation.getAnnotationType().getQualifiedName().equals(java.lang.Override.class.getName())) {
                    return;
                }
                if (myAnnotation.getAnnotationType().getQualifiedName().equals(spoon.reflect.visitor.Root.class.getName())) {
                    return;
                }
            }
            if ((((role == (spoon.reflect.path.CtRole.SUPER_TYPE)) && (other == null)) && (element != null)) && (((spoon.reflect.reference.CtTypeReference<?>) (element)).getQualifiedName().equals(java.lang.Object.class.getName()))) {
                return;
            }
            super.biScan(role, element, other);
        }

        @java.lang.Override
        protected void biScan(spoon.reflect.path.CtRole role, java.util.Collection<? extends spoon.reflect.declaration.CtElement> elements, java.util.Collection<? extends spoon.reflect.declaration.CtElement> others) {
            if (role == (spoon.reflect.path.CtRole.TYPE_MEMBER)) {
                java.util.Map<java.lang.String, spoon.reflect.declaration.CtTypeMember> elementsByName = spoon.support.visitor.java.JavaReflectionTreeBuilderTest.groupTypeMembersBySignature(((java.util.Collection) (elements)));
                java.util.Map<java.lang.String, spoon.reflect.declaration.CtTypeMember> othersByName = spoon.support.visitor.java.JavaReflectionTreeBuilderTest.groupTypeMembersBySignature(((java.util.Collection) (others)));
                for (java.util.Map.Entry<java.lang.String, spoon.reflect.declaration.CtTypeMember> e : elementsByName.entrySet()) {
                    java.lang.String name = e.getKey();
                    spoon.reflect.declaration.CtTypeMember other = othersByName.remove(name);
                    if (other == null) {
                        if (e.getValue().isImplicit()) {
                            continue;
                        }
                        differences.add(("Missing shadow typeMember: " + name));
                    }
                    biScan(role, e.getValue(), other);
                }
                for (java.util.Map.Entry<java.lang.String, spoon.reflect.declaration.CtTypeMember> e : othersByName.entrySet()) {
                    differences.add(("Unexpected shadow typeMember: " + (e.getKey())));
                }
                return;
            }
            if (role == (spoon.reflect.path.CtRole.ANNOTATION)) {
                java.util.List<spoon.reflect.declaration.CtAnnotation<?>> fileteredElements = ((java.util.List<spoon.reflect.declaration.CtAnnotation<?>>) (elements)).stream().filter(( a) -> {
                    spoon.reflect.reference.CtTypeReference<?> at = ((spoon.reflect.reference.CtTypeReference) (a.getAnnotationType()));
                    java.lang.Class ac = at.getActualClass();
                    if (((ac == (java.lang.Override.class)) || (ac == (java.lang.SuppressWarnings.class))) || (ac == (spoon.reflect.visitor.Root.class))) {
                        return false;
                    }
                    return true;
                }).collect(java.util.stream.Collectors.toList());
                super.biScan(role, fileteredElements, others);
                return;
            }
            super.biScan(role, elements, others);
        }

        public java.util.List<java.lang.String> checkDiffs(spoon.reflect.declaration.CtType<?> type, spoon.reflect.declaration.CtType<?> shadowType) {
            differences = new java.util.ArrayList<>();
            rootOfOther = shadowType;
            biScan(null, type, shadowType);
            for (spoon.support.visitor.java.JavaReflectionTreeBuilderTest.Diff diff : getDiffs()) {
                try {
                    spoon.reflect.declaration.CtElement parentOf;
                    spoon.reflect.declaration.CtElement rootOf;
                    if ((diff.other) != null) {
                        parentOf = diff.other.getParent();
                        rootOf = rootOfOther;
                    }else {
                        parentOf = diff.element.getParent();
                        rootOf = type;
                    }
                    differences.add((((((((("Diff on path: " + (pathBuilder.fromElement(parentOf, rootOf).toString())) + "#") + (diff.roles.stream().map(spoon.reflect.path.CtRole::getCamelCaseName).collect(java.util.stream.Collectors.joining(", ", "[", "]")))) + "\nShadow: ") + (java.lang.String.valueOf(diff.other))) + "\nNormal: ") + (java.lang.String.valueOf(diff.element))) + "\n"));
                } catch (spoon.reflect.path.CtPathException e) {
                    throw new spoon.SpoonException(e);
                }
            }
            return differences;
        }
    }

    private static java.util.Map<java.lang.String, spoon.reflect.declaration.CtTypeMember> groupTypeMembersBySignature(java.util.Collection<spoon.reflect.declaration.CtTypeMember> typeMembers) {
        java.util.Map<java.lang.String, spoon.reflect.declaration.CtTypeMember> typeMembersByName = new java.util.HashMap<>();
        for (spoon.reflect.declaration.CtTypeMember tm : typeMembers) {
            java.lang.String name;
            if (tm instanceof spoon.reflect.declaration.CtExecutable) {
                spoon.reflect.declaration.CtExecutable<?> exec = ((spoon.reflect.declaration.CtExecutable) (tm));
                name = exec.getSignature();
            }else {
                name = tm.getSimpleName();
            }
            spoon.reflect.declaration.CtTypeMember conflictTM = typeMembersByName.put(name, tm);
            if (conflictTM != null) {
                throw new spoon.SpoonException(((("There are two type members with name: " + name) + " in ") + (tm.getParent(spoon.reflect.declaration.CtType.class).getQualifiedName())));
            }
        }
        return typeMembersByName;
    }

    @org.junit.Test
    public void testSuperInterfaceActualTypeArgumentsByJavaReflectionTreeBuilder() {
        final spoon.reflect.declaration.CtType<spoon.support.reflect.code.CtConditionalImpl> aType = new spoon.support.visitor.java.JavaReflectionTreeBuilder(spoon.testing.utils.ModelUtils.createFactory()).scan(spoon.support.reflect.code.CtConditionalImpl.class);
        spoon.reflect.reference.CtTypeReference<?> ifaceRef = aType.getSuperInterfaces().iterator().next();
        org.junit.Assert.assertEquals(spoon.reflect.code.CtConditional.class.getName(), ifaceRef.getQualifiedName());
        org.junit.Assert.assertEquals(1, ifaceRef.getActualTypeArguments().size());
        spoon.reflect.reference.CtTypeReference<?> typeArg = ifaceRef.getActualTypeArguments().get(0);
        org.junit.Assert.assertEquals("T", typeArg.getSimpleName());
        org.junit.Assert.assertTrue((typeArg instanceof spoon.reflect.reference.CtTypeParameterReference));
    }

    @org.junit.Test
    public void testSuperInterfaceActualTypeArgumentsByCtTypeReferenceImpl() {
        spoon.reflect.factory.TypeFactory typeFactory = spoon.testing.utils.ModelUtils.createFactory().Type();
        spoon.reflect.reference.CtTypeReference<?> aTypeRef = typeFactory.createReference(spoon.support.reflect.code.CtConditionalImpl.class);
        spoon.reflect.reference.CtTypeReference<?> ifaceRef = aTypeRef.getSuperInterfaces().iterator().next();
        org.junit.Assert.assertEquals(spoon.reflect.code.CtConditional.class.getName(), ifaceRef.getQualifiedName());
        org.junit.Assert.assertEquals(1, ifaceRef.getActualTypeArguments().size());
        spoon.reflect.reference.CtTypeReference<?> typeArg = ifaceRef.getActualTypeArguments().get(0);
        org.junit.Assert.assertEquals("T", typeArg.getSimpleName());
        org.junit.Assert.assertTrue((typeArg instanceof spoon.reflect.reference.CtTypeParameterReference));
    }

    @org.junit.Test
    public void testSuperInterfaceCorrectActualTypeArgumentsByCtTypeReferenceImpl() {
        spoon.reflect.factory.TypeFactory typeFactory = spoon.testing.utils.ModelUtils.createFactory().Type();
        spoon.reflect.reference.CtTypeReference<?> aTypeRef = typeFactory.createReference(spoon.reflect.declaration.CtField.class);
        spoon.reflect.declaration.CtType aType = aTypeRef.getTypeDeclaration();
        for (spoon.reflect.reference.CtTypeReference<?> ifaceRef : aType.getSuperInterfaces()) {
            for (spoon.reflect.reference.CtTypeReference<?> actTypeRef : ifaceRef.getActualTypeArguments()) {
                if (actTypeRef instanceof spoon.reflect.reference.CtTypeParameterReference) {
                    spoon.reflect.reference.CtTypeParameterReference actTypeParamRef = ((spoon.reflect.reference.CtTypeParameterReference) (actTypeRef));
                    spoon.reflect.declaration.CtTypeParameter typeParam = actTypeParamRef.getDeclaration();
                    org.junit.Assert.assertNotNull(typeParam);
                    org.junit.Assert.assertSame(aType, typeParam.getTypeParameterDeclarer());
                }
            }
        }
    }

    @org.junit.Test
    public void testSuperInterfaceQName() {
        spoon.reflect.factory.TypeFactory typeFactory = spoon.testing.utils.ModelUtils.createFactory().Type();
        spoon.reflect.reference.CtTypeReference<?> aTypeRef = typeFactory.createReference(spoon.reflect.code.CtExpression.class);
        spoon.reflect.declaration.CtType aType = aTypeRef.getTypeDeclaration();
        for (spoon.reflect.reference.CtTypeReference<?> ifaceRef : aType.getSuperInterfaces()) {
            org.junit.Assert.assertNotNull(((ifaceRef.getQualifiedName()) + " doesn't exist?"), ifaceRef.getActualClass());
            org.junit.Assert.assertSame(aType, ifaceRef.getParent());
        }
        for (spoon.reflect.reference.CtTypeReference<?> ifaceRef : aTypeRef.getSuperInterfaces()) {
            org.junit.Assert.assertNotNull(((ifaceRef.getQualifiedName()) + " doesn't exist?"), ifaceRef.getActualClass());
            org.junit.Assert.assertSame(aType, ifaceRef.getParent());
        }
    }

    @org.junit.Test
    public void testSuperClass() {
        spoon.reflect.factory.TypeFactory typeFactory = spoon.testing.utils.ModelUtils.createFactory().Type();
        spoon.reflect.reference.CtTypeReference<?> aTypeRef = typeFactory.createReference(spoon.support.reflect.declaration.CtEnumValueImpl.class);
        spoon.reflect.declaration.CtType aType = aTypeRef.getTypeDeclaration();
        spoon.reflect.reference.CtTypeReference<?> superClass = aType.getSuperclass();
        org.junit.Assert.assertEquals(spoon.support.reflect.declaration.CtFieldImpl.class.getName(), superClass.getQualifiedName());
        org.junit.Assert.assertSame(aType, superClass.getParent());
        org.junit.Assert.assertEquals(1, superClass.getActualTypeArguments().size());
        spoon.reflect.reference.CtTypeParameterReference paramRef = ((spoon.reflect.reference.CtTypeParameterReference) (superClass.getActualTypeArguments().get(0)));
        org.junit.Assert.assertSame(aType.getFormalCtTypeParameters().get(0), paramRef.getDeclaration());
    }

    @org.junit.Test
    public void testSuperOfActualTypeArgumentsOfReturnTypeOfMethod() throws java.lang.Exception {
        java.util.function.Consumer<spoon.reflect.declaration.CtType<?>> checker = ( type) -> {
            {
                spoon.reflect.declaration.CtMethod method = type.getMethodsByName("setAssignment").get(0);
                spoon.reflect.reference.CtTypeReference<?> paramType = ((spoon.reflect.declaration.CtParameter<?>) (method.getParameters().get(0))).getType();
                org.junit.Assert.assertEquals(spoon.reflect.code.CtExpression.class.getName(), paramType.getQualifiedName());
                org.junit.Assert.assertEquals(1, paramType.getActualTypeArguments().size());
                spoon.reflect.reference.CtTypeParameterReference actTypeArgOfReturnType = ((spoon.reflect.reference.CtTypeParameterReference) (paramType.getActualTypeArguments().get(0)));
                org.junit.Assert.assertEquals("A", actTypeArgOfReturnType.getSimpleName());
                spoon.reflect.reference.CtTypeReference<?> boundType = actTypeArgOfReturnType.getBoundingType();
                org.junit.Assert.assertEquals("T", boundType.getSimpleName());
                org.junit.Assert.assertTrue((boundType instanceof spoon.reflect.reference.CtTypeParameterReference));
            }
            {
                spoon.reflect.declaration.CtMethod method = type.getMethodsByName("getAssignment").get(0);
                spoon.reflect.reference.CtTypeReference<?> returnType = method.getType();
                org.junit.Assert.assertEquals(spoon.reflect.code.CtExpression.class.getName(), returnType.getQualifiedName());
                org.junit.Assert.assertEquals(1, returnType.getActualTypeArguments().size());
                spoon.reflect.reference.CtTypeParameterReference actTypeArgOfReturnType = ((spoon.reflect.reference.CtTypeParameterReference) (returnType.getActualTypeArguments().get(0)));
                org.junit.Assert.assertEquals("A", actTypeArgOfReturnType.getSimpleName());
                spoon.reflect.reference.CtTypeReference<?> boundType = actTypeArgOfReturnType.getBoundingType();
                org.junit.Assert.assertEquals("T", boundType.getSimpleName());
                org.junit.Assert.assertTrue((boundType instanceof spoon.reflect.reference.CtTypeParameterReference));
            }
        };
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        launcher.addInputResource(new spoon.support.compiler.FileSystemFile(new java.io.File("./src/main/java/spoon/support/reflect/code/CtAssignmentImpl.java")));
        launcher.buildModel();
        spoon.reflect.declaration.CtClass<?> classFromSources = launcher.getFactory().Class().get(spoon.support.reflect.code.CtAssignmentImpl.class.getName());
        org.junit.Assert.assertFalse(classFromSources.isShadow());
        checker.accept(classFromSources);
        spoon.reflect.declaration.CtType<?> classFromReflection = spoon.testing.utils.ModelUtils.createFactory().Class().get(spoon.support.reflect.code.CtAssignmentImpl.class);
        org.junit.Assert.assertTrue(classFromReflection.isShadow());
        checker.accept(classFromReflection);
    }

    @org.junit.Test
    public void testTypeParameterCtConditionnal() {
        spoon.reflect.factory.Factory factory = spoon.testing.utils.ModelUtils.createFactory();
        spoon.reflect.reference.CtTypeReference typeReference = factory.Type().createReference(spoon.support.reflect.code.CtConditionalImpl.class);
        spoon.reflect.declaration.CtType shadowType = typeReference.getTypeDeclaration();
        org.junit.Assert.assertEquals(1, shadowType.getFormalCtTypeParameters().size());
        spoon.reflect.declaration.CtTypeParameter typeParameter = shadowType.getFormalCtTypeParameters().get(0);
        org.junit.Assert.assertEquals("T", typeParameter.getSimpleName());
        org.junit.Assert.assertTrue(((typeParameter.getSuperclass()) == null));
    }
}

