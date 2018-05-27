package spoon.support.compiler.jdt;


public class JDTBasedSpoonCompiler implements spoon.SpoonModelBuilder {
    protected org.eclipse.jdt.internal.compiler.env.INameEnvironment environment = null;

    protected final java.util.List<org.eclipse.jdt.core.compiler.CategorizedProblem> probs = new java.util.ArrayList<>();

    protected final spoon.support.compiler.jdt.TreeBuilderRequestor requestor = new spoon.support.compiler.jdt.TreeBuilderRequestor(this);

    protected spoon.reflect.factory.Factory factory;

    protected int javaCompliance = 7;

    protected boolean build = false;

    protected spoon.compiler.SpoonFolder sources = new spoon.support.compiler.VirtualFolder();

    protected spoon.compiler.SpoonFolder templates = new spoon.support.compiler.VirtualFolder();

    protected java.lang.String[] templateClasspath = new java.lang.String[0];

    protected java.util.List<spoon.support.compiler.jdt.CompilationUnitFilter> compilationUnitFilters = new java.util.ArrayList<>();

    private boolean sortList;

    public JDTBasedSpoonCompiler(spoon.reflect.factory.Factory factory) {
        this.factory = factory;
        this.initializeCUCOmparator();
    }

    private void initializeCUCOmparator() {
        try {
            if ((java.lang.System.getenv("SPOON_SEED_CU_COMPARATOR")) != null) {
                this.sortList = false;
            }else {
                this.sortList = true;
            }
        } catch (java.lang.NumberFormatException | java.lang.SecurityException e) {
            spoon.Launcher.LOGGER.error("Error while parsing Spoon seed for CU sorting", e);
            this.sortList = true;
        }
    }

    @java.lang.Override
    public boolean build() {
        return build(null);
    }

    @java.lang.Override
    public boolean build(spoon.compiler.builder.JDTBuilder builder) {
        if ((factory) == null) {
            throw new spoon.SpoonException("Factory not initialized");
        }
        if (build) {
            throw new spoon.SpoonException("Model already built");
        }
        build = true;
        boolean srcSuccess;
        boolean templateSuccess;
        factory.getEnvironment().debugMessage(("building sources: " + (sources.getAllJavaFiles())));
        long t = java.lang.System.currentTimeMillis();
        javaCompliance = factory.getEnvironment().getComplianceLevel();
        srcSuccess = buildSources(builder);
        reportProblems(factory.getEnvironment());
        factory.getEnvironment().debugMessage((("built in " + ((java.lang.System.currentTimeMillis()) - t)) + " ms"));
        factory.getEnvironment().debugMessage(("building templates: " + (templates.getAllJavaFiles())));
        t = java.lang.System.currentTimeMillis();
        templateSuccess = buildTemplates(builder);
        factory.getEnvironment().debugMessage((("built in " + ((java.lang.System.currentTimeMillis()) - t)) + " ms"));
        checkModel();
        return srcSuccess && templateSuccess;
    }

    private void checkModel() {
        if (!(factory.getEnvironment().checksAreSkipped())) {
            factory.getModel().getUnnamedModule().accept(new spoon.reflect.visitor.AstParentConsistencyChecker());
        }
    }

    @java.lang.Override
    public boolean compile(spoon.SpoonModelBuilder.InputType... types) {
        factory.getEnvironment().debugMessage(("compiling sources: " + (factory.CompilationUnit().getMap().keySet())));
        long t = java.lang.System.currentTimeMillis();
        javaCompliance = factory.getEnvironment().getComplianceLevel();
        spoon.support.compiler.jdt.JDTBatchCompiler batchCompiler = createBatchCompiler(types);
        final java.lang.String[] args = new spoon.compiler.builder.JDTBuilderImpl().classpathOptions(new spoon.compiler.builder.ClasspathOptions().encoding(this.getEnvironment().getEncoding().displayName()).classpath(getSourceClasspath()).binaries(getBinaryOutputDirectory())).complianceOptions(new spoon.compiler.builder.ComplianceOptions().compliance(javaCompliance)).annotationProcessingOptions(new spoon.compiler.builder.AnnotationProcessingOptions().compileProcessors()).advancedOptions(new spoon.compiler.builder.AdvancedOptions().preserveUnusedVars().continueExecution().enableJavadoc()).sources(new spoon.compiler.builder.SourceOptions().sources(sources.getAllJavaFiles())).build();
        getFactory().getEnvironment().debugMessage(("compile args: " + (java.util.Arrays.toString(args))));
        java.lang.System.setProperty("jdt.compiler.useSingleThread", "true");
        batchCompiler.compile(args);
        reportProblems(factory.getEnvironment());
        factory.getEnvironment().debugMessage((("compiled in " + ((java.lang.System.currentTimeMillis()) - t)) + " ms"));
        return (probs.size()) == 0;
    }

