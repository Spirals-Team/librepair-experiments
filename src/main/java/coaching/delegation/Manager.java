
package coaching.delegation;

/**
 * Manager Class with delegation.
 */
public class Manager implements ProcessInterface {

    /** The worker. */
    private Worker worker;

    /**
     * Instantiates a new manager, with a new Worker instance.
     */
    public Manager() {
        super();
        worker = new Worker();
    }

    /**
     * Instantiates a new manager with a passed Worker instance.
     *
     * worker
     *
     * @param worker
     *            the worker
     */
    public Manager(final Worker worker) {
        super();
        setWorker(worker);
    }

    /**
     * configure the worker to receive delegation.
     *
     * worker manager
     *
     * @param worker
     *            the worker
     * @return the manager
     */
    public Manager setWorker(final Worker worker) {
        this.worker = worker;
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see delegation.ProcessInterface#doProcess()
     */
    @Override
    public Manager doProcess() {
        worker.doProcess();
        return this;
    }

}
