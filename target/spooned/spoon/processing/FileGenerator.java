package spoon.processing;


public interface FileGenerator<T extends spoon.reflect.declaration.CtElement> extends spoon.processing.Processor<T> {
    @java.lang.Deprecated
    void setOutputDirectory(java.io.File directory);

    java.io.File getOutputDirectory();

    java.util.List<java.io.File> getCreatedFiles();
}

