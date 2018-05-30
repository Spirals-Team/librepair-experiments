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
package spoon;


/**
 * Responsible for building a spoon model from Java source code.
 *
 * <p>
 * The Spoon model (see {@link Factory} is built from input sources given as
 * files. Use {@link #build()} to create the Spoon model.
 * Once the model is built and stored in the factory, it
 * can be processed by using a {@link #instantiateAndProcess(List)}.
 * </p>
 *
 * <p>
 * Create an instance of the default implementation of the Spoon compiler by
 * using {@link spoon.Launcher#createCompiler()}. For example:
 * </p>
 */
public interface SpoonModelBuilder {
    /**
     * Builds the program's model with this compiler's factory and stores the
     * result into this factory. Note that this method should only be used once
     * on a given factory.
     *
     * @return true if the Java was successfully compiled with the core Java
     * compiler, false if some errors were encountered while compiling
     * @throws spoon.SpoonException
     * 		when a building problem occurs
     * @see #getSourceClasspath()
     * @see #getTemplateClasspath()
     */
    boolean build();

    /**
     * Builds the program's model with this compiler's factory and stores the
     * result into this factory. Note that this method should only be used once
     * on a given factory.
     *
     * @param builder
     * 		Parameters given at JDT compiler.
     * @return true if the Java was successfully compiled with the core Java
     * compiler, false if some errors were encountered while compiling
     * @throws spoon.SpoonException
     * 		when a building problem occurs
     * @see #getSourceClasspath()
     * @see #getTemplateClasspath()
     */
    boolean build(spoon.compiler.builder.JDTBuilder builder);

    /**
     * The types of compilable elements
     * FILES - compiles the java files from the file system, which were registered by {@link #addInputSource(File)} and {@link #addTemplateSource(File)}
     * CTTYPES - compiles virtual java files, which are dynamically generated from the all top level classes of the CtModel by {@link spoon.reflect.visitor.DefaultJavaPrettyPrinter}
     */
    interface InputType {
        spoon.SpoonModelBuilder.InputType FILES = spoon.support.compiler.jdt.FileCompilerConfig.INSTANCE;

        spoon.SpoonModelBuilder.InputType CTTYPES = spoon.support.compiler.jdt.FactoryCompilerConfig.INSTANCE;

        /**
         * responsible for setting the parameters of JDTBatchCompiler, must call setCompilationUnits()
         */
        void initializeCompiler(spoon.support.compiler.jdt.JDTBatchCompiler compiler);
    }

    /**
     * Generates the bytecode associated to the classes stored in this
     * compiler's factory. The bytecode is generated in the directory given by
     * {@link #getBinaryOutputDirectory()}.
     *
     * The array of types must be of size 0 or 1. If it's empty,
     * the types of the factory are compiled.
     * If it's InputType.FILES, the files given as input are compiled.
     *
     * Note that the varargs ... enables this version to be backward compatible for callers.
     *
     * @see #getSourceClasspath()
     */
    boolean compile(spoon.SpoonModelBuilder.InputType... types);

    /**
     * Takes a list of fully qualified name processors and instantiates them to process
     * the Java model.
     */
    void instantiateAndProcess(java.util.List<java.lang.String> processors);

    /**
     * Processes the Java model with the given processors.
     */
    void process(java.util.Collection<spoon.processing.Processor<? extends spoon.reflect.declaration.CtElement>> processors);

    /**
     * Generates the source code associated to the classes stored in this
     * compiler's factory. The source code is generated in the directory given
     * by {@link #getSourceOutputDirectory()}.
     *
     * @param outputType
     * 		the output method
     */
    void generateProcessedSourceFiles(spoon.OutputType outputType);

    /**
     * Generates the source code associated to the classes stored in this
     * compiler's factory. The source code is generated in the directory given
     * by {@link #getSourceOutputDirectory()}.
     *
     * @param outputType
     * 		the output method
     * @param typeFilter
     * 		Filter on CtType to know which type Spoon must print.
     */
    void generateProcessedSourceFiles(spoon.OutputType outputType, spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtType<?>> typeFilter);

    /**
     * Adds a file/directory to be built. By default, the files could be Java
     * source files or Jar files. Directories are processed recursively.
     *
     * @param source
     * 		file or directory to add
     */
    void addInputSource(java.io.File source);

    /**
     * Adds a file/directory (as a {@link SpoonResource}) to be built. By default, the
     * files could be Java source files or Jar files. Directories are processed
     * recursively.
     *
     * @param source
     * 		file or directory to add
     */
    void addInputSource(spoon.compiler.SpoonResource source);

