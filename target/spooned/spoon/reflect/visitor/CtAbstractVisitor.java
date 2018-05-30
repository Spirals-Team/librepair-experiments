package spoon.reflect.visitor;


public abstract class CtAbstractVisitor implements spoon.reflect.visitor.CtVisitor {
    @java.lang.Override
    public <A extends java.lang.annotation.Annotation> void visitCtAnnotation(spoon.reflect.declaration.CtAnnotation<A> annotation) {
    }

    @java.lang.Override
    public <T> void visitCtCodeSnippetExpression(spoon.reflect.code.CtCodeSnippetExpression<T> expression) {
    }

    @java.lang.Override
    public void visitCtCodeSnippetStatement(spoon.reflect.code.CtCodeSnippetStatement statement) {
    }

    @java.lang.Override
    public <A extends java.lang.annotation.Annotation> void visitCtAnnotationType(spoon.reflect.declaration.CtAnnotationType<A> annotationType) {
    }

    @java.lang.Override
    public void visitCtAnonymousExecutable(spoon.reflect.declaration.CtAnonymousExecutable anonymousExec) {
    }

    @java.lang.Override
    public <T> void visitCtArrayRead(spoon.reflect.code.CtArrayRead<T> arrayRead) {
    }

    @java.lang.Override
    public <T> void visitCtArrayWrite(spoon.reflect.code.CtArrayWrite<T> arrayWrite) {
    }

    @java.lang.Override
    public <T> void visitCtArrayTypeReference(spoon.reflect.reference.CtArrayTypeReference<T> reference) {
    }

    @java.lang.Override
    public <T> void visitCtAssert(spoon.reflect.code.CtAssert<T> asserted) {
    }

    @java.lang.Override
    public <T, A extends T> void visitCtAssignment(spoon.reflect.code.CtAssignment<T, A> assignement) {
    }

    @java.lang.Override
    public <T> void visitCtBinaryOperator(spoon.reflect.code.CtBinaryOperator<T> operator) {
    }

    @java.lang.Override
    public <R> void visitCtBlock(spoon.reflect.code.CtBlock<R> block) {
    }

    @java.lang.Override
    public void visitCtBreak(spoon.reflect.code.CtBreak breakStatement) {
    }

    @java.lang.Override
    public <S> void visitCtCase(spoon.reflect.code.CtCase<S> caseStatement) {
    }

    @java.lang.Override
    public void visitCtCatch(spoon.reflect.code.CtCatch catchBlock) {
    }

    @java.lang.Override
    public <T> void visitCtClass(spoon.reflect.declaration.CtClass<T> ctClass) {
    }

    @java.lang.Override
    public void visitCtTypeParameter(spoon.reflect.declaration.CtTypeParameter typeParameter) {
    }

    @java.lang.Override
    public <T> void visitCtConditional(spoon.reflect.code.CtConditional<T> conditional) {
    }

    @java.lang.Override
    public <T> void visitCtConstructor(spoon.reflect.declaration.CtConstructor<T> c) {
    }

    @java.lang.Override
    public void visitCtContinue(spoon.reflect.code.CtContinue continueStatement) {
    }

    @java.lang.Override
    public void visitCtDo(spoon.reflect.code.CtDo doLoop) {
    }

    @java.lang.Override
    public <T extends java.lang.Enum<?>> void visitCtEnum(spoon.reflect.declaration.CtEnum<T> ctEnum) {
    }

    @java.lang.Override
    public <T> void visitCtExecutableReference(spoon.reflect.reference.CtExecutableReference<T> reference) {
    }

    @java.lang.Override
    public <T> void visitCtField(spoon.reflect.declaration.CtField<T> f) {
    }

    @java.lang.Override
    public <T> void visitCtEnumValue(spoon.reflect.declaration.CtEnumValue<T> enumValue) {
    }

    @java.lang.Override
    public <T> void visitCtFieldReference(spoon.reflect.reference.CtFieldReference<T> reference) {
    }

    @java.lang.Override
    public void visitCtFor(spoon.reflect.code.CtFor forLoop) {
    }

    @java.lang.Override
    public void visitCtForEach(spoon.reflect.code.CtForEach foreach) {
    }

    @java.lang.Override
    public void visitCtIf(spoon.reflect.code.CtIf ifElement) {
    }

    @java.lang.Override
    public <T> void visitCtInterface(spoon.reflect.declaration.CtInterface<T> intrface) {
    }

    @java.lang.Override
    public <T> void visitCtInvocation(spoon.reflect.code.CtInvocation<T> invocation) {
    }

    @java.lang.Override
    public <T> void visitCtLiteral(spoon.reflect.code.CtLiteral<T> literal) {
    }

    @java.lang.Override
    public <T> void visitCtLocalVariable(spoon.reflect.code.CtLocalVariable<T> localVariable) {
    }

    @java.lang.Override
    public <T> void visitCtLocalVariableReference(spoon.reflect.reference.CtLocalVariableReference<T> reference) {
    }

    @java.lang.Override
    public <T> void visitCtCatchVariable(spoon.reflect.code.CtCatchVariable<T> catchVariable) {
    }

    @java.lang.Override
    public <T> void visitCtMethod(spoon.reflect.declaration.CtMethod<T> m) {
    }

    @java.lang.Override
    public <T> void visitCtAnnotationMethod(spoon.reflect.declaration.CtAnnotationMethod<T> annotationMethod) {
    }

    @java.lang.Override
    public <T> void visitCtNewArray(spoon.reflect.code.CtNewArray<T> newArray) {
    }

    @java.lang.Override
    public <T> void visitCtConstructorCall(spoon.reflect.code.CtConstructorCall<T> ctConstructorCall) {
    }

