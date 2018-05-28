package spoon.reflect.factory;


public interface Factory {
    spoon.reflect.CtModel getModel();

    spoon.reflect.factory.CoreFactory Core();

    spoon.reflect.factory.TypeFactory Type();

    spoon.reflect.factory.EnumFactory Enum();

    spoon.compiler.Environment getEnvironment();

    spoon.reflect.factory.PackageFactory Package();

    spoon.reflect.factory.CodeFactory Code();

    spoon.reflect.factory.ClassFactory Class();

    spoon.reflect.factory.FieldFactory Field();

    spoon.reflect.factory.ExecutableFactory Executable();

    spoon.reflect.factory.CompilationUnitFactory CompilationUnit();

    spoon.reflect.factory.InterfaceFactory Interface();

    spoon.reflect.factory.MethodFactory Method();

    spoon.reflect.factory.AnnotationFactory Annotation();

    spoon.reflect.factory.EvalFactory Eval();

    spoon.reflect.factory.ConstructorFactory Constructor();

    spoon.reflect.factory.QueryFactory Query();

    spoon.reflect.factory.ModuleFactory Module();

    <A extends java.lang.annotation.Annotation> spoon.reflect.declaration.CtAnnotation<A> createAnnotation(spoon.reflect.reference.CtTypeReference<A> annotationType);

    <A, T extends A> spoon.reflect.code.CtAssignment<A, T> createVariableAssignment(spoon.reflect.reference.CtVariableReference<A> variable, boolean isStatic, spoon.reflect.code.CtExpression<T> expression);

    <R> spoon.reflect.code.CtStatementList createStatementList(spoon.reflect.code.CtBlock<R> block);

    <T extends spoon.reflect.code.CtStatement> spoon.reflect.code.CtBlock<?> createCtBlock(T element);

    <T> spoon.reflect.code.CtBinaryOperator<T> createBinaryOperator(spoon.reflect.code.CtExpression<?> left, spoon.reflect.code.CtExpression<?> right, spoon.reflect.code.BinaryOperatorKind kind);

    <T> spoon.reflect.code.CtCatchVariable<T> createCatchVariable(spoon.reflect.reference.CtTypeReference<T> type, java.lang.String name, spoon.reflect.declaration.ModifierKind... modifierKinds);

    <T> spoon.reflect.code.CtCodeSnippetExpression<T> createCodeSnippetExpression(java.lang.String expression);

    <T> spoon.reflect.code.CtConstructorCall<T> createConstructorCall(spoon.reflect.reference.CtTypeReference<T> type, spoon.reflect.code.CtExpression<?>... parameters);

    <T> spoon.reflect.code.CtFieldAccess<java.lang.Class<T>> createClassAccess(spoon.reflect.reference.CtTypeReference<T> type);

    <T> spoon.reflect.code.CtInvocation<T> createInvocation(spoon.reflect.code.CtExpression<?> target, spoon.reflect.reference.CtExecutableReference<T> executable, java.util.List<spoon.reflect.code.CtExpression<?>> arguments);

    <T> spoon.reflect.code.CtInvocation<T> createInvocation(spoon.reflect.code.CtExpression<?> target, spoon.reflect.reference.CtExecutableReference<T> executable, spoon.reflect.code.CtExpression<?>... arguments);

    <T> spoon.reflect.code.CtLiteral<T> createLiteral(T value);

    <T> spoon.reflect.code.CtLocalVariable<T> createLocalVariable(spoon.reflect.reference.CtTypeReference<T> type, java.lang.String name, spoon.reflect.code.CtExpression<T> defaultExpression);

    @java.lang.SuppressWarnings("unchecked")
    <T> spoon.reflect.code.CtNewArray<T[]> createLiteralArray(T[] value);

    <T> spoon.reflect.code.CtNewClass<T> createNewClass(spoon.reflect.reference.CtTypeReference<T> type, spoon.reflect.declaration.CtClass<?> anonymousClass, spoon.reflect.code.CtExpression<?>... parameters);

    <T> spoon.reflect.code.CtStatementList createVariableAssignments(java.util.List<? extends spoon.reflect.declaration.CtVariable<T>> variables, java.util.List<? extends spoon.reflect.code.CtExpression<T>> expressions);

