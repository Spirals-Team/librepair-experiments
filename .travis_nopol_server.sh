#!/bin/bash
# script for Travis

echo "Compiling & testing Nopol"
cd nopol
mvn clean install mvn clean install -DskipTests
if [[ $? != 0 ]]
then
    exit 1
fi
cd ..

echo ${JAVA_HOME}

echo "Compiling & testing nopol server"
cd nopol-server
mvn clean install
if [[ $? != 0 ]]
then
    exit 1
fi
cd ..

