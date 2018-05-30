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
 * This visitor implements a deep-search scan on the model.
 *
 * Ensures that all children nodes are visited once, a visit means three method
 * calls, one call to "enter", one call to "exit" and one call to scan.
 *
 * Is used by the processing and filtering engine.
 */
public abstract class CtScanner implements spoon.reflect.visitor.CtVisitor {
    /**
     * Default constructor.
     */
    public CtScanner() {
        super();
    }

    /**
     * This method is upcalled by the scanner when entering a scanned element.
     * To be overridden to implement specific scanners.
     */
    protected void enter(spoon.reflect.declaration.CtElement e) {
    }

    /**
     * This method is upcalled by the scanner when exiting a scanned element. To
     * be overridden to implement specific scanners.
     */
    protected void exit(spoon.reflect.declaration.CtElement e) {
    }

    /**
     * Generically scans a collection of meta-model elements.
     */
    public void scan(spoon.reflect.path.CtRole role, java.util.Collection<? extends spoon.reflect.declaration.CtElement> elements) {
        if (elements != null) {
            // we use defensive copy so as to be able to change the class while scanning
            // otherwise one gets a ConcurrentModificationException
            for (spoon.reflect.declaration.CtElement e : new java.util.ArrayList<>(elements)) {
                scan(role, e);
            }
        }
    }

    /**
     * Generically scans a Map of meta-model elements.
     */
    public void scan(spoon.reflect.path.CtRole role, java.util.Map<java.lang.String, ? extends spoon.reflect.declaration.CtElement> elements) {
        if (elements != null) {
            for (spoon.reflect.declaration.CtElement obj : elements.values()) {
                scan(role, obj);
            }
        }
    }

    /**
     * Generically scans a collection of meta-model elements.
     */
    public void scan(java.util.Collection<? extends spoon.reflect.declaration.CtElement> elements) {
        scan(null, elements);
    }

    /**
     * Generically scans a meta-model element.
     */
    public void scan(spoon.reflect.path.CtRole role, spoon.reflect.declaration.CtElement element) {
        scan(element);
    }

    /**
     * Generically scans a meta-model element.
     */
    public void scan(spoon.reflect.declaration.CtElement element) {
        if (element != null) {
            element.accept(this);
        }
    }

    public <A extends java.lang.annotation.Annotation> void visitCtAnnotation(final spoon.reflect.declaration.CtAnnotation<A> annotation) {
        enter(annotation);
        scan(spoon.reflect.path.CtRole.TYPE, annotation.getType());
        scan(spoon.reflect.path.CtRole.COMMENT, annotation.getComments());
        scan(spoon.reflect.path.CtRole.ANNOTATION_TYPE, annotation.getAnnotationType());
        scan(spoon.reflect.path.CtRole.ANNOTATION, annotation.getAnnotations());
        scan(spoon.reflect.path.CtRole.VALUE, annotation.getValues());
        exit(annotation);
    }

    /**
     * Generically scans an object that can be an element, a reference, or a
     * collection of those.
     */
    public void scan(java.lang.Object o) {
        scan(null, o);
    }

    /**
     * Generically scans an object that can be an element, a reference, or a
     * collection of those.
     */
    public void scan(spoon.reflect.path.CtRole role, java.lang.Object o) {
        if (o instanceof spoon.reflect.declaration.CtElement) {
            scan(role, ((spoon.reflect.declaration.CtElement) (o)));
        }
        if (o instanceof java.util.Collection<?>) {
            scan(role, ((java.util.Collection<? extends spoon.reflect.declaration.CtElement>) (o)));
        }
        if (o instanceof java.util.Map<?, ?>) {
            scan(role, ((java.util.Map<java.lang.String, ? extends spoon.reflect.declaration.CtElement>) (o)));
        }
    }

    public <A extends java.lang.annotation.Annotation> void visitCtAnnotationType(final spoon.reflect.declaration.CtAnnotationType<A> annotationType) {
        enter(annotationType);
        scan(spoon.reflect.path.CtRole.ANNOTATION, annotationType.getAnnotations());
        scan(spoon.reflect.path.CtRole.TYPE_MEMBER, annotationType.getTypeMembers());
        scan(spoon.reflect.path.CtRole.COMMENT, annotationType.getComments());
        exit(annotationType);
    }

    public void visitCtAnonymousExecutable(final spoon.reflect.declaration.CtAnonymousExecutable anonymousExec) {
        enter(anonymousExec);
        scan(spoon.reflect.path.CtRole.ANNOTATION, anonymousExec.getAnnotations());
        scan(spoon.reflect.path.CtRole.BODY, anonymousExec.getBody());
        scan(spoon.reflect.path.CtRole.COMMENT, anonymousExec.getComments());
        exit(anonymousExec);
    }

    @java.lang.Override
    public <T> void visitCtArrayRead(final spoon.reflect.code.CtArrayRead<T> arrayRead) {
        enter(arrayRead);
        scan(spoon.reflect.path.CtRole.ANNOTATION, arrayRead.getAnnotations());
        scan(spoon.reflect.path.CtRole.TYPE, arrayRead.getType());
        scan(spoon.reflect.path.CtRole.CAST, arrayRead.getTypeCasts());
        scan(spoon.reflect.path.CtRole.TARGET, arrayRead.getTarget());
        scan(spoon.reflect.path.CtRole.EXPRESSION, arrayRead.getIndexExpression());
        scan(spoon.reflect.path.CtRole.COMMENT, arrayRead.getComments());
        exit(arrayRead);
    }

    @java.lang.Override
    public <T> void visitCtArrayWrite(final spoon.reflect.code.CtArrayWrite<T> arrayWrite) {
        enter(arrayWrite);
        scan(spoon.reflect.path.CtRole.ANNOTATION, arrayWrite.getAnnotations());
        scan(spoon.reflect.path.CtRole.TYPE, arrayWrite.getType());
        scan(spoon.reflect.path.CtRole.CAST, arrayWrite.getTypeCasts());
        scan(spoon.reflect.path.CtRole.TARGET, arrayWrite.getTarget());
        scan(spoon.reflect.path.CtRole.EXPRESSION, arrayWrite.getIndexExpression());
        scan(spoon.reflect.path.CtRole.COMMENT, arrayWrite.getComments());
        exit(arrayWrite);
    }

