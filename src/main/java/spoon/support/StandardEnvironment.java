/**
 * Copyright (C) 2006-2018 INRIA and contributors
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


import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import spoon.Launcher;
import spoon.OutputType;
import spoon.SpoonException;
import spoon.compiler.Environment;
import spoon.compiler.InvalidClassPathException;
import spoon.compiler.SpoonFile;
import spoon.compiler.SpoonFolder;
import spoon.experimental.modelobs.EmptyModelChangeListener;
import spoon.experimental.modelobs.FineModelChangeListener;
import spoon.processing.FileGenerator;
import spoon.processing.ProblemFixer;
import spoon.processing.ProcessingManager;
import spoon.processing.Processor;
import spoon.processing.ProcessorProperties;
import spoon.reflect.cu.SourcePosition;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtType;
import spoon.reflect.declaration.ParentNotInitializedException;
import spoon.support.compiler.FileSystemFolder;
import spoon.support.compiler.SpoonProgress;


/**
 * This class implements a simple Spoon environment that reports messages in the
 * standard output stream (Java-compliant).
 */
public class StandardEnvironment implements Serializable, Environment {

	private static final long serialVersionUID = 1L;

	public static final int DEFAULT_CODE_COMPLIANCE_LEVEL = 8;

	private transient  FileGenerator<? extends CtElement> defaultFileGenerator;

	private int errorCount = 0;

	transient ProcessingManager manager;

	private boolean processingStopped = false;

	private boolean autoImports = false;

	private int warningCount = 0;

	private String[] sourceClasspath = null;

	private boolean preserveLineNumbers = false;

	private boolean copyResources = true;

	private boolean enableComments = true;

	private transient  Logger logger = Launcher.LOGGER;

	private Level level = Level.OFF;

	private boolean shouldCompile = false;

	private boolean skipSelfChecks = false;

	private transient  FineModelChangeListener modelChangeListener = new EmptyModelChangeListener();

	private transient  Charset encoding = Charset.defaultCharset();

	private int complianceLevel = DEFAULT_CODE_COMPLIANCE_LEVEL;

	private transient  OutputDestinationHandler outputDestinationHandler = new DefaultOutputDestinationHandler(new File(Launcher.OUTPUTDIR), this);

	private OutputType outputType = OutputType.CLASSES;

	private Boolean noclasspath = null;

	private transient SpoonProgress spoonProgress = null;

	private CompressionType compressionType = CompressionType.GZIP;

	/**
	 * Creates a new environment with a <code>null</code> default file
	 * generator.
	 */
	public StandardEnvironment() {
	}

	@Override
	public void debugMessage(String message) {
		logger.debug(message);
	}

	@Override
	public boolean isAutoImports() {
		return autoImports;
	}

	@Override
	public void setAutoImports(boolean autoImports) {
		this.autoImports = autoImports;
		// TODO: unexpected behaviour could occur, if we reset the autoimport AFTER the pretty printer is created...
	}

	@Override
	public FileGenerator<? extends CtElement> getDefaultFileGenerator() {
		return defaultFileGenerator;
	}

	@Override
	public Level getLevel() {
		return this.level;
	}

	@Override
	public void setLevel(String level) {
		this.level = toLevel(level);
		logger.setLevel(this.level);
	}

	@Override
	public boolean shouldCompile() {
		return shouldCompile;
	}

	@Override
	public void setShouldCompile(boolean shouldCompile) {
		this.shouldCompile = shouldCompile;
	}

	@Override
	public boolean checksAreSkipped() {
		return skipSelfChecks;
	}

	@Override
	public void setSelfChecks(boolean skip) {
		skipSelfChecks = skip;
	}

	@Override
	public void disableConsistencyChecks() {
		skipSelfChecks = true;
	}

	private Level toLevel(String level) {
		if (level == null || level.isEmpty()) {
			throw new SpoonException("Wrong level given at Spoon.");
		}
		return Level.toLevel(level, Level.ALL);
	}

	@Override
	public ProcessingManager getManager() {
		return manager;
	}

	transient Map<String, ProcessorProperties> processorProperties = new TreeMap<>();

	@Override
	public ProcessorProperties getProcessorProperties(String processorName) throws Exception {
		if (processorProperties.containsKey(processorName)) {
			return processorProperties.get(processorName);
		}
		return null;
	}

	/**
	 * Tells if the processing is stopped, generally because one of the
	 * processors called {@link #setProcessingStopped(boolean)} after reporting
	 * an error.
	 */
	@Override
	public boolean isProcessingStopped() {
		return processingStopped;
	}

	private void prefix(StringBuffer buffer, Level level) {
		if (level == Level.ERROR) {
			buffer.append("error: ");
			errorCount++;
		} else if (level == Level.WARN) {
			buffer.append("warning: ");
			warningCount++;
		}
	}

