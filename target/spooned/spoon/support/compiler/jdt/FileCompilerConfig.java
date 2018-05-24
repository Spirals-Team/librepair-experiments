package spoon.support.compiler.jdt;


public class FileCompilerConfig implements spoon.SpoonModelBuilder.InputType {
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

