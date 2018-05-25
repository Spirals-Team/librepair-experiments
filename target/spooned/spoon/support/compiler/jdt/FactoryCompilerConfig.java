package spoon.support.compiler.jdt;


public class FactoryCompilerConfig implements spoon.SpoonModelBuilder.InputType {
    public static final spoon.SpoonModelBuilder.InputType INSTANCE = new spoon.support.compiler.jdt.FactoryCompilerConfig();

    protected FactoryCompilerConfig() {
    }

    @java.lang.Override
    public void initializeCompiler(spoon.support.compiler.jdt.JDTBatchCompiler compiler) {
        spoon.support.compiler.jdt.JDTBasedSpoonCompiler jdtCompiler = compiler.getJdtCompiler();
        java.util.List<org.eclipse.jdt.internal.compiler.batch.CompilationUnit> unitList = new java.util.ArrayList<>();
        for (spoon.reflect.declaration.CtType<?> ctType : jdtCompiler.getFactory().Type().getAll()) {
            if (ctType.isTopLevel()) {
                unitList.add(new spoon.support.compiler.jdt.CompilationUnitWrapper(ctType));
            }
        }
        compiler.setCompilationUnits(unitList.toArray(new org.eclipse.jdt.internal.compiler.batch.CompilationUnit[unitList.size()]));
    }
}

