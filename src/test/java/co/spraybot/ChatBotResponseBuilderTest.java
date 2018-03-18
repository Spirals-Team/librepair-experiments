package co.spraybot;

import co.spraybot.helpers.FixedClockProvider;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ChatBotResponseBuilderTest implements FixedClockProvider {

    @Test
    public void buildValidChatBotResponseToRoom() {
        ChatMessage chatMessage = mock(ChatMessage.class);
        ChatBotResponse response = ChatBotResponse.builder()
            .respondingTo(chatMessage)
            .sentAt(referenceTime())
            .body("this is the response sent to the room")
            .build();

        assertNotNull(response);
        assertEquals(chatMessage, response.getChatMessageRespondingTo());
        assertEquals(referenceTime(), response.getTimestamp());
        assertEquals("this is the response sent to the room", response.getBody());
        assertFalse(response.isDirectMessage());
    }

    @Test
    public void buildValidChatBotResponseToDirectMessage() {
        ChatMessage chatMessage = mock(ChatMessage.class);
        ChatBotResponse response = ChatBotResponse.builder()
            .respondingTo(chatMessage)
            .sentAt(referenceTime())
            .asDirectMessage()
            .body("this is the dm")
            .build();

        assertNotNull(response);
        assertEquals(chatMessage, response.getChatMessageRespondingTo());
        assertEquals(referenceTime(), response.getTimestamp());
        assertEquals("this is the dm", response.getBody());
        assertTrue(response.isDirectMessage());
    }

}
