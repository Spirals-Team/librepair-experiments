package spoon.support.compiler.jdt;


public class TreeBuilderRequestor implements org.eclipse.jdt.internal.compiler.ICompilerRequestor {
    private final spoon.support.compiler.jdt.JDTBasedSpoonCompiler jdtCompiler;

    TreeBuilderRequestor(spoon.support.compiler.jdt.JDTBasedSpoonCompiler jdtCompiler) {
        this.jdtCompiler = jdtCompiler;
    }

    public void acceptResult(org.eclipse.jdt.internal.compiler.CompilationResult result) {
        if (result.hasErrors()) {
            for (org.eclipse.jdt.core.compiler.CategorizedProblem problem : result.problems) {
                this.jdtCompiler.reportProblem(problem);
            }
        }
    }
}