    @java.lang.Override
    public <T> void visitCtNewClass(spoon.reflect.code.CtNewClass<T> newClass) {
    }

    @java.lang.Override
    public <T> void visitCtLambda(spoon.reflect.code.CtLambda<T> lambda) {
    }

    @java.lang.Override
    public <T, E extends spoon.reflect.code.CtExpression<?>> void visitCtExecutableReferenceExpression(spoon.reflect.code.CtExecutableReferenceExpression<T, E> expression) {
    }

    @java.lang.Override
    public <T, A extends T> void visitCtOperatorAssignment(spoon.reflect.code.CtOperatorAssignment<T, A> assignment) {
    }

    @java.lang.Override
    public void visitCtPackage(spoon.reflect.declaration.CtPackage ctPackage) {
    }

    @java.lang.Override
    public void visitCtPackageReference(spoon.reflect.reference.CtPackageReference reference) {
    }

    @java.lang.Override
    public <T> void visitCtParameter(spoon.reflect.declaration.CtParameter<T> parameter) {
    }

    @java.lang.Override
    public <T> void visitCtParameterReference(spoon.reflect.reference.CtParameterReference<T> reference) {
    }

    @java.lang.Override
    public <R> void visitCtReturn(spoon.reflect.code.CtReturn<R> returnStatement) {
    }

    @java.lang.Override
    public <R> void visitCtStatementList(spoon.reflect.code.CtStatementList statements) {
    }

    @java.lang.Override
    public <S> void visitCtSwitch(spoon.reflect.code.CtSwitch<S> switchStatement) {
    }

    @java.lang.Override
    public void visitCtSynchronized(spoon.reflect.code.CtSynchronized synchro) {
    }

    @java.lang.Override
    public void visitCtThrow(spoon.reflect.code.CtThrow throwStatement) {
    }

    @java.lang.Override
    public void visitCtTry(spoon.reflect.code.CtTry tryBlock) {
    }

    @java.lang.Override
    public void visitCtTryWithResource(spoon.reflect.code.CtTryWithResource tryWithResource) {
    }

    @java.lang.Override
    public void visitCtTypeParameterReference(spoon.reflect.reference.CtTypeParameterReference ref) {
    }

    @java.lang.Override
    public void visitCtWildcardReference(spoon.reflect.reference.CtWildcardReference wildcardReference) {
    }

    @java.lang.Override
    public <T> void visitCtIntersectionTypeReference(spoon.reflect.reference.CtIntersectionTypeReference<T> reference) {
    }

    @java.lang.Override
    public <T> void visitCtTypeReference(spoon.reflect.reference.CtTypeReference<T> reference) {
    }

    @java.lang.Override
    public <T> void visitCtTypeAccess(spoon.reflect.code.CtTypeAccess<T> typeAccess) {
    }

    public <T> void visitCtUnaryOperator(spoon.reflect.code.CtUnaryOperator<T> operator) {
    }

    @java.lang.Override
    public <T> void visitCtVariableRead(spoon.reflect.code.CtVariableRead<T> variableRead) {
    }

    @java.lang.Override
    public <T> void visitCtVariableWrite(spoon.reflect.code.CtVariableWrite<T> variableWrite) {
    }

    @java.lang.Override
    public void visitCtWhile(spoon.reflect.code.CtWhile whileLoop) {
    }

    @java.lang.Override
    public <T> void visitCtAnnotationFieldAccess(spoon.reflect.code.CtAnnotationFieldAccess<T> annotationFieldAccess) {
    }

    @java.lang.Override
    public <T> void visitCtThisAccess(spoon.reflect.code.CtThisAccess<T> thisAccess) {
    }

    @java.lang.Override
    public <T> void visitCtCatchVariableReference(spoon.reflect.reference.CtCatchVariableReference<T> reference) {
    }

    @java.lang.Override
    public <T> void visitCtUnboundVariableReference(spoon.reflect.reference.CtUnboundVariableReference<T> reference) {
    }

    @java.lang.Override
    public <T> void visitCtFieldRead(spoon.reflect.code.CtFieldRead<T> fieldRead) {
    }

    @java.lang.Override
    public <T> void visitCtFieldWrite(spoon.reflect.code.CtFieldWrite<T> fieldWrite) {
    }

    @java.lang.Override
    public <T> void visitCtSuperAccess(spoon.reflect.code.CtSuperAccess<T> f) {
    }

    @java.lang.Override
    public void visitCtComment(spoon.reflect.code.CtComment comment) {
    }

    @java.lang.Override
    public void visitCtJavaDoc(spoon.reflect.code.CtJavaDoc javadoc) {
    }

    @java.lang.Override
    public void visitCtJavaDocTag(spoon.reflect.code.CtJavaDocTag docTag) {
    }

    @java.lang.Override
    public void visitCtImport(spoon.reflect.declaration.CtImport ctImport) {
    }

    @java.lang.Override
    public void visitCtModule(spoon.reflect.declaration.CtModule module) {
    }

    @java.lang.Override
    public void visitCtModuleReference(spoon.reflect.reference.CtModuleReference moduleReference) {
    }

    @java.lang.Override
    public void visitCtPackageExport(spoon.reflect.declaration.CtPackageExport moduleExport) {
    }

    @java.lang.Override
    public void visitCtModuleRequirement(spoon.reflect.declaration.CtModuleRequirement moduleRequirement) {
    }

    @java.lang.Override
    public void visitCtProvidedService(spoon.reflect.declaration.CtProvidedService moduleProvidedService) {
    }

    @java.lang.Override
    public void visitCtUsedService(spoon.reflect.declaration.CtUsedService usedService) {
    }
}

