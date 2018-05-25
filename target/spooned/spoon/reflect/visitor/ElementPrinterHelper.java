package spoon.reflect.visitor;


public class ElementPrinterHelper {
    private final spoon.reflect.visitor.DefaultJavaPrettyPrinter prettyPrinter;

    private final spoon.compiler.Environment env;

    private spoon.reflect.visitor.TokenWriter printer;

    public ElementPrinterHelper(spoon.reflect.visitor.TokenWriter printerTokenWriter, spoon.reflect.visitor.DefaultJavaPrettyPrinter prettyPrinter, spoon.compiler.Environment env) {
        this.printer = printerTokenWriter;
        this.prettyPrinter = prettyPrinter;
        this.env = env;
    }

    public void writeAnnotations(spoon.reflect.declaration.CtElement element) {
        for (spoon.reflect.declaration.CtAnnotation<?> annotation : element.getAnnotations()) {
            if ((((element.isParentInitialized()) && (element instanceof spoon.reflect.reference.CtTypeReference)) && ((element.getParent()) instanceof spoon.reflect.declaration.CtTypedElement)) && (element.getParent().getAnnotations().contains(annotation))) {
                continue;
            }
            prettyPrinter.scan(annotation);
            printer.writeln();
        }
    }

    public void writeModifiers(spoon.reflect.declaration.CtModifiable modifiable) {
        java.util.List<java.lang.String> firstPosition = new java.util.ArrayList<>();
        java.util.List<java.lang.String> secondPosition = new java.util.ArrayList<>();
        java.util.List<java.lang.String> thirdPosition = new java.util.ArrayList<>();
        for (spoon.support.reflect.CtExtendedModifier extendedModifier : modifiable.getExtendedModifiers()) {
            if (!(extendedModifier.isImplicit())) {
                spoon.reflect.declaration.ModifierKind modifierKind = extendedModifier.getKind();
                if (((modifierKind == (spoon.reflect.declaration.ModifierKind.PUBLIC)) || (modifierKind == (spoon.reflect.declaration.ModifierKind.PRIVATE))) || (modifierKind == (spoon.reflect.declaration.ModifierKind.PROTECTED))) {
                    firstPosition.add(modifierKind.toString());
                }else
                    if ((modifierKind == (spoon.reflect.declaration.ModifierKind.ABSTRACT)) || (modifierKind == (spoon.reflect.declaration.ModifierKind.STATIC))) {
                        secondPosition.add(modifierKind.toString());
                    }else {
                        thirdPosition.add(modifierKind.toString());
                    }

            }
        }
        for (java.lang.String s : firstPosition) {
            printer.writeKeyword(s).writeSpace();
        }
        for (java.lang.String s : secondPosition) {
            printer.writeKeyword(s).writeSpace();
        }
        for (java.lang.String s : thirdPosition) {
            printer.writeKeyword(s).writeSpace();
        }
        if (modifiable instanceof spoon.reflect.declaration.CtMethod) {
            spoon.reflect.declaration.CtMethod m = ((spoon.reflect.declaration.CtMethod) (modifiable));
            if (m.isDefaultMethod()) {
                printer.writeKeyword("default").writeSpace();
            }
        }
    }

    public void visitCtNamedElement(spoon.reflect.declaration.CtNamedElement namedElement, spoon.reflect.cu.CompilationUnit sourceCompilationUnit) {
        writeAnnotations(namedElement);
        if (env.isPreserveLineNumbers()) {
            getPrinterHelper().adjustStartPosition(namedElement);
        }
    }

    public void writeExtendsClause(spoon.reflect.declaration.CtType<?> type) {
        if ((type.getSuperclass()) != null) {
            printer.writeSpace().writeKeyword("extends").writeSpace();
            prettyPrinter.scan(type.getSuperclass());
        }
    }

    public void writeImplementsClause(spoon.reflect.declaration.CtType<?> type) {
        if ((type.getSuperInterfaces().size()) > 0) {
            printList(type.getSuperInterfaces(), "implements", false, null, false, true, ",", true, false, null, ( ref) -> prettyPrinter.scan(ref));
        }
    }

    public void writeExecutableParameters(spoon.reflect.declaration.CtExecutable<?> executable) {
        printList(executable.getParameters(), null, false, "(", false, false, ",", true, false, ")", ( p) -> prettyPrinter.scan(p));
    }

