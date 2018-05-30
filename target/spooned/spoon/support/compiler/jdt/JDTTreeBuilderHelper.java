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


public class JDTTreeBuilderHelper {
    private final spoon.support.compiler.jdt.JDTTreeBuilder jdtTreeBuilder;

    JDTTreeBuilderHelper(spoon.support.compiler.jdt.JDTTreeBuilder jdtTreeBuilder) {
        this.jdtTreeBuilder = jdtTreeBuilder;
    }

    /**
     * Computes the anonymous simple name from its fully qualified type name.
     *
     * @param anonymousQualifiedName
     * 		Qualified name which contains the anonymous name.
     * @return Anonymous simple name.
     */
    static java.lang.String computeAnonymousName(char[] anonymousQualifiedName) {
        final java.lang.String poolName = org.eclipse.jdt.core.compiler.CharOperation.charToString(anonymousQualifiedName);
        return poolName.substring(((poolName.lastIndexOf(spoon.reflect.declaration.CtType.INNERTTYPE_SEPARATOR)) + 1), poolName.length());
    }

    /**
     * Creates a qualified type name from a two-dimensional array.
     *
     * @param typeName
     * 		two-dimensional array which represents the qualified name expected.
     * @return Qualified name.
     */
    static java.lang.String createQualifiedTypeName(char[][] typeName) {
        java.lang.String s = "";
        for (int i = 0; i < ((typeName.length) - 1); i++) {
            s += (org.eclipse.jdt.core.compiler.CharOperation.charToString(typeName[i])) + ".";
        }
        s += org.eclipse.jdt.core.compiler.CharOperation.charToString(typeName[((typeName.length) - 1)]);
        return s;
    }

    /**
     * Creates a catch variable from a type reference.
     *
     * @param typeReference
     * 		Correspond to the exception type declared in the catch.
     * @return a catch variable.
     */
    spoon.reflect.code.CtCatchVariable<java.lang.Throwable> createCatchVariable(org.eclipse.jdt.internal.compiler.ast.TypeReference typeReference) {
        final org.eclipse.jdt.internal.compiler.ast.Argument jdtCatch = ((org.eclipse.jdt.internal.compiler.ast.Argument) (jdtTreeBuilder.getContextBuilder().stack.peekFirst().node));
        final java.util.Set<spoon.support.reflect.CtExtendedModifier> modifiers = spoon.support.compiler.jdt.JDTTreeBuilderQuery.getModifiers(jdtCatch.modifiers, false, false);
        spoon.reflect.code.CtCatchVariable<java.lang.Throwable> result = jdtTreeBuilder.getFactory().Core().createCatchVariable();
        result.<spoon.reflect.code.CtCatchVariable>setSimpleName(org.eclipse.jdt.core.compiler.CharOperation.charToString(jdtCatch.name)).setExtendedModifiers(modifiers);
        if (typeReference instanceof org.eclipse.jdt.internal.compiler.ast.UnionTypeReference) {
            // do not set type of variable yet. It will be initialized later by visit of multiple types. Each call then ADDs one type
            return result;
        }else {
            spoon.reflect.reference.CtTypeReference ctTypeReference = jdtTreeBuilder.getReferencesBuilder().<java.lang.Throwable>getTypeReference(typeReference.resolvedType);
            return result.<spoon.reflect.code.CtCatchVariable>setType(ctTypeReference);
        }
    }

    /**
     * Creates variable access from a {@link CtVariableReference}. Think to move this method
     * in the {@link spoon.reflect.factory.CodeFactory} if you think that is a good idea.
     */
    public <T> spoon.reflect.code.CtVariableAccess<T> createVariableAccess(spoon.reflect.reference.CtVariableReference<T> variableReference, boolean isReadAccess) {
        spoon.reflect.code.CtVariableAccess<T> variableAccess;
        if (isReadAccess) {
            variableAccess = jdtTreeBuilder.getFactory().Core().createVariableWrite();
        }else {
            variableAccess = jdtTreeBuilder.getFactory().Core().createVariableRead();
        }
        return variableAccess.setVariable(variableReference);
    }

    /**
     * Creates a variable access from its single name.
     *
     * @param singleNameReference
     * 		Used to build a variable reference which will be contained in the variable access.
     * @return a variable access.
     */
    <T> spoon.reflect.code.CtVariableAccess<T> createVariableAccess(org.eclipse.jdt.internal.compiler.ast.SingleNameReference singleNameReference) {
        spoon.reflect.code.CtVariableAccess<T> va;
        if (spoon.support.compiler.jdt.JDTTreeBuilderQuery.isLhsAssignment(jdtTreeBuilder.getContextBuilder(), singleNameReference)) {
            va = jdtTreeBuilder.getFactory().Core().createVariableWrite();
        }else {
            va = jdtTreeBuilder.getFactory().Core().createVariableRead();
        }
        va.setVariable(jdtTreeBuilder.getReferencesBuilder().<T>getVariableReference(((org.eclipse.jdt.internal.compiler.lookup.VariableBinding) (singleNameReference.binding))));
        return va;
    }

