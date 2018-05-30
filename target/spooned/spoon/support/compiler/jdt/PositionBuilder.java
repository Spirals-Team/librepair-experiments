package spoon.support.compiler.jdt;


public class PositionBuilder {
    private final spoon.support.compiler.jdt.JDTTreeBuilder jdtTreeBuilder;

    public PositionBuilder(spoon.support.compiler.jdt.JDTTreeBuilder jdtTreeBuilder) {
        this.jdtTreeBuilder = jdtTreeBuilder;
    }

    spoon.reflect.cu.SourcePosition buildPosition(int sourceStart, int sourceEnd) {
        spoon.reflect.cu.CompilationUnit cu = this.jdtTreeBuilder.getContextBuilder().compilationUnitSpoon;
        final int[] lineSeparatorPositions = this.jdtTreeBuilder.getContextBuilder().compilationunitdeclaration.compilationResult.lineSeparatorPositions;
        return this.jdtTreeBuilder.getFactory().Core().createSourcePosition(cu, sourceStart, sourceEnd, lineSeparatorPositions);
    }

    spoon.reflect.cu.SourcePosition buildPositionCtElement(spoon.reflect.declaration.CtElement e, org.eclipse.jdt.internal.compiler.ast.ASTNode node) {
        spoon.reflect.factory.CoreFactory cf = this.jdtTreeBuilder.getFactory().Core();
        spoon.reflect.cu.CompilationUnit cu = this.jdtTreeBuilder.getFactory().CompilationUnit().getOrCreate(new java.lang.String(this.jdtTreeBuilder.getContextBuilder().compilationunitdeclaration.getFileName()));
        org.eclipse.jdt.internal.compiler.CompilationResult cr = this.jdtTreeBuilder.getContextBuilder().compilationunitdeclaration.compilationResult;
        int[] lineSeparatorPositions = cr.lineSeparatorPositions;
        char[] contents = cr.compilationUnit.getContents();
        int sourceStart = node.sourceStart;
        int sourceEnd = node.sourceEnd;
        if (node instanceof org.eclipse.jdt.internal.compiler.ast.Annotation) {
            org.eclipse.jdt.internal.compiler.ast.Annotation ann = ((org.eclipse.jdt.internal.compiler.ast.Annotation) (node));
            int declEnd = ann.declarationSourceEnd;
            if (declEnd > 0) {
                sourceEnd = declEnd;
            }
        }else
            if (node instanceof org.eclipse.jdt.internal.compiler.ast.Expression) {
                org.eclipse.jdt.internal.compiler.ast.Expression expression = ((org.eclipse.jdt.internal.compiler.ast.Expression) (node));
                int statementEnd = expression.statementEnd;
                if (statementEnd > 0) {
                    sourceEnd = statementEnd;
                }
            }

        if (node instanceof org.eclipse.jdt.internal.compiler.ast.TypeParameter) {
            org.eclipse.jdt.internal.compiler.ast.TypeParameter typeParameter = ((org.eclipse.jdt.internal.compiler.ast.TypeParameter) (node));
            sourceStart = typeParameter.declarationSourceStart;
            sourceEnd = typeParameter.declarationSourceEnd;
            if ((typeParameter.type) != null) {
                sourceEnd = getSourceEndOfTypeReference(contents, typeParameter.type, sourceEnd);
            }
        }else
            if (node instanceof org.eclipse.jdt.internal.compiler.ast.AbstractVariableDeclaration) {
                org.eclipse.jdt.internal.compiler.ast.AbstractVariableDeclaration variableDeclaration = ((org.eclipse.jdt.internal.compiler.ast.AbstractVariableDeclaration) (node));
                int modifiersSourceStart = variableDeclaration.modifiersSourceStart;
                int declarationSourceStart = variableDeclaration.declarationSourceStart;
                int declarationSourceEnd = variableDeclaration.declarationSourceEnd;
                if (modifiersSourceStart <= 0) {
                    modifiersSourceStart = declarationSourceStart;
                }
                int modifiersSourceEnd;
                if ((variableDeclaration.type) != null) {
                    modifiersSourceEnd = (variableDeclaration.type.sourceStart()) - 2;
                }else
                    if (variableDeclaration instanceof org.eclipse.jdt.internal.compiler.ast.Initializer) {
                        modifiersSourceEnd = (((org.eclipse.jdt.internal.compiler.ast.Initializer) (variableDeclaration)).block.sourceStart) - 1;
                    }else {
                        modifiersSourceEnd = declarationSourceStart - 1;
                    }

                if (modifiersSourceStart > modifiersSourceEnd) {
                    modifiersSourceEnd = modifiersSourceStart - 1;
                }else
                    if (e instanceof spoon.reflect.declaration.CtModifiable) {
                        setModifiersPosition(((spoon.reflect.declaration.CtModifiable) (e)), modifiersSourceStart, modifiersSourceEnd);
                    }

                return cf.createDeclarationSourcePosition(cu, sourceStart, sourceEnd, modifiersSourceStart, modifiersSourceEnd, declarationSourceStart, declarationSourceEnd, lineSeparatorPositions);
            }else
                if ((node instanceof org.eclipse.jdt.internal.compiler.ast.TypeDeclaration) && (e instanceof spoon.reflect.declaration.CtPackage)) {
                    return cf.createSourcePosition(cu, 0, ((contents.length) - 1), lineSeparatorPositions);
                }else
                    if (node instanceof org.eclipse.jdt.internal.compiler.ast.TypeDeclaration) {
                        org.eclipse.jdt.internal.compiler.ast.TypeDeclaration typeDeclaration = ((org.eclipse.jdt.internal.compiler.ast.TypeDeclaration) (node));
                        int declarationSourceStart = typeDeclaration.declarationSourceStart;
                        int declarationSourceEnd = typeDeclaration.declarationSourceEnd;
                        int modifiersSourceStart = typeDeclaration.modifiersSourceStart;
                        int bodyStart = typeDeclaration.bodyStart;
                        int bodyEnd = typeDeclaration.bodyEnd;
                        if (modifiersSourceStart <= 0) {
                            modifiersSourceStart = declarationSourceStart;
                        }
                        int modifiersSourceEnd = findPrevNonWhitespace(contents, (modifiersSourceStart - 1), findPrevWhitespace(contents, (modifiersSourceStart - 1), findPrevNonWhitespace(contents, (modifiersSourceStart - 1), (sourceStart - 1))));
                        if (e instanceof spoon.reflect.declaration.CtModifiable) {
                            setModifiersPosition(((spoon.reflect.declaration.CtModifiable) (e)), modifiersSourceStart, bodyStart);
                        }
                        if (modifiersSourceEnd < modifiersSourceStart) {
                            modifiersSourceEnd = modifiersSourceStart - 1;
                        }
                        if ((typeDeclaration.name.length) == 0) {
                            sourceEnd = sourceStart - 1;
                            if ((contents[sourceStart]) == '{') {
                                bodyEnd++;
                            }
                        }
                        return cf.createBodyHolderSourcePosition(cu, sourceStart, sourceEnd, modifiersSourceStart, modifiersSourceEnd, declarationSourceStart, declarationSourceEnd, (bodyStart - 1), bodyEnd, lineSeparatorPositions);
                    }else
                        if (node instanceof org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration) {
                            org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration methodDeclaration = ((org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration) (node));
                            int bodyStart = methodDeclaration.bodyStart;
                            int bodyEnd = methodDeclaration.bodyEnd;
                            int declarationSourceStart = methodDeclaration.declarationSourceStart;
                            int declarationSourceEnd = methodDeclaration.declarationSourceEnd;
                            int modifiersSourceStart = methodDeclaration.modifiersSourceStart;
                            if (modifiersSourceStart <= 0) {
                                modifiersSourceStart = declarationSourceStart;
                            }
                            if ((node instanceof org.eclipse.jdt.internal.compiler.ast.AnnotationMethodDeclaration) && (bodyStart == bodyEnd)) {
                                bodyEnd--;
                            }
                            org.eclipse.jdt.internal.compiler.ast.Javadoc javadoc = methodDeclaration.javadoc;
                            if ((javadoc != null) && ((javadoc.sourceEnd()) > declarationSourceStart)) {
                                modifiersSourceStart = (javadoc.sourceEnd()) + 1;
                            }
                            int modifiersSourceEnd = sourceStart - 1;
                            if (e instanceof spoon.reflect.declaration.CtModifiable) {
                                setModifiersPosition(((spoon.reflect.declaration.CtModifiable) (e)), modifiersSourceStart, declarationSourceEnd);
                            }
                            if ((methodDeclaration instanceof org.eclipse.jdt.internal.compiler.ast.MethodDeclaration) && ((((org.eclipse.jdt.internal.compiler.ast.MethodDeclaration) (methodDeclaration)).returnType) != null)) {
                                modifiersSourceEnd = (((org.eclipse.jdt.internal.compiler.ast.MethodDeclaration) (methodDeclaration)).returnType.sourceStart()) - 2;
                            }
                            org.eclipse.jdt.internal.compiler.ast.TypeParameter[] typeParameters = methodDeclaration.typeParameters();
                            if ((typeParameters != null) && ((typeParameters.length) > 0)) {
                                modifiersSourceEnd = (typeParameters[0].declarationSourceStart) - 3;
                            }
                            if (spoon.support.compiler.jdt.JDTTreeBuilderQuery.getModifiers(methodDeclaration.modifiers, false, true).isEmpty()) {
                                modifiersSourceEnd = modifiersSourceStart - 1;
                            }
                            sourceEnd = (sourceStart + (methodDeclaration.selector.length)) - 1;
                            if (bodyStart == 0) {
                                return cf.createPartialSourcePosition(cu);
                            }
                            if (e instanceof spoon.reflect.code.CtStatementList) {
                                return cf.createSourcePosition(cu, (bodyStart - 1), (bodyEnd + 1), lineSeparatorPositions);
                            }else {
                                if (bodyStart < bodyEnd) {
                                    if ((contents[(bodyStart - 1)]) == '{') {
                                        bodyStart--;
                                        if ((contents[(bodyEnd + 1)]) == '}') {
                                            bodyEnd++;
                                        }else {
                                            throw new spoon.SpoonException(("Missing body end in\n" + (new java.lang.String(contents, sourceStart, (sourceEnd - sourceStart)))));
                                        }
                                    }
                                }
                                return cf.createBodyHolderSourcePosition(cu, sourceStart, sourceEnd, modifiersSourceStart, modifiersSourceEnd, declarationSourceStart, declarationSourceEnd, bodyStart, bodyEnd, lineSeparatorPositions);
                            }
                        }else
                            if (e instanceof spoon.reflect.code.CtCatchVariable) {
                                java.util.Iterator<spoon.support.compiler.jdt.ASTPair> iterator = this.jdtTreeBuilder.getContextBuilder().stack.iterator();
                                spoon.support.compiler.jdt.ASTPair catchNode = iterator.next();
                                while (!((catchNode.node) instanceof org.eclipse.jdt.internal.compiler.ast.Argument)) {
                                    catchNode = iterator.next();
                                } 
                                spoon.reflect.cu.position.DeclarationSourcePosition argumentPosition = ((spoon.reflect.cu.position.DeclarationSourcePosition) (buildPositionCtElement(e, catchNode.node)));
                                int variableNameStart = findNextNonWhitespace(contents, argumentPosition.getSourceEnd(), (sourceEnd + 1));
                                int variableNameEnd = argumentPosition.getSourceEnd();
                                int modifierStart = sourceStart;
                                int modifierEnd = sourceStart - 1;
                                if (!(spoon.support.compiler.jdt.JDTTreeBuilderQuery.getModifiers(((org.eclipse.jdt.internal.compiler.ast.Argument) (catchNode.node)).modifiers, false, false).isEmpty())) {
                                    modifierStart = argumentPosition.getModifierSourceStart();
                                    modifierEnd = argumentPosition.getModifierSourceEnd();
                                    sourceStart = modifierStart;
                                }
                                sourceEnd = argumentPosition.getSourceEnd();
                                return cf.createDeclarationSourcePosition(cu, variableNameStart, variableNameEnd, modifierStart, modifierEnd, sourceStart, sourceEnd, lineSeparatorPositions);
                            }else
                                if (node instanceof org.eclipse.jdt.internal.compiler.ast.TypeReference) {
                                    sourceEnd = getSourceEndOfTypeReference(contents, ((org.eclipse.jdt.internal.compiler.ast.TypeReference) (node)), sourceEnd);
                                }






        if (e instanceof spoon.reflect.declaration.CtModifiable) {
            setModifiersPosition(((spoon.reflect.declaration.CtModifiable) (e)), sourceStart, sourceEnd);
        }
        return cf.createSourcePosition(cu, sourceStart, sourceEnd, lineSeparatorPositions);
    }