	@Override
	public void report(Processor<?> processor, Level level, CtElement element, String message) {
		StringBuffer buffer = new StringBuffer();

		prefix(buffer, level);

		// Adding message
		buffer.append(message);

		// Add sourceposition (javac format)
		try {
			CtType<?> type = (element instanceof CtType) ? (CtType<?>) element : element.getParent(CtType.class);
			SourcePosition sp = element.getPosition();

			if (sp == null) {
				buffer.append(" (Unknown Source)");
			} else {
				buffer.append(" at " + type.getQualifiedName() + ".");
				CtExecutable<?> exe = (element instanceof CtExecutable) ? (CtExecutable<?>) element : element.getParent(CtExecutable.class);
				if (exe != null) {
					buffer.append(exe.getSimpleName());
				}
				buffer.append("(" + sp.getFile().getName() + ":" + sp.getLine() + ")");
			}
		} catch (ParentNotInitializedException e) {
			buffer.append(" (invalid parent)");
		}

		print(buffer.toString(), level);
	}

	@Override
	public void report(Processor<?> processor, Level level, CtElement element, String message, ProblemFixer<?>... fixes) {
		report(processor, level, element, message);
	}

	@Override
	public void report(Processor<?> processor, Level level, String message) {
		StringBuffer buffer = new StringBuffer();

		prefix(buffer, level);
		// Adding message
		buffer.append(message);
		print(buffer.toString(), level);
	}

	private void print(String message, Level level) {
		if (level.equals(Level.ERROR)) {
			logger.error(message);
		} else if (level.equals(Level.WARN)) {
			logger.warn(message);
		} else if (level.equals(Level.DEBUG)) {
			logger.debug(message);
		} else if (level.equals(Level.INFO)) {
			logger.info(message);
		}
	}

	/**
	 * This method should be called to report the end of the processing.
	 */
	public void reportEnd() {
		logger.info("end of processing: ");
		if (warningCount > 0) {
			logger.info(warningCount + " warning");
			if (warningCount > 1) {
				logger.info("s");
			}
			if (errorCount > 0) {
				logger.info(", ");
			}
		}
		if (errorCount > 0) {
			logger.info(errorCount + " error");
			if (errorCount > 1) {
				logger.info("s");
			}
		}
		if ((errorCount + warningCount) > 0) {
			logger.info("\n");
		} else {
			logger.info("no errors, no warnings");
		}
	}

	public void reportProgressMessage(String message) {
		logger.info(message);
	}

	public void setDebug(boolean debug) {
	}

	public void setDefaultFileGenerator(FileGenerator<? extends CtElement> defaultFileGenerator) {
		this.defaultFileGenerator = defaultFileGenerator;
	}

	public void setManager(ProcessingManager manager) {
		this.manager = manager;
	}

	public void setProcessingStopped(boolean processingStopped) {
		this.processingStopped = processingStopped;
	}

	public void setVerbose(boolean verbose) {
	}



	public int getComplianceLevel() {
		return complianceLevel;
	}

	public void setComplianceLevel(int level) {
		complianceLevel = level;
	}

	public void setProcessorProperties(String processorName, ProcessorProperties prop) {
		processorProperties.put(processorName, prop);
	}

	boolean useTabulations = false;

	public boolean isUsingTabulations() {
		return useTabulations;
	}

	public void useTabulations(boolean tabulation) {
		useTabulations = tabulation;
	}

	int tabulationSize = 4;

	public int getTabulationSize() {
		return tabulationSize;
	}

	public void setTabulationSize(int tabulationSize) {
		this.tabulationSize = tabulationSize;
	}

	private transient  ClassLoader classloader;
	/*
	 * cache class loader which loads classes from source class path
	 * we must cache it to make all the loaded classes compatible
	 * The cache is reset when setSourceClasspath(...) is called
	 */
private transient  ClassLoader inputClassloader;

	@Override
	public void setInputClassLoader(ClassLoader aClassLoader) {
		if (aClassLoader instanceof URLClassLoader) {
			final URL[] urls = ((URLClassLoader) aClassLoader).getURLs();
			if (urls != null && urls.length > 0) {
				// Check that the URLs are only file URLs
				boolean onlyFileURLs = true;
				for (URL url : urls) {
					if (!url.getProtocol().equals("file")) {
						onlyFileURLs = false;
					}
				}
				if (onlyFileURLs) {
					List<String> classpath = new ArrayList<>();
					for (URL url : urls) {
						classpath.add(url.getPath());
					}
					setSourceClasspath(classpath.toArray(new String[0]));
				} else {
					throw new SpoonException("Spoon does not support a URLClassLoader containing other resources than local file.");
				}
			}
			return;
		}
		this.classloader = aClassLoader;
	}

	@Override
	public ClassLoader getInputClassLoader() {
		if (classloader != null) {
			return classloader;
		}
		if (inputClassloader == null) {
			inputClassloader = new URLClassLoader(urlClasspath(), Thread.currentThread().getContextClassLoader());
		}
		return inputClassloader;
	}

	/**
	 * Creates a URL class path from {@link Environment#getSourceClasspath()}
	 */
	public URL[] urlClasspath() {
		String[] classpath = getSourceClasspath();
		int length = (classpath == null) ? 0 : classpath.length;
		URL[] urls = new URL[length];
		for (int i = 0; i < length; i += 1) {
			try {
				urls[i] = new File(classpath[i]).toURI().toURL();
			} catch (MalformedURLException e) {
				throw new IllegalStateException("Invalid classpath: " + Arrays.toString(classpath), e);
			}
		}
		return urls;
	}

