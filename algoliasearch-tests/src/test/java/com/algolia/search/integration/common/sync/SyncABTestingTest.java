package com.algolia.search.integration.common.sync;

import static org.assertj.core.api.Assertions.assertThat;

import com.algolia.search.AlgoliaObject;
import com.algolia.search.Analytics;
import com.algolia.search.Index;
import com.algolia.search.SyncAlgoliaIntegrationTest;
import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.analytics.ABTest;
import com.algolia.search.inputs.analytics.Variant;
import com.algolia.search.integration.common.async.AsyncABTestingTest;
import com.algolia.search.objects.tasks.sync.TaskABTest;
import com.algolia.search.responses.ABTests;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public abstract class SyncABTestingTest extends SyncAlgoliaIntegrationTest {

  @Test
  public void createModifyAndDeleteABTests() throws AlgoliaException {
    Analytics analytics = createAnalytics();
    int offset = 0;
    int limit = 10;

    ABTests abTests = analytics.GetABTests(offset, limit);
    List<Long> idsToRemove = new ArrayList<>();

    while (abTests.count != 0) {
      for (ABTest abtest : abTests.abtests) {
        idsToRemove.add(abtest.abTestID);
      }
      offset += limit;
      abTests = analytics.GetABTests(offset, limit);
    }

    List<TaskABTest> tasks = new ArrayList<>();
    for (long id : idsToRemove) {
      tasks.add(analytics.DeleteABTest(id));
    }
    for (TaskABTest task : tasks) {
      waitForCompletion(task);
    }

    Index<AlgoliaObject> i1 = createIndex(AlgoliaObject.class);
    Index<AlgoliaObject> i2 = createIndex(AlgoliaObject.class);

    waitForCompletion(i1.addObject(new AlgoliaObject("algolia", 1)));
    waitForCompletion(i2.addObject(new AlgoliaObject("algolia", 1)));

    LocalDateTime now = LocalDateTime.now();

    ABTest abtest =
        new ABTest(
            "abtest_name",
            Arrays.asList(
                new Variant(i1.getName(), 60, "a description"),
                new Variant(i2.getName(), 40, null)),
            now.plus(1, ChronoUnit.DAYS));

    waitForCompletion(analytics.AddABTest(abtest));

    abTests = analytics.GetABTests(0, 10);
    assertThat(abTests.count).isEqualTo(1);
    assertThat(abTests.total).isEqualTo(1);
    assertThat(abTests.abtests).hasSize(1);

    ABTest inserted = abTests.abtests.get(0);
    assertThat(inserted.endAt).isEqualTo(abtest.endAt);
    assertThat(inserted.name).isEqualTo(abtest.name);
    assertThat(inserted.status).isEqualTo("active");
    assertThat(inserted.variants).hasSize(2);
    AsyncABTestingTest.compareABTests(abtest, inserted);

    waitForCompletion(analytics.StopABTest(inserted.abTestID));
    inserted = analytics.GetABTest(inserted.abTestID);
    assertThat(inserted.status).isEqualTo("stopped");

    waitForCompletion(analytics.DeleteABTest(inserted.abTestID));
    abTests = analytics.GetABTests(0, 10);
    assertThat(abTests.count).isEqualTo(0);
  }
}
