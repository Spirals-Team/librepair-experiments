package spoon.support;


public class JavaOutputProcessor extends spoon.processing.AbstractProcessor<spoon.reflect.declaration.CtNamedElement> implements spoon.processing.FileGenerator<spoon.reflect.declaration.CtNamedElement> {
    spoon.reflect.visitor.PrettyPrinter printer;

    java.util.List<java.io.File> printedFiles = new java.util.ArrayList<>();

    public JavaOutputProcessor(spoon.reflect.visitor.PrettyPrinter printer) {
        this.printer = printer;
    }

    @java.lang.Deprecated
    public JavaOutputProcessor(java.io.File outputDirectory, spoon.reflect.visitor.PrettyPrinter printer) {
        this(printer);
        this.setOutputDirectory(outputDirectory);
    }

    public JavaOutputProcessor() {
    }

    @java.lang.Override
    public spoon.compiler.Environment getEnvironment() {
        return this.getFactory().getEnvironment();
    }

    public spoon.reflect.visitor.PrettyPrinter getPrinter() {
        return printer;
    }

    public java.util.List<java.io.File> getCreatedFiles() {
        return printedFiles;
    }

    public java.io.File getOutputDirectory() {
        return this.getEnvironment().getSourceOutputDirectory();
    }

    @java.lang.Override
    public void init() {
        java.io.File directory = getOutputDirectory();
        if (directory == null) {
            throw new spoon.SpoonException("You should set output directory before printing");
        }
        if (!(directory.exists())) {
            if (!(directory.mkdirs())) {
                throw new spoon.SpoonException("Error creating output directory");
            }
        }
    }

    java.util.Map<java.lang.String, java.util.Map<java.lang.Integer, java.lang.Integer>> lineNumberMappings = new java.util.HashMap<>();

    public void createJavaFile(spoon.reflect.declaration.CtType<?> element) {
        java.nio.file.Path typePath = getElementPath(element);
        getEnvironment().debugMessage(((("printing " + (element.getQualifiedName())) + " to ") + typePath));
        if (!(element.isTopLevel())) {
            throw new java.lang.IllegalArgumentException();
        }
        spoon.reflect.cu.CompilationUnit cu = this.getFactory().CompilationUnit().getOrCreate(element);
        java.util.List<spoon.reflect.declaration.CtType<?>> toBePrinted = new java.util.ArrayList<>();
        toBePrinted.add(element);
        printer.calculate(cu, toBePrinted);
        java.io.PrintStream stream = null;
        try {
            java.io.File file = typePath.toFile();
            file.createNewFile();
            if (!(printedFiles.contains(file))) {
                printedFiles.add(file);
            }
            stream = new java.io.PrintStream(file);
            stream.print(printer.getResult());
            for (spoon.reflect.declaration.CtType<?> t : toBePrinted) {
                lineNumberMappings.put(t.getQualifiedName(), printer.getLineNumberMapping());
            }
            stream.close();
        } catch (java.io.IOException e) {
            spoon.Launcher.LOGGER.error(e.getMessage(), e);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    @java.lang.Override
    public boolean isToBeProcessed(spoon.reflect.declaration.CtNamedElement candidate) {
        return ((candidate instanceof spoon.reflect.declaration.CtType<?>) || (candidate instanceof spoon.reflect.declaration.CtModule)) || ((candidate instanceof spoon.reflect.declaration.CtPackage) && (((candidate.getComments().size()) > 0) || ((candidate.getAnnotations().size()) > 0)));
    }

    public void process(spoon.reflect.declaration.CtNamedElement nameElement) {
        if ((nameElement instanceof spoon.reflect.declaration.CtType) && (((spoon.reflect.declaration.CtType) (nameElement)).isTopLevel())) {
            createJavaFile(((spoon.reflect.declaration.CtType<?>) (nameElement)));
        }else
            if (nameElement instanceof spoon.reflect.declaration.CtPackage) {
                createPackageFile(((spoon.reflect.declaration.CtPackage) (nameElement)));
            }else
                if (nameElement instanceof spoon.reflect.declaration.CtModule) {
                    createModuleFile(((spoon.reflect.declaration.CtModule) (nameElement)));
                }


    }

    private void createPackageFile(spoon.reflect.declaration.CtPackage pack) {
        java.io.File packageAnnot = getElementPath(pack).toFile();
        if (!(printedFiles.contains(packageAnnot))) {
            printedFiles.add(packageAnnot);
        }
        java.io.PrintStream stream = null;
        try {
            stream = new java.io.PrintStream(packageAnnot);
            stream.println(printer.printPackageInfo(pack));
            stream.close();
        } catch (java.io.FileNotFoundException e) {
            spoon.Launcher.LOGGER.error(e.getMessage(), e);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    private void createModuleFile(spoon.reflect.declaration.CtModule module) {
        if (((getEnvironment().getComplianceLevel()) > 8) && (module != (getFactory().getModel().getUnnamedModule()))) {
            java.io.File moduleFile = getElementPath(module).toFile();
            if (!(printedFiles.contains(moduleFile))) {
                printedFiles.add(moduleFile);
            }
            java.io.PrintStream stream = null;
            try {
                stream = new java.io.PrintStream(moduleFile);
                stream.println(printer.printModuleInfo(module));
                stream.close();
            } catch (java.io.FileNotFoundException e) {
                spoon.Launcher.LOGGER.error(e.getMessage(), e);
            } finally {
                if (stream != null) {
                    stream.close();
                }
            }
        }
    }

    private java.nio.file.Path getElementPath(spoon.reflect.declaration.CtModule type) {
        return createFolders(getEnvironment().getOutputDestinationHandler().getOutputPath(type, null, null));
    }

    private java.nio.file.Path getElementPath(spoon.reflect.declaration.CtPackage type) {
        return createFolders(getEnvironment().getOutputDestinationHandler().getOutputPath(type.getDeclaringModule(), type, null));
    }

    private java.nio.file.Path getElementPath(spoon.reflect.declaration.CtType type) {
        return createFolders(getEnvironment().getOutputDestinationHandler().getOutputPath(type.getPackage().getDeclaringModule(), type.getPackage(), type));
    }

    private java.nio.file.Path createFolders(java.nio.file.Path outputPath) {
        if (!(outputPath.getParent().toFile().exists())) {
            if (!(outputPath.getParent().toFile().mkdirs())) {
                throw new java.lang.RuntimeException("Error creating output directory");
            }
        }
        return outputPath;
    }

    @java.lang.Override
    public void setOutputDirectory(java.io.File directory) {
        this.getEnvironment().setSourceOutputDirectory(directory);
    }

    public java.util.Map<java.lang.String, java.util.Map<java.lang.Integer, java.lang.Integer>> getLineNumberMappings() {
        return lineNumberMappings;
    }

    @java.lang.Override
    public spoon.processing.TraversalStrategy getTraversalStrategy() {
        return spoon.processing.TraversalStrategy.PRE_ORDER;
    }
}

