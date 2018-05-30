/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.reflect.visitor;


/**
 * This class provides an abstract implementation of the visitor that allows its
 * subclasses to scans the metamodel elements by recursively using their
 * (abstract) supertype scanning methods. It declares a scan method for each
 * abstract element of the AST and a visit method for each element of the AST.
 */
public abstract class CtInheritanceScanner implements spoon.reflect.visitor.CtVisitor {
    /**
     * Default constructor.
     */
    public CtInheritanceScanner() {
    }

    public <T> void visitCtCodeSnippetExpression(spoon.reflect.code.CtCodeSnippetExpression<T> e) {
        scanCtCodeSnippet(e);
        scanCtExpression(e);
        scanCtCodeElement(e);
        scanCtTypedElement(e);
        scanCtElement(e);
        scanCtVisitable(e);
    }

    public void visitCtCodeSnippetStatement(spoon.reflect.code.CtCodeSnippetStatement e) {
        scanCtCodeSnippet(e);
        scanCtStatement(e);
        scanCtCodeElement(e);
        scanCtElement(e);
        scanCtVisitable(e);
    }

    /**
     * Generically scans a collection of meta-model elements.
     */
    public void scan(java.util.Collection<? extends spoon.reflect.declaration.CtElement> elements) {
        if (elements != null) {
            for (spoon.reflect.declaration.CtElement e : elements) {
                scan(e);
            }
        }
    }

    /**
     * Generically scans a meta-model element.
     */
    public void scan(spoon.reflect.declaration.CtElement element) {
        if (element != null) {
            element.accept(this);
        }
    }

    /**
     * Scans an abstract invocation.
     */
    public <T> void scanCtAbstractInvocation(spoon.reflect.code.CtAbstractInvocation<T> a) {
    }

    /**
     * Scans an abstract control flow break.
     */
    public void scanCtCFlowBreak(spoon.reflect.code.CtCFlowBreak flowBreak) {
    }

    /**
     * Scans a labelled control flow break.
     */
    public void scanCtLabelledFlowBreak(spoon.reflect.code.CtLabelledFlowBreak labelledFlowBreak) {
    }

    /**
     * Scans an abstract code element.
     */
    public void scanCtCodeElement(spoon.reflect.code.CtCodeElement e) {
    }

    public void scanCtTypeMember(spoon.reflect.declaration.CtTypeMember e) {
    }

    public void scanCtModuleDirective(spoon.reflect.declaration.CtModuleDirective e) {
    }

    /**
     * Scans an abstract element.
     */
    public void scanCtElement(spoon.reflect.declaration.CtElement e) {
    }

    /**
     * Scans an abstract executable.
     */
    public <R> void scanCtExecutable(spoon.reflect.declaration.CtExecutable<R> e) {
    }

    /**
     * Scans an abstract expression.
     */
    public <T> void scanCtExpression(spoon.reflect.code.CtExpression<T> expression) {
    }

    /**
     * Scans a formal type declarer.
     */
    public void scanCtFormalTypeDeclarer(spoon.reflect.declaration.CtFormalTypeDeclarer e) {
    }

    public void scanCtVisitable(spoon.reflect.visitor.CtVisitable e) {
    }

    /**
     * Scans an actual type container..
     */
    public void scanCtActualTypeContainer(spoon.reflect.reference.CtActualTypeContainer reference) {
    }

    /**
     * Scans an abstract loop.
     */
    public void scanCtLoop(spoon.reflect.code.CtLoop loop) {
    }

    /**
     * Scans an abstract modifiable element.
     */
    public void scanCtModifiable(spoon.reflect.declaration.CtModifiable m) {
    }

    /**
     * Scans an abstract named element.
     */
    public void scanCtNamedElement(spoon.reflect.declaration.CtNamedElement e) {
    }

    /**
     * Scans an abstract reference.
     */
    public void scanCtReference(spoon.reflect.reference.CtReference reference) {
    }

    /**
     * Scans an abstract statement.
     */
    public void scanCtStatement(spoon.reflect.code.CtStatement s) {
    }