    /**
     * Analyzes if {@code singleNameReference} points to a {@link CtVariable} visible in current
     * scope and, if existent, returns its corresponding {@link CtVariableAccess}. Returns
     * {@code null} if {@code singleNameReference} could not be resolved as variable access. Since
     * we are in noclasspath mode this function may also returns {@code null} if
     * {@code singleNameReference} points to a variable declared by an unknown class.
     *
     * @param singleNameReference
     * 		The potential variable access.
     * @return A {@link CtVariableAccess} if {@code singleNameReference} points to a variable
     * visible in current scope, {@code null} otherwise.
     */
    <T> spoon.reflect.code.CtVariableAccess<T> createVariableAccessNoClasspath(org.eclipse.jdt.internal.compiler.ast.SingleNameReference singleNameReference) {
        final spoon.reflect.factory.TypeFactory typeFactory = jdtTreeBuilder.getFactory().Type();
        final spoon.reflect.factory.CoreFactory coreFactory = jdtTreeBuilder.getFactory().Core();
        final spoon.reflect.factory.ExecutableFactory executableFactory = jdtTreeBuilder.getFactory().Executable();
        final spoon.support.compiler.jdt.ContextBuilder contextBuilder = jdtTreeBuilder.getContextBuilder();
        final spoon.support.compiler.jdt.ReferenceBuilder referenceBuilder = jdtTreeBuilder.getReferencesBuilder();
        final spoon.support.compiler.jdt.PositionBuilder positionBuilder = jdtTreeBuilder.getPositionBuilder();
        final java.lang.String name = org.eclipse.jdt.core.compiler.CharOperation.charToString(singleNameReference.token);
        final spoon.reflect.declaration.CtVariable<T> variable = contextBuilder.getVariableDeclaration(name);
        if (variable == null) {
            return null;
        }
        final spoon.reflect.reference.CtVariableReference<T> variableReference;
        final spoon.reflect.code.CtVariableAccess<T> variableAccess;
        // CtLocalVariable, CtCatchVariable, ...
        if (variable instanceof spoon.reflect.declaration.CtParameter) {
            // create variable of concrete type to avoid type casting while calling methods
            final spoon.reflect.reference.CtParameterReference<T> parameterReference = coreFactory.createParameterReference();
            if ((variable.getParent()) instanceof spoon.reflect.code.CtLambda) {
                // nothing
            }else {
                // Unfortunately, we can not use `variable.getReference()` here as some parent
                // references (in terms of Java objects) have not been set up yet. Thus, we need to
                // create the required parameter reference by our own.
                // Since the given parameter has not been declared in a lambda expression it must
                // have been declared by a method/constructor.
                final spoon.reflect.declaration.CtExecutable executable = ((spoon.reflect.declaration.CtExecutable) (variable.getParent()));
                // create list of executable's parameter types
                final java.util.List<spoon.reflect.reference.CtTypeReference<?>> parameterTypesOfExecutable = new java.util.ArrayList<>();
                @java.lang.SuppressWarnings("unchecked")
                final java.util.List<spoon.reflect.declaration.CtParameter<?>> parametersOfExecutable = executable.getParameters();
                for (spoon.reflect.declaration.CtParameter<?> parameter : parametersOfExecutable) {
                    parameterTypesOfExecutable.add(((parameter.getType()) != null ? parameter.getType().clone() : // it's the best match :(
                    typeFactory.OBJECT.clone()));
                }
                // find executable's corresponding jdt element
                org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration executableJDT = null;
                for (final spoon.support.compiler.jdt.ASTPair astPair : contextBuilder.stack) {
                    if ((astPair.element) == executable) {
                        executableJDT = ((org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration) (astPair.node));
                    }
                }
                assert executableJDT != null;
                // create a reference to executable's declaring class
                // `binding` may be null for anonymous classes which means we have to
                // create an 'empty' type reference since we have no further information
                // available
                final spoon.reflect.reference.CtTypeReference declaringReferenceOfExecutable = ((executableJDT.binding) == null) ? coreFactory.createTypeReference() : referenceBuilder.getTypeReference(executableJDT.binding.declaringClass);
                // If executable is a constructor, `executable.getType()` returns null since the
                // parent is not available yet. Fortunately, however, the return type of a
                // constructor is its declaring class which, in our case, is already available with
                // declaringReferenceOfExecutable.
                spoon.reflect.reference.CtTypeReference executableTypeReference = (executable instanceof spoon.reflect.declaration.CtConstructor) ? // IMPORTANT: Create a clone of the type reference (rt) if retrieved by
                // other AST elements as `executableFactory.createReference` (see below)
                // indirectly sets the parent of `rt` and, thus, may break the AST!
                declaringReferenceOfExecutable.clone() : executable.getType().clone();
            }
            variableReference = parameterReference;
            variableAccess = (spoon.support.compiler.jdt.JDTTreeBuilderQuery.isLhsAssignment(contextBuilder, singleNameReference)) ? coreFactory.<T>createVariableWrite() : coreFactory.<T>createVariableRead();
        }// CtLocalVariable, CtCatchVariable, ...
        else
            if (variable instanceof spoon.reflect.declaration.CtField) {
                variableReference = variable.getReference();
                variableAccess = (spoon.support.compiler.jdt.JDTTreeBuilderQuery.isLhsAssignment(contextBuilder, singleNameReference)) ? coreFactory.<T>createFieldWrite() : coreFactory.<T>createFieldRead();
            }else {
                variableReference = variable.getReference();
                variableAccess = (spoon.support.compiler.jdt.JDTTreeBuilderQuery.isLhsAssignment(contextBuilder, singleNameReference)) ? coreFactory.<T>createVariableWrite() : coreFactory.<T>createVariableRead();
            }

        variableReference.setSimpleName(name);
        variableReference.setPosition(positionBuilder.buildPosition(singleNameReference.sourceStart(), singleNameReference.sourceEnd()));
        variableAccess.setVariable(variableReference);
        return variableAccess;
    }

