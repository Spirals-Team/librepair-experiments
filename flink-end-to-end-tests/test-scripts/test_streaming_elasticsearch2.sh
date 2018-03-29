#!/usr/bin/env bash
################################################################################
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
################################################################################

# End to end test for ElasticsearchSink2.x.

source "$(dirname "$0")"/common.sh

start_cluster

mkdir -p $TEST_DATA_DIR

ELASTICSEARCH_URL="https://download.elastic.co/elasticsearch/release/org/elasticsearch/distribution/tar/elasticsearch/2.3.5/elasticsearch-2.3.5.tar.gz"
echo "Downing Elasticsearch from $ELASTICSEARCH_URL"
curl "$ELASTICSEARCH_URL" > $TEST_DATA_DIR/elasticsearch.tar.gz

tar xzf $TEST_DATA_DIR/elasticsearch.tar.gz -C $TEST_DATA_DIR/
ELASTICSEARCH_DIR=$TEST_DATA_DIR/elasticsearch-2.3.5

# start elasticsearch cluster
$ELASTICSEARCH_DIR/bin/elasticsearch -daemon

CURRENT_DIR=$(cd "$( dirname "$0"  )" && pwd )

TEST_PROGRAM_JAR=$CURRENT_DIR/../flink-elasticsearch2-test/target/Elasticsearch2SinkExample.jar

# run the Flink job
$FLINK_DIR/bin/flink run -p 1 $TEST_PROGRAM_JAR \
  --index index \
  --type type 

touch $TEST_DATA_DIR/output

curl 'localhost:9200/index/_search?q=*&pretty&size=21' > $TEST_DATA_DIR/output

if [ -n "$(grep '\"total\" : 21' $TEST_DATA_DIR/output)" ]; then
    echo "Elasticsearch end to end test pass."
fi

# make sure to stop elasticsearch process at the end.
function shutdown_elasticsearch_cluster {
   pid=$(jps | grep Elasticsearch | awk '{print $1}')
   kill -SIGTERM $pid

   # make sure to run regular cleanup as well
   cleanup
}

# make sure to stop elasticsearch/flink clusters in case of unexpected things.
trap shutdown_elasticsearch_cluster INT
trap shutdown_elasticsearch_cluster EXIT
