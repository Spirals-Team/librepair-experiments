
package patterns.factory;

/**
 * Factory Interface.
 */
public interface FactoryInterface {

    /**
     * product A.
     *
     * abstract product A
     *
     * @return the abstract product A
     */
    AbstractProductAlpha createProductA();

    /**
     * product B.
     *
     * abstract product B
     *
     * @return the abstract product B
     */
    AbstractProductBeta createProductB();

}
