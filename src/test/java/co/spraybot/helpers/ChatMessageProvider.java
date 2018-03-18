package co.spraybot.helpers;

import co.spraybot.ChatMessage;
import co.spraybot.ChatMessageBuilder;

public interface ChatMessageProvider extends FictionalUsersProvider, FixedClockProvider {

    default ChatMessageBuilder fromRoomChatMessageBuilder() {
        return ChatMessage.builder()
            .poweredBy("ChatAdapter")
            .sentFromRoom("#general", pamPoovey())
            .sentAt(referenceTime())
            .body("the chat message body");
    }

    default ChatMessageBuilder directMessageChatMessageBuilder() {
        return ChatMessage.builder()
                .poweredBy("ChatAdapter")
                .sentFromDirectMessage(sterlingArcher())
                .sentAt(referenceTime())
                .body("the direct message body");
    }

    default ChatMessage fromRoomChatMessage() {
        return fromRoomChatMessageBuilder().build();
    }

    default ChatMessage directMessageChatMessage() {
        return directMessageChatMessageBuilder().build();
    }
}