    /**
     * Creates a variable or a field access from its qualified name.
     *
     * @param qualifiedNameReference
     * 		Used to build the variable access. See all sub methods of this class to understand its usage.
     * @return a variable access.
     */
    <T> spoon.reflect.code.CtVariableAccess<T> createVariableAccess(org.eclipse.jdt.internal.compiler.ast.QualifiedNameReference qualifiedNameReference) {
        long[] positions = qualifiedNameReference.sourcePositions;
        int sourceStart = qualifiedNameReference.sourceStart();
        int sourceEnd = qualifiedNameReference.sourceEnd();
        if ((qualifiedNameReference.indexOfFirstFieldBinding) < (positions.length)) {
            sourceEnd = ((int) ((positions[qualifiedNameReference.indexOfFirstFieldBinding]) >>> 32)) - 2;
        }
        spoon.reflect.code.CtVariableAccess<T> va;
        spoon.reflect.reference.CtVariableReference<T> ref;
        boolean fromAssignment = spoon.support.compiler.jdt.JDTTreeBuilderQuery.isLhsAssignment(jdtTreeBuilder.getContextBuilder(), qualifiedNameReference);
        boolean isOtherBinding = ((qualifiedNameReference.otherBindings) == null) || ((qualifiedNameReference.otherBindings.length) == 0);
        if ((qualifiedNameReference.binding) instanceof org.eclipse.jdt.internal.compiler.lookup.FieldBinding) {
            ref = jdtTreeBuilder.getReferencesBuilder().getVariableReference(qualifiedNameReference.fieldBinding());
            ref.setPosition(jdtTreeBuilder.getPositionBuilder().buildPosition(sourceStart, sourceEnd));
            va = createFieldAccess(ref, createTargetFieldAccess(qualifiedNameReference, ((spoon.reflect.reference.CtFieldReference<java.lang.Object>) (ref))), (isOtherBinding && fromAssignment));
        }else {
            ref = jdtTreeBuilder.getReferencesBuilder().getVariableReference(((org.eclipse.jdt.internal.compiler.lookup.VariableBinding) (qualifiedNameReference.binding)));
            ref.setPosition(jdtTreeBuilder.getPositionBuilder().buildPosition(sourceStart, sourceEnd));
            va = createVariableAccess(ref, (isOtherBinding && fromAssignment));
        }
        ref.setPosition(jdtTreeBuilder.getPositionBuilder().buildPosition(sourceStart, sourceEnd));
        // 
        // set source position of va;
        if ((qualifiedNameReference.otherBindings) != null) {
            int i = 0;// positions index;

            va.setPosition(ref.getPosition());
            sourceStart = ((int) ((positions[((qualifiedNameReference.indexOfFirstFieldBinding) - 1)]) >>> 32));
            for (org.eclipse.jdt.internal.compiler.lookup.FieldBinding b : qualifiedNameReference.otherBindings) {
                isOtherBinding = (qualifiedNameReference.otherBindings.length) == (i + 1);
                spoon.reflect.code.CtFieldAccess<T> other = // 
                createFieldAccess(jdtTreeBuilder.getReferencesBuilder().<T>getVariableReference(b, qualifiedNameReference.tokens[(i + 1)]), va, (isOtherBinding && fromAssignment));
                // set source position of fa
                if ((i + (qualifiedNameReference.indexOfFirstFieldBinding)) >= (qualifiedNameReference.otherBindings.length)) {
                    sourceEnd = qualifiedNameReference.sourceEnd();
                }else {
                    sourceEnd = ((int) ((positions[(((qualifiedNameReference.indexOfFirstFieldBinding) + i) + 1)]) >>> 32)) - 2;
                }
                other.setPosition(jdtTreeBuilder.getPositionBuilder().buildPosition(sourceStart, sourceEnd));
                va = other;
                i++;
            }
        }// 
        // set source position of va;
        else
            if ((!((qualifiedNameReference.binding) instanceof org.eclipse.jdt.internal.compiler.lookup.FieldBinding)) && ((qualifiedNameReference.tokens.length) > 1)) {
                sourceStart = ((int) ((positions[0]) >>> 32));
                for (int i = 1; i < (qualifiedNameReference.tokens.length); i++) {
                    isOtherBinding = (qualifiedNameReference.tokens.length) == (i + 1);
                    spoon.reflect.code.CtFieldAccess<T> other = createFieldAccess(jdtTreeBuilder.getReferencesBuilder().<T>getVariableReference(null, qualifiedNameReference.tokens[i]), va, (isOtherBinding && fromAssignment));
                    sourceEnd = ((int) (positions[i]));
                    va.setPosition(jdtTreeBuilder.getPositionBuilder().buildPosition(sourceStart, sourceEnd));
                    va = other;
                }
            }

        va.setPosition(jdtTreeBuilder.getPositionBuilder().buildPosition(qualifiedNameReference.sourceStart(), qualifiedNameReference.sourceEnd()));
        return va;
    }

    /**
     * Creates variable access from a {@link CtVariableReference}. Think to move this method
     * in the {@link spoon.reflect.factory.CodeFactory} if you think that is a good idea.
     */
    public <T> spoon.reflect.code.CtFieldAccess<T> createFieldAccess(spoon.reflect.reference.CtVariableReference<T> variableReference, spoon.reflect.code.CtExpression<?> target, boolean isReadAccess) {
        spoon.reflect.code.CtFieldAccess<T> fieldAccess;
        if (isReadAccess) {
            fieldAccess = jdtTreeBuilder.getFactory().Core().createFieldWrite();
        }else {
            fieldAccess = jdtTreeBuilder.getFactory().Core().createFieldRead();
        }
        fieldAccess.setVariable(variableReference);
        fieldAccess.setTarget(target);
        return fieldAccess;
    }