    public void writeThrowsClause(spoon.reflect.declaration.CtExecutable<?> executable) {
        if ((executable.getThrownTypes().size()) > 0) {
            printList(executable.getThrownTypes(), "throws", false, null, false, false, ",", true, false, null, ( ref) -> prettyPrinter.scan(ref));
        }
    }

    public void writeStatement(spoon.reflect.code.CtStatement statement) {
        prettyPrinter.scan(statement);
        if (!((((((((((statement instanceof spoon.reflect.code.CtBlock) || (statement instanceof spoon.reflect.code.CtIf)) || (statement instanceof spoon.reflect.code.CtFor)) || (statement instanceof spoon.reflect.code.CtForEach)) || (statement instanceof spoon.reflect.code.CtWhile)) || (statement instanceof spoon.reflect.code.CtTry)) || (statement instanceof spoon.reflect.code.CtSwitch)) || (statement instanceof spoon.reflect.code.CtSynchronized)) || (statement instanceof spoon.reflect.declaration.CtClass)) || (statement instanceof spoon.reflect.code.CtComment))) {
            printer.writeSeparator(";");
        }
        writeComment(statement, spoon.reflect.visitor.printer.CommentOffset.AFTER);
    }

    public void writeElementList(java.util.List<spoon.reflect.declaration.CtTypeMember> elements) {
        for (spoon.reflect.declaration.CtTypeMember element : elements) {
            if ((element instanceof spoon.reflect.declaration.CtConstructor) && (element.isImplicit())) {
                continue;
            }
            printer.writeln();
            prettyPrinter.scan(element);
            if (!(env.isPreserveLineNumbers())) {
                printer.writeln();
            }
        }
    }

    public void writeAnnotationElement(spoon.reflect.factory.Factory factory, java.lang.Object value) {
        if (value instanceof spoon.reflect.code.CtTypeAccess) {
            prettyPrinter.scan(((spoon.reflect.code.CtTypeAccess) (value)));
            printer.writeSeparator(".").writeKeyword("class");
        }else
            if (value instanceof spoon.reflect.reference.CtFieldReference) {
                prettyPrinter.scan(((spoon.reflect.reference.CtFieldReference<?>) (value)).getDeclaringType());
                printer.writeSeparator(".").writeIdentifier(((spoon.reflect.reference.CtFieldReference<?>) (value)).getSimpleName());
            }else
                if (value instanceof spoon.reflect.declaration.CtElement) {
                    prettyPrinter.scan(((spoon.reflect.declaration.CtElement) (value)));
                }else
                    if (value instanceof java.lang.String) {
                        printer.writeLiteral((("\"" + (spoon.reflect.visitor.LiteralHelper.getStringLiteral(((java.lang.String) (value)), true))) + "\""));
                    }else
                        if (value instanceof java.util.Collection) {
                            printList(((java.util.Collection<?>) (value)), null, false, "{", false, true, ",", false, false, "}", ( obj) -> writeAnnotationElement(factory, obj));
                        }else
                            if (value instanceof java.lang.Object[]) {
                                printList(java.util.Arrays.asList(((java.lang.Object[]) (value))), null, false, "{", false, true, ",", false, false, "}", ( obj) -> writeAnnotationElement(factory, obj));
                            }else
                                if (value instanceof java.lang.Enum) {
                                    try (spoon.reflect.visitor.PrintingContext.Writable c = prettyPrinter.getContext().modify().ignoreGenerics(true)) {
                                        prettyPrinter.scan(factory.Type().createReference(((java.lang.Enum<?>) (value)).getDeclaringClass()));
                                    }
                                    printer.writeSeparator(".");
                                    printer.writeIdentifier(value.toString());
                                }else {
                                    printer.writeLiteral(value.toString());
                                }






    }

    public void writeFormalTypeParameters(spoon.reflect.declaration.CtFormalTypeDeclarer ctFormalTypeDeclarer) {
        final java.util.Collection<spoon.reflect.declaration.CtTypeParameter> parameters = ctFormalTypeDeclarer.getFormalCtTypeParameters();
        if (parameters == null) {
            return;
        }
        if ((parameters.size()) > 0) {
            printList(parameters, null, false, "<", false, false, ",", true, false, ">", ( parameter) -> prettyPrinter.scan(parameter));
        }
    }

