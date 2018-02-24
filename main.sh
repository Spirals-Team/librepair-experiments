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

function handleError() {
	echo "Error on line $2"
	cleanUpCache
	exit "$1"
}

function cleanUpCache() {
	if ! [ -d "$HOME/test_directory" ]; then
		echo "cache directory does not exist, nothing to clean up."
		exit "$1"
	fi

	echo "cleaning up $HOME/test_directory/$TRAVIS_BUILD_ID"

	rm -rf "$HOME/test_directory/$TRAVIS_BUILD_ID"
}

trap 'handleError $? $LINENO' ERR

if ! [ -d "$HOME/test_directory" ]; then
    echo "Creating test directory"
    mkdir "$HOME/test_directory"
fi

if ! [ -d "$HOME/test_directory/$TRAVIS_BUILD_ID" ]; then
    echo "Creating test build directory"
    mkdir "$HOME/test_directory/$TRAVIS_BUILD_ID"
	cp -r . "$HOME/test_directory/$TRAVIS_BUILD_ID"
fi

function getLatestMarker() {
	for stage in "compile" "core" "libraries" "connectors" "tests" "mist"
	do
		if ! [ -e "$HOME/test_directory/$TRAVIS_BUILD_ID/marker_$stage" ]; then
			echo "$stage"
			return 0
		fi
	done

	echo "cleanup"
	return 0
}

STAGE=$(getLatestMarker)
echo "In stage $STAGE"

EXIT_CODE=0

case $STAGE in
	(cleanup)
		cleanUpCache
		;;
	(*)
		"$HOME/test_directory/$TRAVIS_BUILD_ID/tools/travis_mvn_watchdog.sh" $STAGE 300
		# if this returns an error the trap will fire and cleanup all files
		EXIT_CODE=$?

		if [ $STAGE != 255 ]; then
			touch "$HOME/test_directory/$TRAVIS_BUILD_ID/marker_$STAGE"
			ls "$HOME/test_directory/$TRAVIS_BUILD_ID"
		fi

		;;
esac

# Exit code for Travis build success/failure
exit $EXIT_CODE