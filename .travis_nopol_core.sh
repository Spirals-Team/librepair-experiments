#!/bin/bash
# script for Travis

echo "Compiling & testing Nopol"
cd test-projects
mvn clean package -DskipTests
cd ..
cd nopol
mvn clean install jacoco:report coveralls:report
if [[ $? != 0 ]]
then
    exit 1
fi
cd ..