    /**
     * Creates a field access from its single name.
     *
     * @param singleNameReference
     * 		Used to build a variable reference and a target which will be contained in the field access.
     * @return a field access.
     */
    <T> spoon.reflect.code.CtFieldAccess<T> createFieldAccess(org.eclipse.jdt.internal.compiler.ast.SingleNameReference singleNameReference) {
        spoon.reflect.code.CtFieldAccess<T> va;
        if (spoon.support.compiler.jdt.JDTTreeBuilderQuery.isLhsAssignment(jdtTreeBuilder.getContextBuilder(), singleNameReference)) {
            va = jdtTreeBuilder.getFactory().Core().createFieldWrite();
        }else {
            va = jdtTreeBuilder.getFactory().Core().createFieldRead();
        }
        va.setVariable(jdtTreeBuilder.getReferencesBuilder().<T>getVariableReference(singleNameReference.fieldBinding().original()));
        if ((va.getVariable()) != null) {
            final spoon.reflect.reference.CtFieldReference<T> ref = va.getVariable();
            if ((ref.isStatic()) && (!(ref.getDeclaringType().isAnonymous()))) {
                va.setTarget(jdtTreeBuilder.getFactory().Code().createTypeAccess(ref.getDeclaringType()));
            }else
                if (!(ref.isStatic())) {
                    va.setTarget(jdtTreeBuilder.getFactory().Code().createThisAccess(jdtTreeBuilder.getReferencesBuilder().getTypeReference(singleNameReference.actualReceiverType), true));
                }

        }
        return va;
    }

    /**
     * In no classpath mode, when we build a field access, we have a binding typed by ProblemBinding.
     * This binding doesn't contain all information but we can get some of them.
     *
     * @param singleNameReference
     * 		Used to get the problem binding of the field access and the name of the declaring type.
     * @return a field access.
     */
    <T> spoon.reflect.code.CtFieldAccess<T> createFieldAccessNoClasspath(org.eclipse.jdt.internal.compiler.ast.SingleNameReference singleNameReference) {
        spoon.reflect.code.CtFieldAccess<T> va;
        if (spoon.support.compiler.jdt.JDTTreeBuilderQuery.isLhsAssignment(jdtTreeBuilder.getContextBuilder(), singleNameReference)) {
            va = jdtTreeBuilder.getFactory().Core().createFieldWrite();
        }else {
            va = jdtTreeBuilder.getFactory().Core().createFieldRead();
        }
        va.setVariable(jdtTreeBuilder.getReferencesBuilder().<T>getVariableReference(((org.eclipse.jdt.internal.compiler.lookup.ProblemBinding) (singleNameReference.binding))));
        final spoon.reflect.reference.CtReference declaring = jdtTreeBuilder.getReferencesBuilder().getDeclaringReferenceFromImports(singleNameReference.token);
        if ((declaring instanceof spoon.reflect.reference.CtTypeReference) && ((va.getVariable()) != null)) {
            final spoon.reflect.reference.CtTypeReference<java.lang.Object> declaringRef = ((spoon.reflect.reference.CtTypeReference<java.lang.Object>) (declaring));
            va.setTarget(jdtTreeBuilder.getFactory().Code().createTypeAccess(declaringRef));
            va.getVariable().setDeclaringType(declaringRef);
            va.getVariable().setStatic(true);
        }
        return va;
    }

    /**
     * In no classpath mode, when we build a field access, we have a binding typed by ProblemBinding.
     * We try to get all information we can get from this binding.
     *
     * @param qualifiedNameReference
     * 		Used to get the problem binding of the field access and the name of the declaring type.
     * @return a field access.
     */
    <T> spoon.reflect.code.CtFieldAccess<T> createFieldAccessNoClasspath(org.eclipse.jdt.internal.compiler.ast.QualifiedNameReference qualifiedNameReference) {
        boolean fromAssignment = spoon.support.compiler.jdt.JDTTreeBuilderQuery.isLhsAssignment(jdtTreeBuilder.getContextBuilder(), qualifiedNameReference);
        spoon.reflect.code.CtFieldAccess<T> fieldAccess = createFieldAccess(jdtTreeBuilder.getReferencesBuilder().<T>getVariableReference(((org.eclipse.jdt.internal.compiler.lookup.ProblemBinding) (qualifiedNameReference.binding))), null, fromAssignment);
        // In no classpath mode and with qualified name, the type given by JDT is wrong...
        final char[][] declaringClass = org.eclipse.jdt.core.compiler.CharOperation.subarray(qualifiedNameReference.tokens, 0, ((qualifiedNameReference.tokens.length) - 1));
        final org.eclipse.jdt.internal.compiler.lookup.MissingTypeBinding declaringType = jdtTreeBuilder.getContextBuilder().compilationunitdeclaration.scope.environment.createMissingType(null, declaringClass);
        final spoon.reflect.reference.CtTypeReference<T> declaringRef = jdtTreeBuilder.getReferencesBuilder().getTypeReference(declaringType);
        fieldAccess.getVariable().setDeclaringType(declaringRef);
        fieldAccess.getVariable().setStatic(true);
        fieldAccess.setTarget(jdtTreeBuilder.getFactory().Code().createTypeAccess(declaringRef));
        // In no classpath mode and with qualified name, the binding don't have a good name.
        fieldAccess.getVariable().setSimpleName(spoon.support.compiler.jdt.JDTTreeBuilderHelper.createQualifiedTypeName(org.eclipse.jdt.core.compiler.CharOperation.subarray(qualifiedNameReference.tokens, ((qualifiedNameReference.tokens.length) - 1), qualifiedNameReference.tokens.length)));
        return fieldAccess;
    }

