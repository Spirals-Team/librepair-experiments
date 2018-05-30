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
package spoon.support.compiler.jdt;


@java.lang.SuppressWarnings("unchecked")
public class ParentExiter extends spoon.reflect.visitor.CtInheritanceScanner {
    private final spoon.support.compiler.jdt.JDTTreeBuilder jdtTreeBuilder;

    private spoon.reflect.declaration.CtElement child;

    private org.eclipse.jdt.internal.compiler.ast.ASTNode childJDT;

    private java.util.Map<spoon.reflect.declaration.CtTypedElement<?>, java.util.List<spoon.reflect.declaration.CtAnnotation>> annotationsMap = new java.util.HashMap<>();

    /**
     *
     *
     * @param jdtTreeBuilder
     * 		
     */
    ParentExiter(spoon.support.compiler.jdt.JDTTreeBuilder jdtTreeBuilder) {
        this.jdtTreeBuilder = jdtTreeBuilder;
    }

    public void setChild(spoon.reflect.declaration.CtElement child) {
        this.child = child;
    }

    public void setChild(org.eclipse.jdt.internal.compiler.ast.ASTNode child) {
        this.childJDT = child;
    }

    @java.lang.Override
    public void scanCtElement(spoon.reflect.declaration.CtElement e) {
        if (((child) instanceof spoon.reflect.declaration.CtAnnotation) && (this.jdtTreeBuilder.getContextBuilder().annotationValueName.isEmpty())) {
            // we check if the current element can have the annotation attached
            spoon.reflect.declaration.CtAnnotatedElementType annotatedElementType = spoon.reflect.declaration.CtAnnotation.getAnnotatedElementTypeForCtElement(e);
            annotatedElementType = ((e instanceof spoon.reflect.declaration.CtTypeParameter) || (e instanceof spoon.reflect.reference.CtTypeParameterReference)) ? spoon.reflect.declaration.CtAnnotatedElementType.TYPE_USE : annotatedElementType;
            // in case of noclasspath, we cannot be 100% sure, so we guess it must be attached...
            if ((this.jdtTreeBuilder.getFactory().getEnvironment().getNoClasspath()) || ((annotatedElementType != null) && (spoon.support.compiler.jdt.JDTTreeBuilderQuery.hasAnnotationWithType(((org.eclipse.jdt.internal.compiler.ast.Annotation) (childJDT)), annotatedElementType)))) {
                e.addAnnotation(((spoon.reflect.declaration.CtAnnotation<?>) (child)));
            }
            // in this case the annotation should be (also) attached to the type
            if ((e instanceof spoon.reflect.declaration.CtTypedElement) && (spoon.support.compiler.jdt.JDTTreeBuilderQuery.hasAnnotationWithType(((org.eclipse.jdt.internal.compiler.ast.Annotation) (childJDT)), spoon.reflect.declaration.CtAnnotatedElementType.TYPE_USE))) {
                java.util.List<spoon.reflect.declaration.CtAnnotation> annotations = new java.util.ArrayList<>();
                if (!(annotationsMap.containsKey(e))) {
                    annotationsMap.put(((spoon.reflect.declaration.CtTypedElement<?>) (e)), annotations);
                }else {
                    annotations = annotationsMap.get(e);
                }
                annotations.add(((spoon.reflect.declaration.CtAnnotation) (child.clone())));
                annotationsMap.put(((spoon.reflect.declaration.CtTypedElement<?>) (e)), annotations);
            }
        }
    }

    private void substituteAnnotation(spoon.reflect.declaration.CtTypedElement ele) {
        if (annotationsMap.containsKey(ele)) {
            java.util.List<spoon.reflect.declaration.CtAnnotation> annotations = annotationsMap.get(ele);
            for (spoon.reflect.declaration.CtAnnotation annotation : annotations) {
                // in case of noclasspath we attached previously the element:
                // if we are here, we may have find an element for whom it's a better place
                if ((this.jdtTreeBuilder.getFactory().getEnvironment().getNoClasspath()) && (annotation.isParentInitialized())) {
                    spoon.reflect.declaration.CtElement parent = annotation.getParent();
                    parent.removeAnnotation(annotation);
                }
                if (!(ele.getType().getAnnotations().contains(annotation))) {
                    ele.getType().addAnnotation(annotation.clone());
                }
            }
            annotationsMap.remove(ele);
        }
    }

    @java.lang.Override
    public <R> void scanCtExecutable(spoon.reflect.declaration.CtExecutable<R> e) {
        if ((child) instanceof spoon.reflect.code.CtTypeAccess) {
            e.addThrownType(((spoon.reflect.code.CtTypeAccess) (child)).getAccessedType());
            return;
        }else
            if ((child) instanceof spoon.reflect.declaration.CtParameter) {
                e.addParameter(((spoon.reflect.declaration.CtParameter<?>) (child)));
                return;
            }else
                if (((child) instanceof spoon.reflect.code.CtBlock) && (!((e instanceof spoon.reflect.declaration.CtMethod) || (e instanceof spoon.reflect.declaration.CtConstructor)))) {
                    e.setBody(((spoon.reflect.code.CtBlock<R>) (child)));
                    return;
                }


        super.scanCtExecutable(e);
    }

    @java.lang.Override
    public void scanCtFormalTypeDeclarer(spoon.reflect.declaration.CtFormalTypeDeclarer e) {
        if (((childJDT) instanceof org.eclipse.jdt.internal.compiler.ast.TypeParameter) && ((child) instanceof spoon.reflect.declaration.CtTypeParameter)) {
            e.addFormalCtTypeParameter(((spoon.reflect.declaration.CtTypeParameter) (child)));
        }
        return;
    }

