package com.searchcode.app.service;

import com.searchcode.app.dto.CodeIndexDocument;
import junit.framework.TestCase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class SphinxIndexServiceTest extends TestCase {
    public void testSearch() {
        SphinxIndexService sphinxIndexService = new SphinxIndexService();

        List<String> someList = new ArrayList<>();

        for (int i=0; i < 100; i++) {
            someList.add("" + i);
        }

        someList.parallelStream()
                .forEach(x -> sphinxIndexService.search("test", null, 0, false));
    }

    private CodeIndexDocument codeIndexDocument = new CodeIndexDocument("repoLocationRepoNameLocationFilename",
            "this is a repositoryname",
            "fileName",
            "fileLocation",
            "fileLocationFilename",
            "md5hash",
            "language name",
            100,
            "this is some content to search on test",
            "repoRemoteLocation",
            "owner",
            "mydisplaylocation",
            "source");


    public void testGetShardCountExpectingZero() {
        SphinxIndexService sphinxIndexService = new SphinxIndexService();
        assertThat(sphinxIndexService.getShardCount("")).isZero();
        assertThat(sphinxIndexService.getShardCount("localhost:")).isZero();
    }

    public void testGetShardCountExpectingTwo() {
        SphinxIndexService sphinxIndexService = new SphinxIndexService();
        assertThat(sphinxIndexService.getShardCount("localhost:1,2")).isEqualTo(2);
        assertThat(sphinxIndexService.getShardCount("localhost:1;localhost:2")).isEqualTo(2);
    }

    public void testGetShardCountExpectingFour() {
        SphinxIndexService sphinxIndexService = new SphinxIndexService();
        assertThat(sphinxIndexService.getShardCount("localhost:1,2,3,4")).isEqualTo(4);
        assertThat(sphinxIndexService.getShardCount("localhost:1,2;localhost:3,4")).isEqualTo(4);
    }

//    public void testIndexDocumentEndToEnd() throws IOException {
////        SphinxIndexService sphinxIndexService = new SphinxIndexService();
////
////        Queue<CodeIndexDocument> queue = new ConcurrentLinkedQueue<>();
////        queue.add(this.codeIndexDocument);
////        sphinxIndexService.indexDocument(queue);
//
//        for (int i=1; i <= 100; i++) {
//            int mod = i % 8;
//
//            mod = mod == 0 ? 8 : mod;
//
//            System.out.println(i + " shard " + mod);
//        }
//    }
}
