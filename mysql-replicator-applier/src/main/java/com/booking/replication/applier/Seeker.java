package com.booking.replication.applier;

import com.booking.replication.applier.kafka.KafkaSeeker;
import com.booking.replication.augmenter.model.AugmentedEvent;
import com.booking.replication.commons.checkpoint.Checkpoint;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

public interface Seeker extends Function<AugmentedEvent, AugmentedEvent>, Closeable {
    enum Type {
        NONE {
            @Override
            public Seeker newInstance(Map<String, String> configuration) {
                return event -> event;
            }
        },
        KAFKA {
            @Override
            public Seeker newInstance(Map<String, String> configuration) {
                return new KafkaSeeker(configuration);
            }
        };

        public abstract Seeker newInstance(Map<String, String> configuration);
    }

    interface Configuration {
        String TYPE = "seeker.type";
    }

    default Checkpoint seek(Checkpoint checkpoint) {
        return checkpoint;
    }

    @Override
    default void close() throws IOException {
    }

    static Seeker build(Map<String, String> configuration) {
        return Seeker.Type.valueOf(
                configuration.getOrDefault(Configuration.TYPE, Type.NONE.name())
        ).newInstance(configuration);
    }
}
