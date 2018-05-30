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


/**
 * Helper class for JDTTreeBuilder. Package visible to reduce API surface.
 */
class JDTTreeBuilderQuery {
    private JDTTreeBuilderQuery() {
    }

    /**
     * Searches a type from an entry-point according to a simple name.
     *
     * @param type
     * 		Entry-point to search.
     * @param simpleName
     * 		Expected type name.
     * @return type binding.
     */
    static org.eclipse.jdt.internal.compiler.lookup.TypeBinding searchTypeBinding(org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding type, java.lang.String simpleName) {
        if ((simpleName == null) || (type == null)) {
            return null;
        }
        if ((type.memberTypes()) != null) {
            for (org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding memberType : type.memberTypes()) {
                if (simpleName.equals(org.eclipse.jdt.core.compiler.CharOperation.charToString(memberType.sourceName()))) {
                    return memberType;
                }
            }
        }
        return spoon.support.compiler.jdt.JDTTreeBuilderQuery.searchTypeBinding(type.superclass(), simpleName);
    }

    /**
     * Searches a type used in units declared in a compilation unit.
     *
     * @param qualifiedName
     * 		Qualified name of the expected type.
     * @param unitsToProcess
     * 		Search the type expected in units.
     * @return type binding.
     */
    static org.eclipse.jdt.internal.compiler.lookup.TypeBinding searchTypeBinding(java.lang.String qualifiedName, org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration[] unitsToProcess) {
        if (qualifiedName == null) {
            return null;
        }
        for (org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration unitToProcess : unitsToProcess) {
            for (org.eclipse.jdt.internal.compiler.ast.TypeDeclaration type : unitToProcess.types) {
                if (qualifiedName.equals(org.eclipse.jdt.core.compiler.CharOperation.toString(type.binding.compoundName))) {
                    return type.binding;
                }
                if ((type.memberTypes) != null) {
                    for (org.eclipse.jdt.internal.compiler.ast.TypeDeclaration memberType : type.memberTypes) {
                        if (qualifiedName.equals(org.eclipse.jdt.core.compiler.CharOperation.toString(memberType.binding.compoundName))) {
                            return type.binding;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Searches a type declared in imports.
     *
     * @param typeName
     * 		Expected type name.
     * @param imports
     * 		Search the type in imports.
     * @return qualified name of the expected type.
     */
    static java.lang.String searchType(java.lang.String typeName, org.eclipse.jdt.internal.compiler.ast.ImportReference[] imports) {
        if (typeName == null) {
            return null;
        }else
            if (imports == null) {
                return null;
            }

        for (org.eclipse.jdt.internal.compiler.ast.ImportReference anImport : imports) {
            final java.lang.String importType = org.eclipse.jdt.core.compiler.CharOperation.charToString(anImport.getImportName()[((anImport.getImportName().length) - 1)]);
            if ((importType != null) && (importType.equals(typeName))) {
                return org.eclipse.jdt.core.compiler.CharOperation.toString(anImport.getImportName());
            }
        }
        return null;
    }

    /**
     * Searches a package used in units declared in a compilation unit.
     *
     * @param packageName
     * 		Package name.
     * @param unitsToProcess
     * 		Search the package expected in units.
     * @return import reference which correspond to the package expected.
     */
    static org.eclipse.jdt.internal.compiler.ast.ImportReference searchPackage(char[][] packageName, org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration[] unitsToProcess) {
        for (org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration unit : unitsToProcess) {
            final org.eclipse.jdt.internal.compiler.ast.ImportReference currentPackage = unit.currentPackage;
            if (currentPackage == null) {
                continue;
            }
            final char[][] tokens = currentPackage.tokens;
            if ((packageName.length) > (tokens.length)) {
                continue;
            }
            boolean isFound = true;
            for (int i = 0; i < (packageName.length); i++) {
                char[] chars = packageName[i];
                if (!(org.eclipse.jdt.core.compiler.CharOperation.equals(chars, tokens[i]))) {
                    isFound = false;
                    break;
                }
            }
            if (isFound) {
                return currentPackage;
            }
        }
        return null;
    }

    /**
     * Checks in an annotation if a given type is present.
     *
     * @param a
     * 		An annotation.
     * @param elementType
     * 		Expected element type of the annotation.
     * @return true if the annotation is compatible with the given element type.
     */
    static boolean hasAnnotationWithType(org.eclipse.jdt.internal.compiler.ast.Annotation a, spoon.reflect.declaration.CtAnnotatedElementType elementType) {
        if ((a.resolvedType) == null) {
            return false;
        }
        // JLS says:
        // "If an annotation of type java.lang.annotation.Target is not present on the declaration of an annotation type T,
        // then T is applicable in all declaration contexts except type parameter declarations, and in no type contexts."
        boolean shouldTargetAnnotationExists = (elementType == (spoon.reflect.declaration.CtAnnotatedElementType.TYPE_USE)) || (elementType == (spoon.reflect.declaration.CtAnnotatedElementType.TYPE_PARAMETER));
        boolean targetAnnotationExists = false;
        for (org.eclipse.jdt.internal.compiler.lookup.AnnotationBinding annotation : a.resolvedType.getAnnotations()) {
            if (!("Target".equals(org.eclipse.jdt.core.compiler.CharOperation.charToString(annotation.getAnnotationType().sourceName())))) {
                continue;
            }
            targetAnnotationExists = true;
            java.lang.Object value = annotation.getElementValuePairs()[0].value;
            if (value == null) {
                continue;
            }
            if ((value instanceof org.eclipse.jdt.internal.compiler.lookup.FieldBinding) && (elementType.name().equals(org.eclipse.jdt.core.compiler.CharOperation.charToString(((org.eclipse.jdt.internal.compiler.lookup.FieldBinding) (value)).name)))) {
                return true;
            }
            if (value.getClass().isArray()) {
                java.lang.Object[] fields = ((java.lang.Object[]) (value));
                for (java.lang.Object field : fields) {
                    if ((field instanceof org.eclipse.jdt.internal.compiler.lookup.FieldBinding) && (elementType.name().equals(org.eclipse.jdt.core.compiler.CharOperation.charToString(((org.eclipse.jdt.internal.compiler.lookup.FieldBinding) (field)).name)))) {
                        return true;
                    }
                }
            }
        }
        // true here means that the target annotation is not mandatory and we have not found it
        return (!shouldTargetAnnotationExists) && (!targetAnnotationExists);
    }

    /**
     * Checks if the qualified name reference is a problem field binding and have a valid field.
     *
     * @param qualifiedNameReference
     * 		Reference which should contains a problem field binding.
     * @return true if the qualified name reference is a valid problem field binding.
     */
    static boolean isValidProblemBindingField(org.eclipse.jdt.internal.compiler.ast.QualifiedNameReference qualifiedNameReference) {
        return ((((qualifiedNameReference.binding) instanceof org.eclipse.jdt.internal.compiler.lookup.ProblemFieldBinding) && (!(((org.eclipse.jdt.internal.compiler.lookup.FieldBinding) (qualifiedNameReference.binding)).declaringClass.isAnonymousType()))) && (((qualifiedNameReference.tokens.length) - 1) == (((org.eclipse.jdt.internal.compiler.lookup.FieldBinding) (qualifiedNameReference.binding)).declaringClass.compoundName.length))) && (org.eclipse.jdt.core.compiler.CharOperation.equals(org.eclipse.jdt.core.compiler.CharOperation.subarray(qualifiedNameReference.tokens, 0, ((qualifiedNameReference.tokens.length) - 1)), ((org.eclipse.jdt.internal.compiler.lookup.FieldBinding) (qualifiedNameReference.binding)).declaringClass.compoundName));
    }

    /**
     * Checks if the last node in the stack in the context is an assignment and have a lhs equals to the given expression.
     *
     * @param context
     * 		Context of the {@link JDTTreeBuilder}.
     * @param lhs
     * 		Potential lhs of the assignment.
     * @return true if the lhs is equals to the given expression.
     */
    static boolean isLhsAssignment(spoon.support.compiler.jdt.ContextBuilder context, org.eclipse.jdt.internal.compiler.ast.Expression lhs) {
        return ((context.stack.peek().node) instanceof org.eclipse.jdt.internal.compiler.ast.Assignment) && (((org.eclipse.jdt.internal.compiler.ast.Assignment) (context.stack.peek().node)).lhs.equals(lhs));
    }

    /**
     * Converts the unary operator from JDT to Spoon.
     *
     * @param operator
     * 		Identifier of the unary operator.
     * @return enum value of {@link UnaryOperatorKind}.
     */
    static spoon.reflect.code.UnaryOperatorKind getUnaryOperator(int operator) {
        switch (operator) {
            case org.eclipse.jdt.internal.compiler.ast.OperatorIds.PLUS :
                return spoon.reflect.code.UnaryOperatorKind.POS;
            case org.eclipse.jdt.internal.compiler.ast.OperatorIds.MINUS :
                return spoon.reflect.code.UnaryOperatorKind.NEG;
            case org.eclipse.jdt.internal.compiler.ast.OperatorIds.NOT :
                return spoon.reflect.code.UnaryOperatorKind.NOT;
            case org.eclipse.jdt.internal.compiler.ast.OperatorIds.TWIDDLE :
                return spoon.reflect.code.UnaryOperatorKind.COMPL;
        }
        return null;
    }

    /**
     * Converts the binary operator from JDT to Spoon.
     *
     * @param operator
     * 		Identifier of the binary operator.
     * @return enum value of {@link BinaryOperatorKind}.
     */
    static spoon.reflect.code.BinaryOperatorKind getBinaryOperatorKind(int operator) {
        switch (operator) {
            case org.eclipse.jdt.internal.compiler.ast.OperatorIds.EQUAL_EQUAL :
                return spoon.reflect.code.BinaryOperatorKind.EQ;
            case org.eclipse.jdt.internal.compiler.ast.OperatorIds.LESS_EQUAL :
                return spoon.reflect.code.BinaryOperatorKind.LE;
            case org.eclipse.jdt.internal.compiler.ast.OperatorIds.GREATER_EQUAL :
                return spoon.reflect.code.BinaryOperatorKind.GE;
            case org.eclipse.jdt.internal.compiler.ast.OperatorIds.NOT_EQUAL :
                return spoon.reflect.code.BinaryOperatorKind.NE;
            case org.eclipse.jdt.internal.compiler.ast.OperatorIds.LEFT_SHIFT :
                return spoon.reflect.code.BinaryOperatorKind.SL;
            case org.eclipse.jdt.internal.compiler.ast.OperatorIds.RIGHT_SHIFT :
                return spoon.reflect.code.BinaryOperatorKind.SR;
            case org.eclipse.jdt.internal.compiler.ast.OperatorIds.UNSIGNED_RIGHT_SHIFT :
                return spoon.reflect.code.BinaryOperatorKind.USR;
            case org.eclipse.jdt.internal.compiler.ast.OperatorIds.OR_OR :
                return spoon.reflect.code.BinaryOperatorKind.OR;
            case org.eclipse.jdt.internal.compiler.ast.OperatorIds.AND_AND :
                return spoon.reflect.code.BinaryOperatorKind.AND;
            case org.eclipse.jdt.internal.compiler.ast.OperatorIds.PLUS :
                return spoon.reflect.code.BinaryOperatorKind.PLUS;
            case org.eclipse.jdt.internal.compiler.ast.OperatorIds.MINUS :
                return spoon.reflect.code.BinaryOperatorKind.MINUS;
            case org.eclipse.jdt.internal.compiler.ast.OperatorIds.NOT :
                return spoon.reflect.code.BinaryOperatorKind.NE;
            case org.eclipse.jdt.internal.compiler.ast.OperatorIds.REMAINDER :
                return spoon.reflect.code.BinaryOperatorKind.MOD;
            case org.eclipse.jdt.internal.compiler.ast.OperatorIds.XOR :
                return spoon.reflect.code.BinaryOperatorKind.BITXOR;
            case org.eclipse.jdt.internal.compiler.ast.OperatorIds.AND :
                return spoon.reflect.code.BinaryOperatorKind.BITAND;
            case org.eclipse.jdt.internal.compiler.ast.OperatorIds.MULTIPLY :
                return spoon.reflect.code.BinaryOperatorKind.MUL;
            case org.eclipse.jdt.internal.compiler.ast.OperatorIds.OR :
                return spoon.reflect.code.BinaryOperatorKind.BITOR;
            case org.eclipse.jdt.internal.compiler.ast.OperatorIds.DIVIDE :
                return spoon.reflect.code.BinaryOperatorKind.DIV;
            case org.eclipse.jdt.internal.compiler.ast.OperatorIds.GREATER :
                return spoon.reflect.code.BinaryOperatorKind.GT;
            case org.eclipse.jdt.internal.compiler.ast.OperatorIds.LESS :
                return spoon.reflect.code.BinaryOperatorKind.LT;
            case org.eclipse.jdt.internal.compiler.ast.OperatorIds.QUESTIONCOLON :
                throw new java.lang.RuntimeException("Unknown operator");
            case org.eclipse.jdt.internal.compiler.ast.OperatorIds.EQUAL :
                return spoon.reflect.code.BinaryOperatorKind.EQ;
        }
        return null;
    }

    /**
     * Converts the modifier from JDT to Spoon.
     *
     * @param modifier
     * 		Identifier of the modifier.
     * @param implicit
     * 		True if the modifier is not explicit in the source code (e.g. a missing 'public' in an interface)
     * @return Set of enum value of {@link CtExtendedModifier}.
     */
    static java.util.Set<spoon.support.reflect.CtExtendedModifier> getModifiers(int modifier, boolean implicit, boolean isMethod) {
        java.util.Set<spoon.support.reflect.CtExtendedModifier> modifiers = new java.util.HashSet<>();
        if ((modifier & (org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants.AccPublic)) != 0) {
            modifiers.add(new spoon.support.reflect.CtExtendedModifier(spoon.reflect.declaration.ModifierKind.PUBLIC, implicit));
        }
        if ((modifier & (org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants.AccPrivate)) != 0) {
            modifiers.add(new spoon.support.reflect.CtExtendedModifier(spoon.reflect.declaration.ModifierKind.PRIVATE, implicit));
        }
        if ((modifier & (org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants.AccProtected)) != 0) {
            modifiers.add(new spoon.support.reflect.CtExtendedModifier(spoon.reflect.declaration.ModifierKind.PROTECTED, implicit));
        }
        if ((modifier & (org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants.AccStatic)) != 0) {
            modifiers.add(new spoon.support.reflect.CtExtendedModifier(spoon.reflect.declaration.ModifierKind.STATIC, implicit));
        }
        if ((modifier & (org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants.AccFinal)) != 0) {
            modifiers.add(new spoon.support.reflect.CtExtendedModifier(spoon.reflect.declaration.ModifierKind.FINAL, implicit));
        }
        if ((modifier & (org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants.AccSynchronized)) != 0) {
            modifiers.add(new spoon.support.reflect.CtExtendedModifier(spoon.reflect.declaration.ModifierKind.SYNCHRONIZED, implicit));
        }
        if ((modifier & (org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants.AccVolatile)) != 0) {
            modifiers.add(new spoon.support.reflect.CtExtendedModifier(spoon.reflect.declaration.ModifierKind.VOLATILE, implicit));
        }
        // a method can never be transient, but it can have the flag because of varArgs.
        // source: https://stackoverflow.com/questions/16233910/can-transient-keywords-mark-a-method
        if ((!isMethod) && ((modifier & (org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants.AccTransient)) != 0)) {
            modifiers.add(new spoon.support.reflect.CtExtendedModifier(spoon.reflect.declaration.ModifierKind.TRANSIENT, implicit));
        }
        if ((modifier & (org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants.AccAbstract)) != 0) {
            modifiers.add(new spoon.support.reflect.CtExtendedModifier(spoon.reflect.declaration.ModifierKind.ABSTRACT, implicit));
        }
        if ((modifier & (org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants.AccStrictfp)) != 0) {
            modifiers.add(new spoon.support.reflect.CtExtendedModifier(spoon.reflect.declaration.ModifierKind.STRICTFP, implicit));
        }
        if ((modifier & (org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants.AccNative)) != 0) {
            modifiers.add(new spoon.support.reflect.CtExtendedModifier(spoon.reflect.declaration.ModifierKind.NATIVE, implicit));
        }
        return modifiers;
    }
}

