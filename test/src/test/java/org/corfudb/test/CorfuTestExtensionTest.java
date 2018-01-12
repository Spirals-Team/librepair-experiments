package org.corfudb.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.corfudb.runtime.CorfuRuntime;
import org.corfudb.util.Sleep;

@CorfuTest
public class CorfuTestExtensionTest {

    @CorfuTest
    public void test() {
        // Do some stuff
        final int seconds = 5;
        Sleep.SECONDS.sleepUninterruptibly(seconds);
    }


    @CorfuTest
    public void test2(CorfuRuntime runtime) {
        runtime.connect();
        runtime.shutdown();
        assertThat(runtime.isShutdown());
    }
}
