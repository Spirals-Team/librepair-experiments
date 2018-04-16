#!/bin/bash

set -euo pipefail -x

KUDU_MASTER_RPC_PORT=17051
KUDU_TSERVER_RPC_PORT=17050
KUDU_MASTER_WEB_PORT=17081
KUDU_TSERVER_WEB_PORT=17080
DOCKER_HOST=$(ip -4 addr show docker0 | grep -Po 'inet \K[\d.]+')

function start_docker_container() {
  docker run -d --name apache-kudu \
  -p ${KUDU_TSERVER_WEB_PORT}:8050 \
  -p ${KUDU_MASTER_WEB_PORT}:8051 \
  -p ${KUDU_TSERVER_RPC_PORT}:7050 \
  -p ${KUDU_MASTER_RPC_PORT}:7051 \
  -e "KUDU_MASTER_EXTRA_OPTS=--webserver_advertised_addresses ${DOCKER_HOST}:${KUDU_MASTER_WEB_PORT} --rpc_advertised_addresses ${DOCKER_HOST}:${KUDU_MASTER_RPC_PORT}" \
  -e "KUDU_TSERVER_EXTRA_OPTS=--webserver_advertised_addresses ${DOCKER_HOST}:${KUDU_TSERVER_WEB_PORT} --rpc_advertised_addresses ${DOCKER_HOST}:${KUDU_TSERVER_RPC_PORT}" \
  usuresearch/kudu-docker-slim
}

function cleanup_docker_container() {
  set +e
  docker rm -f apache-kudu
  set -e
}


cleanup_docker_container

start_docker_container

# run product tests
pushd ${PROJECT_ROOT}
set +e
./mvnw -pl presto-kudu test -P test-kudu \
  -Dkudu.client.master-addresses=${DOCKER_HOST}:${KUDU_MASTER_RPC_PORT}
EXIT_CODE=$?
set -e
popd

cleanup_docker_container

exit ${EXIT_CODE}
