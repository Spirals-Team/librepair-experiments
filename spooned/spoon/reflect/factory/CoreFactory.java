package spoon.reflect.factory;


public interface CoreFactory {
    <T extends spoon.reflect.declaration.CtElement> T clone(T element);

    <A extends java.lang.annotation.Annotation> spoon.reflect.declaration.CtAnnotation<A> createAnnotation();

    <T extends java.lang.annotation.Annotation> spoon.reflect.declaration.CtAnnotationType<T> createAnnotationType();

    spoon.reflect.declaration.CtAnonymousExecutable createAnonymousExecutable();

    <T> spoon.reflect.code.CtArrayRead<T> createArrayRead();

    <T> spoon.reflect.code.CtArrayWrite<T> createArrayWrite();

    <T> spoon.reflect.reference.CtArrayTypeReference<T> createArrayTypeReference();

    <T> spoon.reflect.code.CtAssert<T> createAssert();

    <T, A extends T> spoon.reflect.code.CtAssignment<T, A> createAssignment();

    <T> spoon.reflect.code.CtBinaryOperator<T> createBinaryOperator();

    <R> spoon.reflect.code.CtBlock<R> createBlock();

    spoon.reflect.code.CtBreak createBreak();

    <S> spoon.reflect.code.CtCase<S> createCase();

    spoon.reflect.code.CtCatch createCatch();

    <T> spoon.reflect.declaration.CtClass<T> createClass();

    spoon.reflect.declaration.CtTypeParameter createTypeParameter();

    <T> spoon.reflect.code.CtConditional<T> createConditional();

    <T> spoon.reflect.declaration.CtConstructor<T> createConstructor();

    spoon.reflect.code.CtContinue createContinue();

    spoon.reflect.code.CtDo createDo();

    <T extends java.lang.Enum<?>> spoon.reflect.declaration.CtEnum<T> createEnum();

    <T> spoon.reflect.reference.CtExecutableReference<T> createExecutableReference();

    <T> spoon.reflect.declaration.CtField<T> createField();

    <T> spoon.reflect.declaration.CtEnumValue<T> createEnumValue();

    <T> spoon.reflect.code.CtFieldRead<T> createFieldRead();

    <T> spoon.reflect.code.CtFieldWrite<T> createFieldWrite();

    <T> spoon.reflect.code.CtThisAccess<T> createThisAccess();

    <T> spoon.reflect.code.CtSuperAccess<T> createSuperAccess();

    <T> spoon.reflect.reference.CtFieldReference<T> createFieldReference();

    spoon.reflect.code.CtFor createFor();

    spoon.reflect.code.CtForEach createForEach();

    spoon.reflect.code.CtIf createIf();

    <T> spoon.reflect.declaration.CtInterface<T> createInterface();

    <T> spoon.reflect.code.CtInvocation<T> createInvocation();

    <T> spoon.reflect.code.CtLiteral<T> createLiteral();

    <T> spoon.reflect.code.CtLocalVariable<T> createLocalVariable();

    <T> spoon.reflect.reference.CtLocalVariableReference<T> createLocalVariableReference();

    <T> spoon.reflect.code.CtCatchVariable<T> createCatchVariable();

    <T> spoon.reflect.reference.CtCatchVariableReference<T> createCatchVariableReference();

    <T> spoon.reflect.declaration.CtMethod<T> createMethod();

    <T> spoon.reflect.declaration.CtAnnotationMethod<T> createAnnotationMethod();

    <T> spoon.reflect.code.CtNewArray<T> createNewArray();

    <T> spoon.reflect.code.CtConstructorCall<T> createConstructorCall();

    <T> spoon.reflect.code.CtNewClass<T> createNewClass();

    <T> spoon.reflect.code.CtLambda<T> createLambda();

    <T, E extends spoon.reflect.code.CtExpression<?>> spoon.reflect.code.CtExecutableReferenceExpression<T, E> createExecutableReferenceExpression();

    <T, A extends T> spoon.reflect.code.CtOperatorAssignment<T, A> createOperatorAssignment();

