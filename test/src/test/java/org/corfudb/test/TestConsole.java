package org.corfudb.test;

import static org.fusesource.jansi.Ansi.ansi;

import com.google.common.collect.ImmutableList;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import jnr.posix.POSIX;
import jnr.posix.POSIXFactory;
import lombok.Getter;
import org.corfudb.runtime.exceptions.unrecoverable.UnrecoverableCorfuError;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.Erase;

import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.junit.jupiter.api.extension.ExtensionContext;

public class TestConsole {

    public static TestConsole computer(@Nonnull String string) {
        return new TestConsole();
    }

    private static class TestConsoleEvent {
        @Getter
        final TestEvent event;

        @Getter
        final Object[] data;

        public TestConsoleEvent(@Nonnull TestEvent event, @Nullable Object[] data) {
            this.event = event;
            this.data = data;
        }

        public ExtensionContext getContext() {
            return (ExtensionContext) data[0];
        }
    }

    private enum TestEvent {
        TEST_START,
        TEST_END
        ;
        TestConsoleEvent getEvent(Object... data) {
            return new TestConsoleEvent(this, data);
        }
    }


    final BlockingDeque<TestConsoleEvent> eventQueue = new LinkedBlockingDeque<>();
    final Thread consoleThread;
    volatile boolean shutdown = false;
    final static Duration REFRESH_RATE = Duration.ofMillis(25);
    List<IConsoleAnimation> animations = new ArrayList<>();
    final Terminal terminal;
    final POSIX posix = POSIXFactory.getPOSIX();

    private void doAnimations() {
        int total = 0;
        for (IConsoleAnimation animation : animations) {
            total += animation.animate();
            System.out.print(" ");
            total += 1;
        }
        if (total > 0) {
            System.out.print(ansi().cursorLeft(total));
        }
    }

    private void stopAnimations() {
        animations.clear();
        System.out.print(ansi().eraseLine(Erase.FORWARD));
    }

    private void addAnimation(@Nonnull IConsoleAnimation animation) {
        animations.add(animation);
    }

    public TestConsole() {
        try {
            terminal = TerminalBuilder.builder()
                .system(true)
                .dumb(true)
                .build();
        } catch (IOException e) {
            throw new UnrecoverableCorfuError(e);
        }

        consoleThread = new Thread(this::processEvents);
        consoleThread.setDaemon(true);
        consoleThread.setName("TestConsole");
        consoleThread.start();
    }

    interface IConsoleAnimation {
        int animate();
    }

    static class Spinner implements IConsoleAnimation {
        int glyph = 0;
        List<String> glyphs = ImmutableList.<String>builder()
                                    .add("⠋")
                                    .add("⠙")
                                    .add("⠹")
                                    .add("⠸")
                                    .add("⠼")
                                    .add("⠴")
                                    .add("⠦")
                                    .add("⠧")
                                    .add("⠇")
                                    .add("⠏")
                                    .build();
        @Override
        public int animate() {
            draw(glyph);
            glyph++;
            if (glyph == glyphs.size()) {
                glyph = 0;
            }
            return 1;
        }

        void draw(int glyph) {
            System.out.print(Ansi.ansi()
                                .a(glyphs.get(glyph)));
            System.out.flush();
        }
    }

    static class ConsoleCounter implements IConsoleAnimation {

        final long startTime;
        static final int COUNTER_SIZE = 7;

        public ConsoleCounter(long startTime) {
            this.startTime = startTime;
        }


        @Override
        public int animate() {
            long currentTime = System.currentTimeMillis();
            long difference = currentTime - startTime;
            System.out.printf("[%02d:%02d]", TimeUnit.MILLISECONDS.toMinutes(difference),
                                            TimeUnit.MILLISECONDS.toSeconds(difference));
            return COUNTER_SIZE;
        }

    }

    private void cursorOff() {
        if (isInteractive()) {
            System.out.print("\u001b[?25l");
            System.out.flush();
        }
    }

    private void cursorOn() {
        if (isInteractive()) {
            System.out.print("\u001b[?25h");
            System.out.flush();
        }
    }

    @Getter(lazy = true)
    private final boolean interactive = !terminal.getType().equals("dumb");

    private void processEvents() {
        cursorOff();
        try {
            while (!shutdown) {
                // Check if there is an event in the queue, timing out in REFRESH_RATE duration.
                TestConsoleEvent event = eventQueue
                    .pollFirst(REFRESH_RATE.toMillis(), TimeUnit.MILLISECONDS);

                // If there is, process it
                if (event != null) {
                    switch (event.getEvent()) {
                        case TEST_START:
                            System.out.print(ansi().a(event.getContext().getDisplayName()));
                            System.out.print(" ");
                            System.out.flush();
                            addAnimation(new Spinner());
                            addAnimation(new ConsoleCounter(System.currentTimeMillis()));
                            break;
                        case TEST_END:
                            stopAnimations();
                            if (event.getContext().getExecutionException().isPresent()) {
                                System.out.print(Ansi.ansi().fgRed().a(" ✘").reset());
                            } else {
                                System.out.print(Ansi.ansi().fgGreen().a(" ✔").reset());
                            }
                            System.out.println();
                            break;
                    }
                }

                // Console updates
                if (isInteractive()) {
                    doAnimations();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            cursorOn();
        }
    }

    public void shutdown() {
        shutdown = true;
        consoleThread.interrupt();
    }

    public void startTestExecution(@Nonnull ExtensionContext context) {
        eventQueue.add(TestEvent.TEST_START.getEvent(context));
    }

    public void endTestExecution(@Nonnull ExtensionContext context) {
        eventQueue.add(TestEvent.TEST_END.getEvent(context));
    }
}
