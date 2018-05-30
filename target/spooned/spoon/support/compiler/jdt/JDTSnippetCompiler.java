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


public class JDTSnippetCompiler extends spoon.support.compiler.jdt.JDTBasedSpoonCompiler {
    private final java.util.concurrent.atomic.AtomicLong snippetNumber = new java.util.concurrent.atomic.AtomicLong(0);

    public static final java.lang.String SNIPPET_FILENAME_PREFIX = (spoon.support.compiler.jdt.JDTSnippetCompiler.class.getName()) + "_spoonSnippet_";

    private spoon.reflect.cu.CompilationUnit snippetCompilationUnit;

    public JDTSnippetCompiler(spoon.reflect.factory.Factory factory, java.lang.String contents) {
        super(factory);
        // give the Virtual file the unique name so JDTCommentBuilder.spoonUnit can be correctly initialized
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
            // remove snippet compilation unit from the cache (to clear memory) and remember it so client can use it
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

    /**
     *
     *
     * @return CompilationUnit which was produced by compiling of this snippet
     */
    public spoon.reflect.cu.CompilationUnit getSnippetCompilationUnit() {
        return snippetCompilationUnit;
    }
}

