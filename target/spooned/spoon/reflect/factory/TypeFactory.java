package spoon.reflect.factory;


public class TypeFactory extends spoon.reflect.factory.SubFactory {
    private static final java.util.Set<java.lang.String> NULL_PACKAGE_CLASSES = java.util.Collections.unmodifiableSet(new java.util.HashSet<java.lang.String>(java.util.Arrays.asList("void", "boolean", "byte", "short", "char", "int", "float", "long", "double", spoon.reflect.reference.CtTypeReference.NULL_TYPE_NAME)));

    public final spoon.reflect.reference.CtTypeReference<?> NULL_TYPE = createReference(spoon.reflect.reference.CtTypeReference.NULL_TYPE_NAME);

    public final spoon.reflect.reference.CtTypeReference<java.lang.Void> VOID = createReference(java.lang.Void.class);

    public final spoon.reflect.reference.CtTypeReference<java.lang.String> STRING = createReference(java.lang.String.class);

    public final spoon.reflect.reference.CtTypeReference<java.lang.Boolean> BOOLEAN = createReference(java.lang.Boolean.class);

    public final spoon.reflect.reference.CtTypeReference<java.lang.Byte> BYTE = createReference(java.lang.Byte.class);

    public final spoon.reflect.reference.CtTypeReference<java.lang.Character> CHARACTER = createReference(java.lang.Character.class);

    public final spoon.reflect.reference.CtTypeReference<java.lang.Integer> INTEGER = createReference(java.lang.Integer.class);

    public final spoon.reflect.reference.CtTypeReference<java.lang.Long> LONG = createReference(java.lang.Long.class);

    public final spoon.reflect.reference.CtTypeReference<java.lang.Float> FLOAT = createReference(java.lang.Float.class);

    public final spoon.reflect.reference.CtTypeReference<java.lang.Double> DOUBLE = createReference(java.lang.Double.class);

    public final spoon.reflect.reference.CtTypeReference<java.lang.Void> VOID_PRIMITIVE = createReference(void.class);

    public final spoon.reflect.reference.CtTypeReference<java.lang.Boolean> BOOLEAN_PRIMITIVE = createReference(boolean.class);

    public final spoon.reflect.reference.CtTypeReference<java.lang.Byte> BYTE_PRIMITIVE = createReference(byte.class);

    public final spoon.reflect.reference.CtTypeReference<java.lang.Character> CHARACTER_PRIMITIVE = createReference(char.class);

    public final spoon.reflect.reference.CtTypeReference<java.lang.Integer> INTEGER_PRIMITIVE = createReference(int.class);

    public final spoon.reflect.reference.CtTypeReference<java.lang.Long> LONG_PRIMITIVE = createReference(long.class);

    public final spoon.reflect.reference.CtTypeReference<java.lang.Float> FLOAT_PRIMITIVE = createReference(float.class);

    public final spoon.reflect.reference.CtTypeReference<java.lang.Double> DOUBLE_PRIMITIVE = createReference(double.class);

    public final spoon.reflect.reference.CtTypeReference<java.lang.Short> SHORT = createReference(java.lang.Short.class);

    public final spoon.reflect.reference.CtTypeReference<java.lang.Short> SHORT_PRIMITIVE = createReference(short.class);

    public final spoon.reflect.reference.CtTypeReference<java.util.Date> DATE = createReference(java.util.Date.class);

    public final spoon.reflect.reference.CtTypeReference<java.lang.Object> OBJECT = createReference(java.lang.Object.class);

    public final spoon.reflect.reference.CtTypeReference<java.lang.Iterable> ITERABLE = createReference(java.lang.Iterable.class);

    public final spoon.reflect.reference.CtTypeReference<java.util.Collection> COLLECTION = createReference(java.util.Collection.class);

    public final spoon.reflect.reference.CtTypeReference<java.util.List> LIST = createReference(java.util.List.class);

    public final spoon.reflect.reference.CtTypeReference<java.util.Set> SET = createReference(java.util.Set.class);

    public final spoon.reflect.reference.CtTypeReference<java.util.Map> MAP = createReference(java.util.Map.class);

    public final spoon.reflect.reference.CtTypeReference<java.lang.Enum> ENUM = createReference(java.lang.Enum.class);

    private final java.util.Map<java.lang.Class<?>, spoon.reflect.declaration.CtType<?>> shadowCache = new java.util.HashMap<>();

    public spoon.reflect.reference.CtTypeReference<?> nullType() {
        return NULL_TYPE.clone();
    }

    public spoon.reflect.reference.CtTypeReference<java.lang.Void> voidType() {
        return VOID.clone();
    }