    /**
     * Scans an abstract targeted expression.
     */
    public <T, E extends spoon.reflect.code.CtExpression<?>> void scanCtTargetedExpression(spoon.reflect.code.CtTargetedExpression<T, E> targetedExpression) {
    }

    /**
     * Scans an abstract type.
     */
    public <T> void scanCtType(spoon.reflect.declaration.CtType<T> type) {
    }

    /**
     * Scans an abstract typed element.
     */
    public <T> void scanCtTypedElement(spoon.reflect.declaration.CtTypedElement<T> e) {
    }

    /**
     * Scans an abstract variable declaration.
     */
    public <T> void scanCtVariable(spoon.reflect.declaration.CtVariable<T> v) {
    }

    /**
     * Scans an array access (read and write).
     */
    public <T, E extends spoon.reflect.code.CtExpression<?>> void scanCtArrayAccess(spoon.reflect.code.CtArrayAccess<T, E> arrayAccess) {
    }

    /**
     * Scans a field access (read and write).
     */
    public <T> void scanCtFieldAccess(spoon.reflect.code.CtFieldAccess<T> fieldAccess) {
    }

    /**
     * Scans a variable access (read and write).
     */
    public <T> void scanCtVariableAccess(spoon.reflect.code.CtVariableAccess<T> variableAccess) {
    }

    /**
     * Scans the right-hand side of an assignment
     */
    public <T> void scanCtRHSReceiver(spoon.reflect.code.CtRHSReceiver<T> ctRHSReceiver) {
    }

    /**
     * Scans a shadowable element
     */
    public void scanCtShadowable(spoon.reflect.declaration.CtShadowable ctShadowable) {
    }

    /**
     * Scans a body holder
     */
    public void scanCtBodyHolder(spoon.reflect.code.CtBodyHolder ctBodyHolder) {
    }

    @java.lang.Override
    public <T> void visitCtFieldRead(spoon.reflect.code.CtFieldRead<T> fieldRead) {
        visitCtVariableRead(fieldRead);
        scanCtFieldAccess(fieldRead);
        scanCtTargetedExpression(fieldRead);
    }

    @java.lang.Override
    public <T> void visitCtFieldWrite(spoon.reflect.code.CtFieldWrite<T> fieldWrite) {
        visitCtVariableWrite(fieldWrite);
        scanCtFieldAccess(fieldWrite);
        scanCtTargetedExpression(fieldWrite);
    }

    public <T> void visitCtSuperAccess(spoon.reflect.code.CtSuperAccess<T> f) {
        visitCtVariableRead(f);
        scanCtTargetedExpression(f);
    }

    public void scanCtMultiTypedElement(spoon.reflect.declaration.CtMultiTypedElement f) {
    }

    public <T, A extends T> void visitCtOperatorAssignment(spoon.reflect.code.CtOperatorAssignment<T, A> e) {
        visitCtAssignment(e);
    }

    /**
     * Scans an abstract variable reference.
     */
    public <T> void scanCtVariableReference(spoon.reflect.reference.CtVariableReference<T> reference) {
    }

    /**
     * Scans an abstract variable reference.
     */
    public <T> void scanCtTypeInformation(spoon.reflect.declaration.CtTypeInformation typeInfo) {
    }

    public <A extends java.lang.annotation.Annotation> void visitCtAnnotation(spoon.reflect.declaration.CtAnnotation<A> e) {
        scanCtExpression(e);
        scanCtCodeElement(e);
        scanCtTypedElement(e);
        scanCtElement(e);
        scanCtVisitable(e);
        scanCtShadowable(e);
    }

    public <A extends java.lang.annotation.Annotation> void visitCtAnnotationType(spoon.reflect.declaration.CtAnnotationType<A> e) {
        scanCtType(e);
        scanCtNamedElement(e);
        scanCtTypeInformation(e);
        scanCtTypeMember(e);
        scanCtFormalTypeDeclarer(e);
        scanCtModifiable(e);
        scanCtElement(e);
        scanCtVisitable(e);
        scanCtShadowable(e);
    }