    public <T> void visitCtArrayTypeReference(final spoon.reflect.reference.CtArrayTypeReference<T> reference) {
        enter(reference);
        scan(spoon.reflect.path.CtRole.PACKAGE_REF, reference.getPackage());
        scan(spoon.reflect.path.CtRole.DECLARING_TYPE, reference.getDeclaringType());
        scan(spoon.reflect.path.CtRole.TYPE, reference.getComponentType());
        scan(spoon.reflect.path.CtRole.TYPE_ARGUMENT, reference.getActualTypeArguments());
        scan(spoon.reflect.path.CtRole.ANNOTATION, reference.getAnnotations());
        exit(reference);
    }

    public <T> void visitCtAssert(final spoon.reflect.code.CtAssert<T> asserted) {
        enter(asserted);
        scan(spoon.reflect.path.CtRole.ANNOTATION, asserted.getAnnotations());
        scan(spoon.reflect.path.CtRole.CONDITION, asserted.getAssertExpression());
        scan(spoon.reflect.path.CtRole.EXPRESSION, asserted.getExpression());
        scan(spoon.reflect.path.CtRole.COMMENT, asserted.getComments());
        exit(asserted);
    }

    public <T, A extends T> void visitCtAssignment(final spoon.reflect.code.CtAssignment<T, A> assignement) {
        enter(assignement);
        scan(spoon.reflect.path.CtRole.ANNOTATION, assignement.getAnnotations());
        scan(spoon.reflect.path.CtRole.TYPE, assignement.getType());
        scan(spoon.reflect.path.CtRole.CAST, assignement.getTypeCasts());
        scan(spoon.reflect.path.CtRole.ASSIGNED, assignement.getAssigned());
        scan(spoon.reflect.path.CtRole.ASSIGNMENT, assignement.getAssignment());
        scan(spoon.reflect.path.CtRole.COMMENT, assignement.getComments());
        exit(assignement);
    }

    public <T> void visitCtBinaryOperator(final spoon.reflect.code.CtBinaryOperator<T> operator) {
        enter(operator);
        scan(spoon.reflect.path.CtRole.ANNOTATION, operator.getAnnotations());
        scan(spoon.reflect.path.CtRole.TYPE, operator.getType());
        scan(spoon.reflect.path.CtRole.CAST, operator.getTypeCasts());
        scan(spoon.reflect.path.CtRole.LEFT_OPERAND, operator.getLeftHandOperand());
        scan(spoon.reflect.path.CtRole.RIGHT_OPERAND, operator.getRightHandOperand());
        scan(spoon.reflect.path.CtRole.COMMENT, operator.getComments());
        exit(operator);
    }

    public <R> void visitCtBlock(final spoon.reflect.code.CtBlock<R> block) {
        enter(block);
        scan(spoon.reflect.path.CtRole.ANNOTATION, block.getAnnotations());
        scan(spoon.reflect.path.CtRole.STATEMENT, block.getStatements());
        scan(spoon.reflect.path.CtRole.COMMENT, block.getComments());
        exit(block);
    }

    public void visitCtBreak(final spoon.reflect.code.CtBreak breakStatement) {
        enter(breakStatement);
        scan(spoon.reflect.path.CtRole.ANNOTATION, breakStatement.getAnnotations());
        scan(spoon.reflect.path.CtRole.COMMENT, breakStatement.getComments());
        exit(breakStatement);
    }

    public <S> void visitCtCase(final spoon.reflect.code.CtCase<S> caseStatement) {
        enter(caseStatement);
        scan(spoon.reflect.path.CtRole.ANNOTATION, caseStatement.getAnnotations());
        scan(spoon.reflect.path.CtRole.EXPRESSION, caseStatement.getCaseExpression());
        scan(spoon.reflect.path.CtRole.STATEMENT, caseStatement.getStatements());
        scan(spoon.reflect.path.CtRole.COMMENT, caseStatement.getComments());
        exit(caseStatement);
    }

    public void visitCtCatch(final spoon.reflect.code.CtCatch catchBlock) {
        enter(catchBlock);
        scan(spoon.reflect.path.CtRole.ANNOTATION, catchBlock.getAnnotations());
        scan(spoon.reflect.path.CtRole.PARAMETER, catchBlock.getParameter());
        scan(spoon.reflect.path.CtRole.BODY, catchBlock.getBody());
        scan(spoon.reflect.path.CtRole.COMMENT, catchBlock.getComments());
        exit(catchBlock);
    }

    public <T> void visitCtClass(final spoon.reflect.declaration.CtClass<T> ctClass) {
        enter(ctClass);
        scan(spoon.reflect.path.CtRole.ANNOTATION, ctClass.getAnnotations());
        scan(spoon.reflect.path.CtRole.SUPER_TYPE, ctClass.getSuperclass());
        scan(spoon.reflect.path.CtRole.INTERFACE, ctClass.getSuperInterfaces());
        scan(spoon.reflect.path.CtRole.TYPE_PARAMETER, ctClass.getFormalCtTypeParameters());
        scan(spoon.reflect.path.CtRole.TYPE_MEMBER, ctClass.getTypeMembers());
        scan(spoon.reflect.path.CtRole.COMMENT, ctClass.getComments());
        exit(ctClass);
    }

    @java.lang.Override
    public void visitCtTypeParameter(spoon.reflect.declaration.CtTypeParameter typeParameter) {
        enter(typeParameter);
        scan(spoon.reflect.path.CtRole.ANNOTATION, typeParameter.getAnnotations());
        scan(spoon.reflect.path.CtRole.SUPER_TYPE, typeParameter.getSuperclass());
        scan(spoon.reflect.path.CtRole.COMMENT, typeParameter.getComments());
        exit(typeParameter);
    }

