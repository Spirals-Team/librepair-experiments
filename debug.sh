#!/usr/bin/env bash

mvn clean package -Dmaven.test.skip=true
java -jar -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 target/w4tracking-*-swarm.jar