    @java.lang.Override
    public void scanCtLoop(spoon.reflect.code.CtLoop loop) {
        if (((loop.getBody()) == null) && ((child) instanceof spoon.reflect.code.CtStatement)) {
            spoon.reflect.code.CtStatement child = ((spoon.reflect.code.CtStatement) (this.child));
            if (!((this.child) instanceof spoon.reflect.code.CtBlock)) {
                child = jdtTreeBuilder.getFactory().Code().createCtBlock(child);
                child.setImplicit(true);
            }
            loop.setBody(child);
        }
        super.scanCtLoop(loop);
    }

    @java.lang.Override
    public <T, E extends spoon.reflect.code.CtExpression<?>> void scanCtTargetedExpression(spoon.reflect.code.CtTargetedExpression<T, E> targetedExpression) {
        if ((child) instanceof spoon.reflect.code.CtExpression) {
            targetedExpression.setTarget(((E) (child)));
            return;
        }
        super.scanCtTargetedExpression(targetedExpression);
    }

    @java.lang.Override
    public <T> void scanCtType(spoon.reflect.declaration.CtType<T> type) {
        if (((child) instanceof spoon.reflect.declaration.CtType) && (!((child) instanceof spoon.reflect.declaration.CtTypeParameter))) {
            if (type.getTypeMembers().contains(child)) {
                type.removeTypeMember(((spoon.reflect.declaration.CtType) (child)));
            }
            type.addNestedType(((spoon.reflect.declaration.CtType<?>) (child)));
            return;
        }else
            if (((child) instanceof spoon.reflect.declaration.CtEnumValue) && (type instanceof spoon.reflect.declaration.CtEnum)) {
                ((spoon.reflect.declaration.CtEnum) (type)).addEnumValue(((spoon.reflect.declaration.CtEnumValue) (child)));
            }else
                if ((child) instanceof spoon.reflect.declaration.CtField) {
                    type.addField(((spoon.reflect.declaration.CtField<?>) (child)));
                    return;
                }else
                    if ((child) instanceof spoon.reflect.declaration.CtConstructor) {
                        return;
                    }



        if ((child) instanceof spoon.reflect.declaration.CtMethod) {
            type.addMethod(((spoon.reflect.declaration.CtMethod<?>) (child)));
            return;
        }
        super.scanCtType(type);
    }

    @java.lang.Override
    public <T> void scanCtVariable(spoon.reflect.declaration.CtVariable<T> v) {
        if (((childJDT) instanceof org.eclipse.jdt.internal.compiler.ast.TypeReference) && ((child) instanceof spoon.reflect.code.CtTypeAccess)) {
            v.setType(((spoon.reflect.code.CtTypeAccess) (child)).getAccessedType());
            substituteAnnotation(((spoon.reflect.declaration.CtTypedElement) (v)));
            return;
        }else
            if (((child) instanceof spoon.reflect.code.CtExpression) && (hasChildEqualsToDefaultValue(v))) {
                v.setDefaultExpression(((spoon.reflect.code.CtExpression<T>) (child)));
                return;
            }

        super.scanCtVariable(v);
    }

    private <T> boolean hasChildEqualsToDefaultValue(spoon.reflect.declaration.CtVariable<T> ctVariable) {
        if ((jdtTreeBuilder.getContextBuilder().stack.peek().node) instanceof org.eclipse.jdt.internal.compiler.ast.AnnotationMethodDeclaration) {
            final org.eclipse.jdt.internal.compiler.ast.AnnotationMethodDeclaration parent = ((org.eclipse.jdt.internal.compiler.ast.AnnotationMethodDeclaration) (jdtTreeBuilder.getContextBuilder().stack.peek().node));
            // Default value is equals to the jdt child.
            return (((parent.defaultValue) != null) && (getFinalExpressionFromCast(parent.defaultValue).equals(childJDT))) && // Return type not yet initialized.
            (!(child.equals(ctVariable.getDefaultExpression())));
        }
        final org.eclipse.jdt.internal.compiler.ast.AbstractVariableDeclaration parent = ((org.eclipse.jdt.internal.compiler.ast.AbstractVariableDeclaration) (jdtTreeBuilder.getContextBuilder().stack.peek().node));
        // Default value is equals to the jdt child.
        return (((parent.initialization) != null) && (getFinalExpressionFromCast(parent.initialization).equals(childJDT))) && // Return type not yet initialized.
        (!(child.equals(ctVariable.getDefaultExpression())));
    }

    @java.lang.Override
    public <A extends java.lang.annotation.Annotation> void visitCtAnnotation(spoon.reflect.declaration.CtAnnotation<A> annotation) {
        if ((child) instanceof spoon.reflect.code.CtExpression) {
            annotation.addValue(this.jdtTreeBuilder.getContextBuilder().annotationValueName.peek(), child);
        }
        super.visitCtAnnotation(annotation);
    }

    @java.lang.Override
    public <T> void visitCtConstructor(spoon.reflect.declaration.CtConstructor<T> e) {
        if (((e.getBody()) == null) && ((child) instanceof spoon.reflect.code.CtBlock)) {
            e.setBody(((spoon.reflect.code.CtBlock) (child)));
            return;
        }else
            if ((child) instanceof spoon.reflect.code.CtStatement) {
                visitCtBlock(e.getBody());
                return;
            }

        super.visitCtConstructor(e);
    }

