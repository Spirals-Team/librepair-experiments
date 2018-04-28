package org.corfudb.runtime.view.stream;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import org.corfudb.protocols.wireprotocol.ILogData;
import org.corfudb.runtime.CorfuRuntime;
import org.corfudb.runtime.view.Address;
import org.corfudb.runtime.view.StreamOptions;

/**
 * This is an abstract class which represents the space of addresses of a stream. It is based on the
 * assumption that the address space of a stream can be traversed forward and backwards.
 *
 * The abstract method 'findAddresses' defines how to traverse the space of unexplored addresses,
 * i.e, addresses between the latest synced point (oldTail) and the latest update (newTail).
 * For example, the implementation of BackpointerAddressSpace assumes the use of backpointers
 * to determine the previous log entry for a stream.
 *
 * Created by amartinezman on 4/23/18.
 */
public abstract class StreamAddressSpace implements IStreamAddressSpace {

    protected ArrayList<Long> addresses;
    protected UUID streamId;
    protected CorfuRuntime runtime;
    protected int maxInd;
    protected int currInd;
    protected StreamOptions options;

    public StreamAddressSpace(UUID id, CorfuRuntime runtime) {
        this.streamId = id;
        this.runtime = runtime;
        this.addresses = new ArrayList<>(10_000);
        this.currInd = -1;
        this.maxInd = -1;
    }

    @Override
    public void setStreamOptions(StreamOptions options) {
        this.options = options;
    }

    @Override
    public void reset() {
        currInd = -1;
    }

    @Override
    public void seek(long address) {
        int tmpPtr = maxInd;

        while(tmpPtr >= 0) {
            if(addresses.get(tmpPtr) == address) {
                currInd = tmpPtr;
                return;
            }
            tmpPtr = tmpPtr - 1;
        }

        throw new RuntimeException("Couldn't seek " + streamId + " to address " + address);
    }

    @Override
    public long getMax() {
        if (maxInd == -1) {
            return Address.NON_ADDRESS;
        } else {
            return addresses.get(maxInd);
        }
    }

    @Override
    public long getLastAddressSynced() {
        return getMax();
    }

    @Override
    public long next() {
        if (currInd + 1 > maxInd) {
            return Address.NON_ADDRESS;
        } else {
            currInd++;
            return addresses.get(currInd);
        }
    }

    @Override
    public long previous() {
        if (currInd - 1 >= 0) {
            currInd--;
            return addresses.get(currInd);
        } else {
            currInd = -1;
            return Address.NON_ADDRESS;
        }    }

    @Override
    public List<Long> remainingUpTo(long limit) {
        List<Long> res = new ArrayList<>(100);
        while (hasNext() && addresses.get(currInd + 1) <= limit) {
            res.add(addresses.get(currInd + 1));
            currInd++;
        }
        return res;
    }

    @Override
    public void addAddresses(List<Long> addresses) {
        if (!addresses.isEmpty()) {
            this.addresses.addAll(addresses);
            maxInd += addresses.size();
        }
    }

    @Override
    public long getCurrentPointer() {
        if (currInd == -1) {
            return Address.NON_ADDRESS;
        } else {
            return addresses.get(currInd);
        }
    }

    @Override
    public boolean hasNext() {
        if (currInd < maxInd) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean hasPrevious() {
        if (currInd > 0 && currInd < addresses.size()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void removeAddresses(long upperBound) {
        int removedAddresses = 0;
        int index = this.addresses.indexOf(upperBound);
        if (index != -1) {
            for (int i = index; i >= 0; i--){
                removedAddresses ++;
                this.addresses.remove(i);
            }

            if (currInd <= index) {
                // Reset current index to first entry in the stream (if any remaining)
                if (this.addresses.size() > 0) {
                    currInd = -1;
                } else {
                    currInd = -1;
                }
            } else {
                currInd = currInd - removedAddresses;
            }

            maxInd = this.addresses.size() - 1;
        }
    }

    @Override
    public void syncUpTo(long globalAddress, long globalMax, long newTail, long lowerBound,
                         Function<Long, ILogData> readFn) {
        if (getMax() < newTail) {
            long oldTail = lowerBound;

            if (maxInd != -1) {
                if (lowerBound > addresses.get(maxInd)) {
                    oldTail = lowerBound;
                } else {
                    oldTail = addresses.get(maxInd);
                }
            }

            List<Long> addressesToAdd;

            addressesToAdd = findAddresses(oldTail, newTail, readFn);

            // update global max and add the new tail addresses
            // need to consider the global tail too !!!!!!!
            // globalMax = Math.max(globalMax, token.getGlobalTail());
            // this.globalMax = Math.max(this.globalMax, globalAddress);
            List<Long> revList = Lists.reverse(addressesToAdd);
            this.addAddresses(revList);
        }
    }

    @Override
    public abstract List<Long> findAddresses(long oldTail, long newTail, Function<Long, ILogData> readFn);
}