    <T> spoon.reflect.code.CtThisAccess<T> createThisAccess(spoon.reflect.reference.CtTypeReference<T> type);

    <T> spoon.reflect.code.CtThisAccess<T> createThisAccess(spoon.reflect.reference.CtTypeReference<T> type, boolean isImplicit);

    <T> spoon.reflect.code.CtTypeAccess<T> createTypeAccess(spoon.reflect.reference.CtTypeReference<T> accessedType);

    <T> spoon.reflect.code.CtTypeAccess<T> createTypeAccess(spoon.reflect.reference.CtTypeReference<T> accessedType, boolean isImplicit);

    <T> spoon.reflect.code.CtTypeAccess<T> createTypeAccessWithoutCloningReference(spoon.reflect.reference.CtTypeReference<T> accessedType);

    <T> spoon.reflect.code.CtVariableAccess<T> createVariableRead(spoon.reflect.reference.CtVariableReference<T> variable, boolean isStatic);

    <T> spoon.reflect.declaration.CtField<T> createCtField(java.lang.String name, spoon.reflect.reference.CtTypeReference<T> type, java.lang.String exp, spoon.reflect.declaration.ModifierKind... visibilities);

    <T> spoon.reflect.reference.CtCatchVariableReference<T> createCatchVariableReference(spoon.reflect.code.CtCatchVariable<T> catchVariable);

    <T> spoon.reflect.reference.CtLocalVariableReference<T> createLocalVariableReference(spoon.reflect.code.CtLocalVariable<T> localVariable);

    <T> spoon.reflect.reference.CtLocalVariableReference<T> createLocalVariableReference(spoon.reflect.reference.CtTypeReference<T> type, java.lang.String name);

    <T> spoon.reflect.reference.CtTypeReference<T> createCtTypeReference(java.lang.Class<?> originalClass);

    java.util.List<spoon.reflect.code.CtExpression<?>> createVariableReads(java.util.List<? extends spoon.reflect.declaration.CtVariable<?>> variables);

    spoon.reflect.code.CtCatch createCtCatch(java.lang.String nameCatch, java.lang.Class<? extends java.lang.Throwable> exception, spoon.reflect.code.CtBlock<?> ctBlock);

    spoon.reflect.code.CtCodeSnippetStatement createCodeSnippetStatement(java.lang.String statement);

    spoon.reflect.code.CtComment createComment(java.lang.String content, spoon.reflect.code.CtComment.CommentType type);

    spoon.reflect.code.CtJavaDocTag createJavaDocTag(java.lang.String content, spoon.reflect.code.CtJavaDocTag.TagType type);

    spoon.reflect.code.CtComment createInlineComment(java.lang.String content);

    spoon.reflect.code.CtThrow createCtThrow(java.lang.String thrownExp);

    spoon.reflect.reference.CtPackageReference createCtPackageReference(java.lang.Package originalPackage);

    <T> spoon.reflect.declaration.CtConstructor<T> createDefault(spoon.reflect.declaration.CtClass<T> target);

    <A extends java.lang.annotation.Annotation> spoon.reflect.declaration.CtAnnotation<A> createAnnotation();

    <R> spoon.reflect.code.CtBlock<R> createBlock();

    <R> spoon.reflect.code.CtReturn<R> createReturn();

    <R> spoon.reflect.code.CtStatementList createStatementList();

    <S> spoon.reflect.code.CtCase<S> createCase();

    <S> spoon.reflect.code.CtSwitch<S> createSwitch();

    <T extends java.lang.Enum<?>> spoon.reflect.declaration.CtEnum<T> createEnum();

    <T extends java.lang.annotation.Annotation> spoon.reflect.declaration.CtAnnotationType<T> createAnnotationType();

    <T, A extends T> spoon.reflect.code.CtAssignment<T, A> createAssignment();

    <T, A extends T> spoon.reflect.code.CtOperatorAssignment<T, A> createOperatorAssignment();

    <T, E extends spoon.reflect.code.CtExpression<?>> spoon.reflect.code.CtExecutableReferenceExpression<T, E> createExecutableReferenceExpression();

    <T> spoon.reflect.code.CtAnnotationFieldAccess<T> createAnnotationFieldAccess();

    <T> spoon.reflect.code.CtArrayRead<T> createArrayRead();

    <T> spoon.reflect.code.CtArrayWrite<T> createArrayWrite();

