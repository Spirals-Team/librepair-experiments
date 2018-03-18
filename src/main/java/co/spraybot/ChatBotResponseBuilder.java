package co.spraybot;

import java.time.LocalDateTime;

public class ChatBotResponseBuilder {

    private ChatMessage respondingTo;
    private LocalDateTime timestamp;
    private Boolean isDirectMessage = false;
    private String body;

    public ChatBotResponseBuilder respondingTo(ChatMessage chatMessage) {
        respondingTo = chatMessage;
        return this;
    }

    public ChatBotResponseBuilder sentAt(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public ChatBotResponseBuilder asDirectMessage() {
        isDirectMessage = true;
        return this;
    }

    public ChatBotResponseBuilder body(String body) {
        this.body = body;
        return this;
    }

    public ChatBotResponse build() {
        return new ChatBotResponse() {

            private ChatMessage respondingTo;
            private LocalDateTime timestamp;
            private String body;
            private Boolean isDirectMessage;

            private ChatBotResponse init(ChatMessage chatMessage, LocalDateTime timestamp, String body, Boolean isDirectMessage) {
                respondingTo = chatMessage;
                this.timestamp = timestamp;
                this.body = body;
                this.isDirectMessage = isDirectMessage;

                return this;
            }

            @Override
            public ChatMessage getChatMessageRespondingTo() {
                return respondingTo;
            }

            @Override
            public LocalDateTime getTimestamp() {
                return timestamp;
            }

            @Override
            public Boolean isDirectMessage() {
                return isDirectMessage;
            }

            @Override
            public String getBody() {
                return body;
            }
        }.init(respondingTo, timestamp, body, isDirectMessage);
    }

}
