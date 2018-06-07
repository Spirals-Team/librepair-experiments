/**
 * This file is part of Automated Testing Framework for Java (atf4j).
 *
 * Atf4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Atf4j is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with atf4j.  If not, see http://www.gnu.org/licenses/.
 */

package net.atf4j.pog;

import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import net.atf4j.core.TestResultsReporting;
import net.atf4j.core.TimeStamp;
import net.atf4j.core.VerificationError;

/**
 * Abstract Code Generator class.
 */
public abstract class AbstractJavaClassGenerator extends TestResultsReporting {

    /** Default template folder. */
    private static final String DEFAULT_CLASS_TEMPLATE = "templates/Class.vm";

    /** Default package name-space. */
    private static final String DEFAULT_PACKAGE_NAME = "net.atf4j.generated";

    /** Default class name. */
    private static final String DEFAULT_CLASS_NAME = "ExampleClass";

    /** Default target folder. */
    private static final String DEFAULT_TARGET_FOLDER = "src/generated/java";

    /** velocity engine. */
    private final VelocityEngine velocityEngine = new VelocityEngine();

    /** velocity context. */
    private final VelocityContext context = new VelocityContext();

    /** fields. */
    protected final List<ClassField> fields = new ArrayList<ClassField>();

    /** methods. */
    protected final List<ClassMethod> methods = new ArrayList<ClassMethod>();

    /** template filename. */
    private String templateFilename = DEFAULT_CLASS_TEMPLATE;

    /** package name. */
    private String packageName = DEFAULT_PACKAGE_NAME;

    /** class name. */
    private String className = DEFAULT_CLASS_NAME;

    /** target home folder. */
    private String targetHomeFolder = DEFAULT_TARGET_FOLDER;

    /**
     * Instantiates a new code generator.
     */
    protected AbstractJavaClassGenerator() {
        super();
    }

    /**
     * Instantiates a new code generator.
     *
     * @param templateFilename the template filename
     * @throws TemplateNotLoadedException the template not loaded
     */
    protected AbstractJavaClassGenerator(final String templateFilename) throws TemplateNotLoadedException {
        super();
        setTemplateFilename(templateFilename);
    }

    /**
     * Adds the boolean field.
     *
     * @param fieldName the field name
     * @return the abstract java class generator
     */
    public AbstractJavaClassGenerator addBooleanField(final String fieldName) {
        addField(ClassField.makeBoolean(fieldName));
        return this;
    }

    /**
     * Adds the date field.
     *
     * @param fieldName the field name
     * @return the abstract java code generator
     */
    public AbstractJavaClassGenerator addDateField(final String fieldName) {
        addField(ClassField.makeDate(fieldName));
        return this;
    }

    /**
     * Adds the object field.
     *
     * @param fieldName the field name
     * @return the abstract java class generator
     */
    public AbstractJavaClassGenerator addObjectField(final String fieldName) {
        addField(ClassField.makeObject(fieldName));
        return this;
    }

    /**
     * Adds the string field.
     *
     * @param fieldName the field name
     * @return the abstract java class generator
     */
    public AbstractJavaClassGenerator addStringField(final String fieldName) {
        addField(ClassField.makeString(fieldName));
        return this;
    }

    /**
     * Adds the field.
     *
     * @param fieldType the field type
     * @param fieldName the field name
     * @return the code generator
     */
    public AbstractJavaClassGenerator addField(final FieldType fieldType, final String fieldName) {
        final ClassField classField = new ClassField(fieldType, fieldName);
        this.fields.add(classField);
        return this;
    }

    /**
     * Adds the field.
     *
     * @param fieldType the field type
     * @param fieldName the field name
     * @return the code generator
     */
    public AbstractJavaClassGenerator addField(final String fieldType, final String fieldName) {
        final ClassField classField = new ClassField(fieldType, fieldName);
        this.fields.add(classField);
        return this;
    }

    /**
     * Context binding, make the objects available to velocity by key.
     *
     * @param key the key
     * @param value the value
     * @return this for a fluent interface.
     */
    public AbstractJavaClassGenerator contextBinding(final String key, final Object value) {
        assumeNotNull(key);
        assumeTrue(key.length() > 0);
        assumeNotNull(value);
        this.context.put(key, value);
        return this;
    }