    public void visitCtAnonymousExecutable(spoon.reflect.declaration.CtAnonymousExecutable e) {
        scanCtExecutable(e);
        scanCtNamedElement(e);
        scanCtTypedElement(e);
        scanCtTypeMember(e);
        scanCtModifiable(e);
        scanCtElement(e);
        scanCtVisitable(e);
        scanCtBodyHolder(e);
    }

    @java.lang.Override
    public <T> void visitCtArrayRead(spoon.reflect.code.CtArrayRead<T> arrayRead) {
        scanCtArrayAccess(arrayRead);
        scanCtTargetedExpression(arrayRead);
        scanCtExpression(arrayRead);
        scanCtCodeElement(arrayRead);
        scanCtTypedElement(arrayRead);
        scanCtElement(arrayRead);
        scanCtVisitable(arrayRead);
    }

    @java.lang.Override
    public <T> void visitCtArrayWrite(spoon.reflect.code.CtArrayWrite<T> arrayWrite) {
        scanCtArrayAccess(arrayWrite);
        scanCtTargetedExpression(arrayWrite);
        scanCtExpression(arrayWrite);
        scanCtCodeElement(arrayWrite);
        scanCtTypedElement(arrayWrite);
        scanCtElement(arrayWrite);
        scanCtVisitable(arrayWrite);
    }

    public <T> void visitCtArrayTypeReference(spoon.reflect.reference.CtArrayTypeReference<T> e) {
        visitCtTypeReference(e);
    }

    public <T> void visitCtAssert(spoon.reflect.code.CtAssert<T> e) {
        scanCtStatement(e);
        scanCtCodeElement(e);
        scanCtElement(e);
        scanCtVisitable(e);
    }

    public <T, A extends T> void visitCtAssignment(spoon.reflect.code.CtAssignment<T, A> e) {
        scanCtStatement(e);
        scanCtExpression(e);
        scanCtCodeElement(e);
        scanCtTypedElement(e);
        scanCtElement(e);
        scanCtVisitable(e);
        scanCtRHSReceiver(e);
    }

    public <T> void visitCtBinaryOperator(spoon.reflect.code.CtBinaryOperator<T> e) {
        scanCtExpression(e);
        scanCtCodeElement(e);
        scanCtTypedElement(e);
        scanCtElement(e);
        scanCtVisitable(e);
    }

    public <R> void visitCtBlock(spoon.reflect.code.CtBlock<R> e) {
        scanCtStatement(e);
        visitCtStatementList(e);
    }

    public void visitCtBreak(spoon.reflect.code.CtBreak e) {
        scanCtLabelledFlowBreak(e);
        scanCtCFlowBreak(e);
        scanCtStatement(e);
        scanCtCodeElement(e);
        scanCtElement(e);
        scanCtVisitable(e);
    }

    public <E> void visitCtCase(spoon.reflect.code.CtCase<E> e) {
        scanCtStatement(e);
        visitCtStatementList(e);
    }

    public void visitCtCatch(spoon.reflect.code.CtCatch e) {
        scanCtCodeElement(e);
        scanCtElement(e);
        scanCtVisitable(e);
        scanCtBodyHolder(e);
    }

    public <T> void visitCtClass(spoon.reflect.declaration.CtClass<T> e) {
        scanCtType(e);
        scanCtStatement(e);
        scanCtTypeInformation(e);
        scanCtFormalTypeDeclarer(e);
        scanCtCodeElement(e);
        scanCtNamedElement(e);
        scanCtTypeMember(e);
        scanCtElement(e);
        scanCtModifiable(e);
        scanCtVisitable(e);
        scanCtShadowable(e);
    }

    @java.lang.Override
    public void visitCtTypeParameter(spoon.reflect.declaration.CtTypeParameter typeParameter) {
        scanCtType(typeParameter);
        scanCtTypeInformation(typeParameter);
        scanCtFormalTypeDeclarer(typeParameter);
        scanCtNamedElement(typeParameter);
        scanCtTypeMember(typeParameter);
        scanCtElement(typeParameter);
        scanCtModifiable(typeParameter);
        scanCtVisitable(typeParameter);
        scanCtShadowable(typeParameter);
    }

