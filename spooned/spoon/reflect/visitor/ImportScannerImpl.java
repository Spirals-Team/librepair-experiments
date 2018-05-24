package spoon.reflect.visitor;


public class ImportScannerImpl extends spoon.reflect.visitor.CtScanner implements spoon.reflect.visitor.ImportScanner {
    private static final java.util.Collection<java.lang.String> namesPresentInJavaLang8 = java.util.Collections.singletonList("FunctionalInterface");

    private static final java.util.Collection<java.lang.String> namesPresentInJavaLang9 = java.util.Arrays.asList("ProcessHandle", "StackWalker", "StackFramePermission");

    protected java.util.Map<java.lang.String, spoon.reflect.reference.CtTypeReference<?>> classImports = new java.util.TreeMap<>();

    protected java.util.Map<java.lang.String, spoon.reflect.reference.CtFieldReference<?>> fieldImports = new java.util.TreeMap<>();

    protected java.util.Map<java.lang.String, spoon.reflect.reference.CtExecutableReference<?>> methodImports = new java.util.TreeMap<>();

    protected spoon.reflect.reference.CtTypeReference<?> targetType;

    private java.util.Map<java.lang.String, java.lang.Boolean> namesPresentInJavaLang = new java.util.HashMap<>();

    private java.util.Set<java.lang.String> fieldAndMethodsNames = new java.util.HashSet<java.lang.String>();

    private java.util.Set<spoon.reflect.reference.CtTypeReference> exploredReferences = new java.util.HashSet<>();

    @java.lang.Override
    public <T> void visitCtFieldRead(spoon.reflect.code.CtFieldRead<T> fieldRead) {
        enter(fieldRead);
        scan(fieldRead.getAnnotations());
        scan(fieldRead.getTypeCasts());
        scan(fieldRead.getVariable());
        scan(fieldRead.getTarget());
        exit(fieldRead);
    }

    @java.lang.Override
    public <T> void visitCtFieldReference(spoon.reflect.reference.CtFieldReference<T> reference) {
        enter(reference);
        scan(reference.getDeclaringType());
        if (reference.isStatic()) {
            addFieldImport(reference);
        }else {
            scan(reference.getDeclaringType());
        }
        exit(reference);
    }

    @java.lang.Override
    public <T> void visitCtExecutableReference(spoon.reflect.reference.CtExecutableReference<T> reference) {
        enter(reference);
        if (reference.isStatic()) {
            addMethodImport(reference);
        }else
            if (reference.isConstructor()) {
                scan(reference.getDeclaringType());
            }

        scan(reference.getActualTypeArguments());
        exit(reference);
    }

    @java.lang.Override
    public <T> void visitCtTypeReference(spoon.reflect.reference.CtTypeReference<T> reference) {
        if (!(reference instanceof spoon.reflect.reference.CtArrayTypeReference)) {
            spoon.reflect.reference.CtTypeReference typeReference;
            if ((reference.getDeclaringType()) == null) {
                typeReference = reference;
            }else {
                typeReference = reference.getAccessType();
            }
            if (!(this.isTypeInCollision(typeReference, false))) {
                this.addClassImport(typeReference);
            }
        }
        super.visitCtTypeReference(reference);
    }

    @java.lang.Override
    public void scan(spoon.reflect.declaration.CtElement element) {
        if ((element != null) && (!(element.isImplicit()))) {
            element.accept(this);
        }
    }

    @java.lang.Override
    public <A extends java.lang.annotation.Annotation> void visitCtAnnotationType(spoon.reflect.declaration.CtAnnotationType<A> annotationType) {
        addClassImport(annotationType.getReference());
        super.visitCtAnnotationType(annotationType);
    }

    @java.lang.Override
    public <T extends java.lang.Enum<?>> void visitCtEnum(spoon.reflect.declaration.CtEnum<T> ctEnum) {
        addClassImport(ctEnum.getReference());
        super.visitCtEnum(ctEnum);
    }