    /**
     * Adds a list of files/directories (as a {@link SpoonResource} to be built.
     * By default, the files could be Java source files of Java files. Directories
     * are processed recursively.
     *
     * @param resources
     * 		files or directories to add.
     */
    void addInputSources(java.util.List<spoon.compiler.SpoonResource> resources);

    /**
     * Gets all the files/directories given as input sources to this builder
     * (see {@link #addInputSource(File)}).
     */
    java.util.Set<java.io.File> getInputSources();

    /**
     * Adds a file/directory to be used to build templates. By default, the
     * files should be Java source files or Jar files containing the sources.
     * Directories are processed recursively. Templates are set apart from the
     * program to be processed for logical reasons. However, if a template was
     * needed to be processed, it could be added as an input source.
     *
     * @param source
     * 		file or directory to add
     */
    void addTemplateSource(java.io.File source);

    /**
     * Adds a file/directory (as a {@link SpoonResource}) to be used to build templates. By
     * default, the files should be Java source files or Jar files containing
     * the sources. Directories are processed recursively. Templates are set
     * apart from the program to be processed for logical reasons. However, if a
     * template was needed to be processed, it could be added as an input
     * source.
     *
     * @param source
     * 		file or directory to add
     */
    void addTemplateSource(spoon.compiler.SpoonResource source);

    /**
     * Adds a list of files/directories (as a CtResource) to be used to build templates. By
     * default, the files should be Java source files or Jar files containing
     * the sources. Directories are processed recursively. Templates are set
     * apart from the program to be processed for logical reasons. However, if a
     * template was needed to be processed, it could be added as an input
     * source.
     *
     * @param resources
     * 		files or directories to add.
     */
    void addTemplateSources(java.util.List<spoon.compiler.SpoonResource> resources);

    /**
     * Gets all the files/directories given as template sources to this builder
     * (see {@link #addTemplateSource(File)}).
     */
    java.util.Set<java.io.File> getTemplateSources();

    /**
     * Sets the output directory for source generated.
     *
     * @param outputDirectory
     * 		{@link File} for output directory.
     * @deprecated Use {@link spoon.compiler.Environment#setSourceOutputDirectory(File)} instead.
     */
    @java.lang.Deprecated
    void setSourceOutputDirectory(java.io.File outputDirectory);

    /**
     * Gets the output directory of this compiler.
     */
    java.io.File getSourceOutputDirectory();

    /**
     * Sets the output directory for binary generated.
     *
     * @param binaryOutputDirectory
     * 		{@link File} for binary output directory.
     */
    void setBinaryOutputDirectory(java.io.File binaryOutputDirectory);

    /**
     * Gets the binary output directory of the compiler.
     */
    java.io.File getBinaryOutputDirectory();

    /**
     * Gets the classpath that is used to build/compile the input sources.
     */
    java.lang.String[] getSourceClasspath();

    /**
     * Sets the classpath that is used to build/compile the input sources.
     *
     * Each element of the array is either a jar file or a folder containing bytecode files.
     */
    void setSourceClasspath(java.lang.String... classpath);

    /**
     * Gets the classpath that is used to build the template sources.
     *
     * See {@link #setSourceClasspath} for the meaning of the returned string.
     */
    java.lang.String[] getTemplateClasspath();

    /**
     * Sets the classpath that is used to build the template sources.
     */
    void setTemplateClasspath(java.lang.String... classpath);

    /**
     * Returns the working factory
     */
    spoon.reflect.factory.Factory getFactory();

    /**
     * Adds {@code filter}.
     *
     * @param filter
     * 		The {@link CompilationUnitFilter} to add.
     */
    void addCompilationUnitFilter(spoon.support.compiler.jdt.CompilationUnitFilter filter);

    /**
     * Removes {@code filter}. Does nothing, if {@code filter} has not been
     * added beforehand.
     *
     * @param filter
     * 		The {@link CompilationUnitFilter} to remove.
     */
    void removeCompilationUnitFilter(spoon.support.compiler.jdt.CompilationUnitFilter filter);

    /**
     * Returns a copy of the internal list of {@link CompilationUnitFilter}s.
     *
     * @return A copy of the internal list of {@link CompilationUnitFilter}s.
     */
    java.util.List<spoon.support.compiler.jdt.CompilationUnitFilter> getCompilationUnitFilter();
}

