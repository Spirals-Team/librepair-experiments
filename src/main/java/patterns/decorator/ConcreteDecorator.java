
package patterns.decorator;

/**
 * An example concrete decorator class.
 */
public class ConcreteDecorator extends AbstractComponent {

    @Override
    public AbstractComponent operation() {
        final String simpleName = this.getClass().getSimpleName();
        this.log.info(simpleName.toString());
        return this;
    }

}
