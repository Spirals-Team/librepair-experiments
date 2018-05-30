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
package spoon.support;


/**
 * A processor that generates compilable Java source files from the meta-model.
 */
public class JavaOutputProcessor extends spoon.processing.AbstractProcessor<spoon.reflect.declaration.CtNamedElement> implements spoon.processing.FileGenerator<spoon.reflect.declaration.CtNamedElement> {
    spoon.reflect.visitor.PrettyPrinter printer;

    java.util.List<java.io.File> printedFiles = new java.util.ArrayList<>();

    /**
     *
     *
     * @param printer
     * 		the PrettyPrinter to use for written the files
     */
    public JavaOutputProcessor(spoon.reflect.visitor.PrettyPrinter printer) {
        this.printer = printer;
    }

    /**
     * Creates a new processor for generating Java source files.
     *
     * @param outputDirectory
     * 		the root output directory
     * @param printer
     * 		the PrettyPrinter to use for written the files
     * @deprecated The outputDirectory should be get from the environment given to the pretty printer
     * (see {@link Environment#setSourceOutputDirectory(File)}. You should use the constructor with only one parameter.
     */
    @java.lang.Deprecated
    public JavaOutputProcessor(java.io.File outputDirectory, spoon.reflect.visitor.PrettyPrinter printer) {
        this(printer);
        this.setOutputDirectory(outputDirectory);
    }

    /**
     * usedful for testing
     */
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
        // Skip loading properties
        // super.init();
        java.io.File directory = getOutputDirectory();
        // Check output directory
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

    /**
     * Creates the Java file associated to the given element. Splits top-level
     * classes in different files (even if they are in the same file in the
     * original sources).
     */
    public void createJavaFile(spoon.reflect.declaration.CtType<?> element) {
        java.nio.file.Path typePath = getElementPath(element);
        getEnvironment().debugMessage(((("printing " + (element.getQualifiedName())) + " to ") + typePath));
        // we only create a file for top-level classes
        if (!(element.isTopLevel())) {
            throw new java.lang.IllegalArgumentException();
        }
        spoon.reflect.cu.CompilationUnit cu = this.getFactory().CompilationUnit().getOrCreate(element);
        java.util.List<spoon.reflect.declaration.CtType<?>> toBePrinted = new java.util.ArrayList<>();
        toBePrinted.add(element);
        printer.calculate(cu, toBePrinted);
        java.io.PrintStream stream = null;
        // print type
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

    /**
     * Creates a source file for each processed top-level type and pretty prints
     * its contents.
     */
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
        // Create package annotation file
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