    @java.lang.Override
    public void instantiateAndProcess(java.util.List<java.lang.String> processors) {
        spoon.processing.ProcessingManager processing = new spoon.support.QueueProcessingManager(factory);
        for (java.lang.String processorName : processors) {
            processing.addProcessor(processorName);
            factory.getEnvironment().debugMessage((("Loaded processor " + processorName) + "."));
        }
        processing.process(factory.Package().getRootPackage());
    }

    @java.lang.Override
    public void process(java.util.Collection<spoon.processing.Processor<? extends spoon.reflect.declaration.CtElement>> processors) {
        spoon.processing.ProcessingManager processing = new spoon.support.QueueProcessingManager(factory);
        for (spoon.processing.Processor<? extends spoon.reflect.declaration.CtElement> processorName : processors) {
            processing.addProcessor(processorName);
            factory.getEnvironment().debugMessage((("Loaded processor " + processorName) + "."));
        }
        processing.process(factory.Package().getRootPackage());
    }

    @java.lang.Override
    public void generateProcessedSourceFiles(spoon.OutputType outputType) {
        generateProcessedSourceFiles(outputType, null);
    }

    @java.lang.Override
    public void generateProcessedSourceFiles(spoon.OutputType outputType, spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtType<?>> typeFilter) {
        switch (outputType) {
            case CLASSES :
                generateProcessedSourceFilesUsingTypes(typeFilter);
                break;
            case COMPILATION_UNITS :
                generateProcessedSourceFilesUsingCUs();
                break;
            case NO_OUTPUT :
        }
    }

    @java.lang.Override
    public void addInputSource(java.io.File source) {
        try {
            if (spoon.compiler.SpoonResourceHelper.isFile(source)) {
                this.sources.addFile(spoon.compiler.SpoonResourceHelper.createFile(source));
            }else {
                this.sources.addFolder(spoon.compiler.SpoonResourceHelper.createFolder(source));
            }
        } catch (java.lang.Exception e) {
            throw new spoon.SpoonException(e);
        }
    }

    @java.lang.Override
    public void addInputSource(spoon.compiler.SpoonResource source) {
        if (source.isFile()) {
            this.sources.addFile(((spoon.compiler.SpoonFile) (source)));
        }else {
            this.sources.addFolder(((spoon.compiler.SpoonFolder) (source)));
        }
    }

    @java.lang.Override
    public void addInputSources(java.util.List<spoon.compiler.SpoonResource> resources) {
        for (spoon.compiler.SpoonResource r : resources) {
            addInputSource(r);
        }
    }

    @java.lang.Override
    public java.util.Set<java.io.File> getInputSources() {
        java.util.Set<java.io.File> files = new java.util.HashSet<>();
        for (spoon.compiler.SpoonFolder file : getSource().getSubFolders()) {
            files.add(new java.io.File(file.getPath()));
        }
        return files;
    }

    @java.lang.Override
    public void addTemplateSource(spoon.compiler.SpoonResource source) {
        if (source.isFile()) {
            this.templates.addFile(((spoon.compiler.SpoonFile) (source)));
        }else {
            this.templates.addFolder(((spoon.compiler.SpoonFolder) (source)));
        }
    }

    @java.lang.Override
    public void addTemplateSource(java.io.File source) {
        try {
            if (spoon.compiler.SpoonResourceHelper.isFile(source)) {
                this.templates.addFile(spoon.compiler.SpoonResourceHelper.createFile(source));
            }else {
                this.templates.addFolder(spoon.compiler.SpoonResourceHelper.createFolder(source));
            }
        } catch (java.lang.Exception e) {
            throw new spoon.SpoonException(e);
        }
    }

    @java.lang.Override
    public void addTemplateSources(java.util.List<spoon.compiler.SpoonResource> resources) {
        for (spoon.compiler.SpoonResource r : resources) {
            addTemplateSource(r);
        }
    }

    @java.lang.Override
    public java.util.Set<java.io.File> getTemplateSources() {
        java.util.Set<java.io.File> files = new java.util.HashSet<>();
        for (spoon.compiler.SpoonFolder file : getTemplates().getSubFolders()) {
            files.add(new java.io.File(file.getPath()));
        }
        return files;
    }

    @java.lang.Override
    public void setSourceOutputDirectory(java.io.File outputDirectory) {
        this.getEnvironment().setSourceOutputDirectory(outputDirectory);
    }

