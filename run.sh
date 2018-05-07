#!/usr/bin/env bash


mvn clean install -Dmaven.test.skip=true


#所有相关进程
PIDs=`ps -ef | grep fei- | grep -v grep | awk '{print $2}'`

#停止进程
for PID in $PIDs; do
    kill $PID > /dev/null 2>&1
    echo "kill -9 $PID"
done



java -jar ./target/fei-0.0.1-SNAPSHOT.jar --spring.profiles.active=tencent &

sleep 1
tail -f logs/spring-boot-logging.log