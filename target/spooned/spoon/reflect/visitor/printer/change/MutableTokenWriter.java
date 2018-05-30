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
 * {@link TokenWriter}, which simply delegates
 * all tokens to delegate, until {@link #setMuted(boolean)} is called with true
 * Then all tokens are ignored.
 */
class MutableTokenWriter implements spoon.reflect.visitor.TokenWriter {
    private final spoon.reflect.visitor.TokenWriter delegate;

    private boolean muted = false;

    MutableTokenWriter(spoon.compiler.Environment env) {
        super();
        this.delegate = new spoon.reflect.visitor.DefaultTokenWriter(new spoon.reflect.visitor.printer.change.DirectPrinterHelper(env));
    }

    /**
     *
     *
     * @return true if tokens are ignored. false if they are forwarded to `delegate`
     */
    boolean isMuted() {
        return muted;
    }

    /**
     *
     *
     * @param muted
     * 		true if tokens are ignored. false if they are forwarded to `delegate`
     */
    void setMuted(boolean muted) {
        this.muted = muted;
    }

    @java.lang.Override
    public spoon.reflect.visitor.TokenWriter writeSeparator(java.lang.String token) {
        if (isMuted()) {
            getPrinterHelper().setShouldWriteTabs(false);
            return this;
        }
        delegate.writeSeparator(token);
        return this;
    }

    @java.lang.Override
    public spoon.reflect.visitor.TokenWriter writeOperator(java.lang.String token) {
        if (isMuted()) {
            getPrinterHelper().setShouldWriteTabs(false);
            return this;
        }
        delegate.writeOperator(token);
        return this;
    }

    @java.lang.Override
    public spoon.reflect.visitor.TokenWriter writeLiteral(java.lang.String token) {
        if (isMuted()) {
            getPrinterHelper().setShouldWriteTabs(false);
            return this;
        }
        delegate.writeLiteral(token);
        return this;
    }

    @java.lang.Override
    public spoon.reflect.visitor.TokenWriter writeKeyword(java.lang.String token) {
        if (isMuted()) {
            getPrinterHelper().setShouldWriteTabs(false);
            return this;
        }
        delegate.writeKeyword(token);
        return this;
    }

    @java.lang.Override
    public spoon.reflect.visitor.TokenWriter writeIdentifier(java.lang.String token) {
        if (isMuted()) {
            getPrinterHelper().setShouldWriteTabs(false);
            return this;
        }
        delegate.writeIdentifier(token);
        return this;
    }

    @java.lang.Override
    public spoon.reflect.visitor.TokenWriter writeCodeSnippet(java.lang.String token) {
        if (isMuted()) {
            getPrinterHelper().setShouldWriteTabs(false);
            return this;
        }
        delegate.writeCodeSnippet(token);
        return this;
    }

    @java.lang.Override
    public spoon.reflect.visitor.TokenWriter writeComment(spoon.reflect.code.CtComment comment) {
        if (isMuted()) {
            getPrinterHelper().setShouldWriteTabs(false);
            return this;
        }
        delegate.writeComment(comment);
        return this;
    }

    @java.lang.Override
    public spoon.reflect.visitor.TokenWriter writeln() {
        if (isMuted()) {
            getPrinterHelper().setShouldWriteTabs(true);
            return this;
        }
        delegate.writeln();
        return this;
    }

    @java.lang.Override
    public spoon.reflect.visitor.TokenWriter incTab() {
        if (isMuted()) {
            return this;
        }
        delegate.incTab();
        return this;
    }

    @java.lang.Override
    public spoon.reflect.visitor.TokenWriter decTab() {
        if (isMuted()) {
            return this;
        }
        delegate.decTab();
        return this;
    }

    @java.lang.Override
    public spoon.reflect.visitor.printer.change.DirectPrinterHelper getPrinterHelper() {
        return ((spoon.reflect.visitor.printer.change.DirectPrinterHelper) (delegate.getPrinterHelper()));
    }

    @java.lang.Override
    public void reset() {
        if (isMuted()) {
            return;
        }
        delegate.reset();
    }

    @java.lang.Override
    public spoon.reflect.visitor.TokenWriter writeSpace() {
        if (isMuted()) {
            getPrinterHelper().setShouldWriteTabs(false);
            return this;
        }
        delegate.writeSpace();
        return this;
    }
}

