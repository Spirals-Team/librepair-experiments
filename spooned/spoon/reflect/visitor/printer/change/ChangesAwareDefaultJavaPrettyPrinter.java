package spoon.reflect.visitor.printer.change;


public class ChangesAwareDefaultJavaPrettyPrinter extends spoon.reflect.visitor.DefaultJavaPrettyPrinter {
    private final spoon.reflect.visitor.printer.change.MutableTokenWriter mutableTokenWriter;

    private final spoon.experimental.modelobs.ChangeCollector changeCollector;

    private final java.util.Deque<spoon.reflect.visitor.printer.change.SourceFragmentContext> sourceFragmentContextStack = new java.util.ArrayDeque<>();

    public ChangesAwareDefaultJavaPrettyPrinter(spoon.compiler.Environment env) {
        super(env);
        this.changeCollector = spoon.experimental.modelobs.ChangeCollector.getChangeCollector(env);
        if ((this.changeCollector) == null) {
            throw new spoon.SpoonException(((spoon.experimental.modelobs.ChangeCollector.class.getSimpleName()) + " was not attached to the Environment"));
        }
        mutableTokenWriter = new spoon.reflect.visitor.printer.change.MutableTokenWriter(env);
        setPrinterTokenWriter(createTokenWriterListener(mutableTokenWriter));
    }

    private spoon.reflect.visitor.TokenWriter createTokenWriterListener(spoon.reflect.visitor.TokenWriter tokenWriter) {
        return new spoon.reflect.visitor.printer.change.ChangesAwareDefaultJavaPrettyPrinter.TokenWriterProxy(tokenWriter);
    }

    private class TokenWriterProxy implements spoon.reflect.visitor.TokenWriter {
        private final spoon.reflect.visitor.TokenWriter delegate;

        TokenWriterProxy(spoon.reflect.visitor.TokenWriter delegate) {
            super();
            this.delegate = delegate;
        }

        public spoon.reflect.visitor.TokenWriter writeSeparator(java.lang.String token) {
            onTokenWriterWrite("writeSeparator", token, null, () -> delegate.writeSeparator(token));
            return this;
        }

        public spoon.reflect.visitor.TokenWriter writeOperator(java.lang.String token) {
            onTokenWriterWrite("writeOperator", token, null, () -> delegate.writeOperator(token));
            return this;
        }

        public spoon.reflect.visitor.TokenWriter writeLiteral(java.lang.String token) {
            onTokenWriterWrite("writeLiteral", token, null, () -> delegate.writeLiteral(token));
            return this;
        }

        public spoon.reflect.visitor.TokenWriter writeKeyword(java.lang.String token) {
            onTokenWriterWrite("writeKeyword", token, null, () -> delegate.writeKeyword(token));
            return this;
        }

        public spoon.reflect.visitor.TokenWriter writeIdentifier(java.lang.String token) {
            onTokenWriterWrite("writeIdentifier", token, null, () -> delegate.writeIdentifier(token));
            return this;
        }

        public spoon.reflect.visitor.TokenWriter writeCodeSnippet(java.lang.String token) {
            onTokenWriterWrite("writeCodeSnippet", token, null, () -> delegate.writeCodeSnippet(token));
            return this;
        }

        public spoon.reflect.visitor.TokenWriter writeComment(spoon.reflect.code.CtComment comment) {
            onTokenWriterWrite("writeComment", null, comment, () -> delegate.writeComment(comment));
            return this;
        }

        public spoon.reflect.visitor.TokenWriter writeln() {
            onTokenWriterWrite("writeln", "\n", null, () -> delegate.writeln());
            return this;
        }

        public spoon.reflect.visitor.TokenWriter incTab() {
            onTokenWriterWrite("incTab", null, null, () -> delegate.incTab());
            return this;
        }

        public spoon.reflect.visitor.TokenWriter decTab() {
            onTokenWriterWrite("decTab", null, null, () -> delegate.decTab());
            return this;
        }

