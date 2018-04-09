#!/bin/bash


cd $(dirname $0)

if [ ! -f hsqldb.jar ]; then
    wget http://puzniakowski.pl/repo/libs/hsqldb.jar
fi

cd ..

java -cp scripts/hsqldb.jar org.hsqldb.server.Server --database.0 mem:mydb --dbname.0 workdb &