    public spoon.reflect.reference.CtTypeReference<java.lang.Void> voidPrimitiveType() {
        return VOID_PRIMITIVE.clone();
    }

    public spoon.reflect.reference.CtTypeReference<java.lang.String> stringType() {
        return STRING.clone();
    }

    public spoon.reflect.reference.CtTypeReference<java.lang.Boolean> booleanType() {
        return BOOLEAN.clone();
    }

    public spoon.reflect.reference.CtTypeReference<java.lang.Boolean> booleanPrimitiveType() {
        return BOOLEAN_PRIMITIVE.clone();
    }

    public spoon.reflect.reference.CtTypeReference<java.lang.Byte> byteType() {
        return BYTE.clone();
    }

    public spoon.reflect.reference.CtTypeReference<java.lang.Byte> bytePrimitiveType() {
        return BYTE_PRIMITIVE.clone();
    }

    public spoon.reflect.reference.CtTypeReference<java.lang.Character> characterType() {
        return CHARACTER.clone();
    }

    public spoon.reflect.reference.CtTypeReference<java.lang.Character> characterPrimitiveType() {
        return CHARACTER_PRIMITIVE.clone();
    }

    public spoon.reflect.reference.CtTypeReference<java.lang.Integer> integerType() {
        return INTEGER.clone();
    }

    public spoon.reflect.reference.CtTypeReference<java.lang.Integer> integerPrimitiveType() {
        return INTEGER_PRIMITIVE.clone();
    }

    public spoon.reflect.reference.CtTypeReference<java.lang.Long> longType() {
        return LONG.clone();
    }

    public spoon.reflect.reference.CtTypeReference<java.lang.Long> longPrimitiveType() {
        return LONG_PRIMITIVE.clone();
    }

    public spoon.reflect.reference.CtTypeReference<java.lang.Float> floatType() {
        return FLOAT.clone();
    }

    public spoon.reflect.reference.CtTypeReference<java.lang.Float> floatPrimitiveType() {
        return FLOAT_PRIMITIVE.clone();
    }

    public spoon.reflect.reference.CtTypeReference<java.lang.Double> doubleType() {
        return DOUBLE.clone();
    }

    public spoon.reflect.reference.CtTypeReference<java.lang.Double> doublePrimitiveType() {
        return DOUBLE_PRIMITIVE.clone();
    }

    public spoon.reflect.reference.CtTypeReference<java.lang.Short> shortType() {
        return SHORT.clone();
    }

    public spoon.reflect.reference.CtTypeReference<java.lang.Short> shortPrimitiveType() {
        return SHORT_PRIMITIVE.clone();
    }

    public spoon.reflect.reference.CtTypeReference<java.util.Date> dateType() {
        return DATE.clone();
    }

    public spoon.reflect.reference.CtTypeReference<java.lang.Object> objectType() {
        return OBJECT.clone();
    }

    public TypeFactory(spoon.reflect.factory.Factory factory) {
        super(factory);
    }

    public TypeFactory() {
        this(new spoon.reflect.factory.FactoryImpl(new spoon.support.DefaultCoreFactory(), new spoon.support.StandardEnvironment()));
    }

    public <T> spoon.reflect.reference.CtArrayTypeReference<T[]> createArrayReference(spoon.reflect.declaration.CtType<T> type) {
        spoon.reflect.reference.CtArrayTypeReference<T[]> array = factory.Core().createArrayTypeReference();
        array.setComponentType(createReference(type));
        return array;
    }

    public <T> spoon.reflect.reference.CtArrayTypeReference<T[]> createArrayReference(spoon.reflect.reference.CtTypeReference<T> reference) {
        spoon.reflect.reference.CtArrayTypeReference<T[]> array = factory.Core().createArrayTypeReference();
        array.setComponentType(reference);
        return array;
    }

    public spoon.reflect.reference.CtArrayTypeReference<?> createArrayReference(spoon.reflect.reference.CtTypeReference<?> reference, int n) {
        spoon.reflect.reference.CtTypeReference<?> componentType = null;
        if (n == 1) {
            return createArrayReference(reference);
        }
        componentType = createArrayReference(reference, (n - 1));
        spoon.reflect.reference.CtArrayTypeReference<?> array = factory.Core().createArrayTypeReference();
        array.setComponentType(componentType);
        return array;
    }

    public <T> spoon.reflect.reference.CtArrayTypeReference<T> createArrayReference(java.lang.String qualifiedName) {
        spoon.reflect.reference.CtArrayTypeReference<T> array = factory.Core().createArrayTypeReference();
        array.setComponentType(createReference(qualifiedName));
        return array;
    }