    @java.lang.Override
    public <T> void visitCtMethod(spoon.reflect.declaration.CtMethod<T> e) {
        if (((e.getBody()) == null) && ((child) instanceof spoon.reflect.code.CtBlock)) {
            e.setBody(((spoon.reflect.code.CtBlock) (child)));
            return;
        }else
            if ((child) instanceof spoon.reflect.code.CtStatement) {
                visitCtBlock(e.getBody());
                return;
            }else
                if (((child) instanceof spoon.reflect.code.CtTypeAccess) && (hasChildEqualsToType(e))) {
                    e.setType(((spoon.reflect.code.CtTypeAccess) (child)).getAccessedType());
                    substituteAnnotation(e);
                    return;
                }


        super.visitCtMethod(e);
    }

    private <T> boolean hasChildEqualsToType(spoon.reflect.declaration.CtMethod<T> ctMethod) {
        final org.eclipse.jdt.internal.compiler.ast.MethodDeclaration parent = ((org.eclipse.jdt.internal.compiler.ast.MethodDeclaration) (jdtTreeBuilder.getContextBuilder().stack.peek().node));
        // Return type is equals to the jdt child.
        return (((parent.returnType) != null) && (parent.returnType.equals(childJDT))) && // Return type not yet initialized.
        (!(child.equals(ctMethod.getType())));
    }

    @java.lang.Override
    public <T> void visitCtAnnotationMethod(spoon.reflect.declaration.CtAnnotationMethod<T> annotationMethod) {
        if (((child) instanceof spoon.reflect.code.CtExpression) && (hasChildEqualsToDefaultValue(annotationMethod))) {
            annotationMethod.setDefaultExpression(((spoon.reflect.code.CtExpression) (child)));
            return;
        }
        super.visitCtAnnotationMethod(annotationMethod);
    }

    private <T> boolean hasChildEqualsToDefaultValue(spoon.reflect.declaration.CtAnnotationMethod<T> ctAnnotationMethod) {
        final org.eclipse.jdt.internal.compiler.ast.AnnotationMethodDeclaration parent = ((org.eclipse.jdt.internal.compiler.ast.AnnotationMethodDeclaration) (jdtTreeBuilder.getContextBuilder().stack.peek().node));
        // Default value is equals to the jdt child.
        return (((parent.defaultValue) != null) && (parent.defaultValue.equals(childJDT))) && // Default value not yet initialized.
        (!(child.equals(ctAnnotationMethod.getDefaultExpression())));
    }

    @java.lang.Override
    public void visitCtAnonymousExecutable(spoon.reflect.declaration.CtAnonymousExecutable e) {
        if ((child) instanceof spoon.reflect.code.CtBlock) {
            e.setBody(((spoon.reflect.code.CtBlock) (child)));
            return;
        }
        super.visitCtAnonymousExecutable(e);
    }

    @java.lang.Override
    public <T> void visitCtArrayRead(spoon.reflect.code.CtArrayRead<T> arrayRead) {
        if (visitArrayAccess(arrayRead)) {
            super.visitCtArrayRead(arrayRead);
        }
    }

    @java.lang.Override
    public <T> void visitCtArrayWrite(spoon.reflect.code.CtArrayWrite<T> arrayWrite) {
        if (visitArrayAccess(arrayWrite)) {
            super.visitCtArrayWrite(arrayWrite);
        }
    }

    private <T, E extends spoon.reflect.code.CtExpression<?>> boolean visitArrayAccess(spoon.reflect.code.CtArrayAccess<T, E> arrayAccess) {
        if ((child) instanceof spoon.reflect.code.CtExpression) {
            if ((arrayAccess.getTarget()) == null) {
                arrayAccess.setTarget(((E) (child)));
                return false;
            }else {
                arrayAccess.setIndexExpression(((spoon.reflect.code.CtExpression<java.lang.Integer>) (child)));
                return false;
            }
        }
        return true;
    }

    @java.lang.Override
    public <T> void visitCtAssert(spoon.reflect.code.CtAssert<T> asserted) {
        if ((child) instanceof spoon.reflect.code.CtExpression) {
            if ((asserted.getAssertExpression()) == null) {
                asserted.setAssertExpression(((spoon.reflect.code.CtExpression<java.lang.Boolean>) (child)));
                return;
            }else {
                asserted.setExpression(((spoon.reflect.code.CtExpression<T>) (child)));
                return;
            }
        }
        super.visitCtAssert(asserted);
    }

    @java.lang.Override
    public <T, A extends T> void visitCtAssignment(spoon.reflect.code.CtAssignment<T, A> assignement) {
        if ((child) instanceof spoon.reflect.code.CtExpression) {
            if ((assignement.getAssigned()) == null) {
                assignement.setAssigned(((spoon.reflect.code.CtExpression<T>) (child)));
                return;
            }else
                if ((assignement.getAssignment()) == null) {
                    assignement.setAssignment(((spoon.reflect.code.CtExpression<A>) (child)));
                    return;
                }

        }
        super.visitCtAssignment(assignement);
    }

