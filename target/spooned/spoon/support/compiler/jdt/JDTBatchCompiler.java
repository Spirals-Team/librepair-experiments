/**
 * Copyright (C) 2006-2017 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon.support.compiler.jdt;


/* Overrides the getCompilationUnits() from JDT's class to pass the ones we want.

(we use a fully qualified name in inheritance to make it clear we are extending jdt)
 */
public class JDTBatchCompiler extends org.eclipse.jdt.internal.compiler.batch.Main {
    protected final spoon.support.compiler.jdt.JDTBasedSpoonCompiler jdtCompiler;

    protected org.eclipse.jdt.internal.compiler.batch.CompilationUnit[] compilationUnits;

    public JDTBatchCompiler(spoon.support.compiler.jdt.JDTBasedSpoonCompiler jdtCompiler) {
        // by default we don't want anything from JDT
        // the reports are sent with callbakcs to the reporter
        // for debuggging, you may use System.out/err instead
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
                r.acceptResult(compilationResult);// this is required to complete the compilation and produce the class files

            }
        };
    }

    protected java.util.Set<java.lang.String> filesToBeIgnored = new java.util.HashSet<>();

    public void ignoreFile(java.lang.String filePath) {
        filesToBeIgnored.add(filePath);
    }

    /**
     * Calls JDT to retrieve the list of compilation unit declarations.
     * Depends on the actual implementation of {@link #getCompilationUnits()}
     */
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
            // in no classpath, we should proceed on error,
            // as we will encounter some
            errorHandlingPolicy = new org.eclipse.jdt.internal.compiler.IErrorHandlingPolicy() {
                @java.lang.Override
                public boolean proceedOnErrors() {
                    return true;
                }

                @java.lang.Override
                public boolean stopOnFirstError() {
                    return false;
                }

                // we cannot ignore them, because JDT will continue its process
                // and it led to NPE in several places
                @java.lang.Override
                public boolean ignoreAllErrors() {
                    return false;
                }
            };
        }else {
            // when there is a classpath, we should not have any error
            errorHandlingPolicy = new org.eclipse.jdt.internal.compiler.IErrorHandlingPolicy() {
                @java.lang.Override
                public boolean proceedOnErrors() {
                    return false;
                }

                // we wait for all errors to be gathered before stopping
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
        // they have to be done all at once
        final org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration[] result = treeBuilderCompiler.buildUnits(getCompilationUnits());
        // now adding the doc
        if (jdtCompiler.getEnvironment().isCommentsEnabled()) {
            // compile comments only if they are needed
            for (int i = 0; i < (result.length); i++) {
                org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration unit = result[i];
                org.eclipse.jdt.internal.core.util.CommentRecorderParser parser = new org.eclipse.jdt.internal.core.util.CommentRecorderParser(new org.eclipse.jdt.internal.compiler.problem.ProblemReporter(org.eclipse.jdt.internal.compiler.DefaultErrorHandlingPolicies.proceedWithAllProblems(), compilerOptions, new org.eclipse.jdt.internal.compiler.problem.DefaultProblemFactory(java.util.Locale.getDefault())), false);
                // reuse the source compilation unit
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

