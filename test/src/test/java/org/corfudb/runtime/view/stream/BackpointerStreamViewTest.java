package org.corfudb.runtime.view.stream;

import org.corfudb.runtime.CorfuRuntime;
import org.corfudb.runtime.view.AbstractViewTest;
import org.corfudb.test.CorfuTest;
import org.corfudb.test.parameters.Param;
import org.corfudb.test.parameters.Parameter;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the BackpointerStreamView
 * <p>
 * Created by zlokhandwala on 5/24/17.
 */
@CorfuTest
public class BackpointerStreamViewTest {

    /**
     * Tests the hasNext functionality of the streamView.
     */
    @CorfuTest
    public void hasNextTest(CorfuRuntime runtime) {
        IStreamView sv = runtime.getStreamsView().get(CorfuRuntime.getStreamID("streamA"));
        sv.append("hello world".getBytes());

        assertThat(sv.hasNext()).isTrue();
        sv.next();
        assertThat(sv.hasNext()).isFalse();
    }

    /**
     * tests navigating forward/backward on a stream,
     * with intermittent appends to the stream.
     *
     * in addition to correctness assertions, this test can be used for
     * single-stepping with a debugger and observing stream behavior.
     */
    @CorfuTest
    public void readQueueTest(CorfuRuntime runtime,
                             @Parameter(Param.NUM_ITERATIONS_LOW) int iterations) {
        IStreamView sv = runtime.getStreamsView().get(CorfuRuntime.getStreamID("streamA"));
        final int ten = 10;

        // initially, populate the stream with appends
        for (int i = 0; i < iterations; i++) {
            sv.append(String.valueOf(i).getBytes());
        }

        // travese the stream forward while periodically (every ten
        // iterations) appending to it
        for (int i = 0; i < iterations; i++) {
            assertThat(sv.hasNext()).isTrue();
            byte[] payLoad = (byte[]) sv.next().getPayload(runtime);
            assertThat(new String(payLoad).equals(String.valueOf(i)))
                    .isTrue();
            assertThat(sv.getCurrentGlobalPosition()).isEqualTo(i);

            if (i % ten == 1) {
                for (int j = 0; j < iterations; j++) {
                    sv.append(String.valueOf(i).getBytes());
                }

            }
        }

        // traverse the stream backwards, while periodically (every ten
        // iterations) appending to it
        for (int i = iterations - 1; i >= 0; i--) {
            byte[] payLoad = (byte[]) sv.current().getPayload(runtime);
            assertThat(new String(payLoad).equals(String.valueOf(i)))
                    .isTrue();
            assertThat(sv.getCurrentGlobalPosition()).isEqualTo(i);
            sv.previous();

            if (i % ten == 1) {
                for (int j = 0; j < iterations; j++) {
                    sv.append(String.valueOf(i).getBytes());
                }

            }
        }
    }

    /** Test if seeking the stream after resetting and then calling previous
     *  returns the correct entry.
     */
    @CorfuTest
    public void seekSkipTest(CorfuRuntime runtime) {
        IStreamView sv = runtime.getStreamsView().get(CorfuRuntime.getStreamID("streamA"));
        final byte[] ENTRY_0 = {0};
        final byte[] ENTRY_1 = {1};
        final byte[] ENTRY_2 = {2};

        sv.append(ENTRY_0);
        sv.append(ENTRY_1);
        sv.append(ENTRY_2);
        sv.reset();

        // This moves the stream pointer so the NEXT read will be 2
        // (the pointer is at 1).
        sv.seek(2);

        // The previous entry should be ENTRY_0
        assertThat((byte[])sv.previous().getPayload(runtime))
                .isEqualTo(ENTRY_0);
    }


    @CorfuTest
    public void moreReadQueueTest(CorfuRuntime runtime,
                                @Parameter(Param.NUM_ITERATIONS_VERY_LOW) int iterations) {
        IStreamView sv = runtime.getStreamsView().get(CorfuRuntime.getStreamID("streamA"));
        final int ten = 10;

        // initially, populate the stream with appends
        for (int i = 0; i < iterations; i++) {
            sv.append(String.valueOf(i).getBytes());
        }

        // simple traverse to end of stream
        for (int i = 0; i < iterations; i++) {
            assertThat(sv.hasNext()).isTrue();
            sv.next();
        }

        // add two entries on alternate steps, and traverse forward one at a
        // time
        for (int i = 0; i < iterations; i++) {
            if (i % 2 == 0) {
                assertThat(sv.hasNext()).isFalse();
                sv.append(String.valueOf(i).getBytes());
                sv.append(String.valueOf(i).getBytes());
            }
            byte[] payLoad = (byte[]) sv.next().getPayload(runtime);
            assertThat(new String(payLoad).equals(String.valueOf(i)));
        }
    }

    /**
     *  test proper backpointer termination at the head of a stream
     *
     * */
    @CorfuTest
    public void headOfStreamBackpointerTermination(CorfuRuntime runtime,
        @Parameter(Param.NUM_ITERATIONS_VERY_LOW) int iterations) {

        final int totalEntries = iterations + 1;

        // Create StreamA (100 entries)
        IStreamView svA = runtime.getStreamsView().get(CorfuRuntime.getStreamID("streamA"));
        for (int i = 0; i < iterations; i++) {
            svA.append(String.valueOf(i).getBytes());
        }

        // Create StreamB (1 entry)
        IStreamView svB = runtime.getStreamsView().get(CorfuRuntime.getStreamID("streamB"));
        svB.append(String.valueOf(0).getBytes());

        // Fetch Stream B and verify backpointer count (which requires 1 read = 1 entry)
        svB.remainingUpTo(totalEntries);
        assertThat(((BackpointerStreamView) svB).getBackpointerCount()).isEqualTo(1L);
    }

}