    spoon.reflect.declaration.CtPackage createPackage();

    spoon.reflect.reference.CtPackageReference createPackageReference();

    <T> spoon.reflect.declaration.CtParameter<T> createParameter();

    <T> spoon.reflect.reference.CtParameterReference<T> createParameterReference();

    <R> spoon.reflect.code.CtReturn<R> createReturn();

    spoon.reflect.cu.SourcePosition createSourcePosition(spoon.reflect.cu.CompilationUnit compilationUnit, int startSource, int end, int[] lineSeparatorPositions);

    spoon.reflect.cu.SourcePosition createPartialSourcePosition(spoon.reflect.cu.CompilationUnit compilationUnit);

    spoon.reflect.cu.position.DeclarationSourcePosition createDeclarationSourcePosition(spoon.reflect.cu.CompilationUnit compilationUnit, int startSource, int end, int modifierStart, int modifierEnd, int declarationStart, int declarationEnd, int[] lineSeparatorPositions);

    spoon.reflect.cu.position.BodyHolderSourcePosition createBodyHolderSourcePosition(spoon.reflect.cu.CompilationUnit compilationUnit, int startSource, int end, int modifierStart, int modifierEnd, int declarationStart, int declarationEnd, int bodyStart, int bodyEnd, int[] lineSeparatorPositions);

    <R> spoon.reflect.code.CtStatementList createStatementList();

    <S> spoon.reflect.code.CtSwitch<S> createSwitch();

    spoon.reflect.code.CtSynchronized createSynchronized();

    spoon.reflect.code.CtThrow createThrow();

    spoon.reflect.code.CtTry createTry();

    spoon.reflect.code.CtTryWithResource createTryWithResource();

    spoon.reflect.reference.CtTypeParameterReference createTypeParameterReference();

    spoon.reflect.reference.CtWildcardReference createWildcardReference();

    <T> spoon.reflect.reference.CtIntersectionTypeReference<T> createIntersectionTypeReference();

    <T> spoon.reflect.reference.CtTypeReference<T> createTypeReference();

    <T> spoon.reflect.code.CtTypeAccess<T> createTypeAccess();

    <T> spoon.reflect.code.CtUnaryOperator<T> createUnaryOperator();

    <T> spoon.reflect.code.CtVariableRead<T> createVariableRead();

    <T> spoon.reflect.code.CtVariableWrite<T> createVariableWrite();

    spoon.reflect.code.CtWhile createWhile();

    <T> spoon.reflect.code.CtCodeSnippetExpression<T> createCodeSnippetExpression();

    spoon.reflect.code.CtCodeSnippetStatement createCodeSnippetStatement();

    spoon.reflect.code.CtComment createComment();

    spoon.reflect.code.CtJavaDoc createJavaDoc();

    spoon.reflect.code.CtJavaDocTag createJavaDocTag();

    spoon.reflect.declaration.CtImport createImport();

    spoon.reflect.factory.Factory getMainFactory();

    void setMainFactory(spoon.reflect.factory.Factory mainFactory);

    spoon.reflect.cu.CompilationUnit createCompilationUnit();

    <T> spoon.reflect.code.CtAnnotationFieldAccess<T> createAnnotationFieldAccess();

    <T> spoon.reflect.reference.CtUnboundVariableReference<T> createUnboundVariableReference();

    spoon.reflect.declaration.CtElement create(java.lang.Class<? extends spoon.reflect.declaration.CtElement> klass);

    spoon.reflect.reference.CtTypeReference createWildcardStaticTypeMemberReference();

    spoon.reflect.declaration.CtModule createModule();

    spoon.reflect.reference.CtModuleReference createModuleReference();

    spoon.reflect.declaration.CtModuleRequirement createModuleRequirement();

    spoon.reflect.declaration.CtPackageExport createPackageExport();

    spoon.reflect.declaration.CtProvidedService createProvidedService();

    spoon.reflect.declaration.CtUsedService createUsedService();
}

