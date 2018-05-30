package spoon.reflect.factory;


public class CodeFactory extends spoon.reflect.factory.SubFactory {
    public CodeFactory(spoon.reflect.factory.Factory factory) {
        super(factory);
    }

    public <T> spoon.reflect.code.CtBinaryOperator<T> createBinaryOperator(spoon.reflect.code.CtExpression<?> left, spoon.reflect.code.CtExpression<?> right, spoon.reflect.code.BinaryOperatorKind kind) {
        return factory.Core().<T>createBinaryOperator().setLeftHandOperand(left).setKind(kind).setRightHandOperand(right);
    }

    public <T> spoon.reflect.code.CtTypeAccess<T> createTypeAccess(spoon.reflect.reference.CtTypeReference<T> accessedType) {
        if (accessedType == null) {
            return factory.Core().createTypeAccess();
        }
        spoon.reflect.reference.CtTypeReference<T> access = accessedType.clone();
        access.setActualTypeArguments(null);
        return createTypeAccessWithoutCloningReference(access);
    }

    public <T> spoon.reflect.code.CtTypeAccess<T> createTypeAccess(spoon.reflect.reference.CtTypeReference<T> accessedType, boolean isImplicit) {
        return createTypeAccess(accessedType).setImplicit(isImplicit);
    }

    public <T> spoon.reflect.code.CtTypeAccess<T> createTypeAccessWithoutCloningReference(spoon.reflect.reference.CtTypeReference<T> accessedType) {
        final spoon.reflect.code.CtTypeAccess<T> typeAccess = factory.Core().createTypeAccess();
        typeAccess.setAccessedType(accessedType);
        return typeAccess;
    }

    public <T> spoon.reflect.code.CtFieldAccess<java.lang.Class<T>> createClassAccess(spoon.reflect.reference.CtTypeReference<T> type) {
        @java.lang.SuppressWarnings({ "rawtypes", "unchecked" })
        spoon.reflect.reference.CtTypeReference<java.lang.Class<T>> classType = ((spoon.reflect.reference.CtTypeReference) (factory.Type().createReference(java.lang.Class.class)));
        spoon.reflect.code.CtTypeAccess<T> typeAccess = factory.Code().createTypeAccess(type);
        spoon.reflect.reference.CtFieldReference<java.lang.Class<T>> fieldReference = factory.Core().createFieldReference();
        fieldReference.setSimpleName("class");
        fieldReference.setType(classType);
        fieldReference.setDeclaringType(type);
        spoon.reflect.code.CtFieldRead<java.lang.Class<T>> fieldRead = factory.Core().createFieldRead();
        fieldRead.setType(classType.clone());
        fieldRead.setVariable(fieldReference);
        fieldRead.setTarget(typeAccess);
        return fieldRead;
    }

    public <T> spoon.reflect.code.CtConstructorCall<T> createConstructorCall(spoon.reflect.reference.CtTypeReference<T> type, spoon.reflect.code.CtExpression<?>... parameters) {
        spoon.reflect.code.CtConstructorCall<T> constructorCall = factory.Core().createConstructorCall();
        spoon.reflect.reference.CtExecutableReference<T> executableReference = factory.Core().createExecutableReference();
        executableReference.setType(type);
        executableReference.setDeclaringType((type == null ? type : type.clone()));
        executableReference.setSimpleName(spoon.reflect.reference.CtExecutableReference.CONSTRUCTOR_NAME);
        java.util.List<spoon.reflect.reference.CtTypeReference<?>> typeReferences = new java.util.ArrayList<>();
        for (int i = 0; i < (parameters.length); i++) {
            spoon.reflect.code.CtExpression<?> parameter = parameters[i];
            typeReferences.add(parameter.getType());
        }
        executableReference.setParameters(typeReferences);
        constructorCall.setArguments(java.util.Arrays.asList(parameters));
        constructorCall.setExecutable(executableReference);
        return constructorCall;
    }

    public <T> spoon.reflect.code.CtNewClass<T> createNewClass(spoon.reflect.reference.CtTypeReference<T> type, spoon.reflect.declaration.CtClass<?> anonymousClass, spoon.reflect.code.CtExpression<?>... parameters) {
        spoon.reflect.code.CtNewClass<T> ctNewClass = factory.Core().createNewClass();
        spoon.reflect.reference.CtExecutableReference<T> executableReference = factory.Constructor().createReference(type, parameters);
        ctNewClass.setArguments(java.util.Arrays.asList(parameters));
        ctNewClass.setExecutable(executableReference);
        ctNewClass.setAnonymousClass(anonymousClass);
        anonymousClass.setSimpleName("0");
        return ctNewClass;
    }

