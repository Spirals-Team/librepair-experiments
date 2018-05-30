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
 * This class implements an integrated command-line launcher for processing
 * programs at compile-time using the JDT-based builder (Eclipse). It takes
 * arguments that allow building, processing, printing, and compiling Java
 * programs. Launch with no arguments (see {@link #main(String[])}) for detailed
 * usage.
 */
public class Launcher implements spoon.SpoonAPI {
    public static final java.lang.String SPOONED_CLASSES = "spooned-classes";

    public static final java.lang.String OUTPUTDIR = "spooned";

    private final spoon.reflect.factory.Factory factory;

    private spoon.SpoonModelBuilder modelBuilder;

    private java.lang.String[] commandLineArgs = new java.lang.String[0];

    private spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtType<?>> typeFilter;

    /**
     * Contains the arguments accepted by this launcher (available after
     * construction and accessible by sub-classes).
     */
    private static com.martiansoftware.jsap.JSAP jsapSpec;

    protected com.martiansoftware.jsap.JSAPResult jsapActualArgs;

    private java.util.List<java.lang.String> processorTypes = new java.util.ArrayList<>();

    private java.util.List<spoon.processing.Processor<? extends spoon.reflect.declaration.CtElement>> processors = new java.util.ArrayList<>();

    /**
     * A default program entry point (instantiates a launcher with the given
     * arguments and calls {@link #run()}).
     */
    public static void main(java.lang.String[] args) throws java.lang.Exception {
        new spoon.Launcher().run(args);
    }

    @java.lang.Override
    public void run(java.lang.String[] args) {
        this.setArgs(args);
        if ((args.length) != 0) {
            this.run();
            // display GUI
            if (this.jsapActualArgs.getBoolean("gui")) {
                new spoon.support.gui.SpoonModelTree(getFactory());
            }
        }else {
            this.printUsage();
        }
    }

    public void setArgs(java.lang.String[] args2) {
        this.commandLineArgs = args2;
        processArguments();
    }

    public void printUsage() {
        this.commandLineArgs = new java.lang.String[]{ "--help" };
        processArguments();
    }

    static {
        spoon.Launcher.jsapSpec = spoon.Launcher.defineArgs();
    }

    /**
     * Creates a {@link Launcher} using the {@link Factory} returned by {@link #createFactory()}.
     */
    public Launcher() {
        factory = createFactory();
        processArguments();
    }

    /**
     * Creates a {@link Launcher} with {@link Factory} {@code pFactory}.
     *
     * @param pFactory
     * 		The {@link Factory} that will be utilized in {@link #buildModel()}.
     * @throws IllegalArgumentException
     * 		If {@code pFactory == null}.
     */
    public Launcher(final spoon.reflect.factory.Factory pFactory) {
        if (pFactory == null) {
            throw new java.lang.IllegalArgumentException("unable to create launcher with null factory");
        }
        factory = pFactory;
        processArguments();
    }

    @java.lang.Override
    public void addInputResource(java.lang.String path) {
        java.io.File file = new java.io.File(path);
        if (file.isDirectory()) {
            addInputResource(new spoon.support.compiler.FileSystemFolder(file));
        }else {
            addInputResource(new spoon.support.compiler.FileSystemFile(file));
        }
    }

    /**
     * adds a resource to be parsed to build the spoon model
     */
    public void addInputResource(spoon.compiler.SpoonResource resource) {
        modelBuilder.addInputSource(resource);
    }

    @java.lang.Override
    public void addProcessor(java.lang.String name) {
        processorTypes.add(name);
    }

    @java.lang.Override
    public <T extends spoon.reflect.declaration.CtElement> void addProcessor(spoon.processing.Processor<T> processor) {
        processors.add(processor);
    }

    public void addTemplateResource(spoon.compiler.SpoonResource resource) {
        modelBuilder.addTemplateSource(resource);
    }

    @java.lang.Override
    public spoon.compiler.Environment getEnvironment() {
        return factory.getEnvironment();
    }

    /**
     * Defines the common arguments for sub-launchers.
     *
     * @return the JSAP arguments
     */
    protected static com.martiansoftware.jsap.JSAP defineArgs() {
        try {
            // Verbose output
            com.martiansoftware.jsap.JSAP jsap = new com.martiansoftware.jsap.JSAP();
            // help
            com.martiansoftware.jsap.Switch sw1 = new com.martiansoftware.jsap.Switch("help");
            sw1.setShortFlag('h');
            sw1.setLongFlag("help");
            sw1.setDefault("false");
            jsap.registerParameter(sw1);
            // Tabs
            sw1 = new com.martiansoftware.jsap.Switch("tabs");
            sw1.setLongFlag("tabs");
            sw1.setDefault("false");
            sw1.setHelp("Use tabulations instead of spaces in the generated code (use spaces by default).");
            jsap.registerParameter(sw1);
            // Tab size
            com.martiansoftware.jsap.FlaggedOption opt2 = new com.martiansoftware.jsap.FlaggedOption("tabsize");
            opt2.setLongFlag("tabsize");
            opt2.setStringParser(com.martiansoftware.jsap.JSAP.INTEGER_PARSER);
            opt2.setDefault("4");
            opt2.setHelp("Define tabulation size.");
            jsap.registerParameter(opt2);
            // Level logging.
            opt2 = new com.martiansoftware.jsap.FlaggedOption("level");
            opt2.setLongFlag("level");
            opt2.setHelp("Level of the ouput messages about what spoon is doing.");
            opt2.setStringParser(com.martiansoftware.jsap.JSAP.STRING_PARSER);
            opt2.setDefault(org.apache.log4j.Level.ERROR.toString());
            jsap.registerParameter(opt2);
            // Auto-import
            sw1 = new com.martiansoftware.jsap.Switch("imports");
            sw1.setLongFlag("with-imports");
            sw1.setDefault("false");
            sw1.setHelp("Enable imports in generated files.");
            jsap.registerParameter(sw1);
            // java compliance
            opt2 = new com.martiansoftware.jsap.FlaggedOption("compliance");
            opt2.setLongFlag("compliance");
            opt2.setHelp("Java source code compliance level (1,2,3,4,5, 6, 7 or 8).");
            opt2.setStringParser(com.martiansoftware.jsap.JSAP.INTEGER_PARSER);
            opt2.setDefault(((spoon.support.StandardEnvironment.DEFAULT_CODE_COMPLIANCE_LEVEL) + ""));
            jsap.registerParameter(opt2);
            // compiler's encoding
            opt2 = new com.martiansoftware.jsap.FlaggedOption("encoding");
            opt2.setLongFlag("encoding");
            opt2.setStringParser(com.martiansoftware.jsap.JSAP.STRING_PARSER);
            opt2.setRequired(false);
            opt2.setDefault("UTF-8");
            opt2.setHelp("Forces the compiler to use a specific encoding (UTF-8, UTF-16, ...).");
            jsap.registerParameter(opt2);
            // setting Input files & Directory
            opt2 = new com.martiansoftware.jsap.FlaggedOption("input");
            opt2.setShortFlag('i');
            opt2.setLongFlag("input");
            opt2.setStringParser(com.martiansoftware.jsap.JSAP.STRING_PARSER);
            opt2.setRequired(false);
            opt2.setHelp("List of path to sources files.");
            jsap.registerParameter(opt2);
            // Processor qualified name
            opt2 = new com.martiansoftware.jsap.FlaggedOption("processors");
            opt2.setShortFlag('p');
            opt2.setLongFlag("processors");
            opt2.setHelp("List of processor's qualified name to be used.");
            opt2.setStringParser(com.martiansoftware.jsap.JSAP.STRING_PARSER);
            opt2.setRequired(false);
            jsap.registerParameter(opt2);
            // setting input template
            opt2 = new com.martiansoftware.jsap.FlaggedOption("template");
            opt2.setShortFlag('t');
            opt2.setLongFlag("template");
            opt2.setHelp("List of source templates.");
            opt2.setStringParser(com.martiansoftware.jsap.JSAP.STRING_PARSER);
            opt2.setRequired(false);
            opt2.setHelp("List of path to templates java files.");
            jsap.registerParameter(opt2);
            // Spooned output directory
            opt2 = new com.martiansoftware.jsap.FlaggedOption("output");
            opt2.setShortFlag('o');
            opt2.setLongFlag("output");
            opt2.setDefault(spoon.Launcher.OUTPUTDIR);
            opt2.setHelp("Specify where to place generated java files.");
            opt2.setStringParser(com.martiansoftware.jsap.stringparsers.FileStringParser.getParser());
            opt2.setRequired(false);
            jsap.registerParameter(opt2);
            // Source classpath
            opt2 = new com.martiansoftware.jsap.FlaggedOption("source-classpath");
            opt2.setLongFlag("source-classpath");
            opt2.setHelp(("An optional classpath to be passed to the internal " + ("Java compiler when building or compiling the " + "input sources.")));
            opt2.setStringParser(com.martiansoftware.jsap.JSAP.STRING_PARSER);
            opt2.setRequired(false);
            jsap.registerParameter(opt2);
            // Template classpath
            opt2 = new com.martiansoftware.jsap.FlaggedOption("template-classpath");
            opt2.setLongFlag("template-classpath");
            opt2.setHelp(("An optional classpath to be passed to the " + ("internal Java compiler when building " + "the template sources.")));
            opt2.setStringParser(com.martiansoftware.jsap.JSAP.STRING_PARSER);
            opt2.setRequired(false);
            jsap.registerParameter(opt2);
            // Destination
            opt2 = new com.martiansoftware.jsap.FlaggedOption("destination");
            opt2.setShortFlag('d');
            opt2.setLongFlag("destination");
            opt2.setDefault(spoon.Launcher.SPOONED_CLASSES);
            opt2.setHelp("An optional destination directory for the generated class files.");
            opt2.setStringParser(com.martiansoftware.jsap.stringparsers.FileStringParser.getParser());
            opt2.setRequired(false);
            jsap.registerParameter(opt2);
            // Sets output type generation
            opt2 = new com.martiansoftware.jsap.FlaggedOption("output-type");
            opt2.setLongFlag(opt2.getID());
            java.lang.String msg = "States how to print the processed source code: ";
            int i = 0;
            for (spoon.OutputType v : spoon.OutputType.values()) {
                i++;
                msg += v.toString();
                if (i != (spoon.OutputType.values().length)) {
                    msg += "|";
                }
            }
            opt2.setStringParser(com.martiansoftware.jsap.JSAP.STRING_PARSER);
            opt2.setHelp(msg);
            opt2.setDefault("classes");
            jsap.registerParameter(opt2);
            // Enable compilation
            sw1 = new com.martiansoftware.jsap.Switch("compile");
            sw1.setLongFlag(sw1.getUsageName());
            sw1.setHelp("Compiles the resulting classes (after transformation) to bytecode.");
            sw1.setDefault("false");
            jsap.registerParameter(sw1);
            // Enable pre-compilation
            sw1 = new com.martiansoftware.jsap.Switch("precompile");
            sw1.setLongFlag("precompile");
            sw1.setHelp(("[experimental] Enable pre-compilation of input source files " + ("before processing. The compiled classes " + "will be added to the classpath.")));
            sw1.setDefault("false");
            jsap.registerParameter(sw1);
            sw1 = new com.martiansoftware.jsap.Switch("lines");
            sw1.setLongFlag("lines");
            sw1.setHelp(("Set Spoon to try to preserve the original line " + (("numbers when generating the source " + "code (may lead to human-unfriendly ") + "formatting).")));
            sw1.setDefault("false");
            jsap.registerParameter(sw1);
            // nobinding
            sw1 = new com.martiansoftware.jsap.Switch("noclasspath");
            sw1.setShortFlag('x');
            sw1.setLongFlag("noclasspath");
            sw1.setHelp("Does not assume a full classpath");
            jsap.registerParameter(sw1);
            // show GUI
            sw1 = new com.martiansoftware.jsap.Switch("gui");
            sw1.setShortFlag('g');
            sw1.setLongFlag("gui");
            sw1.setHelp("Show spoon model after processing");
            jsap.registerParameter(sw1);
            // Disable copy of resources.
            sw1 = new com.martiansoftware.jsap.Switch("no-copy-resources");
            sw1.setShortFlag('r');
            sw1.setLongFlag("no-copy-resources");
            sw1.setHelp("Disable the copy of resources from source to destination folder.");
            sw1.setDefault("false");
            jsap.registerParameter(sw1);
            // Enable generation of javadoc.
            sw1 = new com.martiansoftware.jsap.Switch("enable-comments");
            sw1.setShortFlag('c');
            sw1.setLongFlag("enable-comments");
            sw1.setHelp("Adds all code comments in the Spoon AST (Javadoc, line-based comments), rewrites them when pretty-printing.");
            sw1.setDefault("false");
            jsap.registerParameter(sw1);
            // Generate only java files specified.
            opt2 = new com.martiansoftware.jsap.FlaggedOption("generate-files");
            opt2.setShortFlag('f');
            opt2.setLongFlag("generate-files");
            opt2.setHelp("Only generate the given fully qualified java classes (separated by ':' if multiple are given).");
            opt2.setStringParser(com.martiansoftware.jsap.JSAP.STRING_PARSER);
            opt2.setRequired(false);
            jsap.registerParameter(opt2);
            // Disable checks.
            sw1 = new com.martiansoftware.jsap.Switch("disable-model-self-checks");
            sw1.setShortFlag('a');
            sw1.setLongFlag("disable-model-self-checks");
            sw1.setHelp("Disables checks made on the AST (hashcode violation, method's signature violation and parent violation). Default: false.");
            sw1.setDefault("false");
            jsap.registerParameter(sw1);
            return jsap;
        } catch (com.martiansoftware.jsap.JSAPException e) {
            throw new spoon.SpoonException(e.getMessage(), e);
        }
    }

    /**
     * Returns the command-line given launching arguments in JSAP format.
     */
    protected final com.martiansoftware.jsap.JSAPResult getArguments() {
        return parseArgs();
    }

    protected void processArguments() {
        jsapActualArgs = getArguments();
        spoon.compiler.Environment environment = factory.getEnvironment();
        // environment initialization
        environment.setComplianceLevel(jsapActualArgs.getInt("compliance"));
        environment.setLevel(jsapActualArgs.getString("level"));
        environment.setAutoImports(jsapActualArgs.getBoolean("imports"));
        environment.setNoClasspath(jsapActualArgs.getBoolean("noclasspath"));
        environment.setPreserveLineNumbers(jsapActualArgs.getBoolean("lines"));
        environment.setTabulationSize(jsapActualArgs.getInt("tabsize"));
        environment.useTabulations(jsapActualArgs.getBoolean("tabs"));
        environment.setCopyResources((!(jsapActualArgs.getBoolean("no-copy-resources"))));
        environment.setCommentEnabled(jsapActualArgs.getBoolean("enable-comments"));
        environment.setShouldCompile(jsapActualArgs.getBoolean("compile"));
        environment.setSelfChecks(jsapActualArgs.getBoolean("disable-model-self-checks"));
        java.lang.String outputString = jsapActualArgs.getString("output-type");
        spoon.OutputType outputType = spoon.OutputType.fromString(outputString);
        if (outputType == null) {
            throw new spoon.SpoonException(("Unknown output type: " + outputString));
        }else {
            environment.setOutputType(outputType);
        }
        try {
            java.nio.charset.Charset charset = java.nio.charset.Charset.forName(jsapActualArgs.getString("encoding"));
            environment.setEncoding(charset);
        } catch (java.lang.Exception e) {
            throw new spoon.SpoonException(e);
        }
        if ((getArguments().getString("generate-files")) != null) {
            setOutputFilter(getArguments().getString("generate-files").split(":"));
        }
        // now we are ready to create a spoon compiler
        modelBuilder = createCompiler();
        if ((getArguments().getString("input")) != null) {
            for (java.lang.String s : getArguments().getString("input").split((("[" + (java.io.File.pathSeparatorChar)) + "]"))) {
                try {
                    modelBuilder.addInputSource(spoon.compiler.SpoonResourceHelper.createResource(new java.io.File(s)));
                } catch (java.io.FileNotFoundException e) {
                    throw new spoon.SpoonException(e);
                }
            }
        }
        if (jsapActualArgs.getBoolean("precompile")) {
            modelBuilder.compile(spoon.SpoonModelBuilder.InputType.FILES);
            getEnvironment().setSourceClasspath(new java.lang.String[]{ getEnvironment().getBinaryOutputDirectory() });
        }
        if ((getArguments().getFile("output")) != null) {
            setSourceOutputDirectory(getArguments().getFile("output"));
        }
        // Adding template from command-line
        if ((getArguments().getString("template")) != null) {
            for (java.lang.String s : getArguments().getString("template").split((("[" + (java.io.File.pathSeparatorChar)) + "]"))) {
                try {
                    modelBuilder.addTemplateSource(spoon.compiler.SpoonResourceHelper.createResource(new java.io.File(s)));
                } catch (java.io.FileNotFoundException e) {
                    environment.report(null, org.apache.log4j.Level.ERROR, ("Unable to add template file: " + (e.getMessage())));
                    spoon.Launcher.LOGGER.error(e.getMessage(), e);
                }
            }
        }
        if ((getArguments().getString("processors")) != null) {
            for (java.lang.String processorName : getArguments().getString("processors").split(java.io.File.pathSeparator)) {
                addProcessor(processorName);
            }
        }
    }

    /**
     * Gets the list of processor types to be initially applied during the
     * processing (-p option).
     */
    protected java.util.List<java.lang.String> getProcessorTypes() {
        return processorTypes;
    }

    /**
     * Gets the list of processors instance to be initially applied during the
     * processing.
     */
    protected java.util.List<spoon.processing.Processor<? extends spoon.reflect.declaration.CtElement>> getProcessors() {
        return processors;
    }

    /**
     * Parses the arguments given by the command line.
     *
     * @return the JSAP-presented arguments
     */
    protected com.martiansoftware.jsap.JSAPResult parseArgs() {
        if ((spoon.Launcher.jsapSpec) == null) {
            throw new java.lang.IllegalStateException("no args, please call setArgs before");
        }
        com.martiansoftware.jsap.JSAPResult arguments = spoon.Launcher.jsapSpec.parse(commandLineArgs);
        if (!(arguments.success())) {
            // print out specific error messages describing the problems
            for (java.util.Iterator<?> errs = arguments.getErrorMessageIterator(); errs.hasNext();) {
                java.lang.System.err.println(("Error: " + (errs.next())));
            }
        }
        if ((!(arguments.success())) || (arguments.getBoolean("help"))) {
            java.lang.System.err.println(getVersionMessage());
            java.lang.System.err.println("Usage: java <launcher name> [option(s)]");
            java.lang.System.err.println();
            java.lang.System.err.println("Options : ");
            java.lang.System.err.println();
            java.lang.System.err.println(spoon.Launcher.jsapSpec.getHelp());
            java.lang.System.exit((-1));
        }
        return arguments;
    }

    /**
     * A default logger to be used by Spoon.
     */
    public static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(spoon.Launcher.class);

    /**
     * Creates a new Spoon Java compiler in order to process and compile Java
     * source code.
     *
     * @param factory
     * 		the factory this compiler works on
     */
    public spoon.SpoonModelBuilder createCompiler(spoon.reflect.factory.Factory factory) {
        spoon.SpoonModelBuilder comp = new spoon.support.compiler.jdt.JDTBasedSpoonCompiler(factory);
        spoon.compiler.Environment env = getEnvironment();
        // building
        comp.setBinaryOutputDirectory(jsapActualArgs.getFile("destination"));
        // backward compatibility
        // we don't have to set the source classpath
        if (jsapActualArgs.contains("source-classpath")) {
            comp.setSourceClasspath(jsapActualArgs.getString("source-classpath").split(java.lang.System.getProperty("path.separator")));
        }
        env.debugMessage(("destination: " + (comp.getBinaryOutputDirectory())));
        env.debugMessage(("source classpath: " + (java.util.Arrays.toString(comp.getSourceClasspath()))));
        env.debugMessage(("template classpath: " + (java.util.Arrays.toString(comp.getTemplateClasspath()))));
        return comp;
    }

    public spoon.SpoonModelBuilder createCompiler(spoon.reflect.factory.Factory factory, java.util.List<spoon.compiler.SpoonResource> inputSources) {
        spoon.SpoonModelBuilder c = createCompiler(factory);
        c.addInputSources(inputSources);
        return c;
    }

    /**
     * Creates a new Spoon Java compiler in order to process and compile Java
     * source code.
     */
    public spoon.SpoonModelBuilder createCompiler(spoon.reflect.factory.Factory factory, java.util.List<spoon.compiler.SpoonResource> inputSources, java.util.List<spoon.compiler.SpoonResource> templateSources) {
        spoon.SpoonModelBuilder c = createCompiler(factory);
        c.addInputSources(inputSources);
        c.addTemplateSources(templateSources);
        return c;
    }

    @java.lang.Override
    public spoon.SpoonModelBuilder createCompiler() {
        return createCompiler(factory);
    }

    /**
     * Creates a new Spoon Java compiler with a default factory and a list of
     * input sources.
     */
    public spoon.SpoonModelBuilder createCompiler(java.util.List<spoon.compiler.SpoonResource> inputSources) {
        spoon.SpoonModelBuilder c = createCompiler(factory);
        c.addInputSources(inputSources);
        return c;
    }

    @java.lang.Override
    public spoon.reflect.factory.Factory createFactory() {
        return new spoon.reflect.factory.FactoryImpl(new spoon.support.DefaultCoreFactory(), createEnvironment());
    }

    @java.lang.Override
    public spoon.reflect.factory.Factory getFactory() {
        return factory;
    }

    @java.lang.Override
    public spoon.compiler.Environment createEnvironment() {
        return new spoon.support.StandardEnvironment();
    }

    @java.lang.Deprecated
    public spoon.support.JavaOutputProcessor createOutputWriter(java.io.File sourceOutputDir, spoon.compiler.Environment environment) {
        return this.createOutputWriter();
    }

    public spoon.support.JavaOutputProcessor createOutputWriter() {
        spoon.support.JavaOutputProcessor outputProcessor = new spoon.support.JavaOutputProcessor(createPrettyPrinter());
        outputProcessor.setFactory(this.getFactory());
        return outputProcessor;
    }

    public spoon.reflect.visitor.PrettyPrinter createPrettyPrinter() {
        return new spoon.reflect.visitor.DefaultJavaPrettyPrinter(getEnvironment());
    }

    /**
     * Runs Spoon using the given compiler, with the given run options. A Spoon
     * run will perform the following tasks:
     *
     * <ol>
     * <li>Source model building in the given compiler:
     * {@link SpoonModelBuilder#build()}.</li>
     * <li>Template model building in the given factory (if any template source
     * is given): {@link SpoonModelBuilder#build()}.</li>
     * <li>Model processing with the list of given processors if any:
     * {@link SpoonModelBuilder#instantiateAndProcess(List)}.</li>
     * <li>Processed Source code printing and generation (can be disabled with
     * {@link OutputType#NO_OUTPUT}):
     * {@link SpoonModelBuilder#generateProcessedSourceFiles(OutputType)}.</li>
     * <li>Processed source code compilation (optional):
     * </ol>
     */
    @java.lang.Override
    public void run() {
        spoon.compiler.Environment env = modelBuilder.getFactory().getEnvironment();
        env.reportProgressMessage(getVersionMessage());
        env.reportProgressMessage("running Spoon...");
        env.reportProgressMessage("start processing...");
        long t = 0;
        long tstart = java.lang.System.currentTimeMillis();
        buildModel();
        process();
        prettyprint();
        if (env.shouldCompile()) {
            // we compile the types from the factory, they may have been modified by some processors
            modelBuilder.compile(spoon.SpoonModelBuilder.InputType.CTTYPES);
        }
        t = java.lang.System.currentTimeMillis();
        env.debugMessage((("program spooning done in " + (t - tstart)) + " ms"));
        env.reportEnd();
    }

    private java.lang.String getVersionMessage() {
        return "Spoon version " + (java.util.ResourceBundle.getBundle("spoon").getString("application.version"));
    }

    public static final org.apache.commons.io.filefilter.IOFileFilter RESOURCES_FILE_FILTER = new org.apache.commons.io.filefilter.IOFileFilter() {
        @java.lang.Override
        public boolean accept(java.io.File file) {
            return !(file.getName().endsWith(".java"));
        }

        @java.lang.Override
        public boolean accept(java.io.File file, java.lang.String s) {
            return false;
        }
    };

    public static final org.apache.commons.io.filefilter.IOFileFilter ALL_DIR_FILTER = new org.apache.commons.io.filefilter.IOFileFilter() {
        @java.lang.Override
        public boolean accept(java.io.File file) {
            return true;
        }

        @java.lang.Override
        public boolean accept(java.io.File file, java.lang.String s) {
            return false;
        }
    };

    @java.lang.Override
    public spoon.reflect.CtModel buildModel() {
        long tstart = java.lang.System.currentTimeMillis();
        modelBuilder.build();
        getEnvironment().debugMessage(("model built in " + ((java.lang.System.currentTimeMillis()) - tstart)));
        return modelBuilder.getFactory().getModel();
    }

    @java.lang.Override
    public void process() {
        long tstart = java.lang.System.currentTimeMillis();
        modelBuilder.instantiateAndProcess(getProcessorTypes());
        modelBuilder.process(getProcessors());
        getEnvironment().debugMessage((("model processed in " + ((java.lang.System.currentTimeMillis()) - tstart)) + " ms"));
    }

    @java.lang.Override
    public void prettyprint() {
        long tstart = java.lang.System.currentTimeMillis();
        try {
            modelBuilder.generateProcessedSourceFiles(getEnvironment().getOutputType(), typeFilter);
        } catch (java.lang.Exception e) {
            throw new spoon.SpoonException(e);
        }
        if ((!(getEnvironment().getOutputType().equals(spoon.OutputType.NO_OUTPUT))) && (getEnvironment().isCopyResources())) {
            for (java.io.File dirInputSource : modelBuilder.getInputSources()) {
                if (dirInputSource.isDirectory()) {
                    final java.util.Collection<?> resources = org.apache.commons.io.FileUtils.listFiles(dirInputSource, spoon.Launcher.RESOURCES_FILE_FILTER, spoon.Launcher.ALL_DIR_FILTER);
                    for (java.lang.Object resource : resources) {
                        final java.lang.String resourceParentPath = ((java.io.File) (resource)).getParent();
                        final java.lang.String packageDir = resourceParentPath.substring(dirInputSource.getPath().length());
                        final java.lang.String targetDirectory = (getEnvironment().getDefaultFileGenerator().getOutputDirectory()) + packageDir;
                        try {
                            org.apache.commons.io.FileUtils.copyFileToDirectory(((java.io.File) (resource)), new java.io.File(targetDirectory));
                        } catch (java.io.IOException e) {
                            throw new spoon.SpoonException(e);
                        }
                    }
                }
            }
        }
        getEnvironment().debugMessage((("pretty-printed in " + ((java.lang.System.currentTimeMillis()) - tstart)) + " ms"));
    }

    public spoon.SpoonModelBuilder getModelBuilder() {
        return modelBuilder;
    }

    @java.lang.Override
    public void setSourceOutputDirectory(java.lang.String path) {
        setSourceOutputDirectory(new java.io.File(path));
    }

    @java.lang.Override
    public void setSourceOutputDirectory(java.io.File outputDirectory) {
        getEnvironment().setSourceOutputDirectory(outputDirectory);
        getEnvironment().setDefaultFileGenerator(createOutputWriter());
    }

    @java.lang.Override
    public void setOutputFilter(spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtType<?>> typeFilter) {
        this.typeFilter = typeFilter;
    }

    @java.lang.Override
    public void setOutputFilter(final java.lang.String... qualifedNames) {
        setOutputFilter(new spoon.reflect.visitor.filter.AbstractFilter<spoon.reflect.declaration.CtType<?>>(spoon.reflect.declaration.CtType.class) {
            @java.lang.Override
            public boolean matches(spoon.reflect.declaration.CtType<?> element) {
                for (java.lang.String generateFile : qualifedNames) {
                    if (generateFile.equals(element.getQualifiedName())) {
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @java.lang.Override
    public void setBinaryOutputDirectory(java.lang.String path) {
        getFactory().getEnvironment().setBinaryOutputDirectory(path);
    }

    @java.lang.Override
    public void setBinaryOutputDirectory(java.io.File outputDirectory) {
        setBinaryOutputDirectory(outputDirectory.getPath());
    }

    @java.lang.Override
    public spoon.reflect.CtModel getModel() {
        return factory.getModel();
    }

    /**
     * returns the AST of an inline class
     */
    public static spoon.reflect.declaration.CtClass<?> parseClass(java.lang.String code) {
        spoon.Launcher launcher = new spoon.Launcher();
        launcher.addInputResource(new spoon.support.compiler.VirtualFile(code));
        launcher.getEnvironment().setNoClasspath(true);
        launcher.getEnvironment().setAutoImports(true);
        java.util.Collection<spoon.reflect.declaration.CtType<?>> allTypes = launcher.buildModel().getAllTypes();
        if ((allTypes.size()) != 1) {
            throw new spoon.SpoonException("parseClass only considers one class. Please consider using a Launcher object for more advanced usage.");
        }
        try {
            return ((spoon.reflect.declaration.CtClass<?>) (allTypes.stream().findFirst().get()));
        } catch (java.lang.ClassCastException e) {
            throw new spoon.SpoonException("parseClass only considers classes (and not interfaces and enums). Please consider using a Launcher object for more advanced usage.");
        }
    }
}

