package co.spraybot.messagecodecs;

import co.spraybot.ChatBotResponse;
import co.spraybot.ChatMessage;
import co.spraybot.helpers.ChatMessageProvider;
import co.spraybot.helpers.FixedClockProvider;
import io.vertx.core.eventbus.MessageCodec;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ChatBotResponseCodecTest extends MessageCodecTest<ChatBotResponse> implements FixedClockProvider, ChatMessageProvider {

    @Override
    protected MessageCodec<ChatBotResponse, ChatBotResponse> subject() {
        return new ChatBotResponseCodec();
    }

    @Override
    protected ChatBotResponse transformEntity() {
        ChatMessage chatMessage = fromRoomChatMessageBuilder()
            .addHeader("foo", "bar")
            .addBodyAttribute("bar", "baz")
            .build();
        return ChatBotResponse.builder()
            .respondingTo(chatMessage)
            .sentAt(referenceTime())
            .body("the response message")
            .asDirectMessage()
            .build();
    }

    @Override
    protected void assertDecodedEntity(ChatBotResponse response) {
        assertNotNull(response);
        assertEquals(referenceTime(), response.getTimestamp());
        assertEquals("the response message", response.getBody());
        assertTrue(response.isDirectMessage());

        assertNotNull(response.getChatMessageRespondingTo());
        assertEquals("ChatAdapter", response.getChatMessageRespondingTo().getAdapterName());
        assertEquals("#general", response.getChatMessageRespondingTo().getRoomName());
        assertEquals("pam-poovey", response.getChatMessageRespondingTo().getUser().getId());
        assertEquals("pam", response.getChatMessageRespondingTo().getUser().getName());
        assertEquals(referenceTime(), response.getChatMessageRespondingTo().getTimestamp());
        assertEquals("the chat message body", response.getChatMessageRespondingTo().getBody());

        Map<String, String> expectedHeaders = new HashMap<>();
        expectedHeaders.put("foo", "bar");

        Map<String, String> expectedAttributes = new HashMap<>();
        expectedAttributes.put("bar", "baz");

        assertEquals(expectedHeaders, response.getChatMessageRespondingTo().getHeaders());
        assertEquals(expectedAttributes, response.getChatMessageRespondingTo().getBodyAttributes());
    }
}
