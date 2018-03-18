package co.spraybot.messagecodecs;

import co.spraybot.ChatMessage;

import static org.junit.Assert.*;

import co.spraybot.helpers.FixedClockProvider;
import co.spraybot.User;
import co.spraybot.DefaultUser;
import io.vertx.core.eventbus.MessageCodec;

public class ChatMessageCodecTest extends MessageCodecTest<ChatMessage> implements FixedClockProvider {

    @Override
    protected MessageCodec<ChatMessage, ChatMessage> subject() {
        return new ChatMessageCodec();
    }

    @Override
    protected ChatMessage transformEntity() {
        User user = new DefaultUser("nick-id", "Nick");
        return ChatMessage.builder()
            .poweredBy("ChatAdapter")
            .sentFromRoom("good-dogs", user)
            .sentAt(referenceTime())
            .addHeader("foo", "bar")
            .addHeader("bar", "foo")
            .addHeader("foobar", "baz")
            .addBodyAttribute("secondWoof", "woof")
            .addBodyAttribute("firstBark", "bark")
            .body("woof woof bark bark")
            .build();
    }

    @Override
    protected void assertDecodedEntity(ChatMessage subject) {
        assertEquals("ChatAdapter", subject.getAdapterName());
        assertEquals("good-dogs", subject.getRoomName());
        assertEquals("nick-id", subject.getUser().getId());
        assertEquals("Nick", subject.getUser().getName());
        assertEquals(referenceTime(), subject.getTimestamp());
        assertEquals("bar", subject.getHeaders().get("foo"));
        assertEquals("foo", subject.getHeaders().get("bar"));
        assertEquals("baz", subject.getHeaders().get("foobar"));
        assertEquals("woof", subject.getBodyAttributes().get("secondWoof"));
        assertEquals("bark", subject.getBodyAttributes().get("firstBark"));
        assertEquals(3, subject.getHeaders().size());
        assertEquals("woof woof bark bark", subject.getBody());
    }
}
