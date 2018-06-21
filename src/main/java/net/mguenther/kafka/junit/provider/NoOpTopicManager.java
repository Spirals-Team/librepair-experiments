package net.mguenther.kafka.junit.provider;

import lombok.extern.slf4j.Slf4j;
import net.mguenther.kafka.junit.TopicConfig;
import net.mguenther.kafka.junit.TopicManager;

@Slf4j
public class NoOpTopicManager implements TopicManager {

    @Override
    public void createTopic(final TopicConfig config) {
        log.warn("No ZK Connection URL has been given. Discarding this request to create a new topic with parameters {}.", config);
    }

    @Override
    public void deleteTopic(final String topic) {
        log.warn("No ZK Connection URL has been given. Discarding this request to delete topic {}.", topic);
    }

    @Override
    public boolean exists(final String topic) {
        log.warn("No ZK Connection URL has been given. Discardings this request for topic existence of topic {}.", topic);
        return false;
    }
}