    public <T> void visitCtConditional(final spoon.reflect.code.CtConditional<T> conditional) {
        enter(conditional);
        scan(spoon.reflect.path.CtRole.TYPE, conditional.getType());
        scan(spoon.reflect.path.CtRole.ANNOTATION, conditional.getAnnotations());
        scan(spoon.reflect.path.CtRole.CONDITION, conditional.getCondition());
        scan(spoon.reflect.path.CtRole.THEN, conditional.getThenExpression());
        scan(spoon.reflect.path.CtRole.ELSE, conditional.getElseExpression());
        scan(spoon.reflect.path.CtRole.COMMENT, conditional.getComments());
        scan(spoon.reflect.path.CtRole.CAST, conditional.getTypeCasts());
        exit(conditional);
    }

    public <T> void visitCtConstructor(final spoon.reflect.declaration.CtConstructor<T> c) {
        enter(c);
        scan(spoon.reflect.path.CtRole.ANNOTATION, c.getAnnotations());
        scan(spoon.reflect.path.CtRole.PARAMETER, c.getParameters());
        scan(spoon.reflect.path.CtRole.THROWN, c.getThrownTypes());
        scan(spoon.reflect.path.CtRole.TYPE_PARAMETER, c.getFormalCtTypeParameters());
        scan(spoon.reflect.path.CtRole.BODY, c.getBody());
        scan(spoon.reflect.path.CtRole.COMMENT, c.getComments());
        exit(c);
    }

    public void visitCtContinue(final spoon.reflect.code.CtContinue continueStatement) {
        enter(continueStatement);
        scan(spoon.reflect.path.CtRole.ANNOTATION, continueStatement.getAnnotations());
        scan(spoon.reflect.path.CtRole.COMMENT, continueStatement.getComments());
        exit(continueStatement);
    }

    public void visitCtDo(final spoon.reflect.code.CtDo doLoop) {
        enter(doLoop);
        scan(spoon.reflect.path.CtRole.ANNOTATION, doLoop.getAnnotations());
        scan(spoon.reflect.path.CtRole.EXPRESSION, doLoop.getLoopingExpression());
        scan(spoon.reflect.path.CtRole.BODY, doLoop.getBody());
        scan(spoon.reflect.path.CtRole.COMMENT, doLoop.getComments());
        exit(doLoop);
    }

    public <T extends java.lang.Enum<?>> void visitCtEnum(final spoon.reflect.declaration.CtEnum<T> ctEnum) {
        enter(ctEnum);
        scan(spoon.reflect.path.CtRole.ANNOTATION, ctEnum.getAnnotations());
        scan(spoon.reflect.path.CtRole.INTERFACE, ctEnum.getSuperInterfaces());
        scan(spoon.reflect.path.CtRole.TYPE_MEMBER, ctEnum.getTypeMembers());
        scan(spoon.reflect.path.CtRole.VALUE, ctEnum.getEnumValues());
        scan(spoon.reflect.path.CtRole.COMMENT, ctEnum.getComments());
        exit(ctEnum);
    }

    public <T> void visitCtExecutableReference(final spoon.reflect.reference.CtExecutableReference<T> reference) {
        enter(reference);
        scan(spoon.reflect.path.CtRole.DECLARING_TYPE, reference.getDeclaringType());
        scan(spoon.reflect.path.CtRole.TYPE, reference.getType());
        scan(spoon.reflect.path.CtRole.ARGUMENT_TYPE, reference.getParameters());
        scan(spoon.reflect.path.CtRole.TYPE_ARGUMENT, reference.getActualTypeArguments());
        scan(spoon.reflect.path.CtRole.ANNOTATION, reference.getAnnotations());
        scan(spoon.reflect.path.CtRole.COMMENT, reference.getComments());
        exit(reference);
    }

    public <T> void visitCtField(final spoon.reflect.declaration.CtField<T> f) {
        enter(f);
        scan(spoon.reflect.path.CtRole.ANNOTATION, f.getAnnotations());
        scan(spoon.reflect.path.CtRole.TYPE, f.getType());
        scan(spoon.reflect.path.CtRole.DEFAULT_EXPRESSION, f.getDefaultExpression());
        scan(spoon.reflect.path.CtRole.COMMENT, f.getComments());
        exit(f);
    }

    @java.lang.Override
    public <T> void visitCtEnumValue(final spoon.reflect.declaration.CtEnumValue<T> enumValue) {
        enter(enumValue);
        scan(spoon.reflect.path.CtRole.ANNOTATION, enumValue.getAnnotations());
        scan(spoon.reflect.path.CtRole.TYPE, enumValue.getType());
        scan(spoon.reflect.path.CtRole.DEFAULT_EXPRESSION, enumValue.getDefaultExpression());
        scan(spoon.reflect.path.CtRole.COMMENT, enumValue.getComments());
        exit(enumValue);
    }

    @java.lang.Override
    public <T> void visitCtThisAccess(final spoon.reflect.code.CtThisAccess<T> thisAccess) {
        enter(thisAccess);
        scan(spoon.reflect.path.CtRole.COMMENT, thisAccess.getComments());
        scan(spoon.reflect.path.CtRole.ANNOTATION, thisAccess.getAnnotations());
        scan(spoon.reflect.path.CtRole.TYPE, thisAccess.getType());
        scan(spoon.reflect.path.CtRole.CAST, thisAccess.getTypeCasts());
        scan(spoon.reflect.path.CtRole.TARGET, thisAccess.getTarget());
        exit(thisAccess);
    }

    public <T> void visitCtAnnotationFieldAccess(final spoon.reflect.code.CtAnnotationFieldAccess<T> annotationFieldAccess) {
        enter(annotationFieldAccess);
        scan(spoon.reflect.path.CtRole.COMMENT, annotationFieldAccess.getComments());
        scan(spoon.reflect.path.CtRole.ANNOTATION, annotationFieldAccess.getAnnotations());
        scan(spoon.reflect.path.CtRole.CAST, annotationFieldAccess.getTypeCasts());
        scan(spoon.reflect.path.CtRole.TARGET, annotationFieldAccess.getTarget());
        scan(spoon.reflect.path.CtRole.VARIABLE, annotationFieldAccess.getVariable());
        exit(annotationFieldAccess);
    }

