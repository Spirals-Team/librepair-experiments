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
 * A visitor for generating Java code from the program compile-time model.
 */
public class DefaultJavaPrettyPrinter implements spoon.reflect.visitor.CtVisitor , spoon.reflect.visitor.PrettyPrinter {
    /**
     * Java file extension (.java).
     */
    public static final java.lang.String JAVA_FILE_EXTENSION = ".java";

    /**
     * Package declaration file name.
     */
    public static final java.lang.String JAVA_PACKAGE_DECLARATION = "package-info" + (spoon.reflect.visitor.DefaultJavaPrettyPrinter.JAVA_FILE_EXTENSION);

    /**
     * Module declaration file name.
     */
    public static final java.lang.String JAVA_MODULE_DECLARATION = "module-info" + (spoon.reflect.visitor.DefaultJavaPrettyPrinter.JAVA_FILE_EXTENSION);

    /**
     * Line separator which is used by the system
     */
    public static final java.lang.String LINE_SEPARATOR = java.lang.System.getProperty("line.separator");

    /**
     * The star at the beginning of a block/JavaDoc comment line
     */
    public static final java.lang.String COMMENT_STAR = " * ";

    /**
     * The end of a block/JavaDoc comment
     */
    public static final java.lang.String BLOCK_COMMENT_END = " */";

    /**
     * The beginning of a JavaDoc comment
     */
    public static final java.lang.String JAVADOC_START = "/**";

    /**
     * The beginning of a inline comment
     */
    public static final java.lang.String INLINE_COMMENT_START = "// ";

    /**
     * The beginning of a block comment
     */
    public static final java.lang.String BLOCK_COMMENT_START = "/* ";

    /**
     * The printing context.
     */
    public spoon.reflect.visitor.PrintingContext context = new spoon.reflect.visitor.PrintingContext();

    /**
     * Handle imports of classes.
     */
    private spoon.reflect.visitor.ImportScanner importsContext;

    /**
     * Environment which Spoon is executed.
     */
    private spoon.compiler.Environment env;

    /**
     * Token detector, which delegates tokens to {@link TokenWriter}
     */
    private spoon.reflect.visitor.TokenWriter printer;

    /**
     * Element printer helper.
     */
    private spoon.reflect.visitor.ElementPrinterHelper elementPrinterHelper;

    /**
     * Compilation unit we are printing.
     */
    private spoon.reflect.cu.CompilationUnit sourceCompilationUnit;

    /**
     * Imports computed
     */
    java.util.Set<spoon.reflect.declaration.CtImport> imports;

    /**
     * Creates a new code generator visitor.
     */
    public DefaultJavaPrettyPrinter(spoon.compiler.Environment env) {
        this.env = env;
        this.imports = new java.util.HashSet<>();
        setPrinterTokenWriter(new spoon.reflect.visitor.DefaultTokenWriter(new spoon.reflect.visitor.PrinterHelper(env)));
        if (env.isAutoImports()) {
            this.importsContext = new spoon.reflect.visitor.ImportScannerImpl();
        }else {
            this.importsContext = new spoon.reflect.visitor.MinimalImportScanner();
        }
    }

    /**
     *
     *
     * @return current line separator. By default there is CR LF, LF or CR depending on the Operation system
     * defined by System.getProperty("line.separator")
     */
    public java.lang.String getLineSeparator() {
        return getPrinterHelper().getLineSeparator();
    }

    /**
     *
     *
     * @param lineSeparator
     * 		characters which will be printed as End of line.
     * 		By default there is System.getProperty("line.separator")
     */
    public spoon.reflect.visitor.DefaultJavaPrettyPrinter setLineSeparator(java.lang.String lineSeparator) {
        getPrinterHelper().setLineSeparator(lineSeparator);
        return this;
    }

    /**
     * Enters an expression.
     */
    protected void enterCtExpression(spoon.reflect.code.CtExpression<?> e) {
        if (!(e instanceof spoon.reflect.code.CtStatement)) {
            elementPrinterHelper.writeComment(e, spoon.reflect.visitor.printer.CommentOffset.BEFORE);
        }
        getPrinterHelper().mapLine(e, sourceCompilationUnit);
        if (shouldSetBracket(e)) {
            context.parenthesedExpression.push(e);
            printer.writeSeparator("(");
        }
        if (!(e.getTypeCasts().isEmpty())) {
            for (spoon.reflect.reference.CtTypeReference<?> r : e.getTypeCasts()) {
                printer.writeSeparator("(");
                this.scan(r);
                printer.writeSeparator(")").writeSpace();
                printer.writeSeparator("(");
                context.parenthesedExpression.push(e);
            }
        }
    }

    /**
     * Enters a statement.
     */
    protected void enterCtStatement(spoon.reflect.code.CtStatement s) {
        elementPrinterHelper.writeComment(s, spoon.reflect.visitor.printer.CommentOffset.BEFORE);
        getPrinterHelper().mapLine(s, sourceCompilationUnit);
        elementPrinterHelper.writeAnnotations(s);
        if ((s.getLabel()) != null) {
            printer.writeIdentifier(s.getLabel()).writeSpace().writeSeparator(":").writeSpace();
        }
    }

    /**
     * Exits an expression.
     */
    protected void exitCtExpression(spoon.reflect.code.CtExpression<?> e) {
        while (((context.parenthesedExpression.size()) > 0) && (e == (context.parenthesedExpression.peek()))) {
            context.parenthesedExpression.pop();
            printer.writeSeparator(")");
        } 
        if (!(e instanceof spoon.reflect.code.CtStatement)) {
            elementPrinterHelper.writeComment(e, spoon.reflect.visitor.printer.CommentOffset.AFTER);
        }
    }

    /**
     * Make the imports for a given type.
     */
    public java.util.Collection<spoon.reflect.declaration.CtImport> computeImports(spoon.reflect.declaration.CtType<?> type) {
        context.currentTopLevel = type;
        importsContext.computeImports(context.currentTopLevel);
        return importsContext.getAllImports();
    }

    /**
     * Make the imports for all elements.
     */
    public void computeImports(spoon.reflect.declaration.CtElement element) {
        if (env.isAutoImports()) {
            importsContext.computeImports(element);
        }
    }

    /**
     * This method is called by {@link #scan(CtElement)} when entering a scanned element.
     * To be overridden to implement specific behavior.
     *
     * Same KISS design as for {@link CtScanner}.
     */
    protected void enter(spoon.reflect.declaration.CtElement e) {
    }

    /**
     * This method is called by {@link #scan(CtElement)} when entering a scanned element.
     * To be overridden to implement specific behavior.
     */
    protected void exit(spoon.reflect.declaration.CtElement e) {
    }

    /**
     * The generic scan method for an element.
     */
    public spoon.reflect.visitor.DefaultJavaPrettyPrinter scan(spoon.reflect.declaration.CtElement e) {
        if (e != null) {
            enter(e);
            context.elementStack.push(e);
            if (env.isPreserveLineNumbers()) {
                if (!(e instanceof spoon.reflect.declaration.CtNamedElement)) {
                    getPrinterHelper().adjustStartPosition(e);
                }
            }
            try {
                e.accept(this);
            } catch (spoon.SpoonException ex) {
                throw ex;
            } catch (java.lang.Exception ex) {
                java.lang.String elementInfo = e.getClass().getName();
                elementInfo += (" on path " + (spoon.reflect.visitor.DefaultJavaPrettyPrinter.getPath(e))) + "\n";
                if (e.getPosition().isValidPosition()) {
                    elementInfo += ("at position " + (e.getPosition().toString())) + " ";
                }
                throw new spoon.SpoonException((("Printing of " + elementInfo) + "failed"), ex);
            }
            context.elementStack.pop();
            exit(e);
        }
        return this;
    }

    private static java.lang.String getPath(spoon.reflect.declaration.CtElement ele) {
        java.lang.StringBuilder sb = new java.lang.StringBuilder();
        spoon.reflect.visitor.DefaultJavaPrettyPrinter.addParentPath(sb, ele);
        if (ele instanceof spoon.reflect.code.CtVariableAccess) {
            sb.append(':').append(((spoon.reflect.code.CtVariableAccess) (ele)).getVariable().getSimpleName());
        }
        return sb.toString();
    }

    private static void addParentPath(java.lang.StringBuilder sb, spoon.reflect.declaration.CtElement ele) {
        if ((ele == null) || ((ele instanceof spoon.reflect.declaration.CtPackage) && (((spoon.reflect.declaration.CtPackage) (ele)).isUnnamedPackage()))) {
            return;
        }
        if (ele.isParentInitialized()) {
            spoon.reflect.visitor.DefaultJavaPrettyPrinter.addParentPath(sb, ele.getParent());
        }
        sb.append("\n\t").append(ele.getClass().getSimpleName());
        if (ele instanceof spoon.reflect.declaration.CtNamedElement) {
            sb.append(":").append(((spoon.reflect.declaration.CtNamedElement) (ele)).getSimpleName());
        }else
            if (ele instanceof spoon.reflect.reference.CtReference) {
                sb.append(":").append(((spoon.reflect.reference.CtReference) (ele)).getSimpleName());
            }

    }

