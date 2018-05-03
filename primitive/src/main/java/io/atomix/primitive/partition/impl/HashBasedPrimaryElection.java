/*
 * Copyright 2018-present Open Networking Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.atomix.primitive.partition.impl;

import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import io.atomix.cluster.ClusterMembershipService;
import io.atomix.cluster.Member;
import io.atomix.cluster.MemberId;
import io.atomix.cluster.messaging.ClusterMessagingService;
import io.atomix.primitive.partition.GroupMember;
import io.atomix.primitive.partition.MemberGroupId;
import io.atomix.primitive.partition.PartitionGroupMembership;
import io.atomix.primitive.partition.PartitionGroupMembershipEvent;
import io.atomix.primitive.partition.PartitionGroupMembershipEventListener;
import io.atomix.primitive.partition.PartitionGroupMembershipService;
import io.atomix.primitive.partition.PartitionId;
import io.atomix.primitive.partition.PrimaryElection;
import io.atomix.primitive.partition.PrimaryElectionEvent;
import io.atomix.primitive.partition.PrimaryElectionEventListener;
import io.atomix.primitive.partition.PrimaryTerm;
import io.atomix.utils.event.AbstractListenerManager;
import io.atomix.utils.serializer.KryoNamespace;
import io.atomix.utils.serializer.KryoNamespaces;
import io.atomix.utils.serializer.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Hash-based primary election.
 */
public class HashBasedPrimaryElection
    extends AbstractListenerManager<PrimaryElectionEvent, PrimaryElectionEventListener>
    implements PrimaryElection {
  private static final Logger LOGGER = LoggerFactory.getLogger(HashBasedPrimaryElection.class);
  private static final long BROADCAST_INTERVAL = 5000;

  private static final Serializer SERIALIZER = Serializer.using(KryoNamespace.builder()
      .register(KryoNamespaces.BASIC)
      .register(MemberId.class)
      .build());

  private final PartitionId partitionId;
  private final ClusterMembershipService clusterMembershipService;
  private final PartitionGroupMembershipService groupMembershipService;
  private final ClusterMessagingService clusterMessagingService;
  private final Map<MemberId, Integer> counters = Maps.newConcurrentMap();
  private final String subject;
  private final ScheduledFuture<?> broadcastFuture;
  private volatile PrimaryTerm currentTerm;

  private final PartitionGroupMembershipEventListener membershipEventListener = new PartitionGroupMembershipEventListener() {
    @Override
    public void onEvent(PartitionGroupMembershipEvent event) {
      recomputeTerm(event.membership().members());
    }

    @Override
    public boolean isRelevant(PartitionGroupMembershipEvent event) {
      return event.membership().group().equals(partitionId.group());
    }
  };

  public HashBasedPrimaryElection(
      PartitionId partitionId,
      ClusterMembershipService clusterMembershipService,
      PartitionGroupMembershipService groupMembershipService,
      ClusterMessagingService clusterMessagingService,
      ScheduledExecutorService executor) {
    this.partitionId = partitionId;
    this.clusterMembershipService = clusterMembershipService;
    this.groupMembershipService = groupMembershipService;
    this.clusterMessagingService = clusterMessagingService;
    this.subject = String.format("primary-election-counter-%s-%d", partitionId.group(), partitionId.id());
    PartitionGroupMembership membership = groupMembershipService.getMembership(partitionId.group());
    recomputeTerm(membership != null ? membership.members() : Collections.emptyList());
    groupMembershipService.addListener(membershipEventListener);
    clusterMessagingService.subscribe(subject, SERIALIZER::decode, this::updateCounters, executor);
    broadcastFuture = executor.scheduleAtFixedRate(this::broadcastCounters, BROADCAST_INTERVAL, BROADCAST_INTERVAL, TimeUnit.MILLISECONDS);
  }

  @Override
  public CompletableFuture<PrimaryTerm> enter(GroupMember member) {
    return CompletableFuture.completedFuture(currentTerm);
  }

  @Override
  public CompletableFuture<PrimaryTerm> getTerm() {
    return CompletableFuture.completedFuture(currentTerm);
  }

  /**
   * Returns the current term.
   *
   * @return the current term
   */
  private long currentTerm() {
    return counters.values().stream().mapToInt(v -> v).sum();
  }

  /**
   * Increments and returns the current term.
   *
   * @return the current term
   */
  private long incrementTerm() {
    counters.compute(clusterMembershipService.getLocalMember().id(), (id, value) -> value != null ? value + 1 : 1);
    broadcastCounters();
    return currentTerm();
  }

  private void updateCounters(Map<MemberId, Integer> counters) {
    for (Map.Entry<MemberId, Integer> entry : counters.entrySet()) {
      this.counters.compute(entry.getKey(), (key, value) -> {
        if (value == null || value < entry.getValue()) {
          return entry.getValue();
        }
        return value;
      });
    }
  }

  private void broadcastCounters() {
    clusterMessagingService.broadcast(subject, counters, SERIALIZER::encode);
  }

  /**
   * Recomputes the current term.
   */
  private void recomputeTerm(Collection<MemberId> members) {
    List<GroupMember> candidates = new ArrayList<>();
    for (MemberId memberId : members) {
      Member member = clusterMembershipService.getMember(memberId);
      if (member != null && member.getState() == Member.State.ACTIVE) {
        candidates.add(new GroupMember(member.id(), MemberGroupId.from(member.id().id())));
      }
    }

    candidates.sort((a, b) -> {
      int aoffset = Hashing.murmur3_32().hashString(a.memberId().id(), StandardCharsets.UTF_8).asInt() % partitionId.id();
      int boffset = Hashing.murmur3_32().hashString(b.memberId().id(), StandardCharsets.UTF_8).asInt() % partitionId.id();
      return aoffset - boffset;
    });

    GroupMember primary = candidates.isEmpty() ? null : candidates.get(0);
    currentTerm = new PrimaryTerm(
        currentTerm != null && currentTerm.primary().equals(primary) ? currentTerm.term() : incrementTerm(),
        candidates.isEmpty() ? null : candidates.get(0),
        candidates.isEmpty() ? Collections.emptyList() : candidates.subList(1, candidates.size()));

    LOGGER.debug("Recomputed term for partition {}: {}", partitionId, currentTerm);
    post(new PrimaryElectionEvent(PrimaryElectionEvent.Type.CHANGED, partitionId, currentTerm));
  }

  /**
   * Closes the election.
   */
  void close() {
    broadcastFuture.cancel(false);
    groupMembershipService.removeListener(membershipEventListener);
  }
}