    public <T> void visitCtFieldReference(final spoon.reflect.reference.CtFieldReference<T> reference) {
        enter(reference);
        scan(spoon.reflect.path.CtRole.DECLARING_TYPE, reference.getDeclaringType());
        scan(spoon.reflect.path.CtRole.TYPE, reference.getType());
        scan(spoon.reflect.path.CtRole.ANNOTATION, reference.getAnnotations());
        exit(reference);
    }

    public void visitCtFor(final spoon.reflect.code.CtFor forLoop) {
        enter(forLoop);
        scan(spoon.reflect.path.CtRole.ANNOTATION, forLoop.getAnnotations());
        scan(spoon.reflect.path.CtRole.FOR_INIT, forLoop.getForInit());
        scan(spoon.reflect.path.CtRole.EXPRESSION, forLoop.getExpression());
        scan(spoon.reflect.path.CtRole.FOR_UPDATE, forLoop.getForUpdate());
        scan(spoon.reflect.path.CtRole.BODY, forLoop.getBody());
        scan(spoon.reflect.path.CtRole.COMMENT, forLoop.getComments());
        exit(forLoop);
    }

    public void visitCtForEach(final spoon.reflect.code.CtForEach foreach) {
        enter(foreach);
        scan(spoon.reflect.path.CtRole.ANNOTATION, foreach.getAnnotations());
        scan(spoon.reflect.path.CtRole.FOREACH_VARIABLE, foreach.getVariable());
        scan(spoon.reflect.path.CtRole.EXPRESSION, foreach.getExpression());
        scan(spoon.reflect.path.CtRole.BODY, foreach.getBody());
        scan(spoon.reflect.path.CtRole.COMMENT, foreach.getComments());
        exit(foreach);
    }

    public void visitCtIf(final spoon.reflect.code.CtIf ifElement) {
        enter(ifElement);
        scan(spoon.reflect.path.CtRole.ANNOTATION, ifElement.getAnnotations());
        scan(spoon.reflect.path.CtRole.CONDITION, ifElement.getCondition());
        scan(spoon.reflect.path.CtRole.THEN, ((spoon.reflect.code.CtStatement) (ifElement.getThenStatement())));
        scan(spoon.reflect.path.CtRole.ELSE, ((spoon.reflect.code.CtStatement) (ifElement.getElseStatement())));
        scan(spoon.reflect.path.CtRole.COMMENT, ifElement.getComments());
        exit(ifElement);
    }

    public <T> void visitCtInterface(final spoon.reflect.declaration.CtInterface<T> intrface) {
        enter(intrface);
        scan(spoon.reflect.path.CtRole.ANNOTATION, intrface.getAnnotations());
        scan(spoon.reflect.path.CtRole.INTERFACE, intrface.getSuperInterfaces());
        scan(spoon.reflect.path.CtRole.TYPE_PARAMETER, intrface.getFormalCtTypeParameters());
        scan(spoon.reflect.path.CtRole.TYPE_MEMBER, intrface.getTypeMembers());
        scan(spoon.reflect.path.CtRole.COMMENT, intrface.getComments());
        exit(intrface);
    }

    public <T> void visitCtInvocation(final spoon.reflect.code.CtInvocation<T> invocation) {
        enter(invocation);
        scan(spoon.reflect.path.CtRole.ANNOTATION, invocation.getAnnotations());
        scan(spoon.reflect.path.CtRole.CAST, invocation.getTypeCasts());
        scan(spoon.reflect.path.CtRole.TARGET, invocation.getTarget());
        scan(spoon.reflect.path.CtRole.EXECUTABLE_REF, invocation.getExecutable());
        scan(spoon.reflect.path.CtRole.ARGUMENT, invocation.getArguments());
        scan(spoon.reflect.path.CtRole.COMMENT, invocation.getComments());
        exit(invocation);
    }

    public <T> void visitCtLiteral(final spoon.reflect.code.CtLiteral<T> literal) {
        enter(literal);
        scan(spoon.reflect.path.CtRole.ANNOTATION, literal.getAnnotations());
        scan(spoon.reflect.path.CtRole.TYPE, literal.getType());
        scan(spoon.reflect.path.CtRole.CAST, literal.getTypeCasts());
        scan(spoon.reflect.path.CtRole.COMMENT, literal.getComments());
        exit(literal);
    }

    public <T> void visitCtLocalVariable(final spoon.reflect.code.CtLocalVariable<T> localVariable) {
        enter(localVariable);
        scan(spoon.reflect.path.CtRole.ANNOTATION, localVariable.getAnnotations());
        scan(spoon.reflect.path.CtRole.TYPE, localVariable.getType());
        scan(spoon.reflect.path.CtRole.DEFAULT_EXPRESSION, localVariable.getDefaultExpression());
        scan(spoon.reflect.path.CtRole.COMMENT, localVariable.getComments());
        exit(localVariable);
    }

    public <T> void visitCtLocalVariableReference(final spoon.reflect.reference.CtLocalVariableReference<T> reference) {
        enter(reference);
        scan(spoon.reflect.path.CtRole.TYPE, reference.getType());
        scan(spoon.reflect.path.CtRole.ANNOTATION, reference.getAnnotations());
        exit(reference);
    }

    public <T> void visitCtCatchVariable(final spoon.reflect.code.CtCatchVariable<T> catchVariable) {
        enter(catchVariable);
        scan(spoon.reflect.path.CtRole.COMMENT, catchVariable.getComments());
        scan(spoon.reflect.path.CtRole.ANNOTATION, catchVariable.getAnnotations());
        scan(spoon.reflect.path.CtRole.MULTI_TYPE, catchVariable.getMultiTypes());
        exit(catchVariable);
    }

    public <T> void visitCtCatchVariableReference(final spoon.reflect.reference.CtCatchVariableReference<T> reference) {
        enter(reference);
        scan(spoon.reflect.path.CtRole.TYPE, reference.getType());
        scan(spoon.reflect.path.CtRole.ANNOTATION, reference.getAnnotations());
        exit(reference);
    }

