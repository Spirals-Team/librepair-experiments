# Incident Management System (Group i3b)

[![Build Status](https://travis-ci.org/Arquisoft/Loader_i3b.svg?branch=master)](https://travis-ci.org/Arquisoft/Loader_i3b)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/43af5c0e2a6745df932bd77d84a0103f)](https://www.codacy.com/app/jelabra/Loader_i3b?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Arquisoft/Loader_i3b&amp;utm_campaign=Badge_Grade)
[![codecov](https://codecov.io/gh/Arquisoft/Loader_i3b/branch/master/graph/badge.svg)](https://codecov.io/gh/Arquisoft/Loader_i3b)


This module is part of the project implemented for the laboratory assignment of the course "Software Architecture". The course is part of the Degree in Software Engineering, School of Computer Science Engineering, University of Oviedo. The whole system is divided in four modules developed by two subteams ([Team A](https://github.com/orgs/Arquisoft/teams/course1718_i3b2) and [Team B](https://github.com/orgs/Arquisoft/teams/course1718_i3b1)). These modules are:

* Module 1.- [Loader](https://github.com/Arquisoft/Loader_i3b)
* Module 2.- [Agents management](https://github.com/Arquisoft/Agents_i3b)
* Module 3.- Incidence Management
* Module 4.- Analysis and dashboard


# Loader_i3b

In this repository you will find the implementation of the first module. It is in charge of loading the data from agents that can submit incidences to the system. The module has been implemented as a batch application that loads the data from an excel file containing the agents information.

# Authors 2017

* Gonzalo de la Cruz Fernández (UO244583)
* Oriol Invernón Llaneza (UO245303)
* Adrian Mirón Cao (UO244843)

# Maintainers 2018

* [Jorge García Marín (UO245241)](https://github.com/JorgeGarciaMarin)
* [Sergio Santano Álvarez (UO244858)](https://github.com/sergiosantano)

# Set up 
### Java Development Kit (JDK)
The Java Development Kit (JDK) is a software development environment used for developing Java applications and applets. It includes the Java Runtime Environment (JRE), an interpreter/loader (java), a compiler (javac), an archiver (jar), a documentation generator (javadoc) and other tools needed in Java development

### MongoDB
As the project uses a MongoDB databse you need to install it in order to use the application. You can download and install mongo from [here](https://www.mongodb.com/dr/fastdl.mongodb.org/win32/mongodb-win32-x86_64-2008plus-ssl-3.4.2-signed.msi/download). Once installed you must create an empty folder called "data\db" in the root of the partition where you installed mongo (Example: "C:\data\db").

### Getting the project
In order to obtain the repository you will need to download it or clone it in case you have git installed in your machine using the following command:
```sh
  git clone https://github.com/Arquisoft/Loader_i3b
  ```
If you don´t have git downloaded in the main page of the [repository](https://github.com/Arquisoft/Loader_i3b) you will find a green button on the top-right that sais: **Clone or Download**. Select Download ZIP option and unzip it once it´s downloaded.
  
# Run instructions

In this section three run methodologies are explained in order to offer different options to our users.

## Direct run from command line
In order to facilitate the execution, a folder called runnable has been created. The content of this folder is the following one:
* agents.xlsx : Agents information file containing some test agents. This file can be modified to add the agent information you want. **Remember to keep the format**
* loader.jar  : The executable you have to run.
* master.csv  : Master file example containing some agents kind. This file can be modified to add the kinds of agents. All the kinds used in the _agents.xlsx_ must be recorded in here. **Remember to keep the format**

### How to run
1. For running the application open this folder and edit (if neccessary) the .xlsx and .csv file. 
1. Go to the directory where mongoDB was installed (for example _C:\Program Files\MongoDB\Server\3.4\bin_) and execute the **mongod.exe**
1. Open the command line by pressing the Win + R keys on your keyboard. Then, type cmd or cmd.exe and press Enter or click/tap OK.
  1. Move to the folder you have downloaded (for example _C:\Users\user\Desktop\runnable_) and execute the following command:
  1. Write the following command. Take into account that the master.csv is always written first and then agents.xlsx:
  ```sh
  java -jar loader.jar master.csv agents.xlsx
  ```

## Generating the executable with maven
If you want to generate your own executable, first you will need to download and install Maven from this [link](https://maven.apache.org/download.cgi). We recommend to choose the Binary zip archive and follow this [installation instructions](https://maven.apache.org/install.html).

Once installed, open the command line (instructions explained in *How to run*), go to the repository you have downloaded and execute the following command:
```sh
  mvn package
  ```
A couple of .jar files will be generated in a directory called **target** within the repository. Now you will only have to execute following instructions in *How to run* explained before using the **Loader_i3b-0.0.1-jar-with-dependencies.jar** and the two files (first master.csv and then agents.xlsx) that you should create or download from the folder [runnable](). If you create them **you must follow the format**.

## Executing from an IDE
Another option is to execute the application using an IDE (Eclipse, IntelliJ...). You only have to download or clone the repository, import it to your IDE and run the application (you would need to configure how to run it adding the names of the files .csv and .xlsx). In this case, you have some execution files in the path _source/test/resources_.



