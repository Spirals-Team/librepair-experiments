#!/bin/bash

set -e

if [[ "${JAVA_VERSION}" = "8" ]]; then
  if [ "$TRAVIS_PULL_REQUEST" != "false" ] && [[ ! "$TRAVIS_PULL_REQUEST_SLUG" =~ ^algolia\/ ]]; then
    eval $(./algolia-keys export) && mvn clean test jacoco:report jacoco:report-aggregate -B && mvn -pl algoliasearch-tests coveralls:report -B;
  else
    mvn clean test jacoco:report jacoco:report-aggregate -B && mvn -pl algoliasearch-tests coveralls:report -B;
  fi
fi

if [[ "${JAVA_VERSION}" = "9" ]]; then
  if [ "$TRAVIS_PULL_REQUEST" != "false" ] && [[ ! "$TRAVIS_PULL_REQUEST_SLUG" =~ ^algolia\/ ]]; then
    eval $(./algolia-keys export) && mvn clean test;
  else
    mvn clean test;
  fi
fi
