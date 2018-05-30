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
package spoon.reflect.visitor.printer.change;


/**
 * {@link PrettyPrinter} implementation which copies as much as possible from origin sources
 * and prints only changed elements
 */
public class ChangesAwareDefaultJavaPrettyPrinter extends spoon.reflect.visitor.DefaultJavaPrettyPrinter {
    private final spoon.reflect.visitor.printer.change.MutableTokenWriter mutableTokenWriter;

    private final spoon.experimental.modelobs.ChangeCollector changeCollector;

    private final java.util.Deque<spoon.reflect.visitor.printer.change.SourceFragmentContext> sourceFragmentContextStack = new java.util.ArrayDeque<>();

    /**
     * Creates a new {@link PrettyPrinter} which copies origin sources and prints only changes.
     */
    public ChangesAwareDefaultJavaPrettyPrinter(spoon.compiler.Environment env) {
        super(env);
        this.changeCollector = spoon.experimental.modelobs.ChangeCollector.getChangeCollector(env);
        if ((this.changeCollector) == null) {
            throw new spoon.SpoonException(((spoon.experimental.modelobs.ChangeCollector.class.getSimpleName()) + " was not attached to the Environment"));
        }
        // create a TokenWriter which can be configured to ignore tokens coming from DJPP
        mutableTokenWriter = new spoon.reflect.visitor.printer.change.MutableTokenWriter(env);
        // wrap that TokenWriter to listen on all incoming events and set wrapped version to DJPP
        setPrinterTokenWriter(createTokenWriterListener(mutableTokenWriter));
    }

    /**
     * wrap a `tokenWriter` by a proxy which intercepts all {@link TokenWriter} writeXxx(String) calls
     * and first calls {@link #onTokenWriterWrite(String, String)} and then calls origin `tokenWriter` method
     *
     * @param tokenWriter
     * 		to be wrapped {@link TokenWriter}
     * @return a wrapped {@link TokenWriter}
     */
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

    /**
     * Is called for each printed token
     *
     * @param tokenWriterMethodName
     * 		the name of {@link TokenWriter} method
     * @param token
     * 		the actual token value. It may be null for some `tokenWriterMethodName`
     * @param comment
     * 		the comment when `tokenWriterMethodName` == `writeComment`
     * @param printAction
     * 		the executor of the action, we are listening for.
     */
    private void onTokenWriterWrite(java.lang.String tokenWriterMethodName, java.lang.String token, spoon.reflect.code.CtComment comment, java.lang.Runnable printAction) {
        spoon.reflect.visitor.printer.change.SourceFragmentContext sfc = sourceFragmentContextStack.peek();
        if (sfc != null) {
            sfc.onTokenWriterToken(tokenWriterMethodName, token, printAction);
            return;
        }
        printAction.run();
    }

    /**
     * Called whenever {@link DefaultJavaPrettyPrinter} scans/prints an element
     */
    @java.lang.Override
    public spoon.reflect.visitor.printer.change.ChangesAwareDefaultJavaPrettyPrinter scan(spoon.reflect.declaration.CtElement element) {
        spoon.reflect.visitor.printer.change.SourceFragmentContext sfc = sourceFragmentContextStack.peek();
        if (sfc != null) {
            spoon.reflect.path.CtRole role = element.getRoleInParent();
            if (role != null) {
                // there is an context in the child element, let it handle scanning
                sfc.onScanElementOnRole(element, role, () -> scanInternal(element));
                return this;
            }
        }
        scanInternal(element);
        return this;
    }

    private void scanInternal(spoon.reflect.declaration.CtElement element) {
        if (mutableTokenWriter.isMuted()) {
            // it is already muted by an parent. Simply scan and ignore all tokens,
            // because the content is not modified and was already copied from source
            sourceFragmentContextStack.push(spoon.reflect.visitor.printer.change.SourceFragmentContextNormal.EMPTY_FRAGMENT_CONTEXT);
            super.scan(element);
            sourceFragmentContextStack.pop();
            return;
        }
        // it is not muted yet, so this element or any sibling is modified
        // detect SourceFragments of element and whether they are modified or not
        spoon.reflect.visitor.printer.change.SourceFragment rootFragmentOfElement = spoon.reflect.visitor.printer.change.ChangesAwareDefaultJavaPrettyPrinter.getSourceFragmentsOfElementUsableForPrintingOfOriginCode(changeCollector, element);
        if (rootFragmentOfElement == null) {
            // we have no origin sources or this element has one source fragment only and it is modified. Use normal printing
            sourceFragmentContextStack.push(spoon.reflect.visitor.printer.change.SourceFragmentContextNormal.EMPTY_FRAGMENT_CONTEXT);
            super.scan(element);
            sourceFragmentContextStack.pop();
            return;
        }
        // the element is modified and it consists of more source fragments, and some of them are not modified
        // so we can copy the origin sources for them
        spoon.reflect.visitor.printer.change.SourceFragmentContextNormal sfx = new spoon.reflect.visitor.printer.change.SourceFragmentContextNormal(mutableTokenWriter, element, rootFragmentOfElement);
        sourceFragmentContextStack.push(sfx);
        super.scan(element);
        sourceFragmentContextStack.pop();
        // at the end we always un-mute the token writer
        mutableTokenWriter.setMuted(false);
    }

    /**
     *
     *
     * @param changeCollector
     * 		holds information about modified elements
     * @param element
     * 		to be printed element
     * @return {@link SourceFragment} or chain of {@link SourceFragment} siblings which wraps all the source parts of the `element`
     * It returns null when there are no sources at all or when all are modified so we cannot use them anyway
     */
    private static spoon.reflect.visitor.printer.change.SourceFragment getSourceFragmentsOfElementUsableForPrintingOfOriginCode(spoon.experimental.modelobs.ChangeCollector changeCollector, spoon.reflect.declaration.CtElement element) {
        // detect source code fragments of this element
        spoon.reflect.visitor.printer.change.SourceFragment rootFragmentOfElement = spoon.reflect.visitor.printer.change.SourcePositionUtils.getSourceFragmentOfElement(element);
        if (rootFragmentOfElement == null) {
            // we have no origin sources for this element
            return null;
        }
        // The origin sources of this element are available
        // check if this element was changed
        java.util.Set<spoon.reflect.path.CtRole> changedRoles = changeCollector.getChanges(element);
        if (changedRoles.isEmpty()) {
            // element was not changed and we know origin sources
            // use origin source instead of printed code
            return rootFragmentOfElement;
        }
        // element is changed. Detect source fragments of this element
        spoon.reflect.visitor.printer.change.SourceFragment childSourceFragmentsOfSameElement = rootFragmentOfElement.getChildFragmentOfSameElement();
        if ((childSourceFragmentsOfSameElement == null) || ((childSourceFragmentsOfSameElement.getNextFragmentOfSameElement()) == null)) {
            // there is only one source fragment and it is modified.
            // So we cannot use origin sources
            return null;
        }
        /* there are more fragments. So may be some of them are not modified and we can use origin source to print them
        e.g. when only type members of class are modified, we can still print the class header from the origin sources
        Mark which fragments contains source code of data from modified roles
         */
        // detect which roles of this element contains a change
        if (spoon.reflect.visitor.printer.change.SourcePositionUtils.markChangedFragments(element, childSourceFragmentsOfSameElement, changedRoles)) {
            return childSourceFragmentsOfSameElement;
        }
        // this kind of changes is not supported for this element yet. We cannot use origin sources :-(
        return null;
    }
}

