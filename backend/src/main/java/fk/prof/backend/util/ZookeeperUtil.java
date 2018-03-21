package fk.prof.backend.util;

import io.vertx.core.Future;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.KeeperException;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;

public class ZookeeperUtil {
    public static final String DELIMITER = "/";

  public static byte[] readZNode(CuratorFramework curatorClient, String zNodePath)
      throws Exception {
    return curatorClient.getData().forPath(zNodePath);
  }

  public static Map.Entry<String, byte[]> readLatestSeqZNodeChild(CuratorFramework curatorClient, String curatorPath) throws Exception {
    String zNodePath = curatorPath;
    if (!curatorClient.getNamespace().isEmpty()) {
      zNodePath = DELIMITER + curatorClient.getNamespace() + curatorPath;
    }
    List<String> sortedPolicyNodes = ZKPaths.getSortedChildren(curatorClient.getZookeeperClient().getZooKeeper(), zNodePath);
    if (sortedPolicyNodes.isEmpty()) {
      return null;
    }
    String nodeName = sortedPolicyNodes.get(sortedPolicyNodes.size() - 1);
    return new AbstractMap.SimpleEntry<>(nodeName, readZNode(curatorClient, curatorPath + DELIMITER + nodeName));
  }

  public static String getLatestSeqZNodeChildName(CuratorFramework curatorClient, String curatorPath) throws Exception {
    String zNodePath = curatorPath;
    if (!curatorClient.getNamespace().isEmpty()) {
      zNodePath = DELIMITER + curatorClient.getNamespace() + curatorPath;
    }
    List<String> sortedPolicyNodes = ZKPaths.getSortedChildren(curatorClient.getZookeeperClient().getZooKeeper(), zNodePath);
    if (sortedPolicyNodes.isEmpty()) {
      return null;
    }
    return sortedPolicyNodes.get(sortedPolicyNodes.size() - 1);
  }

  public static void writeZNode(CuratorFramework curatorClient, String zNodePath, byte[] data, boolean create)
      throws Exception {
    if (create) {
      curatorClient.create().forPath(zNodePath, data);
    } else {
      curatorClient.setData().forPath(zNodePath, data);
    }
  }

  public static Future<Void> sync(CuratorFramework curatorClient, String zNodePath) {
    Future<Void> future = Future.future();
    try {
      curatorClient.sync().inBackground((client, event) -> {
        if (KeeperException.Code.OK.intValue() == event.getResultCode()) {
          future.complete();
        } else {
          future.fail(new RuntimeException("Error when zk sync issued for node path = " + zNodePath + " with result code = " + event.getResultCode()));
        }
      }).forPath(zNodePath);
    } catch (Exception ex) {
      future.fail(ex);
    }
    return future;
  }
}
