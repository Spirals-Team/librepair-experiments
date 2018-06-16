#!/bin/sh

set +e
docker rmi -f academia-client-web-prod

set -e
docker build -f scripts/Dockerfile-prod -t academia-client-web-prod .
