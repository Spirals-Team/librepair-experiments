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


public class ReferenceBuilder {
    // Allow to detect circular references and to avoid endless recursivity
    // when resolving parameterizedTypes (e.g. Enum<E extends Enum<E>>)
    private java.util.Map<org.eclipse.jdt.internal.compiler.lookup.TypeBinding, spoon.reflect.reference.CtTypeReference> exploringParameterizedBindings = new java.util.HashMap<>();

    private boolean bounds = false;

    private final spoon.support.compiler.jdt.JDTTreeBuilder jdtTreeBuilder;

    ReferenceBuilder(spoon.support.compiler.jdt.JDTTreeBuilder jdtTreeBuilder) {
        this.jdtTreeBuilder = jdtTreeBuilder;
    }

    private spoon.reflect.reference.CtTypeReference<?> getBoundedTypeReference(org.eclipse.jdt.internal.compiler.lookup.TypeBinding binding) {
        bounds = true;
        spoon.reflect.reference.CtTypeReference<?> ref = getTypeReference(binding);
        bounds = false;
        return ref;
    }

    /**
     * Builds a type reference from a {@link TypeReference}.
     *
     * @param type
     * 		Type from JDT.
     * @param scope
     * 		Scope of the parent element.
     * @param <T>
     * 		Type of the type reference.
     * @return a type reference.
     */
    <T> spoon.reflect.reference.CtTypeReference<T> buildTypeReference(org.eclipse.jdt.internal.compiler.ast.TypeReference type, org.eclipse.jdt.internal.compiler.lookup.Scope scope) {
        if (type == null) {
            return null;
        }
        spoon.reflect.reference.CtTypeReference<T> typeReference = this.<T>getTypeReference(type.resolvedType, type);
        return buildTypeReferenceInternal(typeReference, type, scope);
    }

    /**
     * Builds a qualified type reference from a {@link TypeReference}.
     *
     * @param type
     * 		Qualified type from JDT.
     * @param scope
     * 		Scope of the parent element.
     * @return 
     */
    <T> spoon.reflect.reference.CtTypeReference<T> buildTypeReference(org.eclipse.jdt.internal.compiler.ast.QualifiedTypeReference type, org.eclipse.jdt.internal.compiler.lookup.Scope scope) {
        spoon.reflect.reference.CtTypeReference<T> accessedType = buildTypeReference(((org.eclipse.jdt.internal.compiler.ast.TypeReference) (type)), scope);
        final org.eclipse.jdt.internal.compiler.lookup.TypeBinding receiverType = (type != null) ? type.resolvedType : null;
        if (receiverType != null) {
            final spoon.reflect.reference.CtTypeReference<T> ref = getQualifiedTypeReference(type.tokens, receiverType, receiverType.enclosingType(), new spoon.support.compiler.jdt.JDTTreeBuilder.OnAccessListener() {
                @java.lang.Override
                public boolean onAccess(char[][] tokens, int index) {
                    return true;
                }
            });
            if (ref != null) {
                accessedType = ref;
            }
        }
        return accessedType;
    }

    /**
     * Builds a type parameter reference from a {@link TypeReference}
     *
     * @param type
     * 		Type from JDT.
     * @param scope
     * 		Scope of the parent element.
     * @return a type parameter reference.
     */
    private spoon.reflect.reference.CtTypeParameterReference buildTypeParameterReference(org.eclipse.jdt.internal.compiler.ast.TypeReference type, org.eclipse.jdt.internal.compiler.lookup.Scope scope) {
        if (type == null) {
            return null;
        }
        return ((spoon.reflect.reference.CtTypeParameterReference) (this.buildTypeReferenceInternal(this.getTypeParameterReference(type.resolvedType, type), type, scope)));
    }

    private <T> spoon.reflect.reference.CtTypeReference<T> buildTypeReferenceInternal(spoon.reflect.reference.CtTypeReference<T> typeReference, org.eclipse.jdt.internal.compiler.ast.TypeReference type, org.eclipse.jdt.internal.compiler.lookup.Scope scope) {
        if (type == null) {
            return null;
        }
        spoon.reflect.reference.CtTypeReference<?> currentReference = typeReference;
        for (int position = (type.getTypeName().length) - 1; position >= 0; position--) {
            if (currentReference == null) {
                break;
            }
            this.jdtTreeBuilder.getContextBuilder().enter(currentReference, type);
            if (((((type.annotations) != null) && (((type.annotations.length) - 1) <= position)) && ((type.annotations[position]) != null)) && ((type.annotations[position].length) > 0)) {
                for (org.eclipse.jdt.internal.compiler.ast.Annotation annotation : type.annotations[position]) {
                    if (scope instanceof org.eclipse.jdt.internal.compiler.lookup.ClassScope) {
                        annotation.traverse(this.jdtTreeBuilder, ((org.eclipse.jdt.internal.compiler.lookup.ClassScope) (scope)));
                    }else
                        if (scope instanceof org.eclipse.jdt.internal.compiler.lookup.BlockScope) {
                            annotation.traverse(this.jdtTreeBuilder, ((org.eclipse.jdt.internal.compiler.lookup.BlockScope) (scope)));
                        }else {
                            annotation.traverse(this.jdtTreeBuilder, ((org.eclipse.jdt.internal.compiler.lookup.BlockScope) (null)));
                        }

                }
            }
            if (((((type.getTypeArguments()) != null) && (((type.getTypeArguments().length) - 1) <= position)) && ((type.getTypeArguments()[position]) != null)) && ((type.getTypeArguments()[position].length) > 0)) {
                spoon.reflect.reference.CtTypeReference<?> componentReference = getTypeReferenceOfArrayComponent(currentReference);
                componentReference.getActualTypeArguments().clear();
                for (org.eclipse.jdt.internal.compiler.ast.TypeReference typeArgument : type.getTypeArguments()[position]) {
                    if (((typeArgument instanceof org.eclipse.jdt.internal.compiler.ast.Wildcard) || ((typeArgument.resolvedType) instanceof org.eclipse.jdt.internal.compiler.lookup.WildcardBinding)) || ((typeArgument.resolvedType) instanceof org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding)) {
                        componentReference.addActualTypeArgument(buildTypeParameterReference(typeArgument, scope));
                    }else {
                        componentReference.addActualTypeArgument(buildTypeReference(typeArgument, scope));
                    }
                }
            }else
                if (((type instanceof org.eclipse.jdt.internal.compiler.ast.ParameterizedSingleTypeReference) || (type instanceof org.eclipse.jdt.internal.compiler.ast.ParameterizedQualifiedTypeReference)) && (!(isTypeArgumentExplicit(type.getTypeArguments())))) {
                    for (spoon.reflect.reference.CtTypeReference<?> actualTypeArgument : currentReference.getActualTypeArguments()) {
                        actualTypeArgument.setImplicit(true);
                        if (actualTypeArgument instanceof spoon.reflect.reference.CtArrayTypeReference) {
                            ((spoon.reflect.reference.CtArrayTypeReference) (actualTypeArgument)).getComponentType().setImplicit(true);
                        }
                    }
                }

            if ((type instanceof org.eclipse.jdt.internal.compiler.ast.Wildcard) && (typeReference instanceof spoon.reflect.reference.CtTypeParameterReference)) {
                ((spoon.reflect.reference.CtTypeParameterReference) (typeReference)).setBoundingType(buildTypeReference(((org.eclipse.jdt.internal.compiler.ast.Wildcard) (type)).bound, scope));
            }
            this.jdtTreeBuilder.getContextBuilder().exit(type);
            currentReference = currentReference.getDeclaringType();
        }
        return typeReference;
    }

