
package patterns.facade;

/**
 * Concrete Facade Class.
 */
public final class ConcreteFacade implements FacadeInterface {

    /** The sub system one. */
    private final SubSystemOne subSystemOne = new SubSystemOne();

    /** The sub system two. */
    private final SubSystemTwo subSystemTwo = new SubSystemTwo();

    /** The sub system three. */
    private final SubSystemThree subSystemThree = new SubSystemThree();

    /*
     * (non-Javadoc)
     *
     * @see patterns.facade.FacadeInterface#operation()
     */
    @Override
    public void operation() {
        subSystemOneOperation();
        subSystemTwoOperation();
        subSystemThreeOperation();
    }

    /**
     * Sub system one operation.
     */
    public void subSystemOneOperation() {
        subSystemOne.operation();
    }

    /**
     * Sub system two operation.
     */
    public void subSystemTwoOperation() {
        subSystemTwo.operation();
    }

    /**
     * Sub system three operation.
     */
    public void subSystemThreeOperation() {
        subSystemThree.operation();
    }

}
