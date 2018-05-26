package spoon.template;


public abstract class Substitution {
    private Substitution() {
    }

    public static <T extends spoon.template.Template<?>> void insertAll(spoon.reflect.declaration.CtType<?> targetType, T template) {
        spoon.reflect.declaration.CtClass<T> templateClass = spoon.template.Substitution.getTemplateCtClass(targetType, template);
        spoon.template.Substitution.insertAllSuperInterfaces(targetType, template);
        spoon.template.Substitution.insertAllMethods(targetType, template);
        spoon.template.Substitution.insertAllConstructors(targetType, template);
        for (spoon.reflect.declaration.CtTypeMember typeMember : templateClass.getTypeMembers()) {
            if (typeMember instanceof spoon.reflect.declaration.CtField) {
                spoon.template.Substitution.insertGeneratedField(targetType, template, ((spoon.reflect.declaration.CtField<?>) (typeMember)));
            }else
                if (typeMember instanceof spoon.reflect.declaration.CtType) {
                    spoon.template.Substitution.insertGeneratedNestedType(targetType, template, ((spoon.reflect.declaration.CtType) (typeMember)));
                }

        }
    }

    @java.lang.SuppressWarnings("unchecked")
    public static <T extends spoon.reflect.declaration.CtType<?>> T createTypeFromTemplate(java.lang.String qualifiedTypeName, spoon.reflect.declaration.CtType<?> templateOfType, java.util.Map<java.lang.String, java.lang.Object> templateParameters) {
        final spoon.reflect.factory.Factory f = templateOfType.getFactory();
        spoon.reflect.reference.CtTypeReference<T> typeRef = f.Type().createReference(qualifiedTypeName);
        spoon.reflect.declaration.CtPackage targetPackage = f.Package().getOrCreate(typeRef.getPackage().getSimpleName());
        final java.util.Map<java.lang.String, java.lang.Object> extendedParams = new java.util.HashMap<java.lang.String, java.lang.Object>(templateParameters);
        extendedParams.put(templateOfType.getSimpleName(), typeRef);
        java.util.List<spoon.reflect.declaration.CtType<?>> generated = ((java.util.List) (new spoon.support.template.SubstitutionVisitor(f, extendedParams).substitute(templateOfType.clone())));
        for (spoon.reflect.declaration.CtType<?> ctType : generated) {
            targetPackage.addType(ctType);
        }
        return ((T) (typeRef.getTypeDeclaration()));
    }

    public static void insertAllSuperInterfaces(spoon.reflect.declaration.CtType<?> targetType, spoon.template.Template<?> template) {
        spoon.reflect.declaration.CtClass<? extends spoon.template.Template<?>> sourceClass = spoon.template.Substitution.getTemplateCtClass(targetType, template);
        spoon.template.Substitution.insertAllSuperInterfaces(targetType, template, sourceClass);
    }

    static void insertAllSuperInterfaces(spoon.reflect.declaration.CtType<?> targetType, spoon.template.Template<?> template, spoon.reflect.declaration.CtClass<? extends spoon.template.Template<?>> sourceClass) {
        for (spoon.reflect.reference.CtTypeReference<?> t : sourceClass.getSuperInterfaces()) {
            if (!(t.equals(targetType.getFactory().Type().createReference(spoon.template.Template.class)))) {
                spoon.reflect.reference.CtTypeReference<?> t1 = t;
                if (spoon.support.template.Parameters.getNames(sourceClass).contains(t.getSimpleName())) {
                    java.lang.Object o = spoon.support.template.Parameters.getValue(template, t.getSimpleName(), null);
                    if (o instanceof spoon.reflect.reference.CtTypeReference) {
                        t1 = ((spoon.reflect.reference.CtTypeReference<?>) (o));
                    }else
                        if (o instanceof java.lang.Class) {
                            t1 = targetType.getFactory().Type().createReference(((java.lang.Class<?>) (o)));
                        }else
                            if (o instanceof java.lang.String) {
                                t1 = targetType.getFactory().Type().createReference(((java.lang.String) (o)));
                            }


                }
                if (!(t1.equals(targetType.getReference()))) {
                    java.lang.Class<?> c = null;
                    try {
                        c = t1.getActualClass();
                    } catch (java.lang.Exception e) {
                    }
                    if ((c != null) && (c.isInterface())) {
                        targetType.addSuperInterface(t1);
                    }
                    if (c == null) {
                        targetType.addSuperInterface(t1);
                    }
                }
            }
        }
    }

    public static void insertAllMethods(spoon.reflect.declaration.CtType<?> targetType, spoon.template.Template<?> template) {
        spoon.reflect.declaration.CtClass<?> sourceClass = spoon.template.Substitution.getTemplateCtClass(targetType, template);
        spoon.template.Substitution.insertAllMethods(targetType, template, sourceClass);
    }