    @java.lang.Override
    public <T> void visitCtInterface(spoon.reflect.declaration.CtInterface<T> intrface) {
        addClassImport(intrface.getReference());
        for (spoon.reflect.declaration.CtTypeMember t : intrface.getTypeMembers()) {
            if (!(t instanceof spoon.reflect.declaration.CtType)) {
                continue;
            }
            addClassImport(((spoon.reflect.declaration.CtType) (t)).getReference());
        }
        super.visitCtInterface(intrface);
    }

    @java.lang.Override
    public <T> void visitCtClass(spoon.reflect.declaration.CtClass<T> ctClass) {
        addClassImport(ctClass.getReference());
        for (spoon.reflect.declaration.CtTypeMember t : ctClass.getTypeMembers()) {
            if (!(t instanceof spoon.reflect.declaration.CtType)) {
                continue;
            }
            addClassImport(((spoon.reflect.declaration.CtType) (t)).getReference());
        }
        super.visitCtClass(ctClass);
    }

    @java.lang.Override
    public <T> void visitCtCatchVariable(spoon.reflect.code.CtCatchVariable<T> catchVariable) {
        for (spoon.reflect.reference.CtTypeReference<?> type : catchVariable.getMultiTypes()) {
            addClassImport(type);
        }
        super.visitCtCatchVariable(catchVariable);
    }

    @java.lang.Override
    public java.util.Collection<spoon.reflect.declaration.CtImport> getAllImports() {
        java.util.Collection<spoon.reflect.declaration.CtImport> listallImports = new java.util.ArrayList<>();
        for (spoon.reflect.reference.CtReference reference : this.classImports.values()) {
            listallImports.add(reference.getFactory().Type().createImport(reference));
        }
        for (spoon.reflect.reference.CtReference reference : this.fieldImports.values()) {
            listallImports.add(reference.getFactory().Type().createImport(reference));
        }
        for (spoon.reflect.reference.CtReference reference : this.methodImports.values()) {
            listallImports.add(reference.getFactory().Type().createImport(reference));
        }
        return listallImports;
    }

    @java.lang.Override
    public void computeImports(spoon.reflect.declaration.CtElement element) {
        if (element instanceof spoon.reflect.declaration.CtType) {
            spoon.reflect.declaration.CtType simpleType = ((spoon.reflect.declaration.CtType) (element));
            targetType = simpleType.getReference().getTopLevelType();
            addClassImport(simpleType.getReference());
            scan(simpleType);
        }else {
            spoon.reflect.declaration.CtType<?> type = element.getParent(spoon.reflect.declaration.CtType.class);
            targetType = (type == null) ? null : type.getReference().getTopLevelType();
            scan(element);
        }
    }

    @java.lang.Override
    public boolean isImported(spoon.reflect.reference.CtReference ref) {
        if (ref instanceof spoon.reflect.reference.CtFieldReference) {
            return isImportedInFieldImports(((spoon.reflect.reference.CtFieldReference) (ref)));
        }else
            if (ref instanceof spoon.reflect.reference.CtExecutableReference) {
                return isImportedInMethodImports(((spoon.reflect.reference.CtExecutableReference) (ref)));
            }else
                if (ref instanceof spoon.reflect.reference.CtTypeReference) {
                    return isImportedInClassImports(((spoon.reflect.reference.CtTypeReference) (ref)));
                }else {
                    return false;
                }


    }

    private boolean isThereAnotherClassWithSameNameInAnotherPackage(spoon.reflect.reference.CtTypeReference<?> ref) {
        for (spoon.reflect.reference.CtTypeReference typeref : this.exploredReferences) {
            if ((typeref.getSimpleName().equals(ref.getSimpleName())) && (!(typeref.getQualifiedName().equals(ref.getQualifiedName())))) {
                return true;
            }
        }
        return false;
    }

