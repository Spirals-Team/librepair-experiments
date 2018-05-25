package spoon.support.compiler.jdt;


class JDTTreeBuilderQuery {
    private JDTTreeBuilderQuery() {
    }

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

    static boolean hasAnnotationWithType(org.eclipse.jdt.internal.compiler.ast.Annotation a, spoon.reflect.declaration.CtAnnotatedElementType elementType) {
        if ((a.resolvedType) == null) {
            return false;
        }
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
        return (!shouldTargetAnnotationExists) && (!targetAnnotationExists);
    }

    static boolean isValidProblemBindingField(org.eclipse.jdt.internal.compiler.ast.QualifiedNameReference qualifiedNameReference) {
        return ((((qualifiedNameReference.binding) instanceof org.eclipse.jdt.internal.compiler.lookup.ProblemFieldBinding) && (!(((org.eclipse.jdt.internal.compiler.lookup.FieldBinding) (qualifiedNameReference.binding)).declaringClass.isAnonymousType()))) && (((qualifiedNameReference.tokens.length) - 1) == (((org.eclipse.jdt.internal.compiler.lookup.FieldBinding) (qualifiedNameReference.binding)).declaringClass.compoundName.length))) && (org.eclipse.jdt.core.compiler.CharOperation.equals(org.eclipse.jdt.core.compiler.CharOperation.subarray(qualifiedNameReference.tokens, 0, ((qualifiedNameReference.tokens.length) - 1)), ((org.eclipse.jdt.internal.compiler.lookup.FieldBinding) (qualifiedNameReference.binding)).declaringClass.compoundName));
    }

    static boolean isLhsAssignment(spoon.support.compiler.jdt.ContextBuilder context, org.eclipse.jdt.internal.compiler.ast.Expression lhs) {
        return ((context.stack.peek().node) instanceof org.eclipse.jdt.internal.compiler.ast.Assignment) && (((org.eclipse.jdt.internal.compiler.ast.Assignment) (context.stack.peek().node)).lhs.equals(lhs));
    }

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

