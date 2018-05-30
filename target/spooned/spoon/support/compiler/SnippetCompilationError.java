package spoon.support.compiler;


public class SnippetCompilationError extends spoon.SpoonException {
    private static final long serialVersionUID = 7805276558728052328L;

    public java.util.List<java.lang.String> problems;

    public SnippetCompilationError(java.util.List<java.lang.String> problems) {
        super();
        this.problems = problems;
    }

    public SnippetCompilationError(java.lang.String string) {
        super(string);
        this.problems = new java.util.ArrayList<>();
        this.problems.add(string);
    }
}

