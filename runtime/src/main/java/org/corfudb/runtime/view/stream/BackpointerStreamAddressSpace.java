package org.corfudb.runtime.view.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import lombok.extern.slf4j.Slf4j;

import org.corfudb.protocols.wireprotocol.ILogData;
import org.corfudb.runtime.CorfuRuntime;
import org.corfudb.runtime.exceptions.TrimmedException;

/**
 * The BackpointerStreamAddressSpace follows backpointers in order to
 * traverse a stream in a given range (defined by the space between oldTail and newTail).
 *
 * Created by amartinezman on 4/18/18.
 */
@Slf4j
public class BackpointerStreamAddressSpace extends StreamAddressSpace {

    public BackpointerStreamAddressSpace(UUID id, CorfuRuntime runtime) {
        super(id, runtime);
    }

    @Override
    public List<Long> findAddresses(long oldTail, long newTail, Function<Long, ILogData> readFn) {
        List<Long> addressesToAdd = new ArrayList<>(100);

        while (newTail > oldTail) {
            try {
                ILogData d = readFn.apply(newTail);
                if (d.containsStream(streamId)) {
                    addressesToAdd.add(newTail);
                }
                // What happens when a hole is encountered ? (see streamCanSurviveOverwriteException test in StreamViewTest)
                newTail = d.getBackpointer(streamId);
            } catch (TrimmedException te) {
                if (options.ignoreTrimmed) {
                    log.warn("followBackpointers: Ignoring trimmed exception for address[{}]," +
                            " stream[{}]", newTail, streamId);
                    return addressesToAdd;
                } else {
                    throw te;
                }
            }
        }

        return addressesToAdd;
    }

}