    public <T> void visitCtConditional(spoon.reflect.code.CtConditional<T> e) {
        scanCtExpression(e);
        scanCtCodeElement(e);
        scanCtTypedElement(e);
        scanCtElement(e);
        scanCtVisitable(e);
    }

    public <T> void visitCtConstructor(spoon.reflect.declaration.CtConstructor<T> e) {
        scanCtExecutable(e);
        scanCtNamedElement(e);
        scanCtFormalTypeDeclarer(e);
        scanCtTypedElement(e);
        scanCtTypeMember(e);
        scanCtModifiable(e);
        scanCtElement(e);
        scanCtVisitable(e);
        scanCtShadowable(e);
        scanCtBodyHolder(e);
    }

    public void visitCtContinue(spoon.reflect.code.CtContinue e) {
        scanCtLabelledFlowBreak(e);
        scanCtCFlowBreak(e);
        scanCtStatement(e);
        scanCtCodeElement(e);
        scanCtElement(e);
        scanCtVisitable(e);
    }

    public void visitCtDo(spoon.reflect.code.CtDo e) {
        scanCtLoop(e);
        scanCtStatement(e);
        scanCtCodeElement(e);
        scanCtElement(e);
        scanCtVisitable(e);
        scanCtBodyHolder(e);
    }

    public <T extends java.lang.Enum<?>> void visitCtEnum(spoon.reflect.declaration.CtEnum<T> e) {
        visitCtClass(e);
    }

    public <T> void visitCtExecutableReference(spoon.reflect.reference.CtExecutableReference<T> e) {
        scanCtReference(e);
        scanCtElement(e);
        scanCtActualTypeContainer(e);
        scanCtVisitable(e);
    }

    public <T> void visitCtField(spoon.reflect.declaration.CtField<T> e) {
        scanCtNamedElement(e);
        scanCtVariable(e);
        scanCtTypeMember(e);
        scanCtModifiable(e);
        scanCtTypedElement(e);
        scanCtElement(e);
        scanCtVisitable(e);
        scanCtRHSReceiver(e);
        scanCtShadowable(e);
    }

    @java.lang.Override
    public <T> void visitCtEnumValue(spoon.reflect.declaration.CtEnumValue<T> enumValue) {
        visitCtField(enumValue);
    }

    public <T> void visitCtThisAccess(spoon.reflect.code.CtThisAccess<T> e) {
        scanCtTargetedExpression(e);
        scanCtExpression(e);
        scanCtCodeElement(e);
        scanCtTypedElement(e);
        scanCtElement(e);
        scanCtVisitable(e);
    }

    public <T> void visitCtFieldReference(spoon.reflect.reference.CtFieldReference<T> e) {
        scanCtVariableReference(e);
        scanCtReference(e);
        scanCtElement(e);
        scanCtVisitable(e);
    }

    public void visitCtFor(spoon.reflect.code.CtFor e) {
        scanCtLoop(e);
        scanCtStatement(e);
        scanCtCodeElement(e);
        scanCtElement(e);
        scanCtVisitable(e);
        scanCtBodyHolder(e);
    }

    public void visitCtForEach(spoon.reflect.code.CtForEach e) {
        scanCtLoop(e);
        scanCtStatement(e);
        scanCtCodeElement(e);
        scanCtElement(e);
        scanCtVisitable(e);
        scanCtBodyHolder(e);
    }

    public void visitCtIf(spoon.reflect.code.CtIf e) {
        scanCtStatement(e);
        scanCtCodeElement(e);
        scanCtElement(e);
        scanCtVisitable(e);
    }

    public <T> void visitCtInterface(spoon.reflect.declaration.CtInterface<T> e) {
        scanCtType(e);
        scanCtTypeInformation(e);
        scanCtFormalTypeDeclarer(e);
        scanCtNamedElement(e);
        scanCtTypeMember(e);
        scanCtElement(e);
        scanCtModifiable(e);
        scanCtVisitable(e);
        scanCtShadowable(e);
    }