    public <T> spoon.reflect.code.CtInvocation<T> createInvocation(spoon.reflect.code.CtExpression<?> target, spoon.reflect.reference.CtExecutableReference<T> executable, spoon.reflect.code.CtExpression<?>... arguments) {
        java.util.List<spoon.reflect.code.CtExpression<?>> ext = new java.util.ArrayList<>(arguments.length);
        java.util.Collections.addAll(ext, arguments);
        return createInvocation(target, executable, ext);
    }

    public <T> spoon.reflect.code.CtInvocation<T> createInvocation(spoon.reflect.code.CtExpression<?> target, spoon.reflect.reference.CtExecutableReference<T> executable, java.util.List<spoon.reflect.code.CtExpression<?>> arguments) {
        return factory.Core().<T>createInvocation().<spoon.reflect.code.CtInvocation<T>>setTarget(target).<spoon.reflect.code.CtInvocation<T>>setExecutable(executable).setArguments(arguments);
    }

    public <T> spoon.reflect.code.CtLiteral<T> createLiteral(T value) {
        spoon.reflect.code.CtLiteral<T> literal = factory.Core().<T>createLiteral();
        literal.setValue(value);
        if (value != null) {
            literal.setType(((spoon.reflect.reference.CtTypeReference<T>) (factory.Type().<T>createReference(((java.lang.Class<T>) (value.getClass()))).unbox())));
        }else {
            literal.setType(((spoon.reflect.reference.CtTypeReference<T>) (factory.Type().nullType())));
        }
        return literal;
    }

    @java.lang.SuppressWarnings("unchecked")
    public <T> spoon.reflect.code.CtNewArray<T[]> createLiteralArray(T[] value) {
        if (!(value.getClass().isArray())) {
            throw new java.lang.RuntimeException("value is not an array");
        }
        if (value.getClass().getComponentType().isArray()) {
            throw new java.lang.RuntimeException("can only create one-dimension arrays");
        }
        final spoon.reflect.reference.CtTypeReference<T> componentTypeRef = factory.Type().createReference(((java.lang.Class<T>) (value.getClass().getComponentType())));
        final spoon.reflect.reference.CtArrayTypeReference<T[]> arrayReference = factory.Type().createArrayReference(componentTypeRef);
        spoon.reflect.code.CtNewArray<T[]> array = factory.Core().<T[]>createNewArray().setType(arrayReference);
        for (T e : value) {
            spoon.reflect.code.CtLiteral<T> l = factory.Core().createLiteral();
            l.setValue(e);
            array.addElement(l);
        }
        return array;
    }

    public <T> spoon.reflect.code.CtLocalVariable<T> createLocalVariable(spoon.reflect.reference.CtTypeReference<T> type, java.lang.String name, spoon.reflect.code.CtExpression<T> defaultExpression) {
        return factory.Core().<T>createLocalVariable().<spoon.reflect.code.CtLocalVariable<T>>setSimpleName(name).<spoon.reflect.code.CtLocalVariable<T>>setType(type).setDefaultExpression(defaultExpression);
    }

    public <T> spoon.reflect.reference.CtLocalVariableReference<T> createLocalVariableReference(spoon.reflect.code.CtLocalVariable<T> localVariable) {
        spoon.reflect.reference.CtLocalVariableReference<T> ref = factory.Core().createLocalVariableReference();
        ref.setType(((localVariable.getType()) == null ? null : localVariable.getType().clone()));
        ref.setSimpleName(localVariable.getSimpleName());
        ref.setParent(localVariable);
        return ref;
    }

    public <T> spoon.reflect.reference.CtLocalVariableReference<T> createLocalVariableReference(spoon.reflect.reference.CtTypeReference<T> type, java.lang.String name) {
        return factory.Core().<T>createLocalVariableReference().setType(type).setSimpleName(name);
    }

    public <T> spoon.reflect.code.CtCatchVariable<T> createCatchVariable(spoon.reflect.reference.CtTypeReference<T> type, java.lang.String name, spoon.reflect.declaration.ModifierKind... modifierKinds) {
        return factory.Core().<T>createCatchVariable().<spoon.reflect.code.CtCatchVariable<T>>setSimpleName(name).<spoon.reflect.code.CtCatchVariable<T>>setType(type).setModifiers(new java.util.HashSet<>(java.util.Arrays.asList(modifierKinds)));
    }

    public <T> spoon.reflect.reference.CtCatchVariableReference<T> createCatchVariableReference(spoon.reflect.code.CtCatchVariable<T> catchVariable) {
        return factory.Core().<T>createCatchVariableReference().setType(catchVariable.getType()).<spoon.reflect.reference.CtCatchVariableReference<T>>setSimpleName(catchVariable.getSimpleName());
    }

