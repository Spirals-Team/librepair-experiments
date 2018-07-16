package org.zalando.riptide;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

public final class CompletionTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    @Deprecated
    public void shouldJoinSuccessfully() {
        assertThat(Completion.join(completedFuture("test")), is("test"));
    }

    @Test
    @Deprecated
    public void shouldPropagateCause() {
        final CompletableFuture<Void> future = new CompletableFuture<>();
        final IOException e = new IOException();
        future.completeExceptionally(e);

        exception.expect(sameInstance(e));
        Completion.join(future);
    }

}
