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

FOLD_ESCAPE="\x0d\x1b"
COLOR_ON="\x5b\x30\x4b\x1b\x5b\x33\x33\x3b\x31\x6d"
COLOR_OFF="\x1b\x5b\x30\x6d"

HERE="`dirname \"$0\"`"				# relative
HERE="`( cd \"$HERE\" && pwd )`" 	# absolutized and normalized
if [ -z "$HERE" ] ; then
	# error; for some reason, the path is not accessible
	# to the script (e.g. permissions re-evaled after suid)
	exit 1  # fail
fi

CACHE_DIR="$HOME/flink_cache"
BASE_DIR="$CACHE_DIR/$TRAVIS_BUILD_NUMBER"
FLINK_DIR="$BASE_DIR/flink"

function handleError() {
	echo "Error on line $2"
	cleanUpCache
	exit "$1"
}

function cleanUpCache() {
	echo "Cleaning up $BASE_DIR"
	rm -rf "$BASE_DIR"
}

trap 'handleError $? $LINENO' ERR

if ! [ -d "$BASE_DIR" ]; then
    echo "Creating test build directories"
    mkdir -p "$FLINK_DIR"

	cp -r . "$FLINK_DIR"
fi

function deleteOldCaches() {
	while read CACHE_DIR; do
		local old_number="${CACHE_DIR##*/}"
		if [ "$old_number" -lt "$TRAVIS_BUILD_NUMBER" ]; then
			echo "Deleting old cache $CACHE_DIR"
			rm -rf "$CACHE_DIR"
		fi
	done
}

# delete leftover caches from previous builds
find "$CACHE_DIR" -mindepth 1 -maxdepth 1 | grep -v "$TRAVIS_BUILD_NUMBER" | deleteOldCaches

function getCurrentStage() {
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

STAGE=$(getCurrentStage)
if [ $? != 0 ]; then
	echo "Could not determine current stage."
	exit 1
fi
echo "Current stage: \"$STAGE\""

EXIT_CODE=0

# Run actual compile&test steps
if [ $STAGE == "compile" ]; then
	# compile directly in the cached directory
	cd "$FLINK_DIR"

	MVN="mvn clean package -Dspotbugs -nsu -Dflink.forkCount=2 -Dflink.forkCountTestPackage=2 -Dmaven.javadoc.skip=true -B -DskipTests $PROFILE"
	$MVN
	EXIT_CODE=$?

	cd "$HERE"
else
	# merged compiled flink into local clone
	# this prevents the cache from being re-uploaded
	echo "Merging cached flink project"
	travis_time_start
	mkdir "flink-copy"
	cp -pRT "$FLINK_DIR" "flink-copy"
	travis_time_finish

	TEST="$STAGE" "./flink-copy/tools/travis_mvn_watchdog.sh" 300
	EXIT_CODE=$?
fi

if [ $STAGE == "compile" ]; then
	function minimizeCachedFiles() {
		# reduces the size of the cached directory to speed up
		# the packing&upload / download&unpacking process
		# by removing files not required for subsequent stages

		echo "# files before minimize"
		find "$FLINK_DIR" -type f | wc -l

		# japicmp reports
		find "$FLINK_DIR" -maxdepth 8 -type d -name 'japicmp' | xargs rm -rf

		# original jars
		find "$FLINK_DIR" -maxdepth 8 -type f -name 'original-*.jar' | xargs rm -rf

		# .git directory
		# not deleting this can cause build stability issues
		# merging the cached version sometimes fails
		rm -rf "$FLINK_DIR/.git"

		echo "# files after minimize"
		find "$FLINK_DIR" -type f | wc -l
	}

	minimizeCachedFiles
fi

if [ $STAGE == "misc" ]; then
		cleanUpCache
fi
# Exit code for Travis build success/failure
exit $EXIT_CODE