    public void writeActualTypeArguments(spoon.reflect.reference.CtActualTypeContainer ctGenericElementReference) {
        final java.util.Collection<spoon.reflect.reference.CtTypeReference<?>> arguments = ctGenericElementReference.getActualTypeArguments();
        if ((arguments != null) && ((arguments.size()) > 0)) {
            printList(arguments.stream().filter(( a) -> !(a.isImplicit()))::iterator, null, false, "<", false, false, ",", true, false, ">", ( argument) -> {
                if (prettyPrinter.context.forceWildcardGenerics()) {
                    printer.writeSeparator("?");
                }else {
                    prettyPrinter.scan(argument);
                }
            });
        }
    }

    private boolean isJavaLangClasses(java.lang.String importType) {
        return importType.matches("^(java\\.lang\\.)[^.]*$");
    }

    public void writeImports(java.util.Collection<spoon.reflect.declaration.CtImport> imports) {
        java.util.Set<java.lang.String> setImports = new java.util.HashSet<>();
        java.util.Set<java.lang.String> setStaticImports = new java.util.HashSet<>();
        for (spoon.reflect.declaration.CtImport ctImport : imports) {
            java.lang.String importTypeStr;
            switch (ctImport.getImportKind()) {
                case TYPE :
                    spoon.reflect.reference.CtTypeReference typeRef = ((spoon.reflect.reference.CtTypeReference) (ctImport.getReference()));
                    importTypeStr = typeRef.getQualifiedName();
                    if (!(isJavaLangClasses(importTypeStr))) {
                        setImports.add(importTypeStr);
                    }
                    break;
                case ALL_TYPES :
                    spoon.reflect.reference.CtPackageReference packageRef = ((spoon.reflect.reference.CtPackageReference) (ctImport.getReference()));
                    importTypeStr = (packageRef.getQualifiedName()) + ".*";
                    if (!(isJavaLangClasses(importTypeStr))) {
                        setImports.add(importTypeStr);
                    }
                    break;
                case METHOD :
                    spoon.reflect.reference.CtExecutableReference execRef = ((spoon.reflect.reference.CtExecutableReference) (ctImport.getReference()));
                    if ((execRef.getDeclaringType()) != null) {
                        setStaticImports.add((((this.removeInnerTypeSeparator(execRef.getDeclaringType().getQualifiedName())) + ".") + (execRef.getSimpleName())));
                    }
                    break;
                case FIELD :
                    spoon.reflect.reference.CtFieldReference fieldRef = ((spoon.reflect.reference.CtFieldReference) (ctImport.getReference()));
                    setStaticImports.add((((this.removeInnerTypeSeparator(fieldRef.getDeclaringType().getQualifiedName())) + ".") + (fieldRef.getSimpleName())));
                    break;
                case ALL_STATIC_MEMBERS :
                    spoon.reflect.reference.CtTypeReference typeStarRef = ((spoon.reflect.reference.CtTypeReference) (ctImport.getReference()));
                    importTypeStr = typeStarRef.getQualifiedName();
                    if (!(isJavaLangClasses(importTypeStr))) {
                        setStaticImports.add(importTypeStr);
                    }
                    break;
            }
        }
        java.util.List<java.lang.String> sortedImports = new java.util.ArrayList<>(setImports);
        java.util.Collections.sort(sortedImports);
        boolean isFirst = true;
        for (java.lang.String importLine : sortedImports) {
            if (isFirst) {
                printer.writeln();
                printer.writeln();
                isFirst = false;
            }
            printer.writeKeyword("import").writeSpace();
            writeQualifiedName(importLine).writeSeparator(";").writeln();
        }
        if ((setStaticImports.size()) > 0) {
            if (isFirst) {
                printer.writeln();
            }
            printer.writeln();
            java.util.List<java.lang.String> sortedStaticImports = new java.util.ArrayList<>(setStaticImports);
            java.util.Collections.sort(sortedStaticImports);
            for (java.lang.String importLine : sortedStaticImports) {
                printer.writeKeyword("import").writeSpace().writeKeyword("static").writeSpace();
                writeQualifiedName(importLine).writeSeparator(";").writeln();
            }
        }
    }