    /**
     * Creates a field access from a field reference.
     *
     * @param fieldReference
     * 		Used to build the spoon variable reference and the type of the field access.
     * @return a field access.
     */
    <T> spoon.reflect.code.CtFieldAccess<T> createFieldAccess(org.eclipse.jdt.internal.compiler.ast.FieldReference fieldReference) {
        spoon.reflect.code.CtFieldAccess<T> fieldAccess;
        if (spoon.support.compiler.jdt.JDTTreeBuilderQuery.isLhsAssignment(jdtTreeBuilder.getContextBuilder(), fieldReference)) {
            fieldAccess = jdtTreeBuilder.getFactory().Core().createFieldWrite();
        }else {
            fieldAccess = jdtTreeBuilder.getFactory().Core().createFieldRead();
        }
        fieldAccess.setVariable(jdtTreeBuilder.getReferencesBuilder().<T>getVariableReference(fieldReference.binding, fieldReference.token));
        fieldAccess.setType(jdtTreeBuilder.getReferencesBuilder().<T>getTypeReference(fieldReference.resolvedType));
        return fieldAccess;
    }

    /**
     * Creates a type access from its qualified name and with a field reference.
     *
     * @param qualifiedNameReference
     * 		Used to update the field reference if necessary.
     * @param fieldReference
     * 		Used to get its declaring class and to put it in the type access.
     * @return a type access.
     */
    spoon.reflect.code.CtTypeAccess<?> createTypeAccess(org.eclipse.jdt.internal.compiler.ast.QualifiedNameReference qualifiedNameReference, spoon.reflect.reference.CtFieldReference<?> fieldReference) {
        final org.eclipse.jdt.internal.compiler.lookup.TypeBinding receiverType = qualifiedNameReference.actualReceiverType;
        if (receiverType != null) {
            final spoon.reflect.reference.CtTypeReference<java.lang.Object> qualifiedRef = // 
            jdtTreeBuilder.getReferencesBuilder().getQualifiedTypeReference(qualifiedNameReference.tokens, receiverType, qualifiedNameReference.fieldBinding().declaringClass.enclosingType(), new spoon.support.compiler.jdt.JDTTreeBuilder.OnAccessListener() {
                @java.lang.Override
                public boolean onAccess(char[][] tokens, int index) {
                    return !(org.eclipse.jdt.core.compiler.CharOperation.equals(tokens[(index + 1)], tokens[((tokens.length) - 1)]));
                }
            });
            if (qualifiedRef != null) {
                fieldReference.setDeclaringType(qualifiedRef);
            }else {
                fieldReference.setDeclaringType(jdtTreeBuilder.getReferencesBuilder().getTypeReference(receiverType));
            }
        }
        spoon.reflect.code.CtTypeAccess<?> typeAccess = jdtTreeBuilder.getFactory().Code().createTypeAccess(fieldReference.getDeclaringType());
        if ((qualifiedNameReference.indexOfFirstFieldBinding) > 1) {
            // the array sourcePositions contains the position of each element of the qualifiedNameReference
            // the last element contains the position of the field
            long[] positions = qualifiedNameReference.sourcePositions;
            typeAccess.setPosition(jdtTreeBuilder.getPositionBuilder().buildPosition(qualifiedNameReference.sourceStart(), (((int) ((positions[((qualifiedNameReference.indexOfFirstFieldBinding) - 1)]) >>> 32)) - 2)));
        }else {
            typeAccess.setImplicit(qualifiedNameReference.isImplicitThis());
        }
        return typeAccess;
    }

    /**
     * Creates a type access from its qualified name.
     *
     * @param qualifiedNameReference
     * 		Used to get the declaring class of this type. This qualified type should have a type as target.
     * @return a type access.
     */
    <T> spoon.reflect.code.CtTypeAccess<T> createTypeAccessNoClasspath(org.eclipse.jdt.internal.compiler.ast.QualifiedNameReference qualifiedNameReference) {
        spoon.reflect.reference.CtTypeReference<T> typeReference;
        // TODO try to determine package/class boundary by upper case
        if ((qualifiedNameReference.binding) instanceof org.eclipse.jdt.internal.compiler.lookup.ProblemBinding) {
            typeReference = jdtTreeBuilder.getFactory().Type().<T>createReference(org.eclipse.jdt.core.compiler.CharOperation.toString(qualifiedNameReference.tokens));
        }// TODO try to determine package/class boundary by upper case
        else
            if ((qualifiedNameReference.binding) instanceof org.eclipse.jdt.internal.compiler.lookup.FieldBinding) {
                final org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding declaringClass = ((org.eclipse.jdt.internal.compiler.lookup.FieldBinding) (qualifiedNameReference.binding)).declaringClass;
                typeReference = jdtTreeBuilder.getReferencesBuilder().<T>getTypeReference(declaringClass);
            }else {
                char[][] packageName = org.eclipse.jdt.core.compiler.CharOperation.subarray(qualifiedNameReference.tokens, 0, ((qualifiedNameReference.tokens.length) - 1));
                char[][] className = org.eclipse.jdt.core.compiler.CharOperation.subarray(qualifiedNameReference.tokens, ((qualifiedNameReference.tokens.length) - 1), qualifiedNameReference.tokens.length);
                if ((packageName.length) > 0) {
                    try {
                        final org.eclipse.jdt.internal.compiler.lookup.PackageBinding aPackage = jdtTreeBuilder.getContextBuilder().compilationunitdeclaration.scope.environment.createPackage(packageName);
                        final org.eclipse.jdt.internal.compiler.lookup.MissingTypeBinding declaringType = jdtTreeBuilder.getContextBuilder().compilationunitdeclaration.scope.environment.createMissingType(aPackage, className);
                        typeReference = jdtTreeBuilder.getReferencesBuilder().getTypeReference(declaringType);
                    } catch (java.lang.NullPointerException e) {
                        typeReference = jdtTreeBuilder.getFactory().Type().createReference(qualifiedNameReference.toString());
                    }
                }else {
                    typeReference = jdtTreeBuilder.getFactory().Type().createReference(qualifiedNameReference.toString());
                }
            }

        final spoon.reflect.code.CtTypeAccess<T> typeAccess = jdtTreeBuilder.getFactory().Code().createTypeAccess(typeReference);
        int sourceStart = qualifiedNameReference.sourceStart();
        int sourceEnd = qualifiedNameReference.sourceEnd();
        typeAccess.setPosition(jdtTreeBuilder.getPositionBuilder().buildPosition(sourceStart, sourceEnd));
        return typeAccess;
    }

