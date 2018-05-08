package org.corfudb.runtime.view;

import java.util.Set;
import java.util.UUID;

import org.corfudb.protocols.wireprotocol.TokenResponse;
import org.corfudb.protocols.wireprotocol.TxResolutionInfo;
import org.corfudb.runtime.CorfuRuntime;
import org.corfudb.runtime.object.transactions.AbstractTransactionalContext;
import org.corfudb.runtime.object.transactions.TransactionType;
import org.corfudb.runtime.object.transactions.TransactionalContext;
import org.corfudb.util.CFUtils;


/**
 * Created by mwei on 12/10/15.
 */

public class SequencerView extends AbstractView {

    public SequencerView(CorfuRuntime runtime) {
        super(runtime);
    }

    /**
     * Return the next token in the sequencer for a particular stream.
     *
     * <p>If numTokens == 0, then the streamAddressesMap returned is the last handed out token for
     * each stream (if streamIDs is not empty). The token returned is the global address as
     * previously defined, namely, max global address across all the streams.</p>
     *
     * @param streamIDs The stream IDs to retrieve from.
     * @param numTokens The number of tokens to reserve.
     * @return The first token retrieved.
     */
    public TokenResponse nextToken(Set<UUID> streamIDs, int numTokens) {
        if (numTokens == 0) {
            long tail = checkHints(streamIDs.iterator().next());
            if (tail != Address.NOT_FOUND) {
                // the request can be serviced from sequencer hints (omitting sequencer request)
                long epoch = TransactionalContext.getCurrentContext().hintsEpoch;
                return new TokenResponse(tail, epoch, null);
            } else {
                // the request can't be sericed from the sequencer hints, need to
                // request the tail from the sequencer
                return layoutHelper(e -> CFUtils.getUninterruptibly(e.getPrimarySequencerClient()
                        .nextToken(streamIDs, numTokens)));
            }
        } else {
            // Not a tail query, by-pass sequencer hints
            return layoutHelper(e -> CFUtils.getUninterruptibly(e.getPrimarySequencerClient()
                    .nextToken(streamIDs, numTokens)));
        }
    }

    public TokenResponse nextToken(Set<UUID> streamIDs, int numTokens,
                                  TxResolutionInfo conflictInfo) {
        return layoutHelper(e -> CFUtils.getUninterruptibly(e.getPrimarySequencerClient()
                .nextToken(streamIDs, numTokens, conflictInfo)));
    }

    /**
     * check if a stream tail query can be serviced from the sequencer hints.
     * @param streamId
     * @return stream tail or Address.NOT_FOUND if the hints don't exist.
     */
    private long checkHints(UUID streamId) {
        AbstractTransactionalContext ctx = TransactionalContext.getCurrentContext();
        if (ctx != null && ctx.builder.getType() != TransactionType.SNAPSHOT
                && ctx.getSequencerHints() != null) {
            return ctx.getSequencerHints().getOrDefault(streamId, Address.NON_EXIST);
        } else {
            return Address.NOT_FOUND;
        }
    }

    public void trimCache(long address) {
        runtime.getLayoutView().getRuntimeLayout().getPrimarySequencerClient().trimCache(address);
    }
}