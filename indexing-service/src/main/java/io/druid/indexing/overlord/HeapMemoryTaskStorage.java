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

package io.druid.indexing.overlord;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Ordering;
import com.google.inject.Inject;
import io.druid.indexing.common.TaskLock;
import io.druid.indexing.common.TaskStatus;
import io.druid.indexing.common.actions.TaskAction;
import io.druid.indexing.common.config.TaskStorageConfig;
import io.druid.indexing.common.task.Task;
import io.druid.java.util.common.DateTimes;
import io.druid.java.util.common.Pair;
import io.druid.java.util.common.logger.Logger;
import io.druid.metadata.EntryExistsException;
import org.joda.time.DateTime;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * Implements an in-heap TaskStorage facility, with no persistence across restarts. This class is not
 * thread safe.
 */
public class HeapMemoryTaskStorage implements TaskStorage
{
  private final TaskStorageConfig config;

  private final ReentrantLock giant = new ReentrantLock();
  private final Map<String, TaskStuff> tasks = Maps.newHashMap();
  private final Multimap<String, TaskLock> taskLocks = HashMultimap.create();
  private final Multimap<String, TaskAction> taskActions = ArrayListMultimap.create();

  private static final Logger log = new Logger(HeapMemoryTaskStorage.class);

  @Inject
  public HeapMemoryTaskStorage(TaskStorageConfig config)
  {
    this.config = config;
  }

  @Override
  public void insert(Task task, TaskStatus status) throws EntryExistsException
  {
    giant.lock();

    try {
      Preconditions.checkNotNull(task, "task");
      Preconditions.checkNotNull(status, "status");
      Preconditions.checkArgument(
          task.getId().equals(status.getId()),
          "Task/Status ID mismatch[%s/%s]",
          task.getId(),
          status.getId()
      );

      if (tasks.containsKey(task.getId())) {
        throw new EntryExistsException(task.getId());
      }

      log.info("Inserting task %s with status: %s", task.getId(), status);
      tasks.put(task.getId(), new TaskStuff(task, status, DateTimes.nowUtc(), task.getDataSource()));
    }
    finally {
      giant.unlock();
    }
  }

  @Override
  public Optional<Task> getTask(String taskid)
  {
    giant.lock();

    try {
      Preconditions.checkNotNull(taskid, "taskid");
      if (tasks.containsKey(taskid)) {
        return Optional.of(tasks.get(taskid).getTask());
      } else {
        return Optional.absent();
      }
    }
    finally {
      giant.unlock();
    }
  }

  @Override
  public void setStatus(TaskStatus status)
  {
    giant.lock();

    try {
      Preconditions.checkNotNull(status, "status");

      final String taskid = status.getId();
      Preconditions.checkState(tasks.containsKey(taskid), "Task ID must already be present: %s", taskid);
      Preconditions.checkState(tasks.get(taskid).getStatus().isRunnable(), "Task status must be runnable: %s", taskid);
      log.info("Updating task %s to status: %s", taskid, status);
      tasks.put(taskid, tasks.get(taskid).withStatus(status));
    }
    finally {
      giant.unlock();
    }
  }

  @Override
  public Optional<TaskStatus> getStatus(String taskid)
  {
    giant.lock();

    try {
      Preconditions.checkNotNull(taskid, "taskid");
      if (tasks.containsKey(taskid)) {
        return Optional.of(tasks.get(taskid).getStatus());
      } else {
        return Optional.absent();
      }
    }
    finally {
      giant.unlock();
    }
  }

  @Override
  public List<Task> getActiveTasks()
  {
    giant.lock();

    try {
      final ImmutableList.Builder<Task> listBuilder = ImmutableList.builder();
      for (final TaskStuff taskStuff : tasks.values()) {
        if (taskStuff.getStatus().isRunnable()) {
          listBuilder.add(taskStuff.getTask());
        }
      }
      return listBuilder.build();
    }
    finally {
      giant.unlock();
    }
  }

  @Override
  public List<TaskStatus> getRecentlyFinishedTaskStatuses(@Nullable Integer maxTaskStatuses)
  {
    giant.lock();

    try {
      final Ordering<TaskStuff> createdDateDesc = new Ordering<TaskStuff>()
      {
        @Override
        public int compare(TaskStuff a, TaskStuff b)
        {
          return a.getCreatedDate().compareTo(b.getCreatedDate());
        }
      }.reverse();

      return maxTaskStatuses == null ?
             getRecentlyFinishedTaskStatusesSince(
                 System.currentTimeMillis() - config.getRecentlyFinishedThreshold().getMillis(),
                 createdDateDesc
             ) :
             getNRecentlyFinishedTaskStatuses(maxTaskStatuses, createdDateDesc);
    }
    finally {
      giant.unlock();
    }
  }