    private void setModifiersPosition(spoon.reflect.declaration.CtModifiable e, int start, int end) {
        spoon.reflect.factory.CoreFactory cf = this.jdtTreeBuilder.getFactory().Core();
        spoon.reflect.cu.CompilationUnit cu = this.jdtTreeBuilder.getFactory().CompilationUnit().getOrCreate(new java.lang.String(this.jdtTreeBuilder.getContextBuilder().compilationunitdeclaration.getFileName()));
        org.eclipse.jdt.internal.compiler.CompilationResult cr = this.jdtTreeBuilder.getContextBuilder().compilationunitdeclaration.compilationResult;
        char[] contents = cr.compilationUnit.getContents();
        java.util.Set<spoon.support.reflect.CtExtendedModifier> modifiers = e.getExtendedModifiers();
        java.lang.String modifierContent = java.lang.String.valueOf(contents, start, ((end - start) + 1));
        for (spoon.support.reflect.CtExtendedModifier modifier : modifiers) {
            if (modifier.isImplicit()) {
                modifier.setPosition(cf.createPartialSourcePosition(cu));
                continue;
            }
            int index = modifierContent.indexOf(modifier.getKind().toString());
            if (index == (-1)) {
                throw new spoon.SpoonException("Explicit modifier not found");
            }
            int indexStart = index + start;
            int indexEnd = (indexStart + (modifier.getKind().toString().length())) - 1;
            modifier.setPosition(cf.createSourcePosition(cu, indexStart, indexEnd, cr.lineSeparatorPositions));
        }
    }

