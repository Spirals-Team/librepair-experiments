
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
        memento = new Memento();
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
        memento = new Memento(state);
        return memento;
    }

    /**
     * memento factory method
     *
     * @param state the state
     * @return the memento
     */
    public Memento createMemento(final Object state) {
        memento = new Memento(state);
        return memento;
    }

    /**
     * Revert.
     */
    public void revert() {
        state = getMemento();
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
        return memento;
    }

}
