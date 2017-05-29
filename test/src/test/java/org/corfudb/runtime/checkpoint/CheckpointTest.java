package org.corfudb.runtime.checkpoint;

import com.google.common.reflect.TypeToken;
import lombok.Getter;
import org.corfudb.runtime.CorfuRuntime;
import org.corfudb.runtime.MultiCheckpointWriter;
import org.corfudb.runtime.collections.SMRMap;
import org.corfudb.runtime.object.AbstractObjectTest;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by dmalkhi on 5/25/17.
 */
public class CheckpointTest extends AbstractObjectTest {

    @Getter
    CorfuRuntime myRuntime = null;

    void setRuntime() {
        myRuntime = new CorfuRuntime(getDefaultConfigurationString()).connect();
    }

    Map<String, Long> instantiateMap(String mapName) {
        return (SMRMap<String, Long>)
                instantiateCorfuObject(
                        getMyRuntime(),
                        new TypeToken<SMRMap<String, Long>>() {},
                        mapName);
    }

    @Test
    public void periodicCkpointTest() throws Exception {
        final String streamNameA = "mystreamA";
        final String streamNameB = "mystreamB";
        final String author = "periodicCkpoint";

        myRuntime = getDefaultRuntime().connect();

        Map<String, Long> m2A = instantiateMap(streamNameA);
        Map<String, Long> m2B = instantiateMap(streamNameB);

        scheduleConcurrently(1, ignored_task_num -> {
            for (int i = 0; i < PARAMETERS.NUM_ITERATIONS_MODERATE; i++) {
                m2A.put(String.valueOf(i), (long)i);
                m2B.put(String.valueOf(i), (long)0);
            }
        });

        scheduleConcurrently(1, ignored_task_num -> {
            CorfuRuntime currentRuntime = getMyRuntime();
            for (int i = 0; i < PARAMETERS.NUM_ITERATIONS_VERY_LOW; i++) {
                MultiCheckpointWriter mcw1 = new MultiCheckpointWriter();
                mcw1.addMap((SMRMap) m2A);
                mcw1.addMap((SMRMap) m2B);
                long firstGlobalAddress1 = mcw1.appendCheckpoints(currentRuntime, author);
            }
        });

        scheduleConcurrently(PARAMETERS.NUM_ITERATIONS_LOW, ignored_task_num -> {
            setRuntime();
            Map<String, Long> localm2A = instantiateMap(streamNameA);
            Map<String, Long> localm2B = instantiateMap(streamNameB);
            for (int i = 0; i < PARAMETERS.NUM_ITERATIONS_MODERATE; i++) {
                assertThat(localm2A.get(String.valueOf(i)) == null ||
                        localm2A.get(String.valueOf(i)) == (long) i
                ).isTrue();
                assertThat(localm2B.get(String.valueOf(i)) == null ||
                        localm2B.get(String.valueOf(i)) == (long) 0
                ).isTrue();
            }
        });

        executeScheduled(PARAMETERS.CONCURRENCY_SOME, PARAMETERS.TIMEOUT_LONG);

        setRuntime();
        Map<String, Long> localm2A = instantiateMap(streamNameA);
        Map<String, Long> localm2B = instantiateMap(streamNameB);
        for (int i = 0; i < PARAMETERS.NUM_ITERATIONS_MODERATE; i++) {
            assertThat(localm2A.get(String.valueOf(i)) ).isEqualTo((long)i);
            assertThat(localm2B.get(String.valueOf(i)) ).isEqualTo(0L);
        }

    }

    @Test
    public void emptyCkpointTest() throws Exception {
        final String streamNameA = "mystreamA";
        final String streamNameB = "mystreamB";
        final String author = "periodicCkpoint";

        myRuntime = getDefaultRuntime().connect();

        Map<String, Long> m2A = instantiateMap(streamNameA);
        Map<String, Long> m2B = instantiateMap(streamNameB);

        scheduleConcurrently(1, ignored_task_num -> {
            CorfuRuntime currentRuntime = getMyRuntime();
            for (int i = 0; i < PARAMETERS.NUM_ITERATIONS_VERY_LOW; i++) {
                MultiCheckpointWriter mcw1 = new MultiCheckpointWriter();
                mcw1.addMap((SMRMap) m2A);
                mcw1.addMap((SMRMap) m2B);
                long firstGlobalAddress1 = mcw1.appendCheckpoints(currentRuntime, author);
            }
        });

        scheduleConcurrently(PARAMETERS.NUM_ITERATIONS_LOW, ignored_task_num -> {
            setRuntime();
            Map<String, Long> localm2A = instantiateMap(streamNameA);
            Map<String, Long> localm2B = instantiateMap(streamNameB);
            for (int i = 0; i < PARAMETERS.NUM_ITERATIONS_MODERATE; i++) {
                assertThat(localm2A.get(String.valueOf(i)) ).isNull();
                assertThat(localm2B.get(String.valueOf(i)) ).isNull();
            }
        });

        executeScheduled(PARAMETERS.CONCURRENCY_SOME, PARAMETERS.TIMEOUT_LONG);

        setRuntime();
        Map<String, Long> localm2A = instantiateMap(streamNameA);
        Map<String, Long> localm2B = instantiateMap(streamNameB);
        for (int i = 0; i < PARAMETERS.NUM_ITERATIONS_MODERATE; i++) {
            assertThat(localm2A.get(String.valueOf(i)) ).isNull();
            assertThat(localm2B.get(String.valueOf(i)) ).isNull();
        }

    }