    private spoon.reflect.reference.CtTypeReference<?> getTypeReferenceOfArrayComponent(spoon.reflect.reference.CtTypeReference<?> currentReference) {
        while (currentReference instanceof spoon.reflect.reference.CtArrayTypeReference) {
            currentReference = ((spoon.reflect.reference.CtArrayTypeReference<?>) (currentReference)).getComponentType();
        } 
        return currentReference;
    }

    private boolean isTypeArgumentExplicit(org.eclipse.jdt.internal.compiler.ast.TypeReference[][] typeArguments) {
        if (typeArguments == null) {
            return true;
        }
        boolean isGenericTypeExplicit = true;
        // This loop is necessary because it is the only way to know if the generic type
        // is implicit or not.
        for (org.eclipse.jdt.internal.compiler.ast.TypeReference[] typeArgument : typeArguments) {
            isGenericTypeExplicit = (typeArgument != null) && ((typeArgument.length) > 0);
            if (isGenericTypeExplicit) {
                break;
            }
        }
        return isGenericTypeExplicit;
    }

    /**
     * Builds a type reference from a qualified name when a type specified in the name isn't available.
     *
     * @param tokens
     * 		Qualified name.
     * @param receiverType
     * 		Last type in the qualified name.
     * @param enclosingType
     * 		Enclosing type of the type name.
     * @param listener
     * 		Listener to know if we must build the type reference.
     * @return a type reference.
     */
    <T> spoon.reflect.reference.CtTypeReference<T> getQualifiedTypeReference(char[][] tokens, org.eclipse.jdt.internal.compiler.lookup.TypeBinding receiverType, org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding enclosingType, spoon.support.compiler.jdt.JDTTreeBuilder.OnAccessListener listener) {
        final java.util.List<spoon.support.reflect.CtExtendedModifier> listPublicProtected = java.util.Arrays.asList(new spoon.support.reflect.CtExtendedModifier(spoon.reflect.declaration.ModifierKind.PUBLIC), new spoon.support.reflect.CtExtendedModifier(spoon.reflect.declaration.ModifierKind.PROTECTED));
        if ((enclosingType != null) && (java.util.Collections.disjoint(listPublicProtected, spoon.support.compiler.jdt.JDTTreeBuilderQuery.getModifiers(enclosingType.modifiers, false, false)))) {
            java.lang.String access = "";
            int i = 0;
            final org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration[] units = ((spoon.support.compiler.jdt.TreeBuilderCompiler) (this.jdtTreeBuilder.getContextBuilder().compilationunitdeclaration.scope.environment.typeRequestor)).unitsToProcess;
            for (; i < (tokens.length); i++) {
                final char[][] qualified = java.util.Arrays.copyOfRange(tokens, 0, (i + 1));
                if ((spoon.support.compiler.jdt.JDTTreeBuilderQuery.searchPackage(qualified, units)) == null) {
                    access = org.eclipse.jdt.core.compiler.CharOperation.toString(qualified);
                    break;
                }
            }
            if (!(access.contains(spoon.reflect.declaration.CtPackage.PACKAGE_SEPARATOR))) {
                access = spoon.support.compiler.jdt.JDTTreeBuilderQuery.searchType(access, this.jdtTreeBuilder.getContextBuilder().compilationunitdeclaration.imports);
            }
            final org.eclipse.jdt.internal.compiler.lookup.TypeBinding accessBinding = spoon.support.compiler.jdt.JDTTreeBuilderQuery.searchTypeBinding(access, units);
            if ((accessBinding != null) && (listener.onAccess(tokens, i))) {
                final org.eclipse.jdt.internal.compiler.lookup.TypeBinding superClassBinding = spoon.support.compiler.jdt.JDTTreeBuilderQuery.searchTypeBinding(accessBinding.superclass(), org.eclipse.jdt.core.compiler.CharOperation.charToString(tokens[(i + 1)]));
                if (superClassBinding != null) {
                    return this.getTypeReference(superClassBinding.clone(accessBinding));
                }else {
                    return this.getTypeReference(receiverType);
                }
            }else {
                return this.getTypeReference(receiverType);
            }
        }
        return null;
    }

    /**
     * Try to get the declaring reference (package or type) from imports of the current
     * compilation unit declaration (current class). This method returns a CtReference
     * which can be a CtTypeReference if it retrieves the information in an static import,
     * a CtPackageReference if it retrieves the information in an standard import, otherwise
     * it returns null.
     *
     * @param expectedName
     * 		Name expected in imports.
     * @return CtReference which can be a CtTypeReference, a CtPackageReference or null.
     */
    spoon.reflect.reference.CtReference getDeclaringReferenceFromImports(char[] expectedName) {
        org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration cuDeclaration = this.jdtTreeBuilder.getContextBuilder().compilationunitdeclaration;
        org.eclipse.jdt.internal.compiler.lookup.LookupEnvironment environment = cuDeclaration.scope.environment;
        if ((cuDeclaration != null) && ((cuDeclaration.imports) != null)) {
            for (org.eclipse.jdt.internal.compiler.ast.ImportReference anImport : cuDeclaration.imports) {
                if (org.eclipse.jdt.core.compiler.CharOperation.equals(anImport.getImportName()[((anImport.getImportName().length) - 1)], expectedName)) {
                    if (anImport.isStatic()) {
                        int indexDeclaring = 2;
                        if (((anImport.bits) & (org.eclipse.jdt.internal.compiler.ast.ASTNode.OnDemand)) != 0) {
                            // With .*
                            indexDeclaring = 1;
                        }
                        char[][] packageName = org.eclipse.jdt.core.compiler.CharOperation.subarray(anImport.getImportName(), 0, ((anImport.getImportName().length) - indexDeclaring));
                        char[][] className = org.eclipse.jdt.core.compiler.CharOperation.subarray(anImport.getImportName(), ((anImport.getImportName().length) - indexDeclaring), ((anImport.getImportName().length) - (indexDeclaring - 1)));
                        org.eclipse.jdt.internal.compiler.lookup.PackageBinding aPackage;
                        try {
                            if ((packageName.length) != 0) {
                                aPackage = environment.createPackage(packageName);
                            }else {
                                aPackage = null;
                            }
                            final org.eclipse.jdt.internal.compiler.lookup.MissingTypeBinding declaringType = environment.createMissingType(aPackage, className);
                            this.jdtTreeBuilder.getContextBuilder().ignoreComputeImports = true;
                            final spoon.reflect.reference.CtTypeReference<java.lang.Object> typeReference = getTypeReference(declaringType);
                            this.jdtTreeBuilder.getContextBuilder().ignoreComputeImports = false;
                            return typeReference;
                        } catch (java.lang.NullPointerException e) {
                            return null;
                        }
                    }else {
                        org.eclipse.jdt.internal.compiler.lookup.PackageBinding packageBinding = null;
                        char[][] chars = org.eclipse.jdt.core.compiler.CharOperation.subarray(anImport.getImportName(), 0, ((anImport.getImportName().length) - 1));
                        // `findImport(chars, false, false);` and `createPackage(chars)` require
                        // an array with a minimum length of 1 and throw an
                        // ArrayIndexOutOfBoundsException if `chars.length == 0`. Fixes #759.
                        if ((chars.length) > 0) {
                            org.eclipse.jdt.internal.compiler.lookup.Binding someBinding = cuDeclaration.scope.findImport(chars, false, false);
                            if (((someBinding != null) && (someBinding.isValidBinding())) && (someBinding instanceof org.eclipse.jdt.internal.compiler.lookup.PackageBinding)) {
                                packageBinding = ((org.eclipse.jdt.internal.compiler.lookup.PackageBinding) (someBinding));
                            }else {
                                try {
                                    packageBinding = environment.createPackage(chars);
                                } catch (java.lang.NullPointerException e) {
                                    packageBinding = null;
                                }
                            }
                        }
                        if ((packageBinding == null) || (packageBinding instanceof org.eclipse.jdt.internal.compiler.lookup.ProblemPackageBinding)) {
                            // Big crisis here. We are already in noclasspath mode but JDT doesn't support always
                            // creation of a package in this mode. So, if we are in this brace, we make the job of JDT...
                            packageBinding = new org.eclipse.jdt.internal.compiler.lookup.PackageBinding(chars, null, environment, environment.module);
                        }
                        return getPackageReference(packageBinding);
                    }
                }
            }
        }
        return null;
    }

