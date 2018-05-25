package spoon.support.compiler.jdt;


public class JDTBatchCompiler extends org.eclipse.jdt.internal.compiler.batch.Main {
    protected final spoon.support.compiler.jdt.JDTBasedSpoonCompiler jdtCompiler;

    protected org.eclipse.jdt.internal.compiler.batch.CompilationUnit[] compilationUnits;

    public JDTBatchCompiler(spoon.support.compiler.jdt.JDTBasedSpoonCompiler jdtCompiler) {
        this(jdtCompiler, new org.apache.commons.io.output.NullOutputStream(), new org.apache.commons.io.output.NullOutputStream());
    }

    JDTBatchCompiler(spoon.support.compiler.jdt.JDTBasedSpoonCompiler jdtCompiler, java.io.OutputStream outWriter, java.io.OutputStream errWriter) {
        super(new java.io.PrintWriter(outWriter), new java.io.PrintWriter(errWriter), false, null, null);
        this.jdtCompiler = jdtCompiler;
        if (jdtCompiler != null) {
            this.jdtCompiler.probs.clear();
        }
    }

    @java.lang.Override
    public org.eclipse.jdt.internal.compiler.batch.CompilationUnit[] getCompilationUnits() {
        return compilationUnits;
    }

    public void setCompilationUnits(org.eclipse.jdt.internal.compiler.batch.CompilationUnit[] compilationUnits) {
        this.compilationUnits = compilationUnits;
    }

    @java.lang.Override
    public org.eclipse.jdt.internal.compiler.ICompilerRequestor getBatchRequestor() {
        final org.eclipse.jdt.internal.compiler.ICompilerRequestor r = super.getBatchRequestor();
        return new org.eclipse.jdt.internal.compiler.ICompilerRequestor() {
            public void acceptResult(org.eclipse.jdt.internal.compiler.CompilationResult compilationResult) {
                if (compilationResult.hasErrors()) {
                    for (org.eclipse.jdt.core.compiler.CategorizedProblem problem : compilationResult.problems) {
                        if ((spoon.support.compiler.jdt.JDTBatchCompiler.this.jdtCompiler) != null) {
                            spoon.support.compiler.jdt.JDTBatchCompiler.this.jdtCompiler.reportProblem(problem);
                        }else {
                            throw new spoon.SpoonException(problem.toString());
                        }
                    }
                }
                r.acceptResult(compilationResult);
            }
        };
    }

    protected java.util.Set<java.lang.String> filesToBeIgnored = new java.util.HashSet<>();

    public void ignoreFile(java.lang.String filePath) {
        filesToBeIgnored.add(filePath);
    }

    public org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration[] getUnits() {
        startTime = java.lang.System.currentTimeMillis();
        org.eclipse.jdt.internal.compiler.env.INameEnvironment environment = this.jdtCompiler.environment;
        if (environment == null) {
            environment = getLibraryAccess();
        }
        org.eclipse.jdt.internal.compiler.impl.CompilerOptions compilerOptions = new org.eclipse.jdt.internal.compiler.impl.CompilerOptions(this.options);
        compilerOptions.parseLiteralExpressionsAsConstants = false;
        org.eclipse.jdt.internal.compiler.IErrorHandlingPolicy errorHandlingPolicy;
        if (jdtCompiler.getEnvironment().getNoClasspath()) {
            errorHandlingPolicy = new org.eclipse.jdt.internal.compiler.IErrorHandlingPolicy() {
                @java.lang.Override
                public boolean proceedOnErrors() {
                    return true;
                }

                @java.lang.Override
                public boolean stopOnFirstError() {
                    return false;
                }

                @java.lang.Override
                public boolean ignoreAllErrors() {
                    return false;
                }
            };
        }else {
            errorHandlingPolicy = new org.eclipse.jdt.internal.compiler.IErrorHandlingPolicy() {
                @java.lang.Override
                public boolean proceedOnErrors() {
                    return false;
                }

                @java.lang.Override
                public boolean stopOnFirstError() {
                    return false;
                }

                @java.lang.Override
                public boolean ignoreAllErrors() {
                    return false;
                }
            };
        }
        spoon.support.compiler.jdt.TreeBuilderCompiler treeBuilderCompiler = new spoon.support.compiler.jdt.TreeBuilderCompiler(environment, errorHandlingPolicy, compilerOptions, this.jdtCompiler.requestor, getProblemFactory(), this.out, null);
        if (jdtCompiler.getEnvironment().getNoClasspath()) {
            treeBuilderCompiler.lookupEnvironment.mayTolerateMissingType = true;
        }
        final org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration[] result = treeBuilderCompiler.buildUnits(getCompilationUnits());
        if (jdtCompiler.getEnvironment().isCommentsEnabled()) {
            for (int i = 0; i < (result.length); i++) {
                org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration unit = result[i];
                org.eclipse.jdt.internal.core.util.CommentRecorderParser parser = new org.eclipse.jdt.internal.core.util.CommentRecorderParser(new org.eclipse.jdt.internal.compiler.problem.ProblemReporter(org.eclipse.jdt.internal.compiler.DefaultErrorHandlingPolicies.proceedWithAllProblems(), compilerOptions, new org.eclipse.jdt.internal.compiler.problem.DefaultProblemFactory(java.util.Locale.getDefault())), false);
                org.eclipse.jdt.internal.compiler.env.ICompilationUnit sourceUnit = unit.compilationResult.compilationUnit;
                final org.eclipse.jdt.internal.compiler.CompilationResult compilationResult = new org.eclipse.jdt.internal.compiler.CompilationResult(sourceUnit, 0, 0, compilerOptions.maxProblemsPerUnit);
                org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration tmpDeclForComment = parser.dietParse(sourceUnit, compilationResult);
                unit.comments = tmpDeclForComment.comments;
            }
        }
        return result;
    }

    public spoon.support.compiler.jdt.JDTBasedSpoonCompiler getJdtCompiler() {
        return jdtCompiler;
    }
}