    @Test
    public void periodicCkpointTestNoUpdates() throws Exception {
        final String streamNameA = "mystreamA";
        final String streamNameB = "mystreamB";
        final String author = "periodicCkpoint";

        myRuntime = getDefaultRuntime().connect();

        Map<String, Long> m2A = instantiateMap(streamNameA);
        Map<String, Long> m2B = instantiateMap(streamNameB);

        // pre-populate map
        for (int i = 0; i < PARAMETERS.NUM_ITERATIONS_MODERATE; i++) {
            m2A.put(String.valueOf(i), (long)i);
            m2B.put(String.valueOf(i), (long)0);
        }

        scheduleConcurrently(1, ignored_task_num -> {
            CorfuRuntime currentRuntime = getMyRuntime();
            for (int i = 0; i < PARAMETERS.NUM_ITERATIONS_VERY_LOW; i++) {
                MultiCheckpointWriter mcw1 = new MultiCheckpointWriter();
                mcw1.addMap((SMRMap) m2A);
                mcw1.addMap((SMRMap) m2B);
                mcw1.appendCheckpoints(currentRuntime, author);
            }
        });

        scheduleConcurrently(PARAMETERS.NUM_ITERATIONS_LOW, ignored_task_num -> {
            setRuntime();
            Map<String, Long> localm2A = instantiateMap(streamNameA);
            Map<String, Long> localm2B = instantiateMap(streamNameB);
            for (int i = 0; i < PARAMETERS.NUM_ITERATIONS_MODERATE; i++) {
                assertThat(localm2A.get(String.valueOf(i))).isEqualTo((long) i);
                assertThat(localm2B.get(String.valueOf(i)) ).isEqualTo((long) 0);
            }
        });

        executeScheduled(PARAMETERS.CONCURRENCY_SOME, PARAMETERS.TIMEOUT_LONG);

        setRuntime();
        Map<String, Long> localm2A = instantiateMap(streamNameA);
        Map<String, Long> localm2B = instantiateMap(streamNameB);
        for (int i = 0; i < PARAMETERS.NUM_ITERATIONS_MODERATE; i++) {
            assertThat(localm2A.get(String.valueOf(i)) ).isEqualTo((long)i);
            assertThat(localm2B.get(String.valueOf(i)) ).isEqualTo(0L);
        }

    }

    @Test
    public void periodicCkpointTrimTest() throws Exception {
        final String streamNameA = "mystreamA";
        final String streamNameB = "mystreamB";
        final String author = "periodicCkpoint";
        final int mapSize = PARAMETERS.NUM_ITERATIONS_MODERATE;

        myRuntime = getDefaultRuntime().connect();

        Map<String, Long> m2A = instantiateMap(streamNameA);
        Map<String, Long> m2B = instantiateMap(streamNameB);

        scheduleConcurrently(1, ignored_task_num -> {
            for (int i = 0; i < mapSize; i++) {
                m2A.put(String.valueOf(i), (long)i);
                m2B.put(String.valueOf(i), (long)i);
            }
        });

        scheduleConcurrently(1, ignored_task_num -> {
            CorfuRuntime currentRuntime = getMyRuntime();
            for (int i = 0; i < PARAMETERS.NUM_ITERATIONS_VERY_LOW; i++) {

                // i'th checkpoint
                MultiCheckpointWriter mcw1 = new MultiCheckpointWriter();
                mcw1.addMap((SMRMap) m2A);
                mcw1.addMap((SMRMap) m2B);
                long checkpointAddress = mcw1.appendCheckpoints(currentRuntime, author);

                // Trim the log
                currentRuntime.getAddressSpaceView().prefixTrim(checkpointAddress - 1);
                currentRuntime.getAddressSpaceView().gc();
                currentRuntime.getAddressSpaceView().invalidateServerCaches();
                currentRuntime.getAddressSpaceView().invalidateClientCache();

            }
        });

        scheduleConcurrently(PARAMETERS.NUM_ITERATIONS_LOW, ignored_task_num -> {
            setRuntime();
            Map<String, Long> localm2A = instantiateMap(streamNameA);
            Map<String, Long> localm2B = instantiateMap(streamNameB);

            int currentMapSize = localm2A.size() >= localm2B.size() ? localm2A.size() : localm2B.size();
            for (int i = 0; i < currentMapSize; i++) {
                assertThat(localm2A.get(String.valueOf(i))).isEqualTo((long) i);
                assertThat(localm2B.get(String.valueOf(i))).isEqualTo((long) i);
            }
        });

        executeScheduled(PARAMETERS.CONCURRENCY_SOME, PARAMETERS.TIMEOUT_LONG);

        setRuntime();
        Map<String, Long> localm2A = instantiateMap(streamNameA);
        Map<String, Long> localm2B = instantiateMap(streamNameB);
        for (int i = 0; i < mapSize; i++) {
            assertThat(localm2A.get(String.valueOf(i)) ).isEqualTo((long)i);
            assertThat(localm2A).hasSize(mapSize);
            assertThat(localm2B.get(String.valueOf(i)) ).isEqualTo((long)i);
            assertThat(localm2B).hasSize(mapSize);
        }

    }


}