    /**
     * Sets the template.
     *
     * @param templateFilename the template filename
     * @return this for a fluent interface.
     * @throws TemplateNotLoadedException the template not loaded
     */
    public AbstractJavaClassGenerator setTemplateFilename(final String templateFilename)
            throws TemplateNotLoadedException {
        this.templateFilename = templateFilename;
        return this;
    }

    /**
     * Sets the package name.
     *
     * @param packageName the package name
     * @return this for a fluent interface.
     */
    public AbstractJavaClassGenerator setPackageName(final String packageName) {
        this.packageName = packageName;
        return this;
    }

    /**
     * Sets the class name.
     *
     * @param className the class name
     * @return this for a fluent interface.
     */
    public AbstractJavaClassGenerator setClassName(final String className) {
        this.className = classCase(className);
        return this;
    }

    /**
     * Sets the target home folder.
     *
     * @param targetHomeFolder the target home folder
     * @return the abstract code generator
     */
    public AbstractJavaClassGenerator setTargetHomeFolder(final String targetHomeFolder) {
        this.targetHomeFolder = targetHomeFolder;
        return this;
    }

    /**
     * Template filename.
     *
     * @return the string
     */
    public String getTemplateFilename() {
        return this.templateFilename;
    }

    /**
     * Package name.
     *
     * @return the string
     */
    public String getPackageName() {
        return this.packageName;
    }

    /**
     * Class name.
     *
     * @return the string
     */
    public String className() {
        return this.className;
    }

    /**
     * Class case.
     *
     * @param candidateClassName the candidate class name
     * @return the string
     */
    public String classCase(final String candidateClassName) {
        return firstUpper(candidateClassName);
    }

    /**
     * Method case.
     *
     * @param string the string
     * @return the string
     */
    public String methodCase(final String string) {
        final char[] charArray = string.toCharArray();
        charArray[0] = Character.toLowerCase(charArray[0]);
        return new String(charArray);
    }

    /**
     * First upper.
     *
     * @param string the string
     * @return the string
     */
    public String firstUpper(final String string) {
        final char[] charArray = string.toCharArray();
        charArray[0] = Character.toUpperCase(charArray[0]);
        return new String(charArray);
    }

    /**
     * Adds the field.
     *
     * @param classField the class field
     * @return this for a fluent interface.
     */
    public AbstractJavaClassGenerator addField(final ClassField classField) {
        this.fields.add(classField);
        return this;
    }

    /**
     * Adds the method.
     *
     * @param returnType the return type
     * @param methodName the method name
     * @return this for a fluent interface.
     */
    public AbstractJavaClassGenerator addMethod(final String returnType, final String methodName) {
        final ClassMethod classField = new ClassMethod(returnType, methodName);
        this.methods.add(classField);
        return this;
    }

    /**
     * Adds the method.
     *
     * @param classMethod the class method
     * @return this for a fluent interface.
     */
    public AbstractJavaClassGenerator addMethod(final ClassMethod classMethod) {
        this.methods.add(classMethod);
        return this;
    }

    /**
     * Generate.
     *
     * @return this for a fluent interface.
     * @throws VerificationError the Atf4jException
     */
    public AbstractJavaClassGenerator generate() throws VerificationError {
        assumeNotNull(this.templateFilename);
        assumeTrue(this.templateFilename.length() > 0);
        return generate(this.templateFilename);
    }

    /**
     * Generate from template.
     *
     * @param templateFilename the template filename
     * @return this for a fluent interface.
     * @throws VerificationError the Atf4jException
     */
    public AbstractJavaClassGenerator generate(final String templateFilename) throws VerificationError {
        return generate(templateReader(templateFilename));
    }

    /**
     * Generate from input steam reader to output stream.
     *
     * @param templateReader the template reader
     * @return this for a fluent interface.
     * @throws VerificationError the Atf4jException
     */
    private AbstractJavaClassGenerator generate(final InputStreamReader templateReader) throws VerificationError {

        initialise();

        final Writer writer = destinationWriter();
        final String logTag = this.getClass().getSimpleName();
        assertTrue(this.velocityEngine.evaluate(this.context, writer, logTag, templateReader));

        cleanup(writer);

        return this;
    }

    /**
     * Initialise.
     */
    private void initialise() {
        this.velocityEngine.init();
        contextBinding("util", this);
        contextBinding("timeStamp", TimeStamp.getDateTime());
        contextBinding("packageName", this.packageName);
        contextBinding("className", this.className);
        contextBinding("fields", this.fields);
        contextBinding("methods", this.methods);
    }