  private List<TaskStatus> getRecentlyFinishedTaskStatusesSince(long start, Ordering<TaskStuff> createdDateDesc)
  {
    giant.lock();

    try {
      return createdDateDesc
          .sortedCopy(tasks.values())
          .stream()
          .filter(taskStuff -> taskStuff.getStatus().isComplete() && taskStuff.getCreatedDate().getMillis() > start)
          .map(TaskStuff::getStatus)
          .collect(Collectors.toList());
    }
    finally {
      giant.unlock();
    }
  }

  private List<TaskStatus> getNRecentlyFinishedTaskStatuses(int n, Ordering<TaskStuff> createdDateDesc)
  {
    giant.lock();

    try {
      return createdDateDesc.sortedCopy(tasks.values())
                            .stream()
                            .limit(n)
                            .map(TaskStuff::getStatus)
                            .collect(Collectors.toList());
    }
    finally {
      giant.unlock();
    }
  }

  @Nullable
  @Override
  public Pair<DateTime, String> getCreatedDateTimeAndDataSource(String taskId)
  {
    giant.lock();

    try {
      final TaskStuff taskStuff = tasks.get(taskId);
      return taskStuff == null ? null : Pair.of(taskStuff.getCreatedDate(), taskStuff.getDataSource());
    }
    finally {
      giant.unlock();
    }
  }

  @Override
  public void addLock(final String taskid, final TaskLock taskLock)
  {
    giant.lock();

    try {
      Preconditions.checkNotNull(taskid, "taskid");
      Preconditions.checkNotNull(taskLock, "taskLock");
      taskLocks.put(taskid, taskLock);
    }
    finally {
      giant.unlock();
    }
  }

  @Override
  public void replaceLock(String taskid, TaskLock oldLock, TaskLock newLock)
  {
    giant.lock();

    try {
      Preconditions.checkNotNull(taskid, "taskid");
      Preconditions.checkNotNull(oldLock, "oldLock");
      Preconditions.checkNotNull(newLock, "newLock");

      if (!taskLocks.remove(taskid, oldLock)) {
        log.warn("taskLock[%s] for replacement is not found for task[%s]", oldLock, taskid);
      }

      taskLocks.put(taskid, newLock);
    }
    finally {
      giant.unlock();
    }
  }

  @Override
  public void removeLock(final String taskid, final TaskLock taskLock)
  {
    giant.lock();

    try {
      Preconditions.checkNotNull(taskLock, "taskLock");
      taskLocks.remove(taskid, taskLock);
    }
    finally {
      giant.unlock();
    }
  }

  @Override
  public List<TaskLock> getLocks(final String taskid)
  {
    giant.lock();

    try {
      return ImmutableList.copyOf(taskLocks.get(taskid));
    }
    finally {
      giant.unlock();
    }
  }

  @Override
  public <T> void addAuditLog(Task task, TaskAction<T> taskAction)
  {
    giant.lock();

    try {
      taskActions.put(task.getId(), taskAction);
    }
    finally {
      giant.unlock();
    }
  }

  @Override
  public List<TaskAction> getAuditLogs(String taskid)
  {
    giant.lock();

    try {
      return ImmutableList.copyOf(taskActions.get(taskid));
    }
    finally {
      giant.unlock();
    }
  }

  private static class TaskStuff
  {
    final Task task;
    final TaskStatus status;
    final DateTime createdDate;
    final String dataSource;

    private TaskStuff(Task task, TaskStatus status, DateTime createdDate, String dataSource)
    {
      Preconditions.checkArgument(task.getId().equals(status.getId()));

      this.task = Preconditions.checkNotNull(task, "task");
      this.status = Preconditions.checkNotNull(status, "status");
      this.createdDate = Preconditions.checkNotNull(createdDate, "createdDate");
      this.dataSource = Preconditions.checkNotNull(dataSource, "dataSource");
    }

    public Task getTask()
    {
      return task;
    }

    public TaskStatus getStatus()
    {
      return status;
    }

    public DateTime getCreatedDate()
    {
      return createdDate;
    }

    public String getDataSource()
    {
      return dataSource;
    }

    private TaskStuff withStatus(TaskStatus _status)
    {
      return new TaskStuff(task, _status, createdDate, dataSource);
    }
  }
}