    public <R> spoon.reflect.code.CtStatementList createStatementList(spoon.reflect.code.CtBlock<R> block) {
        spoon.reflect.code.CtStatementList l = factory.Core().createStatementList();
        for (spoon.reflect.code.CtStatement s : block.getStatements()) {
            l.addStatement(s.clone());
        }
        return l;
    }

    public <T> spoon.reflect.code.CtThisAccess<T> createThisAccess(spoon.reflect.reference.CtTypeReference<T> type) {
        return createThisAccess(type, false);
    }

    public <T> spoon.reflect.code.CtThisAccess<T> createThisAccess(spoon.reflect.reference.CtTypeReference<T> type, boolean isImplicit) {
        spoon.reflect.code.CtThisAccess<T> thisAccess = factory.Core().<T>createThisAccess();
        thisAccess.setImplicit(isImplicit);
        thisAccess.setType(type);
        spoon.reflect.code.CtTypeAccess<T> typeAccess = factory.Code().createTypeAccess(type);
        thisAccess.setTarget(typeAccess);
        return thisAccess;
    }

    public <T> spoon.reflect.code.CtVariableAccess<T> createVariableRead(spoon.reflect.reference.CtVariableReference<T> variable, boolean isStatic) {
        spoon.reflect.code.CtVariableAccess<T> va;
        if (variable instanceof spoon.reflect.reference.CtFieldReference) {
            va = factory.Core().createFieldRead();
            if (!isStatic) {
                ((spoon.reflect.code.CtFieldAccess<T>) (va)).setTarget(createThisAccess(((spoon.reflect.reference.CtFieldReference<T>) (variable)).getDeclaringType()));
            }
        }else {
            va = factory.Core().createVariableRead();
        }
        return va.setVariable(variable);
    }

    public java.util.List<spoon.reflect.code.CtExpression<?>> createVariableReads(java.util.List<? extends spoon.reflect.declaration.CtVariable<?>> variables) {
        java.util.List<spoon.reflect.code.CtExpression<?>> result = new java.util.ArrayList<>(variables.size());
        for (spoon.reflect.declaration.CtVariable<?> v : variables) {
            result.add(createVariableRead(v.getReference(), v.getModifiers().contains(spoon.reflect.declaration.ModifierKind.STATIC)));
        }
        return result;
    }

    public <A, T extends A> spoon.reflect.code.CtAssignment<A, T> createVariableAssignment(spoon.reflect.reference.CtVariableReference<A> variable, boolean isStatic, spoon.reflect.code.CtExpression<T> expression) {
        spoon.reflect.code.CtVariableAccess<A> vaccess = createVariableRead(variable, isStatic);
        return factory.Core().<A, T>createAssignment().<spoon.reflect.code.CtAssignment<A, T>>setAssignment(expression).setAssigned(vaccess);
    }

    public <T> spoon.reflect.code.CtStatementList createVariableAssignments(java.util.List<? extends spoon.reflect.declaration.CtVariable<T>> variables, java.util.List<? extends spoon.reflect.code.CtExpression<T>> expressions) {
        spoon.reflect.code.CtStatementList result = factory.Core().createStatementList();
        for (int i = 0; i < (variables.size()); i++) {
            result.addStatement(createVariableAssignment(variables.get(i).getReference(), variables.get(i).getModifiers().contains(spoon.reflect.declaration.ModifierKind.STATIC), expressions.get(i)));
        }
        return result;
    }

    public <T> spoon.reflect.declaration.CtField<T> createCtField(java.lang.String name, spoon.reflect.reference.CtTypeReference<T> type, java.lang.String exp, spoon.reflect.declaration.ModifierKind... visibilities) {
        return factory.Core().createField().<spoon.reflect.declaration.CtField<T>>setModifiers(modifiers(visibilities)).<spoon.reflect.declaration.CtField<T>>setSimpleName(name).<spoon.reflect.declaration.CtField<T>>setType(type).setDefaultExpression(this.<T>createCodeSnippetExpression(exp));
    }

    public <T extends spoon.reflect.code.CtStatement> spoon.reflect.code.CtBlock<?> createCtBlock(T element) {
        return factory.Core().createBlock().addStatement(element);
    }

    public <T extends spoon.reflect.code.CtStatement> spoon.reflect.code.CtBlock<?> getOrCreateCtBlock(T element) {
        if (element == null) {
            return null;
        }
        if (element instanceof spoon.reflect.code.CtBlock<?>) {
            return ((spoon.reflect.code.CtBlock<?>) (element));
        }
        return this.createCtBlock(element);
    }

    public spoon.reflect.code.CtThrow createCtThrow(java.lang.String thrownExp) {
        return factory.Core().createThrow().setThrownExpression(this.<java.lang.Throwable>createCodeSnippetExpression(thrownExp));
    }

