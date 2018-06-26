
package patterns.bridge;

/**
 * RefinedAbstraction Class.
 */
public class RefinedAbstraction extends Abstraction {

    /**
     * Instantiates a new refined abstraction.
     *
     * @param implementor
     *            the implementor
     */
    public RefinedAbstraction(final AbstractImplementor implementor) {
        super(implementor);
    }

}
