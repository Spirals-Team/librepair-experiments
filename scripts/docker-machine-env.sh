#!/bin/sh

echo "setup academia env:"
docker-machine env academia
echo

echo "remove academia env:"
docker-machine env -u
echo

echo "academia droplet ip:"
docker-machine ip academia
echo
