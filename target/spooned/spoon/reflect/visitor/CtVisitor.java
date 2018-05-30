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
 * This interface defines the visitor for the Spoon metamodel, as defined in
 * {@link spoon.reflect.declaration}, {@link spoon.reflect.code}, and
 * {@link spoon.reflect.reference}. It declares a visit method for each
 * element of the AST.
 */
public interface CtVisitor {
    /**
     * Visits an annotation.
     */
    <A extends java.lang.annotation.Annotation> void visitCtAnnotation(spoon.reflect.declaration.CtAnnotation<A> annotation);

    /**
     * Visits a code snippet expression.
     */
    <T> void visitCtCodeSnippetExpression(spoon.reflect.code.CtCodeSnippetExpression<T> expression);

    /**
     * Visits a code snippet statement.
     */
    void visitCtCodeSnippetStatement(spoon.reflect.code.CtCodeSnippetStatement statement);

    /**
     * Visits an annotation type declaration.
     */
    <A extends java.lang.annotation.Annotation> void visitCtAnnotationType(spoon.reflect.declaration.CtAnnotationType<A> annotationType);

    /**
     * Visits an anonymous executable.
     */
    void visitCtAnonymousExecutable(spoon.reflect.declaration.CtAnonymousExecutable anonymousExec);

    /**
     * Visits an array read access.
     */
    <T> void visitCtArrayRead(spoon.reflect.code.CtArrayRead<T> arrayRead);

    /**
     * Visits an array write access.
     */
    <T> void visitCtArrayWrite(spoon.reflect.code.CtArrayWrite<T> arrayWrite);

    /**
     * Visits a reference to an array type.
     */
    <T> void visitCtArrayTypeReference(spoon.reflect.reference.CtArrayTypeReference<T> reference);

    /**
     * Visits an assert.
     */
    <T> void visitCtAssert(spoon.reflect.code.CtAssert<T> asserted);

    /**
     * Visits an assignment.
     */
    <T, A extends T> void visitCtAssignment(spoon.reflect.code.CtAssignment<T, A> assignement);

    /**
     * Visits a binary operator.
     */
    <T> void visitCtBinaryOperator(spoon.reflect.code.CtBinaryOperator<T> operator);

    /**
     * Visits a block of code.
     */
    <R> void visitCtBlock(spoon.reflect.code.CtBlock<R> block);

    /**
     * Visits a <code>break</code> statement.
     */
    void visitCtBreak(spoon.reflect.code.CtBreak breakStatement);

    /**
     * Visits a <code>case</code> clause.
     */
    <S> void visitCtCase(spoon.reflect.code.CtCase<S> caseStatement);

    /**
     * Visits a <code>catch</code> clause.
     */
    void visitCtCatch(spoon.reflect.code.CtCatch catchBlock);

    /**
     * Visits a class declaration.
     */
    <T> void visitCtClass(spoon.reflect.declaration.CtClass<T> ctClass);

    /**
     * Visits a type parameter declaration.
     */
    void visitCtTypeParameter(spoon.reflect.declaration.CtTypeParameter typeParameter);

    /**
     * Visits a conditional expression
     */
    <T> void visitCtConditional(spoon.reflect.code.CtConditional<T> conditional);

    /**
     * Visits a constructor declaration.
     */
    <T> void visitCtConstructor(spoon.reflect.declaration.CtConstructor<T> c);

    /**
     * Visits a <code>continue</code> statement.
     */
    void visitCtContinue(spoon.reflect.code.CtContinue continueStatement);

    /**
     * Visits a <code>do</code> loop.
     */
    void visitCtDo(spoon.reflect.code.CtDo doLoop);

    /**
     * Visits an enumeration declaration.
     */
    <T extends java.lang.Enum<?>> void visitCtEnum(spoon.reflect.declaration.CtEnum<T> ctEnum);

    /**
     * Visits a reference to an executable.
     */
    <T> void visitCtExecutableReference(spoon.reflect.reference.CtExecutableReference<T> reference);

    /**
     * Visits a field declaration.
     */
    <T> void visitCtField(spoon.reflect.declaration.CtField<T> f);

    /**
     * Visits an enum value declaration.
     */
    <T> void visitCtEnumValue(spoon.reflect.declaration.CtEnumValue<T> enumValue);