    @java.lang.Override
    public java.io.File getSourceOutputDirectory() {
        return this.factory.getEnvironment().getSourceOutputDirectory();
    }

    @java.lang.Override
    public void setBinaryOutputDirectory(java.io.File binaryOutputDirectory) {
        this.getEnvironment().setBinaryOutputDirectory(binaryOutputDirectory.getAbsolutePath());
    }

    @java.lang.Override
    public java.io.File getBinaryOutputDirectory() {
        return new java.io.File(getEnvironment().getBinaryOutputDirectory());
    }

    @java.lang.Override
    public java.lang.String[] getSourceClasspath() {
        return getEnvironment().getSourceClasspath();
    }

    @java.lang.Override
    public void setSourceClasspath(java.lang.String... classpath) {
        getEnvironment().setSourceClasspath(classpath);
    }

    @java.lang.Override
    public java.lang.String[] getTemplateClasspath() {
        return templateClasspath;
    }

    @java.lang.Override
    public void setTemplateClasspath(java.lang.String... classpath) {
        this.templateClasspath = classpath;
    }

    @java.lang.Override
    public spoon.reflect.factory.Factory getFactory() {
        return factory;
    }

    protected boolean buildSources(spoon.compiler.builder.JDTBuilder jdtBuilder) {
        return buildUnitsAndModel(jdtBuilder, sources, getSourceClasspath(), "");
    }

    protected spoon.support.compiler.jdt.JDTBatchCompiler createBatchCompiler() {
        return new spoon.support.compiler.jdt.JDTBatchCompiler(this);
    }

    protected spoon.support.compiler.jdt.JDTBatchCompiler createBatchCompiler(spoon.SpoonModelBuilder.InputType... types) {
        spoon.support.compiler.jdt.JDTBatchCompiler batchCompiler = createBatchCompiler();
        if ((types.length) == 0) {
            types = new spoon.SpoonModelBuilder.InputType[]{ spoon.SpoonModelBuilder.InputType.CTTYPES };
        }
        for (spoon.SpoonModelBuilder.InputType inputType : types) {
            inputType.initializeCompiler(batchCompiler);
        }
        return batchCompiler;
    }

    protected boolean buildTemplates(spoon.compiler.builder.JDTBuilder jdtBuilder) {
        return buildUnitsAndModel(jdtBuilder, templates, getTemplateClasspath(), "template ");
    }

    @java.lang.Deprecated
    protected boolean buildUnitsAndModel(spoon.compiler.builder.JDTBuilder jdtBuilder, spoon.compiler.SpoonFolder sourcesFolder, java.lang.String[] classpath, java.lang.String debugMessagePrefix, boolean buildOnlyOutdatedFiles) {
        return buildUnitsAndModel(jdtBuilder, sourcesFolder, classpath, debugMessagePrefix);
    }

    protected boolean buildUnitsAndModel(spoon.compiler.builder.JDTBuilder jdtBuilder, spoon.compiler.SpoonFolder sourcesFolder, java.lang.String[] classpath, java.lang.String debugMessagePrefix) {
        org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration[] units = buildUnits(jdtBuilder, sourcesFolder, classpath, debugMessagePrefix);
        buildModel(units);
        return (probs.size()) == 0;
    }

    private static final org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration[] EMPTY_RESULT = new org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration[0];

    @java.lang.Deprecated
    protected org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration[] buildUnits(spoon.compiler.builder.JDTBuilder jdtBuilder, spoon.compiler.SpoonFolder sourcesFolder, java.lang.String[] classpath, java.lang.String debugMessagePrefix, boolean buildOnlyOutdatedFiles) {
        return this.buildUnits(jdtBuilder, sourcesFolder, classpath, debugMessagePrefix);
    }

    protected org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration[] buildUnits(spoon.compiler.builder.JDTBuilder jdtBuilder, spoon.compiler.SpoonFolder sourcesFolder, java.lang.String[] classpath, java.lang.String debugMessagePrefix) {
        java.util.List<spoon.compiler.SpoonFile> sourceFiles = java.util.Collections.unmodifiableList(sourcesFolder.getAllJavaFiles());
        if (sourceFiles.isEmpty()) {
            return spoon.support.compiler.jdt.JDTBasedSpoonCompiler.EMPTY_RESULT;
        }
        spoon.support.compiler.jdt.JDTBatchCompiler batchCompiler = createBatchCompiler(new spoon.support.compiler.jdt.FileCompilerConfig(sourceFiles));
        java.lang.String[] args;
        if (jdtBuilder == null) {
            args = new spoon.compiler.builder.JDTBuilderImpl().classpathOptions(new spoon.compiler.builder.ClasspathOptions().encoding(this.getEnvironment().getEncoding().displayName()).classpath(classpath)).complianceOptions(new spoon.compiler.builder.ComplianceOptions().compliance(javaCompliance)).advancedOptions(new spoon.compiler.builder.AdvancedOptions().preserveUnusedVars().continueExecution().enableJavadoc()).sources(new spoon.compiler.builder.SourceOptions().sources(sourceFiles)).build();
        }else {
            args = jdtBuilder.build();
        }
        getFactory().getEnvironment().debugMessage(((debugMessagePrefix + "build args: ") + (java.util.Arrays.toString(args))));
        batchCompiler.configure(args);
        org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration[] units = batchCompiler.getUnits();
        return units;
    }

