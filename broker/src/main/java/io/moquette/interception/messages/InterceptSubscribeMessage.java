
package io.moquette.interception.messages;

import io.moquette.spi.impl.subscriptions.Subscription;
import io.moquette.spi.impl.subscriptions.Topic;
import io.netty.handler.codec.mqtt.MqttQoS;

/**
 * @author Wagner Macedo
 */
public class InterceptSubscribeMessage implements InterceptMessage {

    private final Subscription subscription;
    private final String username;

    public InterceptSubscribeMessage(Subscription subscription, String username) {
        this.subscription = subscription;
        this.username = username;
    }

    public String getClientID() {
        return subscription.getClientId();
    }

    public MqttQoS getRequestedQos() {
        return subscription.getRequestedQos();
    }

    public Topic getTopic() {
        return subscription.getTopicFilter();
    }

    public String getUsername() {
        return username;
    }
}
