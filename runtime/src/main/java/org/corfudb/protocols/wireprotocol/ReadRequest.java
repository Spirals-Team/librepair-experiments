package org.corfudb.protocols.wireprotocol;

import com.google.common.collect.Range;

import com.google.common.collect.Sets;
import io.netty.buffer.ByteBuf;

import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by mwei on 8/11/16.
 */
@Data
@AllArgsConstructor
public class ReadRequest implements ICorfuPayload<ReadRequest> {

    final Set<Long> set;

    /**
     * Deserialization Constructor from ByteBuf to ReadRequest.
     *
     * @param buf The buffer to deserialize
     */
    public ReadRequest(ByteBuf buf) {
        set = ICorfuPayload.setFromBuffer(buf, Long.class);
    }

    public ReadRequest(Long address) {
        set = Sets.newHashSet(address);
    }

    @Override
    public void doSerialize(ByteBuf buf) {
        ICorfuPayload.serialize(buf, set);
    }

}
