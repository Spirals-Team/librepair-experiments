#!/bin/bash
set -e

JDK_FEATURE=11

TMP=$(curl -L jdk.java.net/${JDK_FEATURE})
TMP="${TMP#*Most recent build: jdk-${JDK_FEATURE}-ea+}" # remove everything before the number
TMP="${TMP%%<*}"                                        # remove everything after the number
JDK_BUILD="$(echo -e "${TMP}" | tr -d '[:space:]')" # remove all whitespace

JDK_ARCHIVE=openjdk-${JDK_FEATURE}-ea+${JDK_BUILD}_linux-x64_bin.tar.gz
cd ~
wget https://download.java.net/java/early_access/jdk${JDK_FEATURE}/${JDK_BUILD}/GPL/${JDK_ARCHIVE}
tar -xzf ${JDK_ARCHIVE}
export JAVA_HOME=~/jdk-${JDK_FEATURE}
export PATH=${JAVA_HOME}/bin:$PATH
cd -
echo check java version
java --version

wget https://archive.apache.org/dist/maven/maven-3/3.5.2/binaries/apache-maven-3.5.2-bin.zip
unzip -qq apache-maven-3.5.2-bin.zip
export M2_HOME=$PWD/apache-maven-3.5.2
export PATH=$M2_HOME/bin:$PATH