    private int getSourceEndOfTypeReference(char[] contents, org.eclipse.jdt.internal.compiler.ast.TypeReference node, int sourceEnd) {
        org.eclipse.jdt.internal.compiler.ast.TypeReference[][] typeArgs = ((org.eclipse.jdt.internal.compiler.ast.TypeReference) (node)).getTypeArguments();
        if ((typeArgs != null) && ((typeArgs.length) > 0)) {
            org.eclipse.jdt.internal.compiler.ast.TypeReference[] trs = typeArgs[((typeArgs.length) - 1)];
            if ((trs != null) && ((trs.length) > 0)) {
                org.eclipse.jdt.internal.compiler.ast.TypeReference tr = trs[((trs.length) - 1)];
                if (sourceEnd < (tr.sourceEnd)) {
                    sourceEnd = findNextNonWhitespace(contents, contents.length, ((tr.sourceEnd) + 1));
                }
            }
        }
        return sourceEnd;
    }

    private int findNextNonWhitespace(char[] content, int maxOff, int off) {
        maxOff = java.lang.Math.min(maxOff, ((content.length) - 1));
        while ((off >= 0) && (off <= maxOff)) {
            char c = content[off];
            if ((java.lang.Character.isWhitespace(c)) == false) {
                int endOfCommentOff = getEndOfComment(content, maxOff, off);
                if (endOfCommentOff == (-1)) {
                    return off;
                }
                off = endOfCommentOff;
            }
            off++;
        } 
        return -1;
    }

