
package patterns.strategy;

import java.util.Properties;

import coaching.context.AbstractContext;

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