    /**
     * Destination writer.
     *
     * @return the buffered writer
     * @throws CodeNotGeneratedException the code not generated exception
     *             exception.
     */
    private BufferedWriter destinationWriter() throws CodeNotGeneratedException {
        final String packageFolder = packageFolder(this.packageName);
        final String targetPath = targetPath(this.targetHomeFolder, packageFolder);
        final String targetFile = targetFilename(targetPath, this.className);
        final File file = new File(targetFile);
        try {
            final FileWriter fileWriter = new FileWriter(file);
            return new BufferedWriter(fileWriter);
        } catch (final IOException e) {
            throw new CodeNotGeneratedException(e.toString());
        }
    }

    /**
     * Flush and close the writer.
     *
     * @param writer the writer
     * @throws CodeNotGeneratedException the code not generated exception
     *             exception.
     */
    private void cleanup(final Writer writer) throws CodeNotGeneratedException {
        try {
            writer.flush();
            writer.close();
        } catch (final IOException e) {
            this.log.error(e.toString());
            throw new CodeNotGeneratedException(this.className);
        }
    }

    /**
     * Template reader from template filename.
     *
     * @param templateFilename the template filename
     * @return the input stream reader
     * @throws TemplateNotLoadedException the template not loaded
     */
    private InputStreamReader templateReader(final String templateFilename) throws TemplateNotLoadedException {
        this.log.debug("templateFilename = {}", templateFilename);
        final InputStream resourceAsStream = resourceAsStream(templateFilename);
        if (resourceAsStream == null) {
            throw new TemplateNotLoadedException(templateFilename);
        } else {
            return new InputStreamReader(resourceAsStream);
        }
    }

    /**
     * Resource as stream from resource name.
     *
     * @param resourceFilename the resource filename
     * @return the input stream
     */
    private InputStream resourceAsStream(final String resourceFilename) {
        final ClassLoader classLoader = this.getClass().getClassLoader();
        return classLoader.getResourceAsStream(resourceFilename);
    }

    /**
     * Target filename.
     *
     * @param targetPath the target path
     * @param className the class name
     * @return the string
     */
    private String targetFilename(final String targetPath, final String className) {
        final String targetFile = String.format("%s/%s.java", targetPath, className);
        this.log.debug("generatedFile = {}", targetFile);
        return targetFile;
    }

    /**
     * Target path.
     *
     * @param homeFolder the home folder
     * @param packageFolder the package folder
     * @return the string
     */
    private String targetPath(final String homeFolder, final String packageFolder) {
        verifyNotNull(homeFolder);
        verifyNotNull(packageFolder);
        final String targetPath = String.format("%s/%s", homeFolder, packageFolder);
        this.log.debug("targetPath = {}", targetPath);
        new File(targetPath).mkdirs();
        return targetPath;
    }

    /**
     * Package folder.
     *
     * @param packageName the package name
     * @return the string
     */
    private String packageFolder(final String packageName) {
        final String packageFolder = packageName.replace('.', File.separatorChar);
        this.log.debug("packageFolder = {}", packageFolder);
        return packageFolder;
    }

    /**
     * Debug string.
     *
     * @return the string
     */
    public String debugString() {
        return String.format(
                "%s [templateFilename=%s, packageName=%s, className=%s, fields=%s, methods=%s, targetHomeFolder=%s]",
                this.getClass().getSimpleName(),
                this.templateFilename,
                this.packageName,
                this.className,
                this.fields,
                this.methods,
                this.targetHomeFolder);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return debugString();
    }

    /**
     * Exception to indicate that no Template was not loaded.
     */
    @SuppressWarnings("serial")
    public class TemplateNotLoadedException extends VerificationError {
        /**
         * Instantiates a new template not loaded.
         *
         * @param expectedTemplateFilename the expected template filename
         */
        public TemplateNotLoadedException(final String expectedTemplateFilename) {
            super(String.format("TemplateNotLoaded [expectedTemplateFilename=%s]", expectedTemplateFilename));
        }
    }

    /**
     * Exception to indicate that no Code was Generated.
     */
    @SuppressWarnings("serial")
    public class CodeNotGeneratedException extends VerificationError {

        /**
         * Instantiates a new template not loaded.
         *
         * @param expectedCodeFilename the expected code filename
         */
        public CodeNotGeneratedException(final String expectedCodeFilename) {
            super(String.format("TemplateNotLoaded [expectedCodeFilename=%s]", expectedCodeFilename));
        }
    }

}
