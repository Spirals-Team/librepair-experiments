/*
 * Licensed to Metamarkets Group Inc. (Metamarkets) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. Metamarkets licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.druid.client.indexing;

import io.druid.indexer.TaskStatusPlus;
import io.druid.timeline.DataSegment;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class NoopIndexingServiceClient implements IndexingServiceClient
{
  @Override
  public void killSegments(String dataSource, Interval interval)
  {

  }

  @Override
  public int killPendingSegments(String dataSource, DateTime end)
  {
    return 0;
  }

  @Override
  public void upgradeSegment(DataSegment dataSegment)
  {

  }

  @Override
  public void mergeSegments(List<DataSegment> segments)
  {
    
  }

  @Override
  public String compactSegments(
      List<DataSegment> segments,
      int compactionTaskPriority,
      @Nullable ClientCompactQueryTuningConfig tuningConfig,
      @Nullable Map<String, Object> context
  )
  {
    return null;
  }

  @Override
  public int getTotalWorkerCapacity()
  {
    return 0;
  }

  @Override
  public String runTask(Object taskObject)
  {
    return null;
  }

  @Override
  public String killTask(String taskId)
  {
    return null;
  }

  @Override
  public List<TaskStatusPlus> getRunningTasks()
  {
    return null;
  }

  @Override
  public List<TaskStatusPlus> getPendingTasks()
  {
    return null;
  }

  @Override
  public List<TaskStatusPlus> getWaitingTasks()
  {
    return null;
  }

  @Override
  public TaskStatusResponse getTaskStatus(String taskId)
  {
    return new TaskStatusResponse(taskId, null);
  }

  @Nullable
  @Override
  public TaskStatusPlus getLastCompleteTask()
  {
    return null;
  }
}