    public <T> spoon.reflect.reference.CtTypeReference<T> createReference(java.lang.Class<T> type) {
        return createReference(type, false);
    }

    public <T> spoon.reflect.reference.CtTypeReference<T> createReference(java.lang.Class<T> type, boolean includingFormalTypeParameter) {
        if (type == null) {
            return null;
        }
        if (type.isArray()) {
            spoon.reflect.reference.CtArrayTypeReference<T> array = factory.Core().createArrayTypeReference();
            array.setComponentType(createReference(type.getComponentType(), includingFormalTypeParameter));
            return array;
        }
        spoon.reflect.reference.CtTypeReference typeReference = createReference(type.getName());
        if (includingFormalTypeParameter) {
            for (java.lang.reflect.TypeVariable<java.lang.Class<T>> generic : type.getTypeParameters()) {
                typeReference.addActualTypeArgument(createTypeParameterReference(generic.getName()));
            }
        }
        return typeReference;
    }

    public <T> spoon.reflect.reference.CtTypeReference<T> createReference(spoon.reflect.declaration.CtType<T> type) {
        return createReference(type, false);
    }

    public spoon.reflect.reference.CtTypeReference createWildcardStaticTypeMemberReference(spoon.reflect.reference.CtTypeReference typeReference) {
        spoon.reflect.reference.CtTypeReference ref = factory.Core().createWildcardStaticTypeMemberReference();
        ref.setFactory(this.factory);
        if ((typeReference.getDeclaringType()) != null) {
            ref.setDeclaringType(typeReference.getDeclaringType().clone());
        }
        if ((typeReference.getPackage()) != null) {
            ref.setPackage(typeReference.getPackage().clone());
        }
        ref.setSimpleName(typeReference.getSimpleName());
        return ref;
    }

    public <T> spoon.reflect.reference.CtTypeReference<T> createReference(spoon.reflect.declaration.CtType<T> type, boolean includingFormalTypeParameter) {
        spoon.reflect.reference.CtTypeReference<T> ref = factory.Core().createTypeReference();
        if ((type.getDeclaringType()) != null) {
            ref.setDeclaringType(createReference(type.getDeclaringType(), includingFormalTypeParameter));
        }else
            if ((type.getPackage()) != null) {
                ref.setPackage(factory.Package().createReference(type.getPackage()));
            }

        ref.setSimpleName(type.getSimpleName());
        if (includingFormalTypeParameter) {
            for (spoon.reflect.declaration.CtTypeParameter formalTypeParam : type.getFormalCtTypeParameters()) {
                ref.addActualTypeArgument(formalTypeParam.getReference());
            }
        }
        return ref;
    }

    public spoon.reflect.reference.CtTypeParameterReference createReference(spoon.reflect.declaration.CtTypeParameter type) {
        spoon.reflect.reference.CtTypeParameterReference ref = factory.Core().createTypeParameterReference();
        if ((type.getSuperclass()) != null) {
            ref.setBoundingType(type.getSuperclass().clone());
        }
        for (spoon.reflect.declaration.CtAnnotation<? extends java.lang.annotation.Annotation> ctAnnotation : type.getAnnotations()) {
            ref.addAnnotation(ctAnnotation.clone());
        }
        ref.setSimpleName(type.getSimpleName());
        ref.setParent(type);
        return ref;
    }

    public <T> spoon.reflect.reference.CtTypeReference<T> createReference(java.lang.String qualifiedName) {
        if (qualifiedName.endsWith("[]")) {
            return createArrayReference(qualifiedName.substring(0, ((qualifiedName.length()) - 2)));
        }
        spoon.reflect.reference.CtTypeReference<T> ref = factory.Core().createTypeReference();
        if ((hasInnerType(qualifiedName)) > 0) {
            ref.setDeclaringType(createReference(getDeclaringTypeName(qualifiedName)));
        }else
            if ((hasPackage(qualifiedName)) > 0) {
                ref.setPackage(factory.Package().createReference(getPackageName(qualifiedName)));
            }else
                if (!(spoon.reflect.factory.TypeFactory.NULL_PACKAGE_CLASSES.contains(qualifiedName))) {
                    ref.setPackage(factory.Package().topLevel());
                }


        ref.setSimpleName(getSimpleName(qualifiedName));
        return ref;
    }

