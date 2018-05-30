package spoon.metamodel;


public class SpoonMetaModel {
    public static final java.lang.String CLASS_SUFFIX = "Impl";

    public static final java.util.Set<java.lang.String> MODEL_IFACE_PACKAGES = new java.util.HashSet<>(java.util.Arrays.asList("spoon.reflect.code", "spoon.reflect.declaration", "spoon.reflect.reference"));

    private final spoon.reflect.factory.Factory factory;

    private final java.util.Map<java.lang.String, spoon.metamodel.MetamodelConcept> nameToConcept = new java.util.HashMap<>();

    public SpoonMetaModel(java.io.File spoonJavaSourcesDirectory) {
        this(spoon.metamodel.SpoonMetaModel.createFactory(spoonJavaSourcesDirectory));
    }

    public SpoonMetaModel(spoon.reflect.factory.Factory factory) {
        this.factory = factory;
        for (java.lang.String apiPackage : spoon.metamodel.SpoonMetaModel.MODEL_IFACE_PACKAGES) {
            if ((factory.Package().get(apiPackage)) == null) {
                throw new spoon.SpoonException(("Spoon Factory model is missing API package " + apiPackage));
            }
            java.lang.String implPackage = spoon.metamodel.SpoonMetaModel.replaceApiToImplPackage(apiPackage);
            if ((factory.Package().get(implPackage)) == null) {
                throw new spoon.SpoonException(("Spoon Factory model is missing implementation package " + implPackage));
            }
        }
        factory.getModel().filterChildren(new spoon.reflect.visitor.filter.TypeFilter<>(spoon.reflect.declaration.CtInterface.class)).forEach((spoon.reflect.declaration.CtInterface<?> iface) -> {
            if (spoon.metamodel.SpoonMetaModel.MODEL_IFACE_PACKAGES.contains(iface.getPackage().getQualifiedName())) {
                getOrCreateConcept(iface);
            }
        });
    }

    public SpoonMetaModel() {
        this.factory = new spoon.reflect.factory.FactoryImpl(new spoon.support.DefaultCoreFactory(), new spoon.support.StandardEnvironment());
        for (spoon.reflect.declaration.CtType<?> iface : spoon.Metamodel.getAllMetamodelInterfaces()) {
            if (iface instanceof spoon.reflect.declaration.CtInterface) {
                getOrCreateConcept(iface);
            }
        }
    }

    public java.util.Collection<spoon.metamodel.MetamodelConcept> getConcepts() {
        return java.util.Collections.unmodifiableCollection(nameToConcept.values());
    }

    public static java.lang.String getConceptName(spoon.reflect.declaration.CtType<?> type) {
        java.lang.String name = type.getSimpleName();
        if (name.endsWith(spoon.metamodel.SpoonMetaModel.CLASS_SUFFIX)) {
            name = name.substring(0, ((name.length()) - (spoon.metamodel.SpoonMetaModel.CLASS_SUFFIX.length())));
        }
        return name;
    }

    public static spoon.reflect.declaration.CtClass<?> getImplementationOfInterface(spoon.reflect.declaration.CtInterface<?> iface) {
        java.lang.String impl = (spoon.metamodel.SpoonMetaModel.replaceApiToImplPackage(iface.getQualifiedName())) + (spoon.metamodel.SpoonMetaModel.CLASS_SUFFIX);
        return ((spoon.reflect.declaration.CtClass<?>) (spoon.metamodel.SpoonMetaModel.getType(impl, iface)));
    }

    private static spoon.reflect.declaration.CtType<?> getType(java.lang.String qualifiedName, spoon.reflect.declaration.CtElement anElement) {
        java.lang.Class aClass;
        try {
            aClass = anElement.getClass().getClassLoader().loadClass(qualifiedName);
        } catch (java.lang.ClassNotFoundException e) {
            return null;
        }
        return anElement.getFactory().Type().get(aClass);
    }

    private static final java.lang.String modelApiPackage = "spoon.reflect";

    private static final java.lang.String modelApiImplPackage = "spoon.support.reflect";

    private static java.lang.String replaceApiToImplPackage(java.lang.String modelInterfaceQName) {
        if ((modelInterfaceQName.startsWith(spoon.metamodel.SpoonMetaModel.modelApiPackage)) == false) {
            throw new spoon.SpoonException(((("The qualified name " + modelInterfaceQName) + " doesn't belong to Spoon model API package: ") + (spoon.metamodel.SpoonMetaModel.modelApiPackage)));
        }
        return (spoon.metamodel.SpoonMetaModel.modelApiImplPackage) + (modelInterfaceQName.substring(spoon.metamodel.SpoonMetaModel.modelApiPackage.length()));
    }

