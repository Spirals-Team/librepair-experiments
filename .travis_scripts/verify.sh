#!/usr/bin/env bash

if [ "$TRAVIS_PULL_REQUEST" != "false" ]; then
    ./mvnw verify -Dmaven.javadoc.skip=true -Dtest.travisBuild=true  sonar:sonar \
        -Dsonar.analysis.mode=preview \
        -Dsonar.host.url=https://sonarqube.com \
        -Dsonar.github.pullRequest=$TRAVIS_PULL_REQUEST \
        -Dsonar.github.repository=$TRAVIS_REPO_SLUG \
        -Dsonar.github.oauth=$SONAR_GITHUB_TOKEN \
        -Dsonar.organization=corfudb \
        -Dsonar.login=$SONAR_TOKEN
else
    ./mvnw verify -Dmaven.javadoc.skip=true -Dsonar.host.url=https://sonarqube.com \
        -Dsonar.organization=corfudb -Dsonar.login=$SONAR_TOKEN -Dtest.travisBuild=true sonar:sonar
fi