    @java.lang.Override
    public <T> void visitCtBinaryOperator(spoon.reflect.code.CtBinaryOperator<T> operator) {
        if ((child) instanceof spoon.reflect.code.CtExpression) {
            if ((operator.getLeftHandOperand()) == null) {
                operator.setLeftHandOperand(((spoon.reflect.code.CtExpression<?>) (child)));
                return;
            }else
                if ((operator.getRightHandOperand()) == null) {
                    operator.setRightHandOperand(((spoon.reflect.code.CtExpression<?>) (child)));
                    return;
                }else
                    if ((jdtTreeBuilder.getContextBuilder().stack.peek().node) instanceof org.eclipse.jdt.internal.compiler.ast.StringLiteralConcatenation) {
                        spoon.reflect.code.CtBinaryOperator<?> op = operator.getFactory().Core().createBinaryOperator();
                        op.setKind(spoon.reflect.code.BinaryOperatorKind.PLUS);
                        op.setLeftHandOperand(operator.getRightHandOperand());
                        op.setRightHandOperand(((spoon.reflect.code.CtExpression<?>) (child)));
                        operator.setRightHandOperand(op);
                        int[] lineSeparatorPositions = this.jdtTreeBuilder.getContextBuilder().compilationunitdeclaration.compilationResult.lineSeparatorPositions;
                        spoon.reflect.cu.SourcePosition leftPosition = op.getLeftHandOperand().getPosition();
                        spoon.reflect.cu.SourcePosition rightPosition = op.getRightHandOperand().getPosition();
                        op.setPosition(op.getFactory().createSourcePosition(leftPosition.getCompilationUnit(), leftPosition.getSourceStart(), rightPosition.getSourceEnd(), lineSeparatorPositions));
                        return;
                    }


        }
        super.visitCtBinaryOperator(operator);
    }

    @java.lang.Override
    public <R> void visitCtBlock(spoon.reflect.code.CtBlock<R> block) {
        if ((child) instanceof spoon.reflect.code.CtStatement) {
            block.addStatement(((spoon.reflect.code.CtStatement) (child)));
            return;
        }
        super.visitCtBlock(block);
    }

    @java.lang.Override
    public <E> void visitCtCase(spoon.reflect.code.CtCase<E> caseStatement) {
        final org.eclipse.jdt.internal.compiler.ast.ASTNode node = jdtTreeBuilder.getContextBuilder().stack.peek().node;
        if ((((node instanceof org.eclipse.jdt.internal.compiler.ast.CaseStatement) && ((((org.eclipse.jdt.internal.compiler.ast.CaseStatement) (node)).constantExpression) != null)) && ((caseStatement.getCaseExpression()) == null)) && ((child) instanceof spoon.reflect.code.CtExpression)) {
            caseStatement.setCaseExpression(((spoon.reflect.code.CtExpression<E>) (child)));
            return;
        }else
            if ((child) instanceof spoon.reflect.code.CtStatement) {
                caseStatement.addStatement(((spoon.reflect.code.CtStatement) (child)));
                return;
            }

        super.visitCtCase(caseStatement);
    }

    @java.lang.Override
    public void visitCtCatch(spoon.reflect.code.CtCatch catchBlock) {
        if ((child) instanceof spoon.reflect.code.CtBlock) {
            catchBlock.setBody(((spoon.reflect.code.CtBlock<?>) (child)));
            return;
        }else
            if ((child) instanceof spoon.reflect.code.CtCatchVariable) {
                catchBlock.setParameter(((spoon.reflect.code.CtCatchVariable<? extends java.lang.Throwable>) (child)));
                return;
            }

        super.visitCtCatch(catchBlock);
    }

    @java.lang.Override
    public <T> void visitCtCatchVariable(spoon.reflect.code.CtCatchVariable<T> e) {
        if ((jdtTreeBuilder.getContextBuilder().stack.peekFirst().node) instanceof org.eclipse.jdt.internal.compiler.ast.UnionTypeReference) {
            e.addMultiType(((spoon.reflect.reference.CtTypeReference<?>) (child)));
            return;
        }
        super.visitCtCatchVariable(e);
    }

    @java.lang.Override
    public <T> void visitCtClass(spoon.reflect.declaration.CtClass<T> ctClass) {
        if ((child) instanceof spoon.reflect.declaration.CtConstructor) {
            ctClass.addConstructor(((spoon.reflect.declaration.CtConstructor<T>) (child)));
        }
        if ((child) instanceof spoon.reflect.declaration.CtAnonymousExecutable) {
            ctClass.addAnonymousExecutable(((spoon.reflect.declaration.CtAnonymousExecutable) (child)));
        }
        super.visitCtClass(ctClass);
    }

    @java.lang.Override
    public void visitCtTypeParameter(spoon.reflect.declaration.CtTypeParameter typeParameter) {
        if (((childJDT) instanceof org.eclipse.jdt.internal.compiler.ast.TypeReference) && ((child) instanceof spoon.reflect.code.CtTypeAccess)) {
            if ((typeParameter.getSuperclass()) == null) {
                typeParameter.setSuperclass(((spoon.reflect.code.CtTypeAccess) (child)).getAccessedType());
            }else
                if ((typeParameter.getSuperclass()) instanceof spoon.reflect.reference.CtIntersectionTypeReference) {
                    typeParameter.getSuperclass().asCtIntersectionTypeReference().addBound(((spoon.reflect.code.CtTypeAccess) (child)).getAccessedType());
                }else {
                    final java.util.List<spoon.reflect.reference.CtTypeReference<?>> refs = new java.util.ArrayList<>();
                    refs.add(typeParameter.getSuperclass());
                    refs.add(((spoon.reflect.code.CtTypeAccess) (child)).getAccessedType());
                    typeParameter.setSuperclass(jdtTreeBuilder.getFactory().Type().createIntersectionTypeReferenceWithBounds(refs));
                }

            return;
        }
        super.visitCtTypeParameter(typeParameter);
    }

