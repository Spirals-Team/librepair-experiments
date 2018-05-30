package spoon.reflect.visitor.printer.change;


class MutableTokenWriter implements spoon.reflect.visitor.TokenWriter {
    private final spoon.reflect.visitor.TokenWriter delegate;

    private boolean muted = false;

    MutableTokenWriter(spoon.compiler.Environment env) {
        super();
        this.delegate = new spoon.reflect.visitor.DefaultTokenWriter(new spoon.reflect.visitor.printer.change.DirectPrinterHelper(env));
    }

    boolean isMuted() {
        return muted;
    }

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

