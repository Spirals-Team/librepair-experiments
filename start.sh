#!/bin/bash
mvn package
java -jar ./target/parkingmeter-0.0.1-SNAPSHOT.jar
