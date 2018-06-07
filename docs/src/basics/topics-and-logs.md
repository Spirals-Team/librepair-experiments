# Topics & Partitions

> Note: If you have worked with the [Apache Kafka System](https://kafka.apache.org/) before, the concepts presented on this page will sound very familiar to you.

In Zeebe, all data is organized into *topics*. Topics are user-defined and have a name for distinction. Each topic is physically organized into one or more *partitions* which is a persistent stream of workflow-related events. In a cluster of brokers, partitions are distributed among the nodes so it can be thought of as a *shard*. When you create a topic, you specify its name and how many partitions you need.

## Usage Examples

Whenever you deploy a workflow, you deploy it to a specific topic. The workflow is then distributed to all partitions of that topic. On all partitions, this workflow receives the same key and version such that it can be consistently identified within the topic.

When you start an instance of a workflow, you identify the topic this request addresses. The client library will then route the request to one partition of the topic in which the workflow instance will be published. All subsequent processing of the workflow instance will happen in that partition.

## Data Separation

Use topics to separate your workflow-related data. Let us assume you operate Zeebe for different departments of your organization. You can create different topics for each department ensuring that their workflows do not interfere. For example, workflow versioning applies per topic.

## Scalability

Use partitions to scale your workflow processing. Partitions are dynamically distributed in a Zeebe cluster and for each partition there is one leading broker at a time. This *leader* accepts requests and performs event processing for the partition. Let us assume you want to distribute a topic's workflow processing load over five machines. You can achieve that by creating a topic with five partitions.

## Quality of Service

Use topics to assign dedicated resources to time-critical workflow events. A Zeebe broker reserves event processing resources to each partition. If you have some workflows where processing latency is critical and some with very high event ingestion, you can create separate topics for each such that the time-critical workflows are less interfered with by the mass of unrelated events.

## Partition Data Layout

A partition is a persistent append-only event stream. Initially, a partition is empty. As the first entry gets inserted, it takes the place of the first entry. As the second entry comes in and is inserted, it takes the place as the second entry and so on and so forth. Each entry has a position in the partition which uniquely identifies it.

![partition](/basics/partition.png)

## Replication

For fault tolerance, data in a partition is replicated from the leader of the partition to its *followers*. Followers are other Zeebe Broker nodes that maintain a copy of the partition without performing event processing.
