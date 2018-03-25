#!/bin/bash


cd $(dirname $0)


cd ..

java -cp scripts/hsqldb.jar org.hsqldb.server.Server --database.0 mem:mydb --dbname.0
