package spoon.reflect.visitor;


public interface CtVisitor {
    <A extends java.lang.annotation.Annotation> void visitCtAnnotation(spoon.reflect.declaration.CtAnnotation<A> annotation);

    <T> void visitCtCodeSnippetExpression(spoon.reflect.code.CtCodeSnippetExpression<T> expression);

    void visitCtCodeSnippetStatement(spoon.reflect.code.CtCodeSnippetStatement statement);

    <A extends java.lang.annotation.Annotation> void visitCtAnnotationType(spoon.reflect.declaration.CtAnnotationType<A> annotationType);

    void visitCtAnonymousExecutable(spoon.reflect.declaration.CtAnonymousExecutable anonymousExec);

    <T> void visitCtArrayRead(spoon.reflect.code.CtArrayRead<T> arrayRead);

    <T> void visitCtArrayWrite(spoon.reflect.code.CtArrayWrite<T> arrayWrite);

    <T> void visitCtArrayTypeReference(spoon.reflect.reference.CtArrayTypeReference<T> reference);

    <T> void visitCtAssert(spoon.reflect.code.CtAssert<T> asserted);

    <T, A extends T> void visitCtAssignment(spoon.reflect.code.CtAssignment<T, A> assignement);

    <T> void visitCtBinaryOperator(spoon.reflect.code.CtBinaryOperator<T> operator);

    <R> void visitCtBlock(spoon.reflect.code.CtBlock<R> block);

    void visitCtBreak(spoon.reflect.code.CtBreak breakStatement);

    <S> void visitCtCase(spoon.reflect.code.CtCase<S> caseStatement);

    void visitCtCatch(spoon.reflect.code.CtCatch catchBlock);

    <T> void visitCtClass(spoon.reflect.declaration.CtClass<T> ctClass);

    void visitCtTypeParameter(spoon.reflect.declaration.CtTypeParameter typeParameter);

    <T> void visitCtConditional(spoon.reflect.code.CtConditional<T> conditional);

    <T> void visitCtConstructor(spoon.reflect.declaration.CtConstructor<T> c);

    void visitCtContinue(spoon.reflect.code.CtContinue continueStatement);

    void visitCtDo(spoon.reflect.code.CtDo doLoop);

    <T extends java.lang.Enum<?>> void visitCtEnum(spoon.reflect.declaration.CtEnum<T> ctEnum);

    <T> void visitCtExecutableReference(spoon.reflect.reference.CtExecutableReference<T> reference);

    <T> void visitCtField(spoon.reflect.declaration.CtField<T> f);

    <T> void visitCtEnumValue(spoon.reflect.declaration.CtEnumValue<T> enumValue);

    <T> void visitCtThisAccess(spoon.reflect.code.CtThisAccess<T> thisAccess);

    <T> void visitCtFieldReference(spoon.reflect.reference.CtFieldReference<T> reference);

    <T> void visitCtUnboundVariableReference(spoon.reflect.reference.CtUnboundVariableReference<T> reference);

    void visitCtFor(spoon.reflect.code.CtFor forLoop);

    void visitCtForEach(spoon.reflect.code.CtForEach foreach);

    void visitCtIf(spoon.reflect.code.CtIf ifElement);

    <T> void visitCtInterface(spoon.reflect.declaration.CtInterface<T> intrface);

    <T> void visitCtInvocation(spoon.reflect.code.CtInvocation<T> invocation);

    <T> void visitCtLiteral(spoon.reflect.code.CtLiteral<T> literal);

    <T> void visitCtLocalVariable(spoon.reflect.code.CtLocalVariable<T> localVariable);

    <T> void visitCtLocalVariableReference(spoon.reflect.reference.CtLocalVariableReference<T> reference);

    <T> void visitCtCatchVariable(spoon.reflect.code.CtCatchVariable<T> catchVariable);

    <T> void visitCtCatchVariableReference(spoon.reflect.reference.CtCatchVariableReference<T> reference);

    <T> void visitCtMethod(spoon.reflect.declaration.CtMethod<T> m);