    public <T> void visitCtMethod(final spoon.reflect.declaration.CtMethod<T> m) {
        enter(m);
        scan(spoon.reflect.path.CtRole.ANNOTATION, m.getAnnotations());
        scan(spoon.reflect.path.CtRole.TYPE_PARAMETER, m.getFormalCtTypeParameters());
        scan(spoon.reflect.path.CtRole.TYPE, m.getType());
        scan(spoon.reflect.path.CtRole.PARAMETER, m.getParameters());
        scan(spoon.reflect.path.CtRole.THROWN, m.getThrownTypes());
        scan(spoon.reflect.path.CtRole.BODY, m.getBody());
        scan(spoon.reflect.path.CtRole.COMMENT, m.getComments());
        exit(m);
    }

    @java.lang.Override
    public <T> void visitCtAnnotationMethod(spoon.reflect.declaration.CtAnnotationMethod<T> annotationMethod) {
        enter(annotationMethod);
        scan(spoon.reflect.path.CtRole.ANNOTATION, annotationMethod.getAnnotations());
        scan(spoon.reflect.path.CtRole.TYPE, annotationMethod.getType());
        scan(spoon.reflect.path.CtRole.DEFAULT_EXPRESSION, annotationMethod.getDefaultExpression());
        scan(spoon.reflect.path.CtRole.COMMENT, annotationMethod.getComments());
        exit(annotationMethod);
    }

    public <T> void visitCtNewArray(final spoon.reflect.code.CtNewArray<T> newArray) {
        enter(newArray);
        scan(spoon.reflect.path.CtRole.ANNOTATION, newArray.getAnnotations());
        scan(spoon.reflect.path.CtRole.TYPE, newArray.getType());
        scan(spoon.reflect.path.CtRole.CAST, newArray.getTypeCasts());
        scan(spoon.reflect.path.CtRole.EXPRESSION, newArray.getElements());
        scan(spoon.reflect.path.CtRole.DIMENSION, newArray.getDimensionExpressions());
        scan(spoon.reflect.path.CtRole.COMMENT, newArray.getComments());
        exit(newArray);
    }

    @java.lang.Override
    public <T> void visitCtConstructorCall(final spoon.reflect.code.CtConstructorCall<T> ctConstructorCall) {
        enter(ctConstructorCall);
        scan(spoon.reflect.path.CtRole.ANNOTATION, ctConstructorCall.getAnnotations());
        scan(spoon.reflect.path.CtRole.CAST, ctConstructorCall.getTypeCasts());
        scan(spoon.reflect.path.CtRole.EXECUTABLE_REF, ctConstructorCall.getExecutable());
        scan(spoon.reflect.path.CtRole.TARGET, ctConstructorCall.getTarget());
        scan(spoon.reflect.path.CtRole.ARGUMENT, ctConstructorCall.getArguments());
        scan(spoon.reflect.path.CtRole.COMMENT, ctConstructorCall.getComments());
        exit(ctConstructorCall);
    }

    public <T> void visitCtNewClass(final spoon.reflect.code.CtNewClass<T> newClass) {
        enter(newClass);
        scan(spoon.reflect.path.CtRole.ANNOTATION, newClass.getAnnotations());
        scan(spoon.reflect.path.CtRole.CAST, newClass.getTypeCasts());
        scan(spoon.reflect.path.CtRole.EXECUTABLE_REF, newClass.getExecutable());
        scan(spoon.reflect.path.CtRole.TARGET, newClass.getTarget());
        scan(spoon.reflect.path.CtRole.ARGUMENT, newClass.getArguments());
        scan(spoon.reflect.path.CtRole.NESTED_TYPE, newClass.getAnonymousClass());
        scan(spoon.reflect.path.CtRole.COMMENT, newClass.getComments());
        exit(newClass);
    }

    @java.lang.Override
    public <T> void visitCtLambda(final spoon.reflect.code.CtLambda<T> lambda) {
        enter(lambda);
        scan(spoon.reflect.path.CtRole.ANNOTATION, lambda.getAnnotations());
        scan(spoon.reflect.path.CtRole.TYPE, lambda.getType());
        scan(spoon.reflect.path.CtRole.CAST, lambda.getTypeCasts());
        scan(spoon.reflect.path.CtRole.PARAMETER, lambda.getParameters());
        scan(spoon.reflect.path.CtRole.BODY, lambda.getBody());
        scan(spoon.reflect.path.CtRole.EXPRESSION, lambda.getExpression());
        scan(spoon.reflect.path.CtRole.COMMENT, lambda.getComments());
        exit(lambda);
    }

    @java.lang.Override
    public <T, E extends spoon.reflect.code.CtExpression<?>> void visitCtExecutableReferenceExpression(final spoon.reflect.code.CtExecutableReferenceExpression<T, E> expression) {
        enter(expression);
        scan(spoon.reflect.path.CtRole.COMMENT, expression.getComments());
        scan(spoon.reflect.path.CtRole.ANNOTATION, expression.getAnnotations());
        scan(spoon.reflect.path.CtRole.TYPE, expression.getType());
        scan(spoon.reflect.path.CtRole.CAST, expression.getTypeCasts());
        scan(spoon.reflect.path.CtRole.EXECUTABLE_REF, expression.getExecutable());
        scan(spoon.reflect.path.CtRole.TARGET, expression.getTarget());
        exit(expression);
    }

    public <T, A extends T> void visitCtOperatorAssignment(final spoon.reflect.code.CtOperatorAssignment<T, A> assignment) {
        enter(assignment);
        scan(spoon.reflect.path.CtRole.ANNOTATION, assignment.getAnnotations());
        scan(spoon.reflect.path.CtRole.TYPE, assignment.getType());
        scan(spoon.reflect.path.CtRole.CAST, assignment.getTypeCasts());
        scan(spoon.reflect.path.CtRole.ASSIGNED, assignment.getAssigned());
        scan(spoon.reflect.path.CtRole.ASSIGNMENT, assignment.getAssignment());
        scan(spoon.reflect.path.CtRole.COMMENT, assignment.getComments());
        exit(assignment);
    }