    public spoon.reflect.code.CtCatch createCtCatch(java.lang.String nameCatch, java.lang.Class<? extends java.lang.Throwable> exception, spoon.reflect.code.CtBlock<?> ctBlock) {
        final spoon.reflect.code.CtCatchVariable<java.lang.Throwable> catchVariable = factory.Core().<java.lang.Throwable>createCatchVariable().<spoon.reflect.code.CtCatchVariable<java.lang.Throwable>>setType(this.<java.lang.Throwable>createCtTypeReference(exception)).setSimpleName(nameCatch);
        return factory.Core().createCatch().setParameter(catchVariable).setBody(ctBlock);
    }

    public <T> spoon.reflect.reference.CtTypeReference<T> createCtTypeReference(java.lang.Class<?> originalClass) {
        if (originalClass == null) {
            return null;
        }
        spoon.reflect.reference.CtTypeReference<T> typeReference = factory.Core().<T>createTypeReference();
        typeReference.setSimpleName(originalClass.getSimpleName());
        if (originalClass.isPrimitive()) {
            return typeReference;
        }
        if ((originalClass.getDeclaringClass()) != null) {
            return typeReference.setDeclaringType(createCtTypeReference(originalClass.getDeclaringClass()));
        }
        return typeReference.setPackage(createCtPackageReference(originalClass.getPackage()));
    }

    public spoon.reflect.reference.CtPackageReference createCtPackageReference(java.lang.Package originalPackage) {
        return factory.Core().createPackageReference().setSimpleName(originalPackage.getName());
    }

    public <A extends java.lang.annotation.Annotation> spoon.reflect.declaration.CtAnnotation<A> createAnnotation(spoon.reflect.reference.CtTypeReference<A> annotationType) {
        final spoon.reflect.declaration.CtAnnotation<A> a = factory.Core().createAnnotation();
        a.setAnnotationType(annotationType);
        return a;
    }

    @java.lang.SuppressWarnings("unchecked")
    public <R extends spoon.reflect.reference.CtReference, E extends spoon.reflect.declaration.CtNamedElement> java.util.List<R> getReferences(java.util.List<E> elements) {
        java.util.List<R> refs = new java.util.ArrayList<>(elements.size());
        for (E e : elements) {
            refs.add(((R) (e.getReference())));
        }
        return refs;
    }

    public java.util.Set<spoon.reflect.declaration.ModifierKind> modifiers(spoon.reflect.declaration.ModifierKind... modifiers) {
        java.util.Set<spoon.reflect.declaration.ModifierKind> ret = java.util.EnumSet.noneOf(spoon.reflect.declaration.ModifierKind.class);
        java.util.Collections.addAll(ret, modifiers);
        return ret;
    }

    public <T> spoon.reflect.code.CtCodeSnippetExpression<T> createCodeSnippetExpression(java.lang.String expression) {
        spoon.reflect.code.CtCodeSnippetExpression<T> e = factory.Core().createCodeSnippetExpression();
        e.setValue(expression);
        return e;
    }

    public spoon.reflect.code.CtCodeSnippetStatement createCodeSnippetStatement(java.lang.String statement) {
        spoon.reflect.code.CtCodeSnippetStatement e = factory.Core().createCodeSnippetStatement();
        e.setValue(statement);
        return e;
    }

    public spoon.reflect.code.CtComment createComment(java.lang.String content, spoon.reflect.code.CtComment.CommentType type) {
        if (type == (spoon.reflect.code.CtComment.CommentType.JAVADOC)) {
            return factory.Core().createJavaDoc().setContent(content);
        }
        return factory.Core().createComment().setContent(content).setCommentType(type);
    }

    public spoon.reflect.code.CtComment createInlineComment(java.lang.String content) {
        return createComment(content, spoon.reflect.code.CtComment.CommentType.INLINE);
    }

    public spoon.reflect.code.CtJavaDocTag createJavaDocTag(java.lang.String content, spoon.reflect.code.CtJavaDocTag.TagType type) {
        if (content == null) {
            content = "";
        }
        spoon.reflect.code.CtJavaDocTag docTag = factory.Core().createJavaDocTag();
        if ((type != null) && (type.hasParam())) {
            int firstWord = content.indexOf(" ");
            int firstLine = content.indexOf("\n");
            if ((firstLine < firstWord) && (firstLine >= 0)) {
                firstWord = firstLine;
            }
            if (firstWord == (-1)) {
                firstWord = content.length();
            }
            java.lang.String param = content.substring(0, firstWord);
            content = content.substring(firstWord);
            docTag.setParam(param);
        }
        return docTag.setContent(content.trim()).setType(type);
    }
}

