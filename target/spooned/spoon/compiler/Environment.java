package spoon.compiler;


public interface Environment {
    int getComplianceLevel();

    void setComplianceLevel(int level);

    void debugMessage(java.lang.String message);

    spoon.processing.FileGenerator<? extends spoon.reflect.declaration.CtElement> getDefaultFileGenerator();

    spoon.processing.ProcessingManager getManager();

    spoon.processing.ProcessorProperties getProcessorProperties(java.lang.String processorName) throws java.lang.Exception;

    void setProcessorProperties(java.lang.String processorName, spoon.processing.ProcessorProperties prop);

    boolean isAutoImports();

    boolean isProcessingStopped();

    void report(spoon.processing.Processor<?> processor, org.apache.log4j.Level level, spoon.reflect.declaration.CtElement element, java.lang.String message);

    void report(spoon.processing.Processor<?> processor, org.apache.log4j.Level level, spoon.reflect.declaration.CtElement element, java.lang.String message, spoon.processing.ProblemFixer<?>... fixes);

    void report(spoon.processing.Processor<?> processor, org.apache.log4j.Level level, java.lang.String message);

    void reportEnd();

    void reportProgressMessage(java.lang.String message);

    void setDefaultFileGenerator(spoon.processing.FileGenerator<? extends spoon.reflect.declaration.CtElement> generator);

    void setManager(spoon.processing.ProcessingManager manager);

    void setProcessingStopped(boolean processingStopped);

    int getTabulationSize();

    void setTabulationSize(int size);

    boolean isUsingTabulations();

    void useTabulations(boolean b);

    void setAutoImports(boolean autoImports);

    int getErrorCount();

    int getWarningCount();

    java.lang.ClassLoader getInputClassLoader();

    void setInputClassLoader(java.lang.ClassLoader classLoader);

    void setPreserveLineNumbers(boolean preserveLineNumbers);

    boolean isPreserveLineNumbers();

    java.lang.String[] getSourceClasspath();

    void setSourceClasspath(java.lang.String[] sourceClasspath);

    void setNoClasspath(boolean option);

    boolean getNoClasspath();

    boolean isCopyResources();

    void setCopyResources(boolean copyResources);

    boolean isCommentsEnabled();

    void setCommentEnabled(boolean commentEnabled);

    org.apache.log4j.Level getLevel();

    void setLevel(java.lang.String level);

    boolean shouldCompile();

    void setShouldCompile(boolean shouldCompile);

    boolean checksAreSkipped();

    @java.lang.Deprecated
    void setSelfChecks(boolean skip);

    void disableConsistencyChecks();

    void setBinaryOutputDirectory(java.lang.String directory);

    java.lang.String getBinaryOutputDirectory();

    void setSourceOutputDirectory(java.io.File directory);

    java.io.File getSourceOutputDirectory();

    void setOutputDestinationHandler(spoon.support.OutputDestinationHandler outputDestinationHandler);

    spoon.support.OutputDestinationHandler getOutputDestinationHandler();

    spoon.experimental.modelobs.FineModelChangeListener getModelChangeListener();

    void setModelChangeListener(spoon.experimental.modelobs.FineModelChangeListener modelChangeListener);

    java.nio.charset.Charset getEncoding();

    void setEncoding(java.nio.charset.Charset encoding);

    void setOutputType(spoon.OutputType outputType);

    spoon.OutputType getOutputType();
}