    public static spoon.reflect.declaration.CtInterface<?> getInterfaceOfImplementation(spoon.reflect.declaration.CtClass<?> impl) {
        java.lang.String iface = impl.getQualifiedName();
        if (((iface.endsWith(spoon.metamodel.SpoonMetaModel.CLASS_SUFFIX)) == false) || ((iface.startsWith("spoon.support.reflect.")) == false)) {
            throw new spoon.SpoonException(("Unexpected spoon model implementation class: " + (impl.getQualifiedName())));
        }
        iface = iface.substring(0, ((iface.length()) - (spoon.metamodel.SpoonMetaModel.CLASS_SUFFIX.length())));
        iface = iface.replace("spoon.support.reflect", "spoon.reflect");
        return ((spoon.reflect.declaration.CtInterface<?>) (spoon.metamodel.SpoonMetaModel.getType(iface, impl)));
    }

    private static spoon.reflect.factory.Factory createFactory(java.io.File spoonJavaSourcesDirectory) {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.getEnvironment().setNoClasspath(true);
        launcher.getEnvironment().setCommentEnabled(true);
        java.util.Arrays.asList("spoon/reflect/code", "spoon/reflect/declaration", "spoon/reflect/reference", "spoon/support/reflect/declaration", "spoon/support/reflect/code", "spoon/support/reflect/reference").forEach(( path) -> {
            launcher.addInputResource(new spoon.support.compiler.FileSystemFolder(new java.io.File(spoonJavaSourcesDirectory, path)));
        });
        launcher.buildModel();
        return launcher.getFactory();
    }

    private spoon.metamodel.MetamodelConcept getOrCreateConcept(spoon.reflect.declaration.CtType<?> type) {
        java.lang.String conceptName = spoon.metamodel.SpoonMetaModel.getConceptName(type);
        spoon.metamodel.MetamodelConcept mmConcept = spoon.metamodel.SpoonMetaModel.getOrCreate(nameToConcept, conceptName, () -> new spoon.metamodel.MetamodelConcept());
        if ((mmConcept.name) == null) {
            mmConcept.name = conceptName;
            initializeConcept(type, mmConcept);
        }
        return mmConcept;
    }

    private void initializeConcept(spoon.reflect.declaration.CtType<?> type, spoon.metamodel.MetamodelConcept mmConcept) {
        if (type instanceof spoon.reflect.declaration.CtInterface<?>) {
            spoon.reflect.declaration.CtInterface<?> iface = ((spoon.reflect.declaration.CtInterface<?>) (type));
            mmConcept.setModelClass(spoon.metamodel.SpoonMetaModel.getImplementationOfInterface(iface));
            mmConcept.setModelInterface(iface);
        }else
            if (type instanceof spoon.reflect.declaration.CtClass<?>) {
                spoon.reflect.declaration.CtClass<?> clazz = ((spoon.reflect.declaration.CtClass<?>) (type));
                mmConcept.setModelClass(clazz);
                mmConcept.setModelInterface(spoon.metamodel.SpoonMetaModel.getInterfaceOfImplementation(clazz));
            }else {
                throw new spoon.SpoonException(("Unexpected spoon model type: " + (type.getQualifiedName())));
            }

        if ((mmConcept.getModelClass()) != null) {
            addFieldsOfType(mmConcept, mmConcept.getModelClass());
        }
        if ((mmConcept.getModelInterface()) != null) {
            addFieldsOfType(mmConcept, mmConcept.getModelInterface());
        }
        mmConcept.getRoleToProperty().forEach(( role, mmField) -> {
            mmField.sortByBestMatch();
            mmField.setValueType(mmField.detectValueType());
        });
    }

    private void addFieldsOfType(spoon.metamodel.MetamodelConcept mmConcept, spoon.reflect.declaration.CtType<?> ctType) {
        ctType.getTypeMembers().forEach(( typeMember) -> {
            if (typeMember instanceof spoon.reflect.declaration.CtMethod<?>) {
                spoon.reflect.declaration.CtMethod<?> method = ((spoon.reflect.declaration.CtMethod<?>) (typeMember));
                spoon.reflect.path.CtRole role = spoon.metamodel.SpoonMetaModel.getRoleOfMethod(method);
                if (role != null) {
                    spoon.metamodel.MetamodelProperty field = mmConcept.getOrCreateMMField(role);
                    field.addMethod(method);
                }else {
                    mmConcept.otherMethods.add(method);
                }
            }
        });
        addFieldsOfSuperType(mmConcept, ctType.getSuperclass());
        ctType.getSuperInterfaces().forEach(( superIfaceRef) -> addFieldsOfSuperType(mmConcept, superIfaceRef));
    }

    private static java.util.Set<java.lang.String> EXPECTED_TYPES_NOT_IN_CLASSPATH = new java.util.HashSet<>(java.util.Arrays.asList("java.lang.Cloneable", "java.lang.Object", "spoon.processing.FactoryAccessor", "spoon.reflect.visitor.CtVisitable", "spoon.reflect.visitor.chain.CtQueryable", "spoon.template.TemplateParameter", "java.lang.Iterable", "java.io.Serializable"));

