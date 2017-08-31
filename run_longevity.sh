#!/usr/bin/env bash

result_dir="/home/vmware/longevity_result"
mkdir $result_dir

# Tx exception
tx_aborted_log="${result_dir}/tx_aborted_log"
server_log="${result_dir}/server_log"
server_gc="${result_dir}/server_gc"
generator_gc="${result_dir}/generator_cg"

# Non tx exception
exceptions="${result_dir}/exceptions"

log="${result_dir}/data_log"

# DEFAULT PARAMETERS
time_amount="12"
time_unit="h"

function cleanup {
    echo "cleanup"
    kill $corfu_pid
    kill $generator_pid
    pkill -P $$
}
trap cleanup EXIT

#cd ~/CorfuDb
cd /home/vmware/injection_framework_install/CorfuDB

# Launch corfu_server and keep pid
./bin/corfu_server -s -l $log 9000 &> $server_log &
sleep 1
corfu_pid=`pgrep -f 'org.corfudb.infrastructure.CorfuServer -s -l /home/vmware/longevity_result/data_log 9000'`


# Launch longevity app and keep pid
java -jar generator/target/LongevityRun.jar -t $time_amount -u $time_unit 2> >(tee >(grep TransactionAbortedException > $tx_aborted_log)) | grep -P -A 14 '(?<!TransactionAborted)Exception' &> $exceptions &
sleep 1
#generator_pid=`pgrep -f 'java -jar generator/target/generator-0.1-SNAPSHOT-shaded.jar'`
generator_pid=`pgrep -f 'java -jar generator/target/LongevityRun.jar'`


jstat -gcutil -t $corfu_pid 1000 &> $server_gc &
jstat -gcutil -t $generator_pid 1000 &> $generator_gc &

wait $generator_pid

# kill child of this process
pkill -P $$