    <T> void visitCtAnnotationMethod(spoon.reflect.declaration.CtAnnotationMethod<T> annotationMethod);

    <T> void visitCtNewArray(spoon.reflect.code.CtNewArray<T> newArray);

    <T> void visitCtConstructorCall(spoon.reflect.code.CtConstructorCall<T> ctConstructorCall);

    <T> void visitCtNewClass(spoon.reflect.code.CtNewClass<T> newClass);

    <T> void visitCtLambda(spoon.reflect.code.CtLambda<T> lambda);

    <T, E extends spoon.reflect.code.CtExpression<?>> void visitCtExecutableReferenceExpression(spoon.reflect.code.CtExecutableReferenceExpression<T, E> expression);

    <T, A extends T> void visitCtOperatorAssignment(spoon.reflect.code.CtOperatorAssignment<T, A> assignment);

    void visitCtPackage(spoon.reflect.declaration.CtPackage ctPackage);

    void visitCtPackageReference(spoon.reflect.reference.CtPackageReference reference);

    <T> void visitCtParameter(spoon.reflect.declaration.CtParameter<T> parameter);

    <T> void visitCtParameterReference(spoon.reflect.reference.CtParameterReference<T> reference);

    <R> void visitCtReturn(spoon.reflect.code.CtReturn<R> returnStatement);

    <R> void visitCtStatementList(spoon.reflect.code.CtStatementList statements);

    <S> void visitCtSwitch(spoon.reflect.code.CtSwitch<S> switchStatement);

    void visitCtSynchronized(spoon.reflect.code.CtSynchronized synchro);

    void visitCtThrow(spoon.reflect.code.CtThrow throwStatement);

    void visitCtTry(spoon.reflect.code.CtTry tryBlock);

    void visitCtTryWithResource(spoon.reflect.code.CtTryWithResource tryWithResource);

    void visitCtTypeParameterReference(spoon.reflect.reference.CtTypeParameterReference ref);

    void visitCtWildcardReference(spoon.reflect.reference.CtWildcardReference wildcardReference);

    <T> void visitCtIntersectionTypeReference(spoon.reflect.reference.CtIntersectionTypeReference<T> reference);

    <T> void visitCtTypeReference(spoon.reflect.reference.CtTypeReference<T> reference);

    <T> void visitCtTypeAccess(spoon.reflect.code.CtTypeAccess<T> typeAccess);

    <T> void visitCtUnaryOperator(spoon.reflect.code.CtUnaryOperator<T> operator);

    <T> void visitCtVariableRead(spoon.reflect.code.CtVariableRead<T> variableRead);

    <T> void visitCtVariableWrite(spoon.reflect.code.CtVariableWrite<T> variableWrite);

    void visitCtWhile(spoon.reflect.code.CtWhile whileLoop);

    <T> void visitCtAnnotationFieldAccess(spoon.reflect.code.CtAnnotationFieldAccess<T> annotationFieldAccess);

    <T> void visitCtFieldRead(spoon.reflect.code.CtFieldRead<T> fieldRead);

    <T> void visitCtFieldWrite(spoon.reflect.code.CtFieldWrite<T> fieldWrite);

    <T> void visitCtSuperAccess(spoon.reflect.code.CtSuperAccess<T> f);

    void visitCtComment(spoon.reflect.code.CtComment comment);

    void visitCtJavaDoc(spoon.reflect.code.CtJavaDoc comment);

    void visitCtJavaDocTag(spoon.reflect.code.CtJavaDocTag docTag);

    void visitCtImport(spoon.reflect.declaration.CtImport ctImport);

    void visitCtModule(spoon.reflect.declaration.CtModule module);

    void visitCtModuleReference(spoon.reflect.reference.CtModuleReference moduleReference);

    void visitCtPackageExport(spoon.reflect.declaration.CtPackageExport moduleExport);

    void visitCtModuleRequirement(spoon.reflect.declaration.CtModuleRequirement moduleRequirement);

    void visitCtProvidedService(spoon.reflect.declaration.CtProvidedService moduleProvidedService);

    void visitCtUsedService(spoon.reflect.declaration.CtUsedService usedService);
}

