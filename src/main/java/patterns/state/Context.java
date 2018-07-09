
package patterns.state;

/**
 * Context Class.
 */
public class Context {

    /** state. */
    private AbstractState state = null;

    /**
     * Instantiates a new context.
     *
     * @param state the state
     */
    public Context(final AbstractState state) {
        super();
        this.state = state;
    }

    public Context toAlice() {
        state = new StateAlice();
        return this;
    }

    public Context toBob() {
        state = new StateBob();
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("Context [state=%s]", state);
    }

}
