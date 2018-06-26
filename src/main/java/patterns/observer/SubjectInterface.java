
package patterns.observer;

/**
 * Subject Interface.
 */
public interface SubjectInterface {

    /**
     * Attach observer.
     *
     * @param observer
     *            the observer
     */
    void attachObserver(final AbstractObserver observer);

    /**
     * Detach observer.
     *
     * @param observer
     *            the observer
     */
    void detachObserver(final ObserverInterface observer);

    /**
     * Update observers.
     */
    void updateObservers();

}
