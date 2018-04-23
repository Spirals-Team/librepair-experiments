#!/bin/bash
#
# Runs Kudu tests with single tablet server by default.
# If '3' is given as argument, it runs tests with three tablet servers (and num_replicas = 3).

set -euo pipefail -x

export KUDU_MASTER_RPC_PORT=17051
export DOCKER_HOST_IP=$(docker network inspect bridge --format='{{index .IPAM.Config 0 "Gateway"}}')

# http://stackoverflow.com/questions/3572030/bash-script-absolute-path-with-osx
function absolutepath() {
    [[ $1 = /* ]] && echo "$1" || echo "$PWD/${1#./}"
}

SCRIPT_DIR=$(dirname $(absolutepath "$0"))

PROJECT_ROOT="${SCRIPT_DIR}/../.."

if [ $# -eq 0 ]
then
  DOCKER_COMPOSE_LOCATION="${SCRIPT_DIR}/../conf/docker-compose-single-node.yaml"
elif [ $1 -eq 1 ]
then
  DOCKER_COMPOSE_LOCATION="${SCRIPT_DIR}/../conf/docker-compose-single-node.yaml"
elif [ $1 -eq 3 ]
then
  DOCKER_COMPOSE_LOCATION="${SCRIPT_DIR}/../conf/docker-compose-three-nodes.yaml"
else
  echo unknown node configuration
  exit 1
fi

function start_docker_container() {
  # stop already running containers
  docker-compose -f "${DOCKER_COMPOSE_LOCATION}" down || true

  # start containers
  docker-compose -f "${DOCKER_COMPOSE_LOCATION}" up -d
}

function cleanup_docker_container() {
  docker-compose -f "${DOCKER_COMPOSE_LOCATION}" down
}


start_docker_container

# run product tests
pushd ${PROJECT_ROOT}
set +e
./mvnw -pl presto-kudu test -P test-kudu \
  -Dkudu.client.master-addresses=${DOCKER_HOST_IP}:${KUDU_MASTER_RPC_PORT}
EXIT_CODE=$?
set -e
popd

cleanup_docker_container

exit ${EXIT_CODE}