    public void visitCtPackage(final spoon.reflect.declaration.CtPackage ctPackage) {
        enter(ctPackage);
        scan(spoon.reflect.path.CtRole.ANNOTATION, ctPackage.getAnnotations());
        scan(spoon.reflect.path.CtRole.SUB_PACKAGE, ctPackage.getPackages());
        scan(spoon.reflect.path.CtRole.CONTAINED_TYPE, ctPackage.getTypes());
        scan(spoon.reflect.path.CtRole.COMMENT, ctPackage.getComments());
        exit(ctPackage);
    }

    public void visitCtPackageReference(final spoon.reflect.reference.CtPackageReference reference) {
        enter(reference);
        scan(spoon.reflect.path.CtRole.ANNOTATION, reference.getAnnotations());
        exit(reference);
    }

    public <T> void visitCtParameter(final spoon.reflect.declaration.CtParameter<T> parameter) {
        enter(parameter);
        scan(spoon.reflect.path.CtRole.ANNOTATION, parameter.getAnnotations());
        scan(spoon.reflect.path.CtRole.TYPE, parameter.getType());
        scan(spoon.reflect.path.CtRole.COMMENT, parameter.getComments());
        exit(parameter);
    }

    public <T> void visitCtParameterReference(final spoon.reflect.reference.CtParameterReference<T> reference) {
        enter(reference);
        scan(spoon.reflect.path.CtRole.TYPE, reference.getType());
        scan(spoon.reflect.path.CtRole.ANNOTATION, reference.getAnnotations());
        exit(reference);
    }

    public <R> void visitCtReturn(final spoon.reflect.code.CtReturn<R> returnStatement) {
        enter(returnStatement);
        scan(spoon.reflect.path.CtRole.ANNOTATION, returnStatement.getAnnotations());
        scan(spoon.reflect.path.CtRole.EXPRESSION, returnStatement.getReturnedExpression());
        scan(spoon.reflect.path.CtRole.COMMENT, returnStatement.getComments());
        exit(returnStatement);
    }

    public <R> void visitCtStatementList(final spoon.reflect.code.CtStatementList statements) {
        enter(statements);
        scan(spoon.reflect.path.CtRole.ANNOTATION, statements.getAnnotations());
        scan(spoon.reflect.path.CtRole.STATEMENT, statements.getStatements());
        scan(spoon.reflect.path.CtRole.COMMENT, statements.getComments());
        exit(statements);
    }

    public <S> void visitCtSwitch(final spoon.reflect.code.CtSwitch<S> switchStatement) {
        enter(switchStatement);
        scan(spoon.reflect.path.CtRole.ANNOTATION, switchStatement.getAnnotations());
        scan(spoon.reflect.path.CtRole.EXPRESSION, switchStatement.getSelector());
        scan(spoon.reflect.path.CtRole.CASE, switchStatement.getCases());
        scan(spoon.reflect.path.CtRole.COMMENT, switchStatement.getComments());
        exit(switchStatement);
    }

    public void visitCtSynchronized(final spoon.reflect.code.CtSynchronized synchro) {
        enter(synchro);
        scan(spoon.reflect.path.CtRole.ANNOTATION, synchro.getAnnotations());
        scan(spoon.reflect.path.CtRole.EXPRESSION, synchro.getExpression());
        scan(spoon.reflect.path.CtRole.BODY, synchro.getBlock());
        scan(spoon.reflect.path.CtRole.COMMENT, synchro.getComments());
        exit(synchro);
    }

    public void visitCtThrow(final spoon.reflect.code.CtThrow throwStatement) {
        enter(throwStatement);
        scan(spoon.reflect.path.CtRole.ANNOTATION, throwStatement.getAnnotations());
        scan(spoon.reflect.path.CtRole.EXPRESSION, throwStatement.getThrownExpression());
        scan(spoon.reflect.path.CtRole.COMMENT, throwStatement.getComments());
        exit(throwStatement);
    }

    public void visitCtTry(final spoon.reflect.code.CtTry tryBlock) {
        enter(tryBlock);
        scan(spoon.reflect.path.CtRole.ANNOTATION, tryBlock.getAnnotations());
        scan(spoon.reflect.path.CtRole.BODY, tryBlock.getBody());
        scan(spoon.reflect.path.CtRole.CATCH, tryBlock.getCatchers());
        scan(spoon.reflect.path.CtRole.FINALIZER, tryBlock.getFinalizer());
        scan(spoon.reflect.path.CtRole.COMMENT, tryBlock.getComments());
        exit(tryBlock);
    }

    @java.lang.Override
    public void visitCtTryWithResource(final spoon.reflect.code.CtTryWithResource tryWithResource) {
        enter(tryWithResource);
        scan(spoon.reflect.path.CtRole.ANNOTATION, tryWithResource.getAnnotations());
        scan(spoon.reflect.path.CtRole.TRY_RESOURCE, tryWithResource.getResources());
        scan(spoon.reflect.path.CtRole.BODY, tryWithResource.getBody());
        scan(spoon.reflect.path.CtRole.CATCH, tryWithResource.getCatchers());
        scan(spoon.reflect.path.CtRole.FINALIZER, tryWithResource.getFinalizer());
        scan(spoon.reflect.path.CtRole.COMMENT, tryWithResource.getComments());
        exit(tryWithResource);
    }

    public void visitCtTypeParameterReference(final spoon.reflect.reference.CtTypeParameterReference ref) {
        enter(ref);
        scan(spoon.reflect.path.CtRole.PACKAGE_REF, ref.getPackage());
        scan(spoon.reflect.path.CtRole.DECLARING_TYPE, ref.getDeclaringType());
        scan(spoon.reflect.path.CtRole.ANNOTATION, ref.getAnnotations());
        scan(spoon.reflect.path.CtRole.BOUNDING_TYPE, ref.getBoundingType());
        exit(ref);
    }

    @java.lang.Override
    public void visitCtWildcardReference(spoon.reflect.reference.CtWildcardReference wildcardReference) {
        enter(wildcardReference);
        scan(spoon.reflect.path.CtRole.PACKAGE_REF, wildcardReference.getPackage());
        scan(spoon.reflect.path.CtRole.DECLARING_TYPE, wildcardReference.getDeclaringType());
        scan(spoon.reflect.path.CtRole.ANNOTATION, wildcardReference.getAnnotations());
        scan(spoon.reflect.path.CtRole.BOUNDING_TYPE, wildcardReference.getBoundingType());
        exit(wildcardReference);
    }

