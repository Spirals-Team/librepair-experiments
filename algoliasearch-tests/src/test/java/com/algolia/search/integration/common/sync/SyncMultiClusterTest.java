package com.algolia.search.integration.common.sync;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.objects.Cluster;
import com.algolia.search.objects.UserID;
import com.algolia.search.responses.AssignUserID;
import com.algolia.search.responses.DeleteUserID;
import com.algolia.search.responses.UserIDs;
import java.util.*;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;

public abstract class SyncMultiClusterTest extends SyncAlgoliaIntegrationTest {

  private static List<String> userIDsToDeleteAfterTheTests = new ArrayList<>();

  private static String createUniqueUserID() {
    String uniqueUserID = "java2-tests-" + UUID.randomUUID().toString();
    userIDsToDeleteAfterTheTests.add(uniqueUserID);
    return uniqueUserID;
  }

  @Before
  public void checkEnvVariables() throws Exception {
    ALGOLIA_APPLICATION_ID = System.getenv("ALGOLIA_APPLICATION_ID_MCM");
    ALGOLIA_API_KEY = System.getenv("ALGOLIA_API_KEY_MCM");

    super.checkEnvVariables();
  }

  @Test
  public void testMCMUsage() throws AlgoliaException {
    String year = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));

    Map<String, List<UserID>> top = client.getTopUserID();
    assertThat(top.size()).isGreaterThan(0);

    List<Cluster> clusters = client.listClusters();
    assertThat(clusters.size()).isGreaterThan(0);

    UserIDs userIds = client.listUserIDs(0, 2);
    assertThat(userIds.getHitsPerPage()).isEqualTo(2);
    assertThat(userIds.getPage()).isEqualTo(0);
    assertThat(userIds.getUserIDs().size()).isEqualTo(2);

    String clusterName = clusters.get(0).getClusterName();
    String userID = createUniqueUserID();

    AssignUserID res = client.assignUserID(userID, clusterName);
    assertThat(res.getCreatedAt()).contains(year);

    assertUserWasCreated(userID);

    DeleteUserID deleted = client.removeUserID(userID);
    assertThat(deleted.getDeletedAt()).contains(year);
    assertUserWasRemoved(userID);
  }

  private void assertUserWasCreated(String userID) {
    int retry = 10;

    while (retry > 0) {
      try {
        UserID userIDFromAPI = client.getUserID(userID);
        assertThat(userIDFromAPI.getUserID()).isEqualToIgnoringCase(userID);
        return;
      } catch (AlgoliaException e) {
        try {
          TimeUnit.SECONDS.sleep(10 - retry);
        } catch (InterruptedException e1) {
          e1.printStackTrace();
        }
        retry--;
      }
    }
  }

  private void assertUserWasRemoved(String userID) {
    int retry = 10;

    while (retry > 0) {
      try {
        UserID unused = client.getUserID(userID);
        TimeUnit.SECONDS.sleep(10 - retry);
        retry--;
      } catch (AlgoliaException | InterruptedException e) {
        assertTrue(true);
        return;
      }
    }
  }
}
