/*
 * Copyright (c) 2016-2018 Zerocracy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to read
 * the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.zerocracy.shutdown;

import com.zerocracy.Farm;
import com.zerocracy.farm.fake.FkFarm;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test case for {@link ShutdownFarm}.
 *
 * @since 1.0
 * @checkstyle JavadocMethodCheck (500 lines)
 */
public final class ShutdownFarmTest {

    @Test
    public void shutdown() throws Exception {
        final ShutdownFarm.Hook hook = new ShutdownFarm.Hook();
        MatcherAssert.assertThat(
            "Check failed",
            hook.check(), Matchers.is(true)
        );
        final ScheduledExecutorService exec =
            Executors.newSingleThreadScheduledExecutor();
        final Farm farm = new ShutdownFarm(new FkFarm(), hook);
        exec.schedule(hook::complete, 1L, TimeUnit.SECONDS);
        farm.close();
        MatcherAssert.assertThat(
            "Shutdown wasn't completed",
            hook.stopped(), Matchers.is(true)
        );
    }
}