    /**
     * Creates a type access from its single name.
     *
     * @param singleNameReference
     * 		Used to get the simple name of the type.
     * @return a type access.
     */
    <T> spoon.reflect.code.CtTypeAccess<T> createTypeAccessNoClasspath(org.eclipse.jdt.internal.compiler.ast.SingleNameReference singleNameReference) {
        final spoon.reflect.reference.CtTypeReference<T> typeReference = jdtTreeBuilder.getFactory().Core().createTypeReference();
        if ((singleNameReference.binding) == null) {
            typeReference.setSimpleName(org.eclipse.jdt.core.compiler.CharOperation.charToString(singleNameReference.token));
        }else {
            typeReference.setSimpleName(org.eclipse.jdt.core.compiler.CharOperation.charToString(singleNameReference.binding.readableName()));
        }
        jdtTreeBuilder.getReferencesBuilder().setPackageOrDeclaringType(typeReference, jdtTreeBuilder.getReferencesBuilder().getDeclaringReferenceFromImports(singleNameReference.token));
        return jdtTreeBuilder.getFactory().Code().createTypeAccess(typeReference);
    }

    /**
     * Creates the target of a field access.
     *
     * @param qualifiedNameReference
     * 		Used to get the declaring class of the target.
     * @param ref
     * 		Used in a static context.
     * @return an expression.
     */
    spoon.reflect.code.CtExpression<?> createTargetFieldAccess(org.eclipse.jdt.internal.compiler.ast.QualifiedNameReference qualifiedNameReference, spoon.reflect.reference.CtFieldReference<java.lang.Object> ref) {
        spoon.reflect.code.CtExpression<?> target = null;
        if (spoon.support.compiler.jdt.JDTTreeBuilderQuery.isValidProblemBindingField(qualifiedNameReference)) {
            target = createTypeAccessNoClasspath(qualifiedNameReference);
        }else
            if (ref.isStatic()) {
                target = createTypeAccess(qualifiedNameReference, ref);
            }else
                if ((!(ref.isStatic())) && (!(ref.getDeclaringType().isAnonymous()))) {
                    target = jdtTreeBuilder.getFactory().Code().createThisAccess(jdtTreeBuilder.getReferencesBuilder().<java.lang.Object>getTypeReference(qualifiedNameReference.actualReceiverType), true);
                }


        return target;
    }

    /**
     * Creates a parameter. If the argument have a type == null, we get the type from its binding. A type == null is possible when
     * this type is implicit like in a lambda where you don't need to specify the type of parameters.
     *
     * @param argument
     * 		Used to get the name of the parameter, the modifiers, know if it is a var args parameter.
     * @return a parameter.
     */
    <T> spoon.reflect.declaration.CtParameter<T> createParameter(org.eclipse.jdt.internal.compiler.ast.Argument argument) {
        spoon.reflect.declaration.CtParameter<T> p = jdtTreeBuilder.getFactory().Core().createParameter();
        p.setSimpleName(org.eclipse.jdt.core.compiler.CharOperation.charToString(argument.name));
        p.setVarArgs(argument.isVarArgs());
        p.setExtendedModifiers(spoon.support.compiler.jdt.JDTTreeBuilderQuery.getModifiers(argument.modifiers, false, false));
        if ((((argument.binding) != null) && ((argument.binding.type) != null)) && ((argument.type) == null)) {
            p.setType(jdtTreeBuilder.getReferencesBuilder().<T>getTypeReference(argument.binding.type));
            p.getType().setImplicit(((argument.type) == null));
            if ((p.getType()) instanceof spoon.reflect.reference.CtArrayTypeReference) {
                ((spoon.reflect.reference.CtArrayTypeReference) (p.getType())).getComponentType().setImplicit(((argument.type) == null));
            }
        }
        return p;
    }

    /**
     * Creates an executable reference expression.
     *
     * @param referenceExpression
     * 		Used to get the executable reference.
     * @return an executable reference expression.
     */
    <T, E extends spoon.reflect.code.CtExpression<?>> spoon.reflect.code.CtExecutableReferenceExpression<T, E> createExecutableReferenceExpression(org.eclipse.jdt.internal.compiler.ast.ReferenceExpression referenceExpression) {
        spoon.reflect.code.CtExecutableReferenceExpression<T, E> executableRef = jdtTreeBuilder.getFactory().Core().createExecutableReferenceExpression();
        spoon.reflect.reference.CtExecutableReference<T> executableReference = jdtTreeBuilder.getReferencesBuilder().getExecutableReference(referenceExpression.binding);
        if (executableReference == null) {
            // No classpath mode.
            executableReference = jdtTreeBuilder.getFactory().Core().createExecutableReference();
            executableReference.setSimpleName(org.eclipse.jdt.core.compiler.CharOperation.charToString(referenceExpression.selector));
            executableReference.setDeclaringType(jdtTreeBuilder.getReferencesBuilder().getTypeReference(referenceExpression.lhs.resolvedType));
        }
        final spoon.reflect.reference.CtTypeReference<T> declaringType = ((spoon.reflect.reference.CtTypeReference<T>) (executableReference.getDeclaringType()));
        executableReference.setType((declaringType == null ? null : declaringType.clone()));
        executableRef.setExecutable(executableReference);
        return executableRef;
    }

