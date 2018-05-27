package spoon;


public interface SpoonAPI {
    void run(java.lang.String[] args);

    void addInputResource(java.lang.String file);

    void setSourceOutputDirectory(java.lang.String path);

    void setSourceOutputDirectory(java.io.File outputDirectory);

    void setOutputFilter(spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtType<?>> typeFilter);

    void setOutputFilter(java.lang.String... qualifedNames);

    void setBinaryOutputDirectory(java.lang.String path);

    void setBinaryOutputDirectory(java.io.File outputDirectory);

    void addProcessor(java.lang.String name);

    <T extends spoon.reflect.declaration.CtElement> void addProcessor(spoon.processing.Processor<T> processor);

    spoon.reflect.CtModel buildModel();

    void process();

    void prettyprint();

    void run();

    spoon.reflect.factory.Factory getFactory();

    spoon.compiler.Environment getEnvironment();

    spoon.reflect.factory.Factory createFactory();

    spoon.compiler.Environment createEnvironment();

    spoon.SpoonModelBuilder createCompiler();

    spoon.reflect.CtModel getModel();
}

