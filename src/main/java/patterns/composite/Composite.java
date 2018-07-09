
package patterns.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite Class.
 */
public final class Composite extends AbstractComponent {

    /** The leaves. */
    private final List<AbstractComponent> leaves = new ArrayList<>();

    /*
     * (non-Javadoc)
     *
     * @see patterns.composite.Component#add(patterns.composite.Component)
     */
    @Override
    public ComponentInterface add(final AbstractComponent component) {
        leaves.add(component);
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.composite.Component#remove(patterns.composite.Component)
     */
    @Override
    public ComponentInterface remove(final AbstractComponent component) {
        leaves.remove(component);
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.composite.Component#getChild(int)
     */
    @Override
    public ComponentInterface getChild(final int index) {
        leaves.get(index);
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.composite.Component#operation()
     */
    @Override
    public ComponentInterface operation() {
        for (final AbstractComponent component : leaves) {
            component.operation();
        }
        return this;
    }

}
