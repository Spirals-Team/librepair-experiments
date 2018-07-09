
package patterns.iterator;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * class AbstractAggregate.
 */
public abstract class AbstractAggregate implements AggregateInterface {

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /** The item list. */
    protected final List<ItemInterface> itemList = new ArrayList<>();

    /*
     * (non-Javadoc)
     *
     * @see patterns.iterator.AggregateInterface#createIterator()
     */
    @Override
    public IteratorInterface createIterator() {
        log.info("%s.createIterator()", this.getClass().getSimpleName());
        return new Iterator(this);
    }

}
