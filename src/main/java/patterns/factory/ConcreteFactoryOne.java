
package patterns.factory;

/**
 * ConcreteFactoryOne Class.
 */
class ConcreteFactoryOne extends AbstractFactory {

    /*
     * (non-Javadoc)
     *
     * @see patterns.factory.AbstractFactory#createProductA()
     */
    @Override
    public AbstractProductAlpha createProductA() {
        return new ConcreteProductAlphaOne();
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.factory.AbstractFactory#createProductB()
     */
    @Override
    public AbstractProductBeta createProductB() {
        return new ConcreteProductBetaOne();
    }

}