    private void addFieldsOfSuperType(spoon.metamodel.MetamodelConcept concept, spoon.reflect.reference.CtTypeReference<?> superTypeRef) {
        if (superTypeRef == null) {
            return;
        }
        if (spoon.metamodel.SpoonMetaModel.EXPECTED_TYPES_NOT_IN_CLASSPATH.contains(superTypeRef.getQualifiedName())) {
            return;
        }
        spoon.reflect.declaration.CtType<?> superType = superTypeRef.getTypeDeclaration();
        if (superType == null) {
            throw new spoon.SpoonException((("Cannot create spoon meta model. The class " + (superTypeRef.getQualifiedName())) + " is missing class path"));
        }
        spoon.metamodel.MetamodelConcept superConcept = getOrCreateConcept(superType);
        if (superConcept != concept) {
            concept.addSuperConcept(superConcept);
        }
    }

    static <K, V> V getOrCreate(java.util.Map<K, V> map, K key, java.util.function.Supplier<V> valueCreator) {
        V value = map.get(key);
        if (value == null) {
            value = valueCreator.get();
            map.put(key, value);
        }
        return value;
    }

    static <T> boolean addUniqueObject(java.util.Collection<T> col, T o) {
        if (spoon.metamodel.SpoonMetaModel.containsObject(col, o)) {
            return false;
        }
        col.add(o);
        return true;
    }

    static boolean containsObject(java.lang.Iterable<? extends java.lang.Object> iter, java.lang.Object o) {
        for (java.lang.Object object : iter) {
            if (object == o) {
                return true;
            }
        }
        return false;
    }

    public static spoon.reflect.path.CtRole getRoleOfMethod(spoon.reflect.declaration.CtMethod<?> method) {
        spoon.reflect.factory.Factory f = method.getFactory();
        spoon.reflect.declaration.CtAnnotation<spoon.reflect.annotations.PropertyGetter> getter = spoon.metamodel.SpoonMetaModel.getInheritedAnnotation(method, f.createCtTypeReference(spoon.reflect.annotations.PropertyGetter.class));
        if (getter != null) {
            return getter.getActualAnnotation().role();
        }
        spoon.reflect.declaration.CtAnnotation<spoon.reflect.annotations.PropertySetter> setter = spoon.metamodel.SpoonMetaModel.getInheritedAnnotation(method, f.createCtTypeReference(spoon.reflect.annotations.PropertySetter.class));
        if (setter != null) {
            return setter.getActualAnnotation().role();
        }
        return null;
    }

    private static <A extends java.lang.annotation.Annotation> spoon.reflect.declaration.CtAnnotation<A> getInheritedAnnotation(spoon.reflect.declaration.CtMethod<?> method, spoon.reflect.reference.CtTypeReference<A> annotationType) {
        spoon.reflect.declaration.CtAnnotation<A> annotation = method.getAnnotation(annotationType);
        if (annotation == null) {
            spoon.reflect.declaration.CtType<?> declType = method.getDeclaringType();
            final spoon.support.visitor.ClassTypingContext ctc = new spoon.support.visitor.ClassTypingContext(declType);
            annotation = declType.map(new spoon.reflect.visitor.filter.AllTypeMembersFunction(spoon.reflect.declaration.CtMethod.class)).map((spoon.reflect.declaration.CtMethod<?> currentMethod) -> {
                if (method == currentMethod) {
                    return null;
                }
                if (ctc.isSameSignature(method, currentMethod)) {
                    spoon.reflect.declaration.CtAnnotation<A> annotation2 = currentMethod.getAnnotation(annotationType);
                    if (annotation2 != null) {
                        return annotation2;
                    }
                }
                return null;
            }).first();
        }
        return annotation;
    }

    public spoon.reflect.factory.Factory getFactory() {
        return factory;
    }

    public java.util.List<spoon.reflect.declaration.CtType<? extends spoon.reflect.declaration.CtElement>> getAllInstantiableMetamodelInterfaces() {
        spoon.SpoonAPI interfaces = new spoon.Launcher();
        interfaces.addInputResource("src/main/java/spoon/reflect/declaration");
        interfaces.addInputResource("src/main/java/spoon/reflect/code");
        interfaces.addInputResource("src/main/java/spoon/reflect/reference");
        interfaces.buildModel();
        spoon.SpoonAPI implementations = new spoon.Launcher();
        implementations.addInputResource("src/main/java/spoon/support/reflect/declaration");
        implementations.addInputResource("src/main/java/spoon/support/reflect/code");
        implementations.addInputResource("src/main/java/spoon/support/reflect/reference");
        implementations.buildModel();
        java.util.List<spoon.reflect.declaration.CtType<? extends spoon.reflect.declaration.CtElement>> result = new java.util.ArrayList<>();
        for (spoon.reflect.declaration.CtType<?> itf : interfaces.getModel().getAllTypes()) {
            java.lang.String impl = (itf.getQualifiedName().replace("spoon.reflect", "spoon.support.reflect")) + "Impl";
            spoon.reflect.declaration.CtType implClass = implementations.getFactory().Type().get(impl);
            if ((implClass != null) && (!(implClass.hasModifier(spoon.reflect.declaration.ModifierKind.ABSTRACT)))) {
                result.add(((spoon.reflect.declaration.CtType<? extends spoon.reflect.declaration.CtElement>) (itf)));
            }
        }
        return result;
    }
}

