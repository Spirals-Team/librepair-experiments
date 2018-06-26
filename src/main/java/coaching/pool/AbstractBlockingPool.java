
package coaching.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * BlockingPool Class.
 *
 * generic type
 *
 * @param <E>
 *            the element type
 */
public abstract class AbstractBlockingPool<E> implements PoolInterface<E> {

    /** provides logging. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    /** The free pool. */
    protected LinkedBlockingDeque<E> freePool;

    /** The used pool. */
    protected LinkedBlockingDeque<E> usedPool;

    /**
     * Instantiates a new blocking pool.
     */
    public AbstractBlockingPool() {
        this.freePool = new LinkedBlockingDeque<>();
        this.usedPool = new LinkedBlockingDeque<>();
    }

    /*
     * (non-Javadoc)
     *
     * @see code.pool.PoolInterface#add(java.lang.Object)
     */
    @Override
    public PoolInterface<E> add(final E resource) {
        this.freePool.add(resource);
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see code.pool.PoolInterface#countFree()
     */
    @Override
    public int countFree() {
        return this.freePool.size();
    }

    /*
     * (non-Javadoc)
     *
     * @see code.pool.PoolInterface#countUsed()
     */
    @Override
    public int countUsed() {
        return this.usedPool.size();
    }

    /*
     * (non-Javadoc)
     *
     * @see code.pool.PoolInterface#get()
     */
    @Override
    public E take() {
        try {
            final E resource = this.freePool.take();
            this.usedPool.add(resource);
            return resource;
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            this.log.error(e.toString());
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see code.pool.PoolInterface#release(java.lang.Object)
     */
    @Override
    public PoolInterface<E> release(final E resource) {
        this.usedPool.remove(resource);
        this.freePool.add(resource);
        return this;
    }

    /*
     * (non-Javadoc)
     *
     * @see code.pool.PoolInterface#remove(java.lang.Object)
     */
    @Override
    public PoolInterface<E> remove(final E resource) {
        this.freePool.remove(resource);
        this.usedPool.remove(resource);
        return this;
    }

}
