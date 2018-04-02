package io.cassandrareaper.jmx;

import javax.management.openmbean.CompositeData;

public interface StreamStatusHandler {

  /**
   * Handle the notification about an event related to Cassandra streaming.
   *
   * @param clusterName name of the cluster where the streaming event occurred
   * @param host name of the node originating the notification
   * @param payload payload attached to the notification by Cassandra
   * @param timeStamp of the JMX notification
   */
  void handleNotification(String clusterName, String host, CompositeData payload, long timeStamp);

}
