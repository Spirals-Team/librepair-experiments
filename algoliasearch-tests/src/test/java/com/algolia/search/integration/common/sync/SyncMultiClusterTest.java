package com.algolia.search.integration.common.sync;

import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.objects.Cluster;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public abstract class SyncMultiClusterTest extends SyncAlgoliaIntegrationTest {

  @Before
  public void checkEnvVariables() throws Exception {
    APPLICATION_ID = System.getenv("ALGOLIA_APPLICATION_ID_MCM");
    API_KEY = System.getenv("ALGOLIA_API_KEY_MCM");

    super.checkEnvVariables();
  }

  @Test
  public void listClusters() throws AlgoliaException {
    List<Cluster> clusters = client.listClusters();
    System.out.println(clusters.get(0));
  }
}
