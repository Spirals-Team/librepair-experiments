package co.spraybot.chatbots;

import co.spraybot.ChatMessage;
import co.spraybot.User;
import co.spraybot.helpers.ChatMessageProvider;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class SprayBotTest implements ChatMessageProvider {

    private EventBus eventBus;
    private SprayBot subject;
    private User mockUser;

    @Before
    public void setUp() {
        eventBus = mock(EventBus.class);
        subject = new SprayBot(eventBus);
        mockUser = mock(User.class);
    }

    @Test
    public void chatMessageFromRoomWithFoundCommand() {
        this.subject.programCommand("foo bar", "spraybot.commandName");
        ChatMessage chatMessage = fromRoomChatMessageBuilder().body("foo bar").build();
        this.subject.chatMessageReceived(chatMessage);

        ArgumentCaptor<DeliveryOptions> deliveryOptions = ArgumentCaptor.forClass(DeliveryOptions.class);
        verify(this.eventBus).send(
            eq("spraybot.chatcommandrunner"),
            isA(ChatMessage.class),
            deliveryOptions.capture(),
            isA(Handler.class)
        );

        assertEquals("performCommand", deliveryOptions.getValue().getHeaders().get("op"));
        assertEquals("spraybot.commandName", deliveryOptions.getValue().getHeaders().get("commandName"));
    }

    @Test
    public void chatMessageFromRoomFoundCommandWithSingleAttribute() {
        this.subject.programCommand("foo {id} bar", "spraybot.attributeCommand");
        ChatMessage chatMessage = fromRoomChatMessageBuilder().body("foo with spaces bar").build();
        this.subject.chatMessageReceived(chatMessage);

        ArgumentCaptor<ChatMessage> passedMessage = ArgumentCaptor.forClass(ChatMessage.class);
        ArgumentCaptor<DeliveryOptions> deliveryOptions = ArgumentCaptor.forClass(DeliveryOptions.class);
        verify(this.eventBus).send(
            eq("spraybot.chatcommandrunner"),
            passedMessage.capture(),
            deliveryOptions.capture(),
            isA(Handler.class)
        );

        assertEquals("performCommand", deliveryOptions.getValue().getHeaders().get("op"));
        assertEquals("spraybot.attributeCommand", deliveryOptions.getValue().getHeaders().get("commandName"));
        assertEquals("with spaces", passedMessage.getValue().getBodyAttributes().get("id"));
    }

    @Test
    public void chatMessageFromRoomFoundCommandWithMultipleAttributes() {
        this.subject.programCommand("foo {id} bar {baz}", "spraybot.attributeCommand");
        ChatMessage chatMessage = fromRoomChatMessageBuilder()
            .body("foo with spaces bar and, yes, spaces and commas here too")
            .build();
        this.subject.chatMessageReceived(chatMessage);

        ArgumentCaptor<ChatMessage> passedMessage = ArgumentCaptor.forClass(ChatMessage.class);
        ArgumentCaptor<DeliveryOptions> deliveryOptions = ArgumentCaptor.forClass(DeliveryOptions.class);
        verify(this.eventBus).send(
            eq("spraybot.chatcommandrunner"),
            passedMessage.capture(),
            deliveryOptions.capture(),
            isA(Handler.class)
        );

        assertEquals("performCommand", deliveryOptions.getValue().getHeaders().get("op"));
        assertEquals("spraybot.attributeCommand", deliveryOptions.getValue().getHeaders().get("commandName"));
        assertEquals("with spaces", passedMessage.getValue().getBodyAttributes().get("id"));
        assertEquals("and, yes, spaces and commas here too", passedMessage.getValue().getBodyAttributes().get("baz"));
    }

    @Test
    public void chatMessageFromRoomWithFoundCommandMentionOnly() {
        this.subject.programCommandMentionOnly("foo bar", "spraybot.commandName");
        ChatMessage chatMessage = fromRoomChatMessageBuilder()
            .body("@spraybot foo bar")
            .build();
        this.subject.chatMessageReceived(chatMessage);

        ArgumentCaptor<DeliveryOptions> deliveryOptions = ArgumentCaptor.forClass(DeliveryOptions.class);
        verify(this.eventBus).send(
            eq("spraybot.chatcommandrunner"),
            isA(ChatMessage.class),
            deliveryOptions.capture(),
            isA(Handler.class)
        );

        assertEquals("performCommand", deliveryOptions.getValue().getHeaders().get("op"));
        assertEquals("spraybot.commandName", deliveryOptions.getValue().getHeaders().get("commandName"));
    }

    @Test
    public void chatMessageFromRoomWithFoundCommandMentionOnlyNoAtSign() {
        this.subject.programCommandMentionOnly("foo bar", "spraybot.commandName");
        ChatMessage chatMessage = fromRoomChatMessageBuilder()
            .body("spraybot foo bar")
            .build();
        this.subject.chatMessageReceived(chatMessage);

        ArgumentCaptor<DeliveryOptions> deliveryOptions = ArgumentCaptor.forClass(DeliveryOptions.class);
        verify(this.eventBus).send(
            eq("spraybot.chatcommandrunner"),
            isA(ChatMessage.class),
            deliveryOptions.capture(),
            isA(Handler.class)
        );

        assertEquals("performCommand", deliveryOptions.getValue().getHeaders().get("op"));
        assertEquals("spraybot.commandName", deliveryOptions.getValue().getHeaders().get("commandName"));
    }

    @Test
    public void chatMessageFromRoomWithNoCommandFound() {
        ChatMessage chatMessage = fromRoomChatMessage();
        this.subject.chatMessageReceived(chatMessage);

        verify(this.eventBus, never()).send(any(), any(), any(), any());
    }

}