    @java.lang.Override
    public <T> void visitCtConditional(spoon.reflect.code.CtConditional<T> conditional) {
        if ((child) instanceof spoon.reflect.code.CtExpression) {
            if ((conditional.getCondition()) == null) {
                conditional.setCondition(((spoon.reflect.code.CtExpression<java.lang.Boolean>) (child)));
            }else
                if ((conditional.getThenExpression()) == null) {
                    conditional.setThenExpression(((spoon.reflect.code.CtExpression<T>) (child)));
                }else
                    if ((conditional.getElseExpression()) == null) {
                        conditional.setElseExpression(((spoon.reflect.code.CtExpression<T>) (child)));
                    }


        }
        super.visitCtConditional(conditional);
    }

    @java.lang.Override
    public void visitCtDo(spoon.reflect.code.CtDo doLoop) {
        if ((((doLoop.getBody()) != null) && ((child) instanceof spoon.reflect.code.CtExpression)) && ((doLoop.getLoopingExpression()) == null)) {
            doLoop.setLoopingExpression(((spoon.reflect.code.CtExpression<java.lang.Boolean>) (child)));
            return;
        }
        super.visitCtDo(doLoop);
    }

    @java.lang.Override
    public void visitCtFor(spoon.reflect.code.CtFor forLoop) {
        if ((isContainedInForInit()) && ((child) instanceof spoon.reflect.code.CtStatement)) {
            forLoop.addForInit(((spoon.reflect.code.CtStatement) (child)));
            return;
        }else
            if ((isContainedInForUpdate()) && ((child) instanceof spoon.reflect.code.CtStatement)) {
                forLoop.addForUpdate(((spoon.reflect.code.CtStatement) (child)));
                return;
            }else
                if (((forLoop.getExpression()) == null) && ((child) instanceof spoon.reflect.code.CtExpression)) {
                    forLoop.setExpression(((spoon.reflect.code.CtExpression<java.lang.Boolean>) (child)));
                    return;
                }


        super.visitCtFor(forLoop);
    }

    private boolean isContainedInForInit() {
        if (!((jdtTreeBuilder.getContextBuilder().stack.peek().node) instanceof org.eclipse.jdt.internal.compiler.ast.ForStatement)) {
            return false;
        }
        final org.eclipse.jdt.internal.compiler.ast.ForStatement parent = ((org.eclipse.jdt.internal.compiler.ast.ForStatement) (jdtTreeBuilder.getContextBuilder().stack.peek().node));
        if ((parent.initializations) == null) {
            return false;
        }
        for (org.eclipse.jdt.internal.compiler.ast.Statement initialization : parent.initializations) {
            if ((initialization != null) && (initialization.equals(childJDT))) {
                return true;
            }
        }
        return false;
    }

    private boolean isContainedInForUpdate() {
        if (!((jdtTreeBuilder.getContextBuilder().stack.peek().node) instanceof org.eclipse.jdt.internal.compiler.ast.ForStatement)) {
            return false;
        }
        final org.eclipse.jdt.internal.compiler.ast.ForStatement parent = ((org.eclipse.jdt.internal.compiler.ast.ForStatement) (jdtTreeBuilder.getContextBuilder().stack.peek().node));
        if ((parent.increments) == null) {
            return false;
        }
        for (org.eclipse.jdt.internal.compiler.ast.Statement increment : parent.increments) {
            if ((increment != null) && (increment.equals(childJDT))) {
                return true;
            }
        }
        return false;
    }

    @java.lang.Override
    public void visitCtForEach(spoon.reflect.code.CtForEach foreach) {
        if (((foreach.getVariable()) == null) && ((child) instanceof spoon.reflect.declaration.CtVariable)) {
            foreach.setVariable(((spoon.reflect.code.CtLocalVariable<?>) (child)));
            return;
        }else
            if (((foreach.getExpression()) == null) && ((child) instanceof spoon.reflect.code.CtExpression)) {
                foreach.setExpression(((spoon.reflect.code.CtExpression<?>) (child)));
                return;
            }

        super.visitCtForEach(foreach);
    }

    @java.lang.Override
    public void visitCtWhile(spoon.reflect.code.CtWhile whileLoop) {
        if (((whileLoop.getLoopingExpression()) == null) && ((child) instanceof spoon.reflect.code.CtExpression)) {
            whileLoop.setLoopingExpression(((spoon.reflect.code.CtExpression<java.lang.Boolean>) (child)));
            return;
        }
        super.visitCtWhile(whileLoop);
    }

    @java.lang.Override
    public void visitCtIf(spoon.reflect.code.CtIf ifElement) {
        if (((ifElement.getCondition()) == null) && ((child) instanceof spoon.reflect.code.CtExpression)) {
            ifElement.setCondition(((spoon.reflect.code.CtExpression<java.lang.Boolean>) (child)));
            return;
        }else
            if ((child) instanceof spoon.reflect.code.CtStatement) {
                spoon.reflect.code.CtStatement child = ((spoon.reflect.code.CtStatement) (this.child));
                if (!((this.child) instanceof spoon.reflect.code.CtBlock)) {
                    child = jdtTreeBuilder.getFactory().Code().createCtBlock(child);
                    child.setImplicit(true);
                    child.setPosition(this.child.getPosition());
                }
                if ((ifElement.getThenStatement()) == null) {
                    ifElement.setThenStatement(child);
                    return;
                }else
                    if ((ifElement.getElseStatement()) == null) {
                        ifElement.setElseStatement(child);
                        return;
                    }

            }

        super.visitCtIf(ifElement);
    }

