package io.vertx.up.web;

import io.vertx.ext.unit.TestContext;
import io.vertx.up.Launcher;
import io.vertx.zero.exception.VertxCallbackException;
import io.zero.epic.Ut;
import io.zero.quiz.ZeroBase;
import org.junit.Test;

public class ZeroLauncherTc extends ZeroBase {

    @Test(expected = VertxCallbackException.class)
    public void testLauncher(final TestContext context) {
        final Launcher launcher = Ut.singleton(ZeroLauncher.class);
        launcher.start(null);
    }
}
