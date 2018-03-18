package co.spraybot;

import java.util.HashMap;
import java.util.Map;

import co.spraybot.helpers.ChatMessageProvider;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.*;

public class ChatMessageBuilderTest implements ChatMessageProvider {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void buildValidFromRoomChatMessage() {
        ChatMessage chatMessage = fromRoomChatMessageBuilder().build();

        assertEquals(ChatAdapter.class.getSimpleName(), chatMessage.getAdapterName());
        assertEquals(referenceTime(), chatMessage.getTimestamp());
        assertEquals("pam-poovey", chatMessage.getUser().getId());
        assertEquals("pam", chatMessage.getUser().getName());
        assertEquals("#general", chatMessage.getRoomName());
        assertFalse(chatMessage.isDirectMessage());
        assertEquals("the chat message body", chatMessage.getBody());
    }

    @Test
    public void buildValidDirectChatMessage() {
        ChatMessage chatMessage = directMessageChatMessageBuilder().build();

        assertEquals(ChatAdapter.class.getSimpleName(), chatMessage.getAdapterName());
        assertEquals(referenceTime(), chatMessage.getTimestamp());
        assertEquals("sterling-archer", chatMessage.getUser().getId());
        assertEquals("sterling", chatMessage.getUser().getName());
        assertEquals("@sterling", chatMessage.getRoomName());
        assertTrue(chatMessage.isDirectMessage());
        assertEquals("the direct message body", chatMessage.getBody());
    }

    @Test
    public void buildFromChatMessage() {
        User user = randomUser();
        ChatMessage original = ChatMessage.builder()
            .poweredBy("ChatAdapter")
            .sentFromRoom("general", user)
            .sentAt(referenceTime())
            .addHeader("foo", "bar")
            .addBodyAttribute("bar", "baz")
            .body("foobar")
            .build();

        ChatMessage subject = ChatMessage.builder()
            .fromChatMessage(original)
            .addHeader("overwatch", "junkrat")
            .addBodyAttribute("bob", "belcher")
            .build();

        Map<String, String> expectedHeaders = new HashMap<>();
        expectedHeaders.put("foo", "bar");
        expectedHeaders.put("overwatch", "junkrat");

        Map<String, String> expectedBodyAttributes = new HashMap<>();
        expectedBodyAttributes.put("bar", "baz");
        expectedBodyAttributes.put("bob", "belcher");

        assertEquals(original.getAdapterName(), subject.getAdapterName());
        assertEquals(original.getRoomName(), subject.getRoomName());
        assertEquals(original.getUser(), subject.getUser());
        assertEquals(original.getTimestamp(), subject.getTimestamp());
        assertEquals(expectedHeaders, subject.getHeaders());
        assertEquals(expectedBodyAttributes, subject.getBodyAttributes());
        assertEquals(original.getBody(), subject.getBody());
    }

    @Test
    public void buildHeaders() {
        ChatMessage chatMessage = fromRoomChatMessageBuilder()
            .addHeader("foo", "bar")
            .addHeader("something", "else")
            .addHeader("else", "something")
            .build();

        Map<String, String> headers = chatMessage.getHeaders();

        assertEquals("bar", headers.get("foo"));
        assertEquals("else", headers.get("something"));
        assertEquals("something", headers.get("else"));
    }

    @Test
    public void headersAreImmutable() {
        Map<String, String> headers = fromRoomChatMessage().getHeaders();

        expectedException.expect(UnsupportedOperationException.class);
        headers.put("foo", "bar");
    }

    @Test
    public void buildBodyAttributes() {
        ChatMessage chatMessage = fromRoomChatMessageBuilder()
            .addBodyAttribute("sterling", "archer")
            .addBodyAttribute("pam", "poovey")
            .addBodyAttribute("lana", "kane")
            .build();
        Map<String, String> attributes = chatMessage.getBodyAttributes();

        assertEquals("archer", attributes.get("sterling"));
        assertEquals("poovey", attributes.get("pam"));
        assertEquals("kane", attributes.get("lana"));
    }

    @Test
    public void bodyAttributesAreImmutable() {
        Map<String, String> attributes = directMessageChatMessage().getBodyAttributes();

        expectedException.expect(UnsupportedOperationException.class);
        attributes.put("foo", "bar");
    }

    @Test
    public void buildMultipleObjects() {
        ChatMessageBuilder subject = ChatMessage.builder();
        subject.poweredBy("ChatAdapter")
                .sentFromRoom("general", randomUser())
                .sentAt(referenceTime())
                .addHeader("foo", "bar")
                .addBodyAttribute("bar", "baz")
                .body("foobar")
                .build();

        // we expect to get a nonnull error because we haven't declared anything yet and should see nulls
        expectedException.expect(IllegalArgumentException.class);
        ChatMessage chatMessage2 = subject.build();
    }


}
