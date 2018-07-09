
package patterns.command;

import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Abstract class for Parameters to Commands.
 */
public abstract class AbstractCommandParameters implements ParametersInterface {

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /** The parameters. */
    private final Properties parameters = new Properties();

    /**
     * Instantiates a new abstract parameters.
     */
    public AbstractCommandParameters() {
        super();
    }

    /**
     * Instantiates a new abstract parameters.
     *
     * @param key
     *            the key
     * @param value
     *            the value
     */
    public AbstractCommandParameters(final String key, final String value) {
        setParameter(key, value);
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.command.ParametersInterface#setParameter(java.lang.String,
     * java.lang.String)
     */
    @Override
    public AbstractCommandParameters setParameter(final String key, final String value) {
        parameters.setProperty(key, value);
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.command.ParametersInterface#stringPropertyNames()
     */
    @Override
    public Set<String> stringPropertyNames() {
        return parameters.stringPropertyNames();
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.command.ParametersInterface#valueFor(java.lang.String)
     */
    @Override
    public String valueFor(final String key) {
        return parameters.getProperty(key);
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.command.ParametersInterface#valueFor(java.lang.String,
     * java.lang.String)
     */
    @Override
    public String valueFor(final String key, final String defaultValue) {
        return parameters.getProperty(key, defaultValue);
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.command.ParametersInterface#valueFor(java.lang.String,
     * java.lang.Boolean)
     */
    @Override
    public Boolean valueFor(final String key, final Boolean defaultValue) {
        final String property = parameters.getProperty(key, defaultValue.toString());
        return Boolean.parseBoolean(property);
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.command.ParametersInterface#valueFor(java.lang.String,
     * java.lang.Long)
     */
    @Override
    public Long valueFor(final String key, final Long defaultValue) {
        final String property = parameters.getProperty(key, defaultValue.toString());
        return Long.parseLong(property);
    }
}
