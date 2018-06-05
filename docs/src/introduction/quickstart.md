# Quickstart

This tutorial should help you to get to know the main concepts of Zeebe without
the need to write a single line of code.

1. [Download the Zeebe distribution](#step-1-download-the-zeebe-distribution)
1. [Start the Zeebe broker](#step-2-start-the-zeebe-broker)
1. [Create a topic](#step-3-create-a-topic)
1. [Create a task](#step-4-create-a-task)
1. [Complete a task](#step-5-complete-a-task)
1. [Create a topic subscription](#step-6-create-a-topic-subscription)
1. [Deploy a workflow](#step-7-deploy-a-workflow)
1. [Create a workflow instance](#step-8-create-a-workflow-instance)
1. [Complete the workflow instance](#step-9-complete-the-workflow-instance)
1. [Next steps](#next-steps)

> **Note:** Some command examples might not work on Windows if you use cmd or
> Powershell. For Windows users we recommend to use a bash-like shell, i.e. Git
> Bash, Cygwin or MinGW for this guide.

## Step 1: Download the Zeebe distribution

You can download the latest distribution from the [Zeebe release page](https://github.com/zeebe-io/zeebe/releases).

Extract the archive and enter the Zeebe directory.

```
$ tar -xzvf zeebe-distribution-X.Y.Z.tar.gz
$ cd zeebe-broker-X.Y.Z/
```

Inside the Zeebe directory you will find multiple directories.

```
$ tree -d
.
├── bin     - Binaries and start scripts of the distribution
├── conf    - Zeebe and logging configuration
└── lib     - Shared java libraries
```

## Step 2: Start the Zeebe broker

Change into the `bin/` folder and execute the `broker` file if you are using
Linux or MacOS, or the `broker.bat` file if you are using Windows. This will
start a new Zeebe broker.

```
$ cd bin/
$ ./broker
16:18:37.836 [] [main] INFO  io.zeebe.broker.system - Using config file quickstart/zeebe-broker-X.Y.Z/bin/../conf/zeebe.cfg.toml
16:18:37.888 [] [main] INFO  io.zeebe.broker.system - Using data directory: quickstart/zeebe-broker-X.Y.Z/data/
16:18:37.949 [] [main] INFO  io.zeebe.broker.system - Scheduler configuration: Threads{cpu-bound: 1, io-bound: 2}.
16:18:37.982 [] [main] INFO  io.zeebe.broker.system - Version: X.Y.Z
16:18:38.268 [io.zeebe.servicecontainer.impl.ServiceController] [0.0.0.0:51015-zb-actors-0] INFO  io.zeebe.broker.transport - Bound replicationApi.server to localhost/127.0.0.1:51017
16:18:38.269 [io.zeebe.servicecontainer.impl.ServiceController] [0.0.0.0:51015-zb-actors-0] INFO  io.zeebe.broker.transport - Bound managementApi.server to localhost/127.0.0.1:51016
16:18:38.297 [io.zeebe.servicecontainer.impl.ServiceController] [0.0.0.0:51015-zb-actors-0] INFO  io.zeebe.transport - Bound clientApi.server to /0.0.0.0:51015
16:18:38.412 [io.zeebe.broker.clustering.raft.RaftService] [0.0.0.0:51015-zb-actors-0] INFO  io.zeebe.raft - Created raft with configuration: RaftConfiguration{heartbeatIntervalMs=250, electionIntervalMs=1000, getFlushIntervalMs=10}
```

You will see some output which contains the version of the broker, or different
configuration parameters like directory locations and API socket addresses.

To continue this guide open another terminal to execute commands using the
Zeebe CLI `zbctl`.

## Step 3: Create a topic

To store data in Zeebe you need a [topic](basics/topics-and-logs.html). To
create a topic you can use the `zbctl` command line tool. A binary of zbctl for
all major operation systems can be found in the `bin/` folder of the Zeebe
distribution. In the following examples we will use `zbctl`, replace this based
on the operation system you are using.

```
$ tree bin/
bin/
├── broker        - Zeebe broker startup script for Linux & MacOS
├── broker.bat    - Zeebe broker startup script for Windows
├── zbctl         - Zeebe CLI for Linux
├── zbctl.darwin  - Zeebe CLI for MacOS
└── zbctl.exe     - Zeebe CLI for Windows
```

To create a topic we have to specify a name, for this guide we will use the
topic name `quickstart`.

```
$ ./bin/zbctl create topic quickstart
{
  "Name": "quickstart",
  "State": "CREATING",
  "Partitions": 1,
  "ReplicationFactor": 1
}
```

We can now see our new topic in the topology of the Zeebe broker.

```
$ ./bin/zbctl describe topology
+-----------------+--------------+----------------+---------+
|   TOPIC NAME    | PARTITION ID | BROKER ADDRESS |  STATE  |
+-----------------+--------------+----------------+---------+
| internal-system |            0 | 0.0.0.0:51015  | LEADER  |
+-----------------+--------------+----------------+---------+
| quickstart      |            1 | 0.0.0.0:51015  | LEADER  |
+-----------------+--------------+----------------+---------+
```

## Step 4: Create a task

A work item in Zeebe is called a [task](basics/task-workers.html#what-is-a-task). To identify a category of work items
the task has a type specified by the user. For this example we will use the
task type `step4`. A task can have a payload which can then be used to
execute the action required for this work item. In this example we will set the
payload to contain the key `zeebe` and the value `2018`. When we create the
task we have to specify on which topic the task should be created.

> **Note:** Windows users who want to execute this command using cmd or Powershell
> have to escape the payload differently.
> - cmd: `"{\"zeebe\": 2018}"`
> - Powershell: `'{"\"zeebe"\": 2018}'`

```
$ ./bin/zbctl --topic quickstart create task step4 --payload '{"zeebe": 2018}'
{
  "State": "CREATED",
  "LockTime": 0,
  "LockOwner": "",
  "Headers": {
    "activityId": "",
    "activityInstanceKey": -1,
    "bpmnProcessId": "",
    "workflowDefinitionVersion": -1,
    "workflowInstanceKey": -1,
    "workflowKey": -1
  },
  "CustomHeader": {},
  "Retries": 3,
  "Type": "step4",
  "Payload": "gaV6ZWViZctAn4gAAAAAAA=="
}
```

## Step 5: Complete a task

A [task worker](basics/task-workers.html#task-workers) is able to subscribe to
a specific task type to work on tasks created for this type. To create a task
worker we need to specify the topic, task type and a task handler. The task
handler is processing the work item and completes the task after it is
finished. `zbctl` allows us to specify a simple script or another external
application to handle a task. The handler will receive the payload of the task
on standard input. And has to return the updated payload on standard output.
The simplest task handler is `cat`, which is a unix command that just outputs
its input without modifying it.

> **Note:** For Windows users this command does not work with cmd as the `cat`
> command does not exist. We recommend to use Powershell or a bash-like shell
> to execute this command.

```
$ ./bin/zbctl --topic quickstart subscribe task --taskType step4 cat
{
  "State": "LOCKED",
  "LockTime": 1522659897723,
  "LockOwner": "zbctl-AKVtEPLotx",
  "Headers": {
    "activityId": "",
    "activityInstanceKey": -1,
    "bpmnProcessId": "",
    "workflowDefinitionVersion": -1,
    "workflowInstanceKey": -1,
    "workflowKey": -1
  },
  "CustomHeader": {},
  "Retries": 3,
  "Type": "step4",
  "Payload": "gaV6ZWViZctAn4gAAAAAAA=="
}
Completing Task
```

This command creates a task subscription on the topic `quickstart` for the task
type `step4`. So whenever a new task of this type is created the broker will
push the task to this worker. You can try it out by opening another terminal
and repeat the command from [step 4](#step-4-create-a-task) multiple times.

> **Note:** Windows users who want to execute this command using cmd or Powershell
> have to escape the payload differently.
> - cmd: `"{\"zeebe\": 2018}"`
> - Powershell: `'{"\"zeebe"\": 2018}'`

```
$ ./bin/zbctl --topic quickstart create task step4 --payload '{"zeebe": 2018}'
```

In the terminal with the running worker you will see that it processes every
new task.

To stop the worker press CTRL-C.

## Step 6: Create a topic subscription

You can see all events which are published to a topic by creating a topic
subscription. You have to specify the topic name.

```
$ ./bin/zbctl --topic quickstart subscribe topic
[...]
Metadata [topic=quickstart, partition=1, key=4294969248, position=4294970096, type=Task]
{
  "State": "LOCKED",
  "LockTime": 1525797756019,
  "LockOwner": "zbctl-mdSDPzXmCq",
  "Headers": {
    "activityId": "",
    "activityInstanceKey": -1,
    "bpmnProcessId": "",
    "workflowDefinitionVersion": -1,
    "workflowInstanceKey": -1,
    "workflowKey": -1
  },
  "CustomHeader": {},
  "Retries": 3,
  "Type": "step4",
  "Payload": "gaV6ZWViZctAn4gAAAAAAA=="
}

Metadata [topic=quickstart, partition=1, key=4294969248, position=4294970432, type=Task]
{
  "State": "COMPLETE",
  "LockTime": 1525797756019,
  "LockOwner": "zbctl-mdSDPzXmCq",
  "Headers": {
    "activityId": "",
    "activityInstanceKey": -1,
    "bpmnProcessId": "",
    "workflowDefinitionVersion": -1,
    "workflowInstanceKey": -1,
    "workflowKey": -1
  },
  "CustomHeader": {},
  "Retries": 3,
  "Type": "step4",
  "Payload": "gaV6ZWViZctAn4gAAAAAAA=="
}

Metadata [topic=quickstart, partition=1, key=4294969248, position=4294970768, type=Task]
{
  "State": "COMPLETED",
  "LockTime": 1525797756019,
  "LockOwner": "zbctl-mdSDPzXmCq",
  "Headers": {
    "activityId": "",
    "activityInstanceKey": -1,
    "bpmnProcessId": "",
    "workflowDefinitionVersion": -1,
    "workflowInstanceKey": -1,
    "workflowKey": -1
  },
  "CustomHeader": {},
  "Retries": 3,
  "Type": "step4",
  "Payload": "gaV6ZWViZctAn4gAAAAAAA=="
}
```

The event stream will now contain events which describe the lifecycle of our
example tasks from type `step4`, which starts with the `CREATE` state and ends
in the `COMPLETED` state.

To stop the topic subscription press CTRL-C.

## Step 7: Deploy a workflow

A [workflow](basics/workflows.html) is used to orchestrate loosely coupled task
workers and the flow of data between them.

In this guide we will use an example process `order-process.bpmn`. You can
download it with the following link:
[order-process.bpmn](introduction/order-process.bpmn).

![order-process](introduction/order-process.png)

The process describes a sequential flow of three tasks *Collect Money*, *Fetch
Items* and *Ship Parcel*. If you open the `order-process.bpmn` file in a text
editor you will see that every task has type defined in the XML.

```
<!-- [...] -->
<bpmn:serviceTask id="collect-money" name="Collect Money">
  <bpmn:extensionElements>
    <zeebe:taskDefinition type="payment-service" />
  </bpmn:extensionElements>
</bpmn:serviceTask>
<!-- [...] -->
<bpmn:serviceTask id="fetch-items" name="Fetch Items">
  <bpmn:extensionElements>
    <zeebe:taskDefinition type="inventory-service" />
  </bpmn:extensionElements>
</bpmn:serviceTask>
<!-- [...] -->
<bpmn:serviceTask id="ship-parcel" name="Ship Parcel">
  <bpmn:extensionElements>
    <zeebe:taskDefinition type="shipment-service" />
  </bpmn:extensionElements>
</bpmn:serviceTask>
<!-- [...] -->
```

To complete an instance of this workflow we would need three task workers for
the types `payment-service`, `inventory-service` and `shipment-service`.

But first let's deploy the workflow to the Zeebe broker. We have to specify
the topic to deploy to and the resource we want to deploy, in our case the
`order-process.bpmn`.

```
$ ./bin/zbctl --topic quickstart create workflow order-process.bpmn
{
  "State": "CREATED",
  "TopicName": "quickstart",
  "Resources": [
    {
      "Resource": "[...]",
      "ResourceType": "BPMN_XML",
      "ResourceName": "order-process.bpmn"
    }
  ]
}
```

## Step 8: Create a workflow instance

After the workflow is deployed we can create new instances of it. Every
instance of a workflow is a single execution of the workflow. To create a new
instance we have to specify the topic and the process ID from the BPMN file, in
our case the ID is `order-process`.

```
<bpmn:process id="order-process" isExecutable="true">
```

Every instance of a workflow normaly processes some kind of data. We can
specify the initial data of the instance as payload when we start the instance.

> **Note:** Windows users who want to execute this command using cmd or Powershell
> have to escape the payload differently.
> - cmd: `"{\"orderId\": 1234}"`
> - Powershell: `'{"\"orderId"\": 1234}'`

```
$ ./bin/zbctl --topic quickstart create instance order-process --payload '{"orderId": 1234}'
{
  "BPMNProcessID": "order-process",
  "Payload": "gadvcmRlcklky0CTSAAAAAAA",
  "State": "WORKFLOW_INSTANCE_CREATED",
  "Version": 1,
  "WorkflowInstanceKey": 4294972120
}
```

## Step 9: Complete the workflow instance

To complete the instance all three tasks have to be completed. Therefore we
need three task workers. Let's again use our simple `cat` worker for all three
task types. Start a task worker for all three task types as a background process
(`&`).

> **Note:** For Windows users these commands do not work with cmd as the `cat`
> command does not exist. We recommend to use Powershell or a bash-like shell
> to execute these commands.

```
$ ./bin/zbctl --topic quickstart subscribe task --taskType payment-service cat &
$ ./bin/zbctl --topic quickstart subscribe task --taskType inventory-service cat &
$ ./bin/zbctl --topic quickstart subscribe task --taskType shipment-service cat &
```

To verify that our workflow instance was completed after all tasks were
processed we can again open a topic subscription. The last event should
indicate that the workflow instance was completed.

```
$ ./bin/zbctl --topic quickstart subscribe topic
Metadata [topic=quickstart, partition=1, key=4294996328, position=4294996328, type=Workflow Instance]
{
  "ActivityID": "",
  "BPMNProcessID": "order-process",
  "Payload": "gadvcmRlcklky0CTSAAAAAAA",
  "State": "END_EVENT_OCCURRED",
  "Version": 1,
  "WorkflowInstanceKey": 4294974384
}

Metadata [topic=quickstart, partition=1, key=4294974384, position=4294996584, type=Workflow Instance]
{
  "ActivityID": "",
  "BPMNProcessID": "order-process",
  "Payload": "gadvcmRlcklky0CTSAAAAAAA",
  "State": "WORKFLOW_INSTANCE_COMPLETED",
  "Version": 1,
  "WorkflowInstanceKey": 4294974384
}
```

As you can see in the event log the last event was a workflow instance event
with the state `WORKFLOW_INSTANCE_COMPLETED`, which indicates that the instance
we started in [step 8](#step-8-create-a-workflow-instance) was now completed.
We can now start new instances in another terminal with the command from [step
8](#step-8-create-a-workflow-instance).

> **Note:** Windows users who want to execute this command using cmd or Powershell
> have to escape the payload differently.
> - cmd: `"{\"orderId\": 1234}"`
> - Powershell: `'{"\"orderId"\": 1234}'`

```
$ ./bin/zbctl --topic quickstart create instance order-process --payload '{"orderId": 1234}'
```

To close all subscriptions you can press CTRL-C to stop the topic subscription
and use the `kill` command to stop the background jobs of the tasks
subscriptions.

```
kill %1 %2 %3
```

## Next steps

To continue working with Zeebe we recommend to get more familiar with the basic
concepts of Zeebe, see the [Basics chapter](basics/README.html) of the
documentation.

In the [BPMN Workflows chapter](bpmn-workflows/README.html) you can find an
introduction to creating Workflows with BPMN. And the [BPMN Modeler
chapter](bpmn-modeler/README.html) shows you how to model them by yourself.

The documentation also provides getting started guides for implementing job
workers using [Java](java-client/README.html) or [Go](go-client/README.html).