    public <T> void visitCtInvocation(spoon.reflect.code.CtInvocation<T> e) {
        scanCtAbstractInvocation(e);
        scanCtStatement(e);
        scanCtActualTypeContainer(e);
        scanCtTargetedExpression(e);
        scanCtElement(e);
        scanCtCodeElement(e);
        scanCtExpression(e);
        scanCtVisitable(e);
        scanCtTypedElement(e);
    }

    public <T> void visitCtLiteral(spoon.reflect.code.CtLiteral<T> e) {
        scanCtExpression(e);
        scanCtCodeElement(e);
        scanCtTypedElement(e);
        scanCtElement(e);
        scanCtVisitable(e);
    }

    public <T> void visitCtLocalVariable(spoon.reflect.code.CtLocalVariable<T> e) {
        scanCtStatement(e);
        scanCtVariable(e);
        scanCtCodeElement(e);
        scanCtNamedElement(e);
        scanCtTypedElement(e);
        scanCtElement(e);
        scanCtModifiable(e);
        scanCtVisitable(e);
        scanCtRHSReceiver(e);
    }

    public <T> void visitCtLocalVariableReference(spoon.reflect.reference.CtLocalVariableReference<T> e) {
        scanCtVariableReference(e);
        scanCtReference(e);
        scanCtElement(e);
        scanCtVisitable(e);
    }

    public <T> void visitCtCatchVariable(spoon.reflect.code.CtCatchVariable<T> e) {
        scanCtVariable(e);
        scanCtMultiTypedElement(e);
        scanCtCodeElement(e);
        scanCtNamedElement(e);
        scanCtTypedElement(e);
        scanCtElement(e);
        scanCtModifiable(e);
        scanCtVisitable(e);
    }

    public <T> void visitCtCatchVariableReference(spoon.reflect.reference.CtCatchVariableReference<T> e) {
        scanCtVariableReference(e);
        scanCtReference(e);
        scanCtElement(e);
        scanCtVisitable(e);
    }

    public <T> void visitCtMethod(spoon.reflect.declaration.CtMethod<T> e) {
        scanCtExecutable(e);
        scanCtTypedElement(e);
        scanCtNamedElement(e);
        scanCtFormalTypeDeclarer(e);
        scanCtTypeMember(e);
        scanCtElement(e);
        scanCtModifiable(e);
        scanCtVisitable(e);
        scanCtShadowable(e);
        scanCtBodyHolder(e);
    }

    @java.lang.Override
    public <T> void visitCtAnnotationMethod(spoon.reflect.declaration.CtAnnotationMethod<T> annotationMethod) {
        visitCtMethod(annotationMethod);
    }

    public <T> void visitCtNewArray(spoon.reflect.code.CtNewArray<T> e) {
        scanCtExpression(e);
        scanCtCodeElement(e);
        scanCtTypedElement(e);
        scanCtElement(e);
        scanCtVisitable(e);
    }

    @java.lang.Override
    public <T> void visitCtConstructorCall(spoon.reflect.code.CtConstructorCall<T> e) {
        scanCtTargetedExpression(e);
        scanCtAbstractInvocation(e);
        scanCtStatement(e);
        scanCtActualTypeContainer(e);
        scanCtExpression(e);
        scanCtElement(e);
        scanCtCodeElement(e);
        scanCtTypedElement(e);
        scanCtVisitable(e);
    }

    public <T> void visitCtNewClass(spoon.reflect.code.CtNewClass<T> e) {
        visitCtConstructorCall(e);
    }

    @java.lang.Override
    public <T> void visitCtLambda(spoon.reflect.code.CtLambda<T> e) {
        scanCtExpression(e);
        scanCtCodeElement(e);
        scanCtTypedElement(e);
        scanCtExecutable(e);
        scanCtNamedElement(e);
        scanCtElement(e);
        scanCtVisitable(e);
        scanCtBodyHolder(e);
    }

    @java.lang.Override
    public <T, E extends spoon.reflect.code.CtExpression<?>> void visitCtExecutableReferenceExpression(spoon.reflect.code.CtExecutableReferenceExpression<T, E> e) {
        scanCtTargetedExpression(e);
        scanCtExpression(e);
        scanCtCodeElement(e);
        scanCtTypedElement(e);
        scanCtElement(e);
        scanCtVisitable(e);
    }

