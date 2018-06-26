
package patterns.composite;

/**
 * Leaf Class.
 */
public final class Leaf extends AbstractComponent {

    /*
     * (non-Javadoc)
     *
     * @see patterns.composite.Component#operation()
     */
    @Override
    public ComponentInterface operation() {
        return this;
    }

}
