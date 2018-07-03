package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.analytics.ABTest;
import com.algolia.search.objects.tasks.sync.TaskABTest;
import com.algolia.search.responses.ABTests;
import java.io.Serializable;

public class Analytics implements Serializable {

  private APIClient client;

  Analytics(APIClient client) {
    this.client = client;
  }

  public TaskABTest AddABTest(ABTest abtest) throws AlgoliaException {
    return client.AddABTest(abtest);
  }

  public TaskABTest StopABTest(long id) throws AlgoliaException {
    return client.StopABTest(id);
  }

  public TaskABTest DeleteABTest(long id) throws AlgoliaException {
    return client.DeleteABTest(id);
  }

  public ABTest GetABTest(long id) throws AlgoliaException {
    return client.GetABTest(id);
  }

  public ABTests GetABTests(int offset, int limit) throws AlgoliaException {
    return client.GetABTests(offset, limit);
  }

  public void WaitTask(TaskABTest task) throws AlgoliaException {
    this.client.waitTask(task);
  }
}