    public <T, A extends T> void visitCtOperatorAssignement(spoon.reflect.code.CtOperatorAssignment<T, A> assignment) {
    }

    public void visitCtPackage(spoon.reflect.declaration.CtPackage e) {
        scanCtNamedElement(e);
        scanCtElement(e);
        scanCtVisitable(e);
        scanCtShadowable(e);
    }

    public void visitCtPackageReference(spoon.reflect.reference.CtPackageReference e) {
        scanCtReference(e);
        scanCtElement(e);
        scanCtVisitable(e);
    }

    public <T> void visitCtParameter(spoon.reflect.declaration.CtParameter<T> e) {
        scanCtNamedElement(e);
        scanCtVariable(e);
        scanCtModifiable(e);
        scanCtTypedElement(e);
        scanCtElement(e);
        scanCtVisitable(e);
        scanCtShadowable(e);
    }

    public <T> void visitCtParameterReference(spoon.reflect.reference.CtParameterReference<T> e) {
        scanCtVariableReference(e);
        scanCtReference(e);
        scanCtElement(e);
        scanCtVisitable(e);
    }

    public <R> void visitCtReturn(spoon.reflect.code.CtReturn<R> e) {
        scanCtCFlowBreak(e);
        scanCtStatement(e);
        scanCtCodeElement(e);
        scanCtElement(e);
        scanCtVisitable(e);
    }

    public <R> void visitCtStatementList(spoon.reflect.code.CtStatementList e) {
        scanCtCodeElement(e);
        scanCtElement(e);
        scanCtVisitable(e);
    }

    public <E> void visitCtSwitch(spoon.reflect.code.CtSwitch<E> e) {
        scanCtStatement(e);
        scanCtCodeElement(e);
        scanCtElement(e);
        scanCtVisitable(e);
    }

    public void visitCtSynchronized(spoon.reflect.code.CtSynchronized e) {
        scanCtStatement(e);
        scanCtCodeElement(e);
        scanCtElement(e);
        scanCtVisitable(e);
    }

    public void visitCtThrow(spoon.reflect.code.CtThrow e) {
        scanCtCFlowBreak(e);
        scanCtStatement(e);
        scanCtCodeElement(e);
        scanCtElement(e);
        scanCtVisitable(e);
    }

    public void visitCtTry(spoon.reflect.code.CtTry e) {
        scanCtStatement(e);
        scanCtCodeElement(e);
        scanCtElement(e);
        scanCtVisitable(e);
        scanCtBodyHolder(e);
    }

    @java.lang.Override
    public void visitCtTryWithResource(spoon.reflect.code.CtTryWithResource e) {
        visitCtTry(e);
    }

    public void visitCtTypeParameterReference(spoon.reflect.reference.CtTypeParameterReference e) {
        visitCtTypeReference(e);
    }

    @java.lang.Override
    public void visitCtWildcardReference(spoon.reflect.reference.CtWildcardReference wildcardReference) {
        visitCtTypeParameterReference(wildcardReference);
    }

    @java.lang.Override
    public <T> void visitCtIntersectionTypeReference(spoon.reflect.reference.CtIntersectionTypeReference<T> e) {
        visitCtTypeReference(e);
    }

    public <T> void visitCtTypeReference(spoon.reflect.reference.CtTypeReference<T> e) {
        scanCtReference(e);
        scanCtTypeInformation(e);
        scanCtActualTypeContainer(e);
        scanCtElement(e);
        scanCtVisitable(e);
        scanCtShadowable(e);
    }

    @java.lang.Override
    public <T> void visitCtTypeAccess(spoon.reflect.code.CtTypeAccess<T> e) {
        scanCtExpression(e);
        scanCtCodeElement(e);
        scanCtTypedElement(e);
        scanCtElement(e);
        scanCtVisitable(e);
    }

