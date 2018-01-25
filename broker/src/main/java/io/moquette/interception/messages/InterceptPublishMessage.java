
package io.moquette.interception.messages;

import io.moquette.spi.impl.subscriptions.Topic;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.mqtt.MqttPublishMessage;

/**
 * @author Wagner Macedo
 */
public class InterceptPublishMessage extends InterceptAbstractMessage<MqttPublishMessage> {

    private final String clientID;
    private final String username;
    private final Topic topic;

    public InterceptPublishMessage(MqttPublishMessage msg, String clientID, String username, Topic topic) {
        super(msg);
        this.clientID = clientID;
        this.username = username;
        this.topic = topic;
    }

    public Topic getTopic() {
        return topic;
    }

    public ByteBuf getPayload() {
        return msg.payload();
    }

    public String getClientID() {
        return clientID;
    }

    public String getUsername() {
        return username;
    }
}
