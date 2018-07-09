
package patterns.iterator;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AbstractIterator Class.
 */
public abstract class AbstractIterator implements IteratorInterface {

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /** The aggregate. */
    protected AggregateInterface aggregate = null;

    /** The item list. */
    protected final List<ItemInterface> itemList = new ArrayList<>();

    /** The index. */
    protected int index = 0;

    /**
     * Instantiates a new iterator.
     *
     * @param aggregate
     *            the aggregate
     */
    public AbstractIterator(final AggregateInterface aggregate) {
        super();
        this.aggregate = aggregate;
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.iterator.IteratorInterface#first()
     */
    @Override
    public ItemInterface first() {
        index = 0;
        return itemList.get(index);
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.iterator.IteratorInterface#next()
     */
    @Override
    public ItemInterface next() {
        index++;
        return itemList.get(index);
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.iterator.IteratorInterface#isDone()
     */
    @Override
    public boolean isDone() {
        return index == itemList.size();
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.iterator.IteratorInterface#currentItem()
     */
    @Override
    public ItemInterface currentItem() {
        return itemList.get(index);
    }

}