    protected java.util.List<org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration> sortCompilationUnits(org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration[] units) {
        java.util.List<org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration> unitList = new java.util.ArrayList<>(java.util.Arrays.asList(units));
        if (this.sortList) {
            unitList.sort(new spoon.support.comparator.FixedOrderBasedOnFileNameCompilationUnitComparator());
        }else {
            java.util.Collections.shuffle(unitList);
        }
        return unitList;
    }

    protected void buildModel(org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration[] units) {
        spoon.support.compiler.jdt.JDTTreeBuilder builder = new spoon.support.compiler.jdt.JDTTreeBuilder(factory);
        java.util.List<org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration> unitList = this.sortCompilationUnits(units);
        unitLoop : for (org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration unit : unitList) {
            if ((unit.isModuleInfo()) || (!(unit.isEmpty()))) {
                final java.lang.String unitPath = new java.lang.String(unit.getFileName());
                for (final spoon.support.compiler.jdt.CompilationUnitFilter cuf : compilationUnitFilters) {
                    if (cuf.exclude(unitPath)) {
                        continue unitLoop;
                    }
                }
                unit.traverse(builder, unit.scope);
                if (getFactory().getEnvironment().isCommentsEnabled()) {
                    new spoon.support.compiler.jdt.JDTCommentBuilder(unit, factory).build();
                }
            }
        }
        if (getFactory().getEnvironment().isAutoImports()) {
            for (org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration unit : units) {
                new spoon.support.compiler.jdt.JDTImportBuilder(unit, factory).build();
            }
        }
    }

    protected void generateProcessedSourceFilesUsingTypes(spoon.reflect.visitor.Filter<spoon.reflect.declaration.CtType<?>> typeFilter) {
        if ((factory.getEnvironment().getDefaultFileGenerator()) != null) {
            factory.getEnvironment().debugMessage("Generating source using types...");
            spoon.processing.ProcessingManager processing = new spoon.support.QueueProcessingManager(factory);
            processing.addProcessor(factory.getEnvironment().getDefaultFileGenerator());
            if (typeFilter != null) {
                processing.process(spoon.reflect.visitor.Query.getElements(factory.getModel().getUnnamedModule(), typeFilter));
            }else {
                processing.process(factory.getModel().getAllModules());
            }
        }
    }

    protected void generateProcessedSourceFilesUsingCUs() {
        java.io.File outputDirectory = getSourceOutputDirectory();
        factory.getEnvironment().debugMessage("Generating source using compilation units...");
        if (outputDirectory == null) {
            throw new java.lang.RuntimeException("You should set output directory before generating source files");
        }
        if (outputDirectory.isFile()) {
            throw new java.lang.RuntimeException("Output must be a directory");
        }
        if (!(outputDirectory.exists())) {
            if (!(outputDirectory.mkdirs())) {
                throw new java.lang.RuntimeException("Error creating output directory");
            }
        }
        try {
            outputDirectory = outputDirectory.getCanonicalFile();
        } catch (java.io.IOException e1) {
            throw new spoon.SpoonException(e1);
        }
        factory.getEnvironment().debugMessage(("Generating source files to: " + outputDirectory));
        java.util.List<java.io.File> printedFiles = new java.util.ArrayList<>();
        for (spoon.reflect.cu.CompilationUnit cu : factory.CompilationUnit().getMap().values()) {
            if ((cu.getDeclaredTypes().size()) == 0) {
                continue;
            }
            spoon.reflect.declaration.CtType<?> element = cu.getMainType();
            spoon.reflect.declaration.CtPackage pack = element.getPackage();
            java.io.File packageDir;
            if (pack.isUnnamedPackage()) {
                packageDir = new java.io.File(outputDirectory.getAbsolutePath());
            }else {
                packageDir = new java.io.File((((outputDirectory.getAbsolutePath()) + (java.io.File.separatorChar)) + (pack.getQualifiedName().replace('.', java.io.File.separatorChar))));
            }
            if (!(packageDir.exists())) {
                if (!(packageDir.mkdirs())) {
                    throw new java.lang.RuntimeException("Error creating output directory");
                }
            }
            try {
                java.io.File file = new java.io.File(((((packageDir.getAbsolutePath()) + (java.io.File.separatorChar)) + (element.getSimpleName())) + (spoon.reflect.visitor.DefaultJavaPrettyPrinter.JAVA_FILE_EXTENSION)));
                file.createNewFile();
                try (java.io.InputStream is = getCompilationUnitInputStream(cu.getFile().getPath());java.io.FileOutputStream outFile = new java.io.FileOutputStream(file)) {
                    org.apache.commons.io.IOUtils.copy(is, outFile);
                }
                if (!(printedFiles.contains(file))) {
                    printedFiles.add(file);
                }
            } catch (java.lang.Exception e) {
                spoon.Launcher.LOGGER.error(e.getMessage(), e);
            }
        }
    }

