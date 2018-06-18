#!/bin/sh

docker rm -f academia-api
docker build . -f scripts/Dockerfile -t academia-api

docker run --network=academia -d -p 8080:8080 -v academia-maven:/root/.m2 --name academia-api academia-api
