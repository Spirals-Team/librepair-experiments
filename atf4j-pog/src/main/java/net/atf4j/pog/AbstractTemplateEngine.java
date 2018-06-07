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

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import net.atf4j.core.TestResultsReporting;
import net.atf4j.core.VerificationError;

/**
 * Abstract Template Engine Class.
 */
public abstract class AbstractTemplateEngine extends TestResultsReporting {

    /** Default base path. */
    private static final String DEFAULT_PATH = ".";

    /** Default template folder, under the path. */
    private static final String DEFAULT_CLASS_TEMPLATE = "template.vm";

    /** actual base folder. */
    protected String basePath = DEFAULT_PATH;

    /** actual template filename. */
    protected String templateFilename = DEFAULT_CLASS_TEMPLATE;

    /**
     * Instantiates a new abstract template engine.
     */
    public AbstractTemplateEngine() {
        super();
    }

    /**
     * Instantiates a new abstract template engine.
     *
     * @param basePath the base path
     */
    public AbstractTemplateEngine(final String basePath) {
        super();
        setBasePath(basePath);
    }

    /**
     * Instantiates a new abstract template engine.
     *
     * @param basePath the base path
     * @param templateFilename the template filename
     */
    public AbstractTemplateEngine(final String basePath, final String templateFilename) {
        super();
        setBasePath(basePath);
        setTemplateFilename(templateFilename);
    }

    /**
     * Sets the base path.
     *
     * @param basePath the base path
     * @return the abstract template engine
     */
    public AbstractTemplateEngine setBasePath(final String basePath) {
        this.basePath = basePath;
        return this;
    }

    /**
     * Sets the template filename.
     *
     * @param templateFilename the new template filename
     */
    public void setTemplateFilename(final String templateFilename) {
        this.templateFilename = templateFilename;
    }

    /**
     * Execute.
     *
     * @return this for a fluent interface.
     */
    protected AbstractTemplateEngine execute() {
        return prototype(getFullFilename());
    }

    /**
     * Execute.
     *
     * @param templateFilename the template filename
     * @return this for a fluent interface.
     */
    protected AbstractTemplateEngine prototype(final String templateFilename) {
        this.log.info(toCode(templateFilename));
        return this;
    }

    /**
     * To code.
     *
     * @param templateFilename the template filename
     * @return the code as a string.
     */
    protected String toCode(final String templateFilename) {
        this.log.info("templateFilename = {}", templateFilename);

        final VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init();
        velocityEngine.setProperty("file.resource.loader.path", this.basePath);

        final Template template = velocityEngine.getTemplate(templateFilename);
        final VelocityContext context = new VelocityContext();
        context.put("name", this);

        final StringWriter writer = new StringWriter();
        template.merge(context, writer);

        return writer.toString();
    }

    /**
     * Gets the base path.
     *
     * @return the base path
     */
    public String getBasePath() {
        return this.basePath;
    }

    /**
     * Gets the template filename.
     *
     * @return the template filename
     */
    public String getTemplateFilename() {
        return this.templateFilename;
    }

    /**
     * Gets the full filename.
     *
     * @return the full filename
     */
    public String getFullFilename() {
        return String.format("%s%s", getBasePath(), getTemplateFilename());
    }

    /**
     * Debug string.
     *
     * @return the string
     */
    protected String debugString() {
        return String.format("%s [baseFolder=%s, templateFilename=%s]",
                this.getClass().getSimpleName(),
                this.basePath,
                this.templateFilename);
    }

    /**
     * To code.
     *
     * @return the code as a string.
     */
    public String toCode() {
        return toCode(getTemplateFilename());
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        if (this.log.isDebugEnabled()) {
            return debugString();
        } else {
            return toCode();
        }
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