    <T> spoon.reflect.code.CtAssert<T> createAssert();

    <T> spoon.reflect.code.CtBinaryOperator<T> createBinaryOperator();

    <T> spoon.reflect.code.CtCatchVariable<T> createCatchVariable();

    <T> spoon.reflect.code.CtCodeSnippetExpression<T> createCodeSnippetExpression();

    <T> spoon.reflect.code.CtConditional<T> createConditional();

    <T> spoon.reflect.code.CtConstructorCall<T> createConstructorCall();

    <T> spoon.reflect.code.CtFieldRead<T> createFieldRead();

    <T> spoon.reflect.code.CtFieldWrite<T> createFieldWrite();

    <T> spoon.reflect.code.CtInvocation<T> createInvocation();

    <T> spoon.reflect.code.CtLambda<T> createLambda();

    <T> spoon.reflect.code.CtLiteral<T> createLiteral();

    <T> spoon.reflect.code.CtLocalVariable<T> createLocalVariable();

    <T> spoon.reflect.code.CtNewArray<T> createNewArray();

    <T> spoon.reflect.code.CtNewClass<T> createNewClass();

    <T> spoon.reflect.code.CtSuperAccess<T> createSuperAccess();

    <T> spoon.reflect.code.CtThisAccess<T> createThisAccess();

    <T> spoon.reflect.code.CtTypeAccess<T> createTypeAccess();

    <T> spoon.reflect.code.CtUnaryOperator<T> createUnaryOperator();

    <T> spoon.reflect.code.CtVariableRead<T> createVariableRead();

    <T> spoon.reflect.code.CtVariableWrite<T> createVariableWrite();

    <T> spoon.reflect.declaration.CtAnnotationMethod<T> createAnnotationMethod();

    <T> spoon.reflect.declaration.CtClass<T> createClass();

    <T> spoon.reflect.declaration.CtConstructor<T> createConstructor();

    <T> spoon.reflect.declaration.CtEnumValue<T> createEnumValue();

    <T> spoon.reflect.declaration.CtField<T> createField();

    <T> spoon.reflect.declaration.CtInterface<T> createInterface();

    <T> spoon.reflect.declaration.CtMethod<T> createMethod();

    <T> spoon.reflect.declaration.CtParameter<T> createParameter();

    <T> spoon.reflect.reference.CtArrayTypeReference<T> createArrayTypeReference();

    <T> spoon.reflect.reference.CtCatchVariableReference<T> createCatchVariableReference();

    <T> spoon.reflect.reference.CtExecutableReference<T> createExecutableReference();

    <T> spoon.reflect.reference.CtFieldReference<T> createFieldReference();

    <T> spoon.reflect.reference.CtIntersectionTypeReference<T> createIntersectionTypeReference();

    <T> spoon.reflect.reference.CtLocalVariableReference<T> createLocalVariableReference();

    <T> spoon.reflect.reference.CtParameterReference<T> createParameterReference();

    <T> spoon.reflect.reference.CtTypeReference<T> createTypeReference();

    <T> spoon.reflect.reference.CtUnboundVariableReference<T> createUnboundVariableReference();

    spoon.reflect.code.CtBreak createBreak();

    spoon.reflect.code.CtCatch createCatch();

    spoon.reflect.code.CtCodeSnippetStatement createCodeSnippetStatement();

    spoon.reflect.code.CtComment createComment();

    spoon.reflect.code.CtContinue createContinue();

    spoon.reflect.code.CtDo createDo();

    spoon.reflect.code.CtFor createFor();

    spoon.reflect.code.CtForEach createForEach();

    spoon.reflect.code.CtIf createIf();

    spoon.reflect.code.CtSynchronized createSynchronized();

    spoon.reflect.code.CtThrow createThrow();

    spoon.reflect.code.CtTry createTry();

    spoon.reflect.code.CtTryWithResource createTryWithResource();

    spoon.reflect.code.CtWhile createWhile();

    spoon.reflect.cu.CompilationUnit createCompilationUnit();

    spoon.reflect.cu.SourcePosition createSourcePosition(spoon.reflect.cu.CompilationUnit compilationUnit, int startSource, int end, int[] lineSeparatorPositions);