    static void insertAllMethods(spoon.reflect.declaration.CtType<?> targetType, spoon.template.Template<?> template, spoon.reflect.declaration.CtClass<?> sourceClass) {
        java.util.Set<spoon.reflect.declaration.CtMethod<?>> methodsOfTemplate = sourceClass.getFactory().Type().get(spoon.template.Template.class).getMethods();
        for (spoon.reflect.declaration.CtMethod<?> m : sourceClass.getMethods()) {
            if ((m.getAnnotation(spoon.template.Local.class)) != null) {
                continue;
            }
            if ((m.getAnnotation(spoon.template.Parameter.class)) != null) {
                continue;
            }
            boolean isOverridingTemplateItf = false;
            for (spoon.reflect.declaration.CtMethod m2 : methodsOfTemplate) {
                if (m.isOverriding(m2)) {
                    isOverridingTemplateItf = true;
                }
            }
            if (isOverridingTemplateItf) {
                continue;
            }
            spoon.template.Substitution.insertMethod(targetType, template, m);
        }
    }

    public static void insertAllFields(spoon.reflect.declaration.CtType<?> targetType, spoon.template.Template<?> template) {
        spoon.reflect.declaration.CtClass<?> sourceClass = spoon.template.Substitution.getTemplateCtClass(targetType, template);
        for (spoon.reflect.declaration.CtTypeMember typeMember : sourceClass.getTypeMembers()) {
            if (typeMember instanceof spoon.reflect.declaration.CtField) {
                spoon.template.Substitution.insertGeneratedField(targetType, template, ((spoon.reflect.declaration.CtField<?>) (typeMember)));
            }
        }
    }

    static void insertGeneratedField(spoon.reflect.declaration.CtType<?> targetType, spoon.template.Template<?> template, spoon.reflect.declaration.CtField<?> field) {
        if ((field.getAnnotation(spoon.template.Local.class)) != null) {
            return;
        }
        if (spoon.support.template.Parameters.isParameterSource(field.getReference())) {
            return;
        }
        spoon.template.Substitution.insertField(targetType, template, field);
    }

    public static void insertAllNestedTypes(spoon.reflect.declaration.CtType<?> targetType, spoon.template.Template<?> template) {
        spoon.reflect.declaration.CtClass<?> sourceClass = spoon.template.Substitution.getTemplateCtClass(targetType, template);
        for (spoon.reflect.declaration.CtTypeMember typeMember : sourceClass.getTypeMembers()) {
            if (typeMember instanceof spoon.reflect.declaration.CtType) {
                spoon.template.Substitution.insertGeneratedNestedType(targetType, template, ((spoon.reflect.declaration.CtType<?>) (typeMember)));
            }
        }
    }

    static void insertGeneratedNestedType(spoon.reflect.declaration.CtType<?> targetType, spoon.template.Template<?> template, spoon.reflect.declaration.CtType<?> nestedType) {
        if ((nestedType.getAnnotation(spoon.template.Local.class)) != null) {
            return;
        }
        spoon.reflect.declaration.CtType<?> result = spoon.template.Substitution.substitute(targetType, template, ((spoon.reflect.declaration.CtType) (nestedType)));
        targetType.addNestedType(result);
    }

    public static void insertAllConstructors(spoon.reflect.declaration.CtType<?> targetType, spoon.template.Template<?> template) {
        spoon.reflect.declaration.CtClass<?> sourceClass = spoon.template.Substitution.getTemplateCtClass(targetType, template);
        spoon.template.Substitution.insertAllConstructors(targetType, template, sourceClass);
    }

    static void insertAllConstructors(spoon.reflect.declaration.CtType<?> targetType, spoon.template.Template<?> template, spoon.reflect.declaration.CtClass<?> sourceClass) {
        if (targetType instanceof spoon.reflect.declaration.CtClass) {
            for (spoon.reflect.declaration.CtConstructor<?> c : sourceClass.getConstructors()) {
                if (c.isImplicit()) {
                    continue;
                }
                if ((c.getAnnotation(spoon.template.Local.class)) != null) {
                    continue;
                }
                spoon.template.Substitution.insertConstructor(((spoon.reflect.declaration.CtClass<?>) (targetType)), template, c);
            }
        }
        if (targetType instanceof spoon.reflect.declaration.CtClass) {
            for (spoon.reflect.declaration.CtAnonymousExecutable e : sourceClass.getAnonymousExecutables()) {
                ((spoon.reflect.declaration.CtClass<?>) (targetType)).addAnonymousExecutable(spoon.template.Substitution.substitute(targetType, template, e));
            }
        }
    }

