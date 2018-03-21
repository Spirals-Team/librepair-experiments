#!/usr/bin/env bash

################################################################################
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
################################################################################

source "$(dirname "$0")"/common.sh

#FLINK_DIR=/Users/kkloudas/repos/dataartisans/flink/build-target flink-end-to-end-tests/test-scripts/test_ha.sh
TEST_PROGRAM_JAR=$TEST_INFRA_DIR/../../flink-examples/flink-examples-streaming/target/StateMachineExample.jar\ --error-rate\ 0.1\ --sleep\ 2
TEST_DIR="/Users/kkloudas/haTesting"

createHAMAsters() {
    if [ -e $1 ]; then
        echo "File $1 exists. Deleting it..."
        rm -f $1
    fi

    cat >$1 << EOL
localhost:8081
EOL
}

createHAConf() {
    if [ -e $TEST_DIR/recovery ]; then
       echo "File ${TEST_DIR}/recovery exists. Deleting it..."
       rm -rf $TEST_DIR/recovery
    fi

    cat >$1 << EOL
#==============================================================================
# Common
#==============================================================================

jobmanager.rpc.address: localhost
jobmanager.rpc.port: 6123
jobmanager.heap.mb: 1024
taskmanager.heap.mb: 1024
taskmanager.numberOfTaskSlots: 4
parallelism.default: 1

#==============================================================================
# High Availability
#==============================================================================

high-availability: zookeeper
high-availability.zookeeper.storageDir: file://${TEST_DIR}/recovery/
high-availability.zookeeper.quorum: localhost:2181
high-availability.zookeeper.path.root: /flink
high-availability.cluster-id: /test_cluster_one

#==============================================================================
# Web Frontend
#==============================================================================

web.port: 8081
EOL
}

killJM() {
    jm_pids=`jps | grep 'StandaloneSessionClusterEntrypoint' | cut -d " " -f 1`
    jm_pids=(${jm_pids[@]})

    pid=${jm_pids[0]}

    # time to kill the JM
    kill -9 ${pid}

    echo "Killed JM @ ${pid}."
}

setupHACluster() {
    echo "Setting up HA Cluster..."

    # remove previous log files
    rm $FLINK_DIR/log/*

    createHAMAsters $FLINK_DIR/conf/masters
    createHAConf $FLINK_DIR/conf/flink-conf.yaml

    "$FLINK_DIR"/bin/start-zookeeper-quorum.sh
    start_cluster
}

tearDownHACluster() {
    echo "Tearing down HA Cluster..."

    stop_cluster
    "$FLINK_DIR"/bin/stop-zookeeper-quorum.sh

    echo "HA Cluster has stopped."
}

jmCreator() {
    expectedJMs=$1

    while true;
    do
        runningTm=`jps | grep -o 'StandaloneSessionClusterEntrypoint' | wc -l`;
        count=$((expectedJMs-runningTm))
        for (( c=0; c<count; c++ ))
        do
            $FLINK_DIR/bin/jobmanager.sh start "localhost" "8081"
        done
        sleep 5;
    done
}

makeCheckpointsUnavailable() {
    if [ -e $TEST_DIR/testCheckpoints/ ]; then
        echo "Making ${TEST_DIR}/testCheckpoints/ UNREADABLE."
        chmod -R -rw $TEST_DIR/testCheckpoints/*
    fi
}

makeCheckpointsAvailable() {
    if [ -e $TEST_DIR/testCheckpoints/ ]; then
        echo "Making ${TEST_DIR}/testCheckpoints/ READABLE."
        chmod -R +rw ${TEST_DIR}/testCheckpoints/
    fi
}

searchLogsFor() {
    phrase=$1
    echo `grep --include=\*.log -rnw $FLINK_DIR/log/ -e ${phrase} | wc -l`
}

runHATest() {
    parallelism=$1
    backend=$2
    checkpointDir=$3
    async=$4
    incremental=$5
    chkInterval=$6
    maxAttempts=$7
    rstrtInterval=$8
    output=$9
    error=${10}

    # clean up any left-overs from previous runs
    if [ -e $TEST_DIR ]; then

        # to be on the safe side, kill all java processes
        killall java
        rm $FLINK_DIR/log/*

        echo "Cleaning up dir from previous executions."
        chmod -R +rw ${TEST_DIR}/testCheckpoints/
        rm -rf $TEST_DIR
    fi

    # start the cluster on HA mode and
    # verify that all JMs are running
    setupHACluster

    jms=`jps | grep 'StandaloneSessionClusterEntrypoint' | wc -l`
    if ! [ ${jms} -eq 1 ]; then
        echo "PROBLEM: Not the expected number of JMs running."; exit 1
    fi

    sleep 2

    echo "Running HA test with parallelism=${parallelism}, backend=${backend}, checkpointDir=${checkpointDir}, aysnc=${async} and incremental=${incremental}."

    # submit a job in detached mode and let it run
    $FLINK_DIR/bin/flink run -d -p ${parallelism} \
     $TEST_PROGRAM_JAR \
        --stateBackend ${backend} \
        --checkpointDir ${checkpointDir} \
        --asyncCheckpoints ${async} \
        --incrementalCheckpoints ${incremental} \
        --checkpointInterval ${chkInterval} \
        --restartAttempts ${maxAttempts} \
        --restartDelay ${rstrtInterval} \
        --output ${output} \
        --error-rate ${error}

    # let the job run for a while to take some checkpoints
    sleep 20

    jmCreator 1 &
    watchdogPid=$!
    echo "If you want to kill the JM watchdog, its pid is ${watchdogPid}"

    # change read permission to the underlying checkpoint dir
    #makeCheckpointsUnavailable

    for (( c=0; c<5; c++ ))
    do
        # kill the JM
        killJM

        # recover and take some more checkpoints
        sleep 60
    done

    kill -9 ${watchdogPid}

    # kill the cluster and zookeeper
    tearDownHACluster

    # todo verify that there are no invalid transitions and that the job recovered after the failure.

    # find the log file of the JM that took over
    searchLogsFor "Found 1 checkpoints in ZooKeeper"

    # find the log files of the JMs that ran the job. They should be 2, one before the failure, and one after.
    searchLogsFor "Running initialization on master for job Flink Streaming Job"
}

# checkout trap
runHATest 1 "file" "file://${TEST_DIR}/testCheckpoints/" "false" "false" 500 3 1000 "${TEST_DIR}/output.txt" "0.0"
exit 0

#WHAT HAPPENS WITH WEB INTERFACE?
