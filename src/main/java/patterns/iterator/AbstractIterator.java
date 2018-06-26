
package patterns.iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

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
        this.index = 0;
        return this.itemList.get(this.index);
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.iterator.IteratorInterface#next()
     */
    @Override
    public ItemInterface next() {
        this.index++;
        return this.itemList.get(this.index);
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.iterator.IteratorInterface#isDone()
     */
    @Override
    public boolean isDone() {
        return this.index == this.itemList.size();
    }

    /*
     * (non-Javadoc)
     *
     * @see patterns.iterator.IteratorInterface#currentItem()
     */
    @Override
    public ItemInterface currentItem() {
        return this.itemList.get(this.index);
    }

}
