package spoon.support.compiler.jdt;


public class JDTBasedSpoonCompilerTest {
    @org.junit.Test
    public void testOrderCompilationUnits() {
        final spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource("./src/main/java");
        spoon.support.compiler.jdt.JDTBasedSpoonCompiler spoonCompiler = ((spoon.support.compiler.jdt.JDTBasedSpoonCompiler) (launcher.getModelBuilder()));
        org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration[] compilationUnitDeclarations = spoonCompiler.buildUnits(null, spoonCompiler.sources, spoonCompiler.getSourceClasspath(), "");
        java.util.List<org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration> compilationUnitDeclarations1 = spoonCompiler.sortCompilationUnits(compilationUnitDeclarations);
        if (((java.lang.System.getenv("SPOON_SEED_CU_COMPARATOR")) == null) || (java.lang.System.getenv("SPOON_SEED_CU_COMPARATOR").equals("0"))) {
            for (int i = 1; i < (compilationUnitDeclarations1.size()); i++) {
                org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration cu0 = compilationUnitDeclarations1.get((i - 1));
                org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration cu1 = compilationUnitDeclarations1.get(i);
                java.lang.String filenameCu0 = new java.lang.String(cu0.getFileName());
                java.lang.String filenameCu1 = new java.lang.String(cu1.getFileName());
                org.junit.Assert.assertTrue(((("There is a sort error: " + filenameCu0) + " should be before ") + filenameCu1), ((filenameCu0.compareTo(filenameCu1)) < 0));
            }
        }
    }
}

