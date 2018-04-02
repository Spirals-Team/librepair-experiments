package io.cassandrareaper.storage;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;


import com.google.common.collect.Maps;
import io.cassandrareaper.core.Stream;
import io.cassandrareaper.core.StreamSession;
import org.apache.cassandra.streaming.ProgressInfo;
import org.apache.cassandra.streaming.SessionInfo;

public class StreamCache {

  // Stream :: host to host, list of files
  // StreamSession :: plan ID, map host-to-host -> Stream
  // StreamCache :: cluster name, set of stream sessions

  private ConcurrentMap<String, ConcurrentMap<String, StreamSession>> plansByCluster = Maps.newConcurrentMap();

  /**
   *
   * @param cluster
   * @param planId
   * @param host
   * @param sessionInfo
   * @param timestamp
   */
  public void initSession(String cluster, String planId, String host, SessionInfo sessionInfo, long timestamp) {

    plansByCluster.computeIfAbsent(cluster, this::emptySessionsForCluster);
    plansByCluster.computeIfPresent(cluster, (c, oldSessions) -> {
      oldSessions.computeIfAbsent(planId, StreamSession::empty);
      oldSessions.computeIfPresent(
          planId,
          (id, oldSession) -> oldSession.addStream(host, sessionInfo, timestamp)
      );
      return oldSessions;
    });

  }

  /**
   *
   * @param cluster
   * @param planId
   * @param host
   * @param progressInfo
   * @param timeStamp
   */
  public void updateSession(String cluster, String planId, String host, ProgressInfo progressInfo, long timeStamp) {

    plansByCluster.computeIfAbsent(cluster, this::emptySessionsForCluster);
    plansByCluster.computeIfPresent(cluster, (c, streamSessions) -> {
      streamSessions.computeIfAbsent(planId, StreamSession::empty);
      streamSessions.computeIfPresent(
          planId,
          (id, oldSession) -> oldSession.updateStream(host, progressInfo, timeStamp)
      );
      return streamSessions;
    });

  }

  private ConcurrentMap<String, StreamSession> emptySessionsForCluster(String cluster) {
    return Maps.newConcurrentMap();
  }

  /**
   *
   * @param cluster
   * @param host
   * @param peer
   * @param success
   * @param timeStamp
   */
  public void finalizeSession(String cluster, String planId, String host, String peer, Boolean success, long timeStamp) {

    plansByCluster.computeIfAbsent(cluster, this::emptySessionsForCluster);
    plansByCluster.computeIfPresent(cluster, (c, streamSessions) -> {
      streamSessions.computeIfPresent(
          planId,
          (id, oldSession) -> oldSession.finalizeStream(host, peer, success, timeStamp)
      );
      return streamSessions;

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