    public void writeHeader(java.util.List<spoon.reflect.declaration.CtType<?>> types, java.util.Collection<spoon.reflect.declaration.CtImport> imports) {
        if (!(types.isEmpty())) {
            for (spoon.reflect.declaration.CtType<?> ctType : types) {
                writeComment(ctType, spoon.reflect.visitor.printer.CommentOffset.TOP_FILE);
            }
            if (!(types.get(0).getPackage().isUnnamedPackage())) {
                writePackageLine(types.get(0).getPackage().getQualifiedName());
            }
            this.writeImports(imports);
            printer.writeln();
            printer.writeln();
        }
    }

    public void writeFooter(java.util.List<spoon.reflect.declaration.CtType<?>> types) {
        if (!(types.isEmpty())) {
            for (spoon.reflect.declaration.CtType<?> ctType : types) {
                writeComment(ctType, spoon.reflect.visitor.printer.CommentOffset.BOTTOM_FILE);
            }
        }
    }

    public void writePackageLine(java.lang.String packageQualifiedName) {
        printer.writeKeyword("package").writeSpace();
        writeQualifiedName(packageQualifiedName).writeSeparator(";").writeln();
    }

    private java.lang.String removeInnerTypeSeparator(java.lang.String fqn) {
        return fqn.replace(spoon.reflect.declaration.CtType.INNERTTYPE_SEPARATOR, ".");
    }

    public void writeComment(spoon.reflect.code.CtComment comment) {
        if ((!(env.isCommentsEnabled())) || (comment == null)) {
            return;
        }
        prettyPrinter.scan(comment);
        printer.writeln();
    }

    private void writeComment(java.util.List<spoon.reflect.code.CtComment> comments) {
        if ((!(env.isCommentsEnabled())) || (comments == null)) {
            return;
        }
        for (spoon.reflect.code.CtComment comment : comments) {
            writeComment(comment);
        }
    }

    public void writeComment(spoon.reflect.declaration.CtElement element) {
        if (element == null) {
            return;
        }
        writeComment(element.getComments());
    }

    public void writeComment(spoon.reflect.declaration.CtElement element, spoon.reflect.visitor.printer.CommentOffset offset) {
        writeComment(getComments(element, offset));
    }

    public java.util.List<spoon.reflect.code.CtComment> getComments(spoon.reflect.declaration.CtElement element, spoon.reflect.visitor.printer.CommentOffset offset) {
        java.util.List<spoon.reflect.code.CtComment> commentsToPrint = new java.util.ArrayList<>();
        if ((!(env.isCommentsEnabled())) || (element == null)) {
            return commentsToPrint;
        }
        for (spoon.reflect.code.CtComment comment : element.getComments()) {
            if ((((comment.getCommentType()) == (spoon.reflect.code.CtComment.CommentType.FILE)) && (offset == (spoon.reflect.visitor.printer.CommentOffset.TOP_FILE))) && ((element.getPosition().getSourceEnd()) > (comment.getPosition().getSourceStart()))) {
                commentsToPrint.add(comment);
                continue;
            }
            if ((((comment.getCommentType()) == (spoon.reflect.code.CtComment.CommentType.FILE)) && (offset == (spoon.reflect.visitor.printer.CommentOffset.BOTTOM_FILE))) && ((element.getPosition().getSourceEnd()) < (comment.getPosition().getSourceStart()))) {
                commentsToPrint.add(comment);
                continue;
            }
            if ((comment.getCommentType()) == (spoon.reflect.code.CtComment.CommentType.FILE)) {
                continue;
            }
            if (((comment.getPosition().isValidPosition()) == false) || ((element.getPosition().isValidPosition()) == false)) {
                if (offset == (spoon.reflect.visitor.printer.CommentOffset.BEFORE)) {
                    commentsToPrint.add(comment);
                }
                continue;
            }
            final int line = element.getPosition().getLine();
            final int sourceEnd = element.getPosition().getSourceEnd();
            final int sourceStart = element.getPosition().getSourceStart();
            if ((offset == (spoon.reflect.visitor.printer.CommentOffset.BEFORE)) && (((comment.getPosition().getLine()) < line) || ((sourceStart <= (comment.getPosition().getSourceStart())) && (sourceEnd > (comment.getPosition().getSourceEnd()))))) {
                commentsToPrint.add(comment);
            }else
                if ((offset == (spoon.reflect.visitor.printer.CommentOffset.AFTER)) && (((comment.getPosition().getSourceStart()) > sourceEnd) || ((comment.getPosition().getSourceEnd()) == sourceEnd))) {
                    commentsToPrint.add(comment);
                }else {
                    final int endLine = element.getPosition().getEndLine();
                    if (((offset == (spoon.reflect.visitor.printer.CommentOffset.INSIDE)) && ((comment.getPosition().getLine()) >= line)) && ((comment.getPosition().getEndLine()) <= endLine)) {
                        commentsToPrint.add(comment);
                    }
                }

        }
        return commentsToPrint;
    }

