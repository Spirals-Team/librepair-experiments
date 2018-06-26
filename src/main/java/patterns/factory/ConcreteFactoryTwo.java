
package patterns.factory;

/**
 * ConcreteFactoryTwo Class.
 */
class ConcreteFactoryTwo extends AbstractFactory {

    /*
     * (non-Javadoc)
     *
     * @see patterns.factory.AbstractFactory#createProductA()
     */
    @Override
    public AbstractProductAlpha createProductA() {
        return new ConcreteProductAlphaTwo();
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.factory.AbstractFactory#createProductB()
     */
    @Override
    public AbstractProductBeta createProductB() {
        return new ConcreteProductBetaTwo();
    }

}
