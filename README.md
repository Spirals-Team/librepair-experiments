[![Build Status](https://travis-ci.org/Arquisoft/InciDashboard_i3b.svg?branch=master)](https://travis-ci.org/Arquisoft/InciDashboard_i3b)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/43af5c0e2a6745df932bd77d84a0103f)](https://www.codacy.com/app/jelabra/InciDashboard_i3b?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Arquisoft/InciDashboard_i3b&amp;utm_campaign=Badge_Grade)
[![codecov](https://codecov.io/gh/Arquisoft/InciDashboard_i3b/branch/master/graph/badge.svg)](https://codecov.io/gh/Arquisoft/InciDashboard_i3b)

This module is part of the project implemented for the laboratory assignment of the course "Software Architecture". The course is part of the Degree in Software Engineering, School of Computer Science Engineering, University of Oviedo. The whole system is divided in four modules developed by two subteams ([Team A](https://github.com/orgs/Arquisoft/teams/course1718_i3b2) and [Team B](https://github.com/orgs/Arquisoft/teams/course1718_i3b1)). These modules are:

* Module 1.- [Loader](https://github.com/Arquisoft/Loader_i3b)
* Module 2.- [Agents management](https://github.com/Arquisoft/Agents_i3b)
* Module 3.- [Incidence Management](https://github.com/Arquisoft/InciManager_i3b)
* Module 4.- [Analysis and dashboard](https://github.com/Arquisoft/InciDashboard_i3b)
* Common module.- [Inci_i3b](https://github.com/Arquisoft/Inci_i3b)

# InciDashboard
In this repository you will find the implementation of the fourth module. It allows the visualization and management of the incidents by the operators (properties, information about the incidents, state...). It will receive the incidents submitted by the [InciManagement module](https://github.com/Arquisoft/InciManager_i3b) via Apache Kafka and it will allow the visualization of the geolocated incidents in a map, as well as their values, state and evolution.

# Authors
* [Jorge García Marín (UO245241)](https://github.com/JorgeGarciaMarin)
* [Sergio Santano Álvarez (UO244858)](https://github.com/sergiosantano)

# Set up your machine
For executing this module you will need to have installed in your computer some software:
* First of all you will need MongoDB. You can download and install mongo from [here](https://www.mongodb.com/dr/fastdl.mongodb.org/win32/mongodb-win32-x86_64-2008plus-ssl-3.4.2-signed.msi/download). Once installed you must create an empty folder called "data\db" in the root of the partition where you installed mongo (Example: "C:\data\db").
* You will also need to have Apache-Zookeper and Apache-Kafka installed. You can download them both from  [here](https://www.apache.org/dyn/closer.cgi?path=/kafka/1.0.1/kafka_2.11-1.0.1.tgz). Once you have downloaded them, decompress the files and download the script you have in the root directory of github with name script_ejecucion.bat. Move it to the kafka folder at the same level as the bin folder or congif folder.

# Run the module
Once your machine has the needed software installed, this are the steps you have to follow:
1. Clone the repository in your computer
1. Run mongod.exe
1. Start zookeeper and kafka by executing the script_ejecucion.bat file. This step is not compulsory. You can start it following the instructions of [Kafka quickstart](https://kafka.apache.org/quickstart).
1. Open a new command line window and move to the directory where you have cloned the repository.
1. Execute the commands
```bash
mvn clean install
mvn test
mvn spring-boot:run
```
1. One it´s running, open http://localhost:8090/index in your browser

