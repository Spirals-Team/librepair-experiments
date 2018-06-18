#!/bin/sh

SPRING_PROFILE=""

if [ ! -z $1 ]
then
  SPRING_PROFILE="--spring.profiles.active=${1}"
fi

mvn clean install
java -jar target/academia-api-1.0-SNAPSHOT.jar $SPRING_PROFILE
