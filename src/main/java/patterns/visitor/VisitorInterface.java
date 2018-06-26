
package patterns.visitor;

/**
 * Visitor Interface.
 */
public interface VisitorInterface {

    /**
     * Visit element.
     *
     * @param element
     *            the element
     */
    void visit(final ElementInterface element);

}