    spoon.reflect.cu.position.BodyHolderSourcePosition createBodyHolderSourcePosition(spoon.reflect.cu.CompilationUnit compilationUnit, int startSource, int end, int modifierStart, int modifierEnd, int declarationStart, int declarationEnd, int bodyStart, int bodyEnd, int[] lineSeparatorPositions);

    spoon.reflect.cu.position.DeclarationSourcePosition createDeclarationSourcePosition(spoon.reflect.cu.CompilationUnit compilationUnit, int startSource, int end, int modifierStart, int modifierEnd, int declarationStart, int declarationEnd, int[] lineSeparatorPositions);

    spoon.reflect.declaration.CtAnonymousExecutable createAnonymousExecutable();

    spoon.reflect.declaration.CtPackage createPackage();

    spoon.reflect.declaration.CtTypeParameter createTypeParameter();

    spoon.reflect.reference.CtPackageReference createPackageReference();

    spoon.reflect.reference.CtTypeParameterReference createTypeParameterReference();

    spoon.reflect.reference.CtWildcardReference createWildcardReference();

    spoon.reflect.eval.PartialEvaluator createPartialEvaluator();

    <T> spoon.reflect.declaration.CtParameter<T> createParameter(spoon.reflect.declaration.CtExecutable<?> parent, spoon.reflect.reference.CtTypeReference<T> type, java.lang.String name);

    <T> spoon.reflect.reference.CtParameterReference<T> createParameterReference(spoon.reflect.declaration.CtParameter<T> parameter);

    spoon.reflect.declaration.CtAnonymousExecutable createAnonymous(spoon.reflect.declaration.CtClass<?> target, spoon.reflect.code.CtBlock<java.lang.Void> body);

    <T> spoon.reflect.reference.CtArrayTypeReference<T> createArrayReference(java.lang.String qualifiedName);

    <T> spoon.reflect.reference.CtArrayTypeReference<T[]> createArrayReference(spoon.reflect.declaration.CtType<T> type);

    <T> spoon.reflect.reference.CtArrayTypeReference<T[]> createArrayReference(spoon.reflect.reference.CtTypeReference<T> reference);

    <T> spoon.reflect.reference.CtIntersectionTypeReference<T> createIntersectionTypeReferenceWithBounds(java.util.List<spoon.reflect.reference.CtTypeReference<?>> bounds);

    spoon.support.visitor.GenericTypeAdapter createTypeAdapter(spoon.reflect.declaration.CtFormalTypeDeclarer formalTypeDeclarer);

    java.util.List<spoon.reflect.reference.CtTypeReference<?>> createReferences(java.util.List<java.lang.Class<?>> classes);

    spoon.reflect.reference.CtArrayTypeReference<?> createArrayReference(spoon.reflect.reference.CtTypeReference<?> reference, int n);

    spoon.reflect.reference.CtTypeParameterReference createTypeParameterReference(java.lang.String name);

    spoon.reflect.visitor.chain.CtQuery createQuery();

    spoon.reflect.visitor.chain.CtQuery createQuery(java.lang.Object input);

    spoon.reflect.visitor.chain.CtQuery createQuery(java.lang.Object... input);

    spoon.reflect.visitor.chain.CtQuery createQuery(java.lang.Iterable<?> input);

    spoon.reflect.declaration.CtAnnotationType createAnnotationType(java.lang.String qualifiedName);

    spoon.reflect.declaration.CtAnnotationType createAnnotationType(spoon.reflect.declaration.CtPackage owner, java.lang.String simpleName);

    spoon.reflect.declaration.CtClass createClass(java.lang.String qualifiedName);

    spoon.reflect.declaration.CtClass createClass(spoon.reflect.declaration.CtClass<?> declaringClass, java.lang.String simpleName);

    spoon.reflect.declaration.CtClass createClass(spoon.reflect.declaration.CtPackage owner, java.lang.String simpleName);

    spoon.reflect.declaration.CtConstructor createConstructor(spoon.reflect.declaration.CtClass target, spoon.reflect.declaration.CtConstructor<?> source);

    spoon.reflect.declaration.CtConstructor createConstructor(spoon.reflect.declaration.CtClass target, spoon.reflect.declaration.CtMethod<?> source);

    spoon.reflect.declaration.CtConstructor createConstructor(spoon.reflect.declaration.CtClass target, java.util.Set<spoon.reflect.declaration.ModifierKind> modifiers, java.util.List<spoon.reflect.declaration.CtParameter<?>> parameters, java.util.Set<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>> thrownTypes);

