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
		NUMBER="${CACHE_DIR##*/}"
		if [ "$NUMBER" -lt "$TRAVIS_BUILD_NUMBER" ]; then
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

if [ $STAGE != "compile" ]; then
	# do NOT execute this in any module directory as this slows down the build
	cd $BASE_DIR

	echo -e "travis_fold:start:install_jars${FOLD_ESCAPE}${COLOR_ON}Install jars${COLOR_OFF}"
	echo -e "travis_time_start"

    function installMavenArtifact() {
		JAR=$1
		POM=$2
		if [[ "$JAR" == *"tests.jar"* ]]; then
			echo "Installing test-jar $JAR"
			mvn install:install-file -Dfile="$JAR" -DpomFile="$POM" -Dclassifier=tests | grep -E "Total time|BUILD"
		else
			echo "Installing jar $JAR"
			mvn install:install-file -Dfile="$JAR" -DpomFile="$POM" | grep -E "Total time|BUILD"
		fi
    }

	function installMavenArtifacts() {
		while read JAR_PATH; do
			FILE="${JAR_PATH##*/}"

			FILE_LEN="${#FILE}"
			JAR_PATH_LEN="${#JAR_PATH}"
			TARGET_DIR_LEN="$(($JAR_PATH_LEN - $FILE_LEN))"

			TARGET_DIR="${JAR_PATH:0:$TARGET_DIR_LEN}"

			printf -v POM_PATH "%sdependency-reduced-pom.xml" $TARGET_DIR
			if [ -e "$POM_PATH" ]; then
				installMavenArtifact "$JAR_PATH" "$POM_PATH"
			else
				## fallback to original pom if it exists
				if [ -e "$TARGET_DIR/../pom.xml" ]; then
					echo "Falling back to basic pom for installation of jar $JAR_PATH"
					installMavenArtifact "$JAR_PATH" "$TARGET_DIR/../pom.xml"
				else
					echo "Skipping installation of jar $JAR_PATH"
				fi
			fi
		done
	}

	find . \( -name '*SNAPSHOT.jar' -o -name '*SNAPSHOT-tests.jar' -o -name '*jar-with-dependencies.jar' \) | grep "/target/" | grep -v "/original-"  | installMavenArtifacts

	echo -en "travis_time_end"
	echo -en "travis_fold:end:install_jars${FOLD_ESCAPE}"
	cd "$HERE"

fi

# Run actual compile&test steps
if [ $STAGE == "compile" ]; then
	# compile directly in the cached directory
	"$FLINK_DIR/tools/travis_mvn_watchdog.sh" $STAGE 300
	# if this returns an error the trap will fire and cleanup all files
	EXIT_CODE=$?
else
	# merged compiled flink into local clone
	# this prevents the cache from being re-uploaded
	cp -RT "$FLINK_DIR" "."

	"./tools/travis_mvn_watchdog.sh" $STAGE 300
	EXIT_CODE=$?
fi

# cleanup after fully successful build
if [ EXIT_CODE == 0 ]; then
	if [ $STAGE == 6 ]; then
		cleanUpCache
	fi
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

		echo "# files after minimize"
		find "$FLINK_DIR" -type f | wc -l
	}

	minimizeCachedFiles
fi
# Exit code for Travis build success/failure
exit $EXIT_CODE