    private boolean shouldSetBracket(spoon.reflect.code.CtExpression<?> e) {
        if ((e.getTypeCasts().size()) != 0) {
            return true;
        }
        try {
            if (((e.getParent()) instanceof spoon.reflect.code.CtBinaryOperator) || ((e.getParent()) instanceof spoon.reflect.code.CtUnaryOperator)) {
                return ((((e instanceof spoon.reflect.code.CtTargetedExpression) || (e instanceof spoon.reflect.code.CtAssignment)) || (e instanceof spoon.reflect.code.CtConditional)) || (e instanceof spoon.reflect.code.CtUnaryOperator)) || (e instanceof spoon.reflect.code.CtBinaryOperator);
            }
            if ((e.getParent()) instanceof spoon.reflect.code.CtTargetedExpression) {
                return (((e instanceof spoon.reflect.code.CtBinaryOperator) || (e instanceof spoon.reflect.code.CtAssignment)) || (e instanceof spoon.reflect.code.CtConditional)) || (e instanceof spoon.reflect.code.CtUnaryOperator);
            }
        } catch (spoon.reflect.declaration.ParentNotInitializedException ex) {
            // nothing we accept not to have a parent
        }
        return false;
    }

    /**
     * Gets the currently pretty-printed string.
     */
    @java.lang.Override
    public java.lang.String toString() {
        return printer.getPrinterHelper().toString();
    }

    @java.lang.Override
    public <A extends java.lang.annotation.Annotation> void visitCtAnnotation(spoon.reflect.declaration.CtAnnotation<A> annotation) {
        elementPrinterHelper.writeAnnotations(annotation);
        printer.writeSeparator("@");
        scan(annotation.getAnnotationType());
        if ((annotation.getValues().size()) > 0) {
            elementPrinterHelper.printList(annotation.getValues().entrySet(), null, false, "(", false, false, ",", true, false, ")", ( e) -> {
                if ((((annotation.getValues().size()) == 1) && ("value".equals(e.getKey()))) == false) {
                    // it is not a default value attribute. We must print a attribute name too.
                    printer.writeIdentifier(e.getKey()).writeSpace().writeOperator("=").writeSpace();
                }
                elementPrinterHelper.writeAnnotationElement(annotation.getFactory(), e.getValue());
            });
        }
    }

    @java.lang.Override
    public <A extends java.lang.annotation.Annotation> void visitCtAnnotationType(spoon.reflect.declaration.CtAnnotationType<A> annotationType) {
        visitCtType(annotationType);
        printer.writeSeparator("@").writeKeyword("interface").writeSpace().writeIdentifier(annotationType.getSimpleName()).writeSpace().writeSeparator("{").incTab();
        elementPrinterHelper.writeElementList(annotationType.getTypeMembers());
        printer.decTab().writeSeparator("}");
    }

    @java.lang.Override
    public void visitCtAnonymousExecutable(spoon.reflect.declaration.CtAnonymousExecutable impl) {
        elementPrinterHelper.writeComment(impl);
        elementPrinterHelper.writeAnnotations(impl);
        elementPrinterHelper.writeModifiers(impl);
        scan(impl.getBody());
    }

    @java.lang.Override
    public <T> void visitCtArrayRead(spoon.reflect.code.CtArrayRead<T> arrayRead) {
        printCtArrayAccess(arrayRead);
    }

    @java.lang.Override
    public <T> void visitCtArrayWrite(spoon.reflect.code.CtArrayWrite<T> arrayWrite) {
        printCtArrayAccess(arrayWrite);
    }

    private <T, E extends spoon.reflect.code.CtExpression<?>> void printCtArrayAccess(spoon.reflect.code.CtArrayAccess<T, E> arrayAccess) {
        enterCtExpression(arrayAccess);
        scan(arrayAccess.getTarget());
        printer.writeSeparator("[");
        scan(arrayAccess.getIndexExpression());
        printer.writeSeparator("]");
        exitCtExpression(arrayAccess);
    }

    @java.lang.Override
    public <T> void visitCtArrayTypeReference(spoon.reflect.reference.CtArrayTypeReference<T> reference) {
        if (reference.isImplicit()) {
            return;
        }
        scan(reference.getComponentType());
        if (!(context.skipArray())) {
            printer.writeSeparator("[").writeSeparator("]");
        }
    }

    @java.lang.Override
    public <T> void visitCtAssert(spoon.reflect.code.CtAssert<T> asserted) {
        enterCtStatement(asserted);
        printer.writeKeyword("assert").writeSpace();
        scan(asserted.getAssertExpression());
        if ((asserted.getExpression()) != null) {
            printer.writeSpace().writeSeparator(":").writeSpace();
            scan(asserted.getExpression());
        }
    }

    @java.lang.Override
    public <T, A extends T> void visitCtAssignment(spoon.reflect.code.CtAssignment<T, A> assignement) {
        enterCtStatement(assignement);
        enterCtExpression(assignement);
        scan(assignement.getAssigned());
        printer.writeSpace().writeOperator("=").writeSpace();
        scan(assignement.getAssignment());
        exitCtExpression(assignement);
    }

    @java.lang.Override
    public <T> void visitCtBinaryOperator(spoon.reflect.code.CtBinaryOperator<T> operator) {
        enterCtExpression(operator);
        scan(operator.getLeftHandOperand());
        printer.writeSpace();
        printer.writeOperator(spoon.reflect.visitor.OperatorHelper.getOperatorText(operator.getKind()));
        printer.writeSpace();
        try (spoon.reflect.visitor.PrintingContext.Writable _context = context.modify()) {
            if ((operator.getKind()) == (spoon.reflect.code.BinaryOperatorKind.INSTANCEOF)) {
                _context.forceWildcardGenerics(true);
            }
            scan(operator.getRightHandOperand());
        }
        exitCtExpression(operator);
    }

    @java.lang.Override
    public <R> void visitCtBlock(spoon.reflect.code.CtBlock<R> block) {
        enterCtStatement(block);
        if (!(block.isImplicit())) {
            printer.writeSeparator("{");
        }
        printer.incTab();
        for (spoon.reflect.code.CtStatement statement : block.getStatements()) {
            if (!(statement.isImplicit())) {
                printer.writeln();
                elementPrinterHelper.writeStatement(statement);
            }
        }
        printer.decTab();
        getPrinterHelper().adjustEndPosition(block);
        if (env.isPreserveLineNumbers()) {
            if (!(block.isImplicit())) {
                printer.writeSeparator("}");
            }
        }else {
            printer.writeln();
            if (!(block.isImplicit())) {
                printer.writeSeparator("}");
            }
        }
    }

