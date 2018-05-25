package spoon;


public interface SpoonModelBuilder {
    boolean build();

    boolean build(spoon.compiler.builder.JDTBuilder builder);

    interface InputType {
        spoon.SpoonModelBuilder.InputType FILES = spoon.support.compiler.jdt.FileCompilerConfig.INSTANCE;

        spoon.SpoonModelBuilder.InputType CTTYPES = spoon.support.compiler.jdt.FactoryCompilerConfig.INSTANCE;

        void initializeCompiler(spoon.support.compiler.jdt.JDTBatchCompiler compiler);
    }

    boolean compile(spoon.SpoonModelBuilder.InputType... types);

    void instantiateAndProcess(java.util.List<java.lang.String> processors);

    void process(java.util.Collection<spoon.processing.Processor<? extends spoon.reflect.declaration.CtElement>> processors);

    void generateProcessedSourceFiles(spoon.OutputType outputType);

    void generateProcessedSourceFiles(spoon.OutputType outputType, spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtType<?>> typeFilter);

    void addInputSource(java.io.File source);

    void addInputSource(spoon.compiler.SpoonResource source);

    void addInputSources(java.util.List<spoon.compiler.SpoonResource> resources);

    java.util.Set<java.io.File> getInputSources();

    void addTemplateSource(java.io.File source);

    void addTemplateSource(spoon.compiler.SpoonResource source);

    void addTemplateSources(java.util.List<spoon.compiler.SpoonResource> resources);

    java.util.Set<java.io.File> getTemplateSources();

    @java.lang.Deprecated
    void setSourceOutputDirectory(java.io.File outputDirectory);

    java.io.File getSourceOutputDirectory();

    void setBinaryOutputDirectory(java.io.File binaryOutputDirectory);

    java.io.File getBinaryOutputDirectory();

    java.lang.String[] getSourceClasspath();

    void setSourceClasspath(java.lang.String... classpath);

    java.lang.String[] getTemplateClasspath();

    void setTemplateClasspath(java.lang.String... classpath);

    spoon.reflect.factory.Factory getFactory();

    void addCompilationUnitFilter(spoon.support.compiler.jdt.CompilationUnitFilter filter);

    void removeCompilationUnitFilter(spoon.support.compiler.jdt.CompilationUnitFilter filter);

    java.util.List<spoon.support.compiler.jdt.CompilationUnitFilter> getCompilationUnitFilter();
}

