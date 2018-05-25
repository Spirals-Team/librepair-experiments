package spoon.reflect.code;


public interface CtCodeSnippetStatement extends spoon.reflect.code.CtStatement , spoon.reflect.declaration.CtCodeSnippet {
    <S extends spoon.reflect.code.CtStatement> S compile() throws spoon.support.compiler.SnippetCompilationError;

    @java.lang.Override
    spoon.reflect.code.CtCodeSnippetStatement clone();
}

