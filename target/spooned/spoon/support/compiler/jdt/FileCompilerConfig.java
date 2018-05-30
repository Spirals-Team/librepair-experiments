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


public class FileCompilerConfig implements spoon.SpoonModelBuilder.InputType {
    /**
     * Default implementation of which initializes {@link JDTBatchCompiler} by all sources and templates registered in {@link SpoonModelBuilder}
     */
    public static final spoon.SpoonModelBuilder.InputType INSTANCE = new spoon.support.compiler.jdt.FileCompilerConfig(((java.util.List<spoon.compiler.SpoonFile>) (null))) {
        @java.lang.Override
        public java.util.List<spoon.compiler.SpoonFile> getFiles(spoon.support.compiler.jdt.JDTBatchCompiler compiler) {
            spoon.support.compiler.jdt.JDTBasedSpoonCompiler jdtCompiler = compiler.getJdtCompiler();
            java.util.List<spoon.compiler.SpoonFile> files = new java.util.ArrayList<>();
            files.addAll(jdtCompiler.sources.getAllJavaFiles());
            files.addAll(jdtCompiler.templates.getAllJavaFiles());
            return files;
        }
    };

    private final java.util.List<spoon.compiler.SpoonFile> files;

    public FileCompilerConfig(java.util.List<spoon.compiler.SpoonFile> files) {
        this.files = files;
    }

    @java.lang.Override
    public void initializeCompiler(spoon.support.compiler.jdt.JDTBatchCompiler compiler) {
        spoon.support.compiler.jdt.JDTBasedSpoonCompiler jdtCompiler = compiler.getJdtCompiler();
        java.util.List<org.eclipse.jdt.internal.compiler.batch.CompilationUnit> cuList = new java.util.ArrayList<>();
        java.io.InputStream inputStream = null;
        try {
            for (spoon.compiler.SpoonFile f : getFiles(compiler)) {
                if (compiler.filesToBeIgnored.contains(f.getPath())) {
                    continue;
                }
                java.lang.String fName = (f.isActualFile()) ? f.getPath() : f.getName();
                inputStream = f.getContent();
                char[] content = org.apache.commons.io.IOUtils.toCharArray(inputStream, jdtCompiler.getEnvironment().getEncoding());
                cuList.add(new org.eclipse.jdt.internal.compiler.batch.CompilationUnit(content, fName, null));
                org.apache.commons.io.IOUtils.closeQuietly(inputStream);
            }
        } catch (java.lang.Exception e) {
            org.apache.commons.io.IOUtils.closeQuietly(inputStream);
            throw new spoon.SpoonException(e);
        }
        compiler.setCompilationUnits(cuList.toArray(new org.eclipse.jdt.internal.compiler.batch.CompilationUnit[0]));
    }

    protected java.util.List<spoon.compiler.SpoonFile> getFiles(spoon.support.compiler.jdt.JDTBatchCompiler compiler) {
        return files;
    }
}

