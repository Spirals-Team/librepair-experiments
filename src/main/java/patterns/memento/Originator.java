
package patterns.memento;

/**
 * Originator Class.
 */
public class Originator {

    /** The state. */
    private Object state;

    /** The memento. */
    private Memento memento;

    public Originator(final Object state) {
        this.state = state;
        this.memento = new Memento();
    }

    /**
     * Instantiates a new originator.
     *
     * @param state
     *            the state
     * @param memento
     *            the memento
     */
    public Originator(final Object state, final Memento memento) {
        super();
        this.state = state;
        this.memento = memento;
    }

    /**
     * memento.
     *
     * @return the memento
     */
    public Memento createMemento() {
        this.memento = new Memento(this.state);
        return this.memento;
    }

    /**
     * memento factory method
     *
     * @param state the state
     * @return the memento
     */
    public Memento createMemento(final Object state) {
        this.memento = new Memento(state);
        return this.memento;
    }

    /**
     * Revert.
     */
    public void revert() {
        this.state = getMemento();
    }

    /**
     * memento.
     *
     * @param memento
     *            the new memento
     */
    public void setMemento(final Memento memento) {
        this.memento = memento;
    }

    /**
     * memento.
     *
     * @return the memento
     */
    public Memento getMemento() {
        return this.memento;
    }

}