    private int findNextWhitespace(char[] content, int maxOff, int off) {
        maxOff = java.lang.Math.min(maxOff, ((content.length) - 1));
        while ((off >= 0) && (off <= maxOff)) {
            char c = content[off];
            if ((java.lang.Character.isWhitespace(c)) || ((getEndOfComment(content, maxOff, off)) >= 0)) {
                return off;
            }
            off++;
        } 
        return -1;
    }

    int findPrevNonWhitespace(char[] content, int minOff, int off) {
        minOff = java.lang.Math.max(0, minOff);
        while (off >= minOff) {
            char c = content[off];
            int startOfCommentOff = getStartOfComment(content, minOff, off);
            if (startOfCommentOff >= 0) {
                off = startOfCommentOff;
            }else
                if ((java.lang.Character.isWhitespace(c)) == false) {
                    return off;
                }

            off--;
        } 
        return -1;
    }

    private int findPrevWhitespace(char[] content, int minOff, int off) {
        minOff = java.lang.Math.max(0, minOff);
        while (off >= minOff) {
            char c = content[off];
            if ((java.lang.Character.isWhitespace(c)) || ((getStartOfComment(content, minOff, off)) >= 0)) {
                return off;
            }
            off--;
        } 
        return -1;
    }

    private int getEndOfComment(char[] content, int maxOff, int off) {
        maxOff = java.lang.Math.min(maxOff, ((content.length) - 1));
        if ((off + 1) <= maxOff) {
            if (((content[off]) == '/') && ((content[(off + 1)]) == '*')) {
                off = off + 3;
                while (off <= maxOff) {
                    if (((content[off]) == '/') && ((content[(off - 1)]) == '*')) {
                        return off;
                    }
                    off++;
                } 
                return off;
            }else
                if (((content[off]) == '/') && ((content[(off + 1)]) == '/')) {
                    while (off <= maxOff) {
                        if ((content[off]) == '\n') {
                            return off;
                        }
                        if ((content[off]) == '\r') {
                            if ((content[off]) == '\n') {
                                off++;
                            }
                            return off;
                        }
                        off++;
                    } 
                }

        }
        return -1;
    }

    private int getStartOfComment(char[] content, int minOff, int off) {
        if (off < 2) {
            return -1;
        }
        if (((((content[off]) == '/') && ((content[(off - 1)]) == '*')) || ((content[off]) == '\n')) || ((content[off]) == '\r')) {
            int maxOff = off;
            off = minOff;
            while (off <= maxOff) {
                int endOfComment = getEndOfComment(content, maxOff, off);
                if (endOfComment >= 0) {
                    if (endOfComment == maxOff) {
                        return off;
                    }
                    off = endOfComment;
                }
                off++;
            } 
        }
        return -1;
    }
}

