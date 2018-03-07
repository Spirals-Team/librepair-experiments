#!/bin/bash
# script for Travis

echo "Compiling & testing Nopol"
cd nopol
mvn clean install -DskipTests
if [[ $? != 0 ]]
then
    exit 1
fi
cd ..

# IDE plugin
echo "Compiling & testing nopol-ui-intellij"
cd nopol-ui-intellij
./gradlew buildPlugin
if [[ $? != 0 ]]
then
    exit 1
fi
cd ..