    public static <T> spoon.reflect.declaration.CtConstructor<T> insertConstructor(spoon.reflect.declaration.CtClass<T> targetClass, spoon.template.Template<?> template, spoon.reflect.declaration.CtMethod<?> sourceMethod) {
        if (targetClass instanceof spoon.reflect.declaration.CtInterface) {
            return null;
        }
        spoon.reflect.declaration.CtConstructor<T> newConstructor = targetClass.getFactory().Constructor().create(targetClass, sourceMethod);
        newConstructor = spoon.template.Substitution.substitute(targetClass, template, newConstructor);
        targetClass.addConstructor(newConstructor);
        return newConstructor;
    }

    public static <T> spoon.reflect.declaration.CtMethod<T> insertMethod(spoon.reflect.declaration.CtType<?> targetType, spoon.template.Template<?> template, spoon.reflect.declaration.CtMethod<T> sourceMethod) {
        spoon.reflect.declaration.CtMethod<T> newMethod = spoon.template.Substitution.substitute(targetType, template, sourceMethod);
        if (targetType instanceof spoon.reflect.declaration.CtInterface) {
            newMethod.setBody(null);
        }
        targetType.addMethod(newMethod);
        return newMethod;
    }

    @java.lang.SuppressWarnings("unchecked")
    public static <T> spoon.reflect.declaration.CtConstructor<T> insertConstructor(spoon.reflect.declaration.CtClass<T> targetClass, spoon.template.Template<?> template, spoon.reflect.declaration.CtConstructor<?> sourceConstructor) {
        spoon.reflect.declaration.CtConstructor<T> newConstrutor = spoon.template.Substitution.substitute(targetClass, template, ((spoon.reflect.declaration.CtConstructor<T>) (sourceConstructor)));
        if (newConstrutor.getParameters().isEmpty()) {
            spoon.reflect.declaration.CtConstructor<?> c = targetClass.getConstructor();
            if ((c != null) && (c.isImplicit())) {
                targetClass.removeConstructor(((spoon.reflect.declaration.CtConstructor<T>) (c)));
            }
        }
        targetClass.addConstructor(newConstrutor);
        return newConstrutor;
    }

    public static spoon.reflect.code.CtBlock<?> substituteMethodBody(spoon.reflect.declaration.CtClass<?> targetClass, spoon.template.Template<?> template, java.lang.String executableName, spoon.reflect.reference.CtTypeReference<?>... parameterTypes) {
        spoon.reflect.declaration.CtClass<?> sourceClass = spoon.template.Substitution.getTemplateCtClass(targetClass, template);
        spoon.reflect.declaration.CtExecutable<?> sourceExecutable = (executableName.equals(template.getClass().getSimpleName())) ? sourceClass.getConstructor(parameterTypes) : sourceClass.getMethod(executableName, parameterTypes);
        return spoon.template.Substitution.substitute(targetClass, template, sourceExecutable.getBody());
    }

    public static spoon.reflect.code.CtStatement substituteStatement(spoon.reflect.declaration.CtClass<?> targetClass, spoon.template.Template<?> template, int statementIndex, java.lang.String executableName, spoon.reflect.reference.CtTypeReference<?>... parameterTypes) {
        spoon.reflect.declaration.CtClass<?> sourceClass = spoon.template.Substitution.getTemplateCtClass(targetClass, template);
        spoon.reflect.declaration.CtExecutable<?> sourceExecutable = (executableName.equals(template.getClass().getSimpleName())) ? sourceClass.getConstructor(parameterTypes) : sourceClass.getMethod(executableName, parameterTypes);
        return spoon.template.Substitution.substitute(targetClass, template, sourceExecutable.getBody().getStatement(statementIndex));
    }

    public static spoon.reflect.code.CtExpression<?> substituteFieldDefaultExpression(spoon.reflect.declaration.CtType<?> targetType, spoon.template.Template<?> template, java.lang.String fieldName) {
        spoon.reflect.declaration.CtClass<?> sourceClass = spoon.template.Substitution.getTemplateCtClass(targetType, template);
        spoon.reflect.declaration.CtField<?> sourceField = sourceClass.getField(fieldName);
        return spoon.template.Substitution.substitute(targetType, template, sourceField.getDefaultExpression());
    }

    public static <E extends spoon.reflect.declaration.CtElement> E substitute(spoon.reflect.declaration.CtType<?> targetType, spoon.template.Template<?> template, E code) {
        if (code == null) {
            return null;
        }
        if (targetType == null) {
            throw new java.lang.RuntimeException("target is null in substitution");
        }
        E result = ((E) (code.clone()));
        java.util.List<E> results = new spoon.support.template.SubstitutionVisitor(targetType.getFactory(), targetType, template).substitute(result);
        if ((results.size()) > 1) {
            throw new spoon.SpoonException("StatementTemplate cannot return more then one statement");
        }
        return results.isEmpty() ? null : results.get(0);
    }

