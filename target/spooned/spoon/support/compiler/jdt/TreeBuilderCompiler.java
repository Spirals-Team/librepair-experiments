package spoon.support.compiler.jdt;


class TreeBuilderCompiler extends org.eclipse.jdt.internal.compiler.Compiler {
    TreeBuilderCompiler(org.eclipse.jdt.internal.compiler.env.INameEnvironment environment, org.eclipse.jdt.internal.compiler.IErrorHandlingPolicy policy, org.eclipse.jdt.internal.compiler.impl.CompilerOptions options, org.eclipse.jdt.internal.compiler.ICompilerRequestor requestor, org.eclipse.jdt.internal.compiler.IProblemFactory problemFactory, java.io.PrintWriter out, org.eclipse.jdt.core.compiler.CompilationProgress progress) {
        super(environment, policy, options, requestor, problemFactory, out, progress);
    }

    protected org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration[] buildUnits(org.eclipse.jdt.internal.compiler.batch.CompilationUnit[] sourceUnits) {
        org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration unit = null;
        int i = 0;
        beginToCompile(sourceUnits);
        for (; i < (this.totalUnits); i++) {
            unit = unitsToProcess[i];
            this.parser.getMethodBodies(unit);
            if ((unit.scope) != null) {
                unit.scope.faultInTypes();
            }
            if ((unit.scope) != null) {
                unit.scope.verifyMethods(lookupEnvironment.methodVerifier());
            }
            unit.resolve();
            unit.analyseCode();
            unit.ignoreFurtherInvestigation = false;
            requestor.acceptResult(unit.compilationResult);
        }
        java.util.ArrayList<org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration> unitsToReturn = new java.util.ArrayList<org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration>();
        for (org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration cud : this.unitsToProcess) {
            if (cud != null) {
                unitsToReturn.add(cud);
            }
        }
        return unitsToReturn.toArray(new org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration[unitsToReturn.size()]);
    }
}

