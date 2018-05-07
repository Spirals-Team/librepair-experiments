#!/bin/bash

# Build ubuntu 16.04 docker image
pushd ubuntu
docker build -t apacheapex/sandbox:ubuntu-16.04 .
popd