    public static <T extends spoon.reflect.declaration.CtType<?>> T substitute(spoon.template.Template<?> template, T templateType) {
        T result = ((T) (templateType.clone()));
        result.setPositions(null);
        new spoon.support.template.SubstitutionVisitor(templateType.getFactory(), result, template).substitute(result);
        return result;
    }

    public static <T> spoon.reflect.declaration.CtField<T> insertField(spoon.reflect.declaration.CtType<?> targetType, spoon.template.Template<?> template, spoon.reflect.declaration.CtField<T> sourceField) {
        spoon.reflect.declaration.CtField<T> field = spoon.template.Substitution.substitute(targetType, template, sourceField);
        targetType.addField(field);
        return field;
    }

    public static void redirectTypeReferences(spoon.reflect.declaration.CtElement element, spoon.reflect.reference.CtTypeReference<?> source, spoon.reflect.reference.CtTypeReference<?> target) {
        java.util.List<spoon.reflect.reference.CtTypeReference<?>> refs = spoon.reflect.visitor.Query.getReferences(element, new spoon.reflect.visitor.filter.ReferenceTypeFilter<spoon.reflect.reference.CtTypeReference<?>>(spoon.reflect.reference.CtTypeReference.class));
        java.lang.String srcName = source.getQualifiedName();
        java.lang.String targetName = target.getSimpleName();
        spoon.reflect.reference.CtPackageReference targetPackage = target.getPackage();
        for (spoon.reflect.reference.CtTypeReference<?> ref : refs) {
            if (ref.getQualifiedName().equals(srcName)) {
                ref.setSimpleName(targetName);
                ref.setPackage(targetPackage);
            }
        }
    }

    static <T> spoon.reflect.declaration.CtClass<T> getTemplateCtClass(spoon.reflect.declaration.CtType<?> targetType, spoon.template.Template<?> template) {
        spoon.reflect.factory.Factory factory;
        if (targetType != null) {
            factory = targetType.getFactory();
        }else {
            factory = spoon.template.Substitution.getFactory(template);
        }
        return spoon.template.Substitution.getTemplateCtClass(factory, template);
    }

    static <T> spoon.reflect.declaration.CtClass<T> getTemplateCtClass(spoon.reflect.factory.Factory factory, spoon.template.Template<?> template) {
        spoon.reflect.declaration.CtClass<T> c = factory.Class().get(template.getClass());
        if (c.isShadow()) {
            throw new spoon.SpoonException((("The template " + (template.getClass().getName())) + " is not part of model. Add template sources to spoon template path."));
        }
        spoon.template.Substitution.checkTemplateContracts(c);
        return c;
    }

    private static <T> void checkTemplateContracts(spoon.reflect.declaration.CtClass<T> c) {
        for (spoon.reflect.declaration.CtField f : c.getFields()) {
            spoon.template.Parameter templateParamAnnotation = f.getAnnotation(spoon.template.Parameter.class);
            if ((templateParamAnnotation != null) && (!(templateParamAnnotation.value().equals("")))) {
                java.lang.String proxyName = templateParamAnnotation.value();
                java.lang.String fieldTypeQName = f.getType().getQualifiedName();
                if (fieldTypeQName.equals(java.lang.String.class.getName())) {
                    boolean found = false;
                    for (spoon.reflect.declaration.CtTypeMember member : c.getTypeMembers()) {
                        if (member.getSimpleName().equals(proxyName)) {
                            found = true;
                        }
                    }
                    if (!found) {
                        throw new spoon.template.TemplateException((("if a proxy parameter is declared and named \"" + proxyName) + "\", then a type member named \"\" + proxyName + \"\" must exist."));
                    }
                }else
                    if (fieldTypeQName.equals(spoon.reflect.reference.CtTypeReference.class.getName())) {
                    }else {
                        throw new spoon.template.TemplateException(("proxy template parameter must be typed as String or CtTypeReference, but it is " + fieldTypeQName));
                    }

            }
        }
    }

    static spoon.reflect.factory.Factory getFactory(spoon.template.Template<?> template) {
        try {
            for (java.lang.reflect.Field f : spoon.support.template.Parameters.getAllTemplateParameterFields(template.getClass())) {
                if (((f.get(template)) != null) && ((f.get(template)) instanceof spoon.processing.FactoryAccessor)) {
                    return ((spoon.processing.FactoryAccessor) (f.get(template))).getFactory();
                }
            }
        } catch (java.lang.Exception e) {
            throw new spoon.SpoonException(e);
        }
        throw new spoon.template.TemplateException(("no factory found in template " + (template.getClass().getName())));
    }
}

