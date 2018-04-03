/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.cassandrareaper.storage;

import io.cassandrareaper.core.Stream;
import io.cassandrareaper.core.StreamSession;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import com.google.common.collect.Maps;
import org.apache.cassandra.streaming.ProgressInfo;
import org.apache.cassandra.streaming.SessionInfo;

public class StreamCache {

  /**
   * Cluster -> PlanId -> StreamSession
   */
  private ConcurrentMap<String, ConcurrentMap<String, StreamSession>> plansByCluster = Maps.newConcurrentMap();

  public void initSession(String clusterName, String planId, String host, SessionInfo sessionInfo, long timestamp) {

    plansByCluster.computeIfAbsent(clusterName, this::emptySessionsForCluster);
    plansByCluster.computeIfPresent(clusterName, (oldClusterName, oldSessions) -> {
      oldSessions.computeIfAbsent(planId, StreamSession::empty);
      oldSessions.computeIfPresent(
          planId,
          (id, oldSession) -> oldSession.addStream(host, sessionInfo, timestamp)
      );
      return oldSessions;
    });

  }

  public void updateSession(String clusterName, String planId, String host, ProgressInfo progressInfo, long timeStamp) {

    plansByCluster.computeIfAbsent(clusterName, this::emptySessionsForCluster);
    plansByCluster.computeIfPresent(clusterName, (oldClusterName, oldStreamSessions) -> {
      oldStreamSessions.computeIfAbsent(planId, StreamSession::empty);
      oldStreamSessions.computeIfPresent(
          planId,
          (id, oldSession) -> oldSession.updateStream(host, progressInfo, timeStamp)
      );
      return oldStreamSessions;
    });

  }

  private ConcurrentMap<String, StreamSession> emptySessionsForCluster(String cluster) {
    return Maps.newConcurrentMap();
  }

  public void finalizeSession(String clusterName,
                              String planId,
                              String host,
                              String peer,
                              Boolean success,
                              long timeStamp
  ) {
    plansByCluster.computeIfAbsent(clusterName, this::emptySessionsForCluster);
    plansByCluster.computeIfPresent(clusterName, (oldClusterName, oldStreamSessions) -> {
      oldStreamSessions.computeIfPresent(
          planId,
          (id, oldSession) -> oldSession.finalizeStream(host, peer, success, timeStamp)
      );
      return oldStreamSessions;

    });

    // TODO move to expiring cache
  }

  public Map<String, StreamSession> get(String clusterName) {
    return plansByCluster.getOrDefault(clusterName, Maps.newConcurrentMap());
  }

  public List<Stream> get(String cluster, String node) {
    ConcurrentMap<String, StreamSession> clusterStreams = plansByCluster.getOrDefault(cluster, Maps.newConcurrentMap());
    return filterStreamsFromNode(clusterStreams, node);
  }

  private List<Stream> filterStreamsFromNode(ConcurrentMap<String, StreamSession> clusterStreams, String node) {
    List<Stream> streamsFromNode =
        // from all known StreamSessions for this cluster
        clusterStreams.values().stream()
        // take the Streams
        .map(streamSession -> streamSession.getStreams().entrySet())
        // concatenate Streams from all the sessions
        .flatMap(Collection::stream)
        // streams are a map of streamId -> Stream, we need just the Stream
        .map(Map.Entry::getValue)
        // finally, we want only the streams featuring the given node
        .filter(stream -> stream.getHost().equals(node) || stream.getPeer().equals(node))
        // and we want them as a List
        .collect(Collectors.toList());

    return streamsFromNode;
  }

}
