package co.spraybot;

import java.time.LocalDateTime;

public interface ChatBotResponse {

    ChatMessage getChatMessageRespondingTo();

    LocalDateTime getTimestamp();

    Boolean isDirectMessage();

    String getBody();

    static ChatBotResponseBuilder builder() {
        return new ChatBotResponseBuilder();
    }

}