    @java.lang.SuppressWarnings("unchecked")
    public <T> spoon.reflect.declaration.CtType<T> get(final java.lang.String qualifiedName) {
        int packageIndex = qualifiedName.lastIndexOf(spoon.reflect.declaration.CtPackage.PACKAGE_SEPARATOR);
        spoon.reflect.declaration.CtPackage pack;
        if (packageIndex > 0) {
            pack = factory.Package().get(qualifiedName.substring(0, packageIndex));
        }else {
            pack = factory.Package().getRootPackage();
        }
        if (pack != null) {
            spoon.reflect.declaration.CtType<T> type = pack.getType(qualifiedName.substring((packageIndex + 1)));
            if (type != null) {
                return type;
            }
        }
        int inertTypeIndex = qualifiedName.lastIndexOf(spoon.reflect.declaration.CtType.INNERTTYPE_SEPARATOR);
        if (inertTypeIndex > 0) {
            java.lang.String s = qualifiedName.substring(0, inertTypeIndex);
            spoon.reflect.declaration.CtType<T> t = factory.Type().get(s);
            if (t == null) {
                return null;
            }
            java.lang.String className = qualifiedName.substring((inertTypeIndex + 1));
            final spoon.reflect.reference.CtTypeReference<T> reference = t.getReference();
            if (reference.isLocalType()) {
                final java.util.List<spoon.reflect.declaration.CtClass<T>> enclosingClasses = t.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.declaration.CtClass<T>>(spoon.reflect.declaration.CtClass.class) {
                    @java.lang.Override
                    public boolean matches(spoon.reflect.declaration.CtClass<T> element) {
                        return (super.matches(element)) && (element.getQualifiedName().equals(qualifiedName));
                    }
                });
                if ((enclosingClasses.size()) == 0) {
                    return null;
                }
                return enclosingClasses.get(0);
            }
            try {
                java.lang.Integer.parseInt(className);
                final java.util.List<spoon.reflect.code.CtNewClass> anonymousClasses = t.getElements(new spoon.reflect.visitor.filter.TypeFilter<spoon.reflect.code.CtNewClass>(spoon.reflect.code.CtNewClass.class) {
                    @java.lang.Override
                    public boolean matches(spoon.reflect.code.CtNewClass element) {
                        return (super.matches(element)) && (element.getAnonymousClass().getQualifiedName().equals(qualifiedName));
                    }
                });
                if ((anonymousClasses.size()) == 0) {
                    return null;
                }
                return anonymousClasses.get(0).getAnonymousClass();
            } catch (java.lang.NumberFormatException e) {
                return t.getNestedType(className);
            }
        }
        return null;
    }

    public java.util.List<spoon.reflect.declaration.CtType<?>> getAll() {
        return new java.util.ArrayList<>(factory.getModel().getAllTypes());
    }

    public java.util.List<spoon.reflect.declaration.CtType<?>> getAll(boolean includeNestedTypes) {
        if (!includeNestedTypes) {
            return getAll();
        }
        java.util.List<spoon.reflect.declaration.CtType<?>> types = new java.util.ArrayList<>();
        for (spoon.reflect.declaration.CtPackage pack : factory.Package().getAll()) {
            for (spoon.reflect.declaration.CtType<?> type : pack.getTypes()) {
                addNestedType(types, type);
            }
        }
        return types;
    }

    private void addNestedType(java.util.List<spoon.reflect.declaration.CtType<?>> list, spoon.reflect.declaration.CtType<?> t) {
        list.add(t);
        for (spoon.reflect.declaration.CtType<?> nt : t.getNestedTypes()) {
            addNestedType(list, nt);
        }
    }

    @java.lang.SuppressWarnings("unchecked")
    public <T> spoon.reflect.declaration.CtType<T> get(java.lang.Class<?> cl) {
        final spoon.reflect.declaration.CtType<T> aType = get(cl.getName());
        if (aType == null) {
            final spoon.reflect.declaration.CtType<T> shadowClass = ((spoon.reflect.declaration.CtType<T>) (this.shadowCache.get(cl)));
            if (shadowClass == null) {
                spoon.reflect.declaration.CtType<T> newShadowClass;
                try {
                    newShadowClass = new spoon.support.visitor.java.JavaReflectionTreeBuilder(spoon.testing.utils.ModelUtils.createFactory()).scan(((java.lang.Class<T>) (cl)));
                } catch (java.lang.Throwable e) {
                    throw new spoon.support.SpoonClassNotFoundException(("cannot create shadow class: " + (cl.getName())), e);
                }
                newShadowClass.setFactory(factory);
                newShadowClass.accept(new spoon.reflect.visitor.CtScanner() {
                    @java.lang.Override
                    public void scan(spoon.reflect.declaration.CtElement element) {
                        if (element != null) {
                            element.setFactory(factory);
                        }
                    }
                });
                this.shadowCache.put(cl, newShadowClass);
                return newShadowClass;
            }else {
                return shadowClass;
            }
        }
        return aType;
    }

    protected java.lang.String getDeclaringTypeName(java.lang.String qualifiedName) {
        return qualifiedName.substring(0, hasInnerType(qualifiedName));
    }

    public java.util.List<spoon.reflect.reference.CtTypeReference<?>> createReferences(java.util.List<java.lang.Class<?>> classes) {
        java.util.List<spoon.reflect.reference.CtTypeReference<?>> refs = new java.util.ArrayList<>(classes.size());
        for (java.lang.Class<?> c : classes) {
            refs.add(createReference(c));
        }
        return refs;
    }

    protected java.lang.String getPackageName(java.lang.String qualifiedName) {
        if ((hasPackage(qualifiedName)) >= 0) {
            return qualifiedName.substring(0, hasPackage(qualifiedName));
        }
        return "";
    }

    protected java.lang.String getSimpleName(java.lang.String qualifiedName) {
        if ((hasInnerType(qualifiedName)) > 0) {
            return qualifiedName.substring(((hasInnerType(qualifiedName)) + 1));
        }else
            if ((hasPackage(qualifiedName)) > 0) {
                return qualifiedName.substring(((hasPackage(qualifiedName)) + 1));
            }else {
                return qualifiedName;
            }

    }

    protected int hasInnerType(java.lang.String qualifiedName) {
        int ret = qualifiedName.lastIndexOf(spoon.reflect.declaration.CtType.INNERTTYPE_SEPARATOR);
        return ret;
    }

    protected int hasPackage(java.lang.String qualifiedName) {
        return qualifiedName.lastIndexOf(spoon.reflect.declaration.CtPackage.PACKAGE_SEPARATOR);
    }

    public spoon.reflect.reference.CtTypeParameterReference createTypeParameterReference(java.lang.String name) {
        spoon.reflect.reference.CtTypeParameterReference typeParam = factory.Core().createTypeParameterReference();
        typeParam.setSimpleName(name);
        return typeParam;
    }

    public spoon.support.visitor.GenericTypeAdapter createTypeAdapter(spoon.reflect.declaration.CtFormalTypeDeclarer formalTypeDeclarer) {
        class Visitor extends spoon.reflect.visitor.CtAbstractVisitor {
            spoon.support.visitor.GenericTypeAdapter adapter;

            @java.lang.Override
            public <T> void visitCtClass(spoon.reflect.declaration.CtClass<T> ctClass) {
                adapter = new spoon.support.visitor.ClassTypingContext(ctClass);
            }

            @java.lang.Override
            public <T> void visitCtInterface(spoon.reflect.declaration.CtInterface<T> intrface) {
                adapter = new spoon.support.visitor.ClassTypingContext(intrface);
            }

            @java.lang.Override
            public <T> void visitCtMethod(spoon.reflect.declaration.CtMethod<T> m) {
                adapter = new spoon.support.visitor.MethodTypingContext().setMethod(m);
            }

            @java.lang.Override
            public <T> void visitCtConstructor(spoon.reflect.declaration.CtConstructor<T> c) {
                adapter = new spoon.support.visitor.MethodTypingContext().setConstructor(c);
            }
        }
        Visitor visitor = new Visitor();
        ((spoon.reflect.declaration.CtElement) (formalTypeDeclarer)).accept(visitor);
        return visitor.adapter;
    }

    public <T> spoon.reflect.reference.CtIntersectionTypeReference<T> createIntersectionTypeReferenceWithBounds(java.util.List<spoon.reflect.reference.CtTypeReference<?>> bounds) {
        final spoon.reflect.reference.CtIntersectionTypeReference<T> intersectionRef = factory.Core().createIntersectionTypeReference();
        spoon.reflect.reference.CtTypeReference<?> firstBound = bounds.toArray(new spoon.reflect.reference.CtTypeReference<?>[0])[0].clone();
        intersectionRef.setSimpleName(firstBound.getSimpleName());
        intersectionRef.setDeclaringType(firstBound.getDeclaringType());
        intersectionRef.setPackage(firstBound.getPackage());
        intersectionRef.setActualTypeArguments(firstBound.getActualTypeArguments());
        intersectionRef.setBounds(bounds);
        return intersectionRef;
    }

    public spoon.reflect.reference.CtTypeReference getDefaultBoundingType() {
        return OBJECT;
    }

    public spoon.reflect.declaration.CtImport createImport(spoon.reflect.reference.CtReference reference) {
        spoon.reflect.declaration.CtImport ctImport = factory.Core().createImport();
        return ctImport.setReference(reference.clone());
    }
}