    @java.lang.SuppressWarnings("unchecked")
    <T> spoon.reflect.reference.CtExecutableReference<T> getExecutableReference(org.eclipse.jdt.internal.compiler.lookup.MethodBinding exec) {
        if (exec == null) {
            return null;
        }
        final spoon.reflect.reference.CtExecutableReference ref = this.jdtTreeBuilder.getFactory().Core().createExecutableReference();
        if (exec.isConstructor()) {
            ref.setSimpleName(spoon.reflect.reference.CtExecutableReference.CONSTRUCTOR_NAME);
            ref.setType(getTypeReference(exec.declaringClass));
        }else {
            ref.setSimpleName(new java.lang.String(exec.selector));
            ref.setType(getTypeReference(exec.returnType));
        }
        if (exec instanceof org.eclipse.jdt.internal.compiler.lookup.ProblemMethodBinding) {
            if (((exec.declaringClass) != null) && (java.util.Arrays.asList(exec.declaringClass.methods()).contains(exec))) {
                ref.setDeclaringType(getTypeReference(exec.declaringClass));
            }else {
                final spoon.reflect.reference.CtReference declaringType = getDeclaringReferenceFromImports(exec.constantPoolName());
                if (declaringType instanceof spoon.reflect.reference.CtTypeReference) {
                    ref.setDeclaringType(((spoon.reflect.reference.CtTypeReference<?>) (declaringType)));
                }
            }
            if (exec.isConstructor()) {
                // super() invocation have a good declaring class.
                ref.setDeclaringType(getTypeReference(exec.declaringClass));
            }
            ref.setStatic(true);
        }else {
            ref.setDeclaringType(getTypeReference(exec.declaringClass));
            ref.setStatic(exec.isStatic());
        }
        if ((exec.declaringClass) instanceof org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding) {
            ref.setDeclaringType(getTypeReference(exec.declaringClass.actualType()));
        }
        // original() method returns a result not null when the current method is generic.
        // This is a method without a generic argument.
        if ((exec.original()) != null) {
            final java.util.List<spoon.reflect.reference.CtTypeReference<?>> parameters = new java.util.ArrayList<>(exec.original().parameters.length);
            for (org.eclipse.jdt.internal.compiler.lookup.TypeBinding b : exec.original().parameters) {
                parameters.add(getTypeReference(b));
            }
            ref.setParameters(parameters);
        }// This is a method without a generic argument.
        else
            if ((exec.parameters) != null) {
                final java.util.List<spoon.reflect.reference.CtTypeReference<?>> parameters = new java.util.ArrayList<>();
                for (org.eclipse.jdt.internal.compiler.lookup.TypeBinding b : exec.parameters) {
                    parameters.add(getTypeReference(b));
                }
                ref.setParameters(parameters);
            }

        return ref;
    }

    <T> spoon.reflect.reference.CtExecutableReference<T> getExecutableReference(org.eclipse.jdt.internal.compiler.ast.AllocationExpression allocationExpression) {
        spoon.reflect.reference.CtExecutableReference<T> ref;
        if ((allocationExpression.binding) != null) {
            ref = getExecutableReference(allocationExpression.binding);
        }else {
            ref = jdtTreeBuilder.getFactory().Core().createExecutableReference();
            ref.setSimpleName(spoon.reflect.reference.CtExecutableReference.CONSTRUCTOR_NAME);
            ref.setDeclaringType(getTypeReference(null, allocationExpression.type));
            final java.util.List<spoon.reflect.reference.CtTypeReference<?>> parameters = new java.util.ArrayList<>(allocationExpression.argumentTypes.length);
            for (org.eclipse.jdt.internal.compiler.lookup.TypeBinding b : allocationExpression.argumentTypes) {
                parameters.add(getTypeReference(b));
            }
            ref.setParameters(parameters);
        }
        if ((allocationExpression.type) == null) {
            ref.setType(this.<T>getTypeReference(allocationExpression.expectedType()));
        }
        return ref;
    }

    <T> spoon.reflect.reference.CtExecutableReference<T> getExecutableReference(org.eclipse.jdt.internal.compiler.ast.MessageSend messageSend) {
        if ((messageSend.binding) != null) {
            return getExecutableReference(messageSend.binding);
        }
        spoon.reflect.reference.CtExecutableReference<T> ref = jdtTreeBuilder.getFactory().Core().createExecutableReference();
        ref.setSimpleName(org.eclipse.jdt.core.compiler.CharOperation.charToString(messageSend.selector));
        ref.setType(this.<T>getTypeReference(messageSend.expectedType()));
        if ((messageSend.receiver.resolvedType) == null) {
            // It is crisis dude! static context, we don't have much more information.
            if ((messageSend.receiver) instanceof org.eclipse.jdt.internal.compiler.ast.SingleNameReference) {
                ref.setDeclaringType(jdtTreeBuilder.getHelper().createTypeAccessNoClasspath(((org.eclipse.jdt.internal.compiler.ast.SingleNameReference) (messageSend.receiver))).getAccessedType());
            }else
                if ((messageSend.receiver) instanceof org.eclipse.jdt.internal.compiler.ast.QualifiedNameReference) {
                    ref.setDeclaringType(jdtTreeBuilder.getHelper().createTypeAccessNoClasspath(((org.eclipse.jdt.internal.compiler.ast.QualifiedNameReference) (messageSend.receiver))).getAccessedType());
                }

        }else {
            ref.setDeclaringType(getTypeReference(messageSend.receiver.resolvedType));
        }
        if ((messageSend.arguments) != null) {
            final java.util.List<spoon.reflect.reference.CtTypeReference<?>> parameters = new java.util.ArrayList<>();
            for (org.eclipse.jdt.internal.compiler.ast.Expression expression : messageSend.arguments) {
                parameters.add(getTypeReference(expression.resolvedType));
            }
            ref.setParameters(parameters);
        }
        return ref;
    }

    private spoon.reflect.reference.CtPackageReference getPackageReference(org.eclipse.jdt.internal.compiler.lookup.PackageBinding reference) {
        return getPackageReference(new java.lang.String(reference.shortReadableName()));
    }

    public spoon.reflect.reference.CtPackageReference getPackageReference(java.lang.String name) {
        if ((name.length()) == 0) {
            return this.jdtTreeBuilder.getFactory().Package().topLevel();
        }
        spoon.reflect.reference.CtPackageReference ref = this.jdtTreeBuilder.getFactory().Core().createPackageReference();
        ref.setSimpleName(name);
        return ref;
    }