    public void setEnvironment(org.eclipse.jdt.internal.compiler.env.INameEnvironment environment) {
        this.environment = environment;
    }

    public void reportProblem(org.eclipse.jdt.core.compiler.CategorizedProblem pb) {
        if (pb == null) {
            return;
        }
        if ((pb.getID()) == (org.eclipse.jdt.core.compiler.IProblem.DuplicateTypes)) {
            throw new spoon.compiler.ModelBuildingException(pb.getMessage());
        }
        probs.add(pb);
    }

    public void reportProblems(spoon.compiler.Environment environment) {
        if ((getProblems().size()) > 0) {
            for (org.eclipse.jdt.core.compiler.CategorizedProblem problem : getProblems()) {
                if (problem != null) {
                    report(environment, problem);
                }
            }
        }
    }

    protected void report(spoon.compiler.Environment environment, org.eclipse.jdt.core.compiler.CategorizedProblem problem) {
        if (problem == null) {
            throw new java.lang.IllegalArgumentException("problem cannot be null");
        }
        java.io.File file = new java.io.File(new java.lang.String(problem.getOriginatingFileName()));
        java.lang.String filename = file.getAbsolutePath();
        java.lang.String message = ((((problem.getMessage()) + " at ") + filename) + ":") + (problem.getSourceLineNumber());
        if (problem.isError()) {
            if (!(environment.getNoClasspath())) {
                throw new spoon.compiler.ModelBuildingException(message);
            }else {
                int problemId = problem.getID();
                if (((problemId != (org.eclipse.jdt.core.compiler.IProblem.UndefinedType)) && (problemId != (org.eclipse.jdt.core.compiler.IProblem.UndefinedName))) && (problemId != (org.eclipse.jdt.core.compiler.IProblem.ImportNotFound))) {
                    environment.report(null, org.apache.log4j.Level.WARN, message);
                }
            }
        }
    }

    public java.util.List<org.eclipse.jdt.core.compiler.CategorizedProblem> getProblems() {
        return java.util.Collections.unmodifiableList(this.probs);
    }

    public spoon.compiler.SpoonFolder getSource() {
        return sources;
    }

    public spoon.compiler.SpoonFolder getTemplates() {
        return templates;
    }

    protected java.io.InputStream getCompilationUnitInputStream(java.lang.String path) {
        spoon.compiler.Environment env = factory.getEnvironment();
        spoon.reflect.cu.CompilationUnit cu = factory.CompilationUnit().getMap().get(path);
        java.util.List<spoon.reflect.declaration.CtType<?>> toBePrinted = cu.getDeclaredTypes();
        spoon.reflect.visitor.PrettyPrinter printer = new spoon.reflect.visitor.DefaultJavaPrettyPrinter(env);
        printer.calculate(cu, toBePrinted);
        return new java.io.ByteArrayInputStream(printer.getResult().toString().getBytes());
    }

    protected spoon.compiler.Environment getEnvironment() {
        return getFactory().getEnvironment();
    }

    @java.lang.Override
    public void addCompilationUnitFilter(final spoon.support.compiler.jdt.CompilationUnitFilter filter) {
        compilationUnitFilters.add(filter);
    }

    @java.lang.Override
    public void removeCompilationUnitFilter(spoon.support.compiler.jdt.CompilationUnitFilter filter) {
        compilationUnitFilters.remove(filter);
    }

    @java.lang.Override
    public java.util.List<spoon.support.compiler.jdt.CompilationUnitFilter> getCompilationUnitFilter() {
        return new java.util.ArrayList<>(compilationUnitFilters);
    }
}

