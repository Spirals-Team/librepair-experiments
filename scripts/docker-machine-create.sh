#!/bin/sh

TOKEN=$1

docker-machine create --driver digitalocean --digitalocean-access-token $TOKEN academia