    final java.util.Map<org.eclipse.jdt.internal.compiler.lookup.TypeBinding, spoon.reflect.reference.CtTypeReference> bindingCache = new java.util.HashMap<>();

    <T> spoon.reflect.reference.CtTypeReference<T> getTypeReference(org.eclipse.jdt.internal.compiler.lookup.TypeBinding binding, org.eclipse.jdt.internal.compiler.ast.TypeReference ref) {
        spoon.reflect.reference.CtTypeReference<T> ctRef = getTypeReference(binding);
        if ((ctRef != null) && (isCorrectTypeReference(ref))) {
            insertGenericTypesInNoClasspathFromJDTInSpoon(ref, ctRef);
            return ctRef;
        }
        spoon.reflect.reference.CtTypeReference<T> result = getTypeReference(ref);
        return result;
    }

    spoon.reflect.reference.CtTypeReference<java.lang.Object> getTypeParameterReference(org.eclipse.jdt.internal.compiler.lookup.TypeBinding binding, org.eclipse.jdt.internal.compiler.ast.TypeReference ref) {
        spoon.reflect.reference.CtTypeReference<java.lang.Object> ctRef = getTypeReference(binding);
        if ((ctRef != null) && (isCorrectTypeReference(ref))) {
            if (!(ctRef instanceof spoon.reflect.reference.CtTypeParameterReference)) {
                spoon.reflect.reference.CtTypeParameterReference typeParameterRef = this.jdtTreeBuilder.getFactory().Core().createTypeParameterReference();
                typeParameterRef.setSimpleName(ctRef.getSimpleName());
                typeParameterRef.setDeclaringType(ctRef.getDeclaringType());
                typeParameterRef.setPackage(ctRef.getPackage());
                ctRef = typeParameterRef;
            }
            insertGenericTypesInNoClasspathFromJDTInSpoon(ref, ctRef);
            return ctRef;
        }
        return getTypeParameterReference(org.eclipse.jdt.core.compiler.CharOperation.toString(ref.getParameterizedTypeName()));
    }

    /**
     * In no classpath, the model of the super interface isn't always correct.
     */
    private boolean isCorrectTypeReference(org.eclipse.jdt.internal.compiler.ast.TypeReference ref) {
        if ((ref.resolvedType) == null) {
            return false;
        }
        if (!((ref.resolvedType) instanceof org.eclipse.jdt.internal.compiler.lookup.ProblemReferenceBinding)) {
            return true;
        }
        final java.lang.String[] compoundName = org.eclipse.jdt.core.compiler.CharOperation.charArrayToStringArray(((org.eclipse.jdt.internal.compiler.lookup.ProblemReferenceBinding) (ref.resolvedType)).compoundName);
        final java.lang.String[] typeName = org.eclipse.jdt.core.compiler.CharOperation.charArrayToStringArray(ref.getTypeName());
        if (((compoundName.length) == 0) || ((typeName.length) == 0)) {
            return false;
        }
        return compoundName[((compoundName.length) - 1)].equals(typeName[((typeName.length) - 1)]);
    }

    private <T> void insertGenericTypesInNoClasspathFromJDTInSpoon(org.eclipse.jdt.internal.compiler.ast.TypeReference original, spoon.reflect.reference.CtTypeReference<T> type) {
        if (((original.resolvedType) instanceof org.eclipse.jdt.internal.compiler.lookup.ProblemReferenceBinding) && ((original.getTypeArguments()) != null)) {
            for (org.eclipse.jdt.internal.compiler.ast.TypeReference[] typeReferences : original.getTypeArguments()) {
                if (typeReferences != null) {
                    for (org.eclipse.jdt.internal.compiler.ast.TypeReference typeReference : typeReferences) {
                        type.addActualTypeArgument(this.getTypeReference(typeReference.resolvedType));
                    }
                }
            }
        }
    }

    /**
     * JDT doesn't return a correct AST with the resolved type of the reference.
     * This method try to build a correct Spoon AST from the name of the JDT
     * reference, thanks to the parsing of the string, the name parameterized from
     * the JDT reference and java convention.
     * Returns a complete Spoon AST when the name is correct, otherwise a spoon type
     * reference with a name that correspond to the name of the JDT type reference.
     */
    <T> spoon.reflect.reference.CtTypeReference<T> getTypeReference(org.eclipse.jdt.internal.compiler.ast.TypeReference ref) {
        spoon.reflect.reference.CtTypeReference<T> res = null;
        spoon.reflect.reference.CtTypeReference inner = null;
        final java.lang.String[] namesParameterized = org.eclipse.jdt.core.compiler.CharOperation.charArrayToStringArray(ref.getParameterizedTypeName());
        java.lang.String nameParameterized = org.eclipse.jdt.core.compiler.CharOperation.toString(ref.getParameterizedTypeName());
        java.lang.String typeName = org.eclipse.jdt.core.compiler.CharOperation.toString(ref.getTypeName());
        int index = (namesParameterized.length) - 1;
        for (; index >= 0; index--) {
            // Start at the end to get the class name first.
            spoon.reflect.reference.CtTypeReference main = getTypeReference(namesParameterized[index]);
            if (main == null) {
                break;
            }
            if (res == null) {
                res = ((spoon.reflect.reference.CtTypeReference<T>) (main));
            }else {
                inner.setDeclaringType(((spoon.reflect.reference.CtTypeReference<?>) (main)));
            }
            inner = main;
        }
        if (res == null) {
            return this.jdtTreeBuilder.getFactory().Type().createReference(nameParameterized);
        }
        if ((inner.getPackage()) == null) {
            spoon.reflect.factory.PackageFactory packageFactory = this.jdtTreeBuilder.getFactory().Package();
            spoon.reflect.reference.CtPackageReference packageReference = (index >= 0) ? packageFactory.getOrCreate(concatSubArray(namesParameterized, index)).getReference() : packageFactory.topLevel();
            inner.setPackage(packageReference);
        }
        if (!(res.toString().replace(", ?", ",?").endsWith(nameParameterized))) {
            // verify that we did not match a class that have the same name in a different package
            return this.jdtTreeBuilder.getFactory().Type().createReference(typeName);
        }
        return res;
    }

