package spoon.support;


public interface OutputDestinationHandler {
    java.nio.file.Path getOutputPath(spoon.reflect.declaration.CtModule module, spoon.reflect.declaration.CtPackage pack, spoon.reflect.declaration.CtType type);

    java.io.File getDefaultOutputDirectory();
}

