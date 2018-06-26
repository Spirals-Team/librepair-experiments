
package patterns.visitor;

/**
 * Element Class.
 */
public final class Element extends AbstractElement {

    /*
     * (non-Javadoc)
     *
     * @see patterns.visitor.AbstractElement#accept(patterns.visitor.
     * VisitorInterface)
     */
    @Override
    public void accept(final VisitorInterface visitor) {
        this.log.info("{}.accept", this.getClass().getSimpleName());
    }

}
