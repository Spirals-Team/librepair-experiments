
package io.moquette.interception.messages;

import io.moquette.spi.impl.subscriptions.Topic;

/**
 * @author Wagner Macedo
 */
public class InterceptUnsubscribeMessage implements InterceptMessage {

    private final Topic topic;
    private final String clientID;
    private final String username;

    public InterceptUnsubscribeMessage(Topic topic, String clientID, String username) {
        this.topic = topic;
        this.clientID = clientID;
        this.username = username;
    }

    public Topic getTopic() {
        return topic;
    }

    public String getClientID() {
        return clientID;
    }

    public String getUsername() {
        return username;
    }
}
