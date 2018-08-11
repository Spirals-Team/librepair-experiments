package net.coljate.collection.impl;

import com.hazelcast.core.DistributedObject;
import com.hazelcast.core.ICollection;

/**
 *
 * @author ollie
 */
public class HazelcastCollection<T> extends MutableWrappedCollection<T> implements DistributedObject {

    private final ICollection<T> delegate;

    public HazelcastCollection(final ICollection<T> delegate) {
        super(delegate);
        this.delegate = delegate;
    }

    @Override
    public String getPartitionKey() {
        return delegate.getPartitionKey();
    }

    @Override
    public String getName() {
        return delegate.getName();
    }

    @Override
    public String getServiceName() {
        return delegate.getServiceName();
    }

    @Override
    public void destroy() {
        delegate.destroy();
    }

}
