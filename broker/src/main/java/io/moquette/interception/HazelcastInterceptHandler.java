
package io.moquette.interception;

import com.hazelcast.core.ITopic;
import io.moquette.BrokerConstants;
import io.moquette.interception.messages.InterceptPublishMessage;
import io.moquette.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by mackristof on 28/05/2016.
 */
public class HazelcastInterceptHandler extends AbstractInterceptHandler {

    private static final Logger LOG = LoggerFactory.getLogger(HazelcastInterceptHandler.class);
    private final ITopic<HazelcastMsg> topic;

    public HazelcastInterceptHandler(Server server) {
        super(server);

        String topicName = server.getConfig().getProperty(BrokerConstants.HAZELCAST_TOPIC_NAME) == null
                ? "moquette": server.getConfig().getProperty(BrokerConstants.HAZELCAST_TOPIC_NAME);
        topic = hz.getTopic(topicName);
    }

    @Override
    public void onPublish(InterceptPublishMessage msg) {
        HazelcastMsg hazelcastMsg = new HazelcastMsg(msg);
        LOG.debug("{} publish on {} message: {}",
                hazelcastMsg.getClientId(), hazelcastMsg.getTopic(), hazelcastMsg.getPayload());
        topic.publish(hazelcastMsg);
    }
}
