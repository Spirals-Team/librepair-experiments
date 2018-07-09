
package patterns.bridge;

/**
 * Abstraction Class.
 */
public abstract class Abstraction {

    /** The implementor. */
    private AbstractImplementor implementor = null;

    /**
     * Instantiates a new abstraction.
     *
     * implementor
     *
     * @param implementor
     *            the implementor
     */
    public Abstraction(final AbstractImplementor implementor) {
        super();
        this.implementor = implementor;
    }

    /**
     * Operation.
     */
    public void operation() {
        implementor.operation();
    }

}
