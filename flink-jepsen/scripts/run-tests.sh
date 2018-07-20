#!/usr/bin/env bash
################################################################################
#  Licensed to the Apache Software Foundation (ASF) under one
#  or more contributor license agreements.  See the NOTICE file
#  distributed with this work for additional information
#  regarding copyright ownership.  The ASF licenses this file
#  to you under the Apache License, Version 2.0 (the
#  "License"); you may not use this file except in compliance
#  with the License.  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
# limitations under the License.
################################################################################

set -euo pipefail

common_jepsen_args+=(--ha-storage-dir hdfs:///flink
--job-jar bin/DataStreamAllroundTestProgram.jar
--job-args "--environment.parallelism 4 --state_backend.checkpoint_directory hdfs:///checkpoints --state_backend rocks --state_backend.rocks.incremental true"
--nodes-file ~/nodes
--tarball ${2}
--username admin
--ssh-private-key ~/.ssh/id_rsa)

for i in $(seq 1 ${1})
do
	echo "Executing run #${i} of ${1}"
		lein run test "${common_jepsen_args[@]}" --nemesis-gen kill-single-task-manager --deployment-mode yarn-job
	echo
done