    @java.lang.Override
    public <T> void visitCtSuperAccess(spoon.reflect.code.CtSuperAccess<T> superAccess) {
        if ((child) instanceof spoon.reflect.code.CtTypeAccess<?>) {
            superAccess.setTarget(((spoon.reflect.code.CtTypeAccess<?>) (child)));
            return;
        }
        super.visitCtSuperAccess(superAccess);
    }

    @java.lang.Override
    public <T> void visitCtInvocation(spoon.reflect.code.CtInvocation<T> invocation) {
        if (((childJDT) instanceof org.eclipse.jdt.internal.compiler.ast.TypeReference) && ((child) instanceof spoon.reflect.code.CtTypeAccess)) {
            invocation.getExecutable().addActualTypeArgument(((spoon.reflect.code.CtTypeAccess) (child)).getAccessedType());
            return;
        }else
            if ((child) instanceof spoon.reflect.code.CtExpression) {
                if ((hasChildEqualsToReceiver(invocation)) || (hasChildEqualsToQualification(invocation))) {
                    if ((child) instanceof spoon.reflect.code.CtThisAccess) {
                        final spoon.reflect.reference.CtTypeReference<?> declaringType = invocation.getExecutable().getDeclaringType();
                        if (((declaringType != null) && (invocation.getExecutable().isStatic())) && (child.isImplicit())) {
                            invocation.setTarget(jdtTreeBuilder.getFactory().Code().createTypeAccess(declaringType, declaringType.isAnonymous()));
                        }else {
                            invocation.setTarget(((spoon.reflect.code.CtThisAccess<?>) (child)));
                        }
                    }else {
                        invocation.setTarget(((spoon.reflect.code.CtExpression<?>) (child)));
                    }
                }else {
                    invocation.addArgument(((spoon.reflect.code.CtExpression<?>) (child)));
                }
                return;
            }

        super.visitCtInvocation(invocation);
    }

    private <T> boolean hasChildEqualsToQualification(spoon.reflect.code.CtInvocation<T> ctInvocation) {
        if (!((jdtTreeBuilder.getContextBuilder().stack.peek().node) instanceof org.eclipse.jdt.internal.compiler.ast.ExplicitConstructorCall)) {
            return false;
        }
        final org.eclipse.jdt.internal.compiler.ast.ExplicitConstructorCall parent = ((org.eclipse.jdt.internal.compiler.ast.ExplicitConstructorCall) (jdtTreeBuilder.getContextBuilder().stack.peek().node));
        // qualification is equals to the jdt child.
        return (((parent.qualification) != null) && (getFinalExpressionFromCast(parent.qualification).equals(childJDT))) && // qualification not yet initialized.
        (!(child.equals(ctInvocation.getTarget())));
    }

    private <T> boolean hasChildEqualsToReceiver(spoon.reflect.code.CtInvocation<T> ctInvocation) {
        if (!((jdtTreeBuilder.getContextBuilder().stack.peek().node) instanceof org.eclipse.jdt.internal.compiler.ast.MessageSend)) {
            return false;
        }
        final org.eclipse.jdt.internal.compiler.ast.MessageSend parent = ((org.eclipse.jdt.internal.compiler.ast.MessageSend) (jdtTreeBuilder.getContextBuilder().stack.peek().node));
        // Receiver is equals to the jdt child.
        return (((parent.receiver) != null) && (getFinalExpressionFromCast(parent.receiver).equals(childJDT))) && // Receiver not yet initialized.
        (!(child.equals(ctInvocation.getTarget())));
    }

    private org.eclipse.jdt.internal.compiler.ast.Expression getFinalExpressionFromCast(org.eclipse.jdt.internal.compiler.ast.Expression potentialCase) {
        if (!(potentialCase instanceof org.eclipse.jdt.internal.compiler.ast.CastExpression)) {
            return potentialCase;
        }
        return getFinalExpressionFromCast(((org.eclipse.jdt.internal.compiler.ast.CastExpression) (potentialCase)).expression);
    }

    @java.lang.Override
    public <T> void visitCtNewArray(spoon.reflect.code.CtNewArray<T> newArray) {
        if (((childJDT) instanceof org.eclipse.jdt.internal.compiler.ast.TypeReference) && ((child) instanceof spoon.reflect.code.CtTypeAccess)) {
            final org.eclipse.jdt.internal.compiler.ast.ArrayAllocationExpression arrayAlloc = ((org.eclipse.jdt.internal.compiler.ast.ArrayAllocationExpression) (jdtTreeBuilder.getContextBuilder().stack.peek().node));
            newArray.setType(((spoon.reflect.reference.CtArrayTypeReference) (jdtTreeBuilder.getFactory().Type().createArrayReference(((spoon.reflect.code.CtTypeAccess) (child)).getAccessedType(), arrayAlloc.dimensions.length))));
        }else
            if ((child) instanceof spoon.reflect.code.CtExpression) {
                if (isContainedInDimensionExpression()) {
                    newArray.addDimensionExpression(((spoon.reflect.code.CtExpression<java.lang.Integer>) (child)));
                }else
                    if ((((child) instanceof spoon.reflect.code.CtNewArray) && ((childJDT) instanceof org.eclipse.jdt.internal.compiler.ast.ArrayInitializer)) && ((jdtTreeBuilder.getContextBuilder().stack.peek().node) instanceof org.eclipse.jdt.internal.compiler.ast.ArrayAllocationExpression)) {
                        newArray.setElements(((spoon.reflect.code.CtNewArray) (child)).getElements());
                    }else {
                        newArray.addElement(((spoon.reflect.code.CtExpression) (child)));
                    }

            }

    }