    /**
     * Creates a class, an enum, an interface or a annotation type.
     *
     * @return a type.
     */
    spoon.reflect.declaration.CtType<?> createType(org.eclipse.jdt.internal.compiler.ast.TypeDeclaration typeDeclaration) {
        spoon.reflect.declaration.CtType<?> type;
        if (((typeDeclaration.modifiers) & (org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants.AccAnnotation)) != 0) {
            type = jdtTreeBuilder.getFactory().Core().<java.lang.annotation.Annotation>createAnnotationType();
        }else
            if (((typeDeclaration.modifiers) & (org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants.AccEnum)) != 0) {
                type = jdtTreeBuilder.getFactory().Core().createEnum();
            }else
                if (((typeDeclaration.modifiers) & (org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants.AccInterface)) != 0) {
                    type = jdtTreeBuilder.getFactory().Core().createInterface();
                }else {
                    type = jdtTreeBuilder.getFactory().Core().createClass();
                }


        // Setting modifiers
        type.setExtendedModifiers(spoon.support.compiler.jdt.JDTTreeBuilderQuery.getModifiers(typeDeclaration.modifiers, false, false));
        jdtTreeBuilder.getContextBuilder().enter(type, typeDeclaration);
        if ((typeDeclaration.superInterfaces) != null) {
            for (org.eclipse.jdt.internal.compiler.ast.TypeReference ref : typeDeclaration.superInterfaces) {
                final spoon.reflect.reference.CtTypeReference superInterface = jdtTreeBuilder.references.buildTypeReference(ref, null);
                type.addSuperInterface(superInterface);
            }
        }
        if (type instanceof spoon.reflect.declaration.CtClass) {
            if ((typeDeclaration.superclass) != null) {
                ((spoon.reflect.declaration.CtClass) (type)).setSuperclass(jdtTreeBuilder.references.buildTypeReference(typeDeclaration.superclass, typeDeclaration.scope));
            }
            if ((typeDeclaration.binding.isAnonymousType()) || (((typeDeclaration.binding) instanceof org.eclipse.jdt.internal.compiler.lookup.LocalTypeBinding) && ((typeDeclaration.binding.enclosingMethod()) != null))) {
                type.setSimpleName(spoon.support.compiler.jdt.JDTTreeBuilderHelper.computeAnonymousName(typeDeclaration.binding.constantPoolName()));
            }else {
                type.setSimpleName(new java.lang.String(typeDeclaration.name));
            }
        }else {
            type.setSimpleName(new java.lang.String(typeDeclaration.name));
        }
        return type;
    }

    /**
     * Creates an entire object CtModule from a module declaration.
     *
     * @return a CtModule
     */
    spoon.reflect.declaration.CtModule createModule(org.eclipse.jdt.internal.compiler.ast.ModuleDeclaration moduleDeclaration) {
        spoon.reflect.declaration.CtModule module = jdtTreeBuilder.getFactory().Module().getOrCreate(new java.lang.String(moduleDeclaration.moduleName));
        module.setIsOpenModule(moduleDeclaration.isOpen());
        jdtTreeBuilder.getContextBuilder().enter(module, moduleDeclaration);
        if (((moduleDeclaration.requires) != null) && ((moduleDeclaration.requires.length) > 0)) {
            java.util.List<spoon.reflect.declaration.CtModuleRequirement> moduleRequirements = new java.util.ArrayList<>();
            for (org.eclipse.jdt.internal.compiler.ast.RequiresStatement requiresStatement : moduleDeclaration.requires) {
                moduleRequirements.add(this.createModuleRequirement(requiresStatement));
            }
            module.setRequiredModules(moduleRequirements);
        }
        if (((moduleDeclaration.exports) != null) && ((moduleDeclaration.exports.length) > 0)) {
            java.util.List<spoon.reflect.declaration.CtPackageExport> moduleExports = new java.util.ArrayList<>();
            for (org.eclipse.jdt.internal.compiler.ast.ExportsStatement exportsStatement : moduleDeclaration.exports) {
                moduleExports.add(this.createModuleExport(exportsStatement));
            }
            module.setExportedPackages(moduleExports);
        }
        if (((moduleDeclaration.opens) != null) && ((moduleDeclaration.opens.length) > 0)) {
            java.util.List<spoon.reflect.declaration.CtPackageExport> moduleOpens = new java.util.ArrayList<>();
            for (org.eclipse.jdt.internal.compiler.ast.OpensStatement opensStatement : moduleDeclaration.opens) {
                moduleOpens.add(this.createModuleExport(opensStatement));
            }
            module.setOpenedPackages(moduleOpens);
        }
        if (((moduleDeclaration.uses) != null) && ((moduleDeclaration.uses.length) > 0)) {
            java.util.List<spoon.reflect.declaration.CtUsedService> consumedServices = new java.util.ArrayList<>();
            for (org.eclipse.jdt.internal.compiler.ast.UsesStatement consumedService : moduleDeclaration.uses) {
                consumedServices.add(this.createUsedService(consumedService));
            }
            module.setUsedServices(consumedServices);
        }
        if (((moduleDeclaration.services) != null) && ((moduleDeclaration.services.length) > 0)) {
            java.util.List<spoon.reflect.declaration.CtProvidedService> moduleProvidedServices = new java.util.ArrayList<>();
            for (org.eclipse.jdt.internal.compiler.ast.ProvidesStatement providesStatement : moduleDeclaration.services) {
                moduleProvidedServices.add(this.createModuleProvidedService(providesStatement));
            }
            module.setProvidedServices(moduleProvidedServices);
        }
        module.setPosition(this.jdtTreeBuilder.getPositionBuilder().buildPosition(moduleDeclaration.declarationSourceStart, moduleDeclaration.declarationSourceEnd));
        return module;
    }