    /**
     * Visits a this access.
     */
    <T> void visitCtThisAccess(spoon.reflect.code.CtThisAccess<T> thisAccess);

    /**
     * Visits a reference to a field.
     */
    <T> void visitCtFieldReference(spoon.reflect.reference.CtFieldReference<T> reference);

    /**
     * Visits a reference to an unbound field
     */
    <T> void visitCtUnboundVariableReference(spoon.reflect.reference.CtUnboundVariableReference<T> reference);

    /**
     * Visits a <code>for</code> loop.
     */
    void visitCtFor(spoon.reflect.code.CtFor forLoop);

    /**
     * Visits an enhanced <code>for</code> loop.
     */
    void visitCtForEach(spoon.reflect.code.CtForEach foreach);

    /**
     * Visits an <code>if</code> statement.
     */
    void visitCtIf(spoon.reflect.code.CtIf ifElement);

    /**
     * Visits an interface declaration.
     */
    <T> void visitCtInterface(spoon.reflect.declaration.CtInterface<T> intrface);

    /**
     * Visits an executable invocation.
     */
    <T> void visitCtInvocation(spoon.reflect.code.CtInvocation<T> invocation);

    /**
     * Visits a literal expression.
     */
    <T> void visitCtLiteral(spoon.reflect.code.CtLiteral<T> literal);

    /**
     * Visits a local variable declaration.
     */
    <T> void visitCtLocalVariable(spoon.reflect.code.CtLocalVariable<T> localVariable);

    /**
     * Visits a reference to a local variable.
     */
    <T> void visitCtLocalVariableReference(spoon.reflect.reference.CtLocalVariableReference<T> reference);

    /**
     * Visits a catch variable declaration.
     */
    <T> void visitCtCatchVariable(spoon.reflect.code.CtCatchVariable<T> catchVariable);

    /**
     * Visits a reference to a catch variable.
     */
    <T> void visitCtCatchVariableReference(spoon.reflect.reference.CtCatchVariableReference<T> reference);

    /**
     * Visits a method declaration.
     */
    <T> void visitCtMethod(spoon.reflect.declaration.CtMethod<T> m);

    /**
     * Visits an annotation method declaration.
     */
    <T> void visitCtAnnotationMethod(spoon.reflect.declaration.CtAnnotationMethod<T> annotationMethod);

    /**
     * Visits an array construction.
     */
    <T> void visitCtNewArray(spoon.reflect.code.CtNewArray<T> newArray);

    /**
     * Visits a call to a constructor.
     */
    <T> void visitCtConstructorCall(spoon.reflect.code.CtConstructorCall<T> ctConstructorCall);

    /**
     * Visits an anonymous class construction.
     */
    <T> void visitCtNewClass(spoon.reflect.code.CtNewClass<T> newClass);

    /**
     * Visits an anonymous method construction.
     */
    <T> void visitCtLambda(spoon.reflect.code.CtLambda<T> lambda);

    /**
     * Visits a reference to an executable.
     */
    <T, E extends spoon.reflect.code.CtExpression<?>> void visitCtExecutableReferenceExpression(spoon.reflect.code.CtExecutableReferenceExpression<T, E> expression);

    /**
     * Visits an operator assignment.
     */
    <T, A extends T> void visitCtOperatorAssignment(spoon.reflect.code.CtOperatorAssignment<T, A> assignment);

    /**
     * Visits a package declaration.
     */
    void visitCtPackage(spoon.reflect.declaration.CtPackage ctPackage);

    /**
     * Visits a reference to a package.
     */
    void visitCtPackageReference(spoon.reflect.reference.CtPackageReference reference);

    /**
     * Visits a parameter declaration.
     */
    <T> void visitCtParameter(spoon.reflect.declaration.CtParameter<T> parameter);

    /**
     * Visits a reference to a parameter.
     */
    <T> void visitCtParameterReference(spoon.reflect.reference.CtParameterReference<T> reference);

    /**
     * Visits a <code>return</code> statement.
     */
    <R> void visitCtReturn(spoon.reflect.code.CtReturn<R> returnStatement);

    /**
     * Visits a statement list.
     */
    <R> void visitCtStatementList(spoon.reflect.code.CtStatementList statements);