    public <T> void visitCtUnaryOperator(spoon.reflect.code.CtUnaryOperator<T> e) {
        scanCtExpression(e);
        scanCtStatement(e);
        scanCtCodeElement(e);
        scanCtTypedElement(e);
        scanCtElement(e);
        scanCtVisitable(e);
    }

    @java.lang.Override
    public <T> void visitCtVariableRead(spoon.reflect.code.CtVariableRead<T> e) {
        scanCtVariableAccess(e);
        scanCtExpression(e);
        scanCtCodeElement(e);
        scanCtTypedElement(e);
        scanCtElement(e);
        scanCtVisitable(e);
    }

    @java.lang.Override
    public <T> void visitCtVariableWrite(spoon.reflect.code.CtVariableWrite<T> e) {
        scanCtVariableAccess(e);
        scanCtExpression(e);
        scanCtCodeElement(e);
        scanCtTypedElement(e);
        scanCtElement(e);
        scanCtVisitable(e);
    }

    @java.lang.Override
    public void visitCtComment(spoon.reflect.code.CtComment e) {
        scanCtElement(e);
        scanCtVisitable(e);
        scanCtStatement(e);
        scanCtCodeElement(e);
    }

    @java.lang.Override
    public void visitCtJavaDoc(spoon.reflect.code.CtJavaDoc e) {
        visitCtComment(e);
    }

    @java.lang.Override
    public void visitCtJavaDocTag(spoon.reflect.code.CtJavaDocTag e) {
        scanCtElement(e);
        scanCtVisitable(e);
    }

    public <T> void visitCtAnnotationFieldAccess(spoon.reflect.code.CtAnnotationFieldAccess<T> e) {
        visitCtVariableRead(e);
        scanCtTargetedExpression(e);
    }

    public void visitCtWhile(spoon.reflect.code.CtWhile e) {
        scanCtLoop(e);
        scanCtStatement(e);
        scanCtCodeElement(e);
        scanCtElement(e);
        scanCtVisitable(e);
        scanCtBodyHolder(e);
    }

    public <T> void visitCtUnboundVariableReference(spoon.reflect.reference.CtUnboundVariableReference<T> reference) {
        scanCtVariableReference(reference);
        scanCtReference(reference);
        scanCtElement(reference);
        scanCtVisitable(reference);
    }

    public void scanCtCodeSnippet(spoon.reflect.declaration.CtCodeSnippet snippet) {
    }

    @java.lang.Override
    public void visitCtImport(spoon.reflect.declaration.CtImport ctImport) {
        scanCtElement(ctImport);
        scanCtVisitable(ctImport);
    }

    @java.lang.Override
    public void visitCtModule(spoon.reflect.declaration.CtModule module) {
        scanCtNamedElement(module);
        scanCtVisitable(module);
        scanCtElement(module);
    }

    @java.lang.Override
    public void visitCtModuleReference(spoon.reflect.reference.CtModuleReference moduleReference) {
        scanCtReference(moduleReference);
        scanCtElement(moduleReference);
        scanCtVisitable(moduleReference);
    }

    @java.lang.Override
    public void visitCtPackageExport(spoon.reflect.declaration.CtPackageExport moduleExport) {
        scanCtElement(moduleExport);
        scanCtVisitable(moduleExport);
        scanCtModuleDirective(moduleExport);
    }

    @java.lang.Override
    public void visitCtModuleRequirement(spoon.reflect.declaration.CtModuleRequirement moduleRequirement) {
        scanCtElement(moduleRequirement);
        scanCtVisitable(moduleRequirement);
        scanCtModuleDirective(moduleRequirement);
    }

    @java.lang.Override
    public void visitCtProvidedService(spoon.reflect.declaration.CtProvidedService moduleProvidedService) {
        scanCtElement(moduleProvidedService);
        scanCtVisitable(moduleProvidedService);
        scanCtModuleDirective(moduleProvidedService);
    }

    @java.lang.Override
    public void visitCtUsedService(spoon.reflect.declaration.CtUsedService usedService) {
        scanCtElement(usedService);
        scanCtVisitable(usedService);
        scanCtModuleDirective(usedService);
    }
}