    @java.lang.Override
    public void visitCtBreak(spoon.reflect.code.CtBreak breakStatement) {
        enterCtStatement(breakStatement);
        printer.writeKeyword("break");
        if ((breakStatement.getTargetLabel()) != null) {
            printer.writeSpace().writeKeyword(breakStatement.getTargetLabel());
        }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("rawtypes")
    public <E> void visitCtCase(spoon.reflect.code.CtCase<E> caseStatement) {
        enterCtStatement(caseStatement);
        if ((caseStatement.getCaseExpression()) != null) {
            printer.writeKeyword("case").writeSpace();
            // writing enum case expression
            if ((caseStatement.getCaseExpression()) instanceof spoon.reflect.code.CtFieldAccess) {
                final spoon.reflect.reference.CtFieldReference variable = ((spoon.reflect.code.CtFieldAccess) (caseStatement.getCaseExpression())).getVariable();
                // In noclasspath mode, we don't have always the type of the declaring type.
                if ((((variable.getType()) != null) && ((variable.getDeclaringType()) != null)) && (variable.getType().getQualifiedName().equals(variable.getDeclaringType().getQualifiedName()))) {
                    printer.writeIdentifier(variable.getSimpleName());
                }else {
                    scan(caseStatement.getCaseExpression());
                }
            }else {
                scan(caseStatement.getCaseExpression());
            }
        }else {
            printer.writeKeyword("default");
        }
        printer.writeSpace().writeSeparator(":").incTab();
        for (spoon.reflect.code.CtStatement statement : caseStatement.getStatements()) {
            printer.writeln();
            elementPrinterHelper.writeStatement(statement);
        }
        printer.decTab();
    }

    @java.lang.Override
    public void visitCtCatch(spoon.reflect.code.CtCatch catchBlock) {
        elementPrinterHelper.writeComment(catchBlock, spoon.reflect.visitor.printer.CommentOffset.BEFORE);
        printer.writeSpace().writeKeyword("catch").writeSpace().writeSeparator("(");
        spoon.reflect.code.CtCatchVariable<? extends java.lang.Throwable> parameter = catchBlock.getParameter();
        if ((parameter != null) && ((parameter.getMultiTypes().size()) > 1)) {
            elementPrinterHelper.printList(parameter.getMultiTypes(), null, false, null, false, true, "|", true, false, null, ( type) -> scan(type));
            printer.writeSpace().writeIdentifier(parameter.getSimpleName());
        }else {
            scan(parameter);
        }
        printer.writeSeparator(")").writeSpace();
        scan(catchBlock.getBody());
    }

    @java.lang.Override
    public <T> void visitCtClass(spoon.reflect.declaration.CtClass<T> ctClass) {
        context.pushCurrentThis(ctClass);
        if ((((ctClass.getSimpleName()) != null) && (!(spoon.reflect.declaration.CtType.NAME_UNKNOWN.equals(ctClass.getSimpleName())))) && (!(ctClass.isAnonymous()))) {
            visitCtType(ctClass);
            if (ctClass.isLocalType()) {
                printer.writeKeyword("class").writeSpace().writeIdentifier(ctClass.getSimpleName().replaceAll("^[0-9]*", ""));
            }else {
                printer.writeKeyword("class").writeSpace().writeIdentifier(ctClass.getSimpleName());
            }
            elementPrinterHelper.writeFormalTypeParameters(ctClass);
            elementPrinterHelper.writeExtendsClause(ctClass);
            elementPrinterHelper.writeImplementsClause(ctClass);
        }
        // lst.addAll(elementPrinterHelper.getComments(ctClass, CommentOffset.INSIDE));
        printer.writeSpace().writeSeparator("{").incTab();
        elementPrinterHelper.writeElementList(ctClass.getTypeMembers());
        getPrinterHelper().adjustEndPosition(ctClass);
        printer.decTab().writeSeparator("}");
        context.popCurrentThis();
    }

    @java.lang.Override
    public void visitCtTypeParameter(spoon.reflect.declaration.CtTypeParameter typeParameter) {
        spoon.reflect.reference.CtTypeParameterReference ref = typeParameter.getReference();
        if (ref.isImplicit()) {
            return;
        }
        elementPrinterHelper.writeAnnotations(ref);
        if (printQualified(ref)) {
            elementPrinterHelper.writeQualifiedName(ref.getQualifiedName());
        }else {
            printer.writeIdentifier(ref.getSimpleName());
        }
        if ((!(ref.isDefaultBoundingType())) || (!(ref.getBoundingType().isImplicit()))) {
            if (ref.isUpper()) {
                printer.writeSpace().writeKeyword("extends").writeSpace();
            }else {
                printer.writeSpace().writeKeyword("super").writeSpace();
            }
            scan(ref.getBoundingType());
        }
    }

    @java.lang.Override
    public <T> void visitCtConditional(spoon.reflect.code.CtConditional<T> conditional) {
        enterCtExpression(conditional);
        spoon.reflect.code.CtExpression<java.lang.Boolean> condition = conditional.getCondition();
        boolean parent;
        try {
            parent = ((conditional.getParent()) instanceof spoon.reflect.code.CtAssignment) || ((conditional.getParent()) instanceof spoon.reflect.declaration.CtVariable);
        } catch (spoon.reflect.declaration.ParentNotInitializedException ex) {
            // nothing if we have no parent
            parent = false;
        }
        if (parent) {
            printer.writeSeparator("(");
        }
        scan(condition);
        if (parent) {
            printer.writeSeparator(")");
        }
        printer.writeSpace().writeOperator("?").writeSpace();
        spoon.reflect.code.CtExpression<T> thenExpression = conditional.getThenExpression();
        scan(thenExpression);
        printer.writeSpace().writeOperator(":").writeSpace();
        spoon.reflect.code.CtExpression<T> elseExpression = conditional.getElseExpression();
        boolean isAssign = false;
        if (isAssign = elseExpression instanceof spoon.reflect.code.CtAssignment) {
            printer.writeSeparator("(");
        }
        scan(elseExpression);
        if (isAssign) {
            printer.writeSeparator(")");
        }
        exitCtExpression(conditional);
    }

    @java.lang.Override
    public <T> void visitCtConstructor(spoon.reflect.declaration.CtConstructor<T> constructor) {
        elementPrinterHelper.writeComment(constructor);
        elementPrinterHelper.visitCtNamedElement(constructor, sourceCompilationUnit);
        elementPrinterHelper.writeModifiers(constructor);
        elementPrinterHelper.writeFormalTypeParameters(constructor);
        if ((constructor.getFormalCtTypeParameters().size()) > 0) {
            printer.writeSpace();
        }
        if ((constructor.getDeclaringType()) != null) {
            if (constructor.getDeclaringType().isLocalType()) {
                printer.writeIdentifier(constructor.getDeclaringType().getSimpleName().replaceAll("^[0-9]*", ""));
            }else {
                printer.writeIdentifier(constructor.getDeclaringType().getSimpleName());
            }
        }
        elementPrinterHelper.writeExecutableParameters(constructor);
        elementPrinterHelper.writeThrowsClause(constructor);
        printer.writeSpace();
        scan(constructor.getBody());
    }

    @java.lang.Override
    public void visitCtContinue(spoon.reflect.code.CtContinue continueStatement) {
        enterCtStatement(continueStatement);
        printer.writeKeyword("continue");
        if ((continueStatement.getTargetLabel()) != null) {
            printer.writeSpace().writeIdentifier(continueStatement.getTargetLabel());
        }
    }

    @java.lang.Override
    public void visitCtDo(spoon.reflect.code.CtDo doLoop) {
        enterCtStatement(doLoop);
        printer.writeKeyword("do");
        elementPrinterHelper.writeIfOrLoopBlock(doLoop.getBody());
        printer.writeKeyword("while").writeSpace().writeSeparator("(");
        scan(doLoop.getLoopingExpression());
        printer.writeSpace().writeSeparator(")");
    }

    @java.lang.Override
    public <T extends java.lang.Enum<?>> void visitCtEnum(spoon.reflect.declaration.CtEnum<T> ctEnum) {
        visitCtType(ctEnum);
        printer.writeKeyword("enum").writeSpace().writeIdentifier(ctEnum.getSimpleName());
        elementPrinterHelper.writeImplementsClause(ctEnum);
        context.pushCurrentThis(ctEnum);
        printer.writeSpace().writeSeparator("{").incTab().writeln();
        if ((ctEnum.getEnumValues().size()) == 0) {
            printer.writeSeparator(";").writeln();
        }else {
            elementPrinterHelper.printList(ctEnum.getEnumValues(), null, false, null, false, false, ",", true, false, ";", ( enumValue) -> scan(enumValue));
        }
        elementPrinterHelper.writeElementList(ctEnum.getTypeMembers());
        printer.decTab().writeSeparator("}");
        context.popCurrentThis();
    }

    @java.lang.Override
    public <T> void visitCtExecutableReference(spoon.reflect.reference.CtExecutableReference<T> reference) {
        // it is not called during printing of sources. Use shortcut and print directly to PrinterHelper
        printer.getPrinterHelper().write(reference.getSignature());
    }

    @java.lang.Override
    public <T> void visitCtField(spoon.reflect.declaration.CtField<T> f) {
        elementPrinterHelper.writeComment(f, spoon.reflect.visitor.printer.CommentOffset.BEFORE);
        elementPrinterHelper.visitCtNamedElement(f, sourceCompilationUnit);
        elementPrinterHelper.writeModifiers(f);
        scan(f.getType());
        printer.writeSpace();
        printer.writeIdentifier(f.getSimpleName());
        if ((f.getDefaultExpression()) != null) {
            printer.writeSpace().writeOperator("=").writeSpace();
            scan(f.getDefaultExpression());
        }
        printer.writeSeparator(";");
        elementPrinterHelper.writeComment(f, spoon.reflect.visitor.printer.CommentOffset.AFTER);
    }

    @java.lang.Override
    public <T> void visitCtEnumValue(spoon.reflect.declaration.CtEnumValue<T> enumValue) {
        elementPrinterHelper.visitCtNamedElement(enumValue, sourceCompilationUnit);
        printer.writeIdentifier(enumValue.getSimpleName());
        if ((enumValue.getDefaultExpression()) != null) {
            spoon.reflect.code.CtConstructorCall<?> constructorCall = ((spoon.reflect.code.CtConstructorCall<?>) (enumValue.getDefaultExpression()));
            if ((constructorCall.getArguments().size()) > 0) {
                printer.writeSeparator("(");
                boolean first = true;
                for (spoon.reflect.code.CtExpression<?> ctexpr : constructorCall.getArguments()) {
                    if (first) {
                        first = false;
                    }else {
                        printer.writeSeparator(",");
                    }
                    scan(ctexpr);
                }
                printer.writeSeparator(")");
            }
            if (constructorCall instanceof spoon.reflect.code.CtNewClass) {
                scan(((spoon.reflect.code.CtNewClass<?>) (constructorCall)).getAnonymousClass());
            }
        }
    }

    @java.lang.Override
    public <T> void visitCtFieldRead(spoon.reflect.code.CtFieldRead<T> fieldRead) {
        printCtFieldAccess(fieldRead);
    }

    @java.lang.Override
    public <T> void visitCtFieldWrite(spoon.reflect.code.CtFieldWrite<T> fieldWrite) {
        printCtFieldAccess(fieldWrite);
    }

    private boolean isImported(spoon.reflect.reference.CtFieldReference fieldReference) {
        spoon.reflect.declaration.CtImport fieldImport = fieldReference.getFactory().createImport(fieldReference);
        if (this.imports.contains(fieldImport)) {
            return true;
        }else {
            if ((fieldReference.getDeclaringType()) == null) {
                return false;
            }
            spoon.reflect.reference.CtTypeReference staticTypeMemberReference = fieldReference.getFactory().Type().createWildcardStaticTypeMemberReference(fieldReference.getDeclaringType());
            spoon.reflect.declaration.CtImport staticClassImport = fieldReference.getFactory().createImport(staticTypeMemberReference);
            return this.imports.contains(staticClassImport);
        }
    }

    private boolean isImported(spoon.reflect.reference.CtExecutableReference executableReference) {
        spoon.reflect.declaration.CtImport executableImport = executableReference.getFactory().createImport(executableReference);
        if (this.imports.contains(executableImport)) {
            return true;
        }else {
            if ((executableReference.getDeclaringType()) == null) {
                return false;
            }
            spoon.reflect.reference.CtTypeReference staticTypeMemberReference = executableReference.getFactory().Type().createWildcardStaticTypeMemberReference(executableReference.getDeclaringType());
            spoon.reflect.declaration.CtImport staticClassImport = executableReference.getFactory().createImport(staticTypeMemberReference);
            return this.imports.contains(staticClassImport);
        }
    }

    private <T> void printCtFieldAccess(spoon.reflect.code.CtFieldAccess<T> f) {
        enterCtExpression(f);
        try (spoon.reflect.visitor.PrintingContext.Writable _context = context.modify()) {
            if (((f.getVariable().isStatic()) || ("class".equals(f.getVariable().getSimpleName()))) && ((f.getTarget()) instanceof spoon.reflect.code.CtTypeAccess)) {
                _context.ignoreGenerics(true);
            }
            spoon.reflect.code.CtExpression<?> target = f.getTarget();
            if (target != null) {
                boolean isInitializeStaticFinalField = isInitializeStaticFinalField(f.getTarget());
                boolean isStaticField = f.getVariable().isStatic();
                boolean isImportedField = this.isImported(f.getVariable());
                if ((!isInitializeStaticFinalField) && (!(isStaticField && isImportedField))) {
                    if ((target.isImplicit()) && (!(((f.getVariable().getFieldDeclaration()) == null) && (this.env.getNoClasspath())))) {
                        /* target is implicit, check whether there is no conflict with an local variable, catch variable or parameter
                        in case of conflict make it explicit, otherwise the field access is shadowed by that variable.
                        Search for potential variable declaration until we found a class which declares or inherits this field
                         */
                        final spoon.reflect.declaration.CtField<?> field = f.getVariable().getFieldDeclaration();
                        if (field != null) {
                            final java.lang.String fieldName = field.getSimpleName();
                            spoon.reflect.declaration.CtVariable<?> var = f.getVariable().map(new spoon.reflect.visitor.filter.PotentialVariableDeclarationFunction(fieldName)).first();
                            if (var != field) {
                                // another variable declaration was found which is hiding the field declaration for this field access. Make the field access expicit
                                target.setImplicit(false);
                            }
                        }else {
                            // There is a model inconsistency
                            printer.writeComment(f.getFactory().createComment((("ERROR: Missing field \"" + (f.getVariable().getSimpleName())) + "\", please check your model. The code may not compile."), spoon.reflect.code.CtComment.CommentType.BLOCK)).writeSpace();
                        }
                    }
                    // the implicit drives the separator
                    if (!(target.isImplicit())) {
                        scan(target);
                        printer.writeSeparator(".");
                    }
                }
                _context.ignoreStaticAccess(true);
            }
            scan(f.getVariable());
        }
        exitCtExpression(f);
    }

    /**
     * Check if the target expression is a static final field initialized in a static anonymous block.
     */
    private <T> boolean isInitializeStaticFinalField(spoon.reflect.code.CtExpression<T> targetExp) {
        final spoon.reflect.declaration.CtElement parent;
        final spoon.reflect.declaration.CtAnonymousExecutable anonymousParent;
        try {
            parent = targetExp.getParent();
            anonymousParent = targetExp.getParent(spoon.reflect.declaration.CtAnonymousExecutable.class);
        } catch (spoon.reflect.declaration.ParentNotInitializedException e) {
            return false;
        }
        if ((((((parent instanceof spoon.reflect.code.CtFieldWrite) && (targetExp.equals(((spoon.reflect.code.CtFieldWrite) (parent)).getTarget()))) && (anonymousParent != null)) && ((((spoon.reflect.code.CtFieldWrite) (parent)).getVariable()) != null)) && (((spoon.reflect.code.CtFieldWrite) (parent)).getVariable().getModifiers().contains(spoon.reflect.declaration.ModifierKind.STATIC))) && (((spoon.reflect.code.CtFieldWrite) (parent)).getVariable().getModifiers().contains(spoon.reflect.declaration.ModifierKind.FINAL))) {
            return true;
        }
        return false;
    }

    @java.lang.Override
    public <T> void visitCtThisAccess(spoon.reflect.code.CtThisAccess<T> thisAccess) {
        try {
            enterCtExpression(thisAccess);
            // we only write qualified this when this is required
            // this is good both in fully-qualified mode and in readable (with-imports) mode
            // the implicit information is used for analysis (eg are visibility caused by implicit bugs?) but
            // not for pretty-printing
            spoon.reflect.code.CtTypeAccess target = ((spoon.reflect.code.CtTypeAccess) (thisAccess.getTarget()));
            spoon.reflect.reference.CtTypeReference targetType = target.getAccessedType();
            // readable mode as close as possible to the original code
            if (thisAccess.isImplicit()) {
                // write nothing, "this" is implicit and we unfortunately cannot always know
                // what the good target is in JDTTreeBuilder
                return;
            }
            // the simplest case: we always print "this" if we're in the top-level class,
            // this is shorter (no qualified this), explicit, and less fragile wrt transformation
            if ((targetType == null) || (((thisAccess.getParent(spoon.reflect.declaration.CtType.class)) != null) && (thisAccess.getParent(spoon.reflect.declaration.CtType.class).isTopLevel()))) {
                printer.writeKeyword("this");
                return;// still go through finally block below

            }
            // we cannot have fully-qualified this in anonymous classes
            // we simply print "this" and it always works
            // this has to come after the implicit test just before
            if (targetType.isAnonymous()) {
                printer.writeKeyword("this");
                return;
            }
            // complex case of qualifed this
            if (!(context.currentThis.isEmpty())) {
                spoon.reflect.declaration.CtType lastType = context.currentThis.peekFirst().type;
                java.lang.String lastTypeQualifiedName = lastType.getQualifiedName();
                java.lang.String targetTypeQualifiedName = targetType.getQualifiedName();
                if (!(lastTypeQualifiedName.equals(targetTypeQualifiedName))) {
                    if (!(targetType.isImplicit())) {
                        visitCtTypeReferenceWithoutGenerics(targetType);
                        printer.writeSeparator(".");
                    }
                    printer.writeKeyword("this");
                    return;
                }
            }
            // the default super simple case only comes at the end
            printer.writeKeyword("this");
        } finally {
            exitCtExpression(thisAccess);
        }
    }

    @java.lang.Override
    public <T> void visitCtSuperAccess(spoon.reflect.code.CtSuperAccess<T> f) {
        enterCtExpression(f);
        if ((f.getTarget()) != null) {
            scan(f.getTarget());
            printer.writeSeparator(".");
        }
        printer.writeKeyword("super");
        exitCtExpression(f);
    }

    @java.lang.Override
    public void visitCtJavaDoc(spoon.reflect.code.CtJavaDoc comment) {
        visitCtComment(comment);
    }

    @java.lang.Override
    public void visitCtJavaDocTag(spoon.reflect.code.CtJavaDocTag docTag) {
        /* is not called during normal printing of java sources.
        It can be called only when CtJavaDocTag has to be printed directly.
        E.g. from CtJavaDocTag#toString
        Write directly to PrinterHelper, because java doc tag is not a java token. Normally it is part of COMMENT token.
         */
        spoon.reflect.visitor.CommentHelper.printJavaDocTag(printer.getPrinterHelper(), docTag);
    }

    @java.lang.Override
    public void visitCtImport(spoon.reflect.declaration.CtImport ctImport) {
        if ((ctImport.getImportKind()) != null) {
            printer.writeKeyword("import");
            printer.writeSpace();
            switch (ctImport.getImportKind()) {
                case TYPE :
                    visitCtTypeReference(((spoon.reflect.reference.CtTypeReference) (ctImport.getReference())));
                    break;
                case METHOD :
                    printer.writeKeyword("static");
                    printer.writeSpace();
                    visitCtExecutableReference(((spoon.reflect.reference.CtExecutableReference) (ctImport.getReference())));
                    break;
                case FIELD :
                    printer.writeKeyword("static");
                    printer.writeSpace();
                    visitCtFieldReference(((spoon.reflect.reference.CtFieldReference) (ctImport.getReference())));
                    break;
                case ALL_TYPES :
                    visitCtPackageReference(((spoon.reflect.reference.CtPackageReference) (ctImport.getReference())));
                    printer.writeSeparator(".");
                    printer.writeIdentifier("*");
                    break;
                case ALL_STATIC_MEMBERS :
                    printer.writeKeyword("static");
                    printer.writeSpace();
                    visitCtTypeReference(((spoon.reflect.reference.CtTypeReference) (ctImport.getReference())));
                    break;
            }
            printer.writeSeparator(";");
            printer.writeln();
        }
    }

    @java.lang.Override
    public void visitCtModule(spoon.reflect.declaration.CtModule module) {
        enter(module);
        if (module.isOpenModule()) {
            printer.writeKeyword("open").writeSpace();
        }
        printer.writeKeyword("module").writeSpace().writeIdentifier(module.getSimpleName());
        printer.writeSpace().writeSeparator("{").incTab().writeln();
        for (spoon.reflect.declaration.CtModuleDirective moduleDirective : module.getModuleDirectives()) {
            scan(moduleDirective);
        }
        printer.decTab().writeSeparator("}");
        exit(module);
    }

    @java.lang.Override
    public void visitCtModuleReference(spoon.reflect.reference.CtModuleReference moduleReference) {
        printer.writeIdentifier(moduleReference.getSimpleName());
    }

    @java.lang.Override
    public void visitCtPackageExport(spoon.reflect.declaration.CtPackageExport moduleExport) {
        if (moduleExport.isOpenedPackage()) {
            printer.writeKeyword("opens");
        }else {
            printer.writeKeyword("exports");
        }
        printer.writeSpace();
        visitCtPackageReference(moduleExport.getPackageReference());
        if (!(moduleExport.getTargetExport().isEmpty())) {
            this.elementPrinterHelper.printList(moduleExport.getTargetExport(), null, false, " to", true, false, ",", true, false, null, ( moduleReference) -> scan(moduleReference));
        }
        printer.writeSeparator(";").writeln();
    }

    @java.lang.Override
    public void visitCtModuleRequirement(spoon.reflect.declaration.CtModuleRequirement moduleRequirement) {
        printer.writeKeyword("requires").writeSpace();
        if (!(moduleRequirement.getRequiresModifiers().isEmpty())) {
            this.elementPrinterHelper.printList(moduleRequirement.getRequiresModifiers(), null, false, null, false, false, " ", false, false, " ", ( modifier) -> printer.writeKeyword(modifier.name().toLowerCase()));
        }
        scan(moduleRequirement.getModuleReference());
        printer.writeSeparator(";").writeln();
    }

    @java.lang.Override
    public void visitCtProvidedService(spoon.reflect.declaration.CtProvidedService moduleProvidedService) {
        printer.writeKeyword("provides").writeSpace();
        scan(moduleProvidedService.getServiceType());
        this.elementPrinterHelper.printList(moduleProvidedService.getImplementationTypes(), null, false, " with", true, false, ",", true, false, null, ( implementations) -> scan(implementations));
        printer.writeSeparator(";").writeln();
    }

    @java.lang.Override
    public void visitCtUsedService(spoon.reflect.declaration.CtUsedService usedService) {
        printer.writeKeyword("uses").writeSpace();
        scan(usedService.getServiceType());
        printer.writeSeparator(";").writeln();
    }

    @java.lang.Override
    public void visitCtComment(spoon.reflect.code.CtComment comment) {
        if ((!(env.isCommentsEnabled())) && ((context.elementStack.size()) > 1)) {
            return;
        }
        printer.writeComment(comment);
    }

    @java.lang.Override
    public <T> void visitCtAnnotationFieldAccess(spoon.reflect.code.CtAnnotationFieldAccess<T> annotationFieldAccess) {
        enterCtExpression(annotationFieldAccess);
        try (spoon.reflect.visitor.PrintingContext.Writable _context = context.modify()) {
            if ((annotationFieldAccess.getTarget()) != null) {
                scan(annotationFieldAccess.getTarget());
                printer.writeSeparator(".");
                _context.ignoreStaticAccess(true);
            }
            _context.ignoreGenerics(true);
            scan(annotationFieldAccess.getVariable());
            printer.writeSeparator("(").writeSeparator(")");
        }
        exitCtExpression(annotationFieldAccess);
    }

    @java.lang.Override
    public <T> void visitCtFieldReference(spoon.reflect.reference.CtFieldReference<T> reference) {
        boolean isStatic = (reference.getSimpleName().equals("class")) || ((!(reference.getSimpleName().equals("super"))) && (reference.isStatic()));
        boolean printType = true;
        if ((reference.isFinal()) && (reference.isStatic())) {
            spoon.reflect.reference.CtTypeReference<?> declTypeRef = reference.getDeclaringType();
            if (declTypeRef.isAnonymous()) {
                // never print anonymous class ref
                printType = false;
            }else {
                if (context.isInCurrentScope(declTypeRef)) {
                    // do not printType if we are in scope of that type
                    printType = false;
                }
            }
        }
        if ((isStatic && printType) && (!(context.ignoreStaticAccess()))) {
            try (spoon.reflect.visitor.PrintingContext.Writable _context = context.modify().ignoreGenerics(true)) {
                scan(reference.getDeclaringType());
            }
            printer.writeSeparator(".");
        }
        if (reference.getSimpleName().equals("class")) {
            printer.writeKeyword("class");
        }else {
            printer.writeIdentifier(reference.getSimpleName());
        }
    }

    @java.lang.Override
    public void visitCtFor(spoon.reflect.code.CtFor forLoop) {
        enterCtStatement(forLoop);
        printer.writeKeyword("for").writeSpace().writeSeparator("(");
        java.util.List<spoon.reflect.code.CtStatement> st = forLoop.getForInit();
        if ((st.size()) > 0) {
            scan(st.get(0));
        }
        if ((st.size()) > 1) {
            try (spoon.reflect.visitor.PrintingContext.Writable _context = context.modify().noTypeDecl(true)) {
                for (int i = 1; i < (st.size()); i++) {
                    printer.writeSeparator(",").writeSpace();
                    scan(st.get(i));
                }
            }
        }
        printer.writeSeparator(";").writeSpace();
        scan(forLoop.getExpression());
        printer.writeSeparator(";");
        if (!(forLoop.getForUpdate().isEmpty())) {
            printer.writeSpace();
        }
        elementPrinterHelper.printList(forLoop.getForUpdate(), null, false, null, false, true, ",", true, false, null, ( s) -> scan(s));
        printer.writeSeparator(")");
        elementPrinterHelper.writeIfOrLoopBlock(forLoop.getBody());
    }

    @java.lang.Override
    public void visitCtForEach(spoon.reflect.code.CtForEach foreach) {
        enterCtStatement(foreach);
        printer.writeKeyword("for").writeSpace().writeSeparator("(");
        scan(foreach.getVariable());
        printer.writeSpace().writeSeparator(":").writeSpace();
        scan(foreach.getExpression());
        printer.writeSeparator(")");
        elementPrinterHelper.writeIfOrLoopBlock(foreach.getBody());
    }

    @java.lang.Override
    public void visitCtIf(spoon.reflect.code.CtIf ifElement) {
        enterCtStatement(ifElement);
        printer.writeKeyword("if").writeSpace().writeSeparator("(");
        scan(ifElement.getCondition());
        printer.writeSeparator(")");
        elementPrinterHelper.writeIfOrLoopBlock(ifElement.getThenStatement());
        if ((ifElement.getElseStatement()) != null) {
            java.util.List<spoon.reflect.code.CtComment> comments = elementPrinterHelper.getComments(ifElement, spoon.reflect.visitor.printer.CommentOffset.INSIDE);
            for (spoon.reflect.code.CtComment comment : comments) {
                spoon.reflect.cu.SourcePosition thenPosition = (ifElement.getThenStatement().getPosition().isValidPosition()) ? ifElement.getThenStatement().getPosition() : ((spoon.reflect.code.CtBlock) (ifElement.getThenStatement())).getStatement(0).getPosition();
                if ((comment.getPosition().getSourceStart()) > (thenPosition.getSourceEnd())) {
                    elementPrinterHelper.writeComment(comment);
                }
            }
            printer.writeKeyword("else");
            elementPrinterHelper.writeIfOrLoopBlock(ifElement.getElseStatement());
        }
    }

    @java.lang.Override
    public <T> void visitCtInterface(spoon.reflect.declaration.CtInterface<T> intrface) {
        visitCtType(intrface);
        printer.writeKeyword("interface").writeSpace().writeIdentifier(intrface.getSimpleName());
        if ((intrface.getFormalCtTypeParameters()) != null) {
            elementPrinterHelper.writeFormalTypeParameters(intrface);
        }
        if ((intrface.getSuperInterfaces().size()) > 0) {
            elementPrinterHelper.printList(intrface.getSuperInterfaces(), "extends", false, null, false, true, ",", true, false, null, ( ref) -> scan(ref));
        }
        context.pushCurrentThis(intrface);
        printer.writeSpace().writeSeparator("{").incTab();
        // Content
        elementPrinterHelper.writeElementList(intrface.getTypeMembers());
        printer.decTab().writeSeparator("}");
        context.popCurrentThis();
    }

    @java.lang.Override
    public <T> void visitCtInvocation(spoon.reflect.code.CtInvocation<T> invocation) {
        enterCtStatement(invocation);
        enterCtExpression(invocation);
        if (invocation.getExecutable().isConstructor()) {
            // It's a constructor (super or this)
            elementPrinterHelper.writeActualTypeArguments(invocation.getExecutable());
            spoon.reflect.declaration.CtType<?> parentType;
            try {
                parentType = invocation.getParent(spoon.reflect.declaration.CtType.class);
            } catch (spoon.reflect.declaration.ParentNotInitializedException e) {
                parentType = null;
            }
            if (((parentType != null) && ((parentType.getQualifiedName()) != null)) && (parentType.getQualifiedName().equals(invocation.getExecutable().getDeclaringType().getQualifiedName()))) {
                printer.writeKeyword("this");
            }else {
                if (((invocation.getTarget()) != null) && (!(invocation.getTarget().isImplicit()))) {
                    scan(invocation.getTarget());
                    printer.writeSeparator(".");
                }
                printer.writeKeyword("super");
            }
        }else {
            // It's a method invocation
            boolean isImported = this.isImported(invocation.getExecutable());
            if (!isImported) {
                try (spoon.reflect.visitor.PrintingContext.Writable _context = context.modify()) {
                    if ((invocation.getTarget()) instanceof spoon.reflect.code.CtTypeAccess) {
                        _context.ignoreGenerics(true);
                    }
                    if (((invocation.getTarget()) != null) && (!(invocation.getTarget().isImplicit()))) {
                        scan(invocation.getTarget());
                        printer.writeSeparator(".");
                    }
                }
            }
            elementPrinterHelper.writeActualTypeArguments(invocation);
            if (env.isPreserveLineNumbers()) {
                getPrinterHelper().adjustStartPosition(invocation);
            }
            printer.writeIdentifier(invocation.getExecutable().getSimpleName());
        }
        elementPrinterHelper.printList(invocation.getArguments(), null, false, "(", false, false, ",", true, false, ")", ( e) -> scan(e));
        exitCtExpression(invocation);
    }

    @java.lang.Override
    public <T> void visitCtLiteral(spoon.reflect.code.CtLiteral<T> literal) {
        enterCtExpression(literal);
        printer.writeLiteral(spoon.reflect.visitor.LiteralHelper.getLiteralToken(literal));
        exitCtExpression(literal);
    }

    @java.lang.Override
    public <T> void visitCtLocalVariable(spoon.reflect.code.CtLocalVariable<T> localVariable) {
        if (!(context.noTypeDecl())) {
            enterCtStatement(localVariable);
        }
        if (env.isPreserveLineNumbers()) {
            getPrinterHelper().adjustStartPosition(localVariable);
        }
        if (!(context.noTypeDecl())) {
            elementPrinterHelper.writeModifiers(localVariable);
            scan(localVariable.getType());
            printer.writeSpace();
        }
        printer.writeIdentifier(localVariable.getSimpleName());
        if ((localVariable.getDefaultExpression()) != null) {
            printer.writeSpace().writeOperator("=").writeSpace();
            scan(localVariable.getDefaultExpression());
        }
    }

    @java.lang.Override
    public <T> void visitCtLocalVariableReference(spoon.reflect.reference.CtLocalVariableReference<T> reference) {
        printer.writeIdentifier(reference.getSimpleName());
    }

    @java.lang.Override
    public <T> void visitCtCatchVariable(spoon.reflect.code.CtCatchVariable<T> catchVariable) {
        if (env.isPreserveLineNumbers()) {
            getPrinterHelper().adjustStartPosition(catchVariable);
        }
        elementPrinterHelper.writeModifiers(catchVariable);
        scan(catchVariable.getType());
        printer.writeSpace();
        printer.writeIdentifier(catchVariable.getSimpleName());
    }

    @java.lang.Override
    public <T> void visitCtCatchVariableReference(spoon.reflect.reference.CtCatchVariableReference<T> reference) {
        printer.writeIdentifier(reference.getSimpleName());
    }

    @java.lang.Override
    public <T> void visitCtMethod(spoon.reflect.declaration.CtMethod<T> m) {
        elementPrinterHelper.writeComment(m, spoon.reflect.visitor.printer.CommentOffset.BEFORE);
        elementPrinterHelper.visitCtNamedElement(m, sourceCompilationUnit);
        elementPrinterHelper.writeModifiers(m);
        elementPrinterHelper.writeFormalTypeParameters(m);
        if ((m.getFormalCtTypeParameters().size()) > 0) {
            printer.writeSpace();
        }
        try (spoon.reflect.visitor.PrintingContext.Writable _context = context.modify().ignoreGenerics(false)) {
            scan(m.getType());
        }
        printer.writeSpace();
        printer.writeIdentifier(m.getSimpleName());
        elementPrinterHelper.writeExecutableParameters(m);
        elementPrinterHelper.writeThrowsClause(m);
        if ((m.getBody()) != null) {
            printer.writeSpace();
            scan(m.getBody());
            if (m.getBody().getPosition().isValidPosition()) {
                if ((m.getBody().getPosition().getCompilationUnit()) == (sourceCompilationUnit)) {
                    if ((m.getBody().getStatements().isEmpty()) || (!((m.getBody().getStatements().get(((m.getBody().getStatements().size()) - 1))) instanceof spoon.reflect.code.CtReturn))) {
                        getPrinterHelper().putLineNumberMapping(m.getBody().getPosition().getEndLine());
                    }
                }else {
                    getPrinterHelper().undefineLine();
                }
            }else {
                getPrinterHelper().undefineLine();
            }
        }else {
            printer.writeSeparator(";");
        }
        elementPrinterHelper.writeComment(m, spoon.reflect.visitor.printer.CommentOffset.AFTER);
    }

    @java.lang.Override
    public <T> void visitCtAnnotationMethod(spoon.reflect.declaration.CtAnnotationMethod<T> annotationMethod) {
        elementPrinterHelper.writeComment(annotationMethod);
        elementPrinterHelper.visitCtNamedElement(annotationMethod, sourceCompilationUnit);
        elementPrinterHelper.writeModifiers(annotationMethod);
        scan(annotationMethod.getType());
        printer.writeSpace();
        printer.writeIdentifier(annotationMethod.getSimpleName());
        printer.writeSeparator("(").writeSeparator(")");
        if ((annotationMethod.getDefaultExpression()) != null) {
            printer.writeSpace().writeKeyword("default").writeSpace();
            scan(annotationMethod.getDefaultExpression());
        }
        printer.writeSeparator(";");
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("rawtypes")
    public <T> void visitCtNewArray(spoon.reflect.code.CtNewArray<T> newArray) {
        enterCtExpression(newArray);
        boolean isNotInAnnotation;
        try {
            isNotInAnnotation = ((newArray.getParent(spoon.reflect.declaration.CtAnnotationType.class)) == null) && ((newArray.getParent(spoon.reflect.declaration.CtAnnotation.class)) == null);
        } catch (spoon.reflect.declaration.ParentNotInitializedException e) {
            isNotInAnnotation = true;
        }
        if (isNotInAnnotation) {
            spoon.reflect.reference.CtTypeReference<?> ref = newArray.getType();
            if (ref != null) {
                printer.writeKeyword("new").writeSpace();
            }
            try (spoon.reflect.visitor.PrintingContext.Writable _context = context.modify().skipArray(true)) {
                scan(ref);
            }
            for (int i = 0; ref instanceof spoon.reflect.reference.CtArrayTypeReference; i++) {
                printer.writeSeparator("[");
                if ((newArray.getDimensionExpressions().size()) > i) {
                    spoon.reflect.code.CtExpression<java.lang.Integer> e = newArray.getDimensionExpressions().get(i);
                    scan(e);
                }
                printer.writeSeparator("]");
                ref = ((spoon.reflect.reference.CtArrayTypeReference) (ref)).getComponentType();
            }
        }
        if ((newArray.getDimensionExpressions().size()) == 0) {
            elementPrinterHelper.printList(newArray.getElements(), null, false, "{", true, false, ",", true, true, "}", ( e) -> scan(e));
            elementPrinterHelper.writeComment(newArray, spoon.reflect.visitor.printer.CommentOffset.INSIDE);
        }
        elementPrinterHelper.writeComment(newArray, spoon.reflect.visitor.printer.CommentOffset.AFTER);
        exitCtExpression(newArray);
    }

    @java.lang.Override
    public <T> void visitCtConstructorCall(spoon.reflect.code.CtConstructorCall<T> ctConstructorCall) {
        enterCtStatement(ctConstructorCall);
        enterCtExpression(ctConstructorCall);
        printConstructorCall(ctConstructorCall);
        exitCtExpression(ctConstructorCall);
    }

    @java.lang.Override
    public <T> void visitCtNewClass(spoon.reflect.code.CtNewClass<T> newClass) {
        enterCtStatement(newClass);
        enterCtExpression(newClass);
        printConstructorCall(newClass);
        scan(newClass.getAnonymousClass());
        exitCtExpression(newClass);
    }

    private <T> void printConstructorCall(spoon.reflect.code.CtConstructorCall<T> ctConstructorCall) {
        try (spoon.reflect.visitor.PrintingContext.Writable _context = context.modify()) {
            if ((ctConstructorCall.getTarget()) != null) {
                scan(ctConstructorCall.getTarget());
                printer.writeSeparator(".");
                _context.ignoreEnclosingClass(true);
            }
            if (hasDeclaringTypeWithGenerics(ctConstructorCall.getType())) {
                _context.ignoreEnclosingClass(true);
            }
            printer.writeKeyword("new").writeSpace();
            if ((ctConstructorCall.getActualTypeArguments().size()) > 0) {
                elementPrinterHelper.writeActualTypeArguments(ctConstructorCall);
            }
            scan(ctConstructorCall.getType());
        }
        elementPrinterHelper.printList(ctConstructorCall.getArguments(), null, false, "(", false, false, ",", true, false, ")", ( exp) -> scan(exp));
    }

    /**
     * JDT doesn't support <code>new Foo<K>.Bar()</code>. To avoid reprint this kind of type reference,
     * we check that the reference has a declaring type with generics.
     * See https://bugs.eclipse.org/bugs/show_bug.cgi?id=474593
     *
     * @param reference
     * 		Type reference concerned by the bug.
     * @return true if a declaring type have generic types.
     */
    private <T> boolean hasDeclaringTypeWithGenerics(spoon.reflect.reference.CtTypeReference<T> reference) {
        // We don't have a declaring type, it can't have generics.
        if (reference == null) {
            return false;
        }
        // If the declaring type isn't a type, we don't need this hack.
        if ((reference.getDeclaringType()) == null) {
            return false;
        }
        // If current reference is a class declared in a method, we don't need this hack.
        if (reference.isLocalType()) {
            return false;
        }
        // If declaring type have generics, we return true.
        if ((reference.getDeclaringType().getActualTypeArguments().size()) != 0) {
            return true;
        }
        // Checks if the declaring type has generic types.
        return hasDeclaringTypeWithGenerics(reference.getDeclaringType());
    }

    @java.lang.Override
    public <T> void visitCtLambda(spoon.reflect.code.CtLambda<T> lambda) {
        enterCtExpression(lambda);
        elementPrinterHelper.printList(lambda.getParameters(), null, false, "(", false, false, ",", false, false, ")", ( parameter) -> scan(parameter));
        printer.writeSpace();
        printer.writeSeparator("->");
        printer.writeSpace();
        if ((lambda.getBody()) != null) {
            scan(lambda.getBody());
        }else {
            scan(lambda.getExpression());
        }
        exitCtExpression(lambda);
    }

    @java.lang.Override
    public <T, E extends spoon.reflect.code.CtExpression<?>> void visitCtExecutableReferenceExpression(spoon.reflect.code.CtExecutableReferenceExpression<T, E> expression) {
        enterCtExpression(expression);
        try (spoon.reflect.visitor.PrintingContext.Writable _context = context.modify()) {
            if (expression.getExecutable().isStatic()) {
                _context.ignoreGenerics(true);
            }
            scan(expression.getTarget());
        }
        printer.writeSeparator("::");
        if (expression.getExecutable().isConstructor()) {
            printer.writeKeyword("new");
        }else {
            printer.writeIdentifier(expression.getExecutable().getSimpleName());
        }
        exitCtExpression(expression);
    }

    @java.lang.Override
    public <T, A extends T> void visitCtOperatorAssignment(spoon.reflect.code.CtOperatorAssignment<T, A> assignment) {
        enterCtStatement(assignment);
        enterCtExpression(assignment);
        scan(assignment.getAssigned());
        printer.writeSpace();
        // the operators like +=, *= are sent as one operator token
        printer.writeOperator(((spoon.reflect.visitor.OperatorHelper.getOperatorText(assignment.getKind())) + "="));
        printer.writeSpace();
        scan(assignment.getAssignment());
        exitCtExpression(assignment);
    }

    @java.lang.Override
    public void visitCtPackage(spoon.reflect.declaration.CtPackage ctPackage) {
        if (!(ctPackage.isUnnamedPackage())) {
            elementPrinterHelper.writePackageLine(ctPackage.getQualifiedName());
        }else {
            printer.writeComment(ctPackage.getFactory().createComment("default package (CtPackage.TOP_LEVEL_PACKAGE_NAME in Spoon= unnamed package)", spoon.reflect.code.CtComment.CommentType.INLINE)).writeln();
        }
    }

    @java.lang.Override
    public void visitCtPackageReference(spoon.reflect.reference.CtPackageReference reference) {
        elementPrinterHelper.writeQualifiedName(reference.getSimpleName());
    }

    @java.lang.Override
    public <T> void visitCtParameter(spoon.reflect.declaration.CtParameter<T> parameter) {
        elementPrinterHelper.writeComment(parameter);
        elementPrinterHelper.writeAnnotations(parameter);
        elementPrinterHelper.writeModifiers(parameter);
        if (parameter.isVarArgs()) {
            scan(((spoon.reflect.reference.CtArrayTypeReference<T>) (parameter.getType())).getComponentType());
            printer.writeSeparator("...");
        }else {
            scan(parameter.getType());
        }
        printer.writeSpace();
        printer.writeIdentifier(parameter.getSimpleName());
    }

    @java.lang.Override
    public <T> void visitCtParameterReference(spoon.reflect.reference.CtParameterReference<T> reference) {
        printer.writeIdentifier(reference.getSimpleName());
    }

    @java.lang.Override
    public <R> void visitCtReturn(spoon.reflect.code.CtReturn<R> returnStatement) {
        enterCtStatement(returnStatement);
        printer.writeKeyword("return");
        // checkstyle wants "return;" and not "return ;"
        if ((returnStatement.getReturnedExpression()) != null) {
            printer.writeSpace();
        }
        scan(returnStatement.getReturnedExpression());
    }

    private <T> void visitCtType(spoon.reflect.declaration.CtType<T> type) {
        elementPrinterHelper.writeComment(type, spoon.reflect.visitor.printer.CommentOffset.BEFORE);
        getPrinterHelper().mapLine(type, sourceCompilationUnit);
        if (type.isTopLevel()) {
            context.currentTopLevel = type;
        }
        elementPrinterHelper.visitCtNamedElement(type, sourceCompilationUnit);
        elementPrinterHelper.writeModifiers(type);
    }

    @java.lang.Override
    public void visitCtStatementList(spoon.reflect.code.CtStatementList statements) {
        for (spoon.reflect.code.CtStatement s : statements.getStatements()) {
            scan(s);
        }
    }

    @java.lang.Override
    public <E> void visitCtSwitch(spoon.reflect.code.CtSwitch<E> switchStatement) {
        enterCtStatement(switchStatement);
        printer.writeKeyword("switch").writeSpace().writeSeparator("(");
        scan(switchStatement.getSelector());
        printer.writeSeparator(")").writeSpace().writeSeparator("{").incTab();
        for (spoon.reflect.code.CtCase<?> c : switchStatement.getCases()) {
            printer.writeln();
            scan(c);
        }
        if (env.isPreserveLineNumbers()) {
            printer.decTab().writeSeparator("}");
        }else {
            printer.decTab().writeln().writeSeparator("}");
        }
    }

    @java.lang.Override
    public void visitCtSynchronized(spoon.reflect.code.CtSynchronized synchro) {
        enterCtStatement(synchro);
        printer.writeKeyword("synchronized");
        if ((synchro.getExpression()) != null) {
            printer.writeSeparator("(");
            scan(synchro.getExpression());
            printer.writeSeparator(")").writeSpace();
        }
        scan(synchro.getBlock());
    }

    @java.lang.Override
    public void visitCtThrow(spoon.reflect.code.CtThrow throwStatement) {
        enterCtStatement(throwStatement);
        printer.writeKeyword("throw").writeSpace();
        scan(throwStatement.getThrownExpression());
    }

    @java.lang.Override
    public void visitCtTry(spoon.reflect.code.CtTry tryBlock) {
        enterCtStatement(tryBlock);
        printer.writeKeyword("try").writeSpace();
        scan(tryBlock.getBody());
        for (spoon.reflect.code.CtCatch c : tryBlock.getCatchers()) {
            scan(c);
        }
        if ((tryBlock.getFinalizer()) != null) {
            printer.writeSpace().writeKeyword("finally").writeSpace();
            scan(tryBlock.getFinalizer());
        }
    }

    @java.lang.Override
    public void visitCtTryWithResource(spoon.reflect.code.CtTryWithResource tryWithResource) {
        enterCtStatement(tryWithResource);
        printer.writeKeyword("try").writeSpace();
        if (((tryWithResource.getResources()) != null) && (!(tryWithResource.getResources().isEmpty()))) {
            elementPrinterHelper.printList(tryWithResource.getResources(), null, false, "(", false, false, ";", false, false, ")", ( r) -> scan(r));
        }
        printer.writeSpace();
        scan(tryWithResource.getBody());
        for (spoon.reflect.code.CtCatch c : tryWithResource.getCatchers()) {
            scan(c);
        }
        if ((tryWithResource.getFinalizer()) != null) {
            printer.writeSpace().writeKeyword("finally").writeSpace();
            scan(tryWithResource.getFinalizer());
        }
    }

    @java.lang.Override
    public void visitCtTypeParameterReference(spoon.reflect.reference.CtTypeParameterReference ref) {
        if (ref.isImplicit()) {
            return;
        }
        elementPrinterHelper.writeAnnotations(ref);
        if (printQualified(ref)) {
            elementPrinterHelper.writeQualifiedName(ref.getQualifiedName());
        }else {
            printer.writeIdentifier(ref.getSimpleName());
        }
    }

    @java.lang.Override
    public void visitCtWildcardReference(spoon.reflect.reference.CtWildcardReference wildcardReference) {
        if (wildcardReference.isImplicit()) {
            return;
        }
        elementPrinterHelper.writeAnnotations(wildcardReference);
        printer.writeSeparator("?");
        // we ignore printing "extends Object" except if it's explicit
        if ((!(wildcardReference.isDefaultBoundingType())) || (!(wildcardReference.getBoundingType().isImplicit()))) {
            if (wildcardReference.isUpper()) {
                printer.writeSpace().writeKeyword("extends").writeSpace();
            }else {
                printer.writeSpace().writeKeyword("super").writeSpace();
            }
            scan(wildcardReference.getBoundingType());
        }
    }

    private boolean printQualified(spoon.reflect.reference.CtTypeReference<?> ref) {
        if ((importsContext.isImported(ref)) || (((this.env.isAutoImports()) && ((ref.getPackage()) != null)) && (ref.getPackage().getSimpleName().equals("java.lang")))) {
            // If my.pkg.Something is imported, but
            // A) we are in the context of a class which is also called "Something",
            // B) we are in the context of a class which defines field which is also called "Something",
            // we should still use qualified version my.pkg.Something
            for (spoon.reflect.visitor.TypeContext typeContext : context.currentThis) {
                if ((typeContext.getSimpleName().equals(ref.getSimpleName())) && (!(java.util.Objects.equals(typeContext.getPackage(), ref.getPackage())))) {
                    return true;
                }
                if (typeContext.isNameConflict(ref.getSimpleName())) {
                    return true;
                }
            }
            return false;
        }else {
            return true;
        }
    }

    @java.lang.Override
    public <T> void visitCtIntersectionTypeReference(spoon.reflect.reference.CtIntersectionTypeReference<T> reference) {
        elementPrinterHelper.printList(reference.getBounds(), null, false, null, false, true, "&", true, false, null, ( bound) -> scan(bound));
    }

    @java.lang.Override
    public <T> void visitCtTypeReference(spoon.reflect.reference.CtTypeReference<T> ref) {
        visitCtTypeReference(ref, true);
    }

    @java.lang.Override
    public <T> void visitCtTypeAccess(spoon.reflect.code.CtTypeAccess<T> typeAccess) {
        if (typeAccess.isImplicit()) {
            return;
        }
        enterCtExpression(typeAccess);
        scan(typeAccess.getAccessedType());
        exitCtExpression(typeAccess);
    }

    private void visitCtTypeReferenceWithoutGenerics(spoon.reflect.reference.CtTypeReference<?> ref) {
        visitCtTypeReference(ref, false);
    }

    private void visitCtTypeReference(spoon.reflect.reference.CtTypeReference<?> ref, boolean withGenerics) {
        if (ref.isImplicit()) {
            return;
        }
        if (ref.isPrimitive()) {
            elementPrinterHelper.writeAnnotations(ref);
            printer.writeKeyword(ref.getSimpleName());
            return;
        }
        boolean isInner = (ref.getDeclaringType()) != null;
        if (isInner) {
            if ((!(context.ignoreEnclosingClass())) && (!(ref.isLocalType()))) {
                // compute visible type which can be used to print access path to ref
                spoon.reflect.reference.CtTypeReference<?> accessType = ref.getAccessType();
                if (!(accessType.isAnonymous())) {
                    try (spoon.reflect.visitor.PrintingContext.Writable _context = context.modify()) {
                        if (!withGenerics) {
                            _context.ignoreGenerics(true);
                        }
                        scan(accessType);
                    }
                    printer.writeSeparator(".");
                }
            }
            // ?? are these annotations on correct place ??
            elementPrinterHelper.writeAnnotations(ref);
            if (ref.isLocalType()) {
                printer.writeIdentifier(ref.getSimpleName().replaceAll("^[0-9]*", ""));
            }else {
                printer.writeIdentifier(ref.getSimpleName());
            }
        }else {
            if (((ref.getPackage()) != null) && (printQualified(ref))) {
                if (!(ref.getPackage().isUnnamedPackage())) {
                    scan(ref.getPackage());
                    printer.writeSeparator(spoon.reflect.declaration.CtPackage.PACKAGE_SEPARATOR);
                }
            }
            elementPrinterHelper.writeAnnotations(ref);
            printer.writeIdentifier(ref.getSimpleName());
        }
        if (withGenerics && (!(context.ignoreGenerics()))) {
            try (spoon.reflect.visitor.PrintingContext.Writable _context = context.modify().ignoreEnclosingClass(false)) {
                elementPrinterHelper.writeActualTypeArguments(ref);
            }
        }
    }

    @java.lang.Override
    public <T> void visitCtUnaryOperator(spoon.reflect.code.CtUnaryOperator<T> operator) {
        enterCtStatement(operator);
        enterCtExpression(operator);
        spoon.reflect.code.UnaryOperatorKind op = operator.getKind();
        if (spoon.reflect.visitor.OperatorHelper.isPrefixOperator(op)) {
            printer.writeOperator(spoon.reflect.visitor.OperatorHelper.getOperatorText(op));
        }
        scan(operator.getOperand());
        if (spoon.reflect.visitor.OperatorHelper.isSufixOperator(op)) {
            printer.writeOperator(spoon.reflect.visitor.OperatorHelper.getOperatorText(op));
        }
        exitCtExpression(operator);
    }

    @java.lang.Override
    public <T> void visitCtVariableRead(spoon.reflect.code.CtVariableRead<T> variableRead) {
        enterCtExpression(variableRead);
        printer.writeIdentifier(variableRead.getVariable().getSimpleName());
        exitCtExpression(variableRead);
    }

    @java.lang.Override
    public <T> void visitCtVariableWrite(spoon.reflect.code.CtVariableWrite<T> variableWrite) {
        enterCtExpression(variableWrite);
        printer.writeIdentifier(variableWrite.getVariable().getSimpleName());
        exitCtExpression(variableWrite);
    }

    @java.lang.Override
    public void visitCtWhile(spoon.reflect.code.CtWhile whileLoop) {
        enterCtStatement(whileLoop);
        printer.writeKeyword("while").writeSpace().writeSeparator("(");
        scan(whileLoop.getLoopingExpression());
        printer.writeSeparator(")");
        elementPrinterHelper.writeIfOrLoopBlock(whileLoop.getBody());
    }

    @java.lang.Override
    public <T> void visitCtCodeSnippetExpression(spoon.reflect.code.CtCodeSnippetExpression<T> expression) {
        elementPrinterHelper.writeComment(expression);
        printer.writeCodeSnippet(expression.getValue());
    }

    @java.lang.Override
    public void visitCtCodeSnippetStatement(spoon.reflect.code.CtCodeSnippetStatement statement) {
        elementPrinterHelper.writeComment(statement);
        printer.writeCodeSnippet(statement.getValue());
    }

    public spoon.reflect.visitor.ElementPrinterHelper getElementPrinterHelper() {
        return elementPrinterHelper;
    }

    public spoon.reflect.visitor.PrintingContext getContext() {
        return context;
    }

    @java.lang.Override
    public <T> void visitCtUnboundVariableReference(spoon.reflect.reference.CtUnboundVariableReference<T> reference) {
        printer.writeIdentifier(reference.getSimpleName());
    }

    @java.lang.Override
    public java.lang.String printPackageInfo(spoon.reflect.declaration.CtPackage pack) {
        reset();
        elementPrinterHelper.writeComment(pack);
        // we need to compute imports only for annotations
        // we don't want to get all imports coming from content of package
        for (spoon.reflect.declaration.CtAnnotation annotation : pack.getAnnotations()) {
            this.importsContext.computeImports(annotation);
        }
        elementPrinterHelper.writeAnnotations(pack);
        if (!(pack.isUnnamedPackage())) {
            elementPrinterHelper.writePackageLine(pack.getQualifiedName());
        }
        elementPrinterHelper.writeImports(this.importsContext.getAllImports());
        return printer.getPrinterHelper().toString();
    }

    @java.lang.Override
    public java.lang.String printModuleInfo(spoon.reflect.declaration.CtModule module) {
        reset();
        scan(module);
        return this.getResult();
    }

    @java.lang.Override
    public java.lang.String getResult() {
        return printer.getPrinterHelper().toString();
    }

    private void reset() {
        printer.reset();
        context = new spoon.reflect.visitor.PrintingContext();
        if (env.isAutoImports()) {
            this.importsContext = new spoon.reflect.visitor.ImportScannerImpl();
        }else {
            this.importsContext = new spoon.reflect.visitor.MinimalImportScanner();
        }
    }

    /**
     * Write the compilation unit header.
     */
    public spoon.reflect.visitor.DefaultJavaPrettyPrinter writeHeader(java.util.List<spoon.reflect.declaration.CtType<?>> types, java.util.Collection<spoon.reflect.declaration.CtImport> imports) {
        elementPrinterHelper.writeHeader(types, imports);
        return this;
    }

    /**
     * Write the compilation unit footer.
     */
    public spoon.reflect.visitor.DefaultJavaPrettyPrinter writeFooter(java.util.List<spoon.reflect.declaration.CtType<?>> types) {
        elementPrinterHelper.writeFooter(types);
        return this;
    }

    @java.lang.Override
    public void calculate(spoon.reflect.cu.CompilationUnit sourceCompilationUnit, java.util.List<spoon.reflect.declaration.CtType<?>> types) {
        // reset the importsContext to avoid errors with multiple CU
        reset();
        this.sourceCompilationUnit = sourceCompilationUnit;
        this.imports = new java.util.HashSet<>();
        if (sourceCompilationUnit != null) {
            imports.addAll(sourceCompilationUnit.getImports());
        }
        for (spoon.reflect.declaration.CtType<?> t : types) {
            imports.addAll(computeImports(t));
        }
        this.writeHeader(types, imports);
        for (spoon.reflect.declaration.CtType<?> t : types) {
            scan(t);
            if (!(env.isPreserveLineNumbers())) {
                // saving lines and chars
                printer.writeln().writeln();
            }else {
                getPrinterHelper().adjustEndPosition(t);
            }
        }
        this.writeFooter(types);
    }

    @java.lang.Override
    public java.util.Map<java.lang.Integer, java.lang.Integer> getLineNumberMapping() {
        return getPrinterHelper().getLineNumberMapping();
    }

    /**
     *
     *
     * @return current {@link TokenWriter}, so the subclasses of {@link DefaultJavaPrettyPrinter} can print tokens too
     */
    protected spoon.reflect.visitor.TokenWriter getPrinterTokenWriter() {
        return printer;
    }

    /**
     * Set {@link TokenWriter}, which has to be used to print tokens
     */
    public spoon.reflect.visitor.DefaultJavaPrettyPrinter setPrinterTokenWriter(spoon.reflect.visitor.TokenWriter tokenWriter) {
        elementPrinterHelper = new spoon.reflect.visitor.ElementPrinterHelper(tokenWriter, this, env);
        printer = tokenWriter;
        return this;
    }

    private spoon.reflect.visitor.PrinterHelper getPrinterHelper() {
        return printer.getPrinterHelper();
    }
}

