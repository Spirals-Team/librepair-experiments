package com.algolia.search;

import com.algolia.search.exceptions.AlgoliaException;
import com.algolia.search.inputs.analytics.ABTest;
import com.algolia.search.objects.tasks.async.AsyncTaskABTest;
import com.algolia.search.responses.ABTests;
import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

public class AsyncAnalytics implements Serializable {

  private AsyncAPIClient client;

  AsyncAnalytics(AsyncAPIClient client) {
    this.client = client;
  }

  public CompletableFuture<AsyncTaskABTest> AddABTest(ABTest abtest) {
    return client.AddABTest(abtest);
  }

  public CompletableFuture<AsyncTaskABTest> StopABTest(long id) {
    return client.StopABTest(id);
  }

  public CompletableFuture<AsyncTaskABTest> DeleteABTest(long id) {
    return client.DeleteABTest(id);
  }

  public CompletableFuture<ABTest> GetABTest(long id) {
    return client.GetABTest(id);
  }

  public CompletableFuture<ABTests> GetABTests(int offset, int limit) {
    return client.GetABTests(offset, limit);
  }

  public void WaitTask(AsyncTaskABTest task) throws AlgoliaException {
    this.client.waitTask(task);
  }
}
