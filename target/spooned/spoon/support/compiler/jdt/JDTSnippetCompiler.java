package spoon.support.compiler.jdt;


public class JDTSnippetCompiler extends spoon.support.compiler.jdt.JDTBasedSpoonCompiler {
    private final java.util.concurrent.atomic.AtomicLong snippetNumber = new java.util.concurrent.atomic.AtomicLong(0);

    public static final java.lang.String SNIPPET_FILENAME_PREFIX = (spoon.support.compiler.jdt.JDTSnippetCompiler.class.getName()) + "_spoonSnippet_";

    private spoon.reflect.cu.CompilationUnit snippetCompilationUnit;

    public JDTSnippetCompiler(spoon.reflect.factory.Factory factory, java.lang.String contents) {
        super(factory);
        addInputSource(new spoon.support.compiler.VirtualFile(contents, ((spoon.support.compiler.jdt.JDTSnippetCompiler.SNIPPET_FILENAME_PREFIX) + (snippetNumber.incrementAndGet()))));
    }

    @java.lang.Override
    public boolean build() {
        return build(null);
    }

    @java.lang.Override
    public boolean build(spoon.compiler.builder.JDTBuilder builder) {
        if ((factory) == null) {
            throw new spoon.SpoonException("Factory not initialized");
        }
        boolean srcSuccess;
        java.util.List<spoon.compiler.SpoonFile> allFiles = sources.getAllJavaFiles();
        factory.getEnvironment().debugMessage(("compiling sources: " + allFiles));
        long t = java.lang.System.currentTimeMillis();
        javaCompliance = factory.getEnvironment().getComplianceLevel();
        try {
            srcSuccess = buildSources(builder);
        } finally {
            for (spoon.compiler.SpoonFile spoonFile : allFiles) {
                if (spoonFile.getName().startsWith(spoon.support.compiler.jdt.JDTSnippetCompiler.SNIPPET_FILENAME_PREFIX)) {
                    snippetCompilationUnit = factory.CompilationUnit().removeFromCache(spoonFile.getName());
                }
            }
        }
        reportProblems(factory.getEnvironment());
        factory.getEnvironment().debugMessage((("compiled in " + ((java.lang.System.currentTimeMillis()) - t)) + " ms"));
        return srcSuccess;
    }

    @java.lang.Override
    protected boolean buildSources(spoon.compiler.builder.JDTBuilder jdtBuilder) {
        return buildUnitsAndModel(jdtBuilder, sources, getSourceClasspath(), "snippet ");
    }

    @java.lang.Override
    protected void report(spoon.compiler.Environment environment, org.eclipse.jdt.core.compiler.CategorizedProblem problem) {
        if (problem.isError()) {
            throw new spoon.support.compiler.SnippetCompilationError((((problem.getMessage()) + "at line ") + (problem.getSourceLineNumber())));
        }
    }

    public spoon.reflect.cu.CompilationUnit getSnippetCompilationUnit() {
        return snippetCompilationUnit;
    }
}

