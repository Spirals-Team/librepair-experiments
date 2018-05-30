package spoon.reflect.visitor;


public interface TokenWriter {
    spoon.reflect.visitor.TokenWriter writeSeparator(java.lang.String token);

    spoon.reflect.visitor.TokenWriter writeOperator(java.lang.String token);

    spoon.reflect.visitor.TokenWriter writeLiteral(java.lang.String token);

    spoon.reflect.visitor.TokenWriter writeKeyword(java.lang.String token);

    spoon.reflect.visitor.TokenWriter writeIdentifier(java.lang.String token);

    spoon.reflect.visitor.TokenWriter writeCodeSnippet(java.lang.String token);

    spoon.reflect.visitor.TokenWriter writeComment(spoon.reflect.code.CtComment comment);

    spoon.reflect.visitor.TokenWriter writeln();

    spoon.reflect.visitor.TokenWriter incTab();

    spoon.reflect.visitor.TokenWriter decTab();

    spoon.reflect.visitor.PrinterHelper getPrinterHelper();

    void reset();

    spoon.reflect.visitor.TokenWriter writeSpace();
}