	@Override
	public String[] getSourceClasspath() {
		return sourceClasspath;
	}

	@Override
	public void setSourceClasspath(String[] sourceClasspath) {
		verifySourceClasspath(sourceClasspath);
		this.sourceClasspath = sourceClasspath;
		this.inputClassloader = null;
	}

	private void verifySourceClasspath(String[] sourceClasspath) throws InvalidClassPathException {
		for (String classPathElem : sourceClasspath) {
			// preconditions
			File classOrJarFolder = new File(classPathElem);
			if (!classOrJarFolder.exists()) {
				throw new InvalidClassPathException(classPathElem + " does not exist, it is not a valid folder");
			}

			if (classOrJarFolder.isDirectory()) {
				// it should not contain a java file
				SpoonFolder tmp = new FileSystemFolder(classOrJarFolder);
				List<SpoonFile> javaFiles = tmp.getAllJavaFiles();
				if (javaFiles.size() > 0) {
					logger.warn("You're trying to give source code in the classpath, this should be given to " + "addInputSource " + javaFiles);
				}
				logger.warn("You specified the directory " + classOrJarFolder.getPath() + " in source classpath, please note that only class files will be considered. Jars and subdirectories will be ignored.");
			} else if (classOrJarFolder.getName().endsWith(".class")) {
				throw new InvalidClassPathException(".class files are not accepted in source classpath.");
			}
		}
	}

	@Override
	public int getErrorCount() {
		return errorCount;
	}

	@Override
	public int getWarningCount() {
		return warningCount;
	}

	@Override
	public boolean isPreserveLineNumbers() {
		return preserveLineNumbers;
	}

	@Override
	public void setPreserveLineNumbers(boolean preserveLineNumbers) {
		this.preserveLineNumbers = preserveLineNumbers;
	}

	@Override
	public void setNoClasspath(boolean option) {
		noclasspath = option;
	}

	@Override
	public boolean getNoClasspath() {
		if (this.noclasspath == null) {
			logger.warn("Spoon is currently use with the default noClasspath option set as true. Read the documentation for more information: http://spoon.gforge.inria.fr/launcher.html#about-the-classpath");
			this.noclasspath = true;
		}
		return noclasspath;
	}

	@Override
	public boolean isCopyResources() {
		return copyResources;
	}

	@Override
	public void setCopyResources(boolean copyResources) {
		this.copyResources = copyResources;
	}

	@Override
	public boolean isCommentsEnabled() {
		return enableComments;
	}

	@Override
	public void setCommentEnabled(boolean commentEnabled) {
		this.enableComments = commentEnabled;
	}

	private String binaryOutputDirectory = Launcher.SPOONED_CLASSES;

	@Override
	public void setBinaryOutputDirectory(String s) {
		this.binaryOutputDirectory = s;

	}

	@Override
	public String getBinaryOutputDirectory() {
		return binaryOutputDirectory;
	}

	@Override
	public void setSourceOutputDirectory(File directory) {
		if (directory == null) {
			throw new SpoonException("You must specify a directory.");
		}
		if (directory.isFile()) {
			throw new SpoonException("Output must be a directory");
		}

		try {
			this.outputDestinationHandler = new DefaultOutputDestinationHandler(directory.getCanonicalFile(),
					this);
		} catch (IOException e) {
			Launcher.LOGGER.error(e.getMessage(), e);
			throw new SpoonException(e);
		}
	}

	@Override
	public File getSourceOutputDirectory() {
		return this.outputDestinationHandler.getDefaultOutputDirectory();
	}

	@Override
	public void setOutputDestinationHandler(OutputDestinationHandler outputDestinationHandler) {
		this.outputDestinationHandler = outputDestinationHandler;
	}

	@Override
	public OutputDestinationHandler getOutputDestinationHandler() {
		return outputDestinationHandler;
	}

	@Override
	public FineModelChangeListener getModelChangeListener() {
		return modelChangeListener;
	}

	@Override
	public void setModelChangeListener(FineModelChangeListener modelChangeListener) {
		this.modelChangeListener = modelChangeListener;
	}

	@Override
	public Charset getEncoding() {
		return this.encoding;
	}

	@Override
	public void setEncoding(Charset encoding) {
		this.encoding = encoding;
	}

	@Override
	public void setOutputType(OutputType outputType) {
		this.outputType = outputType;
	}

	@Override
	public OutputType getOutputType() {
		return this.outputType;
	}

	@Override
	public SpoonProgress getSpoonProgress() {
		return this.spoonProgress;
	}

	@Override
	public void setSpoonProgress(SpoonProgress spoonProgress) {
		this.spoonProgress = spoonProgress;
	}

	@Override
	public CompressionType getCompressionType() {
		return compressionType;
	}

	@Override
	public void setCompressionType(CompressionType serializationType) {
		this.compressionType = serializationType;
	}
}
