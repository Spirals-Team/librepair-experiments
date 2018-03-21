package fk.prof.backend.model.election.impl;

import fk.prof.backend.model.election.LeaderReadContext;
import fk.prof.backend.model.election.LeaderWriteContext;
import fk.prof.backend.util.proto.BackendProtoUtil;
import fk.prof.idl.Backend;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

public class InMemoryLeaderStore implements LeaderReadContext, LeaderWriteContext {
  private static Logger logger = LoggerFactory.getLogger(InMemoryLeaderStore.class);

  private final Backend.LeaderDetail selfLeaderDetail;
  private volatile Backend.LeaderDetail currentLeaderDetail = null;
  private volatile boolean leader = false;

  public InMemoryLeaderStore(String ipAddress, int leaderPort) {
    this.selfLeaderDetail = Backend.LeaderDetail.newBuilder().setHost(ipAddress).setPort(leaderPort).build();
  }

  @Override
  public synchronized void setLeader(Backend.LeaderDetail leaderDetail) {
    if (leaderDetail == null) {
      logger.info(String.format("Removing backend node as leader. Node IP = %s",
          this.currentLeaderDetail == null ? "" : BackendProtoUtil.leaderDetailCompactRepr(leaderDetail)));
    } else {
      logger.info(String.format("Setting backend leader. Node IP = %s", BackendProtoUtil.leaderDetailCompactRepr(leaderDetail)));
    }
    this.currentLeaderDetail = leaderDetail;
    this.leader = this.currentLeaderDetail != null && this.currentLeaderDetail.equals(selfLeaderDetail);
  }

  @Override
  public Backend.LeaderDetail getLeader() {
    return this.currentLeaderDetail;
  }

  @Override
  public boolean isLeader() {
    return this.leader;
  }
}