        public spoon.reflect.visitor.PrinterHelper getPrinterHelper() {
            return delegate.getPrinterHelper();
        }

        public void reset() {
            delegate.reset();
        }

        public spoon.reflect.visitor.TokenWriter writeSpace() {
            onTokenWriterWrite("writeSpace", " ", null, () -> delegate.writeSpace());
            return this;
        }
    }

    private void onTokenWriterWrite(java.lang.String tokenWriterMethodName, java.lang.String token, spoon.reflect.code.CtComment comment, java.lang.Runnable printAction) {
        spoon.reflect.visitor.printer.change.SourceFragmentContext sfc = sourceFragmentContextStack.peek();
        if (sfc != null) {
            sfc.onTokenWriterToken(tokenWriterMethodName, token, printAction);
            return;
        }
        printAction.run();
    }

    @java.lang.Override
    public spoon.reflect.visitor.printer.change.ChangesAwareDefaultJavaPrettyPrinter scan(spoon.reflect.declaration.CtElement element) {
        spoon.reflect.visitor.printer.change.SourceFragmentContext sfc = sourceFragmentContextStack.peek();
        if (sfc != null) {
            spoon.reflect.path.CtRole role = element.getRoleInParent();
            if (role != null) {
                sfc.onScanElementOnRole(element, role, () -> scanInternal(element));
                return this;
            }
        }
        scanInternal(element);
        return this;
    }

    private void scanInternal(spoon.reflect.declaration.CtElement element) {
        if (mutableTokenWriter.isMuted()) {
            sourceFragmentContextStack.push(spoon.reflect.visitor.printer.change.SourceFragmentContextNormal.EMPTY_FRAGMENT_CONTEXT);
            super.scan(element);
            sourceFragmentContextStack.pop();
            return;
        }
        spoon.reflect.visitor.printer.change.SourceFragment rootFragmentOfElement = spoon.reflect.visitor.printer.change.ChangesAwareDefaultJavaPrettyPrinter.getSourceFragmentsOfElementUsableForPrintingOfOriginCode(changeCollector, element);
        if (rootFragmentOfElement == null) {
            sourceFragmentContextStack.push(spoon.reflect.visitor.printer.change.SourceFragmentContextNormal.EMPTY_FRAGMENT_CONTEXT);
            super.scan(element);
            sourceFragmentContextStack.pop();
            return;
        }
        spoon.reflect.visitor.printer.change.SourceFragmentContextNormal sfx = new spoon.reflect.visitor.printer.change.SourceFragmentContextNormal(mutableTokenWriter, element, rootFragmentOfElement);
        sourceFragmentContextStack.push(sfx);
        super.scan(element);
        sourceFragmentContextStack.pop();
        mutableTokenWriter.setMuted(false);
    }

    private static spoon.reflect.visitor.printer.change.SourceFragment getSourceFragmentsOfElementUsableForPrintingOfOriginCode(spoon.experimental.modelobs.ChangeCollector changeCollector, spoon.reflect.declaration.CtElement element) {
        spoon.reflect.visitor.printer.change.SourceFragment rootFragmentOfElement = spoon.reflect.visitor.printer.change.SourcePositionUtils.getSourceFragmentOfElement(element);
        if (rootFragmentOfElement == null) {
            return null;
        }
        java.util.Set<spoon.reflect.path.CtRole> changedRoles = changeCollector.getChanges(element);
        if (changedRoles.isEmpty()) {
            return rootFragmentOfElement;
        }
        spoon.reflect.visitor.printer.change.SourceFragment childSourceFragmentsOfSameElement = rootFragmentOfElement.getChildFragmentOfSameElement();
        if ((childSourceFragmentsOfSameElement == null) || ((childSourceFragmentsOfSameElement.getNextFragmentOfSameElement()) == null)) {
            return null;
        }
        if (spoon.reflect.visitor.printer.change.SourcePositionUtils.markChangedFragments(element, childSourceFragmentsOfSameElement, changedRoles)) {
            return childSourceFragmentsOfSameElement;
        }
        return null;
    }
}

