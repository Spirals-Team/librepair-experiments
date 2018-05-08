package com.mercateo.eventstore.data;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.mercateo.common.UnitTest;
import com.mercateo.eventstore.domain.Causality;
import com.mercateo.eventstore.domain.EventId;
import com.mercateo.eventstore.domain.EventType;
import com.mercateo.eventstore.json.EventJsonMapper;

import lombok.val;

@Category(UnitTest.class)
public class SerializableMetadataTest {

    private EventJsonMapper eventJsonMapper;

    @Before
    public void setUp() throws Exception {
        eventJsonMapper = new EventJsonMapper();
    }

    @Test
    public void deserializesMinimum() {
        val eventId = UUID.randomUUID();
        val jsonString = "{\"eventId\":\"" + eventId + "\"}";

        val result = eventJsonMapper.readValue(jsonString.getBytes(), SerializableMetadata.class).get();

        assertThat(result.eventId()).isEqualTo(eventId);
        assertThat(result.causality()).isNull();
    }

    @Test
    public void deserializesLegacyCausality() {
        final UUID causalityEventId = UUID.randomUUID();
        val jsonString = "{\"eventId\":\"" + UUID.randomUUID() + "\", " + "\"causality\": {\"eventId\": \""
                + causalityEventId + "\", \"eventType\": \"referenced\"}}";

        val result = eventJsonMapper.readValue(jsonString.getBytes(), SerializableMetadata.class).get();

        assertThat(result.causality()).containsExactly(CausalityData.of(Causality
            .builder()
            .eventId(EventId.of(causalityEventId))
            .eventType(EventType.of("referenced"))
            .build()));
    }

    @Test
    public void deserializesCausality() {
        final UUID causalityEventId = UUID.randomUUID();
        val jsonString = "{\"eventId\":\"" + UUID.randomUUID() + "\", " + "\"causality\": [{\"eventId\": \""
                + causalityEventId + "\", \"eventType\": \"referenced\"}]}";

        val result = eventJsonMapper.readValue(jsonString.getBytes(), SerializableMetadata.class).get();

        assertThat(result.causality()).containsExactly(CausalityData.of(Causality
            .builder()
            .eventId(EventId.of(causalityEventId))
            .eventType(EventType.of("referenced"))
            .build()));
    }
}