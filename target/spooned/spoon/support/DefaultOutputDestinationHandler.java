package spoon.support;


public class DefaultOutputDestinationHandler implements spoon.support.OutputDestinationHandler {
    private java.io.File defaultOutputDirectory;

    private spoon.compiler.Environment environment;

    public DefaultOutputDestinationHandler(java.io.File defaultOutputDirectory, spoon.compiler.Environment environment) {
        this.defaultOutputDirectory = defaultOutputDirectory;
        this.environment = environment;
    }

    @java.lang.Override
    public java.nio.file.Path getOutputPath(spoon.reflect.declaration.CtModule module, spoon.reflect.declaration.CtPackage pack, spoon.reflect.declaration.CtType type) {
        java.nio.file.Path directory = getDirectoryPath(module, pack, type);
        java.nio.file.Path moduleDir = getModulePath(module);
        java.nio.file.Path packagePath = getPackagePath(pack);
        java.lang.String fileName = getFileName(pack, type);
        return java.nio.file.Paths.get(directory.toString(), moduleDir.toString(), packagePath.toString(), fileName);
    }

    protected java.lang.String getFileName(spoon.reflect.declaration.CtPackage pack, spoon.reflect.declaration.CtType type) {
        java.lang.String fileName;
        if (type != null) {
            fileName = (type.getSimpleName()) + (spoon.reflect.visitor.DefaultJavaPrettyPrinter.JAVA_FILE_EXTENSION);
        }else
            if (pack != null) {
                fileName = spoon.reflect.visitor.DefaultJavaPrettyPrinter.JAVA_PACKAGE_DECLARATION;
            }else {
                fileName = spoon.reflect.visitor.DefaultJavaPrettyPrinter.JAVA_MODULE_DECLARATION;
            }

        return fileName;
    }

    protected java.nio.file.Path getPackagePath(spoon.reflect.declaration.CtPackage pack) {
        java.nio.file.Path packagePath = java.nio.file.Paths.get(".");
        if ((pack != null) && (!(pack.isUnnamedPackage()))) {
            packagePath = java.nio.file.Paths.get(pack.getQualifiedName().replace('.', java.io.File.separatorChar));
        }
        return packagePath;
    }

    protected java.nio.file.Path getModulePath(spoon.reflect.declaration.CtModule module) {
        java.nio.file.Path moduleDir = java.nio.file.Paths.get(".");
        if (((module != null) && (!(module.isUnnamedModule()))) && ((environment.getComplianceLevel()) > 8)) {
            moduleDir = java.nio.file.Paths.get(module.getSimpleName());
        }
        return moduleDir;
    }

    protected java.nio.file.Path getDirectoryPath(spoon.reflect.declaration.CtModule module, spoon.reflect.declaration.CtPackage pack, spoon.reflect.declaration.CtType type) {
        return java.nio.file.Paths.get(getDefaultOutputDirectory().getAbsolutePath());
    }

    @java.lang.Override
    public java.io.File getDefaultOutputDirectory() {
        return defaultOutputDirectory;
    }

    public spoon.compiler.Environment getEnvironment() {
        return environment;
    }
}

