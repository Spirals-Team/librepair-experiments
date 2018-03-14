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

function checkLogs {
  parallelism=$1
  attempts=$2
  (( expectedCount=parallelism * (attempts + 1) ))

  # Search for the log message that indicates restore problem from existing local state for the keyed backend.
  failedLocalRecovery=$(grep '^.*Creating keyed state backend.* from alternative (2/2)\.$' $FLINK_DIR/log/* | wc -l | tr -d ' ')

  # Search for attempts to recover locally.
  attemptLocalRecovery=$(grep '^.*Creating keyed state backend.* from alternative (1/2)\.$' $FLINK_DIR/log/* | wc -l | tr -d ' ')

  if [ ${failedLocalRecovery} -ne 0 ]
  then
    PASS=""
    echo "FAILURE: Found ${failedLocalRecovery} failed attempt(s) for local recovery of correctly scheduled task(s)."
  fi

  if [ ${attemptLocalRecovery} -eq 0 ]
  then
    PASS=""
    echo "FAILURE: Found no attempt for local recovery. Configuration problem?"
  fi
}

function cleanupAfterTest {
  # Reset the configurations
  sed -i -e 's/state.backend.local-recovery: .*//' "$FLINK_DIR/conf/flink-conf.yaml"
  sed -i -e 's/log4j.rootLogger=.*/log4j.rootLogger=INFO, file/' "$FLINK_DIR/conf/log4j.properties"
  #
  kill ${watchdogPid} 2> /dev/null
  wait ${watchdogPid} 2> /dev/null
  #
  cleanup
}

function cleanupAfterTestAndExitFail {
  cleanupAfterTest
  exit 1
}

## This function executes one run for a certain configuration
function runLocalRecoveryTest {
  parallelism=$1
  maxAttempts=$2
  backend=$3
  incremental=$4
  killJVM=$5

  echo "Running local recovery test on ${backend} backend: incremental checkpoints = ${incremental}, kill JVM = ${killJVM}."
  TEST_PROGRAM_JAR=$TEST_INFRA_DIR/../../flink-end-to-end-tests/target/flink-end-to-end-tests.jar

  # Enable debug logging
  sed -i -e 's/log4j.rootLogger=.*/log4j.rootLogger=DEBUG, file/' "$FLINK_DIR/conf/log4j.properties"

  # Enable local recovery
  sed -i -e 's/state.backend.local-recovery: .*//' "$FLINK_DIR/conf/flink-conf.yaml"
  echo "state.backend.local-recovery: ENABLE_FILE_BASED" >> "$FLINK_DIR/conf/flink-conf.yaml"

  rm $FLINK_DIR/log/* 2> /dev/null

  start_cluster

  tm_watchdog ${parallelism} &
  watchdogPid=$!

  echo "Started TM watchdog with PID ${watchdogPid}."

  $FLINK_DIR/bin/flink run -c org.apache.flink.streaming.tests.StickyAllocationAndLocalRecoveryTestJob \
  -p ${parallelism} $TEST_PROGRAM_JAR --resolve-order parent-first \
  -D state.backend.local-recovery=ENABLE_FILE_BASED \
  --checkpointDir file://$TEST_DATA_DIR/local_recovery_test/checkpoints \
  --output $TEST_DATA_DIR/out/local_recovery_test/out --killJvmOnFail ${killJVM} --checkpointInterval 1000 \
  --maxAttempts ${maxAttempts} --parallelism ${parallelism} --stateBackend ${backend} \
  --incrementalCheckpoints ${incremental}

  checkLogs ${parallelism} ${maxAttempts}
  cleanupAfterTest
}

## MAIN
trap cleanupAfterTestAndExitFail EXIT
runLocalRecoveryTest 4 3 "file" "false" "false"
runLocalRecoveryTest 4 3 "file" "false" "true"
runLocalRecoveryTest 4 3 "rocks" "false" "false"
runLocalRecoveryTest 4 3 "rocks" "true" "false"
runLocalRecoveryTest 4 3 "rocks" "false" "true"
runLocalRecoveryTest 4 3 "rocks" "true" "true"
trap - EXIT
exit 0
