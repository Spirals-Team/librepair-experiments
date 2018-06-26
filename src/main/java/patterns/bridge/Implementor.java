
package patterns.bridge;

/**
 * Implementor Class.
 */
public class Implementor extends AbstractImplementor {

    /*
     * (non-Javadoc)
     *
     * @see patterns.bridge.AbstractImplementor#operation()
     */
    @Override
    public void operation() {
        this.log.info("{}", this.getClass().getSimpleName());
    }

}
