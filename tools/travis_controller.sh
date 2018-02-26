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

set -x

BASE_DIR="$HOME/test_directory/$TRAVIS_BUILD_ID"
FLINK_DIR="$BASE_DIR/flink"
MARKER_DIR="$BASE_DIR/markers"

function handleError() {
	echo "Error on line $2"
	cleanUpCache
	exit "$1"
}

function cleanUpCache() {
	echo "cleaning up $BASE_DIR"
	rm -rf "$BASE_DIR"
}

trap 'handleError $? $LINENO' ERR

if ! [ -d "$BASE_DIR" ]; then
    echo "Creating test build directories"
    mkdir -p "$FLINK_DIR"
    mkdir -p "$MARKER_DIR"

	cp -r . "$FLINK_DIR"
fi

function getLatestMarker() {
	STAGE_NUMBER=$(echo "$TRAVIS_JOB_NUMBER" | cut -d'.' -f 2)
	case $STAGE_NUMBER in
		(1)
			echo "compile"
			;;
		(2)
			echo "core"
			;;
		(3)
			echo "libraries"
			;;
		(4)
			echo "connectors"
			;;
		(5)
			echo "tests"
			;;
		(6)
			echo "misc"
			;;
		(*)
			echo "Invalid stage detected ($STAGE_NUMBER)"
			return 1
			;;
	esac

	return 0
}

STAGE=$(getLatestMarker)
if [ $? != 0 ]; then
	exit 1
fi
echo "In stage $STAGE"

EXIT_CODE=0

"$FLINK_DIR/tools/travis_mvn_watchdog.sh" $STAGE 300
# if this returns an error the trap will fire and cleanup all files
EXIT_CODE=$?

if [ EXIT_CODE == 0 ]; then
	touch "$MARKER_DIR/marker_$STAGE"
	ls "$BASE_DIR"

	if [ $STAGE == 6 ]; then
		cleanUpCache
	fi
fi

# Exit code for Travis build success/failure
exit $EXIT_CODE