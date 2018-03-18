package co.spraybot;

import com.google.common.collect.ImmutableMap;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

final public class ChatMessageBuilder {

    private String adapter;
    private User user;
    private LocalDateTime timestamp;
    private String roomName;
    private String contents;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> bodyAttributes = new HashMap<>();

    ChatMessageBuilder() {}

    public ChatMessageBuilder fromChatMessage(ChatMessage chatMessage) {
        this.adapter = chatMessage.getAdapterName();
        this.user = chatMessage.getUser();
        this.timestamp = chatMessage.getTimestamp();
        this.roomName = chatMessage.getRoomName();
        this.contents = chatMessage.getBody();
        this.headers = new HashMap<>(chatMessage.getHeaders());
        this.bodyAttributes = new HashMap<>(chatMessage.getBodyAttributes());

        return this;
    }

    public ChatMessageBuilder poweredBy(String adapter) {
        this.adapter = adapter;
        return this;
    }

    public ChatMessageBuilder sentFromRoom(String roomName, User user) {
        this.roomName = roomName;
        this.user = user;
        return this;
    }

    public ChatMessageBuilder sentFromDirectMessage(User user) {
        roomName = "@" + user.getName();
        this.user = user;
        return this;
    }

    public ChatMessageBuilder sentAt(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public ChatMessageBuilder addHeader(String headerName, String headerValue) {
        headers.put(headerName, headerValue);
        return this;
    }

    public ChatMessageBuilder body(String contents) {
        this.contents = contents;
        return this;
    }

    public ChatMessageBuilder addBodyAttribute(String attrName, String attrValue) {
        bodyAttributes.put(attrName, attrValue);
        return this;
    }

    public ChatMessage build() {
        ChatMessage chatMessage = buildChatMessage();

        adapter = null;
        user = null;
        roomName = null;
        contents = null;
        timestamp = null;
        headers = new HashMap<>();
        bodyAttributes = new HashMap<>();

        return chatMessage;
    }

    private ChatMessage buildChatMessage() {
        return new ChatMessage() {
            private String adapter;
            private Map<String, String> headers;
            private String body;
            private Map<String, String> bodyAttributes;
            private String roomName;
            private LocalDateTime timestamp;
            private User user;

            private ChatMessage init(
                @Nonnull String adapter,
                @Nonnull LocalDateTime timestamp,
                @Nonnull Map<String, String> headers,
                @Nonnull String body,
                @Nonnull Map<String, String> bodyAttributes,
                @Nonnull String roomName,
                @Nonnull User user
            ) {
                this.adapter = adapter;
                this.timestamp = timestamp;
                this.headers = ImmutableMap.copyOf(headers);
                this.body = body;
                this.bodyAttributes = ImmutableMap.copyOf(bodyAttributes);
                this.roomName = roomName;
                this.user = user;
                return this;
            }

            @Override
            public String getAdapterName() {
                return adapter;
            }

            @Override
            public Map<String, String> getHeaders() {
                return headers;
            }

            @Override
            public String getBody() {
                return body;
            }

            @Override
            public Map<String, String> getBodyAttributes() { return bodyAttributes; }

            @Override
            public String getRoomName() {
                return roomName;
            }

            @Override
            public LocalDateTime getTimestamp() {
                return timestamp;
            }

            @Override
            public User getUser() {
                return user;
            }

            @Override
            public boolean isDirectMessage() {
                return "@".equals(roomName.substring(0, 1));
            }
        }.init(adapter, timestamp, headers, contents, bodyAttributes, roomName, user);
    }

}
