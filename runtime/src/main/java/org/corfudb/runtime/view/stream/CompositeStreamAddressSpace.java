package org.corfudb.runtime.view.stream;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import org.corfudb.protocols.wireprotocol.ILogData;
import org.corfudb.protocols.wireprotocol.Token;
import org.corfudb.runtime.CorfuRuntime;
import org.corfudb.runtime.view.Address;
import org.corfudb.runtime.view.StreamOptions;

/**
 * This class defines the space of addresses of a stream under the assumption that a stream can
 * be checkpoint(ed). It is made up of two separate spaces: that of regular addresses
 * and that of the checkpoint addresses. The CompositeStreamAddressSpace handles both spaces
 * to transparently provide a single resolved view of the stream address space to upper layers.
 *
 * Created by amartinezman on 4/24/18.
 */
public class CompositeStreamAddressSpace implements IStreamAddressSpace {

    private BackpointerStreamAddressSpace regularSAS;
    private CheckpointStreamAddressSpace cpSAS;
    private boolean pointerOnRegularStream;
    final private CorfuRuntime r;
    final private UUID regularStreamId;
    final private UUID cpStreamId;

    public CompositeStreamAddressSpace(UUID id, CorfuRuntime runtime) {
        regularStreamId = id;
        cpStreamId = CorfuRuntime.getCheckpointStreamIdFromId(id);
        r = runtime;
        regularSAS = new BackpointerStreamAddressSpace(regularStreamId, runtime);
        cpSAS = new CheckpointStreamAddressSpace(cpStreamId, runtime);
        pointerOnRegularStream = true;
    }

    @Override
    public void setStreamOptions(StreamOptions options) {
        this.regularSAS.setStreamOptions(options);
        this.cpSAS.setStreamOptions(options);
    }

    @Override
    public void reset() {
        regularSAS.reset();
        cpSAS.reset();
        pointerOnRegularStream = true;
    }

    @Override
    public void seek(long address) {
        // Seek for the address on the regular stream, if it is not available it means
        // it has been checkpoint(ed) and trimmed.
        regularSAS.seek(address);
        if (!pointerOnRegularStream) {
            // If pointer was following the CPSAS we should reset the pointer
            cpSAS.reset();
        }
        pointerOnRegularStream = true;
    }

    @Override
    public long getMax() {return regularSAS.getMax();}

    @Override
    public long getLastAddressSynced() {
        // If regularSAS has no address, maybe it was checkpoint(ed)/trimmed, the CP Stream Address
        // Space will have info regarding the last address that was synced for this stream and cp..
        if (regularSAS.getLastAddressSynced() == Address.NON_ADDRESS) {
            return cpSAS.getLastAddressSynced();
        }

        return regularSAS.getLastAddressSynced();
    }

    @Override
    public long next() {
        if (pointerOnRegularStream) {
            return regularSAS.next();
        } else {
            // If pointer is on the checkpoint stream and there is no next in this stream
            // we need to move onto the continuation  of the stream on the regularSAS
            if (cpSAS.hasNext()) {
                return cpSAS.next();
            } else if (regularSAS.hasNext()) {
                pointerOnRegularStream = true;
                return regularSAS.next();
            }
            return cpSAS.next();
        }
    }

    @Override
    public long previous() {
        if (pointerOnRegularStream) {
            if (regularSAS.hasPrevious()) {
                return regularSAS.previous();
            } else {
                // If there is no previous on regular stream check if you can move into CPStream
                if (cpSAS.hasPrevious()) {
                    pointerOnRegularStream = false;
                    return cpSAS.previous();
                } else {
                    return regularSAS.previous();
                }
            }
        } else {
            return cpSAS.previous();
        }
    }

    @Override
    public List<Long> remainingUpTo(long limit) {
        if(pointerOnRegularStream) {
            return regularSAS.remainingUpTo(limit);
        } else {
            // If pointer is on CPSAS we might need to move to regularSAS if we are
            // at the end of the CPSAS
            if (!cpSAS.hasNext()) {
                pointerOnRegularStream = true;
                return regularSAS.remainingUpTo(limit);
            }
            return cpSAS.remainingUpTo(limit);
        }
    }

    @Override
    public void addAddresses(List<Long> addresses) {
        regularSAS.addAddresses(addresses);
    }

    @Override
    public long getCurrentPointer() {
        if (pointerOnRegularStream) {
            return regularSAS.getCurrentPointer();
        } else {
            return cpSAS.getCurrentPointer();
        }
    }

    @Override
    public boolean hasNext() {
        if (pointerOnRegularStream) {
            return regularSAS.hasNext();
        } else if (cpSAS.hasNext()) {
            return cpSAS.hasNext();
        } else {
            pointerOnRegularStream = true;
            return regularSAS.hasNext();
        }
    }

    @Override
    public boolean hasPrevious() {
        if (!pointerOnRegularStream) {
            return cpSAS.hasPrevious();
        } else if (regularSAS.hasPrevious()) {
            return regularSAS.hasPrevious();
        } else {
            // Regular stream has no previous, move to CPStream
            pointerOnRegularStream = false;
            return cpSAS.hasPrevious();
        }
    }

    @Override
    public void removeAddresses(long upperBound) {
        throw new UnsupportedOperationException("removeAddresses");
    }

    public void syncUpTo(long globalAddress, long globalMax, long newTail, long lowerBound,
                         Function<Long, ILogData> readFn) {

        // The composite stream address space assumes that a stream is composed of both the address space of the regular stream
        // and the address space of the checkpoint stream, to sync the stream implies:
        // 1. Sync the checkpoint SAS
        // 2. Based on the upper limit of checkpoint(ed) addresses, sync the regular SAS.
        // TODO: this might be incurring in an extra sequencer call, if we know CP is disabled we should surround this part
        // TODO: of the code with this verification...
        Token token = r.getSequencerView()
                .nextToken(Collections.singleton(cpStreamId), 0).getToken();
        long cpTail = token.getTokenValue();
        if (cpTail != Address.NON_EXIST) {
            cpSAS.syncUpTo(globalAddress, globalMax, cpTail, lowerBound, readFn);
        }
        // Get the maximum address that has been checkpoint(ed) for the regular stream, this will
        // give the lower bound so we sync the regular stream until this limit, as all
        // entries below this address are contained in the CP.
        long upperLimitAddressesCheckpointed = cpSAS.getLastAddressSynced();

        // If there is an upperLimitAddressesCheckpointed, we
        // remove checkpoint(ed) addresses from the regular stream.
        if (upperLimitAddressesCheckpointed != Address.NON_ADDRESS) {

            // If we are pointing to the regular stream and the pointer is set on an address lower
            // or equal than the addresses to be removed from the regular stream, move the pointer
            // to the CPSAS
            // TODO: couldn't this affect other threads in snapshot transactions which had
            // TODO: the object on a given version?
            if(pointerOnRegularStream && regularSAS.getCurrentPointer() <= upperLimitAddressesCheckpointed) {
                pointerOnRegularStream = false;
            }

            // TODO: VERIFY IF THEY CAN BE REMOVED OR ONLY IF ADDRESSES ARE TRIMMED..
            regularSAS.removeAddresses(upperLimitAddressesCheckpointed);

            if (lowerBound < upperLimitAddressesCheckpointed) {
                lowerBound = upperLimitAddressesCheckpointed;
            }
        }

        regularSAS.syncUpTo(globalAddress, globalMax, newTail, lowerBound, readFn);
    }

    @Override
    public List<Long> findAddresses(long oldTail, long newTail, Function<Long, ILogData> readFn) {
        throw new UnsupportedOperationException("findAddresses");
    }
}