    @java.lang.Override
    public <T> void visitCtIntersectionTypeReference(final spoon.reflect.reference.CtIntersectionTypeReference<T> reference) {
        enter(reference);
        scan(spoon.reflect.path.CtRole.PACKAGE_REF, reference.getPackage());
        scan(spoon.reflect.path.CtRole.DECLARING_TYPE, reference.getDeclaringType());
        // TypeReferenceTest fails if actual type arguments are really not set-able on CtIntersectionTypeReference
        scan(spoon.reflect.path.CtRole.TYPE_ARGUMENT, reference.getActualTypeArguments());
        scan(spoon.reflect.path.CtRole.ANNOTATION, reference.getAnnotations());
        scan(spoon.reflect.path.CtRole.BOUND, reference.getBounds());
        exit(reference);
    }

    public <T> void visitCtTypeReference(final spoon.reflect.reference.CtTypeReference<T> reference) {
        enter(reference);
        scan(spoon.reflect.path.CtRole.PACKAGE_REF, reference.getPackage());
        scan(spoon.reflect.path.CtRole.DECLARING_TYPE, reference.getDeclaringType());
        scan(spoon.reflect.path.CtRole.TYPE_ARGUMENT, reference.getActualTypeArguments());
        scan(spoon.reflect.path.CtRole.ANNOTATION, reference.getAnnotations());
        scan(spoon.reflect.path.CtRole.COMMENT, reference.getComments());
        exit(reference);
    }

    @java.lang.Override
    public <T> void visitCtTypeAccess(final spoon.reflect.code.CtTypeAccess<T> typeAccess) {
        enter(typeAccess);
        scan(spoon.reflect.path.CtRole.ANNOTATION, typeAccess.getAnnotations());
        scan(spoon.reflect.path.CtRole.CAST, typeAccess.getTypeCasts());
        scan(spoon.reflect.path.CtRole.ACCESSED_TYPE, typeAccess.getAccessedType());
        scan(spoon.reflect.path.CtRole.COMMENT, typeAccess.getComments());
        exit(typeAccess);
    }

    public <T> void visitCtUnaryOperator(final spoon.reflect.code.CtUnaryOperator<T> operator) {
        enter(operator);
        scan(spoon.reflect.path.CtRole.ANNOTATION, operator.getAnnotations());
        scan(spoon.reflect.path.CtRole.TYPE, operator.getType());
        scan(spoon.reflect.path.CtRole.CAST, operator.getTypeCasts());
        scan(spoon.reflect.path.CtRole.EXPRESSION, operator.getOperand());
        scan(spoon.reflect.path.CtRole.COMMENT, operator.getComments());
        exit(operator);
    }

    @java.lang.Override
    public <T> void visitCtVariableRead(final spoon.reflect.code.CtVariableRead<T> variableRead) {
        enter(variableRead);
        scan(spoon.reflect.path.CtRole.ANNOTATION, variableRead.getAnnotations());
        scan(spoon.reflect.path.CtRole.CAST, variableRead.getTypeCasts());
        scan(spoon.reflect.path.CtRole.VARIABLE, variableRead.getVariable());
        scan(spoon.reflect.path.CtRole.COMMENT, variableRead.getComments());
        exit(variableRead);
    }

    @java.lang.Override
    public <T> void visitCtVariableWrite(final spoon.reflect.code.CtVariableWrite<T> variableWrite) {
        enter(variableWrite);
        scan(spoon.reflect.path.CtRole.ANNOTATION, variableWrite.getAnnotations());
        scan(spoon.reflect.path.CtRole.CAST, variableWrite.getTypeCasts());
        scan(spoon.reflect.path.CtRole.VARIABLE, variableWrite.getVariable());
        scan(spoon.reflect.path.CtRole.COMMENT, variableWrite.getComments());
        exit(variableWrite);
    }

    public void visitCtWhile(final spoon.reflect.code.CtWhile whileLoop) {
        enter(whileLoop);
        scan(spoon.reflect.path.CtRole.ANNOTATION, whileLoop.getAnnotations());
        scan(spoon.reflect.path.CtRole.EXPRESSION, whileLoop.getLoopingExpression());
        scan(spoon.reflect.path.CtRole.BODY, whileLoop.getBody());
        scan(spoon.reflect.path.CtRole.COMMENT, whileLoop.getComments());
        exit(whileLoop);
    }

    public <T> void visitCtCodeSnippetExpression(final spoon.reflect.code.CtCodeSnippetExpression<T> expression) {
        enter(expression);
        scan(spoon.reflect.path.CtRole.TYPE, expression.getType());
        scan(spoon.reflect.path.CtRole.COMMENT, expression.getComments());
        scan(spoon.reflect.path.CtRole.ANNOTATION, expression.getAnnotations());
        scan(spoon.reflect.path.CtRole.CAST, expression.getTypeCasts());
        exit(expression);
    }

    public void visitCtCodeSnippetStatement(final spoon.reflect.code.CtCodeSnippetStatement statement) {
        enter(statement);
        scan(spoon.reflect.path.CtRole.COMMENT, statement.getComments());
        scan(spoon.reflect.path.CtRole.ANNOTATION, statement.getAnnotations());
        exit(statement);
    }

    public <T> void visitCtUnboundVariableReference(final spoon.reflect.reference.CtUnboundVariableReference<T> reference) {
        enter(reference);
        scan(spoon.reflect.path.CtRole.TYPE, reference.getType());
        exit(reference);
    }

    @java.lang.Override
    public <T> void visitCtFieldRead(final spoon.reflect.code.CtFieldRead<T> fieldRead) {
        enter(fieldRead);
        scan(spoon.reflect.path.CtRole.ANNOTATION, fieldRead.getAnnotations());
        scan(spoon.reflect.path.CtRole.CAST, fieldRead.getTypeCasts());
        scan(spoon.reflect.path.CtRole.TARGET, fieldRead.getTarget());
        scan(spoon.reflect.path.CtRole.VARIABLE, fieldRead.getVariable());
        scan(spoon.reflect.path.CtRole.COMMENT, fieldRead.getComments());
        exit(fieldRead);
    }

