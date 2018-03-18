package co.spraybot.commandrunners;

import co.spraybot.*;
import co.spraybot.helpers.FixedClockProvider;
import io.vertx.core.Future;
import io.vertx.core.eventbus.EventBus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Stack;

import static org.junit.Assert.*;
import static org.mockito.Mockito.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DefaultChatCommandRunnerTest implements FixedClockProvider {

    private ChatCommandRunner subject;

    @Before
    public void setUp() {
        EventBus eventBus = mock(EventBus.class);
        this.subject = new DefaultChatCommandRunner(eventBus, fixedClock(), "spraybot");
    }

    @Test
    public void canPerformCommandNotRegistered() {
        assertFalse(this.subject.canPerformCommand("foo"));
    }

    @Test
    public void canPerformCommandRegistered() {
        ChatCommand stub = mock(ChatCommand.class);
        when(stub.getName()).thenReturn("foo");
        this.subject.registerCommand(stub);

        assertTrue(this.subject.canPerformCommand("foo"));
    }

    @Test
    public void performCommandFound() {
        ChatCommand stub = mock(ChatCommand.class);
        when(stub.getName()).thenReturn("foo-bar");
        when(stub.perform(isA(ChatCommandDrone.class))).thenReturn(Future.future());
        this.subject.registerCommand(stub);

        ChatMessage chatMessage = mock(ChatMessage.class);
        this.subject.performCommand("foo-bar", chatMessage);

        ArgumentCaptor<ChatCommandDrone> droneCaptor = ArgumentCaptor.forClass(ChatCommandDrone.class);
        verify(stub).perform(droneCaptor.capture());

        assertEquals("spraybot", droneCaptor.getValue().getChatBotName());
        assertSame(chatMessage, droneCaptor.getValue().getChatMessage());
    }

    @Test
    public void performCommandNotFound() {
        ChatMessage chatMessage = mock(ChatMessage.class);
        Future<Void> future = this.subject.performCommand("notFound", chatMessage);
        List<Object> data = new Stack<>();
        future.setHandler(event -> {
            data.add(event.failed());
            data.add(event.cause().getMessage());
        });

        assertTrue((Boolean) data.get(0));
        assertEquals("The command 'notFound' is not registered in co.spraybot.commandrunners.DefaultChatCommandRunner", (String) data.get(1));
    }

    @Test
    public void performCommandFoundSucceedsFromCommandFuture() {
        ChatMessage chatMessage = mock(ChatMessage.class);
        ChatCommand stub = mock(ChatCommand.class);

        when(stub.getName()).thenReturn("foobar");
        when(stub.perform(isA(ChatCommandDrone.class))).thenReturn(Future.succeededFuture());

        this.subject.registerCommand(stub);
        Future<Void> future = this.subject.performCommand("foobar", chatMessage);
        List<Object> data = new Stack<>();
        future.setHandler(event -> {
            data.add(event.succeeded());
        });

        assertTrue((Boolean) data.get(0));
    }

    @Test
    public void performCommandFoundFailsFromCommandFuture() {
        ChatMessage chatMessage = mock(ChatMessage.class);
        ChatCommand stub = mock(ChatCommand.class);

        when(stub.getName()).thenReturn("foobar");
        when(stub.perform(isA(ChatCommandDrone.class))).thenReturn(Future.failedFuture("This is from the command"));

        this.subject.registerCommand(stub);
        Future<Void> future = this.subject.performCommand("foobar", chatMessage);
        List<Object> data = new Stack<>();
        future.setHandler(event -> {
            data.add(event.failed());
            data.add(event.cause().getMessage());
        });

        assertTrue((Boolean) data.get(0));
        assertEquals("This is from the command", (String) data.get(1));
    }

    @Test
    public void performingRegisteredCommandHandler() {
        List<String> data = new Stack<>();
        this.subject.registerCommand("fooBar", drone -> {
            data.add("called");
            return Future.succeededFuture();
        });

        this.subject.performCommand("fooBar", mock(ChatMessage.class));

        assertEquals("called", data.get(0));
    }
}