    private boolean isContainedInDimensionExpression() {
        if (!((jdtTreeBuilder.getContextBuilder().stack.peek().node) instanceof org.eclipse.jdt.internal.compiler.ast.ArrayAllocationExpression)) {
            return false;
        }
        final org.eclipse.jdt.internal.compiler.ast.ArrayAllocationExpression parent = ((org.eclipse.jdt.internal.compiler.ast.ArrayAllocationExpression) (jdtTreeBuilder.getContextBuilder().stack.peek().node));
        if ((parent.dimensions) == null) {
            return false;
        }
        for (org.eclipse.jdt.internal.compiler.ast.Expression dimension : parent.dimensions) {
            if ((dimension != null) && (getFinalExpressionFromCast(dimension).equals(childJDT))) {
                return true;
            }
        }
        return false;
    }

    @java.lang.Override
    public <T> void visitCtConstructorCall(spoon.reflect.code.CtConstructorCall<T> ctConstructorCall) {
        if ((child) instanceof spoon.reflect.code.CtTypeAccess) {
            if (hasChildEqualsToType(ctConstructorCall)) {
                ctConstructorCall.getExecutable().setType(((spoon.reflect.code.CtTypeAccess) (child)).getAccessedType());
            }else {
                ctConstructorCall.addActualTypeArgument(((spoon.reflect.code.CtTypeAccess) (child)).getAccessedType());
            }
            return;
        }else
            if ((child) instanceof spoon.reflect.code.CtExpression) {
                if (hasChildEqualsToEnclosingInstance(ctConstructorCall)) {
                    ctConstructorCall.setTarget(((spoon.reflect.code.CtExpression<?>) (child)));
                }else {
                    ctConstructorCall.addArgument(((spoon.reflect.code.CtExpression<?>) (child)));
                }
                return;
            }

        super.visitCtConstructorCall(ctConstructorCall);
    }

    private <T> boolean hasChildEqualsToEnclosingInstance(spoon.reflect.code.CtConstructorCall<T> ctConstructorCall) {
        if (!((jdtTreeBuilder.getContextBuilder().stack.peek().node) instanceof org.eclipse.jdt.internal.compiler.ast.QualifiedAllocationExpression)) {
            return false;
        }
        final org.eclipse.jdt.internal.compiler.ast.QualifiedAllocationExpression parent = ((org.eclipse.jdt.internal.compiler.ast.QualifiedAllocationExpression) (jdtTreeBuilder.getContextBuilder().stack.peek().node));
        // Enclosing instance is equals to the jdt child.
        return (((parent.enclosingInstance) != null) && (getFinalExpressionFromCast(parent.enclosingInstance).equals(childJDT))) && // Enclosing instance not yet initialized.
        (!(child.equals(ctConstructorCall.getTarget())));
    }

    private <T> boolean hasChildEqualsToType(spoon.reflect.code.CtConstructorCall<T> ctConstructorCall) {
        final org.eclipse.jdt.internal.compiler.ast.AllocationExpression parent = ((org.eclipse.jdt.internal.compiler.ast.AllocationExpression) (jdtTreeBuilder.getContextBuilder().stack.peek().node));
        // Type is equals to the jdt child.
        return ((parent.type) != null) && (parent.type.equals(childJDT));
    }

    @java.lang.Override
    public <T> void visitCtNewClass(spoon.reflect.code.CtNewClass<T> newClass) {
        if ((child) instanceof spoon.reflect.declaration.CtClass) {
            newClass.setAnonymousClass(((spoon.reflect.declaration.CtClass<?>) (child)));
            final org.eclipse.jdt.internal.compiler.ast.QualifiedAllocationExpression node = ((org.eclipse.jdt.internal.compiler.ast.QualifiedAllocationExpression) (jdtTreeBuilder.getContextBuilder().stack.peek().node));
            final org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding[] referenceBindings = ((node.resolvedType) == null) ? null : node.resolvedType.superInterfaces();
            if ((referenceBindings != null) && ((referenceBindings.length) > 0)) {
                ((spoon.reflect.declaration.CtClass<?>) (child)).addSuperInterface(newClass.getType().clone());
            }else
                if ((newClass.getType()) != null) {
                    ((spoon.reflect.declaration.CtClass<?>) (child)).setSuperclass(newClass.getType().clone());
                }

            return;
        }
        super.visitCtNewClass(newClass);
    }

    @java.lang.Override
    public <T> void visitCtLambda(spoon.reflect.code.CtLambda<T> lambda) {
        if ((child) instanceof spoon.reflect.declaration.CtParameter) {
            lambda.addParameter(((spoon.reflect.declaration.CtParameter<?>) (child)));
            return;
        }else
            if ((child) instanceof spoon.reflect.code.CtBlock) {
                lambda.setBody(((spoon.reflect.code.CtBlock) (child)));
                return;
            }else
                if ((child) instanceof spoon.reflect.code.CtExpression) {
                    lambda.setExpression(((spoon.reflect.code.CtExpression<T>) (child)));
                }


        super.visitCtLambda(lambda);
    }

