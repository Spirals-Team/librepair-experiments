
package patterns.visitor;

/**
 * Element Interface.
 */
public interface ElementInterface {

    /**
     * Accept visitor.
     *
     * @param visitor
     *            the visitor
     */
    void accept(final VisitorInterface visitor);

}
