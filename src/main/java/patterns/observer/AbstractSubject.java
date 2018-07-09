
package patterns.observer;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AbstractSubject Class.
 */
public abstract class AbstractSubject implements SubjectInterface {

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /** The observers. */
    private final List<AbstractObserver> observers = new ArrayList<>();

    /*
     * (non-Javadoc)
     *
     * @see patterns.observer.SubjectInterface#attachObserver(patterns.observer.
     * AbstractObserver)
     */
    @Override
    public void attachObserver(final AbstractObserver observer) {
        observers.add(observer);
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.observer.SubjectInterface#detachObserver(patterns.observer.
     * ObserverInterface)
     */
    @Override
    public void detachObserver(final ObserverInterface observer) {
        observers.remove(observer);
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.observer.SubjectInterface#updateObservers()
     */
    @Override
    public void updateObservers() {
        for (final ObserverInterface observer : observers) {
            observer.updateObservers();
        }
    }
}
