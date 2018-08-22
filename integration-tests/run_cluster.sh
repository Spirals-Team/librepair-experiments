# cleanup 
for node in druid-historical druid-coordinator druid-overlord druid-router druid-broker druid-middlemanager druid-zookeeper-kafka druid-metadata-storage;
do
docker stop $node
docker rm $node
done

# environment variables
DIR=$(cd $(dirname $0) && pwd)
DOCKERDIR=$DIR/docker
SHARED_DIR=${HOME}/shared
SUPERVISORDIR=/usr/lib/druid/conf
RESOURCEDIR=$DIR/src/test/resources

# so docker IP addr will be known during docker build
echo ${DOCKER_IP:=127.0.0.1} > $DOCKERDIR/docker_ip

# setup client keystore
./docker/tls/generate-client-certs-and-keystores.sh

# Make directories if they dont exist
mkdir -p $SHARED_DIR/logs
mkdir -p $SHARED_DIR/tasklogs

# install druid jars 
rm -rf $SHARED_DIR/docker
cp -R docker $SHARED_DIR/docker
mvn -B dependency:copy-dependencies -DoutputDirectory=$SHARED_DIR/docker/lib

# Build Druid Cluster Image
docker build -t druid/cluster $SHARED_DIR/docker

# Start zookeeper and kafka
docker run -d --privileged --name druid-zookeeper-kafka -p 2181:2181 -p 9092:9092 -p 9093:9093 -v $SHARED_DIR:/shared -v $DOCKERDIR/zookeeper.conf:$SUPERVISORDIR/zookeeper.conf -v $DOCKERDIR/kafka.conf:$SUPERVISORDIR/kafka.conf druid/cluster

# Start MYSQL 
docker run -d --privileged --name druid-metadata-storage -v $SHARED_DIR:/shared -v $DOCKERDIR/metadata-storage.conf:$SUPERVISORDIR/metadata-storage.conf druid/cluster

# Start Overlord
docker run -d --privileged --name druid-overlord -p 8090:8090 -p 8290:8290 -v $SHARED_DIR:/shared -v $DOCKERDIR/overlord.conf:$SUPERVISORDIR/overlord.conf --link druid-metadata-storage:druid-metadata-storage --link druid-zookeeper-kafka:druid-zookeeper-kafka druid/cluster

# Start Coordinator
docker run -d --privileged --name druid-coordinator -p 8081:8081 -p 8281:8281 -v $SHARED_DIR:/shared -v $DOCKERDIR/coordinator.conf:$SUPERVISORDIR/coordinator.conf --link druid-overlord:druid-overlord --link druid-metadata-storage:druid-metadata-storage --link druid-zookeeper-kafka:druid-zookeeper-kafka druid/cluster

# Start Historical 
docker run -d --privileged --name druid-historical -p 8083:8083 -p 8283:8283 -v $SHARED_DIR:/shared -v $DOCKERDIR/historical.conf:$SUPERVISORDIR/historical.conf --link druid-zookeeper-kafka:druid-zookeeper-kafka druid/cluster

# Start Middlemanger
docker run -d --privileged --name druid-middlemanager -p 8091:8091 -p 8291:8291 -p 8100:8100 -p 8101:8101 -p 8102:8102 -p 8103:8103 -p 8104:8104 -p 8105:8105 -p 8300:8300 -p 8301:8301 -p 8302:8302 -p 8303:8303 -p 8304:8304 -p 8305:8305 -v $RESOURCEDIR:/resources -v $SHARED_DIR:/shared -v $DOCKERDIR/middlemanager.conf:$SUPERVISORDIR/middlemanager.conf --link druid-zookeeper-kafka:druid-zookeeper-kafka --link druid-overlord:druid-overlord druid/cluster

# Start Broker 
docker run -d --privileged --name druid-broker -p 8082:8082 -p 8282:8282 -v $SHARED_DIR:/shared -v $DOCKERDIR/broker.conf:$SUPERVISORDIR/broker.conf --link druid-zookeeper-kafka:druid-zookeeper-kafka --link druid-middlemanager:druid-middlemanager --link druid-historical:druid-historical druid/cluster

# Start Router
docker run -d --privileged --name druid-router -p 8888:8888 -p 9088:9088 -v $SHARED_DIR:/shared -v $DOCKERDIR/router.conf:$SUPERVISORDIR/router.conf --link druid-zookeeper-kafka:druid-zookeeper-kafka --link druid-coordinator:druid-coordinator --link druid-broker:druid-broker druid/cluster
