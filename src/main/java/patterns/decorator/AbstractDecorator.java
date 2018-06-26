
package patterns.decorator;

import java.util.ArrayList;
import java.util.List;

/**
 * AbstractDecorator Class.
 */
public abstract class AbstractDecorator extends AbstractComponent implements DecoratorInterface {

    /** The before behaviour. */
    private final List<AbstractComponent> beforeBehaviour = new ArrayList<>();

    /** The after behaviour. */
    private final List<AbstractComponent> afterBehaviour = new ArrayList<>();

    /*
     * (non-Javadoc)
     *
     * @see
     * patterns.decorator.DecoratorInterface#attachBefore(patterns.decorator.
     * AbstractComponent)
     */
    @Override
    public DecoratorInterface attachBefore(final AbstractComponent behaviour) {
        this.beforeBehaviour.add(behaviour);
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * patterns.decorator.DecoratorInterface#detachBefore(patterns.decorator.
     * AbstractComponent)
     */
    @Override
    public DecoratorInterface detachBefore(final AbstractComponent behaviour) {
        this.beforeBehaviour.remove(behaviour);
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * patterns.decorator.DecoratorInterface#attachAfter(patterns.decorator.
     * AbstractComponent)
     */
    @Override
    public DecoratorInterface attachAfter(final AbstractComponent behaviour) {
        this.afterBehaviour.add(behaviour);
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * patterns.decorator.DecoratorInterface#detachAfter(patterns.decorator.
     * AbstractComponent)
     */
    @Override
    public DecoratorInterface detachAfter(final AbstractComponent behaviour) {
        this.afterBehaviour.remove(behaviour);
        return this;
    }

    /**
     * Before operation.
     * @return 
     */
    protected DecoratorInterface beforeOperation() {
        for (final ComponentInterface component : this.beforeBehaviour) {
            component.operation();
        }
        return this;
    }

    /**
     * After operation.
     * @return 
     */
    protected DecoratorInterface afterOperation() {
        for (final ComponentInterface component : this.afterBehaviour) {
            component.operation();
        }
        return this;
    }
}
