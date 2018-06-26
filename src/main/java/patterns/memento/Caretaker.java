
package patterns.memento;

/**
 * Caretaker Class.
 */
class Caretaker {

    /** The memento. */
    private Memento memento;

    /**
     * memento.
     *
     * @return the memento
     */
    public Memento getMemento() {
        return this.memento;
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

}
