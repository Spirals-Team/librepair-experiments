
package patterns.visitor;

/**
 * Visitor Class.
 */
public final class Visitor extends AbstractVisitor {

    /*
     * (non-Javadoc)
     *
     * @see
     * patterns.visitor.AbstractVisitor#visit(patterns.visitor.ElementInterface)
     */
    @Override
    public void visit(final ElementInterface element) {
        throw new UnsupportedOperationException();
    }

}
