package org.corfudb.runtime.view.stream;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.corfudb.protocols.logprotocol.CheckpointEntry;
import org.corfudb.protocols.wireprotocol.ILogData;
import org.corfudb.runtime.CorfuRuntime;
import org.corfudb.runtime.view.Address;
import org.corfudb.util.Utils;

@Slf4j
/**
 * The Checkpoint Stream Address Space resolves the space of addresses of a checkpoint(ed) stream,
 * therefore it is capable of following backpointers and resolving the checkpoint at the same time,
 * i.e., determine how to traverse the cp stream
 *
 * Created by amartinezman on 4/18/18.
 */
public class CheckpointStreamAddressSpace extends StreamAddressSpace {

    /** Info on checkpoint we used for initial stream replay,
     *  other checkpoint-related info & stats.  Hodgepodge, clarify.
     */
    @Getter
    public long lastCPAddress;
    UUID checkpointSuccessId = null;
    long checkpointSuccessStartAddr = Address.NEVER_READ;
    long checkpointSuccessEndAddr = Address.NEVER_READ;
    long checkpointSuccessNumEntries = 0L;
    long checkpointSuccessBytes = 0L;

    /** The address the current checkpoint snapshot was taken at.
     *  The checkpoint guarantees for this stream there are no entries
     *  between checkpointSuccessStartAddr and checkpointSnapshotAddress.
     */
    long checkpointSnapshotAddress = Address.NEVER_READ;

    public CheckpointStreamAddressSpace(UUID id, CorfuRuntime runtime) {
        super(id, runtime);
        lastCPAddress = Address.NON_ADDRESS;
    }

    @Override
    public long getLastAddressSynced() {
        if (maxInd == -1) {
            return Address.NON_ADDRESS;
        } else {
            return lastCPAddress;
        }
    }

    @Override
    public void syncUpTo(long globalAddress, long globalMax, long newTail, long lowerBound, Function<Long, ILogData> readFn) {
        if(getMax() < newTail ) {
            long oldTail = Address.NON_ADDRESS;

            if (maxInd != -1) {
                if (lowerBound > addresses.get(maxInd)) {
                    oldTail = lowerBound;
                } else {
                    oldTail = addresses.get(maxInd);
                }
            }

            List<Long> addressesToAdd;
            addressesToAdd = findAddresses(oldTail, newTail, readFn);
            List<Long> revList = Lists.reverse(addressesToAdd);
            this.addAddresses(revList);

            // If a CP was found, update the latest address checkpoint(ed) from regular tream
            if (revList.size() > 0) {
                // TODO: should take the start of the last CP (or will it only return the latest?)
                setLastCheckpointedAddress(readFn.apply(revList.get(0)));
            }
        }
    }

    private void setLastCheckpointedAddress(ILogData firstCPEntry) {
        if (firstCPEntry.hasCheckpointMetadata()) {
            CheckpointEntry cpEntry = (CheckpointEntry)
                    firstCPEntry.getPayload(runtime);

            if (cpEntry.getCpType() == CheckpointEntry.CheckpointEntryType.START) {
                lastCPAddress = Long.decode(cpEntry.getDict().get(CheckpointEntry.CheckpointDictKey.START_LOG_ADDRESS));
                return;
            }
        }

        lastCPAddress = -1L;
    }

    @Override
    public List<Long> findAddresses(long oldTail, long newTail, Function<Long, ILogData> readFn) {
        List<Long> addressesToAdd = new ArrayList<>(100);

        while (newTail > oldTail) {
            ILogData d = readFn.apply(newTail);
            if (d.containsStream(streamId)) {
                BackpointerOp op = resolveCheckpoint(d, newTail);
                if (op == BackpointerOp.INCLUDE
                        || op == BackpointerOp.INCLUDE_STOP) {
                    addressesToAdd.add(newTail);
                }
            }
            // TODO: This needs to be solved
            // What happens when a hole is encountered ?
            newTail = d.getBackpointer(streamId);
        }

        return addressesToAdd;
    }

    protected BackpointerOp resolveCheckpoint(ILogData data, long maxGlobal) {
        if (data.hasCheckpointMetadata()) {
            CheckpointEntry cpEntry = (CheckpointEntry)
                    data.getPayload(runtime);

            // Select the latest cp that has a snapshot address
            // which is less than maxGlobal
            if (this.checkpointSuccessId == null &&
                    cpEntry.getCpType() == CheckpointEntry.CheckpointEntryType.END
                    && Long.decode(cpEntry.getDict().get(CheckpointEntry.CheckpointDictKey.START_LOG_ADDRESS)) <= maxGlobal) {
                // First time a checkpoint is found for this stream
                log.trace("Checkpoint[{}] END found at address {} type {} id {} author {}",
                        this, data.getGlobalAddress(), cpEntry.getCpType(),
                        Utils.toReadableId(cpEntry.getCheckpointId()),
                        cpEntry.getCheckpointAuthorId());
                this.checkpointSuccessId = cpEntry.getCheckpointId();
                this.checkpointSuccessNumEntries = 1L;
                this.checkpointSuccessBytes = (long) data.getSizeEstimate();
                this.checkpointSuccessEndAddr = data.getGlobalAddress();
            }
            else if (data.getCheckpointId().equals(this.checkpointSuccessId)) {
                this.checkpointSuccessNumEntries++;
                this.checkpointSuccessBytes += cpEntry.getSmrEntriesBytes();
                if (cpEntry.getCpType().equals(CheckpointEntry.CheckpointEntryType.START)) {
                    this.checkpointSuccessStartAddr = Long.decode(cpEntry.getDict()
                            .get(CheckpointEntry.CheckpointDictKey.START_LOG_ADDRESS));
                    if (cpEntry.getDict().get(CheckpointEntry.CheckpointDictKey
                            .SNAPSHOT_ADDRESS) != null) {
                        this.checkpointSnapshotAddress = Long.decode(cpEntry.getDict()
                                .get(CheckpointEntry.CheckpointDictKey.SNAPSHOT_ADDRESS));
                    }
                    log.trace("Checkpoint[{}] HALT due to START at address {} startAddr"
                                    + " {} type {} id {} author {}",
                            this, data.getGlobalAddress(), this.checkpointSuccessStartAddr,
                            cpEntry.getCpType(),
                            Utils.toReadableId(cpEntry.getCheckpointId()),
                            cpEntry.getCheckpointAuthorId());
                    // We have reached the start of the checkpoint, include address and
                    // stop following backpointers
                    return BackpointerOp.INCLUDE_STOP;
                }
            } else {
                return BackpointerOp.EXCLUDE;
            }
        }
        // Not a checkpoint entry
        return BackpointerOp.INCLUDE;
    }

    protected enum BackpointerOp {
        INCLUDE,    /** Include this address. */
        EXCLUDE,    /** Exclude this address. */
        INCLUDE_STOP    /** Stop, but also include this address. */
    }
}
