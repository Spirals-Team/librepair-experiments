package org.corfudb.runtime.checkpoint;

import static org.assertj.core.api.Assertions.assertThat;
import com.google.common.reflect.TypeToken;

import java.util.Map;

import org.corfudb.runtime.MultiCheckpointWriter;
import org.corfudb.runtime.collections.SMRMap;
import org.corfudb.runtime.exceptions.TransactionAbortedException;
import org.corfudb.runtime.exceptions.TrimmedException;
import org.corfudb.runtime.object.transactions.TransactionType;
import org.corfudb.runtime.view.AbstractViewTest;
import org.corfudb.runtime.view.ObjectOpenOptions;
import org.junit.Test;

/**
 * Created by mwei on 5/25/17.
 */
public class CheckpointTrimTest extends AbstractViewTest {

    @Test
    public void testCheckpointTrim() throws Exception {
        Map<String, String> testMap = getDefaultRuntime().getObjectsView().build()
                                            .setTypeToken(new TypeToken<SMRMap<String, String>>() {})
                                            .setStreamName("test")
                                            .open();

        // Place 3 entries into the map
        testMap.put("a", "a");
        testMap.put("b", "b");
        testMap.put("c", "c");

        // Insert a checkpoint
        MultiCheckpointWriter mcw = new MultiCheckpointWriter();
        mcw.addMap((SMRMap) testMap);
        long checkpointAddress = mcw.appendCheckpoints(getRuntime(), "author");

        // Trim the log
        getRuntime().getAddressSpaceView().prefixTrim(checkpointAddress - 1);
        getRuntime().getAddressSpaceView().gc();
        getRuntime().getAddressSpaceView().invalidateServerCaches();
        getRuntime().getAddressSpaceView().invalidateClientCache();

        // Ok, get a new view of the map
        Map<String, String> newTestMap = getDefaultRuntime().getObjectsView().build()
                .setTypeToken(new TypeToken<SMRMap<String, String>>() {})
                .addOption(ObjectOpenOptions.NO_CACHE)
                .setStreamName("test")
                .open();

        // Reading an entry from scratch should be ok
        assertThat(newTestMap)
                .containsKeys("a", "b", "c");
    }

    @Test
    public void testSuccessiveCheckpointTrim() throws Exception {
        final int nCheckpoints = 2;
        final long ckpointGap = 5;

        Map<String, String> testMap = getDefaultRuntime().getObjectsView().build()
                .setTypeToken(new TypeToken<SMRMap<String, String>>() {})
                .setStreamName("test")
                .open();

        long checkpointAddress = -1;
        // generate two successive checkpoints
        for (int ckpoint = 0; ckpoint < nCheckpoints; ckpoint++) {
            // Place 3 entries into the map
            testMap.put("a", "a"+ckpoint);
            testMap.put("b", "b"+ckpoint);
            testMap.put("c", "c"+ckpoint);

            // Insert a checkpoint
            MultiCheckpointWriter mcw = new MultiCheckpointWriter();
            mcw.addMap((SMRMap) testMap);
            checkpointAddress = mcw.appendCheckpoints(getRuntime(), "author");
        }

        // Trim the log in between the checkpoints
        getRuntime().getAddressSpaceView().prefixTrim(checkpointAddress - ckpointGap - 1);
        getRuntime().getAddressSpaceView().gc();
        getRuntime().getAddressSpaceView().invalidateServerCaches();
        getRuntime().getAddressSpaceView().invalidateClientCache();

        // Ok, get a new view of the map
        Map<String, String> newTestMap = getDefaultRuntime().getObjectsView().build()
                .setTypeToken(new TypeToken<SMRMap<String, String>>() {})
                .addOption(ObjectOpenOptions.NO_CACHE)
                .setStreamName("test")
                .open();

        boolean snapshotTransactionCompleted = true;

        // try to get a snapshot inside the gap
        getRuntime().getObjectsView()
                .TXBuild()
                .setType(TransactionType.SNAPSHOT)
                .setSnapshot(checkpointAddress-1)
                .begin();

        try {
            // Reading an entry from this snapshot should result in a TransactionAbortedException
            // Given the state of the stream:
            // (1) Regular Stream: 0 1 2
            //     CP Stream:      3 4 5
            // (2) Regular Stream: 0 1 2 6 7 8
            //     CP Stream:      3 4 5 9 10 11 ----> (second checkpoint subsumes the first cp) therefore it will result in
            // ---->               9 10 11
            // (3) Trim from address 2 results in:
            //     Regular Stream: 6 7 8
            //     CP Stream:      9 10 11
            // A snapshot transaction @7 is NOT possible, as the checkpoint subsumes the state of the stream from address 8.

            newTestMap.get("a");
        } catch (Exception e) {
            snapshotTransactionCompleted = false;
            assertThat(e).isInstanceOf(TransactionAbortedException.class);
            assertThat(e.getCause()).isInstanceOf(TrimmedException.class);
        }

        assertThat(snapshotTransactionCompleted).isEqualTo(false);
    }


    @Test
    public void testCheckpointTrimDuringPlayback() throws Exception {
        Map<String, String> testMap = getDefaultRuntime().getObjectsView().build()
                .setTypeToken(new TypeToken<SMRMap<String, String>>() {})
                .setStreamName("test")
                .open();

        // Place 3 entries into the map
        testMap.put("a", "a");
        testMap.put("b", "b");
        testMap.put("c", "c");

        // Ok, get a new view of the map
        Map<String, String> newTestMap = getDefaultRuntime().getObjectsView().build()
                .setTypeToken(new TypeToken<SMRMap<String, String>>() {})
                .addOption(ObjectOpenOptions.NO_CACHE)
                .setStreamName("test")
                .open();

        // Play the new view up to "b" only
        getRuntime().getObjectsView().TXBuild()
                .setType(TransactionType.SNAPSHOT)
                .setSnapshot(1)
                .begin();

        assertThat(newTestMap)
                .containsKeys("a", "b")
                .hasSize(2);

        getRuntime().getObjectsView().TXEnd();

        // Insert a checkpoint
        MultiCheckpointWriter mcw = new MultiCheckpointWriter();
        mcw.addMap((SMRMap) testMap);
        long checkpointAddress = mcw.appendCheckpoints(getRuntime(), "author");

        // Trim the log
        getRuntime().getAddressSpaceView().prefixTrim(checkpointAddress - 1);
        getRuntime().getAddressSpaceView().gc();
        getRuntime().getAddressSpaceView().invalidateServerCaches();
        getRuntime().getAddressSpaceView().invalidateClientCache();


        // Sync should encounter trim exception, reset, and use checkpoint
        assertThat(newTestMap)
                 .containsKeys("a", "b", "c");
    }
}