    @java.lang.Override
    public <T, E extends spoon.reflect.code.CtExpression<?>> void visitCtExecutableReferenceExpression(spoon.reflect.code.CtExecutableReferenceExpression<T, E> expression) {
        if ((child) instanceof spoon.reflect.code.CtExpression) {
            expression.setTarget(((E) (child)));
        }
        super.visitCtExecutableReferenceExpression(expression);
    }

    @java.lang.Override
    public void visitCtPackage(spoon.reflect.declaration.CtPackage ctPackage) {
        if ((child) instanceof spoon.reflect.declaration.CtType) {
            if (ctPackage.getTypes().contains(child)) {
                ctPackage.removeType(((spoon.reflect.declaration.CtType<?>) (child)));
            }
            ctPackage.addType(((spoon.reflect.declaration.CtType<?>) (child)));
            if ((child.getPosition().getCompilationUnit()) != null) {
                spoon.reflect.cu.CompilationUnit cu = child.getPosition().getCompilationUnit();
                java.util.List<spoon.reflect.declaration.CtType<?>> declaredTypes = new java.util.ArrayList<>(cu.getDeclaredTypes());
                declaredTypes.add(((spoon.reflect.declaration.CtType<?>) (child)));
                cu.setDeclaredTypes(declaredTypes);
            }
            return;
        }
        super.visitCtPackage(ctPackage);
    }

    @java.lang.Override
    public <R> void visitCtReturn(spoon.reflect.code.CtReturn<R> returnStatement) {
        if ((child) instanceof spoon.reflect.code.CtExpression) {
            returnStatement.setReturnedExpression(((spoon.reflect.code.CtExpression<R>) (child)));
            return;
        }
        super.visitCtReturn(returnStatement);
    }

    @java.lang.Override
    public <E> void visitCtSwitch(spoon.reflect.code.CtSwitch<E> switchStatement) {
        if (((switchStatement.getSelector()) == null) && ((child) instanceof spoon.reflect.code.CtExpression)) {
            switchStatement.setSelector(((spoon.reflect.code.CtExpression<E>) (child)));
            return;
        }
        if ((child) instanceof spoon.reflect.code.CtCase) {
            switchStatement.addCase(((spoon.reflect.code.CtCase<E>) (child)));
            return;
        }
        super.visitCtSwitch(switchStatement);
    }

    @java.lang.Override
    public void visitCtSynchronized(spoon.reflect.code.CtSynchronized synchro) {
        if (((synchro.getExpression()) == null) && ((child) instanceof spoon.reflect.code.CtExpression)) {
            synchro.setExpression(((spoon.reflect.code.CtExpression<?>) (child)));
            return;
        }
        if (((synchro.getBlock()) == null) && ((child) instanceof spoon.reflect.code.CtBlock)) {
            synchro.setBlock(((spoon.reflect.code.CtBlock<?>) (child)));
            return;
        }
        super.visitCtSynchronized(synchro);
    }

    @java.lang.Override
    public void visitCtThrow(spoon.reflect.code.CtThrow throwStatement) {
        if (((throwStatement.getThrownExpression()) == null) && ((child) instanceof spoon.reflect.code.CtExpression)) {
            throwStatement.setThrownExpression(((spoon.reflect.code.CtExpression<? extends java.lang.Throwable>) (child)));
            return;
        }
        super.visitCtThrow(throwStatement);
    }

    @java.lang.Override
    public void visitCtTry(spoon.reflect.code.CtTry tryBlock) {
        if ((child) instanceof spoon.reflect.code.CtBlock) {
            final spoon.reflect.code.CtBlock<?> childBlock = ((spoon.reflect.code.CtBlock<?>) (this.child));
            if (((tryBlock.getCatchers().size()) > 0) && ((tryBlock.getCatchers().get(((tryBlock.getCatchers().size()) - 1)).getBody()) == null)) {
                tryBlock.getCatchers().get(((tryBlock.getCatchers().size()) - 1)).setBody(childBlock);
            }else
                if (((tryBlock.getBody()) != null) && ((tryBlock.getFinalizer()) == null)) {
                    tryBlock.setFinalizer(childBlock);
                }else {
                    tryBlock.setBody(childBlock);
                }

            return;
        }else
            if ((child) instanceof spoon.reflect.code.CtCatch) {
                tryBlock.addCatcher(((spoon.reflect.code.CtCatch) (child)));
                return;
            }

        super.visitCtTry(tryBlock);
    }

    @java.lang.Override
    public void visitCtTryWithResource(spoon.reflect.code.CtTryWithResource tryWithResource) {
        if ((child) instanceof spoon.reflect.code.CtLocalVariable) {
            tryWithResource.addResource(((spoon.reflect.code.CtLocalVariable<?>) (child)));
        }
        super.visitCtTryWithResource(tryWithResource);
    }

    @java.lang.Override
    public <T> void visitCtUnaryOperator(spoon.reflect.code.CtUnaryOperator<T> operator) {
        if (((operator.getOperand()) == null) && ((child) instanceof spoon.reflect.code.CtExpression)) {
            operator.setOperand(((spoon.reflect.code.CtExpression<T>) (child)));
            return;
        }
        super.visitCtUnaryOperator(operator);
    }

    @java.lang.Override
    public void visitCtTypeParameterReference(spoon.reflect.reference.CtTypeParameterReference e) {
        if (((childJDT) instanceof org.eclipse.jdt.internal.compiler.ast.TypeReference) && ((child) instanceof spoon.reflect.code.CtTypeAccess)) {
            e.addBound(((spoon.reflect.code.CtTypeAccess) (child)).getAccessedType());
        }
        super.visitCtTypeParameterReference(e);
    }
}