    /**
     * Visits a <code>switch</code> statement.
     */
    <S> void visitCtSwitch(spoon.reflect.code.CtSwitch<S> switchStatement);

    /**
     * Visits a <code>synchronized</code> modifier.
     */
    void visitCtSynchronized(spoon.reflect.code.CtSynchronized synchro);

    /**
     * Visits a <code>throw</code> statement.
     */
    void visitCtThrow(spoon.reflect.code.CtThrow throwStatement);

    /**
     * Visits a <code>try</code> statement.
     */
    void visitCtTry(spoon.reflect.code.CtTry tryBlock);

    /**
     * Visits a <code>try</code> with resource statement.
     */
    void visitCtTryWithResource(spoon.reflect.code.CtTryWithResource tryWithResource);

    /**
     * Visits a reference to a type parameter.
     */
    void visitCtTypeParameterReference(spoon.reflect.reference.CtTypeParameterReference ref);

    /**
     * Visits a reference to a wildcard.
     */
    void visitCtWildcardReference(spoon.reflect.reference.CtWildcardReference wildcardReference);

    /**
     * Visits an intersection type.
     */
    <T> void visitCtIntersectionTypeReference(spoon.reflect.reference.CtIntersectionTypeReference<T> reference);

    /**
     * Visits a reference to a type.
     */
    <T> void visitCtTypeReference(spoon.reflect.reference.CtTypeReference<T> reference);

    /**
     * Visits a type access.
     */
    <T> void visitCtTypeAccess(spoon.reflect.code.CtTypeAccess<T> typeAccess);

    /**
     * Visits a unary operator.
     */
    <T> void visitCtUnaryOperator(spoon.reflect.code.CtUnaryOperator<T> operator);

    /**
     * Visits a variable read access.
     */
    <T> void visitCtVariableRead(spoon.reflect.code.CtVariableRead<T> variableRead);

    /**
     * Visits a variable write access.
     */
    <T> void visitCtVariableWrite(spoon.reflect.code.CtVariableWrite<T> variableWrite);

    /**
     * Visits a <code>while</code> loop.
     */
    void visitCtWhile(spoon.reflect.code.CtWhile whileLoop);

    /**
     * Visits a field of an annotation.
     */
    <T> void visitCtAnnotationFieldAccess(spoon.reflect.code.CtAnnotationFieldAccess<T> annotationFieldAccess);

    /**
     * Visits a field read access.
     */
    <T> void visitCtFieldRead(spoon.reflect.code.CtFieldRead<T> fieldRead);

    /**
     * Visits a field write access.
     */
    <T> void visitCtFieldWrite(spoon.reflect.code.CtFieldWrite<T> fieldWrite);

    /**
     * Visits an access to a super invocation.
     */
    <T> void visitCtSuperAccess(spoon.reflect.code.CtSuperAccess<T> f);

    /**
     * Visits a comment
     */
    void visitCtComment(spoon.reflect.code.CtComment comment);

    /**
     * Visits a javadoc comment
     */
    void visitCtJavaDoc(spoon.reflect.code.CtJavaDoc comment);

    /**
     * Visits a javadoc tag
     */
    void visitCtJavaDocTag(spoon.reflect.code.CtJavaDocTag docTag);

    /**
     * Visits an import declaration
     */
    void visitCtImport(spoon.reflect.declaration.CtImport ctImport);

    /**
     * Visits a module declaration
     */
    void visitCtModule(spoon.reflect.declaration.CtModule module);

    /**
     * Visits a module reference
     */
    void visitCtModuleReference(spoon.reflect.reference.CtModuleReference moduleReference);

    /**
     * Visits a package export in a module declaration.
     */
    void visitCtPackageExport(spoon.reflect.declaration.CtPackageExport moduleExport);

    /**
     * Visits a "require" directive in a module declaration.
     */
    void visitCtModuleRequirement(spoon.reflect.declaration.CtModuleRequirement moduleRequirement);

    /**
     * Visits a "provides" directive in a module declaration.
     */
    void visitCtProvidedService(spoon.reflect.declaration.CtProvidedService moduleProvidedService);

    /**
     * Visits a "uses" directive in a module declaration.
     */
    void visitCtUsedService(spoon.reflect.declaration.CtUsedService usedService);
}