    protected boolean addClassImport(spoon.reflect.reference.CtTypeReference<?> ref) {
        this.exploredReferences.add(ref);
        if (ref == null) {
            return false;
        }
        if ((((targetType) != null) && (targetType.getSimpleName().equals(ref.getSimpleName()))) && (!(targetType.equals(ref)))) {
            return false;
        }
        if (classImports.containsKey(ref.getSimpleName())) {
            return isImportedInClassImports(ref);
        }
        if (((ref.getPackage()) == null) || (ref.getPackage().isUnnamedPackage())) {
            return false;
        }
        if (((targetType) != null) && ((targetType.canAccess(ref)) == false)) {
            return false;
        }
        if (this.isThereAnotherClassWithSameNameInAnotherPackage(ref)) {
            return false;
        }
        if ((targetType) != null) {
            try {
                spoon.reflect.declaration.CtElement parent = ref.getParent();
                if (parent != null) {
                    parent = parent.getParent();
                    if (parent != null) {
                        if (((parent instanceof spoon.reflect.code.CtFieldAccess) || (parent instanceof spoon.reflect.declaration.CtExecutable)) || (parent instanceof spoon.reflect.code.CtInvocation)) {
                            spoon.reflect.reference.CtTypeReference declaringType;
                            spoon.reflect.reference.CtReference reference;
                            spoon.reflect.reference.CtPackageReference pack = targetType.getPackage();
                            if (parent instanceof spoon.reflect.code.CtFieldAccess) {
                                spoon.reflect.code.CtFieldAccess field = ((spoon.reflect.code.CtFieldAccess) (parent));
                                spoon.reflect.reference.CtFieldReference localReference = field.getVariable();
                                declaringType = localReference.getDeclaringType();
                                reference = localReference;
                            }else
                                if (parent instanceof spoon.reflect.declaration.CtExecutable) {
                                    spoon.reflect.declaration.CtExecutable exec = ((spoon.reflect.declaration.CtExecutable) (parent));
                                    spoon.reflect.reference.CtExecutableReference localReference = exec.getReference();
                                    declaringType = localReference.getDeclaringType();
                                    reference = localReference;
                                }else
                                    if (parent instanceof spoon.reflect.code.CtInvocation) {
                                        spoon.reflect.code.CtInvocation invo = ((spoon.reflect.code.CtInvocation) (parent));
                                        spoon.reflect.reference.CtExecutableReference localReference = invo.getExecutable();
                                        declaringType = localReference.getDeclaringType();
                                        reference = localReference;
                                    }else {
                                        declaringType = null;
                                        reference = null;
                                    }


                            if ((reference != null) && (isImported(reference))) {
                                if (declaringType != null) {
                                    if (((declaringType.getPackage()) != null) && (!(declaringType.getPackage().isUnnamedPackage()))) {
                                        if (!(declaringType.getPackage().getSimpleName().equals("java.lang"))) {
                                            if (declaringType.getPackage().getSimpleName().equals(pack.getSimpleName())) {
                                                classImports.put(ref.getSimpleName(), ref);
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (spoon.reflect.declaration.ParentNotInitializedException e) {
            }
            spoon.reflect.reference.CtPackageReference pack = targetType.getPackage();
            if (((pack != null) && ((ref.getPackage()) != null)) && (!(ref.getPackage().isUnnamedPackage()))) {
                if (!(ref.getPackage().getSimpleName().equals("java.lang"))) {
                    if (ref.getPackage().getSimpleName().equals(pack.getSimpleName())) {
                        return false;
                    }
                }
            }
        }
        classImports.put(ref.getSimpleName(), ref);
        return true;
    }

    protected boolean isImportedInClassImports(spoon.reflect.reference.CtTypeReference<?> ref) {
        if ((targetType) != null) {
            spoon.reflect.reference.CtPackageReference pack = targetType.getPackage();
            if (((pack != null) && ((ref.getPackage()) != null)) && (!(ref.getPackage().isUnnamedPackage()))) {
                if (!(ref.getPackage().getSimpleName().equals("java.lang"))) {
                    if (ref.getPackage().getSimpleName().equals(pack.getSimpleName())) {
                        return true;
                    }
                }
            }
        }
        if (ref.equals(targetType)) {
            return true;
        }
        if ((!(ref.isImplicit())) && (classImports.containsKey(ref.getSimpleName()))) {
            spoon.reflect.reference.CtTypeReference<?> exist = classImports.get(ref.getSimpleName());
            if (exist.getQualifiedName().equals(ref.getQualifiedName())) {
                return true;
            }
        }
        return false;
    }

    private boolean declaringTypeIsLocalOrImported(spoon.reflect.reference.CtTypeReference declaringType) {
        if (declaringType != null) {
            boolean isInCollision = isTypeInCollision(declaringType, false);
            if (!isInCollision) {
                boolean importSuccess = addClassImport(declaringType);
                if (importSuccess) {
                    return true;
                }
            }
            boolean importedInClassImports = isImportedInClassImports(declaringType);
            boolean inJavaLang = classNamePresentInJavaLang(declaringType);
            if (importedInClassImports || inJavaLang) {
                return true;
            }
            while (declaringType != null) {
                if (declaringType.equals(targetType)) {
                    return true;
                }
                declaringType = declaringType.getDeclaringType();
            } 
        }
        return false;
    }

    private boolean isInCollisionWithLocalMethod(spoon.reflect.reference.CtExecutableReference ref) {
        spoon.reflect.declaration.CtType<?> typeDecl = ref.getParent(spoon.reflect.declaration.CtType.class);
        if (typeDecl != null) {
            java.lang.String methodName = ref.getSimpleName();
            for (spoon.reflect.declaration.CtMethod<?> method : typeDecl.getAllMethods()) {
                if (method.getSimpleName().equals(methodName)) {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean addMethodImport(spoon.reflect.reference.CtExecutableReference ref) {
        if ((ref.getFactory().getEnvironment().getComplianceLevel()) < 5) {
            return false;
        }
        if (this.methodImports.containsKey(ref.getSimpleName())) {
            return isImportedInMethodImports(ref);
        }
        if (declaringTypeIsLocalOrImported(ref.getDeclaringType())) {
            return false;
        }
        if (this.isInCollisionWithLocalMethod(ref)) {
            return false;
        }
        methodImports.put(ref.getSimpleName(), ref);
        if ((ref.getDeclaringType()) != null) {
            if ((ref.getDeclaringType().getPackage()) != null) {
                if (ref.getDeclaringType().getPackage().equals(this.targetType.getPackage())) {
                    addClassImport(ref.getDeclaringType());
                }
            }
        }
        return true;
    }

    protected boolean isImportedInMethodImports(spoon.reflect.reference.CtExecutableReference<?> ref) {
        if ((!(ref.isImplicit())) && (methodImports.containsKey(ref.getSimpleName()))) {
            spoon.reflect.reference.CtExecutableReference<?> exist = methodImports.get(ref.getSimpleName());
            if (getSignature(exist).equals(getSignature(ref))) {
                return true;
            }
        }
        return false;
    }

    private java.lang.String getSignature(spoon.reflect.reference.CtExecutableReference<?> exist) {
        return (((exist.getDeclaringType()) != null ? exist.getDeclaringType().getQualifiedName() : "") + ".") + (exist.getSignature());
    }

    protected boolean addFieldImport(spoon.reflect.reference.CtFieldReference ref) {
        if ((ref.getFactory().getEnvironment().getComplianceLevel()) < 5) {
            return false;
        }
        if (this.fieldImports.containsKey(ref.getSimpleName())) {
            return isImportedInFieldImports(ref);
        }
        if (declaringTypeIsLocalOrImported(ref.getDeclaringType())) {
            return false;
        }
        fieldImports.put(ref.getSimpleName(), ref);
        return true;
    }

    protected boolean isImportedInFieldImports(spoon.reflect.reference.CtFieldReference<?> ref) {
        if ((!(ref.isImplicit())) && (fieldImports.containsKey(ref.getSimpleName()))) {
            spoon.reflect.reference.CtFieldReference<?> exist = fieldImports.get(ref.getSimpleName());
            try {
                if (((exist.getFieldDeclaration()) != null) && (exist.getFieldDeclaration().equals(ref.getFieldDeclaration()))) {
                    return true;
                }
            } catch (spoon.support.SpoonClassNotFoundException notfound) {
                return false;
            }
        }
        return false;
    }

    protected boolean classNamePresentInJavaLang(spoon.reflect.reference.CtTypeReference<?> ref) {
        java.lang.Boolean presentInJavaLang = namesPresentInJavaLang.get(ref.getSimpleName());
        if (presentInJavaLang == null) {
            if ((spoon.reflect.visitor.ImportScannerImpl.namesPresentInJavaLang8.contains(ref.getSimpleName())) || (spoon.reflect.visitor.ImportScannerImpl.namesPresentInJavaLang9.contains(ref.getSimpleName()))) {
                presentInJavaLang = true;
            }else {
                try {
                    java.lang.Class.forName(("java.lang." + (ref.getSimpleName())));
                    presentInJavaLang = true;
                } catch (java.lang.NoClassDefFoundError | java.lang.ClassNotFoundException e) {
                    presentInJavaLang = false;
                }
            }
            namesPresentInJavaLang.put(ref.getSimpleName(), presentInJavaLang);
        }
        return presentInJavaLang;
    }

    protected java.util.Set<java.lang.String> lookForLocalVariables(spoon.reflect.declaration.CtElement parent) {
        java.util.Set<java.lang.String> result = new java.util.HashSet<>();
        while ((parent != null) && (!(parent instanceof spoon.reflect.code.CtBlock))) {
            if (parent instanceof spoon.reflect.declaration.CtClass) {
                return result;
            }
            parent = parent.getParent();
        } 
        if (parent != null) {
            spoon.reflect.code.CtBlock block = ((spoon.reflect.code.CtBlock) (parent));
            boolean innerClass = false;
            while ((parent != null) && (!(parent instanceof spoon.reflect.declaration.CtClass))) {
                parent = parent.getParent();
            } 
            if (parent != null) {
                if (!((parent.getParent()) instanceof spoon.reflect.declaration.CtPackage)) {
                    while ((parent != null) && (!(parent instanceof spoon.reflect.code.CtBlock))) {
                        parent = parent.getParent();
                    } 
                    if (parent != null) {
                        block = ((spoon.reflect.code.CtBlock) (parent));
                    }
                }
            }
            spoon.reflect.visitor.AccessibleVariablesFinder avf = new spoon.reflect.visitor.AccessibleVariablesFinder(block);
            java.util.List<spoon.reflect.declaration.CtVariable> variables = avf.find();
            for (spoon.reflect.declaration.CtVariable variable : variables) {
                result.add(variable.getSimpleName());
            }
        }
        return result;
    }

    protected boolean isTypeInCollision(spoon.reflect.reference.CtReference ref, boolean fqnMode) {
        if ((((targetType) != null) && (targetType.getSimpleName().equals(ref.getSimpleName()))) && (!(targetType.equals(ref)))) {
            return true;
        }
        try {
            spoon.reflect.declaration.CtElement parent;
            if (ref instanceof spoon.reflect.reference.CtTypeReference) {
                parent = ref.getParent();
            }else {
                parent = ref;
            }
            if (parent instanceof spoon.reflect.code.CtLiteral) {
                return false;
            }
            java.util.Set<java.lang.String> localVariablesOfBlock = new java.util.HashSet<>();
            if (parent instanceof spoon.reflect.declaration.CtField) {
                this.fieldAndMethodsNames.add(((spoon.reflect.declaration.CtField) (parent)).getSimpleName());
            }else
                if (parent instanceof spoon.reflect.declaration.CtMethod) {
                    this.fieldAndMethodsNames.add(((spoon.reflect.declaration.CtMethod) (parent)).getSimpleName());
                }else {
                    localVariablesOfBlock = this.lookForLocalVariables(parent);
                }

            while (!(parent instanceof spoon.reflect.declaration.CtPackage)) {
                if (((parent instanceof spoon.reflect.reference.CtFieldReference) || (parent instanceof spoon.reflect.reference.CtExecutableReference)) || (parent instanceof spoon.reflect.code.CtInvocation)) {
                    spoon.reflect.reference.CtReference parentType;
                    if (parent instanceof spoon.reflect.code.CtInvocation) {
                        parentType = ((spoon.reflect.code.CtInvocation) (parent)).getExecutable();
                    }else {
                        parentType = ((spoon.reflect.reference.CtReference) (parent));
                    }
                    java.util.LinkedList<java.lang.String> qualifiedNameTokens = new java.util.LinkedList<>();
                    if (parentType != parent) {
                        qualifiedNameTokens.add(parentType.getSimpleName());
                    }
                    spoon.reflect.reference.CtTypeReference typeReference;
                    if (parent instanceof spoon.reflect.reference.CtFieldReference) {
                        typeReference = ((spoon.reflect.reference.CtFieldReference) (parent)).getDeclaringType();
                    }else
                        if (parent instanceof spoon.reflect.reference.CtExecutableReference) {
                            typeReference = ((spoon.reflect.reference.CtExecutableReference) (parent)).getDeclaringType();
                        }else {
                            typeReference = ((spoon.reflect.code.CtInvocation) (parent)).getExecutable().getDeclaringType();
                        }

                    if (typeReference != null) {
                        qualifiedNameTokens.addFirst(typeReference.getSimpleName());
                        if ((typeReference.getPackage()) != null) {
                            java.util.StringTokenizer token = new java.util.StringTokenizer(typeReference.getPackage().getSimpleName(), spoon.reflect.declaration.CtPackage.PACKAGE_SEPARATOR);
                            int index = 0;
                            while (token.hasMoreElements()) {
                                qualifiedNameTokens.add(index, token.nextToken());
                                index++;
                            } 
                        }
                    }
                    if (!(qualifiedNameTokens.isEmpty())) {
                        if ((fieldAndMethodsNames.contains(qualifiedNameTokens.getFirst())) || (localVariablesOfBlock.contains(qualifiedNameTokens.getFirst()))) {
                            qualifiedNameTokens.removeFirst();
                            if (fqnMode) {
                                if (ref instanceof spoon.reflect.reference.CtTypeReference) {
                                    if (qualifiedNameTokens.isEmpty()) {
                                        return true;
                                    }
                                    for (int i = 0; i < (qualifiedNameTokens.size()); i++) {
                                        java.lang.String testedToken = qualifiedNameTokens.get(i);
                                        if ((!(fieldAndMethodsNames.contains(testedToken))) && (!(localVariablesOfBlock.contains(testedToken)))) {
                                            return true;
                                        }
                                    }
                                    return false;
                                }else {
                                    return true;
                                }
                            }else {
                                for (int i = 0; i < (qualifiedNameTokens.size()); i++) {
                                    java.lang.String testedToken = qualifiedNameTokens.get(i);
                                    if ((!(fieldAndMethodsNames.contains(testedToken))) && (!(localVariablesOfBlock.contains(testedToken)))) {
                                        return false;
                                    }
                                }
                                return true;
                            }
                        }
                    }
                }
                parent = parent.getParent();
            } 
        } catch (spoon.reflect.declaration.ParentNotInitializedException e) {
            return false;
        }
        return false;
    }
}

