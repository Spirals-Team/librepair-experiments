/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.storm.scheduler.resource.strategies.scheduling;

import org.apache.storm.Config;
import org.apache.storm.scheduler.*;
import org.apache.storm.scheduler.resource.ResourceUtils;
import org.apache.storm.scheduler.resource.SchedulingResult;
import org.apache.storm.scheduler.resource.SchedulingStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class DefaultResourceAwareStrategy extends BaseResourceAwareStrategy implements IStrategy {

    private static final Logger LOG = LoggerFactory.getLogger(BaseResourceAwareStrategy.class);

    @Override
    public SchedulingResult schedule(Cluster cluster, TopologyDetails td) {
        prepare(cluster);
        if (nodes.getNodes().size() <= 0) {
            LOG.warn("No available nodes to schedule tasks on!");
            return SchedulingResult.failure(
                    SchedulingStatus.FAIL_NOT_ENOUGH_RESOURCES, "No available nodes to schedule tasks on!");
        }
        Collection<ExecutorDetails> unassignedExecutors =
                new HashSet<>(this.cluster.getUnassignedExecutors(td));
        LOG.info("ExecutorsNeedScheduling: {}", unassignedExecutors);
        Collection<ExecutorDetails> scheduledTasks = new ArrayList<>();
        List<Component> spouts = this.getSpouts(td);

        if (spouts.size() == 0) {
            LOG.error("Cannot find a Spout!");
            return SchedulingResult.failure(
                    SchedulingStatus.FAIL_INVALID_TOPOLOGY, "Cannot find a Spout!");
        }

        //order executors to be scheduled
        List<ExecutorDetails> orderedExecutors = this.orderExecutors(td, unassignedExecutors);
        Collection<ExecutorDetails> executorsNotScheduled = new HashSet<>(unassignedExecutors);
        List<String> favoredNodes = (List<String>) td.getConf().get(Config.TOPOLOGY_SCHEDULER_FAVORED_NODES);
        List<String> unFavoredNodes = (List<String>) td.getConf().get(Config.TOPOLOGY_SCHEDULER_UNFAVORED_NODES);
        final List<ObjectResources> sortedNodes = this.sortAllNodes(td, null, favoredNodes, unFavoredNodes);

        for (ExecutorDetails exec : orderedExecutors) {
            LOG.debug(
                    "Attempting to schedule: {} of component {}[ REQ {} ]",
                    exec,
                    td.getExecutorToComponent().get(exec),
                    td.getTaskResourceReqList(exec));
            scheduleExecutor(exec, td, scheduledTasks, sortedNodes);
        }

        executorsNotScheduled.removeAll(scheduledTasks);
        LOG.error("/* Scheduling left over task (most likely sys tasks) */");
        // schedule left over system tasks
        for (ExecutorDetails exec : executorsNotScheduled) {
            scheduleExecutor(exec, td, scheduledTasks, sortedNodes);
        }

        SchedulingResult result;
        executorsNotScheduled.removeAll(scheduledTasks);
        if (executorsNotScheduled.size() > 0) {
            LOG.error("Not all executors successfully scheduled: {}", executorsNotScheduled);
            result =
                    SchedulingResult.failure(
                            SchedulingStatus.FAIL_NOT_ENOUGH_RESOURCES,
                            (td.getExecutors().size() - unassignedExecutors.size())
                                    + "/"
                                    + td.getExecutors().size()
                                    + " executors scheduled");
        } else {
            LOG.debug("All resources successfully scheduled!");
            result = SchedulingResult.success("Fully Scheduled by " + this.getClass().getSimpleName());
        }
        return result;
    }

    /**
     * Sort objects by the following two criteria. 1) the number executors of the topology that needs
     * to be scheduled is already on the object (node or rack) in descending order. The reasoning to
     * sort based on criterion 1 is so we schedule the rest of a topology on the same object (node or
     * rack) as the existing executors of the topology. 2) the subordinate/subservient resource
     * availability percentage of a rack in descending order We calculate the resource availability
     * percentage by dividing the resource availability of the object (node or rack) by the resource
     * availability of the entire rack or cluster depending on if object references a node or a rack.
     * By doing this calculation, objects (node or rack) that have exhausted or little of one of the
     * resources mentioned above will be ranked after racks that have more balanced resource
     * availability. So we will be less likely to pick a rack that have a lot of one resource but a
     * low amount of another.
     *
     * @param allResources contains all individual ObjectResources as well as cumulative stats
     * @param existingScheduleFunc a function to get existing executors already scheduled on this object
     * @return a sorted list of ObjectResources
     */
    @Override
    protected TreeSet<ObjectResources> sortObjectResources(
            final AllResources allResources, ExecutorDetails exec, TopologyDetails topologyDetails,
            final ExistingScheduleFunc existingScheduleFunc) {

        for (ObjectResources objectResources : allResources.objectResources) {
            StringBuilder sb = new StringBuilder();
            if (ResourceUtils.getMinValuePresentInResourceMap(objectResources.availableResources) <= 0) {
                objectResources.effectiveResources = 0.0;
            } else {
                List<Double> values = new LinkedList<>();

                Map<String, Double> percentageTotal = ResourceUtils.getPercentageOfTotalResourceMap(
                        objectResources.availableResources, allResources.availableResourcesOverall
                );
                for(Map.Entry<String, Double> percentageEntry : percentageTotal.entrySet()) {
                    values.add(percentageEntry.getValue());
                    sb.append(String.format("%s %f(%f%%) ", percentageEntry.getKey(),
                            objectResources.availableResources.get(percentageEntry.getKey()),
                            percentageEntry.getValue())
                    );

                }

                objectResources.effectiveResources = Collections.min(values);
            }
            LOG.debug(
                    "{}: Avail [ {} ] Total [ {} ] effective resources: {}",
                    objectResources.id,
                    sb.toString(),
                    objectResources.totalResources,
                    objectResources.effectiveResources);
        }

        TreeSet<ObjectResources> sortedObjectResources =
                new TreeSet<>((o1, o2) -> {
                    int execsScheduled1 = existingScheduleFunc.getNumExistingSchedule(o1.id);
                    int execsScheduled2 = existingScheduleFunc.getNumExistingSchedule(o2.id);
                    if (execsScheduled1 > execsScheduled2) {
                        return -1;
                    } else if (execsScheduled1 < execsScheduled2) {
                        return 1;
                    } else {
                        if (o1.effectiveResources > o2.effectiveResources) {
                            return -1;
                        } else if (o1.effectiveResources < o2.effectiveResources) {
                            return 1;
                        } else {
                            Collection<Double> o1Values = ResourceUtils.getPercentageOfTotalResourceMap(
                                    o1.availableResources, allResources.availableResourcesOverall).values();
                            Collection<Double> o2Values = ResourceUtils.getPercentageOfTotalResourceMap(
                                    o2.availableResources, allResources.availableResourcesOverall).values();

                            double o1Avg = ResourceUtils.avg(o1Values);
                            double o2Avg = ResourceUtils.avg(o2Values);

                            if (o1Avg > o2Avg) {
                                return -1;
                            } else if (o1Avg < o2Avg) {
                                return 1;
                            } else {
                                return o1.id.compareTo(o2.id);
                            }
                        }
                    }
                });
        sortedObjectResources.addAll(allResources.objectResources);
        LOG.debug("Sorted Object Resources: {}", sortedObjectResources);
        return sortedObjectResources;
    }

}
