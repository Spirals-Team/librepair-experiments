
package patterns.decorator;

/**
 * DecoratedComponent Class.
 */
public class DecoratedComponent extends AbstractDecorator implements DecoratorInterface {

    /*
     * (non-Javadoc)
     *
     * @see patterns.decorator.AbstractComponent#operation()
     */
    @Override
    public AbstractComponent operation() {
        super.beforeOperation();

        log.info("{}.operation", this.getClass().getSimpleName());

        super.afterOperation();

        return this;
    }

}