    public void writeIfOrLoopBlock(spoon.reflect.code.CtStatement block) {
        if (block != null) {
            if ((!(block.isImplicit())) && ((block instanceof spoon.reflect.code.CtBlock) || (block instanceof spoon.reflect.code.CtIf))) {
                printer.writeSpace();
            }
            if ((!(block instanceof spoon.reflect.code.CtBlock)) && (!(block instanceof spoon.reflect.code.CtIf))) {
                printer.incTab();
                printer.writeln();
            }
            writeStatement(block);
            if ((!(block instanceof spoon.reflect.code.CtBlock)) && (!(block instanceof spoon.reflect.code.CtIf))) {
                printer.decTab().writeln();
            }
            if (!(block.isImplicit())) {
                if ((!(block.isParentInitialized())) || (((!((block.getParent()) instanceof spoon.reflect.code.CtFor)) && (!((block.getParent()) instanceof spoon.reflect.code.CtForEach))) && (!((block.getParent()) instanceof spoon.reflect.code.CtIf)))) {
                    printer.writeSpace();
                }
            }
        }else {
            printer.writeSeparator(";");
        }
    }

    private spoon.reflect.visitor.ListPrinter createListPrinter(boolean startPrefixSpace, java.lang.String start, boolean startSufficSpace, boolean nextPrefixSpace, java.lang.String next, boolean nextSuffixSpace, boolean endPrefixSpace, java.lang.String end) {
        return new spoon.reflect.visitor.ListPrinter(printer, startPrefixSpace, start, startSufficSpace, nextPrefixSpace, next, nextSuffixSpace, endPrefixSpace, end);
    }

    private static final java.lang.String QALIFIED_NAME_SEPARATORS = ".$";

    public spoon.reflect.visitor.TokenWriter writeQualifiedName(java.lang.String qualifiedName) {
        java.util.StringTokenizer st = new java.util.StringTokenizer(qualifiedName, spoon.reflect.visitor.ElementPrinterHelper.QALIFIED_NAME_SEPARATORS, true);
        while (st.hasMoreTokens()) {
            java.lang.String token = st.nextToken();
            if (((token.length()) == 1) && ((spoon.reflect.visitor.ElementPrinterHelper.QALIFIED_NAME_SEPARATORS.indexOf(token.charAt(0))) >= 0)) {
                printer.writeSeparator(token);
            }else {
                printer.writeIdentifier(token);
            }
        } 
        return printer;
    }

    private spoon.reflect.visitor.PrinterHelper getPrinterHelper() {
        return printer.getPrinterHelper();
    }

    public <T> void printList(java.lang.Iterable<T> iterable, java.lang.String startKeyword, boolean startPrefixSpace, java.lang.String start, boolean startSuffixSpace, boolean nextPrefixSpace, java.lang.String next, boolean nextSuffixSpace, boolean endPrefixSpace, java.lang.String end, java.util.function.Consumer<T> elementPrinter) {
        if (startKeyword != null) {
            printer.writeSpace().writeKeyword(startKeyword).writeSpace();
        }
        try (spoon.reflect.visitor.ListPrinter lp = createListPrinter(startPrefixSpace, start, startSuffixSpace, nextPrefixSpace, next, nextSuffixSpace, endPrefixSpace, end)) {
            for (T item : iterable) {
                lp.printSeparatorIfAppropriate();
                elementPrinter.accept(item);
            }
        }
    }
}