    spoon.reflect.declaration.CtConstructor createConstructor(spoon.reflect.declaration.CtClass target, java.util.Set<spoon.reflect.declaration.ModifierKind> modifiers, java.util.List<spoon.reflect.declaration.CtParameter<?>> parameters, java.util.Set<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>> thrownTypes, spoon.reflect.code.CtBlock body);

    spoon.reflect.declaration.CtEnum<?> createEnum(java.lang.String qualifiedName);

    spoon.reflect.declaration.CtEnum<?> createEnum(spoon.reflect.declaration.CtPackage owner, java.lang.String simpleName);

    spoon.reflect.declaration.CtField createField(spoon.reflect.declaration.CtType<?> target, java.util.Set<spoon.reflect.declaration.ModifierKind> modifiers, spoon.reflect.reference.CtTypeReference type, java.lang.String name);

    spoon.reflect.declaration.CtField createField(spoon.reflect.declaration.CtType<?> target, java.util.Set<spoon.reflect.declaration.ModifierKind> modifiers, spoon.reflect.reference.CtTypeReference type, java.lang.String name, spoon.reflect.code.CtExpression defaultExpression);

    spoon.reflect.declaration.CtField createField(spoon.reflect.declaration.CtType<?> target, spoon.reflect.declaration.CtField source);

    spoon.reflect.declaration.CtInterface createInterface(spoon.reflect.declaration.CtPackage owner, java.lang.String simpleName);

    spoon.reflect.declaration.CtInterface createInterface(spoon.reflect.declaration.CtType owner, java.lang.String simpleName);

    spoon.reflect.declaration.CtInterface createInterface(java.lang.String qualifiedName);

    spoon.reflect.declaration.CtMethod createMethod(spoon.reflect.declaration.CtClass<?> target, java.util.Set<spoon.reflect.declaration.ModifierKind> modifiers, spoon.reflect.reference.CtTypeReference returnType, java.lang.String name, java.util.List<spoon.reflect.declaration.CtParameter<?>> parameters, java.util.Set<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>> thrownTypes, spoon.reflect.code.CtBlock body);

    spoon.reflect.declaration.CtMethod createMethod(spoon.reflect.declaration.CtType<?> target, spoon.reflect.declaration.CtMethod source, boolean redirectReferences);

    spoon.reflect.declaration.CtMethod createMethod(spoon.reflect.declaration.CtType<?> target, java.util.Set<spoon.reflect.declaration.ModifierKind> modifiers, spoon.reflect.reference.CtTypeReference returnType, java.lang.String name, java.util.List<spoon.reflect.declaration.CtParameter<?>> parameters, java.util.Set<spoon.reflect.reference.CtTypeReference<? extends java.lang.Throwable>> thrownTypes);

    spoon.reflect.declaration.CtPackage createPackage(spoon.reflect.declaration.CtPackage parent, java.lang.String simpleName);

    spoon.reflect.declaration.CtElement createElement(java.lang.Class<? extends spoon.reflect.declaration.CtElement> klass);

    spoon.reflect.declaration.CtImport createImport(spoon.reflect.reference.CtReference reference);

    spoon.reflect.reference.CtTypeReference createWildcardStaticTypeMemberReference(spoon.reflect.reference.CtTypeReference typeReference);

    spoon.reflect.declaration.CtPackageExport createPackageExport(spoon.reflect.reference.CtPackageReference ctPackageReference);

    spoon.reflect.declaration.CtProvidedService createProvidedService(spoon.reflect.reference.CtTypeReference ctTypeReference);

    spoon.reflect.declaration.CtModuleRequirement createModuleRequirement(spoon.reflect.reference.CtModuleReference ctModuleReference);

    spoon.reflect.declaration.CtModule createModule(java.lang.String moduleName);

    spoon.reflect.reference.CtModuleReference createModuleReference(spoon.reflect.declaration.CtModule ctModule);

    spoon.reflect.declaration.CtUsedService createUsedService(spoon.reflect.reference.CtTypeReference typeReference);

    spoon.reflect.cu.SourcePosition createPartialSourcePosition(spoon.reflect.cu.CompilationUnit compilationUnit);
}

