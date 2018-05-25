package spoon.support.reflect.reference;


public class CtTypeReferenceImpl<T> extends spoon.support.reflect.reference.CtReferenceImpl implements spoon.reflect.reference.CtTypeReference<T> {
    private static final long serialVersionUID = 1L;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.TYPE_ARGUMENT)
    java.util.List<spoon.reflect.reference.CtTypeReference<?>> actualTypeArguments = spoon.support.reflect.declaration.CtElementImpl.emptyList();

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.DECLARING_TYPE)
    spoon.reflect.reference.CtTypeReference<?> declaringType;

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.PACKAGE_REF)
    private spoon.reflect.reference.CtPackageReference pack;

    public CtTypeReferenceImpl() {
        super();
    }

    @java.lang.Override
    public void accept(spoon.reflect.visitor.CtVisitor visitor) {
        visitor.visitCtTypeReference(this);
    }

    @java.lang.Override
    public spoon.reflect.reference.CtTypeReference<?> box() {
        if (!(isPrimitive())) {
            return this;
        }
        if (getSimpleName().equals("int")) {
            return getFactory().Type().createReference(java.lang.Integer.class);
        }
        if (getSimpleName().equals("float")) {
            return getFactory().Type().createReference(java.lang.Float.class);
        }
        if (getSimpleName().equals("long")) {
            return getFactory().Type().createReference(java.lang.Long.class);
        }
        if (getSimpleName().equals("char")) {
            return getFactory().Type().createReference(java.lang.Character.class);
        }
        if (getSimpleName().equals("double")) {
            return getFactory().Type().createReference(java.lang.Double.class);
        }
        if (getSimpleName().equals("boolean")) {
            return getFactory().Type().createReference(java.lang.Boolean.class);
        }
        if (getSimpleName().equals("short")) {
            return getFactory().Type().createReference(java.lang.Short.class);
        }
        if (getSimpleName().equals("byte")) {
            return getFactory().Type().createReference(java.lang.Byte.class);
        }
        if (getSimpleName().equals("void")) {
            return getFactory().Type().createReference(java.lang.Void.class);
        }
        return this;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public java.lang.Class<T> getActualClass() {
        if (isPrimitive()) {
            java.lang.String simpleN = getSimpleName();
            if ("boolean".equals(simpleN)) {
                return ((java.lang.Class<T>) (boolean.class));
            }else
                if ("byte".equals(simpleN)) {
                    return ((java.lang.Class<T>) (byte.class));
                }else
                    if ("double".equals(simpleN)) {
                        return ((java.lang.Class<T>) (double.class));
                    }else
                        if ("int".equals(simpleN)) {
                            return ((java.lang.Class<T>) (int.class));
                        }else
                            if ("short".equals(simpleN)) {
                                return ((java.lang.Class<T>) (short.class));
                            }else
                                if ("char".equals(simpleN)) {
                                    return ((java.lang.Class<T>) (char.class));
                                }else
                                    if ("long".equals(simpleN)) {
                                        return ((java.lang.Class<T>) (long.class));
                                    }else
                                        if ("float".equals(simpleN)) {
                                            return ((java.lang.Class<T>) (float.class));
                                        }else
                                            if ("void".equals(simpleN)) {
                                                return ((java.lang.Class<T>) (void.class));
                                            }








        }
        return findClass();
    }

    @java.lang.SuppressWarnings("unchecked")
    protected java.lang.Class<T> findClass() {
        try {
            java.lang.ClassLoader classLoader = getFactory().getEnvironment().getInputClassLoader();
            java.lang.String qualifiedName = getQualifiedName();
            return ((java.lang.Class<T>) (classLoader.loadClass(qualifiedName)));
        } catch (java.lang.Throwable e) {
            throw new spoon.support.SpoonClassNotFoundException(("cannot load class: " + (getQualifiedName())), e);
        }
    }

    @java.lang.Override
    public java.util.List<spoon.reflect.reference.CtTypeReference<?>> getActualTypeArguments() {
        return actualTypeArguments;
    }

    @java.lang.Override
    protected java.lang.reflect.AnnotatedElement getActualAnnotatedElement() {
        return getActualClass();
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public spoon.reflect.declaration.CtType<T> getDeclaration() {
        return getFactory().Type().get(getQualifiedName());
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtType<T> getTypeDeclaration() {
        spoon.reflect.declaration.CtType<T> t = getFactory().Type().get(getQualifiedName());
        if (t != null) {
            return t;
        }
        return getFactory().Type().get(getActualClass());
    }

    @java.lang.Override
    public spoon.reflect.reference.CtTypeReference<?> getDeclaringType() {
        return declaringType;
    }

    @java.lang.Override
    public spoon.reflect.reference.CtPackageReference getPackage() {
        return pack;
    }

    @java.lang.Override
    public java.lang.String getQualifiedName() {
        if ((getDeclaringType()) != null) {
            return ((getDeclaringType().getQualifiedName()) + (spoon.reflect.declaration.CtType.INNERTTYPE_SEPARATOR)) + (getSimpleName());
        }else
            if (((getPackage()) != null) && (!(getPackage().isUnnamedPackage()))) {
                return ((getPackage().getSimpleName()) + (spoon.reflect.declaration.CtPackage.PACKAGE_SEPARATOR)) + (getSimpleName());
            }else {
                return getSimpleName();
            }

    }

    @java.lang.Override
    public boolean isPrimitive() {
        return (((((((("boolean".equals(getSimpleName())) || ("byte".equals(getSimpleName()))) || ("double".equals(getSimpleName()))) || ("int".equals(getSimpleName()))) || ("short".equals(getSimpleName()))) || ("char".equals(getSimpleName()))) || ("long".equals(getSimpleName()))) || ("float".equals(getSimpleName()))) || ("void".equals(getSimpleName()));
    }

    @java.lang.Override
    public boolean isSubtypeOf(spoon.reflect.reference.CtTypeReference<?> type) {
        if (type instanceof spoon.reflect.reference.CtTypeParameterReference) {
            return false;
        }
        if ((spoon.reflect.reference.CtTypeReference.NULL_TYPE_NAME.equals(getSimpleName())) || (spoon.reflect.reference.CtTypeReference.NULL_TYPE_NAME.equals(type.getSimpleName()))) {
            return false;
        }
        if ((isPrimitive()) || (type.isPrimitive())) {
            return equals(type);
        }
        if (((this) instanceof spoon.reflect.reference.CtArrayTypeReference) && (type instanceof spoon.reflect.reference.CtArrayTypeReference)) {
            return ((spoon.reflect.reference.CtArrayTypeReference<?>) (this)).getComponentType().isSubtypeOf(((spoon.reflect.reference.CtArrayTypeReference<?>) (type)).getComponentType());
        }
        if (java.lang.Object.class.getName().equals(type.getQualifiedName())) {
            return true;
        }
        return new spoon.support.visitor.ClassTypingContext(this).isSubtypeOf(type);
    }

    private boolean isImplementationOf(spoon.reflect.reference.CtTypeReference<?> type) {
        spoon.reflect.reference.CtTypeReference<?> impl = this;
        while (impl != null) {
            if (impl.isSubtypeOf(type)) {
                return true;
            }
            impl = impl.getDeclaringType();
        } 
        return false;
    }

    @java.lang.Override
    public <C extends spoon.reflect.reference.CtActualTypeContainer> C setActualTypeArguments(java.util.List<? extends spoon.reflect.reference.CtTypeReference<?>> actualTypeArguments) {
        if ((actualTypeArguments == null) || (actualTypeArguments.isEmpty())) {
            this.actualTypeArguments = spoon.support.reflect.declaration.CtElementImpl.emptyList();
            return ((C) (this));
        }
        if ((this.actualTypeArguments) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.reference.CtTypeReference<?>>emptyList())) {
            this.actualTypeArguments = new java.util.ArrayList<>(spoon.reflect.ModelElementContainerDefaultCapacities.TYPE_TYPE_PARAMETERS_CONTAINER_DEFAULT_CAPACITY);
        }
        getFactory().getEnvironment().getModelChangeListener().onListDeleteAll(this, spoon.reflect.path.CtRole.TYPE_ARGUMENT, this.actualTypeArguments, new java.util.ArrayList<>(this.actualTypeArguments));
        this.actualTypeArguments.clear();
        for (spoon.reflect.reference.CtTypeReference<?> actualTypeArgument : actualTypeArguments) {
            addActualTypeArgument(actualTypeArgument);
        }
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.reference.CtTypeReference<T>> C setDeclaringType(spoon.reflect.reference.CtTypeReference<?> declaringType) {
        if (declaringType != null) {
            declaringType.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.DECLARING_TYPE, declaringType, this.declaringType);
        this.declaringType = declaringType;
        return ((C) (this));
    }

    @java.lang.Override
    public <C extends spoon.reflect.reference.CtTypeReference<T>> C setPackage(spoon.reflect.reference.CtPackageReference pack) {
        if (pack != null) {
            pack.setParent(this);
        }
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.PACKAGE_REF, pack, this.pack);
        this.pack = pack;
        return ((C) (this));
    }

    @java.lang.Override
    public spoon.reflect.reference.CtIntersectionTypeReference<T> asCtIntersectionTypeReference() {
        return ((spoon.reflect.reference.CtIntersectionTypeReference<T>) (this));
    }

    @java.lang.Override
    public spoon.reflect.reference.CtTypeReference<?> unbox() {
        if (isPrimitive()) {
            return this;
        }
        java.lang.Class<T> actualClass;
        try {
            actualClass = getActualClass();
        } catch (spoon.support.SpoonClassNotFoundException e) {
            return this;
        }
        if (actualClass == (java.lang.Integer.class)) {
            return getFactory().Type().createReference(int.class);
        }
        if (actualClass == (java.lang.Float.class)) {
            return getFactory().Type().createReference(float.class);
        }
        if (actualClass == (java.lang.Long.class)) {
            return getFactory().Type().createReference(long.class);
        }
        if (actualClass == (java.lang.Character.class)) {
            return getFactory().Type().createReference(char.class);
        }
        if (actualClass == (java.lang.Double.class)) {
            return getFactory().Type().createReference(double.class);
        }
        if (actualClass == (java.lang.Boolean.class)) {
            return getFactory().Type().createReference(boolean.class);
        }
        if (actualClass == (java.lang.Short.class)) {
            return getFactory().Type().createReference(short.class);
        }
        if (actualClass == (java.lang.Byte.class)) {
            return getFactory().Type().createReference(byte.class);
        }
        if (actualClass == (java.lang.Void.class)) {
            return getFactory().Type().createReference(void.class);
        }
        return this;
    }

    @java.lang.Override
    public java.util.Collection<spoon.reflect.reference.CtFieldReference<?>> getDeclaredFields() {
        spoon.reflect.declaration.CtType<?> t = getDeclaration();
        if (t == null) {
            try {
                return getDeclaredFieldReferences();
            } catch (spoon.support.SpoonClassNotFoundException cnfe) {
                handleParentNotFound(cnfe);
                return java.util.Collections.emptyList();
            }
        }else {
            return t.getDeclaredFields();
        }
    }

    private java.util.Collection<spoon.reflect.reference.CtFieldReference<?>> getDeclaredFieldReferences() {
        java.util.Collection<spoon.reflect.reference.CtFieldReference<?>> references = new java.util.ArrayList<>();
        for (java.lang.reflect.Field f : getDeclaredFields(getActualClass())) {
            references.add(getFactory().Field().createReference(f));
        }
        if (getActualClass().isAnnotation()) {
            for (java.lang.reflect.Method m : getActualClass().getDeclaredMethods()) {
                spoon.reflect.reference.CtTypeReference<?> retRef = getFactory().Type().createReference(m.getReturnType());
                spoon.reflect.reference.CtFieldReference<?> fr = getFactory().Field().createReference(this, retRef, m.getName());
                references.add(fr);
            }
        }
        return references;
    }

    private java.lang.reflect.Field[] getDeclaredFields(java.lang.Class<?> cls) {
        try {
            return cls.getDeclaredFields();
        } catch (java.lang.Throwable e) {
            throw new spoon.support.SpoonClassNotFoundException(("cannot load fields of class: " + (getQualifiedName())), e);
        }
    }

    private void handleParentNotFound(spoon.support.SpoonClassNotFoundException cnfe) {
        java.lang.String msg = (("cannot load class: " + (getQualifiedName())) + " with class loader ") + (java.lang.Thread.currentThread().getContextClassLoader());
        if (getFactory().getEnvironment().getNoClasspath()) {
            spoon.Launcher.LOGGER.warn(msg);
            return;
        }else {
            throw cnfe;
        }
    }

    @java.lang.Override
    public spoon.reflect.reference.CtFieldReference<?> getDeclaredField(java.lang.String name) {
        if (name == null) {
            return null;
        }
        spoon.reflect.declaration.CtType<?> t = getDeclaration();
        if (t == null) {
            try {
                java.util.Collection<spoon.reflect.reference.CtFieldReference<?>> fields = getDeclaredFieldReferences();
                for (spoon.reflect.reference.CtFieldReference<?> field : fields) {
                    if (name.equals(field.getSimpleName())) {
                        return field;
                    }
                }
            } catch (spoon.support.SpoonClassNotFoundException cnfe) {
                handleParentNotFound(cnfe);
                return null;
            }
            return null;
        }else {
            return t.getDeclaredField(name);
        }
    }

    public spoon.reflect.reference.CtFieldReference<?> getDeclaredOrInheritedField(java.lang.String fieldName) {
        spoon.reflect.declaration.CtType<?> t = getDeclaration();
        if (t == null) {
            spoon.reflect.reference.CtFieldReference<?> field = getDeclaredField(fieldName);
            if (field != null) {
                return field;
            }
            spoon.reflect.reference.CtTypeReference<?> typeRef = getSuperclass();
            if (typeRef != null) {
                field = typeRef.getDeclaredOrInheritedField(fieldName);
                if (field != null) {
                    return field;
                }
            }
            java.util.Set<spoon.reflect.reference.CtTypeReference<?>> ifaces = getSuperInterfaces();
            for (spoon.reflect.reference.CtTypeReference<?> iface : ifaces) {
                field = iface.getDeclaredOrInheritedField(fieldName);
                if (field != null) {
                    return field;
                }
            }
            return field;
        }else {
            return t.getDeclaredOrInheritedField(fieldName);
        }
    }

    @java.lang.Override
    public java.util.Collection<spoon.reflect.reference.CtExecutableReference<?>> getDeclaredExecutables() {
        spoon.reflect.declaration.CtType<T> t = getDeclaration();
        if (t == null) {
            try {
                return spoon.support.util.RtHelper.getAllExecutables(getActualClass(), getFactory());
            } catch (final spoon.support.SpoonClassNotFoundException e) {
                if (getFactory().getEnvironment().getNoClasspath()) {
                    return java.util.Collections.emptyList();
                }
                throw e;
            }
        }else {
            return t.getDeclaredExecutables();
        }
    }

    @java.lang.Override
    public java.util.Collection<spoon.reflect.reference.CtFieldReference<?>> getAllFields() {
        try {
            spoon.reflect.declaration.CtType<?> t = getTypeDeclaration();
            return t.getAllFields();
        } catch (spoon.support.SpoonClassNotFoundException cnfe) {
            handleParentNotFound(cnfe);
            return java.util.Collections.emptyList();
        }
    }

    @java.lang.Override
    public java.util.Collection<spoon.reflect.reference.CtExecutableReference<?>> getAllExecutables() {
        java.util.Collection<spoon.reflect.reference.CtExecutableReference<?>> l = new java.util.ArrayList<>();
        spoon.reflect.declaration.CtType<T> t = getTypeDeclaration();
        if (t != null) {
            l.addAll(t.getAllExecutables());
        }
        return l;
    }

    @java.lang.Override
    public java.util.Set<spoon.reflect.declaration.ModifierKind> getModifiers() {
        spoon.reflect.declaration.CtType<T> t = getDeclaration();
        if (t != null) {
            return t.getModifiers();
        }
        java.lang.Class<T> c = getActualClass();
        return spoon.support.util.RtHelper.getModifiers(c.getModifiers());
    }

    @java.lang.Override
    public spoon.reflect.reference.CtTypeReference<?> getSuperclass() {
        try {
            spoon.reflect.declaration.CtType<T> t = getTypeDeclaration();
            if (t != null) {
                return t.getSuperclass();
            }
        } catch (spoon.support.SpoonClassNotFoundException e) {
            return null;
        }
        return null;
    }

    @java.lang.Override
    public java.util.Set<spoon.reflect.reference.CtTypeReference<?>> getSuperInterfaces() {
        spoon.reflect.declaration.CtType<?> t = getTypeDeclaration();
        if (t != null) {
            return java.util.Collections.unmodifiableSet(t.getSuperInterfaces());
        }else {
            java.lang.Class<?> c = getActualClass();
            java.lang.Class<?>[] sis = c.getInterfaces();
            if ((sis != null) && ((sis.length) > 0)) {
                java.util.Set<spoon.reflect.reference.CtTypeReference<?>> set = new spoon.support.util.QualifiedNameBasedSortedSet<spoon.reflect.reference.CtTypeReference<?>>();
                for (java.lang.Class<?> si : sis) {
                    set.add(getFactory().Type().createReference(si));
                }
                return java.util.Collections.unmodifiableSet(set);
            }
        }
        throw new spoon.SpoonException(("Cannot provide CtType for " + (getQualifiedName())));
    }

    @java.lang.Override
    public boolean isAnonymous() {
        try {
            java.lang.Integer.parseInt(getSimpleName());
        } catch (java.lang.NumberFormatException e) {
            return false;
        }
        return true;
    }

    @java.lang.Override
    public boolean isLocalType() {
        if ((this.getDeclaration()) != null) {
            return this.getDeclaration().isLocalType();
        }
        final java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("^([0-9]+)([a-zA-Z]+)$");
        final java.util.regex.Matcher m = pattern.matcher(getSimpleName());
        return m.find();
    }

    @java.lang.Override
    public <C extends spoon.reflect.reference.CtActualTypeContainer> C addActualTypeArgument(spoon.reflect.reference.CtTypeReference<?> actualTypeArgument) {
        if (actualTypeArgument == null) {
            return ((C) (this));
        }
        if ((actualTypeArguments) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.reference.CtTypeReference<?>>emptyList())) {
            actualTypeArguments = new java.util.ArrayList<>(spoon.reflect.ModelElementContainerDefaultCapacities.TYPE_TYPE_PARAMETERS_CONTAINER_DEFAULT_CAPACITY);
        }
        actualTypeArgument.setParent(this);
        getFactory().getEnvironment().getModelChangeListener().onListAdd(this, spoon.reflect.path.CtRole.TYPE_ARGUMENT, this.actualTypeArguments, actualTypeArgument);
        actualTypeArguments.add(actualTypeArgument);
        return ((C) (this));
    }

    @java.lang.Override
    public boolean removeActualTypeArgument(spoon.reflect.reference.CtTypeReference<?> actualTypeArgument) {
        if ((actualTypeArguments) == (spoon.support.reflect.declaration.CtElementImpl.<spoon.reflect.reference.CtTypeReference<?>>emptyList())) {
            return false;
        }
        getFactory().getEnvironment().getModelChangeListener().onListDelete(this, spoon.reflect.path.CtRole.TYPE_ARGUMENT, actualTypeArguments, actualTypeArguments.indexOf(actualTypeArgument), actualTypeArgument);
        return actualTypeArguments.remove(actualTypeArgument);
    }

    @java.lang.Override
    public boolean isClass() {
        spoon.reflect.declaration.CtType<T> t = getTypeDeclaration();
        if (t == null) {
            java.lang.Class<?> clazz = getActualClass();
            if (((((clazz.isEnum()) || (clazz.isInterface())) || (clazz.isAnnotation())) || (clazz.isArray())) || (clazz.isPrimitive())) {
                return false;
            }
            return true;
        }else {
            return t.isClass();
        }
    }

    @java.lang.Override
    public boolean isInterface() {
        spoon.reflect.declaration.CtType<T> t = getTypeDeclaration();
        if (t == null) {
            return getActualClass().isInterface();
        }else {
            return t.isInterface();
        }
    }

    @java.lang.Override
    public boolean isAnnotationType() {
        spoon.reflect.declaration.CtType<T> t = getTypeDeclaration();
        if (t == null) {
            return getActualClass().isAnnotation();
        }else {
            return t.isAnnotationType();
        }
    }

    @java.lang.Override
    public boolean isEnum() {
        spoon.reflect.declaration.CtType<T> t = getTypeDeclaration();
        if (t == null) {
            return getActualClass().isEnum();
        }else {
            return t.isEnum();
        }
    }

    @java.lang.Override
    public boolean canAccess(spoon.reflect.reference.CtTypeReference<?> type) {
        try {
            java.util.Set<spoon.reflect.declaration.ModifierKind> modifiers = type.getModifiers();
            if (modifiers.contains(spoon.reflect.declaration.ModifierKind.PUBLIC)) {
                return true;
            }
            if (modifiers.contains(spoon.reflect.declaration.ModifierKind.PROTECTED)) {
                spoon.reflect.reference.CtTypeReference<?> declaringType = type.getDeclaringType();
                if (declaringType == null) {
                    throw new spoon.SpoonException((("The protected class " + (type.getQualifiedName())) + " has no declaring class."));
                }
                if (isImplementationOf(declaringType)) {
                    return true;
                }
                return isInSamePackage(type);
            }
            if (modifiers.contains(spoon.reflect.declaration.ModifierKind.PRIVATE)) {
                return type.getTopLevelType().getQualifiedName().equals(this.getTopLevelType().getQualifiedName());
            }
            spoon.reflect.reference.CtTypeReference<?> declaringTypeRef = type.getDeclaringType();
            if ((declaringTypeRef != null) && (declaringTypeRef.isInterface())) {
                return true;
            }
            return isInSamePackage(type);
        } catch (spoon.support.SpoonClassNotFoundException e) {
            handleParentNotFound(e);
            return true;
        }
    }

    private boolean isInSamePackage(spoon.reflect.reference.CtTypeReference<?> type) {
        return type.getTopLevelType().getPackage().getSimpleName().equals(this.getTopLevelType().getPackage().getSimpleName());
    }

    @java.lang.Override
    public spoon.reflect.reference.CtTypeReference<?> getTopLevelType() {
        spoon.reflect.reference.CtTypeReference<?> type = this;
        while (true) {
            spoon.reflect.reference.CtTypeReference<?> parentType = type.getDeclaringType();
            if (parentType == null) {
                return type;
            }
            type = parentType;
        } 
    }

    @java.lang.Override
    public spoon.reflect.reference.CtTypeReference<?> getAccessType() {
        spoon.reflect.reference.CtTypeReference<?> declType = this.getDeclaringType();
        if (declType == null) {
            throw new spoon.SpoonException((("The declaring type is expected, but " + (getQualifiedName())) + " is top level type"));
        }
        spoon.reflect.declaration.CtType<?> contextType = getParent(spoon.reflect.declaration.CtType.class);
        if (contextType == null) {
            return declType;
        }
        spoon.reflect.reference.CtTypeReference<?> contextTypeRef = contextType.getReference();
        if ((contextTypeRef != null) && ((contextTypeRef.canAccess(declType)) == false)) {
            spoon.reflect.reference.CtTypeReference<?> visibleDeclType = null;
            spoon.reflect.reference.CtTypeReference<?> type = contextTypeRef;
            while ((visibleDeclType == null) && (type != null)) {
                visibleDeclType = spoon.support.reflect.reference.CtTypeReferenceImpl.getLastVisibleSuperClassExtendingFrom(type, declType);
                if (visibleDeclType != null) {
                    spoon.support.reflect.reference.CtTypeReferenceImpl.applyActualTypeArguments(visibleDeclType, declType);
                    break;
                }
                type = type.getDeclaringType();
            } 
            declType = visibleDeclType;
        }
        if (declType == null) {
            throw new spoon.SpoonException(((("Cannot compute access path to type: " + (this.getQualifiedName())) + " in context of type: ") + (contextType.getQualifiedName())));
        }
        return declType;
    }

    private static void applyActualTypeArguments(spoon.reflect.reference.CtTypeReference<?> targetTypeRef, spoon.reflect.reference.CtTypeReference<?> sourceTypeRef) {
        spoon.reflect.reference.CtTypeReference<?> targetDeclType = targetTypeRef.getDeclaringType();
        spoon.reflect.reference.CtTypeReference<?> sourceDeclType = sourceTypeRef.getDeclaringType();
        if (((targetDeclType != null) && (sourceDeclType != null)) && (targetDeclType.isSubtypeOf(sourceDeclType))) {
            spoon.support.reflect.reference.CtTypeReferenceImpl.applyActualTypeArguments(targetDeclType, sourceDeclType);
        }
        if ((targetTypeRef.isSubtypeOf(sourceTypeRef)) == false) {
            throw new spoon.SpoonException(((("Invalid arguments. targetTypeRef " + (targetTypeRef.getQualifiedName())) + " must be a sub type of sourceTypeRef ") + (sourceTypeRef.getQualifiedName())));
        }
        java.util.List<spoon.reflect.reference.CtTypeReference<?>> newTypeArgs = new java.util.ArrayList<>();
        for (spoon.reflect.reference.CtTypeReference<?> l_tr : sourceTypeRef.getActualTypeArguments()) {
            newTypeArgs.add(l_tr.clone());
        }
        targetTypeRef.setActualTypeArguments(newTypeArgs);
    }

    private static spoon.reflect.reference.CtTypeReference<?> getLastVisibleSuperClassExtendingFrom(spoon.reflect.reference.CtTypeReference<?> sourceType, spoon.reflect.reference.CtTypeReference<?> targetType) {
        java.lang.String targetQN = targetType.getQualifiedName();
        spoon.reflect.reference.CtTypeReference<?> adept = sourceType;
        spoon.reflect.reference.CtTypeReference<?> type = sourceType;
        while (true) {
            if (targetQN.equals(type.getQualifiedName())) {
                return adept;
            }
            type = type.getSuperclass();
            if (type == null) {
                return null;
            }
            if (sourceType.canAccess(type)) {
                adept = type;
            }
        } 
    }

    @spoon.reflect.annotations.MetamodelPropertyField(role = spoon.reflect.path.CtRole.IS_SHADOW)
    boolean isShadow;

    @java.lang.Override
    public boolean isShadow() {
        return isShadow;
    }

    @java.lang.Override
    public <E extends spoon.reflect.declaration.CtShadowable> E setShadow(boolean isShadow) {
        getFactory().getEnvironment().getModelChangeListener().onObjectUpdate(this, spoon.reflect.path.CtRole.IS_SHADOW, isShadow, this.isShadow);
        this.isShadow = isShadow;
        return ((E) (this));
    }

    @java.lang.Override
    public spoon.reflect.reference.CtTypeReference<T> clone() {
        return ((spoon.reflect.reference.CtTypeReference<T>) (super.clone()));
    }

    @java.lang.Override
    public spoon.reflect.declaration.CtTypeParameter getTypeParameterDeclaration() {
        spoon.reflect.declaration.CtElement parent = this.getParent();
        if (parent instanceof spoon.reflect.reference.CtTypeReference) {
            spoon.reflect.declaration.CtType t = ((spoon.reflect.reference.CtTypeReference) (parent)).getTypeDeclaration();
            return findTypeParamDeclarationByPosition(t, ((spoon.reflect.reference.CtTypeReference) (parent)).getActualTypeArguments().indexOf(this));
        }
        if (parent instanceof spoon.reflect.reference.CtExecutableReference) {
            spoon.reflect.declaration.CtExecutable<?> exec = ((spoon.reflect.reference.CtExecutableReference<?>) (parent)).getExecutableDeclaration();
            if ((exec instanceof spoon.reflect.declaration.CtMethod) || (exec instanceof spoon.reflect.declaration.CtConstructor)) {
                return findTypeParamDeclarationByPosition(((spoon.reflect.declaration.CtFormalTypeDeclarer) (exec)), ((spoon.reflect.reference.CtTypeReference) (parent)).getActualTypeArguments().indexOf(this));
            }
        }
        return null;
    }

    @java.lang.Override
    public boolean isGenerics() {
        if ((getDeclaration()) instanceof spoon.reflect.declaration.CtTypeParameter) {
            return true;
        }
        for (spoon.reflect.reference.CtTypeReference ref : getActualTypeArguments()) {
            if (ref.isGenerics()) {
                return true;
            }
        }
        return false;
    }

    private spoon.reflect.declaration.CtTypeParameter findTypeParamDeclarationByPosition(spoon.reflect.declaration.CtFormalTypeDeclarer type, int position) {
        return type.getFormalCtTypeParameters().get(position);
    }

    @java.lang.Override
    public spoon.reflect.reference.CtTypeReference<?> getTypeErasure() {
        if (getActualTypeArguments().isEmpty()) {
            return this;
        }
        spoon.reflect.reference.CtTypeReference<?> erasedRef = clone();
        erasedRef.getActualTypeArguments().clear();
        return erasedRef;
    }
}