    spoon.reflect.declaration.CtUsedService createUsedService(org.eclipse.jdt.internal.compiler.ast.UsesStatement usesStatement) {
        spoon.reflect.reference.CtTypeReference typeReference = this.jdtTreeBuilder.references.getTypeReference(usesStatement.serviceInterface);
        spoon.reflect.declaration.CtUsedService usedService = this.jdtTreeBuilder.getFactory().Module().createUsedService(typeReference);
        usedService.setPosition(this.jdtTreeBuilder.getPositionBuilder().buildPosition(usesStatement.sourceStart, usesStatement.sourceEnd));
        return usedService;
    }

    spoon.reflect.declaration.CtModuleRequirement createModuleRequirement(org.eclipse.jdt.internal.compiler.ast.RequiresStatement requiresStatement) {
        int sourceStart = requiresStatement.sourceStart;
        int sourceEnd = requiresStatement.sourceEnd;
        spoon.reflect.reference.CtModuleReference ctModuleReference = jdtTreeBuilder.references.getModuleReference(requiresStatement.module);
        spoon.reflect.declaration.CtModuleRequirement moduleRequirement = jdtTreeBuilder.getFactory().Module().createModuleRequirement(ctModuleReference);
        java.util.Set<spoon.reflect.declaration.CtModuleRequirement.RequiresModifier> modifiers = new java.util.HashSet<>();
        if (requiresStatement.isStatic()) {
            modifiers.add(spoon.reflect.declaration.CtModuleRequirement.RequiresModifier.STATIC);
        }
        if (requiresStatement.isTransitive()) {
            modifiers.add(spoon.reflect.declaration.CtModuleRequirement.RequiresModifier.TRANSITIVE);
        }
        moduleRequirement.setRequiresModifiers(modifiers);
        moduleRequirement.setPosition(this.jdtTreeBuilder.getPositionBuilder().buildPosition(sourceStart, sourceEnd));
        return moduleRequirement;
    }

    spoon.reflect.declaration.CtPackageExport createModuleExport(org.eclipse.jdt.internal.compiler.ast.ExportsStatement exportsStatement) {
        java.lang.String packageName = new java.lang.String(exportsStatement.pkgName);
        int sourceStart = exportsStatement.sourceStart;
        int sourceEnd = exportsStatement.sourceEnd;
        spoon.reflect.reference.CtPackageReference ctPackageReference = jdtTreeBuilder.references.getPackageReference(packageName);
        spoon.reflect.declaration.CtPackageExport moduleExport = jdtTreeBuilder.getFactory().Module().createPackageExport(ctPackageReference);
        if (((exportsStatement.targets) != null) && ((exportsStatement.targets.length) > 0)) {
            java.util.List<spoon.reflect.reference.CtModuleReference> moduleReferences = new java.util.ArrayList<>();
            for (org.eclipse.jdt.internal.compiler.ast.ModuleReference moduleReference : exportsStatement.targets) {
                moduleReferences.add(this.jdtTreeBuilder.references.getModuleReference(moduleReference));
            }
            moduleExport.setTargetExport(moduleReferences);
        }
        moduleExport.setPosition(this.jdtTreeBuilder.getPositionBuilder().buildPosition(sourceStart, sourceEnd));
        return moduleExport;
    }

    spoon.reflect.declaration.CtPackageExport createModuleExport(org.eclipse.jdt.internal.compiler.ast.OpensStatement opensStatement) {
        java.lang.String packageName = new java.lang.String(opensStatement.pkgName);
        int sourceStart = opensStatement.sourceStart;
        int sourceEnd = opensStatement.sourceEnd;
        spoon.reflect.reference.CtPackageReference ctPackageReference = jdtTreeBuilder.references.getPackageReference(packageName);
        spoon.reflect.declaration.CtPackageExport moduleExport = jdtTreeBuilder.getFactory().Module().createPackageExport(ctPackageReference);
        if (((opensStatement.targets) != null) && ((opensStatement.targets.length) > 0)) {
            java.util.List<spoon.reflect.reference.CtModuleReference> moduleReferences = new java.util.ArrayList<>();
            for (org.eclipse.jdt.internal.compiler.ast.ModuleReference moduleReference : opensStatement.targets) {
                moduleReferences.add(this.jdtTreeBuilder.references.getModuleReference(moduleReference));
            }
            moduleExport.setTargetExport(moduleReferences);
        }
        moduleExport.setPosition(this.jdtTreeBuilder.getPositionBuilder().buildPosition(sourceStart, sourceEnd));
        return moduleExport;
    }

    spoon.reflect.declaration.CtProvidedService createModuleProvidedService(org.eclipse.jdt.internal.compiler.ast.ProvidesStatement providesStatement) {
        int sourceStart = providesStatement.sourceStart;
        int sourceEnd = providesStatement.sourceEnd;
        spoon.reflect.reference.CtTypeReference provideService = this.jdtTreeBuilder.references.getTypeReference(providesStatement.serviceInterface);
        java.util.List<spoon.reflect.reference.CtTypeReference> implementations = new java.util.ArrayList<>();
        for (org.eclipse.jdt.internal.compiler.ast.TypeReference typeReference : providesStatement.implementations) {
            implementations.add(this.jdtTreeBuilder.references.getTypeReference(typeReference));
        }
        spoon.reflect.declaration.CtProvidedService providedService = this.jdtTreeBuilder.getFactory().Module().createProvidedService(provideService);
        providedService.setImplementationTypes(implementations);
        providedService.setPosition(this.jdtTreeBuilder.getPositionBuilder().buildPosition(sourceStart, sourceEnd));
        return providedService;
    }
}

