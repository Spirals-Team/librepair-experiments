#!/bin/bash


cd $(dirname $0)


cd ..

#in memory database
java -cp scripts/hsqldb.jar org.hsqldb.server.Server --database.0 mem:mydb --dbname.0 workdb
