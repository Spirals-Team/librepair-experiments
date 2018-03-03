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

MAVEN_ZIP="apache-maven-3.2.5-bin.zip"
MAVEN_DIR="$HOME/maven"

mkdir "$MAVEN_DIR"
cd "$MAVEN_DIR"

wget "https://archive.apache.org/dist/maven/maven-3/3.2.5/binaries/$MAVEN_ZIP"
unzip -qq "$MAVEN_ZIP"
rm "$MAVEN_ZIP"

export M2_HOME="$PWD/apache-maven-3.2.5"
export PATH="$M2_HOME/bin:$PATH"
export MAVEN_OPTS="\"-Dorg.slf4j.simpleLogger.showDateTime=true -Dorg.slf4j.simpleLogger.dateTimeFormat=HH:mm:ss.SSS\""

cd "$HOME"