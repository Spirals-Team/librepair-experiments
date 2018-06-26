
package coaching.associations;

/**
 * Using a basic Java array to implement a UML Composite association.
 *
 * In a Composite the Lifetime of parts is time constrained by lifetime of
 * parent.
 **/
public final class Composite {

    /** The element alice. */
    private Alice elementAlice = new Alice();

    /** The element bob. */
    private Bob elementBob = new Bob();

    /** The element charlie. */
    private Charlie elementCharlie = new Charlie();

    /** The element dan. */
    private Dan elementDan = new Dan();

    /**
     * Instantiates a new composite class array.
     */
    public Composite() {
        super();
        this.elementAlice = new Alice();
        this.elementBob = new Bob();
        this.elementCharlie = new Charlie();
        this.elementDan = new Dan();
    }

    /**
     * Instantiates a new composite.
     *
     * @param elementAlice
     *            the element alice
     * @param elementBob
     *            the element bob
     * @param elementCharlie
     *            the element charlie
     * @param elementDan
     *            the element dan
     */
    public Composite(final Alice elementAlice,
            final Bob elementBob,
            final Charlie elementCharlie,
            final Dan elementDan) {
        super();
        this.elementAlice = elementAlice;
        this.elementBob = elementBob;
        this.elementCharlie = elementCharlie;
        this.elementDan = elementDan;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("Composite [elementAlice=%s, elementBob=%s, elementCharlie=%s, elementDan=%s]",
                this.elementAlice,
                this.elementBob,
                this.elementCharlie,
                this.elementDan);
    }

}