    @java.lang.Override
    public <T> void visitCtFieldWrite(final spoon.reflect.code.CtFieldWrite<T> fieldWrite) {
        enter(fieldWrite);
        scan(spoon.reflect.path.CtRole.ANNOTATION, fieldWrite.getAnnotations());
        scan(spoon.reflect.path.CtRole.CAST, fieldWrite.getTypeCasts());
        scan(spoon.reflect.path.CtRole.TARGET, fieldWrite.getTarget());
        scan(spoon.reflect.path.CtRole.VARIABLE, fieldWrite.getVariable());
        scan(spoon.reflect.path.CtRole.COMMENT, fieldWrite.getComments());
        exit(fieldWrite);
    }

    @java.lang.Override
    public <T> void visitCtSuperAccess(final spoon.reflect.code.CtSuperAccess<T> f) {
        enter(f);
        scan(spoon.reflect.path.CtRole.COMMENT, f.getComments());
        scan(spoon.reflect.path.CtRole.ANNOTATION, f.getAnnotations());
        scan(spoon.reflect.path.CtRole.CAST, f.getTypeCasts());
        scan(spoon.reflect.path.CtRole.TARGET, f.getTarget());
        scan(spoon.reflect.path.CtRole.VARIABLE, f.getVariable());
        exit(f);
    }

    @java.lang.Override
    public void visitCtComment(final spoon.reflect.code.CtComment comment) {
        enter(comment);
        scan(spoon.reflect.path.CtRole.COMMENT, comment.getComments());
        scan(spoon.reflect.path.CtRole.ANNOTATION, comment.getAnnotations());
        exit(comment);
    }

    @java.lang.Override
    public void visitCtJavaDoc(final spoon.reflect.code.CtJavaDoc javaDoc) {
        enter(javaDoc);
        scan(spoon.reflect.path.CtRole.COMMENT, javaDoc.getComments());
        scan(spoon.reflect.path.CtRole.ANNOTATION, javaDoc.getAnnotations());
        scan(spoon.reflect.path.CtRole.COMMENT_TAG, javaDoc.getTags());
        exit(javaDoc);
    }

    @java.lang.Override
    public void visitCtJavaDocTag(final spoon.reflect.code.CtJavaDocTag docTag) {
        enter(docTag);
        scan(spoon.reflect.path.CtRole.COMMENT, docTag.getComments());
        scan(spoon.reflect.path.CtRole.ANNOTATION, docTag.getAnnotations());
        exit(docTag);
    }

    @java.lang.Override
    public void visitCtImport(final spoon.reflect.declaration.CtImport ctImport) {
        enter(ctImport);
        scan(spoon.reflect.path.CtRole.IMPORT_REFERENCE, ctImport.getReference());
        scan(spoon.reflect.path.CtRole.ANNOTATION, ctImport.getAnnotations());
        scan(spoon.reflect.path.CtRole.COMMENT, ctImport.getComments());
        exit(ctImport);
    }

    @java.lang.Override
    public void visitCtModule(spoon.reflect.declaration.CtModule module) {
        enter(module);
        scan(spoon.reflect.path.CtRole.COMMENT, module.getComments());
        scan(spoon.reflect.path.CtRole.ANNOTATION, module.getAnnotations());
        scan(spoon.reflect.path.CtRole.MODULE_DIRECTIVE, module.getModuleDirectives());
        scan(spoon.reflect.path.CtRole.SUB_PACKAGE, module.getRootPackage());
        exit(module);
    }

    @java.lang.Override
    public void visitCtModuleReference(spoon.reflect.reference.CtModuleReference moduleReference) {
        enter(moduleReference);
        scan(spoon.reflect.path.CtRole.ANNOTATION, moduleReference.getAnnotations());
        exit(moduleReference);
    }

    @java.lang.Override
    public void visitCtPackageExport(spoon.reflect.declaration.CtPackageExport moduleExport) {
        enter(moduleExport);
        scan(spoon.reflect.path.CtRole.COMMENT, moduleExport.getComments());
        scan(spoon.reflect.path.CtRole.PACKAGE_REF, moduleExport.getPackageReference());
        scan(spoon.reflect.path.CtRole.MODULE_REF, moduleExport.getTargetExport());
        scan(spoon.reflect.path.CtRole.ANNOTATION, moduleExport.getAnnotations());
        exit(moduleExport);
    }

    @java.lang.Override
    public void visitCtModuleRequirement(spoon.reflect.declaration.CtModuleRequirement moduleRequirement) {
        enter(moduleRequirement);
        scan(spoon.reflect.path.CtRole.COMMENT, moduleRequirement.getComments());
        scan(spoon.reflect.path.CtRole.MODULE_REF, moduleRequirement.getModuleReference());
        scan(spoon.reflect.path.CtRole.ANNOTATION, moduleRequirement.getAnnotations());
        exit(moduleRequirement);
    }

    @java.lang.Override
    public void visitCtProvidedService(spoon.reflect.declaration.CtProvidedService moduleProvidedService) {
        enter(moduleProvidedService);
        scan(spoon.reflect.path.CtRole.COMMENT, moduleProvidedService.getComments());
        scan(spoon.reflect.path.CtRole.SERVICE_TYPE, moduleProvidedService.getServiceType());
        scan(spoon.reflect.path.CtRole.IMPLEMENTATION_TYPE, moduleProvidedService.getImplementationTypes());
        scan(spoon.reflect.path.CtRole.ANNOTATION, moduleProvidedService.getAnnotations());
        exit(moduleProvidedService);
    }

    @java.lang.Override
    public void visitCtUsedService(spoon.reflect.declaration.CtUsedService usedService) {
        enter(usedService);
        scan(spoon.reflect.path.CtRole.COMMENT, usedService.getComments());
        scan(spoon.reflect.path.CtRole.SERVICE_TYPE, usedService.getServiceType());
        scan(spoon.reflect.path.CtRole.ANNOTATION, usedService.getAnnotations());
        exit(usedService);
    }
}

