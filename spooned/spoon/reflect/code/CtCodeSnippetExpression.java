package spoon.reflect.code;


public interface CtCodeSnippetExpression<T> extends spoon.reflect.code.CtExpression<T> , spoon.reflect.declaration.CtCodeSnippet {
    <E extends spoon.reflect.code.CtExpression<T>> E compile() throws spoon.support.compiler.SnippetCompilationError;

    @java.lang.Override
    spoon.reflect.code.CtCodeSnippetExpression<T> clone();
}

