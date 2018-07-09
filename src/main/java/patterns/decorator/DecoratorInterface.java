
package patterns.decorator;

/**
 * Decorator Interface.
 */
public interface DecoratorInterface {

    /**
     * Attach before behaviour.
     *
     * @param behaviour
     *            the behaviour
     */
    DecoratorInterface attachBefore(final AbstractComponent behaviour);

    /**
     * Detach before behaviour.
     *
     * @param behaviour
     *            the behaviour
     */
    DecoratorInterface detachBefore(final AbstractComponent behaviour);

    /**
     * Attach after behaviour.
     *
     * @param behaviour
     *            the behaviour
     */
    DecoratorInterface attachAfter(final AbstractComponent behaviour);

    /**
     * Detach after behaviour.
     *
     * @param behaviour
     *            the behaviour
     */
    DecoratorInterface detachAfter(final AbstractComponent behaviour);

}