    private java.lang.String concatSubArray(java.lang.String[] a, int endIndex) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        for (int i = 0; i < endIndex; i++) {
            sb.append(a[i]).append('.');
        }
        sb.append(a[endIndex]);
        return sb.toString();
    }

    /**
     * Try to build a CtTypeReference from a simple name with specified generic types but
     * returns null if the name doesn't correspond to a type (not start by an upper case).
     */
    private <T> spoon.reflect.reference.CtTypeReference<T> getTypeReference(java.lang.String name) {
        spoon.reflect.reference.CtTypeReference<T> main = null;
        if (name.matches(".*(<.+>)")) {
            java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("([^<]+)<(.+)>");
            java.util.regex.Matcher m = pattern.matcher(name);
            if (name.startsWith("?")) {
                main = ((spoon.reflect.reference.CtTypeReference) (this.jdtTreeBuilder.getFactory().Core().createWildcardReference()));
            }else {
                main = this.jdtTreeBuilder.getFactory().Core().createTypeReference();
            }
            if (m.find()) {
                main.setSimpleName(m.group(1));
                final java.lang.String[] split = m.group(2).split(",");
                for (java.lang.String parameter : split) {
                    ((spoon.reflect.reference.CtTypeReference) (main)).addActualTypeArgument(getTypeParameterReference(parameter.trim()));
                }
            }
        }else
            if (java.lang.Character.isUpperCase(name.charAt(0))) {
                main = this.jdtTreeBuilder.getFactory().Core().createTypeReference();
                main.setSimpleName(name);
                final spoon.reflect.reference.CtReference declaring = this.getDeclaringReferenceFromImports(name.toCharArray());
                setPackageOrDeclaringType(main, declaring);
            }else
                if (name.startsWith("?")) {
                    return ((spoon.reflect.reference.CtTypeReference) (this.jdtTreeBuilder.getFactory().Core().createWildcardReference()));
                }


        return main;
    }

    /**
     * Try to build a CtTypeParameterReference from a single name with specified generic types but
     * keep in mind that if you give wrong data in the strong, reference will be wrong.
     */
    private spoon.reflect.reference.CtTypeReference<java.lang.Object> getTypeParameterReference(java.lang.String name) {
        spoon.reflect.reference.CtTypeReference<java.lang.Object> param = null;
        if ((name.contains("extends")) || (name.contains("super"))) {
            java.lang.String[] split = (name.contains("extends")) ? name.split("extends") : name.split("super");
            param = getTypeParameterReference(split[0].trim());
            ((spoon.reflect.reference.CtTypeParameterReference) (param)).setBoundingType(getTypeReference(split[((split.length) - 1)].trim()));
        }else
            if (name.matches(".*(<.+>)")) {
                java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("([^<]+)<(.+)>");
                java.util.regex.Matcher m = pattern.matcher(name);
                if (m.find()) {
                    param = this.jdtTreeBuilder.getFactory().Core().createTypeReference();
                    param.setSimpleName(m.group(1));
                    final java.lang.String[] split = m.group(2).split(",");
                    for (java.lang.String parameter : split) {
                        param.addActualTypeArgument(getTypeParameterReference(parameter.trim()));
                    }
                }
            }else
                if (name.contains("?")) {
                    param = this.jdtTreeBuilder.getFactory().Core().createWildcardReference();
                }else {
                    param = this.jdtTreeBuilder.getFactory().Core().createTypeParameterReference();
                    param.setSimpleName(name);
                }


        return param;
    }

    @java.lang.SuppressWarnings("unchecked")
    <T> spoon.reflect.reference.CtTypeReference<T> getTypeReference(org.eclipse.jdt.internal.compiler.lookup.TypeBinding binding) {
        if (binding == null) {
            return null;
        }
        spoon.reflect.reference.CtTypeReference<?> ref = null;
        // When we define a nested class in a method and when the enclosing class of this method
        // is a parameterized type binding, JDT give a ParameterizedTypeBinding for the nested class
        // and hide the real class in actualType().
        // if the type parameter has a super class other than java.lang.Object, we get it
        // superClass.superclass() is null if it's java.lang.Object
        // this case could happen with Enum<E extends Enum<E>> for example:
        // in that case we only want to have E -> Enum -> E
        // to conserve the same behavior as JavaReflectionTreeBuilder
        // if the type parameter has a super interface, then we'll get it too, as a superclass
        // type parameter can only extends an interface or a class, so we don't make the distinction
        // in Spoon. Moreover we can only have one extends in a type parameter.
        // if it's object we can ignore it
        // always create new TypeReference, because clonning from a cache clones invalid SourcePosition
        // if(((SourceTypeBinding) binding).typeVariables!=null &&
        // ((SourceTypeBinding) binding).typeVariables.length>0){
        // for (TypeBinding b : ((SourceTypeBinding)
        // binding).typeVariables) {
        // ref.getActualTypeArguments().add(getTypeReference(b));
        // }
        // }
        // JDT can't resolve the type of this binding and we only have a string.
        // In this case, we return a type Object because we can't know more about it.
        // Spoon is able to analyze also without the classpath
        if (binding instanceof org.eclipse.jdt.internal.compiler.lookup.RawTypeBinding) {
            ref = getTypeReference(((org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding) (binding)).genericType());
        }// When we define a nested class in a method and when the enclosing class of this method
        // is a parameterized type binding, JDT give a ParameterizedTypeBinding for the nested class
        // and hide the real class in actualType().
        // if the type parameter has a super class other than java.lang.Object, we get it
        // superClass.superclass() is null if it's java.lang.Object
        // this case could happen with Enum<E extends Enum<E>> for example:
        // in that case we only want to have E -> Enum -> E
        // to conserve the same behavior as JavaReflectionTreeBuilder
        // if the type parameter has a super interface, then we'll get it too, as a superclass
        // type parameter can only extends an interface or a class, so we don't make the distinction
        // in Spoon. Moreover we can only have one extends in a type parameter.
        // if it's object we can ignore it
        // always create new TypeReference, because clonning from a cache clones invalid SourcePosition
        // if(((SourceTypeBinding) binding).typeVariables!=null &&
        // ((SourceTypeBinding) binding).typeVariables.length>0){
        // for (TypeBinding b : ((SourceTypeBinding)
        // binding).typeVariables) {
        // ref.getActualTypeArguments().add(getTypeReference(b));
        // }
        // }
        // JDT can't resolve the type of this binding and we only have a string.
        // In this case, we return a type Object because we can't know more about it.
        // Spoon is able to analyze also without the classpath
        else
            if (binding instanceof org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding) {
                if (((binding.actualType()) != null) && ((binding.actualType()) instanceof org.eclipse.jdt.internal.compiler.lookup.LocalTypeBinding)) {
                    ref = getTypeReference(binding.actualType());
                }else {
                    ref = this.jdtTreeBuilder.getFactory().Core().createTypeReference();
                    this.exploringParameterizedBindings.put(binding, ref);
                    if (binding.isAnonymousType()) {
                        ref.setSimpleName("");
                    }else {
                        ref.setSimpleName(java.lang.String.valueOf(binding.sourceName()));
                        if ((binding.enclosingType()) != null) {
                            ref.setDeclaringType(getTypeReference(binding.enclosingType()));
                        }else {
                            ref.setPackage(getPackageReference(binding.getPackage()));
                        }
                    }
                }
                if ((binding.actualType()) instanceof org.eclipse.jdt.internal.compiler.lookup.MissingTypeBinding) {
                    ref = getTypeReference(binding.actualType());
                }
                if ((((org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding) (binding)).arguments) != null) {
                    for (org.eclipse.jdt.internal.compiler.lookup.TypeBinding b : ((org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding) (binding)).arguments) {
                        if (bindingCache.containsKey(b)) {
                            ref.addActualTypeArgument(getCtCircularTypeReference(b));
                        }else {
                            if (!(this.exploringParameterizedBindings.containsKey(b))) {
                                this.exploringParameterizedBindings.put(b, null);
                                spoon.reflect.reference.CtTypeReference typeRefB = getTypeReference(b);
                                this.exploringParameterizedBindings.put(b, typeRefB);
                                ref.addActualTypeArgument(typeRefB);
                            }else {
                                spoon.reflect.reference.CtTypeReference typeRefB = this.exploringParameterizedBindings.get(b);
                                if (typeRefB != null) {
                                    ref.addActualTypeArgument(typeRefB.clone());
                                }
                            }
                        }
                    }
                }
            }else
                if (binding instanceof org.eclipse.jdt.internal.compiler.lookup.MissingTypeBinding) {
                    ref = this.jdtTreeBuilder.getFactory().Core().createTypeReference();
                    ref.setSimpleName(new java.lang.String(binding.sourceName()));
                    ref.setPackage(getPackageReference(binding.getPackage()));
                    if (!(this.jdtTreeBuilder.getContextBuilder().ignoreComputeImports)) {
                        final spoon.reflect.reference.CtReference declaring = this.getDeclaringReferenceFromImports(binding.sourceName());
                        if (declaring instanceof spoon.reflect.reference.CtPackageReference) {
                            ref.setPackage(((spoon.reflect.reference.CtPackageReference) (declaring)));
                        }else
                            if (declaring instanceof spoon.reflect.reference.CtTypeReference) {
                                ref.setDeclaringType(((spoon.reflect.reference.CtTypeReference) (declaring)));
                            }

                    }
                }else
                    if (binding instanceof org.eclipse.jdt.internal.compiler.lookup.BinaryTypeBinding) {
                        ref = this.jdtTreeBuilder.getFactory().Core().createTypeReference();
                        if ((binding.enclosingType()) != null) {
                            ref.setDeclaringType(getTypeReference(binding.enclosingType()));
                        }else {
                            ref.setPackage(getPackageReference(binding.getPackage()));
                        }
                        ref.setSimpleName(new java.lang.String(binding.sourceName()));
                    }else
                        if (binding instanceof org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding) {
                            boolean oldBounds = bounds;
                            if (binding instanceof org.eclipse.jdt.internal.compiler.lookup.CaptureBinding) {
                                ref = this.jdtTreeBuilder.getFactory().Core().createWildcardReference();
                                bounds = true;
                            }else {
                                org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding typeParamBinding = ((org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding) (binding));
                                org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding superClass = typeParamBinding.superclass;
                                org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding[] superInterfaces = typeParamBinding.superInterfaces();
                                spoon.reflect.reference.CtTypeReference refSuperClass = null;
                                if ((superClass != null) && (!((superClass.superclass()) == null))) {
                                    if ((!(superClass instanceof org.eclipse.jdt.internal.compiler.lookup.ParameterizedTypeBinding)) || (!(this.exploringParameterizedBindings.containsKey(superClass)))) {
                                        refSuperClass = this.getTypeReference(superClass);
                                    }
                                }else
                                    if ((superInterfaces != null) && ((superInterfaces.length) == 1)) {
                                        refSuperClass = this.getTypeReference(superInterfaces[0]);
                                    }

                                ref = this.jdtTreeBuilder.getFactory().Core().createTypeParameterReference();
                                ref.setSimpleName(new java.lang.String(binding.sourceName()));
                                if (refSuperClass != null) {
                                    ((spoon.reflect.reference.CtTypeParameterReference) (ref)).addBound(refSuperClass);
                                }
                            }
                            org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding b = ((org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding) (binding));
                            if (bounds) {
                                if ((b instanceof org.eclipse.jdt.internal.compiler.lookup.CaptureBinding) && ((((org.eclipse.jdt.internal.compiler.lookup.CaptureBinding) (b)).wildcard) != null)) {
                                    bounds = oldBounds;
                                    return getTypeReference(((org.eclipse.jdt.internal.compiler.lookup.CaptureBinding) (b)).wildcard);
                                }else
                                    if (((b.superclass) != null) && ((b.firstBound) == (b.superclass))) {
                                        bounds = false;
                                        bindingCache.put(binding, ref);
                                        ((spoon.reflect.reference.CtTypeParameterReference) (ref)).setBoundingType(getTypeReference(b.superclass));
                                        bounds = oldBounds;
                                    }

                            }
                            if (((bounds) && ((b.superInterfaces) != null)) && ((b.superInterfaces) != (org.eclipse.jdt.internal.compiler.lookup.Binding.NO_SUPERINTERFACES))) {
                                bounds = false;
                                bindingCache.put(binding, ref);
                                java.util.List<spoon.reflect.reference.CtTypeReference<?>> bounds = new java.util.ArrayList<>();
                                spoon.reflect.reference.CtTypeParameterReference typeParameterReference = ((spoon.reflect.reference.CtTypeParameterReference) (ref));
                                if (!(typeParameterReference.isDefaultBoundingType())) {
                                    bounds.add(typeParameterReference.getBoundingType());
                                }
                                for (org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding superInterface : b.superInterfaces) {
                                    bounds.add(getTypeReference(superInterface));
                                }
                                ((spoon.reflect.reference.CtTypeParameterReference) (ref)).setBoundingType(this.jdtTreeBuilder.getFactory().Type().createIntersectionTypeReferenceWithBounds(bounds));
                            }
                            if (binding instanceof org.eclipse.jdt.internal.compiler.lookup.CaptureBinding) {
                                bounds = false;
                            }
                        }else
                            if (binding instanceof org.eclipse.jdt.internal.compiler.lookup.BaseTypeBinding) {
                                java.lang.String name = new java.lang.String(binding.sourceName());
                                ref = this.jdtTreeBuilder.getFactory().Core().createTypeReference();
                                ref.setSimpleName(name);
                            }else
                                if (binding instanceof org.eclipse.jdt.internal.compiler.lookup.WildcardBinding) {
                                    org.eclipse.jdt.internal.compiler.lookup.WildcardBinding wildcardBinding = ((org.eclipse.jdt.internal.compiler.lookup.WildcardBinding) (binding));
                                    ref = this.jdtTreeBuilder.getFactory().Core().createWildcardReference();
                                    if (((wildcardBinding.boundKind) == (org.eclipse.jdt.internal.compiler.ast.Wildcard.SUPER)) && (ref instanceof spoon.reflect.reference.CtTypeParameterReference)) {
                                        ((spoon.reflect.reference.CtTypeParameterReference) (ref)).setUpper(false);
                                    }
                                    if (((wildcardBinding.bound) != null) && (ref instanceof spoon.reflect.reference.CtTypeParameterReference)) {
                                        if (bindingCache.containsKey(wildcardBinding.bound)) {
                                            ((spoon.reflect.reference.CtTypeParameterReference) (ref)).setBoundingType(getCtCircularTypeReference(wildcardBinding.bound));
                                        }else {
                                            ((spoon.reflect.reference.CtTypeParameterReference) (ref)).setBoundingType(getTypeReference(((org.eclipse.jdt.internal.compiler.lookup.WildcardBinding) (binding)).bound));
                                        }
                                    }
                                }else
                                    if (binding instanceof org.eclipse.jdt.internal.compiler.lookup.LocalTypeBinding) {
                                        ref = this.jdtTreeBuilder.getFactory().Core().createTypeReference();
                                        if (binding.isAnonymousType()) {
                                            ref.setSimpleName(spoon.support.compiler.jdt.JDTTreeBuilderHelper.computeAnonymousName(((org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding) (binding)).constantPoolName()));
                                            ref.setDeclaringType(getTypeReference(binding.enclosingType()));
                                        }else {
                                            ref.setSimpleName(new java.lang.String(binding.sourceName()));
                                            if ((((((org.eclipse.jdt.internal.compiler.lookup.LocalTypeBinding) (binding)).enclosingMethod) == null) && ((binding.enclosingType()) != null)) && ((binding.enclosingType()) instanceof org.eclipse.jdt.internal.compiler.lookup.LocalTypeBinding)) {
                                                ref.setDeclaringType(getTypeReference(binding.enclosingType()));
                                            }else
                                                if ((binding.enclosingMethod()) != null) {
                                                    ref.setSimpleName(spoon.support.compiler.jdt.JDTTreeBuilderHelper.computeAnonymousName(((org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding) (binding)).constantPoolName()));
                                                    ref.setDeclaringType(getTypeReference(binding.enclosingType()));
                                                }

                                        }
                                    }else
                                        if (binding instanceof org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding) {
                                            ref = this.jdtTreeBuilder.getFactory().Core().createTypeReference();
                                            if (binding.isAnonymousType()) {
                                                ref.setSimpleName(spoon.support.compiler.jdt.JDTTreeBuilderHelper.computeAnonymousName(((org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding) (binding)).constantPoolName()));
                                                ref.setDeclaringType(getTypeReference(binding.enclosingType()));
                                            }else {
                                                ref.setSimpleName(new java.lang.String(binding.sourceName()));
                                                if ((binding.enclosingType()) != null) {
                                                    ref.setDeclaringType(getTypeReference(binding.enclosingType()));
                                                }else {
                                                    ref.setPackage(getPackageReference(binding.getPackage()));
                                                }
                                            }
                                        }else
                                            if (binding instanceof org.eclipse.jdt.internal.compiler.lookup.ArrayBinding) {
                                                spoon.reflect.reference.CtArrayTypeReference<java.lang.Object> arrayref;
                                                arrayref = this.jdtTreeBuilder.getFactory().Core().createArrayTypeReference();
                                                ref = arrayref;
                                                for (int i = 1; i < (binding.dimensions()); i++) {
                                                    spoon.reflect.reference.CtArrayTypeReference<java.lang.Object> tmp = this.jdtTreeBuilder.getFactory().Core().createArrayTypeReference();
                                                    arrayref.setComponentType(tmp);
                                                    arrayref = tmp;
                                                }
                                                arrayref.setComponentType(getTypeReference(binding.leafComponentType()));
                                            }else
                                                if (binding instanceof org.eclipse.jdt.internal.compiler.lookup.PolyTypeBinding) {
                                                    ref = this.jdtTreeBuilder.getFactory().Type().objectType();
                                                }else
                                                    if (binding instanceof org.eclipse.jdt.internal.compiler.lookup.ProblemReferenceBinding) {
                                                        ref = this.jdtTreeBuilder.getFactory().Core().createTypeReference();
                                                        ref.setSimpleName(new java.lang.String(binding.readableName()));
                                                        final spoon.reflect.reference.CtReference declaring = this.getDeclaringReferenceFromImports(binding.sourceName());
                                                        setPackageOrDeclaringType(ref, declaring);
                                                    }else
                                                        if (binding instanceof spoon.support.compiler.jdt.JDTTreeBuilder.SpoonReferenceBinding) {
                                                            ref = this.jdtTreeBuilder.getFactory().Core().createTypeReference();
                                                            ref.setSimpleName(new java.lang.String(binding.sourceName()));
                                                            ref.setDeclaringType(getTypeReference(binding.enclosingType()));
                                                        }else
                                                            if (binding instanceof org.eclipse.jdt.internal.compiler.lookup.IntersectionTypeBinding18) {
                                                                java.util.List<spoon.reflect.reference.CtTypeReference<?>> bounds = new java.util.ArrayList<>();
                                                                for (org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding superInterface : binding.getIntersectingTypes()) {
                                                                    bounds.add(getTypeReference(superInterface));
                                                                }
                                                                ref = this.jdtTreeBuilder.getFactory().Type().createIntersectionTypeReferenceWithBounds(bounds);
                                                            }else {
                                                                throw new java.lang.RuntimeException(((("Unknown TypeBinding: " + (binding.getClass())) + " ") + binding));
                                                            }













        bindingCache.remove(binding);
        this.exploringParameterizedBindings.remove(binding);
        return ((spoon.reflect.reference.CtTypeReference<T>) (ref));
    }

    private spoon.reflect.reference.CtTypeReference<?> getCtCircularTypeReference(org.eclipse.jdt.internal.compiler.lookup.TypeBinding b) {
        return bindingCache.get(b).clone();
    }

    @java.lang.SuppressWarnings("unchecked")
    <T> spoon.reflect.reference.CtVariableReference<T> getVariableReference(org.eclipse.jdt.internal.compiler.lookup.MethodBinding methbin) {
        spoon.reflect.reference.CtFieldReference<T> ref = this.jdtTreeBuilder.getFactory().Core().createFieldReference();
        ref.setSimpleName(new java.lang.String(methbin.selector));
        ref.setType(((spoon.reflect.reference.CtTypeReference<T>) (getTypeReference(methbin.returnType))));
        if ((methbin.declaringClass) != null) {
            ref.setDeclaringType(getTypeReference(methbin.declaringClass));
        }else {
            ref.setDeclaringType(ref.getType());
        }
        return ref;
    }

    <T> spoon.reflect.reference.CtFieldReference<T> getVariableReference(org.eclipse.jdt.internal.compiler.lookup.FieldBinding varbin) {
        spoon.reflect.reference.CtFieldReference<T> ref = this.jdtTreeBuilder.getFactory().Core().createFieldReference();
        if (varbin == null) {
            return ref;
        }
        ref.setSimpleName(new java.lang.String(varbin.name));
        ref.setType(this.<T>getTypeReference(varbin.type));
        if ((varbin.declaringClass) != null) {
            ref.setDeclaringType(getTypeReference(varbin.declaringClass));
        }else {
            ref.setDeclaringType(((ref.getType()) == null ? null : ref.getType().clone()));
        }
        ref.setFinal(varbin.isFinal());
        ref.setStatic((((varbin.modifiers) & (org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants.AccStatic)) != 0));
        return ref;
    }

    <T> spoon.reflect.reference.CtFieldReference<T> getVariableReference(org.eclipse.jdt.internal.compiler.lookup.FieldBinding fieldBinding, char[] tokens) {
        final spoon.reflect.reference.CtFieldReference<T> ref = getVariableReference(fieldBinding);
        if (fieldBinding != null) {
            return ref;
        }
        ref.setSimpleName(org.eclipse.jdt.core.compiler.CharOperation.charToString(tokens));
        return ref;
    }

    @java.lang.SuppressWarnings("unchecked")
    <T> spoon.reflect.reference.CtVariableReference<T> getVariableReference(org.eclipse.jdt.internal.compiler.lookup.VariableBinding varbin) {
        // unknown VariableBinding, the caller must do something
        if (varbin instanceof org.eclipse.jdt.internal.compiler.lookup.FieldBinding) {
            return getVariableReference(((org.eclipse.jdt.internal.compiler.lookup.FieldBinding) (varbin)));
        }// unknown VariableBinding, the caller must do something
        else
            if (varbin instanceof org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding) {
                final org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding localVariableBinding = ((org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding) (varbin));
                if (((localVariableBinding.declaration) instanceof org.eclipse.jdt.internal.compiler.ast.Argument) && ((localVariableBinding.declaringScope) instanceof org.eclipse.jdt.internal.compiler.lookup.MethodScope)) {
                    spoon.reflect.reference.CtParameterReference<T> ref = this.jdtTreeBuilder.getFactory().Core().createParameterReference();
                    ref.setSimpleName(new java.lang.String(varbin.name));
                    ref.setType(((spoon.reflect.reference.CtTypeReference<T>) (getTypeReference(varbin.type))));
                    final org.eclipse.jdt.internal.compiler.impl.ReferenceContext referenceContext = localVariableBinding.declaringScope.referenceContext();
                    return ref;
                }else
                    if ((localVariableBinding.declaration.binding) instanceof org.eclipse.jdt.internal.compiler.lookup.CatchParameterBinding) {
                        spoon.reflect.reference.CtCatchVariableReference<T> ref = this.jdtTreeBuilder.getFactory().Core().createCatchVariableReference();
                        ref.setSimpleName(new java.lang.String(varbin.name));
                        spoon.reflect.reference.CtTypeReference<T> ref2 = getTypeReference(varbin.type);
                        ref.setType(ref2);
                        return ref;
                    }else {
                        spoon.reflect.reference.CtLocalVariableReference<T> ref = this.jdtTreeBuilder.getFactory().Core().createLocalVariableReference();
                        ref.setSimpleName(new java.lang.String(varbin.name));
                        spoon.reflect.reference.CtTypeReference<T> ref2 = getTypeReference(varbin.type);
                        ref.setType(ref2);
                        return ref;
                    }

            }else {
                return null;
            }

    }

    <T> spoon.reflect.reference.CtVariableReference<T> getVariableReference(org.eclipse.jdt.internal.compiler.lookup.ProblemBinding binding) {
        spoon.reflect.reference.CtFieldReference<T> ref = this.jdtTreeBuilder.getFactory().Core().createFieldReference();
        if (binding == null) {
            return ref;
        }
        ref.setSimpleName(new java.lang.String(binding.name));
        ref.setType(((spoon.reflect.reference.CtTypeReference<T>) (getTypeReference(binding.searchType))));
        return ref;
    }

    java.util.List<spoon.reflect.reference.CtTypeReference<?>> getBoundedTypesReferences(org.eclipse.jdt.internal.compiler.lookup.TypeBinding[] genericTypeArguments) {
        java.util.List<spoon.reflect.reference.CtTypeReference<?>> res = new java.util.ArrayList<>(genericTypeArguments.length);
        for (org.eclipse.jdt.internal.compiler.lookup.TypeBinding tb : genericTypeArguments) {
            res.add(getBoundedTypeReference(tb));
        }
        return res;
    }

    /**
     * Sets {@code declaring} as inner of {@code ref}, as either the package or the declaring type
     */
    void setPackageOrDeclaringType(spoon.reflect.reference.CtTypeReference<?> ref, spoon.reflect.reference.CtReference declaring) {
        // sometimes JDT does not provide the information that ref comes from java.lang
        // it seems to occurs in particular with anonymous inner classes: see #1307
        // In that case, we try to load the class to check if it belongs to java.lang
        // in that case we consider the package should be the same as the current one. Fix #1293
        if (declaring instanceof spoon.reflect.reference.CtPackageReference) {
            ref.setPackage(((spoon.reflect.reference.CtPackageReference) (declaring)));
        }// sometimes JDT does not provide the information that ref comes from java.lang
        // it seems to occurs in particular with anonymous inner classes: see #1307
        // In that case, we try to load the class to check if it belongs to java.lang
        // in that case we consider the package should be the same as the current one. Fix #1293
        else
            if (declaring instanceof spoon.reflect.reference.CtTypeReference) {
                ref.setDeclaringType(((spoon.reflect.reference.CtTypeReference) (declaring)));
            }else
                if (declaring == null) {
                    try {
                        java.lang.Class.forName(("java.lang." + (ref.getSimpleName())));
                        spoon.reflect.reference.CtPackageReference javaLangPackageReference = this.jdtTreeBuilder.getFactory().Core().createPackageReference();
                        javaLangPackageReference.setSimpleName("java.lang");
                        ref.setPackage(javaLangPackageReference);
                    } catch (java.lang.NoClassDefFoundError | java.lang.ClassNotFoundException e) {
                        ref.setPackage(jdtTreeBuilder.getContextBuilder().compilationUnitSpoon.getDeclaredPackage().getReference());
                    }
                }else {
                    throw new java.lang.AssertionError(((("unexpected declaring type: " + (declaring.getClass())) + " of ") + declaring));
                }


    }

    /**
     * In noclasspath, lambda doesn't have always a binding for their variables accesses in their block/expression.
     * Here, we make the job of JDT and bind their variables accesses to their parameters.
     *
     * @param singleNameReference
     * 		Name of the variable access.
     * @return executable reference which corresponds to the lambda.
     */
    public spoon.reflect.reference.CtExecutableReference<?> getLambdaExecutableReference(org.eclipse.jdt.internal.compiler.ast.SingleNameReference singleNameReference) {
        spoon.support.compiler.jdt.ASTPair potentialLambda = null;
        for (spoon.support.compiler.jdt.ASTPair astPair : jdtTreeBuilder.getContextBuilder().stack) {
            if ((astPair.node) instanceof org.eclipse.jdt.internal.compiler.ast.LambdaExpression) {
                potentialLambda = astPair;
                // stop at innermost lambda, fixes #1100
                break;
            }
        }
        if (potentialLambda == null) {
            return null;
        }
        org.eclipse.jdt.internal.compiler.ast.LambdaExpression lambdaJDT = ((org.eclipse.jdt.internal.compiler.ast.LambdaExpression) (potentialLambda.node));
        for (org.eclipse.jdt.internal.compiler.ast.Argument argument : lambdaJDT.arguments()) {
            if (org.eclipse.jdt.core.compiler.CharOperation.equals(argument.name, singleNameReference.token)) {
                spoon.reflect.reference.CtTypeReference<?> declaringType = null;
                if ((lambdaJDT.enclosingScope) instanceof org.eclipse.jdt.internal.compiler.lookup.MethodScope) {
                    declaringType = jdtTreeBuilder.getReferencesBuilder().getTypeReference(((org.eclipse.jdt.internal.compiler.lookup.MethodScope) (lambdaJDT.enclosingScope)).parent.enclosingSourceType());
                }
                spoon.reflect.code.CtLambda<?> ctLambda = ((spoon.reflect.code.CtLambda<?>) (potentialLambda.element));
                java.util.List<spoon.reflect.reference.CtTypeReference<?>> parametersType = new java.util.ArrayList<>();
                java.util.List<spoon.reflect.declaration.CtParameter<?>> parameters = ctLambda.getParameters();
                for (spoon.reflect.declaration.CtParameter<?> parameter : parameters) {
                    parametersType.add(((parameter.getType()) != null ? parameter.getType().clone() : // it's the best match :(
                    jdtTreeBuilder.getFactory().Type().OBJECT.clone()));
                }
                return jdtTreeBuilder.getFactory().Executable().createReference(declaringType, ctLambda.getType(), ctLambda.getSimpleName(), parametersType);
            }
        }
        return null;
    }

    public spoon.reflect.reference.CtModuleReference getModuleReference(org.eclipse.jdt.internal.compiler.ast.ModuleReference moduleReference) {
        java.lang.String moduleName = new java.lang.String(moduleReference.moduleName);
        spoon.reflect.declaration.CtModule module = this.jdtTreeBuilder.getFactory().Module().getModule(moduleName);
        if (module == null) {
            spoon.reflect.reference.CtModuleReference ctModuleReference = this.jdtTreeBuilder.getFactory().Core().createModuleReference();
            ctModuleReference.setSimpleName(moduleName);
            return ctModuleReference;
        }else {
            return module.getReference();
        }
    }
}

