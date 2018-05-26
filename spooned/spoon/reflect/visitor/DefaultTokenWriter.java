package spoon.reflect.visitor;


public class DefaultTokenWriter implements spoon.reflect.visitor.TokenWriter {
    private final spoon.reflect.visitor.PrinterHelper printerHelper;

    public DefaultTokenWriter(spoon.reflect.visitor.PrinterHelper printerHelper) {
        this.printerHelper = printerHelper;
    }

    @java.lang.Override
    public spoon.reflect.visitor.DefaultTokenWriter writeOperator(java.lang.String token) {
        printerHelper.write(token);
        return this;
    }

    @java.lang.Override
    public spoon.reflect.visitor.DefaultTokenWriter writeSeparator(java.lang.String token) {
        printerHelper.write(token);
        return this;
    }

    @java.lang.Override
    public spoon.reflect.visitor.DefaultTokenWriter writeLiteral(java.lang.String token) {
        printerHelper.write(token);
        return this;
    }

    @java.lang.Override
    public spoon.reflect.visitor.DefaultTokenWriter writeKeyword(java.lang.String token) {
        printerHelper.write(token);
        return this;
    }

    @java.lang.Override
    public spoon.reflect.visitor.DefaultTokenWriter writeIdentifier(java.lang.String token) {
        printerHelper.write(token);
        return this;
    }

    @java.lang.Override
    public spoon.reflect.visitor.DefaultTokenWriter writeCodeSnippet(java.lang.String token) {
        printerHelper.write(token);
        return this;
    }

    @java.lang.Override
    public spoon.reflect.visitor.DefaultTokenWriter writeComment(spoon.reflect.code.CtComment comment) {
        spoon.reflect.visitor.CommentHelper.printComment(printerHelper, comment);
        return this;
    }

    @java.lang.Override
    public spoon.reflect.visitor.DefaultTokenWriter writeln() {
        printerHelper.writeln();
        return this;
    }

    @java.lang.Override
    public spoon.reflect.visitor.DefaultTokenWriter incTab() {
        printerHelper.incTab();
        return this;
    }

    @java.lang.Override
    public spoon.reflect.visitor.DefaultTokenWriter decTab() {
        printerHelper.decTab();
        return this;
    }

    @java.lang.Override
    public void reset() {
        printerHelper.reset();
    }

    @java.lang.Override
    public spoon.reflect.visitor.TokenWriter writeSpace() {
        printerHelper.writeSpace();
        return this;
    }

    @java.lang.Override
    public spoon.reflect.visitor.PrinterHelper getPrinterHelper() {
        return printerHelper;
    }
}

