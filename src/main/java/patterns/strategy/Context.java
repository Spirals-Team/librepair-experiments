
package patterns.strategy;

import coaching.context.AbstractContext;

import java.util.Properties;

/**
 * Context Class.
 */
public final class Context extends AbstractContext {

    /**
     * Instantiates a new context.
     */
    public Context() {
        super(new Properties());
    }

    /**
     * Instantiates a new context.
     *
     * @param properties
     *            the properties
     */
    public Context(final Properties properties) {
        super(properties);
    }
}
